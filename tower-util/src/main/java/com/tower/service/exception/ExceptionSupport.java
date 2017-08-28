package com.tower.service.exception;

import com.tower.service.exception.basic.BasicException;
import com.tower.service.exception.basic.IExceptionBody;
import com.tower.service.util.SPUtil;

/**
 * 异常默认实现.
 * <b>创建异常对象时需要显式指定spid，如果SPID为空或null 使用异常代码中的spId替代</b>
 * <b>在具体的层中可直接实现 getSpid() 返回对应 spid 值 </b>
 * Example:
 * <pre>
 *    public int getSpid() {
 *      return Constant.SPID;
 *    }
 * </pre>
 * <pre>
 *     请在文件/META-INF/SPID中添加spid
 *
 * </pre>
 */
public abstract class ExceptionSupport extends BasicException {

    /**
     * 异常构造器.
     * <b>默认httpCode = 400</b>
     * @see com.tower.service.exception.basic.IExceptionBody
     * @param iExceptionBody 异常code 与信息

     */
    public ExceptionSupport(final IExceptionBody iExceptionBody) {

        super(iExceptionBody,SPUtil.getSpid());
    }

    /**
     * 异常构造器.
     * <b>默认httpCode = 400</b>
     * @see com.tower.service.exception.basic.IExceptionBody
     * @param iExceptionBody 异常code 与信息
     * @param ex 自定义信息
     */
    public ExceptionSupport(final IExceptionBody iExceptionBody, Exception ex) {

        super(iExceptionBody,SPUtil.getSpid(),ex);
    }
    /**
     * 支持消息format
     * @param iExceptionBody
     * @param args
     */
    public ExceptionSupport(IExceptionBody iExceptionBody,Object ... args) {
        this(iExceptionBody,null,args);
    }



    /**
     * 异常构造器.
     * <b>可自定义返回异常信息</b>
     * @see IExceptionBody
     * @param iExceptionBody 异常code 与信息

     * @param args 自定义异常信息
     */
    public ExceptionSupport(final IExceptionBody iExceptionBody,Exception ex,final Object... args) {
        super(iExceptionBody,SPUtil.getSpid(),ex,args);
    }

}
