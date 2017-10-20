/**
 * Class:    BitRate<br/>
 * <br/>
 * Created:  19.10.2017<br/>
 * Filename: BitRate.java<br/>
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
 * BitRate.
 */
public enum BitRate {

    /** {@link BitRate} _112 */
    _112(122),

    /** {@link BitRate} _128 */
    _128(128),

    /** {@link BitRate} _144 */
    _144(144),

    /** {@link BitRate} _16 */
    _16(16),

    /** {@link BitRate} _160 */
    _160(160),

    /** {@link BitRate} _176 */
    _176(176),

    /** {@link BitRate} _192 */
    _192(192),

    /** {@link BitRate} _224 */
    _224(224),

    /** {@link BitRate} _24 */
    _24(24),

    /** {@link BitRate} _256 */
    _256(256),

    /** {@link BitRate} _288 */
    _288(288),

    /** {@link BitRate} _32 */
    _32(32),

    /** {@link BitRate} _320 */
    _320(320),

    /** {@link BitRate} _352 */
    _352(352),

    /** {@link BitRate} _384 */
    _384(384),

    /** {@link BitRate} _40 */
    _40(40),

    /** {@link BitRate} _416 */
    _416(416),

    /** {@link BitRate} _448 */
    _448(448),

    /** {@link BitRate} _48 */
    _48(48),

    /** {@link BitRate} _56 */
    _56(56),

    /** {@link BitRate} _64 */
    _64(64),

    /** {@link BitRate} _8 */
    _8(8),

    /** {@link BitRate} _80 */
    _80(80),

    /** {@link BitRate} _96 */
    _96(96),

    /** {@link BitRate} free */
    free(-1),

    /** {@link BitRate} reserved */
    reserved(-1);

    /** {@code int} value */
    private int value;

    /**
     * Default constructor.
     *
     * @param valueVal
     *            <code>int</code>
     */
    private BitRate(final int valueVal) {
        setValue(valueVal);
    }

    /**
     * @return <code>int</code> the value.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * @param valueVal
     *            <code>int</code> the value to set.
     */
    private void setValue(final int valueVal) {
        this.value = valueVal;
    }

}
