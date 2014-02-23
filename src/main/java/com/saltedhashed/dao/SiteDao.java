package com.saltedhashed.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.saltedhashed.model.Site;
import com.saltedhashed.model.User;

@Repository
public class SiteDao {

    @Inject
    private MongoTemplate mongo;

    public void save(Site site) {
        mongo.save(site);
        User user = mongo.findById(site.getOwner(), User.class);
        if (user.getSites().add(site.getBaseUrl())) {
            mongo.save(user);
        }
    }

    public Site find(String baseUrl) {
        return mongo.findById(baseUrl, Site.class);
    }

    public List<Site> getSitesForUser(String email) {
        User user = mongo.findById(email, User.class);
        return mongo.find(Query.query(Criteria.where("baseUrl").in(user.getSites())), Site.class);
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
                    data.add(mongo.getConverter().read(Site.class, dbObject));
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

    public void delete(Site site) {
        mongo.remove(site);
    }
}
