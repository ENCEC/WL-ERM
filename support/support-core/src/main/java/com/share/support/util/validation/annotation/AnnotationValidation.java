package com.share.support.util.validation.annotation;

import com.share.support.util.ReaderFile;
import com.share.support.util.validation.Errors;
import com.share.support.util.validation.Validation;
import com.share.support.util.validation.ValidationUtils;
import com.share.support.util.validation.constant.PropertiesConstant;
import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * 使用注解驱动的验证解决方案
 *
 * @see ValidationUtils
 * @see Validation
 */
@Slf4j
public class AnnotationValidation implements Validation {

    /**
     * 通过注解对一个Pojo进行验证
     */
    @Override
    public Errors validate(Object target) {
        Errors errors = new Errors();
        validate(target, errors);
        return errors;
    }

    @Override
    public List<String> validateExlBean(List<?> target) {
        String message = PropertiesConstant.EXCEL_ROW_COLOUMN_ERROR;
        List<String> errorList = new ArrayList<String>();
        Properties pro = new Properties();
        // 如果错误的国际化文件存在
		/*if (ReaderFile.checkURLExist(PropertiesConstant.PROPERTIES_FILE)) {
			pro = ReaderFile.getInstance(PropertiesConstant.PROPERTIES_FILE);
			message = pro.get(PropertiesConstant.EXCEL_BEAN_ERROR).toString();
		}*/
        try {
            for (int i = 0; i < target.size(); i++) {
                Errors errors = new Errors();
                validate(target.get(i), errors);
                Map<String, String> errorMap = errors.getErrorsMap();
                // 得到错误数据信息
                for (String key : errorMap.keySet()) {
					Class clazz = target.get(i).getClass();
					Method method = null;
					if (clazz.getDeclaredConstructor().toString().indexOf("") >= 0) {
						method = target
								.get(i)
								.getClass()
								.getDeclaredMethod("getColByFieldName",
										String.class);
					} else {
						method = target
								.get(i)
								.getClass()
								.getSuperclass()
								.getDeclaredMethod("getColByFieldName",
										String.class);
					}

					Object col = method.invoke(target.get(i), key);
                    String errorMsg = errorMap.get(key);
                    Object propertiesErrorMsg = pro.get(errorMsg);
                    if (propertiesErrorMsg != null) {
                        errorMsg = propertiesErrorMsg.toString();
                    }
                    Object[] objPar = {(i + 1), col.toString(), errorMsg};
                    errorList.add(MessageFormat.format(message, objPar));
                }

            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
        return errorList;
    }

    @Override
    public List<String> validateExlBean(List<?> target, List<String> errorList, int startLine) {
        String message = PropertiesConstant.EXCEL_ROW_COLOUMN_ERROR;
        Properties pro = new Properties();
        // 如果错误的国际化文件存在
		/*if (ReaderFile.checkURLExist(PropertiesConstant.PROPERTIES_FILE)) {
			pro = ReaderFile.getInstance(PropertiesConstant.PROPERTIES_FILE);
			message = pro.get(PropertiesConstant.EXCEL_BEAN_ERROR).toString();
		}*/
        try {
            for (int i = 0; i < target.size(); i++) {
                Errors errors = new Errors();
                validate(target.get(i), errors);
                Map<String, String> errorMap = errors.getErrorsMap();
                // 得到错误数据信息
                for (String key : errorMap.keySet()) {
                    //仓单es导入注释
      /*              if (clazz.getDeclaredConstructor().toString().indexOf("ImportSdrWarehouseWarrantInfoDTO") >= 0) {
                        method = target
                                .get(i)
                                .getClass()
                                .getDeclaredMethod("getColByFieldName",
                                        String.class);
                    } else {
                        method = target
                                .get(i)
                                .getClass()
                                .getSuperclass()
                                .getDeclaredMethod("getColByFieldName",
                                        String.class);
                    }*/
                    Method  method = target
                            .get(i)
                            .getClass()
                            .getSuperclass()
                            .getDeclaredMethod("getColByFieldName",
                                    String.class);
                    Object col = method.invoke(target.get(i), key);
                    String errorMsg = errorMap.get(key);
                    Object propertiesErrorMsg = pro.get(errorMsg);
                    if (propertiesErrorMsg != null) {
                        errorMsg = propertiesErrorMsg.toString();
                    }
                    Object[] objPar = {(startLine + i + 1), col.toString(), errorMsg};
                    errorList.add(MessageFormat.format(message, objPar));
                }

            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
        return errorList;
    }

    /**
     * 通过注解对一个Pojo进行验证
     */
    @Override
    public void validate(Object target, Errors errors) {
        if (null == target) {
            return;
        }
        // 遍历对象的所有字段
        Mirror<?> mirror = Mirror.me(target.getClass());

        try {
            Boolean nullValue = (Boolean) mirror.getGetter("nullRow").invoke(target);
            if (nullValue) {
                return;
            }
            Field[] fields = mirror.getFields(Validations.class);
            for (Field field : fields) {
                // 检查该字段是否声明了需要验证
                Validations vals = field.getAnnotation(Validations.class);
                String errMsg = vals.errorMsg();

                Method getMethod = mirror.getGetter(field);
                // 这个对象字段get方法的值
                Object value = getMethod.invoke(target, new Object[]{});

                // 验证该字段是否必须
                if (vals.required()
                        && !ValidationUtils.required(field.getName(), value,
                        errMsg, errors)) {
                    continue;
                }

                // 账号验证
                if (vals.account()
                        && !ValidationUtils.account(field.getName(), value,
                        errMsg, errors)) {
                    continue;
                }

                // 手机号码验证
                if (vals.mobile()
                        && !ValidationUtils.mobile(field.getName(), value,
                        errMsg, errors)) {
                    continue;
                }

                // 验证是否为 email
                if (vals.email()
                        && !ValidationUtils.email(field.getName(), value,
                        errMsg, errors)) {
                    continue;
                }

                // 验证是否为 qq 号
                if (vals.qq()
                        && !ValidationUtils.qq(field.getName(), value, errMsg,
                        errors)) {
                    continue;
                }

                // 必须为中文效验
                if (vals.chinese()
                        && !ValidationUtils.chinese(field.getName(), value,
                        errMsg, errors)) {
                    continue;
                }

                // 邮政编码效验
                if (vals.post()
                        && !ValidationUtils.post(field.getName(), value,
                        errMsg, errors)) {
                    continue;
                }

                // 验证正则表达式
                if (!Strings.isBlank(vals.regex())
                        && !ValidationUtils.regex(field.getName(), value,
                        vals.regex(), errMsg, errors)) {
                    continue;
                }

                // 验证该字段长度
                if (vals.strLen().length > 0 && Objects.nonNull(value)
                        && !ValidationUtils.stringLength(field.getName(),
                        value, vals.strLen(), errMsg, errors)) {
                    continue;
                }

                // 重复值检验
                if (!Strings.isBlank(vals.repeat())) {
                    Object repeatValue = mirror.getGetter(vals.repeat())
                            .invoke(target, new Object[]{});
                    if (!ValidationUtils.repeat(field.getName(), value,
                            repeatValue, errMsg, errors)) {
                        continue;
                    }
                }

                // 判断指定值是否在某个区间
                if (vals.limit().length > 0
                        && !ValidationUtils.limit(field.getName(), value,
                        vals.limit(), errMsg, errors)) {
                    continue;
                }

                // 自定义验证方法
                if (!Strings.isBlank(vals.custom())
                        && !ValidationUtils.custom(field.getName(), target,
                        vals.custom(), errMsg, errors)) {
                    continue;
                }
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
    }
}
