package org.beetl.impl;

import org.beetl.core.runtime.BeetlRuntime;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultBeetlMemoryManagerTest {


    @Test
    public void testGet() {

    }

    @Test
    public void testMap() {
    }

    @Test
    public void testRecoveryMap() {
    }

    @Test
    public void testSet() {
    }

    @Test
    public void testRecoverySet() {
    }

    @Test
    public void testList() {
        String tag = "get#";
        int id = 1;

        {
            List list1 = BeetlRuntime.getMemoryManager().takeList();
            list1.add("String");
            list1.add(123);
            list1.add(new HashMap<>());
            boolean recoveryResult = BeetlRuntime.getMemoryManager().recoveryList(list1);
            Assert.assertTrue(recoveryResult, tag + id++);

            System.out.println(BeetlRuntime.getMemoryManager());
            System.out.println(BeetlRuntime.getMemoryManager().sizeOfListPool());

            List<String> list2 = BeetlRuntime.getMemoryManager().takeList();
            Assert.assertTrue(list1 == list2, tag + id++);

            Assert.assertTrue(list1.isEmpty(), tag + id++);
        }

        List<String> list2 = BeetlRuntime.getMemoryManager().takeList();
        List<String> list3 = BeetlRuntime.getMemoryManager().takeList();
        Assert.assertTrue(list2 != list3, tag + id++);
    }

    @Test
    public void testRecoveryList() {
        String tag = "recoveryList#";
        int id = 1;

        // performance: 当 Collection 的容量较大时，MemoryManager的优势开始凸显，减少了 Collection 扩容的开销
        final int size = 20;
        for (int i = 0; i < size; i++) {
            List list = new ArrayList();
            opList(list); // List 的数据操作
            BeetlRuntime.getMemoryManager().recoveryList(list); // 回收引用
        }

        long time1 = System.nanoTime();
        for (int i = 0; i < size; i++) { // 耗时: 578ms
            List list = BeetlRuntime.getMemoryManager().takeList(); // 获取引用，没有则创建
            opList(list); // List 的数据操作
        }
        long time2 = System.nanoTime();
        for (int i = 0; i < size; i++) { // 耗时: 1058ms
            List list = new ArrayList(); // 常规场景创建 List 的实例
            opList(list); // List 的数据操作
        }
        long time3 = System.nanoTime();

        System.out.println(tag + id++ + " 耗时: " + (time2 - time1) / 1_000_000 + "ms");
        System.out.println(tag + id++ + " 耗时: " + (time3 - time2) / 1_000_000 + "ms");
        System.out.println(BeetlRuntime.getMemoryManager());
        Assert.assertTrue(true);
    }

    private static void opList(List list) {
        for (int i = 0; i < 1000_000; i++) {
            list.add(i);
        }
    }

    @Test
    public void testNewUnpaddedArray() {
    }
}