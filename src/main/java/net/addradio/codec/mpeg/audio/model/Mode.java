/**
 * Class:    Mode<br/>
 * <br/>
 * Created:  19.10.2017<br/>
 * Filename: Mode.java<br/>
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
 * Mode.
 */
public enum Mode implements BitMaskFlag {

    /** {@link Mode} stereo. */
    Stereo(0b00),

    /**
     * {@link ChannelMode} joint_stereo. In Layer I and II the joint_stereo mode
     * is intensity_stereo, in Layer III it is intensity_stereo and/or
     * ms_stereo.
     */
    JointStereo(0b01),

    /** {@link ChannelMode} dual_channel. */
    DualChannel(0b10),

    /** {@link ChannelMode} single_channel. */
    SingleChannel(0b11);

    /** {@code int} bitMask. */
    private int bitMask;

    /**
     * ChannelMode constructor.
     * 
     * @param bitMaskVal
     *            {@code int}
     */
    private Mode(final int bitMaskVal) {
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
