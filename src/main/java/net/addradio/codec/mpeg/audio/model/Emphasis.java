/**
 * Class:    Emphasis<br/>
 * <br/>
 * Created:  19.10.2017<br/>
 * Filename: Emphasis.java<br/>
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
 * Emphasis.
 */
public enum Emphasis implements BitMaskFlag {

    /** {@link Emphasis} _50_15ms. */
    _50_15ms(0b01),

    /** {@link Emphasis} ITU_T_J_17. */
    ITU_T_J_17(0b11),

    /** {@link Emphasis} none. */
    none(0b00),

    /** {@link Emphasis} reserved. */
    reserved(0b10);

    /** {@code int} bitMask. */
    private int bitMask;

    /**
     * Emphasis constructor.
     *
     * @param bitMaskVal
     *            {@code int}
     */
    private Emphasis(final int bitMaskVal) {
        this.bitMask = bitMaskVal;
    }

    /**
     * getBitMask.
     *
     * @see net.addradio.codec.mpeg.audio.model.BitMaskFlag#getBitMask()
     * @return {@code int}
     */
    @Override
    public int getBitMask() {
        return this.bitMask;
    }

}
