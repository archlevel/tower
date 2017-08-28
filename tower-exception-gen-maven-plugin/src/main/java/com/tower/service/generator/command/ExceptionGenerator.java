package com.tower.service.generator.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.tower.service.generator.Constant;
import com.tower.service.generator.ExceptionMessage;
import com.tower.service.generator.manager.ILoader;
import com.tower.service.generator.manager.LoadFactory;

/**
 * 生成异常代码
 * mvn exception-generator:gen
 *
 * @goal gen
 */
public class ExceptionGenerator
        extends AbstractMojo {
    /**
     * Location of the file.
     *
     * @parameter property="project.build.directory"
     * @required
     */
    private File outputDirectory;

    /**
     * @parameter property="project.basedir"
     * @required
     * @readonly
     */
    private File basedir;

    /**
     * @parameter property ="project.build.sourceDirectory"
     * @required
     * @readonly
     */
    private File sourceDirectory;


    /**
     * 生成异常枚举的完整类名
     * @parameter property="exceptionEnumClass"
     */
    private String exceptionEnumClass;


    /**
     * File加载方式配置文件地址 src 下的相对地址
     * @parameter property="configFile"
     */
    private String configFile;
    /**
     * 异常等级,仅File方式配置有效
     * parameter property="level"

    private Integer level = Constant.DEFAULT_LEVEL;
     */

    /**
     * 异常枚举实现接口
     * @parameter property="interface"
     */
    private String interfaceClass = Constant.DEFAULT_INTERFACE_CLASS;
    /**
     * 服务ID
     * @parameter property="spId"
     */
    private String spId = Constant.DEFAULT_SPID;

    /**
     * 配置信息加载方式.
     * FILE,DB,URL
     *
     * @parameter property="loadType"
     */
    private String loadType = "FILE";

    /**
     * API加载方式的API地址
     * @parameter property="url"
     */
    private String url;
    /**
     * 异常层级 .
     *  DATA_ACCESS("数据访问层"),CONTROLLER("控制层"),SERVICE("业务层");
     * @parameter property="exceptionLevel"
     */
    private String exceptionLevel="NORMAL";

    /**
     * 数据库链接
     * @parameter property="dburl"
     */
    private String dburl;

    /**
     * 数据库用户
     * @parameter property="dbuser"
     */
    private String dbuser;

    /**
     * 数据库用户密码
     * @parameter property="dbpwd"
     */
    private String dbpwd;

    /**
     *  tableName .
     *
     * @parameter property="tableName"
     */
    private String tableName;

    /**
     *
     * @parameter property="project.groupId"
     */
    private String groupId;

    /**
     *
     * @parameter property="project.artifactId"
     */
    private String artifactId;


    private static final String LINE_SEPARATOR = System.getProperty("line.separator");


    public void execute()
            throws MojoExecutionException {
        genSpIdFile();
        if (!Strings.isNullOrEmpty(exceptionEnumClass) && !"AUTO".equals(exceptionEnumClass.toUpperCase())) {
            this.gen();
            this.getLog().info("生成异常信息成功!!!");
            //genSpIdFile
        } else {
            //String aa[] = artifactId.split("[_|-]");
          //  exceptionEnumClass=groupId+"."+ Joiner.on(".").join(aa)+"."+this.getClzzzName(aa);
          //  this.gen();
          //  this.getLog().info("生成异常信息成功!!!");
            if(!Strings.isNullOrEmpty(exceptionEnumClass) && "AUTO".equals(exceptionEnumClass.toUpperCase())){
                this.genExceptionEnumClass();
                this.execute();
            }else {
                this.getLog().info("exception generator exit!!");
            }
        }
    }

    private void genExceptionEnumClass(){
        String aa[] = artifactId.split("[_|-]");
        // exceptionEnumClass=groupId+"."+ Joiner.on(".").join(aa)+"."+this.getClzzzName(aa);
       String packageName=groupId;
        if(aa.length==2){
            packageName+="."+aa[aa.length-1];
        }
        packageName+=".exception";
        exceptionEnumClass=packageName+"."+this.getClzzzName(aa);
    }

    private void genSpIdFile() throws MojoExecutionException{
        if(Strings.isNullOrEmpty(spId) || "00".equals(spId)||"0".equals(spId)){
            return ;
        }
        if(!artifactId.endsWith("-service")){
        	return;
        }
        String metaInfPath = this.getResourcesDirectoryPath()+"/META-INF/";
        File metaInfoFile= new File(metaInfPath);
        if(!metaInfoFile.exists()){
            metaInfoFile.mkdirs();
        }
        File spIdFile = new File(metaInfPath+"SPID");
        try {
            Files.write(this.spId.getBytes(),spIdFile);
        } catch (IOException e) {
           throw new MojoExecutionException(e.getMessage());
        }

    }

    private String getResourcesDirectoryPath(){
       return  sourceDirectory.getParentFile().getAbsoluteFile()+"/resources";
    }

    private void delete(String parentPath,String fileName){
        File f=new File(parentPath+"/"+fileName);
        if(f.exists()){
            f.delete();
        }
    }
    /**
     * 生成
     *
     * @throws MojoExecutionException
     */
    private void gen() throws MojoExecutionException {

        String _package = this.getPackage(exceptionEnumClass);
        String _class = this.getClazz(exceptionEnumClass);

        String tpl = getTemplate();
        if(Strings.isNullOrEmpty(loadType)){
            this.getLog().info("取消生成：加载方式配置错误！");
            return ;
        }
        LoadFactory.LoaderType lt = LoadFactory.LoaderType.valueOf(loadType.toUpperCase());
        if(lt==null){
            this.getLog().info("取消生成：加载方式配置错误！");
            return ;
        }
        ILoader loader = LoadFactory.getLoader(lt, sourceDirectory, configFile, url, dburl, dbuser, dbpwd, tableName,spId,exceptionLevel);
        if(loader==null){
            this.getLog().info("取消生成：显式关闭！");
            return ;
        }

        List<ExceptionMessage> exceptionMessages = loader.getExceptionMessages();

        if( exceptionMessages.isEmpty()){
            this.getLog().info("取消生成：没有相关异常定义");
            //delete(this.getDestDirectory(_package).getAbsolutePath(),_class+".java");
            return ;
        }

        try {
            writeFile(this.getDestDirectory(_package), _class, this.build(tpl, _package, _class, this.interfaceClass, exceptionMessages));
        } catch (IOException e) {
            throw new MojoExecutionException("生成失败");
        }
    }

    /**
     * 获取目标目录
     *
     * @param _package 包名
     * @return
     */
    private File getDestDirectory(final String _package) {
        String packagePath = _package.replaceAll("\\.", "/");
        File dest = new File(sourceDirectory.getAbsolutePath() + "/" + packagePath);
        return dest;
    }

    private String build(final String tpl, final String _package, final String _class, final String interfaceClass, final List<ExceptionMessage> exceptionMessages) {
        String dest = tpl;
        dest = dest.replace("#{" + Constant.PAGE_NAME + "}", _package);
        dest = dest.replace("#{" + Constant.CLASS_NAME + "}", _class);
        dest = dest.replace("#{" + Constant.AUTHOR + "}", "maven plugin with "+loadType);
        dest = dest.replace("#{" + Constant.DATE + "}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (Strings.isNullOrEmpty(interfaceClass)) {
            dest = dest.replace("#{" + Constant.IMPORT + "}", "");
            dest = dest.replace("#{" + Constant.INTERFACE + "}", "");
        } else {
            dest = dest.replace("#{" + Constant.IMPORT + "}", "import " + interfaceClass + ";");
            dest = dest.replace("#{" + Constant.INTERFACE + "}", "implements " + this.getClazz(interfaceClass));
        }
        StringBuffer enums = new StringBuffer(1024);
        boolean isFirst = true;
        for (ExceptionMessage exceptionMessage : exceptionMessages) {
            if (isFirst) {
                isFirst = false;
            } else {
                enums.append("," + LINE_SEPARATOR);
            }
            String genCode = exceptionMessage.getType() + "_" + ExceptionMessage.genCode(exceptionMessage.getCode(),Constant.CODE_LENGTH);

            enums.append("    " + "/**");
            enums.append(LINE_SEPARATOR);
            enums.append("    " + " * " + exceptionMessage.getMessage() + ".");
            enums.append(LINE_SEPARATOR);
            enums.append("    " + " * <p>type:" + exceptionMessage.getType() + "</p>");
            enums.append(LINE_SEPARATOR);
            enums.append("    " + " * <p>code:" + exceptionMessage.getFullCode() + "</p>");
            enums.append(LINE_SEPARATOR);
            enums.append("    " + " */");
            enums.append(LINE_SEPARATOR);


            enums.append(String.format("    " + Constant.PREFIX + "%s(\"%s\",\"%s\",%s,\"%s\")",
                    genCode,
                    exceptionMessage.getCode(),
                    exceptionMessage.getMessage(),
                    exceptionMessage.getType()
                    , exceptionMessage.getSpid()));
        }

        dest = dest.replace("#{" + Constant.ENUMS + "}", enums.toString());
        return dest;
    }

    private String getPackage(String fullName) {
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    private String getClazz(String fullName) {

        return fullName.substring(fullName.lastIndexOf(".") + 1);
    }

    /**
     * 保存生成内容
     *
     * @param parent   生成目录
     * @param fileName 文件名
     * @param context  文件内容
     * @throws IOException
     */
    private void writeFile(File parent, String fileName, String context) throws IOException {

        if (!parent.isDirectory()) {
            parent.mkdirs();
        }
        File exceptionCoe = new File(parent, fileName + ".java");
        Files.write(context, exceptionCoe, Charsets.UTF_8);
    }

    /**
     * 获取模版
     *
     * @return 模版内容
     * @throws IOException
     */
    private String getTemplate() throws MojoExecutionException {
        InputStream is = this.getClass().getResourceAsStream(Constant.TPL_NAME);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer stringBuffer = new StringBuffer(1024);
        try {
            String s;
            while ((s = br.readLine()) != null)
                stringBuffer.append(s + LINE_SEPARATOR);
        } catch (IOException e) {
            throw new MojoExecutionException("获取模版文件失败");
        }
        return stringBuffer.toString();
    }

    private String getClzzzName(String ... nameBlocks){
        String className="";
        for(String a:nameBlocks){
            className+=StringUtils.capitalize(a.toLowerCase());
        }
        return className+"ExceptionMessage";
    }
}

