/**
 * @summary DSIS
 * @description 优惠券js方法集合
 * @version 1.0
 * @author wuyichu
 * @copyright .
 * 
 */

var voucher={
        version:"1.0",
        vouchers:[],
        memberId:"",
        salesOrgId:"",
        discountAmt:"",
        notavalilable:false,
        hasUnStackableVoucher:false,
        appliedVouchers:[],
        availableVouchers:[]
}

//页面初始化
$(function ()
{
    $(document).on("click",".addCoupon",function(){
        if(voucher.notavalilable){
            $.ligerDialog.error($l("msg.warn.pdm.voucher.notavalilablevoucher"));
            return;
        }
        if(voucher.hasUnStackableVoucher){
            $.ligerDialog.error($l("msg.warn.pdm.voucher.unstackable"));
            return;
        }
        var num=$("#couponsAdjust table tr").length+1;
        var str="<div style='float: left; margin-right: 5px'><input id='coupons"+num+"' name='coupons'/><input type='hidden' name='couponsid' id='couponsid'>";
        str+="</div>";
        var tr = $(this).parents('tr');
        var ntr = tr.clone();
        ntr.find(":input").val("");
        ntr.find('.addCoupon').hide();
        ntr.find('.subCoupon').show();
        ntr.find('td:eq(1)').html(str);
        ntr.find('td:eq(2)').html("<label id='couponsText"+num+"'>0</label>")
        tr.after(ntr);
        
         $("#coupons"+num).ligerComboBox({
            css : 'combobox',
            valueField : "voucherId",
            textField : "name",
            cancelable:false,
            autocomplete:false,
            focusWhenSetValue : false,
            onBeforeOpen:function(e){
                setAvailableVouchers(this,num);
            },onBeforeSelect:function(value,text,data){
                var currentValue=this.getValue();
                if(currentValue){
                    if(currentValue!=value){
                        changeVoucher(this.getSelected(),data,num);
                    }
                }else{
                    selectVoucher(data,num);
                }
            }
        });
    });
    $(document).on("click",".subCoupon",function(){ 
        var tr = $(this).parents('tr');
        var id=tr.find("input[id^='coupons']").attr("id");
        if(liger.get(id).getSelected()){
            removeVoucher(liger.get(id).getSelected());
        }
        if(id=="coupons"){
            liger.get(id).clear();
            $("#couponsText").text("0");
            return;
        }
        tr.remove();
    });
});

/**
 * 获取会员可用的优惠券
 * @param memberId 会员ID
 * @param salesOrgId 销售组织ID
 */
function getAllAvailableVouchers(memberId){
    voucher.memberId=memberId||liger.get("memberId").getValue();
    voucher.salesOrgId=liger.get("salesOrgId").getValue();
    var url=_basePath + '/pdm/voucher/getByMember'
    //跨市场购买时用户的优惠券不能使用
    if(liger.get("orderType").getValue()!="NMDP"){
        url=url+'?memberId='+voucher.memberId +'&salesOrgId=' + voucher.salesOrgId;
    }
    //vouchers
    $.ajax({
        type: 'GET',
        url:url,
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            if(data.success){
                var lines=linegrid.getData();
                voucher.vouchers=data.rows;
                for(var i in data.rows){
                    if(data.rows[i].quantity>0&&validateVoucher(data.rows[i],lines)){
                        //优惠券的可用数量
                        data.rows[i].surplus=data.rows[i].quantity;
                        voucher.availableVouchers.push(data.rows[i]);
                    }
                }
                if(voucher.availableVouchers.length<1){
                    voucher.notavalilable=true;
                }
                setAvailableVouchers(liger.get("coupons"),0);
            }
        }
    });
}


/**
 * 获取已经选择的优惠券
 */
function getAppliedVouchers(){
    var appliedVouchers=[];
    var trLength=$("#couponsAdjust table tr").length;
    if(liger.get("coupons").getSelected()){
        appliedVouchers.push(liger.get("coupons").getSelected());
    }
    if(trLength==1){
        return appliedVouchers;
    }
    for(var i=1;i<trLength;i++){
        appliedVouchers.push(liger.get("coupons"+(i+1)).getSelected());
    }
    return appliedVouchers;
}

/**
 * 设置当前下拉框可用的优惠券
 * @param coupon 下拉框对象
 * @param index 头下标
 * @param lineIndex 订单行的下标
 */
function setAvailableVouchers(coupon,index,lineIndex){
    var avalilableVouchers=[];
    var trLength=$("#couponsAdjust table tr").length;
   // var appliedVouchers=getAppliedVouchers();
    for(var i in voucher.availableVouchers){
        
        //优惠券的应用层级--头
        if(!lineIndex){
            //订单头
            if(voucher.availableVouchers[i].applyCriteria=="PRODU"){
                continue;
            }
        }else{
           //订单行
            if(voucher.availableVouchers[i].applyCriteria=="INVOI"){
                continue;
            } 
        }
        
        //不可叠加类型的优惠券
        if(voucher.availableVouchers[i].usageMode=="NSTAC"){
            //如果已经用过优惠券，则不再提供不可叠加的优惠券
            //TODO: 待修改为头与行情况
            if(voucher.appliedVouchers.length>=1&&!voucher.hasUnStackableVoucher&&trLength>1){
                continue;
            }
        }
        var appliedIndex=-1;//使用过的下标
        //遍历是否有使用过的记录
        for(var j in voucher.appliedVouchers){
            if(voucher.appliedVouchers[j].voucherId==voucher.availableVouchers[i].voucherId){
                appliedIndex=j;
                break;
            }
        }
        //如果有使用过的，则校验数量是否有剩余
        if(appliedIndex>=0){
            var appliedVoucher=voucher.appliedVouchers[appliedIndex];
            if(appliedVoucher.surplus>0){
                if(voucher.availableVouchers[i].applyCriteria=="INVOI"&&voucher.availableVouchers[i].type=="AM"){
                  //已经有选择的，先暂时减少优惠金额
                    var tempdiscountAmt=0;
                    if(coupon.getSelected()){
                        if(index==0){
                            tempdiscountAmt=Number($("#couponsText").text()).toFixed(summary.precision);
                        }else{
                            tempdiscountAmt=Number($("#couponsText"+index).text()).toFixed(summary.precision);
                        }
                        summary.discountAmt=summary.discountAmt-Number(tempdiscountAmt);
                    }
                    if(validateVoucher(voucher.availableVouchers[i])){
                        avalilableVouchers.push(voucher.availableVouchers[i]);
                    }
                    summary.discountAmt=summary.discountAmt+Number(tempdiscountAmt);
                }else{
                    avalilableVouchers.push(voucher.availableVouchers[i]);
                }
                
            }
        }else{
            if(voucher.availableVouchers[i].applyCriteria=="INVOI"&&voucher.availableVouchers[i].type=="AM"){
              //已经有选择的，先暂时减少优惠金额
                var tempdiscountAmt=0;
                if(coupon.getSelected()){
                    if(index==0){
                        tempdiscountAmt=Number($("#couponsText").text()).toFixed(summary.precision);
                    }else{
                        tempdiscountAmt=Number($("#couponsText"+index).text()).toFixed(summary.precision);
                    }
                    summary.discountAmt=summary.discountAmt-Number(tempdiscountAmt);
                }
                if(validateVoucher(voucher.availableVouchers[i])){
                    avalilableVouchers.push(voucher.availableVouchers[i]);
                }
                summary.discountAmt=summary.discountAmt+Number(tempdiscountAmt);
            }else{
                avalilableVouchers.push(voucher.availableVouchers[i]);
            }
            
        }
    }
    if(coupon.getSelected()){
        var isInclude=false;
        var selectVoucher=coupon.getSelected();
        for(var i in avalilableVouchers){
            if(avalilableVouchers[i].voucherId==selectVoucher.voucherId){
                isInclude=true;
                break;
            }
        }
        if(!isInclude){
            avalilableVouchers.push(coupon.getSelected());
        }
    }
    if(avalilableVouchers.length>=1){
        coupon.set({data:avalilableVouchers});
    }else{
        voucher.notavalilable=true;
        coupon.setText($l("msg.warn.pdm.voucher.notavalilablevoucher"));
    }
    
    return avalilableVouchers;
}

/**
 * 判断该优惠券是否可用
 * @param validateVoucher 优惠券
 * @param lines 订单行
 * @return true/false 可用为true，不可用为false
 */
function validateVoucher(validateVoucher,lines){
    //订单头金额
    if(validateVoucher.applyCriteria=="INVOI"&&validateVoucher.type=="AM"){
        var checkAmt;
        if(validateVoucher.useCriteria =="ECLD"){//不含税
            checkAmt=summary.currency-summary.discountAmt-summary.tax;
        }else{//含税
            checkAmt=summary.currency-summary.discountAmt;
        }
        if(validateVoucher.operation=="MT"&&checkAmt>validateVoucher.orderAmount){
            return true;
        }else if(validateVoucher.operation=="MOE"&&checkAmt>=validateVoucher.orderAmount){
            return true;
        }else if(validateVoucher.operation=="LT"&&checkAmt<validateVoucher.orderAmount){
            return true;
        }else if(validateVoucher.operation=="LOE"&&checkAmt<=validateVoucher.orderAmount){
            return true;
        }else if(validateVoucher.operation=="EQL"&&validateVoucher.orderAmount==checkAmt){
            return true;
        }else{
            return false;
        }
    }else if(validateVoucher.applyCriteria=="INVOI"&&validateVoucher.type=="NM"){
        //订单头数量处理对比
        for(var i in validateVoucher.pdmVoucherItems){
            var voucherItem=validateVoucher.pdmVoucherItems[i];
            var temp=false;
            for(var j in lines){
                if(lines[j].itemId==voucherItem.itemId&&lines[j].quantity>=voucherItem.quantity){
                    temp=true;
                    break;
                }
            }
            if(!temp){
                return false;
            }
        }
        return true;
    }else if(validateVoucher.applyCriteria=="PRODU"&&validateVoucher.type=="PD"){
        //订单行处理对比
        for(var i in validateVoucher.pdmVoucherItems){
            var voucherItem=validateVoucher.pdmVoucherItems[i];
            var temp=false;
            for(var j in lines){
                if(lines[j].itemId==voucherItem.itemId&&lines[j].quantity>=voucherItem.quantity){
                    return true;
                }
            }
        }
        return false;
    }else{
        return false;
    }
}

/**
 * 优惠券选中
 * @param selectVoucher 选择的优惠券
 * @param index 订单行的下标
 */
function selectVoucher(selectVoucher,index){
    if(selectVoucher.applyCriteria=="INVOI"){
      //头类型的优惠券
        
        //不可叠加类型的头优惠券处理
        if(selectVoucher.usageMode=="NSTAC"){
            voucher.hasUnStackableVoucher=true;
        }
        var isUsed=false;//是否已经有使用过
        for(var i in voucher.appliedVouchers){
            if(voucher.appliedVouchers[i].voucherId==selectVoucher.voucherId){
                isUsed=true;
                //可用数量-1
                voucher.appliedVouchers[i].surplus=voucher.appliedVouchers[i].surplus-1;
                break;
            }
        }
        if(!isUsed){
            //可用数量-1
            selectVoucher.surplus=selectVoucher.surplus-1;
            voucher.appliedVouchers.push(selectVoucher);
        }
        
        //界面操作，金额扣减等.
        if(selectVoucher.discountType=="FIX"){
            if(index==0){
                $("#couponsText").html("-" + selectVoucher.discountValue);
            }else if(index&&index>0){
                $("#couponsText"+index).html("-" + selectVoucher.discountValue);
            }
            summary.discountAmt=summary.discountAmt+selectVoucher.discountValue;
            if(summary.discountAmt>summary.currency){
                $.ligerDialog.error($l('msg.error.pdm.voucher.morethenorderamount'));
            }else{
                if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()!="SHIPP"){
                    if(Number((summary.currency+summary.adjustMents-summary.discountAmt))>=0){
                        $("#actrualPayAmt").text(Number((summary.currency+summary.adjustMents-summary.discountAmt)).toFixed(summary.precision));
                    }else{
                        Hap.showError($l("msg.error.om.actual_payment_amount_error"));
                        $("#actrualPayAmt").text("0");
                    }
                    
                }else{
                    if(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt))>=0){
                        $("#actrualPayAmt").text(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt)).toFixed(summary.precision));
                    }else{
                        Hap.showError($l("msg.error.om.actual_payment_amount_error"));
                        $("#actrualPayAmt").text("0");
                    }
                    
                }
            }
        }else if(selectVoucher.discountType=="PERCE"){
            var selectDiscountAmt=Number(((summary.currency-summary.discountAmt)*(selectVoucher.discountValue/100)).toFixed(summary.precision));
            summary.discountAmt=summary.discountAmt+selectDiscountAmt;
            if(index==0){
                $("#couponsText").html("-" + selectDiscountAmt);
            }else if(index&&index>0){
                $("#couponsText"+index).html("-" + selectDiscountAmt);
            }
            if(summary.discountAmt>summary.currency){
                $.ligerDialog.error($l('msg.error.pdm.voucher.morethenorderamount'));
                removeVoucher(selectVoucher,index);
                return;
            }else{
                if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()!="SHIPP"){
                    if(Number((summary.currency+summary.adjustMents-summary.discountAmt))>=0){
                        $("#actrualPayAmt").text(Number((summary.currency+summary.adjustMents-summary.discountAmt)).toFixed(summary.precision));
                    }else{
                        Hap.showError($l("msg.error.om.actual_payment_amount_error"));
                        $("#actrualPayAmt").text("0");
                    }
                    
                }else{
                    if(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt))>=0){
                        $("#actrualPayAmt").text(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt)).toFixed(summary.precision));
                    }else{
                        Hap.showError($l("msg.error.om.actual_payment_amount_error"));
                        $("#actrualPayAmt").text("0");
                    }
                    
                }
            }
        }else if(selectVoucher.discountType=="GIFT"){
            //TODO：礼物类型
        }
       
        return;
    }else{
      //行类型的优惠券
        var isUsed=false;//是否已经有使用过
        for(var i in voucher.appliedVouchers){
            if(voucher.appliedVouchers[i].voucherId==selectVoucher.voucherId){
                isUsed=true;
                //可用数量-1
                voucher.appliedVouchers[i].surplus=voucher.appliedVouchers[i].surplus-1;
                break;
            }
        }
        if(!isUsed){
            //可用数量-1
            selectVoucher.surplus=selectVoucher.surplus-1;
            voucher.appliedVouchers.push(selectVoucher);
        }
      //界面操作，金额扣减等.
        //TODO:
    }
}

/**
 * 更改使用的优惠券
 * @param oldVoucher 旧的优惠券
 * @param newVoucher 新的优惠券
 * @param index 订单行的下标
 */
function changeVoucher(oldVoucher,newVoucher,index){
    removeVoucher(oldVoucher,index);
    selectVoucher(newVoucher,index);
}

/**
 * 删除一张使用的优惠券
 * @param removed 删除的优惠券
 * @param index 订单行的下标
 */
function removeVoucher(removed,index){
    var isAddToAvailable=false;//是否添加回可用优惠券标记
    if(removed.usageMode=="NSTAC"){
        voucher.hasUnStackableVoucher=false;
    }
    for(var i in voucher.appliedVouchers){
        if(voucher.appliedVouchers[i].voucherId==removed.voucherId){
            //可用数量+1
            voucher.appliedVouchers[i].surplus=voucher.appliedVouchers[i].surplus+1;
            //最大可用量是优惠券数量时
            if(voucher.appliedVouchers[i].surplus<=voucher.appliedVouchers[i].quantity){
              //当前可用量与优惠券的可用量相等时，说明该优惠券没有被使用，须从使用池删除.
                if(voucher.appliedVouchers[i].surplus==voucher.appliedVouchers[i].quantity){
                    voucher.appliedVouchers.splice(i,1);
                }
                isAddToAvailable=true;
            }
            break;
        }
    }
    //可用优惠券添加
    if(isAddToAvailable){
        var isInclude=false;
        for(var i in voucher.availableVouchers){
            if(voucher.availableVouchers[i].voucherId==removed.voucherId){
                isInclude=true;
                break;
            }
        }
        if(!isInclude){
            voucher.availableVouchers.push(removed);
        }
    }
    if(removed.discountType=="FIX"){
        summary.discountAmt=summary.discountAmt-removed.discountValue;
        if(summary.discountAmt<0){
            summary.discountAmt=0;
        }
        if(summary.discountAmt>summary.currency){
            $.ligerDialog.error($l('msg.error.pdm.voucher.morethenorderamount'));
        }else{
            if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()!="SHIPP"){
                if(Number((summary.currency+summary.adjustMents-summary.discountAmt))>=0){
                    $("#actrualPayAmt").text(Number((summary.currency+summary.adjustMents-summary.discountAmt)).toFixed(summary.precision));
                }else{
                    Hap.showError($l("msg.error.om.actual_payment_amount_error"));
                    $("#actrualPayAmt").text("0");
                }
                
            }else{
                if(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt))>=0){
                    $("#actrualPayAmt").text(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt)).toFixed(summary.precision));
                }else{
                    Hap.showError($l("msg.error.om.actual_payment_amount_error"));
                    $("#actrualPayAmt").text("0");
                }
                
            }
        }
    }else if(removed.discountType=="PERCE"){
        if(index==0){
            summary.discountAmt=summary.discountAmt-Number($("#couponsText").text()).toFixed(summary.precision);
        }else{
            summary.discountAmt=summary.discountAmt-Number($("#couponsText"+index).text()).toFixed(summary.precision);
        }
        if(summary.discountAmt<0){
            summary.discountAmt=0;
        }
        if(summary.discountAmt>summary.currency){
            $.ligerDialog.error($l('msg.error.pdm.voucher.morethenorderamount'));
        }else{
            if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()!="SHIPP"){
                if(Number((summary.currency+summary.adjustMents-summary.discountAmt))>=0){
                    $("#actrualPayAmt").text(Number((summary.currency+summary.adjustMents-summary.discountAmt)).toFixed(summary.precision));
                }else{
                    Hap.showError($l("msg.error.om.actual_payment_amount_error"));
                    $("#actrualPayAmt").text("0");
                }
                
            }else{
                if(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt))>=0){
                    $("#actrualPayAmt").text(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt)).toFixed(summary.precision));
                }else{
                    Hap.showError($l("msg.error.om.actual_payment_amount_error"));
                    $("#actrualPayAmt").text("0");
                }
                
            }
        }
    }else if(removed.discountType=="GIFT"){
        //TODO：礼物类型
    }
    return;
}


/**
 * 获取使用的优惠券集合.
 * @return array
 */
function getUseVoucher(){
    var results=[];
    for(var i in voucher.appliedVouchers){
        var appliedVoucher=voucher.appliedVouchers[i];
        for(var index=appliedVoucher.surplus;index<appliedVoucher.quantity;index++){
            results.push(appliedVoucher);
        }
    }
    return results;
}

/**
 * 设置订单已使用的优惠券
 * @param useVouchers 已使用的优惠券
 */
function setUseVouchers(useVouchers){
    if(!useVouchers){
        return;
    }
    var tr;
    if(useVouchers[0]){
        tr = $("#coupons").parents('tr');
        tr.find('td:eq(1)').html(useVouchers[0].name);
        if(useVouchers[0].discountType=="FIX"){
            summary.discountAmt=summary.discountAmt+useVouchers[0].discountValue;
            tr.find('td:eq(2)').html("-" + useVouchers[0].discountValue)
        }else if(useVouchers[0].discountType=="PERCE"){
            var disCountValue=Number((orderDetail.orderAmt*(selectVoucher.discountValue/100)).toFixed(summary.precision));
            summary.discountAmt=summary.discountAmt+disCountValue;
            tr.find('td:eq(2)').html("-" + disCountValue);
        }
        
    }else{
        return;
    }
    if(useVouchers.length>1){
        var num=$("#couponsAdjust table tr").length+1;
        for(var i=1;i<useVouchers.length;i++){
            var ntr = tr.clone();
            ntr.find('td:eq(1)').html(useVouchers[i].name);
            if(useVouchers[0].discountType=="FIX"){
                summary.discountAmt=summary.discountAmt+useVouchers[i].discountValue;
                ntr.find('td:eq(2)').html("-" + useVouchers[i].discountValue)
            }else if(useVouchers[0].discountType=="PERCE"){
                var disCountValue=Number((orderDetail.orderAmt*(selectVoucher.discountValue/100)).toFixed(summary.precision));
                summary.discountAmt=summary.discountAmt+disCountValue;
                ntr.find('td:eq(2)').html("-" + disCountValue);
            }
            tr.after(ntr);
        }
    }
    if(liger.get("deliveryType")&&liger.get("deliveryType").getValue()!="SHIPP"){
        if(Number((summary.currency+summary.adjustMents-summary.discountAmt))>=0){
            $("#actrualPayAmt").text(Number((summary.currency+summary.adjustMents-summary.discountAmt)).toFixed(summary.precision));
        }else{
            Hap.showError($l("msg.error.om.actual_payment_amount_error"));
            $("#actrualPayAmt").text("0");
        }
        
    }else{
        if(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt))>=0){
            $("#actrualPayAmt").text(Number((summary.currency+summary.shippingTier+summary.adjustMents-summary.discountAmt)).toFixed(summary.precision));
        }else{
            Hap.showError($l("msg.error.om.actual_payment_amount_error"));
            $("#actrualPayAmt").text("0");
        }
        
    }
}