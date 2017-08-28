package com.#{company}.service.#{artifactId}.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tower.service.job.IPageableJob;
import com.tower.service.job.impl.AbsPageableJob;

/**
 * 该实现请自行根据业务进行修改
 * 
 * @author alexzhu
 * 
 * @param <String> 该参数可以为自己的单条数据结构，随时调整 xxxDTO
 */
public class JobImpl extends AbsPageableJob<String> implements IPageableJob<String> {
    // 该实现请自行根据业务进行修改
    public JobImpl(String id) {
        super(id);
    }

    @Override
    public int getPages() {
        // TODO 请根据业务获取待处理数据总页
        return 1;
    }

    @Override
    public List<String> pageLoad() {
        // TODO 请根据业务实现单页数据获取实现
        List<String> datas = new ArrayList<String>();
        datas.add("alex.zhu");
        return datas;
    }

    private Date start = new Date();

    @Override
    public void doProcess(String datas) {
        // TODO 请根据业务实现单条数据处理实现
        System.err.println(datas);
        Date now = new Date();
        System.err.println("start: " + start);
        System.err.println("end: " + (start = now));
    }
}
