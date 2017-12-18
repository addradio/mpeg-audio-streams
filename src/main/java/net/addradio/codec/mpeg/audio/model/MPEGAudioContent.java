/**
 * Class:    MPEGAudioContent<br/>
 * <br/>
 * Created:  29.10.2017<br/>
 * Filename: MPEGAudioContent.java<br/>
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
package net.addradio.codec.mpeg.audio.model;

/**
 * MPEGAudioContent.
 */
public interface MPEGAudioContent {

    /**
     * isID3Tag.
     * @return {@code true} if content is an ID3Tag
     */
    boolean isID3Tag();

    /**
     * isMPEGAudioFrame.
     * @return {@code true} if content is a {@link MPEGAudioFrame}
     */
    boolean isMPEGAudioFrame();

}