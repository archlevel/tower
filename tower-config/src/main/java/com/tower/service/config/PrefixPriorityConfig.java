package com.tower.service.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;

import com.tower.service.util.StringUtil;

/**
 * subset配置类，默认读取前缀开头的相关配置，当没有相关配置设置时，读取当前key相关设置
 * 
 * @author alexzhu
 *
 */
public class PrefixPriorityConfig extends DynamicConfig {
	/**
	 * Logger for this class
	 */
	/**
	 * subset前缀
	 */
	private String prefix;

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {

		String prefix_ = prefix;
		if (!StringUtil.isEmpty(prefix) && !prefix.endsWith(".")) {
			prefix_ = prefix + ".";
		} else {
			prefix_ = "";
		}

		return prefix_;
	}

	public Object getProperty(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getProperty(this.getPrefix() + key);
		} else {
			return super.getProperty(key);
		}
	}

	public Properties getProperties(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getProperties(this.getPrefix() + key);
		} else {
			return super.getProperties(key);
		}
	}

	public boolean getBoolean(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getBoolean(this.getPrefix() + key);
		} else {
			return super.getBoolean(key);
		}
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getBoolean(this.getPrefix() + key);
		} else {
			return super.getBoolean(key, defaultValue);
		}
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getBoolean(this.getPrefix() + key);
		} else {
			return super.getBoolean(key, defaultValue);
		}
	}

	public byte getByte(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getByte(this.getPrefix() + key);
		} else {
			return super.getByte(key);
		}
	}

	public byte getByte(String key, byte defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getByte(this.getPrefix() + key);
		} else {
			return super.getByte(key, defaultValue);
		}
	}

	public Byte getByte(String key, Byte defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getByte(this.getPrefix() + key);
		} else {
			return super.getByte(key, defaultValue);
		}
	}

	public double getDouble(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getDouble(this.getPrefix() + key);
		} else {
			return super.getDouble(key);
		}
	}

	public double getDouble(String key, double defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getDouble(this.getPrefix() + key);
		} else {
			return super.getDouble(key, defaultValue);
		}
	}

	public Double getDouble(String key, Double defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getDouble(this.getPrefix() + key);
		} else {
			return super.getDouble(key, defaultValue);
		}
	}

	public float getFloat(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getFloat(this.getPrefix() + key);
		} else {
			return super.getFloat(key);
		}
	}

	public float getFloat(String key, float defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getFloat(this.getPrefix() + key);
		} else {
			return super.getFloat(key, defaultValue);
		}
	}

	public Float getFloat(String key, Float defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getFloat(this.getPrefix() + key);
		} else {
			return super.getFloat(key, defaultValue);
		}
	}

	public int getInt(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getInt(this.getPrefix() + key);
		} else {
			return super.getInt(key);
		}
	}

	public int getInt(String key, int defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getInt(this.getPrefix() + key);
		} else {
			return super.getInt(key, defaultValue);
		}
	}

	public Integer getInteger(String key, Integer defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getInt(this.getPrefix() + key);
		} else {
			return super.getInt(key, defaultValue);
		}
	}

	public long getLong(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getLong(this.getPrefix() + key);
		} else {
			return super.getLong(key);
		}
	}

	public long getLong(String key, long defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getLong(this.getPrefix() + key);
		} else {
			return super.getLong(key, defaultValue);
		}
	}

	public Long getLong(String key, Long defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getLong(this.getPrefix() + key);
		} else {
			return super.getLong(key, defaultValue);
		}
	}

	public short getShort(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getShort(this.getPrefix() + key);
		} else {
			return super.getShort(key);
		}
	}

	public short getShort(String key, short defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getShort(this.getPrefix() + key);
		} else {
			return super.getShort(key, defaultValue);
		}
	}

	public Short getShort(String key, Short defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getShort(this.getPrefix() + key);
		} else {
			return super.getShort(key, defaultValue);
		}
	}

	public BigDecimal getBigDecimal(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getBigDecimal(this.getPrefix() + key);
		} else {
			return super.getBigDecimal(key);
		}
	}

	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getBigDecimal(this.getPrefix() + key);
		} else {
			return super.getBigDecimal(key, defaultValue);
		}
	}

	public BigInteger getBigInteger(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getBigInteger(this.getPrefix() + key);
		} else {
			return super.getBigInteger(key);
		}
	}

	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getBigInteger(this.getPrefix() + key);
		} else {
			return super.getBigInteger(key, defaultValue);
		}
	}

	public String getString(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getString(this.getPrefix() + key);
		} else {
			return super.getString(key);
		}
	}

	public String getString(String key, String defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getString(this.getPrefix() + key);
		} else {
			return super.getString(key, defaultValue);
		}
	}

	public String getString(String root, String key, String defaultValue) {
		if (!StringUtil.isEmpty(root)
				&& super.containsKey(root + "." + this.getPrefix() + key)) {
			return super.getString(root + "." + this.getPrefix() + key);
		} else if (super.containsKey(this.getPrefix() + key)) {
			return super.getString(this.getPrefix() + key);
		} else if (!StringUtil.isEmpty(root)
				&& super.containsKey(root + "." + key)) {
			return super.getString(root + "." + key);
		} else {
			return super.getString(key, defaultValue);
		}
	}

	public String[] getStringArray(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getStringArray(this.getPrefix() + key);
		} else {
			return super.getStringArray(key);
		}
	}

	public List getList(String key) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getList(this.getPrefix() + key);
		} else {
			return super.getList(key);
		}
	}

	public List getList(String key, List defaultValue) {
		if (super.containsKey(this.getPrefix() + key)) {
			return super.getList(this.getPrefix() + key);
		} else {
			return super.getList(key, defaultValue);
		}
	}

	protected String configToString(Configuration config) {
		StringBuilder sb = new StringBuilder();
		if(!StringUtil.isEmpty(this.prefix)){
			String globalSetting = global(config);
			sb.append(globalSetting);
		}
		String instanceSetting = instance(config);
		sb.append(instanceSetting);
		return sb.toString();
	}

	protected String global(Configuration config) {
		if (config == null) {
			return "";
		}
		Iterator<String> it = config.getKeys();
		if (it == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			String key = it.next();
			if (key.toLowerCase().startsWith("global")
					|| key.indexOf(".") == -1) {
				sb.append(key + "=" + config.getString(key) + "\n");
			}
		}
		return sb.toString();
	}

	protected String instance(Configuration config) {
		if (config == null) {
			return "";
		}
		String prefix = this.getPrefix();
		Iterator<String> it = config.getKeys();
		if (it == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			String key = it.next();
			if (key.startsWith(prefix)) {
				sb.append(key + "=" + config.getString(key) + "\n");
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args){
		System.out.println("fdafdadfae".indexOf("."));
	}
}
