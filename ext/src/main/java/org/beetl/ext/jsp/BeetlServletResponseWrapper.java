package org.beetl.ext.jsp;

import org.beetl.core.io.NoLockStringWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.PrintWriter;
import java.io.Writer;

public class BeetlServletResponseWrapper extends HttpServletResponseWrapper {
    NoLockStringWriter cw = null;
    PrintWriter writer = null;

    public BeetlServletResponseWrapper(HttpServletResponse rsp) {
        super(rsp);
    }

    public PrintWriter getWriter() {
        if (writer == null) {
            cw = new NoLockStringWriter();
            writer = new PrintWriter(cw);
        }

        return writer;
    }

    public Writer getRealWriter() {

        return cw;
    }

}
