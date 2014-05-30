/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.awt;

import org.ehony.awt.ImagePopup.TestPainter;
import org.junit.Test;

import java.awt.*;

public class ShapePainterTest
{

    @Test
    public void testPerception() throws Exception {
        final ShapePainter painter = new ShapePainter()
                .background(new Color(0xcccccc))
                .outline(2, Color.PINK)
                .outline(1, Color.RED)
                .insetShadow(2, 2, 3, 0, Color.BLACK)
                .dropShadow(5, 5, 20, 0, Color.BLACK, false);

        ImagePopup.showFrame(600, 200, new TestPainter()
        {
            @Override
            public void paint(Graphics2D canvas) {
                canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                painter.paint("Hello world!", canvas.getFont().deriveFont(60f), canvas, 50, 100);
            }
        });
    }
}
