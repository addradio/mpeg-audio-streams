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

import junit.framework.TestCase;
import net.addradio.codec.mpeg.audio.model.BitRate;
import net.addradio.codec.mpeg.audio.model.Layer;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.model.SamplingRate;
import net.addradio.codec.mpeg.audio.model.Version;
import net.addradio.codec.mpeg.audio.tools.MP3TestFiles;
import net.addradio.codec.mpeg.audio.tools.MPEGAudioContentFilter;

/**
 * TestMPEGAudio.
 */
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
     * @param numberOfMPEGAudioFrames {@code int} number of mpeg frames in file.
     * @throws IOException due to file problems.
     */
    private static final void assertMPEGFileIntegrity(final String fileName, final int numberOfMPEGAudioFrames)
            throws IOException {
        final DecodingResult dr = MPEGAudio.decode(fileName, MPEGAudioContentFilter.MPEG_AUDIO_FRAMES);
        assertEquals(numberOfMPEGAudioFrames, dr.getContent().size());
        assertEquals(0, dr.getSkippedBits());
        MPEGAudioFrame firstFrame = null;
        for (final MPEGAudioContent frame : dr.getContent()) {
            if (firstFrame == null) {
                firstFrame = (MPEGAudioFrame) frame;
                continue;
            }
            assertCommonFramesProperties(firstFrame, (MPEGAudioFrame) frame);
        }
    }

    /**
     * TestMPEGAudio constructor.
     */
    public TestMPEGAudio() {
    }

    /**
     * testDecode.
     * @throws IOException due to file problems.
     */
    @SuppressWarnings("static-method")
    public void testDecode1000Hz() throws IOException {
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME__1000HZ_MP3, 194);
    }

    /**
     * testDecode440Hz.
     * @throws IOException due to file problems.
     */
    @SuppressWarnings("static-method")
    public void testDecode440Hz() throws IOException {
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME__440HZ_MP3, 194);
    }

    /**
     * testDecodePaddingBug48000SR48Kbps.
     * @throws IOException due to I/O issues.
     */
    @SuppressWarnings("static-method")
    public void testDecodeBug20180320Kbps112SF48000() throws IOException {
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME__BUG_20180320_48000_MP3, 12132);
        //        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME__PADDING_BUG_48000_48_MP3, 194);
    }

    // SEBASTIAN fix this test
    /**
     * testDecodeAd1.
     * @throws IOException due to file problems.
     */
    @SuppressWarnings("static-method")
    public void testDecodeAd1() throws IOException {
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME__AD1_MP3, 1252);
    }

    /**
     * testDecodeClick.
     * @throws IOException due to file problems.
     */
    @SuppressWarnings("static-method")
    public void testDecodeClick() throws IOException {
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME__CLICK_MP3, 1228);
    }

    // SEBASTIAN fix this test
    //    /**
    //     * testDecodeMusic.
    //     * @throws IOException due to file problems.
    //     */
    //    @SuppressWarnings("static-method")
    //    public void testDecodeMusic() throws IOException {
    //        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME__MUSIC_MP3, 13324);
    //    }

    /**
     * testDecodeFirstFrame.
     * @throws IOException  due to file problems.
     */
    @SuppressWarnings("static-method")
    public void testDecodeFirstFrame() throws IOException {
        try (FileInputStream fis = new FileInputStream(MP3TestFiles.FILE_NAME__440HZ_MP3)) {
            final MPEGAudioFrame firstFrame = MPEGAudio.decodeFirstMPEGAudioFrame(fis);
            assertEquals(Version.MPEG_1, firstFrame.getVersion());
            assertEquals(Layer.III, firstFrame.getLayer());
            assertEquals(false, firstFrame.isErrorProtected());
            assertEquals(BitRate._48, firstFrame.getBitRate());
            assertEquals(SamplingRate._44100, firstFrame.getSamplingRate());
        }
    }

    /**
     * testDecodeOrgan.
     * @throws IOException due to file prolems.
     */
    @SuppressWarnings("static-method")
    public void testDecodeOrgan() throws IOException {
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME__ORGAN_MP3, 501);
    }

    /**
     * testDecodePiano.
     * @throws IOException due to file problems.
     */
    @SuppressWarnings("static-method")
    public void testDecodePiano() throws IOException {
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME__PIANO_MP3, 265);
    }

    /**
     * testDecodeSweep.
     * @throws IOException due to file problems.
     */
    @SuppressWarnings("static-method")
    public void testDecodeSweep() throws IOException {
        assertMPEGFileIntegrity(MP3TestFiles.FILE_NAME__SWEEP_MP3, 386);
    }

}
