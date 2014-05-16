/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.awt.api;

import java.awt.*;

/**
 * Logic of representing particular shape on canvas.
 */
public interface Painter
{
    
    /**
     * Get shape which must be painted by subsequent {@link #paint} invocations.
     * <p>Returned shape must contain provided shape and may be equal to it.
     * Result is not required to be equal to shape processed by this painter.</p>
     *
     * @param shape shape to compute subsequent shape for.
     * @return Shape for further processing.
     * @see Painter
     */
    Shape getSubsequentShape(Shape shape);

    /**
     * Paints given shape on {@link Graphics2D} canvas at specified coordinates.
     * <p>Implementations must not modify shape to avoid ambiguous behavior.</p>
     * <p>Provided graphics is expected to preserve its state, so revert to original
     * paint, background, composite, rendering hints, stroke, transform, clip and
     * font must be performed.</p>
     * <p>To draw a shape applying several sequential painters consider preserving
     * shape patches introduced by previous painter. This is needed in cases such
     * as painting shadows dropped by an outlined shape.</p>
     * <pre>
     * <b>painterA</b>.paint(shape, graphics, 0, 0);
     * painterB.paint(<b>painterA.getSubsequentShape(shape)</b>, graphics, 0, 0);
     * </pre>
     *
     * @param shape shape to paint on canvas.
     * @param canvas canvas to paint shape on.
     * @param x horizontal offset to paint shape.
     * @param y vertical offset to paint shape.
     * @see #getSubsequentShape(Shape)
     */
    void paint(Shape shape, Graphics2D canvas, int x, int y);
}
