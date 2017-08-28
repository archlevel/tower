package com.tower.service.domain.generate.tool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tower.service.gen.AbsGen;
import com.tower.service.gen.TemplateFactoryBean;
import com.tower.service.util.Pair;

import freemarker.template.Configuration;

public class IDtoGen extends AbsGen{
	/**
	 * 
	 * @param pkgname 包名
	 * @param name Dto对象名
	 * @param targetJava 目标类目录
	 */
	public IDtoGen(List<Pair<String,Field>> fields,String pkgname,String name,String targetJava){
		
		TemplateFactoryBean templateFactoryBean = new TemplateFactoryBean();
	    templateFactoryBean.setPath("classpath:/META-INF/generate/templates/dto");
	    try {
			setConfiguration((Configuration) templateFactoryBean.getObject());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Map root = new HashMap();
		String package_ = pkgname;

		root.put("package", package_);
	    root.put("name", name);
	    root.put("fields", fields);
	    
	    String java = targetJava;

	    package_ = package_.replaceAll("\\.", "/");

	    List<String> modelNames = new ArrayList<String>();
	    
	    try {
			create("idto.ftl", root, java + package_ + "/dto/" + name + "Dto.java");
			System.out.println(name+"Dto生成成功");
		} catch (Exception e) {
			System.out.println(name+"Dto生成失败"+e.getMessage());
		}
	}
	
	public static void main(String[] args){
		new IDtoGen(null,"com.ebls.service.oft","Hello","src/main/java/");
	}
}
