package app.tweet.dto;


import app.tweet.annotation.AuthInputType;
import app.tweet.annotation.MaxLength;
import app.tweet.annotation.NotEmpty;
import app.tweet.annotation.UniqueUserID;

/**
 * ユーザ登録画面での入力情報を保持するためのDTO
 * エンティティで直接アノテーションによるバリデーションを行うと、
 * submit時、daoでのmerge時で2回実行され、かつdaoインスタンスを参照できなくなるので、
 * バリデーションが必要な項目は個別に直接定義
 * @author aoi
 */
public class SignUpDto {
	/**
	 * ユーザID
	 * 以下条件でバリデーション
	 * ・必須
	 * ・32文字以下制限
	 * ・ユニーク
	 * ・半角英数及びハイフン、アンダースコアのみ
	 */
	@NotEmpty(message = "ユーザIDを入力してください")
	@UniqueUserID
	@MaxLength(message = "ユーザIDは32文字以下で入力してください", maxLength = 32)
	@AuthInputType
	private String userId;
	
	/**
	 * パスワード
	 * 以下条件でバリデーション
	 * ・必須
	 * ・32文字以下制限
	 * ・半角英数及びハイフン、アンダースコアのみ
	 */
	@NotEmpty(message = "パスワードを入力してください")
	@MaxLength(message = "パスワードは32文字以下で入力してください", maxLength = 32)
	@AuthInputType
	private String password;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
