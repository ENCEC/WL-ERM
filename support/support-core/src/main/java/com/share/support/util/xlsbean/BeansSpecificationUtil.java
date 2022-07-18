package com.share.support.util.xlsbean;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * 获取映射配置
 *
 * @author Administrator
 */
@Slf4j
public class BeansSpecificationUtil {
	
	/**
	 * 解析xml，获取跟元素节点
	 * @param xml，包路径
	 * @return
	 */
	public static BeansSpecification getBeans (InputStream xml) {
		
		Digester digester = new Digester();  
		
		//当遇�?beans>时创建一个com.BeansSpecificationImpl对象，并将其放在栈顶
		digester.addObjectCreate("beans", "com.share.support.util.xlsbean.BeansSpecification");
		//根据<beans>元素的属�?attribute)，对刚创建的com.BeansSpecificationImpl对象的属�?property)进行设置
		digester.addSetProperties("beans");
		//当遇�?beans>的子元素<bean>时创建一个com.BeanSpecificationImpl对象，并将其放在栈顶�?
		digester.addObjectCreate("beans/bean", "com.share.support.util.xlsbean.BeanSpecification");
		//
		digester.addSetProperties("beans/bean");
		
		//将调用beans级即BeansSpecificationImpl的addBean方法，参数为BeanSpecificationImpl（栈顶元素）
		digester.addSetNext("beans/bean", "addBean");
		
		//同样遇到<property>的时候实例化�?��com.PropertySpecificationImpl对象
		digester.addObjectCreate("*/property", "com.share.support.util.xlsbean.PropertySpecification");
		//赋�?
		digester.addSetProperties("*/property");
		
		//调用第二栈顶元素的addProperty方法
		digester.addSetNext("*/property", "addProperty");
		
		//�?��的配置都将装在这�?
		BeansSpecification beans=null;
		try {
			//解析完成后返回根元素
			beans = (BeansSpecification) digester.parse(xml);
		} catch (IOException e) {
			log.error("Format xml error IOException"+e.getMessage());
		} catch (SAXException e) {
			log.error("Format xml error SAXException"+e.getMessage());
		}
		
		return beans;
	}
}
