# AWT Painter

CSS-like effects brought to Java shape painting.

## Contents

1. [Quick Start](#quick-start)
2. [Shadows](#shadows)
    1. [Inner Shadow](#inner-shadow)
    2. [Drop Shadow](#drop-shadow)
3. [Background](#background)
4. [Outlines](#outlines)
5. [Painter Ordering](#painter-ordering)
6. [License](#license)

## Quick Start

Use `org.ehony.awt.ShapePainter` to access fluent api for drawing shapes.

## Bundled Painters

AWT Painter provides several classes which implement CSS-like effects.

Exemples below use fllowing objects:
```java
ShapePainter shapePainter = new ShapePainter();
Shape shape = new Rectangle(20, 20, 60, 60);
```

### Shadows

This section describes bundled painters which provide same effects as CSS [`box-shadow`][1] property.

#### Inset Shadow

`org.ehony.awt.painter.InsetShadowPainter` is analogue [`box-shadow`][1] property _with_ `inset` keyword specified.

Default shadow settings match ones defined in CSS specification. Drop shadow painter does not have any required parameters but may be configured with:

<table>
<thead>
<tr>
    <th>Output</th>
    <th>Description</th>
</tr>
</thead>
<tbody>
<tr>
    <td><img src="docs/inset-shadow-blur-radius.png"></td>
    <td><b>Blur Radius</b><br/>Positive blur radius indicates that the resulting shadow should be blurred. If the blur value is zero or negative, the edge of the shadow is sharp. By default shadow is blurred with parallelized implemetation of <a href="http://en.wikipedia.org/wiki/Box_blur">box blur</a> filter.</td>
</tr>
</tbody>
</table>

Note that inset shadow with all parameters set to zero paints nothing.

#### Drop Shadow

`org.ehony.awt.painter.DropShadowPainter` is analogue [`box-shadow`][1] property _without_ `inset` keyword specified.

This painter inherits all the parameters from [inner shadow](#inner-shadow) and introduces:

| Output | Description |
| ------ | ----------- |
| ![Blur Radius](docs/drop-shadow-blur-radius.png) | **Blur Radius**<br/> Positive blur radius indicates that the resulting shadow should be blurred. If the blur value is zero or negative, the edge of the shadow is sharp. By default shadow is blurred with parallelized implemetation of [box blur](http://en.wikipedia.org/wiki/Box_blur) filter. |
| ![Offset](docs/drop-shadow-offset.png) | **Offset**<br/> Horizontal and vertical offsets can be specified separately. A positive value draws a shadow that is offset to the right (bottom) of the box, a negative length to the left (top). |
| ![Spread](docs/drop-shadow-spread.png) | **Spread**<br/> Positive values cause the shadow to expand in all directions by the specified value. Negative values cause the shadow to contract. |
| ![Paint](docs/drop-shadow-paint.png) | **Paint**<br/> Shadow may be painted with an arbitrary `java.awt.Paint`. If the paint was not specified then paint returned by `Graphics#getPaint()` is used. |
| ![Exclude Original Shape](docs/drop-shadow-exclude.png) | **Exclude Original Shape**<br/> If set to `true` omits painting shadow pixels which overlap with original shape. |

### Background

`org.ehony.awt.painter.BackgroundPainter` is analogue of [`background`](http://www.w3.org/TR/css3-background/#background) property.

### Outlines

`org.ehony.awt.painter.OutlinePainter` is analogue of [`outline`](http://www.w3.org/TR/CSS21/ui.html#dynamic-outlines) property.

Default outline settings match ones defined in CSS specification. Outline painter does not have any required parameters but may be configured with:

**Width** The width of the outline. The width must be greater than or equal to zero. If width is set to zero, outline is not painted.

**Cap** If shape being drawn is an open path instance this property defined the decoration of the ends of an outline.

**Line Join** Defines the decoration applied where path segments meet.

**Miter Limit** The limit to trim the miter join, must be greater than or equal to 1.

**Dash Pattern** Pattern of empty and painted areas of an outline.

**Initial Dash Phase** The offset to start the dashing pattern.

**Subsequent Dash** If set to `false` then `getSubsequentShape(Shape)` method would return shape ignoring the dash pattern. This may be useful if multiple outline painters are used allowing to prohibit consequent outlines to bend around each other dashes.

## Painter Ordering

`org.ehony.awt.painter.CompositePainter` description.

## License

The code is available under [MIT licence](LICENSE.txt).

[1]: http://www.w3.org/TR/css3-background/#box-shadow
