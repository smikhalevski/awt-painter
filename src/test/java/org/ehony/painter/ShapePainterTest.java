/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */

package org.ehony.painter;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.AffineTransform;
import java.util.concurrent.CountDownLatch;

public class ShapePainterTest
{

    @Test
    public void testPaintComposite() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        JFrame f = new JFrame();
        f.setLayout(new BorderLayout());
        JPanel p = new JPanel()
        {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Font f = getFont().deriveFont(Font.BOLD, 150);
                FontRenderContext frc = getFontMetrics(f).getFontRenderContext();
                GlyphVector v = f.createGlyphVector(frc, new char[] {'E'});
                
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Shape shape = AffineTransform.getTranslateInstance(100, 180).createTransformedShape(v.getOutline());
                
                //new Ellipse2D.Float(100, 100, 50, 50);
                new ShapePainter()
                        .outline(2, Color.RED)
                        .outline(5, Color.BLUE)
                        .outline(10, Color.GREEN)
                                // .background(Color.PINK)
                        .insetShadow(5, 5, 10, 0, Color.BLACK)
                        .dropShadow(-40, -20, 30, 0, Color.ORANGE, false)
                        .dropShadow(20, 20, 5, 0, Color.LIGHT_GRAY, true)
                        .paint(shape, g);
            }
        };
        p.setBackground(Color.WHITE);
        f.add(p, BorderLayout.CENTER);
        f.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e) {
                latch.countDown();
            }
        });
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(300, 300);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        latch.await();
    }

}
