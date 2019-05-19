Vue.directive('click-outside', {
  bind: function (el, binding, vnode) {
	  console.log('bind!!');
	  el.clickOutsideEvent = function (event) {
		  const buttonDOM = document.getElementById(binding.arg);
		  console.log(buttonDOM);
		  console.log(event.target);
		  console.log('equal ' + (event.target === buttonDOM));
		  console.log('el contains ' + (el == event.target || el.contains(event.target)));
	      // here I check that click was outside the el and his childrens
	      if (!(el == event.target || el.contains(event.target)) && !(event.target === buttonDOM) ) {
	        // and if it did, call method provided in attribute value
	        vnode.context[binding.expression](event);
	      }
	    };
	    document.body.addEventListener('click', el.clickOutsideEvent);
    //document.body.addEventListener('click', this.event)
  },
  unbind: function (el) {
	  console.log('unbind...');
    document.body.removeEventListener('click', el.clickOutsideEvent);
  },
});
