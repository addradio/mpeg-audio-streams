/**
 * Class:    SineGainFilter<br/>
 * <br/>
 * Created:  13.11.2017<br/>
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
package net.addradio.codec.mpeg.audio.filter.gain;

/**
 * SineGainFilter.
 */
public class SineGainFilter extends WaveFormGainFilter {

    /**
     * calculateWaveFormPart.
     * @see net.addradio.codec.mpeg.audio.filter.gain.WaveFormGainFilter#calculateWaveFormPart(double)
     * @param xVal {@code double}
     * @return {@code double}
     */
    @Override
    protected double calculateWaveFormPart(final double xVal) {
        return Math.sin(xVal);
    }

}
