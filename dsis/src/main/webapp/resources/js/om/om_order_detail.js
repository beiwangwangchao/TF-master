/**
 * @summary DSIS
 * @description 订单新建页面js方法集合
 * @version 1.0
 * @author wuyichu
 * @copyright Copyright LKK Health Products Group Limited.
 * 
 */
 
function o_paymentShow(headId) {
	var options = 
    {
    columns : [ {
        display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.paymentmethod"),
        name : 'paymentMethod',
        type : 'select',
        render: function (data){
             for ( var paymethod in allPayMethod) {
                    if (data.paymentMethod == allPayMethod[paymethod].value) {
                        return allPayMethod[paymethod].meaning;
                    }
                }
        }
    }, {
        display : $l("type.com.lkkhpg.dsis.common.inv.dto.transaction.totalprice"),
        name : 'paymentAmount'
    }, {
        display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remark"),
        name : 'remark'
    } ],
    showTitle : false,
    width : '99%',
    url :_basePath + "/om/orderinfo/queryPayments?headerId=" + headId,
    detail: { onShowDetail: f_showPayment ,height:'auto'}
    };
	return options;
}

/**
 * 支付信息/退款信息
 */

function o_paymentOptions(headId) {
    var options = 
        {
        columns : [ {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.paymentmethod"),
            name : 'paymentMethod',
            type : 'select',
            render: function (data){
                 for ( var paymethod in allPayMethod) {
                        if (data.paymentMethod == allPayMethod[paymethod].value) {
                            return allPayMethod[paymethod].meaning;
                        }
                    }
            }
        }, {
            display : $l("type.com.lkkhpg.dsis.common.inv.dto.transaction.totalprice"),
            name : 'paymentAmount',
            //POS2SUPT-1162 20171120 BEGIN 精度修改
            render:function(data){
                return Number(data.paymentAmount).toFixed(summary.precision);
            }
        	//POS2SUPT-1162 20171120 END 精度修改
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remark"),
            name : 'remark'
        } ],
        showTitle : false,
        width : '99%',
        url :_basePath + "/om/order/queryPaymentRefund?headerId=" + headId,
         detail: { onShowDetail: f_showPayment ,height:'auto'}
    };
    return options;
}
/**
 * 
 * @param row 行数据
 * @param detailPanel 详情面板
 * @param callback huidiao
 */
function f_showPayment(row, detailPanel, callback) {
    var paymentMothod = row.paymentMethod;
    var fields;
    if ("CHEQU" == paymentMothod || "REMIT" == paymentMothod) {
        fields = [ {
            display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.bank"),
            readonly : true,
            name : 'bankCode',
            type : 'text',
            /*options : {
                    valueField : "value",
                    textField : "meaning",
                    data : bankBelongData
                }*/
                
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.transactionnumber"),
            readonly : true,
            name : 'transactionNumber'
        }]
    } else if ("CREDI" == paymentMothod) {
        fields = [{
            display :  $l("type.com.lkkhpg.dsis.common.order.orderinvalid.bank"),
            readonly : true,
            name : 'bankCode',
            type : 'text'
        },{
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.creditcardtype"),
            readonly : true,
            name : 'creditCardType',
            type : 'combobox',
            options : {
                valueField : 'value',
                textField : 'meaning',
                data : creditCardType,
            }
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.tailnumber"),
            readonly : true,
            name : 'tailNumber'
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.pos"),
            readonly : true,
            name : 'paymentMethodInfo',
            type : 'select',
            options : {
                    valueField : "value",
                    textField : "meaning",
                    data : posSelectData
                }
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.transactionnumber"),
            readonly : true,
            name : 'transactionNumber'
        }]
    } else if ("DBCRD" == paymentMothod) {
        fields = [ {
            display :  $l("type.com.lkkhpg.dsis.common.order.orderinvalid.bank"),
            readonly : true,
            name : 'bankCode',
            type : 'text'
        },{
        	display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.creditcardtype"),
            readonly : true,
            name : 'creditCardType',
            type : 'combobox',
            options : {
                valueField : 'value',
                textField : 'meaning',
                data : creditCardType,
            }
        },{
            display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.tailnumber"),
            readonly : true,
            name : 'tailNumber'
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.pos"),
            readonly : true,
            name : 'paymentMethodInfo',
            type : 'select',
            options : {
                    valueField : "value",
                    textField : "meaning",
                    data : posSelectData
                }
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.transactionnumber"),
            readonly : true,
            name : 'transactionNumber'
        }]
    } else if ("ECUP" == paymentMothod) {
    	fields = [ {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.ecupon.name"),
            readonly : true,
            name : 'name',
            type : 'text'
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.ecupon.discountvalue"),
            readonly : true,
            name : 'discountValue'
        }]
    } else if ("ONLPA" == paymentMothod) {
    	fields = [ {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.atttibute1"),
            readonly : true,
            name : 'attribute1',
            type : 'text'
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.transactionnumber"),
            readonly : true,
            name : 'transactionNumber',
            type : 'text'
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.tailnumber"),
            readonly : true,
            name : 'tailNumber',
            type : 'text'
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.paymentmethod"),
            readonly : true,
            name : 'attribute2',
            type : 'text'
        }, {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.atttibute3"),
            readonly : true,
            name : 'attribute3',
            type : 'text'
        }
        //#PE20-173 2017-05-03 BEGIN
        , {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remark"),
            readonly : true,
            name : 'remark',
            type : 'text'
        }
        //#PE20-173 2017-05-03 END
        ]

    }
    var grid = document.createElement('div');
    $(detailPanel).append(grid);
    $(grid).css('margin', 10).ligerForm({
            width : '90%',
            data : row,
            fields:fields
            });
}

function confirmReceive(){
    $.ligerDialog.confirm($l('msg.warn.isconfirmreceive'), function (yes){
        if (yes){
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
                    location.reload() ;
                },
                contentType : "application/json; charset=utf-8"
           });
        }
      });
}

/**
 * 销售订单表单参数
 */
function o_omDetailFormOptions() {
    var options = {
        fields : [
                {
                    type : 'select',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.createuserid"),
                    name : 'createUserId',
                    textField :'createUserName',
                    newline : false,
                    readonly : true,
                    group: $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.orderinfo")
                },
                {
                    type : 'select',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salesmarket"),
                    name : 'salesMarketId',
                    cancelable: false,
                    newline : false,
                    readonly : true
                },
                {
                    type : 'select',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salesorgid"),
                    name : 'salesOrgId',
                    newline : false,
                    cancelable: false,
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
                        cancelable: false,
                        // 当“订单类型=总监购买/员工购买/非会员购买”时，不可用灰掉。

                        onselected : function(data) {
                            cleanMember();
                            reSetLine(false,true,false);
                            var orderType = data;
                            var isself=true;
                            if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()=='SHIPP'){
                                isself=false;
                            }
                            if (orderType) {
                                switch (orderType) {
                                case "DIRP":
                                case "STFP":
                                case "CONP":
                                case "CONPT":
                                case "STFPT":{
                                    if(linegrid){
                                        linegrid.set({columns:o_getLineColumns(true,false,isself)});
                                    }
                                    liger.get("memberId").set({readonly:true});
                                    liger.get('memberId').setPlaceholder("   ");
                                    f_changeMemberShow(false);
									if(orderType == 'STFP' || orderType == 'STFPT'){
										f_changeStaffShow(true);
									}else{
										f_changeStaffShow(false);
									}
                                    var str="<label class='label1'>"+$l('type.com.lkkhpg.dsis.common.order.dto.salesorder.orderamt')+":</label><label id='TotalCurrency' class='label2'>0</label>"
                                    $("#salesLineSummary tr:eq(0) td:eq(2)").html(str);
                                    break;
                                }
                                case "RDEM":{
                                    if(linegrid){
                                        linegrid.set({columns:o_getLineColumns(true,true,isself)});
                                    }
                                    f_changeMemberShow(true);
                                    f_changeStaffShow(false);
                                    liger.get("memberId").setEnabled();
                                    liger.get('memberId').set({readonly:false});
                                    liger.get('memberId').set({validate:{required:true}});
                                    liger.get('memberId').setPlaceholder($l("msg.warn.order.om_sales_order.fullmembercode"));
                                    var str="<label class='label1'>"+$l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salespointsummary")+":</label><label id='TotalCurrency' class='label2'>0</label>"
                                    $("#salesLineSummary tr:eq(0) td:eq(2)").html(str);
                                    break;
                                }
                                default:
                                    if(linegrid){
                                        linegrid.set({columns:o_getLineColumns(true,false,isself)});
                                    }
                                liger.get("memberId").setEnabled();
                                liger.get('memberId').set({readonly:false});
                                liger.get('memberId').set({validate:{required:true}});
                                liger.get('memberId').setPlaceholder($l("msg.warn.order.om_sales_order.fullmembercode"));
                                f_changeMemberShow(true);
                                f_changeStaffShow(false);
                                var str="<label class='label1'>"+$l('type.com.lkkhpg.dsis.common.order.dto.salesorder.orderamt')+":</label><label id='TotalCurrency' class='label2'>0</label>"
                                $("#salesLineSummary tr:eq(0) td:eq(2)").html(str);
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
                        cancelable: false,
                        data : channelData,
                        onSelected : function(value,text,data) {
                            if(value=='SRVC'){
                                liger.get('serviceCenterId').set({readonly:false});
                                liger.get('serviceCenterId').set({validate:{required:true}});
                                //会员ID置为只读
                                liger.get('memberId').set({readonly:true});
                                liger.get('memberId').setPlaceholder("   ");
                                liger.get('serviceCenterId').setPlaceholder($l("msg.warn.order.om_sales_order.fullservicecentercode"));
                            }else{
                                liger.get('serviceCenterId').set({readonly:true});
                                liger.get('serviceCenterId').setPlaceholder("   ");
                                liger.get('memberId').set({readonly:false});
                                liger.get('memberId').setPlaceholder($l("msg.warn.order.om_sales_order.fullmembercode"));
                            }
                            reSetLine(false,true,false);
                        },render:function(data){
                            for(var i in channelData){
                                if(channelData[i].value==data){
                                    return channelData[i].meaning;
                                }
                            }
                        }
                    },render:function(data){
                        for(var i in channelData){
                            if(channelData[i].value==data){
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
                    cancelable: false,
                    options : {
                        valueField : "value",
                        textField : "meaning",
                        onBeforeSelect : function(newvalue) {
                            return confirm($l('msg.info.order.period') + newvalue);
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
                    textField :"memberCode",
                    readonly : true,
                    newline : true,
                    cancelable: false,
                    group: $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.memberinfo")
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
                // {
                //     type : 'text',
                //     display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.salesscore"),
                //     name : 'currentPoints',
                //     newline : false,
                //     readonly : true
                // },
                // {
                //     display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.servicecenter"),
                //     name : 'serviceCenterId',
                //     newline : true,
                //     cancelable: false,
                //     type : 'popup',
                //     readonly : true
                // },
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
                // {
                //     type : 'text',
                //     display : $l("type.com.lkkhpg.dsis.common.order.dto.shortcut_operation.currentmoneypv"),
                //     name : 'currentPV',
                //     newline : false,
                //     readonly : true
                // },
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
                    readonly : true,
                    width : 300,
                    group: $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remarkinfo")
                },
                {
                    type : 'textarea',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.internalremark"),
                    name : 'internalRemark',
                    newline : false,
                    readonly : false,
                    validate : {
                        stringMaxLength: 240
                        },
                    width : 300
                 },
                 {
                     type : 'text',
                     display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.channelreference"),
                     name : 'channelReference',
                     readonly : true,
                     newline : true
                 },
                {
                    type : 'text',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.sourcekey"),
                    name : 'sourceKey',
                    readonly : true,
                    newline : false


                },
                {
                    type : 'text',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.batchnumber"),
                    name : 'batchNumber',
                    readonly : true,
                    newline : false
                },
                {
                    type : 'hidden',
                    name : 'headerId'
                },
                {
                    type : 'hidden',
                    name : 'logisticsId'
                },
                {
                    type : 'hidden',
                    name : 'periodId'
                },
                {
                    type : 'hidden',
                    name : 'billSiteId'
                },
                {
                    type : 'hidden',
                    name : 'deliverySiteId'
                },
				{
                	type : 'hidden',
                	name : 'salesOrgCode'
                }]
    };
    return options;
}

/**
 * 订单列信息获取
 * @param istax 是否含税
 * @param ispoint 是否积分购买
 * @param isself 是否自提
 * @returns 订单列数组对象
 */
function o_getLineColumns(istax,ispoint,isself,isAutoship,isApplyLine) {
    var unit={
            display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.unitorigprice"),
            name : 'unitOrigPrice',
            align : 'center',
            type : 'float',
            render:function(data){
                if(!data||!data.itemId){
                    return;
                }
                if(!data.unitOrigPrice){
                    data.unitOrigPrice=0;
                    return summary.zero;
                }
                if(data.itemSalesType&&data.itemSalesType=="FREE"){
                    data.unitOrigPrice=0;
                    return summary.zero;
                }
                return Number(data.unitOrigPrice).toFixed(summary.precision);
            }
        };
    var amount={
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.amount'),
            name : 'amount',
            align : 'center',
            type : 'float',
            render : function(data) {
                if (!data||!data.itemId) {
                    return;
                }
                if(!data.quantity){
                    data.quantity=1;
                }
                if(!data.unitSellingPrice){
                    data.unitSellingPrice=0;
                }
                if(data.itemSalesType&&data.itemSalesType=="FREE"){
                    data.unitSellingPrice=0;
                }
                return Number(data.unitSellingPrice*data.quantity).toFixed(summary.precision);
            }
        };
        var sellPrice={
            display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.unitsellingprice"),
            name : 'unitSellingPrice',
            align : 'center',
            type : 'float',
            render:function(data){
                if(!data||!data.itemId){
                    return;
                }
                if(!data.unitSellingPrice){
                    data.unitSellingPrice=0;
                    return summary.zero;
                }
                if(data.itemSalesType&&data.itemSalesType=="FREE"){
                    data.unitSellingPrice=0;
                    return summary.zero;
                }
                return Number(data.unitSellingPrice).toFixed(summary.precision);
            }
        }
    var saleType={
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
            validate:{
                required:true
            }
        };
    if(ispoint){
        unit={
                display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.unitredeempoint'),
                name : 'unitRedeemPoint',
                align : 'center',
                type : 'text'
            };
        amount={
                display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.redeempoint'),
                name : 'redeemPoint',
                align : 'center',
                type : 'text',
                render : function(data) {
                    if(!data||!data.itemId){
                        return;
                    }
                    if(!data.quantity){
                        data.quantity=1;
                    }
                    if(!data.unitRedeemPoint){
                        data.unitRedeemPoint=0;
                    }
                    return Number(data.unitRedeemPoint*data.quantity).toFixed(summary.precision);
                }
            };
    }
    var columns = [{
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
        },saleType,
            {
                display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemid'),
                name : 'itemNumber',
                align : 'center',
                width : 100,
                textField : 'itemNumber',
                validate:{
                    required:true
                }
            },
            {
                display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemname'),
                name : 'itemName',
                align : 'center',
                width : 260,
                type : 'text'
            },
            // {
            //     display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pv'),
            //     name : 'pv',
            //     align : 'center',
            //     type : 'text',
            //     render:function(data){
            //         if(!data||!data.itemId){
            //             return;
            //         }
            //         if (data.pv == null || data.pv == 0) {
            //
            //
            //
            //
				// 		return "0"
				// 	}else if(data.discountPv == null || data.discountPv == 0){
				// 		return data.pv;
				// 	}
				// 	return data.pv.add(data.discountPv.div(data.quantity));
            //
            //
            //
            //
            //
            //
            //
            //
            //
            //
            //
            //
            //     }
            // },
        unit
            // BEGIN lkkhpg_coupon xin.zhang 2017-09-01 删除优惠方式
            // BEGIN lkkhpg_coupon xin.zhang 2017-09-01 删除优惠方式






















            ,sellPrice, {
                display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.quantity"),
                name : 'quantity',
                align : 'center',
                type : 'int',
                validate:{required:true,digits:true,min:1,max:99999}
            }, {
                display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.discountprice'),
                name : 'discountAmt',


                align : 'center',
                type : 'float',
                render : function(data) {
                    if (!data||!data.itemId) {
                        return;
                    }
                    if(!data.discountAmt||data.discountAmt==0){


                        return "0";
                    }
                    return data.discountAmt;
                }
            },
        // {
        //         display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pvsum'),
        //         name : 'PVSum',
        //         align : 'center',
        //         type : 'text',
        //         render : function(data) {
        //             if (!data||!data.itemId) {
        //                 return;
        //             }
        //             if(!data.pv){
        //                 return "0";
        //
        //
        //
        //
        //             }
        //             if(!data.quantity){
        //                 data.quantity=1;
        //             }
        //             if(!data.discountPv){
        //             	data.discountPv = 0;
        //             	//return;
        //             }
        //             data.PVSum=data.pv*data.quantity;
        //             if(!data.PVSum||data.PVSum==0){
        //                 return "0"
        //             }
        //             return data.PVSum;
        //         }
        //     },
    amount  ];
    // BEGIN lkkhpg_coupon xin.zhang 2017-09-01
    if(summary.enableTax && !summary.priceIncludeTax){
        var tax = {
                display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.tax"),
                name : 'tax',
                align : 'center',
                type : 'float',
                render:function(data){
                    if (!data||!data.itemId) {
                        return;
                    }
                    data.tax=data.unitSellingPrice - data.unitOrigPrice - -data.discountAmt;
                    return Number(data.tax).toFixed(summary.precision);
                }
            };
            columns.splice(6, 0, tax);

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
                if (!data.discountPv) {
                    data.discountPv = 0;
                }
                return data.pv;
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
                if (!data.discountTax) {
                    data.discountTax = 0;
                }
                return (Number(data.unitOrigPrice.sub(data.discountAmt)) - -data.discountTax).toFixed(summary.precision);
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
                if (!data.discountTax) {
                    data.discountTax = 0;
                }
                return data.taxAmount.sub(data.discountTax);
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
                if (!data.discountPv) {
                    data.discountPv = 0;
                }
                return data.pv.sub(data.discountPv);
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
                return Number(data.unitSellingPrice).toFixed(summary.precision);
            }
        };
        columns.splice(6, 0, discountAfterPv);
        columns.splice(7, 0, discountAfterPrice);
    }
    // END lkkhpg_coupon xin.zhang 2017-09-01
    // lkkhpg_coupon add start.
	// lkkhpg_coupon end
    var deliveryStatus = {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.deliverystatus"),
            name : 'status',
            width : 100,
            type :'select',
            render : function(data) {
                for ( var linestatus in linesdeliverystatus) {
                    if(data.status == linesdeliverystatus[linestatus].value){
                        return linesdeliverystatus[linestatus].meaning;
                    }
                }
            }
    };
    
    if(liger.get("orderStatus").getValue()=="COMP"){
        columns.unshift(deliveryStatus);
    }
    return columns;
}

/**
 * 订单行表格的参数
 * @param isauto 是否autoship订单
 */
function o_ligerGridOptions(isauto) {
    var options = {
        detail: { onShowDetail: f_getItemPackageDetail,height:'auto' },
        columns : o_getLineColumns(true,false,true,isauto,false),
        usePager : false,
        width : '98%',
        onBeforeShowData : function(data) {
			if("PROD" == applyOn){
				if(linegrid){
					linegrid.set({
						columns:o_getLineColumns(true,false,true,isauto,true)
					});
				}
				//linegrid.set({columns:o_getLineColumns(true,false,true,false,true)});
			}
		}
    };
    return options;
}

/**
 * 地址表格的参数
 */
function o_addressGridOptions(dataOption) {
    var displayName= $l('type.com.lkkhpg.dsis.common.member.dto.membersite.receivebillsname');
    if(dataOption){
        displayName= $l('type.com.lkkhpg.dsis.common.member.dto.membersite.receivegoodsname');
    }
    var options = {
        usePager : false,
        checkbox: false,
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
                    width: 400,
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
                }
                ]
    }
  
    return options;
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
        period.set({readonly:true});
        return;
    }

    var date = cdate.getDate();
    if (date > 3) {
        period.set({readonly:true});
        return;
    }
    period.set({readonly:false});
}

/**
 *  校验配送类型
 *  @param isautoship 是否autoship订单
 */
function validateDeliver(isautoship) {
    var deliveryType = liger.get("deliveryType");
    type = deliveryType.getValue();
    var isPoint=false;
    if(liger.get("orderType")&&liger.get("orderType").getValue()=='RDEM'){
        isPoint=true;
    }
    if (type == "PCKUP") {
        $("#addressPanel").hide();
        if(linegrid){
        	if(applyOn == "PROD"){
        		linegrid.set({columns:o_getLineColumns(true,isPoint,true,isautoship,true)});
        	}else{
        		linegrid.set({columns:o_getLineColumns(true,isPoint,true,isautoship)});
        	}
            linegrid.reRender();
        }
        liger.get("isCod").setValue(false);
        $("#actrualPayAmt").text(Number(summary.currency - -summary.adjustMents).toFixed(summary.precision));
        
    } else if (type == "SHIPP") {
        if(linegrid){
        	if(applyOn == "PROD"){
        		linegrid.set({columns:o_getLineColumns(true,isPoint,true,isautoship,true)});
        	}else{
        		linegrid.set({columns:o_getLineColumns(true,isPoint,true,isautoship)});
        	}
            linegrid.reRender();
        }
        reSetLine(false,false,true);
        $("#addressPanel").show();
        $("#actrualPayAmt").text(Number(summary.currency - -summary.shippingTier - -summary.adjustMents-summary.discountAmt).toFixed(summary.precision));
//        if(liger.get("memberId")){
//            f_memberAddress(liger.get("memberId").getValue());
//        }
    }
    
}

/**
 * 校验
 * @param isautoship
 */
function validate(isautoship) {
    if(!isautoship){
        validatePeriod();
    }
    validateDeliver(isautoship);
}


/**
 * 页面初始化
 * @param isautoship
 */
function init(isautoship) {
    $("#deliveryType").ligerComboBox({
        css : 'combobox',
        data : deliveryTypeData,
        valueField : "value",
        textField : "meaning",
        readonly : true,
        cancelable:false,
        onSelected : function(value) {
            validateDeliver(isautoship);
        }
    });
    var defaultType="SHIPP";
    if(!isautoship){
        defaultType="PCKUP";
    }
    for(var i in deliveryTypeData){
        if(deliveryTypeData[i].value== defaultType){
            liger.get("deliveryType").setValue(defaultType);
            liger.get("deliveryType").setText(deliveryTypeData[i].meaning);
            break;
        }
    }
    $("#deliveryCompany").ligerComboBox({
        css : 'combobox',
        valueField : "shippingTierId",
        textField : "shippingTierName",
        cancelable:false,
        readonly : true,
        onSelected : function(value,text,data) {
            var price=Number.MAX_VALUE;
            if(data){
                for(var i in data.shippingTierDtls){
                    if(data.shippingTierDtls[i].shippingFee<price&&summary.currency>=data.shippingTierDtls[i].invAmountFrom&&(!data.shippingTierDtls[i].invAmountTo||summary.currency<=data.shippingTierDtls[i].invAmountTo)){
                        summary.shippingTier=data.shippingTierDtls[i].shippingFee;
                        $("#shippingTier").text(summary.shippingTier);
                        $("#actrualPayAmt").text(summary.currency - -summary.shippingTier - -summary.adjustMents);
                    }
                }
               
            }
        }
    });
    if(!isautoship){
        $("#payAdjust").ligerPanel({
            title : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.adjustment'),
            width : '98%',
            height : 'auto'
        });
        liger.get("payAdjust").collapse();
        $("#coupons").ligerComboBox({
            css : 'combobox',
            valueField : "value",
            textField : "meaning",
            readonly : true,
            cancelable:false
        });
        // 人工调整里面的下拉框调整
        $("#adjustType").ligerComboBox({
            width : 120,
            data : adjustTypeData,
            readonly : true,
            valueField : "value",
            textField : "meaning",
            cancelable:false
        });
        liger.get('serviceCenterId').setDisabled();
        liger.get('adjustType').setValue("add");
        for(var i in adjustTypeData){
            if(adjustTypeData[i].value=="add"){
                liger.get('adjustType').setText(adjustTypeData[i].meaning);
            }
        }
        
      
        if(liger.get("orderStatus").getValue()!="COMP" ){
            var files=new Array();
            files.push("sourceKey");
            files.push("batchNumber");
            om_oc_form.setVisible(files,false);
        }
    }
    $("#isCod").ligerCheckBox({ readonly: true });
    $("#freeShipping").ligerCheckBox({ readonly: true });
    validate(isautoship);
}

/**
 * 打印清单.
 */
function f_printList(){
	var marketCode = om_oc_form.getData().salesOrganization.market.code;
	if(marketCode == "TH"){
		$.ligerDialog.open({
	        title: $l("type.com.lkkhpg.dsis.report.type.choosing"),
	        width:400,
	        height:300,
	        slide: false,
	        url: _basePath + "/sys/report/sys_report_type_dialog_th.html",
	        buttons: [{
	            text: $l("sys.hand.btn.ok" ),
	            onclick: function(i, d) {
	                var orderNumber = liger.get("orderNumber").getValue();
	                
	                if(!orderNumber){
	                	return;
	                }else{
	                    var form = d.frame.report_type_form;
	                    var docType= form.getData().docType;
	                    var lange = form.getData().lange;
	                    if (form.valid()) {
	                        d.close();
	                        window.open(_basePath+'/report/run?docType='+ docType +'&reportProgramCode=RPT-PACKING-LIST-'+marketCode+'&lange='+lange+'&orderNumber='+orderNumber+'&deliveryNumber=');
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
	}else if(marketCode == "CA"){
		$.ligerDialog.open({
	        title: $l("type.com.lkkhpg.dsis.report.type.choosing"),
	        width:400,
	        height:300,
	        slide: false,
	        url: _basePath + "/sys/report/sys_report_type_dialog_fr_ca.html",
	        buttons: [{
	            text: $l("sys.hand.btn.ok" ),
	            onclick: function(i, d) {
	                var orderNumber = liger.get("orderNumber").getValue();
	                
	                if(!orderNumber){
	                	return;
	                }else{
	                    var form = d.frame.report_type_form;
	                    var docType= form.getData().docType;
	                    var lange = form.getData().lange;
	                    if (form.valid()) {
	                        d.close();
	                        window.open(_basePath+'/report/run?docType='+ docType +'&reportProgramCode=RPT-PACKING-LIST-'+marketCode+'&lange='+lange+'&orderNumber='+orderNumber+'&deliveryNumber=');
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
	}else{
	$.ligerDialog.open({
        title: $l("type.com.lkkhpg.dsis.report.type.choosing"),
        width:400,
        height:300,
        slide: false,
        url: _basePath + "/sys/report/sys_report_type_dialog.html",
        buttons: [{
            text: $l("sys.hand.btn.ok" ),
            onclick: function(i, d) {
                var orderNumber = liger.get("orderNumber").getValue();

                if(!orderNumber){
                	return;
                }else{
                    var form = d.frame.report_type_form;
                    var docType= form.getData().docType;
                    if (form.valid()) {
	                    if(marketCode == "HK"){
                    		$.ajaxSetup({async:false});
                    		$.ajax({
                    	          type : 'POST',
                    	          url : _basePath + "/om/pageList/printTime/update?orderNumber="+orderNumber,
                    	          success : function(json) {
                    	        	  return true;
                    	          }
                    	     });
                    		$.ajaxSetup({async:true});
                    	}
                        d.close();
                        window.open(_basePath+'/report/run?docType='+ docType +'&reportProgramCode=RPT-PACKING-LIST-'+marketCode+'&orderNumber='+orderNumber+'&deliveryNumber=');
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
}
/**
 * 打印发票
 */
function f_printInvoice(){
    var headerId = $("#headerId").val();
    var marketCode = om_oc_form.getData().salesOrganization.market.code;
    if(!headerId) retrun;

    if(marketCode == "TH"){
    	$.ligerDialog.open({
    		title: $l("type.com.lkkhpg.dsis.report.type.choosing"),
            width:400,
            height:400,
            slide: false,
            url: _basePath + "/sys/report/sys_report_type_dialog_th.html",
            buttons: [{
                text: $l("sys.hand.btn.ok" ),
                onclick: function(i, d) { 
                	var form = d.frame.report_type_form;
                    var docType= form.getData().docType;
                    var lange = form.getData().lange;
                    if (form.valid()) {
                        d.close();
                        window.open(_basePath+'/report/run?docType='+ docType +'&reportProgramCode=TMPL_' + marketCode + '_INVOICE'+'&lange='+lange+'&headerId='+headerId);
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
    }else if(marketCode == "CA"){
    	$.ligerDialog.open({
    		title: $l("type.com.lkkhpg.dsis.report.type.choosing"),
            width:400,
            height:400,
            slide: false,
            url: _basePath + "/sys/report/sys_report_type_dialog_fr_ca.html",
            buttons: [{
                text: $l("sys.hand.btn.ok" ),
                onclick: function(i, d) { 
                	var form = d.frame.report_type_form;
                    var docType= form.getData().docType;
                    var lange = form.getData().lange;
                    if (form.valid()) {
                        d.close();
                        window.open(_basePath+'/report/run?docType='+ docType +'&reportProgramCode=TMPL_' + marketCode + '_INVOICE'+'&lange='+lange+'&headerId='+headerId);
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
    } else {
    	$.ajax({
            type : 'POST',
            url : _basePath + "/om/print_invoice?orderId=" +headerId ,
            success : function(json) {
                if(!json || !json.rows ||!json.rows[0].attachmentId){


                 f_printEinvoice(headerId, null, null);
                   //  Hap.showError($l("msg.warning.order.print_invoice_fail"));

                }else{
                     //window.open( _basePath + "/sys/attach/file/detail?fileId=" +json.rows[0].attachmentId);

                	//lkkhpg_tw_einvoices 2017-07-31 BEGIN
                	//window.open(_basePath+"/report/run?docType=PDF&reportProgramCode="+json.rows[0].programCode+"&headerId="+headerId);
                	if(json.rows[0].einvoiceFlag && json.rows[0].einvoiceFlag == 'Y'){
                		f_printEinvoice(headerId, json.rows[0].getInvoiceMethod, json.rows[0].invoiceNumber);
                	}else{
                		window.open(_basePath+"/report/run?docType=PDF&reportProgramCode=TMPL_" + marketCode + "_INVOICE"+"&headerId="+headerId);
                	}
                	//lkkhpg_tw_einvoices 2017-07-31 END






                }
            },
            error : function(json){
                //Hap.showError($l("msg.warning.order.print_invoice_fail"));
            }




        });

    }  
    
    
    






}
/**
 * 保存订单
 */
function f_save(isSave,isSumbit) {
    var requestData = om_oc_form.getData();

    $.ajax({
        type : 'POST',
        url : _basePath + "/om/order/saveHead",
        data : JSON2.stringify(requestData),
        success : function(json) {
              if(json.success){

                        Hap.showSuccess();
              }

        },
        contentType : "application/json; charset=utf-8"
    });

}


/**
 * 验证按钮是否可见
 * @param isAutoship 是否autoship
 */
function initButton(isAutoship){
	
	$("#printList").hide();
    $("#pickItem").hide();
    $("#saveBtn").hide();
    $("#submitBtn").hide();
    $("#paymentBtn").hide();
    $("#deliveryPackage").hide();
    $("#cancle").hide();
    $("#invalid").hide();
    //TODO 修改支付方式
    $("#editPaymentBtn").hide();
    $("#printInvoice").hide();
    //TODO 所有已完成订单，可修改支付方式
    var status = liger.get("orderStatus").getValue();
    if (status == "COMP") {
        $("#editPaymentBtn").ligerButton({
            click : getPaymentOfEditPaymentMth,
            text : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.paymentinfo")
        });
        $("#editPaymentBtn").show();
    }
    if(!isAutoship){
        var orderStatus=liger.get("orderStatus").getValue();
        if(orderStatus=="WPAY"){
            $("#saveBtn").ligerButton({
                click : function(){
                    f_save(true,false);
                },
                text : $l('sys.hand.btn.save')
            });
            $("#submitBtn").ligerButton({
                click : function(){
                    f_save(false,true);
                },
                text : $l('sys.hand.btn.submit')
            });
            $("#paymentBtn").ligerButton({
                click : function(){
                    var headerId=$("#headerId").val();
                    toPaymentPage(headerId);
                },
                text : $l('sys.hand.btn.payment')
            });

            $("#cancle").ligerButton({
                click : function(){
                	 $.ligerDialog.confirm($l('msg.warn.iscancel'), function (yes)
                             {
                                 if (yes){
                                     var requestData ={orderStatus:"CANCL",headerId:$("#headerId").val()};
                                     $.ajax({
                                         type : 'POST',
                                         url : _basePath + "/om/order/update",
                                         data:JSON2.stringify(requestData),
                                         success : function(json) {
                                             if (json.success) {
                                                 liger.get("orderStatus").setValue("CANCL");
                                                 for(var i in orderStatusData){
                                                     if(orderStatusData[i].value=="CANCL"){
                                                         liger.get("orderStatus").setText(orderStatusData[i].meaning);
                                                         break;
                                                     }
                                                 }
                                                 $("#saveBtn").hide();
                                                 $("#submitBtn").hide();
                                                 $("#paymentBtn").hide();
                                                 $("#cancle").hide();
                                             }
                                         },contentType : "application/json; charset=utf-8"
                                     });
                                 }
                             });
                },
                text : $l('sys.hand.btn.cancel')
            });
            $("#saveBtn").show();
            $("#cancle").show();
            $("#paymentBtn").show();
        }else if(orderStatus=="NEW"/*||orderStatus=="FAIL"*/){
            $("#saveBtn").ligerButton({
                click : function(){
                    f_save(true,false);
                },
                text : $l('sys.hand.btn.save')
            });
            $("#submitBtn").ligerButton({
                click : function(){
                    f_save(false,true);
                },
                text : $l('sys.hand.btn.submit')
            });
            //$("#saveBtn").show();
           // $("#submitBtn").show();
        }else if(orderStatus=="SAV"){
            $("#submitBtn").ligerButton({
                click : function(){
                    f_save(false,true);
                },
                text : $l('sys.hand.btn.submit')
            });
            //$("#submitBtn").show();
        }else if(orderStatus=="COMP"){

            $("#saveBtn").ligerButton({
                click : function(){
                    f_save(true,false);
                },
                text : $l('sys.hand.btn.save')
            });
           $("#periodBtn").hide();
           $("#printList").ligerButton({
                   click : function(){
                      f_printList();
                   },
                   text : $l('msg.info.order.printlist')
               });
            $("#pickItem").ligerButton({
                click : function(){
                    window.top.f_addTab(null, $l('msg.info.order.pick_release'),_basePath + '/dm/dm_delivery_pick_release.html?orderNumber='+liger.get("orderNumber").getValue());
                },
                text : $l('msg.info.order.pick_release')
            });
           
            $("#deliveryPackage").ligerButton({
                click : function(){
                    window.top.f_addTab(null, $l('msg.info.order.delivery'),_basePath + '/dm/dm_delivery_query.html?orderNumber='+liger.get("orderNumber").getValue());
                },
                text : $l('msg.info.order.delivery')
            });
            $("#printInvoice").ligerButton({
                click : function(){
                    f_printInvoice();
                },
                text : $l('msg.info.order.printinvoice')
            });
            $("#printInvoice").show();
            $("#deliveryPackage").show();
            $("#printList").show();
            $("#pickItem").show();
            $("#invalid").ligerButton({
                click : getMarket,
                text : $l('msg.info.order.invalid')
            });
            $("#invalid").show();
            $("#saveBtn").show();
        }else if(orderStatus=="CONF"){
             $("#pickItem").ligerButton({
                 click : function(){
                     window.top.f_addTab(null, $l('msg.info.order.pick_release'),_basePath + '/dm/dm_delivery_pick_release.html?orderNumber='+liger.get("orderNumber").getValue());
                 },
                 text : $l('msg.info.order.pick_release')
             });
             $("#deliveryPackage").ligerButton({
                 click : function(){
                     window.top.f_addTab(null, $l('msg.info.order.delivery'),_basePath + '/dm/dm_delivery_query.html?orderNumber='+liger.get("orderNumber").getValue());
                 },
                 text : $l('msg.info.order.delivery')
             });
             $("#printInvoice").ligerButton({
                 click : function(){
                     alert($l("msg.info.order.printinvoice"))
                 },
                 text : $l('msg.info.order.printinvoice')
             });
             $("#printInvoice").show();
             $("#deliveryPackage").show();
             $("#pickItem").show();
             $("#invalid").ligerButton({
                 click : getMarket,
                 text : $l('msg.info.order.invalid')
             });
             $("#invalid").show();
            $("#confirmReceive").ligerButton({
                click : confirmReceive,
                text : $l('msg.info.order.confirmreceipt')
            });
            $("#confirmReceive").show();
        }else if(orderStatus=="VOID"){
        	$("#printInvoice").ligerButton({
                click : function(){
                	f_printInvoice();
                },
                text : $l('msg.info.order.printinvoice')
            });
            $("#printInvoice").show();
        }
    }
    if(isAutoship){
        $("#activeBtn").ligerButton({
            click :function(){
                f_updateAutoShipStatus('active');
            } ,
            text : $l('msg.info.order.activation')
        });
        $("#stopBtn").ligerButton({
            click : function(){
                f_updateAutoShipStatus('suspend')
            },
            text : $l('msg.info.order.suspend')
        });
        $("#endBtn").ligerButton({
            click : function(){
                f_updateAutoShipStatus('termination')
            },
            text : $l('type.com.lkkhpg.dsis.common.member.btn.memberdetails.terminate')
        });
        $("#autoShipsaveBtn").ligerButton({
            text:$l('sys.hand.btn.submit'),
            click:f_sumbitAutoShip
        });
        $("#addCreditCar").ligerButton({
            click : function(){
                $.ligerDialog.open({ 
                    target: $("#addCredit") ,
                    title:$l('msg.info.order.add_your_credit_card'),
                    width:650
                    });
            },
            text : $l('sys.hand.btn.new')
        });
        
    }
    $("#copyBtn").hide();
}


/**
 * 会员地址获取与设置
 * @param memberId 会员id
 */
function f_memberAddress(memberId) {
    if (!memberId) {
        return;
    }
    if(liger.get("deliveryType").getValue()!="SHIPP"){
        return;
    }
    $.ajax({
        type : 'POST',
        url : _basePath + "/mm/site/query?memberId=" + memberId,
        success : function(json) {
            if (json.success) {
                var addressData={};
                var billAddress=new Array();
                var shipAddress=new Array();
                for(var i in json.rows){
                    if(json.rows[i].siteUseCode=="BILL"){
                        billAddress.push(json.rows[i]);
                    }else if(json.rows[i].siteUseCode=="SHIP"){
                        shipAddress.push(json.rows[i]);
                    }
                }
                addressData.rows=billAddress;
                addressgrid.set({data:addressData});
                addressData.rows=shipAddress;
                deliveryaddress.set({data:addressData});
            }
        }
    });
}

/**
 * 会员账户信息获取
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
        } else if(rows[row].accountingType == 'SP'){
            liger.get("currentPoints").setValue(rows[row].balance);
           // liger.get("currentPV").setValue(rows[row].balance);
        }
    }
}

/**
 * 订单行金额汇总
 */
function f_calculateLinePrice(data) {
    var lineData = linegrid.getData();
    // summary.pv = 0;
    summary.currency = 0;
    summary.exchange=0;
    summary.points=0;
    summary.tax = 0;
    // var totalPv = 0;
    var orderType;
    if(liger.get('orderType')){
        orderType=liger.get('orderType').getValue();
    }else{
        orderType='STDP';
    }
    var ispoint=(orderType=='RDEM');
    for ( var line in lineData) {
        if(!lineData[line].redeemPoint){
            lineData[line].redeemPoint=0;
        }
        if(!lineData[line].unitOrigPrice){
            lineData[line].unitOrigPrice=0;
        }
        if(!lineData[line].unitDiscountPrice){
            lineData[line].unitDiscountPrice=0;
        }
        if(!lineData[line].amount){
            lineData[line].amount=0;
        }
        if(!lineData[line].PVSum){
            lineData[line].PVSum=0;
        }
        if(!lineData[line].quantity){
            lineData[line].quantity=1;
        }
        //coupon 修改
        /*if(lineData[line].PVSum){
            summary.pv += Number(lineData[line].PVSum);
        }else{
            if(!lineData[line].pv){
                lineData[line].pv=0;
            }
            summary.pv += Number(lineData[line].pv*lineData[line].quantity);
        }*/
        // summary.pv += Number(lineData[line].PVSum);


        /*if(!summary.priceIncludeTax&&summary.enableTax){
            lineData[line].unitSellingPrice=(lineData[line].unitOrigPrice*(1+summary.taxRate)-lineData[line].unitDiscountPrice).toFixed(summary.precision);
            if(lineData[line].tax){
            summary.tax += Number(Number(lineData[line].tax).toFixed(summary.precision) * lineData[line].quantity)
        }
        }else{
            lineData[line].unitSellingPrice=lineData[line].unitOrigPrice-lineData[line].unitDiscountPrice;
        }*/
      
        lineData[line].amount=Number(lineData[line].unitSellingPrice*lineData[line].quantity);
        if(lineData[line].itemSalesType=="EXCH"){
            summary.exchange+=Number(lineData[line].amount);
        }
        summary.points += Number(lineData[line].redeemPoint);
        if(lineData[line].itemSalesType!='FREE'){
            summary.currency += Number(lineData[line].amount);
        }
        
    }
    // $("#totalPV").text(summary.pv);
    // $("#summaryPV").text(summary.pv);
    if(ispoint){
        $("#beforeTax").text(summary.zero);
        $("#tax").text(summary.zero);
        $("#afterTax").text(summary.zero);
        $("#TotalCurrency").text(Number(summary.points));
        if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()=="SHIPP"){
            $("#actrualPayAmt").text(Number((summary.shippingTier - -summary.adjustMents).toFixed(summary.precision)));
        }else{
            $("#actrualPayAmt").text(Number((summary.adjustMents).toFixed(summary.precision)));
        }
    }else{
        if(summary.enableTax){
			//lkkhpg_invoice_sit 2017-9-6 BEGIN
        	if(summary.priceIncludeTax){
        		summary.tax = Number((summary.currency/ (1 + Number(summary.taxRate)) * summary.taxRate).toFixed(summary.precision));
        	}else if(data&&data.taxAmt){
        		summary.tax = data.taxAmt;
        	}
        	//lkkhpg_invoice_sit 2017-9-6 END
        }else{
            summary.tax = 0;
        }
        
        $("#beforeTax").text(Number(summary.currency - summary.tax).toFixed(summary.precision));
//        if(summary.priceIncludeTax){
//            $("#beforeTax").text(Number((summary.currency - summary.tax).toFixed(summary.precision)));
//        }else{
//            $("#beforeTax").text(Number(summary.currency.toFixed(summary.precision)));
//        }
        
        $("#tax").text(Number(summary.tax).toFixed(summary.precision));
        $("#TotalCurrency").text(Number(summary.currency).toFixed(summary.precision));
        $("#afterTax").text(Number(summary.currency).toFixed(summary.precision));
        
        if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()!="SHIPP"){
            $("#actrualPayAmt").text(Number((summary.currency - -summary.adjustMents-summary.discountAmt).toFixed(summary.precision)));
        }else{
            $("#actrualPayAmt").text(Number((summary.currency - -summary.shippingTier+summary.adjustMents-summary.discountAmt).toFixed(summary.precision)));
        }
        
        $("#exchange").text(Number(summary.exchange.toFixed(summary.precision)))
        
        if($("#getsalespoint")&&ordertype=="autoship"&&pointRate){
            if(pointLimit&&(Number($("#actrualPayAmt").text())*pointRate>pointLimit)){
                $("#getsalespoint").text(pointLimit);
            }else{
                $("#getsalespoint").text(Number((Number($("#actrualPayAmt").text())*pointRate).toFixed(summary.precision)));
            }
        }
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
function f_payMentAdjust(obj,ischeck) {
    var isNum = /^-?\d*\.?\d*$/;
    if(!isNum.test(obj.value)){
        $(obj).addClass("l-text-invalid");
        //$(obj).removeAttr("title").ligerHideTip();
        $(obj).attr("title", $l("type.com.lkkhpg.dsis.common.order.payadjustment.number")).ligerTip({
            distanceX : 5,
            distanceY : -3,
            auto : true
        });
        $(obj).val("");
        return;
    }else{
        if($(obj).hasClass("l-text-invalid")){
            $(obj).removeClass("l-text-invalid");
            $(obj).removeAttr("title").ligerHideTip();
        }
    }
    
    if(obj.value<0){
        $(obj).val("");
        Hap.showError($l("type.com.lkkhpg.dsis.common.order.payadjustment.minus"));
        return;
    }
    var id ;
    if (obj) {
        id = obj.id + 'Text';
        obj.tempValue=Number($("#" + id).text());
        if(obj.id=='adjust'){
            var tr = $(obj).parents('tr');
            if(!obj.value){
                tr.find("#adjustText").text("0");
            }else{
                if (tr.find("input[type='hidden']:eq(0)").val()=='subs') {
                    tr.find("#adjustText").text("-"+obj.value);
                }else{
                    tr.find("#adjustText").text(/*"+"+*/obj.value);
                }
            }
            
        }else{
            $("#" + id).text(obj.value);
        }
    }
    if(ischeck){
    	/* if(obj.id=='exchangeBalanceUse'){
             if(Number(obj.value)>Number(liger.get('exchangeBalance').getValue())){
                 Hap.showError("exchangeBalance"+$l("type.com.lkkhpg.dsis.common.order.payadjustment.biggerthenorder")+"exchangeBalance");
                 $(obj).val("");
                 $("#" + id).text("0");
                 return;
             }
             if(Number(obj.value)>Number(summary.exchange)){
                 Hap.showError("exchangeBalance"+$l("type.com.lkkhpg.dsis.common.order.payadjustment.totalbiggerthenorder"));
                 $(obj).val("");
                 $("#" + id).text("0");
                 return;
             }
             $("#exchangeBalanceUse").val(obj.value);
             if(!obj.value){
                 $("#" + id).text("0")
             }else{
                 $("#" + id).text(obj.value)
             }
         }else if(obj.id=='remainingBalanceUse'){
             if(Number(obj.value)>Number(liger.get('remainingBalance').getValue())){
                 Hap.showError("remainingBalance"+$l("type.com.lkkhpg.dsis.common.order.payadjustment.totalbiggerthenorder")+"remainingBalance");
                 $(obj).val("");
                 $("#" + id).text("0");
                 return;
             }else{
                 $("#remainingBalanceUse").val(obj.value);
                 if(!obj.value){
                     $("#" + id).text("0")
                 }else{
                     $("#" + id).text(obj.value)
                 }
             }
        }*/
    }
    var afterAdjust = 0;
   /* afterAdjust -= Number($("#exchangeBalanceUse").val());
    afterAdjust -= Number($("#remainingBalanceUse").val());*/
    var trLength=$('#payAdjust table tr').length;
    var trs = $('#payAdjust table tr:lt('+(trLength-1)+')');
    $.each( trs, function() {
        if(Number($(this).find("#adjust").val())>0){
            if($(this).find("input[type='hidden']:eq(0)").val()=='subs'){
                afterAdjust -= Number($(this).find("#adjust").val());  
            }else{
                afterAdjust += Number($(this).find("#adjust").val());  
            }
        }
      });
    if((Number(summary.currency)+afterAdjust)<0){
        if(obj.id=='adjust'){
            var tr = $(obj).parents('tr');
            tr.find("#adjustText").text("0");
        }else{
            $("#" + id).text("0");
        }
        $(obj).val("");
        Hap.showError($l("type.com.lkkhpg.dsis.common.order.payadjustment.adjustbiggerthenorder"));
        return;
    }else{
        $("#afterAdjust").text(afterAdjust);
        summary.adjustMents=afterAdjust;
        if(liger.get("deliveryType").getValue()!="SHIPP"){
            $("#actrualPayAmt").text(summary.currency - -afterAdjust-summary.discountAmt);
        }else{
            $("#actrualPayAmt").text(summary.currency - -summary.shippingTier - -afterAdjust-summary.discountAmt);
        }
        
    }
    return;
}


/**
 * 订单数据设置
 * @param data 订单详情数据
 */
function f_setOrderDetai(data) {
    var memberCode=liger.get("memberId").getText();

    om_oc_form.setData(data);
    liger.get("memberId").setText(memberCode);
    liger.get("period").setText(data.period);
    //销售组织以及市场货币设置
    if(data.salesOrganization){
        f_setMarketAndCurrency(data.salesOrganization.market, data.spmCurrency);
        liger.get('salesOrgId').setValue(data.salesOrganization.salesOrgId);
        liger.get('salesOrgId').setText(data.salesOrganization.name);
		// add 获取销售组织Code 20161010
        $("#salesOrgCode").val(data.salesOrganization.code);
    }
    //订单状态设置
    for(var i in orderStatusData){
        if(orderStatusData[i].value==data.orderStatus){
            liger.get("orderStatus").setText(orderStatusData[i].meaning);
            break;
        }
    }
    //订单类型设置
    for(var i in orderTypeData){
        if(orderTypeData[i].value==data.orderType){
            liger.get("orderType").setText(orderTypeData[i].meaning);
            break;
        }
    }
    //销售渠道设置
    for(var i in channelData){
        if(channelData[i].value==data.channel){
            liger.get("channel").setText(channelData[i].meaning);
            break;
        }
    }
    //是否货到付款设置
    if(data.codFlag=='Y'){
        liger.get("isCod").setValue(true);
    }
    //是否包邮设置
    if(data.freeShipping == 'Y'){
    	liger.get("freeShipping").setValue(true);
    }
    var gridData = {};
    gridData.rows = data.lines;
    linegrid.set({
        data : gridData
    });
    summary.currency=data.orderAmt;
    if (data.adjustMents) {
        f_setDefaultAdjustMents(data.adjustMents)
    }
    //订单用户信息设置
    if(data.member){
        if (data.member.memAccountingBalances) {
            f_setMemberAccount(data.member.memAccountingBalances);
        }
        gridData.rows=data.member.memSites;
        liger.get("cnName").setValue(data.member.chineseName);
        liger.get("enName").setValue(data.member.englishName);
        liger.get("memberId").setValue(data.member.memberId);
        if(data.member.memberCode){
            liger.get("memberId").setText(data.member.memberCode);
        }
        if(data.member.exchangeBalance){
            liger.get("exchangeBalance").setValue(data.member.exchangeBalance);
            $("#exchangeBalance").text(data.member.exchangeBalance);
        }
        if(data.member.remainingBalance){
            liger.get("remainingBalance").setValue(data.member.remainingBalance);
            $("#remainingBalance").text(data.member.remainingBalance);
        }
        
        liger.get("currentPoints").setValue(data.member.salesPiont);
        // liger.get("currentPV").setValue(data.member.currentPv);
        memberStatus=data.member.status;
       //lkkhpg_tw_einvoices 2017-08-01 BEGIN
        memberType=data.member.memberType;
        //lkkhpg_tw_einvoices 2017-08-01 END



    }

    // 优惠券set
    var appliedCoupon = data.appliedCoupon;
	$("#couponsText").text(data.discountAmt); 
	$("#couponsText").val(data.discountAmt); 
    for(var i in appliedCoupon){
    	liger.get("coupons").setValue(appliedCoupon[i].assignCode);
    	liger.get("coupons").setText(appliedCoupon[i].name);
    	if(appliedCoupon[i].applyOn == "PROD"){
    		$("#couponsText").val(""); 
    		$("#couponsText").text(""); 
    		applyOn = appliedCoupon[i].applyOn; //查询页面点击进详情页面取不到参数applyOn,重新赋值.
    		linegrid.set({
				columns:o_getLineColumns(true,false,true,false,true)
			});
    		linegrid.reRender();
    	}
    }
    //订单配送设置
    f_setDelivery(data.salesSites,data.logistics);
    liger.get('deliveryType').setValue(data.deliveryType);
    for ( var i in deliveryTypeData) {
        if (deliveryTypeData[i].value == data.deliveryType) {
            liger.get('deliveryType').setText(deliveryTypeData[i].meaning);
            validateDeliver();
            break;
        }
    }
    f_setDefaultAddress(data.salesSites);
    //计算价格
    f_calculateLinePrice(data);
    $("#actrualPayAmt").text(data.actrualPayAmt.toFixed(summary.precision));
    // D003 2017/07/10 BEGIN
    selectMethod = data.getEinvoiceMethod;
    // D003 END


}

/*--------设置默认支付调整----------*/

/**
 * 设置默认支付调整
 * @param adjustMents 价格调整数组
 */
function f_setDefaultAdjustMents(adjustMents) {
    if(!adjustMents||!liger.get("adjustType")){
        return;
    }
    var tr = $('#payAdjust table tr:eq(0)');
    var trlength=$("#payAdjust table tr").length
    var deleteTrs = $('#payAdjust table tr:lt('+(trlength-3)+'):gt(0)');
    $.each( deleteTrs, function() {
        if($(this).find("#adjustid").length > 0){
            $(this).remove();
        }
      });
    var adjustTimes=0;
    var adjustTypeData=liger.get("adjustType").options.data;
    for ( var i in adjustMents) {
        var obj = new Object();
        obj.value = Math.abs(adjustMents[i].adjustmentAmount);
        /*if ('EB' == adjustMents[i].adjustmentType) {
            $('#exchangeBalanceUse').val(obj.value);
            obj.id = "exchangeBalanceUse";
            $("#exchangeBalanceUseid").val(adjustMents[i].salesAdjustmentId);
            f_payMentAdjust(obj);
        }
        if ('RB' == adjustMents[i].adjustmentType) {
            $('#remainingBalanceUse').val(obj.value);
            $("#remainingBalanceUseid").val(adjustMents[i].salesAdjustmentId);
            obj.id = "remainingBalanceUse";
            f_payMentAdjust(obj);
        }*/
        if ('AD' == adjustMents[i].adjustmentType) {
            adjustTimes++;
            obj.id = "adjust";
            if(adjustTimes>1){
                var ntr = tr.clone();
                ntr.find("#adjustType_val").attr('id',"adjustType"+adjustTimes+"_val");
                ntr.find("#adjustType").attr('id',"adjustType"+adjustTimes);
                if(Number(adjustMents[i].adjustmentAmount)>0){
                    ntr.find("#adjustType"+adjustTimes+"_val").val("add");
                    for(var j in adjustTypeData){
                        if(adjustTypeData[j].value=="add"){
                            ntr.find("#adjustType"+adjustTimes).val(adjustTypeData[j].meaning);
                            ntr.find("#adjustText").html(/*"+"+*/obj.value);
                            break;
                        }
                    }
                }else{
                    ntr.find("#adjustType"+adjustTimes+"_val").val("subs");
                    for(var j in adjustTypeData){
                        if(adjustTypeData[j].value=="subs"){
                            ntr.find("#adjustType"+adjustTimes).val(adjustTypeData[j].meaning);
                            ntr.find("#adjustText").html("-"+obj.value);
                            break;
                        }
                    }
                }
                
                ntr.find("#adjust").val(obj.value);
                ntr.find("#remarks").val(adjustMents[i].remark);
                ntr.find("#adjustid").val(adjustMents[i].salesAdjustmentId);
                ntr.find('.addArtificial').hide();
                ntr.find('.subArtificial').show();
                tr.after(ntr);
            }else{
                if(Number(adjustMents[i].adjustmentAmount)>0){
                    tr.find("#adjustType_val").val("add");
                    for(var j in adjustTypeData){
                        if(adjustTypeData[j].value=="add"){
                            tr.find("#adjustType").val(adjustTypeData[j].meaning);
                            tr.find("#adjustText").html(/*"+"+*/obj.value);
                            break;
                        }
                    }
                }else{
                    tr.find("#adjustType_val").val("subs");
                    for(var j in adjustTypeData){
                        if(adjustTypeData[j].value=="subs"){
                            tr.find("#adjustType").val(adjustTypeData[j].meaning);
                            tr.find("#adjustText").html("-"+obj.value);
                            break;
                        }
                    }
                }
                tr.find("#adjust").val(obj.value);
                tr.find("#remarks").val(adjustMents[i].remark);
                tr.find("#adjustid").val(adjustMents[i].salesAdjustmentId);
            }
            f_payMentAdjust(obj);
        }
    }
}


/**
 * 设置所属市场以及货币
 */
function f_setMarketAndCurrency(market, currency) {
    if(market){
        liger.get('salesMarketId').setValue(market.marketId);
        liger.get('salesMarketId').setText(market.name);
    }

}

/**
 * 订单行价格获取
 */
function f_getPriceType(){
    var orderType;
    if(liger.get('orderType')){
        orderType=liger.get('orderType').getValue();
    }else{
        orderType='STDP';
    }
    var pricetype;
    if(orderType=='DIRP'||orderType=='STFP'){
        pricetype='STU';
    }else if(orderType=='STDP'||orderType=='NMDP'){
        pricetype='DIS';
    }else if(orderType=='CONP'){
        pricetype='RE';
    }else if(orderType=='CONPT'){
        pricetype='DIS';
    }else if(orderType=='BIGF'){
        pricetype='0';
    }else{
        pricetype='RP'
    }
    return pricetype;
}

/**
 * 获取支付调整数据
 */
function f_getAdjustMents() {
    var adjustMents = new Array();
    /*if ($('#exchangeBalanceUse').val()) {
        adjustMents.push({
            adjustmentType : 'EB',
            adjustmentAmount : -$('#exchangeBalanceUse').val(),
            headerId:$("#headerId").val(),
            salesAdjustmentId : $('#exchangeBalanceUseid').val()
        });
    }
    if ($('#remainingBalanceUse').val()) {
        adjustMents.push({
            adjustmentType : 'RB',
            adjustmentAmount : -$('#remainingBalanceUse').val(),
            headerId:$("#headerId").val(),
            salesAdjustmentId : $('#remainingBalanceUseid').val()
        });
    }*/
    if ($('#extra').val()) {
        adjustMents.push({
            adjustmentType : 'EX',
            adjustmentAmount : $('#extra').val(),
            headerId:$("#headerId").val(),
            salesAdjustmentId : $('#extraid').val()
        });
    }
    var trs = $('#payAdjust table tr:gt(1)');
    $.each( trs, function() {
        if($(this).find("#adjust").val()){
            var amount=$(this).find("#adjust").val();
            if($(this).find("input[type='hidden']:eq(0)").val()=='subs'){
                amount=-Number($(this).find("#adjust").val());
            }
            adjustMents.push({
                adjustmentType : 'AD',
                adjustmentAmount : amount,
                headerId:$("#headerId").val(),
                remark:  $(this).find("#remarks").val(),
                salesAdjustmentId: $(this).find("#adjustid").val()
            });
        }
      });
    return adjustMents;
}

/**
 * 获取配送地址数据
 */
function f_getLogistics(){
    if(!liger.get('deliveryCompany').getValue()){
        return null;
    }
    var logistics=new Object();
    logistics.logisticsId=$("#logisticsId").val();
    logistics.headerId=$("#headerId").val();
    logistics.shippingTierId=liger.get('deliveryCompany').getValue();
    logistics.shippingFee=$('#shippingTier').text();
    logistics.salesOrgId=liger.get('salesOrgId').getValue();
    logistics.autoshipFlag='N';
    if(liger.get("isCod")&&liger.get("isCod").getValue()){
        logistics.codFlag='Y';
    }else{
        logistics.codFlag='N';
    }
    return logistics;
}

/**
 * 根据配送地址查询承运商
 */
function f_queryShippingTier(address){
    var param={
            currency:liger.get('currency').getValue()
    };
    if(liger.get("memberId").getValue()){
        param.countryCode=address.spmLocation.countryCode;
        param.cityCode=address.spmLocation.cityCode;
        param.stateCode=address.spmLocation.stateCode;
    }else{
        param.countryCode=address.countryCode;
        param.cityCode=address.cityCode;
        param.stateCode=address.stateCode;
    }
    $.post(_basePath+"/dm/shippingTierDtl/queryByLocation",param,function(data) {
        liger.get('deliveryCompany').set({data:data.rows});
        var lowPrice={};
        lowPrice.price=Number.MAX_VALUE;
        for(var i in data.rows){
           var shippingTier=data.rows[i];
           for(var j in shippingTier.shippingTierDtls){
               var shippingTierDtl=shippingTier.shippingTierDtls[j];
               if(summary.currency>shippingTierDtl.invAmountFrom&&(!shippingTierDtl.invAmountTo||summary.currency<=shippingTierDtl.invAmountTo)){
                   if(lowPrice.price>shippingTierDtl.shippingFee){
                       lowPrice.price=shippingTierDtl.shippingFee;
                       lowPrice.name=shippingTier.shippingTierName;
                       lowPrice.value=shippingTier.shippingTierId;
                   }
               }
           }
        }
        if(lowPrice.price==Number.MAX_VALUE){
            lowPrice.price=0;
        }
        summary.shippingTier=lowPrice.price;
        liger.get('deliveryCompany').setValue(lowPrice.value);
        liger.get('deliveryCompany').setText(lowPrice.name);
        $('#shippingTier').text(lowPrice.price)
        return data.rows;
    })
}


/**
 * 配送地址及运费设置
 * @param memSites 会员地址信息
 * @param logistics 配送地址信息
 */
function f_setDelivery(memSites,logistics){
    if(!memSites){
        return;
    }
    var addressData={};
    var billAddress=new Array();
    var shipAddress=new Array();
    for(var i in memSites){
        if(memSites[i].siteType=="BILL"){
            billAddress.push(memSites[i]);
        }else if(memSites[i].siteType=="SHIP"){
            shipAddress.push(memSites[i]);
        }
    }
    addressData.rows=billAddress;
    addressgrid.set({data:addressData});
    addressData.rows=shipAddress;
    deliveryaddress.set({data:addressData});
    if(logistics){
//        f_queryShippingTier(deliveryaddress.getData()[0]);
        var datas=liger.get("deliveryCompany").data;
        $("#shippingTier").text(Number(logistics.shippingFee).toFixed(summary.precision));
        $("#logisticsId").val(logistics.logisticsId);
        summary.shippingTier=logistics.shippingFee;
        if(logistics.taxAmount){
			$("#shippingTierTax").text(Number(logistics.taxAmount).toFixed(summary.precision));
		}else{
			$("#shippingTierTax").parent().hide();
		}
        liger.get("deliveryCompany").setValue(logistics.shippingTierId);
        liger.get("deliveryCompany").setText(logistics.shippingTierName);
    }
}


/**
 * 数据提交前验证
 */
function valideBefore(){
	 var orderType=liger.get("orderType").getValue();
	    if(!liger.get("orderType").getValue()||!liger.get("channel").getValue()||!liger.get("salesOrgId").getValue()){
	        $.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesorder.miss'))
	        return false;
	    }
	    if(liger.get("deliveryType").getValue()=="SHIPP"){
	        if(!deliveryaddress.getSelectedRow()){
	            $.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesorder.delivery_not_null'));
	            return false;
	        }
	        if(!liger.get("deliveryCompany").getValue()){
	            $.ligerDialog.error($l('msg.error.order.deliverycompany_null'));
	            return false;
	        }
	    }
	    if(liger.get("channel")=="SRVC"&&!liger.get("serviceCenterId")){
	        $.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesorder.servicecenter_not_null'));
	        return false;
	    }
	    var lineDatas=linegrid.getData();
	    for(var i in lineDatas){
	        if(!lineDatas[i].itemId){
	            $.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesline.item_not_null'));
	            return false;
	        }
	    }
	    
	    if(orderType=="DIRP"||orderType=="STFP"||orderType=="CONP"||orderType=="CONPT"){
	        return true;
	    }else{
	        if(!liger.get("memberId").getValue()){
	            $.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesorder.not_member'));
	            return false;
	        }
	        return true;
	    }
}


/**
 * 清除会员信息.
 */
function cleanMember(){
    liger.get("memberId").setValue("");
    liger.get("memberId").setText("");
    liger.get("cnName").setValue("");
    liger.get("enName").setValue("");
    liger.get("exchangeBalance").setValue("");
    liger.get("remainingBalance").setValue("");
    liger.get("currentPoints").setValue("");
    // liger.get("currentPV").setValue("");
    if(liger.get("deliveryCompany")){
        liger.get("deliveryCompany").setValue("");
        liger.get("deliveryCompany").setText("");
        $("#shippingTier").html("");
    }
    var data={};
    data.rows=[];
    if(addressgrid){
        addressgrid.set({data:data});
    }
    if(deliveryaddress){
        deliveryaddress.set({data:data});
    }
}

/**
 * 重置订单行
 * @param isCopy 是否复制
 * @param isChangePrice 是否更改价格
 * @param isChangeInvOrg 是否更改库存组织
 */
function reSetLine(isCopy,isChangePrice,isChangeInvOrg){
    if (!linegrid) {
        return;
    }
    var lineDatas = linegrid.getData();
    if (isCopy) {
        for ( var i in lineDatas) {
            lineDatas[i].lineId = "";
            lineDatas[i].headerId = "";
            lineDatas[i].unitOrigPrice = "";
            lineDatas[i].unitDiscountPrice = "";
            lineDatas[i].unitSellingPrice = "";
            lineDatas[i].amount = "";
            lineDatas[i].unitRedeemPoint = "";
            lineDatas[i].redeemPoint = "";
        }
        var newData = {};
        newData.rows = lineDatas;
        linegrid.set({
            data : newData
        });
        f_calculateLinePrice();
        return;
    }
    if(isChangeInvOrg){
        if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()=="PCKUP"){
            for ( var i in lineDatas) {
                if(!lineDatas[i].defaultInvOrgId){
                    lineDatas[i].defaultInvOrgId = defaultInvOrg;
                }
            }
        }else{
            for ( var i in lineDatas) {
                lineDatas[i].defaultInvOrgId = "";
            }
        }
        var newData = {};
        newData.rows = lineDatas;
        linegrid.set({
            data : newData
        });
        return;
    }
    var pricetype = f_getPriceType();
    var orderType = liger.get("orderType");
    if (isChangePrice) {
        var pricetype = f_getPriceType();
        var orderType = liger.get("orderType");
        for ( var i in lineDatas) {
            var lineData = lineDatas[i];
            for ( var j in lineData.priceDetail) {
                if (lineData.priceDetail[j].priceType == 'PV') {
                    if (orderType && orderType.getValue() == "STDP"
                            && newData.itemSalesType == "PURC") {
                        lineData.pv = lineData.priceDetail[j].unitPrice;
                    } else if (!orderType) {
                        lineData.pv = lineData.priceDetail[j].unitPrice;
                    } else {
                        lineData.pv = 0;
                    }

                } else if (lineData.priceDetail[j].priceType == pricetype) {
                    if (pricetype == 'RP') {
                        lineData.unitRedeemPoint = lineData.priceDetail[j].unitPrice;
                    } else {
                        lineData.unitOrigPrice = lineData.priceDetail[j].unitPrice;
                    }
                }
            }
            if (!lineData.pv) {
                lineData.pv = 0;
            }
            if (!lineData.unitOrigPrice) {
                lineData.unitOrigPrice = 0;
            }
            if (!lineData.unitDiscountPrice) {
                lineData.unitDiscountPrice = 0;
            }
            if (!lineData.unitRedeemPoint) {
                lineData.unitRedeemPoint = 0;
            }
            lineData.favorableSum = lineData.quantity
                    * lineData.unitDiscountPrice;
            lineData.redeemPoint = lineData.unitRedeemPoint * lineData.quantity;
            lineData.amount = lineData.unitOrigPrice * lineData.quantity
                    - lineData.favorableSum;
            lineData.PVSum = lineData.quantity * lineData.pv;
            lineData.tax = summary.taxRate * lineData.unitOrigPrice;
        }
        f_calculateLinePrice();
        return;
    }
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
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemid'),
            name : 'itemNumber',
            type : 'text'
        }, {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.itemname'),
            name : 'itemName',
            type : 'text'
        }, {
            display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.quantity'),
            name : 'quantity'
        }],
        isScroll : false,
        showToggleColBtn : false,
        width : 500,
        url : _basePath+"/inv/item/queryHierarchyForOrderDetail?lineId="+row.lineId,
        showTitle : false,
        usePager : false,
        onAfterShowData : callback,
        frozen : false
    });
}

/**
 * 修改会员隐藏样式
 * 
 * @param isShow
 *            是否显示
 */
function f_changeMemberShow(isShow) {
    if(isShow){
        if($(".l-group:eq(1) div").hasClass("togglebtn-down")){
            $(".l-group:eq(1) div").click();
        }
        $(".l-group:eq(1)").show();
    }else{
        liger.get("memberId").clear();
        if(!$(".l-group:eq(1) div").hasClass("togglebtn-down")){
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
 * 设置默认的订单地址.
 * @param sites 订单地址集合
 */
function f_setDefaultAddress(sites){
    if(!sites){
        return;
    }
    for(var i in sites){
        if(sites[i].siteType=="BILL"){
            $("#billSiteId").val(sites[i].salesSiteId);
            sites[i].spmLocation={};
            sites[i].spmLocation.zipCode=sites[i].zipCode;
            sites[i].defaultFlag="Y";
            var data={};
            data.rows=[];
            data.rows.push(sites[i]);
            addressgrid.set({data:data});
           /* var datas=addressgrid.getData();
            var isSelect=false;
            for(var j in datas){
                if(datas[j].address==sites[i].address&&datas[j].phone==sites[i].phone&&datas[j].name==sites[i].name){
                    isSelect=true;
                    addressgrid.select(datas[j]);
                }
            }
            if(!isSelect){
                sites[i].spmLocation={};
                sites[i].spmLocation.zipCode=sites[i].zipCode;
                sites[i].defaultFlag="Y";
                addressgrid.addRow(sites[i]);
            }*/
        }else if(sites[i].siteType=="SHIP"){
            $("#deliverySiteId").val(sites[i].salesSiteId);
            liger.get("deliveryType").setValue("SHIPP");
            sites[i].spmLocation={};
            sites[i].spmLocation.zipCode=sites[i].zipCode;
            sites[i].defaultFlag="Y";
            var data={};
            data.rows=[];
            data.rows.push(sites[i]);
            deliveryaddress.set({data:data});
            /*var datas=deliveryaddress.getData();
            var isSelect=false;
            for(var j in datas){
                if(datas[j].address==sites[i].address&&datas[j].phone==sites[i].phone&&datas[j].name==sites[i].name){
                    isSelect=true;
                    deliveryaddress.select(datas[j]);
                }
            }
            if(!isSelect){
                sites[i].spmLocation={};
                sites[i].spmLocation.zipCode=sites[i].zipCode;
                sites[i].defaultFlag="Y";
                deliveryaddress.addRow(sites[i]);
                
            }*/
        }
    }
}

function toPaymentPage(headerId) {
    var tabId=window.top.tab.getSelectedTabItemID();
    window.top.f_removeTab(tabId);
    window.top.f_addTab('ORDER_CREATE', $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.orderpayment'),
            _basePath +"/om/om_order_payment.html?orderId="+headerId);
}


function initUpdatePeriod(){
	if(liger.get("orderStatus").getValue()!='WPAY'){
		return ;
	}
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
			        		            			location.reload();  
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


//D003 2017-07-06 索取电子发票方式初始化  BEGIN
function initEinvoice(){
	liger.get('paperInvoice').setDisabled(true);
	liger.get('recordedInVehicle').setDisabled(true);
	liger.get('phoneCode').setDisabled(true);
	liger.get("phoneCodeText").set({
		disabled : true
	});
	liger.get("phoneCodeText").set({
		disabled : true
	});
	liger.get('naturalPersonCode').setDisabled(true);
	liger.get("naturalPersonCodeText").set({
		disabled : true
	});
	liger.get("donateCode").set({
		disabled : true
	});
    liger.get('donatedInvoice').setDisabled(true);	
}
//D003 END

//lkkhpg_tw_einvoices 2017-08-01 BEGIN
//电子发票列印功能管控
var printEinvoiceWin;
function f_printEinvoice(headerId, getInvoiceMethod, invoiceNumber){
	if(getInvoiceMethod == null || getInvoiceMethod == ''){
		getInvoiceMethod = 1;
	}
	if(!printEinvoiceWin){
		initPrintEinvoiceForm();
		printEinvoiceWin = $.ligerDialog.open({
			title : $l('msg.info.order.printinvoice'),
			target: $("#om_print_einvoice_form"),
			height:160,
            width: 360,
            showClose:true,
            buttons: [
            {
                text: $l('msg.info.stockio.printlist'),
                onclick: function(item, dialog) {
                	/*if(summary.currency == 0){
                		Hap.showError($l('msg.error.order.einvoices_are_not_allowed_for_zero_amount_orders'));
                	}*/
                	var obj = om_print_einvoice_form.getData();
                	if(obj.reprint == false || (obj.reprint == true && reprintValidate(headerId))){
                		printEinvoice(headerId);
                	}
                }
            },
            {
                text: $l('sys.hand.btn.cancel'),
                onclick: function(item, dialog) {
                	dialog.hide();
                }
            }]
		});
	}
	//零元不开票,只能打交易明细.
	if(!invoiceNumber){
		om_print_einvoice_form.setData({
			certificate:false,
			reprint:false,
			detail:true
		});
		$.ligerui.get("certificate").set("disabled",true);
		$.ligerui.get("reprint").set("disabled",true);
		liger.get("detail").set({
			readonly : true
		});
	}else{
		if(memberType == 'COMP'){ //法人
			om_print_einvoice_form.setData({
				certificate:true,
				reprint:false,
				detail:true
			});
			liger.get("certificate").set({
				readonly : true
			});
			liger.get("detail").set({
				readonly : true
			});
		}else{ //自然人
			if(getInvoiceMethod == 1){
				om_print_einvoice_form.setData({
					certificate:true,
					reprint:false,
					detail:false
				});
			}else{
				om_print_einvoice_form.setData({
					certificate:false,
					reprint:false,
					detail:true
				});
				$.ligerui.get("certificate").set("disabled",true);
				$.ligerui.get("reprint").set("disabled",true);
				liger.get("detail").set({
					readonly : true
				});
			}
		}
	}
	
	printEinvoiceWin.show();
}

//列印电子发票表单
function initPrintEinvoiceForm(){
	window['om_print_einvoice_form'] = $("#om_print_einvoice_form").ligerForm({
        fields : [{
            display : $l('msg.info.order.einvoice_certificate'),
            name : "certificate",
            type : 'checkbox',
            width: 30,
            labelWidth: 90,
            labelAlign:'right',
            newline : false,
            options:{
                onClick : function(){
                	var certificate = $.ligerui.get("certificate").getValue();
                	if(certificate){
                		$.ligerui.get("reprint").set("disabled",false);
                	}else{
                		$.ligerui.get("reprint").setValue(false);
                		$.ligerui.get("reprint").set("disabled",true);
                	}
                }
            }
        },{
            display : $l('msg.info.order.einvoice_reprint'),
            name : "reprint",
            type : "checkbox",
            width: 30,
            labelWidth: 60,
            labelAlign:'right',
            newline : false
        },{
            display : $l('msg.info.order.einvoice_detail'),
            name : "detail",
            type : 'checkbox',
            width: 30,
            labelWidth: 90,
            labelAlign:'right',
            newline : true
        }]
    });
}

//电子发票重印权限验证
function reprintValidate(headerId){
	//验证当前用户是否有权限
	if(reprintFlag){
		return true;
	}else{
		//若当前用户没有权限,要求输入有权限的用户名密码
		var reprintValidateWin;
		if(!reprintValidateWin){
			initReprintValidateForm();
			reprintValidateWin = $.ligerDialog.open({
				title : $l('msg.info.order.enter_username_and_password_with_permission'),
				target : $("#om_reprint_validate_from"),
				height:160,
		        width: 360,
		        async: false,
		        showClose: true,
		        buttons: [{
		        	text: $l('sys.hand.btn.ok'),
		        	onclick: function (item, dialog) {
		        		var data = om_reprint_validate_from.getData();
		        		var url = 'om/om_order_detail';
		        		var accessCode = 'REPRINT_INVOICE';
		        		var loginName = data.loginName;
		        		var password = data.password;
		        		if(Hap.validateForm(om_reprint_validate_from)){
		        			$.ajax({
	                      		url : _basePath+"/om/reprint_valid?url="+url+"&accessCode="+accessCode+"&loginName="+loginName+"&password="+password,
	                      		type : "POST",
	                      		dataType : "json",
	                      		contentType : "application/json",
	                      		success : function(json) {
	                      			if(json && json.success){
	        		        			printEinvoice(headerId);
	        		        			dialog.hide();
	        		        		}
	                      		}
	                      	});
		        		}
		        	}
		        },{
		        	text: $l('sys.hand.btn.cancel'), 
		        	onclick: function (item, dialog) {
		        		dialog.hide();
		        	}
		        }]
		    });
		}
		reprintValidateWin.show();
	}
}

//重印权限验证表单
function initReprintValidateForm(){
	window['om_reprint_validate_from'] = $("#om_reprint_validate_from").ligerForm({
        fields : [ {
            display : $l('msg.info.order.username'),
            name : "loginName",
            type : 'text',
            width: 160,
            align: 'center',
            labelWidth: 80,
            labelAlign:'right',
            validate: {
        		required : true
        	},
            newline : false
        },{
            display : $l('msg.info.order.password'),
            name : "password",
            type : "password",
            width: 160,
            align: 'center',
            labelWidth: 80,
            labelAlign:'right',
            validate: {
        		required : true
        	},
            newline : true
        }]
    });
}

//动态选择模板打印电子发票
function printEinvoice(headerId) {
	var obj = om_print_einvoice_form.getData();
	var reportProgramCode;
	if(obj.certificate == true && obj.detail == true){
		// 打印证明联及交易明细
		reportProgramCode = "RPT-00019-2";
	}else if(obj.certificate == true && obj.detail == false){
		// 打印证明联
		reportProgramCode = "Einvoice-Certificate";
	}else if(obj.certificate == false && obj.detail == true){
		// 打印交易明细
		reportProgramCode = "RPT-00019-1";
	}else{
		// 证明联和交易明细至少选择一个
		Hap.showError($l('msg.error.order.print_content_cannot_be_empty'));
	}
	if(reportProgramCode){
		window.open(_basePath+"/report/run?docType=PDF&reportProgramCode="+reportProgramCode+"&headerId="+headerId+"&reprint="+obj.reprint);
	}
}
//lkkhpg_tw_einvoices 2017-08-01 END