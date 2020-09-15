package org.beetl.core;

public interface ILoopStatus {
    boolean hasNext();

    Object next();

    int getIndex();

    int getDataIndex();

    boolean isFirst();

    boolean isLast();

    boolean isEven();

    boolean isOdd();

    boolean hasSize();

    int getSize();

    boolean hasData();

}
