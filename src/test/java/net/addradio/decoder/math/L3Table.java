/**
 * Class:    L3Table<br/>
 * <br/>
 * Created:  04.12.2019<br/>
 * Filename: L3Table.java<br/>
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

import java.util.HashMap;
import java.util.Map;

import net.addradio.codec.mpeg.audio.model.SamplingRate;
import net.addradio.codec.mpeg.audio.model.Version;

/**
 * L3Table.
 */
public final class L3Table {

    /**
     * SBI.
     */
    public static final class SBI {

        /** {@code int[]} l. */
        public int[] l;

        /** {@code int[]} s. */
        public int[] s;

        /**
         * SBI constructor.
         */
        public SBI() {
            this.l = new int[23];
            this.s = new int[14];
        }

        /**
         * SBI constructor.
         * @param thel {@code int[]}
         * @param thes {@code int[]}
         */
        public SBI(final int[] thel, final int[] thes) {
            this.l = thel;
            this.s = thes;
        }
    }

    /** {@link Map}{@code <Version,Map<SamplingRate,SBI>>} SBIs. */
    public final static Map<Version, Map<SamplingRate, SBI>> SBIs = createSBIs();

    /** {@code float[][]} io. */
    public static final float io[][] = { //
            { //
                    1.0000000000E+00f, 8.4089641526E-01f, 7.0710678119E-01f, 5.9460355751E-01f, //
                    5.0000000001E-01f, 4.2044820763E-01f, 3.5355339060E-01f, 2.9730177876E-01f, //
                    2.5000000001E-01f, 2.1022410382E-01f, 1.7677669530E-01f, 1.4865088938E-01f, //
                    1.2500000000E-01f, 1.0511205191E-01f, 8.8388347652E-02f, 7.4325444691E-02f, //
                    6.2500000003E-02f, 5.2556025956E-02f, 4.4194173826E-02f, 3.7162722346E-02f, //
                    3.1250000002E-02f, 2.6278012978E-02f, 2.2097086913E-02f, 1.8581361173E-02f, //
                    1.5625000001E-02f, 1.3139006489E-02f, 1.1048543457E-02f, 9.2906805866E-03f, //
                    7.8125000006E-03f, 6.5695032447E-03f, 5.5242717285E-03f, 4.6453402934E-03f },
            { //
                    1.0000000000E+00f, 7.0710678119E-01f, 5.0000000000E-01f, 3.5355339060E-01f, //
                    2.5000000000E-01f, 1.7677669530E-01f, 1.2500000000E-01f, 8.8388347650E-02f, //
                    6.2500000001E-02f, 4.4194173825E-02f, 3.1250000001E-02f, 2.2097086913E-02f, //
                    1.5625000000E-02f, 1.1048543456E-02f, 7.8125000002E-03f, 5.5242717282E-03f, //
                    3.9062500001E-03f, 2.7621358641E-03f, 1.9531250001E-03f, 1.3810679321E-03f, //
                    9.7656250004E-04f, 6.9053396603E-04f, 4.8828125002E-04f, 3.4526698302E-04f, //
                    2.4414062501E-04f, 1.7263349151E-04f, 1.2207031251E-04f, 8.6316745755E-05f, //
                    6.1035156254E-05f, 4.3158372878E-05f, 3.0517578127E-05f, 2.1579186439E-05f } };

    /** {@code float[]} TAN12. */
    public static final float TAN12[] = { 0.0f, 0.26794919f, 0.57735027f, 1.0f, 1.73205081f, 3.73205081f, 9.9999999e10f,
            -3.73205081f, -1.73205081f, -1.0f, -0.57735027f, -0.26794919f, 0.0f, 0.26794919f, 0.57735027f, 1.0f };

    /**
     * createSBIs.
     * @return
     */
    private static Map<Version, Map<SamplingRate, SBI>> createSBIs() {
        final Map<Version, Map<SamplingRate, SBI>> versions = new HashMap<>();

        final Map<SamplingRate, SBI> mpeg1SBIs = new HashMap<>();
        mpeg1SBIs.put(SamplingRate._44100, new SBI(//
                new int[] { 0, 4, 8, 12, 16, 20, 24, 30, 36, 44, 52, 62, 74, 90, 110, 134, 162, 196, 238, 288, 342, 418,
                        576 },
                new int[] { 0, 4, 8, 12, 16, 22, 30, 40, 52, 66, 84, 106, 136, 192 }));
        mpeg1SBIs.put(SamplingRate._48000, new SBI(//
                new int[] { 0, 4, 8, 12, 16, 20, 24, 30, 36, 42, 50, 60, 72, 88, 106, 128, 156, 190, 230, 276, 330, 384,
                        576 },
                new int[] { 0, 4, 8, 12, 16, 22, 28, 38, 50, 64, 80, 100, 126, 192 }));
        mpeg1SBIs.put(SamplingRate._32000, new SBI(//
                new int[] { 0, 4, 8, 12, 16, 20, 24, 30, 36, 44, 54, 66, 82, 102, 126, 156, 194, 240, 296, 364, 448,
                        550, 576 },
                new int[] { 0, 4, 8, 12, 16, 22, 30, 42, 58, 78, 104, 138, 180, 192 }));
        versions.put(Version.MPEG_1, mpeg1SBIs);

        final Map<SamplingRate, SBI> mpeg25SBIs = new HashMap<>();
        mpeg25SBIs.put(SamplingRate._11025, new SBI(//
                new int[] { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464,
                        522, 576 },
                new int[] { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 134, 174, 192 }));
        mpeg25SBIs.put(SamplingRate._12000, new SBI(//
                new int[] { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464,
                        522, 576 },
                new int[] { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 134, 174, 192 }));
        mpeg25SBIs.put(SamplingRate._8000, new SBI(//
                new int[] { 0, 12, 24, 36, 48, 60, 72, 88, 108, 132, 160, 192, 232, 280, 336, 400, 476, 566, 568, 570,
                        572, 574, 576 },
                new int[] { 0, 8, 16, 24, 36, 52, 72, 96, 124, 160, 162, 164, 166, 192 }));
        versions.put(Version.MPEG_2_5, mpeg25SBIs);

        final Map<SamplingRate, SBI> mpeg2LSFSBIs = new HashMap<>();
        mpeg2LSFSBIs.put(SamplingRate._22050, new SBI( //
                new int[] { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464,
                        522, 576 },
                new int[] { 0, 4, 8, 12, 18, 24, 32, 42, 56, 74, 100, 132, 174, 192 }));
        mpeg2LSFSBIs.put(SamplingRate._24000, new SBI(//
                new int[] { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 114, 136, 162, 194, 232, 278, 330, 394, 464,
                        540, 576 },
                new int[] { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 136, 180, 192 }));
        mpeg2LSFSBIs.put(SamplingRate._16000, new SBI(//
                new int[] { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464,
                        522, 576 },
                new int[] { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 134, 174, 192 }));
        versions.put(Version.MPEG_2_LSF, mpeg2LSFSBIs);

        return versions;
    }

    /**
     * L3Table constructor.
     */
    private L3Table() {
    }

}
