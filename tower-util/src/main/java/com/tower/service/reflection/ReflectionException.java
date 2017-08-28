package com.tower.service.reflection;


/**
 * @author Clinton Begin
 */
public class ReflectionException extends RuntimeException {

  private static final long serialVersionUID = 7642570221267566591L;

  public ReflectionException() {
    super();
  }

  public ReflectionException(String message) {
    super(message);
  }

  public ReflectionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ReflectionException(Throwable cause) {
    super(cause);
  }

}