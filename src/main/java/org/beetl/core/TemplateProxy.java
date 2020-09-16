package org.beetl.core;

import org.beetl.core.exception.BeetlException;
import org.beetl.core.io.ByteWriter_Byte;
import org.beetl.core.io.ByteWriter_Char;
import org.beetl.core.io.NoLockStringWriter;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.statement.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * 提供一个可重用的模板引擎
 * <pre>
 * Template template = gt.getTemplate();
 * Template templateProxy =  gt.getTemplateProxy(template);
 *
 * </pre>
 */
public class TemplateProxy extends Template {

    ThreadLocal<Template> local = new ThreadLocal<>();

    public TemplateProxy(Template template) {
        super(template.gt, template.program, template.cf);

    }

    public void renderTo(ByteWriter byteWriter) {
        current().renderTo(byteWriter);
        clear();
    }

    public void binding(String varName, Object o) {
        current().binding(varName, o);
    }

    public void fastBinding(Map map) {
        current().fastBinding(map);
    }

    public Context getCtx() {
        //有些功能是可以把渲染后模板的变量保存下来（比如脚本引擎)，目前此类不支持，渲染完毕就关闭
        throw new UnsupportedOperationException();
    }

    /**
     * 语法校验，如果返回BeetlException，则表示语法有错，返回null，语法无错误
     */
    public BeetlException validate() {
        return super.validate();
    }

    protected Template current() {
        Template template = local.get();
        if (template != null) {
            return template;
        }

        template = new Template(this.gt, this.program, this.cf);
        local.set(template);
        return template;

    }

    protected void clear() {
        local.set(null);
    }

}
