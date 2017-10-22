/**
 * Class:    BitInputStream<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: BitInputStream.java<br/>
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

import java.io.IOException;
import java.io.InputStream;

/**
 * BitInputStream
 */
public class BitInputStream extends InputStream {

    /** {@code int} MAX_OFFSET */
    static final int MAX_OFFSET = 7;

    /** {@code int} bitInByteOffset */
    private int bitInByteOffset = -1;

    /** {@link InputStream} inner */
    private InputStream inner;

    /** {@code int} lastByte */
    private int lastByte;

    /** {@link Object} lock */
    private final Object lock = new Object();

    /**
     * BitInputStream constructor.
     * @param innerRef
     *            {@link InputStream}
     */
    public BitInputStream(final InputStream innerRef) {
        setInner(innerRef);
    }

    /**
     * available.
     * @see java.io.InputStream#available()
     * @return {@code int}
     * @throws IOException
     *             in case of bad IO situations.
     */
    @Override
    public int available() throws IOException {
        return this.inner.available();
    }

    /**
     * close.
     * @see java.io.InputStream#close()
     * @throws IOException
     *             in case of bad IO situations.
     */
    @Override
    public void close() throws IOException {
        this.lastByte = 0;
        this.bitInByteOffset = -1;
        this.inner.close();
    }

    /**
     * getInner.
     * @return {@link InputStream} the inner.
     */
    public InputStream getInner() {
        return this.inner;
    }

    /**
     * isByteAligned.
     * @return {@code boolean true} if read pointer is aligned to byte boundaries.
     */
    public boolean isByteAligned() {
        return this.bitInByteOffset < 0;
    }

    /**
     * read.
     * @see java.io.InputStream#read()
     * @return {@code int}
     * @throws IOException
     *             in case of bad IO situations.
     */
    @Override
    public int read() throws IOException {
        synchronized (this.lock) {
            if (isByteAligned()) {
                return this.inner.read();
            }
        }
        return readBits(8);
    }

    /**
     * readBit.
     * @return {@code int} the read bit or {@code -1} if end of stream has been reached.
     * @throws IOException
     *             in case of bad IO situations.
     */
    public int readBit() throws IOException {
        synchronized (this.lock) {
            if (isByteAligned()) {
                this.bitInByteOffset = BitInputStream.MAX_OFFSET;
                this.lastByte = this.inner.read();
                if (this.lastByte == -1) {
                    return -1;
                }
            }
            final int returnVal = (this.lastByte & (0x1 << this.bitInByteOffset)) >> this.bitInByteOffset;
            this.bitInByteOffset--;
            return returnVal;
        }
    }

    /**
     * readBits.
     * @param numberOfBitsToRead
     *            <code>int</code>
     * @return <code>int</code> the value of the read bits or {@code -1} if the end of the stream has been reached;
     * @throws IOException
     *             in case of bad IO situations.
     */
    public int readBits(final int numberOfBitsToRead) throws IOException {
        int returnVal = 0;
        for (int offset = numberOfBitsToRead - 1; offset > -1; offset--) {
            int readBit = readBit();
            if (readBit == -1) {
                return -1;
            }
            returnVal |= readBit << offset;
        }
        return returnVal;
    }

    /**
     * readFully.
     * @param toFill
     *            <code>byte[]</code>
     * @throws IOException
     *             in case of bad IO situations.
     */
    public void readFully(final byte[] toFill) throws IOException {
        for (int i = 0; i < toFill.length; i++) {
            toFill[i] = (byte) read();
        }
    }

    /**
     * setInner.
     * @param innerRef
     *            {@link InputStream} the inner to set.
     */
    public void setInner(final InputStream innerRef) {
        this.inner = innerRef;
    }

}
