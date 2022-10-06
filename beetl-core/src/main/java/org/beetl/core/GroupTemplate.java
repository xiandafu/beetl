/*
 [The "BSD license"]
 Copyright (c) 2011-2022  闲大赋 (李家智)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
     derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.beetl.core;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.beetl.core.runtime.BeetlRuntime;
import org.beetl.core.runtime.IBeetlCache;
import org.beetl.core.cache.ContextBuffer;
import org.beetl.core.cache.ContextLocalBuffers;
import org.beetl.core.event.Event;
import org.beetl.core.event.EventListener;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.HTMLTagParserException;
import org.beetl.core.fun.FunctionWrapper;
import org.beetl.core.fun.ObjectUtil;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.misc.ClassSearch;
import org.beetl.core.misc.PrimitiveArrayUtil;
import org.beetl.core.om.AABuilder;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.statement.ErrorGrammarProgram;
import org.beetl.core.statement.Program;
import org.beetl.core.tag.TagFactory;
import org.beetl.core.text.AttributeNameConvert;
import org.beetl.core.text.TextParser;

/**
 * 系统核心类，重量级对象，详见指南
 *
 * @author xiangdafu
 */
public class GroupTemplate {

    /* 模板在运行过程中,class方法，accessory调用等需要的classLoader */
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader() != null
            ? Thread.currentThread().getContextClassLoader()
            : GroupTemplate.class.getClassLoader();

    AABuilder attributeAccessFactory = new AABuilder();
    ResourceLoader resourceLoader = null;
    Configuration conf = null;
    TemplateEngine engine = null;
    IBeetlCache programCache ;

    /** 事件监听器列表 */
    List<EventListener> events = new ArrayList<>();

    /** 所有注册的方法 */
    Map<String, Function> fnMap = new HashMap<>();
    /** 格式化函数 */
    Map<String, Format> formatMap = new HashMap<>();
    /** 默认的格式化函数 */
    Map<Class, Format> defaultFormatMap = new HashMap<>(0);
    /** 虚拟函数列表 */
    List<VirtualAttributeEval> virtualAttributeList = new ArrayList<>();
    /** 虚类与虚类属性的映射 */
    Map<Class, VirtualClassAttribute> virtualClass = new HashMap<>();
    /** 标签函数 */
    Map<String, TagFactory> tagFactoryMap = new HashMap<>();
    /** 用于查找类的工具 */
    ClassSearch classSearch = null;
    /** 本地调用(这里就是Java)安全管理器 */
    NativeSecurityManager nativeSecurity = null;
    /** 错误处理类 */
    ErrorHandler errorHandler = null;
    /** 共享变量 */
    Map<String, Object> sharedVars = null;
    /** 缓冲区 */
    ContextLocalBuffers buffers = null;
    /** 用于解析html tag得属性，转化为符合js变量名字 */
    AttributeNameConvert htmlTagAttrNameConvert = null;

    DelimeterConfig delimeterConfig = null;

    /**
     * 使用默认的配置和默认的模板资源加载器{@link ClasspathResourceLoader}，
     */
    public GroupTemplate() {
        try {
            this.conf = Configuration.defaultConfiguration();
            init();
            initResourceLoader();
        } catch (Exception ex) {
            throw new RuntimeException("初始化失败", ex);
        }

    }

    /**
     * 使用指定的配置和默认的资源加载器{@link ClasspathResourceLoader}
     */
    public GroupTemplate(Configuration conf) {

        try {
            this.conf = conf;
            init();
            initResourceLoader();
        } catch (Exception ex) {
            throw new RuntimeException("初始化失败", ex);
        }

    }

    /**
     * 使用loader 和 conf初始化GroupTempalte
     *
     * @param loader 资源加载器
     * @param conf   模板引擎配置
     */

    public GroupTemplate(ResourceLoader loader, Configuration conf) {
        this(loader, conf, null);
    }

	/**
	 *  beetl加载类都通过classloader加载，在某些框架，需要显示设置一个classloader
	 * @param loader
	 * @param conf
	 * @param classLoader  默认是 Thread.currentThread().getContextClassLoader() 或者GroupTemplate的加载类
	 */
    public GroupTemplate(ResourceLoader loader, Configuration conf, ClassLoader classLoader) {
        try {
            this.resourceLoader = loader;
            this.classLoader = classLoader == null ? this.classLoader : classLoader;
            this.conf = conf;
            init();
            initResourceLoader();
        } catch (Exception ex) {
            throw new IllegalStateException("初始化失败 " + ex.getMessage(), ex);
        }

    }

    protected void initResourceLoader() {
        if (this.resourceLoader == null) {
            this.resourceLoader = (ResourceLoader) ObjectUtil.instance(conf.resourceLoader, classLoader);
        }
        resourceLoader.init(this);
    }

    protected void init() {
        conf.build();
        engine = (TemplateEngine) ObjectUtil.instance(conf.getEngine(), classLoader);
        this.initCache();
        this.initFunction();
        this.initFormatter();
        this.initTag();
        this.initVirtual();
        this.initBuffers();

        classSearch = new ClassSearch(conf.getPkgList(), this);
        nativeSecurity = (NativeSecurityManager) ObjectUtil.instance(conf.getNativeSecurity(), this.classLoader);
        if (conf.errorHandlerClass == null) {
            errorHandler = null;
        } else {
            errorHandler = (ErrorHandler) ObjectUtil.instance(conf.errorHandlerClass, classLoader);
        }

        htmlTagAttrNameConvert = (AttributeNameConvert) ObjectUtil.instance(conf.htmlTagAttributeConvert, classLoader);

        if(conf.delimeterClass!=null){
        	this.delimeterConfig = (DelimeterConfig)ObjectUtil.instance(conf.delimeterClass,classLoader);
		}else{
			this.delimeterConfig = new DelimeterConfig.DefaultDelimeterConfig(conf);
		}

    }

    protected  void initCache(){
    	String cacheClass = conf.getCacheClass();
		IBeetlCache cache = this.programCache = BeetlRuntime.getCache(cacheClass);
		cache.init(conf);
	}

    protected void initFunction() {
        Map<String, String> fnMap = this.conf.fnMap;
        for (Entry<String, String> entry : fnMap.entrySet()) {
            String name = entry.getKey();
            String clsName = entry.getValue();
            this.registerFunction(name, (Function) ObjectUtil.instance(clsName, classLoader));
        }

        Map<String, String> fnpMap = this.conf.fnPkgMap;
        for (Entry<String, String> entry : fnpMap.entrySet()) {
            String name = entry.getKey();
            String clsName = entry.getValue();
            this.registerFunctionPackage(name, ObjectUtil.getClassByName(clsName, this.classLoader),
                    ObjectUtil.tryInstance(clsName, classLoader));
        }

    }

    protected void initFormatter() {
        Map<String, String> formatMap = this.conf.formatMap;
        for (Entry<String, String> entry : formatMap.entrySet()) {
            String name = entry.getKey();
            String clsName = entry.getValue();
            this.registerFormat(name, (Format) ObjectUtil.instance(clsName, classLoader));
        }

        Map<String, String> defaultFormatMap = this.conf.defaultFormatMap;
        Map<String, Format> temp = new HashMap<String, Format>();
        for (Entry<String, String> entry : defaultFormatMap.entrySet()) {
            String defaultType = entry.getKey();
            String formatClass = entry.getValue();
            Format format = temp.get(formatClass);
            if (format == null) {
                format = (Format) ObjectUtil.instance(formatClass, classLoader);
                temp.put(formatClass, format);
            }
            this.registerDefaultFormat(ObjectUtil.getClassByName(defaultType, this.classLoader), format);
        }

        // 原始类型无法通过反射获取，因此不再配置文件里
        Format numberFormat = temp.get("org.beetl.ext.format.NumberFormat");
        this.registerDefaultFormat(short.class, numberFormat);
        this.registerDefaultFormat(long.class, numberFormat);
        this.registerDefaultFormat(int.class, numberFormat);
        this.registerDefaultFormat(float.class, numberFormat);
        this.registerDefaultFormat(double.class, numberFormat);

    }

    protected void initTag() {

        Map<String, String> tagMap = this.conf.tagMap;
        for (Entry<String, String> entry : tagMap.entrySet()) {
            String name = entry.getKey();
            String clsName = entry.getValue();
            this.registerTag(name, ObjectUtil.getClassByName(clsName, this.classLoader));
        }

        Map<String, String> tagFactoryMap = this.conf.tagFactoryMap;
        for (Entry<String, String> entry : tagFactoryMap.entrySet()) {
            String name = entry.getKey();
            String clsName = entry.getValue();
            this.registerTagFactory(name, (TagFactory) ObjectUtil.instance(clsName, classLoader));
        }

    }

    protected void initVirtual() {

        Map<String, String> map = conf.getVirtualClass();
        for (Entry<String, String> entry : map.entrySet()) {
            String attr = entry.getKey();
            Class clz = ObjectUtil.getClassByName(entry.getValue(), this.classLoader);
            VirtualAttributeEval virtualAttributeEval = (VirtualAttributeEval) ObjectUtil.instance(entry.getValue(), this.classLoader);
            this.registerVirtualAttributeEval(virtualAttributeEval);
        }
    }

    protected void initBuffers() {
        buffers = new ContextLocalBuffers(conf.bufferNum, conf.bufferSize);
    }

    /**
     * GroupTempalte 动态加载默写类使用的classloader
     *
     * @param classLoader 资源加载器
     */

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 获得脚本
     */
    public Script getScript(Object key) {
        return loadScriptTemplate(key, this.resourceLoader);
    }


    /**
     * 获得脚本
     */
    public Script getScript(Object key, ResourceLoader loader) {
        return loadScriptTemplate(key, loader);
    }


    /**
     * 执行某个脚本，参数是paras，返回的是顶级变量
     *
     * @throws BeetlException ,可以通过ErrorInfo 来获取异常信息详细信息
     */
    public Map runScript(Object key, Map<String, Object> paras) throws BeetlException {
        return this.runScript(key, paras, new StringWriter());
    }



    /**
     * 执行某个脚本，参数是paras，返回的是顶级变量 ,如果script有输出，则输出到writer里
     */
    public Map runScript(Object key, Map<String, Object> paras, Writer w) throws BeetlException {
        return this.runScript(key, paras, w, this.resourceLoader);
    }



    /**
     * 执行某个脚本，参数是paras，返回的是顶级变量
     */
    public Map runScript(Object key, Map<String, Object> paras, Writer w, ResourceLoader loader)
            throws BeetlException {
        Script t = loadScriptTemplate(key, loader);
        t.fastBinding(paras);

        t.renderTo(w);
        if (t.isSuccess()) {
            Map map = t.getResult();
            return map;
        } else {
            throw t.ex;
        }

    }


    public BeetlException validateTemplate(Object key, ResourceLoader loader) {
        Template t = getTemplate(key, loader);
        return t.validate();
    }



    public BeetlException validateTemplate(Object key) {
        Template t = getTemplate(key, this.resourceLoader);
        return t.validate();
    }


    public BeetlException validateScript(Object key, ResourceLoader loader) {
        return loadScriptTemplate(key, loader).validate();
    }



    public BeetlException validateScript(Object key) {
        return loadScriptTemplate(key, this.resourceLoader).validate();
    }


    private Map getScriptTopScopeVars(Template t) {
        Map<String, Integer> idnexMap = t.program.metaData.getTemplateRootScopeIndexMap();
        Object[] values = t.ctx.vars;
        Map<String, Object> result = new HashMap<String, Object>();
        for (Entry<String, Integer> entry : idnexMap.entrySet()) {
            String name = entry.getKey();
            int index = entry.getValue();
            Object value = values[index];
            result.put(name, value);
        }
        if (values == null) {
            return null;
        }
        Object ret = t.ctx.vars[t.ctx.vars.length - 1];
        if (ret != Context.NOT_EXIST_OBJECT) {
            result.put("return", ret);
        }

        return result;
    }

    private Script loadScriptTemplate(Object key, ResourceLoader loader) {
    	Resource resource = loader.getResource(key);
        Program program = (Program) this.programCache.get(key, k -> this.loadScript(resource));

        if (resourceLoader.isModified(resource)) {
            program = this.loadScript(resource);
            this.programCache.set(key, program);
        }
        return new Script(this, program,resource, this.conf);
    }

    /**
     * 使用额外的资源加载器加载模板
     */
    public Template getTemplate(Object key, ResourceLoader loader) {
        return this.getTemplateByLoader(key, loader, null);
    }


    /**
     * 获取模板key的标有ajaxId的模板片段。
     */
    public Template getAjaxTemplate(Object key, String ajaxId, ResourceLoader loader) {
        Template template = this.getTemplateByLoader(key, loader, null);
        template.ajaxId = ajaxId;
        return template;
    }


    /**
     * 得到模板，并指明父模板
     */
    public Template getTemplate(Object key, String parent, ResourceLoader loader) {
        Template template = this.getTemplate(key, loader);
        template.isRoot = false;
        return template;
    }


    public Template getTemplate(Object key, Context parent) {
        Template template = this.getTemplateByLoader(key, this.resourceLoader, parent.localBuffer);
        template.isRoot = false;
        return template;
    }


    /**
     * 获取指定模板。
     * 注意，不能根据Template为空来判断模板是否存在，请调用ResourceLoader来判断
     */
    public Template getTemplate(Object key) {
        return getTemplateByLoader(key, this.resourceLoader, null);
    }


    /**
     * 获取模板的ajax片段，
     *
     * @param key ，key为模板resourceId
     */
    public Template getAjaxTemplate(Object key, String ajaxId) {
        Template t = getTemplateByLoader(key, this.resourceLoader, null);
        t.ajaxId = ajaxId;
        return t;
    }

    /**
     * Template类是线程安全和高效的，但只能运行一次就抛弃。如果想一直持有Template
     * 可以调用此方法获得一个TemplateProxy
     */
    public Template getTemplateProxy(Template t) {
        return new TemplateProxy(t);
    }

    private Template getTemplateByLoader(Object key, ResourceLoader loader, ContextBuffer buffers) {
		Resource templateResource = loader.getResource(key);
		//取缓存，如果不存在，创建Program
        Program program = (Program) this.programCache.get(key, k -> this.loadTemplate(templateResource));
		Resource resource  = program.res;
        if (resourceLoader.isModified(resource)) {
            program = this.loadTemplate(resource);
            this.programCache.set(key, program);
        }

        return buffers == null
                ? new Template(this, program,resource, this.conf)
                : new Template(this, program, resource,this.conf, buffers);
    }

    public Program getProgram(String key) {
        return (Program) this.programCache.get(key);
    }

    /**
     * 判断是否加载过模板
     */
    public boolean hasTemplate(Object key) {
        Program program = (Program) this.programCache.get(key);
        return program != null;
    }


    /**
     * 手工删除加载过的模板
     */
    public void removeTemplate(Object key) {
        programCache.remove(key);
    }


    private Program loadTemplate(Resource res) {
        TextParser text = null;
        try( Reader reader = res.openReader()) {
            text = new TextParser(this,delimeterConfig.getPlaceHolder(res.id), delimeterConfig.getStatement(res.id), conf.getTagConf());
            text.doParse(reader);
            Reader scriptReader = new StringReader(text.getScript().toString());
            return engine.createProgram(res, scriptReader, text.getTextVars(), text.getTextCr(), this);

        } catch (HTMLTagParserException e) {
            ErrorGrammarProgram ep = new ErrorGrammarProgram(res, text.systemCrStr);
            ep.setException(e);
            e.pushResource(res);
            return ep;
        } catch (IOException e) {
            ErrorGrammarProgram ep = new ErrorGrammarProgram(res, String.valueOf(text.cr1));
            ep.setException(new BeetlException(BeetlException.TEMPLATE_LOAD_ERROR).pushResource(res));
            return ep;
        } catch (BeetlException ex) {
            ErrorGrammarProgram ep = new ErrorGrammarProgram(res, text.systemCrStr);
            ex.pushResource(res);
            ep.setException(ex);
            return ep;
        }

    }

    private Program loadScript(Resource res)  {
		Reader scriptReader = res.openReader();
        try {

            return engine.createProgram(res, scriptReader, Collections.EMPTY_MAP,
                    System.getProperty("line.separator"), this);
        } catch (BeetlException ex) {
            ErrorGrammarProgram ep = new ErrorGrammarProgram(res, System.getProperty("line.separator"));
            ex.pushResource(res);
            ep.setException(ex);
            return ep;
        }finally {
			try {
				scriptReader.close();
			} catch (IOException ioException) {
				//忽略这个异常处理，交给用户
			}
		}

    }

    /**
     * 关闭GroupTemplate，清理所有的资源
     */
    public void close() {
        this.resourceLoader.close();

    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Configuration getConf() {
        return conf;
    }

    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void fireEvent(Event event) {
        for (EventListener l : this.events) {
            l.onEvent(event);
        }
    }

    public void addListener(EventListener eventListener) {
        this.events.add(eventListener);
    }

    public IBeetlCache getProgramCache() {
        return programCache;
    }

    public void registerFunction(String name, Function fn) {
        checkFunctionName(name);
        this.fnMap.put(name, fn);
    }

    /**
     * 注册一个function包，包名由packageName指定，方法名是对象o里的所有方法
     * 如果方法定义为void，则方法返回null，如果方法参数最后一个Context，则传入一个Context
     */
    public void registerFunctionPackage(String packageName, Object o) {
        checkFunctionName(packageName);
        registerFunctionPackage(packageName, o.getClass(), o);
    }

    /**
     * 注册一个类的所有方法，packageName+方法名是beetl的方法名
     */
    public void registerFunctionPackage(String packageName, Class cls) {
        checkFunctionName(packageName);
        Object o = ObjectUtil.tryInstance(cls.getName(), this.classLoader);
        registerFunctionPackage(packageName, cls, o);
    }

    /**
     * 注册一个类的所有方法，方法名是beetl的方法名，类似registerFunction
     */
    public void registerFunctionPackageAsRoot(Class cls) {
        Object o = ObjectUtil.tryInstance(cls.getName(), this.classLoader);
        registerFunctionPackageAsRoot(cls, o);
    }

    private void checkFunctionName(String name) {
        if (!BeetlUtil.checkNameing(name)) {
            int[] log = BeetlUtil.getLog();
            throw new RuntimeException("注册方法名不合法:" + name + ",错误位置:" + log[0] + ",出现错误的字符:" + (char) log[1]);
        }
    }

    protected void registerFunctionPackage(String packageName, Class target, Object o) {
        if (packageName.equals("_root")) {
            registerFunctionPackageAsRoot(target, o);
        } else {
            List<FunctionWrapper> list = FunctionWrapper.getFunctionWrapper(packageName, target, o);
            for (FunctionWrapper fw : list) {
                this.registerFunction(fw.functionName, fw);
            }
        }
    }

    protected void registerFunctionPackageAsRoot(Class target, Object o) {
        String packageName = "_root";
        List<FunctionWrapper> list = FunctionWrapper.getFunctionWrapper(packageName, target, o);
        for (FunctionWrapper fw : list) {
            //去掉前缀
            String functionName = fw.functionName.replace("_root.", "");
            this.registerFunction(functionName, fw);
        }
    }

    /**
     * 注册一个自定义格式化函数
     */
    public void registerFormat(String name, Format format) {
        this.formatMap.put(name, format);
    }

    public void registerDefaultFormat(Class type, Format format) {
        this.defaultFormatMap.put(type, format);
    }

    public void registerTag(String name, Class tagCls) {
        checkTagName(name);
        this.tagFactoryMap.put(name, new DefaultTagFactory(tagCls));
    }

    public void registerTagFactory(String name, TagFactory tagFactory) {
        checkTagName(name);
        this.tagFactoryMap.put(name, tagFactory);
    }

    private void checkTagName(String name) {
        if (!BeetlUtil.checkNameing(name)) {
            int[] log = BeetlUtil.getLog();
            if (log[1] == 58) {
                throw new RuntimeException(
                        "注册Tag名称不合法:" + name + ",错误位置:" + log[0] + ",出现错误的字符:" + (char) log[1] + ",请使用\'.\'");
            } else {
                throw new RuntimeException("注册Tag名称不合法:" + name + ",错误位置:" + log[0] + ",出现错误的字符:" + (char) log[1]);
            }
        }
    }

    public TagFactory getTagFactory(String name) {
        return this.tagFactoryMap.get(name);
    }

    public Function getFunction(String name) {
        return fnMap.get(name);
    }

    public Format getFormat(String name) {
        return formatMap.get(name);
    }

    public Format getDefaultFormat(Class type) {
        return this.defaultFormatMap.get(type);
    }

    public void registerVirtualAttributeEval(VirtualAttributeEval e) {
        virtualAttributeList.add(e);
    }

    public void registerVirtualAttributeClass(Class cls, VirtualClassAttribute virtual) {
        this.virtualClass.put(cls, virtual);
    }

    public VirtualClassAttribute getVirtualAttributeEval(Class c, String attributeName) {
        VirtualClassAttribute attr = virtualClass.get(c);
        if (attr != null) return attr;

        for (VirtualAttributeEval eval : virtualAttributeList) {
            if (eval.isSupport(c, attributeName)) {
                return eval;
            }
        }
        return null;
    }

    /**
     * 通过class的简单名字找到class
     */
    public Class loadClassBySimpleName(String simpleName) {
        return this.classSearch.getClassByName(simpleName);
    }

    public NativeSecurityManager getNativeSecurity() {
        return nativeSecurity;
    }

    public void setNativeSecurity(NativeSecurityManager nativeSecurity){
    	this.nativeSecurity = nativeSecurity;
	}

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    /**
     * 获得共享变量，如果未有，返回一个空的HashMap
     */
    public Map<String, Object> getSharedVars() {
        if (sharedVars == null) {
            sharedVars = new HashMap<>();
        }
        return sharedVars;
    }

    /**
     * 添加共享变量
     */
    public void setSharedVars(Map<String, Object> vars) {
        if (this.sharedVars != null) {
            this.sharedVars.putAll(vars);
        } else {
            this.sharedVars = vars;
        }
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public ClassSearch getClassSearch() {
        return classSearch;
    }

    public AABuilder getAttributeAccessFactory() {
        return attributeAccessFactory;
    }

    public void enableStrict() {
        this.conf.setStrict(true);
    }

    /**
     * 设置一个自定义引擎
     */
    public TemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

    public static class SizeVirtualAttributeEval implements VirtualAttributeEval {

        public Integer eval(Object o, String attributeName, Context ctx) {
            if (attributeName.equals("size")) {
                if (o instanceof Collection) {
                    return ((Collection) o).size();
                } else if (o instanceof Map) {
                    return ((Map) o).size();
                } else if (o.getClass().isArray()) {

                    if (o.getClass().getComponentType().isPrimitive()) {
                        return PrimitiveArrayUtil.getSize(o);
                    } else {
                        return ((Object[]) o).length;
                    }

                } else {
                    throw new IllegalArgumentException();
                }

            } else {
                throw new IllegalArgumentException();
            }

        }

        public boolean isSupport(Class c, String attributeName) {
            return (Map.class.isAssignableFrom(c) || Collection.class.isAssignableFrom(c) || c.isArray())
                    && attributeName.equals("size");
        }
    }

    public AttributeNameConvert getHtmlTagAttrNameConvert() {
        return htmlTagAttrNameConvert;
    }
}
