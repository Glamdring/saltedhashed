package com.saltedhashed;

import java.util.ArrayList;
import java.util.List;

public class Site {
    private String baseUrl;
    private String endpointPath;
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
}
