package org.beetl.core.lab;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.config.BeetlConfig;
import org.beetl.android.util.Log;
import org.beetl.core.resource.ClasspathResourceLoader;

/**
 * @author xiandafu
 */
public class Test {

    /** DEBUG flag */
    private static final boolean DEBUG = BeetlConfig.DEBUG;
    /** Log TAG */
    private static final String TAG = "org.beetl.core.lab.Test";

    public static void main(String[] args) throws Exception {

        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("lab/");
        Configuration cfg = Configuration.defaultConfiguration();

        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

        gt.registerFunctionPackageAsRoot(TestUser.class);

        for (int i = 0; i < 100; i++) {
			Template t = gt.getTemplate("/hello.txt");
			t.binding("user", new MyTestObject("abc"));
			t.binding("abc", 1234);
			String str = t.render();
			System.out.println(str);
		}
        int a =1;

    }

}
