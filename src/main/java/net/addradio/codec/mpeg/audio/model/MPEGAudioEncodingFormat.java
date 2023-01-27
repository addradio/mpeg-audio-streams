/**
 * Class:    MPEGAudioEncodingFormat<br/>
 * <br/>
 * Created:  26.10.2017<br/>
 * Filename: MPEGAudioEncodingFormat.java<br/>
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
 * MPEGAudioEncodingFormat.
 */
public class MPEGAudioEncodingFormat implements EncodingFormat {

    /** {@link String} MPEG_ENCODING_FORMAT_NAME. */
    private static final String MPEG_ENCODING_FORMAT_NAME = "MPEG-Audio"; //$NON-NLS-1$

    /** {@code boolean} _private. */
    private boolean _private = false;

    /** {@link BitRate} bitRate */
    private BitRate bitRate;

    /** {@code boolean} copyright. */
    private boolean copyright = false;

    /** {@link Emphasis} emphasis. */
    private Emphasis emphasis;

    /** {@code boolean} errorProtected. */
    private boolean errorProtected = false;

    /** {@link Layer} layer. */
    private Layer layer;

    /** {@link Mode} mode. */
    private Mode mode;

    /** {@link ModeExtension} modeExtension. */
    private ModeExtension modeExtension;

    /** {@link String} name. */
    private String name;

    /** {@code boolean} original. */
    private boolean original = false;

    /** {@link SamplingRate} samplingRate */
    private SamplingRate samplingRate;

    /** {@link Version} version. */
    private Version version;

    /**
     * MPEGAudioEncodingFormat constructor.
     */
    public MPEGAudioEncodingFormat() {
        setName(MPEGAudioEncodingFormat.MPEG_ENCODING_FORMAT_NAME);
    }

    /**
     * clone.
     * @return {@link EncodingFormat}
     */
    @Override
    public EncodingFormat clone() {
        final MPEGAudioEncodingFormat clone = new MPEGAudioEncodingFormat();
        clone.setBitRate(getBitRate());
        clone.setCopyright(isCopyright());
        clone.setEmphasis(getEmphasis());
        clone.setErrorProtected(isErrorProtected());
        clone.setLayer(getLayer());
        clone.setMode(getMode());
        clone.setModeExtension(getModeExtension());
        clone.setOriginal(isOriginal());
        clone.setPrivate(isPrivate());
        clone.setSamplingRate(getSamplingRate());
        clone.setVersion(getVersion());
        return clone;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MPEGAudioEncodingFormat)) {
            return false;
        }
        final MPEGAudioEncodingFormat other = (MPEGAudioEncodingFormat) obj;
        if (this._private != other._private) {
            return false;
        }
        if (this.bitRate != other.bitRate) {
            return false;
        }
        if (this.copyright != other.copyright) {
            return false;
        }
        if (this.emphasis != other.emphasis) {
            return false;
        }
        if (this.errorProtected != other.errorProtected) {
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
        if (this.samplingRate != other.samplingRate) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        return true;
    }

    /**
     * @return the {@link BitRate} bitRate
     */
    public BitRate getBitRate() {
        return this.bitRate;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.model.EncodingFormat#getBitRateInBps()
     */
    @Override
    public int getBitRateInBps() {
        return this.bitRate.getValueInBps();
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.model.EncodingFormat#getBitRateInBpsInteger()
     */
    @Override
    public Integer getBitRateInBpsInteger() {
        return this.bitRate.getValueInBpsInteger();
    }

    /**
     * getEmphasis.
     * @return Emphasis the emphasis
     */
    public Emphasis getEmphasis() {
        return this.emphasis;
    }

    /**
     * getLayer.
     * @return Layer the layer
     */
    public Layer getLayer() {
        return this.layer;
    }

    /**
     * getMode.
     * @return Mode the mode
     */
    public Mode getMode() {
        return this.mode;
    }

    /**
     * getModeExtension.
     * @return ModeExtension the modeExtension
     */
    public ModeExtension getModeExtension() {
        return this.modeExtension;
    }

    /**
     * getName.
     * @return {@link String} the name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return the {@link SamplingRate} samplingRate
     */
    public SamplingRate getSamplingRate() {
        return this.samplingRate;
    }

    /**
     * getSamplingRate.
     * @return {@code int} the samplingRate
     */
    public int getSamplingRateInHz() {
        return this.samplingRate.getValueInHz();
    }

    /**
     * getVersion.
     * @return Version the version
     */
    public Version getVersion() {
        return this.version;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (this._private ? 1231 : 1237);
        result = (prime * result) + ((this.bitRate == null) ? 0 : this.bitRate.hashCode());
        result = (prime * result) + (this.copyright ? 1231 : 1237);
        result = (prime * result) + ((this.emphasis == null) ? 0 : this.emphasis.hashCode());
        result = (prime * result) + (this.errorProtected ? 1231 : 1237);
        result = (prime * result) + ((this.layer == null) ? 0 : this.layer.hashCode());
        result = (prime * result) + ((this.mode == null) ? 0 : this.mode.hashCode());
        result = (prime * result) + ((this.modeExtension == null) ? 0 : this.modeExtension.hashCode());
        result = (prime * result) + (this.original ? 1231 : 1237);
        result = (prime * result) + ((this.samplingRate == null) ? 0 : this.samplingRate.hashCode());
        result = (prime * result) + ((this.version == null) ? 0 : this.version.hashCode());
        return result;
    }

    /**
     * isCopyright.
     * @return boolean the copyright
     */
    public boolean isCopyright() {
        return this.copyright;
    }

    /**
     * isErrorProtected.
     * @return boolean the errorProtected
     */
    public boolean isErrorProtected() {
        return this.errorProtected;
    }

    /**
     * isOriginal.
     * @return boolean the original
     */
    public boolean isOriginal() {
        return this.original;
    }

    /**
     * isPrivate.
     * @return boolean the _private
     */
    public boolean isPrivate() {
        return this._private;
    }

    /**
     * @param bitRateRef {@link BitRate} the bitRate to set
     */
    public void setBitRate(final BitRate bitRateRef) {
        this.bitRate = bitRateRef;
    }

    /**
     * setCopyright.
     * @param copyrightVal {@code boolean} the copyright to set
     */
    public void setCopyright(final boolean copyrightVal) {
        this.copyright = copyrightVal;
    }

    /**
     * setEmphasis.
     * @param emphasisRef {@link Emphasis} the emphasis to set
     */
    public void setEmphasis(final Emphasis emphasisRef) {
        this.emphasis = emphasisRef;
    }

    /**
     * setErrorProtected.
     * @param errorProtectedVal {@code boolean} the errorProtected to set
     */
    public void setErrorProtected(final boolean errorProtectedVal) {
        this.errorProtected = errorProtectedVal;
    }

    /**
     * setLayer.
     * @param layerRef {@link Layer} the layer to set
     */
    public void setLayer(final Layer layerRef) {
        this.layer = layerRef;
    }

    /**
     * setMode.
     * @param modeRef {@link Mode} the mode to set
     */
    public void setMode(final Mode modeRef) {
        this.mode = modeRef;
    }

    /**
     * setModeExtension.
     * @param modeExtensionRef {@link ModeExtension} the modeExtension to set
     */
    public void setModeExtension(final ModeExtension modeExtensionRef) {
        this.modeExtension = modeExtensionRef;
    }

    /**
     * setName.
     * @param nameVal {@link String} the name to set
     */
    public void setName(final String nameVal) {
        this.name = nameVal;
    }

    /**
     * setOriginal.
     * @param originalVal {@code boolean} the original to set
     */
    public void setOriginal(final boolean originalVal) {
        this.original = originalVal;
    }

    /**
     * setPrivate.
     * @param privateVal {@code boolean} the _private to set
     */
    public void setPrivate(final boolean privateVal) {
        this._private = privateVal;
    }

    /**
     * @param samplingRateRef {@link SamplingRate} the samplingRate to set
     */
    public void setSamplingRate(final SamplingRate samplingRateRef) {
        this.samplingRate = samplingRateRef;
    }

    /**
     * setVersion.
     * @param versionRef {@link Version} the version to set
     */
    public void setVersion(final Version versionRef) {
        this.version = versionRef;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.model.EncodingFormat#toLongForm()
     */
    @Override
    public String toLongForm() {
        // SEBASTIAN implement
        return toShortForm();
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.mpeg.audio.model.EncodingFormat#toShortForm()
     */
    @Override
    public String toShortForm() {
        return String.format("%s [%s-%s/%s/%s/%s]", getName(), getVersion() != null ? getVersion().name() : null, //$NON-NLS-1$
                getLayer() != null ? getLayer().name() : null, getMode() != null ? getMode().name() : null,
                getSamplingRate() != null ? getSamplingRate().name() : null,
                getBitRate() != null ? getBitRate().name() : null).intern();
    }

    /**
     * toString.
     * @see java.lang.Object#toString()
     * @return {@link String}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("MPEGAudioEncodingFormat [version="); //$NON-NLS-1$
        builder.append(this.version);
        builder.append(", layer="); //$NON-NLS-1$
        builder.append(this.layer);
        builder.append(", errorProtected="); //$NON-NLS-1$
        builder.append(this.errorProtected);
        builder.append(", bitRate="); //$NON-NLS-1$
        builder.append(this.bitRate);
        builder.append(", samplingRate="); //$NON-NLS-1$
        builder.append(this.samplingRate);
        builder.append(", mode="); //$NON-NLS-1$
        builder.append(this.mode);
        builder.append(", modeExtension="); //$NON-NLS-1$
        builder.append(this.modeExtension);
        builder.append(", _private="); //$NON-NLS-1$
        builder.append(this._private);
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
