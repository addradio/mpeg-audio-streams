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
import net.addradio.codec.mpeg.audio.codecs.MPEGAudioCodecException;
import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.model.Version;
import net.addradio.streams.BitInputStream;

/**
 * MPEGAudioFrameInputStream
 */
public class MPEGAudioFrameInputStream extends BitInputStream {

    /** {@link Logger} LOG */
    private static final Logger LOG = LoggerFactory.getLogger(MPEGAudioFrameInputStream.class);

    /** {@code int} SYNC_PATTERN_0X7FF */
    private static final int SYNC_PATTERN_0X7FF = 0x7FF;

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
        // SEBASTIAN implement

        mp3Frame.setVersion((Version) BitMaskFlagCodec.decode(readBits(2), Version.class));
        // VersionCodec.decodeVersion(mp3Frame, readBits(2));
        // LayerCodec.decodeLayer(mp3Frame, readBits(2));
        // ProtectionCodec.decodeProtected(mp3Frame, readBit());
        // BitrateCodec.decodeBitrate(mp3Frame, readBits(4));
        // SamplingrateCodec.decodeSamplingrate(mp3Frame, readBits(2));
        // PaddingCodec.decodePadding(mp3Frame, readBit());
        // PrivateCodec.decodePrivate(mp3Frame, readBit());
        // ChannelModeCodec.decodeChannelMode(mp3Frame, readBits(2));
        // ModeExtensionCodec.decodeModeExtension(mp3Frame, readBits(2));
        // CopyrightCodec.decodeCopyright(mp3Frame, readBit());
        // OriginalCodec.decodeOriginal(mp3Frame, readBit());
        // EmphasisCodec.decodeEmphasis(mp3Frame, readBits(2));
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
            mp3Frame.setCrc(new byte[2]);
            readFully(mp3Frame.getCrc());
        }
    }

    /**
     * Reads one frame from the inner stream.
     *
     * @return {@link MPEGAudioFrame}
     * @throws IOException
     *             in case of bad IO situations.
     * @throws MPEGAudioCodecException
     *             if a decoding error occurred.
     */
    public MPEGAudioFrame readFrame() throws IOException, MPEGAudioCodecException {
        final MPEGAudioFrame mpegFrame = new MPEGAudioFrame();
        sync();
        decodeHeader(mpegFrame);
        readCrcIfNeeded(mpegFrame);

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
            // SEBASTIAN implement
        case III:
            // SEBASTIAN implement
        case reserved:
        default:
            break;
        }

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
     * sync.
     *
     * @throws IOException
     *             in case of bad IO situations.
     */
    private void sync() throws IOException {
        int syncWord = readBits(11);
        while (syncWord != MPEGAudioFrameInputStream.SYNC_PATTERN_0X7FF) {
            if (MPEGAudioFrameInputStream.LOG.isTraceEnabled()) {
                MPEGAudioFrameInputStream.LOG.trace("Out of sync [syncword: " + syncWord + "]"); //$NON-NLS-1$ //$NON-NLS-2$
            }
            syncWord = ((syncWord << 1) | readBit()) & MPEGAudioFrameInputStream.SYNC_PATTERN_0X7FF;
        }
        if (MPEGAudioFrameInputStream.LOG.isTraceEnabled()) {
            MPEGAudioFrameInputStream.LOG.trace("Stream is byteAligned [" + isByteAligned() + "]."); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

}
