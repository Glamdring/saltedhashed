package com.saltedhashed.dao;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.saltedhashed.model.User;

@Repository
public class UserDao {

    @Inject
    private MongoTemplate mongo;

    public User find(String email) {
        return mongo.findById(email, User.class);
    }

    public void save(User user) {
        mongo.save(user);
    }

    public User completeUserRegistration(String email) {
        User user = new User();
        user.setEmail(email);
        user.setRegistrationTimestamp(System.currentTimeMillis());
        save(user);
        return user;
    }
}
