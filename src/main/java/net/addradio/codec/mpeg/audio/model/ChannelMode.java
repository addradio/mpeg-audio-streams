/**
 * Class:    ChannelMode<br/>
 * <br/>
 * Created:  19.10.2017<br/>
 * Filename: ChannelMode.java<br/>
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
 * ChannelMode.
 */
public enum ChannelMode {

    /** {@link ChannelMode} stereo. */
    Stereo,

    /** 
     * {@link ChannelMode} joint_stereo. In Layer I and II the joint_stereo mode
     * is intensity_stereo, in Layer III it is intensity_stereo and/or 
     * ms_stereo. 
     */
    JointStereo,

    /** {@link ChannelMode} dual_channel. */
    DualChannel,

    /** {@link ChannelMode} single_channel. */
    SingleChannel;

}
