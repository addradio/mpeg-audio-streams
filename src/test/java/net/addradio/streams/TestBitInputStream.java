/**
 * Class:    TestBitInputStream<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: TestBitInputStream.java<br/>
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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * TestBitInputStream
 */
public class TestBitInputStream extends TestCase {

    /** {@code byte[]} BYTES */
    static final byte[] BYTES = new byte[] { 0x14, 0x1D, 0x06 };

    /** {@link BitInputStream} bitStream */
    private BitInputStream bitStream;

    /**
     * TestBitInputStream constructor.
     */
    public TestBitInputStream() {
    }

    /**
     * setUp.
     *
     * @see junit.framework.TestCase#setUp()
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.bitStream = new BitInputStream(new ByteArrayInputStream(TestBitInputStream.BYTES));
    }

    /**
     * testMixedReadBits.
     *
     * @throws IOException
     *             wont happen.
     */
    public void testMixedReadBits() throws IOException {
        // first byte is 0x14 or in binary mode 00010100
        assertTrue(this.bitStream.isByteAligned());
        assertEquals(0, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());
        assertEquals(0, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());
        assertEquals(0, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());
        assertEquals(1, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());
        assertEquals(0, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());

        // assertEquals(1, this.bitStream.readBit());
        // assertEquals(0, this.bitStream.readBit());
        // assertEquals(0, this.bitStream.readBit());
        // // second byte is 0x1D or in binary mode 00011101
        // assertEquals(0, this.bitStream.readBit());
        // assertEquals(0, this.bitStream.readBit());
        // assertEquals(0, this.bitStream.readBit());
        // assertEquals(1, this.bitStream.readBit());

        // 1000001 or 65
        assertEquals(65, this.bitStream.readBits(7));
        assertTrue(!this.bitStream.isByteAligned());

        // assertEquals(1, this.bitStream.readBit());
        // assertEquals(1, this.bitStream.readBit());
        // assertEquals(0, this.bitStream.readBit());
        // assertEquals(1, this.bitStream.readBit());
        // // third byte is 0x06 or in binary mode 00000110
        // assertEquals(0, this.bitStream.readBit());

        // 11010 or 26
        assertEquals(26, this.bitStream.readBits(5));
        assertTrue(!this.bitStream.isByteAligned());

        assertEquals(0, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());
        assertEquals(0, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());
        assertEquals(0, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());
        assertEquals(0, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());
        assertEquals(1, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());
        assertEquals(1, this.bitStream.readBit());
        assertTrue(!this.bitStream.isByteAligned());

        assertEquals(0, this.bitStream.readBit());

        assertTrue(this.bitStream.isByteAligned());
    }

    /**
     * testRead.
     *
     * @throws IOException
     *             wont happen.
     */
    public void testRead() throws IOException {
        assertEquals(0x14, this.bitStream.read());
        assertEquals(0x1D, this.bitStream.read());
        assertEquals(0x06, this.bitStream.read());
    }

    /**
     * testReadBit.
     *
     * @throws IOException
     *             wont happen.
     */
    public void testReadBit() throws IOException {
        // first byte is 0x14 or in binary mode 00010100
        assertEquals(0, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(1, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(1, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        // second byte is 0x1D or in binary mode 00011101
        assertEquals(0, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(1, this.bitStream.readBit());
        assertEquals(1, this.bitStream.readBit());
        assertEquals(1, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(1, this.bitStream.readBit());
        // third byte is 0x06 or in binary mode 00000110
        assertEquals(0, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
        assertEquals(1, this.bitStream.readBit());
        assertEquals(1, this.bitStream.readBit());
        assertEquals(0, this.bitStream.readBit());
    }

    //    /**
    //     * testReadMaxBits.
    //     *
    //     * @throws IOException
    //     *             wont happen.
    //     */
    //    public void testReadMaxBits() throws IOException {
    //        this.bitStream.readBits(128);
    //    }

}
