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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * AppTestMPEGAudioFrameInputStream
 */
public class AppTestMPEGAudioFrameInputStream {

    /** {@link String} MP3_SUFFIX. */
    private static final String MP3_SUFFIX = ".mp3"; //$NON-NLS-1$

    /** {@link Logger} LOG */
    private static final Logger LOG = LoggerFactory.getLogger(AppTestMPEGAudioFrameInputStream.class);

    /**
     * main.
     * 
     * @param args
     *            {@link String}{@code []}
     */
    public static void main(String[] args) {
        BasicConfigurator.configure();
        org.apache.log4j.Logger.getRootLogger().setLevel(Level.INFO);

        File[] files = new File(MP3TestFiles.MP3_TEST_FILE_DIRECTORY).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.canRead() && pathname.getName().toLowerCase().endsWith(MP3_SUFFIX);
            }
        });
        for (File file : files) {
            if (AppTestMPEGAudioFrameInputStream.LOG.isInfoEnabled()) {
                AppTestMPEGAudioFrameInputStream.LOG
                        .info("######## Try to decode new file [" + file.getAbsolutePath() + "]."); //$NON-NLS-1$ //$NON-NLS-2$
            }
            decodeMpegAudioFile(MP3TestFiles.MP3_TEST_FILE_DIRECTORY + File.separator + file.getName());
        }

    }

    /**
     * decodeMpegAudioFile.
     * @param fileName {@link String}
     */
    private static void decodeMpegAudioFile(String fileName) {
        try (MPEGAudioFrameInputStream mafis = new MPEGAudioFrameInputStream(new FileInputStream(fileName))) {
            mafis.setUnalignedSyncAllowed(false);

            MPEGAudioFrame frame = null;
            while ((frame = mafis.readFrame()) != null) {
                if (LOG.isInfoEnabled()) {
                    LOG.info(frame.toString());
                }
            }

        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

}
