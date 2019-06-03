package org.beetl.core.engine;

import java.io.Reader;
import java.util.Map;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.om.AABuilder;
import org.beetl.core.om.AsmAAFactory;
import org.beetl.core.statement.*;
import org.beetl.core.statement.optimal.BlockStatementOptimal;
import org.beetl.core.statement.optimal.VarRefOptimal;

/**
 * POJO 的属性采用ASM来访问
 */
public class FastRuntimeEngine extends DefaultTemplateEngine {

	public FastRuntimeEngine(){
		super();
		//TOOD,改成非静态，跟引擎相关属性
		AABuilder.defalutAAFactory = new AsmAAFactory();
	}
	@Override
	public Program createProgram(Resource rs, Reader reader, Map<Integer, String> textMap, String cr,
			GroupTemplate gt) {

		Program program =  super.createProgram(rs, reader, textMap, cr, gt);
		return program;
	}


	protected GrammarCreator getGrammerCreator(GroupTemplate gt) {
		GrammarCreator grammar = new NewGrammarCreator();
		if (gt.getConf().isStrict()) {
			// 严格MVC 不允许很多语法，跟逻辑相关的
			grammar.disable("VarAssign");
			grammar.disable("Function");
			grammar.disable("IncDec");
			grammar.disable("VarRefAssignExp");
			grammar.disable("VarRefAssign");
			grammar.disable("ClassNativeCall");
			grammar.disable("InstanceNativeCall");
			grammar.disable("Arth");
			grammar.disable("Compare");
			grammar.disable("InstanceNativeCall");

		}
		return grammar;
	}


	class NewGrammarCreator extends  GrammarCreator{
		@Override
		public VarRef createVarRef(VarAttribute[] attributes, boolean hasSafe, Expression safe, GrammarToken token,
				GrammarToken firstToken) {
			check("VarRefOptimal");
			if(attributes.length==1&&!hasSafe){
				VarRef express = new VarRefOptimal(attributes[0], null, firstToken);
				return express;
			}else{
				VarRef express = new VarRef(attributes, hasSafe, safe, firstToken);
				return express;
			}

		}
		@Override
		public BlockStatement createBlock(Statement[] nodes, GrammarToken token) {
			if(nodes.length==1){
				return new BlockStatementOptimal(nodes,token);
			}
			BlockStatement block = new BlockStatement(nodes, token);
			return block;

		}

	}


}
