/**
 * Class:    ExtendedHeader<br/>
 * <br/>
 * Created:  01.11.2017<br/>
 * Filename: ExtendedHeader.java<br/>
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
package net.addradio.codec.id3.model.v2.v230;

/**
 * ExtendedHeader.
 */
public class ExtendedHeader {

    /** {@link int} crc32. */
    private int crc32;

    /** {@link boolean} crcDataIsPresent. */
    private boolean crcDataIsPresent;

    /** {@link int} paddingSize. */
    private int paddingSize;

    /** {@link int} size. */
    private int size;

    /**
     * equals.
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj {@link Object}
     * @return {@code boolean}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExtendedHeader other = (ExtendedHeader) obj;
        if (this.crc32 != other.crc32) {
            return false;
        }
        if (this.paddingSize != other.paddingSize) {
            return false;
        }
        if (this.size != other.size) {
            return false;
        }
        return true;
    }

    /**
     * getCrc32.
     * @return {@code int} the crc32
     */
    public int getCrc32() {
        return this.crc32;
    }

    /**
     * getPaddingSize.
     * @return {@code int} the paddingSize
     */
    public int getPaddingSize() {
        return this.paddingSize;
    }

    /**
     * getSize.
     * @return {@code int} the size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * hashCode.
     * @see java.lang.Object#hashCode()
     * @return {@code int}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + this.crc32;
        result = (prime * result) + this.paddingSize;
        result = (prime * result) + this.size;
        return result;
    }

    /**
     * isCrcDataIsPresent.
     * @return {@code boolean} the crcDataIsPresent
     */
    public boolean isCrcDataIsPresent() {
        return this.crcDataIsPresent;
    }

    /**
     * setCrc32.
     * @param crc32Val {@code int} the crc32 to set
     */
    public void setCrc32(final int crc32Val) {
        this.crc32 = crc32Val;
    }

    /**
     * setCrcDataIsPresent.
     * @param crcDataIsPresentVal {@code boolean} the crcDataIsPresent to set
     */
    public void setCrcDataIsPresent(final boolean crcDataIsPresentVal) {
        this.crcDataIsPresent = crcDataIsPresentVal;
    }

    /**
     * setPaddingSize.
     * @param paddingSizeVal {@code int} the paddingSize to set
     */
    public void setPaddingSize(final int paddingSizeVal) {
        this.paddingSize = paddingSizeVal;
    }

    /**
     * setSize.
     * @param sizeVal {@code int} the size to set
     */
    public void setSize(final int sizeVal) {
        this.size = sizeVal;
    }

    /**
     * toString.
     * @see java.lang.Object#toString()
     * @return {@link String}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ExtendedHeader [size="); //$NON-NLS-1$
        builder.append(this.size);
        builder.append(", crc32="); //$NON-NLS-1$
        builder.append(this.crc32);
        builder.append(", paddingSize="); //$NON-NLS-1$
        builder.append(this.paddingSize);
        builder.append("]"); //$NON-NLS-1$
        return builder.toString();
    }

}
