/**
 * Class:    DecodingException<br/>
 * <br/>
 * Created:  02.12.2019<br/>
 * Filename: DecodingException.java<br/>
 * Version:  $Revision$<br/>
 * <br/>
 * last modified on $Date$<br/>
 *               by $Author$<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author$ -- $Revision$ -- $Date$
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2019 - All rights reserved.
 */
package net.addradio.decoder;

/**
 * DecodingException.
 */
public class DecodingException extends Exception {

    /** {@link long} serialVersionUID. */
    private static final long serialVersionUID = 1977168217273320442L;

    /**
     * DecodingException constructor.
     */
    public DecodingException() {
    }

    /**
     * DecodingException constructor.
     * @param message {@link String}
     */
    public DecodingException(final String message) {
        super(message);
    }

    /**
     * DecodingException constructor.
     * @param message {@link String}
     * @param cause {@link Throwable}
     */
    public DecodingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * DecodingException constructor.
     * @param message {@link String}
     * @param cause {@link Throwable}
     * @param enableSuppression {@code boolean}
     * @param writableStackTrace {@code boolean}
     */
    public DecodingException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * DecodingException constructor.
     * @param cause {@link Throwable}
     */
    public DecodingException(final Throwable cause) {
        super(cause);
    }

}
