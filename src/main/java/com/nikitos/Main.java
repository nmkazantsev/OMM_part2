package com.nikitos;

import org.jzy3d.analysis.AnalysisLauncher;

public class Main {
    public static void main(String[] args) throws Exception {
        Layer[] layers = new Layer[100];
        int steps = 150;
        double h = 1/(double)steps;
        int steps_time = layers.length;
        TaskConfig taskConfig = new TaskConfig(0.03, h, 0, steps, steps_time);
        layers[0] = new Layer(steps, 0);
        layers[0].initCond(taskConfig);
        for (int i = 0; i < layers.length - 1; i++) {
            layers[i + 1] = layers[i].newLayer(taskConfig);
        }
        Plotter plotter = new Plotter(layers, taskConfig);
        AnalysisLauncher.open(plotter);
    }
}