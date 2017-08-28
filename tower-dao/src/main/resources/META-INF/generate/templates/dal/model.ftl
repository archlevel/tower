package ${package}.dao.model;

import java.util.HashMap;
import java.util.Map;

import com.tower.service.annotation.JField;
import com.tower.service.cache.AbsModel;
import com.tower.service.cache.IModel;

public class ${name} extends AbsModel implements IModel{
	
	static Map<String,Integer> fks = new HashMap<String,Integer>();
	
	static{
		/**
		 * 把该model相关的外键属性字段注册到fks map中
		 */
		//eg:fks.put("cityId",0);
	}
	/**
	 * 是否是外键字段
     */
    public static boolean isFk(String name){
    	return fks.containsKey(name);
    }

<#list cols as col>
	/**
	 * ${col.desc}
	 */
	@JField(name="${col.name}")
 	<#if tab.pkFieldNum==1 && col.isPK="yes"> 
 	<#if col.type.javaType="Integer" || col.type.javaType="Long" || col.type.javaType="Float" || col.type.javaType="Double" || col.type.javaType="java.math.BigInteger" || col.type.javaType="String">
 	protected ${col.type.javaType} id;
 	
  	public void setId(${col.type.javaType} id){
  		this.id=id;
  	}
  	
  	public ${col.type.javaType} getId(){
  		return this.id;
  	}
 	<#else>
  	protected String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	</#if>
	<#elseif col.isPK="no">		
  	private ${col.type.javaType} ${col.fieldName};
  	
  	public void set${col.methodName}(${col.type.javaType} ${col.fieldName}){
  		this.${col.fieldName}=${col.fieldName};
  	}
  	
  	public ${col.type.javaType} get${col.methodName}(){
  		return this.${col.fieldName};
  	}
	</#if>	  	
</#list>
	
    
	/**
	 * 保存时非空数据项校验；
	 */
	public boolean validate(){
		boolean passed = true;
		<#list cols as col>
	  	<#if col.isPK="no">
	  	<#if col.isAllowNull="no">
	  	if(get${col.methodName}()==null){
	  		throw new RuntimeException("${col.fieldName} cannot be null!");
	  	}
	  	</#if>  	
	  	</#if>
		</#list>
		return passed;
	}
	
	/**
   	* 保存时对应的分表；
   	*/
  	private String TowerTabName;

  	public String getTowerTabName() {
    	return TowerTabName;
  	}

  	@Override
  	public void setTowerTabName(String TowerTabName) {
    	this.TowerTabName = TowerTabName;
  	}
}
