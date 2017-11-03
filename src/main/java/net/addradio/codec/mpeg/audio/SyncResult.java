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

    /** {@link SyncMode} mode. */
    private SyncMode mode;

    /** {@code int} skippedBits. */
    private int skippedBits;

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
     * @return {@link SyncMode} the mode
     */
    public SyncMode getMode() {
        return this.mode;
    }

    /**
     * getSkippedBits.
     * @return {@code int} the skippedBits
     */
    public int getSkippedBits() {
        return this.skippedBits;
    }

    /**
     * setMode.
     * @param modeRef {@link SyncMode} the mode to set
     */
    public void setMode(final SyncMode modeRef) {
        this.mode = modeRef;
    }

    /**
     * setSkippedBits.
     * @param skippedBitsVal {@code int} the skippedBits to set
     */
    public void setSkippedBits(final int skippedBitsVal) {
        this.skippedBits = skippedBitsVal;
    }

}
