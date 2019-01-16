/*==============================
 *  INT
 *=============================
 */
var sales_point_rate = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.sales_point_rate'),
    name: 'sales_point_rate',
    type: 'number'
};
var sales_point_limit = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.sales_point_limit'),
    name: 'sales_point_limit',
    type: 'number'
};
var add_fee_payment = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.add_fee_payment'),
    name: 'add_fee_payment',
    type: 'number'
};
var add_fee_freight = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.add_fee_freight'),
    name: 'add_fee_freight',
    type: 'number'
};
var autoship_min_sum = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.autoship_min_sum'),
    name: 'autoship_min_sum',
    type: 'number'
};
var max_pv_service_center = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.max_pv_service_center'),
    name: 'max_pv_service_center',
    type: 'number'
};
var bonus_percentage_service_center = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.bonus_percentage_service_center'),
    name: 'bonus_percentage_service_center',
    type: 'number'
};
var bonus_ratio_service_center = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.bonus_ratio_service_center'),
    name: 'bonus_ratio_service_center',
    type: 'number'
};
var max_sales_ipoint_center = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.max_sales_ipoint_center'),
    name: 'max_sales_ipoint_center',
    type: 'number'
};
var bonus_percentage_ipoint_center = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.bonus_percentage_ipoint_center'),
    name: 'bonus_percentage_ipoint_center',
    type: 'number'
};
var bonus_ratio_sipoint_center = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.bonus_ratio_sipoint_center'),
    name: 'bonus_ratio_sipoint_center',
    type: 'number'
};
var return_limit = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.return_limit'),
    name: 'return_limit',
    type: 'number'
};
var bonus_release_threshold = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.bonus_release_threshold'),
    name: 'bonus_release_threshold',
    type: 'number'
};
var member_age_alert = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_age_alert'),
    name: 'member_age_alert',
    type: 'int'
};
var autoship_min_pv = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.autoship_min_pv'),
    name: 'autoship_min_pv',
    type: 'int'
};
/*
 * ==============================
 * STRING
 *  ==============================
 */
var merid = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.merid'),
    name: 'merid',
    type: 'string'
};
var merchantid = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.merchantid'),
    name: 'merchantid',
    type: 'string'
};
var member_id_prefix = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_id_prefix'),
    name: 'member_id_prefix',
    type: 'string'
};

var terminalid = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.terminalid'),
    name: 'terminalid',
    type: 'string'
};
/*==============================
 *  combobox
 *=============================
 */

var execute_date = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.execute_date'),
    name: 'execute_date',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: EXEC_DATE_OP
    }
};
bonus_month_date = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.bonus_month_date'),
    name: 'bonus_month_date',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: EXEC_DATE_OP,
        isMultiSelect: true
    }
};

var bank = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.bank'),
    name: 'bank',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: BANK_OP,
        isMultiSelect: true
    }
};
var pay_by_rb = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.pay_by_rb'),
    name: 'pay_by_rb',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var payment_method = {
    display: $l('type.com.lkkhpg.dsis.common.order.dto.orderpayment.paymentmethod'),
    name: 'payment_method',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: PAY_METHOD_MODIFY,
        isMultiSelect: true
    }
};
var sales_org_type = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.slaeorg_type'),
    name: 'sales_org_type',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: SALES_ORG_TYPE_OP
    }
};
var refund_to_rb = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.refund_to_rb'),
    name: 'refund_to_rb',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var pos_num = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.pos_num'),
    name: 'pos_num',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: SELECT_POS_OP,
        isMultiSelect: true
    }
};
var invoice_temp = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.invoice_temp'),
    name: 'invoice_temp',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: INVOICE_TEMPLETE_OP
    }
};

var member_auto_approved = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_auto_approved'),
    name: 'member_auto_approved',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var member_home_address = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_home_address'),
    name: 'member_home_address',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var member_race = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_race'),
    name: 'member_race',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var member_home_citizen_type = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_home_citizen_type'),
    name: 'member_home_citizen_type',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var member_gst_id = {
    display: 'GST ID',
    name: 'member_gst_id',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var member_nhi_tax_excluded = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_nhi_tax_excluded'),
    name: 'member_nhi_tax_excluded',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var member_applicition_list = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_applicition_list'),
    name: 'member_applicition_list',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var allow_back_order = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.allow_back_order'),
    name: 'allow_back_order',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
//var delivery_type = {
//	display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.delivery_type'),
//	name : 'delivery_type',
//	type : 'combobox',
//	options : {
//		valueField : 'value',
//		textField : 'meaning',
//		data : DELIVERY_TYPE_OP
//	}
//};
//var sales_channel = {
//	display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.sales_channel'),
//	name : 'sales_channel',
//	type : 'combobox',
//	options : {
//		valueField : 'value',
//		textField : 'meaning',
//		data : SALES_CHANNEL_OP
//	}
//};
var cost_method = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.cost_method'),
    name: 'cost_method',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: COST_METHOD_OP
    }
};
var currency = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.currency'),
    name: 'currency',
    type: 'combobox',
    options: {
        valueField: 'currencyCode',
        textField: 'currencyName',
        url: _basePath + "/spm/currency/query?page=-1"
    }
};
var execute_type = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.execute_type'),
    name: 'execute_type',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: EXECUTE_TYPE_OP
    }
};
var auto_write_off = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.auto_write_off'),
    name: 'auto_write_off',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var present_rebate = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.present_rebate'),
    name: 'present_rebate',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: PRESENT_REBATE_OP
    }
};

var return_type = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.return_type'),
    name: 'return_type',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: RETURN_TYPE_OP,
        isMultiSelect: true
    }
};
var refund_way_option = {
    display: $l("type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.refund_method"),
    name: "refund_way_option",
    type: "combobox",
    options: {
        valueField: "value",
        textField: "meaning",
        data: REFUND_WAY_OP,
        isMultiSelect: true
    }
}
var enable_tax = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.enable_tax'),
    name: 'enable_tax',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};

var tax_code = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.tax_code'),
    name: 'tax_code',
    type: 'popup',
    options: $.extend(tax_op, {
        onSelect: function (datas) {
            var d = datas.data[0];
            datas.value = d.taxCode;
            datas.text = d.taxCode;
        },
        onLoadData: function () {
            this.setParm('isActive', 'Y');
        }
    })
//		type : 'combobox',
//		options : {
//			valueField : 'taxCode',
//			textField : 'description',
//			url : _basePath+"/spm/tax/query"
//		}
};

var price_include_tax = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.price_include_tax'),
    name: 'price_include_tax',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var spm_editable = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.spm_editable'),
    name: 'spm_editable',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var defaul_shop = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.defaul_shop'),
    name: 'defaul_shop',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var shop_visible = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.shop_visible'),
    name: 'shop_visible',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};
var payment_gateway_code = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.payment_gateway_code'),
    name: 'payment_gateway_code',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: PAYMENT_GATEWAY_CODE_OP,
        isMultiSelect: true
    }
};

var mws_allow_back_order = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.mws_allow_back_order'),
    name: 'mws_allow_back_order',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};

var member_bank_branch = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_bank_branch'),
    name: 'member_bank_branch',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};

var member_vip_sponsor = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_vip_sponsor'),
    name: 'member_vip_sponsor',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};

var enable_conversion_rate = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.enable_conversion_rate'),
    name: 'enable_conversion_rate',
    type: 'combobox',
    options: {
        valueField: 'value',
        textField: 'meaning',
        data: YES_NO_OP
    }
};

/*==============================
 *  Date
 *=============================
 */
var return_date = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.return_date'),
    name: 'return_date',
    type: 'date',
    format: 'yyyy-MM-DD'
};
/*==============================
 * 20171110问题修复
 *=============================
 */
var member_type = {
    display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.member_type'),
    name : 'member_type',
    type : 'combobox',
    options : {
        valueField : 'value',
        textField : 'meaning',
        data : YES_NO_OP
    }
};
var zipcode_tax = {
    display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.zipcode_tax'),
    name : 'zipcode_tax',
    type : 'combobox',
    options : {
        valueField : 'value',
        textField : 'meaning',
        data : YES_NO_OP
    }

};
var rebate_rate = {
    display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.rebaterate'),
    name : 'rebate_rate',
    type : 'number'
};
var shipping_date_default = {
    display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.shipping_date_default'),
    name : 'shipping_date_default',
    type : 'combobox',
    options : {
        valueField : 'value',
        textField : 'meaning',
        data : YES_NO_OP
    }
};
var first_order_check = {
    display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.first_order_check'),
    name : 'first_order_check',
    type : 'combobox',
    options : {
        valueField : 'value',
        textField : 'meaning',
        data : YES_NO_OP
    }
};
var special_product = {
    display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.special_product'),
    name : 'special_product',
    type : 'combobox',
    options : {
        valueField : 'value',
        textField : 'meaning',
        data : YES_NO_OP
    }
};
var autoship_creditcard = {
    display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.autoship_creditcard'),
    name : 'autoship_creditcard',
    type : 'combobox',
    options : {
        valueField : 'value',
        textField : 'meaning',
        data : YES_NO_OP
    }
};
var max_order_amount = {
    display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.max_order_amount'),
    name : 'max_order_amount',
    type : 'text'
};
var max_order_weight = {
    display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.max_order_weight'),
    name : 'max_order_weight',
    type : 'text'
};
var receive_bonus = {
    display : $l('type.com.lkkhpg.dsis.common.member.dto.memberdetails.bonusreceive'),
    name : 'receive_bonus',
    type : 'combobox',
    options : {
        valueField : 'value',
        textField : 'meaning',
        data : YES_NO_OP
    }
};

var term_and_condition = {
    display : $l('com.lkkhpg.dsis.common.coupon.dto.coupon.termandcondition'),
    name : 'term_and_condition',
    type : 'combobox',
    options : {
        valueField : 'value',
        textField : 'meaning',
        data : YES_NO_OP
    }
};
var  enable_einvoice = {
    display : $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.enable_einvoice'),
    name : 'enable_einvoice',
    type : 'combobox',
    options : {
        valueField : 'value',
        textField : 'meaning',
        data : YES_NO_OP
    }
};


/*
================================================
加了之后保存报错的
================================================
 */
// var bonus_method = {
//     display : $l('type.com.lkkhpg.dsis.common.member.dto.memberdetails.bonusrcvmethod'),
//     name : 'bonus_method',
//     type : 'combobox',
//     options : {
//         valueField : 'value',
//         textField : 'meaning',
//         data : RECEIVE_METHOD
//     }
// };

//2017/11/10修复结束
/*==============================
 *  LOV
 *=============================
 */
var noted_warehouseman = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.noted_warehouseman'),
    name: 'noted_warehouseman',
    type: "popup",
    options: $.extend(true, noted_warehouseman_op, {
        onSelect: function (datas) {
            var d = datas.data, userNames = [], userIds = [];
            for (var i = 0, len = d.length; i < len; i++) {
                userNames.push(d[i].userName);
                userIds.push(d[i].userId);
            }
            datas.value = userIds.join(';');
            datas.text = userNames.join(';')
        },
        grid: {
            checkbox: true,
            isSingleCheck: false
        }
    })
};
var ipoint_bonus_record_approver = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.ipoint_bonus_record_approver'),
    name: 'ipoint_bonus_record_approver',
    type: "popup",
    options: $.extend({}, $.extend(true, user_op, {
        onSelect: function (datas) {
            var d = datas.data, userNames = [], userIds = [];
            for (var i = 0, len = d.length; i < len; i++) {
                userNames.push(d[i].userName);
                userIds.push(d[i].userId);
            }
            datas.value = userIds.join(';');
            datas.text = userNames.join(';')
        },
        onLoadData: function () {
            this.setParm('userType', 'IPONT');
            this.setParm('status', 'ACTV');
        },
        grid: {
            checkbox: true,
            isSingleCheck: false
        }
    }))
};

var change_market_auditer = {
    display: $l('type.com.lkkhpg.dsis.common.config.dto.orgparamvalue.change_market_auditer'),
    name: 'change_market_auditer',
    type: "popup",
    options: $.extend({}, $.extend(true, user_op, {
        onSelect: function (datas) {
            var d = datas.data, userNames = [], userIds = [];
            for (var i = 0, len = d.length; i < len; i++) {
                userNames.push(d[i].userName);
                userIds.push(d[i].userId);
            }
            datas.value = userIds.join(';');
            datas.text = userNames.join(';')
        },
        onLoadData: function () {
            this.setParm('status', 'ACTV');
        },
        grid: {
            checkbox: true,
            isSingleCheck: false
        }
    }))
};

/**
 * items [{paramName:'xxxxx',options:{}}]
 */
function getParamFields(items) {
    var fields = [];
    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        var varName = item.paramName.replace('SPM.', '').toLowerCase();
        var op = $.extend({}, eval(varName));
        var itemOptions = $.extend(op, item.options);
        if ((i % 3) == 0) {
            itemOptions.newline = true;
        } else {
            itemOptions.newline = false;
        }
        itemOptions.group = item.categoryDesc;
        itemOptions.labelWidth = 150;
        fields[i] = itemOptions;
    }
    return fields;
}


