package com.share.support.util.xlsbean;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileUtil  {
    
	/**
     * 通配规则
     * @param dirname
     * @param filter
     * @return
     */
    public static List<File> getFiles(String dirname,String filter) {  
        File dir = new File(dirname);  
        File[] files =  dir.listFiles(new WildcardFileFilter(filter));
        if(files==null)return null;
        List<File> filess = new ArrayList<File>();  
        for (int i = 0; i < files.length; i++) {  
            if (files[i].isDirectory()) {// 判断是否是目�? 
                //子文件不处理
            	//filess.addAll(getFiles(files[i].getAbsolutePath()));
            }else{
            	filess.add(files[i]);
            }
        }  
        return filess;  
    }  
  
    
    
    /**
     * 是否符合通配符规�?
     * @param exp
     * @param input
     * @return
     */
    public static boolean wildcard(String exp,String input){
    	  if(isNotEmpty(exp)&&isNotEmpty(input)){
		      exp = exp.replace('.', ':');
		      exp = exp.replaceAll(":", "\\\\.");
		      exp = exp.replace('*', ':');
		      exp = exp.replaceAll(":", ".*");
		      exp = exp.replace('?', ':');
		      exp = exp.replaceAll(":", ".?");
		      exp = "^" + exp + "$";
		      return Pattern.matches(exp, input);
    	  }else{
    		  return false;
    	  }
    }
    
    /**
     * 是否含有通配�?
     * @param input
     * @return
     */
    public static boolean isWildcard(String input){
    	if(isNotEmpty(input)){
    		if(input.contains("*")||input.contains("?")){
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean isEmpty(String str){
    	
    	return (str == null) || (str.length() == 0);
    }
    
    public static boolean isNotEmpty(String str){
    	
    	return (str != null) && (str.length() > 0);
    }
}

/**
 * 通配符规则过�?
 */
class WildcardFileFilter implements FileFilter{  
	
	private String pattern;
	
    @Override
    public boolean accept(File pathname) {
        String filename = pathname.getName().toLowerCase();  
        if(FileUtil.wildcard(pattern.toLowerCase(),filename)){  
            return true;  
        }else{  
            return false;  
        }  
    }
    
    public WildcardFileFilter (String pattern){
    	this.pattern=pattern;
    }
}  
