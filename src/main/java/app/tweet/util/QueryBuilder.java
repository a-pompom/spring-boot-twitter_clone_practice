package app.tweet.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.tweet.base.BaseEntity;

/**
 * クエリ作成クラス
 * 呼び出す際は以下手順で利用する
 * ・EntityManagerを渡してインスタンス化
 * ・appendメソッドでクエリ文字列を渡す。パラメータは「:」プレースホルダを付与
 * ・setParamメソッドでkey-value形式でパラメータを付与。このとき、keyにプレースホルダは不要
 * ・createQueryで結果セットが属するEntityクラスを指定
 * ・結果セットをfindSingle/findResultListメソッドで取得
 * 
 * @author aoi
 */
public class QueryBuilder {
	
	/**
	 * Queryオブジェクトを操作するためのインタフェース
	 */
	EntityManager em;
	
	/**
	 * クエリ文字列のリスト
	 */
	StringBuilder queryString;
	
	/**
	 * パラメータのkey部分を保持するリスト
	 */
	private List<String> paramNameList;
	
	/**
	 * パラメータのvalue部分を保持するリスト
	 */
	private List<Object> paramValueList;
	
	/**
	 * クエリオブジェクト
	 */
	private Query query;
	
	/**
	 * コンストラクタ
	 * クエリ・パラメータを格納するリストを初期化する
	 * @param em
	 */
	public QueryBuilder(EntityManager em) {
		this.em = em;
		this.queryString = new StringBuilder();
		this.paramNameList = new ArrayList<String>();
		this.paramValueList = new ArrayList<Object>();
	}
	
	/**
	 * クエリ文字列をリストに追加する。
	 * 連続で追加できるよう、自身を返す
	 * @param inputQuery 入力されたクエリ文字列
	 * @return QueryBuilder
	 */
	public QueryBuilder append(String inputQuery) {
		this.queryString.append(inputQuery);
		this.queryString.append(" ");
		//コンソールでのクエリ確認用
		System.out.println(inputQuery);
		return this;
	}
	
	/**
	 * クエリオブジェクトにセットするためのパラメータを格納する。
	 * @param key 「:name」のような形で与えられるSQLへの埋め込み箇所を特定するための文字列　
	 * @param value　実際にセットされるパラメータ
	 * @return 連続してセットできるよう自身を返す
	 */
	public QueryBuilder setParam(String key, Object value) {
		this.paramNameList.add(key);
		this.paramValueList.add(value);
		
		return this;
	}
	
	/**
	 * クエリオブジェクトを作成する
	 * @param entityClass 結果セットが属するエンティティクラス
	 * @return 自身
	 */
	public QueryBuilder createQuery(Class<?> entityClass) {
		this.query = this.em.createNativeQuery(queryString.toString(), entityClass);
		//クエリ文字列の「:」プレースホルダが付与されたパラメータへ値を設定
		for (int i = 0; i < this.paramNameList.size(); i++) {
			this.query.setParameter(this.paramNameList.get(i), this.paramValueList.get(i));
		}

		//結果セット取得メソッドを続けて呼び出せるよう自身を返す
		return this;
	}
	
	/**
	 * クエリの実行結果のリストを取得する
	 * @return Entityのリスト
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> findResultList() {
		return this.query.getResultList();
	}
	
	/**
	 * クエリの実行結果の単一オブジェクトを取得する
	 * @return 結果セットがnull→null | 結果セットの単一オブジェクト
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> T findSingle() {
		//EMで標準で用意されているgetSingleResultメソッドは結果セットが空の場合例外を投げ、処理が複雑となるので、
		//getResultListの結果で分岐させる
		List<T> result = this.query.getResultList();
		if (result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
}
