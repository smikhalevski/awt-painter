package org.ehony.awt;

import org.junit.Ignore;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import static java.awt.image.BufferedImage.*;

@Ignore
public class DocumentationImageProducer
{

    private static List<BufferedImage> images = new ArrayList<BufferedImage>();
    private static Shape shape = new Rectangle(20, 20, 60, 60);

    public static void main(String... args) throws InterruptedException, IOException {
        createImage("inset-shadow-blur-radius",
                new ShapePainter()
                        .background(new Color(0xfcaf3e))
                        .insetShadow(0, 0, 15, 0, new Color(0xcc000000, true)));

        createImage("inset-shadow-offset", new ShapePainter()
                .background(new Color(0xfcaf3e))
                .insetShadow(0, -5, 10, 0, new Color(0xaa000000, true)));
        createImage("inset-shadow-spread", new ShapePainter()
                .background(new Color(0xfcaf3e))
                .insetShadow(0, 0, 15, 10, new Color(0xcc000000, true)));
        createImage("inset-shadow-paint", new ShapePainter()
                .background(Color.WHITE)
                .insetShadow(0, 0, 15, 0,
                        new GradientPaint(0f, 0f, new Color(0xfcaf3e), 60, 60, new Color(0x4e9a06))));
        createImage("drop-shadow-blur-radius", new ShapePainter()
                .background(new Color(0xfcaf3e))
                .dropShadow(0, 0, 10, 0, new Color(0xcc000000, true), false));
        createImage("drop-shadow-offset", new ShapePainter()
                .background(new Color(0xfcaf3e))
                .dropShadow(5, 5, 10, 0, new Color(0x99000000, true), false));
        createImage("drop-shadow-spread", new ShapePainter()
                .background(new Color(0xfcaf3e))
                .dropShadow(6, 12, 6, -12, new Color(0xcc000000, true), false));
        createImage("drop-shadow-paint", new ShapePainter()
                .background(Color.WHITE)
                .dropShadow(-2, -2, 15, 4,
                        new GradientPaint(0f, 0f, new Color(0xfcaf3e), 60, 60, new Color(0x4e9a06)),
                        false));
        {
            BufferedImage image = createImage();
            Graphics2D g = createGraphics(image);
            new ShapePainter()
                    .dropShadow(0, 0, 10, 0, new Color(0xee000000, true), true)
                    .paint(shape, g);

            int step = 5;
            for (int i = 0; i < 60 / step; i++) {
                for (int j = 0; j < 60 / step; j++) {
                    if ((i + j) % 2 == 0) {
                        g.setPaint(new Color(0x33000000, true));
                        g.fill(new Rectangle(20 + i * step, 20 + j * step, step, step));
                    }
                }
            }
            images.add(image);
            ImageIO.write(image, "png", new File("docs/drop-shadow-exclude.png"));
            g.dispose();
        }
    }

    public static void createImage(String name, ShapePainter painter) throws IOException {
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
