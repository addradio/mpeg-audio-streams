/**
 * Class:    SyncMode<br/>
 * <br/>
 * Created:  29.10.2017<br/>
 * Filename: SyncMode.java<br/>
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

/**
 * SyncMode.
 */
public enum SyncMode {

    /** {@link SyncMode} mpeg_aligned. */
    mpeg_aligned,

    /** {@link SyncMode} id3v1_aligned. */
    id3v1_aligned,

    /** {@link SyncMode} id3v2_aligned. */
    id3v2_aligned,

    /** {@link SyncMode} unaligned. */
    unaligned;

}
