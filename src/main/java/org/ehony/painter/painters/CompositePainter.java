/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.painter.painters;

import org.ehony.painter.api.Painter;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.Collections.sort;

/**
 * Z-index aware painter.
 */
public class CompositePainter implements Painter
{

    private List<Layer> layers = new LinkedList<Layer>();

    public List<Layer> getLayers() {
        return layers;
    }

    @Override
    public Shape getSubsequentShape(Shape shape) {
        return shape;
    }

    @Override
    public void paint(Shape shape, Graphics2D g, int x, int y) {
        if (!layers.isEmpty()) {
            sort(layers);
            Shape[] shapes = new Shape[layers.size()];
            shapes[shapes.length - 1] = shape;
            for (int i = shapes.length - 2; i >= 0; i--) {
                shape = layers.get(i + 1).getPainter().getSubsequentShape(shape);
                shapes[i] = shape;
            }
            for (int i = 0; i < shapes.length; i++) {
                layers.get(i).getPainter().paint(shapes[i], g, x, y);
            }
        }
    }

    public static class Layer implements Comparable<Layer> {
        
        private Painter painter;
        private int order;

        public Painter getPainter() {
            return painter;
        }

        public void setPainter(Painter painter) {
            this.painter = painter;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        @Override
        public int compareTo(Layer layer) {
            return getOrder() - layer.getOrder();
        }
    }
}
