/**
 * Class:    SamplingRate<br/>
 * <br/>
 * Created:  19.10.2017<br/>
 * Filename: SamplingRate.java<br/>
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
 * SamplingRate.
 */
public enum SamplingRate {

    /** {@link SamplingRate} COMMENT. */
    _11025(11025, 11.025f),

    /** {@link SamplingRate} COMMENT. */
    _12000(12000, 12f),

    /** {@link SamplingRate} COMMENT. */
    _16000(16000, 16f),

    /** {@link SamplingRate} COMMENT. */
    _22050(22050, 22.05f),

    /** {@link SamplingRate} COMMENT. */
    _24000(24000, 24f),

    /** {@link SamplingRate} COMMENT. */
    _32000(32000, 32f),

    /** {@link SamplingRate} COMMENT. */
    _44100(44100, 44.1f),

    /** {@link SamplingRate} COMMENT. */
    _48000(48000, 48f),

    /** {@link SamplingRate} COMMENT. */
    _8000(8000, 8f),

    /** {@link SamplingRate} COMMENT. */
    reserved(-1, -1f);

    /** {@link int} COMMENT. */
    private int valueInHz;

    /** {@link float} valueInKHz */
    private float valueInKHz;

    /**
     * Default constructor.
     *
     * @param valueVal <code>int</code>
     * @param valueInKHzVal {@code float}
     */
    private SamplingRate(final int valueVal, final float valueInKHzVal) {
        setValueInHz(valueVal);
        setValueInKHz(valueInKHzVal);
    }

    /**
     * @return <code>int</code> the valueInHz.
     */
    public int getValueInHz() {
        return this.valueInHz;
    }

    /**
     * @return the {@code float} valueInKHz
     */
    public float getValueInKHz() {
        return this.valueInKHz;
    }

    /**
     * @param valueVal <code>int</code> the valueInHz to set.
     */
    private void setValueInHz(final int valueVal) {
        this.valueInHz = valueVal;
    }

    /**
     * @param valueInKHzVal {@link float} the valueInKHz to set
     */
    private void setValueInKHz(final float valueInKHzVal) {
        this.valueInKHz = valueInKHzVal;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return Integer.toString(getValueInHz());
    }

}
