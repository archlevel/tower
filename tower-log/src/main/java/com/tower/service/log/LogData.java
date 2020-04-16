package com.tower.service.log;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tower.service.json.impl.SimpleValueFilter;

import java.io.Serializable;

public class LogData implements Serializable {

    @JsonProperty( index= 1)
    protected String requestId;

    @JsonProperty(index = 2)
    protected String reqNum;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getReqNum() {
        return reqNum;
    }

    public void setReqNum(String reqNum) {
        this.reqNum = reqNum;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this,new SimpleValueFilter());
    }
}
