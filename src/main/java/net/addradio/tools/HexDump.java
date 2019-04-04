/**
 * Class:    HexDump<br/>
 * <br/>
 * Created:  11.08.2014<br/>
 * Filename: HexDump.java<br/>
 * Version:  $Revision: 1.3 $<br/>
 * <br/>
 * last modified on $Date: 2014/08/13 09:03:37 $<br/>
 *               by $Author: sweiss $<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author: sweiss $ -- $Revision: 1.3 $ -- $Date: 2014/08/13 09:03:37 $
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2014 - All rights reserved.
 */

package net.addradio.tools;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * HexDump
 */
public class HexDump {

    /**
     * createHexDumpString.
     *
     * @param bytes
     *            <code>byte[]</code>
     * @return {@link String}
     * @throws UnsupportedEncodingException
     *             if encoding is not supported on this platform.
     */
    public static String createHexDumpString(final byte[] bytes) throws UnsupportedEncodingException {
        final StringBuilder sb = new StringBuilder();
        int baseCount = 0;
        do {
            int bytesLeft = 8;
            if ((baseCount + bytesLeft) > bytes.length) {
                bytesLeft = bytes.length - baseCount;
            }
            final int leftSpaces = 8 - bytesLeft;

            sb.append('|');
            for (int i = 0; i < bytesLeft; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(getHexString(bytes[baseCount + i] & 0xFF));
            }
            for (int i = 0; i < leftSpaces; i++) {
                sb.append("   "); //$NON-NLS-1$
            }
            sb.append("| |"); //$NON-NLS-1$
            for (int i = 0; i < bytesLeft; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(getBinaryString(bytes[baseCount + i] & 0xFF));
            }
            for (int i = 0; i < leftSpaces; i++) {
                sb.append("         "); //$NON-NLS-1$
            }
            sb.append("| |"); //$NON-NLS-1$
            sb.append(new String(Arrays.copyOfRange(bytes, baseCount, baseCount + bytesLeft), "ISO-8859-9") //$NON-NLS-1$
                    .replace('\n', '.').replace('\r', '.'));
            sb.append("|\n"); //$NON-NLS-1$
            baseCount += bytesLeft;
        } while (baseCount < bytes.length);
        final String string = sb.toString();
        return string;
    }

    /**
     * getBinaryString.
     *
     * @param i
     *            <code>int</code>
     * @return {@link String}
     */
    private static String getBinaryString(final int i) {
        String string = Integer.toBinaryString(i).toUpperCase();
        while (string.length() < 8) {
            string = "0" + string; //$NON-NLS-1$
        }
        return string;
    }

    /**
     * getHexString.
     *
     * @param b
     *            <code>int</code>
     * @return {@link String}
     */
    public static String getHexString(final int b) {
        final String string = Integer.toHexString(b).toUpperCase();
        if (string.length() > 1) {
            return string;
        }
        return "0" + string; //$NON-NLS-1$
    }

    /**
     * main.
     *
     * @param args
     *            {@link String}[]
     * @throws UnsupportedEncodingException
     *             if encoding is not supported on this platform.
     */
    public static void main(final String[] args) throws UnsupportedEncodingException {
        printHexDumpString(new byte[188]);
    }

    /**
     * pribtHexDumpString.
     *
     * @param bytes
     *            <code>byte[]</code>
     * @throws UnsupportedEncodingException
     *             if encoding is not supported on this platform.
     */
    public static void printHexDumpString(final byte[] bytes) throws UnsupportedEncodingException {
        if (bytes != null) {
            System.out.println(createHexDumpString(bytes));
        }
    }

}
// ==============[VERSION-CONTROL-LOG-START]==============
// -------------------------------------------------------
// $Log: HexDump.java,v $
// Revision 1.3 2014/08/13 09:03:37 sweiss
// *** empty log message ***
//
// Revision 1.2 2014/08/13 08:51:08 sweiss
// enhanced DecoderLog
//
// Revision 1.1 2014-08-12 15:06:20 sweiss
// *** empty log message ***
//
// Revision 1.2 2014-08-12 10:37:30 sweiss
// removed discard exception
//
// Revision 1.1 2014-08-11 13:11:03 sweiss
// performance improvements
//
// _______________________________________________________
// ==============[VERSION-CONTROL-LOG-END]================