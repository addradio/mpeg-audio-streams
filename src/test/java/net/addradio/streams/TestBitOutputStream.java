/**
 * Class:    TestBitOutputStream<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: TestBitOutputStream.java<br/>
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

package net.addradio.streams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Level;

import junit.framework.TestCase;
import net.addradio.Log4J;

/**
 * TestBitOutputStream
 */
public class TestBitOutputStream extends TestCase {

    /**
     * TestBitOutputStream constructor.
     */
    public TestBitOutputStream() {
        Log4J.configureLog4J(Level.INFO);
    }

    /**
     * testWriteBits.
     *
     * @throws IOException
     *             wont happen.
     */
    @SuppressWarnings("static-method")
    public void testMixedWriteBits() throws IOException {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (final BitOutputStream bitOS = new BitOutputStream(bos)) {
                // first byte is 0x14 or in binary mode 00010100
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(1);
                bitOS.writeBit(0);

                // bitOS.writeBit(1);
                // bitOS.writeBit(0);
                // bitOS.writeBit(0);
                // // second byte is 0x1D or in binary mode 00011101
                // bitOS.writeBit(0);
                // bitOS.writeBit(0);
                // bitOS.writeBit(0);
                // bitOS.writeBit(1);

                // 1000001 or 65
                bitOS.writeBits(65, 7);

                // bitOS.writeBit(1);
                // bitOS.writeBit(1);
                // bitOS.writeBit(0);
                // bitOS.writeBit(1);
                // // third byte is 0x06 or in binary mode 00000110
                // bitOS.writeBit(0);

                // 11010 or 26
                bitOS.writeBits(26, 5);

                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(1);
                bitOS.writeBit(1);
                bitOS.writeBit(0);
                bitOS.flush();

                final byte[] result = bos.toByteArray();

                assertEquals(0x14, result[0]);
                assertEquals(0x1D, result[1]);
                assertEquals(0x06, result[2]);
            }
        }
    }

    /**
     * testWrite.
     *
     * @throws IOException
     *             wont happen.
     */
    @SuppressWarnings("static-method")
    public void testWrite() throws IOException {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (final BitOutputStream bitOS = new BitOutputStream(bos)) {
                bitOS.write(0x14);
                bitOS.write(0x1D);
                bitOS.write(0x06);
                bitOS.flush();

                final byte[] result = bos.toByteArray();

                assertEquals(0x14, result[0]);
                assertEquals(0x1D, result[1]);
                assertEquals(0x06, result[2]);
            }
        }
    }

    /**
     * testWriteBit.
     *
     * @throws IOException
     *             wont happen.
     */
    @SuppressWarnings("static-method")
    public void testWriteBit() throws IOException {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (final BitOutputStream bitOS = new BitOutputStream(bos)) {
                // first byte is 0x14 or in binary mode 00010100
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(1);
                bitOS.writeBit(0);
                bitOS.writeBit(1);
                bitOS.writeBit(0);
                bitOS.writeBit(0);

                // second byte is 0x1D or in binary mode 00011101
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(1);
                bitOS.writeBit(1);
                bitOS.writeBit(1);
                bitOS.writeBit(0);
                bitOS.writeBit(1);

                // third byte is 0x06 or in binary mode 00000110
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(0);
                bitOS.writeBit(1);
                bitOS.writeBit(1);
                bitOS.writeBit(0);
                bitOS.flush();

                final byte[] result = bos.toByteArray();

                assertEquals(0x14, result[0]);
                assertEquals(0x1D, result[1]);
                assertEquals(0x06, result[2]);
            }
        }
    }

}
