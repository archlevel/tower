package com.tower.service.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tower.service.domain.PageResult;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public class PagerTag extends TagSupport {
    /**
     * 变量说明：
     *    该变量的作用
     */
    private static final long serialVersionUID = 865110490631806339L;
    private int nCount;
    private int nPageSize;
    private int nPageNum;
    private String onclick;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public PagerTag() {

    }

    public int doEndTag() throws JspException {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.
                getRequestAttributes()).getRequest();
        PageResult pager = (PageResult)request.getAttribute("pager");
        nCount = pager.getTotal();
        nPageSize = pager.getPageSize();
        nPageNum = pager.getPageIndex();

        JspWriter out = this.pageContext.getOut();

        String pagerText = "<p/>";

        long temp = nPageNum;
        int curentPage = nPageNum;
        int totalPage = (int) Math.ceil(nCount*1d/nPageSize);

        pagerText += "<table width=\"98%\" align=\"center\"><tbody><tr><td align=\"left\" colspan=\"6\">";

        if(curentPage > 1){
            temp = curentPage - 1;
            pagerText += "<a title=\"1\" href=\"javascript:void(0);\" onclick=\""+onclick+"\">第一页</a>    ";
            pagerText += "<a title=\""+temp+"\" href=\"javascript:void(0);\" onclick=\""+onclick+"\">上一页</a>";
        }
        else{
            pagerText += "第一页    上一页   ";
        }

        if(totalPage <= 5){
            for(int n = 1; n <= totalPage; n++){
                if(curentPage == n){
                    pagerText += "[" + n + "] ";
                }
                else{
                    pagerText += " <a title=\"" + n + "\" href=\"javascript:void(0);\" onclick=\"" + onclick + "\">" + n + "</a> ";
                }
            }
        }
        else{
            int leftCnt = curentPage;
            if(curentPage >= 3){
                if(curentPage == totalPage){
                    leftCnt = 4;
                }
                else{
                    leftCnt = 2;
                }
            }
            else if(curentPage >= 2){
                leftCnt = 1;
            }
            else if(curentPage >= 1){
                leftCnt = 0;
            }

            for(int n = leftCnt; n > 0; n--){
                pagerText += " <a title=\"" + (curentPage-n) + "\" href=\"javascript:void(0);\" onclick=\"" + onclick + "\">" + (curentPage-n) + "</a> ";
            }

            pagerText += "[" + curentPage + "]";

            if(curentPage != totalPage){
                for(int n = 1; n < 5-leftCnt; n++){
                    if(curentPage+n <= totalPage){
                        pagerText += " <a title=\"" + (curentPage+n) + "\" href=\"javascript:void(0);\" onclick=\"" + onclick + "\">" + (curentPage+n) + "</a> ";
                    }
                }
            }
        }

        if(curentPage == totalPage){
            pagerText += "下一页 最后一页";
        }
        else{
            temp = curentPage - 1;
            pagerText += "<a title=\"" + (curentPage+1) + "\" href=\"javascript:void(0);\" onclick=\""+onclick+"\">下一页</a> ";
            pagerText += "<a title=\""+totalPage+"\" href=\"javascript:void(0);\" onclick=\""+onclick+"\">最后一页</a>";
        }

        pagerText += " 转到 ";

        pagerText += "<select onchange=\"this.title=$(this).val();" + onclick + "\">";
        for(int n=1; n<=totalPage; n++){
            if(n == curentPage){
                pagerText += "<option selected=\"selected\">" + n + "</option>";
            }
            else{
                pagerText += "<option>" + n + "</option>";
            }
        }
        pagerText += "</select>";
        pagerText += " 页 共 " + totalPage + " 页 共 " + nCount + " 条记录";

        pagerText += "</td></tr></tbody></table>";
        try {
            out.println(pagerText);
        } catch (IOException e) {
            logger.error(e);
        }

        return PagerTag.SKIP_BODY;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public int getNCount() {
        return nCount;
    }

    public void setNCount(int nCount) {
        this.nCount = nCount;
    }

    public int getNPageSize() {
        return nPageSize;
    }

    public void setNPageSize(int nPageSize) {
        this.nPageSize = nPageSize;
    }

    public int getNPageNum()
    {
        return nPageNum;
    }

    public void setNPageNum(int pageNum)
    {
        nPageNum = pageNum;
    }
}