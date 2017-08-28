package com.#{company}.service.#{artifactId};

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.tower.service.annotation.JField;
import com.tower.service.domain.PkType;
import com.tower.service.domain.generate.tool.IDtoGen;
import com.tower.service.generate.tool.IServiceGen;
import com.tower.service.impl.generate.tool.ServiceImplGen;
import com.tower.service.util.Pair;

public class ServiceGen {

	static String pkgName = "com.#{company}.service.#{artifactId}";//包名，建议不要修改
	
	static String path = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParentFile().getAbsolutePath();//项目绝对路径，建议不要修改
	static String Domain_Project_Path = path+"/#{artifactId}-domain/src/main/java/";//#{artifactId}-domain 项目路径，建议不要修改
	static String Service_Project_Path = path+"/#{artifactId}-service/src/main/java/";//#{artifactId}-service 项目路径，建议不要修改
	static String Service_Impl_Project_Path = path+"/#{artifactId}-service-impl/src/main/java/";//#{artifactId}-service-impl 项目路径，建议不要修改
	
    private static Field[] getFields(String name) throws ClassNotFoundException{
    	Class cls = Class.forName(pkgName+".dao.model."+name);
    	return cls.getDeclaredFields();
    }
    
    private static List<Pair<String,Field>> genPair(String model) throws ClassNotFoundException{
    	Field[] field = getFields(model);
    	int len = field.length;
    	List<Pair<String,Field>> fields = new ArrayList<Pair<String,Field>>();
    	for(int i=0;i<len;i++){
    		JField ann = field[i].getAnnotation(JField.class);
			if(ann != null){
				String first = null;
				String last = "";
				String name = field[i].getName();
				first = name.substring(0,1).toUpperCase();
				if(name.length()>1){
					last = name.substring(1);
				}
				Pair<String,Field> pair = new Pair<String,Field>(first+last,field[i]);
				fields.add(pair);
			}
    	}
    	return fields;
    }

    public static void main(String[] args) {
        try {
        	
        	String model = "SoaSp";//model name，可以修改
        	String pkType = PkType.INTEGER;//主健类型，根据model的实际主健类型进行修改
        	
        	new IDtoGen(genPair(model),pkgName, model,Domain_Project_Path);//生成dto，建议不要修改
        	new IServiceGen(pkType, pkgName, model,Service_Project_Path);//生成iservice，建议不要修改
        	new ServiceImplGen(pkType, pkgName, model,Service_Impl_Project_Path);//生成serviceimpl，建议不要修改
        	System.exit(1);//建议不要修改
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
