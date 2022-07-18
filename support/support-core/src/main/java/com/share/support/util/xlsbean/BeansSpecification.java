package com.share.support.util.xlsbean;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 根元
 */
public class BeansSpecification extends PropertySpecification {

	/**
	 * 跟元素下的beans
	 */
	private List<BeanSpecification> beans = new ArrayList<BeanSpecification>();

	public List<BeanSpecification> getBeans() {
		return this.beans;
	}

	public void addBean(BeanSpecification field) {
		this.beans.add(field);
	}

	/**
	 * 根据文件名和类名查找
	 * @param fileName
	 * @return
	 */
	public BeanSpecification getBean(String fileName) {
		for (BeanSpecification bean : beans) {
			if (FileUtil.wildcard(bean.getFileName(), fileName)||bean.getClassName().equals(fileName)) {
				return bean;
			}
		}
		return null;
	}
	public BeanSpecification getBeanByClassName(String className) {
		for (BeanSpecification bean : beans) {
			if (bean.getClassName().equals(className)) {
				return bean;
			}
		}
		return null;
	}

}
