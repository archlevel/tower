package com.tower.service.log;

import java.io.Serializable;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class LogData extends Serializable {

    @JsonProperty(ordinal = 1)
    protected String requestId;

    @JsonProperty(ordinal = 2)
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
