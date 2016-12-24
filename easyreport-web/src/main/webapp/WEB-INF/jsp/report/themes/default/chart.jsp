<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/vendor/echarts.min.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/report/themes/default/chart.js?v=${version}"></script>
    <title ng-app="i18n" ng-controller="translate">${name}-{{info.chart_reportchart}}</title>
</head>
<body ng-app="i18n" ng-controller="translate">
<div id="chart-report-div" style="margin: 5px;">
    <form id="chart-report-form" method="post">
        <table class="designer-table" style="width: 100%;padding: 6px;">
            <tr>
                <td style="text-align: left;">${formHtmlText}&nbsp;&nbsp;
                    <a id="btn-chart-generate" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-ok'">{{info.chart_generate}}</a>
                    <input id="chart-report-id" type="hidden" name="id" value="${id}"/>
                    <input id="chart-report-name" type="hidden" name="name" value="${name}"/>
                    <input id="chart-report-uid" type="hidden" name="uid" value="${uid}"/>
                </td>
            </tr>
            <c:if test="${not empty statColumHtmlText}">
                <tr id="chart-report-columns">
                    <td colspan="2">${statColumHtmlText}</td>
                </tr>
            </c:if>
            <tr>
                <td colspan="2">
                    <label style="width: 120px;">{{info.chart_dimension_sel}}</label>${nonStatColumHtmlText}&nbsp;
                   {{info.chart_x_dimission}}<select id="xAxisDim"></select>&nbsp;
                    {{info.chart_dimension_sequence}}<select id="sortType">
                    <option value="asc">{{info.chart_dimension_sequence_asc}}</option>
                    <option value="desc">{{info.chart_dimension_sequence_desc}}</option>
                </select>&nbsp;
                    <a id="btnViewChart" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-preview'">{{info.chart_preview}}</a>&nbsp;
                    <a id="btnAddChart" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-add'">{{info.chart_compare}}</a>&nbsp;
                    <a id="btnToggleChart" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-toggle'">{{info.chart_dimension_toggle}}</a>&nbsp;
                    <a id="btnResetChart" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-reload'">{{info.chart_reset}}</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="legend1" style="height: 345px; border: 1px solid #ccc;">${message}</div>
</body>
</html>