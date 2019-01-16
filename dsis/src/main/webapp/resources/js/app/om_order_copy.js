/**
 * @summary DSIS
 * @description 订单复制js方法集合
 * @version 1.0
 * @author linxixin
 * @copyright Copyright LKK Health Products Group Limited.
 * 
 */

function clearForm(form) {

	$("#" + form + " :input").each(function(e) {
		 $(this).val("");
	});
}

function clearGrid(grid) {
	grid = liger.get(grid);
	if(grid.currentData != null && grid.currentData.length > 0) {
		var rows = grid.currentData.rows;
		rows.splice(0, rows.length);
		grid.reRender();
	}
}

function f_copy() {
	$("#copyModelDialog").parent().hide();
	clearForm("om_oc_form");
	clearGrid("linegrid");
	clearGrid("addressgrid");
	clearForm("delivery");
	clearForm("payAdjust");
	liger.get('copyModelDialog').trigger('buttonClick')
}


