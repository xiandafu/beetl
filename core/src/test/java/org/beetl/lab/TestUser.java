package org.beetl.lab;

import org.beetl.core.util.Log;

import java.util.Arrays;

/**
 * @author 所有测试类采用的Pojo类
 */
class TestUser implements BaseEntity {

    /** Log TAG */
    private static final String TAG = "org.beetl.core.lab.TestUser";

    String name = "default";

    boolean isMaster = true;
    boolean isSuccess = true;

    public String getName() {
        return name;
    }

    public TestUser setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void printStr(String str) {
        Log.i(TAG, " str=" + str);
    }

    public void printStr(String[] strs) {
        Log.i(TAG, " strs=" + Arrays.toString(strs));
    }
}
