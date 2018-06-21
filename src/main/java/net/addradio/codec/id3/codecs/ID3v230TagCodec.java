/**
 * Class:    ID3v230TagCodec<br/>
 * <br/>
 * Created:  02.11.2017<br/>
 * Filename: ID3v230TagCodec.java<br/>
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
import net.addradio.codec.id3.model.v2.v230.ID3v230Tag;
import net.addradio.streams.BitInputStream;

/**
 * ID3v230TagCodec
 */
public final class ID3v230TagCodec {

    /** {@link Logger} LOG */
    private static final Logger LOG = LoggerFactory.getLogger(ID3v230TagCodec.class);

    /**
     * decodeID3v230Tag.
     * @param bis {@link BitInputStream}
     * @return {@link ID3v230Tag}
     * @throws IOException due to IO problems.
     * @throws UnsupportedEncodingException due to byte to string conversion.
     */
    public final static ID3v230Tag decodeID3v230Tag(final BitInputStream bis)
            throws IOException, UnsupportedEncodingException {
        final ID3v230Tag id3v230Tag = new ID3v230Tag();
        if (bis.isNextBitOne()) {
            id3v230Tag.setExtendedHeader(new net.addradio.codec.id3.model.v2.v230.ExtendedHeader());
        }
        id3v230Tag.setExperimental(bis.isNextBitOne());
        // next 5 bit should be zeros, otherwise we could not decode this tag
        boolean maybeUnreadable = false;
        for (int i = 0; i < 5; i++) {
            if (bis.isNextBitOne()) {
                maybeUnreadable = true;
                break;
            }
        }
        if (maybeUnreadable && ID3v230TagCodec.LOG.isDebugEnabled()) {
            ID3v230TagCodec.LOG.debug("id3v2 tag is maybe unreadable!"); //$NON-NLS-1$
        }
        id3v230Tag.setPayloadSize(ID3CodecTools.readSyncSafeInt(bis));
        id3v230Tag.setOverallSize(ID3v220TagCodec.NUM_OF_HEADER_BYTES + id3v230Tag.getPayloadSize());

        int bytesLeft = id3v230Tag.getPayloadSize();
        if (id3v230Tag.getExtendedHeader() != null) {
            id3v230Tag.getExtendedHeader().setSize(bis.readInt(4));
            bytesLeft -= 4;
            id3v230Tag.getExtendedHeader().setCrcDataIsPresent(bis.isNextBitOne());
            bis.readBits(7);
            bytesLeft--;
            if (id3v230Tag.getExtendedHeader().isCrcDataIsPresent()) {
                id3v230Tag.getExtendedHeader().setCrc32(bis.readInt(4));
                bytesLeft -= 4;
            }
        }
        while (bytesLeft > 0) {
            final Frame e = new Frame();
            e.setFrameId(ID3CodecTools.readStringFromStream(bis, 4));
            bytesLeft -= 4;
            e.setSize(bis.readInt(4));
            bytesLeft -= 4;
            // SEBASTIAN decode flags
            bis.read();
            bytesLeft--;
            bis.read();
            bytesLeft--;
            e.setPayload(ID3CodecTools.readStringFromStream(bis, e.getSize()));
            bytesLeft -= e.getSize();
            id3v230Tag.getFrames().put(e.getFrameId(), e);
        }
        return id3v230Tag;
    }

    /**
     * ID3v230TagCodec constructor.
     */
    private ID3v230TagCodec() {
    }

}
