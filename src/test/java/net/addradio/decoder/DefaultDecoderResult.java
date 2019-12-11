/**
 * Class:    DefaultDecoderResult<br/>
 * <br/>
 * Created:  10.12.2019<br/>
 * Filename: DefaultDecoderResult.java<br/>
 * Version:  $Revision$<br/>
 * <br/>
 * last modified on $Date$<br/>
 *               by $Author$<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author$ -- $Revision$ -- $Date$
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2019 - All rights reserved.
 */
package net.addradio.decoder;

/**
 * DefaultDecoderResult.
 */
public class DefaultDecoderResult implements PCMDecoderResult {

    /** {@link float[]} sampleBuffer. */
    private final float[] sampleBuffer;

    /** {@link int} samplesLen. */
    private int samplesLen;

    /** {@link int[]} positionInSampleBufferForChannel. */
    private final int[] positionInSampleBufferForChannel;

    /** {@link int} frequency. */
    private int frequency;

    /**
     * DefaultDecoderResult constructor.
     * @param numberOfChannels {@code int}
     * @param frequencyVal {@code int}
     */
    public DefaultDecoderResult(final int numberOfChannels, final int frequencyVal) {
        this.positionInSampleBufferForChannel = new int[numberOfChannels];
        this.sampleBuffer = new float[2 * 1152]; // max. 2 * 1152 samples per frame
        setFrequency(frequencyVal);
        reset();
    }

    /**
     * appendSamples.
     * @param samples {@code float[]}
     * @param samplesInArrayLen {@code int}
     * @param channel {@code int}
     */
    public void appendSamples(final float[] samples, final int samplesInArrayLen, final int channel) {
        for (int index = 0; index < samplesInArrayLen; index++) {
            final int pos = this.positionInSampleBufferForChannel[channel];
            this.sampleBuffer[pos] = samples[index];
            if (this.samplesLen < pos) {
                this.samplesLen = pos;
            }
            this.positionInSampleBufferForChannel[channel] += getNumberOfChannels();
        }
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.decoder.PCMDecoderResult#getBuffer()
     */
    @Override
    public float[] getBuffer() {
        return this.sampleBuffer;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.decoder.PCMDecoderResult#getFrequency()
     */
    @Override
    public int getFrequency() {
        return this.frequency;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.decoder.PCMDecoderResult#getNumberOfChannels()
     */
    @Override
    public int getNumberOfChannels() {
        return this.positionInSampleBufferForChannel.length;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.decoder.PCMDecoderResult#getSamplesLen()
     */
    @Override
    public int getSamplesLen() {
        return this.samplesLen;
    }

    /**
     * initPositions.
     */
    public void reset() {
        this.samplesLen = 0;
        for (int i = 0; i < this.positionInSampleBufferForChannel.length; i++) {
            this.positionInSampleBufferForChannel[i] = i;
        }
    }

    /**
     * setFrequency.
     * @param frequency int the frequency to set
     */
    public void setFrequency(final int frequency) {
        this.frequency = frequency;
    }
}
