package app.tweet.util;

public class QueryBuilder {
	
	/**
	 * クエリを格納するためのオブジェクト
	 */
	private StringBuilder sb;
	
	//インスタンス生成時にクエリ格納用のStringBuilderも同時にインスタンス化
	public QueryBuilder() {
		this.sb = new StringBuilder();
	}
	
	/**
	 * クエリ用文字列をクエリへ追加する。
	 */
	public void append(String queryString) {
		sb.append(queryString);
		
	}
	
	/**
	 * クエリを文字列で出力する
	 */
	public String toString() {
		return sb.toString();
	}
	
	

}
