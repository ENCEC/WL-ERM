package com.share.support.config;

import com.share.support.util.validation.annotation.Validations;
import com.share.support.util.xlsbean.BaseValidationBean;
import org.apache.commons.lang3.StringUtils;

public class DeviceUpload extends BaseValidationBean {

	@Validations(custom="checkCode", errorMsg = "只能是"+ Common.DEVICE_CODE_LENGTH_STR+"位,并且不能为"+Common.DEVICE_CODE_ILLEGAL+"")
	private String deviceCode;
	@Validations(required=true,strLen={0,50}, errorMsg = "不能小于0或大于50位")
	private String manufacturer;
	@Validations(required=true,strLen={0,50}, errorMsg = "不能小于0或大于50位")
	private String type;
	private String description;
	
	public Boolean checkCode(){
		if(StringUtils.isEmpty(deviceCode)){
			return false;
		}else if(deviceCode.length()!=Common.DEVICE_CODE_LENGTH){
			return false;
		}else if(deviceCode.toUpperCase().equals(Common.DEVICE_CODE_ILLEGAL)){
			return false;
		}
		return true;
	}
	
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
