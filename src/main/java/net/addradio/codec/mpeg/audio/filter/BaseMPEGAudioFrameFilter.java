/**
 * Class:    BaseMPEGAudioFrameFilter<br/>
 * <br/>
 * Created:  12.11.2017<br/>
 * Filename: BaseMPEGAudioFrameFilter.java<br/>
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
package net.addradio.codec.mpeg.audio.filter;

import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * BaseMPEGAudioFrameFilter.
 */
public abstract class BaseMPEGAudioFrameFilter implements Filter {

    /**
     * apply.
     * @see net.addradio.codec.mpeg.audio.filter.Filter#apply(net.addradio.codec.mpeg.audio.model.MPEGAudioContent)
     * @param content {@link MPEGAudioContent}
     * @return {@link MPEGAudioContent}
     */
    @Override
    public MPEGAudioContent apply(final MPEGAudioContent content) {
        if (content.isMPEGAudioFrame()) {
            applyImpl((MPEGAudioFrame) content);
        }
        return content;
    }

    /**
     * applyImpl.
     * @param frame {@link MPEGAudioFrame}
     */
    abstract protected void applyImpl(MPEGAudioFrame frame);
}
