package org.beetl.core.lab;


import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.TemplateProxy;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xiandafu
 */
public class Test {
    public static void main(String[] args) throws Exception {


        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("lab/");
        Configuration cfg = Configuration.defaultConfiguration();

        cfg.setDirectByteOutput(true);
        cfg.getResourceMap().put("tagRoot", "");
        cfg.getPkgList().add("org.beetl.core.lab.");
        cfg.setStatementStart("<%");
        cfg.setStatementEnd("%>");
        // js 里专用
//		cfg.setStatementStart2("//#");
//		cfg.setStatementEnd2(null);
//		cfg.setPlaceholderStart2("{");
//		cfg.setPlaceholderEnd2();

        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        gt.registerTag("table", TableTag.class);
        gt.registerTag("col", ColTag.class);
//		TestUser user = new TestUser("a");
//		TestUser older = new TestUser("b");
        gt.registerFunctionPackageAsRoot(TestUser.class);


        List list = new ArrayList();
        list.add(1);
        list.add(2);
        Iterator it = list.iterator();
        Template t = gt.getTemplate("/hello.txt");
        String[] array = new String[]{"efg"};
        Template st = gt.getTemplateProxy(t);
        for (int i = 0; i < 1; i++) {

            st.binding("array", array);
            System.out.println(i + " " + st.render());
        }


    }


}
