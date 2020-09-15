package org.beetl.core.lab;

public interface BaseEntity {
    default int getPage() {
        return 1;
    }
}
