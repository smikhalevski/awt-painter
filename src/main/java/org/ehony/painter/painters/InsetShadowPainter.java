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

public class InsetShadowPainter implements Painter
{

    private float x, y, radius, spread;
    private Paint paint;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    @Override
    public void paint(Shape shape, Graphics2D g, int x1, int y1) {
        g.translate(x1, y1);

        Area area = new Area(shape);

        // Bounds of inner shadow.
        Rectangle2D r = area.getBounds2D();

        Shape s = AffineTransform.getTranslateInstance(-r.getX(), -r.getY())
                                 .createTransformedShape(area);

        // Create shadow shape considering spread.
        double sx = 1 + spread / r.getWidth();
        double sy = 1 + spread / r.getHeight();
        if (Double.compare(spread, 0d) != 0) {
            s = AffineTransform.getScaleInstance(sx, sy).createTransformedShape(s);
        }
        // Create shadow image.
        BufferedImage bi = new BufferedImage((int) (r.getWidth() * sx + 2 * radius),
                                             (int) (r.getHeight() * sy + 2 * radius),
                                             BufferedImage.TYPE_INT_ARGB);

        s = AffineTransform.getTranslateInstance(x + radius, y + radius)
                           .createTransformedShape(s);

        Area a = new Area(new Rectangle2D.Double(0, 0, bi.getWidth(), bi.getHeight()));
        a.subtract(new Area(s));

        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHints(g.getRenderingHints());
        g2d.setPaint(paint);
        g2d.fill(a);

        BoxBlurFilter f = new BoxBlurFilter();
        f.setRadius((int) radius);
        f.setIterations(1);

        Rectangle2D rct = new Rectangle2D.Double((r.getX() - radius),
                                                 (r.getY() - radius),
                                                 bi.getWidth(),
                                                 bi.getHeight());
        f.filter(bi, bi);
        g.setPaint(new TexturePaint(bi, rct));

        g.fill(area);

        g.translate(-x1, -y1);
    }
}
