package org.ehony.awt.font;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.*;

import static java.awt.font.TextAttribute.*;
import static java.lang.Math.*;

public class FontBuilder
{

    private Font font;
    private Float slope, tracking;
    private Integer size, weight = Weight.Normal.weight;

    public FontBuilder(Font font) {
        this.font = font;
        this.size = font.getSize();
    }

    public Font getFont() {
        return font.deriveFont(new HashMap<TextAttribute, Object>() {
            {
                put(KERNING, KERNING_ON);
                put(SIZE, size);
                if (weight != null) {
                    put(WEIGHT, (float) weight / Weight.Normal.weight);
                }
                if (slope != null) {
                    put(POSTURE, slope);
                }
                if (tracking != null) {
                    put(TRACKING, tracking);
                }
            }
        });
    }

    // <editor-fold desc="Fluent API">

    /**
     * Set absolute font size in points.
     * <p>Very large or small sizes will impact rendering performance,
     * and the rendering system might not render text at these sizes.
     * Negative sizes are illegal and result in the default size.</p>
     * <p>Note that the appearance and metrics of a 12 pt font with a
     * 2x transform might be different than that of a 24 point font
     * with no transform.</p>
     *
     * @param pt font size in points (1 pt = 1/72 inch).
     * @return Original {@link org.ehony.awt.font.FontBuilder} instance.
     */
    public FontBuilder size(int pt) {
        this.size = max(pt, 1);
        return this;
    }

    /**
     * Set font size relative to the current size.
     *
     * @param percent size ratio.
     * @return Original {@link org.ehony.awt.font.FontBuilder} instance.
     */
    public FontBuilder adjustSize(float percent) {
        return size(round(size * percent));
    }

    /**
     * Set font character thickness in range [100, 900] as defined by
     * <a href="http://en.wikipedia.org/wiki/Font#Weight">TrueType</a> format.
     *
     * @param weight font weight.
     * @return Original {@link org.ehony.awt.font.FontBuilder} instance.
     */
    public FontBuilder weight(int weight) {
        this.weight = max(weight, 1);
        return this;
    }

    public FontBuilder weight(Weight weight) {
        return weight(weight.weight);
    }

    /**
     * Set font weight relatively to current weight.
     *
     * @param percent percent to adjust weight.
     * @return Original {@link org.ehony.awt.font.FontBuilder} instance.
     */
    public FontBuilder adjustWeight(float percent) {
        return weight(round(weight * percent));
    }

    /**
     * Set font slope.
     * <p>The value is roughly the slope of the stems of the font,
     * expressed as the run over the rise. Positive values lean right.</p>
     * <p>The system can interpolate the provided value.</p>
     * <p>This will affect the italic angle of the font as returned by
     * {@link java.awt.Font#getItalicAngle()}</p>
     *
     * @param slope font slope in range [-1, 1].
     * @return Original {@link org.ehony.awt.font.FontBuilder} instance.
     */
    public FontBuilder slope(float slope) {
        this.slope = slope;
        return this;
    }

    /**
     * Set italic slope for backing font.
     * @return Original {@link org.ehony.awt.font.FontBuilder} instance.
     */
    public FontBuilder italic() {
        return slope(POSTURE_OBLIQUE);
    }

    /**
     * Set character tracking (letter spacing).
     * <p>The tracking value is multiplied by the font point size and passed
     * through the font transform to determine an additional amount to add
     * to the advance of each glyph cluster. Positive tracking values will
     * inhibit formation of optional ligatures. Tracking values are typically
     * between -0.1 and 0.3 while values outside this range are generally
     * not desirable.</p>
     *
     * @param percent font italic angle in range [-1, 1].
     * @return Original {@link org.ehony.awt.font.FontBuilder} instance.
     */
    public FontBuilder tracking(float percent) {
        this.tracking = percent;
        return this;
    }

    // </editor-fold>
}
