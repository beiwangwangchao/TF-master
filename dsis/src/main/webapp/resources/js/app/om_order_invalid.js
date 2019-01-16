/**
 * @summary DSIS
 * @description 订单失效按钮js方法集合
 * @version 1.0
 * @author gulin
 * @copyright Copyright LKK Health Products Group Limited.
 * 
 */
	var headerId = 0;
	var amount = 0;
	var amountLess = 0;
	var market ;
	var balance = 0;
	var payments;
	var paymentMethodFixed = [];
	var payMethodList = [];
	var ajaxTemp1 = null;
	var ajaxTemp2 = null;
	var ajaxTemp3 = null;
	
	/**
	 * 2016-08-23 houmin 订单详情页,已完成订单【修改支付信息】逻辑.
	 * add 1.获取订单支付信息，修改后更新订单支付信息.
	 */
	function getPaymentOfEditPaymentMth() {
		headerId =  $("#headerId").val();
		if(!headerId){
            return;
        }
		isCod = liger.get("isCod").getValue();
		//货到付款
		if (isCod) {
			Hap.showError($l("msg.warning.om.paymentedit.podel.not_allow_edit"));
			return false;
		}
		loadEditform();
		getMethod(true);
		$.ajax({
			url: _basePath + "/om/orderinfo/queryPayments?headerId="+headerId,
			type:"POST",
			dataType:"json",
			contentType : "application/json",
            success : function(data) {
            	if(data.rows[0] == 'error'){
            		//获取支付信息错误
            		Hap.showError($l("msg.warning.om.paymentedit.get.payment_error"));
            	}else{
        			amount = 0;
                	payments = null;
                	for(var i = 0 ; i< data.total ; i++){
                		amount += data.rows[i].paymentAmount;
                	}
                	if (amount) {
                		payments = data;
                		invalid2 = window["invalid2"] = $("#invalid2").ligerGrid(initInvalidGrid(true));
                		dialog_editPayMethod();
                	} else {
                		//订单没有支付信息可修改
                		Hap.showError($l("msg.warning.om.paymentedit.not_allow_edit"));
                		return false;
                	}
            	}
            },
            error : function() {
            	//不付款修改支付信息条件
            	Hap.showError($l("type.com.lkkhpg.dsis.common.order.error.edit_paymethod"));
            }
		});
	}
	
	/**
	 * 2016-08-23 houmin add 2.支付信息修改弹出框.
	 */
	function dialog_editPayMethod() {
		$.ligerDialog.open({
			target: $("#invalidFrame"),
			title: $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.paymentinfo"),
			width: 1020,
	        height: 500,
	        modal: true,
	        isResize : true,
	        buttons : 
	        	[{
	        		 text: $l("sys.hand.btn.ok"),
	        		 onclick: function (i,d){
	        			 var checkoutBalance = liger.get("amountsform").getData().invalidBalance;
	        			 if(!checkoutInfo(invalid2.getData())){
	        				 //金額有誤或資料不完整
	        				 Hap.showError($l("type.com.lkkhpg.dsis.common.order.orderinvalid.infoerror"));
	        				 return false;
	        			 }
	        			 if(checkoutBalance != 0){
	        				 //退款金额总额不等于实付款
	        				 Hap.showError($l("type.com.lkkhpg.dsis.common.order.orderinvalid.errorrefundamounts"));
	        				 return false;
	        			 }else{
	        				 var dataStr = invalid2.currentData.rows;
		        			 $.ajax({
		        				    // 更新支付信息
		        					url: _basePath + "/om/update/paymentInfo?orderHeaderId="+headerId,
		        					type:"POST",
		        					dataType:"json",
		        					contentType : "application/json",
		        					data : JSON2.stringify(dataStr),
		        		            success : function(data) {
		        		            	if(data.success){
		        		            		Hap.showSuccess($l("msg.info.om.paymethod.edit_successful"), function() {
		        		            			location.reload();  
		        							});
		        		            		d.hide();
		        		            	}
		        		            },
		        		            error : function() {
		        		            	//更新支付信息出错
		        		                Hap.showError($l("type.com.lkkhpg.dsis.common.order.error.update_payment"));
		        		            }
		        				});
	        			 }
	        		 }
	        	 },{
	        		 text: $l("sys.hand.btn.cancel"), 
	        		 onclick: function (i,d){
	        			 d.hide();
	        		 }
	        }]
		});
	}
	/**
	 * 20161010 houmin update 支付信息修改中支付方式和退款支付方式获取.
	 * @param param 是否是支付信息修改中支付方式获取.
	 * true-是；false-否
	 */
	function getMethod(param){
		payMethodList = [];
		paymentMethodFixed = [];
		if (param) {//支付信息按钮支付方式
			payMethodList = modifyPayinfoPaymethod;
		} else {//失效支付方式
			payMethodList = voidPaymethodData;
		}
		//add 支付方式-取组织参数中维护支付方式，与当前支付方式取交集
		for (var e=0; e<orgParamPayMethod.length; e++) {
			for (var f=0; f<payMethodList.length; f++) {
				if (orgParamPayMethod[e] == payMethodList[f].value) {
					paymentMethodFixed.push(payMethodList[f]);
				}
			}
		}
	}
	
	//获取市场组织参数
	function getMarket(headerId){
		headerId =  $("#headerId").val();
		if(!headerId){
            return;
        }
		loadEditform();
		loadAmountsform();
		$.ajax({
			url: _basePath + "/om/order/querySpmRefund?headerId="+headerId,
			type:"POST",
			dataType:"json",
			contentType : "application/json",
            success : function(data) {
            	if(data.success){
            		market = data.rows[0];
            		getMethod(false);
            		getPayment();
            	}
            },
            error : function() {
                alert("error");
            }
		});
	}
	
	//判断订单是否可以失效，若可以，执行退款流程
	function getPayment(){
		headerId =  $("#headerId").val();
		isCod = liger.get("isCod").getValue();
		$.ajax({
			url: _basePath + "/om/order/queryPayments?headerId="+headerId,
			type:"POST",
			dataType:"json",
			contentType : "application/json",
            success : function(data) {
            	if(data.rows[0] == 'error'){
            		$.ligerDialog.error($l("type.com.lkkhpg.dsis.common.order.orderinvalid.refundConditions"));
            	}else{
            		if (isCod) {
            			payments = data.rows;
            			invalidDialog3();
            		}else{
            			amount = 0;
            			amountLess = 0;
                    	payments = null;
                    	for(var i = 0 ; i< data.total ; i++){
                    		amount += data.rows[i].paymentAmount;
                    		if ("EBPAY" != data.rows[i].paymentMethod && "RBPAY" != data.rows[i].paymentMethod
                    				&& "ECUP" != data.rows[i].paymentMethod) {
                    			amountLess += data.rows[i].paymentAmount;
                    		}
                    	}
                    	payments = data;
                    	if(market == "Y"){
                    		invalidDialog1();
                    	}else {
                    		invalid2 = window["invalid2"] = $("#invalid2").ligerGrid(initInvalidGrid());
                    		invalidDialog2();
                    	}
            		}
            	}
            },
            error : function() {
                alert("error");
            }
		});
	}
	
	
	//生成马来西亚地区退款窗口
	function invalidDialog1(){
		$("#invalid_lable").text(amountLess+$l("type.com.lkkhpg.dsis.common.order.orderinvalid.refundtoremain"));
		$.ligerDialog.open({
			target: $("#invalid1"),
			title: $l("type.com.lkkhpg.dsis.common.order.orderinvalid.refund"),
			width: 400,
	        height: 200,
	        modal: true,
	        buttons : 
	        	[
	        	 {
	        		 text: $l("sys.hand.btn.ok"), 
	        		 onclick: function (i,d){
	        			 var remarkText = JSON2.stringify($("#invalid_text").val());
	        			 if(ajaxTemp1 && ajaxTemp1.readyState != 4) return;
	        			 ajaxTemp1 = $.ajax({
	        					url: _basePath + "/om/order/invalidToRemaining?headerId="+headerId,
	        					type:"POST",
	        					dataType:"json",
	        					contentType : "application/json",
	        					data : remarkText,
	        		            success : function(data) {
	        		            	if(data.success){
	        		            		$.ligerDialog.success($l("type.com.lkkhpg.dsis.common.order.orderinvalid.refundsuccess"), function() {
	        		            			location.reload();  
	        							});
	        		            		d.hide();
	        		            	}
	        		            },
	        		            error : function() {
	        		                alert("error");
	        		            }
	        				});
	        			 $("#invalid_text").val("");
	        		 }
	        	 },
	        	 {
	        		 text: $l("sys.hand.btn.cancel"), 
	        		 onclick: function (i,d){
	        			 $("#invalid_text").val("");
	        			 d.hide();
	        		 }
	        	 }
	        	 ]
		});
	} 
	
	//加载退款金额表单
	function loadAmountsform(param){
		var str = "type.com.lkkhpg.dsis.common.order.orderinvalid.refundamounts";
		if (param) {
			str = "type.com.lkkhpg.dsis.common.order.dto.orderpayment.paymentamount";
		}
	    amountsform = $("#amountsform").ligerForm({
	        inputWidth : 80,
	        labelWidth : 80,
	        space : 40,
	        align : 'center',
	        labelAlign : 'center',
	        readonly : true,
	        fields : [{
	            display : $l(str),
	            name : "invalidAmount",
	            newline : true,
	            type : 'text',
	        },{
	            display : $l("type.com.lkkhpg.dsis.common.member.dto.accountingtrx.balance"),
	            name : "invalidBalance",
	            newline : false,
	            type : 'text',
	        }
	        ]
	    });
	}
	
	//生成非马来地区退款窗口
	//var invalid2 = window["invalid2"] = $("#invalid2").ligerGrid(initInvalidGrid());
	function initInvalidGrid(param){
		var str = "type.com.lkkhpg.dsis.common.order.orderinvalid.refundamounts";
		if (param) {
			str = "type.com.lkkhpg.dsis.common.inv.dto.transaction.totalprice";
		}
		loadAmountsform(param);
		var tempAmount = 0;
		var tempbalance = 0;
		liger.get("amountsform").setData({
			invalidAmount : amount,
			invalidBalance : balance,
		})
		var options = {
			columns : [{
				display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.paymentmethod"), 
				name : 'paymentMethod',
				align : 'center', 
				width : 260,
				editor :  {
					type: 'combobox' ,
					data : paymentMethodFixed,
	                valueField : 'value',
	                textField : 'meaning',
	                onChanged : function(data){
						invalid2.updateRow(invalid2.getSelected(),{
							paymentMethodInfo : "",		//支付方式具体信息：POS机、银行
          		   		  	transactionNumber : "", 	//交易凭证号
          		   		  	bankCode : "",				//银行
          		   		  	creditCardType : "",		//信用卡类型
          		   		  	tailNumber : "",			//银行卡尾号
          		   		  	remark : "",				//备注
						})
					},
				}, 
				
				render: function (item) {//单元格渲染器
	                for (var i = 0; i < allPayMethod.length; i++)
	                {
	                 	if (allPayMethod[i].value == item.paymentMethod)
	                    	return allPayMethod[i].meaning;
	              	}
	         		return "";
		      	},
			},{
				display : $l(str),
				align : 'center',
				name : 'paymentAmount',
				width : 260,
				type: 'float', 
				editor: {
					type: 'float',
				},
				totalSummary : {
					render:function(suminf,column) {
					    //动态更新余款
					    liger.get("amountsform").setData({
					    	invalidBalance : (amount-suminf.sum),
			    		});
					}
				}
			},{
				display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.otherinfo"),
				name : "transactionNumber", 
				align : 'center',
				render : gridRender,
			}],
			data:payments,
			enabledEdit: true,
			width:980,
			height : 400,
			usePager : false,
			onBeforeEdit : function (e) {
				if (e.column.columnname == "paymentAmount") {
					tempAmount = e.value;
					tempbalance = liger.get("amountsform").getData().invalidBalance;
				}
				if ("ECUP" == e.record.paymentMethod || "EBPAY" == e.record.paymentMethod || "RBPAY" == e.record.paymentMethod || "ONLPA" == e.record.paymentMethod || "CDCRD" == e.record.paymentMethod) {
					if (e.column.columnname == "paymentAmount" || e.column.columnname == "paymentMethod") {
						invalid2.setCellEditing(e.record, e.column, false);
						return false;
					}
				}
			},
			onBeforeSubmitEdit: function (e){
				if (e.column.columnname == "paymentAmount") {
					if (e.value < 0) {
						$.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.orderinvalid.amountError'));
						return false;
					}
					if(e.value > (tempbalance + tempAmount)){
						$.ligerDialog.error($l('msg.error.order.amount_beyond'));
						return false;
					}
					return true;
				}
			},
			onAfterAddRow : function(e) {
				invalid2.updateCell('paymentAmount', liger.get("invalidBalance").getValue(), e);
				liger.get("invalidBalance").setValue(0);
				if($("#balance")[0].textContent == 0) {
					saveEditButton.setEnabled();
				}
			},
			toolbar: 
			{ items: [
						{ text: $l("sys.hand.btn.new"),  icon: 'add',
						  click : function(){invalid2.addRow({
							  orderHeaderId : headerId, 	//订单头ID
              		   	      salesOrgId : payments.rows[0].salesOrgId,	//销售区域ID
              		   		  paymentMethod : 'CASH',	//支付方式
              		   		  paymentMethodInfo : "",		//支付方式具体信息：POS机、银行
            		   		  paymentAmount : "",			//支付金额
            		   		  transactionNumber : "", 	//交易凭证号
            		   		  bankCode : "",				//银行
            		   		  creditCardType : "",		//信用卡类型
            		   		  tailNumber : "",			//银行卡尾号
            		   		  remark : "",				//备注
						});
						  }},
						{ line: true },
						{ text: $l("sys.hand.btn.delete"),  icon: 'delete' , click : function(){invalid2.deleteSelectedRow();}}
			          ]
			}
		};
		return options;
	}
	
	
	function invalidDialog2(){
		$.ligerDialog.open({
			target: $("#invalidFrame"),
			title: $l("type.com.lkkhpg.dsis.common.order.orderinvalid.refund"),
			width: 1020,
	        height: 500,
	        modal: true,
	        isResize : true,
	        buttons : 
	        	[
	        	 {
	        		 text: $l("sys.hand.btn.ok"), 
	        		 onclick: function (i,d){
	        			 var checkoutBalance = liger.get("amountsform").getData().invalidBalance;
	        			 if(!checkoutInfo(invalid2.getData())){
	        				 $.ligerDialog.error($l("type.com.lkkhpg.dsis.common.order.orderinvalid.infoerror"));
	        				 return false;
	        			 }
	        			 if(checkoutBalance != 0){
	        				 $.ligerDialog.error($l("type.com.lkkhpg.dsis.common.order.orderinvalid.errorrefundamounts"));
	        			 }else{
	        				 var dataStr = JSON2.stringify(invalid2.getData());
	        				 if(ajaxTemp2 && ajaxTemp2.readyState != 4) return;
		        			 ajaxTemp2 = $.ajax({
		        					url: _basePath + "/om/order/updatePaymentRefund?headerId="+headerId,
		        					type:"POST",
		        					dataType:"json",
		        					contentType : "application/json",
		        					data : dataStr ,
		        		            success : function(data) {
		        		            	if(data.success){
		        		            		$.ligerDialog.success($l("type.com.lkkhpg.dsis.common.order.orderinvalid.refundsuccess"), function() {
		        		            			location.reload();  
		        							});
		        		            		d.hide();
		        		            	}
		        		            },
		        		            error : function() {
		        		                alert("error");
		        		            }
		        				});
	        			 }
	        		 }
	        	 },
	        	 {
	        		 text: $l("sys.hand.btn.cancel"), 
	        		 onclick: function (i,d){
	        			 d.hide();
	        		 }
	        	 }
	        	 ]
		});
	}
	
	function invalidDialog3(){
		$.ligerDialog.confirm($l("type.com.lkkhpg.dsis.common.order.orderinvalid.confirminvalid"), function (yes){
			if(yes){
				if(ajaxTemp3 && ajaxTemp3.readyState != 4) return;
				ajaxTemp3 = $.ajax({
					url: _basePath + "/om/order/updatePaymentRefund?headerId="+headerId,
					type:"POST",
					dataType:"json",
					contentType : "application/json",
					data : JSON2.stringify(payments) ,
		            success : function(data) {
		            	if(data.success){
		            		$.ligerDialog.success($l("type.com.lkkhpg.dsis.common.order.orderinvalid.refundsuccess"), function() {
		            			location.reload();  
							});
		            		d.hide();
		            	}
		            },
		            error : function() {
		                alert("error");
		            }
				});
			}
		});
	}
	
	//表格渲染函数
	function gridRender(record, rowindex, value, column){
		var payMethod = record.paymentMethod +"";
		if(record.paymentMethod == 'CASH' || record.paymentMethod == 'CDCRD' || record.paymentMethod == 'ONLPA'){
			var remark = $("#remarkText").clone();
			var remarkInput = remark.find('input')[0];
			$(remarkInput).attr("id","remark_"+rowindex);
			$(remarkInput).attr("value",record.remark);
			$(remarkInput).attr("onblur","javascript:setGridData(this,"+rowindex+");");
			return remark.html();
		}else if (record.paymentMethod == 'CREDI'){
			var credit = $("#creditText").clone();
			var creditTypeInput = credit.find('input')[0];
			$(creditTypeInput).attr("id","credit_card_type_"+rowindex);
			$(creditTypeInput).attr("value",getCreditCardType(record.creditCardType));
			var creditNumber = credit.find('input')[1];
			$(creditNumber).attr("id","tail_number_"+rowindex);
			$(creditNumber).attr("value",record.tailNumber);
			var editButton = credit.find('input')[2];
			$(editButton).attr("onclick","javascript:editInfo("+rowindex+",\""+record.paymentMethod+"\");");
			return credit.html();
		}else if (record.paymentMethod == 'DBCRD'){
			var debit = $("#debitText").clone();
			var bankNameInput = debit.find('input')[0];
			$(bankNameInput).attr("id","bank_code_"+rowindex);
			$(bankNameInput).attr("value",record.bankCode);
			//$(bankNameInput).attr("value",getBankName(record.bankCode));
			var tailNumber = debit.find('input')[1];
			$(tailNumber).attr("id","tail_number_"+rowindex);
			$(tailNumber).attr("value",record.tailNumber);
			var editButton = debit.find('input')[2];
			$(editButton).attr("onclick","javascript:editInfo("+rowindex+",\""+record.paymentMethod+"\");");
			return debit.html();
		}else if (record.paymentMethod == 'REMIT' || record.paymentMethod == 'CHEQU'){
			var cheque = $("#chequeText").clone();
			var bankNameInput = cheque.find('input')[0];
			$(bankNameInput).attr("id","bank_code_"+rowindex);
			$(bankNameInput).attr("value",record.bankCode);
			//$(bankNameInput).attr("value",getBankName(record.bankCode));
			var transactionNumber = cheque.find('input')[1];
			$(transactionNumber).attr("id","transaction_number_"+rowindex);
			$(transactionNumber).attr("value",record.transactionNumber);
			var editButton = cheque.find('input')[2];
			$(editButton).attr("onclick","javascript:editInfo("+rowindex+",\""+record.paymentMethod+"\");");
			return cheque.html();
		} else if (record.paymentMethod == 'EBPAY' || record.paymentMethod == 'RBPAY') {
			var remark = $("#remarkText").clone();
			var remarkInput = remark.find('input')[0];
			$(remarkInput).attr("id","remark_"+rowindex);
			$(remarkInput).attr("value",record.remark);
			$(remarkInput).attr("onblur","javascript:setGridData(this,"+rowindex+");");
			return remark.html();
		} else if (record.paymentMethod == 'ECUP') {
			var ecupon = $("#ecuponText").clone();
			var remarkInput = ecupon.find('input')[1];
			$(remarkInput).attr("id","remark_"+rowindex);
			$(remarkInput).attr("value",record.remark);
			$(remarkInput).attr("onblur","javascript:setGridData(this,"+rowindex+");");
			var ecuponInfo = ecupon.find('input')[0];
			$(ecuponInfo).attr("id","ecupon_"+rowindex);
			$(ecuponInfo).attr("value",record.discountValue + "  " + record.name);
			return ecupon.html();
		} else {
			var remark = $("#remarkText").clone();
			var remarkInput = remark.find('input')[0];
			$(remarkInput).attr("id","remark_"+rowindex);
			$(remarkInput).attr("value",record.remark);
			$(remarkInput).attr("onblur","javascript:setGridData(this,"+rowindex+");");
			return remark.html();
		}
	}
	
	//渲染信用卡类型中文名字段
	function getCreditCardType(value){
		for(var i = 0 ; i < creditCardType.length ; i++){
			if(creditCardType[i].value == value){
				return creditCardType[i].meaning;
			}
		}
	}
	
	//设置备注内容
	function setGridData(obj,rowindex) {
		var id = $(obj).attr("id");
		var value = $(obj).val();
		// 设置Grid
		invalid2.updateRow(invalid2.getRow(rowindex),{
	   		remark : value,		//备注
		});
	}
	
	
	
	function loadEditform(){
	  //加载其他信息详情表单
		var cardType = [{"value":"VISA","meaning":"VISA"},{"value":"MASTER","meaning":"MASTER"}];
	    editform = window.editform = $("#editform").ligerForm({
	        inputWidth : 100,
	        labelWidth : 80,
	        space : 40,
	        rightToken : ' ',
	        fields : [{
	            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.creditcardtype"),
	            name : 'creditCardType',
	            newline : true,
	            type : 'combobox',
	            options : {
	                valueField : 'value',
	                textField : 'meaning',
	                data : creditCardType,
	            },
	            validate : {
	                required : true
	            }
	        },{
	            display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.bank"),
	            name : 'bankCode',
	            newline : false,
	            type : "text",
	            /*options : {
	                valueField : 'value',
	                textField : 'meaning',
	                data : bankBelongData,
	            },
	            validate : {
	                required : true
	            }*/
	        },{
	            display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.tailnumber"),
	            name : 'tailNumber',
	            newline : true,
	            type : 'text',
	            validate : {
	                required : true,
	                minlength : 4,
	                maxlength : 4
	            }
	        },{
	            display : $l("type.com.lkkhpg.dsis.common.order.orderinvalid.pos"),
	            name : 'paymentMethodInfo',
	            newline : false,
	            type : "combobox",
	            options : {
	                valueField : 'value',
	                textField : 'meaning',
	                data : posSelectData,
	            }/*,
	            validate : {
	                required : true
	            }*/
	        },{
	            display : $l("type.com.lkkhpg.dsis.common.order.dto.orderpayment.transactionnumber"),
	            name : 'transactionNumber',
	            newline : true,
	            type : 'text',
	            width : 200
	        },{
	            display : $l("type.com.lkkhpg.dsis.common.order.dto.salesorder.remark"),
	            name : 'remark',
	            newline : true,
	            type : 'text',
	            width : 200
	        }]
	    });
	}
	
	
	//加载其他信息详情弹框
	function editInfo(value,type){
		var rowData = invalid2.getRow(value);
		if(type == "CREDI") {
			//editform.setVisible(["bankCode"],false);
		} else if(type == "DBCRD") {
			//editform.setVisible(["creditCardType"],false);
		} else if(type == "CHEQU" || type == "REMIT" ) {
			editform.setVisible(["creditCardType","tailNumber","paymentMethodInfo"],false);
		}
		//将编辑行的数据放入到表格中
		liger.get("editform").setData({
			creditCardType : rowData.creditCardType,
			bankCode : rowData.bankCode,
			tailNumber : rowData.tailNumber,
			paymentMethodInfo : rowData.paymentMethodInfo,
			transactionNumber : rowData.transactionNumber,
			remark : rowData.remark
		})
		$.ligerDialog.open({
			target: $("#invalid3"),
			title: $l("type.com.lkkhpg.dsis.common.order.orderinvalid.refund"),
			width: 500,
	        height: 300,
	        modal: true,
	        isResize : true,
	        isHidden : true,
	        allowClose : false,
	        buttons: [{ 
	        	text: $l("sys.hand.btn.ok"),cls:'l-dialog-btn-highlight',onclick: function (item, dialog) {
	        		//获得弹出框中的Form值
	        		var editformData = liger.get("editform").getData() ;
	        		//设置新数据到支付Grid中
	        		var tailnumber = editformData.tailNumber + "";
	        		invalid2.updateRow(invalid2.getRow(value),{
	        			creditCardType : editformData.creditCardType,			//信用卡类型
	        			transactionNumber : editformData.transactionNumber, 	//交易凭证号
        		   		bankCode : editformData.bankCode,						//银行
        		   		tailNumber : tailnumber,							    //银行卡尾号
        		   		remark : editformData.remark,							//备注
        		   		paymentMethodInfo : editformData.paymentMethodInfo,
	        		});
	        		if(!checkoutOtherInfo(invalid2.getRow(value))){
	        			$.ligerDialog.error($l('type.com.lkkhpg.dsis.common.order.orderinvalid.otherinfoerror'));
	        			return false;
       			 	}
	        		editform.setVisible(["bankCode","creditCardType","creditCardType","tailNumber","paymentMethodInfo"],true);
	        		dialog.hide();
	        	}
	        },{ 
	        	text: $l("sys.hand.btn.cancel"), onclick: function (item, dialog) {
	        		editform.setVisible(["bankCode","creditCardType","creditCardType","tailNumber","paymentMethodInfo"],true);
	        		dialog.hide();
	        	}
	        }]

		});
	}
	
	function checkoutOtherInfo(temp){
		if('CHEQU' == temp.paymentMethod ) {//|| 'REMIT' == temp.paymentMethod 
			if (checkNull(temp.transactionNumber)) {
				return false;
			}
		}else if('CREDI' == temp.paymentMethod){
			if (checkNull(temp.creditCardType) /*|| checkNull(temp.paymentMethodInfo)*/ 
					|| checkNull(temp.transactionNumber) || checkNull(temp.tailNumber) 
					|| temp.tailNumber.length != 4 || isNaN(temp.tailNumber)) {
				return false;
			}
		}else if ('DBCRD' == temp.paymentMethod) {
			if (checkNull(temp.creditCardType) || /*checkNull(temp.paymentMethodInfo) ||*/ checkNull(temp.transactionNumber) || checkNull(temp.tailNumber)
					|| temp.tailNumber.length != 4 || isNaN(temp.tailNumber)) {
				return false;
			}
		}
		return true;
	}
	
	//校验退款信息必填字段
	function checkoutInfo(data){
		//根据不同的退款类型对数据必填字段校验
		var temp;
		for (var i in data) {
			temp = data[i];
			if(!checkoutOtherInfo(temp)){
				return false;
			}
			if (checkNull(temp.paymentAmount) || temp.paymentAmount <= 0) {
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