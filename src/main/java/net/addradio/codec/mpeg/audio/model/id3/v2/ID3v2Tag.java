/**
 * Class:    ID3v2Tag<br/>
 * <br/>
 * Created:  30.10.2017<br/>
 * Filename: ID3v2Tag.java<br/>
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

import java.util.LinkedList;
import java.util.List;

import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;

/**
 * ID3v2Tag.
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
public class ID3v2Tag implements MPEGAudioContent {

    /** {@code int} majorVersion. */
    private int majorVersion;

    /** {@code int} revisionNumber. */
    private int revisionNumber;

    /** {@link boolean} unsynchronisation. */
    private boolean unsynchronisation;

    /** {@link boolean} experimental. */
    private boolean experimental;

    /** {@link boolean} footer. */
    private boolean footer;

    /** {@link int} tagSize. */
    private int tagSize;

    /** {@link List}{@code <Frame>} frames. */
    private List<Frame> frames;

    /** {@link ExtendedHeader} extendedHeader. */
    private ExtendedHeader extendedHeader;

    /**
     * ID3v2Tag constructor.
     */
    public ID3v2Tag() {
        this.frames = new LinkedList<>();
    }

    /**
     * getExtendedHeader.
     * @return ExtendedHeader the extendedHeader
     */
    public ExtendedHeader getExtendedHeader() {
        return this.extendedHeader;
    }

    /**
     * getFrames.
     * @return List<Frame> the frames
     */
    public List<Frame> getFrames() {
        return this.frames;
    }

    /**
     * getMajorVersion.
     * @return int the majorVersion
     */
    public int getMajorVersion() {
        return this.majorVersion;
    }

    /**
     * getRevisionNumber.
     * @return int the revisionNumber
     */
    public int getRevisionNumber() {
        return this.revisionNumber;
    }

    /**
     * getTagSize.
     * @return int the tagSize
     */
    public int getTagSize() {
        return this.tagSize;
    }

    /**
     * isExperimental.
     * @return boolean the experimental
     */
    public boolean isExperimental() {
        return this.experimental;
    }

    /**
     * isFooter.
     * @return boolean the footer
     */
    public boolean isFooter() {
        return this.footer;
    }

    /**
     * isUnsynchronisation.
     * @return boolean the unsynchronisation
     */
    public boolean isUnsynchronisation() {
        return this.unsynchronisation;
    }

    /**
     * setExperimental.
     * @param experimental boolean the experimental to set
     */
    public void setExperimental(final boolean experimental) {
        this.experimental = experimental;
    }

    /**
     * setExtendedHeader.
     * @param extendedHeader ExtendedHeader the extendedHeader to set
     */
    public void setExtendedHeader(final ExtendedHeader extendedHeader) {
        this.extendedHeader = extendedHeader;
    }

    /**
     * setFooter.
     * @param footer boolean the footer to set
     */
    public void setFooter(final boolean footer) {
        this.footer = footer;
    }

    /**
     * setFrames.
     * @param frames List<Frame> the frames to set
     */
    public void setFrames(final List<Frame> frames) {
        this.frames = frames;
    }

    /**
     * setMajorVersion.
     * @param majorVersion int the majorVersion to set
     */
    public void setMajorVersion(final int majorVersion) {
        this.majorVersion = majorVersion;
    }

    /**
     * setRevisionNumber.
     * @param revisionNumber int the revisionNumber to set
     */
    public void setRevisionNumber(final int revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    /**
     * setTagSize.
     * @param tagSize int the tagSize to set
     */
    public void setTagSize(final int tagSize) {
        this.tagSize = tagSize;
    }

    /**
     * setUnsynchronisation.
     * @param unsynchronisation boolean the unsynchronisation to set
     */
    public void setUnsynchronisation(final boolean unsynchronisation) {
        this.unsynchronisation = unsynchronisation;
    }

    /**
     * toString.
     * @see java.lang.Object#toString()
     * @return {@link String}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ID3v2Tag [majorVersion=");
        builder.append(this.majorVersion);
        builder.append(", revisionNumber=");
        builder.append(this.revisionNumber);
        builder.append(", unsynchronisation=");
        builder.append(this.unsynchronisation);
        builder.append(", experimental=");
        builder.append(this.experimental);
        builder.append(", footer=");
        builder.append(this.footer);
        builder.append(", tagSize=");
        builder.append(this.tagSize);
        builder.append(", frames=");
        builder.append(this.frames);
        builder.append(", extendedHeader=");
        builder.append(this.extendedHeader);
        builder.append("]");
        return builder.toString();
    }
}
