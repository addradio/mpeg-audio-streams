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

/**
 * MPEGAudioFrame.
 *
 * The frame header scheme is as follows:
 *
 * |     1st byte   |     2nd byte         |      3rd byte         |       4th byte          |
 * | 1 1 1 1 1 1 1 1 1 1 1 | 0 0 | 0 0 | 0 | 0 0 0 0 | 0 0 | 0 | 0 | 0 0 | 0 0 | 0 | 0 | 0 0 |
 *  \__________ __________/ \_ _/ \_ _/ \ / \___ ___/ \_ _/ \ / \ / \_ _/ \_ _/ \ / \ / \_ _/
 *             V              V     V    V      V       V    V   V    V     V    V   V    V
 *         sync bits          ID  Layer Pro  Bitrate   SR   Pad Pri  CM    ME   Cop Org  Emph
 *
 * If protected flag is set to 1, header is followed by a two byte CRC.
 *
 * That means the header's size may be 4 or 6 bytes in total.
 *
 */
public class MPEGAudioFrame {

    /** {@code int} SYNC_PATTERN_0X7FF */
    public static final int SYNC_PATTERN_0X7FF = 0x7FF;

    /** {@code int} HEADER_SIZE_IN_BYTES. */
    public static final int HEADER_SIZE_IN_BYTES = 4;

    /** {@code int} CRC_SIZE_IN_BYTES. */
    public static final int CRC_SIZE_IN_BYTES = 2;

    /** {@code boolean} _private. */
    private boolean _private = false;

    /** {@code boolean} _protected. */
    private boolean _protected = false;

    /** {@code int[][]} allocations. */
    private int[][] allocations;

    /** {@link BitRate} bitRate. */
    private BitRate bitRate;

    /** {@link ChannelMode} channelMode. */
    private ChannelMode channelMode;

    /** {@link boolean} copyright. */
    private boolean copyright = false;

    /** {@code byte[]} crc. */
    private byte[] crc;

    /** {@link Emphasis} emphasis. */
    private Emphasis emphasis;

    /** {@link Layer} layer. */
    private Layer layer;

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
     * getChannelMode.
     * @return {@link ChannelMode} the channelMode.
     */
    public ChannelMode getChannelMode() {
        return this.channelMode;
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
     * @return {@code int} the frame length incl. header and crc.
     */
    public int getFrameLength() {
        return getPayload().length + HEADER_SIZE_IN_BYTES + (isProtected() ? CRC_SIZE_IN_BYTES : 0);
    }

    /**
     * getLayer.
     * @return {@link Layer} the layer.
     */
    public Layer getLayer() {
        return this.layer;
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
     * isCopyright.
     * @return {@code boolean} the copyright.
     */
    public boolean isCopyright() {
        return this.copyright;
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
     * isProtected.
     * @return {@code boolean} the _protected.
     */
    public boolean isProtected() {
        return this._protected;
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
     * setChannelMode.
     * @param channelModeRef
     *            {@link ChannelMode} the channelMode to set.
     */
    public void setChannelMode(final ChannelMode channelModeRef) {
        this.channelMode = channelModeRef;
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
     * setLayer.
     * @param layerRef
     *            {@link Layer} the layer to set.
     */
    public void setLayer(final Layer layerRef) {
        this.layer = layerRef;
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
     * setProtected.
     * @param protectedVal
     *            {@code boolean} the _protected to set.
     */
    public void setProtected(final boolean protectedVal) {
        this._protected = protectedVal;
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
        builder.append(", _protected="); //$NON-NLS-1$
        builder.append(this._protected);
        builder.append(", bitRate="); //$NON-NLS-1$
        builder.append(this.bitRate);
        builder.append(", samplingRate="); //$NON-NLS-1$
        builder.append(this.samplingRate);
        builder.append(", padding="); //$NON-NLS-1$
        builder.append(this.padding);
        builder.append(", _private="); //$NON-NLS-1$
        builder.append(this._private);
        builder.append(", channelMode="); //$NON-NLS-1$
        builder.append(this.channelMode);
        builder.append(", modeExtension="); //$NON-NLS-1$
        builder.append(this.modeExtension);
        builder.append(", copyright="); //$NON-NLS-1$
        builder.append(this.copyright);
        builder.append(", original="); //$NON-NLS-1$
        builder.append(this.original);
        builder.append(", emphasis="); //$NON-NLS-1$
        builder.append(this.emphasis);
        builder.append("]"); //$NON-NLS-1$
        return builder.toString();
    }

}
