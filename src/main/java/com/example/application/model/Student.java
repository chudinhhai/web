package com.example.application.model;

import com.vaadin.flow.component.template.Id;

public class Student {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Float cpa;
    private String gender;
    private String email;
    public Student() {
    }

    public Student(String firstName, String lastName, Float cpa, String gender, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpa = cpa;
        this.gender = gender;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Float getCpa() {
        return cpa;
    }

    public void setCpa(Float cpa) {
        this.cpa = cpa;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
