/**
 * Class:    MPEGAudioContentFilter<br/>
 * <br/>
 * Created:  03.11.2017<br/>
 * Filename: MPEGAudioContentFilter.java<br/>
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

import net.addradio.codec.id3.model.ID3Tag;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * MPEGAudioContentFilter
 */
public interface MPEGAudioContentFilter {

    /** {@link MPEGAudioContentFilter} ACCEPT_ALL */
    public static final MPEGAudioContentFilter ACCEPT_ALL = new MPEGAudioContentFilter() {
        @Override
        public boolean accept(final MPEGAudioContent content) {
            return true;
        }
    };

    /** {@link MPEGAudioContentFilter} ID3_TAGS */
    public static final MPEGAudioContentFilter ID3_TAGS = new MPEGAudioContentFilter() {
        @Override
        public boolean accept(final MPEGAudioContent content) {
            return ID3Tag.class.isAssignableFrom(content.getClass());
        }
    };

    /** {@link MPEGAudioContentFilter} MPEG_AUDIO_FRAMES */
    public static final MPEGAudioContentFilter MPEG_AUDIO_FRAMES = new MPEGAudioContentFilter() {
        @Override
        public boolean accept(final MPEGAudioContent content) {
            return (content instanceof MPEGAudioFrame);
        }
    };

    /**
     * accept.
     * @param content {@link MPEGAudioContent}
     * @return {@code boolean} {@code true} if content shall be accepted.
     */
    boolean accept(MPEGAudioContent content);

}
