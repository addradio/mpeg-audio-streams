/**
 * Class:    ID3v1Tag<br/>
 * <br/>
 * Created:  29.10.2017<br/>
 * Filename: ID3v1Tag.java<br/>
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

import net.addradio.codec.id3.model.ID3Tag;

/**
 * ID3v1Tag.
 *
 * <pre>
 * T      A      G
 * 084    065    071
 * 0x55   0x41   0x47
 * </pre>
 */
public class ID3v1Tag extends ID3Tag {

    /** {@code int} ID3_V1_OVERALL_SIZE */
    public static final int ID3_V1_OVERALL_SIZE = 128;

    /** {@link String} album. */
    private String album;

    /** {@link String} artist. */
    private String artist;

    /** {@link String} comment. */
    private String comment;

    /** {@link Genre} genre. */
    private Genre genre;

    /** {@link String} title. */
    private String title;

    /** {@code int} year. */
    private int year;

    /**
     * equals.
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj {@link Object}
     * @return {@code boolean}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ID3v1Tag other = (ID3v1Tag) obj;
        if (this.album == null) {
            if (other.album != null) {
                return false;
            }
        } else if (!this.album.equals(other.album)) {
            return false;
        }
        if (this.artist == null) {
            if (other.artist != null) {
                return false;
            }
        } else if (!this.artist.equals(other.artist)) {
            return false;
        }
        if (this.comment == null) {
            if (other.comment != null) {
                return false;
            }
        } else if (!this.comment.equals(other.comment)) {
            return false;
        }
        if (this.genre != other.genre) {
            return false;
        }
        if (this.title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!this.title.equals(other.title)) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }

    /**
     * getAlbum.
     * @return {@link String} the album
     */
    public String getAlbum() {
        return this.album;
    }

    /**
     * getArtist.
     * @return {@link String} the artist
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * getComment.
     * @return {@link String} the comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * getGenre.
     * @return {@link Genre} the genre
     */
    public Genre getGenre() {
        return this.genre;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.id3.model.ID3Tag#getLeadPerformerSoloistORBandOrchestraAccompaniment()
     */
    @Override
    public String getLeadPerformerSoloistORBandOrchestraAccompaniment() {
        return getArtist();
    }

    /**
     * getOverallSize.
     * @see net.addradio.codec.id3.model.ID3Tag#getOverallSize()
     * @return {@code int}
     */
    @Override
    public int getOverallSize() {
        return ID3v1Tag.ID3_V1_OVERALL_SIZE;
    }

    /**
     * getTitle.
     * @return {@link String} the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * {@inheritDoc}
     * @see net.addradio.codec.id3.model.ID3Tag#getTitleSongnameContentDescription()
     */
    @Override
    public String getTitleSongnameContentDescription() {
        return getTitle();
    }

    /**
     * getYear.
     * @return {@code int} the year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * hashCode.
     * @see java.lang.Object#hashCode()
     * @return {@code int}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.album == null) ? 0 : this.album.hashCode());
        result = (prime * result) + ((this.artist == null) ? 0 : this.artist.hashCode());
        result = (prime * result) + ((this.comment == null) ? 0 : this.comment.hashCode());
        result = (prime * result) + ((this.genre == null) ? 0 : this.genre.hashCode());
        result = (prime * result) + ((this.title == null) ? 0 : this.title.hashCode());
        result = (prime * result) + this.year;
        return result;
    }

    /**
     * setAlbum.
     * @param albumVal {@link String} the album to set
     */
    public void setAlbum(final String albumVal) {
        this.album = albumVal;
    }

    /**
     * setArtist.
     * @param artistVal {@link String} the artist to set
     */
    public void setArtist(final String artistVal) {
        this.artist = artistVal;
    }

    /**
     * setComment.
     * @param commentVal {@link String} the comment to set
     */
    public void setComment(final String commentVal) {
        this.comment = commentVal;
    }

    /**
     * setGenre.
     * @param genreRef {@link Genre} the genre to set
     */
    public void setGenre(final Genre genreRef) {
        this.genre = genreRef;
    }

    /**
     * setTitle.
     * @param titleVal {@link String} the title to set
     */
    public void setTitle(final String titleVal) {
        this.title = titleVal;
    }

    /**
     * setYear.
     * @param yearVal {@code int} the year to set
     */
    public void setYear(final int yearVal) {
        this.year = yearVal;
    }

    /**
     * toString.
     * @see java.lang.Object#toString()
     * @return {@link String}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ID3v1Tag [title="); //$NON-NLS-1$
        builder.append(this.title);
        builder.append(", artist="); //$NON-NLS-1$
        builder.append(this.artist);
        builder.append(", album="); //$NON-NLS-1$
        builder.append(this.album);
        builder.append(", year="); //$NON-NLS-1$
        builder.append(this.year);
        builder.append(", comment="); //$NON-NLS-1$
        builder.append(this.comment);
        builder.append(", genre="); //$NON-NLS-1$
        builder.append(this.genre);
        builder.append("]"); //$NON-NLS-1$
        return builder.toString();
    }

}
