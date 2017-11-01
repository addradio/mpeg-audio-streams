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
import net.addradio.streams.BitOutputStream;

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
     * @throws IOException due to IO problems.
     * @throws MPEGAudioCodecException if encoding encountered a bad model state.
     */
    public void writeFrame(final MPEGAudioContent frame) throws IOException, MPEGAudioCodecException {
        if (frame instanceof MPEGAudioFrame) {
            final MPEGAudioFrame mpegAudioFrame = (MPEGAudioFrame) frame;
            writeBits(MPEGAudioFrame.SYNC_WORD_PATTERN, 11);
            writeBits(mpegAudioFrame.getVersion().getBitMask(), 2);
            writeBits(mpegAudioFrame.getLayer().getBitMask(), 2);
            writeBit(mpegAudioFrame.isErrorProtected() ? 1 : 0);
            writeBits(BitRateCodec.encode(mpegAudioFrame), 4);
            writeBits(SamplingRateCodec.encode(mpegAudioFrame), 2);
            writeBit(mpegAudioFrame.isPadding() ? 1 : 0);
            writeBit(mpegAudioFrame.isPrivate() ? 1 : 0);
            writeBits(mpegAudioFrame.getMode().getBitMask(), 2);
            writeBits(ModeExtensionCodec.encode(mpegAudioFrame), 2);
            writeBit(mpegAudioFrame.isCopyright() ? 1 : 0);
            writeBit(mpegAudioFrame.isOriginal() ? 1 : 0);
            writeBits(mpegAudioFrame.getEmphasis().getBitMask(), 2);

            if (mpegAudioFrame.isErrorProtected()) {
                if ((mpegAudioFrame.getCrc() != null)
                        && (mpegAudioFrame.getCrc().length == MPEGAudioFrame.CRC_SIZE_IN_BYTES)) {
                    write(mpegAudioFrame.getCrc());
                } else {
                    throw new MPEGAudioCodecException("Invalid CRC in frame [" //$NON-NLS-1$
                            + Arrays.toString(mpegAudioFrame.getCrc()) + "]."); //$NON-NLS-1$
                }
            }
            if (mpegAudioFrame.getPayload() == null) {
                throw new MPEGAudioCodecException("payload of frame is null."); //$NON-NLS-1$
            }
            write(mpegAudioFrame.getPayload());
        } else {
            // SEBASTIAN implement
        }
    }

}
