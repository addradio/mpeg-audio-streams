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
    _11025(11025),

    /** {@link SamplingRate} COMMENT. */
    _12000(12000),

    /** {@link SamplingRate} COMMENT. */
    _16000(16000),

    /** {@link SamplingRate} COMMENT. */
    _22050(22050),

    /** {@link SamplingRate} COMMENT. */
    _24000(24000),

    /** {@link SamplingRate} COMMENT. */
    _32000(32000),

    /** {@link SamplingRate} COMMENT. */
    _44100(44100),

    /** {@link SamplingRate} COMMENT. */
    _48000(48000),

    /** {@link SamplingRate} COMMENT. */
    _8000(8000),

    /** {@link SamplingRate} COMMENT. */
    reserved(-1);

    /** {@link int} COMMENT. */
    private int value;

    /**
     * Default constructor.
     *
     * @param valueVal <code>int</code>
     */
    private SamplingRate(final int valueVal) {
        setValue(valueVal);
    }

    /**
     * @return <code>int</code> the value.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * @param valueVal <code>int</code> the value to set.
     */
    private void setValue(final int valueVal) {
        this.value = valueVal;
    }

}
