package app.tweet.util;

public final class StringUtil {
	/**
	 * 文字列が空か判定する
	 * @param value 判定対象の文字列
	 * @return 空→true 空でない→false
	 */
	public static Boolean isNullOrEmpty(String value) {
		if (value == null) {
			return true;
		}
		
		if (value == "") {
			return true;
		}
		
		return false;
	}
}
