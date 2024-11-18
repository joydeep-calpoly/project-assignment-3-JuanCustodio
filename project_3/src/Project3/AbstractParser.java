package Project3;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractParser {

    protected Logger logger;

    public AbstractParser() {
        this.logger = Logger.getLogger(AbstractParser.class.getName());
    }

    /**
     * Abstract method to parse data from the  source.
     *
     * @param dataSource the source of data to parse
     * @return a list of articles parsed from the source
     */
    public abstract List<Article> parse(String dataSource);
    
    /**
     * Logs an error to the user.
     *
     * @param message to log
     */
    protected void logError(String message) {
        logger.warning(message);
    }
}
