package com.dogspringframework.util;

/**
 * @author vaxtomis
 */
public class PrintUtils {

	private static final String TAB = "    ";

	private static final StringBuffer sb = new StringBuffer();

	public static void print(String msg) {
		print(msg, 0);
	}

	public static void print(String msg, int count) {
		for (int i = 0; i < count; i++) {
			sb.append(TAB);
		}
		sb.append(msg);
		System.out.println(sb.toString());
		sb.setLength(0);
	}
}
