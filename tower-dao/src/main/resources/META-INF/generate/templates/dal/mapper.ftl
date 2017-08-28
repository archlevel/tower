package ${package}.dao.ibatis.mapper;

<#if tab.pkFieldNum==1>
	<#if tab.pkFieldType.javaType="Integer">	
import com.tower.service.dao.ibatis.IIMapper;
	<#elseif tab.pkFieldType.javaType="java.math.BigInteger">
import com.tower.service.dao.ibatis.IBigIMapper;
	<#elseif tab.pkFieldType.javaType="String">
import com.tower.service.dao.ibatis.ISMapper;
	<#else>
import com.tower.service.dao.ibatis.ILMapper;
	</#if>
</#if>

import ${package}.dao.model.${name};

<#if tab.pkFieldNum==1>
	<#if tab.pkFieldType.javaType="Integer">	
public interface ${name}Mapper extends IIMapper<${name}>{
	<#elseif tab.pkFieldType.javaType="java.math.BigInteger">
public interface ${name}Mapper extends IBigIMapper<${name}>{	
	<#elseif tab.pkFieldType.javaType="String">
public interface ${name}Mapper extends ISMapper<${name}>{
	<#else>
public interface ${name}Mapper extends ILMapper<${name}>{
	</#if>
</#if>

}
