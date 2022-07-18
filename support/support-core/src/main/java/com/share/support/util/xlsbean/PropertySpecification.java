package com.share.support.util.xlsbean;


/**
 * 
 * 描述属�?的映�?
 *
 */
public class PropertySpecification {
	
	/**
	 * 属�?�名
	 */
	private String name;
	
	/**
	 * 列名
	 */
	private String value;
	
	/**
	 * 是否可以为空
	 */
	private boolean nullable=true;
	
	private int index;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	@Override
	public String toString() {
		return name+","+value+","+index;
	}
}
