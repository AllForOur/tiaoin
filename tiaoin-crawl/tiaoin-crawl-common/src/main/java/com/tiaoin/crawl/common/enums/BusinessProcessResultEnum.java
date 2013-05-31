package com.tiaoin.crawl.common.enums;
/**
 * <p>
 * 业务处理结果枚举
 * </p>
 * @author yangxing
 *
 */
public enum BusinessProcessResultEnum implements BaseResultCode {
	
	SUCCESS("0000", "SUCCESS", "处理成功"),

    GENERIC_FAILURE("0001", "GENERIC_FAILURE", "系统异常"),

    DATABASE_OPERATE_FAILURE("0002", "DATABASE_OPERATE_FAILURE", "数据库操作失败"),

    OBJECT_CONVERT_FAILURE("0003", "OBJECT_CONVERT_FAILURE", "对象转换失败"),

    DATE_CONVERT_FAILURE("0004", "DATE_CONVERT_FAILURE", "日期转换失败"),

    TASK_REGISTE_FAILURE("0005", "TASK_REGISTE_FAILURE", "注册任务失败"),

    PARAM_NOT_ALLOW_EMPTY("0006", "PARAM_NOT_ALLOW_EMPTY", "参数不能为空"),

    PARAM_ERROR("0007", "PARAM_ERROR", "参数不正确"),
    
    NOT_SUPPORT_BUSINESS_OPERATION("0008", "NOT_SUPPORT_BUSINESS_OPERATION", "不支持此种业务操作"),
    
    NOT_FOUND_BUSINESS_PROCESSOR("0009", "NOT_FOUND_BUSINESS_PROCESSOR", "没有找到对应业务类型的处理器"),
    
    BUSINESS_TYPE_EMPTY("0010", "BUSINESS_TYPE_EMPTY", "businessType不能为空"),
	
	BUSINESS_NAME_EMPTY("0011", "BUSINESS_NAME_EMPTY", "BusinessName不能为空");
	
    private final String code;

    private final String msg;

    private final String detail;

    BusinessProcessResultEnum(String code, String msg, String detail) {
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }

    public String getMsg() {
        return msg;
    }

	public String getDetail(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name();
	}

    /**
     * 通过状态码获取ENUM的名字
     * 
     * @param code
     * @return
     */
    public static BusinessProcessResultEnum getEnumByCode(String code) {
        for (BusinessProcessResultEnum p : BusinessProcessResultEnum.values()) {
            if (p.getCode().equalsIgnoreCase(code)) {
                return p;
            }
        }
        return null;
    }


}
