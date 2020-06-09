package org.beetl.core.lab;

import java.util.*;

/**
 * @author 所有测试类采用的Pojo类
 *
 *
 */
public class TestUser implements  BaseEntity
{
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

	public void printStr(String str){
		System.out.println(" str ="+str);
	}

	public void printStr(String[] strs){
		System.out.println(" strs ="+strs);
	}
}
