/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.painter.painters;

import org.ehony.painter.api.Painter;

import java.awt.*;
import java.awt.geom.Area;

/**
 * Style of the outline of a shape.
 * <p>Analogue of <code><a href="http://www.w3.org/TR/CSS2/ui.html#dynamic-outlines">outline</a></code> property.</p>
 */
public class OutlinePainter implements Painter
{

    private float width;
    private Paint paint;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    public Shape getSubsequentShape(Shape shape) {
        Area a = new Area(new BasicStroke((float) width * 2).createStrokedShape(shape));
        a.add(new Area(shape));
        return a;
    }

    @Override
    public void paint(Shape shape, Graphics2D g, int x, int y) {
        g.translate(x, y);
        g.setPaint(paint);
        Area a = new Area(getSubsequentShape(shape));
        a.subtract(new Area(shape));
        g.fill(a);
        g.translate(-x, -y);
    }
}
