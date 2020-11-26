package org.beetl.core.runtime;

public interface IBeetlMemoryManager {

    Object newUnpaddedArray(Class<?> type, int minLen);

}
