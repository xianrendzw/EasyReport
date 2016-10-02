<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<div id="chart-report-div" style="margin: 5px;">
    <form id="chart-report-form" method="post">
        <table class="designer-table" style="width: 100%;padding: 6px;">
            <tr>
                <td style="text-align: left;">${formHtmlText}&nbsp;&nbsp;
                    <a id="btn-chart-generate" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-ok'">生成</a>
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
                    <label style="width: 120px;">选择维度:</label>${nonStatColumHtmlText}&nbsp;
                    横轴维度：<select id="xAxisDim"></select>&nbsp;
                    顺序：<select id="sortType">
                    <option value="asc">升序</option>
                    <option value="desc">降序</option>
                </select>&nbsp;
                    <a id="btnViewChart" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-preview'">查看</a>&nbsp;
                    <a id="btnAddChart" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-add'">对比</a>&nbsp;
                    <a id="btnToggleChart" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-toggle'">转换</a>&nbsp;
                    <a id="btnResetChart" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-reload'">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="legend1" style="height: 345px; border: 1px solid #ccc;">${message}</div>