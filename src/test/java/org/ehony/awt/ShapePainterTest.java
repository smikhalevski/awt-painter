/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */

package org.ehony.awt;

import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;

public class ShapePainterTest
{

    @Test
    public void testPaintComposite() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final ShapePainter sp = new ShapePainter()
                .background(new Color(0xcccccc))
                .outline(2, Color.PINK)
                .insetShadow(2, 2, 3, 0, Color.BLACK)
                .dropShadow(5, 5, 10, 0, Color.BLACK, false);

        final Rectangle shape = new Rectangle(20, 20, 60, 60);

//        {
//            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = bi.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            new ShapePainter()
//                    .background(new Color(0xfcaf3e))
//                    .insetShadow(0, 0, 15, 0, new Color(0xcc000000, true))
//                    .paint(shape, g);
//            File file = new File("D:\\github\\awt-painter\\docs\\inset-shadow-blur-radius.png");
//            try {
//                ImageIO.write(bi, "png", file);
//            } catch (IOException e1) {
//                throw new RuntimeException(e1);
//            }
//        }
//        {
//            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = bi.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            new ShapePainter<>()
//                    .background(new Color(0xfcaf3e))
//                    .insetShadow(0, -5, 10, 0, new Color(0xaa000000, true))
//                    .paint(shape, g);
//            File file = new File("D:\\github\\awt-painter\\docs\\inset-shadow-offset.png");
//            try {
//                ImageIO.write(bi, "png", file);
//            } catch (IOException e1) {
//                throw new RuntimeException(e1);
//            }
//        }
//        {
//            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = bi.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            new ShapePainter<>()
//                    .background(new Color(0xfcaf3e))
//                    .insetShadow(0, 0, 15, 10, new Color(0xcc000000, true))
//                    .paint(shape, g);
//            File file = new File("D:\\github\\awt-painter\\docs\\inset-shadow-spread.png");
//            try {
//                ImageIO.write(bi, "png", file);
//            } catch (IOException e1) {
//                throw new RuntimeException(e1);
//            }
//        }
//        {
//            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = bi.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            new ShapePainter<>()
//                    .background(Color.WHITE)
//                    .insetShadow(0,
//                                 0,
//                                 15,
//                                 0,
//                                 new GradientPaint(0f, 0f, new Color(0xfcaf3e), 60, 60, new Color(0x4e9a06)))
//                    .paint(shape, g);
//            File file = new File("D:\\github\\awt-painter\\docs\\inset-shadow-paint.png");
//            try {
//                ImageIO.write(bi, "png", file);
//            } catch (IOException e1) {
//                throw new RuntimeException(e1);
//            }
//        }
//
//
//        {
//            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = bi.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            new ShapePainter<>()
//                    .background(new Color(0xfcaf3e))
//                    .dropShadow(0, 0, 10, 0, new Color(0xcc000000, true), false)
//                    .paint(shape, g);
//            File file = new File("D:\\github\\awt-painter\\docs\\drop-shadow-blur-radius.png");
//            try {
//                ImageIO.write(bi, "png", file);
//            } catch (IOException e1) {
//                throw new RuntimeException(e1);
//            }
//        }
//        {
//            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = bi.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            new ShapePainter<>()
//                    .background(new Color(0xfcaf3e))
//                    .dropShadow(5, 5, 10, 0, new Color(0x99000000, true), false)
//                    .paint(shape, g);
//            File file = new File("D:\\github\\awt-painter\\docs\\drop-shadow-offset.png");
//            try {
//                ImageIO.write(bi, "png", file);
//            } catch (IOException e1) {
//                throw new RuntimeException(e1);
//            }
//        }
//        {
//            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = bi.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            new ShapePainter<>()
//                    .background(new Color(0xfcaf3e))
//                    .dropShadow(6, 12, 6, -12, new Color(0xcc000000, true), false)
//                    .paint(shape, g);
//            File file = new File("D:\\github\\awt-painter\\docs\\drop-shadow-spread.png");
//            try {
//                ImageIO.write(bi, "png", file);
//            } catch (IOException e1) {
//                throw new RuntimeException(e1);
//            }
//        }
//        {
//            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = bi.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            new ShapePainter<>()
//                    .background(Color.WHITE)
//                    .dropShadow(-2,
//                                -2,
//                                15,
//                                4,
//                                new GradientPaint(0f, 0f, new Color(0xfcaf3e), 60, 60, new Color(0x4e9a06)),
//                                false)
//                    .paint(shape, g);
//            File file = new File("D:\\github\\awt-painter\\docs\\drop-shadow-paint.png");
//            try {
//                ImageIO.write(bi, "png", file);
//            } catch (IOException e1) {
//                throw new RuntimeException(e1);
//            }
//        }
//        {
//            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = bi.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            new ShapePainter<>()
//                    .dropShadow(0, 0, 10, 0, new Color(0xee000000, true), true)
//                    .paint(shape, g);
//
//            int step = 5;
//            for (int i = 0; i < 60 / step; i++) {
//                for (int j = 0; j < 60 / step; j++) {
//                    if ((i + j) % 2 == 0) {
//                        g.setPaint(new Color(0x33000000, true));
//                        g.fill(new Rectangle(20 + i * step, 20 + j * step, step, step));
//                    }
//                }
//            }
//
//            File file = new File("D:\\github\\awt-painter\\docs\\drop-shadow-exclude.png");
//            try {
//                ImageIO.write(bi, "png", file);
//            } catch (IOException e1) {
//                throw new RuntimeException(e1);
//            }
//        }


        JFrame f = new JFrame();
        f.setLayout(new BorderLayout());
        JPanel p = new JPanel()
        {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                sp.paint("Hello world!", getFont().deriveFont(70f), (Graphics2D)g, 50, 100);
            }
        };
        p.setBackground(Color.WHITE);
        p.setPreferredSize(new Dimension(400, 400));
        f.add(p, BorderLayout.CENTER);
        f.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e) {
                latch.countDown();
            }
        });
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        latch.await();
    }

}
