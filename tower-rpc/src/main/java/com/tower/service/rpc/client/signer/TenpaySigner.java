package com.tower.service.rpc.client.signer;

import com.tower.service.rpc.ISigner;

public class TenpaySigner implements ISigner {

//    @Override
//    public HttpMethodParams sign(Map<String, Object> params) {
//        // spid=2000000501&trans_time=2007-12-26&stamp=1198661222&cft_signtype=1&mchtype=1&key=$key
//        StringBuilder sb = new StringBuilder();
//        sb.append("spid=" + params.get("spid"));
//        if (params.get("trans_time") != null) {
//            sb.append("&trans_time=" + params.get("trans_time"));
//        }
//        sb.append("&stamp=" + DateUtil.getCurrentUnixTimestamp());
//        if (params.get("trans_time") != null) {
//            sb.append("&cft_signtype=" + params.get("cft_signtype"));
//        }
//        if (params.get("trans_time") != null) {
//            sb.append("&mchtype=" + params.get("mchtype"));
//        }
//        if (params.get("key") != null) {
//            sb.append("&key=" + params.get("key"));
//        }
//        
//        HttpMethodParams httpParams = new HttpMethodParams();
//        int size = params==null?0:params.size();
//        String[] keys = new String[size];
//        params.keySet().toArray(keys);
//        for(int i=0;i<size;i++){
//            httpParams.setParameter(keys[i], params.get(keys[i]));
//        }
//        String sign = MD5Util.md5Hex(sb.toString()).toLowerCase();
//        httpParams.setParameter("sign", sign);
//
//        return httpParams;
//    }

}
