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

        try (MPEGAudioFrameInputStream mafis = new MPEGAudioFrameInputStream(
                new FileInputStream(MP3TestFiles.FILE_NAME_PIANO_MP3))) {
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
