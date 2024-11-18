package Project3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class TestCases {

    private Logger logger;
    private NewsParser newsParser; 
    private SimpleParser simpleParser; 

    private String readJsonFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * Sets up the logger and parsers before each test below.
     */
    @BeforeEach
    void setup() {
        logger = LoggerSetter.getLogger(); 
        String apiKey = "fakekey"; 
        newsParser = new NewsParser(logger, apiKey); 
        simpleParser = new SimpleParser(logger); 
        logger = LoggerSetter.getLogger(); 
    }
    
    /**
     * Tests that FileSource with newsapi format to trigger the NewsParser.
     */
    @Test
    void testFileSourceWithNewsAPIFormatTriggersNewsParser() {
        String filePath = "inputs/newsapi.txt";
        String format = "newsapi";
        
        FileSource fileSource = new FileSource(filePath, format);
        fileSource.accept(newsParser); //tests the implementation of the visit method as well
        
        // Asserting that the correct parsing method has been called
        assertTrue(fileSource.getFormat().equals("newsapi"));
    }

    /**
     * Tests that FileSource with simple format for the SimpleParser.
     */
    @Test
    void testFileSourceWithSimpleFormatTriggersSimpleParser() {
        String filePath = "inputs/simple.txt";
        String format = "simple"; 
        
        FileSource fileSource = new FileSource(filePath, format);
        fileSource.accept(simpleParser);
        
        // Asserts that the correct parsing method has been called 
        assertTrue(fileSource.getFormat().equals("simple"));
    }

    /**
     * Tests that the URLSource triggers the NewsParser
     */
    @Test
    void testURLSourceTriggersNewsParser() {
        String url = "https://newsapi.org/v2/top-headlines?country=us"; 
        
        URLSource urlSource = new URLSource(url);
        urlSource.accept(newsParser); 
        
        // URL should be passed to the NewsParser
        assertTrue(urlSource.getUrl().equals(url));
    }

    /**
     * Tests NewsParser with articles from newsapi.txt and sees that the fields are present.
     */
    @Test
    void testNewsParserWithFileInput() throws IOException {
        String filePath = "inputs/newsapi.txt"; 
        String jsonInput = readJsonFromFile(filePath);

        List<Article> articles = newsParser.parse(jsonInput); 
        assertNotNull(articles);
        assertFalse(articles.isEmpty());

        for (Article article : articles) {
            assertNotNull(article.getTitle());
            assertNotNull(article.getUrl());
            assertNotNull(article.getDescription());
            assertNotNull(article.getPublishedAt());
        }
    }

    /**
     * Tests NewsParser with completely missing articles.
     */
    @Test
    void testNewsParserWithEmptyArticles() {
        String emptyArticlesInput = """
            {
                "status": "ok",
                "totalResults": 0,
                "articles": []
            }
        """;

        List<Article> articles = newsParser.parse(emptyArticlesInput);
        
        assertNotNull(articles);
        assertTrue(articles.isEmpty());
    }

    /**
     * Tests NewsParser with articles that have missing fields.
     */
    @Test
    void testNewsParserWithMissingFields() {
        String invalidInput = """
            {
                "status": "ok",
                "totalResults": 2,
                "articles": [
                    {
                        "source": {
                            "id": "cnn",
                            "name": "CNN"
                        },
                        "author": "author1",
                        "title": "Article 1",
                        "description": "Description of Article 1",
                        "url": "https://example.com/article1",
                        "publishedAt": "2024-10-10T10:00:00Z"
                    },
                    {
                        "source": {
                            "id": "cnn2",
                            "name": "CNN2"
                        },
                        "author": "author2",
                        "description": "Description of Article 2",
                        "url": "https://example.com/article2",
                        "urlToImage": "https://example.com/image2.jpg",
                        "publishedAt": "2024-10-10T10:00:00Z"
                    },
                    {
                        "source": {
                            "id": "cnn3",
                            "name": "CNN3"
                        },
                        "author": "author3",
                        "title": "Article 3",
                        "description": "Description of Article 3",
                        "publishedAt": "2024-10-10T10:00:00Z"
                    }
                ]
            }
        """;

        List<Article> articles = newsParser.parse(invalidInput);
        
        assertNotNull(articles);
        assertEquals(1, articles.size()); // Only one valid article should be present

        Article validArticle = articles.get(0);
        assertEquals("Article 1", validArticle.getTitle());
        assertEquals("https://example.com/article1", validArticle.getUrl());
        assertEquals("Description of Article 1", validArticle.getDescription());
        assertEquals("2024-10-10T10:00:00Z", validArticle.getPublishedAt());
    }

    /**
     * Tests SimpleParser with a valid article from simple.txt and sees that the fields are present.
     */
    @Test
    void testSimpleParserWithFileInput() throws IOException {
        String filePath = "inputs/simple.txt"; 
        String jsonInput = readJsonFromFile(filePath);

        List<Article> articles = simpleParser.parse(jsonInput);
        assertNotNull(articles);
        assertEquals(1, articles.size());

        Article article = articles.get(0);
        assertNotNull(article.getTitle());
        assertNotNull(article.getUrl());
        assertNotNull(article.getDescription());
        assertNotNull(article.getPublishedAt());
    }

    /**
     * Tests SimpleParser with a invalid article
     */
    @Test
    void testSimpleParserWithMissingFields() {
        String invalidSimpleInput = """
            {
                "title": "Article",
                
                "description": "A description.",
                "publishedAt": "2024-11-01"
            }
            """;

        List<Article> articles = simpleParser.parse(invalidSimpleInput);
        assertNotNull(articles);
        assertEquals(1, articles.size());

        Article article = articles.get(0);
        assertNotNull(article.getTitle());
        assertNull(article.getUrl());
        assertNotNull(article.getDescription());
        assertNotNull(article.getPublishedAt());
    }
}



