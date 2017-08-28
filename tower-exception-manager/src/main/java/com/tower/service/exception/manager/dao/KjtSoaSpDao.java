package com.tower.service.exception.manager.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.common.collect.Lists;
import com.tower.service.exception.manager.model.KjtSoaSp;

/**
 * Created by kevin on 15/1/13.
 */
public class KjtSoaSpDao {

    public List<KjtSoaSp> list(){
        String sql="select * from soa_provider ";

        List<KjtSoaSp> ajkSoaSps= Lists.newArrayList();
        Statement statement= null;
        Connection connection=null;
        try {
            connection=DbUtil.getConnection();
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            while(rs.next()){
                KjtSoaSp ajkSoaSp=new KjtSoaSp();
                ajkSoaSp.setId(rs.getInt(1));
                ajkSoaSp.setSp_name(rs.getString(2));
                ajkSoaSp.setSp_description(rs.getString(3));
                ajkSoaSps.add(ajkSoaSp);
            }
            rs.close();
            statement.close();
            DbUtil.closeConnection(connection);
        } catch (SQLException e) {
        }

        return ajkSoaSps;
    }
    public KjtSoaSp get(int id){
        KjtSoaSp ajkSoaSp=new KjtSoaSp();
        if(id<=0){
            ajkSoaSp.setId(0);
            ajkSoaSp.setSp_name("框架");
            return ajkSoaSp;
        }
        String sql="select * from soa_provider ";

        List<KjtSoaSp> ajkSoaSps= Lists.newArrayList();
        Statement statement= null;
        Connection connection=null;
        try {
            connection=DbUtil.getConnection();
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            if(rs.next()){
                ajkSoaSp=new KjtSoaSp();
                ajkSoaSp.setId(rs.getInt(1));
                ajkSoaSp.setSp_name(rs.getString(2));
                ajkSoaSp.setSp_description(rs.getString(3));
                ajkSoaSps.add(ajkSoaSp);
            }
            rs.close();
            statement.close();
            DbUtil.closeConnection(connection);
        } catch (SQLException e) {
        }

        return ajkSoaSp;
    }
}
