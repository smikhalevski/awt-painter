/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.painter.painters;

import org.ehony.painter.api.Painter;

import java.awt.*;

/**
 * Painter that fills area with a set paint.
 */
public class BackgroundPainter implements Painter
{

    private Paint paint;

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    public Shape getSubsequentShape(Shape shape) {
        return shape;
    }

    @Override
    public void paint(Shape shape, Graphics2D g, int x, int y) {
        g.translate(x, y);
        g.setPaint(paint);
        g.fill(shape);
        g.translate(-x, -y);
    }
}
