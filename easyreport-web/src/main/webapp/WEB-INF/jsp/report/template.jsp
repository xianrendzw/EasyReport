<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${name}</title>
    <%@ include file="/WEB-INF/jsp-views/includes/header.jsp" %>
    <%@ include file="/WEB-INF/jsp-views/includes/form_scripts.jsp" %>
    <%@ include file="/WEB-INF/jsp-views/includes/report_scripts.jsp" %>
</head>
<body>
<div id="loading"
     style="position: absolute; z-index: 300; height: 120px; width: 284px; left: 50%; top: 50%; margin-left: -150px; margin-top: -80px; display: none;">
    <div style="position: absolute; z-index: 300; width: 270px; height: 90px; background-color: #FFFFFF; border: solid #000000 1px; font-size: 14px;">
        <div style="height: 26px; background: #f1f1f1; line-height: 26px; padding: 0px 3px 0px 3px; font-weight: bolder;">
            操作提示 <span id="Per"></span><span id="SCount" style="font-weight: normal"></span>
        </div>
        <div style="height: 64px; line-height: 150%; padding: 0px 3px 0px 3px;" align="center">
            <br/>
            <table>
                <tr>
                    <td valign="top"><img alt="loading" border="0"
                                          src="<%=request.getContextPath()%>/assets/modules/report/icons/loading.gif"/>
                    </td>
                    <td valign="middle" style="font-size: 14px;" id="loadingText">报表正在生成中, 请稍等...</td>
                </tr>
            </table>
            <br/>
        </div>
    </div>
    <div style="position: absolute; width: 270px; z-index: 299; left: 4px; top: 5px; background-color: #E8E8E8;"></div>
</div>
<div id="rpCommentTip" tabindex="-1" class="tooltip tooltip-bottom"
     style="display: none; width: 400px; right: 2px; top: 95px; z-index: 9001; display: none; background-color: rgb(102, 102, 102); border-color: rgb(102, 102, 102);">
    <div class="tooltip-content">
        <span style="color: #fff;" id="rpComment"><c:if test="${empty comment}">该报表没有说明信息！</c:if>${comment}</span>
    </div>
    <div class="tooltip-arrow-outer" style="border-bottom-color: rgb(102, 102, 102); left: 90%;"></div>
    <div class="tooltip-arrow" style="border-bottom-color: rgb(102, 102, 102); left: 90%;"></div>
</div>
<div style="margin: 5px;">
    <form id="templateFrom" method="post">
        <table cellpadding="0" class="form-table" cellspacing="0" style="width: 99%;">
            <tr class="text_center blueside" style="height: 45px;">
                <td colspan="2" id="rpTitle" style="font-size: 24px;">${name}</td>
            </tr>
            <tr style="height: 40px;">
                <td style="text-align: left; vertical-align: middle; white-space: normal;">${formHtmlText}
                    <input id="isMergeRow" type="checkbox" name="isMergeRow" checked="checked"/>合并左边相同维度行
                    <a id="btnGenarate" href="#" class="easyui-linkbutton" style="vertical-align: bottom"
                       data-options="iconCls:'icon-ok'">生成报表</a></td>
                <td style="text-align: right; vertical-align: middle; white-space: normal;">
                    <img style="cursor: pointer;" id="btnFullScreen" title="全屏显示"
                         src="<%=request.getContextPath()%>/assets/modules/report/icons/fullscreen_24.png"/>&nbsp;
                    <img style="cursor: pointer;" id="btnExportToExcel" title="导出到Excel"
                         src="<%=request.getContextPath()%>/assets/modules/report/icons/excel_24.png"/>&nbsp;
                    <img style="cursor: pointer;" id="btnShowChart" title="图表展示"
                         src="<%=request.getContextPath()%>/assets/modules/report/icons/chart_24.png"/>&nbsp;
                    <img style="cursor: pointer;" id="btnToggleSkins" title="切换报表样式"
                         src="<%=request.getContextPath()%>/assets/modules/report/icons/skins_24.png"/>&nbsp;
                    <img style="cursor: pointer;" id="btnShowComment" title="报表说明"
                         src="<%=request.getContextPath()%>/assets/modules/report/icons/comment_24.png"/>
                    <input id="rpId" type="hidden" name="id" value="${id}"/>
                    <input id="rpName" type="hidden" name="name" value="${name}"/>
                    <input id="rpUid" type="hidden" name="uid" value="${uid}"/>
                    <input id="isRowSpan" type="hidden" name="isRowSpan" value="true"/></td>
            </tr>
            <c:if test="${not empty statColumHtmlText}">
                <tr style="height: 40px;">
                    <td colspan="2"
                        style="text-align: left; vertical-align: middle; white-space: normal; word-wrap: break-word;">${statColumHtmlText}</td>
                </tr>
            </c:if>
        </table>
    </form>
</div>
<div style="margin: 5px;" id="reportDiv">${htmlTable}</div>
</body>
</html>