package com.nazmul.assessmentapphpl.model;

public class Registration {

    private String name;
    private String email;
    private String contactNumber;

    public Registration(String name, String email, String contactNumber) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
