package com.tower.service.exception.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.common.collect.Lists;
import com.tower.service.exception.manager.model.KjtException;

/**
 * Created by kevin on 15/1/6.
 */
public class KjtExceptionDao {

    private static final String TABLE_NAME="soa_exception";

    public int getMaxCode(int type){
        int max=1;

        String sql="select max(code) from "+TABLE_NAME+" where type="+type;

        List<KjtException> ajkExceptions=Lists.newArrayList();
        Statement statement= null;
        Connection connection=null;
        try {
            connection=DbUtil.getConnection();
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            while(rs.next()){
              String r= rs.getString(1);
                if(r!=null) {
                    max = Integer.valueOf(r);
                }
            }
            rs.close();
            statement.close();
            DbUtil.closeConnection(connection);
        } catch (SQLException e) {
        }
        return max;
    }

    public int addAjkException(KjtException kjtException){
        String sql="insert into "+TABLE_NAME+"(code,type,message,spid,level)values(?,?,?,?,?)";

        PreparedStatement pstmt= null;
        Connection connection=null;
        int ret=0;
        try {
            connection=DbUtil.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,kjtException.getCode());
            pstmt.setInt(2,kjtException.getType());
            pstmt.setString(3, kjtException.getMessage());
            pstmt.setInt(4, kjtException.getSpid());
            pstmt.setInt(5, kjtException.getLevel());
            ret =  pstmt.executeUpdate();
            pstmt.close();
            DbUtil.closeConnection(connection);
        } catch (SQLException e) {
        }

        return ret;
    }
    public List<KjtException> list(int code){

        String sql="select id,code,type,message,spid ,level from "+TABLE_NAME ;
        sql+=(code>0?" where code = "+code:"");
        List<KjtException> ajkExceptions=Lists.newArrayList();
        Statement statement= null;
        Connection connection=null;
        try {
            connection=DbUtil.getConnection();
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            while(rs.next()){
                KjtException ajkException=new KjtException();
                ajkException.setId(rs.getInt(1));
                ajkException.setCode(rs.getInt(2));
                ajkException.setType(rs.getInt(3));
                ajkException.setMessage(rs.getString(4));
                ajkException.setSpid(rs.getInt(5));
                ajkException.setLevel(rs.getInt(6));
                ajkExceptions.add(ajkException);
            }
            rs.close();
            statement.close();
            DbUtil.closeConnection(connection);
        } catch (SQLException e) {
        }

        return ajkExceptions;
    }

}
