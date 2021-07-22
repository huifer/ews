package com.github.huifer.ews.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.omg.CORBA.PRIVATE_MEMBER;

public interface Convert<T> {
	default T convert(String data, Style style) {
		throw new RuntimeException();
	}


	@AllArgsConstructor
	@Getter
	enum DateTimeFormatStyle implements Style {
		ONE("yyyy-MM-dd"),
		TWO("yyyyMMddHHmmss"),
		THREE("yyyy-MM-dd HH:mm:ss"),
		FOUR("yyyy-MM-dd HH:MM:ss"),
		;
		private final String format;
	}


	interface Style {
		String getFormat();
	}
}
