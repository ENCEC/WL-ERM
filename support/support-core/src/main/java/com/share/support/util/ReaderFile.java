package com.share.support.util;


import com.share.support.util.validation.constant.PropertiesConstant;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

public final class ReaderFile extends Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Properties instace;
	
	public ReaderFile(){}
	
	private static void ReaderFiles(String fileURL){
		try {
			instace.load(ReaderFile.class.getResourceAsStream(fileURL));
		} catch (IOException e) {
			System.out.println("路径错误");
			e.printStackTrace();
		}
	}
	public static Properties getInstance(String fileURL){
		instace = null;
		if(instace!=null){
			return instace;
		}else{
			makeInstance(fileURL);
			return instace;
		}
	}
	public static synchronized void makeInstance(String fileURL) {
		if(instace==null){
			instace = new ReaderFile();
			ReaderFiles(fileURL);
		}
	}
	public static Boolean checkURLExist(String fileURL){
		InputStream ip = ReaderFile.class.getResourceAsStream(fileURL);
		if(ip!=null){
			return true;
		}
		return false;
	}
	public static void main(String[] args){
		if(checkURLExist("/validation.properties")){
			Properties pro = ReaderFile.getInstance("/validation.properties");
			String a =pro.get(PropertiesConstant.EXCEL_BEAN_ERROR).toString();
			System.out.println(a);
			Object[] fmtargs={"test","name"}; 
			System.out.println( MessageFormat.format(a, fmtargs));
		}else{
			System.out.println("文件不存在");
		}
//		a=a+",sss";
//		String[] b = a.split(",");
//		System.out.println(a);
//		for (int i = 0; i < b.length; i++) {
//			b[b.length-1]="";
//			System.out.println(b[i]);
//		}
//		System.out.println(b[0].indexOf("-"));
//		String[] c = b[0].split("/");
//		System.out.println(c[0]);
	}
}
