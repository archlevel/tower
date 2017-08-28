package com.tower.service.dao;

import java.util.List;
import java.util.Map;

import com.tower.service.cache.ICacheable;

/**
 * 数据访问层基础接口
 * 
 * @todo 建议添加一个nocache访问接口［for关键性业务］
 * @author alexzhu
 *
 * @param <T>
 */
public interface IBatchDAO<T> extends ICacheable<T> {

	/**
	 * 批量查询
	 * 
	 * @param datas
	 *            查询条件
	 * @param tabNameSuffix
	 * @return
	 */
	public List<T> batchQuery(List<Map<String, Object>> datas,
			String tabNameSuffix);

	/**
	 * 批量更新
	 * 
	 * @param new_
	 *            更新值
	 * @param datas
	 *            更新条件
	 * @param tabNameSuffix
	 * @return
	 */
	public Integer batchUpdate(Map<String, Object> new_,
			List<Map<String, Object>> datas, String tabNameSuffix);

	/**
	 * 批量删除
	 * 
	 * @param datas
	 *            删除条件
	 * @param tabNameSuffix
	 * @return
	 */
	public Integer batchDelete(List<Map<String, Object>> datas,
			String tabNameSuffix);

	public String get$TowerTabName(String tabNameSuffix);

}
