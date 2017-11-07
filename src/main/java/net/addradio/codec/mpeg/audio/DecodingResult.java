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

    /** {@link float} averageBitRate */
    private float averageBitRate = -1;

    /** {@link List}{@code <}{@link MPEGAudioContent}{@code >} content. */
    private List<MPEGAudioContent> content;

    /** {@code long} skippedBits. */
    private long skippedBits;

    /**
    * DecodingResult constructor.
    */
    public DecodingResult() {
    }

    /**
     * DecodingResult constructor.
     * @param skippedBitsVal {@code long}
     * @param averageBitRateVal {@code float}
     * @param contentRef {@link List}{@code <}{@link MPEGAudioContent}{@code >}
     */
    public DecodingResult(final long skippedBitsVal, final float averageBitRateVal,
            final List<MPEGAudioContent> contentRef) {
        setSkippedBits(skippedBitsVal);
        setAverageBitRate(averageBitRateVal);
        setContent(contentRef);
    }

    /**
     * @return the {@link float} averageBitRate or {@code -1} if average bitrate has not been calculated so far.
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
     * getSkippedBits.
     * @return {@code long} the skippedBits
     */
    public long getSkippedBits() {
        return this.skippedBits;
    }

    /**
     * @param averageBitRateVal {@link float} the averageBitRate to set
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
     * setSkippedBits.
     * @param skippedBitsVal {@code long} the skippedBits to set
     */
    public void setSkippedBits(final long skippedBitsVal) {
        this.skippedBits = skippedBitsVal;
    }

}
