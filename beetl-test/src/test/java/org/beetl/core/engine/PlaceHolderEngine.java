package org.beetl.core.engine;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.statement.*;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlaceHolderEngine extends  DefaultTemplateEngine{
	//所有的占位符，模板文件-> 占位符列表
	Map<Object, List<String>>  all = new ConcurrentHashMap<>();
	ThreadLocal threadLocal = new ThreadLocal();
	public Program createProgram(Resource resource, Reader reader, Map<Integer, String> textMap, String cr,
			GroupTemplate gt) {
		all.put(resource.getId(),new ArrayList<>());
		threadLocal.set(resource.getId());
		return super.createProgram(resource,reader,textMap,cr,gt);
	}
	@Override
	protected GrammarCreator getGrammarCreator(GroupTemplate groupTemplate) {
		GrammarCreator result = new PlaceHolderCreator(this);
		return result;
	}

	static class PlaceHolderCreator extends  GrammarCreator{
		PlaceHolderEngine placeHolderEngine;
		public PlaceHolderCreator(PlaceHolderEngine placeHolderEngine){
			this.placeHolderEngine = placeHolderEngine;
		}
		public PlaceholderST createTextOutputSt(Expression exp, FormatExpression format) {

			Object resourceId = placeHolderEngine.threadLocal.get();
			List<String> expressList = placeHolderEngine.all.get(resourceId);
			expressList.add(exp.token.text);
			return super.createTextOutputSt(exp,format);
		}

	}
}
