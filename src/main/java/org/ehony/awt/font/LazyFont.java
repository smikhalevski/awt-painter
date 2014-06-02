package org.ehony.awt.font;

import org.ehony.awt.api.Lazy;

import java.awt.*;
import java.io.*;

/**
 * Lazy-loaded font resource.
 */
public class LazyFont implements Lazy<Font>
{

    private Font font;
    private String path;

    /**
     * Set path to load font from.
     * @param path Path to load font from.
     */
    public void setPath(String path) {
        this.path = path;
        this.font = null; // Force font to be reloaded.
    }

    /**
     * Get {@link java.awt.Font} representation of this outline.
     * @see #setPath(String)
     */
    public Font load() {
        if (font == null) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            InputStream stream = getClass().getResourceAsStream(path);
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, stream);
            } catch (Exception e) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    // noop
                }
                throw new RuntimeException("Cannot load font.", e);
            }
            ge.registerFont(font);
        }
        return font;
    }
}
