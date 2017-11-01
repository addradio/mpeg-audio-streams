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
package net.addradio.codec.mpeg.audio.model.id3.v2.v230;

/**
 * ExtendedHeader.
 */
public class ExtendedHeader {

    /** {@link int} size. */
    private int size;

    /** {@link boolean} crcDataIsPresent. */
    private boolean crcDataIsPresent;

    /** {@link int} crc32. */
    private int crc32;

    /** {@link int} paddingSize. */
    private int paddingSize;

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
     * @return int the crc32
     */
    public int getCrc32() {
        return this.crc32;
    }

    /**
     * getPaddingSize.
     * @return int the paddingSize
     */
    public int getPaddingSize() {
        return this.paddingSize;
    }

    /**
     * getSize.
     * @return int the size
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
     * @return boolean the crcDataIsPresent
     */
    public boolean isCrcDataIsPresent() {
        return this.crcDataIsPresent;
    }

    /**
     * setCrc32.
     * @param crc32 int the crc32 to set
     */
    public void setCrc32(final int crc32) {
        this.crc32 = crc32;
    }

    /**
     * setCrcDataIsPresent.
     * @param crcDataIsPresent boolean the crcDataIsPresent to set
     */
    public void setCrcDataIsPresent(final boolean crcDataIsPresent) {
        this.crcDataIsPresent = crcDataIsPresent;
    }

    /**
     * setPaddingSize.
     * @param paddingSize int the paddingSize to set
     */
    public void setPaddingSize(final int paddingSize) {
        this.paddingSize = paddingSize;
    }

    /**
     * setSize.
     * @param size int the size to set
     */
    public void setSize(final int size) {
        this.size = size;
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
