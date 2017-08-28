package com.tower.service.common.exception;

import com.tower.service.exception.ServiceException;
import com.tower.service.exception.basic.BasicException;
import com.tower.service.exception.basic.IExceptionBody;

import junit.framework.TestCase;

import org.slf4j.helpers.MessageFormatter;


/**
 * 可格式化异常消息测试
 */
public class ExceptionTest  extends TestCase {

   public void testFormatExceptionMessage(){
       IExceptionBody messageBody=new IExceptionBody() {
           @Override
           public String getCode() {
               return "10";
           }

           @Override
           public String getValue() {
               return "10";
           }

           @Override
           public String getMessage() {
               return "可格式化异常消息测试：参数1:{}参数2:{}";
           }

           @Override
           public String getSpId() {
               return "1";
           }

           @Override
           public int getType() {
               return 0;
           }
       };
       Object [] args =new Object[]{"我是参数1","我是参数2"};
       BasicException basicException = new ServiceException(messageBody,args);
      // System.out.println(basicException.getCode());
       assertEquals(120010,basicException.getCode());
       assertEquals(MessageFormatter.arrayFormat(messageBody.getMessage(),args).getMessage(),basicException.getMessage());
   }
}
