/**
 * Class:    Genre<br/>
 * <br/>
 * Created:  29.10.2017<br/>
 * Filename: Genre.java<br/>
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
package net.addradio.codec.id3.model.v1;

import net.addradio.codec.mpeg.audio.model.BitMaskFlag;

/**
 * Genre.
 */
public enum Genre implements BitMaskFlag {

    /** {@link Genre} undefined. */
    undefined(0b0);

    /** {@code int} bitMask. */
    private int bitMask;

    /**
     * Genre constructor.
     *
     * @param bitMaskVal
     *            {@code int}
     */
    private Genre(final int bitMaskVal) {
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
