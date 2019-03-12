/**
 * Class:    FadeOutSandbox<br/>
 * <br/>
 * Created:  08.11.2017<br/>
 * Filename: FadeOutSandbox.java<br/>
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
package net.addradio.codec.mpeg.audio;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.codec.mpeg.audio.tools.MPEGAudioContentFilter;

/**
 * FadeOutSandbox.
 */
public class FadeOutSandbox {

    /** <code>int</code> INT_0XFF. */
    private static final int INT_0XFF = 0xff;

    /** <code>int</code> INT_8. */
    private static final int INT_8 = 8;

    /**
     * formatIntegerToBitString.
     * @param unsignedIntForByte <code>int</code>
     * @return {@link String}
     */
    public static String formatIntegerToBitString(final int unsignedIntForByte) {
        String binaryString = Integer.toBinaryString(unsignedIntForByte);
        while (binaryString.length() < INT_8) {
            binaryString = "0" + binaryString; //$NON-NLS-1$
        }
        return binaryString;
    }

    /**
     * getUnsignedIntForByte.
     * @param b <code>byte</code>
     * @return <code>int</code>
     */
    public static int getUnsignedIntForByte(final byte b) {
        return (b & INT_0XFF);
    }

    /**
     * main.
     * @param args {@link String}{@code []}
     */
    @SuppressWarnings("nls")
    public static void main(final String[] args) {

        //        DecodingResult drfo = MPEGAudio.decode(new File("src/test/mp3/click.mp3"),
        final DecodingResult drfo = MPEGAudio.decode(new File("src/test/mp3/click_fade_out.mp3"),
                MPEGAudioContentFilter.MPEG_AUDIO_FRAMES);
        final DecodingResult dro = MPEGAudio.decode(new File("src/test/mp3/click.mp3"),
                MPEGAudioContentFilter.MPEG_AUDIO_FRAMES);

        System.out.println("drfo contents count: " + drfo.getNumberOfDecodedContents());
        System.out.println("dro contents count: " + dro.getNumberOfDecodedContents());

        //        final List<MPEGAudioContent> content = dro.getContent();
        final Iterator<MPEGAudioFrame> droIterator = dro.getAudioFrames().iterator();
        // remove APE header
        droIterator.next();
        final Iterator<MPEGAudioFrame> drfoIterator = drfo.getAudioFrames().iterator();
        int frameCount = 0;
        int diffCount = 0;
        while (droIterator.hasNext() && drfoIterator.hasNext()) {

            final MPEGAudioFrame droNext = droIterator.next();
            final MPEGAudioFrame drfoNext = drfoIterator.next();

            if (!Arrays.equals(droNext.getPayload(), drfoNext.getPayload())) {
                System.out.println("fc: " + frameCount + ", droLength: " + droNext.getPayload().length
                        + ", drfoLength: " + drfoNext.getPayload().length);
                StringBuffer droSB = new StringBuffer();
                StringBuffer drfoSB = new StringBuffer();
                for (int i = 0; (i < droNext.getPayload().length) && (i < drfoNext.getPayload().length); i++) {
                    final byte bo = droNext.getPayload()[i];
                    final byte bfo = drfoNext.getPayload()[i];
                    droSB.append(formatIntegerToBitString(getUnsignedIntForByte(bo)));
                    if (bo != bfo) {
                        droSB.append("<");
                    } else {
                        droSB.append(" ");
                    }
                    drfoSB.append(formatIntegerToBitString(getUnsignedIntForByte(bfo)));
                    if (bo != bfo) {
                        drfoSB.append("<");
                    } else {
                        drfoSB.append(" ");
                    }
                    if ((i > 0) && (((i + 1) % 8) == 0)) {
                        System.out.println(droSB.toString() + "\t" + drfoSB.toString() + "\n");
                        drfoSB = new StringBuffer();
                        droSB = new StringBuffer();
                    }
                }
                diffCount++;
                if (diffCount > 2) {
                    System.exit(0);
                }
            }

            frameCount++;
        }

        //        System.out.println(decode);
        //        List<MPEGAudioContent> content = decode.getContent();
        //        for (MPEGAudioContent mpegAudioContent : content) {
        //            //            if (ID3Tag.class.isAssignableFrom(mpegAudioContent.getClass())) {
        //            System.out.println(mpegAudioContent);
        //            //            }
        //        }
    }

}
