package com.petstore.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CategoryApplication.class)
@WebAppConfiguration
public class CategoryApplicationTests {

	@Test
	public void contextLoads() {
	}

}
