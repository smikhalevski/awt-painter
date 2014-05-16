/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.awt.painter;

import org.ehony.awt.api.Painter;

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
    private int cap = BasicStroke.CAP_BUTT;
    private int join = BasicStroke.JOIN_MITER;
    private float miterlimit = 10f;
    float dash[];
    private float dash_phase = 0f;

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

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public int getJoin() {
        return join;
    }

    public void setJoin(int join) {
        this.join = join;
    }

    public float getMiterlimit() {
        return miterlimit;
    }

    public void setMiterlimit(float miterlimit) {
        this.miterlimit = miterlimit;
    }

    public float[] getDash() {
        return dash;
    }

    public void setDash(float[] dash) {
        this.dash = dash;
    }

    public float getDash_phase() {
        return dash_phase;
    }

    public void setDash_phase(float dash_phase) {
        this.dash_phase = dash_phase;
    }

    @Override
    public Shape getSubsequentShape(Shape shape) {
        Area a = new Area(new BasicStroke(width * 2, cap, join, miterlimit, dash, dash_phase).createStrokedShape(shape));
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

    public static enum Cap {


    }

    public static enum Join {


    }


}
