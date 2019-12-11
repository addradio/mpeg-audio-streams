/**
 * Class:    PCMDecoderResult<br/>
 * <br/>
 * Created:  10.12.2019<br/>
 * Filename: PCMDecoderResult.java<br/>
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
 * PCMDecoderResult.
 */
public interface PCMDecoderResult {

    /** {@code float []} EMPTY_BUFFER. */
    static final float[] EMPTY_BUFFER = new float[0];

    /** {@link void} UNDEFINED. */
    static final PCMDecoderResult UNDEFINED = new PCMDecoderResult() {

        @Override
        public float[] getBuffer() {
            return EMPTY_BUFFER;
        }

        @Override
        public int getFrequency() {
            return 0;
        }

        @Override
        public int getNumberOfChannels() {
            return 0;
        }

        @Override
        public int getSamplesLen() {
            return 0;
        }
    };

    /**
     * getBuffer.
     * @return {@code float[]}
     */
    float[] getBuffer();

    /**
     * getFrequency.
     * @return int the frequency
     */
    int getFrequency();

    /**
     * getNumberOfChannels.
     * @return {@code int} the number of channels contained in this buffer.
     */
    int getNumberOfChannels();

    /**
     * getSamplesLen.
     * @return int the samplesLen
     */
    int getSamplesLen();

}