package com.tower.service.rpc;

import io.searchbox.client.JestResult;

import com.tower.service.domain.IDTO;

public interface IClient {

	/**
	 * 
	 * @param idxName
	 *            索引库名
	 */
	public void createIndex(String idxName);

	/**
	 * 
	 * @param idxName
	 *            索引库名
	 * @param settingsJson
	 */
	public void createIndex(String idxName, String settingsJson);

	/**
	 * 
	 * @param idxName
	 *            索引库名
	 * @param idxType
	 *            索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
	 * @param mapDefine
	 *            对索引库中索引的字段名及其数据类型进行定义,一般不需要要指定mapping都可以，因为es会自动根据数据格式定义它的类型，
	 *            如果你需要对某些字段添加特殊属性如：定义使用其它分词器、是否分词、是否存储等），就必须手动添加mapping。
	 *            有两种添加mapping的方法，一种是定义在配置文件中，一种是运行时手动提交mapping，两种选一种就行了。配置文件中
	 *            定义mapping，你可以把[mapping名].json文件放到config/mappings/[索引名]目录下，这个目录要自己创建，
	 *            一个mapping和一个索引对应，你也可以定义一个默认的mapping，把自己定义的default-mapping.json放到config目录下就行
	 */
	public void createIdxMap(String idxName, String idxType, String mapDefine);

	/**
	 * 
	 * @param source
	 * @param idxName
	 *            索引库名
	 * @param idxType
	 *            索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。 
	 */
	public void createIndex(IDTO source, String idxName, String idxTypeName);

	/**
	 * 
	 * @param source
	 * @param idxName
	 *            索引库名
	 * @param idxType
	 *            索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
	 * @param id
	 */
	public void createIndex(IDTO source, String idxName, String idxType,
			String id);

	/**
	 * 
	 * @param idxName
	 *            索引库名
	 * @param idxType
	 *            索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
	 * @param id
	 * @return
	 */
	public JestResult search(String idxName, String idxType, String id);

	/**
	 * 
	 * @param idxName
	 *            索引库名
	 * @param idxType
	 *            索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
	 * @param id
	 * @param newData
	 */
	public void update(String idxName, String idxType, String id, String newData);

	/**
	 * 
	 * @param idxName
	 *            索引库名
	 * @param idxType
	 *            索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
	 * @param id
	 */
	public void delete(String idxName, String idxType, String id);

}
