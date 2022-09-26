package org.beetl.ext.tag;

import org.beetl.core.tag.Tag;
import org.beetl.ext.jsp.BeetlServletRequestWrapper;
import org.beetl.ext.jsp.BeetlServletResponseWrapper;
import org.beetl.ext.web.WebVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *
 *     includeUrl("/user/id/1")
 *
 * </pre>
 * Created by yangyuanshuai on 15:21.
 * mail:645429330@qq.com
 * qq:645429330
 */
public class IncludeUrlTag extends Tag {
    public void render() {
        String child = (String) args[0];
        try {
            Map map = ctx.globalVar;
            WebVariable wv = (WebVariable) map.get("servlet");
            if (wv == null) {
                throw new RuntimeException("不支持JSP");
            }
            HttpServletRequest request = wv.getRequest();

            HttpServletResponse response = wv.getResponse();
            if (args.length == 2) {
                Map<String, Object> paras = (Map<String, Object>) args[1];
                if (paras.size() != 0) {
                    BeetlServletRequestWrapper requestWrapper = null;
                    Map requestPara = request.getParameterMap();
                    Map newRequestParas = new HashMap();
                    newRequestParas.putAll(requestPara);
                    for (Map.Entry<String, Object> entry : paras.entrySet()) {
                        newRequestParas.put(entry.getKey(), entry.getValue());
                    }
                    requestWrapper = new BeetlServletRequestWrapper(request, newRequestParas);
                    request = requestWrapper;

                }
            }
            BeetlServletResponseWrapper rspWrapper = new BeetlServletResponseWrapper(response);
            request.getRequestDispatcher(child).include(request, rspWrapper);
            ctx.byteWriter.writeString(rspWrapper.getRealWriter().toString());

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
