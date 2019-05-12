/**
 * 文字列が空もしくはnullかどうか検証する
 * @param   {String} value 検証対象文字列
 * @returns {boolean}  空もしくはnull→true それ以外→false
 */
export function isNullOrEmpty(value) {
	if (value === null) {
		return true;
	}
	
	if (value === "") {
		return true;
	}
	
	return false;
}

/**
 * 二つの値が文字列として等しいか検証する。
 * @param   {Object} value1 文字列に変換可能な値
 * @param   {Object} value2 文字列に変換可能な値
 * @returns {boolean}  等しい→true 等しくない→false
 */
export function isSameString(value1, value2) {
	//文字列化して等しいか比較
	if (value1.toString() === value2.toString()) {
		return true;
	}
	
	return false;
}