package com.sharemylocation.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

@Path("/ping")
public class PingResource {

    @Inject
    private DB db;
    
    @GET
    @Produces(value = "application/json")
    public String ping() {
        DBCollection pingCollection = db.getCollection("ping");
        pingCollection.insert(new BasicDBObject("ping","pong"));
        return "{'ping': 'pong'}";
    }
}
