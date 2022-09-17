package org.beetl.lab;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.config.BeetlConfig;
import org.beetl.core.io.SoftReferenceWriter;
import org.beetl.core.resource.ClasspathResourceLoader;

/**
 * @author xiandafu
 */
class Test {

    /** DEBUG flag */
    private static final boolean DEBUG = BeetlConfig.DEBUG;
    /** Log TAG */
    private static final String TAG = "org.beetl.core.lab.Test";

    public static void main(String[] args) throws Exception {

        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("lab/");
        Configuration cfg = Configuration.defaultConfiguration();

        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

        for (int i = 0; i < 2; i++) {
            Template t = gt.getTemplate("/hello.txt");
			t.binding("user",new TestUser());
			try(SoftReferenceWriter sw = SoftReferenceWriter.local()){
				t.renderTo(sw);
				String str = sw.toString();
				System.out.println(str);
			}


        }
        int a = 1;

    }

}
