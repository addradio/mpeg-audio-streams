/**
 * Class:    ID3v240TagCodec<br/>
 * <br/>
 * Created:  02.11.2017<br/>
 * Filename: ID3v240TagCodec.java<br/>
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
import net.addradio.codec.id3.model.v2.v240.ExtendedHeader;
import net.addradio.codec.id3.model.v2.v240.ID3v240Tag;
import net.addradio.codec.id3.model.v2.v240.TagRestrictions;
import net.addradio.codec.mpeg.audio.MPEGAudioFrameInputStream;
import net.addradio.streams.BitInputStream;

/**
 * ID3v240TagCodec
 */
public final class ID3v240TagCodec {

    /** {@link Logger} LOG */
    private static final Logger LOG = LoggerFactory.getLogger(ID3v240TagCodec.class);

    /**
     * decodeID3v240Tag.
     * @param bis {@link BitInputStream}
     * @return {@link ID3v240Tag}
     * @throws IOException due to IO problems.
     * @throws UnsupportedEncodingException during byte to string conversion.
     */
    public static final ID3v240Tag decodeID3v240Tag(final BitInputStream bis)
            throws IOException, UnsupportedEncodingException {
        final ID3v240Tag id3v240Tag = new ID3v240Tag();
        if (bis.isNextBitOne()) {
            id3v240Tag.setExtendedHeader(new ExtendedHeader());
        }
        id3v240Tag.setExperimental(bis.isNextBitOne());
        id3v240Tag.setFooter(bis.isNextBitOne());
        // next 4 bit should be zeros, otherwise we could not decode this tag
        boolean maybeUnreadable = false;
        for (int i = 0; i < 4; i++) {
            if (bis.isNextBitOne()) {
                maybeUnreadable = true;
                break;
            }
        }
        if (maybeUnreadable && ID3v240TagCodec.LOG.isDebugEnabled()) {
            ID3v240TagCodec.LOG.debug("id3v2 tag is maybe unreadable!"); //$NON-NLS-1$
        }
        id3v240Tag.setTagSize(ID3CodecTools.readSyncSafeInt(bis));

        int bytesLeft = id3v240Tag.getTagSize();
        if (id3v240Tag.getExtendedHeader() != null) {
            id3v240Tag.getExtendedHeader().setSize(ID3CodecTools.readSyncSafeInt(bis));
            bytesLeft -= 4;
            final int numberOfFlagBytes = bis.read();
            bytesLeft--;
            if ((numberOfFlagBytes != 1) && ID3v240TagCodec.LOG.isDebugEnabled()) {
                ID3v240TagCodec.LOG.debug("unknown extended header flagbytes!"); //$NON-NLS-1$
            }
            int numberOfAdditionalFlags = 0;
            if (numberOfFlagBytes > 0) {
                bis.readBit();
                id3v240Tag.getExtendedHeader().setTagIsAnUpdate(bis.isNextBitOne());
                id3v240Tag.getExtendedHeader().setCrcDataIsPresent(bis.isNextBitOne());
                if (bis.isNextBitOne()) {
                    id3v240Tag.getExtendedHeader().setTagRestrictions(new TagRestrictions());
                }
                for (int i = 4; i > 0; i--) {
                    if (bis.isNextBitOne()) {
                        numberOfAdditionalFlags++;
                    }
                }
                bytesLeft--;
                for (int i = numberOfFlagBytes - 1; i > 0; i--) {
                    for (int j = 8; j > 0; j--) {
                        if (bis.isNextBitOne()) {
                            numberOfAdditionalFlags++;
                        }
                    }
                    bytesLeft--;
                }
            }
            if (id3v240Tag.getExtendedHeader().isTagIsAnUpdate()) {
                bis.read();
                bytesLeft--;
            }
            if (id3v240Tag.getExtendedHeader().isCrcDataIsPresent()) {
                final int crcSize = bis.read();
                bytesLeft--;
                if (crcSize != 5) {
                    // SEBASTIAN error handling
                }
                id3v240Tag.getExtendedHeader().setCrc32((int) MPEGAudioFrameInputStream.readSyncSave5ByteInteger(bis));
                bytesLeft -= 5;
            }
            if (id3v240Tag.getExtendedHeader().getTagRestrictions() != null) {
                final int length = bis.read();
                bytesLeft--;
                if (length != 1) {
                    // SEBASTIAN error handling
                }
                bis.read();
                bytesLeft--;
                // SEBASTIAN do something with restrictions
            }
            for (int i = numberOfAdditionalFlags; i > 0; i--) {
                final int length = bis.read();
                bytesLeft--;
                for (int j = length; j > 0; j--) {
                    bis.read();
                    bytesLeft--;
                }
            }
        }
        while (bytesLeft > 0) {
            final Frame e = new Frame();
            e.setFrameId(ID3CodecTools.readStringFromStream(bis, 4));
            bytesLeft -= 4;
            e.setSize(ID3CodecTools.readSyncSafeInt(bis));
            bytesLeft -= 4;
            // SEBASTIAN decode flags
            bis.read();
            bytesLeft--;
            bis.read();
            bytesLeft--;
            // SEBASTIAN decode payload
            for (int i = e.getSize(); i > 0; i--) {
                bis.read();
                bytesLeft--;
            }
            id3v240Tag.getFrames().add(e);
        }
        return id3v240Tag;
    }

    /**
     * ID3v240TagCodec constructor.
     */
    private ID3v240TagCodec() {
    }

}
