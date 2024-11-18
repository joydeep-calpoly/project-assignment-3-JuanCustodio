package Project3;

public class URLSource {

    private String url;
    
    /**
     * Constructs a URLSource with the given URL.
     * 
     * @param url. 
     */
    public URLSource(String url) {
        this.url = url;
    }

    /**
     * Returns the URL associated with this URLSource.
     * 
     * @return the url string.
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * Accepts a parser visitor.  
     * 
     * @param parser that will process the URLSource.
     */
    public void accept(ParserVisitor parser) {
        parser.visit(this);
    }
}
