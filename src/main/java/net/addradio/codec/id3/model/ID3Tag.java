/**
 * Class:    ID3Tag<br/>
 * <br/>
 * Created:  01.11.2017<br/>
 * Filename: ID3Tag.java<br/>
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

package net.addradio.codec.id3.model;

/**
 * ID3Tag
 */
public interface ID3Tag {

    /**
     * getOverallSize.
     * @return {@code int} the tag's overall size (incl. header) in {@code bytes}
     */
    int getOverallSize();

}
