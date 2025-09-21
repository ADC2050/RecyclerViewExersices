package com.example.recyclerviewexersices;

public class Person {
    String name;
    int age;
    String city;
    boolean isStudent;

    public Person(String name, int age, String city, boolean isStudent) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.isStudent = isStudent;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public boolean getIsStudent() {
        return isStudent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

}
