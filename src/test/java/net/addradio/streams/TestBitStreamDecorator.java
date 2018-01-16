/**
 * Class:    TestBitStreamDecorator<br/>
 * <br/>
 * Created:  10.11.2017<br/>
 * Filename: TestBitStreamDecorator.java<br/>
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * TestBitStreamDecorator
 */
public class TestBitStreamDecorator extends TestCase {

    /** {@link byte[]} IN_BUF */
    private static final byte[] IN_BUF = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };

    /** {@link byte[]} OUT_BUF */
    private static final byte[] OUT_BUF = new byte[] { 0, 1, 0, 16, 0, 0, 3, 0 };

    /**
     * TestBitStreamDecorator constructor.
     */
    public TestBitStreamDecorator() {
    }

    /**
     * testIt.
     * @throws IOException due to errors.
     */
    @SuppressWarnings("static-method")
    public void testIt() throws IOException {

        try (final BitInputStream inBIS = new BitInputStream(new ByteArrayInputStream(TestBitStreamDecorator.IN_BUF))) {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (final BitOutputStream outBOS = new BitOutputStream(baos)) {
                try (final BitStreamDecorator bsd = new BitStreamDecorator(inBIS, outBOS)) {
                    bsd.skipBits(8);

                    bsd.write(1);

                    bsd.skipBits(8);

                    bsd.skipBits(3);
                    bsd.writeBit(1);
                    bsd.skipBits(4);

                    bsd.skipBits(8);
                    bsd.skipBits(8);

                    bsd.skipBits(6);
                    bsd.writeBits(3, 2);

                    bsd.skipBits(8);
                }
            }
            final byte[] byteArray = baos.toByteArray();
            assertEquals(OUT_BUF.length, byteArray.length);
            for (int i = 0; i < byteArray.length; i++) {
                assertEquals(OUT_BUF[i], byteArray[i]);
            }

        }

    }

}
