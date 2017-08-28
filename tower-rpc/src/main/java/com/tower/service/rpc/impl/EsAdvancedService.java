package com.tower.service.rpc.impl;

import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.DeleteMapping;
import io.searchbox.indices.mapping.PutMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.InitializingBean;

import com.tower.service.config.IConfigChangeListener;
import com.tower.service.domain.IDTO;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.rpc.IClient;

public class EsAdvancedService implements JestClient, IClient,
		IConfigChangeListener, InitializingBean {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	private HttpClientConfig clientConfig;
	private JestClient client;
	private boolean discoveryEnabled = true;
	private long time = 1l;
	private TimeUnit unit = TimeUnit.SECONDS;
	private String filter;
	private boolean inited;
	private static String defaultServer = "http://192.168.0.158:9200";
	private RpcConfig config;

	public void setConfig(RpcConfig config) {
		this.config = config;
	}

	private void init() {
		List<String> servers = new ArrayList<String>();
		if (config != null) {
			config.addChangeListener(this);
			servers = config.getList("es.servers", servers);
		}
		else{
			servers.add(defaultServer);
		}
		clientConfig = new HttpClientConfig.Builder(servers)
				.discoveryEnabled(discoveryEnabled)
				.discoveryFrequency(time, unit).multiThreaded(true).connTimeout(1000).readTimeout(20000).build();
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(clientConfig);
		client = factory.getObject();
		inited = true;
	}

	@Override
	public void shutdownClient() {
		client.shutdownClient();
	}

	@Override
	public void setServers(Set<String> servers) {
		client.setServers(servers);
	}

	@Override
	public void configChanged() {
		setServers(new LinkedHashSet<String>(config.getList("es.servers")));
	}

	@Override
	public <T extends JestResult> T execute(Action<T> clientRequest)
			throws IOException {
		T result = client.execute(clientRequest);
		int responseCode = result.getResponseCode();
		logger.info("ResponseCode:"+responseCode);
		if(responseCode>=200 && responseCode<=206){
			return result;
		}
		throw new RuntimeException(result.getErrorMessage());
	}

	@Override
	public <T extends JestResult> void executeAsync(Action<T> clientRequest,
			JestResultHandler<? super T> jestResultHandler) {
		client.executeAsync(clientRequest, jestResultHandler);
	}

	@Override
	public void createIdxMap(String idxName, String idxType, String mapDefine) {
		PutMapping putMapping = new PutMapping.Builder(idxName, idxType,
				mapDefine).setParameter("update_all_types", "true").build();
		try {
			this.execute(putMapping);
		} catch (IOException e) {
			logger.error("createIdxMap error with:idxName=" + idxName
					+ " ,idxType=" + idxType + " ,mapDefine=" + mapDefine, e);
		}
	}
	
	public void deleteIdxMap(String idxName,String idxType) {
		DeleteMapping delMapping = new DeleteMapping.Builder(idxName, idxType).build();
		try{
			this.execute(delMapping);
		}
		catch (IOException e) {
			logger.error("createIdxMap error with:idxName=" + idxName
					+ " ,idxType=" + idxType , e);
		}
	}

	@Override
	public void createIndex(String idxName) {
		try {
			execute(new CreateIndex.Builder(idxName).build());
		} catch (IOException e) {
			logger.error("createIndex error with:idxName=" + idxName, e);
		}
	}

	@Override
	public void createIndex(String idxName, String settingsJson) {
		try {
			execute(new CreateIndex.Builder(idxName).settings(settingsJson)
					.build());
		} catch (IOException e) {
			logger.error("createIndex error with:idxName=" + idxName
					+ " ,settingsJson=" + settingsJson, e);
		}
	}

	@Override
	public void createIndex(IDTO source, String idxName, String idxTypeName,
			String id) {
		Index index = new Index.Builder(source).index(idxName)
				.type(idxTypeName).id(id).build();
		try {
			JestResult result = execute(index);
			
		} catch (IOException e) {
			logger.error("createIndex error with:source=" + source + " ,id="
					+ id + " ,idxName=" + idxName + " ,idxTypeName="
					+ idxTypeName, e);
		}
	}

	@Override
	public void createIndex(IDTO source, String idxName, String idxTypeName) {
		Index index = new Index.Builder(source).index(idxName)
				.type(idxTypeName).build();
		try {
			execute(index);
		} catch (IOException e) {
			logger.error("createIndex error with:source=" + source
					+ " ,idxName=" + idxName + " ,idxTypeName=" + idxTypeName,
					e);
		}
	}
	
	@Override
	public JestResult search(String idxName, String idxType, String id) {
		JestResult result = null;
		Get get = new Get.Builder(idxName, id).type(idxType).build();
		try {
			result = execute(get);
		} catch (Exception e) {
			logger.error("search error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,id=" + id, e);
		}

		return result;
	}

	/**
	 * 
	 * @param idxName 索引名
	 * @param idxType 索引类型
	 * @param queryString 查询条件字符串
	 * @return
	 */
	public SearchResult searchByQueryString(String idxName, String idxType,
			String queryString) {
		SearchResult result = null;
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryString);
		Search search = new Search.Builder(searchSourceBuilder.toString())
				.addIndex(idxName).addType(idxType).build();
		try {
			result = execute(search);
		} catch (Exception e) {
			logger.error("search error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,queryString=" + queryString, e);
		}

		return result;
	}

	/**
	 * 
	 * @param idxName 索引名
	 * @param idxType 索引类型
	 * @param params 查询条件
	 * @return
	 */
	public SearchResult searchByMap(String idxName, String idxType,
			Map<String, Object> params) {
		SearchResult result = null;
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(params);
		Search search = new Search.Builder(searchSourceBuilder.toString())
				.addIndex(idxName).addType(idxType).build();
		try {
			result = execute(search);
		} catch (Exception e) {
			logger.error("search error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,params=" + params, e);
		}

		return result;
	}
	
	/**
	 * 
	 * @param idxName 索引名
	 * @param idxType 索引类型
	 * @param query queryBuilder
	 * @return
	 */
	public SearchResult searchByQuery(String idxName, String idxType,
			QueryBuilder query) {
		SearchResult result = null;
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(query);
		Search search = new Search.Builder(searchSourceBuilder.toString())
				.addIndex(idxName).addType(idxType).build();
		try {
			result = execute(search);
		} catch (Exception e) {
			logger.error("search error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,query=" + query, e);
		}

		return result;
	}
	/**
	 * 
	 * @param idxName 索引名
	 * @param idxType 索引类型
	 * @param searchSourceBuilder 查询source builder
	 * @return
	 */
	public SearchResult searchByQuery(String idxName, String idxType,
			SearchSourceBuilder searchSourceBuilder) {
		SearchResult result = null;
		Search search = new Search.Builder(searchSourceBuilder.toString())
				.addIndex(idxName).addType(idxType).build();
		try {
			result = execute(search);
		} catch (Exception e) {
			logger.error("search error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,searchSourceBuilder=" + searchSourceBuilder.toString(), e);
		}

		return result;
	}

	/**
	 * 使用DSL语句查询
	 * 
	 * @return
	 */
	public SearchResult searchByDSL(String idxName, String idxType,
			String dslJson) {
		SearchResult result = null;
		Search search = new Search.Builder(dslJson).addIndex(idxName)
				.addType(idxType).build();
		try {
			result = execute(search);
		} catch (Exception e) {
			logger.error("search error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,dslJson=" + dslJson, e);
		}

		return result;
	}

	@Override
	public void update(String idxName, String idxType, String id, String script) {
		try {
			execute(new Update.Builder(script).index(idxName).type(idxType)
					.id(id).build());
		} catch (IOException e) {
			logger.error("update error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,id=" + id + ",script=" + script, e);
		}
	}

	@Override
	public void delete(String idxName, String idxType, String id) {
		try {
			execute(new Delete.Builder(id).index(idxName).type(idxType)
					.build());
		} catch (IOException e) {
			logger.error("delete error with:idxName=" + idxName + " ,idxType="
					+ idxType + " ,id=" + id, e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

	public static void main(String args[]) {
		EsAdvancedService client = new EsAdvancedService();
		client.init();
		//JestResult result = client.search("megacorp", "employee", "1");
		client.deleteIdxMap("index", "test");
		/*
		String mapDef = "{\"properties\":{\"content\":{\"type\":\"string\",\"index\": \"analyzed\",\"analyzer\":\"ik\"}}}";
		System.out.println(mapDef);
		client.createIdxMap("index", "test", mapDef);
		// Employee result = client.search("megacorp", "employee",
		// "1",Employee.class);
		//System.out.println(result.getJsonString());
		for (int i = 0; i < 1; i++) {
		}
		*/
	}
}
