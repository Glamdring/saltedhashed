package com.saltedhashed.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saltedhashed.Site;

@Repository
public class SiteDao {

    public <T> void performBatched(Class<T> clazz, int pageSize, PageableOperation<T> operation) {
        int page = 0;
        while (true) {
            List<T> data = null; //listPaged(clazz, page * pageSize, pageSize);
            page++;
            operation.setData(data);
            operation.execute();
            // final batch
            if (data.size() < pageSize) {
                break;
            }
        }
    }
}
