package com.tower.service.web.filter;

/**
 * 对通过HttpServeletRequest的方法获取到的参数以及Header进行预处理的处理器。
 * 
 * @author alex.zhu
 * @see XssHttpServletRequestWrapper
 */
public interface IParamPreprocessor {
	/**
	 * 对HttpServeletRequest的参数进行预处理。<br>
	 * 返回处理后的参数值。注意若从HttpServetRequest中获取到的参数值为null、则不会调用本方法。
	 * 
	 * @param paramName
	 *            参数名
	 * @param values
	 *            参数值。参数值不为null
	 * @return
	 */
	String preprocessParameter(String paramName, String value);

	/**
	 * 对HttpServeletRequest的Header进行预处理。<br>
	 * 返回处理后的Header值。注意若从HttpServetRequest中获取到的参数值为null、则不会调用本方法。
	 * 
	 * @param header
	 *            Header名
	 * @param values
	 *            参数值。参数值不为null
	 * @return
	 */
	String preprocessHeader(String header, String value);
}
