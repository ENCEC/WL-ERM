package com.share.support.config;

public interface Common {

	Integer OP_SUCCESS=0;//操作成功
	Integer OP_FAIL=1;//操作失败
	
	//设备批量导入支持的文件
	String UPLOAD_ALOWED_XLS = ".xls";
	String UPLOAD_ALOWED_XLSX = ".xlsx";
	String UPLOAD_ALOWED_CSV = ".csv";
	String UPLOAD_ALOWED_TXT = ".txt";
	
	//设备配置的类型
	Integer DEVICE_CONFIG_HEAT_CYCLE=1;//设备设置类型之--心跳周期配置
	String DEVICE_CONFIG_HEAT_CYCLE_VALUE="300";//设备设置类型之--心跳周期配置默认值
	
	//设备状态定义
	Integer DEVICE_STATUS_DORMANCY=1;//休眠状态
	Integer DEVICE_STATUS_WORKING=2;//通信中
	Integer DEVICE_STATUS_ILLEGAL=3;//非法
	
	String DEVICE_CODE_ILLEGAL="FFFFFFFFFFFFFFFF";//设备id不能为这个值
	
	//下面两个判断是一起的,要一起改
	Integer DEVICE_CODE_LENGTH=16;//设备ID的长度只能16
	String DEVICE_CODE_LENGTH_STR="16";//设备ID的长度只能16
}
