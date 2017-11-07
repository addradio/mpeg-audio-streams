/**
 * Class:    Genre<br/>
 * <br/>
 * Created:  29.10.2017<br/>
 * Filename: Genre.java<br/>
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
package net.addradio.codec.id3.model.v1;

import net.addradio.codec.mpeg.audio.model.BitMaskFlag;

/**
 * Genre.
 */
public enum Genre implements BitMaskFlag {

    /** {@link Genre} Alternative */
    Alternative(20, null),

    /** {@link Genre} AlternRock */
    AlternRock(40, null),

    /** {@link Genre} Bass */
    Bass(41, null),

    /** {@link Genre} Blues */
    Blues(0, null),

    /** {@link Genre} Christian_Rap */
    Christian_Rap(61, "Christian Rap"), //$NON-NLS-1$

    /** {@link Genre} Classic_Rock */
    Classic_Rock(1, "Classic Rock"), //$NON-NLS-1$

    /** {@link Genre} Country */
    Country(2, null),

    /** {@link Genre} Dance */
    Dance(3, null),

    /** {@link Genre} Death_Metal */
    Death_Metal(22, "Death Metal"), //$NON-NLS-1$

    /** {@link Genre} Disco */
    Disco(4, null),

    /** {@link Genre} Jungle */
    Jungle(63, null),

    /** {@link Genre} Native_American */
    Native_American(64, "Native American"), //$NON-NLS-1$

    /** {@link Genre} Pop_Funk */
    Pop_Funk(62, "Pop/Funk"), //$NON-NLS-1$

    /** {@link Genre} Pranks */
    Pranks(23, null),

    /** {@link Genre} Punk */
    Punk(43, null),

    /** {@link Genre} Ska */
    Ska(21, null),

    /** {@link Genre} Soul */
    Soul(42, null),

    /** {@link Genre} Soundtrack */
    Soundtrack(24, null),

    /** {@link Genre} Space */
    Space(44, null),

    /** {@link Genre} Top_40 */
    Top_40(60, "Top 40"); //$NON-NLS-1$

    // SEBASTIAN implement
    //    5   'Funk'  25  'Euro-Techno'   45  'Meditative'    65  'Cabaret'
    //    6   'Grunge'    26  'Ambient'   46  'Instrumental Pop'  66  'New Wave'
    //    7   'Hip-Hop'   27  'Trip-Hop'  47  'Instrumental Rock' 67  'Psychadelic'
    //    8   'Jazz'  28  'Vocal' 48  'Ethnic'    68  'Rave'
    //    9   'Metal' 29  'Jazz+Funk' 49  'Gothic'    69  'Showtunes'
    //    10  'New Age'   30  'Fusion'    50  'Darkwave'  70  'Trailer'
    //    11  'Oldies'    31  'Trance'    51  'Techno-Industrial' 71  'Lo-Fi'
    //    12  'Other' 32  'Classical' 52  'Electronic'    72  'Tribal'
    //    13  'Pop'   33  'Instrumental'  53  'Pop-Folk'  73  'Acid Punk'
    //    14  'R&B'   34  'Acid'  54  'Eurodance' 74  'Acid Jazz'
    //    15  'Rap'   35  'House' 55  'Dream' 75  'Polka'
    //    16  'Reggae'    36  'Game'  56  'Southern Rock' 76  'Retro'
    //    17  'Rock'  37  'Sound Clip'    57  'Comedy'    77  'Musical'
    //    18  'Techno'    38  'Gospel'    58  'Cult'  78  'Rock & Roll'
    //    19  'Industrial'    39  'Noise' 59  'Gangsta'   79  'Hard Rock'
    //    80  'Folk'  92  'Progressive Rock'  104 'Chamber Music' 116 'Ballad'
    //    81  'Folk-Rock' 93  'Psychedelic Rock'  105 'Sonata'    117 'Poweer Ballad'
    //    82  'National Folk' 94  'Symphonic Rock'    106 'Symphony'  118 'Rhytmic Soul'
    //    83  'Swing' 95  'Slow Rock' 107 'Booty Brass'   119 'Freestyle'
    //    84  'Fast Fusion'   96  'Big Band'  108 'Primus'    120 'Duet'
    //    85  'Bebob' 97  'Chorus'    109 'Porn Groove'   121 'Punk Rock'
    //    86  'Latin' 98  'Easy Listening'    110 'Satire'    122 'Drum Solo'
    //    87  'Revival'   99  'Acoustic'  111 'Slow Jam'  123 'A Capela'
    //    88  'Celtic'    100 'Humour'    112 'Club'  124 'Euro-House'
    //    89  'Bluegrass' 101 'Speech'    113 'Tango' 125 'Dance Hall'
    //    90  'Avantgarde'    102 'Chanson'   114 'Samba'
    //    91  'Gothic Rock'   103 'Opera' 115 'Folklore'

    /** {@code int} bitMask. */
    private int bitMask;

    /** {@link String} label */
    private String label;

    /**
     * Genre constructor.
     *
     * @param bitMaskVal
     *            {@code int}
     * @param labelVal {@link String}
     */
    private Genre(final int bitMaskVal, final String labelVal) {
        this.bitMask = bitMaskVal;
        this.label = labelVal;
    }

    /**
     * getBitMask.
     *
     * @see net.addradio.codec.mpeg.audio.model.BitMaskFlag#getBitMask()
     * @return {@code int}
     */
    @Override
    public int getBitMask() {
        return this.bitMask;
    }

    /**
     * @return the {@link String} label
     */
    public String getLabel() {
        return this.label != null ? this.label : name();
    }

}
