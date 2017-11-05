/**
 * Class:    DecodingResult<br/>
 * <br/>
 * Created:  05.11.2017<br/>
 * Filename: DecodingResult.java<br/>
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

import java.util.List;

import net.addradio.codec.mpeg.audio.model.MPEGAudioContent;

/**
 * DecodingResult.
 */
public class DecodingResult {

    /** {@code long} skippedBits. */
    private long skippedBits;

    /** {@link List}{@code <}{@link MPEGAudioContent}{@code >} content. */
    private List<MPEGAudioContent> content;

    /**
    * DecodingResult constructor.
    */
    public DecodingResult() {
    }

    /**
     * DecodingResult constructor.
     * @param skippedBitsVal {@code long}
     * @param contentRef {@link List}{@code <}{@link MPEGAudioContent}{@code >}
     */
    public DecodingResult(final long skippedBitsVal, final List<MPEGAudioContent> contentRef) {
        setSkippedBits(skippedBitsVal);
        setContent(contentRef);
    }

    /**
     * getContent.
     * @return {@link List}{@code <}{@link MPEGAudioContent}{@code >} the content
     */
    public List<MPEGAudioContent> getContent() {
        return this.content;
    }

    /**
     * getSkippedBits.
     * @return {@code long} the skippedBits
     */
    public long getSkippedBits() {
        return this.skippedBits;
    }

    /**
     * setContent.
     * @param content {@link List}{@code <}{@link MPEGAudioContent}{@code >} the content to set
     */
    public void setContent(final List<MPEGAudioContent> content) {
        this.content = content;
    }

    /**
     * setSkippedBits.
     * @param skippedBits {@code long} the skippedBits to set
     */
    public void setSkippedBits(final long skippedBits) {
        this.skippedBits = skippedBits;
    }

}
