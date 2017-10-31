/**
 * Class:    ID3v220Tag<br/>
 * <br/>
 * Created:  31.10.2017<br/>
 * Filename: ID3v220Tag.java<br/>
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
package net.addradio.codec.mpeg.audio.model.id3.v2.v220;

import net.addradio.codec.mpeg.audio.model.id3.v2.ID3v2Tag;

/**
 * ID3v220Tag.
 */
public class ID3v220Tag extends ID3v2Tag {

    /** {@link boolean} compressed. */
    private boolean compressed;

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
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ID3v220Tag other = (ID3v220Tag) obj;
        if (this.compressed != other.compressed) {
            return false;
        }
        return true;
    }

    /**
     * hashCode.
     * @see java.lang.Object#hashCode()
     * @return {@code int}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = (prime * result) + (this.compressed ? 1231 : 1237);
        return result;
    }

    /**
     * isCompressed.
     * @return boolean the compressed
     */
    public boolean isCompressed() {
        return this.compressed;
    }

    /**
     * setCompressed.
     * @param compressed boolean the compressed to set
     */
    public void setCompressed(final boolean compressed) {
        this.compressed = compressed;
    }

    /**
     * toString.
     * @see java.lang.Object#toString()
     * @return {@link String}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ID3v220Tag [compressed=");
        builder.append(this.compressed);
        builder.append(", getFrames()=");
        builder.append(getFrames());
        builder.append(", getMajorVersion()=");
        builder.append(getMajorVersion());
        builder.append(", getRevisionNumber()=");
        builder.append(getRevisionNumber());
        builder.append(", getTagSize()=");
        builder.append(getTagSize());
        builder.append(", isUnsynchronisation()=");
        builder.append(isUnsynchronisation());
        builder.append("]");
        return builder.toString();
    }
}
