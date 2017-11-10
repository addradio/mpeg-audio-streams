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
     * innerRead.
     * @return {@code int} read from inner stream.
     * @throws IOException due to IO problems or if end of stream has been reached.
     */
    private int innerRead() throws IOException {
        final int read = this.inner.read();
        //        System.out.println("read byte: 0x" + Integer.toHexString(read));
        if (read < 0) {
            throw new EndOfStreamException();
        }
        return read;
    }

    /**
     * isByteAligned.
     * @return {@code boolean true} if read pointer is aligned to byte boundaries.
     */
    public boolean isByteAligned() {
        return this.bitInByteOffset < 0;
    }

    /**
     * isNextBitOne. Reads just one bit and checks whether it is 0b1 or not.
     *
     * @return {@code boolean true} only if the next bit is 0b1.
     * @throws IOException due to IO problems.
     */
    public boolean isNextBitOne() throws IOException {
        return readBit() == 1;
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
                return innerRead();
            }
        }
        return readBits(8);
    }

    /**
     * readBit.
     * @return {@code int} the read bit.
     * @throws IOException
     *             in case of bad IO situations.
     */
    public int readBit() throws IOException {
        synchronized (this.lock) {
            if (isByteAligned()) {
                this.bitInByteOffset = BitInputStream.MAX_OFFSET;
                this.lastByte = innerRead();
            }
            final int returnVal = (this.lastByte & (0x1 << this.bitInByteOffset)) >> this.bitInByteOffset;
            this.bitInByteOffset--;
            //            System.out.println("read bit: 0b" + Integer.toBinaryString(returnVal));
            return returnVal;
        }
    }

    /**
     * readBits.
     * @param numberOfBitsToRead
     *            <code>int</code>
     * @return <code>int</code> the value of the read bits;
     * @throws IOException
     *             in case of bad IO situations.
     */
    public int readBits(final int numberOfBitsToRead) throws IOException {
        int returnVal = 0;
        for (int offset = numberOfBitsToRead - 1; offset > -1; offset--) {
            returnVal |= readBit() << offset;
        }
        //        System.out.println("read bits: 0b" + Integer.toBinaryString(returnVal));
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
     * readInt.
     * @param  numOfBytes {@code int} to be read.
     * @return {@code int}
     * @throws IOException due to IO problems.
     */
    public int readInt(final int numOfBytes) throws IOException {
        // SEBASTIAN check for max bytes
        int value = 0;
        for (int i = numOfBytes - 1; i > -1; i--) {
            value |= (read() << (i * 8));
        }
        return value;
    }

    /**
     * setInner.
     * @param innerRef
     *            {@link InputStream} the inner to set.
     */
    public void setInner(final InputStream innerRef) {
        this.inner = innerRef;
    }

    /**
     * skipBit.
     * @throws IOException due to bad IO situations.
     */
    public void skipBit() throws IOException {
        skipBits(1);
    }

    /**
     * skipBits.
     * @param numberOfBitsToSkip {@code int}
     * @throws IOException in case of bad IO situations.
     */
    public void skipBits(final int numberOfBitsToSkip) throws IOException {
        // SEBASTIAN implement more efficiently
        readBits(numberOfBitsToSkip);
    }

}
