/**
 * Class:    GranuleSideInfo<br/>
 * <br/>
 * Created:  04.12.2019<br/>
 * Filename: GranuleSideInfo.java<br/>
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
package net.addradio.decoder.layerIII.model;

/**
 * GranuleSideInfo.
 */
public class GranuleSideInfo {

    /** {@code int} part2_3_length. */
    public int part2_3_length = 0;

    /** {@code int} big_values. */
    public int big_values = 0;

    /** {@code int} globalGain. */
    public int globalGain = 0;

    /** {@code int} scalefac_compress. */
    public int scalefac_compress = 0;

    /** {@code boolean} isWindowSwitching. */
    public boolean isWindowSwitching = false;

    /** {@code int} blockType. */
    public int blockType = 0;

    /** {@code boolean} isMixedBlock. */
    public boolean isMixedBlock = false;

    /** {@code int[]} table_select. */
    public int[] table_select;

    /** {@code int[]} subblock_gain. */
    public int[] subblock_gain;

    /** {@code int} region0_count. */
    public int region0_count = 0;

    /** {@code int} region1_count. */
    public int region1_count = 0;

    /** {@code int} preflag. */
    public int preflag = 0;

    /** {@code int} scalefac_scale. */
    public int scalefac_scale = 0;

    /** {@code int} count1table_select. */
    public int count1table_select = 0;

    /**
     * GranuleSideInfo constructor.
     */
    public GranuleSideInfo() {
        this.table_select = new int[3];
        this.subblock_gain = new int[3];
        this.part2_3_length = 0;
        this.big_values = 0;
        this.globalGain = 0;
        this.scalefac_compress = 0;
        this.isWindowSwitching = false;
        this.blockType = 0;
        this.isMixedBlock = false;
        this.region0_count = 0;
        this.region1_count = 0;
        this.preflag = 0;
        this.scalefac_scale = 0;
        this.count1table_select = 0;
    }

}
