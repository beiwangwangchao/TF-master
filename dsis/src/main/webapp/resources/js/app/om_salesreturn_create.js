/**
 * @summary DSIS
 * @description 退货创建页面js方法集合
 * @version 1.0
 * @author houmin
 * @copyright Copyright LKK Health Products Group Limited.
 * 
 */
/**
 * 页面按钮初始化.
 */
function init() {
	
	$("#returnAdjust").ligerPanel({
        title : $l('type.com.lkkhpg.dsis.common.order.dto.return.manualadjustment'),
        width : '98%',
        height : 'auto'
    });
	
	printBtn =  $("#printBtn").ligerButton({
		text : $l('msg.info.order.salesreturn.print'),
		click : function() {
			f_printList();
		}
	});
	
	deleteBtn = $("#deleteBtn").ligerButton({
		text : $l('sys.hand.btn.delete'),
		click : function() {
			var returnId = $("#returnId").val();
			if (returnId) {
				f_deleteReturnInfo()
			} else {
				Hap.showError($l('msg.warning.dto.salesreturn.return.not_save'));
				return false;
			}
		}
	});
	
	saveBtn = $("#saveBtn").ligerButton({
		text : $l('sys.hand.btn.save'),
		click : function() {
			f_saveReturnInfo()
		}
	});
	
	submitBtn = $("#submitBtn").ligerButton({
		text : $l('sys.hand.btn.submit'),
		click : function() {
			f_submitReturnInfo()
		} 
	});
}

/**
 * 组织参数获取.
 * @param defaultSaleOrg 销售组织
 * @param dataObj 退货单详情
 */
function f_getOrgParam(defaultSaleOrg, dataObj) {
	$.ligerDialog.waitting($l('sys.hand.tip.processing'));
	/*组织参数获取*/
	var params = [{orgType:'SALES', orgId:defaultSaleOrg, 
		paramNames:
			['SPM.ENABLE_TAX','SPM.TAX_CODE','SPM.RETURN_TYPE','SPM.RETURN_LIMIT', "SPM.REFUND_WAY_OPTION", "SPM.CURRENCY", "SPM.ENABLE_CONVERSION_RATE"]}];
	$.ajax({
         url : _basePath+'/spm/orgParam/get',
         type : "POST",
         dataType : "json",
         contentType : "application/json",
         data : JSON2.stringify(params),
         success : function(json) {
          	 spmTaxFlag = json.rows[0].paramValues["SPM.ENABLE_TAX"];
          	 spmRefundMethod = json.rows[0].paramValues["SPM.REFUND_WAY_OPTION"];
          	 spmTaxCode = json.rows[0].paramValues["SPM.TAX_CODE"];
          	 spmReturnType = json.rows[0].paramValues["SPM.RETURN_TYPE"];
          	 spmReturnLimit = json.rows[0].paramValues["SPM.RETURN_LIMIT"];
          	 spmCurrentCode = json.rows[0].paramValues["SPM.CURRENCY"];
          	 spmEnableConversionRate = json.rows[0].paramValues["SPM.ENABLE_CONVERSION_RATE"];
          	 //退货日期校验
          	 if (dataObj && dataObj.rows[0].returnStatus == "NEW") {
          		 if (f_valiReturnDate(dataObj.rows[0].payDate)) {
          			 Hap.showError($l('msg.warning.dto.salesreturn.returndate.not_in_period'), function() {
          				 window.parent.f_removeTab("UPDATE_RETURN");
          			 }, {allowClose : false});
          			 return false;
          		 }
          	 }
          	 //获取精度
          	 $.getJSON(_basePath+"/spm/currency/query?currencyCode=" + spmCurrentCode[0],function(data) {
          		 if(data.success&&data.rows[0]){
          			 precision=data.rows[0].precision;
          		 }
          	 });
          	 //获取退货类型
          	 for (var i=0; i<returnTypeData.length; i++) {
          		 for(var j=0; j<spmReturnType.length; j++) {
          			 if (spmReturnType[j] == returnTypeData[i].value) {
          				 returnType.push(returnTypeData[i]);
          				 break;
          			 }
          		 }
          	 }
          	 liger.get("returnType").setData(returnType);
          	 var taxCode = spmTaxCode[0];
          	 if (!taxCode) {
          		 taxCode = 'NaN';
          	 }
          	 //获取税信息
          	var param = {taxCode : spmTaxCode[0],isActive:'Y'};
            $.getJSON(_basePath+"/spm/tax/queryForOrder",param,function(data) {
                if(data.success){
                	if(data.rows == null || data.rows.length == 0) {
        				taxPercent = 0;
        			} else {
                   	 	taxPercent = data.rows[0].taxPercent/100;
        			}
                	 var returnStatus = 'NEW';
                	 if (dataObj) {
                		 f_setReturnDetai(dataObj);
                		 returnStatus = dataObj.rows[0].returnStatus;
                		//add 针对不同的订单类型，获取不同的退款方式
                	 }
                	 f_getReturnMethod(spmRefundMethod);
                	 var returnId = $("#returnId").val();
                	 if (returnId) {
                		 //页面初始化 
                		 f_initPage(returnStatus);
                	 } else {
                		 liger.get("returnDate").setValue(new Date());
                		 liger.get("creationDate").setValue(new Date());
                	 }
                }
            });
            $.ligerDialog.closeWaitting();
         }
	});
}

/**
 * 获取退款方式.
 * (取RM.ORDER_TYPE_RETURN_METHOD中不同订单类型的退款方式和组织参数中退款方式的交集).
 * @param spmRefundMethod 组织参数中退款方式.
 * spmRefundMethod=null：表示选择订单后执行;
 * spmRefundMethod!=null：表示打开页面后执行
 * 
 */
function f_getReturnMethod(spmRefundMethod) {
	var methods = [];
	var orderType = $("#orderType").val();			//订单类型
	//退款方式
	if (spmRefundMethod) {
		for (var e = 0; e < refundWayData.length; e++) {
			for (var d = 0; d < spmRefundMethod.length; d++) {
				if (refundWayData[e].value == spmRefundMethod[d]) {
					refundMethodsData.push(refundWayData[e]);
					break;
				}
			}
		}
	}
	if (orderType) {
		if (refundMethod.length != 0) {
			refundMethod.splice(0, refundMethod.length);
		}
		for (var i=0; i<orderTypeRmMethod.length; i++) {
			if (orderTypeRmMethod[i].value == orderType) {
				methods = (orderTypeRmMethod[i].meaning).split(";");
				break;
			}
		}
		for (var e = 0; e < methods.length; e++) {
			for (var d = 0; d < refundMethodsData.length; d++) {
				if (methods[e] == refundMethodsData[d].value) {
					refundMethod.push(refundMethodsData[d]);
					break;
				}
			}
		}
	}
}

/**
 * 组织地址获取.
 * @param params 地址参数
 * @param isSalesOrgId 是否是销售组织
 */
function f_getLocation(params,isSalesOrgId) {
	$.ajax({
        url : _basePath+'/om/return/getLocation',
        type : "POST",
        data : params,
        success : function(json) {
        	if (isSalesOrgId) {
        		liger.get("salesOrgLocation").setValue(json.rows[0].addressReturn);
        	} else {
        		liger.get("invOrgLocation").setValue(json.rows[0].addressReturn);
        	}
        }
	});
}

/**
 * 选择订单编号后.
 * @param datas 订单详细信息.
 */
function f_onSelectedOrderNumber(datas) {
	//判断当前日期与销售订单的支付日期间隔是否在“退货期限”内
	var nowDate = new Date();
	var startTime = nowDate.getTime(); 
	var endTime = new Date(Date.parse((datas.data[0].payDate).replace(/-/g, "/"))).getTime(); 
	var days = Math.abs((startTime - endTime))/(1000*60*60*24);
	if (days > spmReturnLimit[0]) {//组织参数中退货期限
		Hap.showError($l("msg.warning.dto.salesreturn.returndate.not_in_period"));
		liger.get("orderNumber").setValue();
		liger.get("orderNumber").setText();
		submitBtn.setDisabled();
		return false;
	}
	f_reset();
	om_return_form.setData(datas.data[0]);
	//订单是积分兑换时，退货类型只能是退款,不可编辑
	var orderType = datas.data[0].orderType;
	if (orderType == "RDEM") {
		liger.get("returnType").setValue("REFD");
		liger.get("returnType").setDisabled();
	} else {
		liger.get("returnType").setEnabled()
	}
	$("[toolbarid='om_rtnRefund_gridAdd']").show();
	$("[toolbarid='om_rtnRefund_gridDel']").show();
	liger.get("returnStatus").setValue("NEW");
	liger.get("creationDate").setValue(new Date());
	liger.get("returnDate").setValue(new Date());
	liger.get("returnAdjustFlag").setValue('N');
	liger.get("shippingFeeFlag").setValue('N');
	// add运费逻辑
	var shippingFeeFlag = datas.data[0].shippingFeeFlag;
	$("#shippingFeeFlagBefore").val(shippingFeeFlag);
	var shippingFeeAmt = Number(datas.data[0].shippingFeeAmt);
	if (shippingFeeFlag != 'Y' && shippingFeeAmt) {
		liger.get("shippingFeeFlag").setEnabled();
	} else {
		liger.get("shippingFeeFlag").setDisabled();
	}
	//人工调整逻辑
	var returnAdjustFlag = datas.data[0].returnAdjustFlag;
	$("#rtnAdjustFlagBefore").val(returnAdjustFlag);
	var adjustAmt = Number(datas.data[0].adjustAmt);
	if (returnAdjustFlag != 'Y' && adjustAmt) {
		liger.get("returnAdjustFlag").setEnabled();
	} else {
		liger.get("returnAdjustFlag").setDisabled();
	}
	//获取退款方式
	f_getReturnMethod(null);
}

/**
 * 选择销售订单编号以后重置.
 */
function f_reset() {
	liger.get("returnType").setValue();
	liger.get("returnReason").setValue();
	liger.get("invOrgId").setValue('');
	liger.get("invOrgId").setText('');
	liger.get("returnPromotion").setValue();
	liger.get("invOrgLocation").setValue('');
	liger.get("remark").setValue('');
	$("#manualAdjustAmt").val('');
	$("#comments").val('');
	
	$("#returnAmtCount")[0].textContent = 0.00;
	$("#rtnPromotionSum")[0].textContent = 0.00;
	$("#adjustAmtCount")[0].textContent = 0.00;
	$("#shippingFeeAmtCount")[0].textContent = 0.00;
	$("#actualRtnAmt")[0].textContent = 0.00;
	$("#taxAmt")[0].textContent = 0.00;
	
	om_return_grid.loadData();
	om_rtnRefund_grid.loadData();
}

/**
 * 退货日期校验.
 * @param payDate_ 订单支付日期
 * @returns true-不在退货期间内；false-在退货期间内.
 */
function f_valiReturnDate(payDate_) {
	//当前日期
	var	nowDate = new Date();     //获取当前日期;
	//当前日期与销售订单的支付日期间隔是否在“退货期限”内
	var payDate = payDate_;
	var startTime = nowDate.getTime(); 
	var endTime = new Date(Date.parse(payDate.replace(/-/g, "/"))).getTime();
	var days = Math.abs((startTime - endTime))/(1000*60*60*24);
	if (days > spmReturnLimit[0]) {
		return true;
	}
	return false;
}

/**
 * 退货单行中选择商品后.
 * @param datas 订单行商品信息
 * @returns {Boolean}
 */
function f_onSelectedItem(datas) {
	var p = this.options;
	var om_return_gridData = om_return_grid.getData();
	for (var i=0; i<om_return_gridData.length; i++) {
		if (om_return_gridData[i].orderLineId == datas.data[0].lineId) {
			Hap.showError($l('msg.warning.dto.salesreturn.item.not_same'));
			return false;
		}
	}
	om_return_grid.updateRow(om_return_grid.getRow(rowIndex), {
		salesOrgId : currentSalesOrgId,				//销售区域ID
		orderLineId :datas.data[0].lineId,					//销售订单行ID
		countItemId : datas.data[0].countItemId,
		discountType : datas.data[0].discountType,
		discountValue : datas.data[0].discountValue,
		adjustmentAmount : datas.data[0].adjustmentAmount,
		unitOrigPrice : datas.data[0].unitOrigPrice,
		unitDiscountPrice : datas.data[0].unitDiscountPrice,
		pv : datas.data[0].pv,
		lotCtrlFlag : datas.data[0].lotCtrlFlag,
		countStockFlag : datas.data[0].countStockFlag,
		itemType : datas.data[0].itemType,
		countType : datas.data[0].countType,
		parentLineId : datas.data[0].parentLineId,
		quantity : null,
		disableTaxFlag : datas.data[0].disableTaxFlag,
		conversionRate : 1
	});
	p.host_grid.updateCell('itemName', datas.data[0].itemName, p.host_grid_row);
	p.host_grid.updateCell('countItemNumber', datas.data[0].countItemNumber, p.host_grid_row);
	p.host_grid.updateCell('countItemName', datas.data[0].countItemName, p.host_grid_row);
	p.host_grid.updateCell('unitSellingPrice', datas.data[0].unitSellingPrice, p.host_grid_row);
	p.host_grid.updateCell('unitRedeemPoint', datas.data[0].unitRedeemPoint, p.host_grid_row);
	p.host_grid.updateCell('uomName', datas.data[0].uomName, p.host_grid_row);
	p.host_grid.updateCell('orderQuantity', datas.data[0].quantity, p.host_grid_row);
	if (datas.data[0].outstandingQty-datas.data[0].returnQty < 0) {
		p.host_grid.updateCell('enabledRtnQuantity', 0, p.host_grid_row)
	} else {
		p.host_grid.updateCell('enabledRtnQuantity', datas.data[0].outstandingQty-datas.data[0].returnQty, p.host_grid_row);
	}
	//更新金额
	//f_amtCalculation();
	//更新商品包明细
	var p = this.options;
    tr = $(p.host_grid.getRowObj(p.host_grid_row));
    tr.next('tr.l-grid-detailpanel').remove();
    tr.find('.l-grid-row-cell-detailbtn').removeClass('l-open');
	//获取商品包明细
	if (datas.data[0].countType != 'PACKG') {
		if (datas.data[0].itemType == 'PACKG' || datas.data[0].itemType == 'VN' || datas.data[0].itemType == 'VY') {
			var obj = new Object();
			obj.headerId = $("#orderHeaderId").val();
			obj.defaultInvOrgId = liger.get("invOrgId").getValue();
			obj.parentLineId = datas.data[0].lineId;
			obj.returnId = $("#returnId").val();
			obj.returnStatus = liger.get("returnStatus").getValue();
			$.ajax({
				url : _basePath+"/om/sales/getHierarchyByParentLineId",
				type : "POST",
		        dataType : "json",
		        data : JSON2.stringify(obj),
		        contentType : "application/json",
		        success : function(json) {
		        	if (json.success) {
		        		for (var i=0; i<json.rows.length; i++) {
		        			json.rows[i].orderLineId = json.rows[i].lineId;
		        			json.rows[i].orderQuantity = json.rows[i].quantity;
		        			json.rows[i].quantity = 0;
		        			json.rows[i].enabledRtnQuantity = json.rows[i].outstandingQty - json.rows[i].returnQty;
		        			json.rows[i].returnInvFlag = p.host_grid_row.returnInvFlag;
		        			json.rows[i].salesRtnLots = null;
		        		}
		        		om_return_grid.updateRow(om_return_grid.getRow(rowIndex), {
		        			itemPkgDetail : json.rows
		        		});
		        	}
		        }
			});
		}
	}
}

/**
 * 行上商品代码改变后，清空行上信息.
 * @param data 行上商品代码单元格数据.
 */
function f_onChangeValue(data) {
	if (!data) {
		if (!om_return_grid.editor.editParm
				|| !om_return_grid.editor.editParm.record) {
			return;
		}
		var editorData = om_return_grid.editor.editParm.record;
		om_return_grid.updateRow(editorData, {
			itemName : null,
			countItemNumber : null,
			countItemName : null,
			unitSellingPrice : null,
			uomName : null,
			orderQuantity : null,
			enabledRtnQuantity : null,
			returnId : null,
			salesOrgId : null,
			orderLineId : null,
			itemId : null,
			pv : null,
			countItemId : null,
			quantity : null,
			returnPromotion : null,
			returnInvFlag : null,
			salesRtnLots : null,
			discountType : null,
			discountValue : null,
			unitOrigPrice : null,
			unitDiscountPrice : null,
			adjustmentAmount : null,
			lotCtrlFlag : null,
			countStockFlag : null,
			itemType : null,
			countType : null,
			conversionRate : 1,
			conversionAmt : null,
			itemPkgDetail : null
		});
		f_amtCalculation();
	}
}

/**
 * 商品包明细校验. 
 * @param requestData 行上商品包详细信息.
 * @returns {Boolean}
 */
function lineItemPkgVali(requestData) {
	var returnLines = requestData.salesRtnLines;
	var itemPkgDetails;
	for (var i=0; i<returnLines.length; i++) {
		if (returnLines[i].countType != 'PACKG') {
			if (returnLines[i].itemType == 'PACKG' || returnLines[i].itemType == 'VN' && returnLines[i].itemType == 'VY') {
				itemPkgDetails = returnLines[i].itemPkgDetail;
				for (var j=0; j<itemPkgDetails.length; j++) {
					//校验数量
					if (!itemPkgDetails[j].quantity) {
						Hap.showError($l('msg.warning.dto.salesreturn.returnquantity.larger_zero'));
						return false;
					}
					//商品包：计算库存=是，计算方式=单个商品
					if (returnLines[i].countStockFlag == 'Y' && returnLines[i].countType == 'IDV') {
						//订单明细中字段“计入库存”=否，商品包明细行“计入库存”也为“否”
						if (returnLines[i].returnInvFlag == 'N') {
							if (itemPkgDetails[j].returnInvFlag != 'N') {
								Hap.showError($l('msg.warning.dto.salesreturn.returninvflag.not_match_n'));
								return false;
							}
						}
						//订单明细中字段“计入库存”=是，商品包明细行“计入库存”也为“是”
						if (returnLines[i].returnInvFlag == 'Y') {
							if (itemPkgDetails[j].returnInvFlag != 'Y') {
								Hap.showError($l('msg.warning.dto.salesreturn.returninvflag.not_match_y'));
								return false;
							}
						}
						//商品明细中，计算库存=计算此商品，批次控制=是，则需进行批次选择
						if (itemPkgDetails[j].countStockFlag == 'O' && itemPkgDetails[j].lotCtrlFlag == 'Y') {
							if (itemPkgDetails[j].salesRtnLots == undefined) {
								Hap.showError('"'+returnLines[i].itemName+'"'+$l("msg.warning.dto.salesreturn.returnlot.not_selection"));
								return false;
							} else {
								// 校验批次添加数量与退货数据是否一致
								var salesRtnLots = itemPkgDetails[j].salesRtnLots;
								var returnQty = itemPkgDetails[j].quantity;	//商品退货数量
								var lotQty = 0;
								for (var k=0; k<salesRtnLots.length; k++) {
									if (salesRtnLots[k].__status != "delete") {
										lotQty +=  salesRtnLots[k].quantity;
									}
								}
								if (returnQty != lotQty) {
									Hap.showError('"'+returnLines[i].itemName+'"'+$l('msg.warning.dto.salesreturn.returnquantity.not_equals'));
									return false;
								}
							}
						}
					}
				}
			}
		}
	}
	return true;
}

/**
 * 校验.
 * @param requestData 请求参数数据
 * @returns {Boolean}
 */
function returnInfoVali(requestData) {
	//表单Form校验
	if (!Hap.validateForm(om_return_form)) {
		return false;
	}
//	if (!Hap.validateGrid(om_return_grid)) {
//		return false;
//	}
	if (!Hap.validateGrid(om_rtnRefund_grid)) {
		return false;
	}
	//退货单明细校验
	var line;
	for (var i=0; i<requestData.salesRtnLines.length; i++) {
		if (requestData.salesRtnLines[i].__status != "delete") {
			line = requestData.salesRtnLines[i];
			if (line.countStockFlag != 'N' && line.lotCtrlFlag == 'Y') {
				if (!line.salesRtnLots || line.salesRtnLots.length ==0) {
					Hap.showError($l('msg.error.dm.delivery_lot_can_not_be_empty'));
					return false;
				}
			}
			// 商品代码校验
			if (!line.itemId) {
				Hap.showError($l("msg.warning.dto.inventory.stockio.no_item_selected"));
				return false;
			}
			// 计入库存校验
			if (!line.returnInvFlag) {
				Hap.showError($l('msg.error.order.returninvflag_not_empty'));
				return false;
			}
			// 退货数量校验
			if (!line.quantity) {
				Hap.showError($l('msg.warning.dto.salesreturn.returnquantity.larger_zero'));
				return false;
			}
			if (line.enabledRtnQuantity < line.quantity) {
				Hap.showError($l('msg.warning.dto.salesreturn.returnquantity.not_match'));
				return false;
			}
			//折扣率校验
			if (!line.conversionRate && line.conversionRate != 0) {
				Hap.showError($l("msg.warning.dto.salesreturn.conversionrate.error"));
				return false;
			}
			//商品包明细校验
			if (!lineItemPkgVali(requestData)) {
				return false;
			}
		}
	}
	//退货类型校验
	var returnAmtCount = Number(requestData.amount).toFixed(precision);	//退款总额
	var returnAmt = Number(0);		//退货单的退款金额总和
	var adjustAmt = Number(requestData.adjustAmt).toFixed(precision);		//人工调整金额
	var shippingFeeAmt = Number(requestData.shippingFeeAmt).toFixed(precision);	//运费
	var returnPromotionSum = Number(requestData.returnPromotionSum).toFixed(precision);	//返还优惠总额
	var actualRtnAmt = Number(requestData.actualPayAmount).toFixed(precision);	//实退款
	if (requestData.returnType == "REFD" 
			&& actualRtnAmt != 0 && requestData.salesRtnRefundLines == null) {
		Hap.showError($l('msg.error.rm.refund_way_not_null'));
		return false;
	}
	var spNumber = 0;
	var spValue = Number(0);
	//退款行上金额总计与实退款校验
	if (requestData.returnType == "REFD" && actualRtnAmt != 0) {
		for (var i=0; i<requestData.salesRtnRefundLines.length; i++) {
			if (requestData.salesRtnRefundLines[i].__status != "delete") {
				//订单类型是积分兑换时
				if ("RDEM" == requestData.orderType && "SP" == requestData.salesRtnRefundLines[i].refundMethod) {
					spNumber ++; 
					spValue += Number(requestData.salesRtnRefundLines[i].refundAmount);
				}
				returnAmt += Number(requestData.salesRtnRefundLines[i].refundAmount);
			}
		}
		if (Number(returnAmt).toFixed(precision) != actualRtnAmt) {
			Hap.showError($l('msg.warning.dto.salesreturn.returnamount.not_match'));
			return false;
		}
		//订单类型是积分兑换时校验
		if ("RDEM" == requestData.orderType) {
			if (spNumber != 1 || Number(spValue).toFixed(precision) != (actualRtnAmt-adjustAmt)) {
				Hap.showError($l('msg.warning.dto.salesreturn.rtnmethod.not_match'));
				return false;
			}
		}
	}
	//实退款校验
	var valiAmt = Number(returnAmtCount)+Number(adjustAmt)+Number(shippingFeeAmt)-Number(returnPromotionSum)-Number(requestData.manualAdjustAmt);
	if (Number(valiAmt).toFixed(precision) != actualRtnAmt) {
		Hap.showError($l('msg.warning.dto.salesreturn.returnamount.not_match'));
		return false;
	}
	// 人工调整和运费返还校验.
	var valStr = f_shippingFeeAndAdjustVal(actualRtnAmt);
	if ("ADJ" == valStr) {
		Hap.showError($l('msg.error.rm.must_return_adjust_amt'));
		return false;
	} else if ("SHIP" == valStr) {
		Hap.showError($l('msg.error.rm.must_return_shipping_fee_amt'));
		return false;
	}
	
	return true;
}

/**
 * 人工调整和运费返还校验.
 * @param actualRtnAmt 当前退货单实退款
 * @returns {String} 校验类型
 * SHIP-运费必须返还；ADJ-人工调整必须得返还.
 * 
 */
function f_shippingFeeAndAdjustVal(actualRtnAmtVar) {
	//运费
	var shippingFeeFlag = liger.get("shippingFeeFlag").getValue();
	var shippingFlagBefore = $("#shippingFeeFlagBefore").val();					//是否已返还运费
	var shippingFeeAmt = Number($("#shippingFeeAmt").val());					//订单运费
	//人工调整
	var returnAdjustFlag = liger.get("returnAdjustFlag").getValue();
	var adjustFlagBefore = $("#rtnAdjustFlagBefore").val();						//是否已返还人工调整
	var actrualAdjustAmt = Number($("#adjustAmt").val());						//订单人工调整金额
	//可退货数量
	var enabledRtnQtySum = 0;
	var rtnQtySum = 0;
	var gridData = om_return_grid.getData();
	for (var i=0; i<gridData.length; i++) {
		enabledRtnQtySum += gridData[i].enabledRtnQuantity;
		rtnQtySum += gridData[i].quantity;
	}
	//是最后一单
	if (enabledRtnQtySum == rtnQtySum) {
		//1.有运费且是否返还人工调整选择的为"否"
		if (shippingFeeAmt != Number(0) && shippingFeeFlag == 'N') {
			if (shippingFlagBefore == 'N') {
				return "SHIP"
			}
		}
		//2.有人工调整且是否返还人工调整选择的为"否"
		if (actrualAdjustAmt != Number(0) && returnAdjustFlag == 'N') {
			if (adjustFlagBefore == 'N') {
				return "ADJ";
			}
		}
	}
	return "YES";
}


/**
 * 删除退货单.
 */
function f_deleteReturnInfo() {
	var array = new Array();
	array.push(f_getRequestData());
	$.ligerDialog.confirm($l('msg.warning.sys.delete_or_not'), function(yes) {
		if (yes) {
			$.ligerDialog.waitting($l('sys.hand.tip.processing'));
			Hap.ajax({
				url : _basePath + '/om/return/deleteSalesReturn',
				data : array,
				success : function(data) {
					if (data.success) {
						var option = new Object();
						option.json = data;
						Hap.defaultSuccessHandler(option);
						window.location = _basePath+"/om/om_salesreturn_create.html";
					}
				}
			});
			return true;
		} else {
			return false;
		}
	});
}

/**
 * 保存退货单.
 */
function f_saveReturnInfo() {
	if(om_return_grid.conn) return;
	if(!returnInfoVali(f_getRequestData())) {
		return false;
	}
	$.ligerDialog.waitting($l('sys.hand.tip.processing'));
	Hap.ajax({
		url : _basePath + '/om/return/saveRtnDetail',
		data : f_getRequestData(),
		success : function(data) {
			if (data.success) {
				var returnId = data.rows[0].returnId;
				var option = new Object();
				option.json = data;
				Hap.defaultSuccessHandler(option);
				window.location = _basePath+"/om/om_salesreturn_create.html?returnId=" + returnId;
			}
		}
	});
}

/**
 * 提交退货单.
 */
function f_submitReturnInfo() {
	if(!returnInfoVali(f_getRequestData())) {
		return false;
	}
	$.ligerDialog.confirm($l('msg.warning.sys.commit_or_not'), function(yes) {
		if (yes) {
			$.ligerDialog.waitting($l('sys.hand.tip.processing'));
			Hap.ajax({
				url : _basePath + '/om/return/submitRtnDetail',
				data : f_getRequestData(),
				success : function(data) {
					if (data.success) {
						var returnId = data.rows[0].returnId;
						var option = new Object();
						option.json = data;
						Hap.defaultSuccessHandler(option);
						window.location = _basePath+"/om/om_salesreturn_create.html?returnId=" + returnId;
					}
				}
			});
		}
	});
}

/**
 * 退货单提交后初始化页面.
 * @param returnStatus 退货单状态
 */
function f_initPage(returnStatus) {
	//设置Form表单不可编辑
	liger.get("returnType").setData(returnTypeData);
	liger.get("salesOrgId").set({readonly:true});
	liger.get("orderNumber").set({readonly:true});
	liger.get("returnType").set({readonly:true});
	liger.get("returnReason").set({readonly:true});
	liger.get("returnStatus").set({readonly:true});
	liger.get("returnAdjustFlag").set({readonly:true});
	liger.get("shippingFeeFlag").set({readonly:true});
	liger.get("invOrgId").set({readonly:true});
	liger.get("remark").set({readonly:true});
	if (returnStatus != "NEW") {
		$("#manualAdjustAmt").attr("disabled",true);
		$("#comments").attr("disabled",true);
		//掩藏按钮
		$("[toolbarid='om_return_lotGridAdd']").hide();
		$("[toolbarid='om_return_lotGridDel']").hide();
		$("[toolbarid='om_return_gridAdd']").hide();
		$("[toolbarid='om_return_gridDel']").hide();
		$("[toolbarid='om_rtnRefund_gridAdd']").hide();
		$("[toolbarid='om_rtnRefund_gridDel']").hide();
		submitBtn.setDisabled();
		saveBtn.setDisabled();
		deleteBtn.setDisabled();
	}
}

/**
 * 获取请求参数.
 * @returns 请求参数集合
 */
function f_getRequestData() {
	var requestData = om_return_form.getData();
	requestData.payDate = new Date(Date.parse($("#payDate").val()));
	//退货日期更新为当前日期
	requestData.returnDate = new Date(Date.parse(new Date()));
	requestData.amount = Number($("#returnAmtCount")[0].textContent).toFixed(precision);
	requestData.taxAmount = Number($("#taxAmt")[0].textContent).toFixed(precision);
	requestData.actualPayAmount = Number($("#actualRtnAmt")[0].textContent).toFixed(precision);
	requestData.returnPromotionSum = Number($("#rtnPromotionSum")[0].textContent).toFixed(precision);
	requestData.adjustAmt = Number($("#adjustAmtCount")[0].textContent).toFixed(precision);
	requestData.shippingFeeAmt = Number($("#shippingFeeAmtCount")[0].textContent).toFixed(precision);
	//人工调整
	requestData.manualAdjustAmt = Number($("#manualAdjustAmt").val()).toFixed(precision);
	requestData.comments = $("#comments").val();
	//退货行信息,包括批次信息
	var returnLines = om_return_grid.currentData.rows;
	requestData.salesRtnLines = returnLines;
	//退货支付行信息
	if (requestData.returnType == "REFD") {
		requestData.salesRtnRefundLines = om_rtnRefund_grid.currentData.rows;
	}
	return requestData;
}

/**
 * 商品行批次选择弹窗.
 * @param rowindex 退货单行索引
 * @param lineIdParam 订单行Id
 * @param itemIdParam 商品Id
 */
function invLotSelect(rowindex, lineIdParam, itemIdParam) {
	lineId = lineIdParam;
	itemId = itemIdParam;
	var returnStatus = liger.get("returnStatus").getValue();
	var returnType = liger.get("returnType").getValue();
	var om_return_lotGridData = om_return_grid.getRow(rowindex).salesRtnLots;
	//批次弹出框处理
	var lotObj = {};
	lotObj.rows = om_return_lotGridData;
	om_return_lotGrid.loadData(lotObj);
	$.ligerDialog.open({
		target : $("#om_return_lotGrid"),
        width : 900,
        height : 400,
        buttons: [{ 
        	text: $l('sys.hand.btn.ok'),
        	cls:'l-dialog-btn-highlight',
        	onclick: function (item, dialog) {
        		if (returnStatus != 'NEW') {
        			dialog.hide();
        			return true;
        		}
        		if (f_lotValidator(rowindex)) {
        			dialog.hide();
        		} else {
        			return false;
        		}
        	}
        },{
        	text: $l('sys.hand.btn.cancel'), 
        	onclick: function (item, dialog) {
        		dialog.hide();
        	}
        }]
    });
	om_return_lotGrid.reRender();
}

/**
 * 商品包明细中商品批次选择.
 * @param lineRowindex 退货行上索引
 * @param rowindex 商品包中行索引
 * @param lineIdParam 订单行ID
 * @param itemIdParam 商品包Id
 */
function invLotPkgSelect(lineRowindex, rowindex, lineIdParam, itemIdParam) {
	if (!om_return_grid.getRow(lineRowindex).quantity) {
		Hap.showError($l('msg.warning.dto.salesreturn.returnline_quantity_pre'));
		return false;
	}
	lineId = lineIdParam;
	itemId = itemIdParam;
	var returnStatus = liger.get("returnStatus").getValue();
	var om_return_lotPkgGridData = om_return_grid.getRow(lineRowindex).itemPkgDetail;
	//批次弹出框处理
	var lotObj = {};
	lotObj.rows = om_return_lotPkgGridData[rowindex].salesRtnLots;
	om_return_lotGrid.loadData(lotObj);
	$.ligerDialog.open({
		target : $("#om_return_lotGrid"),
        width : 900,
        height : 400,
        buttons: [{ 
        	text: $l('sys.hand.btn.ok'),
        	cls:'l-dialog-btn-highlight',
        	onclick: function (item, dialog) {
        		if (returnStatus != 'NEW') {
        			dialog.hide();
        			return true;
        		}
        		if (f_pkgLotValidator(lineRowindex,rowindex)) {
        			dialog.hide();
        		} else {
        			return false;
        		}
        	}
        },{
        	text: $l('sys.hand.btn.cancel'), 
        	onclick: function (item, dialog) {
        		dialog.hide();
        	}
        }]
    });
	om_return_lotGrid.reRender();
}

/**
 * 订单行批次退货数量校验.
 * @param rowindex 行索引.
 */
function f_lotValidator(rowindex) {
	var om_return_gridData = om_return_grid.getRow(rowindex);
	//var om_return_lotGridData = om_return_lotGrid.getData();
	var om_return_lotGridData = om_return_lotGrid.currentData.rows;
	if (!om_return_lotGridData) {
		return true;
	}
	var returnNum = 0;//退货数量
	var returnPromotion = 0;//返还优惠
	var unitDiscountPrice = om_return_gridData.unitDiscountPrice; //折扣
	if (!Hap.validateGrid(om_return_lotGrid)) {
		return false;
	}
	for (var i=0 ; i<om_return_lotGridData.length; i++) {
		if (om_return_lotGridData[i].__status != "delete") {
			if (!om_return_lotGridData[i].lotNumber) {
				Hap.showError($l('msg.warning.dto.salesreturn.returnline.lot_not_selection'));
				return false;
			}
			if (om_return_lotGridData[i].quantity && Number(om_return_lotGridData[i].quantity)>0) {
				returnNum += om_return_lotGridData[i].quantity;
			} else {
				Hap.showError($l('msg.warning.dto.salesreturn.returnquantity.larger_zero'));
				return false;
			}
		}
	}
	if (returnNum > om_return_gridData.enabledRtnQuantity) {
		Hap.showError($l('msg.warning.dto.salesreturn.returnquantity.not_match'));
		return false;
	}
	//（优惠总额/订单数量）*退货数量
	returnPromotion = Number(unitDiscountPrice*returnNum);
	//更新订单明细
	om_return_grid.updateRow(om_return_grid.getRow(rowindex), {
		quantity : returnNum,
		returnPromotion : returnPromotion,
		salesRtnLots : om_return_lotGridData
	});
	f_amtCalculation();
	return true;
}

/**
 * 商品包明细中商品批次校验.
 * @param lineRowindex 退货单行上索引
 * @param rowindex 商品包中行索引
 */
function f_pkgLotValidator(lineRowindex,rowindex) {
	var om_return_itemPkgDetail = om_return_grid.getRow(lineRowindex).itemPkgDetail;
	var om_return_pkgLotGridData = om_return_lotGrid.currentData.rows;
	if (!om_return_pkgLotGridData) {
		return true;
	}
	//批次中添加的退货数量
	var returnNum = 0;
	//需退货的数量
	var returnQuantity = om_return_itemPkgDetail[rowindex].quantity;
	if (!Hap.validateGrid(om_return_lotGrid)) {
		return false;
	}
	for (var i=0 ; i<om_return_pkgLotGridData.length; i++) {
		if (om_return_pkgLotGridData[i].__status != "delete") {
			if (!om_return_pkgLotGridData[i].lotNumber) {
				Hap.showError($l('msg.warning.dto.salesreturn.returnline.lot_not_selection'));
				return false;
			}
			if (om_return_pkgLotGridData[i].quantity && Number(om_return_pkgLotGridData[i].quantity)>0) {
				returnNum += om_return_pkgLotGridData[i].quantity;
			} else {
				Hap.showError($l('msg.warning.dto.salesreturn.returnquantity.larger_zero'));
				return false;
			}
		}
	}
	//数量需一致
	if (returnNum != returnQuantity) {
		Hap.showError($l('msg.warning.dto.salesreturn.returnquantity.not_equals'));
		return false;
	}
	//更新订单明细
	om_return_itemPkgDetail[rowindex].salesRtnLots = om_return_pkgLotGridData;
	om_return_grid.updateRow(om_return_grid.getRow(lineRowindex), {
		itemPkgDetail : om_return_itemPkgDetail
	});
	return true;
}

/**
 * 人工调整金额.
 * @param obj 人工调整金额对象.
 */
function f_returnAdjust(obj) {
	var isNum = /^-?\d*\.?\d*$/;
    if(!isNum.test(obj.value)){
        Hap.showError($l('msg.warning.dto.salesreturn.manualadjustamt.only_numeric'));
        $(obj).val("");
        f_amtCalculation();
        return;
    }
    if (obj.value < 0) {
    	Hap.showError($l('msg.warning.dto.salesreturn.manualadjustamt.larger_zero'));
    	$(obj).val("");
    	f_amtCalculation();
    	return;
    }
    f_amtCalculation();
}

/**
 * 计算金额.
 */
function f_amtCalculation() {
	var valiAmt = 0, returnLineAmt = 0, rtnPromotionSum = 0;
	var taxAmtSum = 0, actualRtnAmt = 0, unitSelling = 0;	//销售积分或销售价格
	var quantity = 0, conversionRate = 0, returnPvSum = 0;
	var disableTaxFlag;	//商品是否计税标识
	var om_return_gridData = om_return_grid.getData();
	var manualAdjustAmt = Number($("#manualAdjustAmt").val());			//退货单-人工调整
	var orderType = $("#orderType").val();								//订单类型
	var orderAmt = Number($("#orderAmt").val());						//订单金额
	var adjustAmt = Number($("#adjustAmt").val());						//订单人工调整金额
	var adjustAmtCount = Number($("#adjustAmtCount")[0].textContent);	//订单-人工调整金额
	var shippingFeeAmtCount = Number($("#shippingFeeAmtCount")[0].textContent); //订单运费
	var voucherAmt = Number($("#voucherAmt").val());					//订单优惠券优惠总金额
	var voucherOrderAmt = Number($("#voucherOrderAmt").val());			//优惠券额数
	var returnAmt = Number($("#returnAmt").val());						//历史退货金额
	//var amount = Number($("#amount").val());							//当前退货单退款金额
	var returnPromotionSum = Number($("#returnPromotionSum").val());	//已返还优惠金额
	valiAmt = Number(orderAmt)-Number(returnAmt)+Number(adjustAmt)-Number(voucherOrderAmt);
	for (var i=0; i<om_return_gridData.length; i++) {
		if (om_return_gridData[i].__status != "delete" && om_return_gridData[i].itemId) {
			returnPvSum += (om_return_gridData[i].quantity * om_return_gridData[i].pv);
			if ("RDEM" == orderType) {
				unitSelling = om_return_gridData[i].unitRedeemPoint;
			} else {
				unitSelling = om_return_gridData[i].unitSellingPrice;
			}
			quantity = om_return_gridData[i].quantity;
			conversionRate = om_return_gridData[i].conversionRate;
			conversionAmt = Number(quantity)*Number(conversionRate)*Number(unitSelling);
			om_return_grid.updateCell('conversionAmt', Number(conversionAmt).toFixed(precision), i);
			returnLineAmt += (Number(unitSelling)*Number(quantity)*Number(conversionRate));
			rtnPromotionSum += Number(om_return_gridData[i].returnPromotion);	//汇总行上优惠
			if (spmTaxFlag[0] == "Y") {//启用税
				disableTaxFlag = om_return_gridData[i].disableTaxFlag;
				if ("Y" != disableTaxFlag) {
					taxAmtSum += (Number(unitSelling) - (Number(unitSelling)/(1 + taxPercent)))*Number(quantity);
				}
			}
		}
	}
	//订单积分兑换时，不能使用优惠券
	if ("RDEM" != orderType) {
		if (valiAmt >= returnLineAmt) {
			liger.get("returnPromotion").setValue(0);
			actualRtnAmt = Number(returnLineAmt+adjustAmtCount);
		} else {
			if (returnPromotionSum > 0) {
				liger.get("returnPromotion").setValue(0);
				actualRtnAmt = Number(returnLineAmt+adjustAmtCount);
			} else {
				liger.get("returnPromotion").setValue(voucherAmt);
				rtnPromotionSum += voucherAmt;	//加上头上返还优惠
				actualRtnAmt = Number(returnLineAmt-rtnPromotionSum+adjustAmtCount);
			}
		}
	} else {
		actualRtnAmt = Number(returnLineAmt+adjustAmtCount);
	}
	actualRtnAmt = actualRtnAmt + shippingFeeAmtCount - manualAdjustAmt;
	liger.get("returnPvSum").setValue(returnPvSum);
	$("#returnAmtCount")[0].textContent = Number(returnLineAmt).toFixed(precision);
	$("#rtnPromotionSum")[0].textContent = Number(rtnPromotionSum).toFixed(precision);
	$("#actualRtnAmt")[0].textContent = Number(actualRtnAmt).toFixed(precision);
	if (spmTaxFlag[0] == "Y") {
		$("#taxAmt")[0].textContent = Number(taxAmtSum).toFixed(precision);
	} else {
		$("#taxAmt")[0].textContent = 0.00;
	}
	//订单类型是积分兑换时
	if ("RDEM" == orderType && om_rtnRefund_grid.rows.length > 0) {
		om_rtnRefund_grid.updateCell("refundAmount",Number(actualRtnAmt-adjustAmtCount).toFixed(precision),0);
	}
}

/**
 * 退货单详情金额计算.
 * @param data 退货单信息
 */
function f_amtCalculationDetail(data) {
	$("#returnAmtCount")[0].textContent = Number(data.rows[0].amount).toFixed(precision);
	$("#taxAmt")[0].textContent = Number(data.rows[0].taxAmount).toFixed(precision);
	//人工调整金额;
	var returnAdjustFlag = liger.get("returnAdjustFlag").getValue();
	if (returnAdjustFlag == 'Y') {
		$("#adjustAmtCount")[0].textContent = Number($("#adjustAmt").val());
	} else {
		$("#adjustAmtCount")[0].textContent = Number(0.00);
	}
	//运费
	var shippingFeeFlag = liger.get("shippingFeeFlag").getValue();
	if (shippingFeeFlag == 'Y') {
		$("#shippingFeeAmtCount")[0].textContent = Number($("#shippingFeeAmt").val());
	} else {
		$("#shippingFeeAmtCount")[0].textContent = Number(0.00);
	}
	//后期还需加上行上优惠值
	$("#rtnPromotionSum")[0].textContent = Number(data.rows[0].returnPromotion).toFixed(precision);
	$("#actualRtnAmt")[0].textContent = Number(data.rows[0].actualPayAmount).toFixed(precision);
}

/**
 * 退货单详情.
 * @param data 退货单详细信息.
 */
function f_setReturnDetai(data) {
	var conversionAmt = 0;
	om_return_form.setData(data.rows[0]);
	liger.get("invOrgId").setText(data.rows[0].invOrgName);
	//人工调整
	$("#manualAdjustAmt").val(data.rows[0].manualAdjustAmt);
	$("#comments").val(data.rows[0].comments);
	//商品包详情加载
	var salesRtnLines = data.rows[0].salesRtnLines;
	var itemPkgDetail, salesRtnLots, returnPvSum = 0;
	if (salesRtnLines.length > 0) {
		for (var i=0; i<salesRtnLines.length; i++) {
			returnPvSum += (salesRtnLines[i].pv * salesRtnLines[i].quantity);
			conversionAmt = Number(salesRtnLines[i].unitSellingPrice)
								* Number(salesRtnLines[i].quantity)
								* Number(salesRtnLines[i].conversionRate);
			salesRtnLines[i].conversionAmt = Number(conversionAmt).toFixed(precision);
			if (salesRtnLines[i].itemType == "PACKG" || salesRtnLines[i].itemType == "VN" || salesRtnLines[i].itemType == "VY") {
				if (salesRtnLines[i].countType == "IDV") {
					itemPkgDetail = salesRtnLines[i].itemPkgDetail;
					for (var j=0; j<itemPkgDetail.length; j++) {
						salesRtnLots = itemPkgDetail[j].salesRtnLots;
						for (var k=0; k<salesRtnLots.length; k++) {
							salesRtnLots[k].__status = "nochange";
						}
					}
				}
			}
		}
	}
	liger.get("returnPvSum").setValue(returnPvSum);
	//订单行信息(包括批次信息)加载
	var returnLineObj = {};
	returnLineObj.rows = data.rows[0].salesRtnLines;
	om_return_grid.loadData(returnLineObj);
	//退款信息加载
	var returnRefundObj = {};
	returnRefundObj.rows = data.rows[0].salesRtnRefundLines;
	om_rtnRefund_grid.loadData(returnRefundObj);
	var orderType = $("#orderType").val();
	if ("RDEM" == orderType && data.rows[0].returnAdjustFlag == "N") {
		$("[toolbarid='om_rtnRefund_gridAdd']").hide();
		$("[toolbarid='om_rtnRefund_gridDel']").hide();
	}
	//退款总额信息加载
	f_amtCalculationDetail(data);
}

/**
 * 获取商品包详情.
 * @param row 商品包行
 * @param detailPanel 行上样式
 * @param callback 回调函数
 */
function f_getItemPackageDetail(row, detailPanel, callback) {
	var objData = new Object();
	objData.rows = row.itemPkgDetail;
	//非商品包的直接返回
	if(row.itemType != 'PACKG' && row.itemType != 'VN' && row.itemType != 'VY'){
        return;
    }
	//商品包库存计算方式是商品包时
	if (row.countType == "PACKG") {
		return false;
	}
	var countType = row.countType;	//商品包库存计算方式
    var grid = document.createElement('div');
    $(detailPanel).append(grid);
    var om_returnpkg_detail = $(grid).css('margin', 10).ligerGrid({
        columns : [ {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesrtnline.itemid'),
            name : 'itemNumber',
            width : 100,
            type : 'text'
        }, {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesrtnline.itemname'),
            name : 'itemName',
            width : 100,
            type : 'text'
        }, {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesrtnline.countitemnumber'),
            name : 'countItemNumber',
            width : 100,
            type : 'text'
        }, {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesrtnline.countitemname'),
            name : 'countItemName',
            width : 100,
            type : 'text'
        }, {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesrtnline.orderquantity'),
			name : 'orderQuantity',
			width : 100,
			type : 'int'
		}, {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesrtnline.enabledrtnquantity'),
            name : 'enabledRtnQuantity',
            width : 100,
            type : 'int'
        }, {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesrtnline.quantity'),
            name : 'quantity',
            width : 100,
            type : 'int'
        }, {
			display : $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.returninvflag"),
			name : 'returnInvFlag',
			width : 100,
			editor : {
				type : 'select',
				data : yesOrNo,
				valueField : 'value',
				textField : 'meaning'
			},
			render : function(item) {
				for (var i in yesOrNo) {
					if (yesOrNo[i].value == item.returnInvFlag) {
						return yesOrNo[i].meaning;
					}
				}
			}
		}, {
        	display : $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots"),
			name : 'invLot',
			align : 'center',
			width : 100,
			render : function (rowData, rowindex, value, column) {
				if (rowData.lotCtrlFlag == "Y" && rowData.countStockFlag == "O") {
					return '<a href="javascript:void(0)	" '
						+ 'onclick="invLotPkgSelect('+row.__index+','+rowindex+','+rowData.orderLineId+','+rowData.itemId+')">'
						+ $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots")+'</a>';
				}
				if (rowData.lotCtrlFlag == "N" && rowData.countStockFlag == "O") {
					return $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots");
				}
				if (rowData.lotCtrlFlag == "N" && rowData.countStockFlag == "R") {
					return $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots");
				}
				if (rowData.lotCtrlFlag == "Y" && rowData.countStockFlag == "R") {
					return '<a href="javascript:void(0)	" '
						+ 'onclick="invLotPkgSelect('+row.__index+','+rowindex+','+rowData.orderLineId+','+rowData.itemId+')">'
						+ $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots")+'</a>';
				}
				if (rowData.countStockFlag == "N") {
					return $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots");
				}
				return $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots");
			}
        }],
        enabledEdit : false,
        isScroll : false,
        showToggleColBtn : false,
        width : 910,
        data : objData,
        showTitle : false,
        usePager : false,
        frozen : false
    });
}

/**
 * 商品包和商品分开处理.
 * 
 * @param rowData 行数据
 * @param rowindex 行索引
 * @param value 字段值
 * @param column 字段名称
 * @returns {String}
 */
function f_itemTypeRender(rowData, rowindex, value, column) {
	if (rowData.countType != "IDV") {
		if (rowData.countStockFlag == "N") {
			om_return_grid.setCellEditing(rowData, column, true);
			return $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots");
		}
		if (rowData.lotCtrlFlag == "N") {
			if (rowData.countStockFlag == "O" || rowData.countStockFlag == "R" || rowData.countStockFlag == "Y") {
				return $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots");
			}
		}
		if (rowData.lotCtrlFlag == "Y") {
			if(rowData.countStockFlag == "O" || rowData.countStockFlag == "R" || rowData.countStockFlag == "Y") {
				return '<a href="javascript:void(0)	" onclick="invLotSelect('+rowindex+','+rowData.orderLineId+','+rowData.itemId+')">'
				+$l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots")+'</a>';
			}
		}
	} else {//商品包时
		if (rowData.countStockFlag == 'Y' && rowData.countType == 'IDV') {
			//计算库存=是，计算方式=单个商品，订单明细—>不可编辑“批次”按钮
			return $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots");
		}
		if (rowData.countStockFlag == 'N') {
			//计算库存=否，订单明细—>字段“计算库存”必须为“否”，不可编辑“批次”按钮，“退货数量”可编辑
			return $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots");
		}
		if (rowData.countType == 'IDV' ) {
			return $l("type.com.lkkhpg.dsis.common.order.dto.salesrtnline.lots");
		}
	}
}

/**
 * 打印.
 */
function f_printList() {
	$.ligerDialog.open({
        title: $l("type.com.lkkhpg.dsis.report.type.choosing"),
        width:400,
        height:300,
        slide: false,
        url: _basePath + "/sys/report/sys_report_type_dialog.html",
        buttons: [{
            text: $l("sys.hand.btn.ok" ),
            onclick: function(i, d) {
            	var returnNumber = liger.get("returnNumber").getValue();
                var programCode  = "RPT-RETURN-ORDER-"+marketCode;
                if(!orderNumber) {
                	return;
                } else {
                    var form = d.frame.report_type_form;
                    var docType= form.getData().docType;
                    if (form.valid()) {
                        d.close();
                        window.open(_basePath+'/report/run?docType='+ docType +'&reportProgramCode='+programCode+'&returnNumber='+returnNumber);
                    }
                }
            }
        },
        {
            text: $l("sys.hand.btn.cancel"),
            onclick: function(i, d) {
                d.hide();
            }
        }]
    });
}