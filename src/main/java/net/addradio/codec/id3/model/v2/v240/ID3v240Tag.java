/**
 * Class:    ID3v240Tag<br/>
 * <br/>
 * Created:  30.10.2017<br/>
 * Filename: ID3v240Tag.java<br/>
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
package net.addradio.codec.id3.model.v2.v240;

import net.addradio.codec.id3.codecs.ID3CodecTools;
import net.addradio.codec.id3.model.v2.ID3v2Tag;

/**
 * ID3v240Tag.
 *
 * <pre>
 *    Overall tag structure:
 *
 *    +-----------------------------+
 *    |      Header (10 bytes)      |
 *    +-----------------------------+
 *    |       Extended Header       |
 *    | (variable length, OPTIONAL) |
 *    +-----------------------------+
 *    |   Frames (variable length)  |
 *    +-----------------------------+
 *    |           Padding           |
 *    | (variable length, OPTIONAL) |
 *    +-----------------------------+
 *    | Footer (10 bytes, OPTIONAL) |
 *    +-----------------------------+
 *
 * </pre>
 */
public class ID3v240Tag extends ID3v2Tag {

    /** {@code boolean} experimental. */
    private boolean experimental;

    /** {@link ExtendedHeader} extendedHeader. */
    private ExtendedHeader extendedHeader;

    /** {@code boolean} footer. */
    private boolean footer;

    /**
     * ID3v240Tag constructor.
     */
    public ID3v240Tag() {
    }

    /**
     * getExtendedHeader.
     * @return {@link ExtendedHeader} the extendedHeader
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
        // (20190304 saw) since some encoders use old v220 tags in v230 and v240 tags as well, we need to look
        //                for them just in case.
        return ID3CodecTools.getSavePayload(this, "TIT2", "TT2"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * isExperimental.
     * @return {@code boolean} the experimental
     */
    public boolean isExperimental() {
        return this.experimental;
    }

    /**
     * isFooter.
     * @return {@code boolean} the footer
     */
    public boolean isFooter() {
        return this.footer;
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
     * setFooter.
     * @param footerVal {@code boolean} the footer to set
     */
    public void setFooter(final boolean footerVal) {
        this.footer = footerVal;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#toString()
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ID3v240Tag [experimental=");
        builder.append(this.experimental);
        builder.append(", extendedHeader=");
        builder.append(this.extendedHeader);
        builder.append(", footer=");
        builder.append(this.footer);
        builder.append(", getMajorVersion()=");
        builder.append(getMajorVersion());
        builder.append(", getRevisionNumber()=");
        builder.append(getRevisionNumber());
        builder.append(", getFrames()=");
        builder.append(getFrames());
        builder.append("]");
        return builder.toString();
    }

}
