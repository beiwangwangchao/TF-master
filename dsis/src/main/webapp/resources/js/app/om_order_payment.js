/**
 * @summary DSIS
 * @description 订单支付js方法集合
 * @version 1.0
 * @author houmin
 * @copyright Copyright LKK Health Products Group Limited.
 * 
 */

var saveEditButton;
var queryBankCard;
/**
 * 页面初始化
 */
function init() {
	
	saveEditButton = $("#saveOrderPayment").ligerButton({
		click : saveOrderPayment,
		text : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.orderpaymentsave')
	});
	saveEditButton.setEnabled();
	//上一步
	$("#returnBtn").ligerButton({
		click : f_openConfirm,
		text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.return')
	});
	
	$("#cancelOrder").ligerButton({
		click : cancelOrder,
		text : $l('type.com.lkkhpg.dsis.common.dto.order.cancel')
	});
	
	var orderDetailBtn = $("#orderDetail").ligerButton({
		click : toOrderDetail,
		text : $l('type.com.lkkhpg.dsis.common.order.dto.queryorder.orderinfo')
	});
	
	var queryBankCard = $("#bankCard").ligerButton({
		click : checkBankCard,
		text : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.querybank')
	});
	
}

/**
 * 初始化订单编号Form.
 */
function om_InitOrderNumberForm() {
	/* 订单编号 */
	var options = {
		inputWidth : 150,
		space : 10,
		fields : [ {
			name : 'salesOrgCode',
			type : 'hidden'
		}, 
		{
			display : $l('type.com.lkkhpg.dsis.common.order.dto.queryorder.ordernumber'),
			type : 'text',
			name : 'orderNumber',
			newline : false,
			readonly : true
		},{
			display : $l('type.com.lkkhpg.dsis.common.bonus.dto.bonusadjust.membercode'),
			type : 'text',
			name : 'memberCode',
			newline : false,
			readonly : true
		},{
			display : $l('type.com.lkkhpg.dsis.common.member.dto.memberdetails.membername'),
			type : 'text',
			name : 'memberName',
			newline : false,
			readonly : true
		},{
			display : $l('type.com.lkkhpg.dsis.common.order.dto.shortcut_operation.currentmoneypv'),
			type : 'text',
			name : 'currentPv',
			newline : false,
			readonly : true
		},{
			display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.orderpv'),
			type : 'text',
			name : 'orderPv',
			newline : false,
			readonly : true
		}]
	};
	return options;
}

/**
 * 组织参数获取.(从订单上取销售组织ID)
 * @param salesOrgId 订单上销售组织Id
 */
function orgParamGet(salesOrgId) {
	/*组织参数*/
    var param =  [{
    	orgId : salesOrgId, 
    	orgType : 'SALES', 
    	paramNames : ['SPM.PAY_BY_RB', 'SPM.PAYMENT_METHOD']
    }];
    $.ajax({
       async: false,
       url : _basePath+'/spm/orgParam/get',
       type : "POST",
       dataType : "json",
       contentType : "application/json",
       data : JSON2.stringify(param),
       success : function(json) {
           var data=json.rows;
           for(var i=0 ; i < data.length ; i++){
        	   if (data[i]["paramValues"]["SPM.PAYMENT_METHOD"]) {
        		   payMethodOrgParam= data[i]["paramValues"]["SPM.PAYMENT_METHOD"];
        	   }
               if(data[i]["paramValues"]["SPM.PAY_BY_RB"]){
                   if(!data[i]["paramValues"]["SPM.PAY_BY_RB"][0]||data[i]["paramValues"]["SPM.PAY_BY_RB"][0]=="N"){
                       var p = -1;
                       //1.支付方式-判断组织参数是否能RB支付.
                      for (var j=0; j < paymentMethodData.length;j++){
                           if(paymentMethodData[j].value == 'RBPAY'){
                               p = j;
                               break;
                           }
                      }
                      if(p != -1){
                          paymentMethodData.splice(p,1);
                      }
                   }
               }
           }
           
       }
   });
}

/**
 * 显示订单包含商品列表.
 */
function om_showItemListGrid() {
	var options = {
		height : 250,
		width : "99%",
		usePager: false,
		enabledEdit : true,
		columns : om_getItemColumns(false, false),
		detail: { onShowDetail: f_getItemPackageDetail,height:'auto' },
		onBeforeShowData : function(data) {
			f_valiOrderStatus(data.rows[0].orderStatus);
			//积分支付时
			if('RDEM' == data.rows[0].orderType) {
				grid1.set({columns : om_getItemColumns(false,true)});
			}
			//获取组织参数
			orgParamGet(data.rows[0].salesOrgId);
		},
		onAfterShowData : om_itemAfterShowData
	};
	options.url = _basePath+'/orderPayment/queryCommodityList?orderHeaderId='+orderHeaderId; //根据订单头ID查询
	return options;
}

/**
 * 商品详细列获取
 * 
 * @param isTax
 *            是否含税
 * @param isPoint
 *            是否是积分支付
 */
function om_getItemColumns(isTax, isPoint) {
	/* 产品销售类型 */
	var salesType = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.itemsalestype'),/* 产品销售类型 */
		name : 'itemSalesType',
		isSort : false,
		type : 'text',
		align : 'center',
		isSort : false,
		render : function(data) {
			if (!data) {
				return;
			}
			for ( var productSalesType in productSalesTypeData) {
				if (data.itemSalesType == productSalesTypeData[productSalesType].value) {
					return productSalesTypeData[productSalesType].meaning;
				}
			}
		}
	};
	/* 价格或所需积分栏 */
	var unit = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.unitsellingprice'),
		name : "unitSellingPrice",
		type : "int",
		align : "center",
		isSort : false
	};
	/* 金额或积分总计栏 */
	var amount = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.moneysubtotal'),/* 金额小计 */
		name : "amount",
		type : "float",
		align : "center",
		isSort : false
	};
	/* 是否是积分支付 */
	if (isPoint) {
		unit = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.unitredeempoint'),/* 兑换积分 */
			name : "unitRedeemPoint",
			type : "float",
			align : "center",
			isSort : false
		};
		amount = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.redeempoint'),/* 积分小计 */
			name : "redeemPoint",
			type : "float",
			align : "center",
			isSort : false,
			render : function(data) {
                if (!data) {
                    return;
                }
                if(!data.unitRedeemPoint){
                    data.unitRedeemPoint=0;
                }
                return data.unitRedeemPoint*data.quantity;
            }
		};
	}

	/* 汇总列表清单 */
	var columns = [
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.invorgname'), /*库存组织*/
				name : 'defaultInvOrgId',
				width : 120,
				type : 'text',
				align : 'center',
				isSort : false,
				render : function(data) {
	                if (!data) {
	                    return;
	                }
	                for ( var i in invOrgs) {
	                    if (data.defaultInvOrgId == invOrgs[i].invOrgId) {
	                        return invOrgs[i].name;
	                    }
	                }
	            }
			},
	        salesType,/*产品销售类型*/
	        {
				display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.itemid'),/*货号*/
				name : "itemNumber",
				type : "text",
				align : "center",
				isSort : false
			},{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.itemname'),/* 商品名称 */
				name : "itemName",
				width : 200,
				type : "text",
				align : "center",
				isSort : false
			},{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.pvvalue'),/* pv值 */
				name : "pv",
				type : 'float',
				align : "center",
				isSort : false
			},{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.voucherid'),/*优惠方式*/
				name : "voucherName",
				type : "text",
				align : "center",
				isSort : false
			},
				unit,/*价格或所需积分*/
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.quantity'),/* 数量 */
				name : "quantity",
				type : "int",
				align : "center",
				isSort : false
			},{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.favorablesum'),/* 优惠总额 */
				name : "favorableSum",
				type : "float",
				align : "center",
				isSort : false
			},{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.pvsubtotal'),/* PV小计 */
				name : "PVSubtotal",
				type : 'float',
				align : "center",
				isSort : false,
				render : function(rowData) {
					if (!rowData) {
                        return;
                    }
                    if(!rowData.pv){
                    	rowData.pv=0;
                    }
					return rowData.pv*rowData.quantity;
				}
			},
			amount
	];
	/*判断是否含税*/
	if (isTax) {
		var tax = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.itemtax'), /*税*/
			name : "tax",
			type : "float",
			align : "center",
			isSort : false
		}
		columns.splice(6, 0, tax);
	}
	return columns;
}

/**
 * 商品列表信息数据显示后.
 * @returns
 */
function om_itemAfterShowData(data) {
	//订单编号
	orderNumber = data.rows[0].orderNumber;
	tab1form.setData({
		salesOrgCode : data.rows[0].salesOrgCode,
		orderNumber : orderNumber
	});
	//获得PV
	var pvSum = 0;
	//总积分
	var redeemPointSum = 0;
	//会员id
	memberId = data.rows[0].memberId;
	//销售区域ID
	var salesOrgId = data.rows[0].salesOrgId;
	//是否货到付款标志(Y-是)
	codFlag = data.rows[0].codFlag;
	//根据销售渠道设置默认的支付方式
	for (var i=0; i<defaultPayMethod.length; i++) {
		if (data.rows[0].channel == defaultPayMethod[i].value) {
			defaultPayMth = defaultPayMethod[i].meaning;
		}
	}
	//订单用户创建类型
	var userType = data.rows[0].userType;
	//订单类型
	var orderType = data.rows[0].orderType;
	//订单税额
	var tax_amt = Number(data.rows[0].taxAmt).toFixed(2);
	//税后总金额
	var afterTaxValue = Number(data.rows[0].orderAmt).toFixed(2);
	//税前总金额
	var beforeTaxValue = afterTaxValue - tax_amt;
	//订单优惠总额
	var discountValueSum = data.rows[0].discountValueSum;
	if (discountValueSum == null) {
		discountValueSum = 0;
	}
	//支付调整金额
	var adjustmentAmountCount = data.rows[0].adjustmentAmountCount;
	if (adjustmentAmountCount == null) {
		adjustmentAmountCount = 0;
	}
	//运费
	var shippingFee = data.rows[0].shippingFee;
	if (shippingFee == null) {
		shippingFee =0;
	}
	//实付款
	var actual_pay = Number(data.rows[0].actrualPayAmt).toFixed(2);
	//会员现有积分
	var salesPoint = data.rows[0].salesPoint;
	//计算总数
	for(var i =0 ; i<data.rows.length; i++) {
		pvSum += (data.rows[i].pv*data.rows[i].quantity);
		redeemPointSum += data.rows[i].redeemPoint;
		if(data.rows[i].itemSalesType == 'EXCH') {
		    curEB += data.rows[i].amount;
		}
	}
	//账号余额
	if(data.rows[0].memAccountingBalances){
		var memAccountingBalances = data.rows[0].memAccountingBalances;
		for(var i = 0; i < memAccountingBalances.length; i ++){
			if('EB' == memAccountingBalances[i].accountingType ){
				exchangingBalance = memAccountingBalances[i].balance;
			}else if('RB' == memAccountingBalances[i].accountingType){
				remainingBalance = memAccountingBalances[i].balance;
			}
		}
	}
	
	//2.根据订单类型调整支付方式： VIP购买，标准购买且有可用的ecupon后才有ecupon支付
	om_getEcupon();
	if (!('VIPP' == data.rows[0].orderType || 'STDP' == data.rows[0].orderType) || ecuponList.length <= 0) {
		for (var tp = 0; tp < paymentMethodData.length; tp++) {
			if (paymentMethodData[tp].value == "ECUP") {
				paymentMethodData.splice(tp,1);
			}
		}
	}
	
	//积分兑换
	if('RDEM' == data.rows[0].orderType) {
		var str1Td1 = "<label align='right'>" + $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.salespointsummary') + " :</label>";
		var str1Td2 = "<label id='redeemPointSum' style='font-weight : bold;'>0</label>";
		var str2Td1 = "<label>"+ $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.salespointsnow') +" :</label>";
		var str2Td2 = "<label id='salesPoint' style='font-weight : bold;'>0</label>";
		$("#salesLineSummary tr:eq(0) td:eq(2)").html(str1Td1);
	    $("#salesLineSummary tr:eq(0) td:eq(3)").html(str1Td2);
	    $("#salesLineSummary tr:eq(3) td:eq(0)").html(str2Td1);
	    $("#salesLineSummary tr:eq(3) td:eq(1)").html(str2Td2);
	    //积分总计
	    $("#redeemPointSum")[0].textContent = redeemPointSum;
	    //现有积分
	    $("#salesPoint")[0].textContent = salesPoint;
	}else {	//非积分兑换
		//税后总金额
		$("#afterTaxValue")[0].textContent = afterTaxValue;
	}
	//订单优惠总额
	$("#discountValueSum")[0].textContent = Number("-" + discountValueSum).toFixed(2);
	//实付款
	$("#actualPay")[0].textContent = actual_pay;
	//支付调整金额
	$("#adjustmentAmountCount")[0].textContent = Number(adjustmentAmountCount).toFixed(2);
	//运费
	$("#shippingFee")[0].textContent = Number(shippingFee).toFixed(2);
	//获得PV值
	tab1form.setData({
		memberCode : data.rows[0].memberCode,
		memberName : data.rows[0].memberName,
		currrentPv : data.rows[0].currentPv,
		orderPv : pvSum
	});
	$("#pvSum")[0].textContent = pvSum;
	//支付总额
	$("#paymentSum")[0].textContent = actual_pay;
	//余款
	$("#balance")[0].textContent = actual_pay;
	//初始化支付Grid
	grid2 = $("#d_om_002_grid").ligerGrid(om_paymentMethodGrid(userType));
	//是货到付款
	if("Y" == codFlag) {
		grid2.addRow({
			orderHeaderId : orderHeaderId, 						//订单头ID
			salesOrgId : salesOrgId,							//销售区域ID
	   		orderType : orderType,								//订单类型
	   		paymentMethod : 'PODEL',							//支付方式
	   		paymentAmount : "",									//支付金额
	   		remark : "",										//备注
	   		redeemPointCount : redeemPointSum,					//实际支付积分
	   	});
	} else if(actual_pay != 0.00 ) {
		grid2.addRow({
	   		orderHeaderId : orderHeaderId, 						//订单头ID
	   		orderType : data.rows[0].orderType,					//订单类型
	   		salesOrgId : salesOrgId,							//销售区域ID
	   		paymentMethod : defaultPayMth,						//默认支付方式
	   		paymentMethodInfo : "",								//支付方式具体信息：POS机、银行
	   		paymentAmount : "",									//支付金额
	   		transactionNumber : "", 							//交易凭证号
	   		bankCode : "",										//银行
	   		creditCardType : "",								//信用卡类型
	   		tailNumber : "",									//银行卡尾号
	   		remark : "",										//备注
	   		redeemPointCount : redeemPointSum,					//实际支付积分
	   	});
	}
}

var lovCheckPassword;
function checkBankCard(){
	if(!lovCheckPassword){
		window['checkform'] = $("#checkform").ligerForm({
			prefixID:'checkform_',
			fields : [
			{name : "memberId",type : "hidden",newline : false,
            	options:{
                    value:memberId
                }
            },{
                display : $l('type.com.lkkhpg.dsis.common.member.dto.memberdetails.cardpass'),
                name : "value",
                newline: true,
                type : "text",
                validate:{
                    required: true
                }
            }]
		});
		lovCheckPassword = $.ligerDialog.open({
			height:150,
            width: 350,
            allowClose:false,
            title : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.inputpass'),
            target: $("#checkform"),
            buttons: [{
            	text: $l('sys.hand.btn.ok'),
            	onclick: function(item, dialog) {
            		if(Hap.validateForm(checkform)){
            			dialog.hide();
            			var options1 = {
             	               url: _basePath + "/om/payment/checkBank",
             	               data:checkform.getData(),
             	               success:function(data){
             	            	   if ('empty' == data.rows[0]) {
             	            		   $.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.cardauthority'));
             	            		   dialog.hide();
             	                 	   clearPassword();
             	            	   } else if ('error' == data.rows[0]) {
             	            		   $.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.password_error'));
             	            		   dialog.hide();
            	                 	   clearPassword();
             	            	   } else if ('true' == data.rows[0]) {
             	            		   queryBankCard();
             	            		   dialog.hide();
           	                 	       clearPassword();
             	            	   }
             	               }
             	       }
             	       Hap.ajax(options1);
            		}
            	}
            },
            {
            	text: $l('sys.hand.btn.cancel'),
                onclick: function(item, dialog) {
                	dialog.hide();
                	clearPassword();
                }
            }]
		});
	}
	lovCheckPassword.show();
}

function clearPassword(){
	checkform.setData({
		value:""
	});
}

function queryBankCard(){
	$.ajax({
		type : 'POST',
		url : _basePath + "/om/query/bankInfo?memberId="
				+ memberId,
		success : function(data) {
			showBankInfo(data.rows);
		}
	});
}

var lovBankInfo;
function showBankInfo(bankInfoData){
	if (!lovBankInfo) {
		window['bankInfo'] = $("#bankInfo").ligerForm({
			prefixID:'bankInfo_',
			fields : [
			{
                display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.bankcard'),
                name : "cardId",
                newline: true,
                type : "select",
                options: {
	                data: bankInfoData,
	                valueField: "cardId",
	                textField: "bankCode"
	            },
	            editor : {
	            	onChangeValue : function(newValue){
	            		for (var j = 0; j < bankInfoData.length; j++) {
	            			if (newValue == bankInfoData[j].cardId) {
	            				liger.get("bankInfo").setData(bankInfoData[j]);
	            			}
	            		}
	            	}
	            }
            },{
            	display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.cardnumber'),
                name : "cardNumber",
                newline: true,
                type : "text",
                readonly : true
            },{
            	display : $l('type.com.lkkhpg.dsis.common.member.dto.memberdetails.effectdate'),
                name : "expiryDate",
                newline: true,
                type : "text",
                readonly : true
            },{
            	display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.creditCardType'),
                name : "cardSubType",
                newline: true,
                type : "select",
                readonly : true,
                options: {
	                data: creditCardTypeData,
	                valueField: "value",
	                textField: "meaning"
	            }
            }]
		});
		lovBankInfo = $.ligerDialog.open({
			height:225,
            width: 350,
            allowClose:false,
            title : $l('type.com.lkkhpg.dsis.common.member.dto.memberdetails.bankcardinfo'),
            target: $("#bankInfo"),
            buttons: [
            	{
                	text: $l('sys.hand.btn.cancel'),
                    onclick: function(item, dialog) {
                    	dialog.hide();
                    	//clearPassword();
                    }
                }
            ]
		});
	}
	for (var i = 0; i < bankInfoData.length; i++) {
		if (bankInfoData[i].defaultFlag == 'Y') {
			liger.get("bankInfo").setData(bankInfoData[i]);
		}
	}
	lovBankInfo.show();
}

/**
 * 根据当前订单上会员获取其Ecupon信息.
 */
function om_getEcupon(){
	$.ajax({
        url : _basePath + '/orderPayment/queryEcupon',
        type : "POST",
        dataType : "json",
        data: {
        	orderHeaderId : orderHeaderId
		} ,
        success : function(data) {
        	ecuponList = data.rows;
        }
    }); 
}

/**
 * 支付行Grid信息初始化.
 * @param userType 订单创建用户类型.
 */
function om_paymentMethodGrid(userType) {
	var tmpAmount = 0;
	var options = {
		unSetValidateAttr : false,
		columns : om_getPaymentColumns(false, false, userType),
		enabledEdit : true,
		checkbox : true,
		usePager : false,
		width : '99%',
		onBeforeSubmitEdit : function(e) {
			usedEb = usedEB();
			var columnName = e.column.columnname;
			var paymentMethod = e.record.paymentMethod;
			var balance = Number($("#balance")[0].textContent); //余款
			tmpAmount = Number(tmpAmount);
			if(columnName == "paymentAmount") {
				if(Number(e.value) <= 0) {
					Hap.showError($l('type.com.lkkhpg.dsis.common.order.orderinvalid.amountError'));
					return false;
				}
				if (Number(e.value) > (tmpAmount + balance)) {
					Hap.showError($l("msg.error.order.amount_beyond"));
					return false;
				}
				if(paymentMethod == 'EBPAY'){
					var p =e.record.paymentAmount ;
					if(Number(e.value) > curEB - usedEb + p || Number(e.value) > exchangingBalance -usedEb +p){
						Hap.showError($l("msg.error.order.amount_eb_excessive_use"));
						return false;
					}
				}
				if(paymentMethod == 'RBPAY'){
					var p =e.record.paymentAmount ;
					if(Number(e.value) > remainingBalance - usedRB() + p){
						Hap.showError($l("msg.error.order.amount_rb_excessive_use"));
						return false;
					}
				}
			}
			else if(columnName == "paymentMethod") {
				if(e.value == 'EBPAY'){
					if( curEB == 0 || curEB - usedEb <= 0){
						Hap.showError($l("msg.error.order.amount_exchange_goods_is_zero"));
						return false;
					} else if( exchangingBalance == 0 || exchangingBalance - usedEb <= 0 ){
						Hap.showError($l("msg.error.order.amount_eb_is_not_enough"));
						return false;
					}
				}
				else if(e.value == 'RBPAY'){
					if(remainingBalance ==0 || remainingBalance - usedRB() <=0){
						Hap.showError($l("msg.error.order.amount_rb_is_not_enough"));
						return false;
					}
				}
			}
		},
		onAfterEdit : function(e) {
			updateEcuponAmt(null);
			var balance = $("#balance")[0].textContent;
			if(balance == 0) {
				saveEditButton.setEnabled();
			}else {
				saveEditButton.setDisabled();
			}
		},
		onBeforeEdit : function(e) {
			if (e.column.columnname == "paymentAmount") {
				tmpAmount = e.value;
			}
			if("Y" == codFlag) {
				grid2.setCellEditing(e.record, e.column, false);
				return false;
			}
			if ("ECUP" == e.record.paymentMethod) {
				if (e.column.columnname == "paymentAmount") {
					grid2.setCellEditing(e.record, e.column, false);
					return false;
				}
			}
		},
		onAfterAddRow : function(e) {
			grid2.updateCell('paymentAmount', $("#balance")[0].textContent, e);
			$("#balance")[0].textContent= 0;
			saveEditButton.setEnabled();
		},
		toolbar: { items: [
                           {
                        	   text: $l('sys.hand.btn.new'),
                        	   click: function(){
                        			var gridData1 = grid1.getData();
                        			var salesOrgId = gridData1[0].salesOrgId;	
                        			//获取余款
                        			var balance = $("#balance")[0].textContent;					
                        			//判断支付方式,如果货到付款则只能选择一条支付行
                        			var grid2Data = grid2.getData();
                        			if(grid2.getData() != "") {
                        				for (var i=0;i<grid2Data.length;i++) {
	                        				var paymentMethod = grid2Data[i].paymentMethod;
	                        				if('PODEL' == paymentMethod || 'ONLPA' == paymentMethod) {
	                        					return false;
	                        				}
                        				}
                        			};
                        			if(balance <= 0) {
                        				Hap.showError($l("msg.error.order.amount_beyond"));
                        				return false;
                        			}
                        		   	grid2.addRow({
                        		   		orderHeaderId : orderHeaderId, 					//订单头ID
                        		   		orderType : gridData1[0].orderType,				//订单类型
                        		   		salesOrgId : salesOrgId,						//销售区域ID
                        		   		paymentMethod : defaultPayMth,					//支付方式
                        		   		paymentMethodInfo : "",							//支付方式具体信息：POS机、银行
                        		   		paymentAmount : "",								//支付金额
                        		   		transactionNumber : "", 						//交易凭证号
                        		   		bankCode : "",									//银行
                        		   		creditCardType : "",							//信用卡类型
                        		   		tailNumber : "",								//银行卡尾号
                        		   		remark : "",									//备注
                        		   	});
                        	   }, 
                        	   icon: 'add'},
                           { line: true },
                           { 
                        	   text: $l('sys.hand.btn.delete'), 
                        	   click: function(){
	                       			var grid2Data = grid2.getData();
	                       			//判断支付方式,货到付款则只能选择一条支付行
	                       			if(grid2.getData() != "") {
	                       				for (var i=0;i<grid2Data.length;i++) {
	                        				var paymentMethod = grid2Data[i].paymentMethod;
	                        				if('PODEL' == paymentMethod || 'ONLPA' == paymentMethod) {
	                        					return false;
	                        				}
	                       				}
	                       			};
	                       			var temp = grid2.getSelectedRow();
                               		grid2.deleteSelectedRow();
                               		updateEcuponAmt(temp.paymentAmount);
                           		}, 
                           		icon: 'delete'}
             			]
        }
	};
	return options;
}

/**
 * 支付行信息修改之前操作
 * @param e
 */
function om_paymentBeforeEdit(e) {
	if("Y" == codFlag) {
		grid2.setCellEditing(e.record, e.column, false);
		return false;
	}
}

/**
 * 新增一行支付行后操作
 * @param data
 */
function om_paymentAfterAddRow(data) {
	if($("#balance")[0].textContent == 0) {
		saveEditButton.setEnabled();
	}
}

/**
 * 支付行columns.
 * @param isPodel 是否货到付款
 * @param isCdaut 是否信用卡授权
 * @param userType 创建订单用户类型
 */
function om_getPaymentColumns(isPodel,isCdaut,userType) {
	//是否货到付款(Y-是)
	if("Y" == codFlag) {
		isPodel = true;
	}
	var paymentMethod = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.paymentmethod'),
			name : "paymentMethod", 
			align : 'center',
			width : '20%',
			validate : {
				required : true
			},
			render: function (item) {//单元格渲染器
				if (!item) {
					return;
				}
				//支付方式-Ipoint用户的支付方式
				if ("IPONT" == userType) {
					for (var i = 0; i < paymentMethodData.length; i++)
					{
						if ("ECUP" == paymentMethodData[i].value || ("Y" == paymentMethodData[i].description 
								&& paymentMethodData[i].value == item.paymentMethod))
							return paymentMethodData[i].meaning;
					}
				} else if (isPodel || isCdaut) {
					for (var i = 0; i < paymentMethodFixed.length; i++)
					{
						if (paymentMethodFixed[i].value == item.paymentMethod)
								return paymentMethodFixed[i].meaning;
					}
				} else {
					for (var i = 0; i < paymentMethodData.length; i++)
					{
						if (paymentMethodData[i].value == item.paymentMethod)
								return paymentMethodData[i].meaning;
					}
				}
		}
	}; 
	var columns = [
	  paymentMethod,             
	  {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.paymentamount'),
			name : "paymentAmount", 
			align : 'center',
			width : '20%',
			editor :  { 
				type: 'float' 
			},
			option : {
				sign : false
			},
			validate : {
				required : true
			},
			totalSummary : {
				render:function(suminf,column) {
				    //动态更新余款
					var paymentSum = $("#paymentSum")[0].textContent;
					$("#balance")[0].textContent = Number((paymentSum - suminf.sum)).toFixed(2);
				}
			}
		},{
			display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.otherInfo'),
			name : "otherInfo", 
			align : 'center',
			width : '60%',
			render : om_paymentOtherInfoRender
		}];
	//3.支付方式-是否货到付款
	if (!isPodel) {//不是货到付款
		//4.支付方式-是否Ipoint用户创建订单
		if ("IPONT" == userType) {
			for (var i = 0; i < paymentMethodData.length; i++)
			{
				if ("ECUP" == paymentMethodData[i].value) {
					continue;
				}
				if ("Y" != paymentMethodData[i].description
						|| paymentMethodData[i].description == null) {
					paymentMethodData.splice(i,1);
					i--;
				}
			}
		}
		//5.支付方式-取组织参数中的支付方式与筛选后的支付方式取交集
		for (var e = 0; e < payMethodOrgParam.length; e++) {
			for (var d = 0; d < paymentMethodData.length; d++) {
				if (payMethodOrgParam[e] == paymentMethodData[d].value) {
					payMethodList.push(paymentMethodData[d]);
					break;
				}
			}
		}
		//6.支付方式-支付方式默认值
		var flag = false;
		for (var i=0; i<payMethodList.length; i++) {
			if (defaultPayMth == payMethodList[i].value) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			defaultPayMth = "";
		}
		paymentMethod.editor = {
			type: 'select' ,
			data : payMethodList,
            valueField : 'value',
            textField : 'meaning',
            onChanged : function(data){
            	var paymentAmount = data.record.paymentAmount;
                if(data.value == 'EBPAY'){
                    if(exchangingBalance >= curEB){
                        if(curEB - usedEb < paymentAmount){
                            $("#balance")[0].textContent = Number(paymentAmount - curEB + usedEb + Number($("#balance")[0].textContent)).toFixed(2);
                            paymentAmount = Number(curEB - usedEb).toFixed(2);
                        }
                    }else{
                        if(exchangingBalance - usedEb < paymentAmount ){
                        	$("#balance")[0].textContent = Number(paymentAmount - exchangingBalance + usedEb + Number($("#balance")[0].textContent)).toFixed(2);
                            paymentAmount = Number( exchangingBalance - usedEb).toFixed(2);
                        }
                    }
            	}else if(data.value == 'RBPAY'){
            		if(remainingBalance - usedRB() < paymentAmount){
                        $("#balance")[0].textContent = Number(paymentAmount - remainingBalance + usedRB() + Number($("#balance")[0].textContent)).toFixed(2);
                        paymentAmount = Number(remainingBalance).toFixed(2);
                    }
            	}else if (data.value == 'ECUP') {
            		$("#balance")[0].textContent = Number(paymentAmount + Number($("#balance")[0].textContent)).toFixed(2);
            		paymentAmount = Number(0).toFixed(2);
            	}
				grid2.updateRow(data.record,{
					paymentAmount : paymentAmount,
					paymentMethodInfo : "",		//支付方式具体信息：POS机、银行
  		   		  	transactionNumber : "", 	//交易凭证号
  		   		  	bankCode : "",				//银行
  		   		  	creditCardType : "",		//信用卡类型
  		   		  	tailNumber : "",			//银行卡尾号
  		   		  	remark : ""				    //备注
				});
			}
		};
	} else {//是货到付款
		paymentMethod.editor = {
			type: 'select' ,
			data : paymentMethodFixed,
            valueField : 'value',
            textField : 'meaning',
            onChanged : function(data){
                var paymentAmount = data.record.paymentAmount;
                if(data.value == 'EBPAY'){
                    if(exchangingBalance >= curEB){
                        if(curEB < paymentAmount){
                            paymentAmount = curEB;
                        }
                    }else{
                        if(exchangingBalance < paymentAmount){
                            paymentAmount = exchangingBalance;
                        }
                    }
                    curEB = - paymentAmount;
                    exchangingBalance = exchangingBalance - paymentAmount;
            	}
				grid2.updateRow(data.record,{
					paymentAmount : paymentAmount,
					paymentMethodInfo : "",		//支付方式具体信息：POS机、银  行
  		   		  	transactionNumber : "", 	//交易凭证号
  		   		  	bankCode : "",				//银行
  		   		  	creditCardType : "",		//信用卡类型
  		   		  	tailNumber : "",			//银行卡尾号
  		   		  	remark : ""				    //备注
				});
			}
		};
	}
	return columns;
}

/**
 * 设置支付行其他信息显示值
 * @param obj
 * @param rowindex
 */
function setGridData(obj,rowindex) {
	var value = $(obj).val();
	grid2.updateRow(grid2.getRow(rowindex),{
   		remark : value,
	});
}

/**
 * 根据ecupon的选择改变金额，并计算总金额
 * @param rowindex
 */
function setGridEcupon(rowindex) {
	var value = $("#ecupon_" + rowindex).val();
	var text = $("#ecupon_" + rowindex).find("option:selected").text();
	var index = text.indexOf(" ");
	var fixAmt = Number(text.substring(0,index));
	var paymentAmount = Number(grid2.getRow(rowindex).paymentAmount);
	if (value == -1) {
		
		$("#balance")[0].textContent = Number(paymentAmount + Number($("#balance")[0].textContent)).toFixed(2);
		grid2.updateRow(grid2.getRow(rowindex),{
	   		voucherId : "",
	   		paymentAmount : 0
		});
	}else {
		if (fixAmt > (Number($("#balance")[0].textContent)+ paymentAmount)) {
			grid2.updateRow(grid2.getRow(rowindex),{
		   		voucherId : value,
		   		paymentAmount : Number($("#balance")[0].textContent) + paymentAmount
			});
			$("#balance")[0].textContent = Number(0).toFixed(2);
		} else {
			/*grid2.updateCell('paymentAmount', fixAmt, rowindex);
			grid2.updateCell('voucherId', value, rowindex);*/
			grid2.updateRow(grid2.getRow(rowindex),{
		   		voucherId : value,
		   		paymentAmount : fixAmt
			});
			$("#balance")[0].textContent = Number(paymentAmount - fixAmt + Number($("#balance")[0].textContent)).toFixed(2);
		}
	}
	grid2.reRender();
	var balance = $("#balance")[0].textContent;
	if(balance == 0) {
		saveEditButton.setEnabled();
	}else {
		saveEditButton.setDisabled();
	}
}

function setGridEcupon2(rowindex, deleteValue) {
	var value = $("#ecupon_" + rowindex).val();
	var text = $("#ecupon_" + rowindex).find("option:selected").text();
	var index = text.indexOf(" ");
	var fixAmt = Number(text.substring(0,index));
	var paymentAmount = Number(grid2.getRow(rowindex).paymentAmount);
	if (value == -1) {
		
		$("#balance")[0].textContent = Number(paymentAmount + Number($("#balance")[0].textContent)).toFixed(2);
		grid2.updateRow(grid2.getRow(rowindex),{
	   		voucherId : "",
	   		paymentAmount : 0
		});
	}else {
		if (fixAmt > (Number($("#balance")[0].textContent) + paymentAmount + Number(deleteValue))) {
			grid2.updateRow(grid2.getRow(rowindex),{
		   		voucherId : value,
		   		paymentAmount : Number($("#balance")[0].textContent) + paymentAmount + Number(deleteValue)
			});
			$("#balance")[0].textContent = Number(0).toFixed(2);
		} else {
			grid2.updateRow(grid2.getRow(rowindex),{
		   		voucherId : value,
		   		paymentAmount : fixAmt
			});
			$("#balance")[0].textContent = Number(paymentAmount - fixAmt + Number($("#balance")[0].textContent)).toFixed(2);
		}
	}
	grid2.reRender();
	var balance = Number($("#balance")[0].textContent) + Number(deleteValue);
	if(balance == 0) {
		saveEditButton.setEnabled();
	}else {
		saveEditButton.setDisabled();
	}
}


function updateEcuponAmt(value){
	var ecuponData = grid2.getData();
	for (var i = 0; i < ecuponData.length; i++) {
		if (ecuponData[i].paymentMethod == 'ECUP') {
			if (null != value) {
				setGridEcupon2(i,value);
			}else {
				setGridEcupon(i);
			} 
			
		}
	}
	
}

/**
 * 支付行其他信息栏渲染
 */
function om_paymentOtherInfoRender(record, rowindex, value, column) {
	//支付方式
	var paymentMethod = record.paymentMethod;
	var html = "";
	if(paymentMethod == '' || paymentMethod == 'CASH' 
		||  paymentMethod == 'PODEL' || paymentMethod == 'ONLPA'){//现金、货到付款和在线支付
		html = $("#otherInfo1").clone();
		var remarkInput = html.find('input')[0];
		$(remarkInput).attr("id","remark_"+rowindex);
		$(remarkInput).attr("value",record.remark);
		//$(remarkInput).attr("onblur","javascript:setGridData(this,"+rowindex+");");
		$(remarkInput).attr("onblur","javascript:setGridData(this,"+rowindex+");");
	}else if (paymentMethod == 'CREDI'){//信用卡-POS
		html = $("#otherInfo2").clone();
		var creditCardTypeInput = html.find("input")[0];
		$(creditCardTypeInput).attr("id","credit_card_type_" + rowindex);
		$(creditCardTypeInput).attr("value",getCreditCardType(record.creditCardType));
		var tailNumber = html.find("input")[1];
		$(tailNumber).attr("id","tail_number_" + rowindex);
		$(tailNumber).attr("value",record.tailNumber);
		var clickEvent = html.find("input[type=button]")[0];
		$(clickEvent).attr("onclick","javascript:editInfo("+rowindex+")");
	} else if(paymentMethod == 'DBCRD') {//借记卡-POS
		html = $("#otherInfo3").clone();
		var bankCode = html.find("input")[0];
		$(bankCode).attr("id","bank_code_" + rowindex);
		$(bankCode).attr("value",record.bankCode);
		//$(bankCode).attr("value",getBankName(record.bankCode));
		var tailNumber = html.find("input")[1];
		$(tailNumber).attr("id","tail_number_" + rowindex);
		$(tailNumber).attr("value",record.tailNumber);
		var clickEvent = html.find("input[type=button]")[0];
		$(clickEvent).attr("onclick","javascript:editInfo("+rowindex+")");
	}else if (paymentMethod == 'CHEQU' || paymentMethod == 'REMIT'){
		html = $("#otherInfo4").clone();
		var bankCode = html.find("input")[0];
		$(bankCode).attr("id","bank_code_" + rowindex);
		$(bankCode).attr("value",record.bankCode);
		//$(bankCode).attr("value",getBankName(record.bankCode));
		var transactionNumber = html.find("input")[1];
		$(transactionNumber).attr("id","transaction_number_" + rowindex);
		$(transactionNumber).attr("value",record.transactionNumber);
		var clickEvent = html.find("input[type=button]")[0];
		$(clickEvent).attr("onclick","javascript:editInfo("+rowindex+")");
	}else if(paymentMethod == 'EBPAY'){
		html = $("#otherInfo5").clone();
		var eb1 = html.find("input")[0];
		$(eb1).attr("id","eb1_" + rowindex);
		$(eb1).attr("value",exchangingBalance);
		var eb2 = html.find("input")[1];
		$(eb2).attr("id","eb2_" + rowindex);
		$(eb2).attr("value",curEB);
	}else if(paymentMethod == 'RBPAY'){
    	html = $("#otherInfo6").clone();
    	var rb = html.find("input")[0];
    	$(rb).attr("id","rb" + rowindex);
		$(rb).attr("value",remainingBalance);
	}else if (paymentMethod == 'ECUP') {
		html = $("#otherInfo7").clone();
		var remarkInput = html.find('input')[0];
		$(remarkInput).attr("id","remark_"+rowindex);
		$(remarkInput).attr("value",record.remark);
		$(remarkInput).attr("onblur","javascript:setGridData(this,"+rowindex+");");
		var ecupons = html.find('select')[0];
		$(ecupons).attr("id","ecupon_"+rowindex);
		$(ecupons).attr("onchange","setGridEcupon("+rowindex+");");
		ecupons.options.add(new Option($l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.choose.ecupon"),-1));
		var grid2Data = grid2.getData();
		var ecuponIdList = [];
		for (var i = 0; i < grid2Data.length; i++) {
			if (grid2Data[i].paymentMethod == 'ECUP' && grid2Data[i].__id != record.__id && !checkNull(grid2Data[i].voucherId)) {
				ecuponIdList.push({"voucherId" : grid2Data[i].voucherId});
			}
		}
		var tempList = [];
		for (var i = 0; i < ecuponList.length; i++) {
			if (!containList(ecuponList[i].voucherId, ecuponIdList)) {
				tempList.push(ecuponList[i]);
			}
		}
		for (var i = 0; i < tempList.length; i++) {
			var opt = '<option value="'+tempList[i].voucherId+'" ' + (tempList[i].voucherId == record.voucherId ? 'selected' : '') + '>'+ tempList[i].discountValue + " " + tempList[i].name + '</option>';
			$(ecupons).append(opt);
		}
	}else {//其他
		html = $("#otherInfo1").clone();
		var remarkInput = html.find('input')[0];
		$(remarkInput).attr("id","remark_"+rowindex);
		$(remarkInput).attr("value",record.remark);
		$(remarkInput).attr("onblur","javascript:setGridData(this,"+rowindex+");");
	}
	return html.html();
}

function containList(value, list){
	for (var i = 0; i < list.length; i++) {
		if(value == list[i].voucherId) {
			return true;
		}
	}
	return false;
}

//渲染信用卡类型中文名字段
function getCreditCardType(value){
	for(var i = 0 ; i < creditCardTypeData.length ; i++){
		if(creditCardTypeData[i].value == value){
			return creditCardTypeData[i].meaning;
		}
	}
}

/**
 * 编辑按钮，显示弹出框信息
 */
function om_paymentShowDialogInfo() {
	var options = {
			inputWidth : 100,
			labelWidth : 80,
			space : 40,
			fields : [{
				display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.creditCardType"),/*信用卡类型*/
				name : 'creditCardType',
				newline : true,
				type : 'combobox',
				options :  {
					data : creditCardTypeData,
	                valueField : 'value',
	                textField : 'meaning',
	                isMultiSelect : false
				},
				validate : {
					required : true
				}
			},{
				display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.bank"),/*银行*/
				name : 'bankCode',
				newline : false,
				type : 'text',
				/*options :  {
					data : bankBelongData,
	                valueField : 'value',
	                textField : 'meaning',
	                isMultiSelect : false
				},
				validate : {
					required : true
				}*/
			},{
				display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.tailnumber"),/*4位尾号*/
				name : 'tailNumber',
				newline : true,
				type : 'text',//digits
				validate : {
					required : true,
					minlength : 4,
					maxlength : 4
				}
			},{
				display : $l('type.com.lkkhpg.dsis.common.order.orderinvalid.pos'),/*POS机选择*/
				name : 'paymentMethodInfo', 
				newline : false,
				type : 'select',
				options : {
					data : posSelectData,
					valueField : 'value',
					textField : 'meaning',
					isMultiSelect : false
				}/*,
				validate : {
					required : true,
					minlenth : 1
				}*/
			},{
				display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.transactionnumber"),/*凭证号*/
				name : 'transactionNumber',
				newline : true,
				type : 'text',
				width : 200
			},{
				display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remark"),/*备注*/
				name : 'remark',
				newline : true,
				type : 'text',
				width : 200
			}]
	};
	return options;
}
/**
 * 银行名称中文显示
 * @param value
 * @returns
 */
function getBankName(value){
	for(var i = 0 ; i < bankBelongData.length ; i++){
		if(bankBelongData[i].value == value){
			return bankBelongData[i].meaning;
		}
	}
}
/**
 * 编辑弹框信息
 * @param index 行索引
 */
function editInfo(index) { //index-每行的行号，从0开始
	var rowData = grid2.getRow(index);
	var paymentMethod = grid2.getRow(index).paymentMethod
	//根据支付方式隐藏弹出框中的输入项
	if(paymentMethod == "CHEQU" || paymentMethod == "REMIT" ) {
		tab3form.setVisible(["creditCardType","tailNumber","paymentMethodInfo"],false);
	}
	//设置必输项
	if (paymentMethod == "CHEQU" || paymentMethod == "DBCRD" || paymentMethod == "CREDI") {
		tab3form.setFieldValidate("transactionNumber", {required : true});
	} else if (paymentMethod == "REMIT") {
		tab3form.setFieldValidate("transactionNumber", {required : false});
	}
	//将编辑行的数据放入到表格中
	liger.get("tab3form").setData({
		creditCardType : rowData.creditCardType,
		bankCode : rowData.bankCode,
		tailNumber : rowData.tailNumber,
		paymentMethodInfo : rowData.paymentMethodInfo,
		transactionNumber : rowData.transactionNumber,
		remark : rowData.remark
	})
	$.ligerDialog.open({
		target: $("#div3"),
        title: $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.otherInfo'),
        name : 'otherInfo',
        width: 500,
        height: 300,
        isResize: true,
        modal: true,
        allowClose: false,
        buttons: [{ 
        	text: $l('sys.hand.btn.ok'),
        	cls:'l-dialog-btn-highlight',
        	onclick: function (item, dialog) {
        		var tab3FormData = liger.get("tab3form").getData();			//获得弹出框中的Form值
        		//校验字段
        		tab3FormData.paymentMethod = grid2.getRow(index).paymentMethod;
        		if(!checkoutOtherInfo(tab3FormData)) {
        			Hap.showError($l('type.com.lkkhpg.dsis.common.order.orderinvalid.otherinfoerror'));
        			return false;
        		}
        		grid2.updateRow(grid2.getRow(index),{//更新到Grid对应行中
        			creditCardType : tab3FormData.creditCardType,			//信用卡类型
        			transactionNumber : tab3FormData.transactionNumber, 	//交易凭证号
    		   		bankCode : tab3FormData.bankCode,						//银行
    		   		tailNumber : tab3FormData.tailNumber,					//银行卡尾号
    		   		remark : tab3FormData.remark,							//备注
    		   		paymentMethodInfo : tab3FormData.paymentMethodInfo,		//POS机
        		});
				tab3form.setVisible(["bankCode","creditCardType","creditCardType","tailNumber","paymentMethodInfo"],true);
        		dialog.hide();
        	}
        },{
        	text: $l('sys.hand.btn.cancel'), 
        	onclick: function (item, dialog) {
        		tab3form.setVisible(["bankCode","creditCardType","creditCardType","tailNumber","paymentMethodInfo"],true);
        		dialog.hide();
        	}
        }]
	});
}

/**
 * 取消订单.
 */
function cancelOrder() {
	var gridData = grid1.getData();
	var orderStatus = gridData[0].orderStatus;
	if ("WPAY" != orderStatus) {
		Hap.showError($l('msg.error.order.status_not_allow_pay'));
		return false;
	}
    $.ligerDialog.confirm($l('msg.warn.iscancel'), function (yes) {
        if (yes){
            var requestData ={orderStatus:"CANCL",headerId:orderHeaderId};
            $.ajax({
                type : 'POST',
                url : _basePath + "/om/order/update",
                data:JSON2.stringify(requestData),
                contentType : "application/json; charset=utf-8",
                success : function(json) {
					window.top.f_removeTab('ORDER_INFO');
					window.top.f_addTab('ORDER_INFO', $l('type.com.lkkhpg.dsis.common.order.dto.queryorder.orderinfo'),
	                        _basePath +"/om/om_order_detail.html?orderId="+orderHeaderId);
	                window.top.f_removeTab('ORDER_PAYMENT');
                }
            });
        }
    });
}

/**
 * 查询页面进入支付时，订单状态不满足，则关闭支付页面，重新加载查询页面.
 * @param orderStatus 订单状态
 */
function f_valiOrderStatus(orderStatus) {
	if ("WPAY" != orderStatus) {
		Hap.showError($l('msg.error.order.status_not_allow_pay'), function() {
			window.top.f_removeTab('ORDER_CREATE');
			window.top.f_addTab('ORDER_INFO', $l('type.com.lkkhpg.dsis.common.order.dto.queryorder.orderquery'), _basePath + '/om/om_order_query.html');
		});
	}
}

/**
 * 跳转到订单详情页面.
 */
function toOrderDetail() {
	window.top.f_removeTab('ORDER_INFO');
	window.top.f_addTab('ORDER_INFO', $l('type.com.lkkhpg.dsis.common.order.dto.queryorder.orderinfo'), _basePath + '/om/om_order_detail.html?orderId=' + orderHeaderId);
	window.top.f_removeTab('ORDER_CREATE');
}

/**
 * 确认收款
 * @returns {Boolean}
 */
function saveOrderPayment() {
	if (!Hap.validateGrid(d_om_002_grid)) {
		return false;
	}
	var targetUrl;
	//支付方式
	var paymentMethod = "";	
	var orderData = grid1.getData();
	var orderType = orderData[0].orderType;	
	//会员现有积分
	var salesPoint = orderData[0].salesPoint;
	//积分合计
	var redeemPointSum;
	var paymentData = grid2.getData();
	if(!checkoutInfo(paymentData)){
		Hap.showError($l('type.com.lkkhpg.dsis.common.order.orderinvalid.infoerror'));
		 return false;
	 }
	//积分兑换不足时
	if(orderType == 'RDEM') {
		redeemPointSum = $("#redeemPointSum")[0].textContent;
		if (salesPoint-redeemPointSum <= 0) {
			Hap.showError($l("msg.error.om.sales_point_insufficient"));
			return false;
		}
	}
	var str;
	//是否积分兑换
	if('RDEM' == orderType) {
		str = $l("msg.info.order.total_payment")+redeemPointSum+$l("msg.info.order.sales_point")+$("#actualPay")[0].textContent+$l("msg.info.order.amount_confirm");
	} else {
		str = $l("msg.info.order.total_payment")+$("#actualPay")[0].textContent+$l("msg.info.order.amount_confirm");
	}
	$.ligerDialog.confirm($l(str), $l('sys.hand.tip.info'),function(yes) {
		if (yes) {
			var differ = checkEcuponAmt();
			if (differ > 0){
				$.ligerDialog.confirm($l("type.com.lkkhpg.dsis.common.order.orderinvalid.ecupon.beyond")+ differ+","+$l("msg.warning.om.confirm_payment"),function(yes) {
					if (yes) {
						createOrderPayment(paymentData);
					}
				});
			} else {
				createOrderPayment(paymentData);
			}
			
		}
	})
}

/**
 * 检查grid中ecupon是否有金额未使用
 */
function checkEcuponAmt(){
	var result = grid2.getData();
	var difference = 0 ;
	for (var i = 0; i < result.length; i++) {
		if (result[i].paymentMethod == 'ECUP') {
			var oldAmt =0;
			for (var j = 0; j<ecuponList.length; j++) {
				if (ecuponList[j].voucherId == result[i].voucherId) {
					oldAmt = ecuponList[j].discountValue;
				}
			}
			if (oldAmt > 0 && oldAmt > Number(result[i].paymentAmount)) {
				difference += oldAmt - Number(result[i].paymentAmount);
			}
		}
	}
	return difference;
}

/**
 * 创建支付信息
 */
function createOrderPayment(paymentData) {
	$.ligerDialog.waitting($l('sys.hand.tip.processing'));
	$.ajax({
		url : _basePath+'/orderPayment/createOrderPayment?orderHeaderId='+orderHeaderId,
		type : "POST",
		dataType : "json",
		contentType : "application/json",
		data : JSON2.stringify(paymentData),
		success : function(data) {
			//跳转到订单详情界面
			if(data.success) {
				if(data.rows && data.rows.length > 0) {
					window.top.f_removeTab('ORDER_INFO');
					window.top.f_addTab('ORDER_INFO', $l('type.com.lkkhpg.dsis.common.order.dto.queryorder.orderinfo'), _basePath + '/om/om_order_detail.html?orderId=' + orderHeaderId);
					window.top.f_removeTab('ORDER_CREATE');
				} else {
					 $.ligerDialog.alert($l('msg.warning.om.need_pick_up'), function() {
						 window.top.f_removeTab('ORDER_INFO');
						 window.top.f_addTab('ORDER_INFO', $l('type.com.lkkhpg.dsis.common.order.dto.queryorder.orderinfo'), _basePath + '/om/om_order_detail.html?orderId=' + orderHeaderId);
						 window.top.f_removeTab('ORDER_CREATE');
					 });
				}
			}
	     },
		error : function(data) {
			Hap.showError($l("msg.error.order.failure_pay_again"));
		}
	});
}

/**
 * 校验弹出框字段不为空
 * @param temp
 * @returns {Boolean}
 */
function checkoutOtherInfo(temp){
	if('CHEQU' == temp.paymentMethod) {
		if (checkNull(temp.transactionNumber)) {
			return false;
		}
	}else if('CREDI' == temp.paymentMethod || 'DBCRD' == temp.paymentMethod) {
		if (isNaN(temp.tailNumber)) {
			return false;
		}
		if (checkNull(temp.creditCardType) || checkNull(temp.transactionNumber)
				|| checkNull(temp.tailNumber) || (temp.tailNumber+ "").length != 4) {
			return false;
		}
	}
	return true;
}
/**
 * 校验支付信息必填字段
 */
function checkoutInfo(data){
	var temp;
	for (var i in data) {
		temp = data[i];
		if(!checkoutOtherInfo(temp)){
			return false;
		}
		if (checkNull(temp.paymentAmount) || temp.paymentAmount <= 0) {
			//检验货到付款时,支付金额为0时
			if (codFlag == "Y" && temp.paymentAmount == 0) {
				return true;
			}
			return false;
		}
	}
	return true;
}
function checkNull(value){
	if (null == value || "" == value) {
		return true;
	}
	return false;
}

function usedEB(){
	var data = grid2.getData();
	if(!data){
		return 0;
	}
	var totalEb =  0;
	for(var i=0; i<data.length;i++){
        if(data[i].paymentMethod == 'EBPAY' ){
           totalEb =  totalEb + Number(data[i].paymentAmount);
        }
	}
	return totalEb
}

function usedRB(){
	var data = grid2.getData();
	if(!data){
		return 0;
	}
	var totalRb =  0;
	for(var i=0; i<data.length;i++){
        if(data[i].paymentMethod == 'RBPAY' ){
        	totalRb =  totalRb + Number(data[i].paymentAmount);
        }
	}
	return totalRb
}

/**
 * 获取商品包详情
 * @param row 商品包行
 * @param detailPanel 详情的pannel
 * @param callback 回调函数
 */
function f_getItemPackageDetail(row, detailPanel, callback) {
    if(row.itemType!='PACKG'&&row.itemType!='VN'&&row.itemType!='VY'){//非商品包的直接返回
        return;
    }
    var grid = document.createElement('div');
    $(detailPanel).append(grid);
    $(grid).css('margin', 10).ligerGrid({
        columns : [ {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.itemid'),
            name : 'itemNumber',
            type : 'text'
        }, {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.itemname'),
            name : 'itemName',
            type : 'text'
        }, {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.quantity'),
            name : 'quantity'
        }],
        isScroll : false,
        showToggleColBtn : false,
        width : 500,
        url : _basePath+"/inv/item/queryHierarchyByItemId?itemId="+row.itemId,
        showTitle : false,
        usePager : false,
        onAfterShowData : callback,
        frozen : false
    });
}

/**
 * 跳转订单确认页
 */
function f_openConfirm() {
	console.log("77777")
	var tabId = window.top.tab.getSelectedTabItemID();
	var headerId = orderHeaderId;
	if (!orderHeaderId) {
		$.ligerDialog.error($l("msg.error.order_pls_save_order"));
		return;
	}
	$.ajax({
		type : 'POST',
		url : _basePath + "/om/order/paytosave?orderId="
				+ headerId,
		success : function(data) {
			window.top.f_removeTab(tabId);
			window.top.f_addTab('ORDER_CREATE', $l('msg.info.order.confirm'), _basePath
					+ "/om/om_order_confirm.html?orderId=" + headerId);
		}
	});
	
}


