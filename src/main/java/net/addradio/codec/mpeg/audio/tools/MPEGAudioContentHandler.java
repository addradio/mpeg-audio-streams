/**
 * Class:    MPEGAudioContentHandler<br/>
 * <br/>
 * Created:  03.11.2017<br/>
 * Filename: MPEGAudioContentHandler.java<br/>
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
 * MPEGAudioContentHandler
 */
public interface MPEGAudioContentHandler {

    /**
     * handle.
     * @param content {@link MPEGAudioContent}
     * @return {@code boolean} {@code true} if handler has finished and should not be called again.
     */
    boolean handle(MPEGAudioContent content);

}
