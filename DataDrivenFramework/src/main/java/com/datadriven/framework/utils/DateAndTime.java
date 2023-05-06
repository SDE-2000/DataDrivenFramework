package com.datadriven.framework.utils;

import java.util.Date;

public class DateAndTime {

	public static String  timeStamp() {
		Date date = new Date();
		return (date.toString().replaceAll(" ", "_").replaceAll(":", "_"));
	}
}
