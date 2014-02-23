package com.saltedhashed.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String email;
    private Set<String> sites = new HashSet<>();
    private long registrationTimestamp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getSites() {
        return sites;
    }

    public void setSites(Set<String> sites) {
        this.sites = sites;
    }

    public long getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(long registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }
}
