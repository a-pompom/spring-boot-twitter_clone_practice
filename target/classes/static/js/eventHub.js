/*イベントハブ
$eventHubにグローバルなvueインスタンスが紐づけられるので
$emitで発火させるディレクティブを指定し、
$onで発火したディレクティブを取得、及びイベントの登録を行うことで、
コンポーネントの親子関係に依らないイベントハンドリングが可能となる

ダイアログとボタンのようにコンポーネント同士が離れているもの、というような
例外的なものにのみ使うこと。
*/
const eventHub = {
  install: function (Vue, options) {
    Vue.prototype.$eventHub = new Vue()
  }
}
export default eventHub