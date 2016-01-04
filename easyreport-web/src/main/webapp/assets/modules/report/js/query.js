var designerPageRootUrl = WebAppRequest.getContextPath() + '/report/designer/';

$(function () {

    ReportQuery.init();

    $('#west').panel({
        tools: [ {
            iconCls: 'icon-reload',
            handler: ReportQuery.reloadTree
        }]
    });

    $('#reportTree').tree({
        checkbox: false,
        method: 'get',
        animate: true,
        dnd: true,
        url: designerPageRootUrl + 'listChildNodes',
        onLoadSuccess: function (node, data) {
            if (ReportQuery.treeNodePathIds && ReportQuery.treeNodePathIds.length > 0) {
                var id = ReportQuery.treeNodePathIds.shift();
                ReportQuery.selectAndExpandTreeNode(id);
            }
        },
        onClick: function (node) {
            if (node.attributes.flag == 1) {
                ReportQuery.previewInNewTab();
            }
        },
        onDblClick: function (node) {
            if (node.attributes.flag == 1) {
                ReportQuery.previewInNewTab();
            }
        },
        onContextMenu: function (e, node) {
            e.preventDefault();
            $('#reportTree').tree('select', node.target);
            $('#reportTreeCtxMenu').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }
    });

    $('#tabs').tabs({
        onContextMenu: function (e, title, index) {
            e.preventDefault();
            $('#tabsCtxMenu').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        },
        onSelect: ReportQuery.tabSelectedHandlder
    });


    $('#searchReportDlg').dialog({
        closed: true,
        modal: true,
        width: 600,
        height: 400,
        buttons: [{
            text: '关闭',
            iconCls: 'icon-no',
            handler: function () {
                $("#searchReportDlg").dialog('close');
            }
        }]
    });

});

var ReportQuery = function () {
};


ReportQuery.treeNodePathIds = [];

ReportQuery.init = function () {
};

//
// tabs相关操作
//
ReportQuery.getSelectedTabIndex = function () {
    var tab = $('#tabs').tabs('getSelected');
    return $('#tabs').tabs('getTabIndex', tab);
};

ReportQuery.selectTab = function (index) {
    $('#tabs').tabs('select', index);
};

ReportQuery.clearAllCheckedNode = function (target) {
    var nodes = $(target).tree('getChecked');
    if (nodes) {
        for (var i = 0; i < nodes.length; i++) {
            $(target).tree('uncheck', nodes[i].target);
        }
    }
};


ReportQuery.tabContextMenu = function (item) {
    if (item.name == "current") {
        return ReportQuery.closeCurrentTab();
    }
    if (item.name == "others") {
        return ReportQuery.closeOthersTab();
    }
    if (item.name == "all") {
        return ReportQuery.closeAllTab();
    }
    return;
};

ReportQuery.closeCurrentTab = function () {
    var tab = $('#tabs').tabs('getSelected');
    var options = tab.panel('options');
    if (options.closable) {
        $('#tabs').tabs('close', tab.panel('options').title);
    }
};

ReportQuery.closeOthersTab = function () {
    var currentTab = $('#tabs').tabs('getSelected');
    var currTitle = currentTab.panel('options').title;
    $('.tabs-inner span').each(function (i, n) {
        var title = $(n).text();
        var tab = $('#tabs').tabs('getTab', title);
        if (tab) {
            var options = tab.panel('options');
            if (title != currTitle && options.closable) {
                $('#tabs').tabs('close', title);
            }
        }
    });
};

ReportQuery.closeAllTab = function () {
    $('.tabs-inner span').each(function (i, n) {
        var title = $(n).text();
        var tab = $('#tabs').tabs('getTab', title);
        if (tab) {
            var options = tab.panel('options');
            if (options.closable) {
                $('#tabs').tabs('close', title);
            }
        }
    });
};

//
// 报表树相关操作
//
ReportQuery.treeContextMenu = function (item) {

    if (item.name == "search") {
        return ReportQuery.openSearchReportDlg();
    }
    if (item.name == "refresh") {
        return ReportQuery.reloadTree();
    }

    if (item.name == "info") {
        return ReportQuery.showProperties();
    }
    return;
};


ReportQuery.clickTreeNodeHandler = function (currNode) {
    if (node.attributes.flag == 1) {
        ReportQuery.previewInNewTab();
    }
};

ReportQuery.selectTreeNodeHandler = function (node) {
    $('#reportTree').tree('options').url = designerPageRootUrl + 'listChildNodes';
    $('#reportTree').tree('expand', node.target);

    ReportQuery.clearAllTab();
    if (node.attributes.flag != 1) {
        $('#reportAction').val('add');
        $('#reportPid').val(node.id);
        ReportQuery.initButtonStatus('add');
    } else {
        ReportQuery.loadAllTabData();
    }
};


ReportQuery.selectTreeNode = function (id) {
    var node = $('#reportTree').tree('find', id);
    if (node) {
        $('#reportTree').tree('select', node.target);
    }
};

ReportQuery.getTreeNodeById = function (id) {
    return $('#reportTree').tree('find', id);
};

ReportQuery.selectAndExpandTreeNode = function (id) {
    var node = $('#reportTree').tree('find', id);
    if (node) {
        $('#reportTree').tree('select', node.target);
        ReportQuery.selectTreeNodeHandler(node);
        var parentNode = $('#reportTree').tree('getParent', node.target);
        if (parentNode) {
            $('#reportTree').tree('expand', parentNode.target);
        }
    }
};


ReportQuery.getSelectedTreeNode = function () {
    return $('#reportTree').tree('getSelected');
};

ReportQuery.reloadTree = function () {
    $('#reportTree').tree('reload');
};


ReportQuery.previewInNewTab = function () {
    var node = ReportQuery.getSelectedTreeNode();
    if (node.attributes.flag == 1) {
        var title = node.attributes.name;
        if ($('#tabs').tabs('exists', title)) {
            $('#tabs').tabs('select', title);
            var tab = $('#tabs').tabs('getSelected');
            return tab.panel('refresh');
        }
        var previewUrl = WebAppRequest.getContextPath() + "/report/uid/" + node.attributes.uid;
        $('#tabs').tabs('add', {
            id: node.id,
            title: title,
            content: '<iframe scrolling="yes" frameborder="0" src="' + previewUrl + '" style="width:100%;height:100%;"></iframe>',
            closable: true
        });
    }
};

// 在tab里显示报表图表页
function showChart(title, id, uid) {
    if ($('#tabs').tabs('exists', title)) {
        $('#tabs').tabs('select', title);
        var tab = $('#tabs').tabs('getSelected');
        return tab.panel('refresh');
    }
    var url = WebAppRequest.getContextPath() + '/report/chart/' + uid;
    $('#tabs').tabs('add', {
        id: id,
        title: title,
        content: '<iframe scrolling="yes" frameborder="0" src="' + url + '" style="width:100%;height:100%;"></iframe>',
        closable: true
    });
}
