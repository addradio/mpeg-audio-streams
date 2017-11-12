/**
 * Class:    Filter<br/>
 * <br/>
 * Created:  12.11.2017<br/>
 * Filename: Filter.java<br/>
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

/**
 * Filter.
 */
public interface Filter {

    /** {@link Filter} DO_NOTHING. */
    public static final Filter DO_NOTHING = new Filter() {
        @Override
        public MPEGAudioContent apply(final MPEGAudioContent content) {
            return content;
        }
    };

    /**
     * apply.
     * @param content {@link MPEGAudioContent}
     * @return {@link MPEGAudioContent}
     */
    MPEGAudioContent apply(MPEGAudioContent content);

}
