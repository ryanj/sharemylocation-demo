package com.sharemylocation.domain;

public enum Type {

    POINT("Point"), LINE_STRING("LineString"), POLYGON("Polygon");

    private String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Type type(String name) {
        Type[] types = values();
        for (Type type : types) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new RuntimeException("Not found");
    }
}
