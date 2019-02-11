package app.tweet.form;

import app.tweet.dto.SignUpDTO;

/**
 * ユーザ登録画面の入力情報を格納するためのフォーム
 * @author aoi
 *
 */
public class SignUpForm {
	
	/**
	 * ユーザ情報を格納したDTO
	 */
	private SignUpDTO dto;
	
	/**
	 * 確認用パスワードを保持するために利用
	 */
	private String confirmPassword;

	/**ユーザ情報を格納したDTOを取得する。
	 * @return the dto
	 */
	public SignUpDTO getDto() {
		return dto;
	}

	/**ユーザ情報を格納したDTOをセットする。
	 * @param dto the dto to set
	 */
	public void setDto(SignUpDTO dto) {
		this.dto = dto;
	}

	/**確認用パスワードを取得する。
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**確認用パスワードをセットする。
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	

}
