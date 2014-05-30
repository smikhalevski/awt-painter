/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.awt.painter;

import org.ehony.awt.ImagePopup;
import org.junit.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Ignore
public class GaussianBlurTest
{

    protected BufferedImage image;

    public BufferedImage createCompatibleImage() {
        return new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
    }

    @Before
    public void setUp() throws Exception {
        image = ImageIO.read(getClass().getResourceAsStream("/pattern.jpg"));
    }

    @Test
    public void testPerception() throws Exception {
        BufferedImage buffer = createCompatibleImage();
        System.out.println("Blurring image of size " + buffer.getWidth() + " by " + buffer.getHeight());
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(10);
        for (int i = 0; i < 10; i++) {
            long n = System.nanoTime();
            gb.filter(image, buffer);
            System.out.println((System.nanoTime() - n) / 1E6);
        }
        ImagePopup.showImage(buffer);
    }
}
