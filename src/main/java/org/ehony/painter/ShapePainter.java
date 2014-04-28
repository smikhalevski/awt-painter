/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */

package org.ehony.painter;

import org.ehony.painter.api.Painter;
import org.ehony.painter.painters.*;

import java.awt.*;

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
}
