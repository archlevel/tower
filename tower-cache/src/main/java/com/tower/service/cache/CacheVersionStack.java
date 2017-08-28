package com.tower.service.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存同步优化机制（一个对象的多个版本变更合并成一次变更）
 * @author alexzhu
 *
 */
public class CacheVersionStack {

    private static final ThreadLocal<Map<String,Integer>> tabIncs = new InheritableThreadLocal<Map<String,Integer>>();
    private static final ThreadLocal<Map<String,Integer>> recIncs = new InheritableThreadLocal<Map<String,Integer>>();
    private static final ThreadLocal<Map<String,CacheVersion>> tabvs = new InheritableThreadLocal<Map<String,CacheVersion>>();
    private static final ThreadLocal<ICacheVersion<CacheVersion>> cachers = new InheritableThreadLocal<ICacheVersion<CacheVersion>>();
    
    public static void init(){
    	Map<String,CacheVersion> tabv = new ConcurrentHashMap<String,CacheVersion>();
    	tabvs.set(tabv);
    	
    	Map<String,Integer> tabInc = new ConcurrentHashMap<String,Integer>();
    	tabIncs.set(tabInc);
		
		Map<String,Integer> recInc = new ConcurrentHashMap<String,Integer>();
		recIncs.set(recInc);
    }
    
    public static void setTabInc(String id) {
    	Map<String,Integer> tabInc = tabIncs.get();
    	Map<String,CacheVersion> tabv = tabvs.get();
    	if(tabInc!=null && tabInc.containsKey(id)){
    		Integer cnt = tabInc.get(id);
    		tabInc.put(id, cnt++);
    	}
    	if(tabv!=null){
			tabv.remove(id);
		}
    }
    
    public static void setRecInc(String id) {
    	Map<String,Integer> recInc = recIncs.get();
    	Map<String,CacheVersion> tabv = tabvs.get();
    	if(recInc!=null && recInc.containsKey(id)){
    		Integer cnt = recInc.get(id);
    		recInc.put(id, cnt++);
    	}
    	if(tabv!=null){
    		tabv.remove(id);
    	}
    }
    
    public static void setTabv(String id,CacheVersion version) {
    	Map<String,CacheVersion> tabv = tabvs.get();
    	if(tabv!=null && !tabv.containsKey(id)){
    		tabv.put(id, version);
    	}
    }
    
    public static Map<String,Integer> getTabIncs() {
    	return tabIncs.get();
    }
    
    public static Map<String,Integer> getRecIncs() {
    	return recIncs.get();
    }
    
    public static Map<String,CacheVersion> getTabvs() {
    	return tabvs.get();
    }
    
    public static void set(ICacheVersion<CacheVersion> cacher){
    	cachers.set(cacher);
    }
    
    public static ICacheVersion<CacheVersion> getCacher(){
    	return cachers.get();
    }
    
    public static void unset() {
    	tabIncs.remove();
    	recIncs.remove();
    	tabvs.remove();
    	cachers.remove();
    }
}
