import * as axiosUtil from './util/axiosUtil.js';
import * as vmUtil from './util/vmUtil.js';

//メンション(共有・お気に入り)を非同期通信で行うためのVueインスタンス
let appMentionPost = new Vue({
	//リスト全体を対象とすることでループで描画されたDOM要素全てに適用することができる
	el: "#postList",
	data: {
		//CSRFトークン
		token: ""
	},
	methods: {
		/**
		 * メンション(共有・お気に入り)を非同期で行う
		 * @param {String} path        リクエスト先
		 * @param {Number} postIndex   対象の投稿ID
		 * @param {String} mentionType 共有orお気に入り
		 */
		doMention(path, postIndex, mentionType){
			let bodyFormData = new FormData();
			let axiosParam = {
					token: this.token,
					path: path,
					modDOMId: 'postMentionShare'+postIndex + mentionType,
					emitEvent: "",
					vm: this,
					insertType: "mod",
					bodyFormData: bodyFormData
			};
			axiosUtil.connect(axiosParam);
			
		},
		/**
		 * 新規投稿が行われたときに呼ばれる処理
		 * 新規投稿について個別にVueインスタンスを生成
		 */
		addInstance() {
			//理想的な流れはDOMを追加したthis.$$elを更新することだが、vueインスタンスを直接更新は無理そうなので
			//vueインスタンスの外部で新規投稿されたDOM用のvueインスタンスを作成してマウントさせることで対処することとした。
			vmUtil.createMentionVM('#' + this.$el.firstChild.id, this.doMention);
		}
		
	},
	
	mounted() {
		//csrfトークンを画面読み込みの段階で取得
		this.token = document.getElementsByName('_csrf')[0].value;
		//新規の投稿にもメンションイベントを紐づける為にイベントを監視
		this.$eventHub.$on('postAdded', this.addInstance);
	}
});

