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
     * @param frame {@link MPEGAudioFrame}
     * @throws IOException due to IO problems.
     * @throws MPEGAudioCodecException if encoding encountered a bad model state.
     */
    public void writeFrame(final MPEGAudioFrame frame) throws IOException, MPEGAudioCodecException {
        writeBits(MPEGAudioFrame.SYNC_PATTERN_0X7FF, 11);
        writeBits(frame.getVersion().getBitMask(), 2);
        writeBits(frame.getLayer().getBitMask(), 2);
        writeBit(frame.isErrorProtected() ? 1 : 0);
        writeBits(BitRateCodec.encode(frame), 4);
        writeBits(SamplingRateCodec.encode(frame), 2);
        writeBit(frame.isPadding() ? 1 : 0);
        writeBit(frame.isPrivate() ? 1 : 0);
        writeBits(frame.getMode().getBitMask(), 2);
        writeBits(ModeExtensionCodec.encode(frame), 2);
        writeBit(frame.isCopyright() ? 1 : 0);
        writeBit(frame.isOriginal() ? 1 : 0);
        writeBits(frame.getEmphasis().getBitMask(), 2);

        if (frame.isErrorProtected()) {
            if ((frame.getCrc() != null) && (frame.getCrc().length == MPEGAudioFrame.CRC_SIZE_IN_BYTES)) {
                write(frame.getCrc());
            } else {
                throw new MPEGAudioCodecException("Invalid CRC in frame [" //$NON-NLS-1$
                        + Arrays.toString(frame.getCrc()) + "]."); //$NON-NLS-1$
            }
        }
        if (frame.getPayload() == null) {
            throw new MPEGAudioCodecException("payload of frame is null."); //$NON-NLS-1$
        }
        write(frame.getPayload());
    }

}
