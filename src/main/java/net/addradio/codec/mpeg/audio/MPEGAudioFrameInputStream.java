/**
 * Class:    MPEGAudioFrameInputStream<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: MPEGAudioFrameInputStream.java<br/>
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

package net.addradio.codec.mpeg.audio;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.id3.model.v1.Genre;
import net.addradio.codec.id3.model.v1.ID3v1Tag;
import net.addradio.codec.id3.model.v2.Frame;
import net.addradio.codec.id3.model.v2.ID3v2Tag;
import net.addradio.codec.id3.model.v2.v220.ID3v220Tag;
import net.addradio.codec.id3.model.v2.v230.ID3v230Tag;
import net.addradio.codec.id3.model.v2.v240.ExtendedHeader;
import net.addradio.codec.id3.model.v2.v240.ID3v240Tag;
import net.addradio.codec.id3.model.v2.v240.TagRestrictions;
import net.addradio.codec.mpeg.audio.codecs.BitMaskFlagCodec;
import net.addradio.codec.mpeg.audio.codecs.BitRateCodec;
import net.addradio.codec.mpeg.audio.codecs.MPEGAudioCodecException;
import net.addradio.codec.mpeg.audio.codecs.ModeExtensionCodec;
import net.addradio.codec.mpeg.audio.codecs.SamplingRateCodec;
import net.addradio.codec.mpeg.audio.model.Emphasis;
import net.addradio.codec.mpeg.audio.model.Layer;
import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.model.Mode;
import net.addradio.codec.mpeg.audio.model.Version;
import net.addradio.streams.BitInputStream;
import net.addradio.streams.EndOfStreamException;

/**
 * MPEGAudioFrameInputStream
 */
public class MPEGAudioFrameInputStream extends BitInputStream {

    /** {@link Logger} LOG */
    private static final Logger LOG = LoggerFactory.getLogger(MPEGAudioFrameInputStream.class);

    /**
     * {@link int} MAGIC_144
     * SEBASTIAN check definitions
     */
    private static final int MAGIC_144 = 144;

    /**
     * calculateLayer2or3FrameLength.
     * @param mpegFrame {@link MPEGAudioFrame}
     * @return {@code int} the overall number of bytes of the frame.
     */
    private static final int calculateLayer2or3FrameLength(final MPEGAudioFrame mpegFrame) {
        return ((MPEGAudioFrameInputStream.MAGIC_144 * mpegFrame.getBitRate().getValue())
                / mpegFrame.getSamplingRate().getValue()) + (mpegFrame.isPadding() ? 1 : 0);
    }

    /**
     * calculateLayer2or3PayloadLength.
     * @param mpegFrame {@link MPEGAudioFrame}
     * @return {@code int} number of payload bytes.
     */
    private static final int calculateLayer2or3PayloadLength(final MPEGAudioFrame mpegFrame) {
        return calculateLayer2or3FrameLength(mpegFrame) - MPEGAudioFrame.HEADER_SIZE_IN_BYTES
                - (mpegFrame.isErrorProtected() ? MPEGAudioFrame.CRC_SIZE_IN_BYTES : 0);
    }

    /** {@code boolean} unalignedSyncAllowed. */
    private boolean unalignedSyncAllowed = false;

    /**
     * MPEGAudioFrameInputStream constructor.
     *
     * @param innerRef
     *            {@link InputStream}
     */
    public MPEGAudioFrameInputStream(final InputStream innerRef) {
        super(innerRef);
    }

    /**
     * assertByteAlignement.
     */
    private void assertByteAlignement() {
        if (!isUnalignedSyncAllowed()) {
            assert isByteAligned();
        }
    }

    /**
     * decodeHeader.
     *
     * @param mp3Frame
     *            {@link MPEGAudioFrame}
     * @throws IOException
     *             in case of bad IO situations.
     * @throws MPEGAudioCodecException
     *             if an decoding error occurred.
     */
    private void decodeHeader(final MPEGAudioFrame mp3Frame) throws IOException, MPEGAudioCodecException {
        mp3Frame.setVersion((Version) BitMaskFlagCodec.decode(readBits(2), Version.class));
        mp3Frame.setLayer((Layer) BitMaskFlagCodec.decode(readBits(2), Layer.class));
        mp3Frame.setErrorProtected(isNextBitOne());
        mp3Frame.setBitRate(BitRateCodec.decode(mp3Frame, readBits(4)));
        mp3Frame.setSamplingRate(SamplingRateCodec.decode(mp3Frame, readBits(2)));
        mp3Frame.setPadding(isNextBitOne());
        mp3Frame.setPrivate(isNextBitOne());
        mp3Frame.setMode((Mode) BitMaskFlagCodec.decode(readBits(2), Mode.class));
        mp3Frame.setModeExtension(ModeExtensionCodec.decode(mp3Frame, readBits(2)));
        mp3Frame.setCopyright(isNextBitOne());
        mp3Frame.setOriginal(isNextBitOne());
        mp3Frame.setEmphasis((Emphasis) BitMaskFlagCodec.decode(readBits(2), Emphasis.class));
    }

    /**
     * decodeID3v1Tag.
     * @return {@link ID3v1Tag}
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws MPEGAudioCodecException
     */
    private ID3v1Tag decodeID3v1Tag() throws IOException, UnsupportedEncodingException, MPEGAudioCodecException {
        final ID3v1Tag tag = new ID3v1Tag();
        tag.setTitle(readStringFromStream(30));
        tag.setArtist(readStringFromStream(30));
        tag.setAlbum(readStringFromStream(30));
        tag.setTitle(readStringFromStream(30));
        tag.setYear(Integer.parseInt(readStringFromStream(4)));
        tag.setComment(readStringFromStream(30));
        tag.setGenre((Genre) BitMaskFlagCodec.decode(read(), Genre.class));
        return tag;
    }

    /**
     * decodeID3v220Tag.
     * @return {@link ID3v220Tag}
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private ID3v220Tag decodeID3v220Tag() throws IOException, UnsupportedEncodingException {
        final ID3v220Tag id3v220Tag = new ID3v220Tag();
        id3v220Tag.setCompressed(isNextBitOne());
        // next 6 bit should be zeros, otherwise we could not decode this tag
        boolean maybeUnreadable = false;
        for (int i = 0; i < 6; i++) {
            if (isNextBitOne()) {
                maybeUnreadable = true;
                break;
            }
        }
        if (maybeUnreadable && MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
            MPEGAudioFrameInputStream.LOG.debug("id3v220 tag is maybe unreadable!"); //$NON-NLS-1$
        }
        id3v220Tag.setTagSize(readSyncSafeInt());

        int bytesLeft = id3v220Tag.getTagSize();
        while (bytesLeft > 0) {
            final Frame e = new Frame();
            e.setFrameId(readStringFromStream(3));
            bytesLeft -= 3;
            e.setSize(readInt(3));
            bytesLeft -= 3;
            // SEBASTIAN decode payload
            e.setPayload(readStringFromStream(e.getSize()));
            bytesLeft -= e.getSize();
            id3v220Tag.getFrames().add(e);
        }
        return id3v220Tag;
    }

    /**
     * decodeID3v230Tag.
     * @return {@link ID3v230Tag}
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private ID3v230Tag decodeID3v230Tag() throws IOException, UnsupportedEncodingException {
        final ID3v230Tag id3v230Tag = new ID3v230Tag();
        if (isNextBitOne()) {
            id3v230Tag.setExtendedHeader(new net.addradio.codec.id3.model.v2.v230.ExtendedHeader());
        }
        id3v230Tag.setExperimental(isNextBitOne());
        // next 5 bit should be zeros, otherwise we could not decode this tag
        boolean maybeUnreadable = false;
        for (int i = 0; i < 5; i++) {
            if (isNextBitOne()) {
                maybeUnreadable = true;
                break;
            }
        }
        if (maybeUnreadable && MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
            MPEGAudioFrameInputStream.LOG.debug("id3v2 tag is maybe unreadable!"); //$NON-NLS-1$
        }
        id3v230Tag.setTagSize(readSyncSafeInt());

        int bytesLeft = id3v230Tag.getTagSize();
        if (id3v230Tag.getExtendedHeader() != null) {
            id3v230Tag.getExtendedHeader().setSize(readInt(4));
            bytesLeft -= 4;
            id3v230Tag.getExtendedHeader().setCrcDataIsPresent(isNextBitOne());
            ;
            readBits(7);
            bytesLeft--;
            if (id3v230Tag.getExtendedHeader().isCrcDataIsPresent()) {
                id3v230Tag.getExtendedHeader().setCrc32(readInt(4));
                bytesLeft -= 4;
            }
        }
        while (bytesLeft > 0) {
            final Frame e = new Frame();
            e.setFrameId(readStringFromStream(4));
            bytesLeft -= 4;
            e.setSize(readInt(4));
            bytesLeft -= 4;
            // SEBASTIAN decode flags
            read();
            bytesLeft--;
            read();
            bytesLeft--;
            e.setPayload(readStringFromStream(e.getSize()));
            bytesLeft -= e.getSize();
            id3v230Tag.getFrames().add(e);
        }
        return id3v230Tag;
    }

    /**
     * decodeID3v240Tag.
     * @return {@link ID3v240Tag}
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private ID3v240Tag decodeID3v240Tag() throws IOException, UnsupportedEncodingException {
        final ID3v240Tag id3v240Tag = new ID3v240Tag();
        if (isNextBitOne()) {
            id3v240Tag.setExtendedHeader(new ExtendedHeader());
        }
        id3v240Tag.setExperimental(isNextBitOne());
        id3v240Tag.setFooter(isNextBitOne());
        // next 4 bit should be zeros, otherwise we could not decode this tag
        boolean maybeUnreadable = false;
        for (int i = 0; i < 4; i++) {
            if (isNextBitOne()) {
                maybeUnreadable = true;
                break;
            }
        }
        if (maybeUnreadable && MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
            MPEGAudioFrameInputStream.LOG.debug("id3v2 tag is maybe unreadable!"); //$NON-NLS-1$
        }
        id3v240Tag.setTagSize(readSyncSafeInt());

        int bytesLeft = id3v240Tag.getTagSize();
        if (id3v240Tag.getExtendedHeader() != null) {
            id3v240Tag.getExtendedHeader().setSize(readSyncSafeInt());
            bytesLeft -= 4;
            final int numberOfFlagBytes = read();
            bytesLeft--;
            if ((numberOfFlagBytes != 1) && MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                MPEGAudioFrameInputStream.LOG.debug("unknown extended header flagbytes!"); //$NON-NLS-1$
            }
            int numberOfAdditionalFlags = 0;
            if (numberOfFlagBytes > 0) {
                readBit();
                id3v240Tag.getExtendedHeader().setTagIsAnUpdate(isNextBitOne());
                id3v240Tag.getExtendedHeader().setCrcDataIsPresent(isNextBitOne());
                if (isNextBitOne()) {
                    id3v240Tag.getExtendedHeader().setTagRestrictions(new TagRestrictions());
                }
                for (int i = 4; i > 0; i--) {
                    if (isNextBitOne()) {
                        numberOfAdditionalFlags++;
                    }
                }
                bytesLeft--;
                for (int i = numberOfFlagBytes - 1; i > 0; i--) {
                    for (int j = 8; j > 0; j--) {
                        if (isNextBitOne()) {
                            numberOfAdditionalFlags++;
                        }
                    }
                    bytesLeft--;
                }
            }
            if (id3v240Tag.getExtendedHeader().isTagIsAnUpdate()) {
                read();
                bytesLeft--;
            }
            if (id3v240Tag.getExtendedHeader().isCrcDataIsPresent()) {
                final int crcSize = read();
                bytesLeft--;
                if (crcSize != 5) {
                    // SEBASTIAN error handling
                }
                id3v240Tag.getExtendedHeader().setCrc32((int) readSyncSave5ByteInteger());
                bytesLeft -= 5;
            }
            if (id3v240Tag.getExtendedHeader().getTagRestrictions() != null) {
                final int length = read();
                bytesLeft--;
                if (length != 1) {
                    // SEBASTIAN error handling
                }
                read();
                bytesLeft--;
                // SEBASTIAN do something with restrictions
            }
            for (int i = numberOfAdditionalFlags; i > 0; i--) {
                final int length = read();
                bytesLeft--;
                for (int j = length; j > 0; j--) {
                    read();
                    bytesLeft--;
                }
            }
        }
        while (bytesLeft > 0) {
            final Frame e = new Frame();
            e.setFrameId(readStringFromStream(4));
            bytesLeft -= 4;
            e.setSize(readSyncSafeInt());
            bytesLeft -= 4;
            // SEBASTIAN decode flags
            read();
            bytesLeft--;
            read();
            bytesLeft--;
            // SEBASTIAN decode payload
            for (int i = e.getSize(); i > 0; i--) {
                read();
                bytesLeft--;
            }
            id3v240Tag.getFrames().add(e);
        }
        return id3v240Tag;
    }

    /**
     * decodeID3v2Tag.
     * @return {@link ID3v2Tag}
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private ID3v2Tag decodeID3v2Tag() throws IOException, UnsupportedEncodingException {
        final int majorVersion = read();
        final int revision = read();
        final boolean unsynchronized = isNextBitOne();
        ID3v2Tag id3v2Tag = null;
        switch (majorVersion) {
        case 2:
            id3v2Tag = decodeID3v220Tag();
            break;
        case 3:
            id3v2Tag = decodeID3v230Tag();
            break;
        case 4:
            id3v2Tag = decodeID3v240Tag();
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
     * decodeMPEGFrame.
     * @return {@link MPEGAudioFrame}
     * @throws IOException
     * @throws MPEGAudioCodecException
     */
    private MPEGAudioFrame decodeMPEGFrame() throws IOException, MPEGAudioCodecException {
        final MPEGAudioFrame frame = new MPEGAudioFrame();
        decodeHeader(frame);
        assertByteAlignement();
        readCrcIfNeeded(frame);

        assertByteAlignement();
        switch (frame.getLayer()) {
        case I:
            switch (frame.getMode()) {
            case SingleChannel:
                readLayer1Payload(frame, 1);
                break;
            case Stereo:
            case DualChannel:
                readLayer1Payload(frame, 2);
                break;
            case JointStereo:
                // SEBASTIAN implement
                break;
            default:
                break;
            }
            break;
        case II:
        case III:
            final int payloadLengthInBytes = calculateLayer2or3PayloadLength(frame);
            if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                MPEGAudioFrameInputStream.LOG.debug("[framelength: " + payloadLengthInBytes + "]"); //$NON-NLS-1$ //$NON-NLS-2$
            }
            frame.setPayload(new byte[payloadLengthInBytes]);
            readFully(frame.getPayload());
            //                    for (int i = 0; i < frameLengthInBytes; i++) {
            //                        final int read = read();
            //                        if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
            //                            MPEGAudioFrameInputStream.LOG.debug(
            //                                    "payload byte read [index: " + i + ", byte: 0b" + Integer.toBinaryString(read)); //$NON-NLS-1$//$NON-NLS-2$
            //                        }
            //                    }
            break;
        case reserved:
        default:
            break;
        }
        assertByteAlignement();

        // SEBASTIAN implement ancillary data

        // int frameSize = 144
        // * mp3Frame.getBitrate().getValue()
        // * 1000
        // / mp3Frame.getSamplingrate().getValue()
        // + (mp3Frame.isPadding() ? (mp3Frame.getLayer() == Layer.I ? 4
        // : 1) : 0);
        // if (LOG.isDebugEnabled()) {
        // LOG.debug("framesize: " + frameSize);
        // }
        //
        // byte[] data = new byte[frameSize - 4
        // - (mp3Frame.is_protected() ? 2 : 0)];
        // readFully(data);
        // mp3Frame.setData(data);
        return frame;
    }

    /**
     * isNextBitOne. Reads just one bit and checks whether it is 0b1 or not.
     *
     * @return {@code boolean true} only if the next bit is 0b1.
     * @throws IOException
     */
    private boolean isNextBitOne() throws IOException {
        return readBit() == 1;
    }

    /**
     * isUnalignedSyncAllowed.
     * @return {@code boolean true} if sync is allowed even if sync bits are not aligned to byte boundaries.
     */
    public boolean isUnalignedSyncAllowed() {
        return this.unalignedSyncAllowed;
    }

    /**
     * readCrcIfNeeded.
     *
     * @param mp3Frame
     *            {@link MPEGAudioFrame}
     * @throws IOException
     */
    private void readCrcIfNeeded(final MPEGAudioFrame mp3Frame) throws IOException {
        if (mp3Frame.isErrorProtected()) {
            mp3Frame.setCrc(new byte[MPEGAudioFrame.CRC_SIZE_IN_BYTES]);
            readFully(mp3Frame.getCrc());
        }
    }

    /**
     * Reads one frame from the inner stream.
     *
     * @return {@link MPEGAudioContent} or {@code null} if end of stream has been reached.
     * @throws IOException
     *             in case of bad IO situations.
     */
    public MPEGAudioContent readFrame() throws IOException {
        while (true) {
            try {
                assertByteAlignement();
                final SyncResult syncResult = sync();
                switch (syncResult.getMode()) {
                case id3v2_aligned:
                    return decodeID3v2Tag();
                case id3v1_aligned:
                    return decodeID3v1Tag();
                case mpeg_aligned:
                    return decodeMPEGFrame();
                case unaligned:
                default:
                    return null;
                }
            } catch (final MPEGAudioCodecException mace) {
                if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                    MPEGAudioFrameInputStream.LOG.debug(mace.getLocalizedMessage());
                }
                if (MPEGAudioFrameInputStream.LOG.isInfoEnabled()) {
                    MPEGAudioFrameInputStream.LOG.info("Dropped Frame."); //$NON-NLS-1$
                }
            } catch (final EndOfStreamException eose) {
                if (MPEGAudioFrameInputStream.LOG.isInfoEnabled()) {
                    MPEGAudioFrameInputStream.LOG.info("End Of Stream."); //$NON-NLS-1$
                }
                return null;
            }
        }
    }

    /**
     * readInt.
     * @param numOfBytes
     * @return {@code int}
     * @throws IOException
     */
    private int readInt(final int numOfBytes) throws IOException {
        int value = 0;
        for (int i = numOfBytes - 1; i > -1; i--) {
            value |= (read() << (i * 8));
        }
        return value;
    }

    /**
     * readLayer1Payload.
     *
     * @param mp3Frame
     *            {@link MPEGAudioFrame}
     * @param maxChannels
     *            {@code int}
     * @throws IOException
     *             in case of bad IO situations.
     */
    private void readLayer1Payload(final MPEGAudioFrame mp3Frame, final int maxChannels) throws IOException {
        mp3Frame.setAllocations(new int[maxChannels][32]);
        mp3Frame.setScalefactors(new int[maxChannels][32]);
        mp3Frame.setSamples(new int[maxChannels][32][12]);
        for (int subband = 0; subband < 32; subband++) {
            for (int channel = 0; channel < maxChannels; channel++) {
                mp3Frame.getAllocations()[channel][subband] = readBits(4);
                // SEBASTIAN validate allocation value
            }
        }
        for (int subband = 0; subband < 32; subband++) {
            for (int channel = 0; channel < maxChannels; channel++) {
                if (mp3Frame.getAllocations()[channel][subband] != 0) {
                    mp3Frame.getScaleFactors()[channel][subband] = readBits(6);
                }
            }
        }
        for (int sample = 0; sample < 12; sample++) {
            for (int subband = 0; subband < 32; subband++) {
                for (int channel = 0; channel < maxChannels; channel++) {
                    if (mp3Frame.getAllocations()[channel][subband] != 0) {
                        mp3Frame.getSamples()[channel][subband][sample] = readBits(
                                mp3Frame.getAllocations()[channel][subband] + 1);
                    }
                }
            }
        }
    }

    /**
     * readStringFromStream.
     * @param length {@code int}
     * @return {@link String}
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private String readStringFromStream(final int length) throws IOException, UnsupportedEncodingException {
        final byte[] buffer = new byte[length];
        readFully(buffer);
        return new String(buffer, "UTF-8").trim(); //$NON-NLS-1$
    }

    /**
     * readSyncSafeInt.
     * @return {@code int}
     * @throws IOException
     */
    private int readSyncSafeInt() throws IOException {
        int size = 0;
        readBit();
        size |= readBits(7) << 21;
        readBit();
        size |= readBits(7) << 14;
        readBit();
        size |= readBits(7) << 7;
        readBit();
        size |= readBits(7);
        return size;
    }

    /**
     * readSyncSave5ByteInteger.
     * @return {@code long}
     * @throws IOException
     */
    private long readSyncSave5ByteInteger() throws IOException {
        long size = 0;
        readBit();
        size |= readBits(7) << 28;
        readBit();
        size |= readBits(7) << 21;
        readBit();
        size |= readBits(7) << 14;
        readBit();
        size |= readBits(7) << 7;
        readBit();
        size |= readBits(7);
        return size;
    }

    /**
     * setUnalignedSyncAllowed.
     * @param unalignedSyncAllowedVal {@code boolean}. If {@code true} sync bits MAY NOT be aligned to byte boundaries.
     */
    public void setUnalignedSyncAllowed(final boolean unalignedSyncAllowedVal) {
        this.unalignedSyncAllowed = unalignedSyncAllowedVal;
    }

    /**
     * sync.
     * @return {@code int} number of skipped bits.
     *
     * @throws IOException
     *             in case of bad IO situations.
     * @throws MPEGAudioCodecException if the end of the stream has been reached.
     */
    SyncResult sync() throws IOException {
        int skippedBits = 0;
        int mpegSyncWord = 0;
        if (isUnalignedSyncAllowed()) {
            mpegSyncWord = readBits(11);
            while (mpegSyncWord != MPEGAudioFrame.SYNC_WORD_PATTERN) {
                mpegSyncWord = ((mpegSyncWord << 1) | readBit()) & MPEGAudioFrame.SYNC_WORD_PATTERN;
                skippedBits++;
            }
        } else {
            while (true) {
                while (!isByteAligned()) {
                    if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                        MPEGAudioFrameInputStream.LOG.debug("sync start wasn't byte aligned..."); //$NON-NLS-1$
                    }
                    readBit();
                    skippedBits++;
                }
                if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                    MPEGAudioFrameInputStream.LOG.debug("Aligend to byte boundaries.."); //$NON-NLS-1$
                }
                int read = 0;
                while (true) {
                    read = read();
                    if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                        MPEGAudioFrameInputStream.LOG.debug("byte read: 0b" + Integer.toBinaryString(read)); //$NON-NLS-1$
                    }
                    if (read == 0xff) {
                        if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                            MPEGAudioFrameInputStream.LOG.debug("byte read 0b11111111."); //$NON-NLS-1$
                        }
                        final int readBits = readBits(3);
                        if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                            MPEGAudioFrameInputStream.LOG
                                    .debug("three bits read: 0b" + Integer.toBinaryString(readBits)); //$NON-NLS-1$
                        }
                        if (readBits == 0b111) {
                            return new SyncResult(SyncMode.mpeg_aligned, skippedBits);
                        }
                        skippedBits += 3;
                    } else if (read == 'T') {
                        read = read();
                        if (read == 'A') {
                            read = read();
                            if (read == 'G') {
                                if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                                    MPEGAudioFrameInputStream.LOG.debug("bytes read TAG."); //$NON-NLS-1$
                                }
                                return new SyncResult(SyncMode.id3v1_aligned, skippedBits);
                            }
                            skippedBits += 24;
                        } else {
                            skippedBits += 16;
                        }
                    } else if (read == 'I') {
                        read = read();
                        if (read == 'D') {
                            read = read();
                            if (read == '3') {
                                if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                                    MPEGAudioFrameInputStream.LOG.debug("bytes read ID3."); //$NON-NLS-1$
                                }
                                return new SyncResult(SyncMode.id3v2_aligned, skippedBits);
                            }
                            skippedBits += 24;
                        } else {
                            skippedBits += 16;
                        }
                    } else {
                        skippedBits += 8;
                    }
                }

            }
        }
        return new SyncResult(SyncMode.unaligned, skippedBits);
    }

}
