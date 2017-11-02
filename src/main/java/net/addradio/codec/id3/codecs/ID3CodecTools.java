/**
 * Class:    ID3CodecTools<br/>
 * <br/>
 * Created:  02.11.2017<br/>
 * Filename: ID3CodecTools.java<br/>
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

package net.addradio.codec.id3.codecs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import net.addradio.codec.id3.model.v2.ID3v2Tag;
import net.addradio.streams.BitInputStream;

/**
 * ID3CodecTools
 */
public final class ID3CodecTools {

    /**
     * decodeID3v2Tag.
     * @param bis {@link BitInputStream}
     * @return {@link ID3v2Tag}
     * @throws IOException due to IO problems.
     * @throws UnsupportedEncodingException while byte to string conversion.
     */
    public static final ID3v2Tag decodeID3v2Tag(final BitInputStream bis)
            throws IOException, UnsupportedEncodingException {
        final int majorVersion = bis.read();
        final int revision = bis.read();
        final boolean unsynchronized = bis.isNextBitOne();
        ID3v2Tag id3v2Tag = null;
        switch (majorVersion) {
        case 2:
            id3v2Tag = ID3v220TagCodec.decodeID3v220Tag(bis);
            break;
        case 3:
            id3v2Tag = ID3v230TagCodec.decodeID3v230Tag(bis);
            break;
        case 4:
            id3v2Tag = ID3v240TagCodec.decodeID3v240Tag(bis);
            break;
        default:
            throw new UnsupportedEncodingException("Do not support ID3v2 version [major: " + majorVersion //$NON-NLS-1$
                    + ", revision: " + revision + "]"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        id3v2Tag.setMajorVersion(majorVersion);
        id3v2Tag.setRevisionNumber(revision);
        id3v2Tag.setUnsynchronisation(unsynchronized);
        return id3v2Tag;
    }

    /**
     * readStringFromStream.
     * @param bis {@link BitInputStream}
     * @param length {@code int}
     * @return {@link String}
     * @throws IOException due to IO problems.
     * @throws UnsupportedEncodingException while byte to string conversion.
     */
    public static final String readStringFromStream(final BitInputStream bis, final int length)
            throws IOException, UnsupportedEncodingException {
        final byte[] buffer = new byte[length];
        bis.readFully(buffer);
        return new String(buffer, "UTF-8").trim(); //$NON-NLS-1$
    }

    /**
     * readSyncSafeInt.
     * @param bis {@link BitInputStream}
     * @return {@code int}
     * @throws IOException due to IO problems.
     */
    public static final int readSyncSafeInt(final BitInputStream bis) throws IOException {
        int size = 0;
        bis.readBit();
        size |= bis.readBits(7) << 21;
        bis.readBit();
        size |= bis.readBits(7) << 14;
        bis.readBit();
        size |= bis.readBits(7) << 7;
        bis.readBit();
        size |= bis.readBits(7);
        return size;
    }

    /**
     * Hidden ID3CodecTools constructor.
     */
    private ID3CodecTools() {
    }

}
