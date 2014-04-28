/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.painter.filters;

import org.ehony.painter.api.Filter;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.*;

public class BoxFilter implements Filter
{

    private static ExecutorService s = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    
    private float[] kernel;
    private int iterations;

    public float[] getKernel() {
        return kernel;
    }

    public void setKernel(float[] kernel) {
        this.kernel = kernel;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public static int[] parallelApplyKernel(int[] pixels, int width, int height, float[] kernel) {
        int[] temp = new int[width * height];
        int lineCount = 30;
        int q = height / lineCount + height % lineCount;
        
        CountDownLatch c = new CountDownLatch(q);
        for (int i = 0; i < q; i++) {
            s.submit(new RunQ(pixels, temp, width, height, kernel, lineCount, i, c));
        }
        try {
            c.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }
    
    public static void applyKernel(int[] pixels, int[] temp, int width, int height, int startLine, int lineCount, float[] kernel) {
        float alphaDenominator = 0.0f;
        float denominator = 0.0f;
        float a, r, g, b;
        int ia, ir, ig, ib, indexOffset, argb;

        int l = (int) Math.sqrt(kernel.length);


        int[] indices = new int[l * l];


        int q = (l - 1) / 2;
        int p = 0;
        for (int y = -q; y <= q; y++) {
            for (int x = -q; x <= q; x++) {
                indices[p] = width * y + x;
                p++;
            }
        }

        int lastLineExclusive = Math.min(height, startLine + lineCount);
        for (int i = startLine; i < lastLineExclusive; i++) {
            for (int j = 0; j < width; j++) {

                a = r = g = b = 0f;

                indexOffset = (i * width) + j;
                alphaDenominator = 0;
                denominator = 0;

                for (int k = 0; k < kernel.length; k++) {
                    int w = indexOffset + indices[k];
                    if (w >= 0 && w < pixels.length) {
                        alphaDenominator += kernel[k];
                        argb = pixels[w];
                        int alpha = ((argb >> 24) & 0xff);
                        if (alpha != 0) {
                            // Totally transparent colors create moire pattern during color blending,
                            // so they are explicitly excluded.
                            denominator += kernel[k];
                            a += alpha * kernel[k];
                            r += ((argb >> 16) & 0xff) * kernel[k];
                            g += ((argb >> 8) & 0xff) * kernel[k];
                            b += ((argb >> 0) & 0xff) * kernel[k];
                        }
                    }
                }

                if (Float.compare(alphaDenominator, 0) == 0) {
                    alphaDenominator = 1f;
                }
                if (Float.compare(denominator, 0) == 0) {
                    denominator = 1f;
                }
                ia = (int) (a / alphaDenominator);
                ir = (int) (r / denominator);
                ig = (int) (g / denominator);
                ib = (int) (b / denominator);

                ia = ia > 255 ? 255 : ia < 0 ? 0 : ia;
                ir = ir > 255 ? 255 : ir < 0 ? 0 : ir;
                ig = ig > 255 ? 255 : ig < 0 ? 0 : ig;
                ib = ib > 255 ? 255 : ib < 0 ? 0 : ib;

                temp[indexOffset] =
                        ((ia & 0xFF) << 24) |
                                ((ir & 0xFF) << 16) |
                                ((ig & 0xFF) << 8) |
                                ((ib & 0xFF) << 0);
            }
        }
    }

    @Override
    public void filter(BufferedImage source, BufferedImage target) {
        int width = source.getWidth();
        int height = source.getHeight();
        int[] inPixels = new int[width * height];
        source.getRGB(0, 0, width, height, inPixels, 0, width);
        for (int i = 0; i < iterations; i++) {
            inPixels = parallelApplyKernel(inPixels, width, height, kernel);
        }
        target.setRGB(0, 0, width, height, inPixels, 0, width);
    }
    
    public static float[] createBlurKernel(int radius, boolean soft) {
        radius = radius * 2 + 1;
        float[] k = new float[radius * radius];
        if (soft) {
            int q = radius / 2;
            int i = 0;
            for (int x = -q; x <= q; x++) {
                for (int y = -q; y <= q; y++) {
                    k[i] = 1 + ((q - Math.abs(x)) + (q - Math.abs(y))) / 2;
                    i++;
                }
            }
        } else {
            Arrays.fill(k, 1);
            k[radius * radius / 2] = 2;
        }
        return k;
    }
    
    public static float[] createSharpenKernel(int radius) {
        radius = radius * 2 + 1;
        float[] k = new float[radius * radius];
        Arrays.fill(k, -1);
        k[radius * radius / 2] = radius * radius;
        return k;
    }
    
    private static final class RunQ implements Runnable
    {

        int[] pixels; int[] temp; int width; int height; float[] kernel; int lineCount; int i; CountDownLatch c;

        private RunQ(int[] pixels, int[] temp, int width, int height, float[] kernel, int lineCount, int i, CountDownLatch c) {
            this.pixels = pixels;
            this.temp = temp;
            this.width = width;
            this.height = height;
            this.kernel = kernel;
            this.lineCount = lineCount;
            this.i = i;
            this.c = c;
        }

        @Override
        public void run() {
            applyKernel(pixels, temp, width, height, i * lineCount, lineCount, kernel);
            c.countDown();
        }
    }
}
