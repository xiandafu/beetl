package org.beetl.core.engine;

import org.beetl.core.*;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

/**
 * 格式验证，确保输出文本不会有多余空格，空行
 * @author xiandafu
 *
 */
public class MyEngineTest extends BasicTestCase
{

	@Test
	public void testEngine() throws Exception
	{

		GroupTemplate newGt = getGt();
		MyTemplateEngine myTemplateEngine = new MyTemplateEngine();
		newGt.setEngine(myTemplateEngine);
		Template t = newGt.getTemplate("/engine/my_template.html");
		String str = t.render();
		AssertJUnit.assertEquals(this.getFileContent("/engine/my_expected.html"), str);

	}

	@Test
	public void testDelimeter() throws Exception
	{


		GroupTemplate newGt = getGt2();
		{
			Template t = newGt.getTemplate("/engine/Java_template.java");
			String str = t.render();
			AssertJUnit.assertEquals(this.getFileContent("/engine/Java_expected.java"), str);
		}

		{
			Template t = newGt.getTemplate("/engine/html_template.html");
			String str = t.render();
			AssertJUnit.assertEquals(this.getFileContent("/engine/html_expected.html"), str);
		}


	}

	@Test
	public void testPlaceHolderEngine() throws Exception
	{

		GroupTemplate newGt = getGt();
		PlaceHolderEngine myTemplateEngine = new PlaceHolderEngine();
		newGt.setEngine(myTemplateEngine);
		String id  ="/engine/placeholder_template.html";
		Template t = newGt.getTemplate(id);
		List<String> list = myTemplateEngine.all.get(id);
		Assert.assertEquals(3,list.size());
		System.out.println(list);

	}

	public GroupTemplate getGt()
	{
		ClasspathResourceLoader rs = new ClasspathResourceLoader("/template");
		Configuration cfg;
		try
		{
			cfg = Configuration.defaultConfiguration();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		GroupTemplate gt = new GroupTemplate(rs, cfg);
		return gt;
	}

	/**
	 * 设置一个配置类
	 * @return
	 */
	public GroupTemplate getGt2()
	{
		ClasspathResourceLoader rs = new ClasspathResourceLoader("/template");
		Configuration cfg;
		try
		{
			cfg = Configuration.defaultConfiguration();
			cfg.setDelimeterClass("org.beetl.core.engine.MyDelimeterConfig");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		GroupTemplate gt = new GroupTemplate(rs, cfg);
		return gt;
	}
}
