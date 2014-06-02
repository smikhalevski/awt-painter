/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.awt;

import org.ehony.awt.api.Painter;
import org.ehony.awt.painter.*;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class ShapePainter<Type extends ShapePainter> extends CompositePainter
{

    @SuppressWarnings("unchecked")
    public Type layer(int order, Painter painter) {
        Layer layer = new Layer();
        layer.setOrder(order);
        layer.setPainter(painter);
        getLayers().add(0, layer);
        return (Type) this;
    }

    @SuppressWarnings("unchecked")
    public Type dropShadow(float dx, float dy, float radius, float spread, Paint paint, boolean excludeShape) {
        DropShadowPainter painter = new DropShadowPainter();
        painter.setPaint(paint);
        painter.setRadius(radius);
        painter.setSpread(spread);
        painter.setX(dx);
        painter.setY(dy);
        painter.setExcludeShape(excludeShape);
        layer(-300, painter);
        return (Type) this;
    }


    @SuppressWarnings("unchecked")
    public Type outline(float weight, Paint paint) {
        OutlinePainter painter = new OutlinePainter();
        painter.setPaint(paint);
        painter.setWidth(weight);
        layer(-200, painter);
        return (Type) this;
    }

    @SuppressWarnings("unchecked")
    public Type outline(float width, int cap, int join, float miterLimit, float dash[], float dash_phase) {
        OutlinePainter p = new OutlinePainter();
        p.setWidth(width);
        p.setCap(cap);
        p.setJoin(join);
        p.setMiterlimit(miterLimit);
        p.setDash(dash);
        p.setDash_phase(dash_phase);
        layer(-200, p);
        return (Type) this;
    }

    /**
     * Paints area background with provided paint.
     *
     * @param paint paint to fill area.
     * @return Original builder instance.
     */
    @SuppressWarnings("unchecked")
    public Type background(Paint paint) {
        BackgroundPainter painter = new BackgroundPainter();
        painter.setPaint(paint);
        layer(-100, painter);
        return (Type) this;
    }

    @SuppressWarnings("unchecked")
    public Type background(Image image) {
        ImagePainter painter = new ImagePainter();
        painter.setImage(image);
        layer(-100, painter);
        return (Type) this;
    }

    @SuppressWarnings("unchecked")
    public Type insetShadow(float dx, float dy, float radius, float spread, Paint paint) {
        InsetShadowPainter painter = new InsetShadowPainter();
        painter.setPaint(paint);
        painter.setRadius(radius);
        painter.setSpread(spread);
        painter.setX(dx);
        painter.setY(dy);
        layer(0, painter);
        return (Type) this;
    }

    @SuppressWarnings("unchecked")
    public Type paint(Shape shape, Graphics g) {
        paint(shape, (Graphics2D) g, 0, 0);
        return (Type) this;
    }

    /**
     * Paints provided text using effects stored by this painter and provided font.
     * @return Original builder instance.
     */
    @SuppressWarnings("unchecked")
    public Type paint(String text, Font font, Graphics2D g, int x, int y) {
        FontRenderContext context = g.getFontMetrics(font).getFontRenderContext();
        Shape shape = font.createGlyphVector(context, text).getOutline();
        paint(shape, g, x, y);
        return (Type) this;
    }

    public Type paint(String text, Graphics2D g, int x, int y) {
        return paint(text, g.getFont(), g, x, y);
    }
}