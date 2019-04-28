package org.fartpig.dbunit_extractor_web.util;

public class StringUtils {

	public static String subStr(String originalStr, int fromIndex, int toIndex) {
		if (toIndex <= fromIndex) {
			return "";
		}

		if (fromIndex >= originalStr.length()) {
			return "";
		}

		if (toIndex >= originalStr.length()) {
			return originalStr.substring(fromIndex);
		}

		return originalStr.substring(fromIndex, toIndex);
	}

	public static String subStr(String originalStr, int fromIndex) {
		if (fromIndex >= originalStr.length()) {
			return "";
		}

		return subStr(originalStr, fromIndex, originalStr.length());
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}

	public static String nullOrTrim(String str) {
		if (str == null) {
			return null;
		}
		return str.trim();
	}

	public static String toUpperCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toUpperCase();
	}
}
