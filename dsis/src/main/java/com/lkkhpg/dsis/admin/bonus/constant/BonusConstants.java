/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.constant;

import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * 奖金常量.
 * 
 * @author li.peng@hand-china.com
 *
 */
public class BonusConstants extends BaseConstants {

    /**
     * 最近10天code.
     */
    public static final String BONUS_CREAT_TIME_10D = "10D";

    /**
     * 最近10天value.
     */
    public static final int BONUS_CREAT_TIME_10D_VALUE = -10;

    /**
     * 最近30天code.
     */
    public static final String BONUS_CREAT_TIME_30D = "30D";

    /**
     * 最近30天value.
     */
    public static final int BONUS_CREAT_TIME_30D_VALUE = -30;

    /**
     * 最近6个月code.
     */
    public static final String BONUS_CREAT_TIME_6M = "6M";

    /**
     * 最近6个月value.
     */
    public static final int BONUS_CREAT_TIME_6M_VALUE = -6;

    /**
     * 最近1年code.
     */
    public static final String BONUS_CREAT_TIME_1Y = "1Y";

    /**
     * 最近1年value.
     */
    public static final int BONUS_CREAT_TIME_1Y_VALUE = -1;

    /**
     * 资金调整同步标识.
     */
    public static final String BONUS_ADJUST_SYNC_FLAG_DEDAULT = "N";

    /**
     * 查询默认条数.
     */
    public static final String BONUS_DEFAULT_PAGE_SIZE = "20";

    /**
     * 奖金期间关闭标识.
     */
    public static final String BONUS_CLOSING_STATUS = "N";

    /**
     * 组织类型.
     */
    public static final String BONUS_ORG_TYPE = "MKT";

    /**
     * 发放状态 N.
     */
    public static final String BONUS_RELEASE_STATUS_N = "N";

    /**
     * 发放状态 Y.
     */
    public static final String BONUS_RELEASE_STATUS_Y = "Y";

    /**
     * 奖金类型 本地.
     */
    public static final String BONUS_TYPE_LOCAL = "LOCAL";

    /**
     * 奖金类型 国际.
     */
    public static final String BONUS_TYPE_INTER = "INTER";

    /**
     * 期间类型.
     */
    public static final String PEROID_TYPE = "BM";
    
    /**
     * retrnasfer状态-已完成
     */
    public static final String RETRANSFER_STATUS = "DONE";
    
    /**
     * retrnasfer状态-结余
     */
    public static final String RETRANSFER_STATUS_RE = "RE";

    public static final String WITHDRAWS_STATUS = "DONE";

    /**
     * 奖金自动冲销组织参数.
     */
    public static final String SPM_ATUO_WRITE_OFF = "SPM.AUTO_WRITE_OFF";

    /**
     * 奖金冲销类型.
     */
    public static final String TRX_TYPE_BONUS = "BONUS";

    /**
     * 奖金-冲销来源-最终奖金表.
     */
    public static final String BM_BONUS_FINAL = "BM_BONUS_FINAL";

    /**
     * re-transfer状态-NEW.
     */
    public static final String BONUS_RETRANSFER_STATUS_NEW = "NEW";

    /**
     * 支付失败结果-成功.
     */
    public static final String BONUS_PAYFAIL_SUCCESS = "success";

    /**
     * 支付失败结果-失败.
     */
    public static final String BONUS_PAYFAIL_ERROR = "error";
    
    /**
     * 支付失败结果-失败1.
     */
    public static final String BONUS_PAYFAIL_ERROR_ONE = "error1";

    /**
     * 奖金代码中使用标识 -Y.
     */
    public static final String BONUS_FLAG_Y = "Y";

    /**
     * 奖金代码中使用标识 -N.
     */
    public static final String BONUS_FLAG_N = "N";

    /**
     * 生成文件名时间格式.
     */
    public static final String BONUS_CREATE_FILE_NAME_TIME = "yyyyMMddHHmmss";

    /**
     * 汇款日期格式.
     */
    public static final String BONUS_CREATE_FILE_TIME = "ddMMYY";
    
    /**
     * 汇款日期格式-台湾.
     */
    public static final String BONUS_CREATE_FILE_TIME_TW = "YYMMdd";
    
    /**
     * 汇款日期格式-年份.
     */
    public static final String BONUS_CREATE_FILE_TIME_YEAR = "YYYY";

    /**
     * 汇款日期格式-马来.
     */
    public static final String BONUS_CREATE_FILE_TIME_MY = "ddMMYYYY";

    /**
     * 奖金月份格式.
     */
    public static final String BONUS_PERIOD_NAME_FORMAT = "yyyyMM";

    /**
     * 香港市场.
     */
    public static final String MARKET_HK = "HK";

    /**
     * 香港市场.
     */
    public static final String MARKET_HG = "HG";

    /**
     * 马来市场.
     */
    public static final String MARKET_ML = "ML";

    /**
     * 马来市场.
     */
    public static final String MARKET_MY = "MY";

    /**
     * 台湾市场.
     */
    public static final String MARKET_TW = "TW";
    
    /**
     * 新加坡市场.
     */
    public static final String MARKET_SG = "SG";

    /**
     * 生成文件类型txt.
     */
    public static final String BONUS_FILE_TYPE_TXT = ".txt";
    
    /**
     * 生成文件类型csv.
     */
    public static final String BONUS_FILE_TYPE_CSV = ".csv";

    /**
     * 奖金金额取两位小数.
     */
    public static final String BONUS_AMT_STYLE = "0.00";

    /**
     * 台湾奖金记录流水号.
     */
    public static final String BONUS_TW_FILE_CODE = "BONUS_TW_FILE_CODE";

    /**
     * retransfer编码序列.
     */
    public static final String BONUS_RETRANSFER_SEQ = "BONUS_RETRANSFER";

    /**
     * retransfer编码序列头.
     */
    public static final String BONUS_RETRANSFER_SEQ_HEAD = "BR";

    /**
     * 编码长度-7.
     */
    public static final int CODE_LENGTH_SEVEN = 7;

    /**
     * 编码长度-5.
     */
    public static final int CODE_LENGTH_FIVE = 5;

    /**
     * 编码长度-2.
     */
    public static final int CODE_LENGTH_TWO = 2;

    /**
     * 编码长度-4.
     */
    public static final int CODE_LENGTH_FOUR = 4;

    /**
     * 编码长度-1.
     */
    public static final int CODE_LENGTH_ONE = 1;

    /**
     * 编码长度-10.
     */
    public static final int CODE_LENGTH_TEN = 10;

    /**
     * 编码长度-21.
     */
    public static final int CODE_LENGTH_TWENTY_ONE = 21;

    /**
     * 编码长度-12.
     */
    public static final int CODE_LENGTH_TWELVE = 12;

    /**
     * 编码长度-20.
     */
    public static final int CODE_LENGTH_TWENTY = 20;

    /**
     * 编码长度-15.
     */
    public static final int CODE_LENGTH_FIFTEEN = 15;

    /**
     * 编码长度-11.
     */
    public static final int CODE_LENGTH_ELEVEN = 11;

    /**
     * 编码长度-6.
     */
    public static final int CODE_LENGTH_SIX = 6;

    /**
     * 编码长度-3.
     */
    public static final int CODE_LENGTH_THREE = 3;

    /**
     * 编码长度-13.
     */
    public static final int CODE_LENGTH_THIRTEEN = 13;

    /**
     * 编码长度-14.
     */
    public static final int CODE_LENGTH_FOURTEEN = 14;

    /**
     * 编码长度-80.
     */
    public static final int CODE_LENGTH_EIGHTY = 80;

    /**
     * 编码长度-40.
     */
    public static final int CODE_LENGTH_FORTY = 40;

    /**
     * 编码长度-16.
     */
    public static final int CODE_LENGTH_SIXTEEN = 16;

    /**
     * 编码长度-30.
     */
    public static final int CODE_LENGTH_THIRTY = 30;

    /**
     * 编码长度-22.
     */
    public static final int CODE_LENGTH_TWENTY_TWO = 22;

    /**
     * 奖金调整状态-新建.
     */
    public static final String BONUS_ADJUSTMENT_STATUS_NEW = "NEW";

    /**
     * 奖金调整状态-处理完成.
     */
    public static final String BONUS_ADJUSTMENT_STATUS_DONE = "DONE";

    /**
     * 奖金调整状态-无效.
     */
    public static final String BONUS_ADJUSTMENT_STATUS_NULL = "NULL";

    /**
     * 奖金调整原因-iPoint奖金.
     */
    public static final String BONUS_ADJUSTMENT_REASON_IPOINT = "IB";

    /**
     * 奖金调整类型-增加.
     */
    public static final String BONUS_ADJUSTMENT_TYPE_ADD = "ADD";

    /**
     * 提交申请成功.
     */
    public static final String MSG_SUBMIT_SUCCESS = "msg.info.bonus.submit_success";

    /**
     * 申请成功.
     */
    public static final String MSG_APPROVE_SUCCESS = "msg.info.bonus.approve_success";

    /**
     * 拒绝成功.
     */
    public static final String MSG_REJECT_SUCCESS = "msg.info.bonus.reject_success";

    /**
     * ipoint奖金审核状态 － 拒绝.
     */
    public static final String IPOINT_BONUS_STATUS_REJECT = "RJED";

    /**
     * ipoint奖金审核状态 － 审核.
     */
    public static final String IPOINT_BONUS_STATUS_ALED = "ALED";

    /**
     * ipoint奖金审核状态 － 审核中.
     */
    public static final String IPOINT_BONUS_STATUS_ALING = "ALING";

    /**
     * 奖金调整编码长度.
     */
    public static final int BONUS_ADJ_CODE_LENGTH = 5;

    /**
     * 奖金调整-奖金调整编码前缀.
     */
    public static final String BONUS_ADJ_CODE_BEGIN = "BA";

    /**
     * 奖金记录编号长度.
     */
    public static final int IPOINT_BONUS_CODE_LENGTH = 7;

    /**
     * 奖金记录编码前缀.
     */
    public static final String IPOINT_BONUS_CODE_BEGIN = "ICB";

    /**
     * 奖金记录编码初始值.
     */
    public static final Long IPOINT_BONUS_CODE_INIT_VALUE = 1000001L;

    /**
     * 奖金调整原因-服务中心奖金.
     */
    public static final String BONUS_ADJUST_REASON_SCB = "SCB";

    /**
     * 奖金调整原因-iPoint奖金.
     */
    public static final String BONUS_ADJUST_REASON_IB = "IB";

    /**
     * 奖金调整类型-增加.
     */
    public static final String BONUS_ADJUST_TYPE_ADD = "ADD";

    /**
     * 奖金调整状态-新.
     */
    public static final String BONUS_ADJUST_STATUS_NEW = "NEW";

    /**
     * 奖金调整.
     */
    public static final String BONUS_ADJUST = "BONUS_ADJUST";

    /**
     * 快速编码-奖金调整类别.
     */
    public static final String BM_BONUS_ADJUSTMENT_CATEGORY = "BM.BONUS_ADJUSTMENT_CATEGORY";

    /**
     * 奖金调整类别-R97(返还订货折让).
     */
    public static final String BONUS_ADJUST_CATEGORY_R97 = "R97";

    /**
     * iPoint奖金记录.
     */
    public static final String BONUS_IPOINT = "BONUS_IPOINT";

    /**
     * 组织参数-Service Center月度PV上限.
     */
    public static final String SPM_MAX_PV_SERVICE_CENTER = "SPM.MAX_PV_SERVICE_CENTER";

    /**
     * 组织参数-Service Center奖金比例(%).
     */
    public static final String SPM_BONUS_PERCENTAGE_SERVICE_CENTER = "SPM.BONUS_PERCENTAGE_SERVICE_CENTER";

    /**
     * 组织参数-Service Center奖金系数.
     */
    public static final String SPM_BONUS_RATIO_SERVICE_CENTER = "SPM.BONUS_RATIO_SERVICE_CENTER";

    /**
     * 组织参数-iPoint Center月度销售额上限.
     */
    public static final String SPM_MAX_SALES_IPOINT_CENTER = "SPM.MAX_SALES_IPOINT_CENTER";

    /**
     * 组织参数-iPoint Center奖金比例(%).
     */
    public static final String SPM_BONUS_PERCENTAGE_IPOINT_CENTER = "SPM.BONUS_PERCENTAGE_IPOINT_CENTER";

    /**
     * 组织参数-iPoint Center奖金系数.
     */
    public static final String SPM_BONUS_RATIO_SIPOINT_CENTER = "SPM.BONUS_RATIO_SIPOINT_CENTER";

    /**
     * iPoint奖金状态-审核中.
     */
    public static final String IC_IPOINT_BONUS_STATUS_ALING = "ALING";

    /**
     * iPoint奖金状态-已审核.
     */
    public static final String IC_IPOINT_BONUS_STATUS_ALED = "ALED";

    /**
     * iPoint奖金状态-已拒绝.
     */
    public static final String IC_IPOINT_BONUS_STATUS_RJED = "RJED";

    /**
     * iPoint奖金审核人.
     */
    public static final String SPM_IPOINT_BONUS_RECORD_APPROVER = "SPM.IPOINT_BONUS_RECORD_APPROVER";

    /**
     * 发送iPoint奖金记录审核站内信消息模板编号.
     */
    public static final String SPM_IPOINT_BONUS_APPROVE = "IPOINT_BONUS_APPROVE";

    /**
     * 百分比转换.
     */
    public static final int SPM_BONUS_PERCENTAGE = 100;
    
    /**
     * 集合最大范围.
     */
    public static final int BONUS_LIST_SIZE = 1000;
    
    /**
     * 奖金处理方式-手动补发
     */
    public static final String BM_PROCESS_MODE_SDBF = "SDBF";
    
    /**
     * 奖金处理方式-银行退款
     */
    public static final String BM_PROCESS_MODE_YHTK = "YHTK";
    
    /**
     * 奖金处理方式-自动成功
     */
    public static final String BM_PROCESS_MODE_ZDCG = "ZDCG";
    
    /**
     * 奖金处理方式-自动失败
     */
    public static final String BM_PROCESS_MODE_ZDSB = "ZDSB";
    
    /**
     * 奖金处理方式-退款补发
     */
    public static final String BM_PROCESS_MODE_TKBF = "TKBF";
    
    /**
     * 奖金处理方式-手动失败
     */
    public static final String BM_PROCESS_MODE_SDSB = "SDSB";
}
