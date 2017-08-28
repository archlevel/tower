package com.tower.service.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tower.service.annotation.JField;
import com.tower.service.reflection.MetaObject;
import com.tower.service.reflection.factory.DefaultObjectFactory;

public class BeanUtil {
    
    /**
     * 通过反射获取到对象的属性值，支持.运算
     * @param obj 对象
     * @param property 
     * @return
     */
    public static Object getValue(Object obj,String property){
        MetaObject metaObject = DefaultObjectFactory.getMetaObject(obj);
        return metaObject.getValue(property);
    }
    
	public static String getJField(Class model,String property,Class<JField> annotation){
		Field tmp = null;
		try {
			tmp = model.getDeclaredField(property);
			JField ann = tmp.getAnnotation(annotation);
			if(ann != null){
				return ann.name();
			}
			throw new RuntimeException(annotation.getCanonicalName()+" does't declared");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static Map<String, Object> beanToMap(Object model) {
        Field[] fields = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(model);
                if (value == null) {
                    continue;
                } else { 
                    String type = field.getGenericType().toString();    //获取属性的类型
                     if(type.equals("class java.util.Date")){
                         map.put(field.getName(), DateUtil.getDateFormat((Date)field.get(model), "yyyy-MM-dd HH:mm:ss"));  
                     } else {  
                         map.put(field.getName(), field.get(model));
                     }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }
	
	public static void main(String[] args){
	    DateUtil util = new DateUtil();
	    MetaObject metaObject = DefaultObjectFactory.getMetaObject(util);
	    System.out.println(metaObject.getValue("weeks"));
	}
}
