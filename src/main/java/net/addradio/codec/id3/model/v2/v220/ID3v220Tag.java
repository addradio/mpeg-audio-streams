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
package net.addradio.codec.id3.model.v2.v220;

import net.addradio.codec.id3.codecs.ID3CodecTools;
import net.addradio.codec.id3.model.v2.ID3v2Tag;

/**
 * ID3v220Tag.
 */
public class ID3v220Tag extends ID3v2Tag {

    /** {@code boolean} compressed. */
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
     * {@inheritDoc}
     * @see net.addradio.codec.id3.model.ID3Tag#getLeadPerformerSoloistORBandOrchestraAccompaniment()
     */
    @Override
    public String getLeadPerformerSoloistORBandOrchestraAccompaniment() {
        return ID3CodecTools.getSavePayload(this, new String[] { "TP1", "TP2" }); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.id3.model.ID3Tag#getTitleSongnameContentDescription()
     */
    @Override
    public String getTitleSongnameContentDescription() {
        return ID3CodecTools.getSavePayload(this, "TT2"); //$NON-NLS-1$
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
     * @return {@code boolean} the compressed
     */
    public boolean isCompressed() {
        return this.compressed;
    }

    /**
     * setCompressed.
     * @param compressedVal {@code boolean} the compressed to set
     */
    public void setCompressed(final boolean compressedVal) {
        this.compressed = compressedVal;
    }

    /**
     * toString.
     * @see java.lang.Object#toString()
     * @return {@link String}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ID3v220Tag [compressed="); //$NON-NLS-1$
        builder.append(this.compressed);
        builder.append(", getFrames()="); //$NON-NLS-1$
        builder.append(getFrames());
        builder.append(", getMajorVersion()="); //$NON-NLS-1$
        builder.append(getMajorVersion());
        builder.append(", getRevisionNumber()="); //$NON-NLS-1$
        builder.append(getRevisionNumber());
        builder.append(", getTagSize()="); //$NON-NLS-1$
        builder.append(getPayloadSize());
        builder.append(", isUnsynchronisation()="); //$NON-NLS-1$
        builder.append(isUnsynchronisation());
        builder.append("]"); //$NON-NLS-1$
        return builder.toString();
    }
}
