package org.beetl.core.cache;

import org.beetl.core.BasicTestCase;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CacheTest extends BasicTestCase
{

	@Test
	public void testLRU() throws Exception
	{

		String t1 = "${a}";
		String t2 = "${a+1}";
		String t3 = "${a+2}";
		GroupTemplate newGt = getLRUCache(1);
		StringTemplateResourceLoader loader = new StringTemplateResourceLoader();

		Template t = newGt.getTemplate(t1,loader);
		t = newGt.getTemplate(t2,loader);

		Object programCache = newGt.getProgramCache().get(t1);
		Assert.assertNull(programCache);

		t = newGt.getTemplate(t3,loader);
		programCache = newGt.getProgramCache().get(t2);
		Assert.assertNull(programCache);
		programCache = newGt.getProgramCache().get(t3);
		Assert.assertNotNull(programCache);

	}


	@Test
	public void testCaffiCaffeine() throws Exception
	{

		String t1 = "${a}";
		String t2 = "${a+1}";
		String t3 = "${a+2}";

		GroupTemplate newGt = getCaffeineCache(1);
		StringTemplateResourceLoader loader = new StringTemplateResourceLoader();

		Template t = newGt.getTemplate(t1,loader);
		t = newGt.getTemplate(t2,loader);
		Assert.assertEquals(1,newGt.getProgramCache().size());

		Object programCache = newGt.getProgramCache().get(t1);
		Assert.assertNull(programCache);

		t = newGt.getTemplate(t3,loader);
		Assert.assertEquals(1,newGt.getProgramCache().size());
		programCache = newGt.getProgramCache().get(t3);
		Assert.assertNotNull(programCache);

	}

	private GroupTemplate getLRUCache(int size)
	{
		ClasspathResourceLoader rs = new ClasspathResourceLoader("/template");
		Configuration cfg;
		try
		{
			cfg = Configuration.defaultConfiguration();
			cfg.setCacheClass("org.beetl.core.impl.cache.LRUBeetlCache");
			cfg.getPs().setProperty("cache.size",String.valueOf(size));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		GroupTemplate gt = new GroupTemplate(rs, cfg);
		return gt;
	}

	private GroupTemplate getCaffeineCache(int size)
	{
		ClasspathResourceLoader rs = new ClasspathResourceLoader("/template");
		Configuration cfg;
		try
		{
			cfg = Configuration.defaultConfiguration();
			cfg.setCacheClass("org.beetl.core.impl.cache.CaffeineCache");
			cfg.getPs().setProperty("cache.size",String.valueOf(size));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		GroupTemplate gt = new GroupTemplate(rs, cfg);
		return gt;
	}
}
