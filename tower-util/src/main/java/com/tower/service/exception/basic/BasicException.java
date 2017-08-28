package com.tower.service.exception.basic;

import org.slf4j.helpers.MessageFormatter;

/**
 * 异常基础类.
 * <b>所有异常都需要继承该类</b>
 * <b>所有异常定义都不支持默认构造器,即：不支持缺省异常信息</b>
 * @author  kevincheng
 * @since 0.0.1
 */
public abstract class BasicException extends RuntimeException {
	

    /**
     * 异常信息.
     */
    private String message;
    /**
     * 异常编码.
     */
    private long code;

    /**
     * 异常构造器.
     * @see IExceptionBody
     * @param iExceptionBody 异常code 与信息
     */
    public BasicException(IExceptionBody iExceptionBody,String spId) {
        this(iExceptionBody,spId,null,null);
    }

    /**
     * 包含原生异常
     * @param iExceptionBody
     * @param spId
     * @param ex
     */
    public BasicException(IExceptionBody iExceptionBody,String spId,Exception ex) {
        this(iExceptionBody,spId,ex,null);
    }

    /**
     * 支持消息format
     * @param iExceptionBody
     * @param spId
     * @param args
     */
    public BasicException(IExceptionBody iExceptionBody,String spId,Object ... args) {
        this(iExceptionBody,spId,null,args);
    }



    /**
     * 异常构造器.
     * <b>可自定义返回异常信息</b>
     * @see IExceptionBody
     * @param iExceptionBody 异常code 与信息

     * @param args 自定义异常信息
     */
    public BasicException(final IExceptionBody iExceptionBody,final String spId,Exception ex,final Object... args) {
    	super(ex);
        assert iExceptionBody != null;
        String currSpId=spId;
        if(currSpId==null || "".equals(currSpId)){
            currSpId=iExceptionBody.getSpId();
        }

       this.code=  Long.valueOf(iExceptionBody.getType()+genCode(currSpId,2)+this.getLevel().getValue()+genCode(iExceptionBody.getCode(), 4));
       this.message=this.formatMessage(iExceptionBody.getMessage(),args);
    }

    private String formatMessage(String message,Object ... args){
        if(args!=null && args.length>0) {
         return MessageFormatter.arrayFormat(message, args).getMessage();
        }
        return message;
    }
    /**
     * 获取异常信息.
     * @return String 异常信息
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 获取异常编码.
     * @return  Long.valueOf(httpCode+spid+level+exceptionCode)
     */
    public long getCode() {
        return code;
    }

    public String genCode(String code,int length){
        String strCode=String.valueOf(code);
        while(strCode.length()<length){
            strCode="0"+strCode;
        }
        return strCode;
    }
    public abstract ExceptionLevel getLevel();

}
