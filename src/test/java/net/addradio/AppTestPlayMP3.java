/**
 * Class:    AppTestPlayMP3<br/>
 * <br/>
 * Created:  11.12.2019<br/>
 * Filename: AppTestPlayMP3.java<br/>
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
package net.addradio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.mpeg.audio.MPEGAudio;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.model.Mode;
import net.addradio.codec.mpeg.audio.tools.MPEGAudioContentFilter;
import net.addradio.codec.mpeg.audio.tools.MPEGAudioContentHandler;
import net.addradio.decoder.DecodingException;
import net.addradio.decoder.PCMDecoderRegistry;
import net.addradio.decoder.PCMDecoderResult;

/**
 * AppTestPlayMP3.
 */
public class AppTestPlayMP3 {

    /** {@link Logger} LOG. */
    final static Logger LOG = LoggerFactory.getLogger(AppTestPlayMP3.class);

    protected static AudioFormat createAudioFormat(final File file) {
        try (final InputStream fis = new FileInputStream(file)) {
            final MPEGAudioFrame firstFrame = (MPEGAudioFrame) MPEGAudio.decodeFirstMPEGAudioContent(fis,
                    MPEGAudioContentFilter.MPEG_AUDIO_FRAMES);
            return new AudioFormat(firstFrame.getSamplingRate().getValueInHz(), 32,
                    firstFrame.getMode().equals(Mode.SingleChannel) ? 1 : 2, true, true);
        } catch (final FileNotFoundException e1) {
            LOG.error(e1.getLocalizedMessage(), e1);
        } catch (final IOException e1) {
            LOG.error(e1.getLocalizedMessage(), e1);
        }
        return null;
    }

    /**
     * main.
     * @param args {@link String}{@code []}
     */
    public static void main(final String[] args) {

        final File file = new File("src/test/mp3/organ.mp3");
        final AudioFormat fmt = createAudioFormat(file);

        if (fmt == null) {
            System.exit(1);
        }

        final SourceDataLine source = openSourceDataLine(fmt);
        if (source == null) {
            System.exit(1);
        }
        source.start();

        try (final InputStream fis = new FileInputStream(file)) {
            MPEGAudio.decode(fis, MPEGAudioContentFilter.MPEG_AUDIO_FRAMES, new MPEGAudioContentHandler() {

                private final byte[] buffer = new byte[1024 * 1024];

                @Override
                public boolean handle(final MPEGAudioContent content) {
                    try {
                        final PCMDecoderResult decode = PCMDecoderRegistry.decode((MPEGAudioFrame) content);
                        final int len = SimpleAudioConversion.encode(decode.getBuffer(), this.buffer,
                                decode.getSamplesLen(), fmt);
                        source.write(this.buffer, 0, len);
                    } catch (final DecodingException e) {
                        LOG.error(e.getLocalizedMessage(), e);
                    }
                    return false;
                }
            });
        } catch (final FileNotFoundException e) {
            LOG.error(e.getLocalizedMessage(), e);
            System.exit(1);
        } catch (final IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
            System.exit(1);
        }
    }

    protected static SourceDataLine openSourceDataLine(final AudioFormat fmt) {
        SourceDataLine source = null;
        try {
            source = AudioSystem.getSourceDataLine(fmt);
            source.open(fmt);
        } catch (final LineUnavailableException e1) {
            LOG.error(e1.getLocalizedMessage(), e1);
        }
        return source;
    }

}
