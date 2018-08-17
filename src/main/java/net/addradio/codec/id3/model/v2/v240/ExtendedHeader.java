/**
 * Class:    ExtendedHeader<br/>
 * <br/>
 * Created:  30.10.2017<br/>
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
package net.addradio.codec.id3.model.v2.v240;

/**
 * ExtendedHeader.
 */
public class ExtendedHeader {

    /** {@code int} crc32. */
    private int crc32;

    /** {@code boolean} crcDataIsPresent. */
    private boolean crcDataIsPresent;

    /** {@code int} size. */
    private int size;

    /** {@code boolean} tagIsAnUpdate. */
    private boolean tagIsAnUpdate;

    /** {@link TagRestrictions} tagRestrictions. */
    private TagRestrictions tagRestrictions;

    /**
     * getCrc32.
     * @return {@code int} the crc32
     */
    public int getCrc32() {
        return this.crc32;
    }

    /**
     * getSize.
     * @return {@code int} the size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * getTagRestrictions.
     * @return {@link TagRestrictions} the tagRestrictions
     */
    public TagRestrictions getTagRestrictions() {
        return this.tagRestrictions;
    }

    /**
     * isCrcDataIsPresent.
     * @return {@code boolean} the crcDataIsPresent
     */
    public boolean isCrcDataIsPresent() {
        return this.crcDataIsPresent;
    }

    /**
     * isTagIsAnUpdate.
     * @return {@code boolean} the tagIsAnUpdate
     */
    public boolean isTagIsAnUpdate() {
        return this.tagIsAnUpdate;
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
     * setSize.
     * @param sizeVal {@code int} the size to set
     */
    public void setSize(final int sizeVal) {
        this.size = sizeVal;
    }

    /**
     * setTagIsAnUpdate.
     * @param tagIsAnUpdateVal {@code boolean} the tagIsAnUpdate to set
     */
    public void setTagIsAnUpdate(final boolean tagIsAnUpdateVal) {
        this.tagIsAnUpdate = tagIsAnUpdateVal;
    }

    /**
     * setTagRestrictions.
     * @param tagRestrictionsRef {@link TagRestrictions} the tagRestrictions to set
     */
    public void setTagRestrictions(final TagRestrictions tagRestrictionsRef) {
        this.tagRestrictions = tagRestrictionsRef;
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
        builder.append(", tagIsAnUpdate="); //$NON-NLS-1$
        builder.append(this.tagIsAnUpdate);
        builder.append(", crcDataIsPresent="); //$NON-NLS-1$
        builder.append(this.crcDataIsPresent);
        builder.append(", crc32="); //$NON-NLS-1$
        builder.append(this.crc32);
        builder.append(", tagRestrictions="); //$NON-NLS-1$
        builder.append(this.tagRestrictions);
        builder.append("]"); //$NON-NLS-1$
        return builder.toString();
    }

}
