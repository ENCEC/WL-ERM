package com.share.file.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * 加密工具
 * @author wangcl
 * @date 20201211
 */
@Slf4j
public class Base64Util {
	/**
	 * 加密
	 * @param str
	 * @return
	 */
	public static String getBase64(String str) {
		return Base64.encodeBase64String(str.getBytes());
	}

	/**
	 * 解密
	 * @param s
	 * @return
	 */
	public static String getFromBase64(String s) {
		byte[] decodeBase64 = Base64.decodeBase64(s);
		s = new String(decodeBase64);
		return s;
	}

	public static void main(String[] args) {
		String a = "1234";
		String base64 = getBase64(a);
		String fromBase64 = getFromBase64(base64);
		log.info(fromBase64);
	}
}
