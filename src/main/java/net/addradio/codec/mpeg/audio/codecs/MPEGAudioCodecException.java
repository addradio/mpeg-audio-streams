/**
 * Class:    MPEGAudioCodecException<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: MPEGAudioCodecException.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2012 - All rights reserved.
 */

package net.addradio.codec.mpeg.audio.codecs;

/**
 * MPEGAudioCodecException
 */
public class MPEGAudioCodecException extends Exception {

    /** {@code long} serialVersionUID */
    private static final long serialVersionUID = -886020228709751481L;

    /**
     * MPEGAudioCodecException constructor.
     */
    public MPEGAudioCodecException() {
    }

    /**
     * MPEGAudioCodecException constructor.
     *
     * @param message
     *            {@link String}
     */
    public MPEGAudioCodecException(final String message) {
        super(message);
    }

    /**
     * MPEGAudioCodecException constructor.
     *
     * @param message
     *            {@link String}
     * @param cause
     *            {@link Throwable}
     */
    public MPEGAudioCodecException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * MPEGAudioCodecException constructor.
     *
     * @param message
     *            {@link String}
     * @param cause
     *            {@link Throwable}
     * @param enableSuppression
     *            {@code boolean}
     * @param writableStackTrace
     *            {@code boolean}
     */
    public MPEGAudioCodecException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * MPEGAudioCodecException constructor.
     *
     * @param cause
     *            {@link Throwable}
     */
    public MPEGAudioCodecException(final Throwable cause) {
        super(cause);
    }

}
