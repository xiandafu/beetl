package org.beetl.ext.tag.html;

import org.beetl.core.GeneralLoopStatus;
import org.beetl.core.ILoopStatus;
import org.beetl.core.tag.GeneralVarTagBinding;

import java.util.Collection;

/**
 * <pre>
 *     <html:set value="${user.name}" export="name" ></html:set>
 *
 * </pre>
 */
public class ForeachHtmlTag extends GeneralVarTagBinding {
    @Override
    public void render() {
        if (!this.containHtmlAttribute("items")) {
            throw new IllegalArgumentException(this.getHtmlTagName() + " 期望 items属性");
        }
        Object value = this.getAttributeValue("items");
        if (value == null) {
            throw new NullPointerException(this.getHtmlTagName() + " items为null");
        }

        //第三个是绑定的列表
        boolean containStatus = false;
        String str = (String) this.args[2];
        if (str.indexOf(',') != -1) {
            containStatus = true;
        }


        ILoopStatus it = GeneralLoopStatus.getIteratorStatus(value);
        while (it.hasNext()) {
            Object item = it.next();
            if (containStatus) {
                this.binds(item, it);
            } else {
                this.binds(item);
            }
            this.doBodyRender();
        }


    }
}
