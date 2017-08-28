package ${package}.dao;

import com.tower.service.dao.IDAO;
import com.tower.service.dao.IFKDAO;
<#if tab.pkFieldNum==1>
	<#if tab.pkFieldType.javaType="Integer">	
import com.tower.service.dao.IIDAO;
	<#elseif tab.pkFieldType.javaType="java.math.BigInteger">
import com.tower.service.dao.IBIDAO;
	<#elseif tab.pkFieldType.javaType="String">
import com.tower.service.dao.ISDAO;
	<#else>
import com.tower.service.dao.ILDAO;
	</#if>
</#if>

import ${package}.dao.model.${name};

<#if tab.pkFieldNum==1>
	<#if tab.pkFieldType.javaType="Integer">	
public interface I${name}DAO<T extends ${name}> extends IIDAO<T>,IFKDAO<T>,IDAO<T> {
	<#elseif tab.pkFieldType.javaType="java.math.BigInteger">
public interface I${name}DAO<T extends ${name}> extends IBIDAO<T>,IFKDAO<T>,IDAO<T> {	
	<#elseif tab.pkFieldType.javaType="String">
public interface I${name}DAO<T extends ${name}> extends ISDAO<T>,IFKDAO<T>,IDAO<T> {
	<#else>
public interface I${name}DAO<T extends ${name}> extends ILDAO<T>,IFKDAO<T>,IDAO<T> {
	</#if>
</#if>

}
