package com.sharemylocation.config;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.mongodb.DB;

public interface MongoConfig {

    public abstract DB db();

}