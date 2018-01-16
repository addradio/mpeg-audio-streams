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
package net.addradio.codec.id3.model.v2;

/**
 * Frame.
 */
public class Frame {

    /** {@link String} frameId. */
    private String frameId;

    /** {@link String} payload. */
    private String payload;

    /** {@link int} size. */
    private int size;

    /**
     * getFrameId.
     * @return {@link String} the frameId
     */
    public String getFrameId() {
        return this.frameId;
    }

    /**
     * getPayload.
     * @return {@link String} the payload
     */
    public String getPayload() {
        return this.payload;
    }

    /**
     * getSize.
     * @return {@code int} the size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * setFrameId.
     * @param frameIdVal {@link String} the frameId to set
     */
    public void setFrameId(final String frameIdVal) {
        this.frameId = frameIdVal;
    }

    /**
     * setPayload.
     * @param payloadVal {@link String} the payload to set
     */
    public void setPayload(final String payloadVal) {
        this.payload = payloadVal;
    }

    /**
     * setSize.
     * @param sizeVal {@code int} the size to set
     */
    public void setSize(final int sizeVal) {
        this.size = sizeVal;
    }

    /**
     * toString.
     * @see java.lang.Object#toString()
     * @return {@link String}
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Frame [frameId="); //$NON-NLS-1$
        builder.append(this.frameId);
        builder.append(", size="); //$NON-NLS-1$
        builder.append(this.size);
        builder.append(", payload="); //$NON-NLS-1$
        if (!getFrameId().equals("APIC")) {
            builder.append(this.payload);
        } else {
            builder.append("<BINARY>");
        }
        builder.append("]"); //$NON-NLS-1$
        return builder.toString();
    }

}
