package org.beetl.ext.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

/**
 * Web 渲染扩展
 */
public interface WebRenderExt {
    /**
     * 将 request 变量赋值给模板，同时赋值的还有 session, request, ctxPath; 其他框架可以继承此类做更多的定制
     *
     * @param template 模板
     * @param gt       组模板
     * @param request  HTTP 请求信息
     * @param response HTTP 响应信息
     */
    void modify(Template template, GroupTemplate gt, HttpServletRequest request, HttpServletResponse response);
}
