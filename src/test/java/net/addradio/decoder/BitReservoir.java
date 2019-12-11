package net.addradio.decoder;

/**
 * Implementation of Bit Reservoir for Layer III.
 * <p>
 * The implementation stores single bits as a word in the buffer. If
 * a bit is set, the corresponding word in the buffer will be non-zero.
 * If a bit is clear, the corresponding word is zero. Although this
 * may seem waseful, this can be a factor of two quicker than
 * packing 8 bits to a byte and extracting.
 * <p>
 */

// REVIEW: there is no range checking, so buffer underflow or overflow
// can silently occur.
public final class BitReservoir {

    /**
     * Size of the internal buffer to store the reserved bits.
     * Must be a power of 2. And x8, as each bit is stored as a single
     * entry.
     */
    private static final int BUFSIZE = 4096 * 8;

    /**
     * Mask that can be used to quickly implement the
     * modulus operation on BUFSIZE.
     */
    private static final int BUFSIZE_MASK = BUFSIZE - 1;

    private int offset, totbit, buf_byte_idx;
    private final int[] buf = new int[BUFSIZE];

    /**
     * BitReservoir constructor.
     */
    public BitReservoir() {
        this.offset = 0;
        this.totbit = 0;
        this.buf_byte_idx = 0;
    }

    /**
    * Returns next bit from reserve.
    * @return {@code int} {@code 0} if next bit is reset, or 1 if next bit is set.
    */
    public int hget1bit() {
        this.totbit++;
        final int val = this.buf[this.buf_byte_idx];
        this.buf_byte_idx = (this.buf_byte_idx + 1) & BUFSIZE_MASK;
        return val;
    }

    /**
     * Read a number bits from the bit stream.
     * @param numberOfBits the number of
     * @return {@code int}
     */
    public int hgetbits(final int numberOfBits) {
        int nob = numberOfBits;
        this.totbit += nob;

        int val = 0;

        int pos = this.buf_byte_idx;
        if ((pos + nob) < BUFSIZE) {
            while (nob-- > 0) {
                val <<= 1;
                val |= ((this.buf[pos++] != 0) ? 1 : 0);
            }
        } else {
            while (nob-- > 0) {
                val <<= 1;
                val |= ((this.buf[pos] != 0) ? 1 : 0);
                pos = (pos + 1) & BUFSIZE_MASK;
            }
        }
        this.buf_byte_idx = pos;
        return val;
    }

    /**
     * Write 8 bits into the bit stream.
     * @param val {@code int}
     */
    public void hputbuf(final int val) {
        int ofs = this.offset;
        this.buf[ofs++] = val & 0x80;
        this.buf[ofs++] = val & 0x40;
        this.buf[ofs++] = val & 0x20;
        this.buf[ofs++] = val & 0x10;
        this.buf[ofs++] = val & 0x08;
        this.buf[ofs++] = val & 0x04;
        this.buf[ofs++] = val & 0x02;
        this.buf[ofs++] = val & 0x01;

        if (ofs == BUFSIZE) {
            this.offset = 0;
        } else {
            this.offset = ofs;
        }

    }

    /**
     * Return totbit Field.
     * @return {@code int}
     */
    public int hsstell() {
        return (this.totbit);
    }

    /**
     * Rewind N bits in Stream.
     * @param N {@code int}
     */
    public void rewindNbits(final int N) {
        this.totbit -= N;
        this.buf_byte_idx -= N;
        if (this.buf_byte_idx < 0) {
            this.buf_byte_idx += BUFSIZE;
        }
    }

    /**
     * Rewind N bytes in Stream.
     * @param N {@code int}
     */
    public void rewindNbytes(final int N) {
        final int bits = (N << 3);
        this.totbit -= bits;
        this.buf_byte_idx -= bits;
        if (this.buf_byte_idx < 0) {
            this.buf_byte_idx += BUFSIZE;
        }
    }
}
