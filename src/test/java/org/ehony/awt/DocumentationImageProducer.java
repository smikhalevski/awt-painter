package org.ehony.awt;

import org.ehony.awt.ImagePopup.TestPainter;
import org.junit.Ignore;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import static java.awt.image.BufferedImage.*;

@Ignore
public class DocumentationImageProducer
{

    private static List<BufferedImage> images = new ArrayList<BufferedImage>();
    private static Shape shape = new Ellipse2D.Float(20, 20, 60, 60);

    public static void main(String... args) throws InterruptedException, IOException {
        writeImage("inset-shadow-blur-radius",
                new ShapePainter()
                        .background(new Color(0xfcaf3e))
                        .insetShadow(0, 0, 15, 0, new Color(0xcc000000, true)));

        writeImage("inset-shadow-offset",
                new ShapePainter()
                        .background(new Color(0xfcaf3e))
                        .insetShadow(-2, -2, 0, 0, new Color(0xaa000000, true)));
        writeImage("inset-shadow-spread",
                new ShapePainter()
                        .background(new Color(0xfcaf3e))
                        .insetShadow(0, 0, 15, -15, new Color(0x55000000, true)));
        writeImage("inset-shadow-paint",
                new ShapePainter()
                        .background(Color.WHITE)
                        .insetShadow(0, 0, 15, 0,
                                new GradientPaint(
                                        0f, 0f, new Color(0xfcaf3e),
                                        60, 60, new Color(0x4e9a06))));
        writeImage("drop-shadow-blur-radius",
                new ShapePainter()
                        .background(new Color(0xfcaf3e))
                        .dropShadow(0, 0, 10, 0, new Color(0xcc000000, true), false));
        writeImage("drop-shadow-offset",
                new ShapePainter()
                        .background(new Color(0xfcaf3e))
                        .dropShadow(5, 5, 10, 0, new Color(0x99000000, true), false));
        writeImage("drop-shadow-spread",
                new ShapePainter()
                        .background(new Color(0xfcaf3e))
                        .dropShadow(6, 12, 6, -12, new Color(0xcc000000, true), false));
        writeImage("drop-shadow-paint",
                new ShapePainter()
                        .background(Color.WHITE)
                        .dropShadow(-2, -2, 15, 4,
                                new GradientPaint(
                                        0f, 0f, new Color(0xfcaf3e),
                                        60, 60, new Color(0x4e9a06)),
                                false));
        {

            Rectangle r = shape.getBounds();
            BufferedImage texture = new BufferedImage(r.width, r.height, TYPE_INT_ARGB);
            Graphics2D tg = createGraphics(texture);
            int step = 5;
            for (int i = 0; i < r.width / step; i++) {
                for (int j = 0; j < r.height / step; j++) {
                    if ((i + j) % 2 == 0) {
                        tg.setPaint(new Color(0x33000000, true));
                        tg.fill(new Rectangle(i * step, j * step, step, step));
                    }
                }
            }

            BufferedImage image = createImage();
            Graphics2D g = createGraphics(image);
            new ShapePainter()
                    .dropShadow(0, 0, 10, 0, new Color(0xee000000, true), true)
                    .background(texture)
                    .paint(shape, g);
            images.add(image);
            ImageIO.write(image, "png", new File("docs/drop-shadow-exclude.png"));
            g.dispose();
        }
        showImages(images);
    }

    public static void showImages(final List<BufferedImage> images) throws InterruptedException {
        ImagePopup.showFrame(400, 300, new TestPainter()
        {
            @Override
            public void paint(Graphics2D canvas, JComponent component) {
                BufferedImage image = images.get(0);
                int count = component.getWidth() / image.getWidth();
                int top = 0;
                int left = 0;
                for (int i = 0; i < images.size(); i++) {
                    if (i != 0 && i % count == 0) {
                        top += image.getHeight();
                        left = 0;
                    }
                    canvas.drawImage(images.get(i), left, top, null);
                    left += image.getWidth();
                }
            }
        });
    }

    public static void writeImage(String name, ShapePainter painter) throws IOException {
        BufferedImage image = createImage();
        Graphics2D g = createGraphics(image);
        painter.paint(shape, g);
        images.add(image);
        ImageIO.write(image, "png", new File("docs/" + name + ".png"));
        g.dispose();
    }

    public static BufferedImage createImage() {
        Rectangle r = shape.getBounds();
        return new BufferedImage(r.x * 2 + r.width, r.y * 2 + r.height, TYPE_INT_ARGB);
    }

    public static Graphics2D createGraphics(BufferedImage image) {
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g;
    }
}
