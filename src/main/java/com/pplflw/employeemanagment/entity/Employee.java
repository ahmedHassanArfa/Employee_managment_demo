package com.pplflw.employeemanagment.entity;

import com.pplflw.employeemanagment.constant.StateEnum;

import javax.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String contact;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private StateEnum state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }
}
