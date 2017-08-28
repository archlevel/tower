package com.tower.service.datasource;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.ResourceTransactionManager;

import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.dao.ibatis.AccConfig;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.util.Request;

public class TowerDataSourceTransactionManager extends DataSourceTransactionManager
		implements ResourceTransactionManager, InitializingBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6224200855383351166L;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = ConfigComponent.AccConfig)
	protected AccConfig accConfig;
	
	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition)
			throws TransactionException {
		Request.start();
		super.doBegin(transaction, definition);
	}

	@Override
	protected void doCommit(DefaultTransactionStatus status)
			throws TransactionException {
		super.doCommit(status);
		long end = System.currentTimeMillis();
		long start = Request.getStartTime();
		Request.deleteStartTime();
		if(end-start>accConfig.getInt("slowTTime",1000)){
			logger.info("slow transaction timeused: "+(end-start));
		}
	}

	@Override
	protected void doRollback(DefaultTransactionStatus status)
			throws TransactionException {
		try{
			super.doRollback(status);
		}
		catch(TransactionSystemException ex){
			throw ex;
		}
		long end = System.currentTimeMillis();
		long start = Request.getStartTime();
		Request.deleteStartTime();
		if(end-start>accConfig.getInt("slowTTime",1000)){
			logger.info("slow transaction timeused: "+(end-start));
		}
	}

}
