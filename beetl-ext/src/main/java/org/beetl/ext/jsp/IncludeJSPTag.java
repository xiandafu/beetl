package org.beetl.ext.jsp;

import org.beetl.core.ResourceLoader;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.tag.Tag;
import org.beetl.ext.web.WebVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class IncludeJSPTag extends Tag {

    public void render() {

        File root = new File(BeetlUtil.getWebRoot());
        String child = (String) args[0];
        child = BeetlUtil.getRelPath(ctx.getResourceId().toString(), child);

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
                    for (Entry<String, Object> entry : paras.entrySet()) {
                        newRequestParas.put(entry.getKey(), entry.getValue());
                    }
                    requestWrapper = new BeetlServletRequestWrapper(request, newRequestParas);
                    request = requestWrapper;

                }

            }

            BeetlServletResponseWrapper rspWrapper = new BeetlServletResponseWrapper(response);
            //jsp path 总是从webroot目录开始，因此要将相对于模板root目录的jsp转成相对于webroot目录
            String prefix = this.getPrefix(root);
            String realJspPath = getJSPPath(prefix, child);
            request.getRequestDispatcher(realJspPath).include(request, rspWrapper);

            ctx.byteWriter.writeString(rspWrapper.getRealWriter().toString());

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected String getJSPPath(String prefix, String child) {
        if (child.startsWith("/")) {
            return prefix + child;
        } else {
            return prefix + "/" + child;
        }

    }

    protected String getPrefix(File root) {
        ResourceLoader rs = ctx.gt.getResourceLoader();
        if(!(rs instanceof FileResourceLoader)) {
            return "";
        }
        String loaderRoot = ((FileResourceLoader) rs).getRoot();
        File loaderFileRoot = new File(loaderRoot);
        return loaderFileRoot.equals(root)
                ? ""
                : loaderFileRoot.getAbsolutePath().substring(root.getAbsolutePath().length());
    }

}
