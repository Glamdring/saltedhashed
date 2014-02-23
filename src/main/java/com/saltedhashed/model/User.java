package com.saltedhashed.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String email;

    private List<String> sites;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getSites() {
        return sites;
    }

    public void setSites(List<String> sites) {
        this.sites = sites;
    }
}