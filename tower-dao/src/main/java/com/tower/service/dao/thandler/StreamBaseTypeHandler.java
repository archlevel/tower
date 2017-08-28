package com.tower.service.dao.thandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class StreamBaseTypeHandler extends BaseTypeHandler<InputStream> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, InputStream parameter,
      JdbcType jdbcType) throws SQLException {
    ps.setBinaryStream(i, parameter);
  }

  @Override
  public InputStream getNullableResult(ResultSet rs, String columnName) throws SQLException {
    InputStream is = rs.getBinaryStream(columnName);
    return read(is);//
  }

  @Override
  public InputStream getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    InputStream is = rs.getBinaryStream(columnIndex);
    return read(is);
  }

  @Override
  public InputStream getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    InputStream is = cs.getBlob(columnIndex).getBinaryStream();
    return read(is);
  }

  private InputStream read(InputStream is) throws SQLException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int readed = 0;
    byte[] buffer = new byte[1024];
    try {
      while ((readed = is.read(buffer)) > 0) {
        baos.write(buffer, 0, readed);
      }
      is.close();
    } catch (IOException ex) {
      throw new SQLException(ex);
    }
    
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    return bais;
  }

}
