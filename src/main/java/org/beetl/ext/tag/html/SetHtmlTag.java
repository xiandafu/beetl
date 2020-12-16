package org.beetl.ext.tag.html;

import org.beetl.core.tag.GeneralVarTagBinding;

/**
 * <pre>
 *     <#html:set value="${user.name}" export="name" ></#html:set>
 *
 * </pre>
 */
public class SetHtmlTag extends GeneralVarTagBinding {
    @Override
    public void render() {
        Object value = this.getAttributeValue("value");
        this.binds(value);
    }
}
