/**
 * Class:    DecodingResultOverContentCollector<br/>
 * <br/>
 * Created:  05.11.2017<br/>
 * Filename: DecodingResultOverContentCollector.java<br/>
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
 * DecodingResultOverContentCollector.
 */
public class DecodingResultOverContentCollector implements DecodingResult {

    /** {@link MPEGAudioContentCollectorHandler} delegate */
    private final MPEGAudioContentCollectorHandler delegate;

    /** {@code long} skippedBits. */
    private final long skippedBits;

    /**
     * DecodingResultOverContentCollector constructor.
     * @param skippedBitsVal {@code long}
     * @param delegateRef {@link MPEGAudioContentCollectorHandler}
     */
    public DecodingResultOverContentCollector(final long skippedBitsVal, final MPEGAudioContentCollectorHandler delegateRef) {
        this.skippedBits = skippedBitsVal;
        this.delegate = delegateRef;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getAudioFramesOnly()
     * @see net.addradio.codec.mpeg.audio.tools.MPEGAudioContentCollectorHandler#getAudioFramesOnly()
     */
    @Override
    public List<MPEGAudioFrame> getAudioFramesOnly() {
        return this.delegate.getAudioFramesOnly();
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getAverageBitRate()
     */
    @Override
    public float getAverageBitRate() {
        return this.delegate.getAverageBitRate();
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getContent()
     */
    @Override
    public List<MPEGAudioContent> getContent() {
        return this.delegate.getAllContents();
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getDurationMillis()
     */
    @Override
    public long getDurationMillis() {
        return this.delegate.getDurationMillis();
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getId3TagsOnly()
     * @see net.addradio.codec.mpeg.audio.tools.MPEGAudioContentCollectorHandler#getId3TagsOnly()
     */
    @Override
    public List<ID3Tag> getId3TagsOnly() {
        return this.delegate.getId3TagsOnly();
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getNumberOfDecodedBytes()
     */
    @Override
    public long getNumberOfDecodedBytes() {
        return this.delegate.getNumberOfCollectedBytes();
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.DecodingResult#getNumberOfDecodedContents()
     */
    @Override
    public int getNumberOfDecodedContents() {
        return this.delegate.getAllContents().size();
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
     * toString.
     * @see java.lang.Object#toString()
     * @return {@link String}
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("DecodingResultOverContentCollector [averageBitRate=");
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
