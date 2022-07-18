package com.share.support.util;

import javax.servlet.http.HttpServletRequest;

public class BrowserTypeUtil {

	/**
	 * 获取浏览器类型
	 * @param request
	 * @return
	 */
	public static String getBrowser(HttpServletRequest request) {
	    String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
	    if (UserAgent != null) {
	        if (UserAgent.indexOf("msie") >= 0)
	            return "IE";
	        if (UserAgent.indexOf("firefox") >= 0)
	            return "FF";
	        if (UserAgent.indexOf("safari") >= 0)
	            return "SF";
	    }
	    return null;
	}
}
