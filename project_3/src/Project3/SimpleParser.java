package Project3;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SimpleParser extends AbstractParser implements ParserVisitor {

    private ObjectMapper objectMapper;

    /**
     * Constructs a SimpleParser instance and initializes the ObjectMapper.
     */
    public SimpleParser(Logger logger) {
        this.objectMapper = new ObjectMapper();
        this.logger = logger; 
        
    }
    
    /**
     * Visitor method for parsing articles from a file source.
     * @param source FileSource containing the path to the file.
     */
    @Override
    public void visit(FileSource source) {
        String jsonResponse = readFile(source.getFilePath());
        List<Article> articles = parse(jsonResponse);
        displayArticles(articles);
    }

    /**
     * Visitor method for parsing articles from a URL source (not applicable for SimpleParser).
     * @param source URLSource containing the URL.
     */
    @Override
    public void visit(URLSource source) {
        logger.warning("SimpleParser does not use URL sources.");
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
     * Parses the provided simple JSON data source and returns a list containing articles.
     *
     * @param dataSource A String containing the JSON data to be parsed.
     * @return A list of Article objects parsed from the JSON
     *         Returns an empty list if an error occurs.
     */
    @Override
    public List<Article> parse(String dataSource) {
        List<Article> articles = new ArrayList<>();
        try {
            Article article = objectMapper.readValue(dataSource, Article.class);
            articles.add(article);
        } catch (IOException e) {
            logError("Error parsing data: " + e.getMessage());
        }
        return articles;
    }
}
