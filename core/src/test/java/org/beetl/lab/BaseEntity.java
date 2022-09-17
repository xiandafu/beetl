package org.beetl.lab;

interface BaseEntity {
    default int getPage() {
        return 1;
    }
}
