package org.beetl.ext.nutz;

import org.beetl.core.ConsoleErrorHandler;
import org.beetl.core.GroupTemplate;
import org.beetl.core.exception.BeetlException;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 通过日志框架来输出日志
 *
 * @author wendal
 */
public class LogErrorHandler extends ConsoleErrorHandler {

    private static final Log log = Logs.get();
    protected ThreadLocal<StringBuilder> sb = new ThreadLocal<StringBuilder>();

    public void processException(BeetlException ex, GroupTemplate groupTemplate,Writer writer) {
        StringBuilder _sb = new StringBuilder();
        try {
            sb.set(_sb);
            if (ex != null) {
                _sb.append(ex.getMessage()).append("\n");
                ex.printStackTrace();
            }
            super.processException(ex,groupTemplate, writer);
            log.debug(_sb);
        } finally {
            sb.set(null);
        }
    }

    protected void println(Writer w, String msg) {
        sb.get().append(msg).append('\n');
    }

    protected void print(Writer w, String msg) {
        sb.get().append(msg);
    }

    protected void printThrowable(Writer w, Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        sb.get().append(sw.getBuffer()).append("\n");
    }
}
