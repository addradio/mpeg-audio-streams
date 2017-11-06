/**
 * Class:    Log4J<br/>
 * <br/>
 * Created:  06.11.2017<br/>
 * Filename: Log4J.java<br/>
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

package net.addradio;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Log4J
 */
public final class Log4J {

    /**
     * Hidden Log4J constructor.
     */
    private Log4J() {
    }

    /**
     * configureLog4J.
     * @param level {@link Level}
     */
    public static final void configureLog4J(final Level level) {
        final Logger rootLogger = Logger.getRootLogger();
        if (!rootLogger.getAllAppenders().hasMoreElements()) {
            rootLogger.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
        }
        rootLogger.setLevel(level);
    }

}
