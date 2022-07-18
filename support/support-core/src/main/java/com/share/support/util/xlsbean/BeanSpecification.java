package com.share.support.util.xlsbean;


import java.util.*;


/**
 * 
 * 描述实体属
 */
public class BeanSpecification extends PropertySpecification {

	private Map<String,PropertySpecification> properties = new HashMap<String,PropertySpecification>();
	//保存列名称和顺序
	private List<String> cellName = new ArrayList<String>();

	/**
	 * 类路径名
	 */
	private String className;

	/**
	 * 文件
	 */
	private String fileName;

	/**
	 * excel中数据记录的起点行数
	 */
	private Integer from;

	/**
	 * 列标题所在行
	 */
	private Integer head;

	/**
	 * 类属性和excel列名对应键
	 */
	private Map<String, String> nv=new LinkedHashMap<String,String>();
	
	/**
	 * 类属性和excel列名对应键
	 */
	private Map<String, String> vn=new HashMap<String,String>();


	public void addProperty(PropertySpecification field) {
		this.nv.put(field.getName(), field.getValue());
		this.vn.put(field.getValue(),field.getName());
		cellName.add(field.getValue());
		this.properties.put(field.getName(), field);
	}

	
	public List<String> getCellName() {
		return cellName;
	}


	public void setCellName(List<String> cellName) {
		this.cellName = cellName;
	}


	public Map<String, PropertySpecification> getProperties() {
		return properties;
	}


	public void setProperties(Map<String, PropertySpecification> properties) {
		this.properties = properties;
	}


	public String getClassName() {
		return this.className;
	}

	public void setClassName(String classname) {
		this.className = classname;
	}

	public Map<String,PropertySpecification> getProperty() {
		return properties;
	}

	public String getPropertyNameByValue(String value) {
		return vn.get(value);
	}
	
	public boolean nullable(String propertyName){
		if(this.properties.containsKey(propertyName)){
			return this.properties.get(propertyName).isNullable();
		}else {
			return false;
		}
	}

	public void setProperty(Map<String,PropertySpecification> property) {
		this.properties = property;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getHead() {
		return head;
	}

	public void setHead(Integer head) {
		this.head = head;
	}

	public Map<String, String> getNv() {
		return nv;
	}

	public void setNv(Map<String, String> nv) {
		this.nv = nv;
	}
}
