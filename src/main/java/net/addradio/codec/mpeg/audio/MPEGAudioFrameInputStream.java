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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.id3.codecs.ID3CodecTools;
import net.addradio.codec.id3.codecs.ID3v1TagCodec;
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

    /**
     * readSyncSave5ByteInteger.
     * @param bis {@link BitInputStream}
     * @return {@code long}
     * @throws IOException due to IO problems.
     */
    public static final long readSyncSave5ByteInteger(final BitInputStream bis) throws IOException {
        long size = 0;
        bis.readBit();
        size |= bis.readBits(7) << 28;
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

    /** {@code boolean} unalignedSyncAllowed. */
    private boolean unalignedSyncAllowed = false;

    /** {@code long} skippedBits. */
    private transient long skippedBits = 0;

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
     * assureByteAlignement.
     * @return {@code int} number of skipped bits during alignment.
     * @throws IOException
     */
    private int assureByteAlignement() throws IOException {
        int skippedBitsVal = 0;
        while (!isByteAligned()) {
            if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                MPEGAudioFrameInputStream.LOG.debug("sync start wasn't byte aligned..."); //$NON-NLS-1$
            }
            readBit();
            skippedBitsVal++;
        }
        assertByteAlignement();
        if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
            MPEGAudioFrameInputStream.LOG.debug("Aligend to byte boundaries.."); //$NON-NLS-1$
        }
        return skippedBitsVal;
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
        // (20171113 saw) bug fix: frame is error protected if bit is 0 not 1!
        mp3Frame.setErrorProtected(isNextBitZero());
        assertByteAlignement();
        mp3Frame.setBitRate(BitRateCodec.decode(mp3Frame, readBits(4)));
        mp3Frame.setSamplingRate(SamplingRateCodec.decode(mp3Frame, readBits(2)));
        mp3Frame.setPadding(isNextBitOne());
        mp3Frame.setPrivate(isNextBitOne());
        assertByteAlignement();
        mp3Frame.setMode((Mode) BitMaskFlagCodec.decode(readBits(2), Mode.class));
        mp3Frame.setModeExtension(ModeExtensionCodec.decode(mp3Frame, readBits(2)));
        mp3Frame.setCopyright(isNextBitOne());
        mp3Frame.setOriginal(isNextBitOne());
        mp3Frame.setEmphasis((Emphasis) BitMaskFlagCodec.decode(readBits(2), Emphasis.class));
        assertByteAlignement();
    }

    /**
     * decodeMPEGFrame.
     * @return {@link MPEGAudioFrame}
     * @throws IOException
     * @throws MPEGAudioCodecException
     */
    private MPEGAudioFrame decodeMPEGFrame() throws IOException, MPEGAudioCodecException {
        try {
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

                switch (frame.getMode()) {
                case SingleChannel:
                    frame.setGlobalGain(new int[2][1]);
                    try (BitInputStream bis = new BitInputStream(new ByteArrayInputStream(frame.getPayload()))) {
                        frame.setMainDataBegin(bis.readBits(9));
                        bis.skipBits(9);
                        for (int gr = 0; gr < 2; gr++) {
                            bis.skipBits(21);
                            frame.getGlobalGain()[gr][0] = bis.read();
                            bis.skipBits(30);
                        }
                    }
                    break;
                case DualChannel:
                case JointStereo:
                case Stereo:
                default:
                    frame.setGlobalGain(new int[2][2]);
                    try (BitInputStream bis = new BitInputStream(new ByteArrayInputStream(frame.getPayload()))) {
                        frame.setMainDataBegin(bis.readBits(9));
                        bis.skipBits(11);
                        for (int gr = 0; gr < 2; gr++) {
                            for (int ch = 0; ch < 2; ch++) {
                                bis.skipBits(21);
                                frame.getGlobalGain()[gr][ch] = bis.read();
                                bis.skipBits(30);
                            }
                        }
                    }
                    break;
                }

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
        } catch (final NegativeArraySizeException nase) {
            throw new MPEGAudioCodecException(nase);
        }
    }

    /**
     * getSkippedBits.
     * @return {@code long} the skippedBits
     */
    public long getSkippedBits() {
        return this.skippedBits;
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
            //            System.out.println("MAFIS is error protected");
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
                final SyncResult syncResult = sync();
                if ((syncResult.getSkippedBits() > 0) && LOG.isInfoEnabled()) {
                    LOG.info("[skippedBits during sync: " + syncResult.getSkippedBits() + ", in bytes: " //$NON-NLS-1$ //$NON-NLS-2$
                            + (syncResult.getSkippedBits() / 8f) + "]"); //$NON-NLS-1$
                }
                this.skippedBits += syncResult.getSkippedBits();
                switch (syncResult.getMode()) {
                case id3v2_aligned:
                    return ID3CodecTools.decodeID3v2Tag(this);
                case id3v1_aligned:
                    return ID3v1TagCodec.decodeID3v1Tag(this);
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
        int skippedBitsVal = 0;
        int mpegSyncWord = 0;
        if (isUnalignedSyncAllowed()) {
            mpegSyncWord = readBits(11);
            while (mpegSyncWord != MPEGAudioFrame.SYNC_WORD_PATTERN) {
                mpegSyncWord = ((mpegSyncWord << 1) | readBit()) & MPEGAudioFrame.SYNC_WORD_PATTERN;
                skippedBitsVal++;
            }
        } else {
            int read = 0;
            while (true) {
                skippedBitsVal += assureByteAlignement();
                read = read();
                if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                    MPEGAudioFrameInputStream.LOG.debug("byte read: 0b" + Integer.toBinaryString(read)); //$NON-NLS-1$
                }
                if (read == 0xff) {
                    if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                        MPEGAudioFrameInputStream.LOG.debug("byte read 0b11111111."); //$NON-NLS-1$
                    }
                    assertByteAlignement();
                    final int readBits = readBits(3);
                    if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                        MPEGAudioFrameInputStream.LOG.debug("three bits read: 0b" + Integer.toBinaryString(readBits)); //$NON-NLS-1$
                    }
                    if (readBits == 0b111) {
                        return new SyncResult(SyncMode.mpeg_aligned, skippedBitsVal);
                    }
                    skippedBitsVal += 3;
                } else if (read == 'T') {
                    read = read();
                    if (read == 'A') {
                        read = read();
                        if (read == 'G') {
                            if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                                MPEGAudioFrameInputStream.LOG.debug("bytes read TAG."); //$NON-NLS-1$
                            }
                            return new SyncResult(SyncMode.id3v1_aligned, skippedBitsVal);
                        }
                        skippedBitsVal += 24;
                    } else {
                        skippedBitsVal += 16;
                    }
                } else if (read == 'I') {
                    read = read();
                    if (read == 'D') {
                        read = read();
                        if (read == '3') {
                            if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                                MPEGAudioFrameInputStream.LOG.debug("bytes read ID3."); //$NON-NLS-1$
                            }
                            return new SyncResult(SyncMode.id3v2_aligned, skippedBitsVal);
                        }
                        skippedBitsVal += 24;
                    } else {
                        skippedBitsVal += 16;
                    }
                } else {
                    skippedBitsVal += 8;
                }
            }
        }
        return new SyncResult(SyncMode.unaligned, skippedBitsVal);
    }

}
