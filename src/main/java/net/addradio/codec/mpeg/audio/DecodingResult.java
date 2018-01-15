/**
 * Class:    DecodingResult<br/>
 * <br/>
 * Created:  05.11.2017<br/>
 * Filename: DecodingResult.java<br/>
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
package net.addradio.codec.mpeg.audio;

import java.util.List;

import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;

/**
 * DecodingResult.
 */
public class DecodingResult {

    /** {@code float} averageBitRate */
    private float averageBitRate = -1;

    /** {@link List}{@code <}{@link MPEGAudioContent}{@code >} content. */
    private List<MPEGAudioContent> content;

    /** {@code long} skippedBits. */
    private long skippedBits;

    /** {@code long} durationMillis. */
    private long durationMillis = -1;

    /**
    * DecodingResult constructor.
    */
    public DecodingResult() {
    }

    /**
     * DecodingResult constructor.
     * @param skippedBitsVal {@code long}
     * @param averageBitRateVal {@code float}
     * @param durationMillisVal {@code long}
     * @param contentRef {@link List}{@code <}{@link MPEGAudioContent}{@code >}
     */
    public DecodingResult(final long skippedBitsVal, final float averageBitRateVal, final long durationMillisVal,
            final List<MPEGAudioContent> contentRef) {
        setSkippedBits(skippedBitsVal);
        setAverageBitRate(averageBitRateVal);
        setDurationMillis(durationMillisVal);
        setContent(contentRef);
    }

    /**
     * @return the {@code float} averageBitRate or {@code -1} if average bitrate has not been calculated so far.
     */
    public float getAverageBitRate() {
        return this.averageBitRate;
    }

    /**
     * getContent.
     * @return {@link List}{@code <}{@link MPEGAudioContent}{@code >} the content
     */
    public List<MPEGAudioContent> getContent() {
        return this.content;
    }

    /**
     * getDurationMillis.
     * @return long the durationMillis
     */
    public long getDurationMillis() {
        return this.durationMillis;
    }

    /**
     * getNumberOfDecodedContents.
     * @return {@code int}
     */
    public int getNumberOfDecodedContents() {
        return this.content.size();
    }

    /**
     * getSkippedBits.
     * @return {@code long} the skippedBits
     */
    public long getSkippedBits() {
        return this.skippedBits;
    }

    /**
     * @param averageBitRateVal {@code float} the averageBitRate to set
     */
    public void setAverageBitRate(final float averageBitRateVal) {
        this.averageBitRate = averageBitRateVal;
    }

    /**
     * setContent.
     * @param contentRef {@link List}{@code <}{@link MPEGAudioContent}{@code >} the content to set
     */
    public void setContent(final List<MPEGAudioContent> contentRef) {
        this.content = contentRef;
    }

    /**
     * setDurationMillis.
     * @param durationMillisVal long the durationMillis to set
     */
    public void setDurationMillis(final long durationMillisVal) {
        this.durationMillis = durationMillisVal;
    }

    /**
     * setSkippedBits.
     * @param skippedBitsVal {@code long} the skippedBits to set
     */
    public void setSkippedBits(final long skippedBitsVal) {
        this.skippedBits = skippedBitsVal;
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
        builder.append("DecodingResult [averageBitRate=");
        builder.append(this.averageBitRate);
        builder.append(", durationMillis=");
        builder.append(this.durationMillis);
        builder.append(", skippedBits=");
        builder.append(this.skippedBits);
        builder.append(", getNumberOfDecodedContents()=");
        builder.append(getNumberOfDecodedContents());
        builder.append("]");
        return builder.toString();
    }

}
