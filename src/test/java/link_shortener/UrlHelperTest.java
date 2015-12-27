package link_shortener;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import link_shortener.Application;
import link_shortener.UrlHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UrlHelperTest {

	@Test
	public void contextLoads() {
		System.out.println("Testing");
	    String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-=";
		System.out.println(RandomStringUtils.random(6, ALPHABET));
		UrlHelper myHelper = new UrlHelper();
		
		
	}

}
