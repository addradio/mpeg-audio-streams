/**
 * Class:    Frame<br/>
 * <br/>
 * Created:  30.10.2017<br/>
 * Filename: Frame.java<br/>
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
 * Frame.
 */
public class Frame {

    /** {@link String} frameId. */
    private String frameId;

    /** {@link int} size. */
    private int size;

    /**
     * getFrameId.
     * @return String the frameId
     */
    public String getFrameId() {
        return this.frameId;
    }

    /**
     * getSize.
     * @return int the size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * setFrameId.
     * @param frameId String the frameId to set
     */
    public void setFrameId(final String frameId) {
        this.frameId = frameId;
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
        builder.append("Frame [frameId=");
        builder.append(this.frameId);
        builder.append(", size=");
        builder.append(this.size);
        builder.append("]");
        return builder.toString();
    }

}
