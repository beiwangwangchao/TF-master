/**
 * @summary DSIS
 * @description 通用导出按钮功能
 * @version 1.0
 * @author zhiwei.zhang
 * @copyright Copyright LKK Health Products Group Limited.
 *
 */

function exportSelectedAsPdf(exportFormId, exportGridId, listData, basePath) {
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
    var url = basePath + "/export/pdf";
    $("#exportContent").val(JSON.stringify(exportGrid.getCheckedRows()));
    $("#exportIndex").val(JSON.stringify(exportIndex));
    $("#exportHeader").val(JSON.stringify(exportHeader));
    $("#exportListData").val(JSON.stringify(exportListData));
    $("#exportForm").attr("action", url).submit();
    $("#exportForm").remove();
};

function exportDirectAllAsPdf(exportFormId, exportGridId, listData, basePath) {
    var formData = window[exportFormId].getData();
    var formDataString = "";
    $("#" + exportGridId)
        .after(
            "<form id='exportForm' method='POST' target='_blank'>"
            + "<input id='exportIndex' name='exportIndex' style='visibility:hidden'/>"
            + "<input id='isExport2Pdf' name='isExport2Pdf' style='visibility:hidden'/>"
            + "<input id='exportListData' name='exportListData' style='visibility:hidden'/>"
            + "<input id='exportHeader' name='exportHeader' style='visibility:hidden'/></form>");
    var exportForm = document.getElementById("exportForm");
    for (var key in formData) {
        if (!formData[key]) {
            delete formData[key]
        } else {
            var newInput = document.createElement("input");
            newInput.id = key;
            newInput.name = key;
            newInput.style = "visibility:hidden";
            newInput.value = formData[key];
            exportForm.appendChild(newInput);
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


        var url = exportGrid.options.url + "?" + formDataString;
        $("#exportIndex").val(JSON.stringify(exportIndex));

        $("#exportHeader").val(JSON.stringify(exportHeader));
        $("#exportListData").val(JSON.stringify(exportListData));
        $("#isExport2Pdf").val("Y");
        $("#exportForm").attr("action", url).submit();
        $("#exportForm").remove();

    }
};