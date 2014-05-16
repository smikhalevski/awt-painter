/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.awt.painter;

import org.junit.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ConvolutionTest
{

    private BufferedImage image;

    @Before
    public void setUp() throws Exception {
        image = ImageIO.read(getClass().getResourceAsStream("/pattern.jpg"));
    }

    @Test
    public void testBlur() throws Exception {
        BufferedImage buffer = createCompatibleImage();
        Convolution.parallelGaussianBlur(image, buffer, 10);
        showImage(buffer);
    }

    interface TestPainter {

        void paint(Graphics2D canvas);
    }

    public BufferedImage createCompatibleImage() {
        return new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
    }

    public static void showFrame(int width, int height, final TestPainter painter) throws InterruptedException {
        final JFrame f = new JFrame();
        f.getContentPane().setLayout(new BorderLayout());
        JPanel panel = new JPanel()
        {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                painter.paint((Graphics2D) g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        f.add(panel, BorderLayout.CENTER);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        synchronized (f) {
            f.wait();
        }
    }

    public static void showImage(final BufferedImage image) throws InterruptedException {
        final int f = getImageDisplayFactor();
        showFrame(image.getWidth() / f, image.getHeight() / f, new TestPainter()
        {
            @Override
            public void paint(Graphics2D canvas) {
                canvas.setTransform(AffineTransform.getScaleInstance(1f / f, 1f / f));
                canvas.drawImage(image, 0, 0, null);
            }
        });
    }

    public static int getImageDisplayFactor() {
        Object f = Toolkit.getDefaultToolkit().getDesktopProperty("apple.awt.contentScaleFactor");
        if (f instanceof Number) {
            return ((Number) f).intValue();
        }
        return 2;
    }
}
