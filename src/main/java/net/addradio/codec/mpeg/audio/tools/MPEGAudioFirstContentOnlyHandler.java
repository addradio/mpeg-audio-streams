/**
 * Class:    MPEGAudioFirstContentOnlyHandler<br/>
 * <br/>
 * Created:  03.11.2017<br/>
 * Filename: MPEGAudioFirstContentOnlyHandler.java<br/>
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

import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;

/**
 * MPEGAudioFirstContentOnlyHandler
 */
public class MPEGAudioFirstContentOnlyHandler implements MPEGAudioContentHandler {

    /** {@link MPEGAudioContent} firstContent */
    private MPEGAudioContent firstContent;

    /**
     * MPEGAudioFirstContentOnlyHandler constructor.
     */
    public MPEGAudioFirstContentOnlyHandler() {
    }

    /**
     * @return the {@link MPEGAudioContent} firstContent
     */
    public MPEGAudioContent getFirstContent() {
        return this.firstContent;
    }

    /**
     * handle.
     * @see net.addradio.codec.mpeg.audio.tools.MPEGAudioContentHandler#handle(net.addradio.codec.mpeg.audio.model.MPEGAudioContent)
     * @param content {@link MPEGAudioContent}
     * @return {@code true} after first content was successfully set.
     */
    @Override
    public boolean handle(final MPEGAudioContent content) {
        if (content != null) {
            this.firstContent = content;
            return true;
        }
        return false;
    }

}
