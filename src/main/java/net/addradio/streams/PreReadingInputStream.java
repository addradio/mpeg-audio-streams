/**
 * Class:    PreReadingInputStream<br/>
 * <br/>
 * Created:  23.10.2017<br/>
 * Filename: PreReadingInputStream.java<br/>
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * PreReadingInputStream.
 */
public class PreReadingInputStream extends InputStream {

    /** {@link InputStream} inner. */
    private InputStream inner;

    /** {@code byte[]} preReadByteArray. */
    private byte[] preReadByteArray = null;

    /** {@code int} preReadByteArrayPointer. */
    private int preReadByteArrayPointer = -1;

    /**
     * PreReadingInputStream constructor.
     * @param innerRef {@link InputStream} the stream to be encapsulated.
     */
    public PreReadingInputStream(final InputStream innerRef) {
        setInner(innerRef);
    }

    /**
     * getInner.
     * @return {@link InputStream} the encapsulated inner input stream.
     */
    public InputStream getInner() {
        return this.inner;
    }

    /**
     * preRead. Pre-reads bytes from the underlying input stream and fills the given byte array. In further steps
     * the pre-read array will be used to fulfill the request to standard InputStream method calls like
     * {@link InputStream#read()} or {@link InputStream#read(byte[], int, int)}.
     *
     * @param toBeFilled {@code byte[]} the byte array to be filled.
     * @param offset {@code int}
     * @param length {@code int}
     * @return {@code int}
     * @throws IOException due to IO problems.
     */
    public int preRead(final byte[] toBeFilled, final int offset, final int length) throws IOException {
        synchronized (this.inner) {
            final int numberOfReadBytes = this.inner.read(toBeFilled, 0, toBeFilled.length);
            if (numberOfReadBytes > 0) {
                this.preReadByteArray = Arrays.copyOf(toBeFilled, numberOfReadBytes);
                this.preReadByteArrayPointer = 0;
            }
            return numberOfReadBytes;
        }
    }

    /**
     * read.
     * @see java.io.InputStream#read()
     * @return {@code int}
     * @throws IOException due to IO problems.
     */
    @Override
    public int read() throws IOException {
        synchronized (this.inner) {
            if (this.preReadByteArray != null) {
                if (this.preReadByteArrayPointer < this.preReadByteArray.length) {
                    return this.preReadByteArray[this.preReadByteArrayPointer++];
                }
                this.preReadByteArray = null;
                this.preReadByteArrayPointer = -1;
            }
            return this.inner.read();
        }
    }

    /**
     * setInner.
     * @param innerRef {@link InputStream} the input stream to be encapsulated.
     */
    public void setInner(final InputStream innerRef) {
        if (innerRef == null) {
            throw new NullPointerException("innerRef MUST NOT be null!"); //$NON-NLS-1$
        }
        this.inner = innerRef;
    }

}
