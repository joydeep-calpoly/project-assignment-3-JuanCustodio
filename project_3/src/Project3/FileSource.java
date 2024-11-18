package Project3;

public class FileSource {

	
    private String filePath;
    private String format;

    /**
     * Constructs a FileSource with the specified file path and format.
     *
     * @param filePath the path to the file containing article data.
     * @param format the format of the article data. 
     */
    public FileSource(String filePath, String format) {
        this.filePath = filePath;
        this.format = format;
    }

    /**
     * Returns the file path associated with this FileSource.
     * 
     * @return the file path string.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Returns the format of the article data stored in the file
     * 
     * @return the format string.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Accepts a parser visitor to process this FileSource.
     * 
     * @param parser that will process the FileSource.
     */
    public void accept(ParserVisitor parser) {
        parser.visit(this);
    }
}
