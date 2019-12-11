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
    SubBands4To31(false, false),

    /** {@link ModeExtension} COMMENT. */
    SubBands8To31(false, false),

    /** {@link ModeExtension} COMMENT. */
    SubBands12To31(false, false),

    /** {@link ModeExtension} COMMENT. */
    SubBands16To31(false, false),

    /** {@link ModeExtension} COMMENT. */
    IntensityStereo_Off__MSStereo_Off(false, false),

    /** {@link ModeExtension} COMMENT. */
    IntensityStereo_On__MSStereo_Off(false, true),

    /** {@link ModeExtension} COMMENT. */
    IntensityStereo_Off__MSStereo_On(true, false),

    /** {@link ModeExtension} COMMENT. */
    IntensityStereo_On__MSStereo_On(true, true);

    /** {@link boolean} MSStereoOn. */
    private final boolean MSStereoOn;

    /** {@link boolean} intensityStereoOn. */
    private final boolean intensityStereoOn;

    /**
     * ModeExtension constructor.
     * @param isMSStereoOnVal {@code boolean}
     * @param intensityStereoOnVal {@code boolean}
     */
    private ModeExtension(final boolean isMSStereoOnVal, final boolean intensityStereoOnVal) {
        this.MSStereoOn = isMSStereoOnVal;
        this.intensityStereoOn = intensityStereoOnVal;
    }

    /**
     * isIntensityStereoOn.
     * @return boolean the intensityStereoOn
     */
    public boolean isIntensityStereoOn() {
        return this.intensityStereoOn;
    }

    /**
     * isMSStereoOn.
     * @return boolean the mSStereoOn
     */
    public boolean isMSStereoOn() {
        return this.MSStereoOn;
    }

}
