/**
 * Class:    AppTestJavaSound<br/>
 * <br/>
 * Created:  02.12.2019<br/>
 * Filename: AppTestJavaSound.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2018 - All rights reserved.
 */

package net.addradio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AppTestJavaSound
 */
public class AppTestJavaSound {

    /** {@code int} SAMPLE_RATE */
    private static final int SAMPLE_RATE = 48000;

    /** {@link Logger} LOG */
    private static final Logger LOG = LoggerFactory.getLogger(AppTestJavaSound.class);

    /**
     * main.
     * @param args {@link String}{@code []}
     */
    public static void main(String[] args) {

        final boolean signed = true;
        final int sampleRate = AppTestJavaSound.SAMPLE_RATE;
        final int sampleSizeInBits = 16;
        final int channels = 1;
        final boolean bigEndian = false;

        final AudioFormat audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        try (final SourceDataLine source = AudioSystem.getSourceDataLine(audioFormat)) {
            source.open(audioFormat, SAMPLE_RATE);
            source.start();

            while (true) {
                byte[] toneBuffer = createSinWaveBuffer(440, 2000);
                int count = source.write(toneBuffer, 0, toneBuffer.length);
            }

        } catch (LineUnavailableException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }

    }

    /**
     * createSinWaveBuffer.
     * @param freq {@code double}
     * @param ms {@code int}
     * @return {@code byte[]}
     */
    public static byte[] createSinWaveBuffer(double freq, int ms) {
        int samples = (ms * SAMPLE_RATE) / 1000;
        byte[] output = new byte[samples];
        //
        double period = SAMPLE_RATE / freq;
        for (int i = 0; i < output.length; i++) {
            double angle = 2.0 * Math.PI * i / period;
            output[i] = (byte) (Math.sin(angle) * 127f);
        }
        return output;
    }

}
