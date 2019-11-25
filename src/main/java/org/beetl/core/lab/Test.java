package org.beetl.core.lab;


import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;
import org.beetl.core.io.NoLockStringWriter;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.MapResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.util.*;

/**
 * @author xiandafu
 */
public class Test {
    public static void main(String[] args) {
        Template t = null;
        try {
//初始化代码
            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
            Configuration cfg = Configuration.defaultConfiguration();
            cfg.setPlaceholderStart2("{");
            cfg.setPlaceholderEnd2("}");
            cfg.setPlaceholderStart("{{");
            cfg.setPlaceholderEnd("}}");
            cfg.setEngine("org.beetl.core.engine.FastRuntimeEngine");
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            MapResourceLoader dbLoader = new MapResourceLoader();

            String str = "今天是星期{day},我的名字叫{{name}},性别{sex}";
			dbLoader.put("1",str);
            t = gt.getTemplate("1", dbLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("name", "lisr");
        map.put("day", "星期五");
        map.put("sex", "男");
        t.binding(map);
        String str = t.render();
        System.out.println(str);

    }


}
