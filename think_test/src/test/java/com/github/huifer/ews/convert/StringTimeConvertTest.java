package com.github.huifer.ews.convert;

import org.junit.jupiter.api.Test;

import java.util.Date;

class StringTimeConvertTest {

	@Test
	void convert() {
		Convert<Date> convert = new StringTimeConvert<Date>();
		Date convert1 = convert.convert("2021-01-01", Convert.DateTimeFormatStyle.ONE);
		System.out.println(convert1.getTime());
	}
}