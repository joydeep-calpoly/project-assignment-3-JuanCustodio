package Project3;

import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class NewsParser extends AbstractParser implements ParserVisitor{

    private Logger logger;
    private String apiKey;

    /**
     * Parser constructor with logger and API key being passed.
     *
     * @param logger used for logging parsing errors.
     * @param apiKey used for accessing the NewsAPI.
     */
    public NewsParser(Logger logger, String apiKey) {
        this.logger = logger;
        this.apiKey = apiKey;
    }
    
    /**
     * Visitor method for parsing articles from a file source.
     * @param source FileSource object containing the path to the file.
     */
    @Override
    public void visit(FileSource source) {
        String jsonResponse = readFile(source.getFilePath());
        List<Article> articles = parse(jsonResponse);
        displayArticles(articles);
    }

    /** 
     * Visitor method for parsing articles from a URL source.
     * @param source URLSource object containing the URL.
     */
    @Override
    public void visit(URLSource source) {
        String jsonResponse = fetchArticles(source.getUrl());
        List<Article> articles = parse(jsonResponse);
        displayArticles(articles);
    }
    
    
    /**
     * Reads the content of a file at the specified path.
     *
     * @param The path to the file to read.
     * @return The content of the file as a String or an empty string if an error happens.
     */
    public String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Displays the list of articles.
     * 
     * @param articles List of articles to display.
     */
    public void displayArticles(List<Article> articles) {
        if (articles.isEmpty()) {
            System.out.println("No articles found.");
        } else {
            for (Article article : articles) {
                System.out.println("Title: " + article.getTitle());
                System.out.println("Content: " + article.getContent());
                System.out.println("Author: " + article.getAuthor());
                System.out.println("Published At: " + article.getPublishedAt());
                System.out.println();
            }
        }
    }

    /**
     * Fetches articles from the given URL and returns the JSON response as a string.
     *
     * @param urlString The URL from which to fetch articles.
     * @return JSON response string from the given URL, or an empty string if there's an error.
     */
    public String fetchArticles(String urlString) {
        String jsonResponse = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { // Success
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    jsonResponse += scanner.nextLine();
                }
                scanner.close();
            } else {
                logger.warning("Error: Received response code " + responseCode);
            }

        } catch (IOException e) {
            logger.warning("Error fetching articles: " + e.getMessage());
        }
        return jsonResponse;
    }

    /**
     * Method that parses the given json string input and checks if the given fields are present.
     * 
     * @return A list of articles that meet the requirements. 
     */
    @Override
    public List<Article> parse(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Article> articles = new ArrayList<>();

        try {
            JSONInfo jsonInfo = objectMapper.readValue(json, JSONInfo.class);  // Make sure this matches the API response structure

            for (Article article : jsonInfo.getArticles()) {
                boolean isValid = true; // Track validity of the article

                // Check for missing required fields
                if (article.getTitle() == null) {
                    logger.warning("Article skipped due to missing title...");
                    isValid = false;
                }
                if (article.getUrl() == null) {
                    logger.warning("Article skipped due to missing URL...");
                    isValid = false;
                }
                if (article.getDescription() == null) {
                    logger.warning("Article skipped due to missing description...");
                    isValid = false;
                }
                if (article.getPublishedAt() == null) {
                    logger.warning("Article skipped due to missing date...");
                    isValid = false;
                }

                if (isValid) {
                    articles.add(article);
                }
            }

        } catch (Exception e) {
            logger.warning("Error processing JSON: " + e.getMessage());
        }
        return articles;
    }
    
}


