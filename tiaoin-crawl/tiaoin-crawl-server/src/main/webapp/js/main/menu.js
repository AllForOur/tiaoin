var menu = function(){
	return {
		init:function(){
			$("#menu a").click(function(){
				var baseName = $(this).attr("name");
				$.ajax({
					type: "POST",
					url: baseName+"Page.action",
					success: function(msg){
						$(".ui-layout-center").html(msg);
						eval(baseName+".init()");
					}
				});
			});
		}
	};
}();