package com.github.huifer.ews.convert;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringTimeConvert<T> implements Convert<Date> {
	@Override
	public Date convert(String data, Style style) {
		if (style instanceof DateTimeFormatStyle) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(style.getFormat());//注意月份是MM
			try {
				return simpleDateFormat.parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
