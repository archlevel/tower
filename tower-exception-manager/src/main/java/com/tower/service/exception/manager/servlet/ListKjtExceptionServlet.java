package com.tower.service.exception.manager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.tower.service.exception.manager.service.KjtSoaExceptionService;

/**
 * Created by kevin on 15/1/6.
 */
public class ListKjtExceptionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {

        String searchCode=req.getParameter("searchCode");
/*        if("test".equals(searchCode)){
            try {
                throw new ServiceException(ExceptionManagerExceptionMessage.MSG_2_0003);
            }catch(ServiceException e){
                System.out.println(e.getCode());
                System.out.println(e.getMessage());
            }
        }
        */
        String code=req.getParameter("code");
        int sCode=0;
        if(Strings.isNullOrEmpty(code)){
            sCode=getCode(searchCode);
        }else{
            sCode=Integer.valueOf(code);
        }


        req.setAttribute("list",new KjtSoaExceptionService().list(sCode));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/pages/list-exception.jsp");

        dispatcher .forward(req, resp);

    }

    /**
     * 1 00 0 0001
     * @param searchCode
     * @return
     */
    private int getCode(String searchCode){
        if(Strings.isNullOrEmpty(searchCode) || searchCode.length()<8){
            return 0;
        }
        return Integer.valueOf(searchCode.substring(4));
    }
}
