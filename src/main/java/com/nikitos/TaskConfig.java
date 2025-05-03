package com.nikitos;

public class TaskConfig {
    public final double tau, h, initX, endTime, endX;
    public final int steps, steps_time;

    public TaskConfig(double tau, double h, double initX, int steps, int steps_time) {
        this.tau = tau;
        this.h = h;
        this.steps = steps;
        this.initX = initX;
        this.steps_time = steps_time;
        this.endTime = steps_time * tau;
        this.endX = initX + h * steps;
    }
}
