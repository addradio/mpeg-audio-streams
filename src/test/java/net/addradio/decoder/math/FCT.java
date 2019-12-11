/**
 * Class:    FCT<br/>
 * <br/>
 * Created:  06.12.2019<br/>
 * Filename: FCT.java<br/>
 * Version:  $Revision$<br/>
 * <br/>
 * last modified on $Date$<br/>
 *               by $Author$<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author$ -- $Revision$ -- $Date$
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2019 - All rights reserved.
 */
package net.addradio.decoder.math;

/**
 * FCT.
 */
public final class FCT {

    /** {@code float} cos1_64. */
    public static final float cos1_64 = predefinedCalculation1(1, 64);
    /** {@code float} cos3_64. */
    public static final float cos3_64 = predefinedCalculation1(3, 64);
    /** {@code float} cos5_64. */
    public static final float cos5_64 = predefinedCalculation1(5, 64);
    /** {@code float} cos7_64. */
    public static final float cos7_64 = predefinedCalculation1(7, 64);
    /** {@code float} cos9_64. */
    public static final float cos9_64 = predefinedCalculation1(9, 64);
    /** {@code float} cos11_64. */
    public static final float cos11_64 = predefinedCalculation1(11, 64);
    /** {@code float} cos13_64. */
    public static final float cos13_64 = predefinedCalculation1(13, 64);
    /** {@code float} cos15_64. */
    public static final float cos15_64 = predefinedCalculation1(15, 64);
    /** {@code float} cos17_64. */
    public static final float cos17_64 = predefinedCalculation1(17, 64);
    /** {@code float} cos19_64. */
    public static final float cos19_64 = predefinedCalculation1(19, 64);
    /** {@code float} cos21_64. */
    public static final float cos21_64 = predefinedCalculation1(21, 64);
    /** {@code float} cos23_64. */
    public static final float cos23_64 = predefinedCalculation1(23, 64);
    /** {@code float} cos25_64. */
    public static final float cos25_64 = predefinedCalculation1(25, 64);
    /** {@code float} cos27_64. */
    public static final float cos27_64 = predefinedCalculation1(27, 64);
    /** {@code float} cos29_64. */
    public static final float cos29_64 = predefinedCalculation1(29, 64);
    /** {@code float} cos31_64. */
    public static final float cos31_64 = predefinedCalculation1(31, 64);
    /** {@code float} cos1_32. */
    public static final float cos1_32 = predefinedCalculation1(1, 32);
    /** {@code float} cos3_32. */
    public static final float cos3_32 = predefinedCalculation1(3, 32);
    /** {@code float} cos5_32. */
    public static final float cos5_32 = predefinedCalculation1(5, 32);
    /** {@code float} cos7_32. */
    public static final float cos7_32 = predefinedCalculation1(7, 32);
    /** {@code float} cos9_32. */
    public static final float cos9_32 = predefinedCalculation1(9, 32);
    /** {@code float} cos11_32. */
    public static final float cos11_32 = predefinedCalculation1(11, 32);
    /** {@code float} cos13_32. */
    public static final float cos13_32 = predefinedCalculation1(13, 32);
    /** {@code float} cos15_32. */
    public static final float cos15_32 = predefinedCalculation1(15, 32);
    /** {@code float} cos1_16. */
    public static final float cos1_16 = predefinedCalculation1(1, 16);
    /** {@code float} cos3_16. */
    public static final float cos3_16 = predefinedCalculation1(3, 16);
    /** {@code float} cos5_16. */
    public static final float cos5_16 = predefinedCalculation1(5, 16);
    /** {@code float} cos7_16. */
    public static final float cos7_16 = predefinedCalculation1(7, 16);
    /** {@code float} cos1_8. */
    public static final float cos1_8 = predefinedCalculation1(1, 8);
    /** {@code float} cos3_8. */
    public static final float cos3_8 = predefinedCalculation1(3, 8);
    /** {@code float} cos1_4. */
    public static final float cos1_4 = predefinedCalculation1(1, 4);

    /**
     * Compute new values via a fast cosine transform.
     * @param input {@code float[]}
     * @param results1 {@code float[]}
     * @param results2 {@code float[]}
     * @param writePos {@code int}
     */
    public static void compute(final float[] input, final float[] results1, final float[] results2,
            final int writePos) {
        float new_v0, new_v1, new_v2, new_v3, new_v4, new_v5, new_v6, new_v7, new_v8, new_v9;
        float new_v10, new_v11, new_v12, new_v13, new_v14, new_v15, new_v16, new_v17, new_v18, new_v19;
        float new_v20, new_v21, new_v22, new_v23, new_v24, new_v25, new_v26, new_v27, new_v28, new_v29;
        float new_v30, new_v31;

        new_v0 = new_v1 = new_v2 = new_v3 = new_v4 = new_v5 = new_v6 = new_v7 = new_v8 = new_v9 = new_v10 = new_v11 = new_v12 = new_v13 = new_v14 = new_v15 = new_v16 = new_v17 = new_v18 = new_v19 = new_v20 = new_v21 = new_v22 = new_v23 = new_v24 = new_v25 = new_v26 = new_v27 = new_v28 = new_v29 = new_v30 = new_v31 = 0.0f;

        final float s0 = input[0];
        final float s1 = input[1];
        final float s2 = input[2];
        final float s3 = input[3];
        final float s4 = input[4];
        final float s5 = input[5];
        final float s6 = input[6];
        final float s7 = input[7];
        final float s8 = input[8];
        final float s9 = input[9];
        final float s10 = input[10];
        final float s11 = input[11];
        final float s12 = input[12];
        final float s13 = input[13];
        final float s14 = input[14];
        final float s15 = input[15];
        final float s16 = input[16];
        final float s17 = input[17];
        final float s18 = input[18];
        final float s19 = input[19];
        final float s20 = input[20];
        final float s21 = input[21];
        final float s22 = input[22];
        final float s23 = input[23];
        final float s24 = input[24];
        final float s25 = input[25];
        final float s26 = input[26];
        final float s27 = input[27];
        final float s28 = input[28];
        final float s29 = input[29];
        final float s30 = input[30];
        final float s31 = input[31];

        float p0 = s0 + s31;
        float p1 = s1 + s30;
        float p2 = s2 + s29;
        float p3 = s3 + s28;
        float p4 = s4 + s27;
        float p5 = s5 + s26;
        float p6 = s6 + s25;
        float p7 = s7 + s24;
        float p8 = s8 + s23;
        float p9 = s9 + s22;
        float p10 = s10 + s21;
        float p11 = s11 + s20;
        float p12 = s12 + s19;
        float p13 = s13 + s18;
        float p14 = s14 + s17;
        float p15 = s15 + s16;

        float pp0 = p0 + p15;
        float pp1 = p1 + p14;
        float pp2 = p2 + p13;
        float pp3 = p3 + p12;
        float pp4 = p4 + p11;
        float pp5 = p5 + p10;
        float pp6 = p6 + p9;
        float pp7 = p7 + p8;
        float pp8 = (p0 - p15) * cos1_32;
        float pp9 = (p1 - p14) * cos3_32;
        float pp10 = (p2 - p13) * cos5_32;
        float pp11 = (p3 - p12) * cos7_32;
        float pp12 = (p4 - p11) * cos9_32;
        float pp13 = (p5 - p10) * cos11_32;
        float pp14 = (p6 - p9) * cos13_32;
        float pp15 = (p7 - p8) * cos15_32;

        p0 = pp0 + pp7;
        p1 = pp1 + pp6;
        p2 = pp2 + pp5;
        p3 = pp3 + pp4;
        p4 = (pp0 - pp7) * cos1_16;
        p5 = (pp1 - pp6) * cos3_16;
        p6 = (pp2 - pp5) * cos5_16;
        p7 = (pp3 - pp4) * cos7_16;
        p8 = pp8 + pp15;
        p9 = pp9 + pp14;
        p10 = pp10 + pp13;
        p11 = pp11 + pp12;
        p12 = (pp8 - pp15) * cos1_16;
        p13 = (pp9 - pp14) * cos3_16;
        p14 = (pp10 - pp13) * cos5_16;
        p15 = (pp11 - pp12) * cos7_16;

        pp0 = p0 + p3;
        pp1 = p1 + p2;
        pp2 = (p0 - p3) * cos1_8;
        pp3 = (p1 - p2) * cos3_8;
        pp4 = p4 + p7;
        pp5 = p5 + p6;
        pp6 = (p4 - p7) * cos1_8;
        pp7 = (p5 - p6) * cos3_8;
        pp8 = p8 + p11;
        pp9 = p9 + p10;
        pp10 = (p8 - p11) * cos1_8;
        pp11 = (p9 - p10) * cos3_8;
        pp12 = p12 + p15;
        pp13 = p13 + p14;
        pp14 = (p12 - p15) * cos1_8;
        pp15 = (p13 - p14) * cos3_8;

        p0 = pp0 + pp1;
        p1 = (pp0 - pp1) * cos1_4;
        p2 = pp2 + pp3;
        p3 = (pp2 - pp3) * cos1_4;
        p4 = pp4 + pp5;
        p5 = (pp4 - pp5) * cos1_4;
        p6 = pp6 + pp7;
        p7 = (pp6 - pp7) * cos1_4;
        p8 = pp8 + pp9;
        p9 = (pp8 - pp9) * cos1_4;
        p10 = pp10 + pp11;
        p11 = (pp10 - pp11) * cos1_4;
        p12 = pp12 + pp13;
        p13 = (pp12 - pp13) * cos1_4;
        p14 = pp14 + pp15;
        p15 = (pp14 - pp15) * cos1_4;

        // this is pretty insane coding
        float tmp1;
        new_v19/*36-17*/ = -(new_v4 = (new_v12 = p7) + p5) - p6;
        new_v27/*44-17*/ = -p6 - p7 - p4;
        new_v6 = (new_v10 = (new_v14 = p15) + p11) + p13;
        new_v17/*34-17*/ = -(new_v2 = p15 + p13 + p9) - p14;
        new_v21/*38-17*/ = (tmp1 = -p14 - p15 - p10 - p11) - p13;
        new_v29/*46-17*/ = -p14 - p15 - p12 - p8;
        new_v25/*42-17*/ = tmp1 - p12;
        new_v31/*48-17*/ = -p0;
        new_v0 = p1;
        new_v23/*40-17*/ = -(new_v8 = p3) - p2;

        p0 = (s0 - s31) * cos1_64;
        p1 = (s1 - s30) * cos3_64;
        p2 = (s2 - s29) * cos5_64;
        p3 = (s3 - s28) * cos7_64;
        p4 = (s4 - s27) * cos9_64;
        p5 = (s5 - s26) * cos11_64;
        p6 = (s6 - s25) * cos13_64;
        p7 = (s7 - s24) * cos15_64;
        p8 = (s8 - s23) * cos17_64;
        p9 = (s9 - s22) * cos19_64;
        p10 = (s10 - s21) * cos21_64;
        p11 = (s11 - s20) * cos23_64;
        p12 = (s12 - s19) * cos25_64;
        p13 = (s13 - s18) * cos27_64;
        p14 = (s14 - s17) * cos29_64;
        p15 = (s15 - s16) * cos31_64;

        pp0 = p0 + p15;
        pp1 = p1 + p14;
        pp2 = p2 + p13;
        pp3 = p3 + p12;
        pp4 = p4 + p11;
        pp5 = p5 + p10;
        pp6 = p6 + p9;
        pp7 = p7 + p8;
        pp8 = (p0 - p15) * cos1_32;
        pp9 = (p1 - p14) * cos3_32;
        pp10 = (p2 - p13) * cos5_32;
        pp11 = (p3 - p12) * cos7_32;
        pp12 = (p4 - p11) * cos9_32;
        pp13 = (p5 - p10) * cos11_32;
        pp14 = (p6 - p9) * cos13_32;
        pp15 = (p7 - p8) * cos15_32;

        p0 = pp0 + pp7;
        p1 = pp1 + pp6;
        p2 = pp2 + pp5;
        p3 = pp3 + pp4;
        p4 = (pp0 - pp7) * cos1_16;
        p5 = (pp1 - pp6) * cos3_16;
        p6 = (pp2 - pp5) * cos5_16;
        p7 = (pp3 - pp4) * cos7_16;
        p8 = pp8 + pp15;
        p9 = pp9 + pp14;
        p10 = pp10 + pp13;
        p11 = pp11 + pp12;
        p12 = (pp8 - pp15) * cos1_16;
        p13 = (pp9 - pp14) * cos3_16;
        p14 = (pp10 - pp13) * cos5_16;
        p15 = (pp11 - pp12) * cos7_16;

        pp0 = p0 + p3;
        pp1 = p1 + p2;
        pp2 = (p0 - p3) * cos1_8;
        pp3 = (p1 - p2) * cos3_8;
        pp4 = p4 + p7;
        pp5 = p5 + p6;
        pp6 = (p4 - p7) * cos1_8;
        pp7 = (p5 - p6) * cos3_8;
        pp8 = p8 + p11;
        pp9 = p9 + p10;
        pp10 = (p8 - p11) * cos1_8;
        pp11 = (p9 - p10) * cos3_8;
        pp12 = p12 + p15;
        pp13 = p13 + p14;
        pp14 = (p12 - p15) * cos1_8;
        pp15 = (p13 - p14) * cos3_8;

        p0 = pp0 + pp1;
        p1 = (pp0 - pp1) * cos1_4;
        p2 = pp2 + pp3;
        p3 = (pp2 - pp3) * cos1_4;
        p4 = pp4 + pp5;
        p5 = (pp4 - pp5) * cos1_4;
        p6 = pp6 + pp7;
        p7 = (pp6 - pp7) * cos1_4;
        p8 = pp8 + pp9;
        p9 = (pp8 - pp9) * cos1_4;
        p10 = pp10 + pp11;
        p11 = (pp10 - pp11) * cos1_4;
        p12 = pp12 + pp13;
        p13 = (pp12 - pp13) * cos1_4;
        p14 = pp14 + pp15;
        p15 = (pp14 - pp15) * cos1_4;

        // manually doing something that a compiler should handle sucks
        // coding like this is hard to read
        float tmp2;
        new_v5 = (new_v11 = (new_v13 = (new_v15 = p15) + p7) + p11) + p5 + p13;
        new_v7 = (new_v9 = p15 + p11 + p3) + p13;
        new_v16/*33-17*/ = -(new_v1 = (tmp1 = p13 + p15 + p9) + p1) - p14;
        new_v18/*35-17*/ = -(new_v3 = tmp1 + p5 + p7) - p6 - p14;

        new_v22/*39-17*/ = (tmp1 = -p10 - p11 - p14 - p15) - p13 - p2 - p3;
        new_v20/*37-17*/ = tmp1 - p13 - p5 - p6 - p7;
        new_v24/*41-17*/ = tmp1 - p12 - p2 - p3;
        new_v26/*43-17*/ = tmp1 - p12 - (tmp2 = p4 + p6 + p7);
        new_v30/*47-17*/ = (tmp1 = -p8 - p12 - p14 - p15) - p0;
        new_v28/*45-17*/ = tmp1 - tmp2;

        results1[0 + writePos] = new_v0;
        results1[16 + writePos] = new_v1;
        results1[32 + writePos] = new_v2;
        results1[48 + writePos] = new_v3;
        results1[64 + writePos] = new_v4;
        results1[80 + writePos] = new_v5;
        results1[96 + writePos] = new_v6;
        results1[112 + writePos] = new_v7;
        results1[128 + writePos] = new_v8;
        results1[144 + writePos] = new_v9;
        results1[160 + writePos] = new_v10;
        results1[176 + writePos] = new_v11;
        results1[192 + writePos] = new_v12;
        results1[208 + writePos] = new_v13;
        results1[224 + writePos] = new_v14;
        results1[240 + writePos] = new_v15;

        // V[16] is always 0.0:
        results1[256 + writePos] = 0.0f;

        // insert V[17-31] (== -new_v[15-1]) into actual v:
        results1[272 + writePos] = -new_v15;
        results1[288 + writePos] = -new_v14;
        results1[304 + writePos] = -new_v13;
        results1[320 + writePos] = -new_v12;
        results1[336 + writePos] = -new_v11;
        results1[352 + writePos] = -new_v10;
        results1[368 + writePos] = -new_v9;
        results1[384 + writePos] = -new_v8;
        results1[400 + writePos] = -new_v7;
        results1[416 + writePos] = -new_v6;
        results1[432 + writePos] = -new_v5;
        results1[448 + writePos] = -new_v4;
        results1[464 + writePos] = -new_v3;
        results1[480 + writePos] = -new_v2;
        results1[496 + writePos] = -new_v1;

        // insert V[32] (== -new_v[0]) into other v:
        results2[0 + writePos] = -new_v0;
        // insert V[33-48] (== new_v[16-31]) into other v:
        results2[16 + writePos] = new_v16;
        results2[32 + writePos] = new_v17;
        results2[48 + writePos] = new_v18;
        results2[64 + writePos] = new_v19;
        results2[80 + writePos] = new_v20;
        results2[96 + writePos] = new_v21;
        results2[112 + writePos] = new_v22;
        results2[128 + writePos] = new_v23;
        results2[144 + writePos] = new_v24;
        results2[160 + writePos] = new_v25;
        results2[176 + writePos] = new_v26;
        results2[192 + writePos] = new_v27;
        results2[208 + writePos] = new_v28;
        results2[224 + writePos] = new_v29;
        results2[240 + writePos] = new_v30;
        results2[256 + writePos] = new_v31;

        // insert V[49-63] (== new_v[30-16]) into other v:
        results2[272 + writePos] = new_v30;
        results2[288 + writePos] = new_v29;
        results2[304 + writePos] = new_v28;
        results2[320 + writePos] = new_v27;
        results2[336 + writePos] = new_v26;
        results2[352 + writePos] = new_v25;
        results2[368 + writePos] = new_v24;
        results2[384 + writePos] = new_v23;
        results2[400 + writePos] = new_v22;
        results2[416 + writePos] = new_v21;
        results2[432 + writePos] = new_v20;
        results2[448 + writePos] = new_v19;
        results2[464 + writePos] = new_v18;
        results2[480 + writePos] = new_v17;
        results2[496 + writePos] = new_v16;
    }

    /**
     * predefinedCalculation1.
     * @param k {@code double}
     * @param l {@code double}
     * @return {@code float}
     */
    public static final float predefinedCalculation1(final double k, final double l) {
        return (float) (1d / (2d * Math.cos((Math.PI * k) / l)));
    }

    /**
     * FCT constructor.
     */
    private FCT() {
    }

}
