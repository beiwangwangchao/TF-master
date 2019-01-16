/**
 * @summary DSIS
 * @description 订单新建页面js方法集合
 * @version 1.0
 * @author wuyichu
 * @author linxixin
 * @author zhangchuangsheng
 * @copyright Copyright LKK Health Products Group Limited.
 * 
 */

DsisOrderCreate = {
	version : '1.0',
	copyOrder : function() {
		$("#copyModelDialog").parent().hide();
		clearForm("om_oc_form");
		clearGrid("linegrid");
		liger.get('copyModelDialog').trigger('buttonClick')
	},
	summary : {
		pv : 0,
		tax : 0,
		currency : 0,
		taxRate : 0.1,
		shippingTier : 0,
		discountAmt : 0,
		adjustMents : 0,
		points : 0,
		exchange : 0
	},
	editLineIndex : 0
}
jQuery.validator.addMethod("stringMaxLength", function(value, element, param) {  
    var length = value.length;  
    for(var i = 0; i < value.length; i++){  
        if(value.charCodeAt(i) > 127){  
            length = length + 2;  
        }  
    }  
    return this.optional(element) || ( length <= param );  
}, $l("sys.hand.validate.maxlength"));  

function f_copy() {
	$("#copyModelDialog").parent().hide();
	clearForm("om_oc_form");
	clearGrid("linegrid");
	liger.get('copyModelDialog').trigger('buttonClick')
}

/**
 * form数据清除
 */
function clearForm(form) {
	$("#" + form + " :input").each(function(e) {
		$(this).val("");
	});
}

/**
 * grid数据清除
 * 
 * @param grid
 */
function clearGrid(grid) {
	grid = liger.get(grid);
	if (grid.currentData != null && grid.currentData.rows.length > 0) {
		var rows = grid.currentData.rows;
		rows.splice(0, rows.length);
		grid.reRender();
	}
}

/**
 * 根据用户类型判断会员pupop
 */
function chargeMemberPupop(){
	if (userType == 'IPONT') {
		return o_ipointMemberPupop();
	} else {
		return o_memberPupop();
	}
}

/**
 * 会员选择的pupop
 */
function o_memberPupop() {
	var options = {
		readonly : false,// 是否可编辑
		cancelable : false,
		autocomplete : true,
		placeholder : $l("msg.warn.order.om_sales_order.fullmembercode"),
		valueField : 'memberId',// 选中的valueField,文本框有值时grid有选中效果
		textField : 'memberCode',// 选中的textField,文本框显示的grid字段
		grid : {
			delayLoad : true,
			isSingleCheck : true,
			columns : f_getMemberPupopColumns(_salesOrgType),
			// 数据查询的url
			url : _basePath + '/mm/member/queryMembersForOrder',
			onLoadData : function() {
				if (liger.get('orderType')
						&& liger.get('orderType').getValue() != 'NMDP') {
					this.setParm("marketId", liger.get("marketId").getValue());
					if (igiMarketId) {
						this.setParm("igiMarketId", igiMarketId);
					}
				} else if (!liger.get('orderType')) {
					this.setParm("marketId", liger.get("marketId").getValue());
				}
				// 限制用户角色
				if (liger.get('orderType')
						&& liger.get('orderType').getValue() == 'VIPP') {
					this.setParm("memberRole", "VIP");
				} else if (liger.get('orderType')
						&& liger.get('orderType').getValue() == 'NMDP') {
					this.setParm("orderType","NMDP");
				} else {
					this.setParm("memberRole", "DIS");
				}

				this.setParm("isActive", "Y");
			}

		},
		// lov上的查询条件
		condition : {
			inputWidth : 130,
			labelWidth : 70,
			fields : [
					{
						display : $l("type.com.lkkhpg.dsis.common.user.dto.userinfo.memberid"),
						name : "memberCode",
						isAutoComplete : true,
						newline : false,
						type : "text"
					},
					{
						display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.membername"),
						name : "memberName",
						newline : false,
						isAutoComplete : false,
						type : "text"
					},
					{
						display : $l("type.com.lkkhpg.dsis.common.member.dto.marketchange.registercode"),
						name : "registerCode",
						isAutoComplete : false,
						newline : true,
						type : "text"
					},
					{
						display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.phoneno"),
						name : 'phoneNo',
						align : 'center',
						newline : false,
						isAutoComplete : false
					} ]
		},
		onselect : function(data) {
			f_setMember(data.data[0]);
		},
		onChangeValue : function(value) {
			if (!value) {
				cleanMember();
			}
		}
	}
	return options;
}

function f_getMemberPupopColumns(salesOrgType){
	var memberCode = {
					display : $l("type.com.lkkhpg.dsis.common.user.dto.userinfo.memberid"),
					name : 'memberCode',
					align : 'center',
					isAutoComplete : true,
					autocompleteField : true
				};
	var memberName = {
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.membername"),
					name : 'memberName',
					isAutoComplete : false,
					align : 'center'
				};
	var phoneNo= {
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.phoneno"),
					name : 'phoneNo',
					align : 'center',
					isAutoComplete : false
				};
	var email = {
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.email"),
					name : 'email',
					isAutoComplete : false,
					align : 'center'
				};
	var statusDesc = {
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.memberstatus"),
					name : 'status',
					isAutoComplete : false,
					align : 'center',
					render:function(rowdata, index, value){
						for(var t=0; t<memberStatusData.length; t++){
							if(memberStatusData[t].value == value){
								return memberStatusData[t].meaning;
							}
						}
						return value;
					}
				};
	var registerCode = {
			display : $l("type.com.lkkhpg.dsis.common.member.dto.marketchange.registercode"),
			name : 'idNumber',
			isAutoComplete : false,
			align : 'center'
		};
	var englishName = {
			display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.englishname"),
			name : 'englishName',
			isAutoComplete : false,
			align : 'center'
		};
	var chineseName = {
			display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.chinesename"),
			name : 'chineseName',
			isAutoComplete : false,
			align : 'center'
		};
    if(salesOrgType == 'INTEL'){
    	return [memberCode, memberName,phoneNo, email,statusDesc];
    } else if (salesOrgType == 'IPONT') {
    	return [memberCode, registerCode, englishName];
    } else{
    	return [memberCode, memberName,statusDesc];
    }
	
}

/**
 * ipoint用户popup
 */
function o_ipointMemberPupop(){
	var options = {
		readonly : false,// 是否可编辑
		cancelable : false,
		autocomplete : true,
		placeholder : $l("msg.warn.order.om_sales_order.fullmembercode"),
		valueField : 'memberId',// 选中的valueField,文本框有值时grid有选中效果
		textField : 'memberCode',// 选中的textField,文本框显示的grid字段
		grid : {
			delayLoad : true,
			isSingleCheck : true,
			columns : f_getMemberPupopColumns('IPONT'),
			// 数据查询的url
			url : _basePath + '/mm/member/queryMembersForIpointOrder',
			onLoadData : function() {
				if (liger.get('orderType')
						&& liger.get('orderType').getValue() != 'NMDP') {
					this.setParm("marketId", liger.get("marketId").getValue());
					if (igiMarketId) {
						this.setParm("igiMarketId", igiMarketId);
					}
				} else if (!liger.get('orderType')) {
					this.setParm("marketId", liger.get("marketId").getValue());
				}
				// 限制用户角色
				if (liger.get('orderType')
						&& liger.get('orderType').getValue() == 'VIPP') {
					this.setParm("memberRole", "VIP");
				} else if (liger.get('orderType')
						&& liger.get('orderType').getValue() == 'NMDP') {
					this.setParm("orderType","NMDP");
				} else {
					this.setParm("memberRole", "DIS");
				}

				this.setParm("isActive", "Y");
			}

		},
		// lov上的查询条件
		condition : {
		inputWidth : 130,
		labelWidth : 70,
		fields : [
				{
					display : $l("type.com.lkkhpg.dsis.common.user.dto.userinfo.memberid"),
					name : "memberCode",
					isAutoComplete : true,
					newline : false,
					type : "text"
				},
				/*{
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.chinesename"),
					name : "chineseName",
					newline : false,
					isAutoComplete : false,
					type : "text"
				},*/
				{
					display : $l("type.com.lkkhpg.dsis.common.member.dto.marketchange.registercode"),
					name : "registerCode",
					isAutoComplete : false,
					newline : false,
					type : "text"
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.englishname"),
					name : 'englishName',
					type : "text",
					newline : true,
					isAutoComplete : false
				} ]
		},
		onselect : function(data) {
			f_setMember(data.data[0]);
		},
		onChangeValue : function(value) {
			if (!value) {
				cleanMember();
			}
		}
	}
	return options;
}




/**
 * 服务中心pupop
 */
function o_serviceCenterPupop() {
	var options = {
		readonly : false,// 是否可编辑
		valueField : 'serviceCenterId',
		textField : 'name',
		autocomplete : true,
		cancelable : false,
		placeholder : "",
		grid : {
			columns : [
					{
						display : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.servicecenter.code'),
						name : 'code',
						isAutoComplete : true,
						autocompleteField : true,
						type : 'int'
					},
					{
						display : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.servicecenter.name'),
						name : 'name',
						isAutoComplete : false,
						autocompleteField : false,
						align : 'left',
						width : 200
					},
					{
						display : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.servicecenter.chargememberid'),
						name : 'chargeMemberId',
						isAutoComplete : false,
						autocompleteField : false,
						align : 'right'
					},
					{
						display : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.servicecenter.phone'),
						isAutoComplete : false,
						autocompleteField : false,
						name : 'phone',
						align : 'right'
					} ],
			// 数据查询的url
			url : _basePath + '/spm/serviceCenterAndMember/query',
			onLoadData : function() {
				if (liger.get('orderType')
						&& liger.get('orderType').getValue() != 'NMDP') {
					this.setParm("marketId", liger.get("marketId").getValue());
				} else if (!liger.get('orderType')) {
					this.setParm("marketId", liger.get("marketId").getValue());
				}
				this.setParm("status", "ALED");
			}
		},
		// lov上的查询条件
		condition : {
			inputWidth : 150,
			labelWidth : 100,
			fields : [
					{
						display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.servicecenter.code"),
						name : "code",
						newline : true,
						isAutoComplete : true,
						type : "text"
					},
					{
						display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.servicecenter.name"),
						name : "name",
						newline : false,
						isAutoComplete : false,
						type : "text"
					} ]
		},
		onselect : function(data) {
			/*f_setMember(data.data[0].member);
			var memberId = data.data[0].member.memberId;
			var memberCode = data.data[0].member.memberCode;
			liger.get("memberId").setValue(memberId);
			liger.get("memberId").setText(memberCode);*/

		},
		validate : {
			required : true
		}
	}
	return options;
}

/**
 * 销售订单表单参数
 * 
 */
function o_omConfirmFormOptions() {

	var options = {
		fields : [
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.createuserid"),
					name : 'createUserId',
					textField : 'createUserName',
					newline : false,
					readonly : true,
					group : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.orderinfo")
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salesmarket"),
					name : 'marketId',
					cancelable : false,
					newline : false,
					readonly : true
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salesorgid"),
					name : 'salesOrgId',
					newline : false,
					cancelable : false,
					readonly : true,
					/*options : {
                        valueField : 'salesOrgId',
                        textField : 'name',
                        url : _basePath +'/sys/salesOrg/queryOrg',
                        isShowCheckBox: false,
                        isMultiSelect: false
                    },*/
                    validate : {
                        required : true
                    }
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.currency"),
					name : 'currency',
					newline : false,
					isShowCheckBox: false,
                    isMultiSelect: false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.ordernumber"),
					name : 'orderNumber',
					newline : true,
					readonly : true
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.orderstatus"),
					name : 'orderStatus',
					newline : false,
					readonly : true,
					options : {
						valueFieldID : "orderStatus",
						valueField : "value",
						textField : "meaning",
						cancelable : false,
						data : orderStatusData,
						focusWhenSetValue :false,
						/*onselected : function(val) {
							validatePeriod();
						}*/
					}
				},
				{
					type : 'date',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.orderdate"),
					newline : false,
					readonly : true,
					name : 'orderDate',
					options : {
						showTime:true,
			            format: "yyyy-MM-dd hh:mm:ss"
					}
				},
				{
					type : 'date',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.paydate"),
					newline : false,
					readonly : true,
					name : 'payDate'
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.ordertype"),
					name : 'orderType',
					newline : true,
					readonly : true,
					options : {
						valueFieldID : "orderType",
						valueField : "value",
						textField : "meaning",
						data : orderTypeData,
						autocomplete : false,
						cancelable : false,
						focusWhenSetValue :false,
					},
					validate : {
						required : true
					}
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.channel"),
					name : 'channel',
					newline : false,
					readonly : true,
					options : {
						valueFieldID : "channel",
						valueField : "value",
						textField : "meaning",
						cancelable : false,
						autocomplete : false,
						focusWhenSetValue :false,
						data : channelData
					},
					validate : {
						required : true
					},
					render : function(data) {
						for ( var i in channelData) {
							if (channelData[i].value == data) {
								return channelData[i].meaning;
							}
						}
					}
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.period"),
					name : 'period',
					newline : false,
					cancelable : false,
					readonly : true,
					options : {
						valueField : "value",
						textField : "meaning",
						focusWhenSetValue :false,
						onBeforeSelect : function(newvalue) {
							return confirm($l('msg.warning.order.payment_date_adjustment')
									+ newvalue);
						}
					}
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.invoicenumber"),
					name : 'invoiceNumber',
					readonly : true,
					newline : false
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.user.dto.userinfo.memberid"),
					name : 'memberId',
					type : "popup",
					newline : true,
					readonly : true,
					cancelable : false,
					/*options : chargeMemberPupop(),*/
					validate : {
						required : true
					},
					group : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.memberinfo")
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.chinesename"),
					name : 'cnName',
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.accountingbalance.exchangebalance"),
					name : 'exchangeBalance',
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.salesscore"),
					name : 'currentPoints',
					newline : false,
					readonly : true
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.servicecenter"),
					name : 'serviceCenterId',
					newline : true,
					cancelable : false,
					readonly : true,
					type : 'popup',
					/*options : o_serviceCenterPupop(),*/
					validate : {
						required : true
					}
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.englishname"),
					name : 'enName',
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.accountingbalance.remainingbalance"),
					name : 'remainingBalance',
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.shortcut_operation.currentmoneypv"),
					name : 'currentPV',
					newline : false,
					readonly : true
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.satffnumber"),
					name : 'staffNum',
					type : "text",
					newline : true,
					readonly : true,
					group : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.satffinfo")
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.satffname"),
					name : 'staffName',
					newline : false,
					readonly : true
				},
				{
					type : 'textarea',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remark"),
					name : 'remark',
					newline : false,
					validate : {
						stringMaxLength: 240
					},
					width : 400,
					group : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remarkinfo")
				},
				{
					type : 'textarea',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.internalremark"),
					name : 'internalRemark',
					newline : false,
					validate : {
						stringMaxLength: 240
					},
					width : 400
				}, {
					type : 'hidden',
					name : 'headerId'
				}, {
					type : 'hidden',
					name : 'logisticsId'
				}, {
					type : 'hidden',
					name : 'periodId'
				}, {
					type : 'hidden',
					name : 'billSiteId'
				}, {
					type : 'hidden',
					name : 'deliverySiteId'
				}, {
					type : 'hidden',
					name : 'memberRole'
				}, 
				// coupon优惠总金额
				{
					type : 'hidden',
					name : 'discountAmt'
				},
				// coupon优惠总PV
				{
					type : 'hidden',
					name : 'discountPvSum'
				},
				// coupon优惠tax
				{
					type : 'hidden',
					name : 'discountTax'
				}]
	};
	return options;
}

/**
 * autoship订单头表单
 */
function o_autoshipCreateFormOptions() {
	var execuDate = [];
	for (var i = 0; i < 28; i++) {
		execuDate.push({
			value : i + 1,
			meaning : i + 1
		});
	}
	var options = {
		fields : [
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.autoshipordernumber"),
					name : 'autoshipNumber',
					newline : false,
					readonly : true,
					group : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.orderinfo")
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.autoshiporderstatus"),
					name : 'autoshipStatus',
					width : 150,
					labelWidth : 120,
					newline : false,
					cancelable : false,
					readonly : true,
					render : function(data) {
						for ( var i in autoShipStatusData) {
							if (autoShipStatusData[i].value == data) {
								return autoShipStatusData[i].meaning;
							}
						}
					}
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salesmarket"),
					name : 'marketId',
					newline : false,
					readonly : true,
					validate : {
						required : true
					}
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salesorgid"),
					name : 'salesOrgId',
					newline : false,
					cancelable : false,
					readonly : false,
					options : {
                        valueField : 'salesOrgId',
                        textField : 'name',
                        url : _basePath +'/sys/salesOrg/queryOrg',
                        isShowCheckBox: false,
                        isMultiSelect: false,
                        onSelected : function(value,text){
                        	var selectdata = this.selected;
                        	defaultSaleOrg = value;

                        	if(!selectdata)return;
                        	var tabId = window.top.tab.getSelectedTabItemID();
                        	var headerId = $("#headerId").val();
                        	window.top.f_removeTab(tabId);
                        	window.top.f_addTab(null, $l('type.com.lkkhpg.dsis.common.order.dto.automatically.create.orders'),
                        			_basePath + "/om/om_autoship_create.html?salesOrgId=" + value );

                        }
                    },
					validate : {
						required : true
					}
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.executedate"),
					name : 'executeDate',
					newline : true,
					readonly : true
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.executetype"),
					name : 'executeType',
					newline : false,
					readonly : true,
					options : {
						valueFieldID : "executeType",
						valueField : "value",
						textField : "meaning",
						cancelable : false,
						data : executeTypeData,
						value : executeTypeData[0].value,
						text : executeTypeData[0].meaning,
						readonly : true,
						render : function(value) {
							if (!value) {
								return;
							}
							for ( var i in executeTypeData) {
								if (executeTypeData[i].value == value) {
									return executeTypeData[i].meaning
								}
							}

						}
					}
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.createuserid"),
					name : 'createUserId',
					textField : 'createUserName',
					newline : false,
					readonly : true,
					validate : {
						required : true
					}
				},
				
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.currency"),
					name : 'currency',
					newline : false,
					readonly : true,
					validate : {
						required : true
					}
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.last_executedate"),
					name : 'latestExecuteDate',
					newline : true,
					readonly : true,
					validate : {
						required : true
					}
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.batchnumber"),
					name : 'latestBatchNumber',
					newline : false,
					readonly : true,
					validate : {
						required : true
					}
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.last_update_member"),
					name : 'lastUpdatedName',
					newline : false,
					readonly : true,
					validate : {
						required : true
					}
				},{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.last_update_date"),
					name : 'lastUpdateDate',
					newline : false,
					readonly : true,
					validate : {
						required : true
					}
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.user.dto.userinfo.memberid"),
					name : 'memberId',
					type : "popup",
					newline : true,
					options : chargeMemberPupop(),
					validate : {
						required : true
					},
					group : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.memberinfo")
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.chinesename"),
					name : 'cnName',
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.accountingbalance.exchangebalance"),
					name : 'exchangeBalance',
					width : 150,
					labelWidth : 120,
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.shortcut_operation.currentmoneypv"),
					name : 'currentPV',
					newline : false,
					readonly : true
				},
				{
					type : 'date',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.orderdate"),
					newline : true,
					cancelable : false,
					readonly : true,
					format : "yyyy/dd/MM",
					name : 'autoshipCreateDate',
					validate : {
						required : true
					}
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.englishname"),
					name : 'enName',
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.accountingbalance.remainingbalance"),
					name : 'remainingBalance',
					width : 150,
					labelWidth : 120,
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.salesscore"),
					name : 'currentPoints',
					newline : false,
					readonly : true
				},

				{
					type : 'textarea',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remark"),
					name : 'remark',
					newline : true,
					width : 500,
					group : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remarkinfo")
				}, {
					type : 'hidden',
					name : 'creditCardId',
					newline : false,
					readonly : true
				}, {
					type : 'hidden',
					name : 'autoshipId',
					newline : false,
					readonly : true
				}, {
					type : 'hidden',
					name : 'logisticsId'
				}, {
					type : 'hidden',
					name : 'billSiteId'
				}, {
					type : 'hidden',
					name : 'deliverySiteId'
				} ]
	};
	return options;
}

function setAutoVisible(){ 
	var autoStatus = om_oc_form.getData().autoshipStatus;
	if ("TER" == autoStatus) {
		om_oc_form.setVisible(["lastUpdatedName"],true);
		om_oc_form.setVisible(["lastUpdateDate"],true);
	} else {
		om_oc_form.setVisible(["lastUpdatedName"],false);
		om_oc_form.setVisible(["lastUpdateDate"],false);
	}
}

/**
 * 订单行货号选择pupop
 */
function o_itemPupop() {
	var options = {
		type : 'popup',
		cancelable : false,
		autocomplete : true,
		valueField : 'itemNumber',// 选中的valueField,文本框有值时grid有选中效果
		textField : 'itemNumber',// 选中的textField,文本框显示的grid字段
		grid : {
			isSingleCheck : true,
			columns : [
					{
						display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.itemid"),
						name : 'itemNumber',
						isAutoComplete : true,
						autocompleteField : true,
						align : 'left',
						width : 200
					},
					{
						display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.itemname"),
						name : 'itemName',
						isAutoComplete : false,
						autocompleteField : true,
						align : 'right',
						type : 'text'
					},
					{
						display : $l("type.com.lkkhpg.dsis.common.product.invitem.dto.description"),
						name : 'description',
						isAutoComplete : false,
						autocompleteField : true,
						align : 'right',
						type : 'text'
					} ],
			// 数据查询的url
			url : _basePath + '/inv/item/queryForOrder',
			onLoadData : function() {
				if (liger.get("channel")) {
					this.setParm("isActive", "Y");
					var channerlValue = liger.get("channel").getValue();
					if (channerlValue == 'FAX') {
						this.setParm("channel", 'STORE');
					} else if (channerlValue == 'DISWB') {
						this.setParm("channel", 'WEB');
					} else if (channerlValue == 'DISAP') {
						this.setParm("channel", 'APP');
					} else if (channerlValue == 'AUTOS') {
						this.setParm("channel", 'AUTOS');
					} else {
						this.setParm("channel", 'STORE');
					}
				} else {
					this.setParm("channel", 'AUTOS');
				}
				var pricetype = f_getPriceType();
				if (pricetype != '0') {
					this.setParm("priceType", pricetype);
					this.setParm("currency", liger.get("currency").getValue());
				}
				if (pricetype == 'RP') {
					this.setParm("redeemFlag", "Y");

				}
				/*
				 * if(memberStatus&&memberRole&&memberStatus=='NEW'&&memberRole=="DIS"){
				 * this.setParm("starterAid",'Y'); }
				 */
				this.setParm("salesOrgId", liger.get("salesOrgId").getValue());
				if (linegrid.editor.editParm.record.defaultInvOrgId) {
					this.setParm("organizationId",
							linegrid.editor.editParm.record.defaultInvOrgId);
				}
			}
		},
		// lov上的查询条件
		condition : {
			inputWidth : 150,
			labelWidth : 70,
			fields : [
					{
						display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.itemid"),
						name : "itemNumber",
						isAutoComplete : true,
						newline : true,
						type : "text"
					},
					{
						display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.itemname"),
						name : "itemName",
						isAutoComplete : false,
						newline : false,
						type : "text"
					} ]
		},
		onselect : function(data) {
			var selected = linegrid.getRow(DsisOrderCreate.editLineIndex);
			if (!selected) {
				return;
			}
			f_setLine(selected, data.data[0]);
			var p = this.options, tr = $(p.host_grid.getRowObj(p.host_grid_row));
			tr.next('tr.l-grid-detailpanel').remove();
			tr.find('.l-grid-row-cell-detailbtn').removeClass('l-open');
		},
		onChangeValue : function(value) {
			if (!value) {
				if (!linegrid.editor.editParm
						|| !linegrid.editor.editParm.record) {
					return;
				}
				var editorData = linegrid.editor.editParm.record;
				linegrid.updateRow(editorData, {
					"itemId" : null,
					"itemNumber" : null,
					"barCode" : null,
					"itemName" : null,
					"description" : null,
					"starterAid" : null,
					"starterAidField" : null,
					"countStockFlag" : null,
					"quantityCountType" : null,
					"minOrderQuantity" : null,
					"itemType" : null,
					"uomCode" : null,
					"publishStatus" : null,
					"publishStatusDesc" : null,
					"lotCtrlFlag" : null,
					"categoryId" : null,
					"invCategory" : null,
					"categoryDesc" : null,
					"categoryIdList" : null,
					"inStore" : null,
					"fax" : null,
					"agencyWeb" : null,
					"agencyApp" : null,
					"autoDeliver" : null,
					"channel" : null,
					"isActive" : null,
					"salesOrgId" : null,
					"currency" : null,
					"priceType" : null,
					"organizationId" : null,
					"priceListDetails" : null,
					"mode" : null,
					"uomName" : null,
					"quantity" : null,
					"organization" : null,
					"countItemName" : null,
					"countItemNumber" : null,
					"packageQuantity" : null,
					"savePropertyFlag" : null,
					"unitDiscountPrice" : null,
					"priceDetail" : null,
					"pv" : null,
					"unitOrigPrice" : null,
					"unitRedeemPoint" : 0,
					"favorableSum" : 0,
					"redeemPoint" : 0,
					"PVSum" : 0,
					"tax" : 0,
					"unitSellingPrice" : 0,
					"amount" : 0
				});
				f_calculateLinePrice();
			}
		}
	}
	return options;
}

/**
 * 订单列信息获取
 * 
 * @param istax
 *            是否含税
 * @param ispoint
 *            是否积分购买
 * @param isself
 *            是否自提
 * @param isauto
 *            是否autoship订单
 * @returns 订单列数组对象
 */
function o_getLineColumns(istax, ispoint, isself, isauto) {
	defaultItemSalesType = "PURC";
	var unit = {
		display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.unitsellingprice"),
		name : 'unitOrigPrice',
		align : 'center',
		type : 'float',
		render : function(data) {
			if (!data || !data.itemId) {
				return;
			}
			if (!data.unitOrigPrice) {
				data.unitOrigPrice = 0;
				return summary.zero;
			}
			if (data.itemSalesType && data.itemSalesType == "FREE") {
				data.unitOrigPrice = 0;
				return summary.zero;
			}
			data.tax = data.unitOrigPrice * summary.taxRate;
			return Number(data.unitOrigPrice).toFixed(summary.precision);
		}
	};
	var amount = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.amount'),
		name : 'amount',
		align : 'center',
		type : 'float',
		render : function(data) {
			if (!data || !data.itemId) {
				return;
			}
			if (!data.quantity) {
				data.quantity = 1;
			}
			if (!data.unitOrigPrice) {
				data.unitOrigPrice = 0;
			}
			if (data.itemSalesType && data.itemSalesType == "FREE") {
				data.unitOrigPrice = 0;
			}
			return Number(data.unitOrigPrice * data.quantity).toFixed(
					summary.precision);
		}
	};
	var saleType = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemsalestype'),
		name : 'itemSalesType',
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
		},
		validate : {
			required : true
		}
	};
	if (!isauto) {
		var salesTypeData = new Array();
		var includeTye = new Array();
		var orderType = liger.get("orderType").getValue();
		switch (orderType) {
		case "STDP": {
			includeTye.push("PURC");
			includeTye.push("EXCH");
			includeTye.push("FREE");
			defaultItemSalesType = "PURC";
			break;
		}
		case "BIGF": {
			includeTye.push("GIFT");
			defaultItemSalesType = "GIFT";
			break;
		}
		case "RDEM": {
			includeTye.push("REDE");
			defaultItemSalesType = "REDE";
			break;
		}
		default: {
			includeTye.push("PURC");
			defaultItemSalesType = "PURC";
			break;
		}
		}
		for ( var i in productSalesTypeData) {
			if ($.inArray(productSalesTypeData[i].value, includeTye) >= 0) {
				salesTypeData.push(productSalesTypeData[i]);
			}
		}
		saleType.editor = {
			type : 'select',
			valueField : "value",
			textField : "meaning",
			cancelable : false,
			data : salesTypeData,
			value : salesTypeData[0].value,
			text : salesTypeData[0].meaning,
			onChanged : function(e) {
				if (orderType == "STDP" && e.record.itemSalesType == "PURC") {
					if (e.record.pv) {
						return;
					}
					var details = e.record.priceDetail;
					if (!details) {
						var param = {
							'itemId' : e.record.itemId,
							'currency' : liger.get("currency").getValue(),
							uomCode : e.record.uomCode,
							salesOrgId : defaultSaleOrg
						};
						$.getJSON(_basePath + "/inv/price/queryPriceByItemId?",
								param, function(data) {
									details = data.rows;
									e.record.priceDetail = data.rows;
									for ( var i in details) {
										if (details[i].priceType == 'PV') {
											e.record.pv = details[i].unitPrice;
										}
									}
								});
					} else {
						for ( var i in details) {
							if (details[i].priceType == 'PV') {
								e.record.pv = details[i].unitPrice;
							}
						}
					}
				} else if (e.record.itemSalesType == "FREE") {
					e.record.unitOrigPrice = 0;
				}
				f_setLinePrice(e.record);
			}
		}
	}
	if (ispoint) {
		unit = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.unitredeempoint'),
			name : 'unitRedeemPoint',
			align : 'center',
			type : 'text'
		};
		amount = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.redeempoint'),
			name : 'redeemPoint',
			align : 'center',
			type : 'text',
			render : function(data) {
				if (!data || !data.itemId) {
					return;
				}
				if (!data.quantity) {
					data.quantity = 1;
				}
				if (!data.unitRedeemPoint) {
					data.unitRedeemPoint = 0;
				}
				return Number(data.unitRedeemPoint * data.quantity).toFixed(
						summary.precision);
			}
		};
	}
	var columns = [
			saleType,
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemid'),
				name : 'itemNumber',
				align : 'center',
				width : 200,
				textField : 'itemNumber',
				editor : o_itemPupop(),
				validate : {
					required : true
				}
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemname'),
				name : 'itemName',
				align : 'center',
				type : 'text'
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pv'),
				name : 'pv',
				align : 'center',
				type : 'text',
				render : function(data) {
					if (!data || !data.itemId) {
						return;
					}
					if (!data.pv) {
						data.pv = 0;
						return;
					}

					var orderType = "STDP";
					if (liger.get("orderType")) {
						orderType = liger.get("orderType").getValue();
					}
					if (orderType == "STDP" && data.itemSalesType == "PURC") {
						if (data.pv) {
							return data.pv;
						}
						var details = data.priceDetail;
						if (!details) {
							var param = {
								'itemId' : data.itemId,
								'currency' : liger.get("currency").getValue(),
								uomCode : data.uomCode,
								salesOrgId : defaultSaleOrg
							};
							$.getJSON(_basePath
									+ "/inv/price/queryPriceByItemId?", param,
									function(json) {
										details = json.rows;
										data.priceDetail = json.rows;
										for ( var i in details) {
											if (details[i].priceType == 'PV') {
												data.pv = details[i].unitPrice;
											}
										}
									});
						} else {
							for ( var i in details) {
								if (details[i].priceType == 'PV') {
									data.pv = details[i].unitPrice;
								}
							}
						}
						if (data.pv == 0) {
							return "0"
						}
						return data.pv;
					} else {
						data.pv = 0;
						return "0";
					}

				}
			},
			unit,
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.unitdiscountprice'),
				name : 'unitDiscountPrice',
				align : 'center',
				type : 'float',
				editor : {
					type : 'select',
					valueField : "value",
					textField : "meaning"
				},
				render : function(data) {
					if (!data || !data.itemId) {
						return;
					}
				}
			},
			{
				display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.quantity"),
				name : 'quantity',
				align : 'center',
				type : 'int',
				editor : {
					type : 'int',
					onChange : function(e) {
						if (e.value > 99999) {
							e.record.quantity = 99999;
							e.value = 99999;
						} else if (e.value < 1) {
							e.record.quantity = 1;
							e.value = 1;
						}
						if (e.value > e.record.quantity) {
							if (e.record.unitRedeemPoint) {
								summary.points += (e.record.quantity - e.value)
										* e.record.unitRedeemPoint;
								/*
								 * if(summary.points>Number(liger.get("currentPoints").getValue())){
								 * $.ligerDialog.error($l('msg.error.order.sales_point_insufficient'));
								 * e.value=e.record.quantity; }
								 */
							}

						}
					},
					onChanged : function(e) {
						f_setLinePrice(e.record);
					}
				},
				validate : {
					required : true,
					digits : true,
					min : 1,
					max : 99999
				}
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.discountprice'),
				name : 'favorableSum',
				align : 'center',
				type : 'float',
				render : function(data) {
					if (!data || !data.itemId) {
						return;
					}
					if (!data.favorableSum || data.favorableSum == 0) {
						return "0";
					}
				}
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pvsum'),
				name : 'PVSum',
				align : 'center',
				type : 'text',
				render : function(data) {
					if (!data || !data.itemId) {
						return;
					}
					if (!data.pv) {
						data.pv = 0;
						return;
					}
					if (!data.quantity) {
						data.quantity = 1;
					}
					data.PVSum = data.pv * data.quantity;
					if (!data.PVSum || data.PVSum == 0) {
						return "0"
					}
					return data.PVSum;
				}
			}, amount ];
	if (istax) {
		var tax = {
			display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.tax"),
			name : 'tax',
			align : 'center',
			type : 'float',
			render : function(data) {
				if (!data || !data.itemId) {
					return;
				}
				if(data.disableTaxFlag == 'Y'){
					if(marketCode == 'CA' && data.itemNumber == '15160502'){
						data.tax = data.unitOrigPrice * 0.07;
					}else{
				         data.tax = data.unitOrigPrice * summary.taxRate;
					}
				}else{
					data.tax = 0;
				}
				return Number(data.tax).toFixed(summary.precision);
			}
		};
		columns.splice(5, 0, tax);
	}
	if (!isauto) {
		var invOrg = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.invorg'),
			name : 'defaultInvOrgId',
			align : 'center',
			width : 200,
			type : 'text',
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
		};
		if (isself) {
			invOrg.editor = {
				type : 'select',
				valueField : "invOrgId",
				textField : "name",
				cancelable : false,
				staticOptions : function(e) {
					e.column.editor.data = invOrgs;
				}
			};
			invOrg.validate = {
				required : true
			};
		}
		columns.unshift(invOrg);
	}
	return columns;
}

/**
 * 增加信用卡记录的form
 */
/*
 * function o_addCreditForm(){ var options = { fields : [ { type : 'text',
 * display :
 * $l("type.com.lkkhpg.dsis.common.order.dto.creditcard.creditcardnum"), name :
 * 'creditCardNum', width : 290, newline : false,
 * validate:{required:true,maxlength:16,minlength:5} },{ type : 'select',
 * display : $l("type.com.lkkhpg.dsis.common.order.dto.creditcard.bankcode"),
 * name : 'bankCode', width:120, newline : true, options : { valueFieldID :
 * "bankCode", valueField : "value", textField : "meaning", cancelable: false,
 * data : bankBelongData, value:bankBelongData[0].value,
 * text:bankBelongData[0].meaning }, validate:{required:true} }, { type :
 * 'text', display :
 * $l("type.com.lkkhpg.dsis.common.order.dto.creditcard.cardholder"), name :
 * 'cardHolder', width:70, newline :false, validate:{required:true} },{ type :
 * 'text', display :
 * $l("type.com.lkkhpg.dsis.common.order.dto.creditcard.validyear"), name :
 * 'validYear', width:50, newline : true, validate:{required:true,digits:true
 * ,min:00,maxlength:2,max:99} },{ type : 'text', display :
 * $l("type.com.lkkhpg.dsis.common.order.dto.creditcard.validmonth"), width:50,
 * name : 'validMonth', newline : false, validate:{required:true,digits:true
 * ,min:1,max:12} } ], validate: true,space:10 }; return options; }
 * 
 */
/**
 * 订单行表格的参数
 * 
 * @param isauto
 *            是否autoship订单
 * @param userType
 *            用户类型
 */
function o_ligerGridOptions(isauto, userType) {
	var deleteUrl = _basePath + '/om/order/line/remove';
	if (isauto) {
		deleteUrl = _basePath + '/om/autoship/deleteLine';
	}
	var options = {
		columns : o_getCreateLineColumns(isauto, false, true, userType),
		detail : {
			onShowDetail : f_getItemPackageDetail,
			height : 'auto'
		},
		enabledEdit : true,
		usePager : false,
		frozen : false,
		width : '98%',
		checkbox : true,
		onBeforeEdit : function(e) {
			DsisOrderCreate.editLineIndex = e.rowindex;
		},
		toolbar : {
			items : [ {
				text : $l("sys.hand.btn.new"),
				disable : addLineBtnDisabled,
				id : "addLineBtn",
				click : function() {
					addLine(isauto);
				},
				icon : 'add'
			}, {
				line : true
			}, {
				text : $l("sys.hand.btn.delete"),
				disable : addLineBtnDisabled,
				id : "deleteLineBtn",
				click : function() {
					Hap.gridDelete({
						grid : linegrid,
						url : deleteUrl,
						success : function(json, opt) {
							f_calculateLinePrice();
						},
						failure : function(json, opt) {
							f_calculateLinePrice();
						}
					})

				},
				icon : 'delete'
			} ]
		}
	};
	return options;
}

/**
 * 地址表格的参数
 */
function o_addressGridOptions(dataOption) {
	var displayName = $l('type.com.lkkhpg.dsis.common.member.dto.membersite.receivegoodsname');
	if (dataOption) {
		displayName = $l('type.com.lkkhpg.dsis.common.member.dto.membersite.receivebillsname');
	}
	var options = {
		unSetValidateAttr : false,
		usePager : false,
		checkbox : true,
		isSingleCheck : true,
		enabledEdit : false,
		width : '98%',
		columns : [
				{
					display : displayName,
					name : 'name',
					type : 'text',
					align : 'center'
				},
				{
					display : $l('type.com.lkkhpg.dsis.common.member.dto.membersite.phone'),
					name : 'phone',
					align : 'center'
				},
				{
					display : $l('type.com.lkkhpg.dsis.common.member.dto.membersite.address'),
					name : 'address',
					align : 'center',
					width : 400,
					type : 'text'
				},
				{
					display : $l('type.com.lkkhpg.dsis.common.member.dto.membersite.spmlocation.zipcode'),
					name : 'spmLocation.zipCode',
					align : 'center',
					type : 'text'
				},
				{
					display : $l('type.com.lkkhpg.dsis.common.member.dto.membersite.defaultflag'),
					name : 'defaultFlag',
					align : 'center',
					type : 'text',
					render : function(rowdata, rowindex, value) {
						if (!value || value == "N") {
							return "";
						} else {
							return value;
						}
					}
				},
				{
					display : $l('sys.hand.btn.action'),
					name : 'UnitsInStock3',
					align : 'center',
					type : 'text',
					render : function(rowdata, rowindex, value) {
						var h = "";
						h += "<a href='javascript:beginEdit(" + dataOption
								+ "," + rowindex + ")'>"
								+ $l('sys.hand.btn.edit') + "</a> ";
						h += "<a href='javascript:deleteRow(" + dataOption
								+ "," + rowindex + ")'>"
								+ $l('sys.hand.btn.delete') + "</a> ";
						return h;
					}
				} ],
		isChecked : function(rowdata) {
			if (rowdata.select) {
				if (((rowdata.siteUseCode && rowdata.siteUseCode == "BILL") || (rowdata.siteType && rowdata.siteType == "BILL"))
						&& $("#billSiteId").val()) {
					return true;
				} else if (((rowdata.siteUseCode && rowdata.siteUseCode == "SHIP") || (rowdata.siteType && rowdata.siteType == "SHIP"))
						&& $("#deliverySiteId").val()) {
					return true;
				}
				return false;
			} else if (rowdata.defaultFlag && rowdata.defaultFlag == "Y") {
				if (((rowdata.siteUseCode && rowdata.siteUseCode == "BILL") || (rowdata.siteType && rowdata.siteType == "BILL"))
						&& !$("#billSiteId").val()) {
					return true;
				} else if (((rowdata.siteUseCode && rowdata.siteUseCode == "SHIP") || (rowdata.siteType && rowdata.siteType == "SHIP"))
						&& !$("#deliverySiteId").val()) {
					return true;
				}
				return false;
			}
			return false;
		}
	}
	if (dataOption) {
		options.toolbar = {
			items : [
					{
						text : $l("sys.hand.btn.new"),
						disable : addLineBtnDisabled,
						id : "addLineBtn",
						click : function() {
							if (liger.get("memberId").getValue()) {
								om_location_edit(_basePath, {}, function(d) {
									f_addAddressWithMember(d, dataOption,
											addressgrid);
								},liger.get("salesOrgId").getValue());
							} else {
								om_location_edit(_basePath, {}, function(d) {
									f_addAddressWithoutMember(d, dataOption,
											addressgrid);
								},liger.get("salesOrgId").getValue());
							}

						},
						icon : 'add'
					}, {
						line : true
					} ]
		}
	} else {
		options.toolbar = {
			items : [
					{
						text : $l("sys.hand.btn.new"),
						disable : addLineBtnDisabled,
						id : "addLineBtn",
						click : function() {
							if (liger.get("memberId").getValue()) {
								om_location_edit(_basePath, {}, function(d) {
									f_addAddressWithMember(d, dataOption,
											deliveryaddress);
								},liger.get("salesOrgId").getValue());
							} else {
								om_location_edit(_basePath, {}, function(d) {
									f_addAddressWithoutMember(d, dataOption,
											deliveryaddress);
								},liger.get("salesOrgId").getValue());
							}
						},
						icon : 'add'
					}, {
						line : true
					} ]
		}
		options.onSelectRow = f_queryShippingTier;
	}
	return options;
}

/**
 * 订单为非会员购买时添加地址
 * 
 * @param address
 *            地址信息
 * @param dataOption
 *            true 账单地址 /false 配送地址
 * @param grid
 *            成功后操作的grid
 */
function f_addAddressWithoutMember(address, dataOption, grid) {
	var url = _basePath + '/om/sites/submit';
	var siteCode = "SHIP";
	if (dataOption) {
		siteCode = "BILL";
	}
	var requestData = {
		headerId : $("#headerId").val(),
		salesOrgId : liger.get("salesOrgId").getValue(),
		siteType : siteCode,
		name : address.memberName,
		phone : address.phoneNumber,
		cityCode : address.cityCode,
		stateCode : address.stateCode,
		countryCode : address.countryCode,
		address : address.address,
		areaCode : address.areaCode,
		zipCode : address.zipCode,
		autoshipFlag : "N",
		address1 : address.addressLine1,
		address2 : address.addressLine2,
		address3 : address.addressLine3
	};
	if (dataOption) {
		requestData.salesSiteId = $("#billSiteId").val();
	} else {
		requestData.salesSiteId = $("#deliverySiteId").val();
	}
	Hap.ajax({
		url : url,
		data : requestData,
		success : function(rowdata) {
			var newAddressData = rowdata.rows[0];
			newAddressData.spmLocation = {};
			newAddressData.spmLocation.zipCode = rowdata.rows[0].zipCode;
			newAddressData.defaultFlag = "Y";
			grid.addRow(newAddressData);
			grid.select(newAddressData);
			if (dataOption) {
				$("#billSiteId").val(newAddressData.salesSiteId);
			} else {
				$("#deliverySiteId").val(newAddressData.salesSiteId);
			}
		}

	});
}

/**
 * 订单为会员购买时添加地址
 * 
 * @param address
 *            地址信息
 * @param dataOption
 *            true 账单地址 /false 配送地址
 * @param grid
 *            成功后操作的grid
 */
function f_addAddressWithMember(address, dataOption, grid) {
	var url = _basePath + '/mm/memsite/save';
	var defaultFlag = 'N';
	if (address.save) {
		defaultFlag = 'Y';
	}
	var siteCode = "SHIP";
	if (dataOption) {
		siteCode = "BILL";
	}
	var data = [ {
		"memberId" : liger.get("memberId").getValue(),
		"siteUseCode" : siteCode,
		"name" : address.memberName,
		"phone" : address.phoneNumber,
		"defaultFlag" : defaultFlag,
		"address" : address.address,
		"areaCode" : address.areaCode,
		"spmLocation" : {
			"countryCode" : address.countryCode,
			"countryName" : address.countryName,
			"stateName" : address.stateName,
			"stateCode" : address.stateCode,
			"cityCode" : address.cityCode,
			"cityName" : address.cityName,
			"addressLine1" : address.addressLine1,
			"addressLine2" : address.addressLine2,
			"addressLine3" : address.addressLine3,
			"zipCode" : address.zipCode
		}
	} ];
	Hap.ajax({
		url : url,
		data : data,
		success : function(rowdata) {
			if (rowdata.rows[0].defaultFlag == "Y") {
				var data = grid.records;
				for ( var i in data) {
					if (data[i].defaultFlag == "Y") {
						data[i].defaultFlag = "N";
						grid.updateRow(data[i]);
						break;
					}
				}
			}
			grid.addRow(rowdata.rows[0])
			grid.reRender();
		}
	});

}

/**
 * 编辑会员地址
 * 
 * @param dataOption
 *            true 账单地址 /false 配送地址
 * @param rowindex
 *            行号
 */
function f_editAddressWithMember(dataOption, rowindex) {

	var rowData;
	if (dataOption) {
		rowData = addressgrid.getRow(rowindex);
	} else {
		rowData = deliveryaddress.getRow(rowindex);
	}
	// 组合数据

	var save;
	if (rowData.defaultFlag && rowData.defaultFlag == 'Y') {
		save = true;
	} else {
		save = false
	}
	var editData = {

		"memberId" : rowData.memberId,
		"memberName" : rowData.name,
		"phoneNumber" : rowData.phone,
		"countryCode" : rowData.spmLocation.countryCode,
		"stateCode" : rowData.spmLocation.stateCode,
		"cityCode" : rowData.spmLocation.cityCode,
		"addressLine1" : rowData.spmLocation.addressLine1,
		"addressLine2" : rowData.spmLocation.addressLine2,
		"addressLine3" : rowData.spmLocation.addressLine3,
		"zipCode" : rowData.spmLocation.zipCode,
		"save" : save,
		"address" : rowData.address,
		"areaCode" : rowData.areaCode
	}
	om_location_edit(_basePath, editData, function(d) {

		var defaultFlag = 'N';
		if (d.save) {
			defaultFlag = 'Y';
		}
		// 更新数据
		var url = _basePath + '/mm/memsite/save';
		if (!rowData.memberId) {
			rowData.memberId = liger.get("memberId").getValue();
		}
		rowData.name = d.memberName;
		rowData.phone = d.phoneNumber;
		rowData.defaultFlag = defaultFlag;
		rowData.address = d.address;
		rowData.spmLocation.countryCode = d.countryCode;
		rowData.spmLocation.countryName = d.countryName;
		rowData.spmLocation.stateName = d.stateName;
		rowData.spmLocation.stateCode = d.stateCode;
		rowData.spmLocation.cityCode = d.cityCode;
		rowData.spmLocation.cityName = d.cityName;
		rowData.spmLocation.addressLine1 = d.addressLine1;
		rowData.spmLocation.addressLine2 = d.addressLine2;
		rowData.spmLocation.addressLine3 = d.addressLine3;
		rowData.spmLocation.zipCode = d.zipCode;
		rowData.areaCode = d.areaCode;

		if (dataOption) {
			// 账单地址
			rowData.siteUseCode = "BILL";
		} else {
			rowData.siteUseCode = "SHIP";
		}
		var array = [ rowData ];
		$.ajax({
			url : url,
			type : 'POST',
			dataType : "json",
			contentType : "application/json",
			data : JSON2.stringify(array),
			success : function(json) {
				json.rows[0].__index = rowData.__index;
				json.rows[0].__id = rowData.__id;
				json.rows[0].__nextid = rowData.__nextid;
				json.rows[0].__previd = rowData.__previd;
				if (dataOption) {
					if (json.rows[0].defaultFlag == "Y") {
						var data = addressgrid.rows;
						for ( var i in data) {
							if (data[i].defaultFlag == "Y" && i != rowindex) {
								data[i].defaultFlag = "N";
								addressgrid.updateRow(data[i]);
								// break;
							}
						}
					}
					addressgrid.updateRow(json.rows[0].__id, json.rows[0]);
					addressgrid.reRender();
				} else {
					if (json.rows[0].defaultFlag == "Y") {
						var data = deliveryaddress.rows;
						for ( var i in data) {
							if (data[i].defaultFlag == "Y" && i != rowindex) {
								data[i].defaultFlag = "N";
								deliveryaddress.updateRow(data[i]);
								// break;
							}
						}
					}
					deliveryaddress.updateRow(json.rows[0].__id, json.rows[0]);
					deliveryaddress.reRender();
				}

			},
			error : function() {
				$.ligerDialog.closeWaitting();
			}
		});
	},liger.get("salesOrgId").getValue());
}

/**
 * 编辑非会员地址
 * 
 * @param dataOption
 *            true 账单地址 /false 配送地址
 * @param rowindex
 *            行号
 */
function f_editAddressWithOutMember(dataOption, rowindex) {
	var rowData;
	if (dataOption) {
		rowData = addressgrid.getRow(rowindex);
	} else {
		rowData = deliveryaddress.getRow(rowindex);
	}
	// 组合数据
	var save = true;
	var editData = {
		"memberName" : rowData.name,
		"phoneNumber" : rowData.phone,
		"countryCode" : rowData.countryCode,
		"stateCode" : rowData.stateCode,
		"cityCode" : rowData.cityCode,
		"zipCode" : rowData.zipCode,
		"save" : save,
		"addressLine1" : rowData.address1,
		"addressLine2" : rowData.address2,
		"addressLine3" : rowData.address3,
		"address" : rowData.address,
		"areaCode" : rowData.areaCode
	}
	om_location_edit(_basePath, editData, function(address) {

		var defaultFlag = 'Y';
		// 更新数据
		var url = _basePath + '/om/sites/submit';
		var siteCode = "SHIP";
		if (dataOption) {
			siteCode = "BILL";
		}
		var requestData = {
			headerId : $("#headerId").val(),
			salesOrgId : liger.get("salesOrgId").getValue(),
			siteType : siteCode,
			salesSiteId : rowData.salesSiteId,
			name : address.memberName,
			phone : address.phoneNumber,
			cityCode : address.cityCode,
			stateCode : address.stateCode,
			countryCode : address.countryCode,
			address : address.address,
			areaCode : address.areaCode,
			zipCode : address.zipCode,
			/*autoshipFlag : "N",*/
			address1 : address.addressLine1,
			address2 : address.addressLine2,
			address3 : address.addressLine3
		};
		if ($('#autoshipId')) {
			requestData.autoshipFlag = 'Y';
		} else {
			requestData.autoshipFlag = 'N';
		}
		$.ajax({
			url : url,
			type : 'POST',
			dataType : "json",
			async : false,
			contentType : "application/json",
			data : JSON2.stringify(requestData),
			success : function(json) {
				json.rows[0].__index = rowData.__index;
				json.rows[0].__id = rowData.__id;
				json.rows[0].__nextid = rowData.__nextid;
				json.rows[0].__previd = rowData.__previd;
				json.rows[0].spmLocation = {};
				json.rows[0].spmLocation.zipCode = json.rows[0].zipCode;
				json.rows[0].defaultFlag = "Y";
				if (dataOption) {
					addressgrid.updateRow(json.rows[0].__id, json.rows[0]);
					addressgrid.reRender();
				} else {
					deliveryaddress.updateRow(json.rows[0].__id, json.rows[0]);
					deliveryaddress.reRender();
				}
			},
			error : function() {
				$.ligerDialog.closeWaitting();
			}
		});
	},liger.get("salesOrgId").getValue());

}

/**
 * 编辑地址.
 * 
 * @param 地址类型
 *            false 送货地址 true 账单地址
 * @param grid
 *            id add by 张闯胜
 */
function beginEdit(dataOption, rowindex) {
	var rowData;
	if (dataOption) {
		rowData = addressgrid.getRow(rowindex);
	} else {
		rowData = deliveryaddress.getRow(rowindex);
	}
	if (liger.get("memberId").getValue() && rowData.memberId) {
		f_editAddressWithMember(dataOption, rowindex);
	} else {
		f_editAddressWithOutMember(dataOption, rowindex);
	}
}
/**
 * 删除地址
 * 
 * @param 地址类型
 *            false 送货地址 true 账单地址
 * @param grid
 *            id add by 张闯胜
 */

function deleteRow(dataOption, rowindex) {

	$.ligerDialog
			.confirm(
					$l('sys.hand.tip.delete_confirm'),
					$l('sys.hand.tip.info'),
					function(yes) {
						if (yes) {

							var url = _basePath + '/mm/memsite/delete';
							if (!liger.get("memberId").getValue()) {
								url = _basePath + '/om/sites/delete';
								var siteId;
								if (dataOption) {
									siteId = addressgrid.getRow(rowindex).salesSiteId;
								} else {
									siteId = deliveryaddress.getRow(rowindex).salesSiteId;
								}
								url = url + "?siteId=" + siteId;
							} else {
								var memSiteId;
								if (dataOption) {
									memSiteId = addressgrid.getRow(rowindex).siteId;
								} else {
									memSiteId = deliveryaddress
											.getRow(rowindex).siteId;
								}
								url = url + "?memSiteId=" + memSiteId;
							}

							Hap.ajax({
								url : url,
								success : function(rowdata) {
									if (rowdata.success) {
										if (dataOption) {
											addressgrid.remove(addressgrid
													.getRow(rowindex));
										} else {
											deliveryaddress
													.remove(deliveryaddress
															.getRow(rowindex));
										}
									}
								}
							});
						}
					});
}

/**
 * 新增订单行
 * 
 * @param isautoship
 *            是否自动订货单
 */
function addLine(isautoship) {
	var object = {
		itemSalesType : defaultItemSalesType
	};
	if (!isautoship) {
		object.defaultInvOrgId = defaultInvOrg;
	}
	linegrid.addRow(object);
}

function addRows(isautoship, lineNumber) {
	if (!lineNumber) {
		lineNumer = 1;
	}
	for (var i = 0; i < lineNumber; i++) {
		addLine(isautoship);
	}
}
/**
 * 校验奖金日期 1.订单状态=已保存/待付款/支付失败/已完成时可以更改 2.每月1-3号（可配置） 3.被销售订单经理更改
 */
function validatePeriod() {
	var cdate = new Date();
	var month = cdate.getMonth() + 1;
	var year = cdate.getFullYear();

	var period = liger.get("period");
	var now = "";
	var last = "";
	// 获取当前年和月份作为下拉框选项
	if (month == 1) {
		now = year + "01";
		last = (year - 1) + "12";
	} else if (month < 10) {
		now = year + "0" + month;
		last = year + "0" + month - 1;
	} else if (month == 10) {
		now = year + "10";
		last = year + "09";
	} else {
		now = year.toString() + month;
		last = year.toString() + (month - 1);
	}

	period.setData([ {
		meaning : now,
		value : now
	}, {
		meaning : last,
		value : last
	} ]);

	period.setValue(now);

	var period = liger.get("period");
	var orderStatus = liger.get('orderStatus').getValue();
	switch (orderStatus) {
	case "SAV":
	case "WPAY":
	case "FAIL":
	case "COMP":
		break;
	default:
		period.set({
			readonly : true
		});
		return;
	}

	var date = cdate.getDate();
	if (date > 3) {
		period.set({
			readonly : true
		});
		return;
	}
	period.set({
		readonly : true
	});
}

/**
 * 校验配送类型
 * 
 * @param isautoship
 *            是否autoship订单
 */
function validateDeliver(isautoship) {
	var deliveryType = liger.get("deliveryType");
	type = deliveryType.getValue();
	var isPoint = false;
	if (liger.get("orderType") && liger.get("orderType").getValue() == 'RDEM') {
		isPoint = true;
	}
	if (type == "PCKUP") {
		if (isautoship) {
			$("#addressPanel").hide();
			linegrid.set({
				columns : o_getCreateLineColumns(isautoship, isPoint, true,
						userType)
			});
			linegrid.reRender();
		} else {
			$("#addressPanel").hide();
			//运费一块对coupon的影响--判断空来实现是否重新加载grid
			var coupon = $("#coupons").val();
			if(coupon == ""){
				linegrid.set({
					columns : o_getConfirmLineColumns(isPoint)
				});
			}
			linegrid.reRender();
			liger.get("isCod").setValue(false);
			//liger.get("freeShipping").setValue(false);
		}
		if (Number(summary.currency - -summary.adjustMents - summary.discountAmt) >= 0) {
			$("#actrualPayAmt").text(
					Number(
							summary.currency - -summary.adjustMents
									- summary.discountAmt).toFixed(
							summary.precision));
		} else {
			Hap.showError($l("msg.error.om.actual_payment_amount_error"));
			$("#actrualPayAmt").text(summary.zero);
		}

		reSetLine(false, false, true);
	} else if (type == "SHIPP") {
		if (linegrid && isautoship) {
			linegrid.set({
				columns : o_getCreateLineColumns(isautoship, isPoint, false,
						userType)
			});
			linegrid.reRender();
		} else {
			//coupon不为空的时候grid不重新加载
			var coupon = liger.get('coupons').getValue();
			//var coupons = $("#coupons").val();
			if(coupon == ""){
				linegrid.set({
					columns : o_getConfirmLineColumns(isPoint)
				});
			}
			linegrid.reRender();
		}
		reSetLine(false, false, true);
        $("#shippingTierTax").text(Number($("#shippingTierTax").text()).toFixed(summary.precision));
        $("#shippingTier").text(Number($("#shippingTier").text()).toFixed(summary.precision));
		$("#addressPanel").show();
		
		if (deliveryaddress) {
			deliveryaddress.reRender();
		}
		if (Number(summary.currency - -summary.shippingTier
				- -summary.adjustMents - summary.discountAmt) >= 0) {
			$("#actrualPayAmt").text(
							Number(
									summary.currency - -summary.shippingTier
											- -summary.adjustMents
											- summary.discountAmt).toFixed(
									summary.precision));
		} else {
			Hap.showError($l("msg.error.om.actual_payment_amount_error"));
			$("#actrualPayAmt").text(summary.zero);
		}

	}
}
/**
 * 更改订单类型和地址更改税率.
 * 
 * @param 更改地址
 */
function changgeSiteUpdatetax(address){
	if(!changgetax)return;
	var salesOrgId = liger.get("salesOrgId").getValue();
	var orderId =  $("#headerId").val();
	var deliveryType = liger.get("deliveryType").getValue();

	var parm =  "?salesOrgId="+salesOrgId+"&orderId="+orderId+"&deliveryType="+deliveryType;
	if(address){
		parm = parm + "&countryCode="+address.countryCode +"&stateCode="+address.stateCode+"&cityCode="+address.cityCode+"&zipCode="+address.zipCode;
	}
	$.ajax({
		url: _basePath + "/om/order/changgedelivery"+parm,
		async : false,
		success :function(json){
			//
			if(json.rows){
				var order = json.rows[0];
				
				if(order.attribute6 == 'Y'){
					summary.priceIncludeTax = true;
				}else{
					summary.priceIncludeTax = false;
				}
				
				if(order.attribute7 == 'Y'){
					summary.enableTax = true;
					$("#salesLineSummary tr:eq(1)").show();
				}else{
					summary.enableTax = false;
					$("#salesLineSummary tr:eq(1)").hide();
				}
				var isPoint = false;
				if (liger.get("orderType") && liger.get("orderType").getValue() == 'RDEM') {
					isPoint = true;
				}
				linegrid.set({
					columns : o_getConfirmLineColumns(isPoint)
				});
				var newData = {};
				newData.rows = order.lines;
				linegrid.set({
					data : newData
				});
				linegrid.reRender();
				f_setOrderPrice(order);
			}
		}
		
	});
}
/**
 * 校验
 * 
 * @param isautoship
 */
function validate(isautoship) {
	if (!isautoship) {
		validatePeriod();
	} else {
		for ( var i in autoShipStatusData) {
			if (autoShipStatusData[i].value == "NEW") {
				liger.get("autoshipStatus").setText(
						autoShipStatusData[i].meaning);
				break;
			}
		}
	}
	if (liger.get("deliveryType")) {
		validateDeliver(isautoship);
	}

}

/**
 * 页面初始化
 * 
 * @param isautoship
 */
function init(isautoship) {
	initButton(isautoship);
	$("#deliveryType").ligerComboBox({
		css : 'l-text-required',
		data : deliveryTypeData,
		valueField : "value",
		textField : "meaning",
		cancelable : false,
		focusWhenSetValue : false,
		readonly : false,
		onSelected : function(value) {
			validateDeliver(isautoship);
		},
		validate : {
			required : true
		},
		render : function(data) {
			if (!data) {
				return;
			}
			for ( var i in deliveryTypeData) {
				if (deliveryTypeData[i].value == data) {
					return deliveryTypeData[i].meaning;
				}
			}

		}
	});

	var defaultType = "SHIPP";
	if (!isautoship) {
		defaultType = "PCKUP";
	}
	for ( var i in deliveryTypeData) {
		if (deliveryTypeData[i].value == defaultType) {
			liger.get("deliveryType").setValue(defaultType);
			liger.get("deliveryType").setText(deliveryTypeData[i].meaning);
			break;
		}
	}
	$("#deliveryCompany")
			.ligerComboBox(
					{
						css : 'combobox',
						valueField : "shippingTierId",
						textField : "shippingTierName",
						cancelable : false,
						focusWhenSetValue : true,
						onSelected : function(value, text, data) {
							var price = Number.MAX_VALUE;

							if (data) {
								// 计算运费的金额
								var calMoney;
								// 是否包含运费
								if (data.privilegeFlag == 'Y') {
									// 计算方式
									if (data.calculationType == 'AFTAX') {
										calMoney = $("#beforeTax").text()
												- summary.discountAmt;
									} else {
										calMoney = $("#afterTax").text()
												- summary.discountAmt;
									}
								} else {
									if (data.calculationType == 'AFTAX') {
										calMoney = $("#beforeTax").text();
									} else {
										calMoney = $("#afterTax").text();
									}
								}
								for ( var i in data.shippingTierDtls) {
									if (data.shippingTierDtls[i].shippingFee < price
											&& calMoney >= data.shippingTierDtls[i].invAmountFrom
											&& (!data.shippingTierDtls[i].invAmountTo || calMoney < data.shippingTierDtls[i].invAmountTo)) {
										//包邮
										if(liger.get("freeShipping")&&liger.get("freeShipping").getValue()){
											summary.shippingTier = 0;
										}else{
											summary.shippingTier = data.shippingTierDtls[i].shippingFee;
										}
										$("#shippingTier").text(summary.shippingTier);
										if (liger.get("deliveryType").getValue() != "SHIPP") {
											if (Number(summary.currency - -summary.adjustMents - summary.discountAmt) >= 0) {
												$("#actrualPayAmt").text(Number(summary.currency - -summary.adjustMents- summary.discountAmt).toFixed(summary.precision));
											} else {
												Hap.showError($l("msg.error.om.actual_payment_amount_error"));
												$("#actrualPayAmt").text(summary.zero);
											}

										} else {
											if (Number(summary.currency - -summary.shippingTier - -summary.adjustMents- summary.discountAmt) >= 0) {
												$("#actrualPayAmt").text(Number(summary.currency
																				- -summary.shippingTier
																				- -summary.adjustMents
																				- summary.discountAmt)
																		.toFixed(summary.precision));
											} else {
												Hap.showError($l("msg.error.om.actual_payment_amount_error"));
												$("#actrualPayAmt").text(summary.zero);
											}

										}

									}
								}

							}
						}
					});
	$("#creditCardId").ligerComboBox({
		css : 'combobox',
		display : '选择信用卡',
		valueField : "cardId",
		textField : "bankCode",
		cancelable : false,
		focusWhenSetValue : false,
		onSelected : function(value, text, data) {
			if (data) {
				for (var j = 0; j < creditCardType.length; j++) {
					if (creditCardType[j].value == data.cardSubType) {
						$("#bankType").text(creditCardType[j].meaning);
					}
				}
			}
		}

	});
	if (!isautoship) {
		$("#payAdjust")
				.ligerPanel(
						{
							title : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.adjustment'),
							width : '98%',
							height : 'auto'
						});
		liger.get("payAdjust").collapse();
		$("#coupons").ligerComboBox({
			css : 'combobox',
			valueField : "value",
			textField : "meaning",
			cancelable : false
		});
		// 人工调整里面的下拉框调整
		$("#adjustType").ligerComboBox({
			width : 120,
			data : adjustTypeData,
			valueField : "value",
			textField : "meaning",
			cancelable : false
		});
		liger.get('serviceCenterId').set({
			readonly : true
		});
		liger.get('adjustType').setValue("add");
		for ( var i in adjustTypeData) {
			if (adjustTypeData[i].value == "add") {
				liger.get('adjustType').setText(adjustTypeData[i].meaning);
			}
		}
		if (liger.get("orderStatus").getValue() != "COMP") {
			var files = new Array();
			files.push("sourceKey");
			files.push("batchNumber");
			om_oc_form.setVisible(files, false);
		}
	}
	validate(isautoship);
}

/**
 * 提交销售订单
 */
function f_sumbitSales() {
	//#POS2SUPT-1096 20170613 BEGIN
	if(zipTaxFlag == 'Y'){
		var flag = "N";
		if(deliveryaddress.selected["0"] != null && deliveryaddress.selected["0"].spmLocation.zipCode !=null && deliveryaddress.selected["0"].spmLocation.zipCode != "" && liger.get("deliveryType").getValue() == "SHIPP"){
	    $.ajaxSetup({async : false});
		$.ajax({
	        type : 'POST',
	        //#PE20-214 2017-07-24 BEGIN
	        url  : _basePath + "/spm/zipcityrel/queryEndActiveDate?zipCode="+ deliveryaddress.selected["0"].spmLocation.zipCode+"&countryCode="+deliveryaddress.selected["0"].spmLocation.countryCode,
	        //#PE20-214 2017-07-24 END
	        success : function(json){
	       	 if(json&&json.code == "N"){
	       		flag = "Y";
	        }
	    }
	    });
		$.ajaxSetup({async : true});}
		if(flag == "Y"){Hap.showError($l('msg.warn.order.zipcodefailure'));return;}
	}
	//#POS2SUPT-1096 20170613 END
	if (Number($("#actrualPayAmt").text()) < 0) {
		Hap.showError($l("msg.error.om.actual_payment_amount_error"));
		return;
	}
	if(liger.get("deliveryType").getValue() == "SHIPP"&&!liger.get("freeShipping").getValue()&&includeFreeShippingData(false)){
	$.ligerDialog.confirm(
			$l('msg.warn.om.exist_free_shipping_item'),
			$l('sys.hand.tip.info'),
			function(yes) {
				if (yes) {
					f_sumbitSale();
				}
			});
	}else{
		f_sumbitSale();
	}
}

/**
 * 提交销售订单
 */
function f_sumbitSale() {
	if (!checkPayment()) {
		$.ligerDialog
				.confirm(
						$l('msg.warn.order.actrualpayamtless').replace('{1}',
								freePayMent)
								+ freeFreight,
						$l('sys.hand.tip.info'),
						function(yes) {
							if (yes) {
								$('#shippingTier').text(
										Number(
												Number(summary.shippingTier)
														+ Number(freeFreight))
												.toFixed(summary.precision));
								if (liger.get("deliveryType").getValue() != "SHIPP") {
									if (Number((summary.currency
											- -summary.adjustMents
											- summary.discountAmt + Number(freeFreight)))) {
										$("#actrualPayAmt")
												.text(
														Number(
																(summary.currency
																		- -summary.adjustMents
																		- summary.discountAmt + Number(freeFreight)))
																.toFixed(
																		summary.precision));
									} else {
										Hap.showError($l("msg.error.om.actual_payment_amount_error"));
										$("#actrualPayAmt").text(summary.zero);
									}

								} else {
									if (Number((summary.currency
											- -summary.shippingTier
											- -summary.adjustMents
											- summary.discountAmt + Number(freeFreight))) >= 0) {
										$("#actrualPayAmt")
												.text(
														Number(
																(summary.currency
																		- -summary.shippingTier
																		- -summary.adjustMents
																		- summary.discountAmt + Number(freeFreight)))
																.toFixed(
																		summary.precision));
									} else {
										Hap.showError($l("msg.error.om.actual_payment_amount_error"));
										$("#actrualPayAmt").text(summary.zero);
									}

								}
								if (!valideBefore()) {
									return;
								}
								f_save(false, true);
							}
						});
	} else {
		if (!valideBefore()) {
			return;
		}
		f_save(false, true);
	}
}

/**
 * 保存订单
 */
function f_save(isSave, isSumbit, isNext, autoSave) {
	if ($(liger.get("remark").element).hasClass("l-text-field error")) {
		return;
	}
	if (isSave) {
		var orderType = liger.get("orderType").getValue();
		if (!liger.get("memberId").getValue() && orderType != 'DIRP'
				&& orderType != 'CONP' && orderType != 'CONPT' && orderType != 'STFP'
				&& orderType != 'STFPT') {
			$.ligerDialog.error($l("type.com.lkkhpg.dsis.common.order.salesorder.not_member"));
			return;
		}
		if (!liger.get("serviceCenterId").getValue()
				&& liger.get("channel") == "SRVC") {
			$.ligerDialog.error($l("type.com.lkkhpg.dsis.common.order.salesorder.servicecenter_not_null"));
			return;
		}
	}
	if (autoSave) {
		var orderType = liger.get("orderType").getValue();
		if (!liger.get("memberId").getValue() && orderType != 'DIRP'
				&& orderType != 'CONP'&& orderType != 'CONPT' && orderType != 'STFP'
				&& orderType != 'STFPT') {
			return;
		}
		if (!liger.get("serviceCenterId").getValue()
				&& liger.get("channel") == "SRVC") {
			return;
		}
	}
	var requestData = om_oc_form.getData();
	requestData.orderStatus = "SAV";
	if (isSumbit) {
	    // 需要进行四舍五入调整的标识
        requestData.roundingFlag = true;
		requestData.orderStatus = "WPAY";
		requestData.salesSites = getOrderSites();
		//拿到当前页面使用的coupon
		requestData.appliedCoupon = getUseVoucher();
	}
	if (!isSave && !isSumbit && !liger.get("memberId").getValue() && !isNext
			&& !autoSave) {
		return;
	}
	requestData.deliveryType = "PCKUP";
	var linegridData = linegrid.getData()
	var lines = new Array();
	for (var j = 0; j < linegridData.length; j++) {
		if (linegridData[j].itemId) {
			lines.push(linegridData[j]);
		}
	}
	requestData.lines = lines;
	requestData.orderAmt = $('#afterTax').text();
	requestData.discountAmt = summary.discountAmt;
	requestData.taxAmt = $('#tax').text();
	if (isSave || autoSave) {
		requestData.actrualPayAmt = requestData.orderAmt;
	} else {
		requestData.actrualPayAmt = $('#actrualPayAmt').text();
	}
	requestData.codFlag = "N";
	requestData.sourceType = "MANUL";
	requestData.salesPoints = $("#getsalespoint").text() || 0;
	if (liger.get('deliveryType')) {
		var deliveryData = deliveryaddress.getSelectedRow();
		if (deliveryData && liger.get('deliveryType').getValue() == "SHIPP") {
			requestData.deliveryLocationId = deliveryData.locationId;
			requestData.deliveryTo = deliveryData.siteId;
		}
		var billData = addressgrid.getSelectedRow();
		if (billData) {
			requestData.billingLocationId = billData.locationId;
			requestData.billingTo = billData.siteId;
		}
		requestData.deliveryType = liger.get('deliveryType').getValue();
		if (liger.get("isCod").getValue()
				&& requestData.deliveryType == "SHIPP") {
			requestData.codFlag = 'Y';
		}
		if (liger.get("freeShipping") && liger.get("freeShipping").getValue()
				&& requestData.deliveryType == "SHIPP") {
			requestData.freeShipping = 'Y';
		}else{
			requestData.freeShipping = 'N';
		}
		requestData.adjustMents = f_getAdjustMents();
		requestData.logistics = f_getLogistics();
		//coupon
		if(requestData.appliedCoupon.length>0){
			requestData.discountAmt = $("#discountAmt").val();
			requestData.discountPvSum = $("#discountPvSum").val();
			requestData.discountTax = $("#discountTax").val();
		}
	}
	
	$.ajax({
				type : 'POST',
				async : false,
				url : _basePath + "/om/order/submit",
				data : JSON2.stringify(requestData),
				success : function(json) {
					if (isNext) {
						if (json.success) {
							$("#headerId").val(json.rows[0].headerId);
						}
						return;
					}
					if (isSumbit) {
						if (json.success) {
							var data = json.rows[0];
							if (data.attribute1 && data.attribute1 == "out") {
								$.ligerDialog.error(
												data.attribute2
														+ $l("msg.warning.order.product_order_qty_over_onhand")
														+ data.attribute3,$l('sys.hand.tip.info'),
												function() {
													if (data.orderStatus == 'WPAY' ) {
														toPaymentPage(data.headerId)
													}
												},{allowClose:false});
							} else {
								toPaymentPage(data.headerId)
							}
							return;
						}
					} else {
						if (json.success) {
							if (isSave) {
								Hap.showSuccess();
							}
							var data = json.rows[0];
							orderDetail = data;
							/*if(autoSave){
								var memberCode = liger.get("memberId").getText();
								var salesOrgName = liger.get("salesOrgId").getText();
								var marketName = liger.get("marketId").getText();
								liger.get('orderStatus').setValue(data.orderStatus);
								liger.get('orderNumber').setValue(data.orderNumber);
								liger.get("memberId").setText(memberCode);
								liger.get("salesOrgId").setText(salesOrgName);
								liger.get("marketId").setText(marketName);
								liger.get("period").setText(data.period);
								$("#headerId").val(data.headerId);
								$("#periodId").val(data.periodId);
							}else{}*/
							
							f_setOrderDetai(data);
							
							if (data.logistics) {
								$("#logisticsId").val(data.logistics.logisticsId);
							}
							$("#delBtn").show();
						}
					}
				},
				contentType : "application/json; charset=utf-8"
			});
}

/**
 * 删除订单
 */
function f_delete() {
	if (!$("#headerId").val()) {
		return;
	}
	$.ligerDialog.confirm($l('sys.hand.tip.delete_confirm'), function(yes) {
		if (yes) {
			var requestData = {
				headerId : $("#headerId").val()
			};
			$.ajax({
				type : 'POST',
				url : _basePath + "/om/order/remove",
				data : JSON2.stringify(requestData),
				success : function(json) {
					if (json.success) {
						var currntTab = top.tab.getSelectedTabItemID();
						top.f_addTab('ORDER_CREATE',
								$l('msg.info.order.new_order'), _basePath
										+ "/om/om_order_create.html");
						top.tab.removeTabItem(currntTab);
					}
				},
				contentType : "application/json; charset=utf-8"
			});
		}
	});
}

/**
 * 取消订单
 */
function f_cancel() {
	if (!$("#headerId").val()) {
		return;
	}
	$.ligerDialog.confirm($l('msg.warn.iscancel'), function(yes) {
		if (yes) {
			var requestData = {
			    orderStatus:"CANCL",
				headerId:$("#headerId").val()
			};
			$.ajax({
				type : 'POST',
				url : _basePath + "/om/order/update",
				data : JSON2.stringify(requestData),
				success : function(json) {
					if (json.success) {
						var currntTab = top.tab.getSelectedTabItemID();
						top.f_addTab('ORDER_CREATE',
								$l('msg.info.order.new_order'), _basePath
										+ "/om/om_order_create.html");
						top.tab.removeTabItem(currntTab);
					}
				},
				contentType : "application/json; charset=utf-8"
			});
		}
	});
}

function toPaymentPage(headerId) {
	var tabId = window.top.tab.getSelectedTabItemID();
	window.top.f_removeTab(tabId);
	window.top
			.f_addTab(
					'ORDER_CREATE',
					$l('type.com.lkkhpg.dsis.common.order.dto.salesorder.orderpayment'),
					_basePath + "/om/om_order_payment.html?orderId=" + headerId +"&applyOn=" + applyOn);
}

/**
 * 提交autoshio订单数据
 */
function f_sumbitAutoShip() {
	if (!validateAutoship()) {
		return;
	}
	var requestData = om_oc_form.getData();
	var deliveryData = deliveryaddress.getSelectedRow();
	if (requestData.autoshipStatus == "NEW") {
		requestData.autoshipStatus == "ACV"
	}
	var lines = linegrid.getData();
	requestData.lines = lines;
	requestData.deliveryType = liger.get('deliveryType').getValue();
	requestData.orderAmt = $('#afterTax').text();
	requestData.discountAmt = summary.discountAmt;
	requestData.taxAmt = $('#tax').text();
	requestData.actrualPayAmt = $('#actrualPayAmt').text();
	requestData.logistics = f_getLogistics();
	requestData.salesScore = $("#getsalespoint").text();
	requestData.salesSites = getOrderSites();
	requestData.member = null;
	requestData.spmSalesOrganization = null;
	requestData.creditCardId = liger.get("creditCardId").getValue();
	$.ajax({
		type : 'POST',
		url : _basePath + "/om/autoship/submit",
		data : JSON2.stringify(requestData),
		success : function(json) {
			if (json.success) {
				var data = json.rows[0];
				f_setAusoShipDetail(data);
				Hap.showSuccess();
				initButton(true);
			} else {
				$.ligerDialog.error(json.message)
			}

		},
		contentType : "application/json; charset=utf-8"
	});
}

/**
 * 验证按钮是否可见
 * 
 * @param isAutoship
 *            是否autoship
 */
function initButton(isAutoship) {
	if (!isAutoship) {
		$("#pickItem").hide();
		$("#saveBtn").hide();
		$("#submitBtn").hide();
		$("#deliveryPackage").hide();
		$("#cancle").hide();
		$("#invalid").hide();
		$("#printInvoice").hide();
		$("#copyBtn").hide();
		var orderStatus = liger.get("orderStatus").getValue();
		if (orderStatus == "WPAY") {
			$("#saveBtn").ligerButton({
				click : function() {
					f_save(true, false);
				},
				text : $l('sys.hand.btn.save')
			});
			$("#submitBtn").ligerButton({
				click : function() {
					f_sumbitSales();
				},
				text : $l('sys.hand.btn.submit')
			});
			$("#cancle")
					.ligerButton(
							{
								click : function() {
									$.ligerDialog
											.confirm(
													$l('msg.warn.iscancel'),
													function(yes) {
														if (yes) {
															var requestData = {
																orderStatus : "CANCL",
																headerId : $(
																		"#headerId")
																		.val()
															};
															$
																	.ajax({
																		type : 'POST',
																		url : _basePath
																				+ "/om/order/update",
																		data : JSON2
																				.stringify(requestData),
																		success : function(
																				json) {
																			if (json.success) {
																				liger
																						.get(
																								"orderStatus")
																						.setValue(
																								"CANCL");
																				for ( var i in orderStatusData) {
																					if (orderStatusData[i].value == "CANCL") {
																						liger
																								.get(
																										"orderStatus")
																								.setText(
																										orderStatusData[i].meaning);
																						break;
																					}
																				}
																				var currntTab = top.tab
																						.getSelectedTabItemID();
																				top
																						.f_addTab(
																								'ORDER_DETAIL',
																								$l('type.com.lkkhpg.dsis.common.order.dto.queryorder.orderinfo'),
																								_basePath
																										+ "/om/om_order_detail.html?orderId="
																										+ orderDetail.headerId);
																				top.tab
																						.removeTabItem(currntTab);
																				// window.top.f_removeCurrentTab();

																			}
																		},
																		contentType : "application/json; charset=utf-8"
																	});
														}
													});
								},
								text : $l('sys.hand.btn.cancel')
							});
			$("#saveBtn").show();
			$("#cancle").show();
			$("#submitBtn").show();
		} else if (orderStatus == "NEW" || orderStatus == "FAIL"
				|| orderStatus == "SAV") {
			$("#saveBtn").ligerButton({
				click : function() {
					f_save(true, false);
				},
				text : $l('sys.hand.btn.save')
			});
			$("#submitBtn").ligerButton({
				click : function() {
					f_sumbitSales();
				},
				text : $l('sys.hand.btn.submit')
			});
			$("#saveBtn").show();
			$("#submitBtn").show();
		} else if (orderStatus == "COMP") {
			$("#pickItem")
					.ligerButton(
							{
								click : function() {
									window.top
											.f_addTab(
													null,
													$l('type.com.lkkhpg.dsis.common.order.dto.salesorder.deliverypickrelease'),
													_basePath
															+ '/dm/dm_delivery_pick_release.html?orderNumber='
															+ liger
																	.get(
																			"orderNumber")
																	.getValue());
								},
								text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.deliverypick')
							});
			$("#deliveryPackage")
					.ligerButton(
							{
								click : function() {
									window.top
											.f_addTab(
													null,
													$l('type.com.lkkhpg.dsis.common.order.dto.salesorder.delivery_query'),
													_basePath
															+ '/dm/dm_delivery_query.html?orderNumber='
															+ liger
																	.get(
																			"orderNumber")
																	.getValue());
								},
								text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.delivery')
							});
			$("#printInvoice")
					.ligerButton(
							{
								click : function() {
									alert($l('type.com.lkkhpg.dsis.common.order.dto.salesorder.printinvoice'))
								},
								text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.printinvoice')
							});
			$("#printInvoice").show();
			$("#deliveryPackage").show();
			$("#pickItem").show();
			$("#invalid")
					.ligerButton(
							{
								click : getMarket,
								text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.invalid')
							});
			$("#invalid").show();
		} else if (orderStatus == "CONF") {
			$("#invalid")
					.ligerButton(
							{
								click : getMarket,
								text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.invalid')
							});
			$("#invalid").show();
		}
		if (orderStatus == "WPAY" || orderStatus == "SAV"
				|| orderStatus == "NEW") {
			$("#copyBtn").show();
		}
		$("#copyBtn")
				.ligerButton(
						{
							click : DsisOrderCreate.copyOrder,
							text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.copytemplate')
						});
	} else {
		$("#activeBtn").ligerButton({
			click : function() {
				f_updateAutoShipStatus('ACV');
				autoshipActive();
			},
			text : $l('msg.warning.order.active')
		});
		$("#stopBtn").ligerButton({
			click : function() {
				f_updateAutoShipStatus('SUS');
				autoshipReadOnly();
			},
			text : $l('msg.hand.btn.pause')
		});
		$("#endBtn")
				.ligerButton(
						{
							click : function() {
								f_updateAutoShipStatus('TER');
								autoshipReadOnly();
							},
							text : $l('type.com.lkkhpg.dsis.common.member.btn.memberdetails.terminate')
						});
		$("#endBtn").hide();
		$("#stopBtn").hide();
		$("#activeBtn").hide();
		$("#autoShipsaveBtn").hide();
		if (liger.get("autoshipStatus").getValue() == "ACV") {
			$("#endBtn").show();
			$("#stopBtn").show();
			$("#autoShipsaveBtn").show();
			autoshipActive();
		} else if (liger.get("autoshipStatus").getValue() == "NEW") {
			$("#autoShipsaveBtn").show();
		} else if (liger.get("autoshipStatus").getValue() == "SUS") {
			$("#endBtn").show();
			$("#activeBtn").show();
		}
		$("#autoShipsaveBtn").ligerButton({
			text : $l('sys.hand.btn.submit'),
			click : f_sumbitAutoShip
		});
		$("#addCreditCar")
				.ligerButton(
						{
							click : function() {
								$.ligerDialog
										.open({
											target : $("#addCredit"),
											title : $l('msg.com.lkkhpg.dsis.common.order.addcredit'),
											width : 500,
											buttons : [
													{
														text : $l('sys.hand.btn.ok'),
														onclick : function(i, d) {
															if (!addCreditFrom
																	.valid()) {
																return;
															}
															$
																	.ajax({
																		type : 'POST',
																		url : _basePath
																				+ "/om/creditCard/submit",
																		data : addCreditFrom
																				.getData(),
																		success : function(
																				json) {
																			var data = json.rows[0];
																			$(
																					"#creditCardId")
																					.val(
																							data.creditCardId);
																			$(
																					"#cardNo")
																					.text(
																							data.maskedCreditCardNum);
																			$(
																					"#creditCardId")
																					.text(
																							liger
																									.get(
																											"bankCode")
																									.getText());
																			$(
																					"input")
																					.ligerHideTip();
																			d
																					.hide();
																		}
																	});
														}
													},
													{
														text : $l('sys.hand.btn.cancel'),
														onclick : function(i, d) {
															$("input")
																	.ligerHideTip();
															d.hide();
														}
													} ]
										});
							},
							text : $l('sys.hand.btn.new')
						});
	}
}
/**
 * 设置表单会员数据
 * 
 * @param detail
 *            会员数据
 */
function f_setMember(detail) {
	cleanMember();
	if (!detail) {
		return;
	}
	memberStatus = detail.status;
	memberRole = detail.memberRole;
	if (memberStatus == 'NEW' && memberRole == "DIS" && ordertype != 'autoship') {
		$.ligerDialog.warn($l('msg.info.order.is_new_member'));
		// reSetLine(false,true,false);

	}
	if (detail.warningMsg) {
		$.ligerDialog.warn(detail.warningMsg);
	}
	if (ordertype != 'autoship') {
		$
				.ajax({
					type : 'POST',
					url : _basePath
							+ "/mm/member/isMemberBirthdayMonth?memberId="
							+ detail.memberId,
					success : function(json) {
						if(json&&json.rows){
                            if(json.rows[0].brithday){
                                $.ligerDialog.warn($l('msg.info.om.member_birthday_month'));
                            }
                            if(json.rows[0].brithdayOrder && liger.get("orderType").getValue() == 'BIGF'){
                            	 $.ligerDialog.warn($l('msg.info.om.member_had_bgit_order'));
                            }
                        }
					}
				});
	}

	liger.get("cnName").setValue("");
	liger.get("enName").setValue("");
	liger.get("currentPoints").setValue("");
	liger.get("currentPV").setValue("");
	liger.get("exchangeBalance").setValue("");
	$("#exchangeBalance").text(summary.zero);
	liger.get("remainingBalance").setValue("");
	$("#remainingBalance").text(summary.zero);
	liger.get("cnName").setValue(detail.chineseName);
	liger.get("enName").setValue(detail.englishName);
	$("#memberRole").val(memberRole);
	if (detail.exchangeBalance) {
		liger.get("exchangeBalance").setValue(detail.exchangeBalance);
		$("#exchangeBalance").text(detail.exchangeBalance);
	}
	if (detail.remainingBalance) {
		liger.get("remainingBalance").setValue(detail.remainingBalance);
		$("#remainingBalance").text(detail.remainingBalance);
	}

	liger.get("currentPoints").setValue(detail.salesPiont);
	liger.get("currentPV").setValue(detail.currentPv);
	if (ordertype == 'autoship') {
		$
				.ajax({
					type : 'POST',
					url : _basePath + "/mm/card/autoquery?memberId="
							+ detail.memberId,
					success : function(json) {
						liger.get("creditCardId").set("data", json.rows);

						for (var i = 0; i < json.rows.length; i++) {
							if (json.rows[i] && json.rows[i].defaultFlag == 'Y') {
								liger.get("creditCardId").setValue(
										json.rows[i].cardId);
								liger.get("creditCardId").setText(
										json.rows[i].bankCode);
								for (var j = 0; j < creditCardType.length; j++) {
									if (creditCardType[j].value == json.rows[i].cardSubType) {
										$("#bankType").text(
												creditCardType[j].meaning);
									}
								}
							}
						}
					}
				});
		$.ajax({
			type : 'POST',
			url : _basePath + "/om/autoship/checkByMemberId?memberId="
					+ detail.memberId,
			success : function(json) {
				if (json.success) {
					if (!json.rows || json.total == 0) {
						f_memberAddress(detail.memberId);
						// f_memberAccount(detail.memberId);
						var lineData = {};
						lineData.rows = [];
						linegrid.set({
							data : lineData
						});
						$("#creditCardId").val("");
						$("#autoshipId").val("");
						$("#logisticsId").val("");
						$("#creditCardId").text("");
						$("#cardNo").text("");
						liger.get("autoshipNumber").setValue("");
						liger.get("autoshipStatus").setValue("NEW");
						for ( var i in autoShipStatusData) {
							if (autoShipStatusData[i].value == "NEW") {
								liger.get("autoshipStatus").setText(
										autoShipStatusData[i].meaning);
								break;
							}
						}
						/*liger.get("creditCardNum").setValue("");
						liger.get("cardHolder").setValue("");
						liger.get("validYear").setValue("");
						liger.get("validMonth").setValue("");*/
						f_calculateLinePrice();
						$("#getsalespoint").text(0);
						$("#shippingTier").text(0);
						$("#actrualPayAmt").text(0);
						$("#summaryPV").text(0);
						addLine(true);
					} else {
						var param = {
							'autoshipId' : json.rows[0].autoshipId
						};
						$.getJSON(_basePath + "/om/autoship/detail", param,
								function(data) {
									f_setAusoShipDetail(data.rows[0]);
									initButton(true);
								})
					}
				}
			}
		});
	} else {
		if (liger.get("deliveryType")) {
			f_memberAddress(detail.memberId);
		}
		// f_memberAccount(detail.memberId);
	}
	// 跨市场购买需要计算价格
	if (liger.get("orderType")&&liger.get("orderType").getValue() == 'NMDP') {
		reSetLine(false, true, false);
		if (linegrid) {
			addRows(false, 10);
		}
	}
}

/**
 * 设置grid产品行数据
 * 
 * @param oldRow
 *            选中行当前值
 * @param newRow
 *            选中行需要替换的值
 */
function f_setLine(oldRow, newRow) {
	if (!oldRow || !newRow) {
		return;
	}
	var newData = newRow;
	newData.__id = oldRow.__id;
	newData.__previd = oldRow.__previd;
	newData.__index = oldRow.__index;
	newData.__nextid = oldRow.__nextid;
	newData.quantity = 1;
	newData.defaultInvOrgId = oldRow.defaultInvOrgId;
	newData.itemSalesType = oldRow.itemSalesType;
	newData.unitDiscountPrice = 0;
	newData.uomCode = newRow.uomCode;
	newData.itemId = newRow.itemId;
	var pricetype = f_getPriceType();
	var orderType = liger.get("orderType");
	$.ajax({
				type : 'POST',
				async : false,
				url : _basePath + "/inv/price/queryPriceByItemId?itemId="
						+ newRow.itemId + "&currency="
						+ liger.get("currency").getValue() + "&uomCode="
						+ newRow.uomCode + "&salesOrgId=" + defaultSaleOrg,
				success : function(json) {
					if (json.success) {
						newData.priceDetail = json.rows;
						var disableTaxFlag = json.rows[0].disableTaxFlag;
						for ( var i in json.rows) {
							if (json.rows[i].priceType == 'PV') {
								if (orderType
										&& (orderType.getValue() == "STDP" || orderType
												.getValue() == "VIPP")
										&& newData.itemSalesType == "PURC") {
									newData.pv = json.rows[i].unitPrice;
								} else if (!orderType) {
									newData.pv = json.rows[i].unitPrice;
								} else {
									newData.pv = 0;
								}

							} else if (json.rows[i].priceType == pricetype) {
								if (pricetype == 'RP') {

									/*
									 * if((summary.points+json.rows[i].unitPrice)>Number(liger.get("currentPoints").getValue())){ //
									 * TODO clear itemNumber
									 * $.ligerDialog.error($l('msg.error.order.sales_point_insufficient'));
									 * return }
									 */
									summary.points + json.rows[i].unitPrice;
									newData.unitRedeemPoint = json.rows[i].unitPrice;
								} else {
									newData.unitOrigPrice = json.rows[i].unitPrice;
								}
							}
						}
						if (!newData.pv) {
							newData.pv = 0;
						}
						if (!newData.unitOrigPrice) {
							newData.unitOrigPrice = 0;
						}
						if (!newData.unitDiscountPrice) {
							newData.unitDiscountPrice = 0;
						}
						if (!newData.unitRedeemPoint) {
							newData.unitRedeemPoint = 0;
						}
						newData.favorableSum = newData.quantity
								* newData.unitDiscountPrice;
						//优惠总额
						var discountAmount = liger.get('discountAmt').getValue();
						newData.favorableSum = newData.quantity
						* newData.unitDiscountPrice;
						newData.redeemPoint = newData.unitRedeemPoint
								* newData.quantity;

						newData.PVSum = newData.quantity * newData.pv;
						
						newData.disableTaxFlag = disableTaxFlag;
						if(disableTaxFlag == 'Y'){
							newData.tax = 0
						}else{
							if(marketCode == 'CA' && newData.itemNumber == '15160502'){
								newData.tax = 0.07 * newData.unitOrigPrice;
							}else{
								newData.tax = summary.taxRate * newData.unitOrigPrice;
							}
						}
						if (!summary.priceIncludeTax && summary.enableTax&&disableTaxFlag != 'Y') {
							if(marketCode == 'CA' && newData.itemNumber == '15160502'){
								newData.unitSellingPrice = (newData.unitOrigPrice
										* (1 + 0.07) - newData.unitDiscountPrice)
										.toFixed(summary.precision);
							}else{
							newData.unitSellingPrice = (newData.unitOrigPrice
									* (1 - -summary.taxRate) - newData.unitDiscountPrice)
									.toFixed(summary.precision);
							}
						} else {
							newData.unitSellingPrice = newData.unitOrigPrice
									- newData.unitDiscountPrice;
						}
						newData.amount = newData.unitSellingPrice
								* newData.quantity;
						linegrid.updateRow(oldRow, newData);
						f_calculateLinePrice();
						return;
					}
				}
			});
}

/**
 * 订单行价格获取
 */
function f_getPriceType() {
	var orderType;
	if (liger.get('orderType')) {
		orderType = liger.get('orderType').getValue();
	} else {
		orderType = 'STDP';
	}
	var pricetype;
	if (orderType == 'DIRP' || orderType == 'STFP') {
		pricetype = 'STU';
	} else if (orderType == 'STDP') {
		pricetype = 'DIS';
	} else if (orderType == 'NMDP') {
		if ($("#memberRole").val() && $("#memberRole").val() == 'VIP') {
			pricetype = 'VIP';
		} else {
			pricetype = 'DIS';
		}
	} else if (orderType == 'VIPP') {
		pricetype = 'VIP';
	} else if (orderType == 'CONP') {
		pricetype = 'RE';
	} else if (orderType == 'CONPT') {
		pricetype = 'DIS';
	}else if (orderType == 'STFPT') {
		pricetype = 'STU2';
	} else if (orderType == 'BIGF') {
		pricetype = '0';
	} else {
		pricetype = 'RP'
	}
	return pricetype;
}

/**
 * 订单行价格设置
 */
function f_setLinePrice(oldRow) {
	if (!oldRow) {
		return;
	}
	var newData = oldRow;
	if (!newData.pv) {
		newData.pv = 0;
	}
	if (!newData.unitOrigPrice) {
		newData.unitOrigPrice = 0;
	}
	if (!newData.unitDiscountPrice) {
		newData.unitDiscountPrice = 0;
	}
	if (!newData.unitRedeemPoint) {
		newData.unitRedeemPoint = 0;
	}
	newData.favorableSum = Number(newData.quantity * newData.unitDiscountPrice);

	newData.redeemPoint = newData.unitRedeemPoint * newData.quantity;
	newData.PVSum = Number(newData.quantity * newData.pv);
	if(marketCode == 'CA' && newData.itemNumber == '15160502'){
		newData.tax = 0.07 * newData.unitOrigPrice;
	}else{
	newData.tax = summary.taxRate * newData.unitOrigPrice;
	}
	if (!summary.priceIncludeTax && summary.enableTax && newData.disableTaxFlag !='Y') {
		if(marketCode == 'CA' && newData.itemNumber == '15160502'){
			newData.unitSellingPrice = (newData.unitOrigPrice
					* (1 + 0.07) - newData.unitDiscountPrice)
					.toFixed(summary.precision);
		}else{
		newData.unitSellingPrice = (newData.unitOrigPrice
				* (1 - -summary.taxRate) - newData.unitDiscountPrice)
				.toFixed(summary.precision);
		}
	} else {
		newData.unitSellingPrice = newData.unitOrigPrice
				- newData.unitDiscountPrice;
	}
	newData.amount = Number(newData.unitSellingPrice * newData.quantity
			- newData.favorableSum);
	linegrid.updateRow(oldRow, newData);
	f_calculateLinePrice();
	return;
}

/**
 * 会员地址获取与设置
 * 
 * @param memberId
 *            会员id
 */
function f_memberAddress(memberId) {
	if (!memberId) {
		return;
	}
	if (!liger.get("deliveryType")
			/*|| liger.get("deliveryType").getValue() != "SHIPP"*/) {
		return;
	}
	$.ajax({
		type : 'POST',
		url : _basePath + "/mm/site/query?memberId=" + memberId,
		success : function(json) {
			if (json.success) {
				var addressData = {};
				var billAddress = new Array();
				var shipAddress = new Array();
				for ( var i in json.rows) {
					if (json.rows[i].siteUseCode == "BILL") {
						billAddress.push(json.rows[i]);
					} else if (json.rows[i].siteUseCode == "SHIP") {
						shipAddress.push(json.rows[i]);
					}
				}
				addressData.rows = billAddress;
				addressgrid.set({
					data : addressData
				});
				addressData.rows = shipAddress;
				deliveryaddress.set({
					data : addressData
				});
			}
		}
	});
}

/**
 * 会员账户信息获取
 * 
 * @param memberId
 */
function f_memberAccount(memberId) {
	if (!memberId) {
		return;
	}
	$.ajax({
		type : 'POST',
		url : _basePath + "/mm/accountingbalance/query?memberId=" + memberId,
		success : function(json) {
			if (json.success) {
				f_setMemberAccount(json.rows)
			}
		}
	});
}

/**
 * 会员账户信息设置
 */
function f_setMemberAccount(rows) {
	for ( var row in rows) {
		if (rows[row].accountingType == 'EB') {
			liger.get("exchangeBalance").setValue(rows[row].balance);
			$("#exchangeBalance").text(rows[row].balance);
		} else if (rows[row].accountingType == 'RB') {
			liger.get("remainingBalance").setValue(rows[row].balance);
			$("#remainingBalance").text(rows[row].balance);
		} else if (rows[row].accountingType == 'SP') {
			liger.get("currentPoints").setValue(rows[row].balance);
			// liger.get("currentPV").setValue(rows[row].balance);
		}
	}
}


function f_setOrderPrice(data){
	var ispoint = (data.orderType == 'RDEM');
	summary.pv = 0;
	summary.currency = 0;
	summary.exchange = 0;
	summary.points = 0;
	summary.tax = 0;
	var lineData = data.lines;
	for ( var line in lineData) {
		
		summary.pv += Number(lineData[line].pv * lineData[line].quantity);
		if (lineData[line].itemSalesType == "EXCH") {
			summary.exchange += Number(lineData[line].amount);
		}
		summary.points += Number(lineData[line].redeemPoint);
	}
	$("#totalPV").text(summary.pv);
	$("#summaryPV").text(summary.pv);

	var actrualPayAmt =   Number(summary.currency - -summary.adjustMents- summary.discountAmt).toFixed(summary.precision)
	if (ispoint) {
		$("#beforeTax").text(0);
		$("#tax").text(0);
		$("#afterTax").text(0);
		var str = "<label class='label1'>"
			+ $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salespointsummary")
			+ ":</label><label id='TotalCurrency' class='label2'>0</label>"
	      $("#salesLineSummary tr:eq(0) td:eq(2)").html(str);
		$("#TotalCurrency").text(Number(summary.points));
		var actrualPayAmt =   Number(summary.adjustMents- summary.discountAmt).toFixed(summary.precision)
		$("#salespointtd").show();
		$("#getsalespoint").text(Number(summary.points));
		$("#actrualPayAmt").text(actrualPayAmt);

	} else {
		summary.currency = data.orderAmt;
		// lkkhpg_invoice_sit 2017-9-6 BEGIN
		if (summary.priceIncludeTax) {
			summary.tax = Number((summary.currency/ (1 + Number(summary.taxRate)) * summary.taxRate).toFixed(summary.precision));
		} else {
			summary.tax = data.taxAmt;
		}
		// lkkhpg_invoice_sit 2017-9-6 END
		$("#beforeTax").text(Number(summary.currency - summary.tax).toFixed(summary.precision));
		$("#tax").text(Number(summary.tax).toFixed(summary.precision));
		$("#TotalCurrency").text(Number(summary.currency).toFixed(summary.precision));
		$("#afterTax").text(Number(summary.currency).toFixed(summary.precision));
		
		var actrualPayAmt =   Number(summary.currency - -summary.adjustMents- summary.discountAmt).toFixed(summary.precision)
		$("#actrualPayAmt").text(actrualPayAmt);

		/*$("#exchange").text(Number(summary.exchange.toFixed(summary.precision)));*/
	}
}

/**
 * 订单行金额汇总
 */
function f_calculateLinePrice() {
	var lineData = linegrid.getData();
	summary.pv = 0;
	summary.currency = 0;
	summary.exchange = 0;
	summary.points = 0;
	summary.tax = 0;
	var orderType;
	if (liger.get('orderType')) {
		orderType = liger.get('orderType').getValue();
	} else {
		orderType = 'STDP';
	}
	var ispoint = (orderType == 'RDEM');
	for ( var line in lineData) {
		if (!lineData[line].redeemPoint) {
			lineData[line].redeemPoint = 0;
		}
		if (!lineData[line].unitOrigPrice) {
			lineData[line].unitOrigPrice = 0;
		}
		if (!lineData[line].unitDiscountPrice) {
			lineData[line].unitDiscountPrice = 0;
		}
		if (!lineData[line].amount) {
			lineData[line].amount = 0;
		}
		if (!lineData[line].pv) {
			lineData[line].pv = 0;
		}
		if (!lineData[line].quantity) {
			lineData[line].quantity = 1;
		}
		if (lineData[line].PVSum) {
			summary.pv += Number(lineData[line].PVSum);
		} else {
			summary.pv += Number(lineData[line].pv * lineData[line].quantity);
		}
		
		if (lineData[line].itemSalesType == "EXCH") {
			summary.exchange += Number(lineData[line].amount);
		}
		summary.points += Number(lineData[line].redeemPoint);
	}
	$("#totalPV").text(Number(summary.pv.toFixed(summary.precision)));
	$("#summaryPV").text(Number(summary.pv.toFixed(summary.precision)));
	if (ispoint) {
		$("#beforeTax").text(0);
		$("#tax").text(0);
		$("#afterTax").text(0);
		$("#TotalCurrency").text(Number(summary.points));
		if (liger.get("deliveryType")
				&& liger.get("deliveryType").getValue() == "SHIPP") {
			if (Number((summary.shippingTier - -summary.adjustMents)) >= 0) {
				$("#actrualPayAmt").text(
						Number((summary.shippingTier - -summary.adjustMents))
								.toFixed(summary.precision));
			} else {
				Hap.showError($l("msg.error.om.actual_payment_amount_error"));
				$("#actrualPayAmt").text(summary.zero);
			}

		} else {
			if (Number(summary.adjustMents) >= 0) {
				$("#actrualPayAmt").text(Number((summary.adjustMents)).toFixed(summary.precision));
			} else {
				Hap.showError($l("msg.error.om.actual_payment_amount_error"));
				$("#actrualPayAmt").text(summary.zero);
			}

		}
	} else {
		if (summary.enableTax ) {
			if(summary.priceIncludeTax){
				summary.tax = Number((summary.currency/ (1 + Number(summary.taxRate)) * summary.taxRate).toFixed(summary.precision));
			}
		} else {
			summary.tax = 0;
		}
		$("#beforeTax").text(
				Number((summary.currency - summary.tax).toFixed(summary.precision)));


		$("#tax").text(Number(summary.tax).toFixed(summary.precision));
		$("#TotalCurrency").text(Number(summary.currency.toFixed(summary.precision)));
		$("#afterTax").text(Number(summary.currency.toFixed(summary.precision)));

		if (liger.get("deliveryType")
				&& liger.get("deliveryType").getValue() != "SHIPP") {
			if (Number(summary.currency - -summary.adjustMents - summary.discountAmt) >= 0) {
				$("#actrualPayAmt").text(
						Number(summary.currency - -summary.adjustMents- summary.discountAmt).toFixed(summary.precision));
			} else {
				Hap.showError($l("msg.error.om.actual_payment_amount_error"));
				$("#actrualPayAmt").text(summary.zero);
			}
		} else {
			if (Number(summary.currency - -summary.shippingTier
					- -summary.adjustMents - summary.discountAmt) >= 0) {
				$("#actrualPayAmt").text(
						Number((summary.currency - -summary.shippingTier - -summary.adjustMents - summary.discountAmt)
								.toFixed(summary.precision)));
			} else {
				Hap.showError($l("msg.error.om.actual_payment_amount_error"));
				$("#actrualPayAmt").text(summary.zero);
			}

		}

		$("#exchange").text(Number(summary.exchange.toFixed(summary.precision)))
	}
	if (liger.get("deliveryType")
			&& liger.get("deliveryType").getValue() == "SHIPP"
			&& deliveryaddress.getSelected()) {
		f_resetShippingTier();
	}
}

/**
 * 订单支付调整
 * 
 * @param obj
 *            调整对象
 * @param ischeck
 *            是否校验价格
 */
function f_payMentAdjust(obj, ischeck) {
	var isNum = /^-?\d*\.?\d*$/;
	if (!isNum.test(obj.value)) {
		$(obj).addClass("l-text-invalid");
		// $(obj).removeAttr("title").ligerHideTip();
		$(obj).attr("title",
				$l("type.com.lkkhpg.dsis.common.order.payadjustment.number"))
				.ligerTip({
					distanceX : 5,
					distanceY : -3,
					auto : true
				});
		$(obj).val("");
		return;
	} else {
		if ($(obj).hasClass("l-text-invalid")) {
			$(obj).removeClass("l-text-invalid");
			$(obj).removeAttr("title").ligerHideTip();
		}
	}

	if (obj.value < 0) {
		$(obj).val("");
		Hap
				.showError($l("type.com.lkkhpg.dsis.common.order.payadjustment.minus"));
		return;
	}
	var id;
	if (obj) {
		id = obj.id + 'Text';
		obj.tempValue = Number($("#" + id).text());
		if (obj.id == 'adjust') {
			var tr = $(obj).parents('tr');
			if (!obj.value) {

				tr.find("#adjustText").text(summary.zero);
			} else {
				if (tr.find("input[type='hidden']:eq(0)").val() == 'subs') {
					tr.find("#adjustText").text("-" + obj.value);
				} else {
					tr.find("#adjustText").text(/*"+" +*/ obj.value);
				}
			}

		} else {
			$("#" + id).text(obj.value);
		}
	}
	if (ischeck) {
		/*
		 * if(obj.id=='exchangeBalanceUse'){
		 * if(Number(obj.value)>Number(liger.get('exchangeBalance').getValue())){
		 * Hap.showError("exchangeBalance"+$l("type.com.lkkhpg.dsis.common.order.payadjustment.biggerthenorder")+"exchangeBalance");
		 * $(obj).val(""); $("#" + id).text("0"); //return; }
		 * if(Number(obj.value)>Number(summary.exchange)){
		 * Hap.showError("exchangeBalance"+$l("type.com.lkkhpg.dsis.common.order.payadjustment.totalbiggerthenorder")+summary.exchange);
		 * $(obj).val(""); $("#" + id).text("0"); // return; }
		 * $("#exchangeBalanceUse").val(obj.value); if(!obj.value){ $("#" +
		 * id).text("0") }else{ $("#" + id).text(obj.value) } }else
		 * if(obj.id=='remainingBalanceUse'){
		 * if(Number(obj.value)>Number(liger.get('remainingBalance').getValue())){
		 * Hap.showError("remainingBalance"+$l("type.com.lkkhpg.dsis.common.order.payadjustment.biggerthenorder")+"remainingBalance");
		 * $(obj).val(""); $("#" + id).text("0"); // return; }else{
		 * $("#remainingBalanceUse").val(obj.value); if(!obj.value){ $("#" +
		 * id).text("0") }else{ $("#" + id).text(obj.value) } } }
		 */
	}
	var afterAdjust = 0;
	/*
	 * afterAdjust -= Number($("#exchangeBalanceUse").val()); afterAdjust -=
	 * Number($("#remainingBalanceUse").val());
	 */
	var trLength = $('#payAdjust table tr').length;
	var trs = $('#payAdjust table tr:lt(' + (trLength - 1) + ')');
	$.each(trs, function() {
		if (Number($(this).find("#adjust").val()) > 0) {
			if ($(this).find("input[type='hidden']:eq(0)").val() == 'subs') {
				afterAdjust -= Number($(this).find("#adjust").val());
			} else {
				afterAdjust += Number($(this).find("#adjust").val());
			}
		}
	});
	if ((Number(summary.currency) + afterAdjust) < 0) {
		if (obj.id == 'adjust') {
			var tr = $(obj).parents('tr');
			tr.find("#adjustText").text(summary.zero);
		} else {
			$("#" + id).text(summary.zero);
		}
		afterAdjust += Number(obj.value);
        afterAdjust = Number(afterAdjust).toFixed(summary.precision);
		$("#afterAdjust").text(afterAdjust);
		$(obj).val("");
		Hap
				.showError($l("type.com.lkkhpg.dsis.common.order.payadjustment.adjustbiggerthenorder"));
		summary.adjustMents = afterAdjust;
		// return;
	} else {
        afterAdjust = Number(afterAdjust).toFixed(summary.precision);
		$("#afterAdjust").text(afterAdjust);
		summary.adjustMents = afterAdjust;
	}
	if (liger.get("deliveryType").getValue() != "SHIPP") {
		if (Number(summary.currency - -afterAdjust - summary.discountAmt) >= 0) {
			$("#actrualPayAmt")
					.text(
							Number(
									summary.currency - -afterAdjust
											- summary.discountAmt).toFixed(
									summary.precision));
		} else {
			Hap.showError($l("msg.error.om.actual_payment_amount_error"));
			$("#actrualPayAmt").text(summary.zero);
		}

	} else {
		if (Number(summary.currency - -summary.shippingTier - -afterAdjust
				- summary.discountAmt) >= 0) {
			$("#actrualPayAmt").text(
					Number(
							summary.currency - -summary.shippingTier
									- -afterAdjust - summary.discountAmt)
							.toFixed(summary.precision));
		} else {
			Hap.showError($l("msg.error.om.actual_payment_amount_error"));
			$("#actrualPayAmt").text(summary.zero);
		}

	}
	return;
}

/**
 * 修改会员隐藏样式
 * 
 * @param isShow
 *            是否显示
 */
function f_changeMemberShow(isShow) {
	if (isShow) {
		if ($(".l-group:eq(1) div").hasClass("togglebtn-down")) {
			$(".l-group:eq(1) div").click();
		}
		$(".l-group:eq(1)").show();
	} else {
		liger.get("memberId").clear();
		if (!$(".l-group:eq(1) div").hasClass("togglebtn-down")) {
			$(".l-group:eq(1) div").click();
		}
		$(".l-group:eq(1)").hide();
	}
}

function f_changeStaffShow(isShow){
	if (isShow) {
		if ($(".l-group:eq(2) div").hasClass("togglebtn-down")) {
			$(".l-group:eq(2) div").click();
		}
		$(".l-group:eq(2)").show();
	} else {
		if (!$(".l-group:eq(2) div").hasClass("togglebtn-down")) {
			$(".l-group:eq(2) div").click();
		}
		$(".l-group:eq(2)").hide();
	}
}
/**
 * 订单数据设置
 * 
 * @param data
 *            订单详情数据
 * @param isConfirm
 *            是否订单确认页面
 */
function f_setOrderDetai(data, isConfirm) {
	var memberCode = liger.get("memberId").getText();
	var salesOrgName = liger.get("salesOrgId").getText();
	var marketName = liger.get("marketId").getText();
	var serviceCenterName = liger.get("serviceCenterId").getText();
	om_oc_form.setData(data);
	liger.get("memberId").setText(memberCode);
	liger.get("salesOrgId").setText(salesOrgName);
	liger.get("marketId").setText(marketName);
	liger.get("period").setText(data.period);
	// 销售组织以及市场货币设置
	if (data.salesOrganization) {
		f_setMarketAndCurrency(data.salesOrganization.market, data.spmCurrency.currencyCode);
		liger.get('salesOrgId').setValue(data.salesOrganization.salesOrgId);
		liger.get('salesOrgId').setText(data.salesOrganization.name);
	}
	// 订单状态设置
	for ( var i in orderStatusData) {
		if (orderStatusData[i].value == data.orderStatus) {
			liger.get("orderStatus").setText(orderStatusData[i].meaning);
			break;
		}
	}
	// 订单类型设置
	for ( var i in orderTypeData) {
		if (orderTypeData[i].value == data.orderType) {
			liger.get("orderType").setText(orderTypeData[i].meaning);
			break;
		}
	}

	// 销售渠道设置
	for ( var i in channelData) {
		if (channelData[i].value == data.channel) {
			liger.get("channel").setText(channelData[i].meaning);
			break;
		}
	}

	var gridData = {};
	gridData.rows = data.lines;
	linegrid.set({
		data : gridData
	});
	if (!isConfirm) {
		if (data.lines.length < 10) {
			addRows(false, 10 - data.lines.length);
		}
	}
	summary.currency = data.orderAmt;

	if ("SAV" != data.orderStatus) {
		// 是否货到付款设置
		if (data.codFlag == 'Y') {
			liger.get("isCod").setValue(true);
		}
		if (data.freeShipping  == 'Y') {
			liger.get("freeShipping").setValue(true);
		}
		if (data.adjustMents) {
			f_setDefaultAdjustMents(data.adjustMents)
		}
	}
	// 订单用户信息设置
	if (data.member) {
		if (data.member.memAccountingBalances) {
			f_setMemberAccount(data.member.memAccountingBalances);
		}
		gridData.rows = data.member.memSites;
		liger.get("cnName").setValue(data.member.chineseName);
		liger.get("enName").setValue(data.member.englishName);
		liger.get("memberId").setValue(data.member.memberId);
		if (data.member.exchangeBalance) {
			liger.get("exchangeBalance").setValue(data.member.exchangeBalance);
			$("#exchangeBalance").text(data.member.exchangeBalance);
		}
		if (data.member.remainingBalance) {
			liger.get("remainingBalance")
					.setValue(data.member.remainingBalance);
			$("#remainingBalance").text(data.member.remainingBalance);
		}

		liger.get("currentPoints").setValue(data.member.salesPiont);
		liger.get("currentPV").setValue(data.member.currentPv);
		if (data.member.memberCode) {
			liger.get("memberId").setText(data.member.memberCode);
		}
		memberStatus = data.member.status;

	}
	if (liger.get('deliveryType')) {
		liger.get('deliveryType').setValue(data.deliveryType);
		for ( var i in deliveryTypeData) {
			if (deliveryTypeData[i].value == data.deliveryType) {
				liger.get('deliveryType').setText(deliveryTypeData[i].meaning);
				validateDeliver();
				break;
			}
		}
		if (data.member) {
			// 订单配送设置
			f_setDelivery(data.member.memSites, data.logistics, data.salesSites);
		} else {
			// 订单配送设置
			f_setDelivery(null, data.logistics, data.salesSites);
		}

	}
	if (data.serviceCenterId) {
		liger.get("serviceCenterId").setValue(data.serviceCenterId);
		if(serviceCenterName){
			liger.get("serviceCenterId").setText(serviceCenterName);
		}else{
			liger.get("serviceCenterId").setText(data.serviceCenterName);
		}
		
	} else {
		liger.get("serviceCenterId").setText("   ");
	}
	f_setOrderPrice(data);
}

/**
 * 设置默认支付调整
 * 
 * @param adjustMents
 *            价格调整数组
 */
function f_setDefaultAdjustMents(adjustMents) {
	if (!adjustMents || !liger.get("adjustType")) {
		return;
	}
	var tr = $('#payAdjust table tr:eq(0)');
	var trlength = $("#payAdjust table tr").length
	var deleteTrs = $('#payAdjust table tr:lt(' + (trlength - 3) + '):gt(0)');
	$.each(deleteTrs, function() {
		if ($(this).find("#adjustid").length > 0) {
			$(this).remove();
		}
	});
	var adjustTimes = 0;
	var adjustTypeData = liger.get("adjustType").options.data;
	for ( var i in adjustMents) {
		var obj = new Object();
		obj.value = Math.abs(adjustMents[i].adjustmentAmount);
		/*
		 * if ('EB' == adjustMents[i].adjustmentType) {
		 * $('#exchangeBalanceUse').val(obj.value); obj.id =
		 * "exchangeBalanceUse";
		 * $("#exchangeBalanceUseid").val(adjustMents[i].salesAdjustmentId);
		 * f_payMentAdjust(obj); } if ('RB' == adjustMents[i].adjustmentType) {
		 * $('#remainingBalanceUse').val(obj.value);
		 * $("#remainingBalanceUseid").val(adjustMents[i].salesAdjustmentId);
		 * obj.id = "remainingBalanceUse"; f_payMentAdjust(obj); }
		 */
		if ('AD' == adjustMents[i].adjustmentType) {
			adjustTimes++;
			obj.id = "adjust";
			if (adjustTimes > 1) {
				var ntr = tr.clone();
				ntr.find("#adjustType_val").attr('id',
						"adjustType" + adjustTimes + "_val");
				ntr.find("#adjustType").attr('id', "adjustType" + adjustTimes);
				if (Number(adjustMents[i].adjustmentAmount) > 0) {
					ntr.find("#adjustType" + adjustTimes + "_val").val("add");
					for ( var j in adjustTypeData) {
						if (adjustTypeData[j].value == "add") {
							ntr.find("#adjustType" + adjustTimes).val(
									adjustTypeData[j].meaning);
							break;
						}
					}
				} else {
					ntr.find("#adjustType" + adjustTimes + "_val").val("subs");
					for ( var j in adjustTypeData) {
						if (adjustTypeData[j].value == "subs") {
							ntr.find("#adjustType" + adjustTimes).val(
									adjustTypeData[j].meaning);
							break;
						}
					}
				}
				ntr.find("#adjustText").html(obj.value);
				ntr.find("#adjust").val(obj.value);
				ntr.find("#remarks").val(adjustMents[i].remark);
				ntr.find("#adjustid").val(adjustMents[i].salesAdjustmentId);
				ntr.find('.addArtificial').hide();
				ntr.find('.subArtificial').show();
				tr.after(ntr);
			} else {
				if (Number(adjustMents[i].adjustmentAmount) > 0) {
					tr.find("#adjustType_val").val("add");
					for ( var j in adjustTypeData) {
						if (adjustTypeData[j].value == "add") {
							tr.find("#adjustType").val(
									adjustTypeData[j].meaning);
							break;
						}
					}
				} else {
					tr.find("#adjustType_val").val("subs");
					for ( var j in adjustTypeData) {
						if (adjustTypeData[j].value == "subs") {
							tr.find("#adjustType").val(
									adjustTypeData[j].meaning);
							break;
						}
					}
				}
				tr.find("#adjust").val(obj.value);
				tr.find("#adjustText").html(obj.value);
				tr.find("#remarks").val(adjustMents[i].remark);
				tr.find("#adjustid").val(adjustMents[i].salesAdjustmentId);
			}
			f_payMentAdjust(obj);
		}
	}
}

/**
 * 初始设置销售区域
 * 
 * @param data
 *            销售区域数据
 */
function f_initSalesOrg(data) {
	for ( var i in data) {
		// var currency={
		// currencyCode:"CNY"
		// };
		if (data[i].salesOrgId == defaultSaleOrg) {
			liger.get('salesOrgId').setValue(data[i].salesOrgId);
			liger.get('salesOrgId').setText(data[i].name);
			// liger.get('salesOrgId').setDisabled(true);
			liger.get('salesOrgId').set({
				readonly : true,
				data : data
			});
			f_setMarketAndCurrency(data[i].market, data[i].currency);
			break;
		}
	}
}

/**
 * 设置所属市场以及货币
 */
function f_setMarketAndCurrency(market, currency) {
	if (market) {
		liger.get('marketId').setValue(market.marketId);
		liger.get('marketId').setText(market.name);
	}
	if (currency) {
		liger.get('currency').setValue(currency);
		liger.get('currency').setText(currency);
		//liger.get('currency').setValue(currency.currencyCode);
		$(".currencysign").text(currency);
	}
}

/**
 * 获取支付调整数据
 */
function f_getAdjustMents() {
	var adjustMents = new Array();
	/*
	 * if ($('#exchangeBalanceUse').val()) { adjustMents.push({ adjustmentType :
	 * 'EB', adjustmentAmount : -$('#exchangeBalanceUse').val(),
	 * headerId:$("#headerId").val(), salesAdjustmentId :
	 * $('#exchangeBalanceUseid').val() }); } if
	 * ($('#remainingBalanceUse').val()) { adjustMents.push({ adjustmentType :
	 * 'RB', adjustmentAmount : -$('#remainingBalanceUse').val(),
	 * headerId:$("#headerId").val(), salesAdjustmentId :
	 * $('#remainingBalanceUseid').val() }); }
	 */
	var trlength = $("#payAdjust table tr").length
	var trs = $('#payAdjust table tr:lt(' + (trlength - 1) + ')');
	$.each(trs, function() {
		if ($(this).find("#adjust").val()) {
			var amount = $(this).find("#adjust").val();
			if ($(this).find("input[type='hidden']:eq(0)").val() == 'subs') {
				amount = -Number($(this).find("#adjust").val());
			}
			adjustMents.push({
				adjustmentType : 'AD',
				adjustmentAmount : amount,
				headerId : $("#headerId").val(),
				remark : $(this).find("#remarks").val(),
				salesAdjustmentId : $(this).find("#adjustid").val()
			});
		}
	});
	return adjustMents;
}

/**
 * 获取配送地址数据
 */
function f_getLogistics() {
	if (liger.get('deliveryType').getValue() == "PCKUP"
			|| !liger.get('deliveryCompany').getValue()) {
		return null;
	}
	var logistics = new Object();
	logistics.logisticsId = $("#logisticsId").val();
	logistics.headerId = $("#headerId").val();
	logistics.shippingTierId = liger.get('deliveryCompany').getValue();
	logistics.shippingFee = $('#shippingTier').text();
	logistics.salesOrgId = liger.get('salesOrgId').getValue();
	logistics.autoshipFlag = 'N';
	if (liger.get("isCod") && liger.get("isCod").getValue()) {
		logistics.codFlag = 'Y';
	} else {
		logistics.codFlag = 'N';
	}
	return logistics;
}

/**
 * 根据配送地址查询承运商
 */
function f_queryShippingTier(address) {
	var param = {
		salesOrgId : liger.get('salesOrgId').getValue(),
		apptype : 'DSIS',
		orderId : $("#headerId").val(),
		'currency' : liger.get("currency").getValue()
	};
	if (liger.get("memberId").getValue()&& address.memberId) {
		param.countryCode = address.spmLocation.countryCode;
		param.cityCode = address.spmLocation.cityCode;
		param.stateCode = address.spmLocation.stateCode;
		param.zipCode = address.spmLocation.zipCode;
	} else {
		param.countryCode = address.countryCode;
		param.cityCode = address.cityCode;
		param.stateCode = address.stateCode;
		param.zipCode = address.zipCode;
	}

	//根据地址修改税
	changgeSiteUpdatetax(param);
	
	var requestData =$.extend({}, om_oc_form.getData());
	var sites = [];
	sites.push({'countryCode':param.countryCode,'cityCode':param.cityCode,'stateCode':param.stateCode,'zipCode':param.zipCode});
	requestData.salesSites = sites;
	/*requestData.vouchers = getUseVoucher();*/
	requestData.lines = linegrid.getData();
	// requestData.orderAmt = $('#afterTax').text();
	requestData.orderAmt = summary.currency;
	requestData.discountAmt = summary.discountAmt;
	requestData.taxAmt = summary.tax;
	/*requestData.actrualPayAmt = requestData.orderAmt;
	requestData.actrualPayAmt = $('#actrualPayAmt').text();*/
	/*requestData.deliveryType = liger.get('deliveryType').getValue();*/
	
	$.ajax({
        url : _basePath + "/dm/shippingTier/queryforOrder",
        type : 'POST',
        dataType : "json",
        contentType : "application/json",
        data : JSON2.stringify(requestData),
        success : function(data) {
        	if (!data.rows || data.rows.length < 1) {
				liger.get('deliveryCompany').set({
					data : data.rows
				});
				liger.get('deliveryCompany').setValue();
				$('#shippingTier').text(0);
				$('#shippingTierTax').text(0);
				summary.shippingTier = 0;
				//f_calculateLinePrice();
				return;
			}
			liger.get('deliveryCompany').set({
				data : data.rows
			});
			if(liger.get("freeShipping")&&liger.get("freeShipping").getValue()){
				summary.shippingTier = 0;
				$('#shippingTier').text(0);
				$('#shippingTierTax').parent().hide();
			}else{
			summary.shippingTier = data.rows[0].includetaxShippingFee-shippingDiscount;
			liger.get('deliveryCompany').setValue(data.rows[0].shippingTierId);
			liger.get('deliveryCompany').setText(data.rows[0].shippingTierName);
			$('#shippingTier').text(summary.shippingTier.toFixed(summary.precision));
			if(data.rows[0].shipIncludeTax =='NO'){
				$('#shippingTierTax').parent().hide();
			}else{
				$('#shippingTierTax').parent().show();
				summary.shippingTierTax = data.rows[0].taxShippingFee;
				$('#shippingTierTax').text(summary.shippingTierTax.toFixed(summary.precision));
			}
			//#PE20-257 2017-09-21 BEGIN
			if(data.rows[0].includetaxShippingFee==0){
				$('#shippingTier').text(0); 
				$('#shippingTierTax').parent().hide();
			}
			//#PE20-257 2017-09-21 BEGIN
        }
        }
    });
/*	$.post(_basePath + "/dm/shippingTierDtl/queryByLocationAndOrder",
			        requestData,
					function(data) {
						if (!data.rows || data.rows.length < 1) {
							liger.get('deliveryCompany').set({
								data : data.rows
							});
							liger.get('deliveryCompany').setValue();
							$('#shippingTier').text(0);
							summary.shippingTier = 0;
							f_calculateLinePrice();
							return;
						}
						liger.get('deliveryCompany').set({
							data : data.rows
						});
						var lowPrice = {};
						lowPrice.price = Number.MAX_VALUE;

						for ( var i in data.rows) {
							// 计算运费的金额
							var calMoney;
							// 是否包含运费
							if (data.rows[i].privilegeFlag == 'Y') {
								// 计算方式
								if (data.rows[i].calculationType == 'AFTAX') {
									calMoney = Number($("#beforeTax").text())
											- summary.discountAmt;
								} else {
									calMoney = Number($("#afterTax").text())
											- summary.discountAmt;
								}
							} else {
								if (data.rows[i].calculationType == 'AFTAX') {
									calMoney = $("#beforeTax").text();
								} else {
									calMoney = $("#afterTax").text();
								}
							}
							var shippingTier = data.rows[i];
							for ( var j in shippingTier.shippingTierDtls) {
								var shippingTierDtl = shippingTier.shippingTierDtls[j];
								if (calMoney >= shippingTierDtl.invAmountFrom
										&& (!shippingTierDtl.invAmountTo || calMoney < shippingTierDtl.invAmountTo)) {
									if (lowPrice.price > shippingTierDtl.shippingFee) {
										lowPrice.price = shippingTierDtl.shippingFee;
										lowPrice.name = shippingTier.shippingTierName;
										lowPrice.value = shippingTier.shippingTierId;
										if(shippingTier.shipIncludeTax != 'NO'){
											if(shippingTierDtl.taxShippingFee != null){
												lowPrice.tax = shippingTierDtl.taxShippingFee;
											}
											if(shippingTierDtl.afterTaxShippingFee != null){
												lowPrice.price = shippingTierDtl.afterTaxShippingFee;
											}
										}
										lowPrice.shipIncludeTax = shippingTier.shipIncludeTax;
									}
								}
							}
						}
						if (lowPrice.price == Number.MAX_VALUE) {
							lowPrice.price = 0;
						}
						summary.shippingTier = lowPrice.price;
						liger.get('deliveryCompany').setValue(lowPrice.value);
						liger.get('deliveryCompany').setText(lowPrice.name);
						$('#shippingTier').text(lowPrice.price.toFixed(summary.precision))
						if(lowPrice.shipIncludeTax =='NO'){
							$('#shippingTierTax').parent().hide();
						}else{
							$('#shippingTierTax').parent().show();
							summary.shippingTierTax = lowPrice.tax;
							$('#shippingTierTax').text(summary.shippingTierTax.toFixed(summary.precision))
						}
						
						return data.rows;
					})*/
}

/**
 * 重置运费
 */
function f_resetShippingTier() {
	var data = liger.get('deliveryCompany').options.data;
	if (!data) {
		f_queryShippingTier(deliveryaddress.getSelectedRow());
		return;
	}
	if(data.length == 0){
		return;
	}
	//包邮
	if(liger.get("freeShipping")&&liger.get("freeShipping").getValue()){
		summary.shippingTier = 0;
	}else{
		summary.shippingTier = data[0].includetaxShippingFee ;
	}
	liger.get('deliveryCompany').setValue(data[0].shippingTierId);
	liger.get('deliveryCompany').setText(data[0].shippingTierName);
	$('#shippingTier').text(summary.shippingTier.toFixed(summary.precision));
}

/**
 * 显示商品包详情
 * 
 * @param rowdata
 *            行数据值
 * @param index
 *            行索引
 * @param value
 *            值
 */
function f_renderItemName(rowdata, index, value) {
	if (!value) {
		return;
	}
	if (rowdata.itemType != 'PACKG') {
		return value;
	}
	var divclass = value
			+ "&nbsp;&nbsp;<img src='../lib/ligerUI/skins/icons/search.gif' id='gridimg' index="
			+ index + "/>";
	return divclass;
}

/**
 * 设置autoship订单详细数据
 */
function f_setAusoShipDetail(data) {
	var memberCode = liger.get("memberId").getText();
	var marketName = liger.get("marketId").getText();
	var salesOrgName = liger.get("salesOrgId").getText();
	om_oc_form.setData(data);
	$("#headerId").val(data.autoshipId);
	liger.get("memberId").setText(memberCode);
	liger.get("marketId").setText(marketName);
	liger.get("salesOrgId").setText(salesOrgName);
	// 销售组织以及市场货币设置
	if (data.spmSalesOrganization) {
		f_setMarketAndCurrency(data.spmSalesOrganization.market,
				data.spmCurrency.currencyCode);
		liger.get('salesOrgId').setValue(data.spmSalesOrganization.salesOrgId);
		liger.get('salesOrgId').setText(data.spmSalesOrganization.name);
	}
	// 订单状态设置
	for ( var i in autoShipStatusData) {
		if (autoShipStatusData[i].value == data.autoshipStatus) {
			liger.get("autoshipStatus").setText(autoShipStatusData[i].meaning);
			break;
		}
	}
	// 订单行设置
	var gridDatas = {};
	gridDatas.rows = data.lines;
	linegrid.set({
		data : gridDatas
	});
	// 订单用户信息设置
	if (data.member) {
		liger.get("cnName").setValue(data.member.chineseName);
		liger.get("enName").setValue(data.member.englishName);
		liger.get("memberId").setValue(data.member.memberId);
		liger.get("memberId").setText(data.member.memberCode);
		if (data.member.exchangeBalance) {
			liger.get("exchangeBalance").setValue(data.member.exchangeBalance);
			$("#exchangeBalance").text(data.member.exchangeBalance);
		}
		if (data.member.remainingBalance) {
			liger.get("remainingBalance")
					.setValue(data.member.remainingBalance);
			$("#remainingBalance").text(data.member.remainingBalance);
		}

		liger.get("currentPoints").setValue(data.member.salesPiont);
		liger.get("currentPV").setValue(data.member.currentPv);
		memberStatus = data.member.status;
		// 订单配送设置
		f_setDelivery(data.member.memSites, data.logistics, data.salesSites);
	} else {
		// 订单配送设置
		f_setDelivery(null, data.logistics, data.salesSites);
	}
	// 订单信用卡设置
	if (data.memCard) {
		liger.get("creditCardId").set("data", data.memCard);
		liger.get("creditCardId").setValue(data.creditCardId);
	}
	f_calculateLinePrice();
}

/**
 * 修改autoship订单状态
 */
function f_updateAutoShipStatus(status) {
	var autoShipId = $("#autoshipId").val();
	if (!autoShipId || !status) {
		Hap
				.showError($l("type.com.lkkhpg.dsis.common.order.autoship.not_save"));
		return;
	}
	var data = {
		autoshipId : autoShipId,
		autoshipStatus : status
	};
	$.ajax({
		type : 'POST',
		url : _basePath + "/om/autoship/updateStatus",
		data : data,
		success : function(json) {
			if (json.success) {
				Hap.showSuccess();
				for ( var i in autoShipStatusData) {
					if (autoShipStatusData[i].value == status) {
						liger.get("autoshipStatus").setValue(status);
						liger.get("autoshipStatus").setText(
								autoShipStatusData[i].meaning);
						break;
					}
				}
				initButton(true);
			}
		}
	});
}

/**
 * 配送地址及运费设置
 * 
 * @param memSites
 *            会员地址信息
 * @param logistics
 *            配送地址信息
 * @param sites
 *            地址集合
 */
function f_setDelivery(memSites, logistics, sites) {
	if (!memSites) {
		f_setDefaultAddress(sites)
	} else {
		var addressData = {};
		var billAddress = new Array();
		var shipAddress = new Array();
		for ( var i in memSites) {
			if (memSites[i].siteUseCode == "BILL") {
				billAddress.push(memSites[i]);
			} else if (memSites[i].siteUseCode == "SHIP") {
				shipAddress.push(memSites[i]);
			}
		}
		if (sites) {
			for ( var i in sites) {
				if (sites[i].siteType == "BILL") {
					$("#billSiteId").val(sites[i].salesSiteId);
					var isSelect = false;
					for ( var j in billAddress) {
						if (billAddress[j].address == sites[i].address
								&& billAddress[j].phone == sites[i].phone
								&& billAddress[j].name == sites[i].name) {
							billAddress[j].select = true;
							isSelect = true;
						}
					}
					if (!isSelect) {
						sites[i].spmLocation = {};
						sites[i].spmLocation.zipCode = sites[i].zipCode;
						sites[i].select = true;
						billAddress.push(sites[i]);
					}
				} else {
					$("#deliverySiteId").val(sites[i].salesSiteId);
					liger.get("deliveryType").setValue("SHIPP");
					var isSelect = false;
					for ( var j in shipAddress) {
						if (shipAddress[j].address == sites[i].address
								&& shipAddress[j].phone == sites[i].phone
								&& shipAddress[j].name == sites[i].name) {
							shipAddress[j].select = true;
							isSelect = true;
						}
					}
					if (!isSelect) {
						sites[i].spmLocation = {};
						sites[i].spmLocation.zipCode = sites[i].zipCode;
						sites[i].select = true;
						// deliveryaddress.addRow(sites[i]);
						// var datas=deliveryaddress.getData();
						shipAddress.push(sites[i]);

					}
				}
			}
		}
		addressData.rows = billAddress;
		addressgrid.set({
			data : addressData
		});
		addressData.rows = shipAddress;
		deliveryaddress.set({
			data : addressData
		});
	}

	if (liger.get("deliveryType").getValue() != "SHIPP") {
		summary.shippingTier = 0;
	}
	if (logistics) {
		if (logistics.shippingFee) {
			summary.shippingTier = logistics.shippingFee;
		}
		$("#shippingTier").text(logistics.shippingFee);
		$("#logisticsId").val(logistics.logisticsId);
		liger.get("deliveryCompany").setValue(logistics.shippingTierId);
		liger.get("deliveryCompany").setText(logistics.shippingTierName);

	}
}

/**
 * 数据提交前验证
 */
function valideBefore() {
	var orderType = liger.get("orderType").getValue();
	if (!liger.get("orderType").getValue() || !liger.get("channel").getValue()
			|| !liger.get("salesOrgId").getValue()) {
		$.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesorder.miss'))
		return false;
	}
	if (liger.get("deliveryType").getValue() == "SHIPP") {
		if (!deliveryaddress.getSelectedRow()) {
			$.ligerDialog
					.error($l('type.com.lkkhpg.dsis.common.order.salesorder.delivery_not_null'));
			return false;
		}
		if (!addressgrid.getSelectedRow()) {
			$.ligerDialog.error($l('msg.error.om.billing_address_null'));
			return false;
		}
		if (!liger.get("deliveryCompany").getValue()) {
			$.ligerDialog.error($l('msg.error.order.deliverycompany_null'));
			return false;
		}
	}
	if (liger.get("channel") == "SRVC" && !liger.get("serviceCenterId")) {
		$.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesorder.servicecenter_not_null'));
		return false;
	}
	/*if (!addressgrid.getSelectedRow()) {
		$.ligerDialog.error($l('msg.error.om.billing_address_null'));
		return false;
	}*/
	var lineDatas = linegrid.getData();
	if (!lineDatas || lineDatas.length < 1) {
		$.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesline.item_not_null'));
		return false;
	}
	for ( var i in lineDatas) {
		if (!lineDatas[i].itemId) {
			$.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesline.item_not_null'));
			return false;
		}
	}

	if (orderType == "DIRP" || orderType == "STFP" || orderType == "STFPT"|| orderType == "CONP"|| orderType == "CONPT") {
		return true;
	} else {
		if (!liger.get("memberId").getValue()) {
			$.ligerDialog
					.error($l('type.com.lkkhpg.dsis.common.order.salesorder.not_member'));
			return false;
		}
		return true;
	}
}

/**
 * 清除会员信息.
 */
function cleanMember() {
	liger.get("memberId").setValue("");
	liger.get("memberId").setText("");
	liger.get("cnName").setValue("");
	liger.get("enName").setValue("");
	liger.get("exchangeBalance").setValue("");
	liger.get("remainingBalance").setValue("");
	liger.get("currentPoints").setValue("");
	liger.get("currentPV").setValue("");
	if ($("#exchangeBalance")) {
		$("#exchangeBalance").html(summary.zero);
		$("#remainingBalance").html(summary.zero);
	}
	if (liger.get("deliveryCompany")) {
		liger.get("deliveryCompany").setValue("");
		liger.get("deliveryCompany").setText("");
		$("#shippingTier").html("");
	}
	if (!orderDetail) {
		var data = {};
		data.rows = [];
		if (addressgrid) {
			addressgrid.set({
				data : data
			});
		}
		if (deliveryaddress) {
			deliveryaddress.set({
				data : data
			});
		}
	}

}

/**
 * 重置订单行
 * 
 * @param isCopy
 *            是否复制
 * @param isChangePrice
 *            是否更改价格
 * @param isChangeInvOrg
 *            是否更改库存组织
 */
function reSetLine(isCopy, isChangePrice, isChangeInvOrg) {
	if (!linegrid) {
		return;
	}
	var lineDatas = linegrid.getData();
	if (isCopy) {
		for ( var i in lineDatas) {
			lineDatas[i].lineId = "";
			lineDatas[i].headerId = "";
			lineDatas[i].PVSum = 0;
			// lineDatas[i].unitOrigPrice = "";
			// lineDatas[i].unitDiscountPrice = "";
			// lineDatas[i].unitSellingPrice = "";
			// lineDatas[i].amount = "";
			// lineDatas[i].unitRedeemPoint = "";
			// lineDatas[i].redeemPoint = "";
		}
		var newData = {};
		newData.rows = lineDatas;
		linegrid.set({
			data : newData
		});
		f_calculateLinePrice();
		return;
	}
	if (isChangeInvOrg) {
		if (liger.get("deliveryType")
				&& liger.get("deliveryType").getValue() == "PCKUP") {
			for ( var i in lineDatas) {
				if (!lineDatas[i].defaultInvOrgId) {
					lineDatas[i].defaultInvOrgId = defaultInvOrg;
				}
			}
		} else {
			for ( var i in lineDatas) {
				//coupon zhaoqi修改----影响库存显示是空
				//lineDatas[i].defaultInvOrgId = "";
			}
		}

		var newData = {};
		newData.rows = lineDatas;
		linegrid.set({
			data : newData
		});
		return;
	}
	if (isChangePrice) {
		if ($("#headerId").val()
				&& lineDatas
				&& (orderDetail.orderType != liger.get("orderType").getValue() || orderDetail.channel != liger
						.get("channel").getValue())) {
			$.ajax({
				type : 'POST',
				url : _basePath + '/om/order/line/remove',
				async : false,
				data : JSON2.stringify(lineDatas),
				contentType : "application/json; charset=utf-8",
				success : function(json) {
					if (json.success) {
						var newData = {};
						newData.rows = [];
						linegrid.set({
							data : newData
						});
						f_calculateLinePrice();
						return;
					}
				}
			});
		} else {
			var newData = {};
			newData.rows = [];
			linegrid.set({
				data : newData
			});
			f_calculateLinePrice();
			return;
		}

	}
}

/**
 * 校验autoship
 */
function validateAutoship() {
	if (!om_oc_form.valid()) {
		return false;
	}
	if (!$("#creditCardId").val()) {
		$.ligerDialog
				.error($l('type.com.lkkhpg.dsis.common.order.autoship.not_credit'))
		return false;
	}
	var lineDatas = linegrid.getData();
	for ( var i in lineDatas) {
		if (!lineDatas[i].itemId) {
			$.ligerDialog
					.error($l('type.com.lkkhpg.dsis.common.order.salesline.item_not_null'));
			return false;
		}
	}
	if (liger.get("deliveryType").getValue() == "SHIPP") {
		if (!deliveryaddress.getSelectedRow()) {
			$.ligerDialog
					.error($l('type.com.lkkhpg.dsis.common.order.salesorder.delivery_not_null'));
			return false;
		}
		if (!addressgrid.getSelectedRow()) {
			$.ligerDialog.error($l('msg.error.order.billing_address_null'));
			return false;
		}
		if (!liger.get("deliveryCompany").getValue()) {
			$.ligerDialog.error($l('msg.error.order.deliverycompany_null'));
			return false;
		}
	}

	/*--------- 校验实付款----- */
	if (minMonery > (summary.currency - -summary.shippingTier)) {
		$.ligerDialog
				.error($l("type.com.lkkhpg.dsis.common.order.dto.salesorder.minimumorderamount")
						+ minMonery);
		return false;
	}

	/*--------- 校验最小PV----- */
	if (minPV > summary.pv) {
		$.ligerDialog
				.error($l("type.com.lkkhpg.dsis.common.order.dto.salesorder.minimumorderpv")
						+ minPV);
		return false;
	}
	return true;
}

/**
 * autoship 展示不可编辑
 */
function autoshipReadOnly() {
	liger.get("memberId").set({
		readonly : true
	});
	liger.get("executeDate").set({
		readonly : true
	});
	liger.get("executeType").set({
		readonly : true
	});
	liger.get("remark").set({
		readonly : true
	});
	liger.get("deliveryCompany").set({
		readonly : true
	});
	liger.get("creditCardId").set({
		readonly : true
	});
	liger.get("deliveryType").set({
		readonly : true
	});
	liger.get("salesOrgId").set({
		readonly : true
	});
	$("#addCreditCar").hide();
	$("[ toolbarid='addLineBtn']").hide();
	$("[ toolbarid='deleteLineBtn']").hide();
	linegrid.set({
		enabledEdit : false,
		rowSelectable : false,
		selectable : false
	});
	deliveryaddress.set({
		enabledEdit : false,
		rowSelectable : false,
		selectable : false
	});
	addressgrid.set({
		enabledEdit : false,
		rowSelectable : false,
		selectable : false
	});
}

/**
 * 激活autoship可编辑
 */
function autoshipActive() {
	liger.get("memberId").set({
		readonly : false
	});
	liger.get("executeDate").set({
		readonly : true
	});
	liger.get("executeType").set({
		readonly : true
	});
	liger.get("remark").set({
		readonly : false
	});
	liger.get("deliveryCompany").set({
		readonly : false
	});
	$("#addCreditCar").show();
	$("[ toolbarid='addLineBtn']").show();
	$("[ toolbarid='deleteLineBtn']").show();
	linegrid.set({
		enabledEdit : true,
		rowSelectable : true,
		selectable : true
	});
	deliveryaddress.set({
		enabledEdit : true,
		rowSelectable : true,
		selectable : true
	});
	addressgrid.set({
		enabledEdit : true,
		rowSelectable : true,
		selectable : true
	});
}

/**
 * 货到付款订单金额校验 如果小于当前市场的金额，则需要在运费添加需要补偿的费用
 */
function checkPayment() {
	if (!liger.get("isCod") || !liger.get("isCod").getValue()) {
		return true;
	}
	if (liger.get("freeShipping") && liger.get("freeShipping").getValue()) {
		return true;
	}
	// 查询不到系统参数则不启用规则，返回true
	if (!freePayMent || !freeFreight) {
		return true;
	}
	if (summary.currency < freePayMent) {
		return false;
	} else {
		return true;
	}
}

/**
 * 获取商品包详情
 * 
 * @param row
 *            商品包行
 * @param detailPanel
 *            详情的pannel
 * @param callback
 *            回调函数
 */
function f_getItemPackageDetail(row, detailPanel, callback) {
	if (row.itemType != 'PACKG' && row.itemType != 'VN' && row.itemType != 'VY') {// 非商品包的直接返回
		return;
	}
	var grid = document.createElement('div');
	$(detailPanel).append(grid);
	$(grid)
			.css('margin', 10)
			.ligerGrid(
					{
						columns : [
								{
									display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemid'),
									name : 'itemNumber',
									type : 'text'
								},
								{
									display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemname'),
									name : 'itemName',
									type : 'text'
								},
								{
									display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.quantity'),
									name : 'quantity'
								} ],
						isScroll : false,
						showToggleColBtn : false,
						width : 500,
						url : _basePath
								+ "/inv/item/queryHierarchyByItemId?itemId="
								+ row.itemId,
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
	if ($(liger.get("remark").element).hasClass("l-text-field error")) {
		return;
	}
	var orderType = liger.get("orderType").getValue();
	if (!liger.get("memberId").getValue() && orderType != 'DIRP'
			&& orderType != 'CONP' && orderType != 'CONPT' && orderType != 'STFP'
			&& orderType != 'STFPT') {
		$.ligerDialog
				.error($l("type.com.lkkhpg.dsis.common.order.salesorder.not_member"));
		return;
	}
	if (!liger.get("serviceCenterId").getValue()
			&& liger.get("channel").getValue() == "SRVC") {
		$.ligerDialog.error($l("type.com.lkkhpg.dsis.common.order.salesorder.servicecenter_not_null"));
		return;
	}
	var lineDatas = linegrid.getData();
	var itemLineDatas = new Array();
	for (var j = 0; j < lineDatas.length; j++) {
		if (lineDatas[j].itemId) {
			itemLineDatas.push(lineDatas[j]);
		}
	}
	if (itemLineDatas.length == 0) {
		$.ligerDialog
				.error($l('type.com.lkkhpg.dsis.common.order.salesline.item_not_null'));
		return;
	}
	for ( var i in itemLineDatas) {
		if (!itemLineDatas[i].itemId) {
			$.ligerDialog
					.error($l('type.com.lkkhpg.dsis.common.order.salesline.item_not_null'));
			return;
		}
	}

	// 销售积分
	if (summary.points > Number(liger.get("currentPoints").getValue())) {
		f_save(false, false, false, true);
		$.ligerDialog.error($l('msg.error.order.sales_point_insufficient'));
		return

	}
	// 换货金额
	/*else if (summary.exchange > Number(liger.get("exchangeBalance").getValue())) {
		f_save(false, false, false, true);
		$.ligerDialog
				.error($l('msg.error.order.exchanging_balance_insufficient'));
		return

	}*/ else {
		f_save(false, false, true);
	}

	var tabId = window.top.tab.getSelectedTabItemID();
	var headerId = $("#headerId").val();
	if (!headerId) {
		$.ligerDialog.error($l("msg.error.order_pls_save_order"));
		return;
	}
	window.top.f_removeTab(tabId);
	window.top.f_addTab('ORDER_CREATE', $l('msg.info.order.confirm'), _basePath
			+ "/om/om_order_confirm.html?orderId=" + headerId);
}

/**
 * 跳转订单创建页
 */
function f_openCreate() {
	var tabId = window.top.tab.getSelectedTabItemID();
	var headerId = $("#headerId").val();
	window.top.f_removeTab(tabId);
	window.top.f_addTab('ORDER_CREATE', $l('msg.info.order.new_order'),
			_basePath + "/om/om_order_create.html?orderId=" + headerId);
}

/**
 * 初始化销售订单页面.
 * 
 * @param isConfirm
 *            是否确认页面
 */
function initSalesOrder(isConfirm) {
	if (!isConfirm) {
		$("#delBtn").ligerButton({
			click : function() {
				f_cancel();
			},
			text : $l('sys.hand.btn.cancel')
		});
		$("#saveBtn").ligerButton({
			click : function() {
				f_save(true, false);
			},
			text : $l('sys.hand.btn.save')
		});
		$("#copyBtn")
				.ligerButton(
						{
							click : f_copy,
							text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.copytemplate')
						});
		$("#nextBtn").ligerButton({
			click : f_openConfirm,
			text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.next')
		});
		liger.get('serviceCenterId').set({
			readonly : true
		});
	} else {
		$("#couponsAdjust")
				.ligerPanel(
						{
							title : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.coupons'),
							width : '98%',
							height : 'auto'
						});
		$("#billPannel")
				.ligerPanel(
						{
							title : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.billaddress'),
							width : '98%',
							height : 'auto'
						});
		$("#deliveryType").ligerComboBox({
			css : 'l-text-required',
			data : deliveryTypeData,
			valueField : "value",
			textField : "meaning",
			cancelable : false,
			focusWhenSetValue : false,
			readonly : false,
			onSelected : function(value) {
				if(value == 'SHIPP'){
					if(!changgetax){
						//TODO 配送地选择是否重新计算税，coupon二期修改 start
						if(marketCode != "MY"){
							changgetax = true;
						}
						// end
					}
				}
				if(changgetax){
					
						if ( liger.get('deliveryType').getValue() == "SHIPP") {
							var deliveryData = deliveryaddress.getSelectedRow();
							var diliverySite;
							if(deliveryData){
								if (deliveryData.cityCode) {
									diliverySite = {
										cityCode : deliveryData.cityCode,
										stateCode : deliveryData.stateCode,
										countryCode : deliveryData.countryCode
									}
								} else {
									var d = deliveryData.spmLocation;
									diliverySite = {
										cityCode : d.cityCode,
										stateCode : d.stateCode,
										countryCode : d.countryCode
									}
								}
								changgeSiteUpdatetax(diliverySite);
							}
						}else {
							changgeSiteUpdatetax();
						}
				}
				validateDeliver(false);
			},
			validate : {
				required : true
			},
			render : function(data) {
				if (!data) {
					return;
				}
				for ( var i in deliveryTypeData) {
					if (deliveryTypeData[i].value == data) {
						return deliveryTypeData[i].meaning;
					}
				}

			}
		});

		defaultType = "PCKUP";
		for ( var i in deliveryTypeData) {
			if (deliveryTypeData[i].value == defaultType) {
				liger.get("deliveryType").setValue(defaultType);
				liger.get("deliveryType").setText(deliveryTypeData[i].meaning);
				break;
			}
		}
		$("#deliveryCompany")
				.ligerComboBox(
						{
							css : 'combobox',
							valueField : "shippingTierId",
							textField : "shippingTierName",
							cancelable : false,
							focusWhenSetValue : false,
							onSelected : function(value, text, data) {
								if (data) {
									//包邮
									if(liger.get("freeShipping")&&liger.get("freeShipping").getValue()){
										summary.shippingTier = 0;
										$('#shippingTierTax').parent().hide();
									}else{
										if(data.shipIncludeTax =='NO'){
											//#PE20-257 2017-09-21 BEGIN
											//if(shippingDiscount<=data.includetaxShippingFee){
												summary.shippingTier = data.includetaxShippingFee;//-shippingDiscount;
											//}else{
												//summary.shippingTier=0;
												//$('#shippingTier').text(0);
											//}
											//#PE20-257 2017-09-21 END
											$('#shippingTierTax').parent().hide();
										}else{
											//#PE20-257 2017-09-21 BEGIN
											//if(shippingDiscount<=data.includetaxShippingFee){
												summary.shippingTier = data.includetaxShippingFee;//-shippingDiscount;
												//$('#shippingTierTax').parent().show();
												//$('#shippingTierTax').text(data.taxShippingFee.toFixed(summary.precision))
											//}else{
												//summary.shippingTier=0;
												//$('#shippingTier').text(0);
												//$('#shippingTierTax').parent().hide();
											//}
											$('#shippingTierTax').parent().show();
											$('#shippingTierTax').text(data.taxShippingFee.toFixed(summary.precision))
											//#PE20-257 2017-09-21 END
										}
										//#PE20-257 2017-09-21 BEGIN
										if(data.includetaxShippingFee==0){
											$('#shippingTier').text(0);
											$('#shippingTierTax').parent().hide();
										}
										//#PE20-257 2017-09-21 END
									}
									$("#shippingTier").text(summary.shippingTier.toFixed(summary.precision));
									
									if (liger.get("deliveryType").getValue() != "SHIPP") {
										if (Number(summary.currency - -summary.adjustMents - summary.discountAmt) >= 0) {
											$("#actrualPayAmt").text(
															Number(summary.currency - -summary.adjustMents- summary.discountAmt)
																	.toFixed(summary.precision));
										} else {
											Hap.showError($l("msg.error.om.actual_payment_amount_error"));
											$("#actrualPayAmt").text(summary.zero);
										}

									} else {
										if (Number(summary.currency - -summary.shippingTier - -summary.adjustMents- summary.discountAmt) >= 0) {
											$("#actrualPayAmt").text(
															Number(summary.currency - -summary.shippingTier - -summary.adjustMents- summary.discountAmt)
																	.toFixed(summary.precision));
										} else {
											Hap.showError($l("msg.error.om.actual_payment_amount_error"));
											$("#actrualPayAmt").text(summary.zero);
										}

									}
								}
								/*if (data) {
									var price = Number.MAX_VALUE;
									// 计算运费的金额
									var calMoney;
									// 是否包含运费
									if (data.privilegeFlag == 'Y') {
										// 计算方式
										// 不含税
										if (data.calculationType == 'AFTAX') {
											calMoney = $("#beforeTax").text() - summary.discountAmt;
										} else {
											calMoney = $("#afterTax").text() - summary.discountAmt;
										}
									} else {
										if (data.calculationType == 'AFTAX') {
											calMoney = $("#beforeTax").text();
										} else {
											calMoney = $("#afterTax").text();
										}
									}
									for ( var i in data.shippingTierDtls) {
										if (data.shippingTierDtls[i].shippingFee < price
												&& calMoney >= data.shippingTierDtls[i].invAmountFrom
												&& (!data.shippingTierDtls[i].invAmountTo || calMoney < data.shippingTierDtls[i].invAmountTo)) {
											

										}
									}

								} */
							}
						});
		$("#payAdjust")
				.ligerPanel(
						{
							title : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.adjustment'),
							width : '98%',
							height : 'auto'
						});
		liger.get('serviceCenterId').set({
			readonly : true
		});
		liger.get('memberId').set({
			readonly : true
		});
		liger.get("payAdjust").collapse();
		$("#coupons").ligerComboBox({
			css : 'combobox', 
			valueField : "assignCode",
			textField : "name", 
			focusWhenSetValue : false,
			cancelable : true,
			autocomplete : false,
			onBeforeOpen : function(e) {
				setAvailableVouchers(this, 0);
			},
			onBeforeSelect : function(value, text, data) {
				var currentValue = this.getValue();
				var isTransferable = data.isTransferable;
				var couponCode = data.couponCode;
					if (currentValue) {
						if (currentValue != value) {
							changeVoucher(this.getSelected(), data, 0);
						}
					} else {
						selectVoucher(data, 0);
					}
				
			},
			onChangeValue : function (value, text, data){  
				if(value == null || value == ""){
					removeVoucher(value);
				} 
			}
		});
		// 人工调整里面的下拉框调整
		$("#adjustType").ligerComboBox({
			width : 120,
			data : adjustTypeData,
			valueField : "value",
			textField : "meaning",
			cancelable : false
		});
		liger.get('serviceCenterId').set({
			readonly : true
		});
		liger.get('adjustType').setValue("add");
		for ( var i in adjustTypeData) {
			if (adjustTypeData[i].value == "add") {
				liger.get('adjustType').setText(adjustTypeData[i].meaning);
			}
		}
		$("#delBtn").ligerButton({
			click : function() {
				f_cancel();
			},
			text : $l('sys.hand.btn.cancel')
		});
		$("#submitBtn").ligerButton({
			click : f_sumbitSales,
			text : $l('sys.hand.btn.submit')
		});
		$("#returnBtn")
				.ligerButton(
						{
							click : f_openCreate,
							text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.return')
						});
	}
}

function getshowLineGrid(isPoint) {
	var options = {
		columns : o_getConfirmLineColumns(isPoint),
		detail : {
			onShowDetail : f_getItemPackageDetail,
			height : 'auto'
		},
		enabledEdit : true,
		usePager : false,
		width : '99%',
		onBeforeSelectRow : function(e) {
			DsisOrderCreate.editLineIndex = e.rowindex;
		}
	}
	return options;
}

/**
 * 创建订单时订单行列表
 * 
 * @param isAutoship
 *            是否autoship
 * @param isPoint
 *            是否积分购买
 * @param isSelf
 *            是否自提
 * @param userType
 *            用户类型
 * @return 列集合
 */
function o_getCreateLineColumns(isAutoship, isPoint, isSelf, userType) {
	defaultItemSalesType = "PURC";
	var unit = {
		display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.unitorigprice"),
		name : 'unitOrigPrice',
		align : 'center',
		type : 'float',
		render : function(data) {
			if (!data || !data.itemId) {
				return;
			}
			if (!data.unitOrigPrice) {
				data.unitOrigPrice = 0;
				return summary.zero;
			}
			if (data.itemSalesType && data.itemSalesType == "FREE") {
				data.unitOrigPrice = 0;
				return summary.zero;
			}
			return Number(data.unitOrigPrice).toFixed(summary.precision);
		}
	};
	var amount = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.amount'),
		name : 'amount',
		align : 'center',
		type : 'float',
		render : function(data) {
			if (!data || !data.itemId) {
				return;
			}
			if (!data.quantity) {
				data.quantity = 1;
			}
			if (!data.unitOrigPrice) {
				data.unitOrigPrice = 0;
			}
			if (data.itemSalesType && data.itemSalesType == "FREE") {
				data.unitOrigPrice = 0;
			}
			return Number(data.unitSellingPrice * data.quantity).toFixed(
					summary.precision);
		}
	};
	var saleType = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemsalestype'),
		name : 'itemSalesType',
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
		},
		validate : {
			required : true
		}
	};
	if (!isAutoship) {
		var salesTypeData = new Array();
		var includeTye = new Array();
		if (userType && userType == "IPONT") {
			var orderType = liger.get("orderType").getValue();
			switch (orderType) {
			case "BIGF": {
				includeTye.push("GIFT");
				defaultItemSalesType = "GIFT";
				break;
			}
			default: {
				includeTye.push("PURC");
				defaultItemSalesType = "PURC";
				break;
			}
			}
		} else {
			var orderType = liger.get("orderType").getValue();
			switch (orderType) {
			case "STDP": {
				includeTye.push("PURC");
				includeTye.push("EXCH");
				includeTye.push("FREE");
				defaultItemSalesType = "PURC";
				break;
			}
			case "BIGF": {
				includeTye.push("GIFT");
				defaultItemSalesType = "GIFT";
				break;
			}
			case "RDEM": {
				includeTye.push("REDE");
				defaultItemSalesType = "REDE";
				break;
			}
			case "VIPP": {
				includeTye.push("PURC");
				includeTye.push("EXCH");
				includeTye.push("FREE");
				defaultItemSalesType = "PURC";
				break;
			}
			default: {
				includeTye.push("PURC");
				defaultItemSalesType = "PURC";
				break;
			}
			}
		}
		for ( var i in productSalesTypeData) {
			if ($.inArray(productSalesTypeData[i].value, includeTye) >= 0) {
				salesTypeData.push(productSalesTypeData[i]);
			}
		}
		saleType.editor = {
			type : 'select',
			valueField : "value",
			textField : "meaning",
			cancelable : false,
			data : salesTypeData,
			value : salesTypeData[0].value,
			text : salesTypeData[0].meaning,
			onChanged : function(e) {
				if ((orderType == "STDP" || orderType == "VIPP")
						&& e.record.itemSalesType == "PURC") {
					var details = e.record.priceDetail;
					if (!details) {
						var param = {
							'itemId' : e.record.itemId,
							'currency' : liger.get("currency").getValue(),
							uomCode : e.record.uomCode,
							salesOrgId : defaultSaleOrg
						};
						$
								.getJSON(
										_basePath
												+ "/inv/price/queryPriceByItemId?",
										param,
										function(data) {
											details = data.rows;
											e.record.priceDetail = data.rows;
											for ( var i in details) {
												if (details[i].priceType == 'PV') {
													e.record.pv = details[i].unitPrice;
												}
												if (f_getPriceType() == details[i].priceType) {
													e.record.unitOrigPrice = details[i].unitPrice;
												}
											}
										});
					} else {
						for ( var i in details) {
							if (details[i].priceType == 'PV') {
								e.record.pv = details[i].unitPrice;
							}
							if (f_getPriceType() == details[i].priceType) {
								e.record.unitOrigPrice = details[i].unitPrice;
							}
						}
					}
				} else if (e.record.itemSalesType == "FREE") {
					e.record.unitOrigPrice = 0;
				} else if (e.record.itemSalesType == "EXCH") {
					var details = e.record.priceDetail;
					if (!details) {
						var param = {
							'itemId' : e.record.itemId,
							'currency' : liger.get("currency").getValue(),
							uomCode : e.record.uomCode,
							salesOrgId : defaultSaleOrg
						};
						$
								.getJSON(
										_basePath
												+ "/inv/price/queryPriceByItemId?",
										param,
										function(data) {
											details = data.rows;
											e.record.priceDetail = data.rows;
											for ( var i in details) {
												if (f_getPriceType() == details[i].priceType) {
													e.record.unitOrigPrice = details[i].unitPrice;
												}
											}
										});
					} else {
						for ( var i in details) {
							if (f_getPriceType() == details[i].priceType) {
								e.record.unitOrigPrice = details[i].unitPrice;
							}
						}
					}
				}
				f_setLinePrice(e.record);
			}
		}
	}
	if (isPoint) {
		unit = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.unitredeempoint'),
			name : 'unitRedeemPoint',
			align : 'center',
			type : 'text'
		};
		amount = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.redeempoint'),
			name : 'redeemPoint',
			align : 'center',
			type : 'text',
			render : function(data) {
				if (!data || !data.itemId) {
					return;
				}
				if (!data.quantity) {
					data.quantity = 1;
				}
				if (!data.unitRedeemPoint) {
					data.unitRedeemPoint = 0;
				}
				return Number(data.unitRedeemPoint * data.quantity).toFixed(
						summary.precision);
			}
		};
	}
	var columns = [
			saleType,
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemid'),
				name : 'itemNumber',
				align : 'center',
				width : 100,
				textField : 'itemNumber',
				editor : o_itemPupop(),
				validate : {
					required : true
				}
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemname'),
				name : 'itemName',
				width : 260,
				align : 'center',
				type : 'text'
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pv'),
				name : 'pv',
				align : 'center',
				type : 'text',
				render : function(data) {
					if (!data || !data.itemId) {
						return;
					}
					if (!data.pv) {
						data.pv = 0;
						return;
					}

					var orderType = "STDP";
					if (liger.get("orderType")) {
						orderType = liger.get("orderType").getValue();
					}
					if ((orderType == "STDP" || orderType == "VIPP")
							&& data.itemSalesType == "PURC") {
						if (data.pv > 0) {
							return data.pv;
						} else {
							return "0";
						}
						var details = data.priceDetail;
						if (!details) {
							var param = {
								'itemId' : data.itemId,
								'currency' : liger.get("currency").getValue(),
								uomCode : data.uomCode,
								salesOrgId : defaultSaleOrg
							};
							$.getJSON(_basePath
									+ "/inv/price/queryPriceByItemId?", param,
									function(json) {
										details = json.rows;
										data.priceDetail = json.rows;
										for ( var i in details) {
											if (details[i].priceType == 'PV') {
												data.pv = details[i].unitPrice;
											}
										}
									});
						} else {
							for ( var i in details) {
								if (details[i].priceType == 'PV') {
									data.pv = details[i].unitPrice;
								}
							}
						}
						if (data.pv == 0) {
							return "0"
						}
						return data.pv;
					} else {
						data.pv = 0;
						return "0";
					}

				}
			},
			unit,
			{
				display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.quantity"),
				name : 'quantity',
				align : 'center',
				type : 'int',
				editor : {
					type : 'int',
					onChange : function(e) {
						if (e.value > 99999) {
							e.record.quantity = 99999;
							e.value = 99999;
						} else if (e.value < 1) {
							e.record.quantity = 1;
							e.value = 1;
						}
						if (e.value > e.record.quantity) {
							if (e.record.unitRedeemPoint) {
								summary.points += (e.record.quantity - e.value)
										* e.record.unitRedeemPoint;
								if (summary.points > Number(liger.get(
										"currentPoints").getValue())) {
									$.ligerDialog
											.error($l('msg.error.order.sales_point_insufficient'));
									e.value = e.record.quantity;
								}
							}

						}
					},
					onChanged : function(e) {
						f_setLinePrice(e.record);
					}
				},
				validate : {
					required : true,
					digits : true,
					min : 1,
					max : 99999
				}
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pvsum'),
				name : 'PVSum',
				align : 'center',
				type : 'text',
				render : function(data) {
					if (!data || !data.itemId) {
						return;
					}
					if (!data.pv) {
						data.pv = 0;
						return;
					}
					if (!data.quantity) {
						data.quantity = 1;
					}
					data.PVSum = data.pv * data.quantity;
					if (!data.PVSum || data.PVSum == 0) {
						return "0"
					}
					return data.PVSum;
				}
			}, amount ];
	if (summary.enableTax && !summary.priceIncludeTax) {
		var tax = {
			display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.tax"),
			name : 'tax',
			align : 'center',
			type : 'float',
			render : function(data) {
				if (!data || !data.itemId) {
					return;
				}
				if(data.disableTaxFlag && data.disableTaxFlag == 'Y'){
					 
					data.tax = 0
				}else{
					if(marketCode == 'CA' && data.itemNumber == '15160502'){
						data.tax = data.unitOrigPrice * 0.07;
					}else{
					data.tax = data.unitOrigPrice * summary.taxRate;
					}
				}
				
				return Number(data.tax).toFixed(summary.precision);
			}
		};
		columns.splice(5, 0, tax);
	}
	if (!isAutoship) {
		var invOrg = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.invorg'),
			name : 'defaultInvOrgId',
			align : 'center',
			width : 150,
			type : 'text',
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
		};
		if (isSelf) {
			invOrg.editor = {
				type : 'select',
				valueField : "invOrgId",
				textField : "name",
				value : _invOrgId,
				text : _invOrgName,
				cancelable : false,
				staticOptions : function(e) {
					e.column.editor.data = invOrgs;
				},
				onSelected : function(value, text) {
					if (!linegrid.editor.editParm
							|| !linegrid.editor.editParm.record) {
						return;
					}
					var editorData = linegrid.editor.editParm.record;
					console.log(JSON2.stringify(editorData));
					linegrid.updateRow(editorData, {
						"itemId" : null,
						"itemNumber" : null,
						"barCode" : null,
						"itemName" : null,
						"description" : null,
						"starterAid" : null,
						"starterAidField" : null,
						"countStockFlag" : null,
						"quantityCountType" : null,
						"minOrderQuantity" : null,
						"itemType" : null,
						"uomCode" : null,
						"publishStatus" : null,
						"publishStatusDesc" : null,
						"lotCtrlFlag" : null,
						"categoryId" : null,
						"invCategory" : null,
						"categoryDesc" : null,
						"categoryIdList" : null,
						"inStore" : null,
						"fax" : null,
						"agencyWeb" : null,
						"agencyApp" : null,
						"autoDeliver" : null,
						"channel" : null,
						"isActive" : null,
						"salesOrgId" : null,
						"currency" : null,
						"priceType" : null,
						"organizationId" : null,
						"priceListDetails" : null,
						"mode" : null,
						"uomName" : null,
						"quantity" : null,
						"organization" : null,
						"countItemName" : null,
						"countItemNumber" : null,
						"packageQuantity" : null,
						"savePropertyFlag" : null,
						"unitDiscountPrice" : null,
						"priceDetail" : null,
						"pv" : null,
						"unitOrigPrice" : null,
						"unitRedeemPoint" : 0,
						"favorableSum" : 0,
						"redeemPoint" : 0,
						"PVSum" : 0,
						"tax" : 0,
						"unitSellingPrice" : 0,
						"amount" : 0
					});
					f_calculateLinePrice();
				}
			};
			invOrg.validate = {
				required : true
			};
		}
		columns.unshift(invOrg);
	}
	return columns;
}

/**
 * 订单确认页面的订单行列获取
 * 
 * @param isPoint
 *            是否积分购买
 * @return 订单行列
 */
function o_getConfirmLineColumns(isPoint, isApplyLine) {
	defaultItemSalesType = "PURC";
	var unit = {
		display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.unitorigprice"),
		name : 'unitOrigPrice',
		align : 'center',
		type : 'float',
		render : function(data) {
			if (!data || !data.itemId) {
				return;
			}
			if (!data.unitOrigPrice) {
				data.unitOrigPrice = 0;
				return summary.zero;
			}
			if (data.itemSalesType && data.itemSalesType == "FREE") {
				data.unitOrigPrice = 0;
				return summary.zero;
			}
			return Number(data.unitOrigPrice).toFixed(summary.precision);
		}
	};
	var amount = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.amount'),
		name : 'amount',
		align : 'center',
		type : 'float',
		render : function(data) {
			if (!data || !data.itemId) {
				return;
			}
			if (!data.quantity) {
				data.quantity = 1;
			}
			if (!data.unitSellingPrice) {
				data.unitSellingPrice = 0;
			}
			if (data.itemSalesType && data.itemSalesType == "FREE") {
				data.unitSellingPrice = 0;
			}
			return Number(data.unitSellingPrice * data.quantity).toFixed(
					summary.precision);
		}
	};
	var pvSum ={
		display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pvsum'),
		name : 'PVSum',
		align : 'center',
		type : 'text',
		render : function(data) {
			if (!data || !data.itemId) {
				return;
			}
			if (!data.quantity) {
				data.quantity = 1;
			}
			data.PVSum = data.pv * data.quantity;
			if (!data.PVSum || data.PVSum == 0) {
				return "0"
			}
			return data.PVSum;
		}
	}
	var sellPrice = {
		display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.unitsellingprice"),
		name : 'unitSellingPrice',
		align : 'center',
		type : 'float',
		render : function(data) {
			if (!data || !data.itemId) {
				return;
			}
			if (!data.unitSellingPrice) {
				data.unitSellingPrice = 0;
				return summary.zero;
			}
			if (data.itemSalesType && data.itemSalesType == "FREE") {
				data.unitSellingPrice = 0;
				return summary.zero;
			}
			return Number(data.unitSellingPrice).toFixed(summary.precision);
		}
	}
	var saleType = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemsalestype'),
		name : 'itemSalesType',
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
		},
		validate : {
			required : true
		}
	};
	
	if(isApplyLine){
		pvSum = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pvsum'),
			name : 'PVSum',
			align : 'center',
			type : 'text',
			render : function(data) {
				if (!data || !data.itemId) {
					return;
				}
				if (data.totalPv == 0 || data.totalPv == null) {
					return "0";
				}
				return data.totalPv;
			}
		}
	};
	
	if (isPoint) {
		unit = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.unitredeempoint'),
			name : 'unitRedeemPoint',
			align : 'center',
			type : 'text'
		};
		amount = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.redeempoint'),
			name : 'redeemPoint',
			align : 'center',
			type : 'text',
			render : function(data) {
				if (!data || !data.itemId) {
					return;
				}
				if (!data.quantity) {
					data.quantity = 1;
				}
				if (!data.unitRedeemPoint) {
					data.unitRedeemPoint = 0;
				}
				return Number(data.unitRedeemPoint * data.quantity).toFixed(
						summary.precision);
			}
		};
	}
	var columns = [
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.invorg'),
				name : 'defaultInvOrgId',
				align : 'center',
				width : 120,
				type : 'text',
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
			saleType,
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemid'),
				name : 'itemNumber',
				align : 'center',
				width : 100,
				textField : 'itemNumber',
				validate : {
					required : true
				}
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemname'),
				name : 'itemName',
				width : 260,
				align : 'center',
				type : 'text'
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pv'),
				name : 'pv',
				align : 'center',
				type : 'text',
				render : function(data) {
					if (!data || !data.itemId) {
						return;
					}
					if (data.pv == null || data.pv == 0) {
						return "0"
					}else if(data.discountPv == null || data.discountPv == 0){
						return data.pv;
					}
					return data.pv.add(data.discountPv.div(data.quantity));
				}
			},
			unit,
			// lkkhpg_coupon add start.
			// lkkhpg_coupon end
		    // BEGIN lkkhpg_coupon xin.zhang 2017-08-31 删除优惠方式
            // END lkkhpg_coupon xin.zhang 2017-08-31 删除优惠方式
			sellPrice,
			{
				display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.quantity"),
				name : 'quantity',
				align : 'center',
				type : 'int',
				validate : {
					required : true,
					digits : true,
					min : 1,
					max : 99999
				}
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.discountprice'),
				name : 'discountAmt',
				align : 'center',
				type : 'float',
				render : function(data) {
					if (!data || !data.itemId) {
						return;
					}
					if (!data.discountAmt || data.discountAmt == 0) {
						return "0";
					}
					return data.discountAmt;
				}
			},
			pvSum, 
			amount ];
	if (summary.enableTax && !summary.priceIncludeTax) {
		var tax = {
			display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.tax"),
			name : 'tax',
			align : 'center',
			type : 'float',
			render : function(data) {
				if (!data || !data.itemId) {
					return;
				}
				return Number(data.taxAmount).toFixed(summary.precision);
			}
		};
		columns.splice(6, 0, tax);

		// BEGIN lkkhpg_coupon 2017-08-31 xin.zhang
        var discountAfterPv = {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.discountafterpv'),
            name : 'discountAfterPv',
            align : 'center',
            type : 'text',
            render : function(data) {
                if (!data || !data.itemId) {
                    return;
                }
                // 当没有使用优惠券的时候 (即优惠额为0) 就显示空
                if (!data.discountAmt) {
                    return;
                }
                return data.discountAfterPv;
            }
        };
        var discountAfterPrice = {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.discountafterprice'),
            name : 'discountAfterPrice',
            align : 'center',
            type : 'text',
            render : function(data) {
                if (!data || !data.itemId) {
                    return;
                }
                // 当没有使用优惠券的时候 (即优惠额为0) 就显示空
                if (!data.discountAmt) {
                    return;
                }
                return (data.discountAfterPrice).toFixed(summary.precision);
            }
        };
        var discountAfterTax = {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.discountaftertax'),
            name : 'discountAfterTax',
            align : 'center',
            type : 'text',
            render : function(data) {
                if (!data || !data.itemId) {
                    return;
                }
                // 当没有使用优惠券的时候 (即优惠额为0) 就显示空
                if (!data.discountAmt) {
                    return;
                }
                return (data.discountAfterTax).toFixed(summary.precision);
            }
        };
        columns.splice(7, 0, discountAfterPv);
        columns.splice(8, 0, discountAfterPrice);
        columns.splice(9, 0, discountAfterTax);
	} else {
        var discountAfterPv = {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.discountafterpv'),
            name : 'discountAfterPv',
            align : 'center',
            type : 'text',
            render : function(data) {
                if (!data || !data.itemId) {
                    return;
                }
                // 当没有使用优惠券的时候 (即优惠额为0) 就显示空
                if (!data.discountAmt) {
                    return;
                }
                return data.discountAfterPv;
            }
        };
        // 这个优惠后价只做显示使用(等于销售价)
        var discountAfterPrice = {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.discountafterprice'),
            name : 'discountAfterPrice',
            align : 'center',
            type : 'text',
            render : function(data) {
                if (!data || !data.itemId) {
                    return;
                }
                // 当没有使用优惠券的时候 (即优惠额为0) 就显示空
                if (!data.discountAmt) {
                    return;
                }
                if (!data.unitSellingPrice) {
                    data.unitSellingPrice = 0;
                }
                if (data.itemSalesType && data.itemSalesType == "FREE") {
                    data.unitSellingPrice = 0;
                }
                return Number(data.unitSellingPrice).toFixed(summary.precision);
            }
        };
        columns.splice(6, 0, discountAfterPv);
        columns.splice(7, 0, discountAfterPrice);
	}
	// END lkkhpg_coupon 2017-08-31 xin.zhang
	return columns;
}

/**
 * 获取订单地址集合
 */
function getOrderSites() {
	var sites = [];
	var deliveryData = deliveryaddress.getSelectedRow();
	var billData = addressgrid.getSelectedRow();
	if (liger.get("memberId").getValue()) {
		if (deliveryData && liger.get('deliveryType').getValue() == "SHIPP") {
			var diliverySite = {
				headerId : $("#headerId").val(),
				salesOrgId : liger.get("salesOrgId").getValue(),
				siteType : "SHIP",
				salesSiteId : $("#deliverySiteId").val(),
				name : deliveryData.name,
				phone : deliveryData.phone,
				/*cityCode : deliveryData.spmLocation.cityCode,
				stateCode : deliveryData.spmLocation.stateCode,
				countryCode : deliveryData.spmLocation.countryCode,*/
				address : deliveryData.address,
				areaCode : deliveryData.areaCode,
				zipCode : deliveryData.spmLocation.zipCode/*,
				address1 : deliveryData.spmLocation.addressLine1,
				address2 : deliveryData.spmLocation.addressLine2,
				address3 : deliveryData.spmLocation.addressLine3*/
			};
			if (deliveryData.cityCode) {
				diliverySite.cityCode = deliveryData.cityCode;
				diliverySite.stateCode = deliveryData.stateCode;
				diliverySite.countryCode = deliveryData.countryCode;
				diliverySite.address1 = deliveryData.address1;
				diliverySite.address2 = deliveryData.address2;
				diliverySite.address3 = deliveryData.address3;
			} else {
				diliverySite.cityCode = deliveryData.spmLocation.cityCode;
				diliverySite.stateCode = deliveryData.spmLocation.stateCode;
				diliverySite.countryCode = deliveryData.spmLocation.countryCode;
				diliverySite.address1 = deliveryData.spmLocation.addressLine1;
				diliverySite.address2 = deliveryData.spmLocation.addressLine2;
				diliverySite.address3 = deliveryData.spmLocation.addressLine3;
			}
			sites.push(diliverySite);
		}

		if (billData) {
			var billSite = {
				headerId : $("#headerId").val(),
				salesOrgId : liger.get("salesOrgId").getValue(),
				siteType : "BILL",
				salesSiteId : $("#billSiteId").val(),
				name : billData.name,
				phone : billData.phone,
				/*cityCode : billData.spmLocation.cityCode,
				stateCode : billData.spmLocation.stateCode,
				countryCode : billData.spmLocation.countryCode,*/
				address : billData.address,
				areaCode : billData.areaCode,
				zipCode : billData.spmLocation.zipCode/*,
				address1 : billData.spmLocation.addressLine1,
				address2 : billData.spmLocation.addressLine2,
				address3 : billData.spmLocation.addressLine3*/
			};
			if (billData.cityCode) {
				billSite.cityCode = billData.cityCode;
				billSite.stateCode = billData.stateCode;
				billSite.countryCode = billData.countryCode;
				billSite.address1 = billData.address1;
				billSite.address2 = billData.address2;
				billSite.address3 = billData.address3;
			} else {
				billSite.cityCode = billData.spmLocation.cityCode;
				billSite.stateCode = billData.spmLocation.stateCode;
				billSite.countryCode = billData.spmLocation.countryCode;
				billSite.address1 = billData.spmLocation.addressLine1;
				billSite.address2 = billData.spmLocation.addressLine2;
				billSite.address3 = billData.spmLocation.addressLine3;
			}
			sites.push(billSite);
		}
	} else {
		if (deliveryData) {
			sites.push(deliveryData);
		}
		if (billData) {
			sites.push(billData);
		}
	}

	return sites;
}

/**
 * 设置默认的订单地址.
 * 
 * @param sites
 *            订单地址集合
 */
function f_setDefaultAddress(sites) {
	if (!sites) {
		return;
	}
	for ( var i in sites) {
		if (sites[i].siteType == "BILL") {
			$("#billSiteId").val(sites[i].salesSiteId);
			var datas = addressgrid.getData();
			var isSelect = false;
			for ( var j in datas) {
				if (datas[j].address == sites[i].address
						&& datas[j].phone == sites[i].phone
						&& datas[j].name == sites[i].name) {
					isSelect = true;
					addressgrid.select(datas[j]);
				}
			}
			if (!isSelect) {
				sites[i].spmLocation = {};
				sites[i].spmLocation.zipCode = sites[i].zipCode;
				// sites[i].defaultFlag="Y";
				addressgrid.addRow(sites[i]);
				addressgrid.select(sites[i]);
			}
		} else {
			$("#deliverySiteId").val(sites[i].salesSiteId);
			liger.get("deliveryType").setValue("SHIPP");
			var datas = deliveryaddress.getData();
			var isSelect = false;
			for ( var j in datas) {
				if (datas[j].address == sites[i].address
						&& datas[j].phone == sites[i].phone
						&& datas[j].name == sites[i].name) {
					isSelect = true;
					deliveryaddress.select(datas[j]);
				}
			}
			if (!isSelect) {
				sites[i].spmLocation = {};
				sites[i].spmLocation.zipCode = sites[i].zipCode;
				deliveryaddress.addRow(sites[i]);
				// var datas=deliveryaddress.getData();
				// datas.push(sites[i]);

			}
		}
	}
}

/**
 * 支付信息
 */

function o_paymentOptions(headId) {
	var options = {
		columns : [
				{
					display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.paymentmethod"),
					name : 'paymentMethod',
					type : 'select',
					render : function(data) {
						for ( var paymethod in paydetail) {
							if (data.paymentMethod == paydetail[paymethod].value) {
								return paydetail[paymethod].meaning;
							}
						}
					}
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.inv.dto.transaction.totalprice"),
					name : 'paymentAmount'
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remark"),
					name : 'remark'
				} ],
		showTitle : false,
		width : '99%',
		url : _basePath + "/om/order/queryPaymentRefund?headerId=" + headId,
		detail : {
			onShowDetail : f_showPayment,
			height : 'auto'
		}
	};
	return options;
}
/**
 * 
 * @param row
 *            行数据
 * @param detailPanel
 *            详情面板
 * @param callback
 *            huidiao
 */
function f_showPayment(row, detailPanel, callback) {
	var paymentMothod = row.paymentMethod;
	var fields;
	if ("CHEQU" == paymentMothod || "REMIT" == paymentMothod) {
		fields = [
				{
					display : $l("type.com.lkkhpg.dsis.common.om.orderinvalid.bank"),
					readonly : true,
					name : 'bankCode',
					type : 'select',
					options : {
						valueField : "value",
						textField : "meaning",
						data : bankBelongData
					}

				},
				{
					display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.transactionnumber"),
					readonly : true,
					name : 'transactionNumber'
				} ]
	} else if ("CREDI" == paymentMothod) {
		fields = [
				{
					display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.creditCardType"),
					readonly : true,
					name : 'creditCardType',
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.om.orderinvalid.tailnumber"),
					readonly : true,
					name : 'tailNumber'
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.om.orderinvalid.pos"),
					readonly : true,
					name : 'paymentMethodInfo',
					type : 'select',
					options : {
						valueField : "value",
						textField : "meaning",
						data : posSelectData
					}
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.transactionnumber"),
					readonly : true,
					name : 'transactionNumber'
				} ]
	} else if ("DBCRD" == paymentMothod) {
		fields = [
				{
					display : $l("type.com.lkkhpg.dsis.common.om.orderinvalid.bank"),
					readonly : true,
					name : 'bankCode',
					type : 'select',
					options : {
						valueField : "value",
						textField : "meaning",
						data : bankBelongData
					}
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.om.orderinvalid.tailnumber"),
					readonly : true,
					name : 'tailNumber'
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.om.orderinvalid.pos"),
					readonly : true,
					name : 'paymentMethodInfo',
					type : 'select',
					options : {
						valueField : "value",
						textField : "meaning",
						data : posSelectData
					}
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.transactionnumber"),
					readonly : true,
					name : 'transactionNumber'
				} ]
	}
	var grid = document.createElement('div');
	$(detailPanel).append(grid);
	$(grid).css('margin', 10).ligerForm({
		width : '90%',
		data : row,
		fields : fields
	});
}

function confirmReceive() {
	var requestData = om_oc_form.getData();
	var param = {
		headerId : requestData.headerId,
		remark : requestData.remark,
		orderStatus : "COMP"
	};
	$.ajax({
		type : 'POST',
		url : _basePath + "/om/order/update",
		data : JSON2.stringify(param),
		success : function(json) {
			location.reload();
		},
		contentType : "application/json; charset=utf-8"
	});

}

/**
 * 销售订单表单参数
 */
function o_omDetailFormOptions() {
	var options = {
		fields : [
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.createuserid"),
					name : 'createUserId',
					textField : 'createUserName',
					newline : false,
					readonly : true,
					group : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.orderinfo")
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salesmarket"),
					name : 'marketId',
					cancelable : false,
					newline : false,
					readonly : true
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salesorgid"),
					name : 'salesOrgId',
					newline : false,
					cancelable : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.currency"),
					name : 'currency',
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.ordernumber"),
					name : 'orderNumber',
					newline : true,
					readonly : true
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.orderstatus"),
					name : 'orderStatus',
					newline : false,
					readonly : true
				},
				{
					type : 'date',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.orderdate"),
					newline : false,
					readonly : true,
					name : 'orderDate'
				},
				{
					type : 'date',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.paydate"),
					newline : false,
					readonly : true,
					name : 'payDate'
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.ordertype"),
					name : 'orderType',
					newline : true,
					readonly : true,
					options : {
						valueFieldID : "orderType",
						valueField : "value",
						textField : "meaning",
						data : orderTypeData,
						cancelable : false,
						// 当“订单类型=总监购买/员工购买/非会员购买”时，不可用灰掉。

						onselected : function(data) {
							cleanMember();
							reSetLine(false, true, false);
							var orderType = data;
							var isself = true;
							if (liger.get("deliveryType")
									&& liger.get("deliveryType").getValue() == 'SHIPP') {
								isself = false;
							}
							if (orderType) {
								switch (orderType) {
								case "DIRP":
								case "STFP":
								case "CONP":
								case "CONPT":{
									if (linegrid) {
										linegrid.set({
											columns : o_getShowLineColumns(
													true, false, isself)
										});
									}
									liger.get("memberId").set({
										readonly : true
									});
									liger.get('memberId').setPlaceholder("   ");
									f_changeMemberShow(false);
									f_changeStaffShow(true);
									
									var str = "<label class='label1'>"
											+ $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.orderamt')
											+ ":</label><label id='TotalCurrency' class='label2'>0</label>"
									$("#salesLineSummary tr:eq(0) td:eq(2)")
											.html(str);
									break;
								}
								case "RDEM": {
									if (linegrid) {
										linegrid.set({
											columns : o_getShowLineColumns(
													true, true, isself)
										});
									}
									f_changeMemberShow(true);
									f_changeStaffShow(false);
									liger.get("memberId").setEnabled();
									liger.get('memberId').set({
										readonly : false
									});
									liger.get('memberId').set({
										validate : {
											required : true
										}
									});
									liger
											.get('memberId')
											.setPlaceholder(
													$l("msg.warn.order.om_sales_order.fullmembercode"));
									var str = "<label class='label1'>"
											+ $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salespointsummary")
											+ ":</label><label id='TotalCurrency' class='label2'>0</label>"
									$("#salesLineSummary tr:eq(0) td:eq(2)")
											.html(str);
									break;
								}
								default:
									if (linegrid) {
										linegrid.set({
											columns : o_getShowLineColumns(
													true, false, isself)
										});
									}
									liger.get("memberId").setEnabled();
									liger.get('memberId').set({
										readonly : false
									});
									liger.get('memberId').set({
										validate : {
											required : true
										}
									});
									liger
											.get('memberId')
											.setPlaceholder(
													$l("msg.warn.order.om_sales_order.fullmembercode"));
									f_changeMemberShow(true);
									f_changeStaffShow(false);
									var str = "<label class='label1'>"
											+ $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.orderamt')
											+ ":</label><label id='TotalCurrency' class='label2'>0</label>"
									$("#salesLineSummary tr:eq(0) td:eq(2)")
											.html(str);
									break;
								}
							}
						}
					}
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.channel"),
					name : 'channel',
					newline : false,
					readonly : true,
					options : {
						valueFieldID : "channel",
						valueField : "value",
						textField : "meaning",
						cancelable : false,
						data : channelData,
						onSelected : function(value, text, data) {
							if (value == 'SRVC') {
								liger.get('serviceCenterId').set({
									readonly : false
								});
								liger.get('serviceCenterId').set({
									validate : {
										required : true
									}
								});
								// 会员ID置为只读
								/*liger.get('memberId').set({
									readonly : true
								});
								liger.get('memberId').setPlaceholder("   ");*/
								liger.get('serviceCenterId').setPlaceholder($l("msg.warn.order.om_sales_order.fullservicecentercode"));
							} else {
								liger.get('serviceCenterId').set({readonly : true});
								liger.get('serviceCenterId').setPlaceholder("   ");
								/*liger.get('memberId').set({readonly : false});
								liger.get('memberId').setPlaceholder($l("msg.warn.order.om_sales_order.fullmembercode"));*/
							}
							reSetLine(false, true, false);
						},
						render : function(data) {
							for ( var i in channelData) {
								if (channelData[i].value == data) {
									return channelData[i].meaning;
								}
							}
						}
					},
					render : function(data) {
						for ( var i in channelData) {
							if (channelData[i].value == data) {
								return channelData[i].meaning;
							}
						}
					}
				},
				{
					type : 'select',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.period"),
					name : 'period',
					newline : false,
					cancelable : false,
					options : {
						valueField : "value",
						textField : "meaning",
						focusWhenSetValue :false,
						onBeforeSelect : function(newvalue) {
							return confirm($l('msg.info.order.period')
									+ newvalue);
						}
					}
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.invoicenumber"),
					name : 'invoiceNumber',
					readonly : true,
					newline : false
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.user.dto.userinfo.memberid"),
					name : 'memberId',
					type : "popup",
					textField : "memberCode",
					readonly : true,
					newline : true,
					cancelable : false,
					group : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.memberinfo")
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.chinesename"),
					name : 'cnName',
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.accountingbalance.exchangebalance"),
					name : 'exchangeBalance',
					width : 150,
					labelWidth : 120,
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.salesscore"),
					name : 'currentPoints',
					newline : false,
					readonly : true
				},
				{
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.servicecenter"),
					name : 'serviceCenterId',
					newline : true,
					cancelable : false,
					type : 'popup',
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.englishname"),
					name : 'enName',
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.member.dto.accountingbalance.remainingbalance"),
					name : 'remainingBalance',
					width : 150,
					labelWidth : 120,
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.shortcut_operation.currentmoneypv"),
					name : 'currentPV',
					newline : false,
					readonly : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.sourcekey"),
					name : 'sourceKey',
					readonly : true,
					newline : true
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.batchnumber"),
					name : 'batchNumber',
					readonly : true,
					newline : false
				},
				{
					type : 'text',
					display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remark"),
					name : 'remark',
					newline : false,
					readonly : true,
					disabled : true,
					options : {
						readonly : true
					},
					width : 400,
					group : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remarkinfo")
				}, {
					type : 'hidden',
					name : 'headerId'
				}, {
					type : 'hidden',
					name : 'logisticsId'
				}, {
					type : 'hidden',
					name : 'periodId'
				}, {
					type : 'hidden',
					name : 'billSiteId'
				}, {
					type : 'hidden',
					name : 'deliverySiteId'
				} ]
	};
	return options;
}

/**
 * 订单列信息获取
 * 
 * @param istax
 *            是否含税
 * @param ispoint
 *            是否积分购买
 * @param isself
 *            是否自提
 * @param isauto
 *            是否autoship订单
 * @returns 订单列数组对象
 */
function o_getShowLineColumns(istax, ispoint, isself, isauto) {
	defaultItemSalesType = "PURC";
	var unit = {
		display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.unitsellingprice"),
		name : 'unitOrigPrice',
		align : 'center',
		type : 'float',
		render : function(data) {
			if (!data) {
				return;
			}
			if (!data.unitOrigPrice) {
				data.unitOrigPrice = 0;
				return summary.zero;
			}
			data.tax = data.unitOrigPrice * summary.taxRate;
			return Number(data.unitOrigPrice).toFixed(summary.precision);
		}
	};
	var amount = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.amount'),
		name : 'amount',
		align : 'center',
		type : 'float',
		render : function(data) {
			if (!data) {
				return;
			}
			if (!data.quantity) {
				data.quantity = 1;
			}
			if (!data.unitOrigPrice) {
				data.unitOrigPrice = 0;
			}
			return Number(data.unitOrigPrice * data.quantity).toFixed(
					summary.precision);
		}
	};
	var saleType = {
		display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemsalestype'),
		name : 'itemSalesType',
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
	if (ispoint) {
		unit = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.unitredeempoint'),
			name : 'unitRedeemPoint',
			align : 'center',
			type : 'text'
		};
		amount = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.redeempoint'),
			name : 'redeemPoint',
			align : 'center',
			type : 'text',
			render : function(data) {
				if (!data) {
					return;
				}
				if (!data.quantity) {
					data.quantity = 1;
				}
				if (!data.unitRedeemPoint) {
					data.unitRedeemPoint = 0;
				}
				return Number(data.unitRedeemPoint * data.quantity).toFixed(
						summary.precision);
			}
		};
	}
	var columns = [
			{
				display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.deliverystatus"),
				name : 'status',
				width : 100,
				type : 'select',
				render : function(data) {
					for ( var linestatus in linesdeliverystatus) {
						if (data.status == linesdeliverystatus[linestatus].value) {
							return linesdeliverystatus[linestatus].meaning;
						}
					}
				}
			},
			saleType,
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemid'),
				name : 'itemId',
				align : 'center',
				width : 200,
				textField : 'itemNumber'
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemname'),
				name : 'itemName',
				align : 'center',
				type : 'text'
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pv'),
				name : 'pv',
				align : 'center',
				type : 'text',
				render : function(data) {
					if (!data) {
						return;
					}
					if (!data.pv) {
						data.pv = 0;
						return;
					}
					if (data.pv == 0) {
						return "0"
					}
					return data.pv;
				}
			},
			unit,
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.unitdiscountprice'),
				name : 'unitDiscountPrice',
				align : 'center',
				type : 'float',
				render : function(data) {
					if (!data) {
						return;
					}
					// TODO
				}
			},
			{
				display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.quantity"),
				name : 'quantity',
				align : 'center',
				type : 'float',
				editor : {
					type : 'float',
					onChanged : function(e) {
						if (e.value > 99999) {
							e.record.quantity = 99999;
						} else if (e.value < 1) {
							e.record.quantity = 1;
						}
						f_setLinePrice(e.record);
					},
					minValue : 1,
					maxValue : 99999
				}
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.discountprice'),
				name : 'favorableSum',
				align : 'center',
				type : 'float',
				render : function(data) {
					if (!data) {
						return;
					}
					if (!data.favorableSum || data.favorableSum == 0) {
						return summary.zero;
					}
				}
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pvsum'),
				name : 'PVSum',
				align : 'center',
				type : 'text',
				render : function(data) {
					if (!data) {
						return;
					}
					if (!data.pv) {
						data.pv = 0;
						return;
					}
					if (!data.quantity) {
						data.quantity = 1;
					}
					data.PVSum = data.pv * data.quantity;
					if (!data.PVSum || data.PVSum == 0) {
						return "0"
					}
					return data.PVSum;
				}
			}, amount ];
	if (istax) {
		var tax = {
			display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.tax"),
			name : 'tax',
			align : 'center',
			type : 'float',
			render : function(data) {
				if (!data) {
					return;
				}
				if(data.disableTaxFlag&&data.disableTaxFlag == 'Y'){
					if(marketCode == 'CA' && data.itemNumber == '15160502'){
						data.tax = data.unitOrigPrice * 0.07;
					}else{
						data.tax = data.unitOrigPrice * summary.taxRate;
					}
				    
				}else{
					data.tax = 0;
				}
				return Number(data.tax).toFixed(summary.precision);
			}
		};
		columns.splice(5, 0, tax);
	}
	if (!isauto) {
		var invOrg = {
			display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.invorg'),
			name : 'defaultInvOrgId',
			align : 'center',
			width : 200,
			type : 'text',
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
		};

		columns.unshift(invOrg);
	}
	var deliveryStatus = {
		display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.deliverystatus"),
		name : 'status',
		width : 100,
		type : 'select',
		render : function(data) {
			for ( var linestatus in linesdeliverystatus) {
				if (data.status == linesdeliverystatus[linestatus].value) {
					return linesdeliverystatus[linestatus].meaning;
				}
			}
		}
	};

	if (liger.get("orderStatus").getValue() == "COMP") {
		columns.unshift(deliveryStatus);
	}
	return columns;
}

/**
 * 订单行表格的参数
 * 
 * @param isauto
 *            是否autoship订单
 */
function o_lineShowGridOptions(isauto) {
	var options = {
		detail : {
			onShowDetail : f_getItemPackageDetail,
			height : 'auto'
		},
		columns : o_getShowLineColumns(true, false, true, isauto),
		usePager : false,
		width : '98%'
	};
	return options;
}

/**
 * 地址表格的参数
 */
function o_ShowaddressGridOptions(dataOption) {
	var displayName = $l('type.com.lkkhpg.dsis.common.member.dto.membersite.receivebillsname');
	if (dataOption) {
		displayName = $l('type.com.lkkhpg.dsis.common.member.dto.membersite.receivegoodsname');
	}
	var options = {
		usePager : false,
		checkbox : false,
		enabledEdit : false,
		width : '98%',
		columns : [
				{
					display : displayName,
					name : 'name',
					type : 'text',
					align : 'center'
				},
				{
					display : $l('type.com.lkkhpg.dsis.common.member.dto.membersite.phone'),
					name : 'phone',
					align : 'center'
				},
				{
					display : $l('type.com.lkkhpg.dsis.common.member.dto.membersite.address'),
					name : 'address',
					align : 'center',
					width : 400,
					type : 'text'
				},
				{
					display : $l('type.com.lkkhpg.dsis.common.member.dto.membersite.spmlocation.zipcode'),
					name : 'spmLocation.zipCode',
					align : 'center',
					type : 'text'
				},
				{
					display : $l('type.com.lkkhpg.dsis.common.member.dto.membersite.defaultflag'),
					name : 'defaultFlag',
					align : 'center',
					type : 'text'
				} ]
	}

	return options;
}

function showShippingTier() {
	var selectValue = liger.get("deliveryCompany").getValue();
	if (!selectValue) {
		return;
	}
	var selectText = liger.get("deliveryCompany").getText();
	var datas = liger.get("deliveryCompany").options.data
	var selectData ;
	var gridData = {};
	for ( var i in datas) {
		if (datas[i].shippingTierId == selectValue) {
			gridData.rows = datas[i].shippingTierDtls;
			selectData = datas[i];
			break;
		}
	}

	var columns ;
	var dataurl = _basePath+'/dm/shippingTier/showShippingTierFee?shippingTireId='+selectValue+'&feeCalcluateType='+'AMT' ;
	if(selectData.feeCalcluateType == 'WEG'){
		dataurl = _basePath+'/dm/shippingTier/showShippingTierFee?shippingTireId='+selectValue+'&feeCalcluateType='+'WEG'
		columns = 	[
			{
				display : $l('type.com.lkkhpg.dsis.common.delivery.dto.shippingtier.weightfrom'),
				name : 'weightFrom',
				align : 'center',
				type : 'text'
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.delivery.dto.shippingtier.weightto'),
				name : 'weightTo',
				align : 'center',
				type : 'text'
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.delivery.dto.shippingtierdtl.shippingfee'),
				name : 'fee',
				align : 'center',
				type : 'text'
			} ]
	}else{
	 columns = 	[
			{
				display : $l('type.com.lkkhpg.dsis.common.delivery.dto.shippingtierdtl.invamountfrom'),
				name : 'invAmountFrom',
				align : 'center',
				type : 'text'
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.delivery.dto.shippingtierdtl.invamountto'),
				name : 'invAmountTo',
				align : 'center',
				type : 'text'
			},
			{
				display : $l('type.com.lkkhpg.dsis.common.delivery.dto.shippingtierdtl.shippingfee'),
				name : 'shippingFee',
				align : 'center',
				type : 'text'
			} ]
	}
	$.ligerDialog.open({
				target : $("#showShippingTier"),
				title : $l('type.com.lkkhpg.dsis.common.delivery.dto.shipping.shippingtier'),
				width : 530
			});
	$("#showShippingTierGrid")
			.ligerGrid(
					{
						width : 500,
						title : selectText,
						usePager : false,
						columns :columns ,
						url :dataurl
					});
	$("#showShippingTierGrid .l-panel-header-text").text(selectText);
}


function initUpdatePeriod(){
    var today=new Date().getDate();
    if($.inArray(today+"",bonusDate )<0){
        $("#periodBtn").hide();
        return;
    }
    var periods;
    $.getJSON(_basePath+'/om/check_order_period?orderId='+$("#headerId").val(),function(data) {
        if(data.success){
            periods=data.rows;
        }
    }) 
    if(!periods||periods.length<2){
        $("#periodBtn").hide();
        return;
    }
    $('#updatePeriodFrom').ligerForm({
        fields : [
                  {
                     type : 'date',
                     display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.paydate"),
                     name : 'updatePayDate',
                     format:'yyyy-MM-dd',
                     width:100,
                     options:{
                         cancelable : false,
                         onChangeDate:function(value){
                             selectPayDate(this);
                         }
                     },
                     validate : {
                        required : true
                     },
                     newline : false
                 },{
                     type : 'select',
                     display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.period"),
                     name : 'updatePeriod',
                     newline : false,
                     width:100,
                     options : {
                         valueFieldID : "updatePeriod",
                         valueField : "periodId",
                         textField : "periodName",
                         cancelable: false,
                         autocomplete : false,
                         data:periods
                     },
                     validate : {
                        required : true
                     }
                 },{
                     type : 'hidden',
                     name : 'updateHeaderId'
                 }],
                 space:10
     }); 
    $("#periodBtn").ligerButton({
        click : function(){
            liger.get("updatePayDate").setValue(new Date());
            liger.get("updatePeriod").setValue($("#periodId").val());
            liger.get("updatePeriod").setText(liger.get("period").getText());
            $("#updateHeaderId").val($("#headerId").val());
            $.ligerDialog.open({ 
                target: $("#updatePeriod") ,
                title:$l('type.com.lkkhpg.dsis.common.order.dto.salesorder.period'),
                width:500,
                buttons: [{
                    text: $l('sys.hand.btn.ok'),
                    onclick: function(i,d){
                    	if(!Hap.validateForm($('#updatePeriodFrom'))){
                            return;
                    	}
                    	$.ligerDialog.confirm($l("msg.warning.member.confirm_tip"), function (yes){
							if(yes){
								//TODO: submit form data
		                        var requestData={};
		                        requestData.headerId=$("#updateHeaderId").val();
		                        requestData.periodId=liger.get("updatePeriod").getValue();
		                        requestData.payDate=liger.get("updatePayDate").getValue();
		                        $.ajax({
		                            url : _basePath+'/om/update_period_paydate',
		                            type : "POST",
		                            dataType : "json",
		                            data : requestData,
		                            success : function(json) {
		                                    if(json.success){
		                                        liger.get("payDate").setValue(requestData.payDate);
		                                        liger.get("period").setText(liger.get("updatePeriod").getText());
		                                        $("#periodId").val(requestData.periodId);
		                                        //Hap.showSuccess();
		                                        $.ligerDialog.success($l("sys.hand.tip.success"), function() {
			        		            			//location.reload();  
			        							});
		                                        d.hide();
		                                    }
		                                }
		                            });
							}
						});
                    }
                },
                {
                    text: $l('sys.hand.btn.cancel'),
                    onclick: function(i, d) {
                        d.hide();
                    }
                }]
                });
        },
        text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.period')
    });
}

/**
 * 支付日期选择
 * @param payDate
 */
function selectPayDate(payDate){
    
    var periods=liger.get("updatePeriod").data;
    var endTime=new Date().getTime();
    if(payDate.getValue().getTime()>endTime || payDate.getValue().getTime()< getLastMonthFirstDay()){
    	Hap.showError($l('msg.error.payment_date_can_be_modified_to_last_months_one_to_the_current_date'));
        if(liger.get("payDate").getValue()){
             liger.get("updatePayDate").setValue(new Date(liger.get("payDate").getValue()));
         }else{
             liger.get("updatePayDate").setValue(new Date());
         }
    }
    var currentDate=liger.get("updatePayDate").currentDate;
    var selectPeriodName=currentDate.year
    if(currentDate.month<10){
        selectPeriodName+="0"+currentDate.month;
    }else{
        selectPeriodName+=""+currentDate.month;
    }
    var isSelect=false;
    for(var i in periods){
        if(periods[i].periodName==selectPeriodName){
            isSelect=true;
            liger.get("updatePeriod").setValue(periods[i].periodId);
            liger.get("updatePeriod").setText(selectPeriodName);
            break;
        }
    }
    if(!isSelect){
        liger.get("updatePayDate").setValue(new Date(liger.get("payDate").getValue()));
    }
}


/**
 * 上个月的第一天
 * 
 * 
 */

function getLastMonthFirstDay(){
	 var nowdays = new Date();
	    var year = nowdays.getFullYear();
	    var month = nowdays.getMonth();
	    var myDate = new Date(year, month-1, 1);
	    return myDate;
}
//
function includeFreeShippingData(setflag){
	//查询当前商品行中
	var linegridData = linegrid.getData();
	//会员类型
	if(!(om_oc_form.getData().member)){
		return false;
	}
	var memberRole = om_oc_form.getData().member.memberRole;
	
	for(var i=0;i<linegridData.length; i++ ){
		var item = linegridData[i];
		for(var j =0 ; j < freeShippingData.length;j++ ){
			var freeitem = freeShippingData[j];
			if(freeitem.meaning.indexOf(memberRole)>-1 && item.itemNumber == freeitem.value ){
				if(setflag){
					liger.get("freeShipping").setValue(true);
				}
				//#PE20-257 2017-09-21 BEGIN
				//return true;
				//#PE20-257 2017-09-21 END
			}
		}
		//#PE20-257 2017-09-21 BEGIN
		for(var j =0 ; j < shipDiscountData.length;j++ ){
			var freeitem = shipDiscountData[j];
			if(freeitem.meaning.indexOf(memberRole)>-1 && item.itemNumber == freeitem.value ){
				if(freeitem.description=="" || freeitem.description==null || freeitem.description<0){
					freeitem.description =0;
				}
				 var reg=/^\d+$/;  //只能是数字
				if(!reg.test(freeitem.description)){
					freeitem.description =0;
				} 
				shippingDiscountArr.push(parseFloat(freeitem.description));
			}
		}
	}
	if(liger.get("freeShipping").getValue()==true){
		return true;
	}
	if(shippingDiscountArr.length!=0){
		shippingDiscount = shippingDiscountArr[0];

		for(var i=1;i<shippingDiscountArr.length;i++){ 
		  if(shippingDiscount<shippingDiscountArr[i])
			  shippingDiscount=shippingDiscountArr[i];
		}
	}
	//#PE20-257 2017-09-21 END
}