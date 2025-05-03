package com.nikitos;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Layer layers[] = new Layer[100];
        TaskConfig taskConfig = new TaskConfig(0.01, 0.01, -1);
        layers[0] = new Layer(100, 0);
        layers[0].initCond(taskConfig);
        for (int i = 0; i < layers.length - 1; i++) {
            layers[i + 1] = layers[i].newLayer(taskConfig);
        }
    }

    public static double func_c(double u_x, double t) {
        return -2 * u_x + t;
    }
}