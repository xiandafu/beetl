package org.beetl.jmh;

import org.beetl.core.util.TextUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;

public class TextUtilsTest extends BaseJmhTest {

    public static void main(String[] args) {
        TextUtilsTest jmhTest = new TextUtilsTest();
        jmhTest.run();
    }

    @Param({"1234.1212", "1213123", "1234567890qwertyuiopasdfghjklzxcvbnm,."})
    String dataIndexOf;

    @Benchmark
    public int textUtilsIndexOf() {
        return TextUtils.indexOf(dataIndexOf, '.');
    }

    @Benchmark
    public int stringIndexOf() {
        return dataIndexOf.indexOf('.');
    }
}
