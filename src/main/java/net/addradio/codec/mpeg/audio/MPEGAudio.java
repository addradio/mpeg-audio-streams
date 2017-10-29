/**
 * Class:    MPEGAudio<br/>
 * <br/>
 * Created:  25.10.2017<br/>
 * Filename: MPEGAudio.java<br/>
 * Version:  $Revision$<br/>
 * <br/>
 * last modified on $Date$<br/>
 *               by $Author$<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author$ -- $Revision$ -- $Date$
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2017 - All rights reserved.
 */
package net.addradio.codec.mpeg.audio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.mpeg.audio.codecs.MPEGAudioCodecException;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * MPEGAudio.
 */
public class MPEGAudio {

    /** {@link Logger} LOG. */
    private final static Logger LOG = LoggerFactory.getLogger(MPEGAudio.class);

    /**
     * decode.
     * @param is {@link InputStream}
     * @return {@link List}{@code <}{@link MPEGAudioContent}{@code >}
     */
    public static final List<MPEGAudioContent> decode(final InputStream is) {
        final LinkedList<MPEGAudioContent> chunks = new LinkedList<>();
        try (final MPEGAudioFrameInputStream mafis = new MPEGAudioFrameInputStream(is)) {
            MPEGAudioContent frame = null;
            while ((frame = mafis.readFrame()) != null) {
                chunks.add(frame);
            }
        } catch (final IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return chunks;
    }

    /**
     * decodeFirstFrame.
     * @param buffer {@code byte[]}
     * @return {@link MPEGAudioFrame} or {@code null} if nothing could be decoded.
     */
    public static MPEGAudioFrame decodeFirstFrame(final byte[] buffer) {
        return decodeFirstFrame(new ByteArrayInputStream(buffer));
    }

    /**
     * decodeFirstFrame.
     * @param is {@link InputStream}
     * @return {@link MPEGAudioFrame}
     */
    public static MPEGAudioFrame decodeFirstFrame(final InputStream is) {
        try (final MPEGAudioFrameInputStream mafis = new MPEGAudioFrameInputStream(is)) {
            MPEGAudioContent content = null;
            while ((content = mafis.readFrame()) != null) {
                if (content instanceof MPEGAudioFrame) {
                    return (MPEGAudioFrame) content;
                }
            }
        } catch (final IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /**
     * encode.
     * @param frames {@link List}{@code <}{@link MPEGAudioContent}{@code >}
     * @return {@code byte[]} or  {@code null} if an error occurred.
     */
    public static byte[] encode(final List<MPEGAudioContent> frames) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            encode(frames, baos);
            return baos.toByteArray();
        } catch (final IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /**
     * encode.
     * @param frames {@link List}{@code <}{@link MPEGAudioContent}{@code >}
     * @param os {@link OutputStream}
     * @throws IOException due to IO problems.
     */
    public static void encode(final List<MPEGAudioContent> frames, final OutputStream os) throws IOException {
        try (MPEGAudioFrameOutputStream mafos = new MPEGAudioFrameOutputStream(os)) {
            for (final MPEGAudioContent mpegAudioFrame : frames) {
                try {
                    mafos.writeFrame(mpegAudioFrame);
                } catch (final MPEGAudioCodecException e) {
                    LOG.error(e.getLocalizedMessage(), e);
                }
            }
        }
    }

}
