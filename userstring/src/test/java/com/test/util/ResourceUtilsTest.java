package com.test.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceUtilsTest {
	
	@Autowired
	ResourceUtils resourceUtils;

	@Test
	public void testAddDigitInString() {
		String text = "5abc141def";
		Integer result = resourceUtils.addDigitsInString(text);
		assertThat(result).isEqualTo(146);
	}
}
