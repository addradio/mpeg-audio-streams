/**
 * Class:    BitStreamDecorator<br/>
 * <br/>
 * Created:  10.11.2017<br/>
 * Filename: BitStreamDecorator.java<br/>
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
 * BitStreamDecorator
 */
public class BitStreamDecorator extends OutputStream {

    /** {@link BitInputStream} inBIS */
    private final BitInputStream inBIS;

    /** {@link BitOutputStream} outBOS */
    private final BitOutputStream outBOS;

    /**
     * BitStreamDecorator constructor.
     * @param inBISRef {@link BitInputStream}
     * @param outBOSRef {@link BitOutputStream}
     */
    public BitStreamDecorator(final BitInputStream inBISRef, final BitOutputStream outBOSRef) {
        this.inBIS = inBISRef;
        this.outBOS = outBOSRef;
    }

    /**
     * skipBit.
     * @throws IOException due to IO problems.
     */
    public void skipBit() throws IOException {
        this.outBOS.writeBit(this.inBIS.readBit());
    }

    /**
     * skipBits.
     * @param numOfBitsToSkip {@code int}
     * @throws IOException due to IO problems.
     */
    public void skipBits(final int numOfBitsToSkip) throws IOException {
        this.outBOS.writeBits(this.inBIS.readBits(numOfBitsToSkip), numOfBitsToSkip);
    }

    /**
     * skipBytes.
     * @param i {@code int}
     * @throws IOException due to IO problems.
     */
    public void skipBytes(final int i) throws IOException {
        // SEBASTIAN improve
        for (int j = i; j > 0; j--) {
            this.outBOS.write(this.inBIS.read());
        }
    }

    /**
     * @param b {@code byte[]}
     * @throws IOException due to IO problems.
     * @see java.io.OutputStream#write(byte[])
     */
    @Override
    public void write(final byte[] b) throws IOException {
        this.outBOS.write(b);
        // SEBASTIAN improve by use of skip()
        this.inBIS.skipBits(b.length * 8);
    }

    /**
     * @param b {@code byte[]}
     * @param off {@link InternalError}
     * @param len {@code int}
     * @throws IOException due to IO problems.
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.outBOS.write(b, off, len);
        // SEBASTIAN improve by use of skip()
        this.inBIS.skipBits(len * 8);
    }

    /**
     * @param b {@link InternalError}
     * @throws IOException due to IO problems.
     * @see net.addradio.streams.BitOutputStream#write(int)
     */
    @Override
    public void write(final int b) throws IOException {
        this.outBOS.write(b);
        // SEBASTIAN improve by use of skip()
        this.inBIS.skipBits(8);
    }

    /**
     * @param i {@code int}
     * @throws IOException due to IO problems.
     * @see net.addradio.streams.BitOutputStream#writeBit(int)
     */
    public void writeBit(final int i) throws IOException {
        this.outBOS.writeBit(i);
        this.inBIS.skipBit();
    }

    /**
     * @param i {@code int}
     * @param numberOfBitsToWrite {@code int}
     * @throws IOException due to IO problems.
     * @see net.addradio.streams.BitOutputStream#writeBits(int, int)
     */
    public void writeBits(final int i, final int numberOfBitsToWrite) throws IOException {
        this.outBOS.writeBits(i, numberOfBitsToWrite);
        this.inBIS.skipBits(numberOfBitsToWrite);
    }

}
