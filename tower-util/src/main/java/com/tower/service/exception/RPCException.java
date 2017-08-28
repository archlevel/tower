package com.tower.service.exception;

import com.tower.service.exception.basic.ExceptionLevel;
import com.tower.service.exception.basic.IExceptionBody;

public class RPCException extends ExceptionSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * RPC层异常.
	 * 
	 * @param iExceptionBody
	 *            异常信息主体
	 */
	public RPCException(IExceptionBody iExceptionBody) {
		super(iExceptionBody);
	}

	/**
	 * RPC层异常.
	 * 
	 * @param iExceptionBody
	 *            异常信息主体
	 * @param ex
	 *            自定义异常信息
	 */
	public RPCException(IExceptionBody iExceptionBody, Exception ex) {
		super(iExceptionBody, ex);
	}

	/**
	 * 支持消息formart
	 * 
	 * @param iExceptionBody
	 * @param args
	 */
	public RPCException(IExceptionBody iExceptionBody, Object... args) {
		this(iExceptionBody, null, args);
	}

	/**
	 * 支持消息format + 原生exception
	 * 
	 * @see IExceptionBody
	 * @param iExceptionBody
	 *            异常code 与信息
	 * @param args
	 *            自定义异常信息
	 */
	public RPCException(final IExceptionBody iExceptionBody, Exception ex, final Object... args) {
		super(iExceptionBody, ex, args);
	}

	@Override
	public ExceptionLevel getLevel() {
		return ExceptionLevel.RPC_ACCESS;
	}

}
