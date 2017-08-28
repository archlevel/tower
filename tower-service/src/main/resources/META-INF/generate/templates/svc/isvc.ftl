package ${package};

import java.util.Map;

import ${package}.dto.${name}Dto;
import com.tower.service.IService;
import com.tower.service.domain.IntegerResult;
<#if pkType="Integer">
<#elseif pkType="java.math.BigInteger">
import java.math.BigInteger;
import com.tower.service.domain.BigIntegerResult;
<#elseif pkType="String">
import com.tower.service.domain.StringResult;
<#else>
import com.tower.service.domain.LongResult;
</#if>
import com.tower.service.domain.ListResult;
import com.tower.service.domain.PageResult;
import com.tower.service.domain.ServiceResponse;

public interface I${name}Service<T extends ${name}Dto> extends IService {
	
	/**
	 * 新增SoaSp对象
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param params
	 *            插入参数
	 * @return
	 */
	<#if pkType="Integer">	
	public ServiceResponse<IntegerResult> insert(Map<String, Object> params);
	<#elseif pkType="java.math.BigInteger">
	public ServiceResponse<BigIntegerResult> insert(Map<String, Object> params);
	<#elseif pkType="String">
	public ServiceResponse<StringResult> insert(Map<String, Object> params);
	<#else>
	public ServiceResponse<LongResult> insert(Map<String, Object> params);
	</#if>
	/**
	 * 通过id删除数据
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * @param id
	 *            主键值
	 * @return
	 */
	<#if pkType="Integer">	
	public ServiceResponse<IntegerResult> deleteById(Integer id);
	<#elseif pkType="java.math.BigInteger">
	public ServiceResponse<IntegerResult> deleteById(BigInteger id);
	<#elseif pkType="String">
	public ServiceResponse<IntegerResult> deleteById(String id);
	<#else>
	public ServiceResponse<IntegerResult> deleteById(Long id);
	</#if>
	/**
	 * 通过条件删除数据
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	public ServiceResponse<IntegerResult> deleteByMap(Map<String, Object> params);
	/**
	 * 通过id，更新业务对象
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param id
	 *            主键值
	 * @param newValue
	 * @return
	 */
	<#if pkType="Integer">	
	public ServiceResponse<IntegerResult> updateById(Integer id, Map<String, Object> newValue);
	<#elseif pkType="java.math.BigInteger">
	public ServiceResponse<IntegerResult> updateById(BigInteger id, Map<String, Object> newValue);
	<#elseif pkType="String">
	public ServiceResponse<IntegerResult> updateById(String id, Map<String, Object> newValue);
	<#else>
	public ServiceResponse<IntegerResult> updateById(Long id, Map<String, Object> newValue);
	</#if>
	/**
	 * 通过map条件更新数据
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param newValue
	 *            新值
	 * @param conds
	 *            更新条件
	 * @return
	 */
	public ServiceResponse<IntegerResult> updateByMap(
			Map<String, Object> newValue, Map<String, Object> conds);
	/**
	 * 通过id查询对象，默认从slave中查询
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param id
	 *            主键值
	 * @return
	 */
	<#if pkType="Integer">	
	public ServiceResponse<T> queryById(Integer id);
	<#elseif pkType="java.math.BigInteger">
	public ServiceResponse<T> queryById(BigInteger id);
	<#elseif pkType="String">
	public ServiceResponse<T> queryById(String id);
	<#else>
	public ServiceResponse<T> queryById(Long id);
	</#if>
	/**
	 * 通过id查询对象
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param id
	 *            主键值
	 * 
	 * @param master
	 *            是否从master中查询,master＝true时从master库中查询,同时重新刷新缓存
	 * @return
	 */
	<#if pkType="Integer">	
	public ServiceResponse<T> queryById(Integer id, Boolean master);
	<#elseif pkType="java.math.BigInteger">
	public ServiceResponse<T> queryById(BigInteger id, Boolean master);
	<#elseif pkType="String">
	public ServiceResponse<T> queryById(String id, Boolean master);
	<#else>
	public ServiceResponse<T> queryById(Long id, Boolean master);
	</#if>
	
	/**
	 * 通过map查询数据
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param params
	 *            查询条件
	 * @return
	 */
	public ServiceResponse<ListResult<T>> queryByMap(Map<String, Object> params);

	/**
	 * 通过map查询数据
	 
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param params
	 *            查询条件
	 * @param master
	 *            是否从主库中查询
	 * @return
	 */
	public ServiceResponse<ListResult<T>> queryByMap(Map<String, Object> params, Boolean master);
	
	/**
	 * 统计记录数
	 *
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param params
	 *            查询条件
	 * @return
	 */
	public ServiceResponse<IntegerResult> count(Map<String, Object> params);
	
	/**
	 * 统计记录数
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param params
	 *            查询条件
	 * @param master
	 *            是否从主库中查询
	 * @return
	 */
	public ServiceResponse<IntegerResult> count(Map<String, Object> params, Boolean master);
	/**
	 * 分页查询数据
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param params
	 *            查询条件
	 * @param pageIndex
	 *            分页位置
	 * @param pageSize
	 *            分页大小
	 * @return
	 */
	public ServiceResponse<PageResult<T>> pageByMap(Map<String, Object> params,int pageIndex,int pageSize);
	/**
	 * 分页查询
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param params
	 *            查询条件
	 * @param pageIndex
	 *            分页位置
	 * @param pageSize
	 *            分页大小
	 * @param orders
	 *            排序规则
	 * @return
	 */
	public ServiceResponse<PageResult<T>> pageByMap(Map<String, Object> params,
			int pageIndex, int pageSize, String orders);
	/**
	 * 分页查询数据
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param params
	 *            查询条件
	 * @param pageIndex
	 *            分页位置
	 * @param pageSize
	 *            分页大小
	 * @param master
	 *            是否从主库中查询
	 * @return
	 */
	public ServiceResponse<PageResult<T>> pageByMap(Map<String, Object> params,int pageIndex,int pageSize, Boolean master);
	/**
	 * 分页查询
	 * 
	 * 按照业务逻辑在服务实现体内<br>
	 * 通过DataSourceRouter.switch2("xxDataSourceId")接口支持分库机制<br>
	 * 通过设置tabNameSuffix来实现分表机制实现<br>
	 * 
	 * @param params
	 *            查询条件
	 * @param pageIndex
	 *            分页位置
	 * @param pageSize
	 *            分页大小
	 * @param orders
	 *            排序规则
	 * @param master
	 *            是否从主库中查询
	 * @return
	 */
	public ServiceResponse<PageResult<T>> pageByMap(Map<String, Object> params,
			int pageIndex, int pageSize, String orders, Boolean master);
}
