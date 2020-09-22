package org.beetl.core.lab;

import org.beetl.core.config.BeetlConfig;
import org.beetl.core.log.Log;

import java.util.*;

/**
 * @author 所有测试类采用的Pojo类
 */
public class TestUser implements BaseEntity {

    /** Log TAG */
    private static final String TAG = "org.beetl.core.lab.TestUser";

    String name = "default";

    boolean isMaster = true;
    boolean isSuccess = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
