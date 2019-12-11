/**
 * Class:    LayerIIISideInfoDecoder<br/>
 * <br/>
 * Created:  04.12.2019<br/>
 * Filename: LayerIIISideInfoDecoder.java<br/>
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
package net.addradio.decoder.layerIII;

import java.io.IOException;

import net.addradio.codec.mpeg.audio.model.MPEGAudioFrame;
import net.addradio.decoder.layerIII.model.GranuleSideInfo;
import net.addradio.decoder.layerIII.model.LayerIIISideInfo;
import net.addradio.streams.BitInputStream;

/**
 * LayerIIISideInfoDecoder.
 */
public final class LayerIIISideInfoDecoder {

    /**
     * decode.
     *
     * @param frame {@link MPEGAudioFrame}
     * @param bis {@link BitInputStream}
     * @param sideInfo {@link LayerIIISideInfo}
     * @return {@code boolean} {@code true} if side info could be decoded successfully.
     * @throws IOException due to I/O issues.
     */
    public static final boolean decode(final MPEGAudioFrame frame, final BitInputStream bis,
            final LayerIIISideInfo sideInfo) throws IOException {
        int channel = 0;
        int granule = 0;

        switch (frame.getVersion()) {
        case MPEG_1:
            sideInfo.mainDataBegin = bis.readBits(9);

            if (frame.getChannels() == 1) {
                sideInfo.private_bits = bis.readBits(5);
            } else {
                sideInfo.private_bits = bis.readBits(3);
            }

            for (channel = 0; channel < frame.getChannels(); channel++) {
                sideInfo.channelInfos[channel].scfsi[0] = bis.readBit();
                sideInfo.channelInfos[channel].scfsi[1] = bis.readBit();
                sideInfo.channelInfos[channel].scfsi[2] = bis.readBit();
                sideInfo.channelInfos[channel].scfsi[3] = bis.readBit();
            }

            for (granule = 0; granule < 2; granule++) {
                for (channel = 0; channel < frame.getChannels(); channel++) {
                    final GranuleSideInfo granuleSideInfo = sideInfo.channelInfos[channel].granuleInfos[granule];
                    granuleSideInfo.part2_3_length = bis.readBits(12);
                    granuleSideInfo.big_values = bis.readBits(9);
                    granuleSideInfo.globalGain = bis.readBits(8);
                    granuleSideInfo.scalefac_compress = bis.readBits(4);
                    granuleSideInfo.isWindowSwitching = bis.isNextBitOne();
                    if (granuleSideInfo.isWindowSwitching) {
                        granuleSideInfo.blockType = bis.readBits(2);
                        granuleSideInfo.isMixedBlock = bis.isNextBitOne();

                        granuleSideInfo.table_select[0] = bis.readBits(5);
                        granuleSideInfo.table_select[1] = bis.readBits(5);

                        granuleSideInfo.subblock_gain[0] = bis.readBits(3);
                        granuleSideInfo.subblock_gain[1] = bis.readBits(3);
                        granuleSideInfo.subblock_gain[2] = bis.readBits(3);

                        // Set region_count parameters since they are implicit in this case.

                        if (granuleSideInfo.blockType == 0) {
                            //   Side info bad: blockType == 0 in split block
                            return false;
                        } else if ((granuleSideInfo.blockType == 2) && !granuleSideInfo.isMixedBlock) {
                            granuleSideInfo.region0_count = 8;
                        } else {
                            granuleSideInfo.region0_count = 7;
                        }
                        granuleSideInfo.region1_count = 20 - granuleSideInfo.region0_count;
                    } else {
                        granuleSideInfo.table_select[0] = bis.readBits(5);
                        granuleSideInfo.table_select[1] = bis.readBits(5);
                        granuleSideInfo.table_select[2] = bis.readBits(5);
                        granuleSideInfo.region0_count = bis.readBits(4);
                        granuleSideInfo.region1_count = bis.readBits(3);
                        granuleSideInfo.blockType = 0;
                    }
                    granuleSideInfo.preflag = bis.readBits(1);
                    granuleSideInfo.scalefac_scale = bis.readBits(1);
                    granuleSideInfo.count1table_select = bis.readBits(1);
                }
            }

            break;
        case MPEG_2_5:
        case MPEG_2_LSF:
        case reserved:
        default:
            sideInfo.mainDataBegin = bis.readBits(8);
            if (frame.getChannels() == 1) {
                sideInfo.private_bits = bis.readBits(1);
            } else {
                sideInfo.private_bits = bis.readBits(2);
            }

            for (channel = 0; channel < frame.getChannels(); channel++) {
                final GranuleSideInfo granuleSideInfo = sideInfo.channelInfos[channel].granuleInfos[0];
                granuleSideInfo.part2_3_length = bis.readBits(12);
                granuleSideInfo.big_values = bis.readBits(9);
                granuleSideInfo.globalGain = bis.readBits(8);
                granuleSideInfo.scalefac_compress = bis.readBits(9);
                granuleSideInfo.isWindowSwitching = bis.isNextBitOne();
                if (granuleSideInfo.isWindowSwitching) {

                    granuleSideInfo.blockType = bis.readBits(2);
                    granuleSideInfo.isMixedBlock = bis.isNextBitOne();
                    granuleSideInfo.table_select[0] = bis.readBits(5);
                    granuleSideInfo.table_select[1] = bis.readBits(5);

                    granuleSideInfo.subblock_gain[0] = bis.readBits(3);
                    granuleSideInfo.subblock_gain[1] = bis.readBits(3);
                    granuleSideInfo.subblock_gain[2] = bis.readBits(3);

                    // Set region_count parameters since they are implicit in this case.

                    if (granuleSideInfo.blockType == 0) {
                        // Side info bad: blockType == 0 in split block
                        return false;
                    } else if ((granuleSideInfo.blockType == 2) && !granuleSideInfo.isMixedBlock) {
                        granuleSideInfo.region0_count = 8;
                    } else {
                        granuleSideInfo.region0_count = 7;
                        granuleSideInfo.region1_count = 20 - granuleSideInfo.region0_count;
                    }

                } else {
                    granuleSideInfo.table_select[0] = bis.readBits(5);
                    granuleSideInfo.table_select[1] = bis.readBits(5);
                    granuleSideInfo.table_select[2] = bis.readBits(5);
                    granuleSideInfo.region0_count = bis.readBits(4);
                    granuleSideInfo.region1_count = bis.readBits(3);
                    granuleSideInfo.blockType = 0;
                }

                granuleSideInfo.scalefac_scale = bis.readBits(1);
                granuleSideInfo.count1table_select = bis.readBits(1);
            }
            break;
        }
        return true;
    }

    /**
     * LayerIIISideInfoDecoder constructor.
     */
    private LayerIIISideInfoDecoder() {
    }

}
