package app.tweet.base;

/**
 * アプリで利用する定数情報を格納したクラス
 * @author aoi
 *
 */
public class TweetConst {
	
	/**
	 * ユーザ画面で表示する情報を切り替えるための列挙型
	 * @author aoi
	 *
	 */
	public static enum UserViewMethod {
		POST(""),
		FOLLOWING("following"),
		FOLLOWER("follower"),
		FAVORITE("favorite");
		
		private final String method;
		
		//コンストラクタ
		private UserViewMethod(final String method) {
			this.method = method;
		}
		
		public String getMethod() {
	        return method;
	    }
		
	}

}
