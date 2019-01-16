/**
 * @summary DSIS
 * @description 通用导出按钮功能
 * @version 1.0
 * @author runbai.chen
 * @copyright .
 *
 */

function exportSelected(exportFormId, exportGridId, listData, basePath) {
    var exportGrid = liger.get(exportGridId);
    var exportIndex = new Array();
    var exportHeader = {};
    var exportListData = {};
    var exportListSubData = {};
    var index = 0;
    for (var gridColIndex in exportGrid.options.columns) {
        if (!exportGrid.options.columns[gridColIndex]._hide) {
            if (exportGrid.options.columns[gridColIndex]._hide == false) {
                if (exportGrid.options.columns[gridColIndex].textField) {
                    exportIndex[index] = exportGrid.options.columns[gridColIndex].textField;
                    exportHeader[exportGrid.options.columns[gridColIndex].textField] = exportGrid.options.columns[gridColIndex].display;
                } else {
                    exportIndex[index] = exportGrid.options.columns[gridColIndex].columnname;
                    exportHeader[exportGrid.options.columns[gridColIndex].columnname] = exportGrid.options.columns[gridColIndex].display;
                }

                index++;
            }
        }
    }

    $.each(listData, function (hdrKey, hdrValue) {
        for (var index in hdrValue) {
            exportListSubData[hdrValue[index].value] = hdrValue[index].meaning;
        }

        exportListData[hdrKey] = exportListSubData;
        exportListSubData = {};
    });
    $("#" + exportGridId)
        .after(
            "<form id='exportForm' method='POST' target='_blank'><input id='exportContent' name='exportContent' style='visibility:hidden'/>"
            + "<input id='exportIndex' name='exportIndex' style='visibility:hidden'/>"
            + "<input id='exportListData' name='exportListData' style='visibility:hidden'/>"
            + "<input id='exportHeader' name='exportHeader' style='visibility:hidden'/></form>");
    var url = basePath + "/export/excel";
    $("#exportContent").val(JSON.stringify(exportGrid.getCheckedRows()));
    $("#exportIndex").val(JSON.stringify(exportIndex));
    $("#exportHeader").val(JSON.stringify(exportHeader));
    $("#exportListData").val(JSON.stringify(exportListData));
    $("#exportForm").attr("action", url).submit();
    $("#exportForm").remove();

};

function exportAll(exportFormId, exportGridId, listData, basePath) {
    var formData = window[exportFormId].getData();
    for (var key in formData) {
        if (!formData[key]) {
            delete formData[key]
        }
    }
    formData["page"] = 1;
    formData["pagesize"] = 10000;
    var exportGrid = liger.get(exportGridId);
    if (exportGrid) {
        $
            .ajax({
                type: "POST",
                url: exportGrid.options.url,
                data: formData,
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        var exportIndex = new Array();
                        var exportHeader = {};
                        var exportListData = {};
                        var exportListSubData = {};
                        var index = 0;
                        for (var gridColIndex in exportGrid.options.columns) {
                            if (!exportGrid.options.columns[gridColIndex]._hide) {
                                if (exportGrid.options.columns[gridColIndex]._hide == false) {
                                    if (exportGrid.options.columns[gridColIndex].textField) {
                                        exportIndex[index] = exportGrid.options.columns[gridColIndex].textField;
                                        exportHeader[exportGrid.options.columns[gridColIndex].textField] = exportGrid.options.columns[gridColIndex].display;
                                    } else {
                                        exportIndex[index] = exportGrid.options.columns[gridColIndex].columnname;
                                        exportHeader[exportGrid.options.columns[gridColIndex].columnname] = exportGrid.options.columns[gridColIndex].display;
                                    }

                                    index++;
                                }
                            }

                        }

                        $
                            .each(
                                listData,
                                function (hdrKey, hdrValue) {
                                    for (var index in hdrValue) {
                                        exportListSubData[hdrValue[index].value] = hdrValue[index].meaning;
                                    }

                                    exportListData[hdrKey] = exportListSubData;
                                    exportListSubData = {};
                                });

                        $("#" + exportGridId)
                            .after(
                                "<form id='exportForm' method='POST' target='_blank'><input id='exportContent' name='exportContent' style='visibility:hidden'/>"
                                + "<input id='exportIndex' name='exportIndex' style='visibility:hidden'/>"
                                + "<input id='exportListData' name='exportListData' style='visibility:hidden'/>"
                                + "<input id='exportHeader' name='exportHeader' style='visibility:hidden'/></form>");

                        var url = basePath + "/export/excel";
                        $("#exportContent").val(JSON.stringify(data.rows));
                        $("#exportIndex").val(JSON.stringify(exportIndex));

                        $("#exportHeader")
                            .val(JSON.stringify(exportHeader));
                        $("#exportListData").val(
                            JSON.stringify(exportListData));

                        $("#exportForm").attr("action", url).submit();
                        $("#exportForm").remove();
                    }
                }
            });
        delete formData["page"];
        delete formData["pagesize"];
    }
};

function exportDirectAll(exportFormId, exportGridId, listData, basePath) {
    var formData = window[exportFormId].getData();
    var formDataString = "";
    for (var key in formData) {
        if (!formData[key]) {
            delete formData[key]
        } else {
            formDataString = formDataString + key + "=" + formData[key] + "&";
        }
    }

    formDataString = formDataString + "page=1&pagesize=10000";
    var exportGrid = liger.get(exportGridId);

    if (exportGrid) {
        var exportIndex = new Array();
        var exportHeader = {};
        var exportListData = {};
        var exportListSubData = {};
        var index = 0;
        for (var gridColIndex in exportGrid.options.columns) {
            if (!exportGrid.options.columns[gridColIndex]._hide) {
                if (exportGrid.options.columns[gridColIndex]._hide == false) {
                    if (exportGrid.options.columns[gridColIndex].textField) {
                        exportIndex[index] = exportGrid.options.columns[gridColIndex].textField;
                        exportHeader[exportGrid.options.columns[gridColIndex].textField] = exportGrid.options.columns[gridColIndex].display;
                    } else {
                        exportIndex[index] = exportGrid.options.columns[gridColIndex].columnname;
                        exportHeader[exportGrid.options.columns[gridColIndex].columnname] = exportGrid.options.columns[gridColIndex].display;
                    }

                    index++;
                }
            }

        }

        $.each(
            listData,
            function (hdrKey, hdrValue) {
                for (var index in hdrValue) {
                    exportListSubData[hdrValue[index].value] = hdrValue[index].meaning;
                }

                exportListData[hdrKey] = exportListSubData;
                exportListSubData = {};
            });

        $("#" + exportGridId)
            .after(
                "<form id='exportForm' method='POST' target='_blank'>"
                + "<input id='exportIndex' name='exportIndex' style='visibility:hidden'/>"
                + "<input id='isExport2Excel' name='isExport2Excel' style='visibility:hidden'/>"
                + "<input id='exportListData' name='exportListData' style='visibility:hidden'/>"
                + "<input id='exportHeader' name='exportHeader' style='visibility:hidden'/></form>");
        var url = exportGrid.options.url + "?" + formDataString;
        $("#exportIndex").val(JSON.stringify(exportIndex));

        $("#exportHeader").val(JSON.stringify(exportHeader));
        $("#exportListData").val(JSON.stringify(exportListData));
        $("#isExport2Excel").val("Y");
        $("#exportForm").attr("action", url).submit();
        $("#exportForm").remove();

    }
};
