/**
 * Class:    ModeExtension<br/>
 * <br/>
 * Created:  19.10.2017<br/>
 * Filename: ModeExtension.java<br/>
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
 * ModeExtension.
 */
public enum ModeExtension {

    /** {@link ModeExtension} COMMENT. */
    SubBands4To31,

    /** {@link ModeExtension} COMMENT. */
    SubBands8To31,

    /** {@link ModeExtension} COMMENT. */
    SubBands12To31,

    /** {@link ModeExtension} COMMENT. */
    SubBands16To31,

    /** {@link ModeExtension} COMMENT. */
    IntensityStereo_Off__MSStereo_Off,

    /** {@link ModeExtension} COMMENT. */
    IntensityStereo_On__MSStereo_Off,

    /** {@link ModeExtension} COMMENT. */
    IntensityStereo_Off__MSStereo_On,

    /** {@link ModeExtension} COMMENT. */
    IntensityStereo_On__MSStereo_On;

}
