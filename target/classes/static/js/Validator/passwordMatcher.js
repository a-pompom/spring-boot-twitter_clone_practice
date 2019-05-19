import * as stringUtil from '../util/stringUtil.js';

let passwordMatcher = new Vue({
	el: "#passwordMatcher",
	
	data: {
		password: "",
		confirmPassword: "",
		
		passwordMatched: false //パスワードが一致するか否かでdisabled属性、objDisabledクラスを操作
	},
	
	//値が変更される度に一致したか検証
	watch: {
		password() {
			this.passwordMatched = this.isPasswordMatched();
		},
		
		confirmPassword() {
			this.passwordMatched = this.isPasswordMatched();
		}
	},
	
	methods: {
		/**
		 * パスワード、確認用パスワードが一致したか検証する
		 * @returns {boolean} 一致→true 不一致→false
		 */
		isPasswordMatched() {
			//いずれかが空の場合は比較が不要となる
			if (stringUtil.isNullOrEmpty(this.password) || stringUtil.isNullOrEmpty(this.confirmPassword)) {
				return false;
			}
			
			if (this.password === this.confirmPassword) {
				return true;
			}
			return false;
		}
	}
});