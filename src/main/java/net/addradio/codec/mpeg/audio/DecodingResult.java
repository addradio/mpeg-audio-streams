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

import net.addradio.codec.id3.model.ID3Tag;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.tools.MPEGAudioContentCollectorHandler;

/**
 * DecodingResult.
 */
public class DecodingResult {

    /** {@link MPEGAudioContentCollectorHandler} delegate */
    private final MPEGAudioContentCollectorHandler delegate;

    /** {@code long} skippedBits. */
    private final long skippedBits;

    /**
     * DecodingResult constructor.
     * @param skippedBitsVal {@code long}
     * @param delegateRef {@link MPEGAudioContentCollectorHandler}
     */
    public DecodingResult(final long skippedBitsVal, final MPEGAudioContentCollectorHandler delegateRef) {
        this.skippedBits = skippedBitsVal;
        this.delegate = delegateRef;
    }

    /**
     * @return {@link List}{@code <}{@link MPEGAudioFrame}{@code >}
     * @see net.addradio.codec.mpeg.audio.tools.MPEGAudioContentCollectorHandler#getAudioFramesOnly()
     */
    public List<MPEGAudioFrame> getAudioFramesOnly() {
        return this.delegate.getAudioFramesOnly();
    }

    /**
     * @return the {@code float} averageBitRate or {@code -1} if average bitrate has not been calculated so far.
     */
    public float getAverageBitRate() {
        return this.delegate.getAverageBitRate();
    }

    /**
     * getContent.
     * @return {@link List}{@code <}{@link MPEGAudioContent}{@code >} the content
     */
    public List<MPEGAudioContent> getContent() {
        return this.delegate.getAllContents();
    }

    /**
     * getDurationMillis.
     * @return long the durationMillis
     */
    public long getDurationMillis() {
        return this.delegate.getDurationMillis();
    }

    /**
     * @return {@link List}{@code <}{@link ID3Tag}{@code >}
     * @see net.addradio.codec.mpeg.audio.tools.MPEGAudioContentCollectorHandler#getId3TagsOnly()
     */
    public List<ID3Tag> getId3TagsOnly() {
        return this.delegate.getId3TagsOnly();
    }

    /**
     * getNumberOfDecodedBytes.
     * @return {@code long}
     */
    public long getNumberOfDecodedBytes() {
        return this.delegate.getNumberOfCollectedBytes();
    }

    /**
     * getNumberOfDecodedContents.
     * @return {@code int}
     */
    public int getNumberOfDecodedContents() {
        return this.delegate.getAllContents().size();
    }

    /**
     * getSkippedBits.
     * @return {@code long} the skippedBits
     */
    public long getSkippedBits() {
        return this.skippedBits;
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
        builder.append(getAverageBitRate());
        builder.append(", durationMillis=");
        builder.append(getDurationMillis());
        builder.append(", skippedBits=");
        builder.append(this.skippedBits);
        builder.append(", getNumberOfDecodedContents()=");
        builder.append(getNumberOfDecodedContents());
        builder.append("]");
        return builder.toString();
    }

}
