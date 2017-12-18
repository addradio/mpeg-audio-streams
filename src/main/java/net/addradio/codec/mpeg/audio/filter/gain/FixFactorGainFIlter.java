/**
 * Class:    FixFactorGainFIlter<br/>
 * <br/>
 * Created:  12.11.2017<br/>
 * Filename: FixFactorGainFIlter.java<br/>
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

import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * FixFactorGainFIlter.
 */
public class FixFactorGainFIlter extends GainFilter {

    /** {@link double} factor. */
    private final double factor;

    /**
     * FixFactorGainFIlter constructor.
     * @param factorVal {@code double}
     */
    public FixFactorGainFIlter(final double factorVal) {
        this.factor = factorVal;
    }

    /**
     * applyImpl.
     * @see net.addradio.codec.mpeg.audio.filter.BaseMPEGAudioFrameFilter#applyImpl(net.addradio.codec.mpeg.audio.model.MPEGAudioFrame)
     * @param frame
     */
    @Override
    protected void applyImpl(final MPEGAudioFrame frame) {
        applyFactorToGlobalGain(frame, this.factor);
    }

}
