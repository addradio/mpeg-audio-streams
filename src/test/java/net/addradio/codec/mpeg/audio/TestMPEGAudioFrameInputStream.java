/**
 * Class:    TestMPEGAudioFrameInputStream<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: TestMPEGAudioFrameInputStream.java<br/>
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
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;
import net.addradio.codec.mpeg.audio.codecs.MPEGAudioCodecException;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * TestMPEGAudioFrameInputStream
 */
public class TestMPEGAudioFrameInputStream extends TestCase {

    /**
     * testSimplyIfBroken.
     * 
     * @throws IOException
     *             if file could not be read
     * @throws FileNotFoundException
     *             if file does not exist
     * @throws MPEGAudioCodecException
     *             if an decoding error occurred.
     */
    @SuppressWarnings("static-method")
    public void testSimplyIfBroken() throws FileNotFoundException, IOException, MPEGAudioCodecException {
        try (MPEGAudioFrameInputStream mafis = new MPEGAudioFrameInputStream(
                new FileInputStream(MP3TestFiles.FILE_NAME_PIANO_MP3))) {
            @SuppressWarnings("unused")
            MPEGAudioFrame frame = null;
            while ((frame = mafis.readFrame()) != null) {
            }
        }
    }

}
