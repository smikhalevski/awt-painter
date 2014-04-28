/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.painter.painters;

import org.ehony.painter.api.Painter;
import org.ehony.painter.filters.BoxBlurFilter;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

/**
 * Paints drop shadow effect.
 */
public class DropShadowPainter implements Painter
{

    private float dx, dy, radius, spread;
    private Paint paint;
    private boolean excludeShape;

    public float getX() {
        return dx;
    }

    public void setX(float x) {
        this.dx = x;
    }

    public float getY() {
        return dy;
    }

    public void setY(float y) {
        this.dy = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getSpread() {
        return spread;
    }

    public void setSpread(float spread) {
        this.spread = spread;
    }

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

    public boolean isExcludeShape() {
        return excludeShape;
    }

    public void setExcludeShape(boolean excludeShape) {
        this.excludeShape = excludeShape;
    }

    @Override
    public void paint(Shape shape, Graphics2D g, int x, int y) {
        g.translate(x, y);

        Area area = new Area(shape);

        // Bounds of outer shadow shape. Blur size is not taken in consideration.
        Rectangle2D r = area.getBounds2D();

        // Outer shadow must contain shape itself.
        area.add(new Area(area));
        area.transform(AffineTransform.getTranslateInstance(-r.getX(), -r.getY()));

        // Create shadow shape considering spread.
        double sx = 1 + spread / r.getWidth();
        double sy = 1 + spread / r.getHeight();
        if (Double.compare(spread, 0d) != 0) {
            area = new Area(AffineTransform.getScaleInstance(sx, sy).createTransformedShape(area));
        }

        // Create shadow image.
        BufferedImage bi = new BufferedImage((int) (r.getWidth() * sx + 4 * radius) + /*magic 2 pixels to avoid redundant color lines*/2,
                                             (int) (r.getHeight() * sy + 4 * radius) + 2,
                                             BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHints(g.getRenderingHints());
        g2d.setPaint(paint);
        g2d.translate(2 * radius, 2 * radius);
        g2d.fill(area);

        Rectangle2D rct = new Rectangle2D.Double(dx + r.getX() - 2 * radius,
                                                 dy + r.getY() - 2 * radius,
                                                 bi.getWidth(),
                                                 bi.getHeight());

        if ((int) radius > 0) {
            BoxBlurFilter f = new BoxBlurFilter();
            f.setRadius((int) radius);
            f.setIterations(1);
            f.filter(bi, bi);
        }
        g.setPaint(new TexturePaint(bi, rct));

        Area rect = new Area(rct);

        if (excludeShape) {
            area.transform(AffineTransform.getTranslateInstance(r.getX(), r.getY()));
            rect.subtract(area);
        }

        g.fill(rect);

        g.translate(-x, -y);
    }
}
