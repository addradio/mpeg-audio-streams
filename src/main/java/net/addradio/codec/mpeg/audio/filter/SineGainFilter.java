/**
 * Class:    SineGainFilter<br/>
 * <br/>
 * Created:  12.11.2017<br/>
 * Filename: SineGainFilter.java<br/>
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
package net.addradio.codec.mpeg.audio.filter;

import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;

/**
 * SineGainFilter.
 */
public class SineGainFilter extends BaseMPEGAudioFrameFilter implements Filter {

    /** {@link boolean} initialized. */
    private boolean initialized;

    /** {@link float} wavelengthInSecs. */
    private float wavelengthInSecs;

    /** {@link double} x. */
    private double x = 0;

    /** {@link double} deltaX. */
    private double deltaX;

    /**
     * SineGainFilter constructor.
     */
    public SineGainFilter() {
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
            this.deltaX = 2 * Math.PI
                    * (((frame.getFrameLength() * 8f) / frame.getBitRate().getValue()) / getWavelengthInSecs());
            this.initialized = true;
        }
        final double sinePart = Math.abs(Math.sin(this.x));
        for (int i = 0; i < frame.getGlobalGain().length; i++) {
            for (int j = 0; j < frame.getGlobalGain()[i].length; j++) {
                frame.getGlobalGain()[i][j] = (int) Math.round(sinePart * frame.getGlobalGain()[i][j]);
            }
        }
        this.x += this.deltaX;
    }

    /**
     * getWavelengthInSecs.
     * @return float the wavelengthInSecs
     */
    public float getWavelengthInSecs() {
        return this.wavelengthInSecs;
    }

    /**
     * setWavelengthInSecs.
     * @param wavelengthInSecs float the wavelengthInSecs to set
     */
    public void setWavelengthInSecs(final float wavelengthInSecs) {
        this.wavelengthInSecs = wavelengthInSecs;
        this.initialized = false;
    }

}
