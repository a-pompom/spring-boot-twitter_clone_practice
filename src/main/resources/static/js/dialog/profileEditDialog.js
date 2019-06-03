import * as axiosUtil from '../util/axiosUtil.js';

/**
 * プロフィール編集ダイアログ
 */
let dialog = new Vue({
	el: "#profEditWrapper",
	data: {
		showDialog: false,
		token: ""
	},
	
	methods: {
		/**
		 * ダイアログを表示する
		 * $onで対象のディレクティブ(編集ボタンクリック)が発火した際に
		 * 実行対象として指定される
		 */
		show() {			
			this.showDialog = true;
			document.getElementById('profEditDialog').show();
		},
		
		/**
		 * プロフィールを更新する(非同期)
		 */
		profEdit(path) {
			let bodyFormData = new FormData();
			bodyFormData.set('dto.user.userName', document.getElementById('dto.user.userName').value);
			bodyFormData.set('dto.user.userNickname', document.getElementById('dto.user.userNickname').value);
			bodyFormData.set('dto.user.bio', document.getElementById('dto.user.bio').value);
			//画像が指定されなかった場合はリクエストに載せない
			if (document.getElementById('file-upload').files[0]) {
				bodyFormData.set('profileImage', document.getElementById('file-upload').files[0]);
			}
			
			let axiosParam = {
					token: this.token,
					path: path,
					modDOMId: "",
					emitEvent: "",
					vm: this,
					insertType: "reload",
					bodyFormData: bodyFormData,
					href: document.getElementById('dto.user.userName').value
			};
			axiosUtil.connect(axiosParam);
		},
		
		/**
		 * ダイアログを閉じる
		 * outsideClickディレクティブでダイアログの領域外が
		 * クリックされると発火する。
		 */
		closeDialog() {
			if (!this.showDialog) {
				return;
			}
			
			this.showDialog = false;
			document.getElementById('profEditDialog').close();
		}
	},
	mounted() {
		//$onは監視の為に利用するのでmountedフックで実行
		this.$eventHub.$on('dialogOpen', this.show);
		this.token = document.getElementsByName('_csrf')[0].value;
	}
});