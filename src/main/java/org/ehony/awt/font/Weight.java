package org.ehony.awt.font;

/**
 * The relative darkness of the characters in the various typefaces within a type family.
 */
public enum Weight
{

    Thin(100),
    ExtraLight(200),
    Light(300),
    Normal(400),
    Medium(500),
    SemiBold(600),
    Bold(700),
    ExtraBold(800),
    Black(900),
    ExtraBlack(950);

    public final int weight;
    
    Weight(int weight) {
        this.weight = weight;
    }
}
