/**
 * Class:    MPEGAudioFrameOutputStream<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: MPEGAudioFrameOutputStream.java<br/>
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

package net.addradio.codec.mpeg.audio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.mpeg.audio.codecs.BitRateCodec;
import net.addradio.codec.mpeg.audio.codecs.MPEGAudioCodecException;
import net.addradio.codec.mpeg.audio.codecs.ModeExtensionCodec;
import net.addradio.codec.mpeg.audio.codecs.SamplingRateCodec;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.streams.BitInputStream;
import net.addradio.streams.BitOutputStream;
import net.addradio.streams.BitStreamDecorator;

/**
 * MPEGAudioFrameOutputStream
 */
public class MPEGAudioFrameOutputStream extends BitOutputStream {

    /** {@link Logger} LOG. */
    @SuppressWarnings("unused")
    private final static Logger LOG = LoggerFactory.getLogger(MPEGAudioFrameOutputStream.class);

    /**
     * MPEGAudioFrameOutputStream constructor.
     *
     * @param innerRef
     *            {@link OutputStream}
     */
    public MPEGAudioFrameOutputStream(final OutputStream innerRef) {
        super(innerRef);
    }

    /**
     * writeFrame.
     * @param frame {@link MPEGAudioContent}
     * @return {@code int} number of bytes written.
     * @throws IOException due to IO problems.
     * @throws MPEGAudioCodecException if encoding encountered a bad model state.
     */
    public int writeFrame(final MPEGAudioContent frame) throws IOException, MPEGAudioCodecException {
        int bytesWritten = 0;
        if (frame instanceof MPEGAudioFrame) {
            final MPEGAudioFrame mpegAudioFrame = (MPEGAudioFrame) frame;
            writeBits(MPEGAudioFrame.SYNC_WORD_PATTERN, 11);
            writeBits(mpegAudioFrame.getVersion().getBitMask(), 2);
            writeBits(mpegAudioFrame.getLayer().getBitMask(), 2);
            writeBit(mpegAudioFrame.isErrorProtected() ? 1 : 0);
            bytesWritten += 2;

            writeBits(BitRateCodec.encode(mpegAudioFrame), 4);
            writeBits(SamplingRateCodec.encode(mpegAudioFrame), 2);
            writeBit(mpegAudioFrame.isPadding() ? 1 : 0);
            writeBit(mpegAudioFrame.isPrivate() ? 1 : 0);
            bytesWritten++;

            writeBits(mpegAudioFrame.getMode().getBitMask(), 2);
            writeBits(ModeExtensionCodec.encode(mpegAudioFrame), 2);
            writeBit(mpegAudioFrame.isCopyright() ? 1 : 0);
            writeBit(mpegAudioFrame.isOriginal() ? 1 : 0);
            writeBits(mpegAudioFrame.getEmphasis().getBitMask(), 2);
            bytesWritten++;

            if (mpegAudioFrame.isErrorProtected()) {
                if ((mpegAudioFrame.getCrc() != null)
                        && (mpegAudioFrame.getCrc().length == MPEGAudioFrame.CRC_SIZE_IN_BYTES)) {
                    write(mpegAudioFrame.getCrc());
                    bytesWritten += MPEGAudioFrame.CRC_SIZE_IN_BYTES;
                } else {
                    throw new MPEGAudioCodecException("Invalid CRC in frame [" //$NON-NLS-1$
                            + Arrays.toString(mpegAudioFrame.getCrc()) + "]."); //$NON-NLS-1$
                }
            }
            if (mpegAudioFrame.getPayload() == null) {
                throw new MPEGAudioCodecException("payload of frame is null."); //$NON-NLS-1$
            }

            switch (((MPEGAudioFrame) frame).getLayer()) {
            case I:
                break;
            case II:
                break;
            case III:
                switch (((MPEGAudioFrame) frame).getMode()) {
                case SingleChannel:
                    final BitStreamDecorator bsd = new BitStreamDecorator(
                            new BitInputStream(new ByteArrayInputStream(((MPEGAudioFrame) frame).getPayload())), this);
                    bsd.skipBits(18);
                    for (int gr = 0; gr < 2; gr++) {
                        bsd.skipBits(21);
                        bsd.write(((MPEGAudioFrame) frame).getGlobalGain()[gr][0]);
                        bsd.skipBits(30);
                    }
                    bsd.skipBytes(((MPEGAudioFrame) frame).getPayload().length - 17);
                    bytesWritten += mpegAudioFrame.getPayload().length;
                    break;
                case DualChannel:
                case JointStereo:
                case Stereo:
                default:
                    final BitStreamDecorator bsd2 = new BitStreamDecorator(
                            new BitInputStream(new ByteArrayInputStream(((MPEGAudioFrame) frame).getPayload())), this);
                    bsd2.skipBits(20);
                    for (int gr = 0; gr < 2; gr++) {
                        for (int ch = 0; ch < 2; ch++) {
                            bsd2.skipBits(21);
                            bsd2.write(((MPEGAudioFrame) frame).getGlobalGain()[gr][ch]);
                            bsd2.skipBits(30);
                        }
                    }
                    bsd2.skipBytes(((MPEGAudioFrame) frame).getPayload().length - 32);
                    bytesWritten += mpegAudioFrame.getPayload().length;
                    break;
                }
                break;
            case reserved:
            default:
                break;
            }

        } else {
            // SEBASTIAN implement
        }
        return bytesWritten;
    }

}
