package Project3;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

 class LoggerSetter {
    private static Logger logger = Logger.getLogger(LoggerSetter.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("reports.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); 
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns the logger instance.
     *
     * @return The logger to write to a file.
    */
    public static Logger getLogger() {
        return logger;
    }
}
