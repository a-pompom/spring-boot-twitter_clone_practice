/*
 * 投稿ダイアログ呼び出し
 * クリック時に「dialogOpen」ディレクティブを発火させることで
 * ダイアログ側で表示フラグを検知することが可能となる
 * 
 */
let postAdd = new Vue({
	el: "#postAdd",
	
	methods: {
		/**
		 * ダイアログを表示するための処理
		 * イベントハブで発火させるディレクティブを指定
		 * ダイアログ側で$onで取得する
		 */
		openDialog() {
			this.$eventHub.$emit('dialogOpen');			
		}
	}
});