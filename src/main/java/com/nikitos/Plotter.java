package com.nikitos;

import org.jzy3d.analysis.AWTAbstractAnalysis;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.chart.factories.IChartFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Func3D;
import org.jzy3d.plot3d.builder.SurfaceBuilder;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Plotter extends AWTAbstractAnalysis {
    private final Layer[] layers;
    private final TaskConfig modelConfig;

    public Plotter(Layer[] layers, TaskConfig config) {
        this.layers = layers;
        this.modelConfig = config;
    }

    @Override
    public void init() {
        // Define a function to plot
        Func3D func = new Func3D((x, y) -> min(max(layers[(int) (x / modelConfig.endTime * (modelConfig.steps_time - 1))]
                .getData()[(int) ((y - modelConfig.initX) / (modelConfig.endX - modelConfig.initX) * (modelConfig.steps - 1))], -10000),10000));

        Range rangeT = new Range(0, modelConfig.endTime);
        Range rangeX = new Range(modelConfig.initX, modelConfig.endX);

        // Create the object to represent the function over the given range.
        final Shape surface =
                new SurfaceBuilder().orthonormal(new OrthonormalGrid(rangeT, modelConfig.steps_time, rangeX, modelConfig.steps), func);
        surface
                .setColorMapper(new ColorMapper(new ColorMapRainbow(), surface, new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(true);
        surface.setWireframeColor(Color.BLACK);
        IChartFactory f = new AWTChartFactory();

        chart = f.newChart(Quality.Advanced().setHiDPIEnabled(true));
        chart.getScene().getGraph().add(surface);
    }
}

