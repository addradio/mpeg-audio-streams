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
package net.addradio.codec.id3.model.v2;

import java.util.LinkedList;
import java.util.List;

import net.addradio.codec.id3.model.ID3Tag;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;

/**
 * ID3v2Tag.
 */
public class ID3v2Tag implements MPEGAudioContent, ID3Tag {

    /** {@link List}{@code <}{@link Frame}{@code >} frames. */
    private List<Frame> frames;

    /** {@code int} majorVersion. */
    private int majorVersion;

    /** {@code int} overallSize */
    private int overallSize;

    /** {@code int} payloadSize. */
    private int payloadSize;

    /** {@code int} revisionNumber. */
    private int revisionNumber;

    /** {@code boolean} unsynchronisation. */
    private boolean unsynchronisation;

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
        if (this.payloadSize != other.payloadSize) {
            return false;
        }
        if (this.unsynchronisation != other.unsynchronisation) {
            return false;
        }
        return true;
    }

    /**
     * getFrames.
     * @return {@link List}{@code <}{@link Frame}{@code >} the frames
     */
    public List<Frame> getFrames() {
        return this.frames;
    }

    /**
     * getMajorVersion.
     * @return {@code int} the majorVersion
     */
    public int getMajorVersion() {
        return this.majorVersion;
    }

    /**
     * @return the {@code int} overallSize
     */
    @Override
    public int getOverallSize() {
        return this.overallSize;
    }

    /**
     * getTagSize.
     * @return {@code int} the payloadSize
     */
    public int getPayloadSize() {
        return this.payloadSize;
    }

    /**
     * getRevisionNumber.
     * @return {@code int} the revisionNumber
     */
    public int getRevisionNumber() {
        return this.revisionNumber;
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
        result = (prime * result) + this.payloadSize;
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
     * @param framesRef {@link List}{@code <}{@link Frame}{@code >} the frames to set
     */
    public void setFrames(final List<Frame> framesRef) {
        this.frames = framesRef;
    }

    /**
     * setMajorVersion.
     * @param majorVersionVal {@code int} the majorVersion to set
     */
    public void setMajorVersion(final int majorVersionVal) {
        this.majorVersion = majorVersionVal;
    }

    /**
     * @param overallSizeVal {@code int} the overallSize to set
     */
    public void setOverallSize(final int overallSizeVal) {
        this.overallSize = overallSizeVal;
    }

    /**
     * setTagSize.
     * @param tagSizeVal {@code int} the payloadSize to set
     */
    public void setPayloadSize(final int tagSizeVal) {
        this.payloadSize = tagSizeVal;
    }

    /**
     * setRevisionNumber.
     * @param revisionNumberVal {@code int} the revisionNumber to set
     */
    public void setRevisionNumber(final int revisionNumberVal) {
        this.revisionNumber = revisionNumberVal;
    }

    /**
     * setUnsynchronisation.
     * @param unsynchronisationVal {@code boolean} the unsynchronisation to set
     */
    public void setUnsynchronisation(final boolean unsynchronisationVal) {
        this.unsynchronisation = unsynchronisationVal;
    }

}