/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.painter.api;

import java.awt.*;

/**
 * Painter that encapsulates logic of representing particular shape on canvas.
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
     * <p>To draw a shape applying several sequential painters consider preserving
     * shape patches introduced by previous painter. This is needed in cases such
     * as painting shadows dropped by an outlined shape.</p>
     * <pre>
     * <b>painterA</b>.paint(shape, graphics, 0, 0);
     * painterB.paint(<b>painterA.getSubsequentShape(shape)</b>, graphics, 0, 0);
     * </pre>
     *
     * @see #getSubsequentShape(Shape)
     */
    void paint(Shape shape, Graphics2D g, int x, int y);
}
