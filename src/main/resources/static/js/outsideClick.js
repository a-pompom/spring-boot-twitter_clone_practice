/**
 * 画面外クリックを検知するカスタムディレクティブ
 * el: 画面内要素
 * binding: 与えられる引数 
 * 	arg: 画面内要素を表示する要素(ボタンなど)ID
 *  expression: 画面外クリック時に実行される処理
 * vnode: vueインスタンス contextへ処理を紐づけるために利用
 * 
 */
Vue.directive('click-outside', {
	bind: function (el, binding, vnode) {
		el.clickOutsideEvent = (event) => {
			//領域外の対象から除外する要素
			let excludeDOM = document.getElementById(binding.arg);
			//領域外がクリックされたか
			if (!(el == event.target || el.contains(event.target)) && !(excludeDOM.contains(event.target)) )  {
				//指定のイベントを紐付け
				vnode.context[binding.expression](event);
			}
		};
		document.body.addEventListener('click', el.clickOutsideEvent);
	},
	unbind: function (el) {
		document.body.removeEventListener('click', el.clickOutsideEvent);
	},
});
