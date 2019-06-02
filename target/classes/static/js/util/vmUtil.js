import * as axiosUtil from './axiosUtil.js';

/**
 * Vueインスタンスの操作に関するユーティリティメソッド
 * 動的に生成されたDOM要素へVueインスタンスをマウントする
 * ここでは新規投稿のDOMへメンション用のVueインスタンスをマウント
 * @param {Number} mountId マウント対象のDOMのID
 * @param {Func} mentionFunc メンションを行う為のメソッド
 */
export function createMentionVM(mountId, mentionFunc) {
	let newVM = new Vue({
		data:{
			token: ""
		},
		
		el: mountId,
		
		methods: {
			//メンションの非同期通信用処理
			doMention: mentionFunc
		},
		
		mounted() {
			this.token = document.getElementsByName('_csrf')[0].value;
		}
	});
}