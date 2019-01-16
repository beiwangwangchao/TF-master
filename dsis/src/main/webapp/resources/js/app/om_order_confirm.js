/**
 * @summary DSIS
 * @description 订单新建页面js方法集合
 * @version 1.0
 * @author wuyichu
 * @author linxixin
 * @author zhangchuangsheng
 * @copyright .
 *
 */

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
 * @param grid
 */
function clearGrid(grid) {
    grid = liger.get(grid);
    if(grid.currentData != null && grid.currentData.length > 0) {
        var rows = grid.currentData.rows;
        rows.splice(0, rows.length);
        grid.reRender();
    }
}

/**
 * 复制模板
 */
function f_copy() {
    $("#copyModelDialog").parent().hide();
    clearForm("om_oc_form");
    clearGrid("linegrid");
    clearGrid("addressgrid");
    clearForm("delivery");
    clearForm("payAdjust");
    liger.get('copyModelDialog').trigger('buttonClick')
}
/**
 * 会员选择的pupop
 */
function o_memberPupop() {
    var options = {
        readonly : false,// 是否可编辑
        cancelable:false,
        autocomplete:true,
        placeholder:$l("msg.warn.order.om_sales_order.fullmembercode"),
        valueField : 'memberId',// 选中的valueField,文本框有值时grid有选中效果
        textField : 'memberCode',// 选中的textField,文本框显示的grid字段
        grid : {
            delayLoad:true,
            isSingleCheck:true,
            needQueryParam:true,
            columns : [
                    {
                        display : $l("type.com.lkkhpg.dsis.common.user.dto.userinfo.memberid"),
                        name : 'memberCode',
                        align : 'center',
                        isAutoComplete:true,
                        autocompleteField:true,
                        width : 200
                    },{
                        display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.phoneno"),
                        name : 'phoneNo',
                        align : 'center',
                        autocompleteField:true,
                        isAutoComplete:false
                    },{
                        display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.chinesename"),
                        name : 'chineseName',
                        isAutoComplete:false,
                        autocompleteField:true,
                        align : 'center'
                    },
                    {
                        display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.englishname"),
                        name : 'englishName',
                        isAutoComplete:false,
                        autocompleteField:true,
                        align : 'center'
                    } ],
            // 数据查询的url
            url : _basePath + '/mm/member/query',
            onLoadData:function(){
                if(liger.get('orderType')&&liger.get('orderType').getValue()!='NMDP'){
                    this.setParm("marketId",liger.get("salesMarketId").getValue());
                }else if(!liger.get('orderType')){
                    this.setParm("marketId",liger.get("salesMarketId").getValue());
                }
                this.setParm("isActive","Y");
            }

        },
        // lov上的查询条件
        condition : {
            inputWidth : 130,
            labelWidth : 70,
            fields : [
                    {
                        display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.englishname"),
                        name : "englishName",
                        isAutoComplete:false,
                        type : "text"
                    },
                    {
                        display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.chinesename"),
                        name : "chineseName",
                        isAutoComplete:false,
                        newline : false,
                        type : "text"
                    },
                    {
                        display : $l("type.com.lkkhpg.dsis.common.user.dto.userinfo.memberid"),
                        name : "memberCode",
                        isAutoComplete:true,
                        newline : true,
                        type : "text"
                    },{
                        display : $l("type.com.lkkhpg.dsis.common.member.dto.memberdetails.phoneno"),
                        name : 'phoneNo',
                        align : 'center',
                        newline : false,
                        isAutoComplete:false
                    } ]
        },
        onselect : function(data) {
            f_setMember(data.data[0]);
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
        autocomplete:true,
        cancelable:false,
        placeholder:"",
        grid : {
            columns : [ {
                display : $l('服务中心号'),
                name : 'code',
                isAutoComplete:true,
                autocompleteField:true,
                type : 'int'
            }, {
                display : $l('服务中心名称'),
                name : 'name',
                isAutoComplete:false,
                autocompleteField:true,
                align : 'left',
                width : 200
            }, {
                display : $l('负责会员'),
                name : 'chargeMemberId',
                isAutoComplete:false,
                autocompleteField:true,
                align : 'right'
            }, {
                display : $l('联系号码'),
                isAutoComplete:false,
                autocompleteField:true,
                name : 'phone',
                align : 'right'
            } ],
            // 数据查询的url
            url : _basePath + '/spm/serviceCenterAndMember/query',
            onLoadData:function(){
                if(liger.get('orderType')&&liger.get('orderType').getValue()!='NMDP'){
                    this.setParm("marketId",liger.get("salesMarketId").getValue());
                }else if(!liger.get('orderType')){
                    this.setParm("marketId",liger.get("salesMarketId").getValue());
                }
                this.setParm("status","ALED");
            }
        },
        // lov上的查询条件
        condition : {
            inputWidth : 150,
            labelWidth : 100,
            fields : [
                    {
                        display : $l("服务中心号"),
                        name : "code",
                        newline : true,
                        isAutoComplete:true,
                        type : "text"
                    },
                    {
                        display : $l("服务中心名称"),
                        name : "name",
                        newline : false,
                        isAutoComplete:false,
                        type : "text"
                    }]
        },
        onselect : function(data) {
        	 f_setMember(data.data[0].member);
            var memberId = data.data[0].member.memberId;
            var memberCode = data.data[0].member.memberCode;
            liger.get("memberId").setValue(memberId);
            liger.get("memberId").setText(memberCode);

        },
        validate : {
            required : true
        }
    }
    return options;
}

/**
 * 销售订单表单参数
 */
function o_omCreateFormOptions() {
    var saleChannelData=[];
    for(var i in channelData){
        if(channelData[i].value=='STORE'||channelData[i].value=='SRVC'||channelData[i].value=='FAX'){
            saleChannelData.push(channelData[i]);
        }
    }
    var options = {
        fields : [
                {
                    type : 'text',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.createuserid"),
                    name : 'createUserId',
                    newline : false,
                    readonly : true,
                    group: "订单信息"
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
                    readonly : true,
                    options : {
                        valueFieldID : "orderStatus",
                        valueField : "value",
                        textField : "meaning",
                        cancelable: false,
                        data : orderStatusData,
                        onselected : function(val) {
                            validatePeriod();
                        }
                    }
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
                    options : {
                        valueFieldID : "orderType",
                        valueField : "value",
                        textField : "meaning",
                        data : orderTypeData,
                        cancelable: false,
                        // 当“订单类型=总监购买/员工购买/非会员购买”时，不可用灰掉。

                        onselected : function(data) {
                            cleanMember();
                            var orderType = data;
                            var isself=true;
                            if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()=='SHIPP'){
                                isself=false;
                            }
                            if (orderType) {
                                switch (orderType) {
                                case "DIRP":
                                case "STFP":
                                case "CONP": {
                                    if(linegrid){
                                        linegrid.set({columns:o_getLineColumns(true,false,isself)});
                                    }
                                    liger.get("memberId").set({readonly:true});
                                    liger.get('memberId').setPlaceholder("   ");
                                    var str="<label class='label1'>"+$l('type.com.lkkhpg.dsis.common.order.dto.salesorder.orderamt')+":</label><label id='TotalCurrency' class='label2'>0</label>"
                                    $("#salesLineSummary tr:eq(0) td:eq(2)").html(str);
                                    break;
                                }
                                case "RDEM":{
                                    if(linegrid){
                                        linegrid.set({columns:o_getLineColumns(true,true,isself)});
                                    }
                                    liger.get("memberId").setEnabled();
                                    liger.get('memberId').set({readonly:false});
                                    liger.get('memberId').set({validate:{required:true}});
                                    liger.get('memberId').setPlaceholder($l("msg.warn.order.om_sales_order.fullmembercode"));
                                    var str="<label class='label1'>"+$l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salespointsummary")+":</label><label id='TotalCurrency' class='label2'>0</label>"
                                    $("#salesLineSummary tr:eq(0) td:eq(2)").html(str);
                                    break;
                                }
                                default:
                                    liger.get("memberId").setEnabled();
                                    liger.get('memberId').set({readonly:false});
                                    liger.get('memberId').set({validate:{required:true}});
                                    liger.get('memberId').setPlaceholder($l("msg.warn.order.om_sales_order.fullmembercode"));
                                    if(linegrid){
                                        linegrid.set({columns:o_getLineColumns(true,false,isself)});
                                    }
                                    var str="<label class='label1'>"+$l('type.com.lkkhpg.dsis.common.order.dto.salesorder.orderamt')+":</label><label id='TotalCurrency' class='label2'>0</label>"
                                    $("#salesLineSummary tr:eq(0) td:eq(2)").html(str);
                                    break;

                                }
                            }
                            reSetLine(false,true,false);
                        }
                    },
                    validate:{required:true}
                },
                {
                    type : 'select',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.channel"),
                    name : 'channel',
                    newline : false,
                    options : {
                        valueFieldID : "channel",
                        valueField : "value",
                        textField : "meaning",
                        cancelable: false,
                        data : saleChannelData,
                        value : saleChannelData[0].value,
                        text : saleChannelData[0].meaning,
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
                    },
                    validate:{required:true},
                    render:function(data){
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
                            return confirm($l('msg.warning.order.payment_date_adjustment') + newvalue);
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
                    cancelable: false,
                    options : o_memberPupop(),
                    validate:{required:true},
                    group: "会员信息"
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
                    cancelable: false,
                    readonly : false,
                    type : 'popup',
                    options : o_serviceCenterPupop(),
                    validate:{required:true}
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
                    type : 'textarea',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remark"),
                    name : 'remark',
                    newline : false,
                    width : 400,
                    group: "备注信息"
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
                    name : 'sourceType'
                } ]
    };
    return options;
}

/**
 * autoship订单头表单
 */
function o_autoshipCreateFormOptions() {
    var execuDate=[];
    for(var i=0;i<28;i++){
        execuDate.push({
            value:i+1,
            meaning:i+1
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
                    group: "订单信息"
                },
                {
                    type : 'select',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.autoshiporder.autoshiporderstatus"),
                    name : 'autoshipStatus',
                    width : 150,
                    labelWidth : 120,
                    newline : false,
                    cancelable: false,
                    readonly : true,
                    render:function(data){
                        for(var i in autoShipStatusData){
                            if(autoShipStatusData[i].value==data){
                                return autoShipStatusData[i].meaning;
                            }
                        }
                    }
                },{
                    type : 'text',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.createuserid"),
                    name : 'createUserId',
                    newline : false,
                    readonly : true,
                    validate:{required:true}
                },
                {
                    type : 'select',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salesmarket"),
                    name : 'salesMarketId',
                    newline : false,
                    readonly : true,
                    validate:{required:true}
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
                    options : {
                        valueFieldID : "executeType",
                        valueField : "value",
                        textField : "meaning",
                        cancelable: false,
                        data : executeTypeData,
                        value:executeTypeData[0].value,
                        text:executeTypeData[0].meaning,
                        readonly : true,
                        render:function(value){
                            if(!value){
                                return;
                            }
                            for(var i in executeTypeData){
                                if(executeTypeData[i].value==value){
                                    return executeTypeData[i].meaning
                                }
                            }

                        }
                    }
                },
                {
                    type : 'select',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.salesorgid"),
                    name : 'salesOrgId',
                    newline : false,
                    cancelable: false,
                    readonly : true,
                    validate:{required:true}
                },
                {
                    type : 'text',
                    display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.currency"),
                    name : 'currency',
                    newline : false,
                    readonly : true,
                    validate:{required:true}
                },
                {
                    display : $l("type.com.lkkhpg.dsis.common.user.dto.userinfo.memberid"),
                    name : 'memberId',
                    type : "popup",
                    newline : true,
                    options : o_memberPupop(),
                    validate:{required:true},
                    group: "会员信息"
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
                    cancelable: false,
                    readonly : true,
                    format: "yyyy/dd/MM",
                    name : 'autoshipCreateDate',
                    validate:{required:true}
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
                    width : 500 ,
                    group: "备注信息"
                },{
                    type : 'hidden',
                    name : 'creditCardId',
                    newline : false,
                    readonly : true
                },{
                    type : 'hidden',
                    name : 'autoshipId',
                    newline : false,
                    readonly : true
                },
                {
                    type : 'hidden',
                    name : 'logisticsId'
                }  ]
    };
    return options;
}


/**
 * 订单行货号选择pupop
 */
function o_itemPupop() {
    var options = {
        type : 'popup',
        cancelable:false,
        autocomplete:true,
        valueField : 'itemNumber',// 选中的valueField,文本框有值时grid有选中效果
        textField : 'itemNumber',// 选中的textField,文本框显示的grid字段
        grid : {
            isSingleCheck:true,
            columns : [ {
                display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.itemid"),
                name : 'itemNumber',
                isAutoComplete:true,
                autocompleteField:true,
                align : 'left',
                width : 200
            }, {
                display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.itemname"),
                name : 'itemName',
                isAutoComplete:false,
                autocompleteField:true,
                align : 'right',
                type : 'text'
            }, {
                display : $l("type.com.lkkhpg.dsis.common.product.invitem.dto.description"),
                name : 'description',
                isAutoComplete:false,
                autocompleteField:true,
                align : 'right',
                type : 'text'
            } ],
            // 数据查询的url
            url : _basePath + '/inv/item/queryForOrder',
            onLoadData:function(){
                if(liger.get("channel")){
                    this.setParm("isActive","Y");
                    var channerlValue=liger.get("channel").getValue();
                    if(channerlValue=='FAX'){
                        this.setParm("channel",'FAX');
                    }else if(channerlValue=='DISWB'){
                        this.setParm("channel",'WEB');
                    }else if(channerlValue=='DISAP'){
                        this.setParm("channel",'APP');
                    }else if(channerlValue=='AUTOS'){
                        this.setParm("channel",'AUTOS');
                    }else{
                        this.setParm("channel",'STORE');
                    }
                }else{
                    this.setParm("channel",'AUTOS');
                }
                var pricetype=f_getPriceType();
                if(pricetype!='0'){
                    this.setParm("priceType",pricetype);
                    this.setParm("currency",liger.get("currency").getValue());
                }
                if(pricetype=='RP'){
                    this.setParm("redeemFlag","Y");

                }
                if(memberStatus&&memberRole&&memberStatus=='NEW'&&memberRole=="DIS"){
                    this.setParm("starterAid",'Y');
                }
                this.setParm("salesOrgId",liger.get("salesOrgId").getValue());

// if(liger.get("deliveryType").getValue()=="PCKUP"){
// var data=linegrid.getSelected();
// this.setParm("organizationId",data.defaultInvOrgId);
// }
            }
        },
        // lov上的查询条件
        condition : {
            inputWidth : 150,
            labelWidth : 70,
            fields : [ {
                display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.itemid"),
                name : "itemNumber",
                isAutoComplete:true,
                newline : true,
                type : "text"
            }, {
                display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.itemname"),
                name : "itemName",
                isAutoComplete:false,
                newline : false,
                type : "text"
            } ]
        },
        onselect : function(data) {
            var selected = linegrid.getSelected();
            if (!selected) {
                return;
            }
            f_setLine(selected, data.data[0]);
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
function o_getLineColumns(istax,ispoint,isself,isauto) {
    defaultItemSalesType="PURC";
    var unit={
            display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.unitsellingprice"),
            name : 'unitOrigPrice',
            align : 'center',
            type : 'float',
            render:function(data){
                if(!data||!data.itemId){
                    return;
                }
                if(!data.unitOrigPrice){
                    data.unitOrigPrice=0;
                    return "0";
                }
                if(data.itemSalesType&&data.itemSalesType=="FREE"){
                    data.unitOrigPrice=0;
                    return "0";
                }
                data.tax=data.unitOrigPrice*summary.taxRate;
                return Number(data.unitOrigPrice).toFixed(2);
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
                if(!data.unitOrigPrice){
                    data.unitOrigPrice=0;
                }
                if(data.itemSalesType&&data.itemSalesType=="FREE"){
                    data.unitOrigPrice=0;
                }
                return Number(data.unitOrigPrice*data.quantity).toFixed(2);
            }
        };
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
    if(!isauto){
        var salesTypeData=new Array();
        var includeTye=new Array();
        var orderType=liger.get("orderType").getValue();
        switch(orderType){
            case "STDP":{
                includeTye.push("PURC");
                includeTye.push("EXCH");
                includeTye.push("FREE");
                defaultItemSalesType="PURC";
                break;
            }
            case "BIGF": {
                includeTye.push("GIFT");
                defaultItemSalesType="GIFT";
                break;
            }
            case "RDEM":{
                includeTye.push("REDE");
                defaultItemSalesType="REDE";
                break;
            }
            default:{
                includeTye.push("PURC");
                defaultItemSalesType="PURC";
                break;
            }
        }
        for(var i in productSalesTypeData){
            if($.inArray(productSalesTypeData[i].value,includeTye)>=0){
                salesTypeData.push(productSalesTypeData[i]);
            }
        }
        saleType.editor = {
            type : 'select',
            valueField : "value",
            textField : "meaning",
            cancelable:false,
            data : salesTypeData,
            value : salesTypeData[0].value,
            text : salesTypeData[0].meaning,
            onChanged : function(e) {
                if(orderType=="STDP"&&e.record.itemSalesType=="PURC"){
                    if(e.record.pv){
                        return;
                    }
                    var details=e.record.priceDetail;
                    if(!details){
                        var param = {'itemId' : e.record.itemId,'currency':liger.get("currency").getValue(),uomCode:e.record.uomCode,salesOrgId:defaultSaleOrg};
                        $.getJSON( _basePath + "/inv/price/queryPriceByItemId?",param,function(data) {
                            details=data.rows;
                            e.record.priceDetail=data.rows;
                            for(var i in details){
                                if(details[i].priceType == 'PV'){
                                    e.record.pv=details[i].unitPrice;
                                }
                            }
                        });
                    }else{
                        for(var i in details){
                            if(details[i].priceType == 'PV'){
                                e.record.pv=details[i].unitPrice;
                            }
                        }
                    }
                }else if(e.record.itemSalesType=="FREE"){
                    e.record.unitOrigPrice=0;
                }
                f_setLinePrice(e.record);
            }
        }
    }
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
                    return Number(data.unitRedeemPoint*data.quantity).toFixed(2);
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
                validate:{
                    required:true
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
                render:function(data){
                    if(!data||!data.itemId){
                        return;
                    }
                    if(!data.pv){
                        data.pv=0;
                        return;
                    }

                    var orderType="STDP";
                    if(liger.get("orderType")){
                        orderType=liger.get("orderType").getValue();
                    }
                    if(orderType=="STDP"&&data.itemSalesType=="PURC"){
                        if(data.pv){
                            return data.pv;
                        }
                        var details=data.priceDetail;
                        if(!details){
                            var param = {'itemId' : data.itemId,'currency':liger.get("currency").getValue(),uomCode:data.uomCode,salesOrgId:defaultSaleOrg};
                            $.getJSON( _basePath + "/inv/price/queryPriceByItemId?",param,function(json) {
                                details=json.rows;
                                data.priceDetail=json.rows;
                                for(var i in details){
                                    if(details[i].priceType == 'PV'){
                                        data.pv=details[i].unitPrice;
                                    }
                                }
                            });
                        }else{
                            for(var i in details){
                                if(details[i].priceType == 'PV'){
                                    data.pv=details[i].unitPrice;
                                }
                            }
                        }
                        if(data.pv==0){
                            return "0"
                        }
                        return Number(data.pv).toFixed(2);
                    }else{
                        data.pv=0;
                        return "0";
                    }

                }
            },unit,
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
                    if (!data||!data.itemId) {
                        return;
                    }
                }
            }, {
                display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.quantity"),
                name : 'quantity',
                align : 'center',
                type : 'int',
                editor : {
                    type : 'int',
                    onChange : function(e) {
                        if(e.value>99999){
                            e.record.quantity=99999;
                            e.value=99999;
                        }else if(e.value<1){
                            e.record.quantity=1;
                            e.value=1;
                        }
                        if(e.value>e.record.quantity){
                            if(e.record.unitRedeemPoint){
                                summary.points+=(e.record.quantity-e.value)*e.record.unitRedeemPoint;
                                if(summary.points>Number(liger.get("currentPoints").getValue())){
                                    $.ligerDialog.error($l('msg.error.order.sales_point_insufficient'));
                                    e.value=e.record.quantity;
                                }
                            }

                        }
                    },onChanged:function(e) {
                        f_setLinePrice(e.record);
                    }
                },
                validate:{required:true,digits:true,min:1,max:99999}
            }, {
                display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.discountprice'),
                name : 'favorableSum',
                align : 'center',
                type : 'float',
                render : function(data) {
                    if (!data||!data.itemId) {
                        return;
                    }
                    if(!data.favorableSum||data.favorableSum==0){
                        return "0";
                    }
                }
            }, {
                display : $l('type.com.lkkhpg.dsis.common.order.dto.salesline.pvsum'),
                name : 'PVSum',
                align : 'center',
                type : 'text',
                render : function(data) {
                    if (!data||!data.itemId) {
                        return;
                    }
                    if(!data.pv){
                        data.pv=0;
                        return;
                    }
                    if(!data.quantity){
                        data.quantity=1;
                    }
                    data.PVSum=data.pv*data.quantity;
                    if(!data.PVSum||data.PVSum==0){
                        return "0"
                    }
                    return Number(data.PVSum).toFixed(2);
                }
            },amount  ];
    if (istax) {
        var tax = {
            display : $l("type.com.lkkhpg.dsis.common.order.dto.salesline.tax"),
            name : 'tax',
            align : 'center',
            type : 'float',
            render:function(data){
                if (!data||!data.itemId) {
                    return;
                }
                data.tax=data.unitOrigPrice*summary.taxRate;
                return Number(data.tax).toFixed(2);
            }
        };
        columns.splice(5, 0, tax);
    }
    if(!isauto){
        var invOrg={
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
        if(isself){
            invOrg.editor = {
                    type : 'select',
                    valueField : "invOrgId",
                    textField : "name",
                    cancelable:false,
                    staticOptions:function(e){
                        e.column.editor.data=invOrgs;
                    }
                };
            invOrg.validate={
                required:true
            };
        }
        columns.unshift(invOrg);
    }
    return columns;
}

/**
 * 增加信用卡记录的form
 */
function o_addCreditForm(){
    var options = {
            fields : [
                     {
                        type : 'text',
                        display : $l("type.com.lkkhpg.dsis.common.order.dto.creditcard.creditcardnum"),
                        name : 'creditCardNum',
                        width : 290,
                        newline : false,
                        validate:{required:true,maxlength:16,minlength:5}
                    },{
                        type : 'select',
                        display : $l("type.com.lkkhpg.dsis.common.order.dto.creditcard.bankcode"),
                        name : 'bankCode',
                        width:120,
                        newline : true,
                        options : {
                            valueFieldID : "bankCode",
                            valueField : "value",
                            textField : "meaning",
                            cancelable: false,
                            data : bankBelongData,
                            value:bankBelongData[0].value,
                            text:bankBelongData[0].meaning
                        },
                        validate:{required:true}
                    },
                    {
                        type : 'text',
                        display : $l("type.com.lkkhpg.dsis.common.order.dto.creditcard.cardholder"),
                        name : 'cardHolder',
                        width:70,
                        newline :false,
                        validate:{required:true}
                    },{
                        type : 'text',
                        display : $l("type.com.lkkhpg.dsis.common.order.dto.creditcard.validyear"),
                        name : 'validYear',
                        width:50,
                        newline : true,
                        validate:{required:true,digits:true ,min:00,maxlength:2,max:99}
                    },{
                        type : 'text',
                        display : $l("type.com.lkkhpg.dsis.common.order.dto.creditcard.validmonth"),
                        width:50,
                        name : 'validMonth',
                        newline : false,
                        validate:{required:true,digits:true ,min:1,max:12}
                    } ],
                    validate: true,space:10
        };
        return options;
}


/**
 * 订单行表格的参数
 *
 * @param isauto
 *            是否autoship订单
 */
function o_ligerGridOptions(isauto) {
    var deleteUrl=_basePath+'/om/order/line/remove';
    if(isauto){
        deleteUrl=_basePath+'/om/autoship/deleteLine';
    }
    var options = {
        columns : o_getLineColumns(true,false,true,isauto),
        detail: { onShowDetail: f_getItemPackageDetail,height:'auto' },
        enabledEdit : true,
        usePager : false,
        width : '98%',
        toolbar : {
            items : [ {
                text : $l("sys.hand.btn.new"),
                disable : false,
                id:"addLineBtn",
                click : function() {
                    addLine(isauto);
                },
                icon : 'add'
            }, {
                line : true
            }, {
                text : $l("sys.hand.btn.delete"),
                id:"deleteLineBtn",
                click : function() {
                    Hap.gridDelete({
                        grid : linegrid,
                        url : deleteUrl,
                        success:function(json,opt){
                            f_calculateLinePrice();
                        }, failure:function(json,opt){
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
    var displayName= $l('type.com.lkkhpg.dsis.common.member.dto.membersite.receivegoodsname');
    if(dataOption){
        displayName= $l('type.com.lkkhpg.dsis.common.member.dto.membersite.receivebillsname');
    }
    var options = {
        unSetValidateAttr : false,
        usePager : false,
        checkbox: true,
        isSingleCheck:true,
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
                    type : 'text',
                    render : function(rowdata, rowindex, value) {
                        if(!value||value=="N"){
                            return "";
                        }else{
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
                        h += "<a href='javascript:beginEdit("+dataOption+ ","+ rowindex+ ")'>"+$l('sys.hand.btn.edit')+"</a> ";
                        h += "<a href='javascript:deleteRow(" +dataOption+ ","+ rowindex
                                + ")'>"+$l('sys.hand.btn.delete')+"</a> ";
                        return h;
                    }
                } ],
                isChecked:function(rowdata){
                    if(orderDetail&&orderDetail.billingLocationId&&dataOption&&orderDetail.billingLocationId==rowdata.locationId){
                        return true;
                    }else if(orderDetail&&orderDetail.deliveryLocationId&&!dataOption&&orderDetail.deliveryLocationId==rowdata.locationId){
                        return true;
                    }else if(rowdata.defaultFlag&&rowdata.defaultFlag=="Y"){
                        return true;
                    }
                    return false;
                }
    }
    if (dataOption) {
        options.toolbar = {
            items : [ {
                text : $l("sys.hand.btn.new"),
                disable : false,
                id:"addLineBtn",
                click : function() {
                    if(liger.get("memberId").getValue()){
                        om_location_edit(_basePath,{},function(d){
                            f_addAddressWithMember(d,dataOption,addressgrid);
                        });
                    }else{
                        om_location_edit(_basePath,{},function(d){
                            f_addAddressWithoutMember(d,dataOption,addressgrid);
                        });
                    }

                },
                icon : 'add'
            }, {
                line : true
            } ]
        }
    }else{
        options.toolbar = {
                items : [ {
                    text : $l("sys.hand.btn.new"),
                    disable : false,
                    id:"addLineBtn",
                    click : function() {
                        if(liger.get("memberId").getValue()){
                            om_location_edit(_basePath,{},function(d){
                                f_addAddressWithMember(d,dataOption,deliveryaddress);
                            });
                        }else{
                            om_location_edit(_basePath,{},function(d){
                                f_addAddressWithoutMember(d,dataOption,deliveryaddress);
                            });
                        }
                    },
                    icon : 'add'
                }, {
                    line : true
                } ]
            }
        options.onSelectRow=f_queryShippingTier;
    }
    return options;
}
/**
 * 订单为非会员购买时添加地址
 * @param address 地址信息
 * @param dataOption true 账单地址 /false 配送地址
 * @param grid 成功后操作的grid
 */
function f_addAddressWithoutMember(address,dataOption,grid){
    var url = _basePath+'/om/sites/submit';
    var siteCode="SHIP";
    if(dataOption){
        siteCode="BILL";
    }
    var requestData={headerId:$("#headerId").val(),salesOrgId:liger.get("salesOrgId").getValue(),siteType:siteCode,
            name:address.memberName,phone:address.phoneNumber,cityCode:address.cityCode,stateCode:address.stateCode,
            countryCode:address.countryCode,address:address.address,areaCode:address.areaCode,zipCode:address.zipCode,autoshipFlag:"N"
    };
    if(dataOption){
        requestData.salesSiteId=$("#billSiteId").val();
    }else{
        requestData.salesSiteId=$("#deliverySiteId").val();
    }
    Hap.ajax({url:url,
        data:requestData,
        success: function(rowdata) {
            var newAddressData=rowdata.rows[0];
            newAddressData.spmLocation={};
            newAddressData.spmLocation.zipCode=rowdata.rows[0].zipCode;
            newAddressData.defaultFlag="Y";
            grid.addRow(newAddressData);
            grid.select(newAddressData);
            if(dataOption){
                $("#billSiteId").val(newAddressData.salesSiteId);
            }else{
                $("#deliverySiteId").val(newAddressData.salesSiteId);
            }
            }

           });
}

/**
 * 订单为会员购买时添加地址
 * @param address 地址信息
 * @param dataOption true 账单地址 /false 配送地址
 * @param grid 成功后操作的grid
 */
function f_addAddressWithMember(address,dataOption,grid){
    var url = _basePath+'/mm/memsite/save';
    var defaultFlag;
    if(address.save){
        defaultFlag='N';
    }else{
        defaultFlag='Y';
    }
    var siteCode="SHIP";
    if(dataOption){
        siteCode="BILL";
    }
    var data =[{
        "memberId":liger.get("memberId").getValue(),
        "siteUseCode": siteCode,
        "name": address.memberName,
        "phone": address.phoneNumber,
        "defaultFlag": defaultFlag,
        "address":address.address,
        "areaCode":address.areaCode,
        "spmLocation" :{
            "countryCode": address.countryCode,
            "countryName": address.countryName,
            "stateName": address.stateName,
            "stateCode": address.stateCode,
            "cityCode": address.cityCode,
            "cityName": address.cityName,
            "addressLine1": address.addressLine1,
            "addressLine2": address.addressLine2,
            "addressLine3": address.addressLine3,
            "zipCode": address.zipCode
        }
    }];
    Hap.ajax({url:url,
              data:data,
              success: function(rowdata) {
                  if(rowdata.rows[0].defaultFlag=="Y"){
                      var data=grid.getData();
                      for(var i in data){
                          if(data[i].defaultFlag=="Y"){
                              data[i].defaultFlag="N";
                              grid.updateRow(data[i]);
                              break;
                          }
                      }
                  }
                  grid.addRow(rowdata.rows[0])}
                 });

}

/**
 * 编辑会员地址
 * @param dataOption true 账单地址 /false 配送地址
 * @param rowindex 行号
 */
function f_editAddressWithMember(dataOption,rowindex){

    var rowData;
     if(dataOption){
         rowData=addressgrid.getRow(rowindex);
       }
    else{
        rowData=deliveryaddress.getRow(rowindex);
       }
    // 组合数据

     var save ;
     if(rowData.defaultFlag&&rowData.defaultFlag == 'Y'){
         save=true;
     }else{
         save=false
     }
     var editData={

                    "memberId": rowData.memberId,
                    "memberName":rowData.name,
                    "phoneNumber":rowData.phone,
                    "countryCode": rowData.spmLocation.countryCode,
                    "stateCode": rowData.spmLocation.stateCode,
                    "cityCode": rowData.spmLocation.cityCode,
                    "addressLine1": rowData.spmLocation.addressLine1,
                    "addressLine2": rowData.spmLocation.addressLine2,
                    "addressLine3": rowData.spmLocation.addressLine3,
                    "zipCode": rowData.spmLocation.zipCode,
                    "save": save,
                    "address": rowData.address,
                    "areaCode":rowData.areaCode
            }
     om_location_edit(_basePath,editData,function(d){

            var defaultFlag='N';
            if(d.save){
                defaultFlag='Y';
            }
            // 更新数据
            var url = _basePath+'/mm/memsite/save';

                rowData.name = d.memberName,
                rowData.phone = d.phoneNumber,
                rowData.defaultFlag = defaultFlag,
                rowData.address = d.address,
                rowData.spmLocation.countryCode= d.countryCode,
                rowData.spmLocation.countryName= d.countryName,
                rowData.spmLocation.stateName= d.stateName,
                rowData.spmLocation.stateCode= d.stateCode,
                rowData.spmLocation.cityCode= d.cityCode,
                rowData.spmLocation.cityName = d.cityName,
                rowData.spmLocation.addressLine1= d.addressLine1,
                rowData.spmLocation.addressLine2= d.addressLine2,
                rowData.spmLocation.addressLine3= d.addressLine3,
                rowData.spmLocation.zipCode= d.zipCode
                rowData.areaCode=d.areaCode
                var array =[rowData];
                $.ajax({
                    url : url,
                    type :  'POST',
                    dataType : "json",
                    contentType : "application/json",
                    data : JSON2.stringify(array),
                    success : function(json) {
                        json.rows[0].__index=rowData.__index;
                        if(dataOption){
                            if(json.rows[0].defaultFlag=="Y"){
                                var data=addressgrid.getData();
                                for(var i in data){
                                    if(data[i].defaultFlag=="Y"){
                                        data[i].defaultFlag="N";
                                        addressgrid.updateRow(data[i]);
                                        break;
                                    }
                                }
                            }
                          addressgrid.updateRow(json.rows[0]);
                      }else{
                            if(json.rows[0].defaultFlag=="Y"){
                                var data=deliveryaddress.getData();
                                orderDetail.deliveryLocationId=json.rows[0].locationId;
                                for(var i in data){
                                    if(data[i].defaultFlag=="Y"){
                                        data[i].defaultFlag=="N";
                                        deliveryaddress.updateRow(data[i]);
                                        break;
                                    }
                                }
                            }
                        deliveryaddress.updateRow(json.rows[0]);
                    }
                    },
                    error : function() {
                        $.ligerDialog.closeWaitting();
                    }
                });
     });
}


/**
 * 编辑非会员地址
 * @param dataOption true 账单地址 /false 配送地址
 * @param rowindex 行号
 */
function f_editAddressWithOutMember(dataOption,rowindex){
    var rowData;
     if(dataOption){
         rowData=addressgrid.getRow(rowindex);
       }
    else{
        rowData=deliveryaddress.getRow(rowindex);
       }
    // 组合数据
     var save =true;
     var editData={
                    "memberName":rowData.name,
                    "phoneNumber":rowData.phone,
                    "countryCode": rowData.countryCode,
                    "stateCode": rowData.stateCode,
                    "cityCode": rowData.cityCode,
                    "addressLine1": rowData.address,
                    "zipCode": rowData.zipCode,
                    "save": save,
                    "address": rowData.address,
                    "areaCode":rowData.areaCode
            }
     om_location_edit(_basePath,editData,function(address){

            var defaultFlag='Y';
            // 更新数据
            var url = _basePath+'/om/sites/submit';
            var siteCode="SHIP";
            if(dataOption){
                siteCode="BILL";
            }
            var requestData={headerId:$("#headerId").val(),salesOrgId:liger.get("salesOrgId").getValue(),siteType:siteCode,salesSiteId:rowData.salesSiteId,
                    name:address.memberName,phone:address.phoneNumber,cityCode:address.cityCode,stateCode:address.stateCode,
                    countryCode:address.countryCode,address:address.address,areaCode:address.areaCode,zipCode:address.zipCode,autoshipFlag:"N"
            };
                $.ajax({
                    url : url,
                    type :  'POST',
                    dataType : "json",
                    async:false,
                    contentType : "application/json",
                    data : JSON2.stringify(requestData),
                    success : function(json) {
                        json.rows[0].__id = rowData.__id;
                        json.rows[0].__previd = rowData.__previd;
                        json.rows[0].__index = rowData.__index;
                        json.rows[0].__nextid = rowData.__nextid;
                        json.rows[0].spmLocation={};
                        json.rows[0].spmLocation.zipCode=json.rows[0].zipCode;
                        json.rows[0].defaultFlag="Y";
                        //TODO: update grid data
                        if(dataOption){
                            addressgrid.updateRow(json.rows[0]);
                        }else{
                          deliveryaddress.updateRow(json.rows[0]);
                      }
                    },
                    error : function() {
                        $.ligerDialog.closeWaitting();
                    }
                });
     });

}

/**
 * 编辑地址.
 *
 * @param 地址类型
 *            false 送货地址 true 账单地址
 * @param grid
 *            id add by 张闯胜
 */
function beginEdit(dataOption,rowindex){
    if(liger.get("memberId").getValue()){
        f_editAddressWithMember(dataOption,rowindex);
    }else{
        f_editAddressWithOutMember(dataOption,rowindex);
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

function deleteRow(dataOption,rowindex){

    $.ligerDialog.confirm($l('sys.hand.tip.delete_confirm'), $l('sys.hand.tip.info'),function(yes) {
        if (yes) {

            var url=_basePath+'/mm/memsite/delete';
            if(!liger.get("memberId").getValue()){
                url=_basePath+'/om/sites/delete';
                var siteId;
                if(dataOption){
                    siteId=addressgrid.getRow(rowindex).salesSiteId;
                }
                else{
                    siteId=deliveryaddress.getRow(rowindex).salesSiteId;
                }
                url=url+"?siteId="+siteId;
            }else{
                var memSiteId;
                if(dataOption){
                     memSiteId=addressgrid.getRow(rowindex).siteId;
                }
                else{
                    memSiteId=deliveryaddress.getRow(rowindex).siteId;
                }
                url=url+"?memSiteId="+memSiteId;
            }

          Hap.ajax({url:url,
                   success: function(rowdata) {
                       if(rowdata.success){
                           if(dataOption){
                               addressgrid.remove(addressgrid.getRow(rowindex));
                           }else{
                             deliveryaddress.remove(deliveryaddress.getRow(rowindex));
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
function addLine(isautoship){
    var object={itemSalesType:defaultItemSalesType};
    if(!isautoship&&liger.get("deliveryType").getValue()!="SHIPP"){
        object.defaultInvOrgId=defaultInvOrg;
    }
    linegrid.addRow(object);
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
 * 校验配送类型
 *
 * @param isautoship
 *            是否autoship订单
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
            linegrid.set({columns:o_getLineColumns(true,isPoint,true,isautoship)});
            linegrid.reRender();
        }
        liger.get("isCod").setValue(false);
        $("#actrualPayAmt").text(Number(summary.currency+summary.adjustMents).toFixed(2));
        reSetLine(false,false,true);
    } else if (type == "SHIPP") {
        if(linegrid){
            linegrid.set({columns:o_getLineColumns(true,isPoint,false,isautoship)});
            linegrid.reRender();
        }
        reSetLine(false,false,true);
        $("#addressPanel").show();
        if(liger.get("memberId")){
            f_memberAddress(liger.get("memberId").getValue());
        }
    }

}

/**
 * 校验
 *
 * @param isautoship
 */
function validate(isautoship) {
    if(!isautoship){
        validatePeriod();
    }else{
        for(var i in autoShipStatusData){
            if(autoShipStatusData[i].value=="NEW"){
                liger.get("autoshipStatus").setText(autoShipStatusData[i].meaning);
                break;
            }
        }
    }

    validateDeliver(isautoship);
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
        cancelable:false,
        focusWhenSetValue : false,
        readonly:isautoship,
        onSelected : function(value) {
            validateDeliver(isautoship);
        },
        validate:{
            required:true
        },render:function(data){
            if(!data){
                return;
            }
            for(var i in deliveryTypeData){
                if(deliveryTypeData[i].value== data){
                    return deliveryTypeData[i].meaning;
                 }
            }

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
        focusWhenSetValue : false,
        onSelected : function(value,text,data) {
            var price=Number.MAX_VALUE;
            if(data){
                for(var i in data.shippingTierDtls){
                    if(data.shippingTierDtls[i].shippingFee<price&&summary.currency>data.shippingTierDtls[i].invAmountFrom&&(!data.shippingTierDtls[i].invAmountTo||summary.currency<=data.shippingTierDtls[i].invAmountTo)){
                        summary.shippingTier=data.shippingTierDtls[i].shippingFee;
                        $("#shippingTier").text(summary.shippingTier);
                        $("#actrualPayAmt").text(summary.currency+summary.shippingTier+summary.adjustMents);
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
            cancelable:false,
            onSelected : function(value) {
                var obj = new Object();
                obj.id = 'coupons';
                obj.value = value;
                f_payMentAdjust(obj)
            }
        });
        // 人工调整里面的下拉框调整
        $("#adjustType").ligerComboBox({
            width : 120,
            data : adjustTypeData,
            valueField : "value",
            textField : "meaning",
            cancelable:false
        });
        liger.get('serviceCenterId').set({readonly:true});
        liger.get('adjustType').setValue("add");
        for(var i in adjustTypeData){
            if(adjustTypeData[i].value=="add"){
                liger.get('adjustType').setText(adjustTypeData[i].meaning);
            }
        }
        if(liger.get("orderStatus").getValue()!="COMP"){
            var files=new Array();
            files.push("sourceKey");
            files.push("batchNumber");
            om_oc_form.setVisible(files,false);
        }
    }
    validate(isautoship);
}

/**
 * 提交销售订单
 */
function f_sumbitSales(){
    if(!checkPayment()){
        $.ligerDialog.confirm($l('实付款小于'+freePayMent+'的货到付款订单，收取额外费用'+freeFreight), $l('sys.hand.tip.info'),function(yes) {
            if(yes){
                $('#shippingTier').text(Number(summary.shippingTier)+Number(freeFreight));
                $("#actrualPayAmt").text(Number((summary.currency+summary.shippingTier+summary.adjustMents+Number(freeFreight))).toFixed(2));
                f_save(false,true);
            }
        });
    }else{
        f_save(false,true);
    }
}

/**
 * 保存订单
 */
function f_save(isSave,isSumbit) {
    var requestData = om_oc_form.getData();
    requestData.orderStatus="SAV";
    if(isSumbit){

        requestData.orderStatus="WPAY";
        if(!valideBefore()){
            return;
        }
    }
    if(!isSave&&!isSumbit&&!liger.get("memberId").getValue()){
        return;
    }
    var deliveryData = deliveryaddress.getSelectedRow();
    if(deliveryData){
        requestData.deliveryLocationId = deliveryData.locationId;
        requestData.deliveryTo = deliveryData.siteId;
    }
    var billData=addressgrid.getSelectedRow();
    if(billData){
        requestData.billingLocationId = billData.locationId;
        requestData.billingTo = billData.siteId;
    }
    var lines = linegrid.getData();
    requestData.lines = lines;
    requestData.deliveryType = liger.get('deliveryType').getValue();
    requestData.orderAmt = $('#afterTax').text();
    requestData.discountAmt = summary.discountAmt;
    requestData.taxAmt = $('#tax').text();
    requestData.actrualPayAmt = $('#actrualPayAmt').text();
    requestData.adjustMents = f_getAdjustMents();
    requestData.logistics = f_getLogistics();
    requestData.codFlag="N";
    requestData.sourceType="MANUL";
    requestData.salesPoints=$("#getsalespoint").text();
    if(liger.get("isCod").getValue()&&requestData.deliveryType=="SHIPP"){
        requestData.codFlag='Y';
    }else{
        requestData.codFlag='N';
    }
    $.ajax({
        type : 'POST',
        url : _basePath + "/om/order/submit",
        data : JSON2.stringify(requestData),
        success : function(json) {
            console.log("--1")
            if(isSave){

            }
            if(isSumbit){
                if(json.success){
                    var data=json.rows[0];
                    if(data.attribute1&&data.attribute1=="out"){
                        $.ligerDialog.error($l("msg.warning.order.product_order_qty_over_onhand"),function(){
                            console.log("666")
                            toPaymentPage(data.headerId)
                        });
// window.top.f_removeCurrentTab();
                    } else {
                        toPaymentPage(data.headerId)
// window.top.f_removeCurrentTab();
                    }

                }
            }else {
                if(isSave){
                    if(json.success){
                        Hap.showSuccess();
                    }
                }
                var data=json.rows[0];
                orderDetail=data;
                f_setOrderDetai(data);
                if(data.logistics){
                    $("#logisticsId").val(data.logistics.logisticsId);
                }

            }
        },
        contentType : "application/json; charset=utf-8"
    });

}

function toPaymentPage(headerId) {
    window.top.f_removeTab('ORDER_CREATE');
    window.top.f_addTab('ORDER_CREATE', $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.orderpayment'),
            _basePath +"/om/om_order_payment.html?orderHeaderId="+headerId);
}

/**
 * 提交autoshio订单数据
 */
function f_sumbitAutoShip(){
    if(!validateAutoship()){
        return;
    }
    var requestData = om_oc_form.getData();
    var deliveryData = deliveryaddress.getSelectedRow();
    if(deliveryData){
        requestData.deliveryLocationId = deliveryData.locationId;
        requestData.deliveryTo = deliveryData.siteId;
    }
    var billData=addressgrid.getSelectedRow();
    if(billData){
        requestData.billingLocationId = billData.locationId;
        requestData.billingTo = billData.siteId;
    }
    if(requestData.autoshipStatus=="NEW"){
        requestData.autoshipStatus=="ACV"
    }
    var lines = linegrid.getData();
    requestData.lines = lines;
    requestData.deliveryType = liger.get('deliveryType').getValue();
    requestData.orderAmt = $('#afterTax').text();
    requestData.discountAmt = summary.discountAmt;
    requestData.taxAmt = $('#tax').text();
    requestData.actrualPayAmt = $('#actrualPayAmt').text();
    requestData.logistics = f_getLogistics();
    requestData.salesScore=$("#getsalespoint").text();
    $.ajax({
        type : 'POST',
        url : _basePath + "/om/autoship/submit",
        data : JSON2.stringify(requestData),
        success : function(json) {
            if(json.success){
                var data=json.rows[0];
                f_setAusoShipDetail(data);
                Hap.showSuccess();
                initButton(true);
            }else{
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
function initButton(isAutoship){
    if(!isAutoship){
        $("#pickItem").hide();
        $("#saveBtn").hide();
        $("#submitBtn").hide();
        $("#deliveryPackage").hide();
        $("#cancle").hide();
        $("#invalid").hide();
        $("#printInvoice").hide();
        $("#copyBtn").hide();
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
                    f_sumbitSales();
                },
                text : $l('sys.hand.btn.submit')
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
                                        var currntTab = top.tab.getSelectedTabItemID();
                                        top.f_addTab('ORDER_DETAIL', $l('type.com.lkkhpg.dsis.common.order.dto.queryorder.orderinfo'),
                                                _basePath +"/om/om_order_detail.html?orderId="+orderDetail.headerId);
                                        top.tab.removeTabItem(currntTab);
                                        // window.top.f_removeCurrentTab();

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
            $("#submitBtn").show();
        }else if(orderStatus=="NEW"||orderStatus=="FAIL"||orderStatus=="SAV"){
            $("#saveBtn").ligerButton({
                click : function(){
                    f_save(true,false);
                },
                text : $l('sys.hand.btn.save')
            });
            $("#submitBtn").ligerButton({
                click : function(){
                    f_sumbitSales();
                },
                text : $l('sys.hand.btn.submit')
            });
            $("#saveBtn").show();
            $("#submitBtn").show();
        }else if(orderStatus=="COMP"){
            $("#pickItem").ligerButton({
                click : function(){
                    window.top.f_addTab(null, $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.deliverypickrelease'),_basePath + '/dm/dm_delivery_pick_release.html?orderNumber='+liger.get("orderNumber").getValue());
                },
                text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.deliverypick')
            });
            $("#deliveryPackage").ligerButton({
                click : function(){
                    window.top.f_addTab(null, $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.delivery_query'),_basePath + '/dm/dm_delivery_query.html?orderNumber='+liger.get("orderNumber").getValue());
                },
                text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.delivery')
            });
            $("#printInvoice").ligerButton({
                click : function(){
                    alert($l('type.com.lkkhpg.dsis.common.order.dto.salesorder.printinvoice'))
                },
                text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.printinvoice')
            });
            $("#printInvoice").show();
            $("#deliveryPackage").show();
            $("#pickItem").show();
            $("#invalid").ligerButton({
                click : getMarket,
                text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.invalid')
            });
            $("#invalid").show();
        }else if(orderStatus=="CONF"){
            $("#invalid").ligerButton({
                click : getMarket,
                text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.invalid')
            });
            $("#invalid").show();
        }
        if(orderStatus=="WPAY"||orderStatus=="SAV"||orderStatus=="NEW"){
            $("#copyBtn").show();
        }
        $("#copyBtn").ligerButton({
            click : f_copy,
            text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.copytemplate')
        });
    }else {
        $("#activeBtn").ligerButton({
            click :function(){
                f_updateAutoShipStatus('ACV');
                autoshipActive();
            } ,
            text : $l('msg.warning.order.active')
        });
        $("#stopBtn").ligerButton({
            click : function(){
                f_updateAutoShipStatus('SUS');
                autoshipReadOnly();
            },
            text : $l('msg.hand.btn.pause')
        });
        $("#endBtn").ligerButton({
            click : function(){
                f_updateAutoShipStatus('TER');
                autoshipReadOnly();
            },
            text : $l('type.com.lkkhpg.dsis.common.member.btn.memberdetails.terminate')
        });
        $("#endBtn").hide();
        $("#stopBtn").hide();
        $("#activeBtn").hide();
        $("#autoShipsaveBtn").hide();
        if(liger.get("autoshipStatus").getValue()=="ACV"){
            $("#endBtn").show();
            $("#stopBtn").show();
            $("#autoShipsaveBtn").show();
            autoshipActive();
        }else if(liger.get("autoshipStatus").getValue()=="NEW"){
            $("#autoShipsaveBtn").show();
        }else if(liger.get("autoshipStatus").getValue()=="SUS"){
            $("#endBtn").show();
            $("#activeBtn").show();
        }
        $("#autoShipsaveBtn").ligerButton({
            text:$l('sys.hand.btn.submit'),
            click:f_sumbitAutoShip
        });
        $("#addCreditCar").ligerButton({
            click : function(){
                $.ligerDialog.open({
                    target: $("#addCredit") ,
                    title:$l('msg.com.lkkhpg.dsis.common.order.addcredit'),
                    width:500,
                    buttons: [{
                        text: $l('sys.hand.btn.ok'),
                        onclick: function(i,d){
                            if(!addCreditFrom.valid()){
                                return;
                            }
                            $.ajax({
                                type : 'POST',
                                url : _basePath + "/om/creditCard/submit",
                                data : addCreditFrom.getData(),
                                success : function(json) {
                                    var data=json.rows[0];
                                    $("#creditCardId").val(data.creditCardId);
                                    $("#cardNo").text(data.maskedCreditCardNum);
                                    $("#bankName").text(liger.get("bankCode").getText());
                                    $("input").ligerHideTip();
                                    d.hide();
                                }
                            });
                        }
                    },
                    {
                        text: $l('sys.hand.btn.cancel'),
                        onclick: function(i, d) {
                            $("input").ligerHideTip();
                            d.hide();
                        }
                    }]
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
    if (!detail) {
        return;
    }
    cleanMember();
    memberStatus=detail.status;
    memberRole=detail.memberRole;
    if(memberStatus=='NEW'&&memberRole=="DIS"){
        $.ligerDialog.warn($l('此会员为新会员'));
        reSetLine(false,true,false);
    }
    $.ajax({
        type : 'POST',
        url  : _basePath + "/mm/member/isMemberBirthdayMonth?memberId=" + detail.memberId + "&dob=" + new   Date(detail.dob.replace(/-/g,   "/")),
        success : function(json){
            if(json.code == 'true'){
                $.ligerDialog.warn($l('msg.info.om.member_birthday_month'));
            }
        }
    });
    liger.get("cnName").setValue("");
    liger.get("enName").setValue("");
    liger.get("cnName").setValue(detail.chineseName);
    liger.get("enName").setValue(detail.englishName);
    if(ordertype=='autoship'){
        $.ajax({
            type : 'POST',
            url : _basePath + "/om/autoship/checkByMemberId?memberId=" + detail.memberId,
            success : function(json) {
                if (json.success) {
                    if(!json.rows||json.total==0){
                        f_memberAddress(detail.memberId);
                        f_memberAccount(detail.memberId);
                        var lineData={};
                        lineData.rows=[];
                        linegrid.set({data:lineData});
                        $("#creditCardId").val("");
                        $("#autoshipId").val("");
                        $("#logisticsId").val("");
                        $("#bankName").text("");
                        $("#cardNo").text("");
                        liger.get("autoshipNumber").setValue("");
                        liger.get("autoshipStatus").setValue("NEW");
                        for(var i in autoShipStatusData){
                            if(autoShipStatusData[i].value=="NEW"){
                                liger.get("autoshipStatus").setText(autoShipStatusData[i].meaning);
                                break;
                            }
                        }
                        liger.get("creditCardNum").setValue("");
                        liger.get("cardHolder").setValue("");
                        liger.get("validYear").setValue("");
                        liger.get("validMonth").setValue("");
                        f_calculateLinePrice();
                        $("#getsalespoint").text(0);
                        $("#shippingTier").text(0);
                        $("#actrualPayAmt").text(0);
                        $("#summaryPV").text(0);
                        addLine(true);
                    }else{
                        var param = {'autoshipId' : json.rows[0].autoshipId};
                        $.getJSON( _basePath +"/om/autoship/detail",param,function(data) {
                            f_setAusoShipDetail(data.rows[0]);
                            initButton(true);
                        })
                    }
                }
            }
        });
    }else{
        f_memberAddress(detail.memberId);
        f_memberAccount(detail.memberId);
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
    newData.defaultInvOrgId=oldRow.defaultInvOrgId;
    newData.itemSalesType=oldRow.itemSalesType;
    newData.unitDiscountPrice = 0;
    newData.uomCode=newRow.uomCode;
    newData.itemId=newRow.itemId;
    var pricetype=f_getPriceType();
    var orderType=liger.get("orderType");
    $.ajax({
        type : 'POST',
        url : _basePath + "/inv/price/queryPriceByItemId?itemId="+ newRow.itemId+"&currency="+liger.get("currency").getValue()+"&uomCode="+newRow.uomCode+"&salesOrgId="+defaultSaleOrg,
        success : function(json) {
            if (json.success) {
                newData.priceDetail=json.rows;
                for (var i in json.rows) {
                    if (json.rows[i].priceType == 'PV') {
                        if(orderType&&orderType.getValue()=="STDP"&&newData.itemSalesType=="PURC"){
                            newData.pv = json.rows[i].unitPrice;
                        }else if(!orderType){
                            newData.pv = json.rows[i].unitPrice;
                        }else{
                            newData.pv=0;
                        }

                    } else if (json.rows[i].priceType == pricetype) {
                        if(pricetype=='RP'){
                            if((summary.points+json.rows[i].unitPrice)>Number(liger.get("currentPoints").getValue())){
                                // TODO clear itemNumber
                                $.ligerDialog.error($l('msg.error.order.sales_point_insufficient'));
                                return
                            }
                            summary.points+json.rows[i].unitPrice;
                            newData.unitRedeemPoint = json.rows[i].unitPrice;
                        }else{
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
                if(!newData.unitRedeemPoint){
                    newData.unitRedeemPoint=0;
                }
                newData.favorableSum = newData.quantity* newData.unitDiscountPrice;
                newData.redeemPoint=newData.unitRedeemPoint * newData.quantity;
                newData.amount = newData.unitOrigPrice * newData.quantity- newData.favorableSum;
                newData.PVSum = newData.quantity * newData.pv;
                newData.tax=summary.taxRate*newData.unitOrigPrice;
                newData.unitSellingPrice=newData.unitOrigPrice;
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
    }else if(orderType=='BIGF'){
        pricetype='0';
    }else{
        pricetype='RP'
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
    if(!newData.unitRedeemPoint){
        newData.unitRedeemPoint=0;
    }
    newData.favorableSum = Number(newData.quantity * newData.unitDiscountPrice);
    newData.amount = Number(newData.unitOrigPrice * newData.quantity- newData.favorableSum);
    newData.redeemPoint=newData.unitRedeemPoint * newData.quantity;
    newData.PVSum = Number(newData.quantity * newData.pv);
    newData.tax=summary.taxRate*newData.unitOrigPrice;
    newData.unitSellingPrice=newData.unitOrigPrice-newData.unitDiscountPrice;
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
        } else if(rows[row].accountingType == 'SP'){
            liger.get("currentPoints").setValue(rows[row].balance);
            // liger.get("currentPV").setValue(rows[row].balance);
        }
    }
}

/**
 * 订单行金额汇总
 */
function f_calculateLinePrice() {
    var lineData = linegrid.getData();
    summary.pv = 0;
    summary.currency = 0;
    summary.exchange=0;
    summary.points=0
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
        if(lineData[line].PVSum){
            summary.pv += Number(lineData[line].PVSum);
        }else{
            if(!lineData[line].pv){
                lineData[line].pv=0;
            }
            summary.pv += Number(lineData[line].pv*lineData[line].quantity);
        }
        lineData[line].unitSellingPrice=Number(lineData[line].unitOrigPrice-lineData[line].unitDiscountPrice);
        lineData[line].amount=Number(lineData[line].unitSellingPrice*lineData[line].quantity);
        if(lineData[line].itemSalesType=="EXCH"){
            summary.exchange+=Number(lineData[line].amount);
        }
        summary.points += Number(lineData[line].redeemPoint);
        if(lineData[line].itemSalesType!='FREE'){
            summary.currency += Number(lineData[line].amount);
        }

    }
    $("#totalPV").text(Number(summary.pv.toFixed(2)));
    $("#summaryPV").text(Number(summary.pv.toFixed(2)));
    if(ispoint){
        $("#beforeTax").text(0);
        $("#tax").text(0);
        $("#afterTax").text(0);
        $("#TotalCurrency").text(Number(summary.points));
        if(liger.get("deliveryType").getValue()!="SHIPP"){
            $("#actrualPayAmt").text(Number((summary.adjustMents).toFixed(2)));
        }else{
            $("#actrualPayAmt").text(Number((summary.shippingTier+summary.adjustMents).toFixed(2)));
        }


    }else{
        if(summary.enableTax){
            if(summary.priceIncludeTax){
                summary.tax = Number((summary.currency /(1+Number(summary.taxRate))* summary.taxRate).toFixed(2));
            }else{
                summary.tax = Number((summary.currency * summary.taxRate).toFixed(2));
            }

        }else{
            summary.tax = 0;
        }
        if(summary.priceIncludeTax){
            $("#beforeTax").text(Number((summary.currency - summary.tax).toFixed(2)));
        }else{
            $("#beforeTax").text(Number(summary.currency.toFixed(2)));
        }

        $("#tax").text(Number(summary.tax));
        if(!summary.priceIncludeTax){
            summary.currency=summary.currency + summary.tax
            $("#afterTax").text(Number((summary.currency + summary.tax).toFixed(2)));
            $("#TotalCurrency").text(Number((summary.currency + summary.tax).toFixed(2)));
        }
        $("#afterTax").text(Number(summary.currency.toFixed(2)));
        $("#TotalCurrency").text(Number(summary.currency.toFixed(2)));
        if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()!="SHIPP"){
            $("#actrualPayAmt").text(Number((summary.currency+summary.adjustMents-summary.discountAmt).toFixed(2)));
        }else{
            $("#actrualPayAmt").text(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt).toFixed(2)));
        }

        $("#exchange").text(Number(summary.exchange.toFixed(2)))

        if($("#getsalespoint")&&ordertype=="autoship"&&pointRate){
            if(pointLimit&&(Number($("#actrualPayAmt").text())*pointRate>pointLimit)){
                $("#getsalespoint").text(pointLimit);
            }else{
                $("#getsalespoint").text(Number((Number($("#actrualPayAmt").text())*pointRate).toFixed(2)));
            }
        }
    }
    if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()=="SHIPP"&&deliveryaddress.getSelected()){
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
function f_payMentAdjust(obj,ischeck) {
    var isNum = /^[0-9]*$/;
    if(!isNum.test(obj.value)){
        Hap.showError($l("type.com.lkkhpg.dsis.common.order.payadjustment.number"));
        $(obj).val("");
        return;
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
            tr.find("#adjustText").text(obj.value);
        }else{
            $("#" + id).text(obj.value);
        }
    }
    if(ischeck){
        if(obj.id=='exchangeBalanceUse'){
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
            $("#" + id).text(obj.value)
        }else if(obj.id=='remainingBalanceUse'){
            if(Number(obj.value)>Number(liger.get('remainingBalance').getValue())){
                Hap.showError("remainingBalance"+$l("type.com.lkkhpg.dsis.common.order.payadjustment.totalbiggerthenorder")+"remainingBalance");
                $(obj).val("");
                $("#" + id).text("0");
                return;
            }else{
                $("#remainingBalanceUse").val(obj.value);
                $("#" + id).text(obj.value);
            }
        }
    }
    var afterAdjust = 0;
    afterAdjust -= Number($("#coupons").val());
    afterAdjust -= Number($("#exchangeBalanceUse").val());
    afterAdjust -= Number($("#remainingBalanceUse").val());
    afterAdjust += Number($("#extra").val());
    var trs = $('#payAdjust table tr:gt(3)');
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
        $("#actrualPayAmt").text(summary.currency+summary.shippingTier+afterAdjust);
    }
    return;
}

/**
 * 修改是否隐藏样式
 *
 * @param obj
 *            需要操作的对象
 * @param id
 *            操作对象的id
 */
function f_changeShow(obj, id) {
    if ($(obj).hasClass('l-panel-header-toggle-hide')) {
        $('#' + id).show();
        $(obj).removeClass('l-panel-header-toggle-hide');
        obj.title = $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.colse');
    } else {
        $('#' + id).hide();
        $(obj).addClass('l-panel-header-toggle-hide');
        obj.title = $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.open');
    }
}


/**
 * 订单数据设置
 *
 * @param data
 *            订单详情数据
 */
function f_setOrderDetai(data) {
    var memberCode=liger.get("memberId").getText();
    var salesOrgName=liger.get("salesOrgId").getText();
    var marketName=liger.get("salesMarketId").getText();
    om_oc_form.setData(data);
    liger.get("memberId").setText(memberCode);
    liger.get("salesOrgId").setText(salesOrgName);
    liger.get("salesMarketId").setText(marketName);
    // 销售组织以及市场货币设置
    if(data.salesOrganization){
        f_setMarketAndCurrency(data.salesOrganization.market, data.spmCurrency);
        liger.get('salesOrgId').setValue(data.salesOrganization.salesOrgId);
        liger.get('salesOrgId').setText(data.salesOrganization.name);
    }
    // 订单状态设置
    for(var i in orderStatusData){
        if(orderStatusData[i].value==data.orderStatus){
            liger.get("orderStatus").setText(orderStatusData[i].meaning);
            break;
        }
    }
    // 订单类型设置
    for(var i in orderTypeData){
        if(orderTypeData[i].value==data.orderType){
            liger.get("orderType").setText(orderTypeData[i].meaning);
            break;
        }
    }
    // 销售渠道设置
    for(var i in channelData){
        if(channelData[i].value==data.channel){
            liger.get("channel").setText(channelData[i].meaning);
            break;
        }
    }
    // 是否货到付款设置
    if(data.codFlag=='Y'){
        liger.get("isCod").setValue(true);
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
    // 订单用户信息设置
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
        memberStatus=data.member.status;
        // 订单配送设置
        f_setDelivery(data.member.memSites,data.logistics,data.deliveryLocationId);
        liger.get('deliveryType').setValue(data.deliveryType);
        for ( var i in deliveryTypeData) {
            if (deliveryTypeData[i].value == data.deliveryType) {
                liger.get('deliveryType').setText(deliveryTypeData[i].meaning);
                validateDeliver();
                break;
            }
        }

    }
    f_calculateLinePrice();
}

/*--------设置默认支付调整----------*/

/**
 * 设置默认支付调整
 *
 * @param adjustMents
 *            价格调整数组
 */
function f_setDefaultAdjustMents(adjustMents) {
    var tr = $('#payAdjust table tr:eq(4)');
    var deleteTrs=$('#payAdjust table tr:gt(4)');
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
        if ('EB' == adjustMents[i].adjustmentType) {
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
        }
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
                            break;
                        }
                    }
                }else{
                    ntr.find("#adjustType"+adjustTimes+"_val").val("subs");
                    for(var j in adjustTypeData){
                        if(adjustTypeData[j].value=="subs"){
                            ntr.find("#adjustType"+adjustTimes).val(adjustTypeData[j].meaning);
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
            }else{
                if(Number(adjustMents[i].adjustmentAmount)>0){
                    tr.find("#adjustType_val").val("add");
                    for(var j in adjustTypeData){
                        if(adjustTypeData[j].value=="add"){
                            tr.find("#adjustType").val(adjustTypeData[j].meaning);
                            break;
                        }
                    }
                }else{
                    tr.find("#adjustType_val").val("subs");
                    for(var j in adjustTypeData){
                        if(adjustTypeData[j].value=="subs"){
                            tr.find("#adjustType").val(adjustTypeData[j].meaning);
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
        if ('EX' == adjustMents[i].adjustmentType) {
            $('#extra').val(obj.value);
            obj.id = "extra";
            f_payMentAdjust(obj);
        }
    }
}


/**
 * 初始设置销售区域
 *
 * @data 销售区域数据
 */
function f_initSalesOrg(data) {
    for ( var i in data) {
// var currency={
// currencyCode:"CNY"
// };
        if (data[i].salesOrgId ==defaultSaleOrg) {
            liger.get('salesOrgId').setValue(data[i].salesOrgId);
            liger.get('salesOrgId').setText(data[i].name);
// liger.get('salesOrgId').setDisabled(true);
            liger.get('salesOrgId').set({readonly:true,data:data});
            f_setMarketAndCurrency(data[i].market, data[i].currency);
            break;
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
    if(currency){
        liger.get('currency').setValue(currency);
// liger.get('currency').setText(currency.currencyCode);
        $(".currencysign").text(currency);
    }
}

/**
 * 获取支付调整数据
 */
function f_getAdjustMents() {
    var adjustMents = new Array();
    if ($('#exchangeBalanceUse').val()) {
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
    }
    if ($('#extra').val()) {
        adjustMents.push({
            adjustmentType : 'EX',
            adjustmentAmount : $('#extra').val(),
            headerId:$("#headerId").val(),
            salesAdjustmentId : $('#extraid').val()
        });
    }
    if ($('#coupons').val()) {
        adjustMents.push({
            adjustmentType : 'CP',
            adjustmentAmount : -$('#coupons').val(),
            headerId:$("#headerId").val(),
            salesAdjustmentId : $('#couponsid').val()
        });
    }
    var trs = $('#payAdjust table tr:gt(3)');
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
	        salesOrgId:liger.get('salesOrgId').getValue()
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
 * 重置运费
 */
function f_resetShippingTier(){
    var data=liger.get('deliveryCompany').options.data;
    var lowPrice={};
    lowPrice.price=Number.MAX_VALUE;
    for(var i in data){
       var shippingTier=data[i];
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
function f_renderItemName(rowdata, index, value){
    if(!value){
        return;
    }
    if(rowdata.itemType!='PACKG'){
        return value;
    }
    var divclass=value+"&nbsp;&nbsp;<img src='../lib/ligerUI/skins/icons/search.gif' id='gridimg' index="+index+"/>";
    return divclass;
}

/**
 * 设置autoship订单详细数据
 */
function f_setAusoShipDetail(data){
    om_oc_form.setData(data);
    // 销售组织以及市场货币设置
    if(data.spmSalesOrganization){
        f_setMarketAndCurrency(data.spmSalesOrganization.market, data.spmCurrency);
        liger.get('salesOrgId').setValue(data.spmSalesOrganization.salesOrgId);
        liger.get('salesOrgId').setText(data.spmSalesOrganization.name);
    }
    // 订单状态设置
    for(var i in autoShipStatusData){
        if(autoShipStatusData[i].value==data.autoshipStatus){
            liger.get("autoshipStatus").setText(autoShipStatusData[i].meaning);
            break;
        }
    }
    // 订单行设置
    var gridDatas={};
    gridDatas.rows=data.lines;
    linegrid.set({
        data:gridDatas
    });
    // 订单用户信息设置
    if(data.member){
        f_setMemberAccount(data.member.memAccountingBalances);
        gridDatas.rows=data.member.memSites;
        liger.get("cnName").setValue(data.member.chineseName);
        liger.get("enName").setValue(data.member.englishName);
        liger.get("memberId").setValue(data.member.memberId);
        liger.get("memberId").setText(data.member.memberCode);
        memberStatus=data.member.status;
      // 订单配送设置
        f_setDelivery(data.member.memSites,data.logistics,data.deliveryLocationId);
    }
    // 订单信用卡设置
    if(data.creditCard){
        addCreditFrom.setData(data.creditCard);
        $("#creditCardId").val(data.creditCard.creditCardId);
        $("#cardNo").text(data.creditCard.maskedCreditCardNum);
        for(var i in bankBelongData){
            if(bankBelongData[i].value==data.creditCard.bankCode){
                $("#bankName").text(bankBelongData[i].meaning);
            }
        }
    }
    f_calculateLinePrice();
}

/**
 * 修改autoship订单状态
 */
function f_updateAutoShipStatus(status){
    var autoShipId=$("#autoshipId").val();
    if(!autoShipId||!status){
        Hap.showError($l("type.com.lkkhpg.dsis.common.order.autoship.not_save"));
        return;
    }
    var data={
            autoshipId:autoShipId,
            autoshipStatus:status
    };
    $.ajax({
        type : 'POST',
        url : _basePath + "/om/autoship/updateStatus",
        data : data,
        success : function(json) {
            if(json.success){
                Hap.showSuccess();
                for(var i in autoShipStatusData){
                    if(autoShipStatusData[i].value==status){
                        liger.get("autoshipStatus").setValue(status);
                        liger.get("autoshipStatus").setText(autoShipStatusData[i].meaning);
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
 * @param locationId
 *            地址Id
 */
function f_setDelivery(memSites,logistics,locationId){
    if(!memSites||!locationId){
        return;
    }
    var addressData={};
    var billAddress=new Array();
    var shipAddress=new Array();
    for(var i in memSites){
        if(memSites[i].siteUseCode=="BILL"){
            billAddress.push(memSites[i]);
        }else if(memSites[i].siteUseCode=="SHIP"){
            shipAddress.push(memSites[i]);
        }
    }
    addressData.rows=billAddress;
    addressgrid.set({data:addressData});
    addressData.rows=shipAddress;
    deliveryaddress.set({data:addressData});
   /*
	 * for(var i in memSites){ if(memSites[i].locationId==locationId){ var
	 * gridData={}; gridData.rows=new Array(); gridData.rows.push(memSites[i]);
	 * deliveryaddress.set({ data:gridData }); break; } }
	 */
    if(logistics){
// f_queryShippingTier(deliveryaddress.getData()[0]);
// var datas=liger.get("deliveryCompany").data;
        if(logistics.shippingFee){
            summary.shippingTier=logistics.shippingFee;
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

    if(orderType=="DIRP"||orderType=="STFP"||orderType=="CONP"){
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
    liger.get("currentPV").setValue("");
    if($("#exchangeBalance")){
        $("#exchangeBalance").html("0");
        $("#remainingBalance").html("0");
    }
    if(liger.get("deliveryCompany")){
        liger.get("deliveryCompany").setValue("");
        liger.get("deliveryCompany").setText("");
        $("#shippingTier").html("");
    }
    if(!orderDetail){
        var data={};
        data.rows=[];
        if(addressgrid){
            addressgrid.set({data:data});
        }
        if(deliveryaddress){
            deliveryaddress.set({data:data});
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
function reSetLine(isCopy,isChangePrice,isChangeInvOrg){
    if (!linegrid) {
        return;
    }
    var lineDatas = linegrid.getData();
    if (isCopy) {
        for ( var i in lineDatas) {
            lineDatas[i].lineId = "";
            lineDatas[i].headerId = "";
            lineDatas[i].PVSum=0;
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
    if(isChangeInvOrg){
        if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()=="PCKUP"){
            for ( var i in lineDatas) {
                lineDatas[i].defaultInvOrgId = defaultInvOrg;
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
    if (isChangePrice) {
       if($("#headerId").val()&&liger.get("orderStatus").getValue()!=orderDetail.orderStatus){
           $.ajax({
               type : 'POST',
               url : _basePath+'/om/order/line/remove',
               data : JSON2.stringify(lineDatas),
               contentType : "application/json; charset=utf-8",
               success : function(json) {
                   if(json.success){
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
       }else if(!orderDetail||liger.get("orderStatus").getValue()!=orderDetail.orderStatus){
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
function validateAutoship(){
    if(!om_oc_form.valid()){
        return false;
    }
    if(!$("#creditCardId").val()){
        $.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.autoship.not_credit'))
        return false;
    }
    var lineDatas=linegrid.getData();
    for(var i in lineDatas){
        if(!lineDatas[i].itemId){
            $.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesline.item_not_null'));
            return false;
        }
    }
    if(liger.get("deliveryType").getValue()=="SHIPP"){
        if(!deliveryaddress.getSelectedRow()){
            $.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.salesorder.delivery_not_null'));
            return false;
        }
        if(!addressgrid.getSelectedRow()){
            $.ligerDialog.error($l('msg.error.order.billing_address_null'));
            return false;
        }
        if(!liger.get("deliveryCompany").getValue()){
            $.ligerDialog.error($l('msg.error.order.deliverycompany_null'));
            return false;
        }
    }

    /*--------- 校验实付款----- */
    if(minMonery > (summary.currency+ summary.shippingTier)){
    	$.ligerDialog.error($l("type.com.lkkhpg.dsis.common.order.dto.salesorder.minimumorderamount") +minMonery);
    	return false;
    }
   return true;
}

/**
 * autoship 展示不可编辑
 */
function autoshipReadOnly(){
    liger.get("memberId").set({readonly:true});
    liger.get("executeDate").set({readonly:true});
    liger.get("executeType").set({readonly:true});
    liger.get("remark").set({readonly:true});
    liger.get("deliveryCompany").set({readonly:true});
    $("#addCreditCar").hide();
    $("[ toolbarid='addLineBtn']").hide();
    $("[ toolbarid='deleteLineBtn']").hide();
    linegrid.set({enabledEdit:false,rowSelectable:false,selectable:false});
    deliveryaddress.set({enabledEdit:false,rowSelectable:false,selectable:false});
    addressgrid.set({enabledEdit:false,rowSelectable:false,selectable:false});
}

/**
 * 激活autoship可编辑
 */
function autoshipActive(){
    liger.get("memberId").set({readonly:false});
    liger.get("executeDate").set({readonly:false});
    liger.get("executeType").set({readonly:false});
    liger.get("remark").set({readonly:false});
    liger.get("deliveryCompany").set({readonly:false});
    $("#addCreditCar").show();
    $("[ toolbarid='addLineBtn']").show();
    $("[ toolbarid='deleteLineBtn']").show();
    linegrid.set({enabledEdit:true,rowSelectable:true,selectable:true});
    deliveryaddress.set({enabledEdit:true,rowSelectable:true,selectable:true});
    addressgrid.set({enabledEdit:true,rowSelectable:true,selectable:true});
}

/**
 * 货到付款订单金额校验 如果小于当前市场的金额，则需要在运费添加需要补偿的费用
 */
function checkPayment(){
    if(!liger.get("isCod")||!liger.get("isCod").getValue()){
        return true;
    }
    // 查询不到系统参数则不启用规则，返回true
    if(!freePayMent||!freeFreight){
        return true;
    }
    if(summary.currency<freePayMent){
        return false;
    }else{
        return true;
    }
}

/**
 * 获取商品包详情
 * @param row 商品包行
 * @param detailPanel 详情的pannel
 * @param callback 回调函数
 */
function f_getItemPackageDetail(row, detailPanel, callback) {
    if(row.itemType!='PACKG'){//非商品包的直接返回
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
        url : _basePath+"/inv/item/queryHierarchyByItemId?itemId="+row.itemId,
        showTitle : false,
        usePager : false,
        onAfterShowData : callback,
        frozen : false
    });

}

/**
 * 初始化销售订单页面.
 * @param isConfirm 是否确认页面
 */
function initSalesOrder(isConfirm){
    if(!isConfirm){
        $("#saveBtn").ligerButton({
            click : function(){
                f_save(true,false);
            },
            text : $l('sys.hand.btn.save')
        });
        $("#copyBtn").ligerButton({
            click : f_copy,
            text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.copytemplate')
        });
        $("#nextBtn").ligerButton({
            click : f_openConfirm,
            text : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.next')
        });
    }else{
        $("#couponsAdjust").ligerPanel({
            title : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.coupons'),
            width : '98%',
            height : 'auto'
        });
        $("#billPannel").ligerPanel({
            title : $l('type.com.lkkhpg.dsis.common.order.dto.salesorder.billaddress'),
            width : '98%',
            height : 'auto'
        });
        $("#deliveryType").ligerComboBox({
            css : 'l-text-required',
            data : deliveryTypeData,
            valueField : "value",
            textField : "meaning",
            cancelable:false,
            focusWhenSetValue : false,
            readonly:false,
            onSelected : function(value) {
                validateDeliver(false);
            },
            validate:{
                required:true
            },render:function(data){
                if(!data){
                    return;
                }
                for(var i in deliveryTypeData){
                    if(deliveryTypeData[i].value== data){
                        return deliveryTypeData[i].meaning;
                     }
                }

            }
        });

        defaultType="PCKUP";
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
            focusWhenSetValue : false,
            onSelected : function(value,text,data) {
                var price=Number.MAX_VALUE;
                if(data){
                    for(var i in data.shippingTierDtls){
                        if(data.shippingTierDtls[i].shippingFee<price&&summary.currency>data.shippingTierDtls[i].invAmountFrom&&(!data.shippingTierDtls[i].invAmountTo||summary.currency<=data.shippingTierDtls[i].invAmountTo)){
                            summary.shippingTier=data.shippingTierDtls[i].shippingFee;
                            $("#shippingTier").text(summary.shippingTier);
                            $("#actrualPayAmt").text(summary.currency+summary.shippingTier+summary.adjustMents);
                        }
                    }

                }
            }
        });
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
            cancelable:false
        });
        // 人工调整里面的下拉框调整
        $("#adjustType").ligerComboBox({
            width : 120,
            data : adjustTypeData,
            valueField : "value",
            textField : "meaning",
            cancelable:false
        });
        liger.get('serviceCenterId').set({readonly:true});
        liger.get('adjustType').setValue("add");
        for(var i in adjustTypeData){
            if(adjustTypeData[i].value=="add"){
                liger.get('adjustType').setText(adjustTypeData[i].meaning);
            }
        }
    }
    validate(false);
}