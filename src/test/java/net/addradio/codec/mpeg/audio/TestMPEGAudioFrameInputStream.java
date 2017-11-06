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

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import junit.framework.TestCase;
import net.addradio.Log4J;
import net.addradio.codec.mpeg.audio.codecs.MPEGAudioCodecException;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.tools.MP3TestFiles;

/**
 * TestMPEGAudioFrameInputStream
 */
public class TestMPEGAudioFrameInputStream extends TestCase {

    /**
     * {@link byte[]} BYTES_WITH_SYNC_BYTES.
     *
     * <pre>
     *     F0       7F       F5        FF       EF
     * |11110000|01111111|11110101|11111111|11101111|      -&gt; bits in byte boundaries
     *            \----------/   \-----------/             -&gt; sync bits unaligned
     *                             \----------/            -&gt; sync bits aligned to byte boundaries
     * </pre>
     */
    static final byte[] BYTES_WITH_SYNC_BYTES = new byte[] { (byte) 0xF0, 0x7F, (byte) 0xF5, (byte) 0xFF, (byte) 0xEF };

    /**
     * TestMPEGAudioFrameInputStream constructor.
     */
    public TestMPEGAudioFrameInputStream() {
        Log4J.configureLog4J(Level.INFO);
    }

    /**
     * testBitAlignment.
     * @throws IOException due to IO problems.
     */
    @SuppressWarnings("static-method")
    public void testBitAlignment() throws IOException {
        MPEGAudioFrameInputStream mafis = new MPEGAudioFrameInputStream(
                new ByteArrayInputStream(BYTES_WITH_SYNC_BYTES));
        mafis.setUnalignedSyncAllowed(true);

        assertTrue(mafis.isByteAligned());
        assertEquals(9, mafis.sync().getSkippedBits());
        assertEquals(3, mafis.sync().getSkippedBits());

        mafis = new MPEGAudioFrameInputStream(new ByteArrayInputStream(BYTES_WITH_SYNC_BYTES));
        mafis.setUnalignedSyncAllowed(false);

        assertTrue(mafis.isByteAligned());
        assertEquals(24, mafis.sync().getSkippedBits());
    }

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
            MPEGAudioContent frame = null;
            while ((frame = mafis.readFrame()) != null) {
            }
        }
    }

    /**
     * testSync.
     * @throws IOException due to file errors.
     */
    @SuppressWarnings("static-method")
    public void testSync() throws IOException {
        try (MPEGAudioFrameInputStream mafis = new MPEGAudioFrameInputStream(
                new FileInputStream(MP3TestFiles.FILE_NAME_1000HZ_MP3))) {
            SyncResult syncResult = mafis.sync();
            assertEquals(0, syncResult.getSkippedBits());
            assertEquals(SyncMode.id3v2_aligned, syncResult.getMode());
        }
    }

}
