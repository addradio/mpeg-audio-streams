/**
 * Class:    DefaultDecodingResult<br/>
 * <br/>
 * Created:  12.03.2019<br/>
 * Filename: DefaultDecodingResult.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2018 - All rights reserved.
 */

package net.addradio.codec.mpeg.audio;

import java.util.LinkedList;
import java.util.List;

import net.addradio.codec.id3.model.ID3Tag;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * DefaultDecodingResult
 */
public class DefaultDecodingResult implements DecodingResult {

    /** {@link List}{@code <MPEGAudioFrame>} audioFrames */
    private List<MPEGAudioFrame> audioFrames;

    /** {@code float} averageBitRate */
    private float averageBitRate = -1;

    /** {@code long} durationMillis */
    private long durationMillis = -1;

    /** {@link List}{@code <ID3Tag>} id3Tags */
    private List<ID3Tag> id3Tags;

    /** {@code long} numberOfDecodedBytes */
    private long numberOfDecodedBytes;

    /** {@code long} skippedBits */
    private long skippedBits = -1;

    /**
     * DefaultDecodingResult constructor.
     */
    public DefaultDecodingResult() {
    }

    /**
     * DefaultDecodingResult constructor.
     * @param toCopyFrom {@link DecodingResult}
     */
    public DefaultDecodingResult(final DecodingResult toCopyFrom) {
        setAudioFrames(new LinkedList<>(toCopyFrom.getAudioFrames()));
        setAverageBitRate(toCopyFrom.getAverageBitRate());
        setDurationMillis(toCopyFrom.getDurationMillis());
        setId3Tags(toCopyFrom.getId3Tags());
        setNumberOfDecodedBytes(toCopyFrom.getNumberOfDecodedBytes());
        setSkippedBits(toCopyFrom.getSkippedBits());
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getAudioFrames()
     */
    @Override
    public List<MPEGAudioFrame> getAudioFrames() {
        return this.audioFrames;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getAverageBitRate()
     */
    @Override
    public float getAverageBitRate() {
        return this.averageBitRate;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getDurationMillis()
     */
    @Override
    public long getDurationMillis() {
        return this.durationMillis;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getId3Tags()
     */
    @Override
    public List<ID3Tag> getId3Tags() {
        return this.id3Tags;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getNumberOfDecodedBytes()
     */
    @Override
    public long getNumberOfDecodedBytes() {
        return this.numberOfDecodedBytes;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getNumberOfDecodedContents()
     */
    @Override
    public int getNumberOfDecodedContents() {
        return (this.id3Tags != null ? this.id3Tags.size() : 0)
                + (this.audioFrames != null ? this.audioFrames.size() : 0);
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getSkippedBits()
     */
    @Override
    public long getSkippedBits() {
        return this.skippedBits;
    }

    /**
     * @param audioFramesRef {@link List}{@code <MPEGAudioFrame>} the audioFrames to set
     */
    public void setAudioFrames(final List<MPEGAudioFrame> audioFramesRef) {
        this.audioFrames = audioFramesRef;
    }

    /**
     * @param averageBitRateVal {@code float} the averageBitRate to set
     */
    public void setAverageBitRate(final float averageBitRateVal) {
        this.averageBitRate = averageBitRateVal;
    }

    /**
     * @param durationMillisVal {@code long} the durationMillis to set
     */
    public void setDurationMillis(final long durationMillisVal) {
        this.durationMillis = durationMillisVal;
    }

    /**
     * @param id3TagsRef {@link List}{@code <ID3Tag>} the id3Tags to set
     */
    public void setId3Tags(final List<ID3Tag> id3TagsRef) {
        this.id3Tags = id3TagsRef;
    }

    /**
     * @param numberOfDecodedBytesVal {@code long} the numberOfDecodedBytes to set
     */
    public void setNumberOfDecodedBytes(final long numberOfDecodedBytesVal) {
        this.numberOfDecodedBytes = numberOfDecodedBytesVal;
    }

    /**
     * @param skippedBitsVal {@code long} the skippedBits to set
     */
    public void setSkippedBits(final long skippedBitsVal) {
        this.skippedBits = skippedBitsVal;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#toString()
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("DefaultDecodingResult [averageBitRate:");
        builder.append(this.averageBitRate);
        builder.append(", durationMillis:");
        builder.append(this.durationMillis);
        builder.append(", numberOfDecodedBytes:");
        builder.append(this.numberOfDecodedBytes);
        builder.append(", skippedBits:");
        builder.append(this.skippedBits);
        builder.append(", getNumberOfDecodedContents():");
        builder.append(getNumberOfDecodedContents());
        builder.append("]");
        return builder.toString();
    }

}
