<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">网站配置列表</a></li>
		</ul>
		<div id="tabs-1">
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	
	<div id="addDialog">
		<div style="float: left;width: 60%;height:100%;">
			<fieldset style="height:90%;overflow:auto;">
				<legend>配置文件树</legend>
				<div id="websiteConfigTree"></div>
				<div id="websiteConfigTreeToolBar" class="ui-widget-header ui-corner-all">
					<button>增加</button>
					<button>删除</button>
					<button>重置</button>
				</div>
				<ul id="websiteConfigAddMenu" style="display:none;width:100px;">
					<li name="site"><a href="#">headers</a></li>
					<li name="site"><a href="#">cookies</a></li>
					<li name="site"><a href="#">queueRules</a></li>
					<li name="site"><a href="#">targets</a></li>
					<li name="site"><a href="#">plugins</a></li>
					
					<li name="headers"><a href="#">header</a></li>
					<li name="cookies"><a href="#">cookie</a></li>
					<li name="queueRules"><a href="#">rule</a></li>
					<li name="targets"><a href="#">target</a></li>
					<li name="plugins"><a href="#">plugin</a></li>
					
				</ul>
			</fieldset>
		</div>
		<div id="nodeDelConfirm" style="overflow: hidden;">
			<p style="color: red;"><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>确定删除吗?</p>
		</div>
		<div style="float: left;width: 40%;height:100%;">
			<fieldset style="height:90%;overflow:auto;">
				<legend>属性列表</legend>
				<div id="websiteConfigAttr">
					<table style="margin: 0 auto;">
						<tbody name="site">
							<tr><td>name:</td><td><input name="name" /></td></tr>
							<tr><td>url:</td><td><input name="url" /></td></tr>
							<tr><td>reqDelay:</td><td><input name="reqDelay" /></td></tr>
							<tr><td>charset:</td><td><input name="charset" /></td></tr>
							<tr><td>enable:</td><td><input name="enable" /></td></tr>
							<tr><td>schedule:</td><td><input name="schedule" /></td></tr>
							<tr><td>thread:</td><td><input name="thread" /></td></tr>
							<tr><td>waitQueue:</td><td><input name="waitQueue" /></td></tr>
						</tbody>
						
						<tbody name="header">
							<tr><td>name:</td><td><input name="name" /></td></tr>
							<tr><td>value:</td><td><input name="value" /></td></tr>
						</tbody>
						
						<tbody name="cookie">
							<tr><td>name:</td><td><input name="name" /></td></tr>
							<tr><td>value:</td><td><input name="value" /></td></tr>
							<tr><td>host:</td><td><input name="host" /></td></tr>
							<tr><td>path:</td><td><input name="path" /></td></tr>
						</tbody>
					</table>
					<!-- <div align="center" style="width: 100%"><button id="websiteConfigAttrSave" >保存</button></div> -->
				</div>
			</fieldset>
		</div>
	</div>
</html>