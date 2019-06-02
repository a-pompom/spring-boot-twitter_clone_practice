package app.tweet.base;

/**
 * アプリで利用する定数情報を格納したクラス
 * @author aoi
 *
 */
public class TweetConst {
	
	public static final String IMAGE_FOLDER = "static/images/";
	
	/**
	 * ユーザ画面のパスを管理する列挙型
	 * @author aoi
	 *
	 */
	public static enum UserPath {
		INITPATH(0),
		USER_NAME(1),
		METHOD(2);
		
		private final int userPath;
		
		//コンストラクタ
		private UserPath(final int userPath) {
			this.userPath = userPath;
		}
		
		public int getUserPath() {
			return this.userPath;
		}
	}
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
