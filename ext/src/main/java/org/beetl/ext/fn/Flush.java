package org.beetl.ext.fn;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.exception.BeetlException;

import java.io.IOException;

public class Flush implements Function {

    @Override
    public Object call(Object[] paras, Context ctx) {
        try {
            ctx.byteWriter.flush();
            return null;
        } catch (IOException e) {
            throw new BeetlException(BeetlException.CLIENT_IO_ERROR_ERROR, e.getMessage(), e);
        }
    }

}
