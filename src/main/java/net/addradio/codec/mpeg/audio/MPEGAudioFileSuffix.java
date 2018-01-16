/**
 * Class:    MPEGAudioFileSuffix<br/>
 * <br/>
 * Created:  16.01.2018<br/>
 * Filename: MPEGAudioFileSuffix.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2018 - All rights reserved.
 */

package net.addradio.codec.mpeg.audio;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;

/**
 * MPEGAudioFileSuffix
 */
public enum MPEGAudioFileSuffix {

    /** {@link MPEGAudioFileSuffix} mp3 */
    mp3(".mp3"), //$NON-NLS-1$

    /** {@link MPEGAudioFileSuffix} mpeg */
    mpeg(".mpeg"), //$NON-NLS-1$

    /** {@link MPEGAudioFileSuffix} mpg */
    mpg(".mpg"); //$NON-NLS-1$

    /** {@link FilenFilter} FILE_FILTER */
    public static final FileFilter FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(final File arg0) {
            return MPEGAudioFileSuffix.isMPEGAudioFile(arg0.getName());
        }

        @Override
        public String getDescription() {
            return "MPEG Audio File"; //$NON-NLS-1$
        }
    };

    /** {@link FilenameFilter} FILENAME_FILTER */
    public static final FilenameFilter FILENAME_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            return MPEGAudioFileSuffix.isMPEGAudioFile(name);
        }
    };

    /**
     * isMPEGAudioFile.
     * @param filename {@link String}
     * @return {@code boolean true} if filename ends with known suffix.
     */
    public static final boolean isMPEGAudioFile(final String filename) {
        final String lowerCase = filename.toLowerCase();
        return lowerCase.endsWith(mp3.getSuffix()) || lowerCase.endsWith(mpg.getSuffix())
                || lowerCase.endsWith(mpeg.getSuffix());
    }

    /** {@link String} suffix */
    private final String suffix;

    /**
     * MPEGAudioFileSuffix constructor.
     * @param suffixVal {@link String}
     */
    private MPEGAudioFileSuffix(final String suffixVal) {
        this.suffix = suffixVal;
    }

    /**
     * @return the {@link String} suffix
     */
    public String getSuffix() {
        return this.suffix;
    }

}
