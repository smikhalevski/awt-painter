/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.painter.api;

import java.awt.image.BufferedImage;

public interface Filter
{

    void filter(BufferedImage from, BufferedImage to);
}
