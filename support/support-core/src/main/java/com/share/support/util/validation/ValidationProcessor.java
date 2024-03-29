package com.share.support.util.validation;

import com.share.support.util.validation.annotation.AnnotationValidation;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;

/**
 * 可用于 MVC 效验的动作链
 * <p>
 * 要求 action 参数中必须有一个 Errors 类型的参数（不需要使用 Param 声明）。当验证完成后会向这个参数赋值
 * 
 */
public class ValidationProcessor extends AbstractProcessor {
	private static AnnotationValidation av = new AnnotationValidation();

	@Override
	public void process(ActionContext ac) throws Throwable {
		Errors es = ValidationUtils.checkArgs(ac.getMethod()
				.getParameterTypes(), ac.getMethodArgs());
		// 参数中没找到 Errors 类型的参数，忽略处理，继续其它动作链处理
		if (null != es) {
			for (Object obj : ac.getMethodArgs()) {
				av.validate(obj, es);
			}
		}
		doNext(ac);
	}
}
