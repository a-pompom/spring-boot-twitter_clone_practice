/**
 * プロフィール編集ダイアログ
 */
let dialog = new Vue({
	el: "#profEditWrapper",
	data: {
		showDialog: false,
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
	}
});