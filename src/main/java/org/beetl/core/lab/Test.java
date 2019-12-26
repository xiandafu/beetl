package org.beetl.core.lab;


import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;
import org.beetl.core.io.CachedStringWriter;
import org.beetl.core.io.NoLockStringWriter;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 *
 * @author xiandafu
 *
 */
public class Test {
		public static void main(String[] args) throws Exception {
//			TestUserAccess ta = new TestUserAccess();
//			System.out.println(ta.value(new TestUser(),"isSuccess"));
			System.out.println("isSuccess".hashCode());
			System.out.println("success".hashCode());
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
		gt.registerTag("table",TableTag.class);
		gt.registerTag("col",ColTag.class);
//		TestUser user = new TestUser("a");
//		TestUser older = new TestUser("b");


		List list = new ArrayList();
		list.add(1);
		list.add(2);
		Iterator it = list.iterator();



//		user.set("older",older);
		for (int i = 0; i < 1; i++) {

			Template t = gt.getTemplate("/hello.txt");
//			t.binding("json",json);
			t.binding("user",new TestUser());

//			t.binding("title","hello");

			     Writer writer = CachedStringWriter.buffers.get();
			t.renderTo(writer);

			System.out.println(writer.toString());


		}


	}


}
