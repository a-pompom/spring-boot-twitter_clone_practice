package app.tweet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.tweet.dao.TsImageDao;
import app.tweet.entity.TsImage;

/**
 * 画像関連の処理を扱うサービス
 * @author aoi
 *
 */
@Service
public class ImageService {
	
	@Autowired
	private TsImageDao tsImageDao;
	
	@Transactional
	public int saveImage(String imageName) {
		TsImage entity = new TsImage();
		entity.setImageName(imageName);
		
		TsImage savedEntity = tsImageDao.saveOrUpdate(entity);
		return savedEntity.getImageId();
	}
	
	/**
	 * ログインユーザのアイコンのパスを取得する　
	 * @param loginUserId ログインユーザのID
	 * @return ログインユーザのアイコンのパス
	 */
	public String getLoginIconPath(int loginUserId) {
		TsImage entity = tsImageDao.findLoginIconPath(loginUserId);
		return entity.getImageName();
	}
	
	public String addParentSymbol(String imagePath) {
		return "../" + imagePath;
	}

}
