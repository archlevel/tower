package com.tower.service.generator.manager;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
;

/**
 * Created by kevin on 15/1/4.
 */
public class LoadFactory {
    private static String DEFAULT_SPID="00";
    /**
     * 异常所在层次
     */
    private static final List<String> levels= ImmutableList.of("CONTROLLER","SERVICE","DATA_ACCESS");

    /**
     * 加载方式
     */
    public static enum LoaderType {
        CLOSE,FILE, URL, DB;
    }

    public static ILoader getLoader(LoaderType loaderType,
                                    File sourceDirectory,
                                    String configFile,
                                    String url,
                                    String dburl,
                                    String dbuser,
                                    String dbpwd,
                                    String tableName,
                                    String spid,
                                    String level
    ) throws MojoExecutionException {
        if(LoaderType.CLOSE ==loaderType){
            return null;
        }
       int levelNum =getLevel(level);

        ILoader exceptionMessageLoader = null;
        if (loaderType == LoaderType.DB) {
            if(Strings.isNullOrEmpty(dburl)
                    || Strings.isNullOrEmpty(dbuser)
                    ||Strings.isNullOrEmpty(dbpwd)
                    ||Strings.isNullOrEmpty(tableName)){
                throw  new MojoExecutionException("数据库配置不完整");
            }
            String sql="select code,message,type,level,spid from "+tableName;
            if(levelNum>0){
                sql+=sql.contains("where")?" and ":" where ";
                sql+="level="+levelNum;
            }
            //if(!DEFAULT_SPID.equals(spid)){
                sql+=sql.contains("where")?" and ":" where ";
                sql+="spid="+Integer.valueOf(spid);
          //  }

            exceptionMessageLoader = new DataBaseLoader(dburl,dbuser,dbpwd,sql);
        } else if (loaderType == LoaderType.FILE) {
            if (configFile == null) {
                throw new MojoExecutionException("配置文件不存在");
            }
            exceptionMessageLoader = new FileLoader(sourceDirectory, configFile);
        } else if (loaderType == LoaderType.URL) {
            if (Strings.isNullOrEmpty(url)) {
                throw new MojoExecutionException("url 不存在");
            }

            if(levelNum>0){
                url+=url.contains("?")?" &level=":"?level=";
                url+=levelNum;
            }
           // if(!DEFAULT_SPID.equals(spid)){
            url+=url.contains("?")?"&spid=":"?spid=";
            url+=Integer.valueOf(spid);
            //}
            exceptionMessageLoader = new UrlLoader(url);
        } else {
            throw new MojoExecutionException("create loader failed");
        }

        return exceptionMessageLoader;
    }

    private static int getLevel(String exceptionLevel){
       if(levels.contains(exceptionLevel)){
            return levels.indexOf(exceptionLevel)+1;
        }
        return 0;
    }

}
