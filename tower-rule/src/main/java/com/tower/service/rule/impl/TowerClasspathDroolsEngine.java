package com.tower.service.rule.impl;


/**
 * Created by pengyong on 16/7/20.
 */
public class TowerClasspathDroolsEngine extends TowerDroolsEngine{

    public TowerClasspathDroolsEngine(){
        super();
        init();
    }

    public void init(){
        this.setkContainer(getKieService().getKieClasspathContainer());
    }
}
