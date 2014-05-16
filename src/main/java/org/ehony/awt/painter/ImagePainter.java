/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.awt.painter;

import org.ehony.awt.api.Painter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePainter implements Painter
{
    
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        BufferedImage cache = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = cache.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return cache;
    }

    @Override
    public Shape getSubsequentShape(Shape shape) {
        return shape;
    }

    @Override
    public void paint(Shape shape, Graphics2D g, int x, int y) {
        BufferedImage texture = toBufferedImage(image);
        g.translate(x, y);
        g.setPaint(new TexturePaint(texture, new Rectangle(x, y, texture.getWidth(), texture.getHeight())));
        g.fill(shape);
        g.translate(-x, -y);
    }
}
