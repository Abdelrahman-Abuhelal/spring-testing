package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class DemoApplicationTests {

	@Test
	void itShouldAddTwoNumbers() {
		// given
		int firstNum=10;
		int secNum=25;

		//when
		int result=Math.addExact(firstNum,secNum);

		//then
		assertThat(result).isEqualTo(35);
	}

}
