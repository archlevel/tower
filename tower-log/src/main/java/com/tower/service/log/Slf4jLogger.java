package com.tower.service.log;

import java.io.Serializable;

/**
 * The Slf4jLogger implementation of Logger.
 */
public class Slf4jLogger implements Logger, Serializable {

	private static final String FQCN = Slf4jLogger.class.getName();

	private org.slf4j.Logger _impl;

	/**
     *
     */
	private static final long serialVersionUID = 1L;

	public Slf4jLogger(org.slf4j.Logger logger) {

		_impl = logger;

	}

	public String getName() {
		return _impl.getName();
	}

	public void trace(String message) {
		_impl.trace(message);
	}

	public void trace(String format, Object... args) {
		if (isTraceEnabled()) {
			_impl.trace(format, args);
		}

	}

	public boolean isTraceEnabled() {
		return _impl.isTraceEnabled();
	}

	public void debug(String message) {
		if (isDebugEnabled()) {
			_impl.debug(message);
		}
	}

	public void debug(String format, Object... args) {
		if (_impl.isDebugEnabled()) {
			_impl.debug(format, args);
		}
	}

	public boolean isDebugEnabled() {
		return _impl.isDebugEnabled();
	}

	public void info(String message) {
		if (_impl.isInfoEnabled()) {
			_impl.info(message);
		}
	}

	public void info(String format, Object... args) {
		if (_impl.isInfoEnabled()) {
			_impl.info(format, args);
		}
	}

	public boolean isInfoEnabled() {
		return _impl.isInfoEnabled();
	}

	public void warn(String message) {
		if (_impl.isWarnEnabled()) {
			_impl.warn(message);
		}
	}

	public void warn(String format, Object... args) {
		if (_impl.isWarnEnabled()) {
			_impl.warn(format, args);
		}
	}

	public boolean isWarnEnabled() {
		return _impl.isWarnEnabled();
	}

	public void error(String message) {
		_impl.error(message);
	}

	public void error(String format, Object... args) {
		if (_impl.isErrorEnabled()) {
			_impl.error(format, args);
		}
	}

	public void error(Exception ex) {
		if (isErrorEnabled()) {
			_impl.error(null, ex);
		}
	}

	public void error(String message, Exception ex) {
		if (isErrorEnabled()) {
			_impl.error(message, ex);
		}
	}

	public boolean isErrorEnabled() {
		return _impl.isErrorEnabled();
	}

}
