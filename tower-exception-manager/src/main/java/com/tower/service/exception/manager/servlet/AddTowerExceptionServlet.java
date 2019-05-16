package com.tower.service.exception.manager.servlet;

import com.google.common.base.Strings;
import com.tower.service.exception.basic.ExceptionLevel;
import com.tower.service.exception.basic.ExceptionType;
import com.tower.service.exception.manager.dao.ExceptionDao;
import com.tower.service.exception.manager.dao.SoaSpDao;
import com.tower.service.exception.manager.model.TowerException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created by kevin on 15/1/6.
 */
public class AddTowerExceptionServlet extends HttpServlet {


    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        int type=Integer.valueOf(req.getParameter("type"));
        int level=Integer.valueOf(req.getParameter("level"));
        int spid=Integer.valueOf(req.getParameter("sp"));
        String message = req.getParameter("message");
        if(Strings.isNullOrEmpty(message)){
            throw new ServletException("异常信息不能为空");
        }
        resp.sendRedirect("list?code="+ this.add(type,message,level,spid));

    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        req.setAttribute("types", ExceptionType.values());
        req.setAttribute("levels", ExceptionLevel.values());
        req.setAttribute("sps",new SoaSpDao().list());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/pages/add-exception.jsp");
        dispatcher .forward(req, resp);
    }

    private synchronized int add(int type,String message,int level,int spid){
        ExceptionDao towerExceptionDao = new ExceptionDao();
        int maxCode =towerExceptionDao.getMaxCode(type);
        TowerException towerException = new TowerException();
        towerException.setCode(++maxCode);
        towerException.setMessage(message);
        towerException.setType(type);
        towerException.setSpid(spid);
        towerException.setLevel(level);
        towerExceptionDao.addtowerException(towerException);
        return towerException.getCode();
    }
}
