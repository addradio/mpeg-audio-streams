/**
 * Class:    AppTestApplyGainFilter<br/>
 * <br/>
 * Created:  12.11.2017<br/>
 * Filename: AppTestApplyGainFilter.java<br/>
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.id3.model.ID3Tag;
import net.addradio.codec.mpeg.audio.filter.Filter;
import net.addradio.codec.mpeg.audio.filter.FixFactorGainFIlter;
import net.addradio.codec.mpeg.audio.tools.MPEGAudioContentFilter;

/**
 * AppTestApplyGainFilter.
 */
public class AppTestApplyGainFilter {

    /** {@link Logger} LOG. */
    private final static Logger LOG = LoggerFactory.getLogger(AppTestApplyGainFilter.class);

    /**
     * main.
     * @param args {@link String}{@code []}
     * @throws IOException due to file errors.
     */
    public static void main(String[] args) throws IOException {

        BasicConfigurator.configure();

        final File in = new File("src/test/mp3/1000Hz.mp3");
        // assume there is just one id 3 tag at the very beginning...
        final ID3Tag firstID3Tag = MPEGAudio.decodeFirstID3Tag(in);
        if (LOG.isInfoEnabled()) {
            LOG.info("" + firstID3Tag); //$NON-NLS-1$
        }
        final DecodingResult dr = MPEGAudio.decode(in, MPEGAudioContentFilter.MPEG_AUDIO_FRAMES);
        //        final File out = File.createTempFile("TestIntegrationReadWrite-TEST", ".mp3"); //$NON-NLS-1$ //$NON-NLS-2$
        final File out = new File("out.mp3");
        if (LOG.isInfoEnabled()) {
            LOG.info("Created tmp file [" + out.getAbsolutePath() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        try (FileOutputStream fos = new FileOutputStream(out)) {
            //            SineGainFilter filter = new SineGainFilter();
            //            filter.setWavelengthInSecs(10);
            Filter filter = new FixFactorGainFIlter(1.0);
            MPEGAudio.encode(dr.getContent(), filter, fos, true);
        }

    }

}
