/**
 * Class:    GainFilter<br/>
 * <br/>
 * Created:  12.11.2017<br/>
 * Filename: GainFilter.java<br/>
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
package net.addradio.codec.mpeg.audio.filter.gain;

import net.addradio.codec.mpeg.audio.filter.BaseMPEGAudioFrameFilter;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * GainFilter.
 */
public abstract class GainFilter extends BaseMPEGAudioFrameFilter {

    /**
     * applyFactorToGlobalGain.
     * @param frame {@link MPEGAudioFrame}
     * @param factor {@code double}
     */
    protected static void applyFactorToGlobalGain(final MPEGAudioFrame frame, final double factor) {
        for (int i = 0; i < frame.getGlobalGain().length; i++) {
            for (int j = 0; j < frame.getGlobalGain()[i].length; j++) {
                frame.getGlobalGain()[i][j] = (int) Math.round(((factor * 0.35) + 0.65) * frame.getGlobalGain()[i][j]);
            }
        }
    }

}
