package com.nikitos;

import static com.nikitos.Main.func_c;
import static java.lang.Math.pow;

/**
 * a 1d array + functions for calculating new one
 */
public class Layer {
    private double[] data;
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

        for (int i = 0; i < data.length - 1; i++) {
            c = func_c(u(getX(i, config), config), getT(time_index, config));
            A = -1 / (2 * tau) - 1 / (2 * h) * c;
            B = 1 / (2 * tau) - 1 / (2 * h) * c;
            C = -1 / (2 * tau) + 1 / (2 * h) * c;
            D = 1 / (2 * tau) + 1 / (2 * h) * c;
            layer.data[i + 1] = (A * data[i] + B * data[i + 1] + C * layer.data[i]) / D;
        }
        double t = time_index * config.tau;
        layer.data[0] = (1 - 2 * pow(t, 2)) / (4 * t + 2);
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
        return (data[(int) (x / hx)] + data[(int) (x / hx) + 1]) / 2;
    }
}
