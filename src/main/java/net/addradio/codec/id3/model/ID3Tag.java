/**
 * Class:    ID3Tag<br/>
 * <br/>
 * Created:  01.11.2017<br/>
 * Filename: ID3Tag.java<br/>
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

package net.addradio.codec.id3.model;

import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;

/**
 * ID3Tag
 */
public abstract class ID3Tag implements MPEGAudioContent {

    /**
     * getOverallSize.
     * @return {@code int} the tag's overall size (incl. header) in {@code bytes}
     */
    public abstract int getOverallSize();

    /**
     * isID3Tag.
     * @see net.addradio.codec.mpeg.audio.model.MPEGAudioContent#isID3Tag()
     * @return {@code boolean true} always.
     */
    @Override
    public boolean isID3Tag() {
        return true;
    }

    /**
     * isMPEGAudioFrame.
     * @see net.addradio.codec.mpeg.audio.model.MPEGAudioContent#isMPEGAudioFrame()
     * @return {@code boolean false} always.
     */
    @Override
    public boolean isMPEGAudioFrame() {
        return false;
    }

}
