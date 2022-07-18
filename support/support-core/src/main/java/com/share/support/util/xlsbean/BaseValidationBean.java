package com.share.support.util.xlsbean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author hehao
 * @Description 解析公共方法
 * @Date  2021/11/24 21:46
 */
@Data
public class BaseValidationBean {

	/**
	 * 解析文件时保存字段和excel的对应关
	 */
	private Map<String,Object> fieldCol = new HashMap<String, Object>();

	private Boolean nullRow = false;

	public void setNullRow(Boolean nullVal){
		this.nullRow = nullVal;
	}
	public void setFieldCol(Map<String, Object> fieldCol) {
		this.fieldCol = fieldCol;
	}
	/**
	 * 根据bean的名称得到，字段和excel对应的名
	 * @param field Bean的字段名
	 * @return
	 */
	public String getColByFieldName(String field){
		return (String) fieldCol.get(field);
	}
	
}
