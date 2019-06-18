package org.beetl.core.text;

public abstract class ScriptFragment   extends BeetlFragment{

	 public ScriptFragment(Source source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public void appendCr() {
		script.append(TextParser.cr1);
	}

}
