package link_shortener;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "urls_repository")
public class Url {
	
	static final Logger logger = LoggerFactory.getLogger(Url.class);
	
	private String id;
	
	private String originalUrl;
    private Date creationDate;
    
    public String getId() {
    	return id;
    }
    
    public String getOriginalUrl() {
    	return this.originalUrl;
    }
    
	public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
	
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
	
	public void setId(String id) {
        this.id = id;
    }
	
	public String toString() {
		return "URL: " + originalUrl + "; hash: " + id + "; ID: " + id;
	}

}