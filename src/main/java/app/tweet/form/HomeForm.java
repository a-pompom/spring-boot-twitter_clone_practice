package app.tweet.form;

import app.tweet.dto.HomeDto;

/**
 * ホーム画面へ表示する情報を格納したフォーム
 * @author aoi
 *
 */
public class HomeForm {
	
	/**
	 * 投稿・投稿者情報を格納したDTO
	 */
	private HomeDto dto;
	
	//TODO ホーム画面の投稿一覧として表示するためDTOのリストをフィールドへ追加

	/**
	 * @return the dto
	 */
	public HomeDto getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(HomeDto dto) {
		this.dto = dto;
	}
	
	
	

}
