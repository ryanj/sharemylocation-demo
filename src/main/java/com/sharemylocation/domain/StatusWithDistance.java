package com.sharemylocation.domain;

public class StatusWithDistance {

    private Status status;
    private double distance;

    public StatusWithDistance() {
    }

    public StatusWithDistance(Status status, double distance) {
        super();
        this.status = status;
        this.distance = distance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "StatusWithDistance [status=" + status + ", distance=" + distance + "]";
    }

}
