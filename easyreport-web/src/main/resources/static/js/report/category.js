$(function () {
    MetaDataCategory.init();
});

var MetaDataCategory = {
    init: function () {
        CategoryMVC.View.initControl();
        CategoryMVC.View.bindEvent();
        CategoryMVC.View.bindValidate();
        CategoryMVC.View.initData();
    }
};

var CategoryCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/report/category/'
};

var CategoryMVC = {
    URLs: {
        add: {
            url: CategoryCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: CategoryCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: CategoryCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: CategoryCommon.baseUrl + 'remove',
            method: 'POST'
        },
        move: {
            url: CategoryCommon.baseUrl + 'move',
            method: 'POST'
        },
        paste: {
            url: CategoryCommon.baseUrl + 'paste',
            method: 'POST'
        },
        getCategoryTree: {
            url: CategoryCommon.baseUrl + 'getCategoryTree',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            // 左边字典树
            $('#west').panel({
                tools: [{
                    iconCls: 'icon-search',
                    handler: CategoryMVC.Controller.openSearchDlg
                }, {
                    iconCls: 'icon-add',
                    handler: CategoryMVC.Controller.addRoot
                }, {
                    iconCls: 'icon-reload',
                    handler: CategoryMVC.Controller.reload
                }]
            });

            $('#category-tree').tree({
                checkbox: false,
                method: 'get',
                animate: true,
                dnd: true,
                onClick: function (node) {
                    $('#category-tree').tree('expand', node.target);
                },
                onSelect: function (node) {
                    MetaDataDesigner.listReports(node)
                },
                onDblClick: function (node) {
                    $('#category-tree').tree('expand', node.target);
                },
                onDrop: function (target, source) {
                    var targetNode = $('#category-tree').tree('getNode', target);
                    if (targetNode) {
                        $.post(CategoryMVC.URLs.move.url, {
                            sourceId: source.id,
                            targetId: targetNode.id,
                            sourcePid: source.attributes.parentId,
                            sourcePath: source.attributes.path
                        }, function (data) {
                            if (data.code) {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        }, 'json');
                    }
                },
                onContextMenu: function (e, node) {
                    e.preventDefault();
                    $('#category-tree').tree('select', node.target);
                    $('#category-tree-ctx-menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                    var copyNodeId = $('#copyNodeId').val();
                    var item = $('#category-tree-ctx-menu').menu('findItem', '粘贴');
                    $('#category-tree-ctx-menu').menu(copyNodeId == 0 ? 'disableItem' : 'enableItem', item.target);
                }
            });

            $('#category-tree-ctx-menu').menu({
                onClick: function (item) {
                    if (item.name == "addCate") {
                        return CategoryMVC.Controller.add();
                    }
                    if (item.name == "edit") {
                        return CategoryMVC.Controller.edit();
                    }
                    if (item.name == "remove") {
                        return CategoryMVC.Controller.remove();
                    }
                    if (item.name == "view") {
                        return CategoryMVC.Controller.view();
                    }
                    if (item.name == "search") {
                        return CategoryMVC.Controller.openSearchDlg();
                    }
                    if (item.name == "addReport") {
                        return MetaDataDesigner.addReport();
                    }
                    if (item.name == "refresh") {
                        return CategoryMVC.Controller.reload();
                    }
                    if (item.name == "copy") {
                        return CategoryMVC.Controller.copy();
                    }
                    if (item.name == "paste") {
                        return CategoryMVC.Controller.paste();
                    }
                }
            });

            // Dialog
            $('#category-dlg').dialog({
                closed: true,
                modal: true,
                width: 540,
                height: 280,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#category-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: CategoryMVC.Controller.save
                }]
            });

            $('#category-search-dlg').dialog({
                closed: true,
                modal: true,
                width: 650,
                height: 450,
                maximizable: true,
                iconCls: 'icon-search',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#category-search-dlg").dialog('close');
                    }
                }]
            });

            $('#category-search-result-grid').datagrid({
                method: 'get',
                fit: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                singleSelect: true,
                pageSize: 10,
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return EasyUIUtils.getEmptyDatagridRows();
                },
                columns: [[{
                    field: 'id',
                    title: '标识',
                    width: 50
                }, {
                    field: 'parentId',
                    title: '父标识',
                    hidden: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 150
                }, {
                    field: 'path',
                    title: '路径',
                    width: 150
                }]],
                onDblClickRow: function (index, row) {
                    CategoryMVC.Util.expandByPath(row.path);
                    var node = $('#category-tree').tree('find', row.id);
                    $('#category-tree').tree('select', node.target);
                }
            });
        },
        bindEvent: function () {
            $('#btn-category-search').bind('click', CategoryMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            CategoryMVC.Util.loadCategories();
        }
    },
    Controller: {
        addRoot: function () {
            var name = "无父级类别";
            var id = "0";
            CategoryMVC.Util.initAdd(id, name);
        },
        add: function () {
            var node = $('#category-tree').tree('getSelected');
            if (node) {
                var name = node.attributes.name;
                var id = node.id;
                CategoryMVC.Util.initAdd(id, name);
            } else {
                CategoryMVC.Controller.addRoot();
            }
        },
        edit: function () {
            var node = $('#category-tree').tree('getSelected');
            if (node) {
                var options = CategoryMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = node.attributes;
                options.title = '修改[' + options.data.name + ']分类';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一个报表分类!', 'info');
            }
        },
        view: function () {
            var node = $('#category-tree').tree('getSelected');
            if (node) {
                $('#category-view-dlg').dialog('open').dialog('center');
                var meta = node.attributes;
                for (var key in meta) {
                    $('#view-' + key).text(meta[key]);
                }
            } else {
                $.messager.alert('警告', '请选中一个报表分类!', 'info');
            }
        },
        remove: function () {
            var node = $('#category-tree').tree('getSelected');
            if (node) {
                var options = {
                    rows: [node.attributes],
                    url: CategoryMVC.URLs.remove.url,
                    data: {
                        id: node.id,
                        pid: node.attributes.parentId
                    },
                    callback: function (rows) {
                        CategoryMVC.Controller.reload(rows[0].parentId);
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一个报表分类!', 'info');
            }
        },
        openSearchDlg: function () {
            $('#category-search-dlg').dialog('open').dialog('center');
            EasyUIUtils.clearDatagrid('#category-search-result-grid');
        },
        find: function () {
            var keyword = $('#category-search-keyword').val();
            var url = CategoryMVC.URLs.list.url + '?fieldName=name&keyword=' + keyword;
            return EasyUIUtils.loadToDatagrid('#category-search-result-grid', url);
        },
        save: function () {
            var action = $('#modal-action').val();
            var actUrl = action === "edit" ? CategoryMVC.URLs.edit.url : CategoryMVC.URLs.add.url;
            var options = {
                dlgId: "#category-dlg",
                formId: "#category-form",
                url: actUrl,
                callback: function (node) {
                    CategoryMVC.Controller.reload(node ? node.id : null);
                }
            };
            EasyUIUtils.save(options);
        },
        reload: function (id) {
            CategoryMVC.Util.loadCategories(id);
        },
        copy: function () {
            var node = $('#category-tree').tree('getSelected');
            if (node) {
                $('#copyNodeId').val(node.id);
            } else {
                $.messager.alert('警告', '请选中一个报表分类!', 'info');
            }
        },
        paste: function () {
            var node = $('#category-tree').tree('getSelected');
            if (node) {
                $.post(CategoryMVC.URLs.paste.url, {
                    sourceId: $('#copyNodeId').val(),
                    targetId: node.id
                }, function (result) {
                    $('#copyNodeId').val(0);

                    if (result.code || !result.data.length) {
                        return $.messager.alert('错误', result.msg, 'error');
                    }

                    var nodeData = result.data;
                    var newNode = nodeData[0];
                    var pid = newNode.attributes.parentId;

                    // 如果是增加根节点
                    if (pid == "0") {
                        var roots = $('#category-tree').tree('getRoots');
                        if (roots && roots.length) {
                            return $('#category-tree').tree('insert', {
                                after: roots[roots.length - 1].target,
                                data: newNode
                            });
                        }
                        return CategoryMVC.Controller.reload();
                    }
                    var parentNode = $('#category-tree').tree('find', pid);
                    return $('#category-tree').tree('append', {
                        parent: parentNode.target,
                        data: nodeData
                    });
                }, 'json');
            } else {
                $.messager.alert('警告', '请选中一个报表分类!', 'info');
            }
        }
    },
    Util: {
        initAdd: function (id, name) {
            var options = CategoryMVC.Util.getOptions();
            options.title = '新增[' + name + ']的分类';
            EasyUIUtils.openAddDlg(options);

            $('#category-parentId').val(id);
            $('#category-parentName').text(name);
            $('#category-status').combobox('setValue', 1);
            $('#category-sequence').textbox('setValue', "10");
        },
        getOptions: function () {
            return {
                dlgId: '#category-dlg',
                formId: '#category-form',
                actId: '#modal-action',
                rowId: '#category-id',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        loadCategories: function (id) {
            $.getJSON(CategoryMVC.URLs.getCategoryTree.url, function (src) {
                if (!src.code) {
                    $('#category-tree').tree('loadData', src.data);
                    var node = null;
                    if (id) {
                        node = $('#category-tree').tree('find', id);
                        CategoryMVC.Util.expandByPath(node.attributes.path);
                    } else {
                        var roots = $('#category-tree').tree('getRoots');
                        if (roots && roots.length) {
                            node = roots[0];
                        }
                    }
                    if (node) {
                        $('#category-tree').tree('select', node.target);
                    }
                }
            });
        },
        expandByPath: function (path) {
            var ids = (path || "").split(',');
            for (var i = 0; i < ids.length; i++) {
                var node = $('#category-tree').tree('find', ids[i]);
                if (node) {
                    $('#category-tree').tree('expand', node.target);
                }
            }
        }
    }
};