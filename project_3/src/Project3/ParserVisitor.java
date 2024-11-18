package Project3;

public interface ParserVisitor {
	
    /**
     * Visit method for processing a FileSource.
     *
     * @param the FileSource "source" containing the path to the file and format. 
     */
    void visit(FileSource source);

    /**
     * Visit method for processing a URLSource.   
     * @param the URLSource containing the URL of the data.
     */
    void visit(URLSource source);
}
