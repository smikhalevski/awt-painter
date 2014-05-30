/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.awt.painter;

import org.ehony.awt.api.Painter;

import java.awt.*;

/**
 * Paint fill painter.
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
        Paint paint = g.getPaint();
        g.translate(x, y);
        g.setPaint(this.paint);
        g.fill(shape);
        g.translate(-x, -y);
        g.setPaint(paint);
    }
}
