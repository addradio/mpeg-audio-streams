/**
 * Class:    Version<br/>
 * <br/>
 * Created:  19.10.2017<br/>
 * Filename: Version.java<br/>
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
 * Version.
 */
public enum Version implements BitMaskFlag {

    /** {@link Version} MPEG Version 1. */
    MPEG_1(0b11, 2),

    /** {@link Version} MPEG Version 2. */
    MPEG_2_LSF(0b10, 1),

    /** {@link Version} MPEG Version 2.5. */
    MPEG_2_5(0b00, 1),

    /** {@link Version} reserved. */
    reserved(0b01, -1);

    /** {@code int} bitMask. */
    private int bitMask;

    /** {@link int} maxNumberOfGranules. */
    private int maxNumberOfGranules;

    /**
     * Version constructor.
     *
     * @param bitmaskVal
     *            {@code int}
     * @param maxNumberOfGranulesVal {@code int}
     */
    private Version(final int bitmaskVal, final int maxNumberOfGranulesVal) {
        this.bitMask = bitmaskVal;
        this.maxNumberOfGranules = maxNumberOfGranulesVal;
    }

    /**
     * getBitMask.
     *
     * @return the bitMask
     */
    @Override
    public int getBitMask() {
        return this.bitMask;
    }

    /**
     * getMaxNumberOfGranules.
     * @return int the maxNumberOfGranules
     */
    public int getMaxNumberOfGranules() {
        return this.maxNumberOfGranules;
    }

}
