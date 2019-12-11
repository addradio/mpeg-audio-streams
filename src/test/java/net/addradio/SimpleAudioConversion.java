/**
 * Class:    SimpleAudioConversion<br/>
 * <br/>
 * Created:  10.12.2019<br/>
 * Filename: SimpleAudioConversion.java<br/>
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
package net.addradio;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.signum;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;

/**
 * <p>Performs simple audio format conversion.</p>
 *
 * <p>Example usage:</p>
 *
 * <pre>{@code  AudioInputStream ais = ... ;
 * SourceDataLine  line = ... ;
 * AudioFormat      fmt = ... ;
 *
 * // do setup
 *
 * for (int blen = 0; (blen = ais.read(bytes)) > -1;) {
 *     int slen;
 *     slen = SimpleAudioConversion.decode(bytes, samples, blen, fmt);
 *
 *     // do something with samples
 *
 *     blen = SimpleAudioConversion.encode(samples, bytes, slen, fmt);
 *     line.write(bytes, 0, blen);
 * }}</pre>
 *
 * @author Radiodef
 * @see <a href="http://stackoverflow.com/a/26824664/2891664">Overview on Stack Overflow</a>
 */
public class SimpleAudioConversion {

    // mu-law constant
    private static final double MU = 255.0;

    // A-law constant
    private static final double A = 87.7;

    // natural logarithm of A
    private static final double LN_A = log(A);

    private static long aLawToBits(float sample) {
        final double sign = signum(sample);
        sample = abs(sample);

        if (sample < (1.0 / A)) {
            sample = (float) ((A * sample) / (1.0 + LN_A));
        } else {
            sample = (float) ((1.0 + log(A * sample)) / (1.0 + LN_A));
        }

        sample *= sign;

        long temp = (long) (sample * fullScale(8));

        if (temp < 0) {
            temp = -temp ^ 0x80L;
        }

        return temp ^ 0x55L;
    }

    private static float bitsToALaw(long temp) {
        temp ^= 0x55L;
        if ((temp & 0x80L) != 0) {
            temp = -(temp ^ 0x80L);
        }

        float sample = (float) (temp / fullScale(8));

        final float sign = signum(sample);
        sample = abs(sample);

        if (sample < (1.0 / (1.0 + LN_A))) {
            sample = (float) (sample * ((1.0 + LN_A) / A));
        } else {
            sample = (float) (exp((sample * (1.0 + LN_A)) - 1.0) / A);
        }

        return sign * sample;
    }

    private static float bitsToMuLaw(long temp) {
        temp ^= 0xffL;
        if ((temp & 0x80L) != 0) {
            temp = -(temp ^ 0x80L);
        }

        final float sample = (float) (temp / fullScale(8));

        return (float) (signum(sample) * (1.0 / MU) * (pow(1.0 + MU, abs(sample)) - 1.0));
    }

    /**
     * Computes the block-aligned bytes per sample of the audio format,
     * using Math.ceil(bitsPerSample / 8.0).
     * <p>
     * Round towards the ceiling because formats that allow bit depths
     * in non-integral multiples of 8 typically pad up to the nearest
     * integral multiple of 8. So for example, a 31-bit AIFF file will
     * actually store 32-bit blocks.
     *
     * @param  bitsPerSample the return value of AudioFormat.getSampleSizeInBits
     * @return The block-aligned bytes per sample of the audio format.
     */
    public static int bytesPerSample(final int bitsPerSample) {
        return (int) ceil(bitsPerSample / 8.0); // optimization: ((bitsPerSample + 7) >>> 3)
    }

    /**
     * Converts from a byte array to an audio sample float array.
     *
     * @param bytes   the byte array, filled by the AudioInputStream
     * @param samples an array to fill up with audio samples
     * @param blen    the return value of AudioInputStream.read
     * @param fmt     the source AudioFormat
     *
     * @return the number of valid audio samples converted
     *
     * @throws NullPointerException if bytes, samples or fmt is null
     * @throws ArrayIndexOutOfBoundsException
     *         if bytes.length is less than blen or
     *         if samples.length is less than blen / bytesPerSample(fmt.getSampleSizeInBits())
     */
    public static int decode(final byte[] bytes, final float[] samples, final int blen, final AudioFormat fmt) {
        final int bitsPerSample = fmt.getSampleSizeInBits();
        final int bytesPerSample = bytesPerSample(bitsPerSample);
        final boolean isBigEndian = fmt.isBigEndian();
        final Encoding encoding = fmt.getEncoding();
        final double fullScale = fullScale(bitsPerSample);

        int sampleIndex = 0;
        for (int offset = 0; offset < blen; offset += bytesPerSample) {
            long temp = unpackBits(bytes, offset, isBigEndian, bytesPerSample);
            float sample = 0f;

            if (encoding == Encoding.PCM_SIGNED) {
                temp = extendSign(temp, bitsPerSample);
                sample = (float) (temp / fullScale);

            } else if (encoding == Encoding.PCM_UNSIGNED) {
                temp = unsignedToSigned(temp, bitsPerSample);
                sample = (float) (temp / fullScale);

            } else if (encoding == Encoding.PCM_FLOAT) {
                if (bitsPerSample == 32) {
                    sample = Float.intBitsToFloat((int) temp);
                } else if (bitsPerSample == 64) {
                    sample = (float) Double.longBitsToDouble(temp);
                }
            } else if (encoding == Encoding.ULAW) {
                sample = bitsToMuLaw(temp);

            } else if (encoding == Encoding.ALAW) {
                sample = bitsToALaw(temp);
            }

            samples[sampleIndex] = sample;
            sampleIndex++;
        }

        return sampleIndex;
    }

    /**
     * Converts from an audio sample float array to a byte array.
     *
     * @param samples {@code float[]} an array of audio samples to encode. 
     *                Each sample value is assumed to be in the range between -1.0 and +1.0.
     * @param bytes {@code byte[]} destination array
     * @param samplesLen {@code int} number of samples in {@code samples[]} by offset 0
     * @param fmt {@link AudioFormat} the destination format
     *
     * @return the number of valid bytes converted
     *
     * @throws NullPointerException if samples, bytes or fmt is null
     * @throws ArrayIndexOutOfBoundsException
     *         if samples.length is less than samplesLen or
     *         if bytes.length is less than samplesLen * bytesPerSample(fmt.getSampleSizeInBits())
     */
    public static int encode(final float[] samples, final byte[] bytes, final int samplesLen, final AudioFormat fmt) {
        final int bitsPerSample = fmt.getSampleSizeInBits();
        final int bytesPerSample = bytesPerSample(bitsPerSample);
        final boolean isBigEndian = fmt.isBigEndian();
        final Encoding encoding = fmt.getEncoding();
        final double fullScale = fullScale(bitsPerSample);

        int i = 0;

        for (int sampleIndex = 0; sampleIndex < samplesLen; sampleIndex++) {
            final float sample = samples[sampleIndex];
            long temp = 0L;

            if (encoding == Encoding.PCM_SIGNED) {
                temp = (long) (sample * fullScale);

            } else if (encoding == Encoding.PCM_UNSIGNED) {
                temp = (long) (sample * fullScale);
                temp = signedToUnsigned(temp, bitsPerSample);

            } else if (encoding == Encoding.PCM_FLOAT) {
                if (bitsPerSample == 32) {
                    temp = Float.floatToRawIntBits(sample);
                } else if (bitsPerSample == 64) {
                    temp = Double.doubleToRawLongBits(sample);
                }
            } else if (encoding == Encoding.ULAW) {
                temp = muLawToBits(sample);

            } else if (encoding == Encoding.ALAW) {
                temp = aLawToBits(sample);
            }

            packBits(bytes, i, temp, isBigEndian, bytesPerSample);
            i += bytesPerSample;
        }

        return i;
    }

    private static long extendSign(final long temp, final int bitsPerSample) {
        final int bitsToExtend = Long.SIZE - bitsPerSample;
        return (temp << bitsToExtend) >> bitsToExtend;
    }

    /**
     * Computes the largest magnitude representable by the audio format,
     * using Math.pow(2.0, bitsPerSample - 1). Note that for two's complement
     * audio, the largest positive value is one less than the return value of
     * this method.
     * <p>
     * The result is returned as a double because in the case that
     * bitsPerSample is 64, a long would overflow.
     *
     * @param bitsPerSample the return value of AudioFormat.getBitsPerSample
     * @return the largest magnitude representable by the audio format
     */
    public static double fullScale(final int bitsPerSample) {
        return pow(2.0, bitsPerSample - 1); // optimization: (1L << (bitsPerSample - 1))
    }

    private static long muLawToBits(float sample) {
        final double sign = signum(sample);
        sample = abs(sample);

        sample = (float) (sign * (log(1.0 + (MU * sample)) / log(1.0 + MU)));

        long temp = (long) (sample * fullScale(8));

        if (temp < 0) {
            temp = -temp ^ 0x80L;
        }

        return temp ^ 0xffL;
    }

    private static void pack16Bit(final byte[] bytes, final int i, final long temp, final boolean isBigEndian) {
        if (isBigEndian) {
            bytes[i] = (byte) ((temp >>> 8) & 0xffL);
            bytes[i + 1] = (byte) (temp & 0xffL);
        } else {
            bytes[i] = (byte) (temp & 0xffL);
            bytes[i + 1] = (byte) ((temp >>> 8) & 0xffL);
        }
    }

    private static void pack24Bit(final byte[] bytes, final int i, final long temp, final boolean isBigEndian) {
        if (isBigEndian) {
            bytes[i] = (byte) ((temp >>> 16) & 0xffL);
            bytes[i + 1] = (byte) ((temp >>> 8) & 0xffL);
            bytes[i + 2] = (byte) (temp & 0xffL);
        } else {
            bytes[i] = (byte) (temp & 0xffL);
            bytes[i + 1] = (byte) ((temp >>> 8) & 0xffL);
            bytes[i + 2] = (byte) ((temp >>> 16) & 0xffL);
        }
    }

    private static void pack8Bit(final byte[] bytes, final int i, final long temp) {
        bytes[i] = (byte) (temp & 0xffL);
    }

    private static void packAnyBit(final byte[] bytes, final int i, final long temp, final boolean isBigEndian,
            final int bytesPerSample) {
        if (isBigEndian) {
            for (int b = 0; b < bytesPerSample; b++) {
                bytes[i + b] = (byte) ((temp >>> (8 * (bytesPerSample - b - 1))) & 0xffL);
            }
        } else {
            for (int b = 0; b < bytesPerSample; b++) {
                bytes[i + b] = (byte) ((temp >>> (8 * b)) & 0xffL);
            }
        }
    }

    private static void packBits(final byte[] bytes, final int i, final long temp, final boolean isBigEndian,
            final int bytesPerSample) {
        switch (bytesPerSample) {
        case 1:
            pack8Bit(bytes, i, temp);
            break;
        case 2:
            pack16Bit(bytes, i, temp, isBigEndian);
            break;
        case 3:
            pack24Bit(bytes, i, temp, isBigEndian);
            break;
        default:
            packAnyBit(bytes, i, temp, isBigEndian, bytesPerSample);
            break;
        }
    }

    private static long signedToUnsigned(final long temp, final int bitsPerSample) {
        return temp + (long) fullScale(bitsPerSample);
    }

    private static long unpack16Bit(final byte[] bytes, final int offset, final boolean isBigEndian) {
        if (isBigEndian) {
            return (((bytes[offset] & 0xffL) << 8) | (bytes[offset + 1] & 0xffL));
        }
        return ((bytes[offset] & 0xffL) | ((bytes[offset + 1] & 0xffL) << 8));
    }

    private static long unpack24Bit(final byte[] bytes, final int offset, final boolean isBigEndian) {
        if (isBigEndian) {
            return (((bytes[offset] & 0xffL) << 16) | ((bytes[offset + 1] & 0xffL) << 8) | (bytes[offset + 2] & 0xffL));
        }
        return ((bytes[offset] & 0xffL) | ((bytes[offset + 1] & 0xffL) << 8) | ((bytes[offset + 2] & 0xffL) << 16));
    }

    private static long unpack8Bit(final byte[] bytes, final int offset) {
        return bytes[offset] & 0xffL;
    }

    private static long unpackAnyBit(final byte[] bytes, final int offset, final boolean isBigEndian,
            final int bytesPerSample) {
        long temp = 0;

        if (isBigEndian) {
            for (int b = 0; b < bytesPerSample; b++) {
                temp |= (bytes[offset + b] & 0xffL) << (8 * (bytesPerSample - b - 1));
            }
        } else {
            for (int b = 0; b < bytesPerSample; b++) {
                temp |= (bytes[offset + b] & 0xffL) << (8 * b);
            }
        }

        return temp;
    }

    private static long unpackBits(final byte[] bytes, final int offset, final boolean isBigEndian,
            final int bytesPerSample) {
        switch (bytesPerSample) {
        case 1:
            return unpack8Bit(bytes, offset);
        case 2:
            return unpack16Bit(bytes, offset, isBigEndian);
        case 3:
            return unpack24Bit(bytes, offset, isBigEndian);
        default:
            return unpackAnyBit(bytes, offset, isBigEndian, bytesPerSample);
        }
    }

    private static long unsignedToSigned(final long temp, final int bitsPerSample) {
        return temp - (long) fullScale(bitsPerSample);
    }

    private SimpleAudioConversion() {
    }
}
