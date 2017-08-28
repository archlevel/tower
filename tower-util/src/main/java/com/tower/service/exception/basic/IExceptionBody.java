package com.tower.service.exception.basic;

/**
 * 异常 信息定义 枚举类 继承改接口.
 * @author  kevincheng
 * @since 0.0.1
 */
public interface IExceptionBody {
    /**
     * 异常code.
      * @return String.valueOf(level+exceptionCode)
     */
    String getCode();

    /**
     * 自定义.
     * @return 自定义内容
     */
    String getValue();
    /**
     * 抛出的异常信息.
     * @return 异常信息。
     */
    String getMessage();

    /**
     * 获取SPIID
     * @return SPID
     */
    String getSpId();

    /**
     * 异常类型.
     * @return 异常类型ID
     */
    int getType();

}
