/**
 * Class:    TestPreReadingInputStream<br/>
 * <br/>
 * Created:  23.10.2017<br/>
 * Filename: TestPreReadingInputStream.java<br/>
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
package net.addradio.streams;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * TestPreReadingInputStream.
 */
public class TestPreReadingInputStream extends TestCase {

    /**
     * testComparingPreReadAndReadByteArray.
     * @throws IOException due to IO problems.
     */
    @SuppressWarnings("static-method")
    public void testComparingPreReadAndReadByteArray() throws IOException {
        final byte[] inputBuffer = new byte[] { 0x4f, (byte) 0xae, 0x32, 0x7b, 0x65, (byte) 0x9a };
        try (PreReadingInputStream pris = new PreReadingInputStream(new ByteArrayInputStream(inputBuffer))) {
            final byte[] toBeFilled = new byte[4];
            final int bytesRead = pris.preRead(toBeFilled, 0, toBeFilled.length);
            assertEquals(toBeFilled.length, bytesRead);
            for (int i = 0; i < bytesRead; i++) {
                assertEquals(toBeFilled[i], inputBuffer[i]);
                assertEquals(toBeFilled[i], (byte) pris.read());
            }
            for (int i = bytesRead; i < inputBuffer.length; i++) {
                assertEquals(inputBuffer[i], (byte) pris.read());
            }
        }
    }

    /**
     * testNullPointer.
     * @throws IOException will never happen.
     */
    @SuppressWarnings("static-method")
    public void testNullPointer() throws IOException {
        try (PreReadingInputStream pris = new PreReadingInputStream(null)) {
            assertTrue("Constructor did not throw NullPointerException.", false); //$NON-NLS-1$
        } catch (final NullPointerException npe) {
        }
        try (PreReadingInputStream pris = new PreReadingInputStream(new ByteArrayInputStream("".getBytes()))) { //$NON-NLS-1$
            pris.setInner(null);
            assertTrue("Setter did not throw NullPointerException.", false); //$NON-NLS-1$
        } catch (final NullPointerException npe) {
        }
    }

}
