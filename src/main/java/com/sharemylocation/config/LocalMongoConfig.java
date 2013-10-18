package com.sharemylocation.config;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

@ApplicationScoped
@Alternative
public class LocalMongoConfig implements MongoConfig{

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private DB db;

    @PostConstruct
    void constructMongoDBInstance() {
        try {
            logger.info("Creating MongoDB instance...");
            MongoClient mongoClient = new MongoClient();
            mongoClient.setWriteConcern(WriteConcern.SAFE);
            this.db = mongoClient.getDB("sharemylocation");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Produces
    @Named
    public DB db() {
        return this.db;
    }
}
