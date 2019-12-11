/**
 * Class:    MPEGAudioFrame<br/>
 * <br/>
 * Created:  19.10.2017<br/>
 * Filename: MPEGAudioFrame.java<br/>
 * Version:  $Revision$<br/>
 * <br/>
 * last modified on $Date$<br/>
 *               by $Author$<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author$ -- $Revision$ -- $Date$
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2017 - All rights reserved.
 */
package net.addradio.codec.mpeg.audio.model;

import java.util.Arrays;

/**
 * MPEGAudioFrame.
 *
 * The frame header scheme is as follows (compatible with MPEG 2.5):
 *
 * <pre>
 * |     1st byte   |     2nd byte         |      3rd byte         |       4th byte          |
 * | 1 1 1 1 1 1 1 1 1 1 1 | 0 0 | 0 0 | 0 | 0 0 0 0 | 0 0 | 0 | 0 | 0 0 | 0 0 | 0 | 0 | 0 0 |
 *  \__________ __________/ \_ _/ \_ _/ \ / \___ ___/ \_ _/ \ / \ / \_ _/ \_ _/ \ / \ / \_ _/
 *             V              V     V    V      V       V    V   V    V     V    V   V    V
 *         syncword          ID   layer  | bitrate_index|    | private|     |    | org/cop|
 *                                error_protection      | padding   mode    | copyright emphasis
 *                                             sampling_frequency     mode_extension
 * </pre>
 *
 * If error_protection flag is set to 1, header is followed by a two byte CRC.
 *
 */
public class MPEGAudioFrame implements MPEGAudioContent {

    /** {@code int} CRC_SIZE_IN_BYTES. */
    public static final int CRC_SIZE_IN_BYTES = 2;

    /** {@code int} HEADER_SIZE_IN_BYTES. */
    public static final int HEADER_SIZE_IN_BYTES = 4;

    /** {@code int} SYNC_WORD_PATTERN */
    public static final int SYNC_WORD_PATTERN = 0x7FF;

    /**
     * calculateAverageBitRate.
     * @param durationMillisVal {@code long}
     * @param payloadLengthInBytes {@code long}
     * @return {@code double}
     */
    public static final double calculateAverageBitRate(final long durationMillisVal, final long payloadLengthInBytes) {
        return (payloadLengthInBytes * 8d * 1000) / durationMillisVal;
    }

    /**
     * calculateDuration.
     * @param bitRateInBps {@code int}
     * @param payloadLengthInBytes {@code int}
     * @return {@code double}
     */
    public static final double calculateDuration(final int bitRateInBps, final int payloadLengthInBytes) {
        return (payloadLengthInBytes * 8d * 1000) / bitRateInBps;
    }

    /** {@code boolean} _private. */
    private boolean _private = false;

    /** {@code int[][]} allocations. */
    private int[][] allocations;

    /** {@link BitRate} bitRate. */
    private BitRate bitRate;

    /** {@code boolean} copyright. */
    private boolean copyright = false;

    /** {@code byte[]} crc. */
    private byte[] crc;

    /** {@link Emphasis} emphasis. */
    private Emphasis emphasis;

    /** {@code boolean} errorProtected. */
    private boolean errorProtected = false;

    /** {@code int[][]} globalGain */
    private int[][] globalGain;

    /** {@link Layer} layer. */
    private Layer layer;

    /** {@code int} mainDataBegin */
    private int mainDataBegin;

    /** {@link Mode} mode. */
    private Mode mode;

    /** {@link ModeExtension} modeExtension. */
    private ModeExtension modeExtension;

    /** {@code boolean} original. */
    private boolean original = false;

    /** {@code boolean} padding. */
    private boolean padding = false;

    /** {@code byte[]} payload. */
    private byte[] payload;

    /** {@code int[][][]} samples. */
    private int[][][] samples;

    /** {@link SamplingRate} samplingRate. */
    private SamplingRate samplingRate;

    /** {@code int[][]} scaleFactors. */
    private int[][] scaleFactors;

    /** {@link Version} version. */
    private Version version;

    /** {@link int} numberOfSlots. */
    private int numberOfSlots = -1;

    /**
     * getNumberOfSlots.
     * @return int the numberOfSlots
     */
    public int getNumberOfSlots() {
        if (this.numberOfSlots < 0) {
            this.numberOfSlots = 0;
            switch (getLayer()) {
            case III:
                switch (getVersion()) {
                case MPEG_1:
                    switch (getMode()) {
                    case SingleChannel:
                        this.numberOfSlots = getPayload().length - 17;
                        break;
                    case DualChannel:
                    case JointStereo:
                    case Stereo:
                    default:
                        this.numberOfSlots = getPayload().length - 32;
                        break;
                    }
                    break;
                case MPEG_2_5:
                case MPEG_2_LSF:
                    switch (getMode()) {
                    case SingleChannel:
                        this.numberOfSlots = getPayload().length - 9;
                        break;
                    case DualChannel:
                    case JointStereo:
                    case Stereo:
                    default:
                        this.numberOfSlots = getPayload().length - 17;
                        break;
                    }
                    break;
                case reserved:
                default:
                    break;
                }
                break;
            case I:
            case II:
            case reserved:
            default:
                break;
            }

        }
        return this.numberOfSlots;
    }

    /**
     * calculateDurationMillis.
     * @return {@code long} duration of frame in milliseconds or
     *         {@code -1} if duration could not be calculated due to decoding issues..
     */
    public long calculateDurationMillis() {
        final int frameLength = getFrameLength();
        if (frameLength < 0) {
            return -1;
        }
        return Math.round(calculateDuration(getBitRate().getValueInBps(), frameLength));
    }

    /**
     * equals.
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj {@link Object}
     * @return {@code boolean}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MPEGAudioFrame)) {
            return false;
        }
        final MPEGAudioFrame other = (MPEGAudioFrame) obj;
        if (this._private != other._private) {
            return false;
        }
        if (this.bitRate != other.bitRate) {
            return false;
        }
        if (this.copyright != other.copyright) {
            return false;
        }
        if (!Arrays.equals(this.crc, other.crc)) {
            return false;
        }
        if (this.emphasis != other.emphasis) {
            return false;
        }
        if (this.errorProtected != other.errorProtected) {
            return false;
        }
        if (!Arrays.deepEquals(this.globalGain, other.globalGain)) {
            return false;
        }
        if (this.layer != other.layer) {
            return false;
        }
        if (this.mode != other.mode) {
            return false;
        }
        if (this.modeExtension != other.modeExtension) {
            return false;
        }
        if (this.original != other.original) {
            return false;
        }
        if (this.padding != other.padding) {
            return false;
        }
        if (this.samplingRate != other.samplingRate) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        return true;
    }

    /**
     * getAllocations.
     * @return {@code int[][]} the allocations.
     */
    public int[][] getAllocations() {
        return this.allocations;
    }

    /**
     * getBitrate.
     * @return {@link BitRate} the bitRate.
     */
    public BitRate getBitRate() {
        return this.bitRate;
    }

    /**
     * getChannels.
     * @return {@code int} number of channels.
     */
    public int getChannels() {
        switch (getMode()) {
        case SingleChannel:
            return 1;
        case DualChannel:
        case JointStereo:
        case Stereo:
        default:
            return 2;
        }
    }

    /**
     * getCrc.
     * @return {@code byte[]} the crc.
     */
    public byte[] getCrc() {
        return this.crc;
    }

    /**
     * getEmphasis.
     * @return {@link Emphasis} the emphasis.
     */
    public Emphasis getEmphasis() {
        return this.emphasis;
    }

    /**
     * getFrameLength.
     * @return {@code int} the frame length in bytes incl. header and crc or
     *         {@code -1} if length of frame is not available (due to decoding issues.).
     */
    public int getFrameLength() {
        if (getPayload() == null) {
            return -1;
        }
        return getPayload().length + MPEGAudioFrame.HEADER_SIZE_IN_BYTES
                + (isErrorProtected() ? MPEGAudioFrame.CRC_SIZE_IN_BYTES : 0);
    }

    /**
     * @return the {@code int[][]} globalGain
     */
    public int[][] getGlobalGain() {
        return this.globalGain;
    }

    /**
     * getLayer.
     * @return {@link Layer} the layer.
     */
    public Layer getLayer() {
        return this.layer;
    }

    /**
     * @return the {@code int} mainDataBegin
     */
    public int getMainDataBegin() {
        return this.mainDataBegin;
    }

    /**
     * getChannelMode.
     * @return {@link Mode} the mode.
     */
    public Mode getMode() {
        return this.mode;
    }

    /**
     * getModeExtension.
     * @return {@link ModeExtension} the modeExtension.
     */
    public ModeExtension getModeExtension() {
        return this.modeExtension;
    }

    /**
     * @return the payload
     */
    public byte[] getPayload() {
        return this.payload;
    }

    /**
     * getSamples.
     * @return <code>int[][][]</code> the samples.
     */
    public int[][][] getSamples() {
        return this.samples;
    }

    /**
     * getSamplingrate.
     * @return {@link SamplingRate} the samplingRate.
     */
    public SamplingRate getSamplingRate() {
        return this.samplingRate;
    }

    /**
     * getScaleFactors.
     * @return {@code int[][]} the scaleFactors.
     */
    public int[][] getScaleFactors() {
        return this.scaleFactors;
    }

    /**
     * getVersion.
     * @return {@link Version} the version.
     */
    public Version getVersion() {
        return this.version;
    }

    /**
     * hashCode.
     * @see java.lang.Object#hashCode()
     * @return {@code int}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (this._private ? 1231 : 1237);
        result = (prime * result) + ((this.bitRate == null) ? 0 : this.bitRate.hashCode());
        result = (prime * result) + (this.copyright ? 1231 : 1237);
        result = (prime * result) + Arrays.hashCode(this.crc);
        result = (prime * result) + ((this.emphasis == null) ? 0 : this.emphasis.hashCode());
        result = (prime * result) + (this.errorProtected ? 1231 : 1237);
        result = (prime * result) + Arrays.deepHashCode(this.globalGain);
        result = (prime * result) + ((this.layer == null) ? 0 : this.layer.hashCode());
        result = (prime * result) + ((this.mode == null) ? 0 : this.mode.hashCode());
        result = (prime * result) + ((this.modeExtension == null) ? 0 : this.modeExtension.hashCode());
        result = (prime * result) + (this.original ? 1231 : 1237);
        result = (prime * result) + (this.padding ? 1231 : 1237);
        result = (prime * result) + ((this.samplingRate == null) ? 0 : this.samplingRate.hashCode());
        result = (prime * result) + ((this.version == null) ? 0 : this.version.hashCode());
        return result;
    }

    /**
     * isCopyright.
     * @return {@code boolean} the copyright.
     */
    public boolean isCopyright() {
        return this.copyright;
    }

    /**
     * isErrorProtected.
     * @return {@code boolean} the errorProtected.
     */
    public boolean isErrorProtected() {
        return this.errorProtected;
    }

    /**
     * isID3Tag.
     * @see net.addradio.codec.mpeg.audio.model.MPEGAudioContent#isID3Tag()
     * @return {@code boolean false} always.
     */
    @Override
    public boolean isID3Tag() {
        return false;
    }

    /**
     * isMPEGAudioFrame.
     * @see net.addradio.codec.mpeg.audio.model.MPEGAudioContent#isMPEGAudioFrame()
     * @return {@code boolean true} always.
     */
    @Override
    public boolean isMPEGAudioFrame() {
        return true;
    }

    /**
     * isOriginal.
     * @return {@code boolean} the original.
     */
    public boolean isOriginal() {
        return this.original;
    }

    /**
     * isPadding.
     * @return {@code boolean} the padding.
     */
    public boolean isPadding() {
        return this.padding;
    }

    /**
     * isPrivate.
     * @return {@code boolean} the _private.
     */
    public boolean isPrivate() {
        return this._private;
    }

    /**
     * setAllocations.
     * @param allocationsRef {@code int[][]} the allocations to set.
     */
    public void setAllocations(final int[][] allocationsRef) {
        this.allocations = allocationsRef;
    }

    /**
     * setBitRate.
     * @param bitrateRef
     *            {@link BitRate} the bitRate to set.
     */
    public void setBitRate(final BitRate bitrateRef) {
        this.bitRate = bitrateRef;
    }

    /**
     * setCopyright.
     * @param copyrightVal
     *            {@code boolean} the copyright to set.
     */
    public void setCopyright(final boolean copyrightVal) {
        this.copyright = copyrightVal;
    }

    /**
     * setCrc.
     * @param crcRef {@code byte[]} the crc to set.
     */
    public void setCrc(final byte[] crcRef) {
        this.crc = crcRef;
    }

    /**
     * setEmphasis.
     * @param emphasisRef
     *            {@link Emphasis} the emphasis to set.
     */
    public void setEmphasis(final Emphasis emphasisRef) {
        this.emphasis = emphasisRef;
    }

    /**
     * setErrorProtected.
     * @param errorProtectedVal
     *            {@code boolean} the errorProtected to set.
     */
    public void setErrorProtected(final boolean errorProtectedVal) {
        this.errorProtected = errorProtectedVal;
    }

    /**
     * @param globalGainRef {@code int[][]} the globalGain to set
     */
    public void setGlobalGain(final int[][] globalGainRef) {
        this.globalGain = globalGainRef;
    }

    /**
     * setLayer.
     * @param layerRef
     *            {@link Layer} the layer to set.
     */
    public void setLayer(final Layer layerRef) {
        this.layer = layerRef;
    }

    /**
     * @param mainDataBeginVal {@code int} the mainDataBegin to set
     */
    public void setMainDataBegin(final int mainDataBeginVal) {
        this.mainDataBegin = mainDataBeginVal;
    }

    /**
     * setChannelMode.
     * @param channelModeRef
     *            {@link Mode} the mode to set.
     */
    public void setMode(final Mode channelModeRef) {
        this.mode = channelModeRef;
    }

    /**
     * setModeExtension.
     * @param modeExtensionRef
     *            {@link ModeExtension} the modeExtension to set.
     */
    public void setModeExtension(final ModeExtension modeExtensionRef) {
        this.modeExtension = modeExtensionRef;
    }

    /**
     * setOriginal.
     * @param originalVal
     *            {@code boolean} the original to set.
     */
    public void setOriginal(final boolean originalVal) {
        this.original = originalVal;
    }

    /**
     * setPadding.
     * @param paddingVal
     *            {@code boolean} the padding to set.
     */
    public void setPadding(final boolean paddingVal) {
        this.padding = paddingVal;
    }

    /**
     * setPayload.
     * @param payloadRef {@code byte[]} the payload to set.
     */
    public void setPayload(final byte[] payloadRef) {
        this.payload = payloadRef;
    }

    /**
     * setPrivate.
     * @param privateVal
     *            {@code boolean} the _private to set.
     */
    public void setPrivate(final boolean privateVal) {
        this._private = privateVal;
    }

    /**
     * setSamples.
     * @param samplesRef {@code int[][]} the samples to set.
     */
    public void setSamples(final int[][][] samplesRef) {
        this.samples = samplesRef;
    }

    /**
     * setSamplingRate.
     * @param samplingRateRef
     *            {@link SamplingRate} the samplingRate to set.
     */
    public void setSamplingRate(final SamplingRate samplingRateRef) {
        this.samplingRate = samplingRateRef;
    }

    /**
     * setScalefactors.
     * @param scalefactorsRef {@code int[][]} the scaleFactors to set.
     */
    public void setScalefactors(final int[][] scalefactorsRef) {
        this.scaleFactors = scalefactorsRef;
    }

    /**
     * setVersion.
     * @param versionRef
     *            {@link Version} the version to set.
     */
    public void setVersion(final Version versionRef) {
        this.version = versionRef;
    }

    /**
     * toString.
     * @return {@link String}
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(MPEGAudioFrame.class.getSimpleName() + " [version="); //$NON-NLS-1$
        builder.append(this.version);
        builder.append(", layer="); //$NON-NLS-1$
        builder.append(this.layer);
        builder.append(", errorProtected="); //$NON-NLS-1$
        builder.append(this.errorProtected);
        builder.append(", bitRate="); //$NON-NLS-1$
        builder.append(this.bitRate);
        builder.append(", samplingRate="); //$NON-NLS-1$
        builder.append(this.samplingRate);
        builder.append(", padding="); //$NON-NLS-1$
        builder.append(this.padding);
        builder.append(", _private="); //$NON-NLS-1$
        builder.append(this._private);
        builder.append(", mode="); //$NON-NLS-1$
        builder.append(this.mode);
        builder.append(", modeExtension="); //$NON-NLS-1$
        builder.append(this.modeExtension);
        builder.append(", copyright="); //$NON-NLS-1$
        builder.append(this.copyright);
        builder.append(", original="); //$NON-NLS-1$
        builder.append(this.original);
        builder.append(", emphasis="); //$NON-NLS-1$
        builder.append(this.emphasis);
        builder.append(", globalGain="); //$NON-NLS-1$
        builder.append(Arrays.deepToString(this.globalGain));
        builder.append("]"); //$NON-NLS-1$
        return builder.toString();
    }

}
