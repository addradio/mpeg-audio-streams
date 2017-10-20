/**
 * Class:    Layer<br/>
 * <br/>
 * Created:  19.10.2017<br/>
 * Filename: Layer.java<br/>
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
 * Layer.
 */
public enum Layer implements BitMaskFlag {

    /** {@link Layer} Layer I. */
    I(0b11),

    /** {@link Layer} Layer II. */
    II(0b10),

    /** {@link Layer} Layer III. */
    III(0b01),

    /** {@link Layer} reserved. */
    reserved(0b00);

    /** {@code int} bitMask. */
    private int bitMask;

    /**
     * Layer constructor.
     *
     * @param bitMaskVal
     *            {@code int}
     */
    private Layer(final int bitMaskVal) {
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
