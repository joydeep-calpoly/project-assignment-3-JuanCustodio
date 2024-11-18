package Project3;

import com.fasterxml.jackson.annotation.JsonProperty;

class Source {
	
    @JsonProperty("id")
    private String id; 

    @JsonProperty("name")
    private String name;  

    /**
     * Constructor of Source object with the ID and name.
     *
     * @param id for the source.
     * @param name of the source.
    */
    public Source(@JsonProperty("id") String id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }


    
    /**
     * Returns the source id.
     *
     * @return The source id.
    */
    public String getId() { return id; }
    
    /**
     * Returns the name of source.
     *
     * @return name of source.
    */
    public String getName() { return name; }
   
    /**
     * Returns a string representation of the Source object.
     *
     * @return A string that displays the id and name of the source for the article.
    */
    @Override
    public String toString() {
        return "Source :" +
                "id= '" + id + '\'' +
                ", name= '" + name + '\'' +
                ' ';
    }
}
