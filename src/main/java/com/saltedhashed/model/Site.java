package com.saltedhashed.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Site {
    @Id
    private String baseUrl;
    private String endpointPath;
    private String owner;
    private List<SiteStatus> statuses = new ArrayList<>();

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getEndpointPath() {
        return endpointPath;
    }

    public void setEndpointPath(String endpointPath) {
        this.endpointPath = endpointPath;
    }

    public List<SiteStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<SiteStatus> statuses) {
        this.statuses = statuses;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
