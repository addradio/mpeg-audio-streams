/**
 * Class:    SamplingRateCodec<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: SamplingRateCodec.java<br/>
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

package net.addradio.codec.mpeg.audio.codecs;

import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.model.SamplingRate;

/**
 * SamplingRateCodec
 */
public final class SamplingRateCodec {

    /**
     * SamplingRateCodec constructor.
     */
    private SamplingRateCodec() {
    }

    /**
     * decode.
     * 
     * @param frame
     *            {@link MPEGAudioFrame}
     * @param value
     *            {@code int}
     * @return {@link SamplingRate}
     * @throws MPEGAudioCodecException
     *             if value could not be decoded.
     */
    public static final SamplingRate decode(MPEGAudioFrame frame, final int value) throws MPEGAudioCodecException {
        throw new MPEGAudioCodecException("Could not decode SamplingRate [valueToBeDecoded: " + value //$NON-NLS-1$
                + "]."); //$NON-NLS-1$
    }

}
