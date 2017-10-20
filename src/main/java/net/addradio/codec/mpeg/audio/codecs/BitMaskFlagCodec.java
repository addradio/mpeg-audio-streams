/**
 * Class:    BitMaskFlagCodec<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: BitMaskFlagCodec.java<br/>
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

import net.addradio.codec.mpeg.audio.model.BitMaskFlag;

/**
 * BitMaskFlagCodec
 */
public class BitMaskFlagCodec {

    /**
     * decode.
     *
     * @param value
     *            {@code int}
     * @param enumClass
     *            {@link Class}
     * @return {@link BitMaskFlag}
     * @throws MPEGAudioCodecException
     *             if flag could not be decoded.
     */
    public static BitMaskFlag decode(final int value, final Class<?> enumClass) throws MPEGAudioCodecException {
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("[enumClass: " + enumClass.getCanonicalName() + "] is not a enum!"); //$NON-NLS-1$//$NON-NLS-2$
        }
        if (!BitMaskFlag.class.isAssignableFrom(enumClass)) {
            throw new IllegalArgumentException(
                    "[enumClass: " + enumClass.getCanonicalName() + "] does not implement BitMaskFlag!"); //$NON-NLS-1$//$NON-NLS-2$
        }
        final Object[] enumConstants = enumClass.getEnumConstants();
        for (final Object object : enumConstants) {
            final BitMaskFlag flag = (BitMaskFlag) object;
            if (value == flag.getBitMask()) {
                return flag;
            }
        }
        throw new MPEGAudioCodecException(
                "Could not decode [Flag: " + enumClass.getSimpleName() + ", valueToBeDecoded: " + value + "]."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * BitMaskFlagCodec constructor.
     */
    private BitMaskFlagCodec() {
    }

}
