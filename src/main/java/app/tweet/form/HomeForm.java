package app.tweet.form;

import java.util.List;

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
	
	/**
	 * タイムラインとして表示する投稿DTOのリスト
	 */
	private List<HomeDto> dtoList;

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

	/**
	 * @return the dtoList
	 */
	public List<HomeDto> getDtoList() {
		return dtoList;
	}

	/**
	 * @param dtoList the dtoList to set
	 */
	public void setDtoList(List<HomeDto> dtoList) {
		this.dtoList = dtoList;
	}
	
	
	
	
	

}
