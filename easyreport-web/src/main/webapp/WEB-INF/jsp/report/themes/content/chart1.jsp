<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/report.jsp" %>
<script src="${ctxPath}/assets/vendor/echarts.min.js?v=${version}"></script>
<script src="${ctxPath}/assets/js/report/themes/default/chart.js?v=${version}"></script>
<div style="margin: 5px;">
    <form id="templateFrom" method="post">
        <table cellpadding="0" class="form-table" cellspacing="0" style="width: 99%;">
            <tr class="text_center blueside" style="height: 45px;">
                <td id="rpName" colspan="2" style="font-size: 24px;">${name}</td>
            </tr>
            <tr style="height: 40px;">
                <td style="text-align: left; vertical-align: middle; white-space: normal; word-wrap: break-word;">${formHtmlText}&nbsp;&nbsp;
                    <a id="btnGenarate" href="#" class="easyui-linkbutton" style="vertical-align: bottom"
                       data-options="iconCls:'icon-ok'">生成</a>
                    <input id="rpId" type="hidden" name="id" value="${id}"/>
                    <input id="rpName" type="hidden" name="name" value="${name}"/>
                    <input id="rpUid" type="hidden" name="uid" value="${uid}"/>
                </td>
            </tr>
            <c:if test="${not empty statColumHtmlText}">
                <tr style="height: 40px;">
                    <td colspan="2"
                        style="text-align: left; vertical-align: middle; white-space: normal; word-wrap: break-word;">${statColumHtmlText}</td>
                </tr>
            </c:if>
            <tr style="height: 40px;">
                <td colspan="2" style="text-align: left; vertical-align: middle;">
                    <label style="width: 120px;">选择维度:</label>${nonStatColumHtmlText}&nbsp;
                    横轴维度：<select id="xAxisDim"></select>&nbsp;
                    顺序：<select id="sortType">
                    <option value="asc">升序</option>
                    <option value="desc">降序</option>
                </select>&nbsp;
                    <a id="btnViewChart" href="#" class="easyui-linkbutton" style="vertical-align: bottom"
                       data-options="iconCls:'icon-preview'">查看</a>&nbsp;
                    <a id="btnAddChart" href="#" class="easyui-linkbutton" style="vertical-align: bottom"
                       data-options="iconCls:'icon-add'">对比</a>&nbsp;
                    <a id="btnToggleChart" href="#" class="easyui-linkbutton" style="vertical-align: bottom"
                       data-options="iconCls:'icon-toggle'">转换</a>&nbsp;
                    <a id="btnResetChart" href="#" class="easyui-linkbutton" style="vertical-align: bottom"
                       data-options="iconCls:'icon-reload'">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="chart1" style="height: 345px; border: 1px solid #ccc;">${message}</div>