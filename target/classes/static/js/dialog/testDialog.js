
let testDialog = new Vue({
	el: "#testDialog",
	data: {
		showDialog: false,
	},
	
	methods: {
		toggle() {
			
			this.showDialog = true;
			document.getElementById('dialogTest2').show();
			
		},
		
		closeTestDialog() {
			if (!this.showDialog) {
				return;
			}
			console.log('close dialog event run');
			this.showDialog = false;
			document.getElementById('dialogTest2').close();
		}
	},
	mounted() {
		this.$eventHub.$on('testDialogOpen', this.toggle);
		
	},
	events: {
		closeEvent: function () {
	      console.log('close event called');
	      this.closeDialog();
	    }
	  }
});