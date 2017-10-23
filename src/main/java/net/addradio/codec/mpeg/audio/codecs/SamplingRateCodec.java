/**
 * Class:    SamplingRateCodec<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: SamplingRateCodec.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2012 - All rights reserved.
 */

package net.addradio.codec.mpeg.audio.codecs;

import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.model.SamplingRate;

/**
 * SamplingRateCodec
 */
public final class SamplingRateCodec {

    /**
     * decode.
     *
     * @param frame
     *            {@link MPEGAudioFrame}
     * @param value
     *            {@code int}
     * @return {@link SamplingRate}
     * @throws MPEGAudioCodecException
     *             if value could not be decoded.
     */
    public static final SamplingRate decode(final MPEGAudioFrame frame, final int value)
            throws MPEGAudioCodecException {
        switch (frame.getVersion()) {
        case MPEG_1:
            switch (value) {
            case 0b00:
                return SamplingRate._44100;
            case 0b01:
                return SamplingRate._48000;
            case 0b10:
                return SamplingRate._32000;
            case 0b11:
                return SamplingRate.reserved;
            default:
                break;
            }
            break;
        case MPEG_2:
            switch (value) {
            case 0b00:
                return SamplingRate._22050;
            case 0b01:
                return SamplingRate._24000;
            case 0b10:
                return SamplingRate._16000;
            case 0b11:
                return SamplingRate.reserved;
            default:
                break;
            }
            break;
        case MPEG_2_5:
            switch (value) {
            case 0b00:
                return SamplingRate._11025;
            case 0b01:
                return SamplingRate._12000;
            case 0b10:
                return SamplingRate._8000;
            case 0b11:
                return SamplingRate.reserved;
            default:
                break;
            }
            break;
        case reserved:
        default:
            break;

        }
        throw new MPEGAudioCodecException("Could not decode SamplingRate [valueToBeDecoded: " + value //$NON-NLS-1$
                + "]."); //$NON-NLS-1$
    }

    /**
     * encode.
     * @param frame {@link MPEGAudioFrame}
     * @return {@code int}
     */
    public static int encode(final MPEGAudioFrame frame) {
        // SEBASTIAN implement
        return 0;
    }

    /**
     * SamplingRateCodec constructor.
     */
    private SamplingRateCodec() {
    }

}
