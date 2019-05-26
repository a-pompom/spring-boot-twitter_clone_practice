/**
 * 投稿ダイアログ
 */
let dialog = new Vue({
	el: "#postDialogWrapper",
	data: {
		showDialog: false,
		token: ""
	},
	
	methods: {
		/**
		 * ダイアログを表示する
		 * $onでイベントが発火した際に
		 * 実行対象として指定される
		 */
		show() {			
			this.showDialog = true;
			document.getElementById('postDialog').show();
		},
		
		/**
		 * 投稿ボタンクリック時処理
		 * 非同期通信で新規投稿を登録し、レスポンスのHTMLを既存の投稿一覧へ追加することで
		 * 新規投稿がDB・画面へ反映される
		 */
		postSubmit(path) {
			axios.defaults.headers['X-CSRF-TOKEN'] = this.token;
			let bodyFormData = new FormData();
			bodyFormData.set('post.post', document.getElementById('post.post').value);
			let vm = this;
			
			axios({
				method: 'post',
				url: path,
				data: bodyFormData,
				})
				.then((response) => {
					//新規の投稿のHTMLは既存の投稿一覧のDOM要素の先頭にセット
					let postList = document.getElementById('postList').innerHTML;
					document.getElementById('postList').innerHTML = response.data + postList;
					this.closeDialog();
					
				})
				.catch((response)=> {
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
			document.getElementById('postDialog').close();
		}
	},
	mounted() {
		//$onは監視の為に利用するのでmountedフックで実行
		this.$eventHub.$on('dialogOpen', this.show);
		//csrfトークンを画面読み込みの段階で取得
		this.token = document.getElementsByName('_csrf')[0].value;
	}
});