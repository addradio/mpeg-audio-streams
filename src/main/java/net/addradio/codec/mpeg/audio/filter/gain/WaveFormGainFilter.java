/**
 * Class:    WaveFormGainFilter<br/>
 * <br/>
 * Created:  12.11.2017<br/>
 * Filename: WaveFormGainFilter.java<br/>
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

import net.addradio.codec.mpeg.audio.filter.Filter;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * WaveFormGainFilter.
 */
public abstract class WaveFormGainFilter extends GainFilter implements Filter {

    /** {@link boolean} initialized. */
    private boolean initialized;

    /** {@link float} wavelengthInSecs. */
    private float wavelengthInSecs;

    /** {@link double} x. */
    private double x = 0;

    /** {@link double} deltaX. */
    private double deltaX;

    /**
     * WaveFormGainFilter constructor.
     */
    public WaveFormGainFilter() {
        this.initialized = false;
        setWavelengthInSecs(1);
    }

    /**
     * applyImpl.
     * @see net.addradio.codec.mpeg.audio.filter.BaseMPEGAudioFrameFilter#applyImpl(net.addradio.codec.mpeg.audio.model.MPEGAudioFrame)
     * @param frame {@link MPEGAudioFrame}
     */
    @Override
    protected void applyImpl(final MPEGAudioFrame frame) {
        if (!this.initialized) {
            int frameLength = frame.getFrameLength();
            if (frameLength > -1) {
                this.deltaX = 2 * Math.PI
                        * (((frameLength * 8d) / frame.getBitRate().getValue()) / getWavelengthInSecs());
                this.initialized = true;
            }
        }
        applyFactorToGlobalGain(frame, Math.abs(calculateWaveFormPart(this.x)));
        this.x += this.deltaX;
    }

    /**
     * calculateWaveFormPart.
     * @param xVal {@code double}
     * @return {@code double}
     */
    protected abstract double calculateWaveFormPart(final double xVal);

    /**
     * getWavelengthInSecs.
     * @return float the wavelengthInSecs
     */
    public float getWavelengthInSecs() {
        return this.wavelengthInSecs;
    }

    /**
     * setWavelengthInSecs.
     * @param wavelengthInSecsVal float the wavelengthInSecs to set
     */
    public void setWavelengthInSecs(final float wavelengthInSecsVal) {
        this.wavelengthInSecs = wavelengthInSecsVal;
        this.initialized = false;
    }

}
