package com.tiaoin.crawl.common.enums;

public interface BaseResultCode {
	 /**
     * 错误代码。
     * @return
     */
    String getCode();

    /**
     * 错误信息。
     * @return
     */
    String getMsg();
    
    /**
     * 详细说明。
     * @return
     */
    String getDetail();

    /**
     * 详细说明。
     * @param params
     * @return
     */
    String getDetail(String[] params);

    /**
     * 取得枚举的名字。
     * @return
     */
    String getName();
}
