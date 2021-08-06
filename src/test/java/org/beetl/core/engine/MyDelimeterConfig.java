package org.beetl.core.engine;

import org.beetl.core.Configuration;
import org.beetl.core.DelimeterConfig;

public class MyDelimeterConfig implements DelimeterConfig {

	Configuration.DelimeterHolder javaPsHolder = null;
	Configuration.DelimeterHolder javaStatementHolder = null;

	Configuration.DelimeterHolder htmlPsHolder = null;
	Configuration.DelimeterHolder htmlStatementHolder = null;

	public MyDelimeterConfig(){
		javaPsHolder = new Configuration.DelimeterHolder("${".toCharArray(),"}".toCharArray());
		htmlPsHolder = new Configuration.DelimeterHolder("${".toCharArray(),"}".toCharArray());

		javaStatementHolder = new Configuration.DelimeterHolder("//:".toCharArray(),null);
		htmlStatementHolder = new Configuration.DelimeterHolder("<!--:".toCharArray(),"-->".toCharArray());
	}

	@Override
	public Configuration.DelimeterHolder getPlaceHolder(Object id) {
		if(isJavaSourceTemplate(id)){
			return javaPsHolder;
		}else{
			return htmlPsHolder;
		}
	}

	@Override
	public Configuration.DelimeterHolder getStatement(Object id) {
		if(isJavaSourceTemplate(id)){
			return javaStatementHolder;
		}else{
			return htmlStatementHolder;
		}
	}

	protected boolean isHtmlTemplate(Object id){
		return isEnd(id,"html");
	}
	protected  boolean isJavaSourceTemplate(Object id){
		return isEnd(id,"java");
	}

	protected  boolean isEnd(Object id,String suffix){
		String file = (String)id;
		return file.endsWith("."+suffix);
	}
}
