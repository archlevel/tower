package com.tower.service.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.util.DesUtils;

public class SecutiryCompositeConfiguration  extends CompositeConfiguration implements Configuration{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
     * 加密／解密工具
     */
    private DesUtils utils = new DesUtils("national");
    
    private boolean isEncryptPropertyVal(String propertyName){
        if(propertyName.toLowerCase().endsWith(".encrypted")){
            return true;
        }else{
            return false;
        }
    }
    
    public SecutiryCompositeConfiguration(){
    	super();
    }
    
    public SecutiryCompositeConfiguration(Configuration inMemoryConfiguration) {
    	super(inMemoryConfiguration);
    }
    
    public SecutiryCompositeConfiguration(Collection configurations){
    	super(configurations);
    }
    
	@Override
	public Configuration subset(String prefix) {
		return super.subset(prefix);
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}

	@Override
	public boolean containsKey(String key) {
		return super.containsKey(key);
	}

	@Override
	public void addProperty(String key, Object value) {
		super.addProperty(key, value);
	}

	@Override
	public void setProperty(String key, Object value) {
		super.setProperty(key, value);
	}

	@Override
	public void clearProperty(String key) {
		super.clearProperty(key);
	}

	@Override
	public void clear() {
		super.clear();
	}

	@Override
	public Object getProperty(String key) {
		return super.getProperty(key);
	}

	@Override
	public Iterator getKeys(String prefix) {
		return super.getKeys(prefix);
	}

	@Override
	public Iterator getKeys() {
		return super.getKeys();
	}

	@Override
	public Properties getProperties(String key) {
		return super.getProperties(key);
	}

	@Override
	public boolean getBoolean(String key) {
		return super.getBoolean(key);
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return super.getBoolean(key,defaultValue);
	}

	@Override
	public Boolean getBoolean(String key, Boolean defaultValue) {
		return super.getBoolean(key, defaultValue);
	}

	@Override
	public byte getByte(String key) {
		return super.getByte(key);
	}

	@Override
	public byte getByte(String key, byte defaultValue) {
		return super.getByte(key,defaultValue);
	}

	@Override
	public Byte getByte(String key, Byte defaultValue) {
		return super.getByte(key, defaultValue);
	}

	@Override
	public double getDouble(String key) {
		return super.getDouble(key);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		return super.getDouble(key,defaultValue);
	}

	@Override
	public Double getDouble(String key, Double defaultValue) {
		return super.getDouble(key, defaultValue);
	}

	@Override
	public float getFloat(String key) {
		return super.getFloat(key);
	}

	@Override
	public float getFloat(String key, float defaultValue) {
		return super.getFloat(key,defaultValue);
	}

	@Override
	public Float getFloat(String key, Float defaultValue) {
		return super.getFloat(key, defaultValue);
	}

	@Override
	public int getInt(String key) {
		return super.getInt(key);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return super.getInt(key, defaultValue);
	}

	@Override
	public Integer getInteger(String key, Integer defaultValue) {
		return super.getInteger(key, defaultValue);
	}

	@Override
	public long getLong(String key) {
		return super.getLong(key);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return super.getLong(key, defaultValue);
	}

	@Override
	public Long getLong(String key, Long defaultValue) {
		return super.getLong(key, defaultValue);
	}

	@Override
	public short getShort(String key) {
		return super.getShort(key);
	}

	@Override
	public short getShort(String key, short defaultValue) {
		return super.getShort(key, defaultValue);
	}

	@Override
	public Short getShort(String key, Short defaultValue) {
		return super.getShort(key, defaultValue);
	}

	@Override
	public BigDecimal getBigDecimal(String key) {
		return super.getBigDecimal(key);
	}

	@Override
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		return super.getBigDecimal(key,defaultValue);
	}

	@Override
	public BigInteger getBigInteger(String key) {
		return super.getBigInteger(key);
	}

	@Override
	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		return super.getBigInteger(key, defaultValue);
	}

	/**
     * 支持加密机制
     */
    public String getString(String key) {
    	String value = super.getString(key);
    	if(value!=null && isEncryptPropertyVal(key)){
    		try {
				value = utils.decrypt(value.toString());
				logger.error("{} 解密成功",key);
			} catch (Exception e) {
				logger.error("{}={}解密失败",key,value);
			}
    	}
        return value;
    }
    /**
     * 支持加密机制
     */
    public String getString(String key, String defaultValue) {
    	String value = super.getString(key,defaultValue);
    	if(value!=null && isEncryptPropertyVal(key)){
    		try {
				value = utils.decrypt(value.toString());
				logger.error("{} 解密成功",key);
			} catch (Exception e) {
				logger.error("{}={}解密失败",key,value);
			}
    	}
        return value;
    }

	@Override
	public String[] getStringArray(String key) {
		return super.getStringArray(key);
	}

	@Override
	public List getList(String key) {
		return super.getList(key);
	}

	@Override
	public List getList(String key, List defaultValue) {
		return super.getList(key, defaultValue);
	}

}
