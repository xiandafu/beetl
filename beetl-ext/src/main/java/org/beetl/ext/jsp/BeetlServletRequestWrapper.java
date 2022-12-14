package org.beetl.ext.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

public class BeetlServletRequestWrapper extends HttpServletRequestWrapper {
    Map paras = null;

    public BeetlServletRequestWrapper(HttpServletRequest request, Map paras) {
        super(request);
        this.paras = paras;
    }

    @Override
    public String getParameter(String name) {
        String[] array = (String[]) paras.get(name);
        return array == null ? null : array[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return (String[]) paras.get(name);
    }

    @Override
    public Map getParameterMap() {
        return this.paras;
    }

    @Override
    public Enumeration getParameterNames() {
        return Collections.enumeration(this.paras.keySet());
    }

}
