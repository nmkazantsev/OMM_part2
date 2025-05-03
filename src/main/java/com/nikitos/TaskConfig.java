package com.nikitos;

public class TaskConfig {
    public final double tau, h, initX;

    public TaskConfig(double tau, double h, double initX) {
        this.tau = tau;
        this.h = h;
        this.initX = initX;
    }
}
