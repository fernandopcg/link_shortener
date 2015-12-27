package link_shortener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.RandomStringUtils;


@Component
public class UrlHelper {
	
	static final Logger logger = LoggerFactory.getLogger(UrlHelper.class);
	
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
    private static final int LENGTH = 7;	// TODO: Store in parameters file
	public String generateShortUri() {
		// TODO: Check if the string is already in the DB, and in that case generate another one to avoid random collisions
		return RandomStringUtils.random(LENGTH, ALPHABET);
	}
	
	public String validateUrl(String url) {
		url = addProtocolIfMissing(url);
		return url;
	}
	
	public static String addProtocolIfMissing(String url) {
	    if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("ftp://")) {
	        url = "http://" + url;
	    }
	    return url;
	}

}
