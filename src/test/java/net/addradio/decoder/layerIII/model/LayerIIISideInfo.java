/**
 * Class:    LayerIIISideInfo<br/>
 * <br/>
 * Created:  04.12.2019<br/>
 * Filename: LayerIIISideInfo.java<br/>
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
 * LayerIIISideInfo.
 */
public class LayerIIISideInfo {

    /** {@code int} mainDataBegin. */
    public int mainDataBegin = 0;

    /** {@code int} private_bits. */
    public int private_bits = 0;

    /** {@link ChannelSideInfo}{@code []} channelInfos. */
    public ChannelSideInfo[] channelInfos;

    /**
     * LayerIIISideInfo constructor.
     */
    public LayerIIISideInfo() {
        this.channelInfos = new ChannelSideInfo[] { new ChannelSideInfo(), new ChannelSideInfo() };
    }

}
