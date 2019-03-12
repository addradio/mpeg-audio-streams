/**
 * Class:    TestIntegrationReadWrite<br/>
 * <br/>
 * Created:  06.11.2017<br/>
 * Filename: TestIntegrationReadWrite.java<br/>
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import net.addradio.codec.id3.model.ID3Tag;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.tools.MP3TestFiles;
import net.addradio.codec.mpeg.audio.tools.MPEGAudioContentFilter;

/**
 * TestIntegrationReadWrite
 */
public class TestIntegrationReadWrite extends TestCase {

    /** {@link Logger} LOG */
    private static final Logger LOG = LoggerFactory.getLogger(TestIntegrationReadWrite.class);

    /**
     * assertByteWiseReencodedFile.
     * @param fileName {@link String}
     * @throws IOException due to I/O issues.
     */
    private static void assertByteWiseReencodedFile(final String fileName) throws IOException {
        final File in = new File(fileName);
        // assume there is just one id 3 tag at the very beginning...
        final ID3Tag firstID3Tag = MPEGAudio.decodeFirstID3Tag(in);
        if (TestIntegrationReadWrite.LOG.isInfoEnabled()) {
            TestIntegrationReadWrite.LOG.info("" + firstID3Tag); //$NON-NLS-1$
        }
        final DecodingResult dr = MPEGAudio.decode(in, MPEGAudioContentFilter.MPEG_AUDIO_FRAMES);
        assertEquals(0, dr.getSkippedBits());
        final File out = File.createTempFile("TestIntegrationReadWrite-TEST", ".mp3"); //$NON-NLS-1$ //$NON-NLS-2$
        //        final File out = new File("out.mp3");
        if (TestIntegrationReadWrite.LOG.isInfoEnabled()) {
            TestIntegrationReadWrite.LOG.info("Created tmp file [" + out.getAbsolutePath() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        try (FileOutputStream fos = new FileOutputStream(out)) {
            MPEGAudio.encode(dr.getAudioFrames(), fos, true);
        }
        // SEBASTIAN since we do not encode ID3Tags until now, those bytes we have less in our result
        final int id3TagSize = firstID3Tag != null ? firstID3Tag.getOverallSize() : 0;
        final long bytesWithoutID3 = Files.size(in.toPath()) - id3TagSize;
        assertEquals(bytesWithoutID3, Files.size(out.toPath()));

        int byteNum = 0;
        try (FileInputStream inFis = new FileInputStream(in)) {
            try (FileInputStream outFis = new FileInputStream(out)) {
                // forget about the id3 tag bytes
                for (int i = id3TagSize; i > 0; i--) {
                    inFis.read();
                }
                for (long i = bytesWithoutID3; i > 0; i--) {
                    assertEquals("byteNum: " + byteNum, Integer.toBinaryString(inFis.read()), //$NON-NLS-1$
                            Integer.toBinaryString(outFis.read()));
                    byteNum++;
                }
            }
        }

        final DecodingResult drreencoded = MPEGAudio.decode(out, MPEGAudioContentFilter.MPEG_AUDIO_FRAMES);
        final List<MPEGAudioFrame> content = dr.getAudioFrames();
        final Iterator<MPEGAudioFrame> iterator = content.iterator();
        final Iterator<MPEGAudioFrame> iterator2 = drreencoded.getAudioFrames().iterator();
        while (iterator.hasNext() && iterator2.hasNext()) {
            assertEquals(iterator.next(), iterator2.next());
        }

        Files.deleteIfExists(out.toPath());
    }

    /**
     * TestIntegrationReadWrite constructor.
     */
    public TestIntegrationReadWrite() {
    }

    /**
     * testReadWrite.
     * @throws IOException due to file access issues.
     */
    @SuppressWarnings("static-method")
    public void testReadWrite1000Hz() throws IOException {
        assertByteWiseReencodedFile(MP3TestFiles.FILE_NAME__1000HZ_MP3);
    }

    /**
     * testReadWrite440Hz.
     * @throws IOException due to file access issues.
     */
    @SuppressWarnings("static-method")
    public void testReadWrite440Hz() throws IOException {
        assertByteWiseReencodedFile(MP3TestFiles.FILE_NAME__440HZ_MP3);
    }

    /**
     * testReadWriteClick.
     * @throws IOException due to file access issues.
     */
    @SuppressWarnings("static-method")
    public void testReadWriteClick() throws IOException {
        assertByteWiseReencodedFile(MP3TestFiles.FILE_NAME__CLICK_MP3);
    }

    // SEBASTIAN temp fix
    //    /**
    //     * testReadWriteMusic.
    //     * @throws IOException due to file access issues.
    //     */
    //    @SuppressWarnings("static-method")
    //    public void testReadWriteMusic() throws IOException {
    //        assertByteWiseReencodedFile(MP3TestFiles.FILE_NAME__MUSIC_MP3);
    //    }

    /**
     * testReadWriteOrgan.
     * @throws IOException due to file access issues.
     */
    @SuppressWarnings("static-method")
    public void testReadWriteOrgan() throws IOException {
        assertByteWiseReencodedFile(MP3TestFiles.FILE_NAME__ORGAN_MP3);
    }

    /**
     * testReadWritePiano.
     * @throws IOException due to file access issues.
     */
    @SuppressWarnings("static-method")
    public void testReadWritePiano() throws IOException {
        assertByteWiseReencodedFile(MP3TestFiles.FILE_NAME__PIANO_MP3);
    }

    /**
     * testReadWriteSweep.
     * @throws IOException due to file access issues.
     */
    @SuppressWarnings("static-method")
    public void testReadWriteSweep() throws IOException {
        assertByteWiseReencodedFile(MP3TestFiles.FILE_NAME__SWEEP_MP3);
    }

}
