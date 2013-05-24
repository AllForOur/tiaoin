var websiteManageList = function(){
	var grid ;
	var tree ;
	var initTreeData = "<root><item rel='site'><content><name><![CDATA[site]]></name></content></item></root>";
	
	return {
		init:function(){
			this.tabInit();
			this.listInit();
			this.dialogInit();
		},
		tabInit:function(){
			$("#tabs").tabs();
		},
		listInit:function(){
			grid = $("#list").jqGrid({
					   	url:'',
						datatype: "json",
					   	colNames:['Inv No','Date', 'Client', 'Amount','Tax','Total','Notes'],
					   	colModel:[
					   		{name:'id',index:'id', width:55},
					   		{name:'invdate',index:'invdate', width:90},
					   		{name:'name',index:'name asc, invdate', width:100},
					   		{name:'amount',index:'amount', width:80, align:"right"},
					   		{name:'tax',index:'tax', width:80, align:"right"},		
					   		{name:'total',index:'total', width:80,align:"right"},		
					   		{name:'note',index:'note', width:150, sortable:false}		
					   	],
					   	rowNum:10,
					   	rowList:[10,20,30],
					   	autowidth:true,
					   	pager: '#pager',
					   	toolbar: [true,"top"],
					   	sortname: 'id',
					    viewrecords: true,
					    sortorder: "desc",
					    caption:"网站配置列表"
					});
			
			$("#t_list").append("<input type='button' value='Click Me' style='height:20px;font-size:-3'/>");
			
			$("#list").navGrid('#pager',{edit:false,add:false,del:false,search:false})
			.navButtonAdd('#pager',{
				caption:"",
				buttonicon:"ui-icon-trash", 
				onClickButton: function(){ 
			    	
				},
				title:"删除",
			   	position:"first"
			})
			.navButtonAdd('#pager',{
				caption:"",
			   	buttonicon:"ui-icon-pencil", 
			   	onClickButton: function(){ 
			    	
			   	},
			   	title:"修改",
			   	position:"first"
			})
			.navButtonAdd('#pager',{
				caption:"",
			   	buttonicon:"ui-icon-plus", 
			   	onClickButton: function(){ 
			   		$("#addDialog").dialog("open");
			   	},
			   	title:"增加",
			   	position:"first"
			});
		},
		dialogInit:function(){
			//----------------------------------- 配置树初始化 --------------------------------------------------------------
			
			tree = $("#websiteConfigTree").jstree({
						"xml_data" : {"data":initTreeData,"xsl":"nest"},
				        "types" : {
				        	"max_children" : 1,
		    	            "valid_children" : [ "site" ],
		    	            "types" : {
		    	                "site" : {
		    	                    "icon" : {"image" : "images/icons/22.jpg"},
		    	                    "valid_children" : [ "headers","cookies","queueRules","targets","plugins" ],
		    	                    "max_children" : 5,
		    	                },
		    	                //------------ 一级子节点 ----------------------------------
		    	                "headers" : {
		    	                	"icon" : {"image" : "images/icons/10.jpg"},
		    	                    "valid_children" : [ "header" ]
		    	                },
		    	                
		    	                "cookies" : {
		    	                	"icon" : {"image" : "images/icons/11.jpg"},
		    	                    "valid_children" : [ "cookie" ]
		    	                },
		    	                
		    	                "queueRules" : {
		    	                	"icon" : {"image" : "images/icons/12.jpg"},
		    	                    "valid_children" : [ "rule" ]
		    	                },
		    	                
		    	                "targets" : {
		    	                	"icon" : {"image" : "images/icons/13.jpg"},
		    	                    "valid_children" : [ "target" ]
		    	                },
		    	                
		    	                "plugins" : {
		    	                	"icon" : {"image" : "images/icons/14.jpg"},
		    	                    "valid_children" : [ "plugin" ]
		    	                },
		    	                
		    	                //------------ 二级子节点 ----------------------------------
		    	                
		    	                "header" : {
		    	                	"icon" : {"image" : "images/icons/15.jpg"},
		    	                	"max_depth" : 0
		    	                },
		    	                
		    	                "cookie" : {
		    	                	"icon" : {"image" : "images/icons/16.jpg"},
		    	                	"max_depth" : 0
		    	                },
		    	                
		    	                "rule" : {
		    	                	"icon" : {"image" : "images/icons/17.jpg"},
		    	                	"max_depth" : 0
		    	                },
		    	                
		    	                "target" : {
		    	                	"icon" : {"image" : "images/icons/18.jpg"},
		    	                    "valid_children" : [ "urls","model"]
		    	                },
		    	                
		    	                "plugin" : {
		    	                	"icon" : {"image" : "images/icons/19.jpg"},
		    	                    "valid_children" : [ "extensions" ]
		    	                },
		    	                
		    	                //------------ 三级子节点 ----------------------------------
		    	            }
		    	        },
		    	        "ui" : {
		    	        	"select_limit" : 1
		    	        },
				        "themes" : {
		    	            "theme" : "apple",
		    	            "url" : "css/jstree/apple/style.css"
		    	        },
				        "plugins" : [ "themes", "xml_data","ui", "crrm","sort","contextmenu","types"]
					}).bind("select_node.jstree", function (event, data) {
						var selectType = data.rslt.obj.attr("rel");
						
						$("#websiteConfigAttr tbody[name="+selectType+"]").find("input").each(function(i){
							$(this).val(data.rslt.obj.attr($(this).attr("name")));
						});
						
						$("#websiteConfigAttr tbody[name="+selectType+"]").find("input").keyup(function(){
							data.rslt.obj.attr($(this).attr("name"),$(this).val());
						});
						
						$("#websiteConfigAttr tbody[name="+selectType+"]").show();
						$("#websiteConfigAttr tbody[name!="+selectType+"]").hide();
			        });
			//----------------------------------- 小菜单初始化 --------------------------------------------------------------
			
			$("#websiteConfigAddMenu").menu({
				select: function(event,ui){
					$("#websiteConfigTree").jstree("create",$("#websiteConfigTree").jstree("get_selected"),false,{"attr":{"rel":ui.item.text()},"data":ui.item.text()});
				}
			});
			
			//----------------------------------- 按钮初始化 --------------------------------------------------------------
			
			$("#websiteConfigTreeToolBar button:first").button({
				icons: {
					secondary: "ui-icon-triangle-1-s"
				}
			}).click(function(){
				var selectType = $("#websiteConfigTree").jstree("get_selected").attr("rel");
				
				if(selectType == null){
					alert("请选择一个节点");
					return false;
				}
				
				var menuContext = $(this).parent().next();
				
				if(menuContext.find("li[name="+selectType+"]").size() == 0){
					return false;
				}
				
				menuContext.find("li[name="+selectType+"]").show();
				menuContext.find("li[name!="+selectType+"]").hide();
				
				var menu = $(this).parent().next().show().position({
		            my: "left-3 top",
		            at: "left bottom",
		            of: this
		        });
				
				$(document).one("click",function(){
					menu.hide();
		        });
				
				return false;
			}).next().button().click(function(){
				var selectType = $("#websiteConfigTree").jstree("get_selected").attr("rel");
				
				if(selectType == null){
					alert("请选择一个节点");
					return false;
				}
				
				if(selectType == "site"){
					alert("根节点不可删除");
					return false;
				}
				
				$("#nodeDelConfirm").dialog("open");
			}).next().button().click(function(){
				/*var tempArr = new Array();
				var selectType = $("#websiteConfigTree").jstree("get_selected").attr("rel");
				$("#websiteConfigAttr tbody[name="+selectType+"]").find("input").each(function(i){
					tempArr.push($(this).attr("name"));
				});*/
				alert($("#websiteConfigTree").jstree("get_xml","nest",-1,new Array()));
			});
			
			/*$("#websiteConfigAttrSave").button().click(function(){
				
			});*/
			
			$("#websiteConfigTreeToolBar button").height(15).children().css({"padding-top":"0px"});
			
			//------------------------------------ 表单格式初始化 -----------------------------------------------------------
			
			$("#websiteConfigAttr input").uniform();
			
			$("#websiteConfigAttr tbody").hide();
			
			//------------------------------------ 对话框初始化 -------------------------------------------------------------
			
			$("#addDialog").dialog({
				autoOpen: false,
				modal: true,
				width:800,
				height:500
			});
			
			$("#nodeDelConfirm").dialog({
				autoOpen: false,
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"删除":function(){
						$("#websiteConfigTree").jstree("remove",$("#websiteConfigTree").jstree("get_selected"));
						$(this).dialog("close");
					},
					"取消":function(){
						$(this).dialog("close");
					}
				}
			});
		}
	};
}();