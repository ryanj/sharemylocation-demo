package com.sharemylocation.domain;

import java.util.Arrays;

public class Location {

    private Type type;

    private double[] coordinates;

    public Location() {
    }

    public Location(Type type, double[] coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Location [type=" + type + ", coordinates=" + Arrays.toString(coordinates) + "]";
    }

}
