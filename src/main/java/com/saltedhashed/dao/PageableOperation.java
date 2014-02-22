package com.saltedhashed.dao;

import java.util.List;

public abstract class PageableOperation<T> {
    private List<T> data;

    public abstract void execute();

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}