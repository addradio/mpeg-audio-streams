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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.addradio.codec.mpeg.audio.codecs.BitMaskFlagCodec;
import net.addradio.codec.mpeg.audio.codecs.BitRateCodec;
import net.addradio.codec.mpeg.audio.codecs.MPEGAudioCodecException;
import net.addradio.codec.mpeg.audio.codecs.ModeExtensionCodec;
import net.addradio.codec.mpeg.audio.codecs.SamplingRateCodec;
import net.addradio.codec.mpeg.audio.model.ChannelMode;
import net.addradio.codec.mpeg.audio.model.Emphasis;
import net.addradio.codec.mpeg.audio.model.Layer;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.model.Version;
import net.addradio.streams.BitInputStream;
import net.addradio.streams.EndOfStreamException;

/**
 * MPEGAudioFrameInputStream
 */
public class MPEGAudioFrameInputStream extends BitInputStream {

    /** {@link int} CRC_SIZE_IN_BYTES. */
    private static final int CRC_SIZE_IN_BYTES = 2;

    /** {@link int} HEADER_SIZE_IN_BYTES. */
    private static final int HEADER_SIZE_IN_BYTES = 4;

    /** {@link Logger} LOG */
    private static final Logger LOG = LoggerFactory.getLogger(MPEGAudioFrameInputStream.class);

    /** {@code int} SYNC_PATTERN_0X7FF */
    private static final int SYNC_PATTERN_0X7FF = 0x7FF;

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
        mp3Frame.setProtected(isNextBitTrue());
        mp3Frame.setBitRate(BitRateCodec.decode(mp3Frame, readBits(4)));
        mp3Frame.setSamplingRate(SamplingRateCodec.decode(mp3Frame, readBits(2)));
        mp3Frame.setPadding(isNextBitTrue());
        mp3Frame.setPrivate(isNextBitTrue());
        mp3Frame.setChannelMode((ChannelMode) BitMaskFlagCodec.decode(readBits(2), ChannelMode.class));
        mp3Frame.setModeExtension(ModeExtensionCodec.decode(mp3Frame, readBits(2)));
        mp3Frame.setCopyright(isNextBitTrue());
        mp3Frame.setOriginal(isNextBitTrue());
        mp3Frame.setEmphasis((Emphasis) BitMaskFlagCodec.decode(readBits(2), Emphasis.class));
    }

    /**
     * isNextBitTrue. Reads just one bit and checks whether it is 0b1 or not.
     *
     * @return {@code boolean true} only if the next bit is 0b1.
     * @throws IOException
     * @throws MPEGAudioCodecException if end of stream has been reached.
     */
    private boolean isNextBitTrue() throws IOException, MPEGAudioCodecException {
        final int readBit = readBit();
        if (readBit == -1) {
            throw new MPEGAudioCodecException("End of stream has been reached during decoding of frame."); //$NON-NLS-1$
        }
        return readBit == 1;
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
        if (mp3Frame.isProtected()) {
            mp3Frame.setCrc(new byte[CRC_SIZE_IN_BYTES]);
            readFully(mp3Frame.getCrc());
        }
    }

    /**
     * Reads one frame from the inner stream.
     *
     * @return {@link MPEGAudioFrame} or {@code null} if end of stream has been reached.
     * @throws IOException
     *             in case of bad IO situations.
     */
    public MPEGAudioFrame readFrame() throws IOException {
        while (true) {
            try {
                final MPEGAudioFrame mpegFrame = new MPEGAudioFrame();

                assertByteAlignement();
                sync();
                decodeHeader(mpegFrame);

                assertByteAlignement();
                readCrcIfNeeded(mpegFrame);

                assertByteAlignement();
                switch (mpegFrame.getLayer()) {
                case I:
                    switch (mpegFrame.getChannelMode()) {
                    case SingleChannel:
                        readLayer1Payload(mpegFrame, 1);
                        break;
                    case Stereo:
                    case DualChannel:
                        readLayer1Payload(mpegFrame, 2);
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
                    final int frameLengthInBytes = (((144 * mpegFrame.getBitRate().getValue())
                            / mpegFrame.getSamplingRate().getValue()) + (mpegFrame.isPadding() ? 1 : 0))
                            - HEADER_SIZE_IN_BYTES - (mpegFrame.isProtected() ? CRC_SIZE_IN_BYTES : 0);
                    if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                        MPEGAudioFrameInputStream.LOG.debug("[framelength: " + frameLengthInBytes + "]"); //$NON-NLS-1$ //$NON-NLS-2$
                    }
                    mpegFrame.setPayload(new byte[frameLengthInBytes]);
                    readFully(mpegFrame.getPayload());
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
                return mpegFrame;
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
     * @param unalignedSyncAllowed {@code boolean}. If {@code true} sync bits MAY NOT be aligned to byte boundaries.
     */
    public void setUnalignedSyncAllowed(final boolean unalignedSyncAllowed) {
        this.unalignedSyncAllowed = unalignedSyncAllowed;
    }

    /**
     * sync.
     * @return {@code int} number of skipped bits.
     *
     * @throws IOException
     *             in case of bad IO situations.
     * @throws MPEGAudioCodecException if the end of the stream has been reached.
     */
    int sync() throws IOException {
        int skippedBits = 0;
        int syncWord = 0;
        if (isUnalignedSyncAllowed()) {
            syncWord = readBits(11);
            while (syncWord != MPEGAudioFrameInputStream.SYNC_PATTERN_0X7FF) {
                syncWord = ((syncWord << 1) | readBit()) & MPEGAudioFrameInputStream.SYNC_PATTERN_0X7FF;
                skippedBits++;
            }
        } else {
            while (syncWord != MPEGAudioFrameInputStream.SYNC_PATTERN_0X7FF) {
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
                while ((read = read()) != 0xff) {
                    if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                        MPEGAudioFrameInputStream.LOG.debug("byte read: 0b" + Integer.toBinaryString(read)); //$NON-NLS-1$
                    }
                    skippedBits += 8;
                }
                if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                    MPEGAudioFrameInputStream.LOG.debug("byte read 0b11111111."); //$NON-NLS-1$
                }
                final int readBits = readBits(3);
                if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
                    MPEGAudioFrameInputStream.LOG.debug("three bits read: 0b" + Integer.toBinaryString(readBits)); //$NON-NLS-1$
                }
                if (readBits == 0b111) {
                    syncWord = SYNC_PATTERN_0X7FF;
                } else {
                    skippedBits += 3;
                }
            }
        }
        if (MPEGAudioFrameInputStream.LOG.isDebugEnabled()) {
            MPEGAudioFrameInputStream.LOG.debug("[skippedBits: " + skippedBits //$NON-NLS-1$
                    + "]"); //$NON-NLS-1$
        }
        return skippedBits;
    }

}
