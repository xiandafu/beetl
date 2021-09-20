package org.beetl.lab;

import org.beetl.core.tag.Tag;

class TableTag extends Tag {
    @Override
    public void render() {
        this.doBodyRender();
    }
}
