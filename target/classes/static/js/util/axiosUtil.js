import * as stringUtil from './stringUtil.js';
/**
 * 非同期通信用のユーティリティメソッド
 * axiosで非同期通信を行うためのパラメータを受け取り、
 * 各処理ごとの実際の非同期通信を行う。
 * @param {object} param axiosで非同期通信を行う際のパラメータ
 * token: CSRF用のトークン
 * path: 非同期通信のリクエスト先
 * modDOMId: 非同期通信のレスポンスで変更を加えるDOM要素
 * emitEvent: 非同期通信後に発火させたいイベント
 * vm: vueインスタンス eventHubを発火させるために利用
 * insertType: modDOMIdで変更を加える際の変更方法
 * bodyFormData: bodyFormData リクエストパラメータ
 */
export function connect(param) {
	//CSRFトークンをリクエストのヘッダに設定
	axios.defaults.headers['X-CSRF-TOKEN'] = param.token;
	
	axios({
		method: 'post',
		url: param.path,
		data: param.bodyFormData,
		})
		//.thenは非同期通信が完了したタイミングで実行される 更新処理もここへ記述
		.then((response) => {
			let modDOM = document.getElementById(param.modDOMId);
			switch(param.insertType) {
				//DOMを先頭に追加
				case "add":
					modDOM.insertAdjacentHTML("afterbegin", response.data);
					
					if (!stringUtil.isNullOrEmpty(param.emitEvent)) {
						
						param.vm.$eventHub.$emit(param.emitEvent);
					}
				break;
					
				//DOMを差し替え
				case "mod":
					modDOM.innerHTML = response.data;
					break;
				case "reload":
					//プロフィールは更新の影響範囲が広いので画面ごと更新
					// ユーザIDが変わった可能性があるので、ユーザIDでGETリクエスト
					window.location.href = param.href;
				default:
					break;
			}
		})
		//.catchは非同期通信に失敗したときに呼ばれる
		.catch((response)=> {
			console.log(response);
		});
};