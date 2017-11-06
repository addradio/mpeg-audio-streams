/**
 * Class:    ID3v220TagCodec<br/>
 * <br/>
 * Created:  02.11.2017<br/>
 * Filename: ID3v220TagCodec.java<br/>
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.id3.model.v2.Frame;
import net.addradio.codec.id3.model.v2.v220.ID3v220Tag;
import net.addradio.streams.BitInputStream;

/**
 * ID3v220TagCodec
 */
public final class ID3v220TagCodec {

    /** {@link Logger} LOG */
    private static final Logger LOG = LoggerFactory.getLogger(ID3v220TagCodec.class);

    /** {@code int} NUM_OF_HEADER_BYTES */
    static final int NUM_OF_HEADER_BYTES = 10;

    /**
     * decodeID3v220Tag.
     * @param bis {@link BitInputStream}
     * @return {@link ID3v220Tag}
     * @throws IOException due to IO problems.
     * @throws UnsupportedEncodingException while byte to string conversion.
     */
    public final static ID3v220Tag decodeID3v220Tag(final BitInputStream bis)
            throws IOException, UnsupportedEncodingException {
        final ID3v220Tag id3v220Tag = new ID3v220Tag();
        id3v220Tag.setCompressed(bis.isNextBitOne());
        // next 6 bit should be zeros, otherwise we could not decode this tag
        boolean maybeUnreadable = false;
        for (int i = 0; i < 6; i++) {
            if (bis.isNextBitOne()) {
                maybeUnreadable = true;
                break;
            }
        }
        if (maybeUnreadable && ID3v220TagCodec.LOG.isDebugEnabled()) {
            ID3v220TagCodec.LOG.debug("id3v220 tag is maybe unreadable!"); //$NON-NLS-1$
        }

        id3v220Tag.setPayloadSize(ID3CodecTools.readSyncSafeInt(bis));
        id3v220Tag.setOverallSize(ID3v220TagCodec.NUM_OF_HEADER_BYTES + id3v220Tag.getPayloadSize());

        int bytesLeft = id3v220Tag.getPayloadSize();
        while (bytesLeft > 0) {
            final Frame e = new Frame();
            e.setFrameId(ID3CodecTools.readStringFromStream(bis, 3));
            bytesLeft -= 3;
            e.setSize(bis.readInt(3));
            bytesLeft -= 3;
            // SEBASTIAN decode payload
            e.setPayload(ID3CodecTools.readStringFromStream(bis, e.getSize()));
            bytesLeft -= e.getSize();
            id3v220Tag.getFrames().add(e);
        }
        return id3v220Tag;
    }

    /**
     * ID3v220TagCodec constructor.
     */
    private ID3v220TagCodec() {
    }

}
