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
import java.util.List;

import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;

/**
 * FadeOutSandbox.
 */
public class FadeOutSandbox {

    /**
     * main.
     * @param args {@link String}{@code []}
     */
    public static void main(String[] args) {

        DecodingResult decode = MPEGAudio.decode(new File("src/test/mp3/click_fade_out.mp3"));
        System.out.println(decode);
        List<MPEGAudioContent> content = decode.getContent();
        for (MPEGAudioContent mpegAudioContent : content) {
            //            if (ID3Tag.class.isAssignableFrom(mpegAudioContent.getClass())) {
            System.out.println(mpegAudioContent);
            //            }
        }
    }

}
