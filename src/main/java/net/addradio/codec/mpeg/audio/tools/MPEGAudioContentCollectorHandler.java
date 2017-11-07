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

import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * MPEGAudioContentCollectorHandler
 */
public class MPEGAudioContentCollectorHandler implements MPEGAudioContentHandler {

    /** {@link float} averageBitRate */
    private transient float averageBitRate = 0;

    /** {@link List}{@code <}{@link MPEGAudioContent}{@code >} contents */
    final List<MPEGAudioContent> contents = new LinkedList<>();

    /** {@link int} mpegaFrameCount */
    private transient int mpegaFrameCount = 0;

    /**
     * MPEGAudioContentCollectorHandler constructor.
     */
    public MPEGAudioContentCollectorHandler() {
    }

    /**
     * @return the {@code float} averageBitRate
     */
    public float getAverageBitRate() {
        return this.averageBitRate;
    }

    /**
     * @return the {@link List}{@code <}{@link MPEGAudioContent}{@code >} contents
     */
    public List<MPEGAudioContent> getContents() {
        return this.contents;
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
            this.contents.add(content);
            if (content instanceof MPEGAudioFrame) {
                this.mpegaFrameCount++;
                this.averageBitRate = ((this.averageBitRate * (this.mpegaFrameCount - 1))
                        + ((MPEGAudioFrame) content).getBitRate().getValue()) / this.mpegaFrameCount;
            }
        }
        return false;
    }

}
