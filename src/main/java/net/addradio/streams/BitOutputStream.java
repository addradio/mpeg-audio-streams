/**
 * Class:    BitOutputStream<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: BitOutputStream.java<br/>
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
import java.io.OutputStream;

/**
 * BitOutputStream
 */
public class BitOutputStream extends OutputStream {

    /** {@code int} bitInByteOffset */
    private int bitInByteOffset = BitInputStream.MAX_OFFSET;

    /** {@code int} currentByte */
    private int currentByte;

    /** {@link OutputStream} inner */
    private OutputStream inner;

    /** {@link Object} lock */
    private final Object lock = new Object();

    /**
     * BitOutputStream constructor.
     *
     * @param innerRef
     *            {@link OutputStream}
     */
    public BitOutputStream(final OutputStream innerRef) {
        setInner(innerRef);
    }

    /**
     * close.
     *
     * @see java.io.OutputStream#close()
     * @throws IOException
     *             in case of bad IO situations.
     */
    @Override
    public void close() throws IOException {
        this.inner.close();
    }

    /**
     * flush.
     *
     * @see java.io.OutputStream#flush()
     * @throws IOException
     *             in case of bad IO situations.
     */
    @Override
    public void flush() throws IOException {
        this.inner.flush();
    }

    /**
     * getInner.
     *
     * @return {@link OutputStream} the inner.
     */
    public OutputStream getInner() {
        return this.inner;
    }

    /**
     * setInner.
     *
     * @param innerRef
     *            {@link OutputStream} the inner to set.
     */
    public void setInner(final OutputStream innerRef) {
        this.inner = innerRef;
    }

    /**
     * write.
     *
     * @see java.io.OutputStream#write(int)
     * @param b
     *            {@code int}
     * @throws IOException
     *             in case of bad IO situations.
     */
    @Override
    public void write(final int b) throws IOException {
        synchronized (this.lock) {
            if (this.bitInByteOffset == BitInputStream.MAX_OFFSET) {
                this.inner.write(b);
            } else {
                writeBits(b, 8);
            }
        }
    }

    /**
     * writeBit.
     *
     * @param i
     *            {@code int}
     * @throws IOException
     *             in case of bad IO situations.
     */
    public void writeBit(final int i) throws IOException {
        synchronized (this.lock) {
            this.currentByte |= i << this.bitInByteOffset;
            this.bitInByteOffset--;
            if (this.bitInByteOffset < 0) {
                this.inner.write(this.currentByte);
                this.currentByte = 0;
                this.bitInByteOffset = BitInputStream.MAX_OFFSET;
            }
        }
    }

    /**
     * writeBits.
     *
     * @param i
     *            {@code int}
     * @param numberOfBitsToWrite
     *            {@code int}
     * @throws IOException
     *             in case of bad IO situations.
     */
    public void writeBits(final int i, final int numberOfBitsToWrite) throws IOException {
        for (int offset = numberOfBitsToWrite - 1; offset > -1; offset--) {
            writeBit(((i & (0x1 << offset)) >> offset));
        }
    }

}
