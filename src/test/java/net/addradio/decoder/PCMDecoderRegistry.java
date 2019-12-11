/**
 * Class:    PCMDecoderRegistry<br/>
 * <br/>
 * Created:  02.12.2019<br/>
 * Filename: PCMDecoderRegistry.java<br/>
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

import java.util.HashMap;

import net.addradio.codec.mpeg.audio.model.Layer;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.decoder.layerIII.LayerIIIDecoder;

/**
 * PCMDecoderRegistry.
 */
public final class PCMDecoderRegistry {

    private static HashMap<Layer, PCMDecoder> decoders = new HashMap<>();

    static {
        decoders.put(Layer.III, new LayerIIIDecoder());
    }

    /**
     * decode.
     * @param frame {@link MPEGAudioFrame}
     * @return {@link PCMDecoderResult}
     * @throws DecodingException due to decoding issues.
     */
    public static PCMDecoderResult decode(final MPEGAudioFrame frame) throws DecodingException {
        final PCMDecoder decoder = decoders.get(frame.getLayer());
        if (decoder == null) {
            throw new DecodingException("No decoder found for layer [layer: " + frame.getLayer() + "]"); //$NON-NLS-1$//$NON-NLS-2$
        }
        return decoder.decode(frame);
    }

}
