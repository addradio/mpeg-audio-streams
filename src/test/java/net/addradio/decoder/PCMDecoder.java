/**
 * Class:    PCMDecoder<br/>
 * <br/>
 * Created:  02.12.2019<br/>
 * Filename: PCMDecoder.java<br/>
 * Version:  $Revision$<br/>
 * <br/>
 * last modified on $Date$<br/>
 *               by $Author$<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author$ -- $Revision$ -- $Date$
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2019 - All rights reserved.
 */
package net.addradio.decoder;

import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * PCMDecoder.
 */
public interface PCMDecoder {

    /**
     * decode.
     * @param frame {@link MPEGAudioFrame}
     * @return {@link DefaultDecoderResult}
     */
    PCMDecoderResult decode(MPEGAudioFrame frame);

}
