/**
 * Class:    TestMPEGAudio<br/>
 * <br/>
 * Created:  26.10.2017<br/>
 * Filename: TestMPEGAudio.java<br/>
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import junit.framework.TestCase;
import net.addradio.codec.mpeg.audio.model.BitRate;
import net.addradio.codec.mpeg.audio.model.Layer;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.model.SamplingRate;
import net.addradio.codec.mpeg.audio.model.Version;

/**
 * TestMPEGAudio.
 */
public class TestMPEGAudio extends TestCase {

    /**
     * assertCommonFramesProperties.
     * @param f1 {@link MPEGAudioFrame}
     * @param f2 {@link MPEGAudioFrame}
     */
    private static final void assertCommonFramesProperties(final MPEGAudioFrame f1, final MPEGAudioFrame f2) {
        assertEquals(f1.getVersion(), f2.getVersion());
        assertEquals(f1.getLayer(), f2.getLayer());
        assertEquals(f1.isErrorProtected(), f2.isErrorProtected());
        assertEquals(f1.getBitRate(), f2.getBitRate());
        assertEquals(f1.getSamplingRate(), f2.getSamplingRate());
        assertEquals(f1.isPrivate(), f2.isPrivate());
        assertEquals(f1.getMode(), f2.getMode());
        // SEBASTIAN can mode extension change during runtime?
        //        assertEquals(f1.getModeExtension(), f2.getModeExtension());
        assertEquals(f1.isCopyright(), f2.isCopyright());
        assertEquals(f1.isOriginal(), f2.isOriginal());
        assertEquals(f1.getEmphasis(), f2.getEmphasis());
    }

    /**
     * assertMPEGFileIntegrity.
     * @param fileName {@link String}
     * @param numberOfFrames {@code int} number of mpeg frames in file.
     * @throws IOException due to file problems.
     */
    private static final void assertMPEGFileIntegrity(final String fileName, final int numberOfFrames)
            throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            final List<MPEGAudioContent> decode = MPEGAudio.decode(fis);
            assertEquals(numberOfFrames, decode.size());
            MPEGAudioFrame firstFrame = null;
            for (final MPEGAudioContent frame : decode) {
                if (frame instanceof MPEGAudioFrame) {
                    if (firstFrame == null) {
                        firstFrame = (MPEGAudioFrame) frame;
                        continue;
                    }
                    assertCommonFramesProperties(firstFrame, (MPEGAudioFrame) frame);
                }
            }
        }
    }

    /**
     * TestMPEGAudio constructor.
     */
    public TestMPEGAudio() {
        BasicConfigurator.configure();
    }

    /**
     * testDecode.
     * @throws IOException due to file problems.
     */
    @SuppressWarnings("static-method")
    public void testDecode() throws IOException {
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME_1000HZ_MP3, 195);
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME_440HZ_MP3, 195);
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME_CLICK_MP3, 1228);
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME_ORGAN_MP3, 501);
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME_PIANO_MP3, 265);
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME_SWEEP_MP3, 387);
    }

    /**
     * testDecodeFirstFrame.
     * @throws IOException  due to file problems.
     */
    @SuppressWarnings("static-method")
    public void testDecodeFirstFrame() throws IOException {
        try (FileInputStream fis = new FileInputStream(MP3TestFiles.FILE_NAME_440HZ_MP3)) {
            final MPEGAudioFrame firstFrame = MPEGAudio.decodeFirstMPEGAudioFrame(fis);
            assertEquals(Version.MPEG_1, firstFrame.getVersion());
            assertEquals(Layer.III, firstFrame.getLayer());
            assertEquals(true, firstFrame.isErrorProtected());
            assertEquals(BitRate._48, firstFrame.getBitRate());
            assertEquals(SamplingRate._44100, firstFrame.getSamplingRate());
        }
    }

}
