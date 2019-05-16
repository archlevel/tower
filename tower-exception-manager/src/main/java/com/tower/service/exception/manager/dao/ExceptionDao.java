package com.tower.service.exception.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.common.collect.Lists;
import com.tower.service.exception.manager.model.TowerException;

/**
 * Created by kevin on 15/1/6.
 */
public class ExceptionDao {

    private static final String TABLE_NAME="soa_exception";

    public int getMaxCode(int type){
        int max=1;

        String sql="select max(code) from "+TABLE_NAME+" where type="+type;

        List<TowerException> towerExceptions=Lists.newArrayList();
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

    public int addtowerException(TowerException towerException){
        String sql="insert into "+TABLE_NAME+"(code,type,message,spid,level)values(?,?,?,?,?)";

        PreparedStatement pstmt= null;
        Connection connection=null;
        int ret=0;
        try {
            connection=DbUtil.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,towerException.getCode());
            pstmt.setInt(2,towerException.getType());
            pstmt.setString(3, towerException.getMessage());
            pstmt.setInt(4, towerException.getSpid());
            pstmt.setInt(5, towerException.getLevel());
            ret =  pstmt.executeUpdate();
            pstmt.close();
            DbUtil.closeConnection(connection);
        } catch (SQLException e) {
        }

        return ret;
    }
    public List<TowerException> list(int code){

        String sql="select id,code,type,message,spid ,level from "+TABLE_NAME ;
        sql+=(code>0?" where code = "+code:"");
        List<TowerException> towerExceptions=Lists.newArrayList();
        Statement statement= null;
        Connection connection=null;
        try {
            connection=DbUtil.getConnection();
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            while(rs.next()){
                TowerException towerException=new TowerException();
                towerException.setId(rs.getInt(1));
                towerException.setCode(rs.getInt(2));
                towerException.setType(rs.getInt(3));
                towerException.setMessage(rs.getString(4));
                towerException.setSpid(rs.getInt(5));
                towerException.setLevel(rs.getInt(6));
                towerExceptions.add(towerException);
            }
            rs.close();
            statement.close();
            DbUtil.closeConnection(connection);
        } catch (SQLException e) {
        }

        return towerExceptions;
    }

}
