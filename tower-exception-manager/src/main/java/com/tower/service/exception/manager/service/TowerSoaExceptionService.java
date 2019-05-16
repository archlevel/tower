package com.tower.service.exception.manager.service;

import com.tower.service.exception.manager.dao.ExceptionDao;
import com.tower.service.exception.manager.dao.SoaSpDao;
import com.tower.service.exception.manager.model.TowerException;

import java.util.List;

/**
 * Created by kevin on 15/1/13.
 */
public class TowerSoaExceptionService {

    private ExceptionDao towerExceptionDao = new ExceptionDao();
    private SoaSpDao towerSoaSpDao =new SoaSpDao();
    public List<TowerException> list(int code){
        List<TowerException> list =towerExceptionDao.list(code);
        for(TowerException towerException:list){
            towerException.settowerSoaSp(towerSoaSpDao.get(towerException.getSpid()));
        }
        return list;
    }
}
