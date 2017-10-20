/**
 * Class:    MPEGAudioFrameOutputStream<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: MPEGAudioFrameOutputStream.java<br/>
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

import java.io.OutputStream;

import net.addradio.streams.BitOutputStream;

/**
 * MPEGAudioFrameOutputStream
 */
public class MPEGAudioFrameOutputStream extends BitOutputStream {

    /**
     * MPEGAudioFrameOutputStream constructor.
     *
     * @param innerRef
     *            {@link OutputStream}
     */
    public MPEGAudioFrameOutputStream(final OutputStream innerRef) {
        super(innerRef);
    }

}
