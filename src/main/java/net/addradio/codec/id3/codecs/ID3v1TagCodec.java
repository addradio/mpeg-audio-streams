/**
 * Class:    ID3v1TagCodec<br/>
 * <br/>
 * Created:  02.11.2017<br/>
 * Filename: ID3v1TagCodec.java<br/>
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

import net.addradio.codec.id3.model.v1.Genre;
import net.addradio.codec.id3.model.v1.ID3v1Tag;
import net.addradio.codec.mpeg.audio.codecs.BitMaskFlagCodec;
import net.addradio.codec.mpeg.audio.codecs.MPEGAudioCodecException;
import net.addradio.streams.BitInputStream;

/**
 * ID3v1TagCodec
 */
public final class ID3v1TagCodec {

    /**
     * decodeID3v1Tag.
     * @param bis {@link BitInputStream}
     * @return {@link ID3v1Tag}
     * @throws IOException due to IO problems.
     * @throws UnsupportedEncodingException while byte to string conversion.
     * @throws MPEGAudioCodecException if {@link Genre} could not be decoded.
     */
    public static final ID3v1Tag decodeID3v1Tag(final BitInputStream bis)
            throws IOException, UnsupportedEncodingException, MPEGAudioCodecException {
        final ID3v1Tag tag = new ID3v1Tag();
        tag.setTitle(ID3CodecTools.readStringFromStream(bis, 30, Integer.MAX_VALUE));
        tag.setArtist(ID3CodecTools.readStringFromStream(bis, 30, Integer.MAX_VALUE));
        tag.setAlbum(ID3CodecTools.readStringFromStream(bis, 30, Integer.MAX_VALUE));
        tag.setTitle(ID3CodecTools.readStringFromStream(bis, 30, Integer.MAX_VALUE));
        final String readStringFromStream = ID3CodecTools.readStringFromStream(bis, 4, Integer.MAX_VALUE);
        tag.setYear(saveParseInt(readStringFromStream));
        tag.setComment(ID3CodecTools.readStringFromStream(bis, 30, Integer.MAX_VALUE));
        tag.setGenre((Genre) BitMaskFlagCodec.decode(bis.read(), Genre.class));
        return tag;
    }

    /**
     * saveParseInt.
     * @param readStringFromStream {@link String}
     * @return {@code int}
     */
    private static int saveParseInt(final String readStringFromStream) {
        // SEBASTIAN maybe throw ID3 encoding exception

        if ((readStringFromStream == null) || readStringFromStream.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(readStringFromStream);
        } catch (final NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * ID3v1TagCodec constructor.
     */
    private ID3v1TagCodec() {
    }

}
