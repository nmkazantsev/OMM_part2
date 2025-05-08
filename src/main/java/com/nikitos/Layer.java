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
    private final double EPSILON = 0.001;

    public Layer(int size, int time_index) {
        data = new double[size];
        this.time_index = time_index;
    }

    public Layer newLayer(TaskConfig config) {
        Layer layer = new Layer(data.length, this.time_index + 1);
        double tau = config.tau;
        double h = config.h;
        for (int i = 1; i < data.length; i++) {
            double v = data[i], v2, v_m1=data[i];
            int counter=0;
            while (true) {
                //find F(v)
                double F = v - data[i] + tau / h * q(getX(i, config), getT(time_index + 1, config), v) -
                        tau / h * q(getX(i - 1, config), getT(time_index + 1, config), layer.data[i - 1]);
                double F_u = 1+tau/h*q_u(getX(i,config));
                v2=v-F/F_u;
                if(counter>=2 && (v2-v)/(1-(v2-v)/(v-v_m1))<EPSILON){
                    break;
                }

                v_m1=v;
                v=v2;
                counter++;
            }
            layer.data[i]=v2;
        }

        return layer;
    }

    private double q(double u, double t, double x) {
        return 2 * u * x + t * x;
    }

    private double q_u(double x) {
        return 2 * x;
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
