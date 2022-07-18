package com.share.support.util.xlsbean;


import com.share.support.util.validation.annotation.Validations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * @Author hehao
 * @Description 转换类
 * @Date  2021/11/24 21:46
 **/
public class Bean extends BaseValidationBean {
	
	@Validations(chinese = true, errorMsg = "must.chain")
	private String name;
	@Validations(qq = true, errorMsg = "must.integer")
	private String qq;


	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getQq() {
		return qq;
	}
	
	public void setQq(String qq) {
		this.qq = qq;
	}

	@Override
	public String toString() {
		return "Bean [name=" + name + ", qq=" + qq + "]";
	}
	
	public static void main(String[] args) {
		try {
			Bean b = new Bean();
			Field[] fields = b.getClass().getFields();
			for (Field field : fields) {
				Validations vals = field.getAnnotation(Validations.class);
				String msg = vals.errorMsg();
				Method method = b.getClass().getDeclaredMethod(field.getName(), field.getType());
				method.invoke(b.getClass(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
