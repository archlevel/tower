package com.tower.service.dao.ibatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;

public class SqlSessionKey {
  public SqlSessionKey(DataSource definition, ExecutorType executeType) {
    this.definition = definition;
    this.executeType = executeType;
  }

  private DataSource definition;
  private ExecutorType executeType;

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof SqlSessionKey)) {
      return false;
    }
    SqlSessionKey other = (SqlSessionKey) obj;
    return definition == other.definition && executeType == other.executeType;
  }

  @Override
  public int hashCode() {
    return (definition.hashCode() << 8) | (executeType.hashCode());
  }

  public DataSource getDefinition() {
    return definition;
  }

  public void setDefinition(DataSource _definition) {
    this.definition = _definition;
  }

  public ExecutorType getExecuteType() {
    return executeType;
  }

  public void setExecuteType(ExecutorType _executeType) {
    this.executeType = _executeType;
  }
}
