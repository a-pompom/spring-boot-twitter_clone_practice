

let profileEdit = new Vue({
	el: "#testEdit",
	
	data: {
		showDialog: false
	},
	watch: {

	},
	methods: {
		test() {
			this.$eventHub.$emit('testDialogOpen');
			this.showDialog = true;
			
		}
	},
	mounted() {
		
	}
});