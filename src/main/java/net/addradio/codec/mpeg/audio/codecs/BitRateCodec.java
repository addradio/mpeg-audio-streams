/**
 * Class:    BitRateCodec<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: BitRateCodec.java<br/>
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

import net.addradio.codec.mpeg.audio.model.BitRate;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * BitRateCodec.
 *
 * <pre>
 *  ---------------------------------------------------------------------------
 * | Bitrate | MPEG-1, | MPEG-1,  | MPEG-1,   | MPEG-2, | MPEG-2,  | MPEG-2,   |
 * | value   | layer I | layer II | layer III | layer I | layer II | layer III |
 * |---------------------------------------------------------------------------|
 * | 0b0000  | free    | free     | free      | free    | free     | free      |
 * |---------------------------------------------------------------------------|
 * | 0b0001  | 32      | 32       | 32        | 32      | 32       | 8         |
 * |---------------------------------------------------------------------------|
 * | 0b0010  | 64      | 48       | 40        | 64      | 48       | 16        |
 * |---------------------------------------------------------------------------|
 * | 0b0011  | 96      | 56       | 48        | 96      | 56       | 24        |
 * |---------------------------------------------------------------------------|
 * | 0b0100  | 128     | 64       | 56        | 128     | 64       | 32        |
 * |---------------------------------------------------------------------------|
 * | 0b0101  | 160     | 80       | 64        | 160     | 80       | 64        |
 * |---------------------------------------------------------------------------|
 * | 0b0110  | 192     | 96       | 80        | 192     | 96       | 80        |
 * |---------------------------------------------------------------------------|
 * | 0b0111  | 224     | 112      | 96        | 224     | 112      | 56        |
 * |---------------------------------------------------------------------------|
 * | 0b1000  | 256     | 128      | 112       | 256     | 128      | 64        |
 * |---------------------------------------------------------------------------|
 * | 0b1001  | 288     | 160      | 128       | 288     | 160      | 128       |
 * |---------------------------------------------------------------------------|
 * | 0b1010  | 320     | 192      | 160       | 320     | 192      | 160       |
 * |---------------------------------------------------------------------------|
 * | 0b1011  | 352     | 224      | 192       | 352     | 224      | 112       |
 * |---------------------------------------------------------------------------|
 * | 0b1100  | 384     | 256      | 224       | 384     | 256      | 128       |
 * |---------------------------------------------------------------------------|
 * | 0b1101  | 416     | 320      | 256       | 416     | 320      | 256       |
 * |---------------------------------------------------------------------------|
 * | 0b1110  | 448     | 384      | 320       | 448     | 384      | 320       |
 * |---------------------------------------------------------------------------|
 * | 0b1111  | rsrvd.  | rsrvd.   | rsrvd.    | rsrvd.  | rsrvd.   | rsrvd.    |
 *  ---------------------------------------------------------------------------
 * </pre>
 * <ul>
 * <li>"free" means free format. If the correct fixed bitrate (such files cannot
 * use variable bitrate) is different than those presented in upper table it
 * must be determined by the application. This may be implemented only for
 * internal purposes since third party applications have no means to find out
 * correct bitrate. Howewer, this is not impossible to do but demands lot's of
 * efforts.</li>
 * <li>"rsrvd." reserved for future use</li>
 * </ul>
 */
public final class BitRateCodec {

    /**
     * decode.
     *
     * @param frame
     *            {@link MPEGAudioFrame}
     * @param value
     *            {@code int}
     * @return {@link BitRate}
     * @throws MPEGAudioCodecException
     *             if bit rate could not be decoded.
     */
    public static final BitRate decode(final MPEGAudioFrame frame, final int value) throws MPEGAudioCodecException {
        if (value > 15) {
            throw new MPEGAudioCodecException("bitrate has to be a flag value between 0 and 15 (incl.) but was [" //$NON-NLS-1$
                    + value + "]"); //$NON-NLS-1$
        }
        switch (frame.getLayer()) {
        case I:
            switch (value) {
            case 0b0000:
                return BitRate.free;
            case 0b0001:
                return BitRate._32;
            case 0b0010:
                return BitRate._64;
            case 0b0011:
                return BitRate._96;
            case 0b0100:
                return BitRate._128;
            case 0b0101:
                return BitRate._160;
            case 0b0110:
                return BitRate._192;
            case 0b0111:
                return BitRate._224;
            case 0b1000:
                return BitRate._256;
            case 0b1001:
                return BitRate._288;
            case 0b1010:
                return BitRate._320;
            case 0b1011:
                return BitRate._352;
            case 0b1100:
                return BitRate._384;
            case 0b1101:
                return BitRate._416;
            case 0b1110:
                return BitRate._448;
            case 0b1111:
                return BitRate.reserved;
            default:
                break;
            }
            break;
        case II:
            switch (value) {
            case 0b0000:
                return BitRate.free;
            case 0b0001:
                return BitRate._32;
            case 0b0010:
                return BitRate._48;
            case 0b0011:
                return BitRate._56;
            case 0b0100:
                return BitRate._64;
            case 0b0101:
                return BitRate._80;
            case 0b0110:
                return BitRate._96;
            case 0b0111:
                return BitRate._112;
            case 0b1000:
                return BitRate._128;
            case 0b1001:
                return BitRate._160;
            case 0b1010:
                return BitRate._192;
            case 0b1011:
                return BitRate._224;
            case 0b1100:
                return BitRate._256;
            case 0b1101:
                return BitRate._320;
            case 0b1110:
                return BitRate._384;
            case 0b1111:
                return BitRate.reserved;
            default:
                break;
            }
            break;
        case III:
            switch (frame.getVersion()) {
            case MPEG_1:
                switch (value) {
                case 0b0000:
                    return BitRate.free;
                case 0b0001:
                    return BitRate._32;
                case 0b0010:
                    return BitRate._40;
                case 0b0011:
                    return BitRate._48;
                case 0b0100:
                    return BitRate._56;
                case 0b0101:
                    return BitRate._64;
                case 0b0110:
                    return BitRate._80;
                case 0b0111:
                    return BitRate._96;
                case 0b1000:
                    return BitRate._112;
                case 0b1001:
                    return BitRate._128;
                case 0b1010:
                    return BitRate._160;
                case 0b1011:
                    return BitRate._192;
                case 0b1100:
                    return BitRate._224;
                case 0b1101:
                    return BitRate._256;
                case 0b1110:
                    return BitRate._320;
                case 0b1111:
                    return BitRate.reserved;
                default:
                    break;
                }
                break;
            case MPEG_2:
            case MPEG_2_5:
                // SEBASTIAN take care of this, table for mpeg2 can be
                // absolutely wrong
                switch (value) {
                case 0b0000:
                    return BitRate.free;
                case 0b0001:
                    return BitRate._8;
                case 0b0010:
                    return BitRate._16;
                case 0b0011:
                    return BitRate._24;
                case 0b0100:
                    return BitRate._32;
                case 0b0101:
                    return BitRate._40;
                case 0b0110:
                    return BitRate._48;
                case 0b0111:
                    return BitRate._56;
                case 0b1000:
                    return BitRate._64;
                case 0b1001:
                    return BitRate._80;
                case 0b1010:
                    return BitRate._96;
                case 0b1011:
                    return BitRate._112;
                case 0b1100:
                    return BitRate._128;
                case 0b1101:
                    return BitRate._144;
                case 0b1110:
                    return BitRate._160;
                case 0b1111:
                    return BitRate.reserved;
                default:
                    break;
                }
                break;
            case reserved:
            default:
                break;
            }
            break;
        case reserved:
        default:
            break;
        }
        throw new MPEGAudioCodecException(
                "Could not decode BitRate [valueToBeDecoded: " + value + ", frameDecodedSoFar: " + frame + "]."); //$NON-NLS-3$  //$NON-NLS-1$//$NON-NLS-2$
    }

    /**
     * encode.
     * @param frame {@link MPEGAudioFrame}
     * @return {@code int}
     * @throws MPEGAudioCodecException if model is ill-formed.
     */
    public static int encode(final MPEGAudioFrame frame) throws MPEGAudioCodecException {
        switch (frame.getLayer()) {
        case I:
            switch (frame.getBitRate()) {
            case free:
                return 0b0000;
            case _32:
                return 0b0001;
            case _64:
                return 0b0010;
            case _96:
                return 0b0011;
            case _128:
                return 0b0100;
            case _160:
                return 0b0101;
            case _192:
                return 0b0110;
            case _224:
                return 0b0111;
            case _256:
                return 0b1000;
            case _288:
                return 0b1001;
            case _320:
                return 0b1010;
            case _352:
                return 0b1011;
            case _384:
                return 0b1100;
            case _416:
                return 0b1101;
            case _448:
                return 0b1110;
            case reserved:
                return 0b1111;
            //$CASES-OMITTED$
            default:
                break;
            }
            break;
        case II:
            switch (frame.getBitRate()) {
            case free:
                return 0b0000;
            case _32:
                return 0b0001;
            case _48:
                return 0b0010;
            case _56:
                return 0b0011;
            case _64:
                return 0b0100;
            case _80:
                return 0b0101;
            case _96:
                return 0b0110;
            case _112:
                return 0b0111;
            case _128:
                return 0b1000;
            case _160:
                return 0b1001;
            case _192:
                return 0b1010;
            case _224:
                return 0b1011;
            case _256:
                return 0b1100;
            case _320:
                return 0b1101;
            case _384:
                return 0b1110;
            case reserved:
                return 0b1111;
            //$CASES-OMITTED$
            default:
                break;
            }
            break;
        case III:
            switch (frame.getVersion()) {
            case MPEG_1:
                switch (frame.getBitRate()) {
                case free:
                    return 0b0000;
                case _32:
                    return 0b0001;
                case _40:
                    return 0b0010;
                case _48:
                    return 0b0011;
                case _56:
                    return 0b0100;
                case _64:
                    return 0b0101;
                case _80:
                    return 0b0110;
                case _96:
                    return 0b0111;
                case _112:
                    return 0b1000;
                case _128:
                    return 0b1001;
                case _160:
                    return 0b1010;
                case _192:
                    return 0b1011;
                case _224:
                    return 0b1100;
                case _256:
                    return 0b1101;
                case _320:
                    return 0b1110;
                case reserved:
                    return 0b1111;
                //$CASES-OMITTED$
                default:
                    break;
                }
                break;
            case MPEG_2:
            case MPEG_2_5:
                // SEBASTIAN take care of this, table for mpeg2 can be
                // absolutely wrong
                switch (frame.getBitRate()) {
                case free:
                    return 0b0000;
                case _8:
                    return 0b0001;
                case _16:
                    return 0b0010;
                case _24:
                    return 0b0011;
                case _32:
                    return 0b0100;
                case _40:
                    return 0b0101;
                case _48:
                    return 0b0110;
                case _56:
                    return 0b0111;
                case _64:
                    return 0b1000;
                case _80:
                    return 0b1001;
                case _96:
                    return 0b1010;
                case _112:
                    return 0b1011;
                case _128:
                    return 0b1100;
                case _144:
                    return 0b1101;
                case _160:
                    return 0b1110;
                case reserved:
                    return 0b1111;
                //$CASES-OMITTED$
                default:
                    break;
                }
                break;
            case reserved:
            default:
                break;
            }
            break;
        case reserved:
        default:
            break;
        }
        throw new MPEGAudioCodecException("Could not encode frame [frame: " + frame + "]."); //$NON-NLS-1$//$NON-NLS-2$
    }

    /**
     * BitRateCodec constructor.
     */
    private BitRateCodec() {
    }

}
