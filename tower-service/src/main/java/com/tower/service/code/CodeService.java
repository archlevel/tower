package com.tower.service.code;

import java.util.*;

import com.tower.service.domain.code.CodeResponse;

/**
 * code 码表数据接口
 * @author Administrator
 *
 */
public interface CodeService {

	/**
	 * 插入数据
	 * @param dto
	 * @return
	 */
	public CodeResponse insert(Map<String, Object> params);
	
	/**
	 * 根据id更新数据
	 * @param dto
	 * @return
	 */
	public CodeResponse updateById(Map<String, Object> params);
	
	/**
	 * 根据id删除数据
	 * @param dto
	 * @return
	 */
	public CodeResponse deleteById(Integer id);
	
	/**
	 * 根据条件查询数据
	 * @param map
	 * @return
	 */
	public CodeResponse selectList(Map<String, Object> params);
}
