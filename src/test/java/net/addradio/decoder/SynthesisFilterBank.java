/*
 * 11/19/04 1.0 moved to LGPL.
 *
 * 04/01/00 Fixes for running under build 23xx Microsoft JVM. mdm.
 *
 * 19/12/99 Performance improvements to compute_pcm_samples().
 *			Mat McGowan. mdm@techie.com.
 *
 * 16/02/99 Java Conversion by E.B , javalayer@javazoom.net
 *
 *  @(#) synthesis_filter.h 1.8, last edit: 6/15/94 16:52:00
 *  @(#) Copyright (C) 1993, 1994 Tobias Bading (bading@cs.tu-berlin.de)
 *  @(#) Berlin University of Technology
 *
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */
package net.addradio.decoder;

import net.addradio.decoder.math.FCT;

/**
 * A class for the synthesis filter bank.
 * This class does a fast downsampling from 32, 44.1 or 48 kHz to 8 kHz, if ULAW is defined.
 * Frequencies above 4 kHz are removed by ignoring higher subbands.
 */
public final class SynthesisFilterBank {

    /**
     * d[] split into subarrays of length 16. This provides for
     * more faster access by allowing a block of 16 to be addressed
     * with constant offset.
     **/
    private static float d16[][] = null;

    /** {@code int[][]} vpIs. */
    private static int[][] vpIs = new int[16][];

    static {
        vpIs[0] = new int[] { 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        vpIs[1] = new int[] { 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        vpIs[2] = new int[] { 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3 };
        vpIs[3] = new int[] { 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4 };
        vpIs[4] = new int[] { 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5 };
        vpIs[5] = new int[] { 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6 };
        vpIs[6] = new int[] { 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8, 7 };
        vpIs[7] = new int[] { 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8 };
        vpIs[8] = new int[] { 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9 };
        vpIs[9] = new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10 };
        vpIs[10] = new int[] { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11 };
        vpIs[11] = new int[] { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12 };
        vpIs[12] = new int[] { 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13 };
        vpIs[13] = new int[] { 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15, 14 };
        vpIs[14] = new int[] { 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 15 };
        vpIs[15] = new int[] { 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
    }

    /**
     * computePCMSamples.
     * @param vp {@code float[]}
     * @param scaleFactor {@code float}
     * @param writePos {@code int}
     * @param resultBuffer {@code float[]}
     */
    private static void computePCMSamples(final float[] vp, final int writePos, final float[] resultBuffer) {
        int dvPos = 0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < vpIs[writePos].length; j++) {
                resultBuffer[i] += (vp[vpIs[writePos][j] + dvPos] * d16[i][j]);
            }
            normalize(resultBuffer, i);
            dvPos += 16;
        }
    }

    /**
     * scaleAndNormalize.
     * @param resultBuffer {@code float[]}
     * @param index {@code int}
     */
    protected static void normalize(final float[] resultBuffer, final int index) {
        if (resultBuffer[index] > 1.0f) {
            resultBuffer[index] = 1.0f;
        } else if (resultBuffer[index] < -1.0f) {
            resultBuffer[index] = -1.0f;
        }
    }

    /**
     * Converts a 1D array into a number of smaller arrays. This is used
     * to achieve offset + constant indexing into an array. Each sub-array
     * represents a block of values of the original array.
     * @param array			The array to split up into blocks.
     * @param blockSize		The size of the blocks to split the array
     *						into. This must be an exact divisor of
     *						the length of the array, or some data
     *						will be lost from the main array.
     *
     * @return	An array of arrays in which each element in the returned
     *			array will be of length <code>blockSize</code>.
     */
    static private float[][] splitArray(final float[] array, final int blockSize) {
        final int size = array.length / blockSize;
        final float[][] split = new float[size][];
        for (int i = 0; i < size; i++) {
            split[i] = subArray(array, i * blockSize, blockSize);
        }
        return split;
    }

    /**
     * Returns a subarray of an existing array.
     *
     * @param array	The array to retrieve a subarra from.
     * @param offs	The offset in the array that corresponds to
     *				the first index of the subarray.
     * @param len	The number of indeces in the subarray.
     * @return The subarray, which may be of length 0.
     */
    static private float[] subArray(final float[] array, final int offs, int len) {
        if ((offs + len) > array.length) {
            len = array.length - offs;
        }

        if (len < 0) {
            len = 0;
        }

        final float[] subarray = new float[len];
        for (int i = 0; i < len; i++) {
            subarray[i] = array[offs + i];
        }

        return subarray;
    }

    private float[] v1;
    private float[] v2;

    private int currentWritePos; // 0-15

    private final float[] _tmpOut = new float[32];

    /**
     * SynthesisFilterBank constructor.
     */
    public SynthesisFilterBank() {
        d16 = splitArray(d_data, 16);
        this.v1 = new float[512];
        this.v2 = new float[512];
        this.currentWritePos = 15;
    }

    /**
     * Calculate 32 PCM subbandSamples and put the into the Obuffer-object.
     * @param subbandSamples {@code float[]}
     * @param dr {@link DefaultDecoderResult}
     * @param channel {@code int}
     */
    public void calculatePCMSamples(final float[] subbandSamples, final DefaultDecoderResult dr, final int channel) {
        FCT.compute(subbandSamples, this.v1, this.v2, this.currentWritePos);
        computePCMSamples(this.v1, this.currentWritePos, this._tmpOut);
        dr.appendSamples(this._tmpOut, this._tmpOut.length, channel);
        this.currentWritePos = (this.currentWritePos + 1) & 0xf;
        switchResultArrays();
    }

    protected void switchResultArrays() {
        final float[] tmp = this.v1;
        this.v1 = this.v2;
        this.v2 = tmp;
    }

    //    /**
    //     * setEQ.
    //     * @param eq0 {@code float[]}
    //     */
    //    public void setEQ(final float[] eq0) {
    //        this.eq = eq0;
    //        if (this.eq == null) {
    //            this.eq = new float[32];
    //            for (int i = 0; i < 32; i++) {
    //                this.eq[i] = 1.0f;
    //            }
    //        }
    //        if (this.eq.length < 32) {
    //            throw new IllegalArgumentException("eq0");
    //        }
    //
    //    }

    // The original data for d[]. This data is loaded from a file
    // to reduce the overall package size and to improve performance.

    static final float d_data[] = { 0.000000000f, -0.000442505f, 0.003250122f, -0.007003784f, 0.031082153f,
            -0.078628540f, 0.100311279f, -0.572036743f, 1.144989014f, 0.572036743f, 0.100311279f, 0.078628540f,
            0.031082153f, 0.007003784f, 0.003250122f, 0.000442505f, -0.000015259f, -0.000473022f, 0.003326416f,
            -0.007919312f, 0.030517578f, -0.084182739f, 0.090927124f, -0.600219727f, 1.144287109f, 0.543823242f,
            0.108856201f, 0.073059082f, 0.031478882f, 0.006118774f, 0.003173828f, 0.000396729f, -0.000015259f,
            -0.000534058f, 0.003387451f, -0.008865356f, 0.029785156f, -0.089706421f, 0.080688477f, -0.628295898f,
            1.142211914f, 0.515609741f, 0.116577148f, 0.067520142f, 0.031738281f, 0.005294800f, 0.003082275f,
            0.000366211f, -0.000015259f, -0.000579834f, 0.003433228f, -0.009841919f, 0.028884888f, -0.095169067f,
            0.069595337f, -0.656219482f, 1.138763428f, 0.487472534f, 0.123474121f, 0.061996460f, 0.031845093f,
            0.004486084f, 0.002990723f, 0.000320435f, -0.000015259f, -0.000625610f, 0.003463745f, -0.010848999f,
            0.027801514f, -0.100540161f, 0.057617188f, -0.683914185f, 1.133926392f, 0.459472656f, 0.129577637f,
            0.056533813f, 0.031814575f, 0.003723145f, 0.002899170f, 0.000289917f, -0.000015259f, -0.000686646f,
            0.003479004f, -0.011886597f, 0.026535034f, -0.105819702f, 0.044784546f, -0.711318970f, 1.127746582f,
            0.431655884f, 0.134887695f, 0.051132202f, 0.031661987f, 0.003005981f, 0.002792358f, 0.000259399f,
            -0.000015259f, -0.000747681f, 0.003479004f, -0.012939453f, 0.025085449f, -0.110946655f, 0.031082153f,
            -0.738372803f, 1.120223999f, 0.404083252f, 0.139450073f, 0.045837402f, 0.031387329f, 0.002334595f,
            0.002685547f, 0.000244141f, -0.000030518f, -0.000808716f, 0.003463745f, -0.014022827f, 0.023422241f,
            -0.115921021f, 0.016510010f, -0.765029907f, 1.111373901f, 0.376800537f, 0.143264771f, 0.040634155f,
            0.031005859f, 0.001693726f, 0.002578735f, 0.000213623f, -0.000030518f, -0.000885010f, 0.003417969f,
            -0.015121460f, 0.021575928f, -0.120697021f, 0.001068115f, -0.791213989f, 1.101211548f, 0.349868774f,
            0.146362305f, 0.035552979f, 0.030532837f, 0.001098633f, 0.002456665f, 0.000198364f, -0.000030518f,
            -0.000961304f, 0.003372192f, -0.016235352f, 0.019531250f, -0.125259399f, -0.015228271f, -0.816864014f,
            1.089782715f, 0.323318481f, 0.148773193f, 0.030609131f, 0.029937744f, 0.000549316f, 0.002349854f,
            0.000167847f, -0.000030518f, -0.001037598f, 0.003280640f, -0.017349243f, 0.017257690f, -0.129562378f,
            -0.032379150f, -0.841949463f, 1.077117920f, 0.297210693f, 0.150497437f, 0.025817871f, 0.029281616f,
            0.000030518f, 0.002243042f, 0.000152588f, -0.000045776f, -0.001113892f, 0.003173828f, -0.018463135f,
            0.014801025f, -0.133590698f, -0.050354004f, -0.866363525f, 1.063217163f, 0.271591187f, 0.151596069f,
            0.021179199f, 0.028533936f, -0.000442505f, 0.002120972f, 0.000137329f, -0.000045776f, -0.001205444f,
            0.003051758f, -0.019577026f, 0.012115479f, -0.137298584f, -0.069168091f, -0.890090942f, 1.048156738f,
            0.246505737f, 0.152069092f, 0.016708374f, 0.027725220f, -0.000869751f, 0.002014160f, 0.000122070f,
            -0.000061035f, -0.001296997f, 0.002883911f, -0.020690918f, 0.009231567f, -0.140670776f, -0.088775635f,
            -0.913055420f, 1.031936646f, 0.221984863f, 0.151962280f, 0.012420654f, 0.026840210f, -0.001266479f,
            0.001907349f, 0.000106812f, -0.000061035f, -0.001388550f, 0.002700806f, -0.021789551f, 0.006134033f,
            -0.143676758f, -0.109161377f, -0.935195923f, 1.014617920f, 0.198059082f, 0.151306152f, 0.008316040f,
            0.025909424f, -0.001617432f, 0.001785278f, 0.000106812f, -0.000076294f, -0.001480103f, 0.002487183f,
            -0.022857666f, 0.002822876f, -0.146255493f, -0.130310059f, -0.956481934f, 0.996246338f, 0.174789429f,
            0.150115967f, 0.004394531f, 0.024932861f, -0.001937866f, 0.001693726f, 0.000091553f, -0.000076294f,
            -0.001586914f, 0.002227783f, -0.023910522f, -0.000686646f, -0.148422241f, -0.152206421f, -0.976852417f,
            0.976852417f, 0.152206421f, 0.148422241f, 0.000686646f, 0.023910522f, -0.002227783f, 0.001586914f,
            0.000076294f, -0.000091553f, -0.001693726f, 0.001937866f, -0.024932861f, -0.004394531f, -0.150115967f,
            -0.174789429f, -0.996246338f, 0.956481934f, 0.130310059f, 0.146255493f, -0.002822876f, 0.022857666f,
            -0.002487183f, 0.001480103f, 0.000076294f, -0.000106812f, -0.001785278f, 0.001617432f, -0.025909424f,
            -0.008316040f, -0.151306152f, -0.198059082f, -1.014617920f, 0.935195923f, 0.109161377f, 0.143676758f,
            -0.006134033f, 0.021789551f, -0.002700806f, 0.001388550f, 0.000061035f, -0.000106812f, -0.001907349f,
            0.001266479f, -0.026840210f, -0.012420654f, -0.151962280f, -0.221984863f, -1.031936646f, 0.913055420f,
            0.088775635f, 0.140670776f, -0.009231567f, 0.020690918f, -0.002883911f, 0.001296997f, 0.000061035f,
            -0.000122070f, -0.002014160f, 0.000869751f, -0.027725220f, -0.016708374f, -0.152069092f, -0.246505737f,
            -1.048156738f, 0.890090942f, 0.069168091f, 0.137298584f, -0.012115479f, 0.019577026f, -0.003051758f,
            0.001205444f, 0.000045776f, -0.000137329f, -0.002120972f, 0.000442505f, -0.028533936f, -0.021179199f,
            -0.151596069f, -0.271591187f, -1.063217163f, 0.866363525f, 0.050354004f, 0.133590698f, -0.014801025f,
            0.018463135f, -0.003173828f, 0.001113892f, 0.000045776f, -0.000152588f, -0.002243042f, -0.000030518f,
            -0.029281616f, -0.025817871f, -0.150497437f, -0.297210693f, -1.077117920f, 0.841949463f, 0.032379150f,
            0.129562378f, -0.017257690f, 0.017349243f, -0.003280640f, 0.001037598f, 0.000030518f, -0.000167847f,
            -0.002349854f, -0.000549316f, -0.029937744f, -0.030609131f, -0.148773193f, -0.323318481f, -1.089782715f,
            0.816864014f, 0.015228271f, 0.125259399f, -0.019531250f, 0.016235352f, -0.003372192f, 0.000961304f,
            0.000030518f, -0.000198364f, -0.002456665f, -0.001098633f, -0.030532837f, -0.035552979f, -0.146362305f,
            -0.349868774f, -1.101211548f, 0.791213989f, -0.001068115f, 0.120697021f, -0.021575928f, 0.015121460f,
            -0.003417969f, 0.000885010f, 0.000030518f, -0.000213623f, -0.002578735f, -0.001693726f, -0.031005859f,
            -0.040634155f, -0.143264771f, -0.376800537f, -1.111373901f, 0.765029907f, -0.016510010f, 0.115921021f,
            -0.023422241f, 0.014022827f, -0.003463745f, 0.000808716f, 0.000030518f, -0.000244141f, -0.002685547f,
            -0.002334595f, -0.031387329f, -0.045837402f, -0.139450073f, -0.404083252f, -1.120223999f, 0.738372803f,
            -0.031082153f, 0.110946655f, -0.025085449f, 0.012939453f, -0.003479004f, 0.000747681f, 0.000015259f,
            -0.000259399f, -0.002792358f, -0.003005981f, -0.031661987f, -0.051132202f, -0.134887695f, -0.431655884f,
            -1.127746582f, 0.711318970f, -0.044784546f, 0.105819702f, -0.026535034f, 0.011886597f, -0.003479004f,
            0.000686646f, 0.000015259f, -0.000289917f, -0.002899170f, -0.003723145f, -0.031814575f, -0.056533813f,
            -0.129577637f, -0.459472656f, -1.133926392f, 0.683914185f, -0.057617188f, 0.100540161f, -0.027801514f,
            0.010848999f, -0.003463745f, 0.000625610f, 0.000015259f, -0.000320435f, -0.002990723f, -0.004486084f,
            -0.031845093f, -0.061996460f, -0.123474121f, -0.487472534f, -1.138763428f, 0.656219482f, -0.069595337f,
            0.095169067f, -0.028884888f, 0.009841919f, -0.003433228f, 0.000579834f, 0.000015259f, -0.000366211f,
            -0.003082275f, -0.005294800f, -0.031738281f, -0.067520142f, -0.116577148f, -0.515609741f, -1.142211914f,
            0.628295898f, -0.080688477f, 0.089706421f, -0.029785156f, 0.008865356f, -0.003387451f, 0.000534058f,
            0.000015259f, -0.000396729f, -0.003173828f, -0.006118774f, -0.031478882f, -0.073059082f, -0.108856201f,
            -0.543823242f, -1.144287109f, 0.600219727f, -0.090927124f, 0.084182739f, -0.030517578f, 0.007919312f,
            -0.003326416f, 0.000473022f, 0.000015259f };

}
