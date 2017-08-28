package com.tower.service.impl.generate.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tower.service.gen.AbsGen;
import com.tower.service.gen.TemplateFactoryBean;

import freemarker.template.Configuration;

public class ServiceImplGen extends AbsGen{
	/**
	 * 
	 * @param pkType 主健类型
	 * @param pkgname 包名
	 * @param name domain对象名
	 * @param targetJava 目标类目录
	 */
	public ServiceImplGen(String pkType, String pkgname, String name,
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
		root.put("subName", (name.substring(0, 1).toLowerCase() + name.substring(1)));

		String java = targetJava==null?"src/main/java/":targetJava;

		package_ = package_.replaceAll("\\.", "/");

		List<String> modelNames = new ArrayList<String>();

		try {
			create("svcimpl.ftl", root, java + package_ + "/impl/" + name
					+ "ServiceImpl.java");
			System.out.println("I"+name+"ServiceImpl生成成功");
		} catch (Exception e) {
			System.out.println("I"+name+"ServiceImpl生成失败"+e.getMessage());
		}
	}

	public static void main(String[] args) {
		new ServiceImplGen("Integer", "com.ebls.service.oft", "Hello",
				"src/main/java/");
	}
}
