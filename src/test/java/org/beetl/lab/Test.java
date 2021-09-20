package org.beetl.lab;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.config.BeetlConfig;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.statement.Program;
import org.beetl.core.statement.ProgramMetaData;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;

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

        gt.registerFunctionPackageAsRoot(TestUser.class);

        for (int i = 0; i < 3; i++) {
            Template t = gt.getTemplate("/hello.txt");
			ProgramMetaData programMetaData = t.program.metaData;
			ByteArrayOutputStream  bs  = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(programMetaData);
			System.out.println(bs.size());

        }
        int a = 1;

    }

}
