<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp-views/frame/pageInclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="reporting,web reporting,easy report,big data,data analysis" />
<meta name="description" content="A simple and easy to use Web Report System ." />
<title>EasyReport - a simple and easy to use Web Report System</title>
<%@ include file="/WEB-INF/jsp-views/frame/pageHeader.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#tt').tabs({
			onLoad : function(panel) {
				var plugin = panel.panel('options').title;
				panel.find('textarea[name="code-' + plugin + '"]').each(function() {
					var data = $(this).val();
					data = data.replace(/(\r\n|\r|\n)/g, '\n');
					if (data.indexOf('\t') == 0) {
						data = data.replace(/^\t/, '');
						data = data.replace(/\n\t/g, '\n');
					}
					data = data.replace(/\t/g, '    ');
					var pre = $('<pre name="code" class="prettyprint linenums"></pre>').insertAfter(this);
					pre.text(data);
					$(this).remove();
				});
				prettyPrint();
			}
		});
	});
	function open1(plugin) {
		if ($('#tt').tabs('exists', plugin)) {
			$('#tt').tabs('select', plugin);
		} else {
			$('#tt').tabs('add', {
				title : plugin,
				href : plugin + '.php',
				closable : true,
				extractor : function(data) {
					data = $.fn.panel.defaults.extractor(data);
					var tmp = $('<div></div>').html(data);
					data = tmp.find('#content').html();
					tmp.remove();
					return data;
				}
			});
		}
	}
</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<div region="north" border="false" style="background: #666; text-align: left">
		<div style="color: #fff; font-size: 22px; font-weight: bold;">EasyReport</div> 
		<div style="color:#fff;text-decoration:none;">a simple and easy to use Web Report System!</div>
	</div>
	<div region="west" split="true" title="Plugins" style="width: 250px; padding: 5px;">
		<ul class="easyui-tree">
			<li iconCls="icon-base"><span>Base</span>
				<ul>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('parser')">parser</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('easyloader')">easyloader</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('draggable')">draggable</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('droppable')">droppable</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('resizable')">resizable</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('pagination')">pagination</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('searchbox')">searchbox</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('progressbar')">progressbar</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('tooltip')">tooltip</a></li>
				</ul></li>
			<li iconCls="icon-layout"><span>Layout</span>
				<ul>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('panel')">panel</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('tabs')">tabs</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('accordion')">accordion</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('layout')">layout</a></li>
				</ul></li>
			<li iconCls="icon-menu"><span>Menu and Button</span>
				<ul>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('menu')">menu</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('linkbutton')">linkbutton</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('menubutton')">menubutton</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('splitbutton')">splitbutton</a></li>
				</ul></li>
			<li iconCls="icon-form"><span>Form</span>
				<ul>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('form')">form</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('validatebox')">validatebox</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('textbox')">textbox</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('combo')">combo</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('combobox')">combobox</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('combotree')">combotree</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('combogrid')">combogrid</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('numberbox')">numberbox</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('datebox')">datebox</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('datetimebox')">datetimebox</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('datetimespinner')">datetimespinner</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('calendar')">calendar</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('spinner')">spinner</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('numberspinner')">numberspinner</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('timespinner')">timespinner</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('slider')">slider</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('filebox')">filebox</a></li>
				</ul></li>
			<li iconCls="icon-window"><span>Window</span>
				<ul>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('window')">window</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('dialog')">dialog</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('messager')">messager</a></li>
				</ul></li>
			<li iconCls="icon-datagrid"><span>DataGrid and Tree</span>
				<ul>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('datagrid')">datagrid</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('propertygrid')">propertygrid</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('tree')">tree</a></li>
					<li iconCls="icon-gears"><a class="e-link" href="#" onclick="open1('treegrid')">treegrid</a></li>
				</ul></li>
		</ul>
	</div>
	<div region="center">
		<div id="tt" class="easyui-tabs" fit="true" border="false" plain="true">
			<div title="使用指南" href=""></div>
		</div>
	</div>
</body>
</html>