package Project3;

import com.fasterxml.jackson.annotation.JsonProperty;

class Article {
    
    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    /**
     * Constructs an Article object with the attributes below.
     *
     * @param source      The source of the article.
     * @param author      The author of the article.
     * @param title       The title of the article.
     * @param description A description of the article.
     * @param url        The URL of the article.
     * @param urlToImage  The URL of the image associated with the article.
     * @param publishedAt The date and time the article was published.
     * @param content     The content of the article.
    */
    public Article(@JsonProperty("source") Source source, 
                   @JsonProperty("author") String author, 
                   @JsonProperty("title") String title, 
                   @JsonProperty("description") String description, 
                   @JsonProperty("url") String url, 
                   @JsonProperty("urlToImage") String urlToImage, 
                   @JsonProperty("publishedAt") String publishedAt, 
                   @JsonProperty("content") String content) 
    {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    /**
     * Returns the source of the article.
     *
     * @return The source which contains source id and name. 
    */
    public Source getSource() { 
        return source; 
    }

    /**
     * Returns the author of the article.
     *
     * @return The author string. 
    */
    public String getAuthor() { 
        return author; 
    }

    /**
     * Returns the title of the article.
     *
     * @return The title. 
    */
    public String getTitle() { 
        return title; 
    }

    /**
     * Returns the description of article.
     *
     * @return The description.
    */
    public String getDescription() { 
        return description; 
    }

    /**
     * Returns the URL of the article.
     *
     * @return The URL of the article.
    */
    public String getUrl() { 
        return url; 
    }

    /**
     * Returns the URL of the image.
     *
     * @return The URL of image of article.
    */
    public String getUrlToImage() { 
        return urlToImage; 
    }

    /**
     * Returns the published date .
     *
     * @return The published date of the article.
    */
    public String getPublishedAt() { 
        return publishedAt; 
    }

    /**
     * Returns the content.
     *
     * @return The content of the article.
    */
    public String getContent() { 
        return content; 
    }
}

