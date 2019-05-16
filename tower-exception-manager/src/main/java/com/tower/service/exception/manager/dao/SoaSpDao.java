package com.tower.service.exception.manager.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.common.collect.Lists;
import com.tower.service.exception.manager.model.TowerSoaSp;

/**
 * Created by kevin on 15/1/13.
 */
public class SoaSpDao {

    public List<TowerSoaSp> list(){
        String sql="select * from soa_provider ";

        List<TowerSoaSp> towerSoaSps= Lists.newArrayList();
        Statement statement= null;
        Connection connection=null;
        try {
            connection=DbUtil.getConnection();
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            while(rs.next()){
                TowerSoaSp towerSoaSp=new TowerSoaSp();
                towerSoaSp.setId(rs.getInt(1));
                towerSoaSp.setSp_name(rs.getString(2));
                towerSoaSp.setSp_description(rs.getString(3));
                towerSoaSps.add(towerSoaSp);
            }
            rs.close();
            statement.close();
            DbUtil.closeConnection(connection);
        } catch (SQLException e) {
        }

        return towerSoaSps;
    }
    public TowerSoaSp get(int id){
        TowerSoaSp towerSoaSp=new TowerSoaSp();
        if(id<=0){
            towerSoaSp.setId(0);
            towerSoaSp.setSp_name("框架");
            return towerSoaSp;
        }
        String sql="select * from soa_provider ";

        List<TowerSoaSp> towerSoaSps= Lists.newArrayList();
        Statement statement= null;
        Connection connection=null;
        try {
            connection=DbUtil.getConnection();
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            if(rs.next()){
                towerSoaSp=new TowerSoaSp();
                towerSoaSp.setId(rs.getInt(1));
                towerSoaSp.setSp_name(rs.getString(2));
                towerSoaSp.setSp_description(rs.getString(3));
                towerSoaSps.add(towerSoaSp);
            }
            rs.close();
            statement.close();
            DbUtil.closeConnection(connection);
        } catch (SQLException e) {
        }

        return towerSoaSp;
    }
}
