package org.beetl.core;

/**
 * 指示模板引擎通过模板id来决定模板定界符，比如以property结尾的采用"#:" 和"回车"作为定界符
 */
public interface DelimeterConfig {
	Configuration.DelimeterHolder getPlaceHolder(Object id);

	Configuration.DelimeterHolder getStatement(Object id);

	 static class DefaultDelimeterConfig implements DelimeterConfig{

		protected  Configuration configuration = null;
		Configuration.DelimeterHolder statementDelimeterHolder = null;
		Configuration.DelimeterHolder placeDelimeterHolder = null;
		DefaultDelimeterConfig(Configuration configuration){
			this.configuration =configuration;
			statementDelimeterHolder = configuration.getScriptDelimeter();
			placeDelimeterHolder = configuration.getPlaceHolderDelimeter();
		}


		@Override
		public Configuration.DelimeterHolder getPlaceHolder(Object id) {
			return placeDelimeterHolder;
		}

		@Override
		public Configuration.DelimeterHolder getStatement(Object id) {
			return statementDelimeterHolder;
		}
	}

}
