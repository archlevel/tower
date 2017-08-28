package ${package}.dao.ibatis;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
<#if tab.pkFieldNum==1>
	<#if tab.pkFieldType.javaType="Integer">	
import com.tower.service.dao.ibatis.AbsIntIDIBatisDAOImpl;
import com.tower.service.dao.ibatis.IIMapper;
	<#elseif tab.pkFieldType.javaType="java.math.BigInteger">
import com.tower.service.dao.ibatis.AbsBigIIDIBatisDAOImpl;
import com.tower.service.dao.ibatis.IBigIMapper;	
	<#elseif tab.pkFieldType.javaType="String">
import com.tower.service.dao.ibatis.AbsStrIDIBatisDAOImpl;
import com.tower.service.dao.ibatis.ISMapper;
	<#else>
import com.tower.service.dao.ibatis.AbsLongIDIBatisDAOImpl;
import com.tower.service.dao.ibatis.ILMapper;
	</#if>
</#if>

import com.tower.service.dao.ibatis.IBatisDAOException;

import ${package}.dao.I${name}DAO;
import ${package}.dao.ibatis.mapper.${name}Mapper;
import ${package}.dao.model.${name};
import com.tower.service.dao.ibatis.SqlmapUtils;
import com.tower.service.exception.DataAccessException;

@Repository("${subName}DAO")
<#if tab.pkFieldNum==1>
	<#if tab.pkFieldType.javaType="Integer">	
public class ${name}IbatisDAOImpl extends AbsIntIDIBatisDAOImpl<${name}> implements I${name}DAO<${name}> {
	<#elseif tab.pkFieldType.javaType="java.math.BigInteger">
public class ${name}IbatisDAOImpl extends AbsBigIIDIBatisDAOImpl<${name}> implements I${name}DAO<${name}> {
<#elseif tab.pkFieldType.javaType="String">
public class ${name}IbatisDAOImpl extends AbsStrIDIBatisDAOImpl<${name}> implements I${name}DAO<${name}> {	
	<#else>
public class ${name}IbatisDAOImpl extends AbsLongIDIBatisDAOImpl<${name}> implements I${name}DAO<${name}> {
	</#if>
</#if>

	@Resource(name = "${masterSessionFactory}")
	private SqlSessionFactory masterSessionFactory;
	
	@Resource(name = "${slaveSessionFactory}")
	private SqlSessionFactory slaveSessionFactory;
	
	@Resource(name = "${mapQuerySessionFactory}")
	private SqlSessionFactory mapQuerySessionFactory;
	
	@Override
	public Class<${name}Mapper> getMapperClass() {
		
		return ${name}Mapper.class;
	}
	
	@Override
	public Class<${name}> getModelClass() {
		
		return ${name}.class;
	}
	
	@Override
	public boolean isFk(String property) {
	
		return ${name}.isFk(property);
	}
	
	public String getTableName() {
    	return "${tab.name}";
  	}
  
	@Override
	public SqlSessionFactory getMasterSessionFactory(){
		return masterSessionFactory;
	}
	
	
	@Override
	public SqlSessionFactory getSlaveSessionFactory(){
		if (slaveSessionFactory == null||SqlmapUtils.hasTransaction()) {
 			return getMasterSessionFactory();
 		}
 		return slaveSessionFactory;
	}
	
	@Override
	public SqlSessionFactory getMapQuerySessionFactory(){
		if (mapQuerySessionFactory == null||SqlmapUtils.hasTransaction()) {
 			return getSlaveSessionFactory();
 		}
 		return mapQuerySessionFactory;
	}
	
	<#if tab.pkFieldNum != 1>
	@Override
	public ${name} queryById(Long id){
		<#if tab.pkFieldNum==0>
		throw new RuntimeException("该表没有主键定义，该方法不能适用，请重新实现！");
		<#else>
		throw new RuntimeException("复合主键，该方法不能适用，请重新实现！");
		</#if>		
	}
	<#else>
	
		<#if tab.pkFieldType.javaType="Integer">	
	public Integer insert(${name} model, String tabNameSuffix) {
		<#elseif tab.pkFieldType.javaType="java.math.BigInteger">
	public java.math.BigInteger insert(${name} model, String tabNameSuffix) {
		<#elseif tab.pkFieldType.javaType="String">
	public String insert(${name} model, String tabNameSuffix) {	
		<#else>
	public Long insert(${name} model, String tabNameSuffix) {
		</#if>
		if (logger.isDebugEnabled()) {
      		logger.debug("insert(T model={}, String tabNameSuffix={}) - start", model, tabNameSuffix); //$NON-NLS-1$
    	}
    	
    	validate(model);
    	
    	model.setTowerTabName(this.get$TowerTabName(tabNameSuffix));
    
    	SqlSessionFactory sessionFactory = this.getMasterSessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    	try {
    		<#if tab.pkFieldType.javaType="Integer">
      		IIMapper<${name}> mapper = session.getMapper(getMapperClass());
      		<#elseif tab.pkFieldType.javaType="java.math.BigInteger">
      		IBigIMapper<${name}> mapper = session.getMapper(getMapperClass());
      		<#elseif tab.pkFieldType.javaType="String">
      		ISMapper<${name}> mapper = session.getMapper(getMapperClass());
      		<#else>
      		ILMapper<${name}> mapper = session.getMapper(getMapperClass());
      		</#if>
      		Long id = mapper.insert(model);
      		if (id !=null) {
        		this.incrTabVersion(CallFrom_PK,tabNameSuffix);
      		}

      		if (logger.isDebugEnabled()) {
        		logger.debug("insert(T model={}, String tabNameSuffix={}) - end - return value={}", model, tabNameSuffix, id); //$NON-NLS-1$
      		}
      		return model.getId();
    	} catch (Exception t) {
      		logger.error("insert(T, String)", t); //$NON-NLS-1$
      		throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    	} finally {
     		SqlmapUtils.release(session,sessionFactory);
    	}
  	}
	</#if>
	
}
