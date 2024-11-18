package Project3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.io.IOException;

public class Main {
	
    /**
     * The main method for Project 3, allows user to input their needed source type and file path as well as URL. 
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        Logger logger = LoggerSetter.getLogger();
        String apiKey = loadApiKey("inputs/secretkey.txt");

        if (apiKey == null) {
            System.err.println("Failed to load API key.");
            return;
        }

        NewsParser newsParser = new NewsParser(logger, apiKey);
        SimpleParser simpleParser = new SimpleParser(logger);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the source type (file/url): ");
        String sourceType = scanner.nextLine();

        if (sourceType.equalsIgnoreCase("file")) {
            System.out.println("Enter the file path: ");
            String filePath = scanner.nextLine();
            System.out.println("Enter the format (newsapi/simple): ");
            String format = scanner.nextLine();

            FileSource fileSource = new FileSource(filePath, format);
            if (format.equalsIgnoreCase("newsapi")) {
                fileSource.accept(newsParser);
            } else if (format.equalsIgnoreCase("simple")) {
                fileSource.accept(simpleParser);
            }
        } else if (sourceType.equalsIgnoreCase("url")) {
            System.out.println("Enter the URL, for example, https://newsapi.org/v2/top-headlines?country=us&apiKey=your_api_key\n"
            		+ ": ");
            String url = scanner.nextLine();
            URLSource urlSource = new URLSource(url);
            urlSource.accept(newsParser);
        }
    }

    /**
     * Loads the API key from the specified file path.

     * 
     * @param filePath to the file containing the API key.
     * @return The API key as a string, or null if there was an error loading it.
     */
    public static String loadApiKey(String filePath) {
        try {
            return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath))).trim();
        } catch (Exception e) {
            System.err.println("Error loading API key: " + e.getMessage());
            return null;
        }
    }
}


