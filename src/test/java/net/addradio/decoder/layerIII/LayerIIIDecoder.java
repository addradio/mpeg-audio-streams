/**
 * Class:    LayerIIIDecoder<br/>
 * <br/>
 * Created:  02.12.2019<br/>
 * Filename: LayerIIIDecoder.java<br/>
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
package net.addradio.decoder.layerIII;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.model.Mode;
import net.addradio.codec.mpeg.audio.model.ModeExtension;
import net.addradio.codec.mpeg.audio.model.SamplingRate;
import net.addradio.codec.mpeg.audio.model.Version;
import net.addradio.decoder.BitReservoir;
import net.addradio.decoder.PCMDecoder;
import net.addradio.decoder.DefaultDecoderResult;
import net.addradio.decoder.PCMDecoderResult;
import net.addradio.decoder.SynthesisFilterBank;
import net.addradio.decoder.layerIII.model.GranuleSideInfo;
import net.addradio.decoder.layerIII.model.LayerIIISideInfo;
import net.addradio.decoder.math.HuffmanDecoderTable;
import net.addradio.decoder.math.HuffmanDecoderTable.HuffmanVector;
import net.addradio.decoder.math.InvMDCT;
import net.addradio.decoder.math.L3Table;
import net.addradio.decoder.math.L3Table.SBI;
import net.addradio.streams.BitInputStream;

/**
 * LayerIIIDecoder.
 */
public class LayerIIIDecoder implements PCMDecoder {

    static class temporaire2 {
        public int[] l; /* [cb] */
        public int[][] s; /* [window][cb] */

        /**
         * Dummy Constructor
         */
        public temporaire2() {
            this.l = new int[23];
            this.s = new int[3][13];
        }
    }

    /** {@code int[][]} slen. */
    private static final int slen[][] = { //
            { 0, 0, 0, 0, 3, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4 }, //
            { 0, 1, 2, 3, 0, 1, 2, 3, 1, 2, 3, 1, 2, 3, 2, 3 } };

    /** {@code int[][][]} nr_of_sfb_block. */
    public static final int nr_of_sfb_block[][][] = { //
            { { 6, 5, 5, 5 }, { 9, 9, 9, 9 }, { 6, 9, 9, 9 } }, //
            { { 6, 5, 7, 3 }, { 9, 9, 12, 6 }, { 6, 9, 12, 6 } }, //
            { { 11, 10, 0, 0 }, { 18, 18, 0, 0 }, { 15, 18, 0, 0 } }, //
            { { 7, 7, 7, 0 }, { 12, 12, 12, 0 }, { 6, 15, 12, 0 } }, //
            { { 6, 6, 6, 3 }, { 12, 9, 9, 6 }, { 6, 12, 9, 6 } }, //
            { { 8, 8, 5, 0 }, { 15, 12, 9, 0 }, { 6, 18, 9, 0 } } };

    /** {@link int} SSLIMIT. */
    private static final int SSLIMIT = 18;

    /** {@link int} SBLIMIT. */
    private static final int SBLIMIT = 32;

    /** {@link float[]} cs. */
    private static final float cs[] = { //
            0.857492925712f, 0.881741997318f, 0.949628649103f, 0.983314592492f, //
            0.995517816065f, 0.999160558175f, 0.999899195243f, 0.999993155067f };

    /** {@link float[]} ca. */
    private static final float ca[] = { //
            -0.5144957554270f, -0.4717319685650f, -0.3133774542040f, -0.1819131996110f, //
            -0.0945741925262f, -0.0409655828852f, -0.0141985685725f, -0.00369997467375f };

    /** {@code float[]} t_43. */
    public static final float[] t_43 = create_t_43();

    /** {@link double} d43. */
    private final static double d43 = (4d / 3d);

    /** {@code float[]} two_to_negative_half_pow. */
    public static final float two_to_negative_half_pow[] = { //
            1.0000000000E+00f, 7.0710678119E-01f, 5.0000000000E-01f, 3.5355339059E-01f, //
            2.5000000000E-01f, 1.7677669530E-01f, 1.2500000000E-01f, 8.8388347648E-02f, //
            6.2500000000E-02f, 4.4194173824E-02f, 3.1250000000E-02f, 2.2097086912E-02f, //
            1.5625000000E-02f, 1.1048543456E-02f, 7.8125000000E-03f, 5.5242717280E-03f, //
            3.9062500000E-03f, 2.7621358640E-03f, 1.9531250000E-03f, 1.3810679320E-03f, //
            9.7656250000E-04f, 6.9053396600E-04f, 4.8828125000E-04f, 3.4526698300E-04f, //
            2.4414062500E-04f, 1.7263349150E-04f, 1.2207031250E-04f, 8.6316745750E-05f, //
            6.1035156250E-05f, 4.3158372875E-05f, 3.0517578125E-05f, 2.1579186438E-05f, //
            1.5258789062E-05f, 1.0789593219E-05f, 7.6293945312E-06f, 5.3947966094E-06f, //
            3.8146972656E-06f, 2.6973983047E-06f, 1.9073486328E-06f, 1.3486991523E-06f, //
            9.5367431641E-07f, 6.7434957617E-07f, 4.7683715820E-07f, 3.3717478809E-07f, //
            2.3841857910E-07f, 1.6858739404E-07f, 1.1920928955E-07f, 8.4293697022E-08f, //
            5.9604644775E-08f, 4.2146848511E-08f, 2.9802322388E-08f, 2.1073424255E-08f, //
            1.4901161194E-08f, 1.0536712128E-08f, 7.4505805969E-09f, 5.2683560639E-09f, //
            3.7252902985E-09f, 2.6341780319E-09f, 1.8626451492E-09f, 1.3170890160E-09f, //
            9.3132257462E-10f, 6.5854450798E-10f, 4.6566128731E-10f, 3.2927225399E-10f };

    /** {@code int[]} pretab. */
    public static final int pretab[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 3, 3, 3, 2, 0 };

    private final static Map<Version, Map<SamplingRate, int[]>> reorder_table = createReorderTable();

    /** {@link Logger} LOG. */
    private final static Logger LOG = LoggerFactory.getLogger(LayerIIIDecoder.class);

    static private float[] create_t_43() {
        final float[] t43 = new float[8192];
        for (int i = 0; i < 8192; i++) {
            t43[i] = (float) Math.pow(i, d43);
        }
        return t43;
    }

    protected static Map<Version, Map<SamplingRate, int[]>> createReorderTable() {
        final HashMap<Version, Map<SamplingRate, int[]>> result = new HashMap<Version, Map<SamplingRate, int[]>>();
        final Set<Entry<Version, Map<SamplingRate, SBI>>> entrySet = L3Table.SBIs.entrySet();
        for (final Entry<Version, Map<SamplingRate, SBI>> entry : entrySet) {
            final HashMap<SamplingRate, int[]> value = new HashMap<>();
            final Set<Entry<SamplingRate, SBI>> entrySet2 = entry.getValue().entrySet();
            for (final Entry<SamplingRate, SBI> entry2 : entrySet2) {
                value.put(entry2.getKey(), reorder(entry2.getValue().s));
            }
            result.put(entry.getKey(), value);
        }
        return result;
    }

    private static int[] reorder(final int scalefac_band[]) { // SZD: converted from LAME
        int j = 0;
        final int ix[] = new int[576];
        for (int sfb = 0; sfb < 13; sfb++) {
            final int start = scalefac_band[sfb];
            final int end = scalefac_band[sfb + 1];
            for (int window = 0; window < 3; window++) {
                for (int i = start; i < end; i++) {
                    ix[(3 * i) + window] = j++;
                }
            }
        }
        return ix;
    }

    /** {@link BitReservoir} reservoir. */
    private final BitReservoir reservoir;

    /** {@link temporaire2}{@code []} scalefac. */
    private final temporaire2[] scalefac;

    /** {@link LayerIIISideInfo} sideInfo. */
    private final LayerIIISideInfo sideInfo;

    /** {@code int[]} scalefac_buffer. */
    public int[] scalefac_buffer;

    /** {@code int[]} new_slen. */
    private final int[] new_slen = new int[4];

    /** {@link HuffmanVector} hv. */
    private final HuffmanVector hv;

    /** {@link int} huffmanCheckSum. */
    private int huffmanCheckSum = 0;

    /** {@link int[]} is_1d. */
    private final int[] is_1d;

    private final int[] nonzero;

    /** {@link int} part2_start. */
    private int part2_start;

    /** {@link int} frame_start. */
    private int frame_start;

    /** {@link float[][][]} ro. */
    private final float[][][] ro;

    /** {@link int[]} is_pos. */
    private final int[] is_pos = new int[576];

    /** {@link float[]} is_ratio. */
    private final float[] is_ratio = new float[576];

    /** {@link float[][][]} lr. */
    private final float[][][] lr;

    private final float[] out_1d;

    /** {@link float[][]} samples.
     * subband samples are buffered and passed to the SynthesisFilterBank in one go. */
    private final float[][] samples = new float[2][32];

    /** {@link SynthesisFilterBank}{@code []} filters. */
    private final SynthesisFilterBank[] filters;

    /** {@link float[]} tsOutCopy. */
    private final float[] tsOutCopy = new float[18];

    /** {@link float[]} rawout. */
    private final float[] rawout = new float[36];

    /** {@link float[][]} prevblck. */
    private final float[][] prevblck;

    /** {@link float[][]} k. */
    private final float[][] k;

    /** {@link DefaultDecoderResult} decoderResult. */
    private DefaultDecoderResult decoderResult;

    /**
     * LayerIIIDecoder constructor.
     */
    public LayerIIIDecoder() {
        this.is_1d = new int[(SBLIMIT * SSLIMIT) + 4];
        this.reservoir = new BitReservoir();
        this.sideInfo = new LayerIIISideInfo();
        this.scalefac = new temporaire2[] { new temporaire2(), new temporaire2() };
        this.scalefac_buffer = new int[54];
        this.hv = new HuffmanVector();
        this.nonzero = new int[] { 576, 576 };
        this.frame_start = 0;
        this.ro = new float[2][SBLIMIT][SSLIMIT];
        this.lr = new float[2][SBLIMIT][SSLIMIT];
        this.out_1d = new float[SBLIMIT * SSLIMIT];
        this.prevblck = new float[2][SBLIMIT * SSLIMIT];
        this.k = new float[2][SBLIMIT * SSLIMIT];

        this.filters = new SynthesisFilterBank[] { new SynthesisFilterBank(), new SynthesisFilterBank() };
    }

    /**
     * antialias.
     * @param granuleInfo {@link GranuleSideInfo}
     * @param channel {@code int}
     * @param granule {@code int}
     */
    private void antialias(final GranuleSideInfo granuleInfo) {
        // 31 alias-reduction operations between each pair of sub-bands
        // with 8 butterflies between each pair

        if (granuleInfo.isWindowSwitching && (granuleInfo.blockType == 2) && !granuleInfo.isMixedBlock) {
            return;
        }

        int sb18lim;
        if (granuleInfo.isWindowSwitching && granuleInfo.isMixedBlock && (granuleInfo.blockType == 2)) {
            sb18lim = 18;
        } else {
            sb18lim = 558;
        }

        for (int sb18 = 0; sb18 < sb18lim; sb18 += 18) {
            for (int ss = 0; ss < 8; ss++) {
                final int src_idx1 = (sb18 + 17) - ss;
                final int src_idx2 = sb18 + 18 + ss;
                final float bu = this.out_1d[src_idx1];
                final float bd = this.out_1d[src_idx2];
                this.out_1d[src_idx1] = (bu * cs[ss]) - (bd * ca[ss]);
                this.out_1d[src_idx2] = (bd * cs[ss]) + (bu * ca[ss]);
            }
        }
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.decoder.PCMDecoder#decode(net.addradio.codec.mpeg.audio.model.MPEGAudioFrame)
     */
    @Override
    public PCMDecoderResult decode(final MPEGAudioFrame frame) {
        if (this.decoderResult == null) {
            this.decoderResult = new DefaultDecoderResult(frame.getChannels(), frame.getSamplingRate().getValueInHz());
        } else {
            this.decoderResult.reset();
            this.decoderResult.setFrequency(frame.getSamplingRate().getValueInHz());
        }

        final SBI sbi = L3Table.SBIs.get(frame.getVersion()).get(frame.getSamplingRate());

        try (BitInputStream bis = new BitInputStream(new ByteArrayInputStream(frame.getPayload()))) {

            int flush_main;
            int granule, channel, ss, sb, sb18;
            int main_data_end;
            int bytes_to_discard;
            int i;

            LayerIIISideInfoDecoder.decode(frame, bis, this.sideInfo);

            for (i = 0; i < frame.getNumberOfSlots(); i++) {
                this.reservoir.hputbuf(bis.readBits(8));
            }

            main_data_end = this.reservoir.hsstell() >>> 3; // of previous frame

            if ((flush_main = (this.reservoir.hsstell() & 7)) != 0) {
                this.reservoir.hgetbits(8 - flush_main);
                main_data_end++;
            }

            bytes_to_discard = this.frame_start - main_data_end - this.sideInfo.mainDataBegin;

            this.frame_start += frame.getNumberOfSlots();

            if (bytes_to_discard < 0) {
                return PCMDecoderResult.UNDEFINED;
            }

            if (main_data_end > 4096) {
                this.frame_start -= 4096;
                this.reservoir.rewindNbytes(4096);
            }

            for (; bytes_to_discard > 0; bytes_to_discard--) {
                this.reservoir.hgetbits(8);
            }

            for (granule = 0; granule < frame.getVersion().getMaxNumberOfGranules(); granule++) {
                for (channel = 0; channel < frame.getChannels(); channel++) {
                    this.part2_start = this.reservoir.hsstell();

                    switch (frame.getVersion()) {
                    case MPEG_1:
                        decodeScaleFactors(channel, granule);
                        break;
                    case MPEG_2_5:
                    case MPEG_2_LSF:
                        get_LSF_scale_factors(frame, channel, granule);
                        break;
                    case reserved:
                    default:
                        break;
                    }

                    huffmanDecode(channel, granule, sbi, frame.getSamplingRate());
                    // System.out.println("CheckSum HuffMan = " + huffmanCheckSum);
                    dequantize_sample(this.ro[channel], channel, granule, sbi);
                }

                stereo(frame, granule, sbi, frame.getModeExtension());

                // SEBASTIAN shall we support downmix?
                //                if ((which_channels == OutputChannels.DOWNMIX_CHANNELS) && (channelInfos > 1)) {
                //                    do_downmix();
                //                }

                for (channel = 0; channel < frame.getChannels(); channel++) {
                    final GranuleSideInfo granuleInfo = this.sideInfo.channelInfos[channel].granuleInfos[granule];
                    reorder(this.lr[channel], granuleInfo, sbi, frame);
                    antialias(granuleInfo);
                    hybrid(granuleInfo, channel);

                    for (sb18 = 18; sb18 < 576; sb18 += 36) {
                        for (ss = 1; ss < SSLIMIT; ss += 2) {
                            this.out_1d[sb18 + ss] = -this.out_1d[sb18 + ss];
                        }
                    }

                    for (ss = 0; ss < SSLIMIT; ss++) { // Polyphase synthesis
                        sb = 0;
                        for (sb18 = 0; sb18 < 576; sb18 += 18) {
                            this.samples[channel][sb] = this.out_1d[sb18 + ss];
                            //filter1.input_sample(out_1d[sb18+ss], sb);
                            sb++;
                        }
                        this.filters[channel].calculatePCMSamples(this.samples[channel], this.decoderResult, channel);
                    }
                } // channelInfos
            } // granule
            return this.decoderResult;
        } catch (final IOException e) {
            if (LayerIIIDecoder.LOG.isDebugEnabled()) {
                LayerIIIDecoder.LOG.debug(e.getLocalizedMessage(), e);
            }
            return PCMDecoderResult.UNDEFINED;
        }
    }

    /**
     * @param channel {@code int}
     * @param granule {@code int}
     */
    private void decodeScaleFactors(final int channel, final int granule) {
        int sfb, window;
        final GranuleSideInfo granuleInfo = this.sideInfo.channelInfos[channel].granuleInfos[granule];
        final int scale_comp = granuleInfo.scalefac_compress;
        final int length0 = slen[0][scale_comp];
        final int length1 = slen[1][scale_comp];

        if (granuleInfo.isWindowSwitching && (granuleInfo.blockType == 2)) {
            if (granuleInfo.isMixedBlock) { // MIXED
                for (sfb = 0; sfb < 8; sfb++) {
                    this.scalefac[channel].l[sfb] = this.reservoir.hgetbits(slen[0][granuleInfo.scalefac_compress]);
                }
                for (sfb = 3; sfb < 6; sfb++) {
                    for (window = 0; window < 3; window++) {
                        this.scalefac[channel].s[window][sfb] = this.reservoir
                                .hgetbits(slen[0][granuleInfo.scalefac_compress]);
                    }
                }
                for (sfb = 6; sfb < 12; sfb++) {
                    for (window = 0; window < 3; window++) {
                        this.scalefac[channel].s[window][sfb] = this.reservoir
                                .hgetbits(slen[1][granuleInfo.scalefac_compress]);
                    }
                }
                for (sfb = 12, window = 0; window < 3; window++) {
                    this.scalefac[channel].s[window][sfb] = 0;
                }

            } else { // SHORT

                this.scalefac[channel].s[0][0] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[1][0] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[2][0] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[0][1] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[1][1] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[2][1] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[0][2] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[1][2] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[2][2] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[0][3] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[1][3] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[2][3] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[0][4] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[1][4] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[2][4] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[0][5] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[1][5] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[2][5] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].s[0][6] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[1][6] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[2][6] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[0][7] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[1][7] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[2][7] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[0][8] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[1][8] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[2][8] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[0][9] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[1][9] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[2][9] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[0][10] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[1][10] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[2][10] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[0][11] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[1][11] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[2][11] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].s[0][12] = 0;
                this.scalefac[channel].s[1][12] = 0;
                this.scalefac[channel].s[2][12] = 0;
            } // SHORT

        } else { // LONG types 0,1,3

            if ((this.sideInfo.channelInfos[channel].scfsi[0] == 0) || (granule == 0)) {
                this.scalefac[channel].l[0] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].l[1] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].l[2] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].l[3] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].l[4] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].l[5] = this.reservoir.hgetbits(length0);
            }
            if ((this.sideInfo.channelInfos[channel].scfsi[1] == 0) || (granule == 0)) {
                this.scalefac[channel].l[6] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].l[7] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].l[8] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].l[9] = this.reservoir.hgetbits(length0);
                this.scalefac[channel].l[10] = this.reservoir.hgetbits(length0);
            }
            if ((this.sideInfo.channelInfos[channel].scfsi[2] == 0) || (granule == 0)) {
                this.scalefac[channel].l[11] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].l[12] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].l[13] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].l[14] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].l[15] = this.reservoir.hgetbits(length1);
            }
            if ((this.sideInfo.channelInfos[channel].scfsi[3] == 0) || (granule == 0)) {
                this.scalefac[channel].l[16] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].l[17] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].l[18] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].l[19] = this.reservoir.hgetbits(length1);
                this.scalefac[channel].l[20] = this.reservoir.hgetbits(length1);
            }

            this.scalefac[channel].l[21] = 0;
            this.scalefac[channel].l[22] = 0;
        }
    }

    /**
     * dequantize_sample.
     * @param xr
     * @param channel {@code int}
     * @param granule {@code int}
     * @param sbi {@link SBI}
    */
    private void dequantize_sample(final float xr[][], final int channel, final int granule, final SBI sbi) {
        final GranuleSideInfo granuleInfo = this.sideInfo.channelInfos[channel].granuleInfos[granule];
        int cb = 0;
        int next_cb_boundary;
        int cb_begin = 0;
        int cb_width = 0;
        int index = 0, t_index, j;
        float globalGain;
        final float[][] xr_1d = xr;

        // choose correct scalefactor band per block type, initalize boundary

        if (granuleInfo.isWindowSwitching && (granuleInfo.blockType == 2)) {
            if (granuleInfo.isMixedBlock) {
                next_cb_boundary = sbi.l[1]; // LONG blocks: 0,1,3
            } else {
                cb_width = sbi.s[1];
                next_cb_boundary = (cb_width << 2) - cb_width;
                cb_begin = 0;
            }
        } else {
            next_cb_boundary = sbi.l[1]; // LONG blocks: 0,1,3
        }

        // Compute overall (global) scaling.
        globalGain = (float) Math.pow(2.0, (0.25 * (granuleInfo.globalGain - 210.0)));

        for (j = 0; j < this.nonzero[channel]; j++) {
            // Modif E.B 02/22/99
            final int reste = j % SSLIMIT;
            final int quotien = (j - reste) / SSLIMIT;
            if (this.is_1d[j] == 0) {
                xr_1d[quotien][reste] = 0.0f;
            } else {
                final int abv = this.is_1d[j];
                // Pow Array fix (11/17/04)
                if (abv < t_43.length) {
                    if (this.is_1d[j] > 0) {
                        xr_1d[quotien][reste] = globalGain * t_43[abv];
                    } else {
                        if (-abv < t_43.length) {
                            xr_1d[quotien][reste] = -globalGain * t_43[-abv];
                        } else {
                            xr_1d[quotien][reste] = -globalGain * (float) Math.pow(-abv, d43);
                        }
                    }
                } else {
                    if (this.is_1d[j] > 0) {
                        xr_1d[quotien][reste] = globalGain * (float) Math.pow(abv, d43);
                    } else {
                        xr_1d[quotien][reste] = -globalGain * (float) Math.pow(-abv, d43);
                    }
                }
            }
        }

        // apply formula per block type
        for (j = 0; j < this.nonzero[channel]; j++) {
            // Modif E.B 02/22/99
            final int reste = j % SSLIMIT;
            final int quotien = (j - reste) / SSLIMIT;

            if (index == next_cb_boundary) { /* Adjust critical band boundary */
                if (granuleInfo.isWindowSwitching && (granuleInfo.blockType == 2)) {
                    if (granuleInfo.isMixedBlock) {

                        if (index == sbi.l[8]) {
                            next_cb_boundary = sbi.s[4];
                            next_cb_boundary = (next_cb_boundary << 2) - next_cb_boundary;
                            cb = 3;
                            cb_width = sbi.s[4] - sbi.s[3];

                            cb_begin = sbi.s[3];
                            cb_begin = (cb_begin << 2) - cb_begin;

                        } else if (index < sbi.l[8]) {
                            next_cb_boundary = sbi.l[(++cb) + 1];

                        } else {
                            next_cb_boundary = sbi.s[(++cb) + 1];
                            next_cb_boundary = (next_cb_boundary << 2) - next_cb_boundary;

                            cb_begin = sbi.s[cb];
                            cb_width = sbi.s[cb + 1] - cb_begin;
                            cb_begin = (cb_begin << 2) - cb_begin;
                        }
                    } else {
                        next_cb_boundary = sbi.s[(++cb) + 1];
                        next_cb_boundary = (next_cb_boundary << 2) - next_cb_boundary;

                        cb_begin = sbi.s[cb];
                        cb_width = sbi.s[cb + 1] - cb_begin;
                        cb_begin = (cb_begin << 2) - cb_begin;
                    }

                } else { // long blocks
                    next_cb_boundary = sbi.l[(++cb) + 1];
                }
            }

            // Do long/short dependent scaling operations

            if (granuleInfo.isWindowSwitching && (((granuleInfo.blockType == 2) && granuleInfo.isMixedBlock)
                    || ((granuleInfo.blockType == 2) && granuleInfo.isMixedBlock && (j >= 36)))) {

                t_index = (index - cb_begin) / cb_width;
                int idx = this.scalefac[channel].s[t_index][cb] << granuleInfo.scalefac_scale;
                idx += (granuleInfo.subblock_gain[t_index] << 2);

                xr_1d[quotien][reste] *= two_to_negative_half_pow[idx];

            } else { // LONG block types 0,1,3 & 1st 2 subbands of switched blocks
                int idx = this.scalefac[channel].l[cb];

                if (granuleInfo.preflag != 0) {
                    idx += pretab[cb];
                }

                idx = idx << granuleInfo.scalefac_scale;
                xr_1d[quotien][reste] *= two_to_negative_half_pow[idx];
            }
            index++;
        }

        for (j = this.nonzero[channel]; j < 576; j++) {
            // Modif E.B 02/22/99
            int reste = j % SSLIMIT;
            int quotien = (j - reste) / SSLIMIT;
            if (reste < 0) {
                reste = 0;
            }
            if (quotien < 0) {
                quotien = 0;
            }
            xr_1d[quotien][reste] = 0.0f;
        }

        return;
    }

    /**
     * get_LSF_scale_data.
     * @param frame {@link MPEGAudioFrame}
     * @param channel {@code int}
     * @param granule {@code int}
     */
    private void get_LSF_scale_data(final MPEGAudioFrame frame, final int channel, final int granule) {

        final GranuleSideInfo gi = (this.sideInfo.channelInfos[channel].granuleInfos[granule]);
        int int_scalefac_comp;
        int m;
        int blocktypenumber;
        int blocknumber = 0;

        if (gi.blockType == 2) {
            if (!gi.isMixedBlock) {
                blocktypenumber = 1;
            } else if (gi.isMixedBlock) {
                blocktypenumber = 2;
            } else {
                blocktypenumber = 0;
            }
        } else {
            blocktypenumber = 0;
        }

        if (!(((frame.getModeExtension() == ModeExtension.IntensityStereo_On__MSStereo_Off)
                || (frame.getModeExtension() == ModeExtension.IntensityStereo_On__MSStereo_On)) && (channel == 1))) {

            if (gi.scalefac_compress < 400) {
                this.new_slen[0] = (gi.scalefac_compress >>> 4) / 5;
                this.new_slen[1] = (gi.scalefac_compress >>> 4) % 5;
                this.new_slen[2] = (gi.scalefac_compress & 0xF) >>> 2;
                this.new_slen[3] = (gi.scalefac_compress & 3);
                this.sideInfo.channelInfos[channel].granuleInfos[granule].preflag = 0;
                blocknumber = 0;

            } else if (gi.scalefac_compress < 500) {
                this.new_slen[0] = ((gi.scalefac_compress - 400) >>> 2) / 5;
                this.new_slen[1] = ((gi.scalefac_compress - 400) >>> 2) % 5;
                this.new_slen[2] = (gi.scalefac_compress - 400) & 3;
                this.new_slen[3] = 0;
                this.sideInfo.channelInfos[channel].granuleInfos[granule].preflag = 0;
                blocknumber = 1;

            } else if (gi.scalefac_compress < 512) {
                this.new_slen[0] = (gi.scalefac_compress - 500) / 3;
                this.new_slen[1] = (gi.scalefac_compress - 500) % 3;
                this.new_slen[2] = 0;
                this.new_slen[3] = 0;
                this.sideInfo.channelInfos[channel].granuleInfos[granule].preflag = 1;
                blocknumber = 2;
            }
        }

        if ((((frame.getModeExtension() == ModeExtension.IntensityStereo_On__MSStereo_Off)
                || (frame.getModeExtension() == ModeExtension.IntensityStereo_On__MSStereo_On)) && (channel == 1))) {
            int_scalefac_comp = gi.scalefac_compress >>> 1;

            if (int_scalefac_comp < 180) {
                this.new_slen[0] = int_scalefac_comp / 36;
                this.new_slen[1] = (int_scalefac_comp % 36) / 6;
                this.new_slen[2] = (int_scalefac_comp % 36) % 6;
                this.new_slen[3] = 0;
                this.sideInfo.channelInfos[channel].granuleInfos[granule].preflag = 0;
                blocknumber = 3;
            } else if (int_scalefac_comp < 244) {
                this.new_slen[0] = ((int_scalefac_comp - 180) & 0x3F) >>> 4;
                this.new_slen[1] = ((int_scalefac_comp - 180) & 0xF) >>> 2;
                this.new_slen[2] = (int_scalefac_comp - 180) & 3;
                this.new_slen[3] = 0;
                this.sideInfo.channelInfos[channel].granuleInfos[granule].preflag = 0;
                blocknumber = 4;
            } else if (int_scalefac_comp < 255) {
                this.new_slen[0] = (int_scalefac_comp - 244) / 3;
                this.new_slen[1] = (int_scalefac_comp - 244) % 3;
                this.new_slen[2] = 0;
                this.new_slen[3] = 0;
                this.sideInfo.channelInfos[channel].granuleInfos[granule].preflag = 0;
                blocknumber = 5;
            }
        }

        for (int x = 0; x < 45; x++) {
            this.scalefac_buffer[x] = 0;
        }

        m = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < nr_of_sfb_block[blocknumber][blocktypenumber][i]; j++) {
                this.scalefac_buffer[m] = (this.new_slen[i] == 0) ? 0 : this.reservoir.hgetbits(this.new_slen[i]);
                m++;

            } // for (unint32 j ...
        } // for (uint32 i ...
    }

    /**
     * get_LSF_scale_factors.
     * @param frame {@link MPEGAudioFrame}
     * @param channel {@code int}
     * @param granule {@code int}
     */
    private void get_LSF_scale_factors(final MPEGAudioFrame frame, final int channel, final int granule) {
        int m = 0;
        int sfb, window;
        final GranuleSideInfo granuleInfo = (this.sideInfo.channelInfos[channel].granuleInfos[granule]);

        get_LSF_scale_data(frame, channel, granule);

        if (granuleInfo.isWindowSwitching && (granuleInfo.blockType == 2)) {
            if (granuleInfo.isMixedBlock) { // MIXED
                for (sfb = 0; sfb < 8; sfb++) {
                    this.scalefac[channel].l[sfb] = this.scalefac_buffer[m];
                    m++;
                }
                for (sfb = 3; sfb < 12; sfb++) {
                    for (window = 0; window < 3; window++) {
                        this.scalefac[channel].s[window][sfb] = this.scalefac_buffer[m];
                        m++;
                    }
                }
                for (window = 0; window < 3; window++) {
                    this.scalefac[channel].s[window][12] = 0;
                }

            } else { // SHORT

                for (sfb = 0; sfb < 12; sfb++) {
                    for (window = 0; window < 3; window++) {
                        this.scalefac[channel].s[window][sfb] = this.scalefac_buffer[m];
                        m++;
                    }
                }

                for (window = 0; window < 3; window++) {
                    this.scalefac[channel].s[window][12] = 0;
                }
            }
        } else { // LONG types 0,1,3

            for (sfb = 0; sfb < 21; sfb++) {
                this.scalefac[channel].l[sfb] = this.scalefac_buffer[m];
                m++;
            }
            this.scalefac[channel].l[21] = 0; // Jeff
            this.scalefac[channel].l[22] = 0;
        }
    }

    /**
     * huffmanDecode.
     * @param channel {@code int}
     * @param granule {@code int}
     * @param sbi {@link SBI}
     * @param samplingRate {@link SamplingRate}
     */
    private void huffmanDecode(final int channel, final int granule, final SBI sbi, final SamplingRate samplingRate) {

        final int part2_3_end = this.part2_start
                + this.sideInfo.channelInfos[channel].granuleInfos[granule].part2_3_length;
        int num_bits;
        int region1Start;
        int region2Start;
        int index;

        int buf, buf1;

        HuffmanDecoderTable h;

        // Find region boundary for short block case

        if ((this.sideInfo.channelInfos[channel].granuleInfos[granule].isWindowSwitching)
                && (this.sideInfo.channelInfos[channel].granuleInfos[granule].blockType == 2)) {

            // Region2.
            //MS: Extrahandling for 8KHZ
            region1Start = samplingRate.equals(SamplingRate._8000) ? 72 : 36; // sfb[9/3]*3=36 or in case 8KHZ = 72
            region2Start = 576; // No Region2 for short block case

        } else { // Find region boundary for long block case
            buf = this.sideInfo.channelInfos[channel].granuleInfos[granule].region0_count + 1;
            buf1 = buf + this.sideInfo.channelInfos[channel].granuleInfos[granule].region1_count + 1;
            if (buf1 > (sbi.l.length - 1)) {
                buf1 = sbi.l.length - 1;
            }
            region1Start = sbi.l[buf];
            region2Start = sbi.l[buf1]; /* MI */
        }

        index = 0;
        // Read bigvalues area
        for (int i = 0; i < (this.sideInfo.channelInfos[channel].granuleInfos[granule].big_values << 1); i += 2) {
            if (i < region1Start) {
                h = HuffmanDecoderTable.ht[this.sideInfo.channelInfos[channel].granuleInfos[granule].table_select[0]];
            } else if (i < region2Start) {
                h = HuffmanDecoderTable.ht[this.sideInfo.channelInfos[channel].granuleInfos[granule].table_select[1]];
            } else {
                h = HuffmanDecoderTable.ht[this.sideInfo.channelInfos[channel].granuleInfos[granule].table_select[2]];
            }

            HuffmanDecoderTable.decode(h, this.hv, this.reservoir);

            this.is_1d[index++] = this.hv.x;
            this.is_1d[index++] = this.hv.y;

            this.huffmanCheckSum = this.huffmanCheckSum + this.hv.x + this.hv.y;
            // System.out.println("x = "+x[0]+" y = "+y[0]);
        }

        // Read count1 area
        h = HuffmanDecoderTable.ht[this.sideInfo.channelInfos[channel].granuleInfos[granule].count1table_select + 32];
        num_bits = this.reservoir.hsstell();

        while ((num_bits < part2_3_end) && (index < 576)) {
            HuffmanDecoderTable.decode(h, this.hv, this.reservoir);

            this.is_1d[index++] = this.hv.v;
            this.is_1d[index++] = this.hv.w;
            this.is_1d[index++] = this.hv.x;
            this.is_1d[index++] = this.hv.y;
            this.huffmanCheckSum = this.huffmanCheckSum + this.hv.v + this.hv.w + this.hv.x + this.hv.y;
            num_bits = this.reservoir.hsstell();
        }

        if (num_bits > part2_3_end) {
            this.reservoir.rewindNbits(num_bits - part2_3_end);
            index -= 4;
        }

        num_bits = this.reservoir.hsstell();

        // Dismiss stuffing bits
        if (num_bits < part2_3_end) {
            this.reservoir.hgetbits(part2_3_end - num_bits);
        }

        // Zero out rest

        if (index < 576) {
            this.nonzero[channel] = index;
        } else {
            this.nonzero[channel] = 576;
        }

        if (index < 0) {
            index = 0;
        }

        // may not be necessary
        for (; index < 576; index++) {
            this.is_1d[index] = 0;
        }
    }

    /**
     * hybrid.
     * @param granuleInfo {@link GranuleSideInfo}
     * @param channel {@code int}
     */
    private void hybrid(final GranuleSideInfo granuleInfo, final int channel) {
        int bt;
        int sb18;
        float[] tsOut;

        float[][] prvblk;

        for (sb18 = 0; sb18 < 576; sb18 += 18) {
            bt = (granuleInfo.isWindowSwitching && (granuleInfo.isMixedBlock) && (sb18 < 36)) ? 0
                    : granuleInfo.blockType;

            tsOut = this.out_1d;
            // Modif E.B 02/22/99
            for (int cc = 0; cc < 18; cc++) {
                this.tsOutCopy[cc] = tsOut[cc + sb18];
            }

            InvMDCT.inv_mdct(this.tsOutCopy, this.rawout, bt);

            for (int cc = 0; cc < 18; cc++) {
                tsOut[cc + sb18] = this.tsOutCopy[cc];
                // Fin Modif
            }

            // overlap addition
            prvblk = this.prevblck;

            tsOut[0 + sb18] = this.rawout[0] + prvblk[channel][sb18 + 0];
            prvblk[channel][sb18 + 0] = this.rawout[18];
            tsOut[1 + sb18] = this.rawout[1] + prvblk[channel][sb18 + 1];
            prvblk[channel][sb18 + 1] = this.rawout[19];
            tsOut[2 + sb18] = this.rawout[2] + prvblk[channel][sb18 + 2];
            prvblk[channel][sb18 + 2] = this.rawout[20];
            tsOut[3 + sb18] = this.rawout[3] + prvblk[channel][sb18 + 3];
            prvblk[channel][sb18 + 3] = this.rawout[21];
            tsOut[4 + sb18] = this.rawout[4] + prvblk[channel][sb18 + 4];
            prvblk[channel][sb18 + 4] = this.rawout[22];
            tsOut[5 + sb18] = this.rawout[5] + prvblk[channel][sb18 + 5];
            prvblk[channel][sb18 + 5] = this.rawout[23];
            tsOut[6 + sb18] = this.rawout[6] + prvblk[channel][sb18 + 6];
            prvblk[channel][sb18 + 6] = this.rawout[24];
            tsOut[7 + sb18] = this.rawout[7] + prvblk[channel][sb18 + 7];
            prvblk[channel][sb18 + 7] = this.rawout[25];
            tsOut[8 + sb18] = this.rawout[8] + prvblk[channel][sb18 + 8];
            prvblk[channel][sb18 + 8] = this.rawout[26];
            tsOut[9 + sb18] = this.rawout[9] + prvblk[channel][sb18 + 9];
            prvblk[channel][sb18 + 9] = this.rawout[27];
            tsOut[10 + sb18] = this.rawout[10] + prvblk[channel][sb18 + 10];
            prvblk[channel][sb18 + 10] = this.rawout[28];
            tsOut[11 + sb18] = this.rawout[11] + prvblk[channel][sb18 + 11];
            prvblk[channel][sb18 + 11] = this.rawout[29];
            tsOut[12 + sb18] = this.rawout[12] + prvblk[channel][sb18 + 12];
            prvblk[channel][sb18 + 12] = this.rawout[30];
            tsOut[13 + sb18] = this.rawout[13] + prvblk[channel][sb18 + 13];
            prvblk[channel][sb18 + 13] = this.rawout[31];
            tsOut[14 + sb18] = this.rawout[14] + prvblk[channel][sb18 + 14];
            prvblk[channel][sb18 + 14] = this.rawout[32];
            tsOut[15 + sb18] = this.rawout[15] + prvblk[channel][sb18 + 15];
            prvblk[channel][sb18 + 15] = this.rawout[33];
            tsOut[16 + sb18] = this.rawout[16] + prvblk[channel][sb18 + 16];
            prvblk[channel][sb18 + 16] = this.rawout[34];
            tsOut[17 + sb18] = this.rawout[17] + prvblk[channel][sb18 + 17];
            prvblk[channel][sb18 + 17] = this.rawout[35];
        }
    }

    /**
     * i_stereo_k_values.
     * @param is_pos_val {@code int}
     * @param io_type {@code int}
     * @param i {@code int}
     */
    private void i_stereo_k_values(final int is_pos_val, final int io_type, final int i) {
        if (is_pos_val == 0) {
            this.k[0][i] = 1.0f;
            this.k[1][i] = 1.0f;
        } else if ((is_pos_val & 1) != 0) {
            this.k[0][i] = L3Table.io[io_type][(is_pos_val + 1) >>> 1];
            this.k[1][i] = 1.0f;
        } else {
            this.k[0][i] = 1.0f;
            this.k[1][i] = L3Table.io[io_type][is_pos_val >>> 1];
        }
    }

    /**
     * reorder.
     * @param xr
     * @param granuleInfo {@link GranuleSideInfo}
     * @param ch
     * @param gr
     * @param sbi {@link SBI}
     * @param frame {@link MPEGAudioFrame}
     */
    private void reorder(final float xr[][], final GranuleSideInfo granuleInfo, final SBI sbi,
            final MPEGAudioFrame frame) {
        int freq, freq3;
        int index;
        int sfb, sfb_start, sfb_lines;
        int src_line, des_line;
        final float[][] xr_1d = xr;

        if (granuleInfo.isWindowSwitching && (granuleInfo.blockType == 2)) {

            for (index = 0; index < 576; index++) {
                this.out_1d[index] = 0.0f;
            }

            if (granuleInfo.isMixedBlock) {
                // NO REORDER FOR LOW 2 SUBBANDS
                for (index = 0; index < 36; index++) {
                    // Modif E.B 02/22/99
                    final int reste = index % SSLIMIT;
                    final int quotien = (index - reste) / SSLIMIT;
                    this.out_1d[index] = xr_1d[quotien][reste];
                }
                // REORDERING FOR REST SWITCHED SHORT
                /*for( sfb=3,sfb_start=sfBandIndex[sfreq].s[3],
                    sfb_lines=sfBandIndex[sfreq].s[4] - sfb_start;
                    sfb < 13; sfb++,sfb_start = sfBandIndex[sfreq].s[sfb],
                    sfb_lines = sfBandIndex[sfreq].s[sfb+1] - sfb_start )
                    {*/
                for (sfb = 3; sfb < 13; sfb++) {
                    sfb_start = sbi.s[sfb];
                    sfb_lines = sbi.s[sfb + 1] - sfb_start;

                    final int sfb_start3 = (sfb_start << 2) - sfb_start;

                    for (freq = 0, freq3 = 0; freq < sfb_lines; freq++, freq3 += 3) {

                        src_line = sfb_start3 + freq;
                        des_line = sfb_start3 + freq3;
                        // Modif E.B 02/22/99
                        int reste = src_line % SSLIMIT;
                        int quotien = (src_line - reste) / SSLIMIT;

                        this.out_1d[des_line] = xr_1d[quotien][reste];
                        src_line += sfb_lines;
                        des_line++;

                        reste = src_line % SSLIMIT;
                        quotien = (src_line - reste) / SSLIMIT;

                        this.out_1d[des_line] = xr_1d[quotien][reste];
                        src_line += sfb_lines;
                        des_line++;

                        reste = src_line % SSLIMIT;
                        quotien = (src_line - reste) / SSLIMIT;

                        this.out_1d[des_line] = xr_1d[quotien][reste];
                    }
                }

            } else { // pure short
                for (index = 0; index < 576; index++) {
                    final int j = reorder_table.get(frame.getVersion()).get(frame.getSamplingRate())[index];
                    final int reste = j % SSLIMIT;
                    final int quotien = (j - reste) / SSLIMIT;
                    this.out_1d[index] = xr_1d[quotien][reste];
                }
            }
        } else { // long blocks
            for (index = 0; index < 576; index++) {
                // Modif E.B 02/22/99
                final int reste = index % SSLIMIT;
                final int quotien = (index - reste) / SSLIMIT;
                this.out_1d[index] = xr_1d[quotien][reste];
            }
        }
    }

    /**
     * stereo.
     * @param frame {@link MPEGAudioFrame}
     * @param granule {@code int}
     * @param sbi {@link SBI}
     * @param modeExtension {@link ModeExtension}
     */
    private void stereo(final MPEGAudioFrame frame, final int granule, final SBI sbi,
            final ModeExtension modeExtension) {
        int sb, ss;

        if (frame.getChannels() == 1) { // mono , bypass xr[0][][] to lr[0][][]

            for (sb = 0; sb < SBLIMIT; sb++) {
                for (ss = 0; ss < SSLIMIT; ss += 3) {
                    this.lr[0][sb][ss] = this.ro[0][sb][ss];
                    this.lr[0][sb][ss + 1] = this.ro[0][sb][ss + 1];
                    this.lr[0][sb][ss + 2] = this.ro[0][sb][ss + 2];
                }
            }

        } else {
            final GranuleSideInfo granuleInfo = (this.sideInfo.channelInfos[0].granuleInfos[granule]);
            int sfb;
            int i;
            int lines, temp, temp2;

            final boolean msStereoOn = ((frame.getMode() == Mode.JointStereo) && modeExtension.isMSStereoOn());
            final boolean intensityStereoOn = ((frame.getMode() == Mode.JointStereo)
                    && modeExtension.isIntensityStereoOn());
            final boolean lsf = (((frame.getVersion() == Version.MPEG_2_LSF)
                    || (frame.getVersion() == Version.MPEG_2_5))); // SZD

            final int io_type = (granuleInfo.scalefac_compress & 1);

            // initialization

            for (i = 0; i < 576; i++) {
                this.is_pos[i] = 7;
                this.is_ratio[i] = 0.0f;
            }

            if (intensityStereoOn) {
                if (granuleInfo.isWindowSwitching && (granuleInfo.blockType == 2)) {
                    if (granuleInfo.isMixedBlock) {
                        int max_sfb = 0;

                        for (int j = 0; j < 3; j++) {
                            int sfbcnt;
                            sfbcnt = 2;
                            for (sfb = 12; sfb >= 3; sfb--) {
                                i = sbi.s[sfb];
                                lines = sbi.s[sfb + 1] - i;
                                i = (((i << 2) - i) + ((j + 1) * lines)) - 1;

                                while (lines > 0) {
                                    if (this.ro[1][i / 18][i % 18] != 0.0f) {
                                        // MDM: in java, array access is very slow.
                                        // Is quicker to compute div and mod values.
                                        //if (ro[1][ss_div[i]][ss_mod[i]] != 0.0f) {
                                        sfbcnt = sfb;
                                        sfb = -10;
                                        lines = -10;
                                    }

                                    lines--;
                                    i--;

                                } // while (lines > 0)

                            } // for (sfb=12 ...
                            sfb = sfbcnt + 1;

                            if (sfb > max_sfb) {
                                max_sfb = sfb;
                            }

                            while (sfb < 12) {
                                temp = sbi.s[sfb];
                                sb = sbi.s[sfb + 1] - temp;
                                i = ((temp << 2) - temp) + (j * sb);

                                for (; sb > 0; sb--) {
                                    this.is_pos[i] = this.scalefac[1].s[j][sfb];
                                    if (this.is_pos[i] != 7) {
                                        if (lsf) {
                                            i_stereo_k_values(this.is_pos[i], io_type, i);
                                        } else {
                                            this.is_ratio[i] = L3Table.TAN12[this.is_pos[i]];
                                        }
                                    }

                                    i++;
                                } // for (; sb>0...
                                sfb++;
                            } // while (sfb < 12)
                            sfb = sbi.s[10];
                            sb = sbi.s[11] - sfb;
                            sfb = ((sfb << 2) - sfb) + (j * sb);
                            temp = sbi.s[11];
                            sb = sbi.s[12] - temp;
                            i = ((temp << 2) - temp) + (j * sb);

                            for (; sb > 0; sb--) {
                                this.is_pos[i] = this.is_pos[sfb];

                                if (lsf) {
                                    this.k[0][i] = this.k[0][sfb];
                                    this.k[1][i] = this.k[1][sfb];
                                } else {
                                    this.is_ratio[i] = this.is_ratio[sfb];
                                }
                                i++;
                            } // for (; sb > 0 ...
                        }
                        if (max_sfb <= 3) {
                            i = 2;
                            ss = 17;
                            sb = -1;
                            while (i >= 0) {
                                if (this.ro[1][i][ss] != 0.0f) {
                                    sb = (i << 4) + (i << 1) + ss;
                                    i = -1;
                                } else {
                                    ss--;
                                    if (ss < 0) {
                                        i--;
                                        ss = 17;
                                    }
                                } // if (ro ...
                            } // while (i>=0)
                            i = 0;
                            while (sbi.l[i] <= sb) {
                                i++;
                            }
                            sfb = i;
                            i = sbi.l[i];
                            for (; sfb < 8; sfb++) {
                                sb = sbi.l[sfb + 1] - sbi.l[sfb];
                                for (; sb > 0; sb--) {
                                    this.is_pos[i] = this.scalefac[1].l[sfb];
                                    if (this.is_pos[i] != 7) {
                                        if (lsf) {
                                            i_stereo_k_values(this.is_pos[i], io_type, i);
                                        } else {
                                            this.is_ratio[i] = L3Table.TAN12[this.is_pos[i]];
                                        }
                                    }
                                    i++;
                                } // for (; sb>0 ...
                            } // for (; sfb<8 ...
                        } // for (j=0 ...
                    } else { // if (gr_info.mixed_block_flag)
                        for (int j = 0; j < 3; j++) {
                            int sfbcnt;
                            sfbcnt = -1;
                            for (sfb = 12; sfb >= 0; sfb--) {
                                temp = sbi.s[sfb];
                                lines = sbi.s[sfb + 1] - temp;
                                i = (((temp << 2) - temp) + ((j + 1) * lines)) - 1;

                                while (lines > 0) {
                                    if (this.ro[1][i / 18][i % 18] != 0.0f) {
                                        // MDM: in java, array access is very slow.
                                        // Is quicker to compute div and mod values.
                                        //if (ro[1][ss_div[i]][ss_mod[i]] != 0.0f) {
                                        sfbcnt = sfb;
                                        sfb = -10;
                                        lines = -10;
                                    }
                                    lines--;
                                    i--;
                                } // while (lines > 0) */

                            } // for (sfb=12 ...
                            sfb = sfbcnt + 1;
                            while (sfb < 12) {
                                temp = sbi.s[sfb];
                                sb = sbi.s[sfb + 1] - temp;
                                i = ((temp << 2) - temp) + (j * sb);
                                for (; sb > 0; sb--) {
                                    this.is_pos[i] = this.scalefac[1].s[j][sfb];
                                    if (this.is_pos[i] != 7) {
                                        if (lsf) {
                                            i_stereo_k_values(this.is_pos[i], io_type, i);
                                        } else {
                                            this.is_ratio[i] = L3Table.TAN12[this.is_pos[i]];
                                        }
                                    }
                                    i++;
                                } // for (; sb>0 ...
                                sfb++;
                            } // while (sfb<12)

                            temp = sbi.s[10];
                            temp2 = sbi.s[11];
                            sb = temp2 - temp;
                            sfb = ((temp << 2) - temp) + (j * sb);
                            sb = sbi.s[12] - temp2;
                            i = ((temp2 << 2) - temp2) + (j * sb);

                            for (; sb > 0; sb--) {
                                this.is_pos[i] = this.is_pos[sfb];

                                if (lsf) {
                                    this.k[0][i] = this.k[0][sfb];
                                    this.k[1][i] = this.k[1][sfb];
                                } else {
                                    this.is_ratio[i] = this.is_ratio[sfb];
                                }
                                i++;
                            } // for (; sb>0 ...
                        } // for (sfb=12
                    } // for (j=0 ...
                } else { // if (gr_info.window_switching_flag ...
                    i = 31;
                    ss = 17;
                    sb = 0;
                    while (i >= 0) {
                        if (this.ro[1][i][ss] != 0.0f) {
                            sb = (i << 4) + (i << 1) + ss;
                            i = -1;
                        } else {
                            ss--;
                            if (ss < 0) {
                                i--;
                                ss = 17;
                            }
                        }
                    }
                    i = 0;
                    while (sbi.l[i] <= sb) {
                        i++;
                    }

                    sfb = i;
                    i = sbi.l[i];
                    for (; sfb < 21; sfb++) {
                        sb = sbi.l[sfb + 1] - sbi.l[sfb];
                        for (; sb > 0; sb--) {
                            this.is_pos[i] = this.scalefac[1].l[sfb];
                            if (this.is_pos[i] != 7) {
                                if (lsf) {
                                    i_stereo_k_values(this.is_pos[i], io_type, i);
                                } else {
                                    this.is_ratio[i] = L3Table.TAN12[this.is_pos[i]];
                                }
                            }
                            i++;
                        }
                    }
                    sfb = sbi.l[20];
                    for (sb = 576 - sbi.l[21]; (sb > 0) && (i < 576); sb--) {
                        this.is_pos[i] = this.is_pos[sfb]; // error here : i >=576

                        if (lsf) {
                            this.k[0][i] = this.k[0][sfb];
                            this.k[1][i] = this.k[1][sfb];
                        } else {
                            this.is_ratio[i] = this.is_ratio[sfb];
                        }
                        i++;
                    } // if (gr_info.mixed_block_flag)
                } // if (gr_info.window_switching_flag ...
            } // if (i_stereo)

            i = 0;
            for (sb = 0; sb < SBLIMIT; sb++) {
                for (ss = 0; ss < SSLIMIT; ss++) {
                    if (this.is_pos[i] == 7) {
                        if (msStereoOn) {
                            this.lr[0][sb][ss] = (this.ro[0][sb][ss] + this.ro[1][sb][ss]) * 0.707106781f;
                            this.lr[1][sb][ss] = (this.ro[0][sb][ss] - this.ro[1][sb][ss]) * 0.707106781f;
                        } else {
                            this.lr[0][sb][ss] = this.ro[0][sb][ss];
                            this.lr[1][sb][ss] = this.ro[1][sb][ss];
                        }
                    } else if (intensityStereoOn) {

                        if (lsf) {
                            this.lr[0][sb][ss] = this.ro[0][sb][ss] * this.k[0][i];
                            this.lr[1][sb][ss] = this.ro[0][sb][ss] * this.k[1][i];
                        } else {
                            this.lr[1][sb][ss] = this.ro[0][sb][ss] / (1 + this.is_ratio[i]);
                            this.lr[0][sb][ss] = this.lr[1][sb][ss] * this.is_ratio[i];
                        }
                    }
                    i++;
                }
            }
        } // channelInfos == 2
    }

}
