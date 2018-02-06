/**
 * Class:    MPEGAudioContentCollectorHandler<br/>
 * <br/>
 * Created:  03.11.2017<br/>
 * Filename: MPEGAudioContentCollectorHandler.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2012 - All rights reserved.
 */

package net.addradio.codec.mpeg.audio.tools;

import java.util.LinkedList;
import java.util.List;

import net.addradio.codec.id3.model.ID3Tag;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * MPEGAudioContentCollectorHandler
 */
public class MPEGAudioContentCollectorHandler implements MPEGAudioContentHandler {

    /** {@link List}{@code <}{@link MPEGAudioContent}{@code >} allContents */
    final List<MPEGAudioContent> allContents = new LinkedList<>();

    /** {@link List}{@code <}{@link MPEGAudioFrame}{@code >} audioFramesOnly */
    final List<MPEGAudioFrame> audioFramesOnly = new LinkedList<>();

    /** {@link float} averageBitRate */
    private transient float averageBitRate = 0;

    /** {@code long} durationMillis. */
    private transient long durationMillis;

    /** {@code long} numberOfCollectedBytes. */
    private transient long numberOfCollectedBytes;

    /** {@link List}{@code <}{@link ID3Tag}{@code >} id3TagsOnly */
    final List<ID3Tag> id3TagsOnly = new LinkedList<>();

    /** {@code int} mpegaFrameCount */
    private transient int mpegaFrameCount = 0;

    /**
     * MPEGAudioContentCollectorHandler constructor.
     */
    public MPEGAudioContentCollectorHandler() {
    }

    /**
     * @return the {@link List}{@code <}{@link MPEGAudioContent}{@code >} allContents
     */
    public List<MPEGAudioContent> getAllContents() {
        return this.allContents;
    }

    /**
     * @return the {@link List}{@code <}{@link MPEGAudioFrame}{@code >} audioFramesOnly
     */
    public List<MPEGAudioFrame> getAudioFramesOnly() {
        return this.audioFramesOnly;
    }

    /**
     * @return the {@code float} averageBitRate
     */
    public float getAverageBitRate() {
        return this.averageBitRate;
    }

    /**
     * getDurationMillis.
     * @return {@code long} the durationMillis
     */
    public long getDurationMillis() {
        return this.durationMillis;
    }

    /**
     * @return the {@link List}{@code <}{@link ID3Tag}{@code >} id3TagsOnly
     */
    public List<ID3Tag> getId3TagsOnly() {
        return this.id3TagsOnly;
    }

    /**
     * getNumberOfCollectedBytes.
     * @return long the numberOfCollectedBytes
     */
    public long getNumberOfCollectedBytes() {
        return this.numberOfCollectedBytes;
    }

    /**
     * handle.
     * @see net.addradio.codec.mpeg.audio.tools.MPEGAudioContentHandler#handle(net.addradio.codec.mpeg.audio.model.MPEGAudioContent)
     * @param content {@link MPEGAudioContent}
     * @return {@code boolean} {@code false} always.
     */
    @Override
    public boolean handle(final MPEGAudioContent content) {
        if (content != null) {
            if (MPEGAudioContentFilter.MPEG_AUDIO_FRAMES.accept(content)) {
                final MPEGAudioFrame mpegAudioFrame = (MPEGAudioFrame) content;
                final long durMillis = mpegAudioFrame.calculateDurationMillis();
                if (durMillis > -1) {
                    this.allContents.add(mpegAudioFrame);
                    this.audioFramesOnly.add(mpegAudioFrame);
                    this.mpegaFrameCount++;
                    this.durationMillis += durMillis;
                    this.numberOfCollectedBytes += mpegAudioFrame.getFrameLength();
                    this.averageBitRate = ((this.averageBitRate * (this.mpegaFrameCount - 1))
                            + mpegAudioFrame.getBitRate().getValue()) / this.mpegaFrameCount;
                }
            } else if (MPEGAudioContentFilter.ID3_TAGS.accept(content)) {
                this.allContents.add(content);
                this.id3TagsOnly.add((ID3Tag) content);
                this.numberOfCollectedBytes += ((ID3Tag) content).getOverallSize();
            } else {
                // SEBASTIAN take care
                this.allContents.add(content);
            }
        }
        return false;
    }

}
