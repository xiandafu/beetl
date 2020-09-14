package org.beetl.core;

public interface ILoopStatus {
    public boolean hasNext();

    public Object next();

    public int getIndex();

    public int getDataIndex();

    public boolean isFirst();

    public boolean isLast();

    public boolean isEven();

    public boolean isOdd();

    public boolean hasSize();

    public int getSize();

    public boolean hasData();

}
