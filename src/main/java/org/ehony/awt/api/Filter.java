package org.ehony.awt.api;

import java.awt.image.BufferedImage;

/**
 * Image filter.
 */
public interface Filter
{

    BufferedImage filter(BufferedImage source, BufferedImage target);
}
