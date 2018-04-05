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
    _112(112000, 112),

    /** {@link BitRate} _128 */
    _128(128000, 128),

    /** {@link BitRate} _144 */
    _144(144000, 144),

    /** {@link BitRate} _16 */
    _16(16000, 16),

    /** {@link BitRate} _160 */
    _160(160000, 160),

    /** {@link BitRate} _176 */
    _176(176000, 176),

    /** {@link BitRate} _192 */
    _192(192000, 192),

    /** {@link BitRate} _224 */
    _224(224000, 224),

    /** {@link BitRate} _24 */
    _24(24000, 24),

    /** {@link BitRate} _256 */
    _256(256000, 256),

    /** {@link BitRate} _288 */
    _288(288000, 288),

    /** {@link BitRate} _32 */
    _32(32000, 32),

    /** {@link BitRate} _320 */
    _320(320000, 320),

    /** {@link BitRate} _352 */
    _352(352000, 352),

    /** {@link BitRate} _384 */
    _384(384000, 384),

    /** {@link BitRate} _40 */
    _40(40000, 40),

    /** {@link BitRate} _416 */
    _416(416000, 416),

    /** {@link BitRate} _448 */
    _448(448000, 448),

    /** {@link BitRate} _48 */
    _48(48000, 48),

    /** {@link BitRate} _56 */
    _56(56000, 56),

    /** {@link BitRate} _64 */
    _64(64000, 64),

    /** {@link BitRate} _8 */
    _8(8000, 8),

    /** {@link BitRate} _80 */
    _80(80000, 80),

    /** {@link BitRate} _96 */
    _96(96000, 96),

    /** {@link BitRate} free */
    free(-1, -1),

    /** {@link BitRate} reserved */
    reserved(-1, -1);

    /** {@code int} valueInBps */
    private int valueInBps;

    /** {@code int} valueInKbps. */
    private int valueInKbps;

    /**
     * Default constructor.
     *
     * @param valueInBpsVal {@code int}
     * @param valueInKbpsVal {@code int}
     */
    private BitRate(final int valueInBpsVal, final int valueInKbpsVal) {
        setValueInBps(valueInBpsVal);
        setValueInKbps(valueInKbpsVal);
    }

    /**
     * @return <code>int</code> the valueInBps.
     */
    public int getValueInBps() {
        return this.valueInBps;
    }

    /**
     * getValueInKbps.
     * @return {@code int} the valueInKbps
     */
    public int getValueInKbps() {
        return this.valueInKbps;
    }

    /**
     * @param valueVal
     *            <code>int</code> the valueInBps to set.
     */
    private void setValueInBps(final int valueVal) {
        this.valueInBps = valueVal;
    }

    /**
     * setValueInKbps.
     * @param valueInKbpsVal {@code int} the valueInKbps to set
     */
    private void setValueInKbps(final int valueInKbpsVal) {
        this.valueInKbps = valueInKbpsVal;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return Integer.toString(getValueInBps());
    }

}
