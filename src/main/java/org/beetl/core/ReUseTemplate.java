package org.beetl.core;

import org.beetl.core.exception.BeetlException;
import org.beetl.core.io.ByteWriter_Byte;
import org.beetl.core.io.ByteWriter_Char;
import org.beetl.core.io.NoLockStringWriter;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.statement.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

public class ReUseTemplate extends Template {

   boolean isNew = true;

    public ReUseTemplate(Template template) {
        super(template.gt, template.program, template.cf);
        isNew = true;
    }



    public void renderTo(ByteWriter byteWriter) {
      super.renderTo(byteWriter);
      this.isNew = false;
    }

    public void binding(String varName, Object o) {
        check();
        super.binding(varName, o);
    }

    public void fastBinding(Map map) {
        check();
        super.fastBinding(map);
    }

    public void check(){
        if(!isNew){
            this.ctx = new Context(this.gt);
        }
    }


    public Context getCtx() {
        //不返回新的，返回一个旧的，因为渲染后，遗留的变量可能需要读取
        return this.ctx;
    }


    /**
     * 语法校验，如果返回BeetlException，则表示语法有错，返回null，语法无错误
     * @return
     */
    public BeetlException validate() {
        if (!(program instanceof ErrorGrammarProgram)) {
            return null;
        }
        ErrorGrammarProgram error = (ErrorGrammarProgram) program;
        return error.getException();
    }




}
