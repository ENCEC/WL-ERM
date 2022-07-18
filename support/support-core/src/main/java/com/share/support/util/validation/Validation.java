package com.share.support.util.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * 验证工具类的接口。
 * 
 */
public interface Validation {

	public Errors validate(Object target);
	/** 
	 * 根据Excel解析为具体的bean，并根据bean的注解验证相应的字段
	 * @param target 要验证bean的集合
	 * @return 验证错误信息
	 * @throws SecurityException
	 * @throws NoSuchMethodException 反射没有找到定义的方法
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<String> validateExlBean(List<?> target)throws SecurityException, NoSuchMethodException,IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	/**
	 * 根据Excel解析为具体的bean，并根据bean的注解验证相应的字段
	 * @param target 要验证bean的集合
	 * @param errorList 存在errorList情况
	 * @return 验证错误信息
	 * @throws SecurityException
	 * @throws NoSuchMethodException 反射没有找到定义的方法
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<String> validateExlBean(List<?> target,List<String> errorList,int startLine)throws SecurityException, NoSuchMethodException,IllegalArgumentException, IllegalAccessException, InvocationTargetException;

	public void validate(Object target, Errors errors);

}
