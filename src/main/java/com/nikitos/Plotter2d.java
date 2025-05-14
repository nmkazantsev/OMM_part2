package com.nikitos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.plot2d.primitives.LineSerie2d;
import org.jzy3d.plot3d.rendering.legends.overlay.Legend;
import org.jzy3d.plot3d.rendering.legends.overlay.LineLegendLayout;
import org.jzy3d.plot3d.rendering.legends.overlay.OverlayLegendRenderer;

import static java.lang.Math.pow;


public class Plotter2d {
    public static void main(String[] args) {
        AWTChartFactory f = new AWTChartFactory();
        AWTChart chart = (AWTChart) f.newChart();

        // Add 2D series
        LineSerie2d[] lines = new LineSerie2d[30];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new LineSerie2d("Line " + (i + 1));
            lines[i].setColor(Color.BLUE);
        }
        for (int i = 0; i < lines.length; i++) {
            double t0 =i/50.0;
            double[] x = new double[120];
            int ind = 0;
            for (double t = -0.001; t < 0.001; t += 0.0001) {
                double x1 = 2*t*(2-t*t)/(4*t+2)-(2-t0*t0)*t0/(4*t0+2)+(t*t-t0*t0)/2+t0;
                //double x1=
                lines[i].add(t, x1);
                ind++;
            }
            chart.add(lines[i]);
        }

        // Legend
        List<Legend> infos = new ArrayList<Legend>();
       /* infos.add(new Legend(line1.getName(), line1.getColor()));
        infos.add(new Legend(line2.getName(), line2.getColor()));
        infos.add(new Legend(line3.getName(), line3.getColor()));*/

        OverlayLegendRenderer legend = new OverlayLegendRenderer(infos);
        LineLegendLayout layout = legend.getLayout();

        layout.getMargin().setWidth(1);
        layout.getMargin().setHeight(1);
        layout.setBackgroundColor(Color.WHITE);
        layout.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 30));

        chart.addRenderer(legend);

        // Open as 2D chart
        chart.view2d();
        chart.open();

        //==================================================

        chart = f.newChart();

        for (int i = 0; i < lines.length; i++) {
            lines[i] = new LineSerie2d("Line " + (i + 1));
            lines[i].setColor(Color.BLUE);

            double x0 = i - (double) lines.length / 2;
            double[] x = new double[120];
            int ind = 0;
            for (double t = -0.3; t < 0.3; t += 0.001) {
                double x1 = (x0 + 2 + pow(t, 2) / 2.0) / (1+2*t);
                lines[i].add(t, x1);
                ind++;
            }
            chart.add(lines[i]);
        }

        // Legend
        infos = new ArrayList<Legend>();

        legend = new OverlayLegendRenderer(infos);
        layout = legend.getLayout();

        layout.getMargin().setWidth(1);
        layout.getMargin().setHeight(1);
        layout.setBackgroundColor(Color.WHITE);
        layout.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 30));

        chart.addRenderer(legend);

        // Open as 2D chart
        chart.view2d();
        chart.open();
    }

}
