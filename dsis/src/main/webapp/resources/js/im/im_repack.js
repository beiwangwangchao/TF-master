//create by xiaohua
//跳轉到商品明細頁面 这个不需要改
//function a_click(data) {
//	var packageItemId = $.ligerui.get('packageItemId').getValue();
//	var organizationId = $.ligerui.get('organizationId').getValue();
//	if ($.ligerui.get("dialog1") == undefined) {
//		$.ligerDialog.open({
//			id : 'dialog1',
//			height : 400,
//			width : 600,
//			title : '<@spring.message "sys.hand.btn.query"/>',
//			url : 'im_repack_trx_items.html?packageItemId=' + data
//					+ '&organizationId=' + organizationId,
//			showMax : false,
//			showToggle : true,
//			showMin : false,
//			isResize : true,
//			slide : false,
//			center : 0,
//		});
//	} else {
//		$.ligerui.get("dialog1").close();
//		// 处理处一个最新的url
//		$.ligerDialog.open({
//			id : 'dialog1',
//			height : 400,
//			width : 600,
//			title : '<@spring.message "sys.hand.btn.query"/>',
//			url : 'im_repack_trx_items.html?packageItemId=' + data
//					+ '&organizationId=' + organizationId,
//			showMax : false,
//			showToggle : true,
//			showMin : false,
//			isResize : true,
//			slide : false,
//			center : 0,
//		});
//	}
//
//};

// 自定义验证(处理日起不能大于当前系统日期)
jQuery.validator.addMethod("trxDateLessSysDate",
		function(value, element, param) {
			var result;
			var temp;
			var sysDate = new Date();
			var trxDate = new Date(value.replace("T", ' ')).getTime();
			if (sysDate < trxDate) {
				result = false;
			} else {
				result = true;
			}
			return result;
		}, $l('msg.warning.inventory.trx_date_must_less_than_now_date'));
// map
function Map() {
	var struct = function(key, value) {
		this.key = key;
		this.value = value;
	}
	// 如果有就替换
	var put = function(key, value) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				this.arr[i].value = value;
				return;
			}
		}
		this.arr[this.arr.length] = new struct(key, value);
	}

	var get = function(key) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				return this.arr[i].value;
			}
		}
		return null;
	}

	var remove = function(key) {
		var v;
		for (var i = 0; i < this.arr.length; i++) {
			v = this.arr.pop();
			if (v.key === key) {
				continue;
			}
			this.arr.unshift(v);
		}
	}

	var size = function() {
		return this.arr.length;
	}

	var isEmpty = function() {
		return this.arr.length <= 0;
	}

	var iterator = function() {
		return this.arr;
	}

	var clear = function() {
		this.arr = [];
	}

	this.arr = new Array();

	this.get = get;

	this.put = put;

	this.remove = remove;

	this.size = size;

	this.isEmpty = isEmpty;

	this.iterator = iterator;

	this.clear = clear;
}

// 缓存当前编辑的批次行信息
var cacheRepackTrx = function(im_repack_trx_create_grid, map, itemDetail,
		isDelete) {
	im_repack_trx_create_grid.endEdit();
	// 获取 当前 分批次grid信息
	var gridData = im_repack_trx_create_grid.rows;
	// 将当前分批次信息保存至map
	if (itemDetail != null && gridData && gridData.length > 0) {
		var datas = new Array();
		for (var i = 0; i < gridData.length; i++) {
			datas.push(reRepackTrxDetail(gridData[i],gridData[i].itemPackageComponents));
		}
		map.put(itemDetail.itemId + '', datas);
		// 从grid中删除
		if (isDelete) {
			im_repack_trx_create_grid.endEdit();
			deleteRows(im_repack_trx_create_grid, gridData);
		}
	}else if(itemDetail != null){
		map.put(itemDetail.itemId + '', null);
	}
}

var deleteRows = function(liger, gridData) {
	if (gridData && gridData.length > 0) {
		for (var i = 0; i < gridData.length; i++) {
			liger.deleteRow(gridData[i]);
		}
	}
}

// 添加行
// liger 批次liger newData 从map里面获得在暂存的rows data指当前的商品明细
var addRows = function(liger, newData, data) {
	if (newData == null || newData.length <= 0 || liger == null) {
		return;
	}
	for (var i = 0; i < newData.length; i++) {
		var obj=reRepackTrxDetail(newData[i],data.itemId);
		liger.addRow(obj);
	}
	repackTrxStatusNoChanged(liger);
}

// 封装自己用的ajax
var ajaxTrx = function(opt) {
	$.ajax({
		url : opt.url || ' ',
		async : opt.async == false ? false : true,
		type : opt.type || 'POST',
		data : opt.data || null,
		dataType : opt.dataType || "json",
		contentType : opt.contentType
				|| "application/x-www-form-urlencoded;charset=UTF-8",
		success : function(data) {
			if (opt.success) {
				opt.success(data);
			}
		},
		error : function() {
			if (opt.error) {
				opt.error();
			}
		}
	});
}

// 验证属性是否为空,为空就删除
var valiAttr = function(data) {
	for ( var key in data) {
		if (!data[key] || data[key] == '') {
			delete data[key];
		}
	}
}

// 提交信息
var ajaxRepackTrx = function(opt) {
	var details = new Array();
	var arrays = opt.map.iterator();
	for (var i = 0; i < arrays.length; i++) {
		Array.prototype.push.apply(details, arrays[i].value);
	}
	opt.repackTrx.repackTrxDetails = details;
	var repackTrxs = new Array();
	repackTrxs.push(opt.repackTrx);
	// 验证属性
	valiAttr(opt.repackTrx);
	// 调式，以后要删除
	Hap.ajax({
		url : opt.url,
		data : repackTrxs,
		callback : function(json) {
			if (opt.callback && json) {
				opt.callback(json);
			}
		},
		success:function(json){
			if(opt.success && json){
				opt.success(json);
			}
		}
	});
}


var loadToMap=function(map,repackTrxDetails){
		// 清空数据
		map.clear();
		// 重新加入
		$.each(repackTrxDetails, function(i, v) {
			if (map.get(v.itemPackageComponents + '') == null) {
				map.put(v.itemPackageComponents + '', new Array(v));
			} else {
				var detail = map.get(v.itemPackageComponents + '');
				detail.push(v);
			}
		});
}

// 点击保存之后返回json 将json装入map，重新渲染
var reRenderData = function(map, rows, grid, itemDetail) {
	
	var rs = $.isArray(rows) ? rows : [], temp = null;
	// 如果有数据
	if (rs.length > 0) {
		if (rs[0].repackTrxDetails && rs[0].repackTrxDetails.length > 0) {
			// 清空数据
			map.clear();
			// 重新加入
			$.each(rs[0].repackTrxDetails, function(i, v) {
				if (map.get(v.itemPackageComponents + '') == null) {
					map.put(v.itemPackageComponents + '', new Array(v));
				} else {
					var detail = map.get(v.itemPackageComponents + '');
					detail.push(v);
				}
			});
		}
	}
	// 删除当前分批次信息
	if(itemDetail == null){
		return ;
	}
	deleteRows(grid, grid.rows);

	var data = map.get(itemDetail.itemId + '');
	if (data!=null && data.length > 0) {
		$.each(data, function(i, v) {
			if (v) {
				v = $.extend(v, {
					'__status' : 'nochanged'
				});
				grid.addRow(v);
			}
		});
		grid.reRender();
	}
}

var valiDataMap = function(inv_item_detail, map, operType) {
	var rs = $.isArray(inv_item_detail.rows) ? inv_item_detail.rows : [];
	for (var i = 0; i < rs.length; i++) {
		var datas = map.get(rs[i].itemId + '');
		if (datas != null && datas.length > 0) {
			var result = vali(rs[i], datas, operType);
			if (result != 'true') {
				return result;
			}
		}
	}
	return 'true';
}

// 验证当数据是否正确 allocateQty 数量 quantity数量
var vali = function(itemDetail, datas, operType) {
	// 合包 COMPS
	var lotQty = 0;
	if (datas != null && datas.length > 0) {
		for (var i = 0; i < datas.length; i++) {
			//组合包 可用库存量不能为0 或者为负数
			if(operType == 'COMPS'){
					if ((!(datas[i].allocateQty && datas[i].quantity)) || datas[i].allocateQty == '' || datas[i].allocateQty<=0 ) {
						return 'allocateQty_or_quantity_empty_error';
					}
			}else if(datas[i].allocateQty<=0 || datas[i].allocateQty == ''){//分解是 分配数里不能为0
				return 'allocateQty_or_quantity_empty_error';
			}
			// 组合的时候，同一批次下的数量不许大于库存量
			if (parseInt(datas[i].quantity) < parseInt(datas[i].allocateQty)
					&& operType == 'COMPS') {
				return 'lotnumber_not_allowed_out_of_stock';
			}
			lotQty += parseInt(datas[i].allocateQty);
		}
		// 同一商品Id下的所有批次数量之和必须等于商品明细的总数量
		if (itemDetail.allocateQty != lotQty) {
			return 'must_equal_with_sum_qty';
		}
	}
	return 'true';
}

var refreshItems=function(repackQty,inv_item_detail){
    var rows = inv_item_detail.rows;
    for (var i = 0; i < rows.length; i++) 
    {
        rows[i].allocateQty = inv_item_detail.getData()[i].quantity * repackQty;
        inv_item_detail.reRender({
            rowdata : rows[i] 
            });
        //更新当前选择对象总数量
    	if(itemDetail !=null && (itemDetail.__id == rows[i].__id)){
    		itemDetail.allocateQty=rows[i].allocateQty;
    	}
    }
}

//重新载入
	function reload(trxId,inv_item_form,inv_item_detail,map,readOnly,callback){
		if(trxId==null){
			return;
			}
		ajaxTrx({
		   url : $('#contextPath').val()+'/im/repackTrx/loadById', 
		   data : { "trxId" : trxId },
	     success : function (data){
	           if (data && data.status == 'success') {
	        	   inv_item_form.setData(data.repackTrx);
	        	   Hap.gridQuery({
                        form : inv_item_form, 
                        grid : inv_item_detail, 
                   				 });
	        	   if(data.onhandQuantity && data.onhandQuantity!=null){
	        				$.ligerui.get('quantity').set('value',data.onhandQuantity.quantity);
	        	   }else{
	        		   $.ligerui.get('quantity').set('value',0);
	        	   }
	        	  $("input[ligeruiid='packageItemId']").val(data.repackTrx.itemNumber);
	        	   //重新载入map
	        	  loadToMap(map,data.repackTrx.repackTrxDetails);
	        	  //将map里面的repackTrxDetails 载入属性invCountItemId
	           	if(data.repackTrx.status =='COMP'){
	           		readOnly();
	           	}
	           	if(callback){
	           		callback(data.repackTrx);
	           		}
	           }
	       }
	   });
	}
	
	var addCountItemProperties=function(inv_item_detail,map){
	 	 var itemDetails=inv_item_detail.rows;
	 	 if(itemDetails ==null || itemDetails.length <=0){
	 		 return ;
	 	 }
	 		for(var i=0;i<itemDetails.length;i++){
	 			var details=map.get(itemDetails[i].itemId+'');
	 			if(details!=null && details.length >0){
	 				 $.each(details,function(k,n){
	 					 n.countItemId=itemDetails[i].invCountItemId;
	 				 });
	 			}
	 		}
	}
	
	
	function reRepackTrxDetail(obj,itemId){
		var data={
				itemPackageComponents : itemId,
				lotNumber : obj.lotNumber,
				expiryDate : obj.expiryDate,
				quantity : obj.quantity,
				allocateQty : obj.allocateQty,
				remark : obj.remark
			};
		data = $.extend(data,obj,{
				'__status' : 'nochanged'
			});
		return data;
	}
	
	//	 将im_repack_trx_create_grid 有id的改称nochanged
	function repackTrxStatusNoChanged(im_repack_trx_create_grid){
		var rs = $.isArray(im_repack_trx_create_grid.rows) ? im_repack_trx_create_grid.rows : [];
        if(rs.length >0)
        $.each(rs,function(i,n){
            if(n['__status'] == 'delete') return true;
           var r = im_repack_trx_create_grid.records[n['__id']];
           if(r && r['trxId']) r = $.extend(r,n,{'__status':'nochanged'});
        })
        im_repack_trx_create_grid.deletedRows = [];
        im_repack_trx_create_grid.reRender();
    
	}
