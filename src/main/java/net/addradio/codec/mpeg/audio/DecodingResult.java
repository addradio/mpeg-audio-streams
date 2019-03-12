/**
 * Class:    DecodingResult<br/>
 * <br/>
 * Created:  07.05.2018<br/>
 * Filename: DecodingResult.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2018 - All rights reserved.
 */

package net.addradio.codec.mpeg.audio;

import java.util.List;

import net.addradio.codec.id3.model.ID3Tag;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * DecodingResult
 */
public interface DecodingResult {

    /**
     * @return {@link List}{@code <}{@link MPEGAudioFrame}{@code >}
     */
    List<MPEGAudioFrame> getAudioFrames();

    /**
     * @return the {@code float} averageBitRate or {@code -1} if average bitrate has not been calculated so far.
     */
    float getAverageBitRate();

    /**
     * getDurationMillis.
     * @return long the durationMillis
     */
    long getDurationMillis();

    /**
     * @return {@link List}{@code <}{@link ID3Tag}{@code >}
     */
    List<ID3Tag> getId3Tags();

    /**
     * getNumberOfDecodedBytes.
     * @return {@code long}
     */
    long getNumberOfDecodedBytes();

    /**
     * getNumberOfDecodedContents.
     * @return {@code int}
     */
    int getNumberOfDecodedContents();

    /**
     * getSkippedBits.
     * @return {@code long} the skippedBits
     */
    long getSkippedBits();

}
