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


        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

        gt.registerFunctionPackageAsRoot(TestUser.class);

        Template t = gt.getTemplate("/hello.txt");

        for (int i = 0; i < 1; i++) {
            t.binding("testUser", new MyTestObject("abc"));
            System.out.println(i + " " + t.render());
        }


    }


}
