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
		
		profEdit(path) {
			axios.defaults.headers['X-CSRF-TOKEN'] = this.token;
			let bodyFormData = new FormData();
			bodyFormData.set('dto.user.userName', document.getElementById('dto.user.userName').value);
			bodyFormData.set('dto.user.userNickname', document.getElementById('dto.user.userNickname').value);
			bodyFormData.set('dto.user.bio', document.getElementById('dto.user.bio').value);
			//bodyFormData.set('profileImage', document.getElementsByName('profileImage').files[0]);
			axios({
				method: 'post',
				url: path,
				data: bodyFormData,
				})
				.then((response) => {
					this.closeDialog();
					//プロフィールは更新の影響範囲が広いので画面ごと更新
					location.reload();
				})
				.catch((response) => {
					console.log(response);
				});
			
			
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