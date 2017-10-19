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

    /** {@link BitRate} COMMENT. */
    _112(122),

    /** {@link BitRate} COMMENT. */
    _128(128),

    /** {@link BitRate} COMMENT. */
    _144(144),

    /** {@link BitRate} COMMENT. */
    _16(16),

    /** {@link BitRate} COMMENT. */
    _160(160),

    /** {@link BitRate} COMMENT. */
    _176(176),

    /** {@link BitRate} COMMENT. */
    _192(192),

    /** {@link BitRate} COMMENT. */
    _224(224),

    /** {@link BitRate} COMMENT. */
    _24(24),

    /** {@link BitRate} COMMENT. */
    _256(256),

    /** {@link BitRate} COMMENT. */
    _288(288),

    /** {@link BitRate} COMMENT. */
    _32(32),

    /** {@link BitRate} COMMENT. */
    _320(320),

    /** {@link BitRate} COMMENT. */
    _352(352),

    /** {@link BitRate} COMMENT. */
    _384(384),

    /** {@link BitRate} COMMENT. */
    _40(40),

    /** {@link BitRate} COMMENT. */
    _416(416),

    /** {@link BitRate} COMMENT. */
    _448(448),

    /** {@link BitRate} COMMENT. */
    _48(48),

    /** {@link BitRate} COMMENT. */
    _56(56),

    /** {@link BitRate} COMMENT. */
    _64(64),

    /** {@link BitRate} COMMENT. */
    _8(8),

    /** {@link BitRate} COMMENT. */
    _80(80),

    /** {@link BitRate} COMMENT. */
    _96(96),

    /** {@link BitRate} COMMENT. */
    Free(-1);

    /** {@link int} COMMENT. */
    private int value;

    /**
     * Default constructor.
     *
     * @param valueVal <code>int</code>
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
     * @param valueVal <code>int</code> the value to set.
     */
    private void setValue(final int valueVal) {
        this.value = valueVal;
    }

}
