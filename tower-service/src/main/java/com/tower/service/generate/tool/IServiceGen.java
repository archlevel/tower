package com.tower.service.generate.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tower.service.gen.AbsGen;
import com.tower.service.gen.TemplateFactoryBean;

import freemarker.template.Configuration;

public class IServiceGen extends AbsGen {

	/**
	 * 
	 * @param pkType 主健类型
	 * @param pkgname 包名
	 * @param name domain对象名
	 * @param targetJava 目标类目录
	 */
	public IServiceGen(String pkType, String pkgname, String name,
			String targetJava) {
		TemplateFactoryBean templateFactoryBean = new TemplateFactoryBean();
		templateFactoryBean
				.setPath("classpath:/META-INF/generate/templates/svc");
		try {
			setConfiguration((Configuration) templateFactoryBean.getObject());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Map root = new HashMap();
		String package_ = pkgname;

		root.put("pkType", pkType);
		root.put("package", package_);
		root.put("name", name);

		String java = targetJava==null?"src/main/java/":targetJava;

		package_ = package_.replaceAll("\\.", "/");

		List<String> modelNames = new ArrayList<String>();

		try {
			create("isvc.ftl", root, java + package_ + "/I" + name
					+ "Service.java");
			System.out.println("I"+name+"Service生成成功");
		} catch (Exception e) {
			System.out.println("I"+name+"Service生成失败"+e.getMessage());
		}
	}

	public static void main(String[] args) {
		new IServiceGen("Integer", "com.ebls.service.oft", "Hello",
				"src/main/java/");
	}
}
