package com.tower.service.generator.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.tower.service.generator.ExceptionMessage;

/**
 * 文件方式加载异常信息配置
 * <pre>
 *     文件格式
 *     1=参数错误
 *     2=状态不合法
 * </pre>
 */
public class FileLoader extends AbstractLoader {

    private File sourceDirectory ;
    private String configFile;
    public FileLoader(final File sourceDirectory,final String configFile){
        this.sourceDirectory=sourceDirectory;
        this.configFile=configFile;
    }

    @Override
    public List<ExceptionMessage> getExceptionMessages() throws MojoExecutionException {

            File sd = sourceDirectory;
            File cf = new File(sd.getAbsolutePath() + "/" + configFile);
            if (!cf.exists()) {
                throw new MojoExecutionException("配置文件不存在");
            }
            List<String> readLines = null;
            try {
                readLines = Files.readLines(cf, Charsets.UTF_8);
            } catch (IOException e) {
                throw new MojoExecutionException("读取错误编码配置文件错误");
            }
            List<ExceptionMessage> messageList = Lists.newArrayList();
            if (readLines != null && !readLines.isEmpty()) {
                int lineCount = 0;
                for (String line : readLines) {
                    lineCount++;
                    String[] lineArr = line.split("=");
                    if (lineArr[0].startsWith("#")) {
                        continue;
                    }
                    ExceptionMessage em = new ExceptionMessage(Integer.valueOf(lineArr[0]), lineArr[1]);
                    if (messageList.contains(em)) {
                        throw new MojoExecutionException("错误编码:" + em.getCode() + "重复 at line :" + lineCount);
                    } else {
                        messageList.add(em);
                    }
                }

            }
            return messageList;
        }

}
