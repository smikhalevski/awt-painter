/*
 * ┌──┐
 * │  │
 * │Eh│ony
 * └──┘
 */
package org.ehony.awt.painter;

import java.awt.image.BufferedImage;
import java.util.concurrent.*;
import static java.lang.Math.*;

public class Convolution
{















    public static void parallelConvolve(int[] source, int[] target, int offset, int length, float[] kernel, int[] offsets, boolean opaque, Executor executor, int pixelsPerThread) throws InterruptedException {
        int delegateCount = source.length / pixelsPerThread + source.length % pixelsPerThread;

        CountDownLatch latch = new CountDownLatch(delegateCount);
        for (int i = 0; i < delegateCount; i++) {
            executor.execute(new ConvolutionDelegate(source, target, i * pixelsPerThread, pixelsPerThread, kernel, offsets, opaque, latch));
        }

        latch.await();
    }



    /**
     * Convolves &alpha;RGB array and provided kernel.
     * @param source array to transform.
     * @param target array to store convolution output.
     * @param offset start index in source array.
     * @param length number sequential pixels from source array to process.
     * @param kernel convolution matrix.
     * @param offsets offsets of pixels affected by corresponding kernel items.
     * @param opaque if {@code true} then alpha channel is ignored.
     */
    public static void convolve(int[] source, int[] target, int offset, int length, float[] kernel, int[] offsets, boolean opaque) {
        int end = min(offset + length, source.length);
        for (int i = offset; i < end; i++) {
            float a = 0,
                    r = 0,
                    g = 0,
                    b = 0,
                    alphaRank = 0,
                    colorRank = 0;

            for (int j = 0; j < kernel.length; j++) {
                float k = kernel[j];

                int index = i + offsets[j];
                if (index >= 0 && index < source.length) {
                    int color = source[index];
                    if (!opaque) {
                        alphaRank += k;
                        int alpha = color >> 24 & 0xff;
                        if (alpha == 0) {
                            // Totally transparent colors create undesired moire pattern
                            // during color blending, so they are explicitly excluded.
                            continue;
                        }
                        a += k * alpha;
                    }
                    colorRank += k;
                    r += k * (color >> 16 & 0xff);
                    g += k * (color >> 8  & 0xff);
                    b += k * (color >> 0  & 0xff);
                }
            }
            target[i] = 0xff << 24; // Opaque black.

            if (alphaRank != 0) {
                target[i] = clamp(a / alphaRank) << 24;
            }
            if (colorRank != 0) {
                target[i] = target[i]
                        | clamp(r / colorRank) << 16
                        | clamp(g / colorRank) << 8
                        | clamp(b / colorRank) << 0;
            }
        }
    }

    private static Executor ex = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static int clamp(float channel) {
        return min(255, max(0, (int) channel)) & 0xff;
    }

/*    public static void parallelGaussianBlur(BufferedImage in, BufferedImage out, float radius) throws InterruptedException {
        int[] source = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
        int[] target = new int[source.length];
        parallelGaussianBlur(source,target, in.getWidth(), radius, in.isAlphaPremultiplied(), ex, 100);
        out.setRGB(0, 0, in.getWidth(), in.getHeight(), target, 0, in.getWidth());
    }*/

    private static class ConvolutionDelegate implements Runnable
    {
        private int[] source;
        private int[] target;
        private int offset;
        private int length;
        private float[] kernel;
        private int[] offsets;
        private boolean opaque;
        private CountDownLatch latch;

        private ConvolutionDelegate(int[] source, int[] target, int offset, int length, float[] kernel, int[] offsets, boolean opaque, CountDownLatch latch) {
            this.source = source;
            this.target = target;
            this.offset = offset;
            this.length = length;
            this.kernel = kernel;
            this.offsets = offsets;
            this.opaque = opaque;
            this.latch = latch;
        }

        @Override
        public void run() {
            convolve(source, target, offset, length, kernel, offsets, opaque);
            latch.countDown();
        }
    }






}
