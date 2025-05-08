package com.nikitos;

import java.util.Arrays;

import static com.nikitos.Main.func_c;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * a 1d array + functions for calculating new one
 */
public class Layer {
    private final double[] data;
    private final int time_index;

    public Layer(int size, int time_index) {
        data = new double[size];
        this.time_index = time_index;
    }

    public Layer newLayer(TaskConfig config) {
        Layer layer = new Layer(data.length, this.time_index + 1);
        double A, B, C, D;
        double c;//a value of c in average point
        double tau = config.tau;
        double h = config.h;

        double t = time_index * config.tau;
        System.out.println("time index: " + time_index);
        layer.data[0] = 1;//(1 - 2 * pow(t, 2)) / (4 * t + 2);
        for (int i = 0; i < data.length - 1; i++) {
            c = 2;//-((data[i] + data[i + 1]) + ((double) time_index + 0.5) * config.tau);//func_c(u(getX(i, config), config), getT(time_index, config));
            A = -1 / (2 * tau) - 1 / (2 * h) * c;
            B = 1 / (2 * tau) - 1 / (2 * h) * c;
            C = -1 / (2 * tau) + 1 / (2 * h) * c;
            D = 1 / (2 * tau) + 1 / (2 * h) * c;
            System.out.println("x: " + getX(i, config) + " A: " + A + ", B: " + B + ", C: " + C + ", D: " + D + " c: " + c);
            System.out.println("i: " + data[i] + " i+1: " + data[i + 1]);
            layer.data[i + 1] = ((A * data[i] + B * data[i + 1] + C * layer.data[i]) / D);
            System.out.println("result " + layer.data[i + 1]);
        }
        double maxSpeed = Arrays.stream(data).max().orElse(0.0);
        if (maxSpeed * config.tau / config.h > 1.0) {
            throw new IllegalStateException("Нарушено условие Куранта");
        }
        System.out.println("\n\n===========================================\n\n");

        return layer;
    }

    public void initCond(TaskConfig config) {
        for (int i = 0; i < data.length; i++) {
            data[i] = 1 - getX(i, config);
        }
    }

    public double[] getData() {
        return data;
    }

    public static double getT(int time_index, TaskConfig config) {
        return config.tau * time_index;
    }

    public static double getX(int x_index, TaskConfig config) {
        return config.h * x_index + config.initX;
    }

    private double u(double x, TaskConfig config) {
        double hx = config.h;
        return (data[(int) ((x - config.initX) / hx)] + data[(int) ((x - config.initX) / hx) + 1]) / 2;
    }
}
