/**
 * Class:    AppTestMPEGAudioFrameInputStream<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: AppTestMPEGAudioFrameInputStream.java<br/>
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.id3.model.ID3Tag;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.tools.MP3TestFiles;
import net.addradio.codec.mpeg.audio.tools.MP3TestFiles.FileHandler;

/**
 * AppTestMPEGAudioFrameInputStream
 */
public class AppTestMPEGAudioFrameInputStream {

    /** {@link Logger} LOG */
    static final Logger LOG = LoggerFactory.getLogger(AppTestMPEGAudioFrameInputStream.class);

    /**
     * decodeMpegAudioFile.
     * @param file {@link File}
     */
    static void decodeMpegAudioFile(final File file) {
        try (MPEGAudioFrameInputStream mafis = new MPEGAudioFrameInputStream(new FileInputStream(file))) {
            mafis.setUnalignedSyncAllowed(false);

            MPEGAudioContent frame = null;
            while ((frame = mafis.readFrame()) != null) {
                if (ID3Tag.class.isAssignableFrom(frame.getClass())
                        && AppTestMPEGAudioFrameInputStream.LOG.isInfoEnabled()) {
                    AppTestMPEGAudioFrameInputStream.LOG.info(frame.toString());
                } else if (frame instanceof MPEGAudioFrame && AppTestMPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                    AppTestMPEGAudioFrameInputStream.LOG.debug(frame.toString());
                }
            }

        } catch (final IOException e) {
            AppTestMPEGAudioFrameInputStream.LOG.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * main.
     *
     * @param args
     *            {@link String}{@code []}
     */
    public static void main(final String[] args) {
        BasicConfigurator.configure();
        org.apache.log4j.Logger.getRootLogger().setLevel(Level.INFO);

        MP3TestFiles.iterateOverTestFiles(new FileHandler() {
            @Override
            public void handle(final File file) {
                if (AppTestMPEGAudioFrameInputStream.LOG.isInfoEnabled()) {
                    AppTestMPEGAudioFrameInputStream.LOG
                            .info("######## Try to decode new file [" + file.getAbsolutePath() + "]."); //$NON-NLS-1$ //$NON-NLS-2$
                }
                decodeMpegAudioFile(file);
            }
        });
    }

}
