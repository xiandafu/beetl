package org.beetl.core.lab;

public class MyTestObject {
    String name;

    public MyTestObject() {
    }

    public MyTestObject(String name) {
        super();
        this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public MyTestObject self() {
        return this;
    }
}
