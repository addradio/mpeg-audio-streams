/**
 * Class:    ChannelSideInfo<br/>
 * <br/>
 * Created:  04.12.2019<br/>
 * Filename: ChannelSideInfo.java<br/>
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
 * ChannelSideInfo.
 */
public class ChannelSideInfo {

    /** {@code int[]} scfsi. */
    public int[] scfsi;

    /** {@link GranuleSideInfo}{@code []} granuleInfos. */
    public GranuleSideInfo[] granuleInfos;

    /**
     * ChannelSideInfo constructor.
     */
    public ChannelSideInfo() {
        this.scfsi = new int[4];
        this.granuleInfos = new GranuleSideInfo[] { new GranuleSideInfo(), new GranuleSideInfo() };
    }

}
