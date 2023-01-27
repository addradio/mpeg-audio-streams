/**
 * Class:    EncodingFormat<br/>
 * <br/>
 * Created:  26.10.2017<br/>
 * Filename: EncodingFormat.java<br/>
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
 * EncodingFormat.
 */
public interface EncodingFormat {

    /**
     * clone.
     * @return {@link EncodingFormat}
     */
    EncodingFormat clone();

    /**
     * getBitRateInBps.
     * @return {@code int}
     */
    int getBitRateInBps();

    /**
     * getBitRateInBps.
     * @return {@link Integer}
     */
    Integer getBitRateInBpsInteger();

    /**
     * getName.
     * @return {@link String}
     */
    String getName();

    /**
     * toLongForm.
     * @return {@link String}
     */
    String toLongForm();

    /**
     * toShortForm.
     * @return {@link String}
     */
    String toShortForm();
}
