package com.share.support.util.validation.constant;

/**
 * @Author hehao
 * @Description 属性
 * @Date  2021/11/24 21:50
 **/
public interface PropertiesConstant {

	public String PROPERTIES_FILE="/validation.properties";
	
	public String EXCEL_BEAN_ERROR="excel.bean.error";
	/**
	 * 如果没有属性文件默认用这句话作为错误输出
	 */
	public String EXCEL_ROW_COLOUMN_ERROR="第{0}行：{1}{2}";
}
