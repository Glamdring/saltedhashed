package com.saltedhashed.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.saltedhashed.Site;

@Repository
public class SiteDao {

    @Inject
    private MongoTemplate mongo;

    public void save(Site site) {
        mongo.insert(site);
    }

    public void performBatched(int pageSize, PageableOperation<Site> operation) {
        int page = 0;
        while (true) {
            Pageable pageable = new PageRequest(page, pageSize);
            Query query = new Query().with(pageable);
            final List<Site> data = new ArrayList<Site>();
            mongo.executeQuery(query, "site", new DocumentCallbackHandler() {
                @Override
                public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
                    mongo.getConverter().read(Site.class, dbObject);
                }
            });
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
