/**
 * Class:    ID3v230Tag<br/>
 * <br/>
 * Created:  01.11.2017<br/>
 * Filename: ID3v230Tag.java<br/>
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

import net.addradio.codec.id3.codecs.ID3CodecTools;
import net.addradio.codec.id3.model.v2.ID3v2Tag;

/**
 * ID3v230Tag.
 */
public class ID3v230Tag extends ID3v2Tag {

    /** {@link boolean} experimental. */
    private boolean experimental;

    /** {@link ExtendedHeader} extendedHeader. */
    private ExtendedHeader extendedHeader;

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
        final ID3v230Tag other = (ID3v230Tag) obj;
        if (this.experimental != other.experimental) {
            return false;
        }
        if (this.extendedHeader == null) {
            if (other.extendedHeader != null) {
                return false;
            }
        } else if (!this.extendedHeader.equals(other.extendedHeader)) {
            return false;
        }
        return true;
    }

    /**
     * getExtendedHeader.
     * @return ExtendedHeader the extendedHeader
     */
    public ExtendedHeader getExtendedHeader() {
        return this.extendedHeader;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.id3.model.ID3Tag#getLeadPerformerSoloistORBandOrchestraAccompaniment()
     */
    @Override
    public String getLeadPerformerSoloistORBandOrchestraAccompaniment() {
        return ID3CodecTools.getSavePayload(this, new String[] { "TPE1", "TPE2" }); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.id3.model.ID3Tag#getTitleSongnameContentDescription()
     */
    @Override
    public String getTitleSongnameContentDescription() {
        return ID3CodecTools.getSavePayload(this, "TIT2"); //$NON-NLS-1$
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
        result = (prime * result) + (this.experimental ? 1231 : 1237);
        result = (prime * result) + ((this.extendedHeader == null) ? 0 : this.extendedHeader.hashCode());
        return result;
    }

    /**
     * isExperimental.
     * @return boolean the experimental
     */
    public boolean isExperimental() {
        return this.experimental;
    }

    /**
     * setExperimental.
     * @param experimentalVal {@code boolean} the experimental to set
     */
    public void setExperimental(final boolean experimentalVal) {
        this.experimental = experimentalVal;
    }

    /**
     * setExtendedHeader.
     * @param extendedHeaderRef {@link ExtendedHeader} the extendedHeader to set
     */
    public void setExtendedHeader(final ExtendedHeader extendedHeaderRef) {
        this.extendedHeader = extendedHeaderRef;
    }

    /**
     * toString.
     * @see java.lang.Object#toString()
     * @return {@link String}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ID3v230Tag [getMajorVersion()="); //$NON-NLS-1$
        builder.append(getMajorVersion());
        builder.append(", getRevisionNumber()="); //$NON-NLS-1$
        builder.append(getRevisionNumber());
        builder.append(", isUnsynchronisation()="); //$NON-NLS-1$
        builder.append(isUnsynchronisation());
        builder.append(", getTagSize()="); //$NON-NLS-1$
        builder.append(getPayloadSize());
        builder.append(", getFrames()="); //$NON-NLS-1$
        builder.append(getFrames());
        builder.append(", experimental="); //$NON-NLS-1$
        builder.append(this.experimental);
        builder.append(", extendedHeader="); //$NON-NLS-1$
        builder.append(this.extendedHeader);
        builder.append("]"); //$NON-NLS-1$
        return builder.toString();
    }

}
