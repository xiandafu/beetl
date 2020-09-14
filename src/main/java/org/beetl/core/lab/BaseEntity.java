package org.beetl.core.lab;

public interface BaseEntity {
    public default int getPage() {
        return 1;
    }
}
