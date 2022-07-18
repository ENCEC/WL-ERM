package com.share.support.util.xlsbean;

import com.share.support.config.DeviceUpload;
import com.share.support.util.validation.annotation.AnnotationValidation;

import java.util.List;


public class Test {

	public static void main(String[] args) {
		String xlsdir=ClassLoader.getSystemResource("com/share/support/config/").getPath();
		List<Bean> on=XlsUtil.parseSingleBean("bean.xml","d:/测试用户数据.xls");
		System.out.println(on);
		AnnotationValidation av = new AnnotationValidation();
	    List<String> list;
		list = av.validateExlBean(on);
		for (String string : list) {
			System.out.println(string);
		}
		
		List<DeviceUpload> deviceUpload=XlsUtil.parseSingleBean("device.xml","d:/test2.csv");
		System.out.println(deviceUpload);
		AnnotationValidation av2 = new AnnotationValidation();
	    List<String> list2 = av2.validateExlBean(deviceUpload);
		for (String string : list2) {
			System.out.println(string);
		}
		String a = "tsdfsdf";
		System.out.println(a.substring(a.lastIndexOf(".")));
		
		String str="13";
		  if(!str.matches("[1-9]{1}([0-9]+)?")){
		   System.out.println("不是正整数");
		  }else{
		   System.out.println("是正整数");
		  }
		/*List<String> list2 = null;
		System.out.println(list2.isEmpty());*/
	}
}
