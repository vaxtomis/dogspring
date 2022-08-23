package com.dogspringframework.util;

/**
 * @author vaxtomis
 */
public class PrintUtils {

	private static final String TAB = "|---";

	private static final StringBuffer sb = new StringBuffer();

	private static int deep = 0;

	private static int lastCount = 0;

	public static void print(String msg) {
		int count = Thread.currentThread().getStackTrace().length;
		if (count > lastCount) {
			deep++;
		}
		else if (count < lastCount) {
			deep--;
		}
		lastCount = count;
		for (int i = 0; i < deep; i++) {
			sb.append(TAB);
		}
		sb.append(msg);
		System.out.println(sb.toString());
		sb.setLength(0);
	}
}
