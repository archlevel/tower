package com.tower.service.generator;

/**
 * 代码生成相关常量.
 * Created by kevin on 14/12/26.
 */
public class Constant {

    /**
     * 模版所在目录.
     */
    public static final String TPL_NAME="/META-INF/exception_enum.tpl";
    /**
     * 模版中名.
     */
    public static final String CLASS_NAME="enumName";
    /**
     * 枚举项.
     */
    public static final String ENUMS="enum";
    /**
     * 包名.
     */
    public static final String PAGE_NAME="package";
    /**
     * 创建者.
     */
    public static final String AUTHOR="author";
    /**
     * 创建时间.
     */
    public static final String DATE="date";
    /**
     * 导入包.
     */
    public static final String IMPORT="import";

    /**
     * 接口.
     */
    public static final String INTERFACE="interface";

    /**
     * 默认继承接口.
     */
    public static final String DEFAULT_INTERFACE_CLASS="com.tower.service.common.exception.basic.IExceptionBody";

    /**
     * 默认层次
     */
    public static final Integer DEFAULT_LEVEL=0;
    /**
     * 枚举前缀
     */
    public static final String PREFIX="MSG_";
    /**
     * spid
     */
    public static final String DEFAULT_SPID="00";
    /**
     * 编码长度
     */
    public static final int CODE_LENGTH=4;
    /**
     * 编码长度
     */
    public static final int SP_CODE_LENGTH=2;
}
