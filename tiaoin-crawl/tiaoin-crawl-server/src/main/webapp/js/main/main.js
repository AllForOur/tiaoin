var main = function(){
	return {
		init:function(){
			this.layoutInit();
			this.leftAreaInit();
		},
		layoutInit:function(){
			$('body').layout({
				applyDemoStyles: true
			});
		},
		leftAreaInit:function(){
			$("#menu").accordion();
		}
	};
}();


$(function(){
	main.init();
	menu.init();
	$("#menu a:first").click();
});