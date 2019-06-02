package app.tweet.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import app.tweet.base.TweetConst;
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
	
	/**
	 * 画像をサーバ上へ配置し、対応情報をDBへ登録する
	 * @param uploadedImage MultiPartFile形式の画像ファイル
	 * @param physicalFileName 画像ファイル名
	 * @return DBへ保存された画像の参照ID
	 * @throws IOException
	 */
	@Transactional
	public int saveImage(MultipartFile uploadedImage, String physicalFileName) throws IOException{
			String imageName = createPhysicalImage(uploadedImage, physicalFileName);
			TsImage entity = new TsImage();
			entity.setImageName(imageName);
			TsImage savedEntity = tsImageDao.saveOrUpdate(entity);
			return savedEntity.getImageId();
	}
	
	/**
	 * アップロードされた画像ファイルをサーバ上に配置する
	 * @param uploadedImage multiParFile形式のアップロード画像ファイル
	 * @param physicalFileName  保存時のファイル名
	 * @return 保存結果のファイル名
	 * @throws IOException
	 */
	private String createPhysicalImage(MultipartFile uploadedImage, String physicalFileName) throws IOException {
		//バイト配列、ユーザ名、イメージを渡してイメージサービスで処理すべき
		//バイト配列形式で画像ファイルを取得
		byte[] bytes = uploadedImage.getBytes();
		//拡張子
		int extLocation = uploadedImage.getOriginalFilename().lastIndexOf(".");
		String extension = uploadedImage.getOriginalFilename().substring(extLocation);
		//ファイル名(フルパス)
		String imageName = TweetConst.IMAGE_FOLDER + physicalFileName + extension;
		
		//バイト配列で上書き用のTempファイル生成
		File tempFile = new File(imageName);
		tempFile.createNewFile();
		
		Path path = Paths.get(imageName);
		Files.write(path, bytes);
		
		String imagePath = imageName.replace("static", "");
		
		return imagePath;
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
	
	/**
	 * ログインユーザのアイコンのIDを取得する(save用)
	 * @param loginUserId ログインユーザのID
	 * @return ログインユーザのアイコンのID
	 */
	public int getLoginIconId(int loginUserId) {
		TsImage entity = tsImageDao.findLoginIconPath(loginUserId);
		return entity.getImageId();
	}

}
