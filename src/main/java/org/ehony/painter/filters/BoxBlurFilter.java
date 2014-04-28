/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.painter.filters;

import org.ehony.painter.api.Filter;

import java.awt.image.BufferedImage;

public class BoxBlurFilter implements Filter
{

    private int radius = 3;
    private int iterations = 1;
    private BoxFilter bbf = new BoxFilter();

    @Override
    public void filter(BufferedImage from, BufferedImage to) {
        bbf.setKernel(BoxFilter.createBlurKernel(radius, true));
        bbf.setIterations(iterations);
        bbf.filter(from, to);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getIterations() {
        return iterations;
    }
}
