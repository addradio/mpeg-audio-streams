/**
 * Class:    MP3TestFiles<br/>
 * <br/>
 * Created:  20.10.2017<br/>
 * Filename: MP3TestFiles.java<br/>
 * Version:  $Revision: $<br/>
 * <br/>
 * last modified on $Date:  $<br/>
 *               by $Author: $<br/>
 * <br/>
 * @author <a href="mailto:sebastian.weiss@nacamar.de">Sebastian A. Weiss, nacamar GmbH</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 * <br/>
 * (c) Sebastian A. Weiss, nacamar GmbH 2012 - All rights reserved.
 */

package net.addradio.codec.mpeg.audio.tools;

import java.io.File;
import java.io.FileFilter;

/**
 * MP3TestFiles
 */
public class MP3TestFiles {

    /**
     * FileHandler
     */
    public static interface FileHandler {

        /**
         * handle.
         * @param file
         */
        void handle(File file);
    }

    /**
     * {@link String} _MP3_TEST_FILE_DIRECTORY.
     * (20171027 saw) name needs to start with _ since automated sorted needs to put it in first place.
     */
    public static final String _MP3_TEST_FILE_DIRECTORY = "src" + File.separator + "test" + File.separator //$NON-NLS-1$ //$NON-NLS-2$
            + "mp3"; //$NON-NLS-1$

    /** {@link String} FILE_NAME_1000HZ_MP3 */
    public static final String FILE_NAME_1000HZ_MP3 = MP3TestFiles._MP3_TEST_FILE_DIRECTORY + File.separator
            + "1000Hz.mp3"; //$NON-NLS-1$

    /** {@link String} FILE_NAME_440HZ_MP3 */
    public static final String FILE_NAME_440HZ_MP3 = MP3TestFiles._MP3_TEST_FILE_DIRECTORY + File.separator
            + "440Hz.mp3"; //$NON-NLS-1$

    /** {@link String} FILE_NAME_CLICK_MP3 */
    public static final String FILE_NAME_CLICK_MP3 = MP3TestFiles._MP3_TEST_FILE_DIRECTORY + File.separator
            + "click.mp3"; //$NON-NLS-1$

    /** {@link String} FILE_NAME_MUSIC_MP3 */
    public static final String FILE_NAME_MUSIC_MP3 = MP3TestFiles._MP3_TEST_FILE_DIRECTORY + File.separator
            + "music.mp3"; //$NON-NLS-1$

    /** {@link String} FILE_NAME_ORGAN_MP3 */
    public static final String FILE_NAME_ORGAN_MP3 = MP3TestFiles._MP3_TEST_FILE_DIRECTORY + File.separator
            + "organ.mp3"; //$NON-NLS-1$

    /** {@link String} FILE_NAME_PIANO_MP3 */
    public static final String FILE_NAME_PIANO_MP3 = MP3TestFiles._MP3_TEST_FILE_DIRECTORY + File.separator
            + "piano.mp3"; //$NON-NLS-1$

    /** {@link String} FILE_NAME_SWEEP_MP3 */
    public static final String FILE_NAME_SWEEP_MP3 = MP3TestFiles._MP3_TEST_FILE_DIRECTORY + File.separator
            + "sweep.mp3"; //$NON-NLS-1$

    /** {@link String} MP3_SUFFIX. */
    public static final String MP3_SUFFIX = ".mp3"; //$NON-NLS-1$

    /**
     * iterateOverTestFiles.
     * @param fileHandler {@link FileHandler}
     */
    public static final void iterateOverTestFiles(final FileHandler fileHandler) {
        iterateOverTestFiles(fileHandler, MP3TestFiles._MP3_TEST_FILE_DIRECTORY);
    }

    /**
     * iterateOverTestFiles.
     * @param fileHandler {@link FileHandler}
     * @param mp3TestFileDirectory {@link String}
     */
    public static void iterateOverTestFiles(final FileHandler fileHandler, final String mp3TestFileDirectory) {
        final File[] files = new File(mp3TestFileDirectory).listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isFile() && pathname.canRead()
                        && pathname.getName().toLowerCase().endsWith(MP3TestFiles.MP3_SUFFIX);
            }
        });
        for (final File file : files) {
            fileHandler.handle(file);
        }
    }

}
