package com.tower.service.generator.manager;

import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;

import com.tower.service.generator.ExceptionMessage;

/**
 * 配置文件加载
 */
public interface ILoader {
    /**
     * 获取要生成的内容
     *
     * @return 生成内容列表
     * @throws MojoExecutionException
     */
    List<ExceptionMessage> getExceptionMessages() throws MojoExecutionException;
}
