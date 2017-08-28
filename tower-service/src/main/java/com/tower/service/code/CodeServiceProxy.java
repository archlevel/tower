package com.tower.service.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tower.service.domain.code.CodeDto;
import com.tower.service.domain.code.CodeResponse;

public class CodeServiceProxy {

    private CodeService codeServiceClient;

    public void setCodeServiceClient(CodeService codeService) {
        codeServiceClient = codeService;
    }

    private static Map<String, List<CodeDto>> codeMap = new ConcurrentHashMap<String, List<CodeDto>>();

    @SuppressWarnings("unchecked")
    private List<CodeDto> getData(String itemType, String... itemNo) {
    	StringBuilder sb = new StringBuilder(itemType);
    	if (itemNo != null && itemNo.length != 0) {
    		for (String string : itemNo) {
    		    sb.append(",");
    			sb.append(string);
    		}
    	} 
    	String key = sb.toString();
        if (codeMap.containsKey(key)) {
            return codeMap.get(key);
        } else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("itemType", itemType);
            CodeResponse response = codeServiceClient.selectList(params);
            List<CodeDto> list = (List<CodeDto>) response.getList();
            if (itemNo == null || itemNo.length == 0) {
                if (list != null && !list.isEmpty()) {
                    codeMap.put(key, list);
                }
            } else {
                if (list != null && !list.isEmpty()) {
                    List<CodeDto> resultCodes = new ArrayList<CodeDto>();
                    for (String string : itemNo) {
                        for (CodeDto codeDto : list) {
                            if (string.equals(codeDto.getItemNo())) {
                                resultCodes.add(codeDto);
                                break;
                            }
                        }
                    }
                    codeMap.put(key, resultCodes);
                }
            }
            return list;
        }
    }
    
    private void reloadLoad(){
        
        int size = codeMap.size();
        String[] keys = new String[size];
        codeMap.keySet().toArray(keys);
        for(int i=0;i<size;i++){
            String key = keys[i];
            String[] keyA = key.split(",");
            int len=keyA.length;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("itemType", keyA[0]);
            CodeResponse response = codeServiceClient.selectList(params);
            List<CodeDto> list = (List<CodeDto>) response.getList();
            if (list != null && !list.isEmpty()) {
                if(len>1){
                    List<CodeDto> resultCodes = new ArrayList<CodeDto>();
                    for(int k=1;k<len;k++){
                        for (CodeDto codeDto : list) {
                            if (keyA[k].equals(codeDto.getItemNo())) {
                                resultCodes.add(codeDto);
                                break;
                            }
                        }
                    }
                    codeMap.put(key, resultCodes);
                }
                else{
                    codeMap.put(key, list);
                }
            }
        }
    }
    
    /**
     * 根据code编号获取所有其下的code子值
     * @param itemType
     * @return
     */
    public List<CodeDto> getCodes(String itemType) {
        return getData(itemType);
    }
    
    /**
     * 根据code编号获取所有其下的code子值
     * @param itemType
     * @return
     */
    public List<CodeDto> getCodes(String itemType, String... itemNo) {
        return getData(itemType,itemNo);
    }
    
    /**
     * 根据code编号 和 节点编号 itemNo 获取 健
     * @param itemType code编号 
     * @param itemNo 节点编号
     * @return
     */
    public String getItemKeyByItemNo(String itemType,String itemNo) {
        List<CodeDto> list = getData(itemType);
        if (list != null && !list.isEmpty()) {
            for (CodeDto codeDto : list) {
                if (codeDto.getItemNo().equals(itemNo)) {
                    return codeDto.getItemKey();
                }
            }
        }
        return "";
    }
    
    /**
     * 根据code编号 和 节点编号 itemNo 获取 值
     * @param itemType code编号 
     * @param itemNo 节点编号
     * @return
     */
    public String getItemValueByItemNo(String itemType,String itemNo) {
        List<CodeDto> list = getData(itemType);
        if (list != null && !list.isEmpty()) {
            for (CodeDto codeDto : list) {
                if (codeDto.getItemNo().equals(itemNo)) {
                    return codeDto.getItemValue();
                }
            }
        }
        return null;
    }
    
    /**
     * 根据code编号 和 节点编号 itemNo 获取 健
     * @param itemType code编号 
     * @param itemNo 节点编号
     * @return
     */
    public String getItemKey(String itemType,String itemValue) {
        List<CodeDto> list = getData(itemType);
        if (list != null && !list.isEmpty()) {
            for (CodeDto codeDto : list) {
                if (codeDto.getItemValue().equals(itemValue)) {
                    return codeDto.getItemKey();
                }
            }
        }
        return null;
    }
    
    /**
     * 根据code编号 和 节点编号 itemNo 获取  值
     * @param itemType code编号 
     * @param itemNo 节点编号
     * @return
     */
    public String getItemValue(String itemType,String itemKey) {
        List<CodeDto> list = getData(itemType);
        if (list != null && !list.isEmpty()) {
            for (CodeDto codeDto : list) {
                if (codeDto.getItemKey().equals(itemKey)) {
                    return codeDto.getItemValue();
                }
            }
        }
        return "";
    }
    
    /**
     * 判断 健 是否存在
     * @param itemType
     * @param itemKey
     * @return
     */
    public boolean isExistByItemKey(String itemType,String itemKey) {
        List<CodeDto> list = getData(itemType);
        if (list != null && !list.isEmpty()) {
            for (CodeDto codeDto : list) {
                if (codeDto.getItemKey().equals(itemKey)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 判断 值 是否存在
     * @param itemType
     * @param itemValue
     * @return
     */
    public boolean isExistByItemValue(String itemType,String itemValue) {
        List<CodeDto> list = getData(itemType);
        if (list != null && !list.isEmpty()) {
            for (CodeDto codeDto : list) {
                if (codeDto.getItemValue().equals(itemValue)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    {
        /**
         * 本地代码同步器
         */
        Thread timer = new Thread(){
          public void run(){
              try {
                sleep(1000);
                reloadLoad();
            } catch (InterruptedException e) {
            }
          }
        };
        timer.start();
    }
}
