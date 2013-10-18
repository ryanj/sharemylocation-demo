package com.sharemylocation.converters;

import com.mongodb.DBObject;

public interface Converter<T> {

    public DBObject toMongo(T t);
    
    public T fromMongo(DBObject u);

}
