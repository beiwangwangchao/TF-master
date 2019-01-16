function location_edit(contextpath, locationObj, callback) {
	$.ligerDialog.open({
		height : 300,
		width : 550,
		title : $l("sys.hand.title.addressinfo"),
		url : contextpath+'/spm/spm_location_edit.html', // 
		showMax : false,
		//showToggle : true,
		showMin : false,
		isResize : true,
		slide : false,
		data : locationObj,// document.activeElement.obj_location,
		buttons : [ {
			text : $l("sys.hand.btn.ok"),
			onclick : function(item, dialog) {
				var form = dialog.frame.spm_location_form_o;
				if (form.valid()) {
					var return_data = dialog.frame.init_return_data();
					dialog.close();
					callback(return_data);
				}
			}
		}, {
			text : $l("sys.hand.btn.cancel"),
			onclick : function(item, dialog) {
				dialog.close();
			}
		}

		]
	})
}

function gst_location_edit(contextpath, locationObj, callback) {
	$.ligerDialog.open({
		height : 300,
		width : 550,
		title : $l("sys.hand.title.addressinfo"),
		url : contextpath+'/mm/mm_gstlocation_edit.html', // 
		showMax : false,
		//showToggle : true,
		showMin : false,
		isResize : true,
		slide : false,
		data : locationObj,// document.activeElement.obj_location,
		buttons : [ {
			text : $l("sys.hand.btn.ok"),
			onclick : function(item, dialog) {
				var form = dialog.frame.spm_location_form_o;
				if (form.valid()) {
					var return_data = dialog.frame.init_return_data();
					dialog.close();
					callback(return_data);
				}
			}
		}, {
			text : $l("sys.hand.btn.cancel"),
			onclick : function(item, dialog) {
				dialog.close();
			}
		}

		]
	})
}

function om_location_edit(contextpath, locationObj, callback) {
	/*var params = {};
	params.activeElement = liger.get(document.activeElement.name);
	params.locationId = liger.get(document.activeElement.name).options.locationId;
	params.memberId = liger.get(document.activeElement.name).options.memberId;*/
	/*
	 * if(typeof(obj)=='object'){ console.log('location_edit方法接收的是string~~~')
	 * return null; }
	 */
	/*if (params.activeElement == null) {

		return null;
	}*/

	$.ligerDialog.open({
		height : 400,
		width : 550,
		title : $l("sys.hand.title.addressinfo"),
		url : contextpath+'/om/om_location_edit.html',
		showMax : false,
		//showToggle : true,
		showMin : false,
		isResize : true,
		slide : false,
		data : locationObj,// document.activeElement.obj_location,
		buttons : [ {
			text : $l("sys.hand.btn.ok"),
			onclick : function(item, dialog) {
				var form = dialog.frame.spm_location_form_o;
				if (form.valid()) {
					var return_data = dialog.frame.init_return_data();
					dialog.close();
					callback(return_data);
				}
			}
		}, {
			text : $l("sys.hand.btn.cancel"),
			onclick : function(item, dialog) {
				dialog.close();
			}
		}

		]
	})
}
