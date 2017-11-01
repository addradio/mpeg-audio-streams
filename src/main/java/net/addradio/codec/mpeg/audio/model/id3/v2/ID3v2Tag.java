/**
 * Class:    ID3v2Tag<br/>
 * <br/>
 * Created:  31.10.2017<br/>
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
import net.addradio.codec.mpeg.audio.model.id3.ID3Tag;

/**
 * ID3v2Tag.
 */
public class ID3v2Tag implements MPEGAudioContent, ID3Tag {

    /** {@code int} majorVersion. */
    private int majorVersion;

    /** {@code int} revisionNumber. */
    private int revisionNumber;

    /** {@link boolean} unsynchronisation. */
    private boolean unsynchronisation;

    /** {@link int} tagSize. */
    private int tagSize;

    /** {@link List}{@code <Frame>} frames. */
    private List<Frame> frames;

    /**
     * ID3v2Tag constructor.
     */
    public ID3v2Tag() {
        this.frames = new LinkedList<>();
    }

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
        final ID3v2Tag other = (ID3v2Tag) obj;
        if (this.frames == null) {
            if (other.frames != null) {
                return false;
            }
        } else if (!this.frames.equals(other.frames)) {
            return false;
        }
        if (this.majorVersion != other.majorVersion) {
            return false;
        }
        if (this.revisionNumber != other.revisionNumber) {
            return false;
        }
        if (this.tagSize != other.tagSize) {
            return false;
        }
        if (this.unsynchronisation != other.unsynchronisation) {
            return false;
        }
        return true;
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
     * hashCode.
     * @see java.lang.Object#hashCode()
     * @return {@code int}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.frames == null) ? 0 : this.frames.hashCode());
        result = (prime * result) + this.majorVersion;
        result = (prime * result) + this.revisionNumber;
        result = (prime * result) + this.tagSize;
        result = (prime * result) + (this.unsynchronisation ? 1231 : 1237);
        return result;
    }

    /**
     * isUnsynchronisation.
     * @return boolean the unsynchronisation
     */
    public boolean isUnsynchronisation() {
        return this.unsynchronisation;
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

}