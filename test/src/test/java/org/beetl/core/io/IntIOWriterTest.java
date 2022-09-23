package org.beetl.core.io;

import junit.framework.Assert;

import org.testng.annotations.Test;

/**
 * 整型输出流的单测类
 */
public class IntIOWriterTest {

    @Test
    public void testWriteInt() {
    }

    @Test
    public void testWriteLong() {
    }

    @Test
    public void testFillChars() {
    }

    @Test
    public void testCalcStringLength() {
        int[] intInputs = {
                100, 1234, -1234, 0, Integer.MAX_VALUE
        };
        int[] intExpecteds = {
                3, 4, 1, 1, 10
        }; // 必须为正数，否则返回 i+1 即 0+1
        for (int i = 0; i < intExpecteds.length; i++) {
            int result = IntIOWriter.calcStringLength(intInputs[i]);
            System.out.println("i=" + i + "\tintInput=" + intInputs[i] + "\tresult=" + result);
            Assert.assertEquals(intExpecteds[i], result);
        }

        long[] longInputs = {
                100, 1234, -1234, 0, Integer.MAX_VALUE, Long.MAX_VALUE, 123_456_789_000_000L,
                123_456_789_000_000_000_0L, (int) 1
        };
        int[] longExpecteds = {
                3, 4, 1, 1, 10, 19, 15, 19, 1
        }; // 必须为正数，否则返回 i+1 即 0+1
        for (int i = 0; i < longExpecteds.length; i++) {
            int result = IntIOWriter.calcStringLength(longInputs[i]);
            System.out.println("i=" + i + "\tlongInput=" + longExpecteds[i] + "\tresult=" + result);
            Assert.assertEquals(longExpecteds[i], result);
        }
    }

}