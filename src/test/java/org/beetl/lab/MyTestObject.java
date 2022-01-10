package org.beetl.lab;

class MyTestObject {
    String name;
    int age = 12;

    public static String getHello(){
    	return "hello";
	}

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MyTestObject self() {
        return this;
    }
}
