package org.beetl.core.lab;

interface BaseEntity {
    default int getPage() {
        return 1;
    }
}
