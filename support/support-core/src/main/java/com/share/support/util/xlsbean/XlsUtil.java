package com.share.support.util.xlsbean;


import com.share.support.config.Common;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * xls解析工具
 */
@Slf4j
public class XlsUtil {


	/**
	 * fileName为文件全路径
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public static <T> List<T> xlsToBean(BeanSpecification bean) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException {
		return xlsToBean("",bean);
	}
	/**
	 * 根据bean的描述，将excel表中的数据封装成实体bean
	 * 
	 * @param dir 文件目录
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> xlsToBean(String dir,BeanSpecification bean) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException {

		String fileName =  dir+bean.getFileName();

		POIFSFileSystem poi = new POIFSFileSystem(new FileInputStream(fileName));
		HSSFWorkbook wb = new HSSFWorkbook(poi);
		HSSFSheet sheet = wb.getSheetAt(0);
		int headCol = 0;
		int fromCol = 1;
		if (bean.getHead() != null) {
			headCol = bean.getHead() - 1 < 0 ? 0 : bean.getHead() - 1;
		}
		if (bean.getHead() != null) {
			fromCol = bean.getFrom() - 1 < 1 ? 1 : bean.getFrom() - 1;
		}
		HSSFRow head = sheet.getRow(headCol);
		int cols=head.getPhysicalNumberOfCells();
		String[] p = new String[cols];
		for (int j = 0; j < cols; j++) {
			HSSFCell cell = head.getCell(j);
			String col = cell.getStringCellValue();
			if (bean.getPropertyNameByValue(col) != null) {
				p[j] = bean.getPropertyNameByValue(col);
			}
		}
		@SuppressWarnings("rawtypes")
		Class clazz = Class.forName(bean.getClassName());
		List<T> result = new ArrayList<T>();
		int rows=sheet.getPhysicalNumberOfRows();
		begin:for (int i = fromCol; i < rows; i++) {
			HSSFRow row = sheet.getRow(i);
			T sh = (T) clazz.newInstance();
			clazz.getSuperclass().getDeclaredMethod("setFieldCol", Map.class).invoke(sh, bean.getNv());
			for (int j = 0; j < cols; j++) {
				
				if (p[j] == null) {
					continue;
				}
				Object value = null;
				HSSFCell cell = row.getCell(j);
				if(cell==null){
					if(!bean.nullable(p[j])) {
						break begin;
					}else{
						continue;
					}
				}
				//将单元格强制转成string
				cell.setCellType(CellType.STRING);
				switch (cell.getCellTypeEnum()) {
				case STRING:
					value = cell.getStringCellValue();
					//字符串比较特殊，当此列不能为空的时�?�，空字符串也不�?
					
					if(FileUtil.isEmpty((String)value)){
						if(!bean.nullable(p[j])) {
							break begin;
						}
					}
					
					break;
				case NUMERIC:
					value = cell.getNumericCellValue();
					break;
				case BLANK:
					value = null;
					if(!bean.nullable(p[j])) {
						break begin;
					}else{
						break;
					}
				case BOOLEAN:
					value = cell.getBooleanCellValue();
					break;
				case ERROR:
					value = cell.getErrorCellValue();
					break;
				default:
					break;
				}
				
				try {
					BeanUtils.setProperty(sh, p[j], value);
				} catch (Exception e) {
					log.error(e.getStackTrace().toString());
				}
			}
			result.add(sh);
		}
		return  result;
	}
	
	/**
	 * 根据bean的描述，将excel表中的数据封装成实体bean
	 * 
	 * @param dir 文件目录
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> xls2007ToBean(String dir,BeanSpecification bean) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException {

		String fileName =  dir+bean.getFileName();
//		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(fileName));
		XSSFWorkbook wb=new XSSFWorkbook(new FileInputStream(fileName));  
		
		XSSFSheet sheet = wb.getSheetAt(0);
		int headCol = 0;
		int fromCol = 1;
		if (bean.getHead() != null) {
			headCol = bean.getHead() - 1 < 0 ? 0 : bean.getHead() - 1;
		}
		if (bean.getHead() != null) {
			fromCol = bean.getFrom() - 1 < 1 ? 1 : bean.getFrom() - 1;
		}
		XSSFRow head = sheet.getRow(headCol);
		int cols=head.getPhysicalNumberOfCells();
		String[] p = new String[cols];
		int headValueIsNull = 0;
		int cellValueIsNull = 0;
		for (int j = 0; j < cols; j++) {
			XSSFCell cell = head.getCell(j);
			String col = cell.getStringCellValue();
			if (bean.getPropertyNameByValue(col) != null) {
				p[j] = bean.getPropertyNameByValue(col);
			}else{
				headValueIsNull++;
			}
		}
		//如果表格头错误，则返回空
		if(headValueIsNull==cols){
			return null;
		}
		@SuppressWarnings("rawtypes")
		Class clazz = Class.forName(bean.getClassName());
		List<T> result = new ArrayList<T>();
		int rows=sheet.getLastRowNum();
		begin:for (int i = fromCol; i <= rows; i++) {
			cellValueIsNull = 0;
			// 统计转化后全部为空值
			XSSFRow row = sheet.getRow(i);
			T sh = (T) clazz.newInstance();
			clazz.getSuperclass().getDeclaredMethod("setFieldCol", Map.class).invoke(sh, bean.getNv());
			// 如果行中列全部为空，则过滤此行
			int cellNullNum = 0;
			for (int j = 0; j <cols ; j++) {
				XSSFCell cell = row.getCell(j);
				if(cell==null || cell.getCellType() == CellType.BLANK){
					cellNullNum++;
				}
			}
			//判断当行是不是为空，设置值false为了不验证行
			if(Objects.isNull(row) ||cellNullNum==cols ){
				clazz.getSuperclass().getDeclaredMethod("setNullRow", Boolean.class).invoke(sh, true);
			}else{
				for (int j = 0; j < cols; j++) {
					if (p[j] == null) {
						cellValueIsNull++;
						continue;
					}
					Object value = null;
					XSSFCell cell = row.getCell(j);
					if(cell==null){
						if(!bean.nullable(p[j])) {
							break begin;
						}else{
							continue;
						}
					}
					//将单元格强制转成string
//				cell.setCellType(CellType.STRING);
					switch (cell.getCellTypeEnum()) {
						case FORMULA:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								value = cell.getDateCellValue();
							} else {
								value = cell.getStringCellValue();
							}
							break;
						case STRING:
							value = cell.getStringCellValue();
							//字符串比较特殊，当此列不能为空的时�?�，空字符串也不�?

							if(FileUtil.isEmpty((String)value)){
								if(!bean.nullable(p[j])) {
									break begin;
								}
							}
							break;
						case NUMERIC:
							value = cell.getNumericCellValue();
							break;
						case BLANK:
							value = null;
							if(!bean.nullable(p[j])) {
								break begin;
							}else{
								break;
							}
						case BOOLEAN:
							value = cell.getBooleanCellValue();
							break;
						case ERROR:
							value = cell.getErrorCellValue();
							break;
						default:
							break;
					}


					try {
						BeanUtils.setProperty(sh, p[j], value);
					} catch (Exception e) {
						log.error(e.getStackTrace().toString());
					}
				}
			}
			if(cellValueIsNull==cols){
				clazz.getSuperclass().getDeclaredMethod("setNullRow", Boolean.class).invoke(sh, true);
			}
			result.add(sh);
		}
		return  result;
	}
	
	/*public static <T> List<T> txtToBean(String dir,BeanSpecification bean) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException {

		String fileName =  dir+bean.getFileName();
		
//		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8")); 
		CSVAnalysis parser = new CSVAnalysis(fileName);
		
		int headCol = 0;
		int fromCol = 1;
		if (bean.getHead() != null) {
			headCol = bean.getHead() - 1 < 0 ? 0 : bean.getHead() - 1;
		}
		if (bean.getHead() != null) {
			fromCol = bean.getFrom() - 1 < 1 ? 1 : bean.getFrom() - 1;
		}
		Class clazz = Class.forName(bean.getClassName());
		List<T> result = new ArrayList<T>();
		
		List<List<String>> txtAllList = parser.readCSVFile();//获取文件文件里面所有的值；
		int rows = txtAllList.size(); //行
		if(rows==0){
			return result;
		}
		int cols = txtAllList.get(headCol).size();//表头列
		String[] p = new String[cols];
		for (int j = 0; j < cols; j++) {
			String col = txtAllList.get(headCol).get(j).trim();
			if (bean.getPropertyNameByValue(col) != null) {
				p[j] = bean.getPropertyNameByValue(col);
			}
		}
		
		for (int i = fromCol; i < rows; i++){
			List<String> row = txtAllList.get(i);//获取文件开始读取行
			if(row!=null || row.size()>0){
				T sh = (T) clazz.newInstance();
				clazz.getSuperclass().getDeclaredMethod("setFieldCol", Map.class).invoke(sh, bean.getNv());
				for(int j=0;j<cols;j++){
					if (p[j] == null) {
						continue;
					}
					Object value ;
					if(j>=row.size()){
						value=null;
					}else{
						value = row.get(j);
					}
					
					try {
						BeanUtils.setProperty(sh, p[j], value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				result.add(sh);
			}
		}

		
		return  result;
	}*/
	
	/**
	 * 
	 * @param maping 映射文件�?在目录，使用类路�?
	 * @return
	 */
	public static List<BeanSpecification> parse(InputStream maping){
		BeansSpecification beansSpecification = BeansSpecificationUtil.getBeans(maping);
		return beansSpecification.getBeans();
	}
	
	/**
	 * 解析单个bean,这样在类型判断上或许�?单些�?
	 * 如果有多个文件符合要求，则只返回第一个满足的文件的数�?
	 * @param <T>
	 * @param dir
	 * @param maping
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T>  parseSingleBean (String dir,InputStream maping){
		List<BeanSpecification> result=parse(maping);
		try {
			for (BeanSpecification bean : result) {
				//通配符，可以多个文件
				if(FileUtil.isWildcard(bean.getFileName())){
					List<File> files=FileUtil.getFiles(dir, bean.getFileName());
					if(files!=null){
						for (File file:files){
							bean.setFileName(file.getAbsolutePath());
							return (List<T>)xlsToBean(bean);
						}
					}
					continue;
				}
				return (List<T>)xlsToBean(dir,bean);
		}
			
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
		}
		return null; 
	}
	
	/**
	 * 解析单个bean,这样在类型判断上或许�?单些�?
	 * @param <T>
	 * @param dir
	 * @param maping
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T>  parseSingleBean (String dir,InputStream maping,String fileName,String...realName){
		try {
			
			BeanSpecification bean=BeansSpecificationUtil.getBeans(maping).getBean(fileName);
			if(bean==null) {
				return null;
			}
			bean.setFileName(fileName);
			
			String name = fileName;
			if(realName.length>0){
				name = realName[0];
			}
			String last = name.substring(name.lastIndexOf(".")).toLowerCase();
			//这个是excel2003文件的上传
			if(last.equals(Common.UPLOAD_ALOWED_XLS)){
				return (List<T>)xlsToBean(dir,bean );
			}else if(last.equals(Common.UPLOAD_ALOWED_XLSX)){
				//这个是excel2007文件的上传
				return (List<T>)xls2007ToBean(dir,bean );
			}else if(last.equals(Common.UPLOAD_ALOWED_CSV) || last.equals(Common.UPLOAD_ALOWED_TXT)){
				//这个是txt和csv格式上传
//				return txtToBean(dir, bean);
				//TODO 先不实现
				return null;
			}
					
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
		}
		return null; 
	}
	
	public static <T> List<T>  parseSingleBean (InputStream maping){
		return parseSingleBean("",maping);
	}
	/**
	 * 解析单个bean，直接上传文件；
	 * @param mapingXml xml对应的bean
	 * @param fileName 上传的文件名称的绝对路径
	 * @param realName 上传文件的真实名称
	 * @return 对象信息
	 */
	public static <T> List<T>  parseSingleBean (String mapingXml,String fileName,String...realName){
		InputStream maping = XlsUtil.class.getClassLoader().getResourceAsStream(mapingXml);
		return parseSingleBean("",maping,fileName,realName);
	}
	
	/**
	 * 解析单个bean,这样在类型判断上或许�?单些�?
	 * @param <T>
	 * @param dir
	 * @param maping
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T>  parseSingleBeanTxt (String dir,InputStream maping,String fileName){
		try {
			
			BeanSpecification bean=BeansSpecificationUtil.getBeans(maping).getBean(fileName);
			if(bean==null) {
				return null;
			}
			bean.setFileName(fileName);
			return (List<T>)xlsToBean(dir,bean );
					
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
		}
		return null; 
	}
	
	public static List<BeanSpecification> getBeanSpecifications(InputStream xml){
		BeansSpecification beansSpecification = BeansSpecificationUtil.getBeans(xml);
		return beansSpecification.getBeans();
	}
	
	
}
