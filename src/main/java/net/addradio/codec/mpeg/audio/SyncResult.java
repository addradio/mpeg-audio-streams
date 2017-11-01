/**
 * Class:    SyncResult<br/>
 * <br/>
 * Created:  29.10.2017<br/>
 * Filename: SyncResult.java<br/>
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
 * SyncResult.
 */
public class SyncResult {

    /** {@code int} skippedBits. */
    private int skippedBits;

    /** {@link SyncMode} mode. */
    private SyncMode mode;

    /**
     * SyncResult constructor.
     */
    public SyncResult() {
    }

    /**
     * SyncResult constructor.
     * @param modeRef {@link SyncMode}
     * @param skippedBitsVal {@code int}
     */
    public SyncResult(final SyncMode modeRef, final int skippedBitsVal) {
        setMode(modeRef);
        setSkippedBits(skippedBitsVal);
    }

    /**
     * getMode.
     * @return SyncMode the mode
     */
    public SyncMode getMode() {
        return this.mode;
    }

    /**
     * getSkippedBits.
     * @return int the skippedBits
     */
    public int getSkippedBits() {
        return this.skippedBits;
    }

    /**
     * setMode.
     * @param mode SyncMode the mode to set
     */
    public void setMode(final SyncMode mode) {
        this.mode = mode;
    }

    /**
     * setSkippedBits.
     * @param skippedBits int the skippedBits to set
     */
    public void setSkippedBits(final int skippedBits) {
        this.skippedBits = skippedBits;
    }

}
