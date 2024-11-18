package Project3;

import java.util.List;

public class JSONInfo {

    private String status;
    private int totalResults;
    private List<Article> articles;

    /**
     * Returns the total number of articles.
     *
     * @return the total number of articles
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * Sets the total number of articles.
     *
     * @param totalResults, total number of articles
     */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * Returns the status of the API. 
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the API. 
     *
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the list of articles. 
     *
     * @return a list of articles
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     * Sets the list of articles.
     *
     * @param list of articles
     */
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
