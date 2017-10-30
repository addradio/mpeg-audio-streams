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
package net.addradio.codec.mpeg.audio.model.id3.v2;

/**
 * ExtendedHeader.
 */
public class ExtendedHeader {

    /** {@link int} size. */
    private int size;

    /** {@link boolean} tagIsAnUpdate. */
    private boolean tagIsAnUpdate;

    /** {@link boolean} crcDataIsPresent. */
    private boolean crcDataIsPresent;

    /** {@link int} crc32. */
    private int crc32;

    /**
     * toString.
     * @see java.lang.Object#toString()
     * @return {@link String}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExtendedHeader [size=");
        builder.append(size);
        builder.append(", tagIsAnUpdate=");
        builder.append(tagIsAnUpdate);
        builder.append(", crcDataIsPresent=");
        builder.append(crcDataIsPresent);
        builder.append(", crc32=");
        builder.append(crc32);
        builder.append(", tagRestrictions=");
        builder.append(tagRestrictions);
        builder.append("]");
        return builder.toString();
    }

    /**
     * getCrc32.
     * @return int the crc32
     */
    public int getCrc32() {
        return this.crc32;
    }

    /**
     * setCrc32.
     * @param crc32 int the crc32 to set
     */
    public void setCrc32(int crc32) {
        this.crc32 = crc32;
    }

    /** {@link TagRestrictions} tagRestrictions. */
    private TagRestrictions tagRestrictions;

    /**
     * getSize.
     * @return int the size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * getTagRestrictions.
     * @return TagRestrictions the tagRestrictions
     */
    public TagRestrictions getTagRestrictions() {
        return this.tagRestrictions;
    }

    /**
     * isCrcDataIsPresent.
     * @return boolean the crcDataIsPresent
     */
    public boolean isCrcDataIsPresent() {
        return this.crcDataIsPresent;
    }

    /**
     * isTagIsAnUpdate.
     * @return boolean the tagIsAnUpdate
     */
    public boolean isTagIsAnUpdate() {
        return this.tagIsAnUpdate;
    }

    /**
     * setCrcDataIsPresent.
     * @param crcDataIsPresent boolean the crcDataIsPresent to set
     */
    public void setCrcDataIsPresent(final boolean crcDataIsPresent) {
        this.crcDataIsPresent = crcDataIsPresent;
    }

    /**
     * setSize.
     * @param size int the size to set
     */
    public void setSize(final int size) {
        this.size = size;
    }

    /**
     * setTagIsAnUpdate.
     * @param tagIsAnUpdate boolean the tagIsAnUpdate to set
     */
    public void setTagIsAnUpdate(final boolean tagIsAnUpdate) {
        this.tagIsAnUpdate = tagIsAnUpdate;
    }

    /**
     * setTagRestrictions.
     * @param tagRestrictions TagRestrictions the tagRestrictions to set
     */
    public void setTagRestrictions(final TagRestrictions tagRestrictions) {
        this.tagRestrictions = tagRestrictions;
    }

}
