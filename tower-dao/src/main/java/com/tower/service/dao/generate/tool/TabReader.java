package com.tower.service.dao.generate.tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class TabReader {
  private JdbcTemplate jdbcTemplate;
  protected String _table;
  protected String _dbName;
  private DataSource dataSource;

  public TabReader(DataSource dataSource, String dbName, String table) throws Exception {
    this.dataSource = dataSource;
    this._dbName = dbName;
    jdbcTemplate = new JdbcTemplate(dataSource);
    this._table = table;
  }

  class Col {
    private int index;
    private String name;
    private String comment;
    private String type;
    private String def;
    private String isKey;
    private String isNullable;

    Col(ResultSet rs) throws SQLException {
      index = rs.getInt("ordinal_position");
      name = rs.getString("column_name");
      comment = rs.getString("column_comment") == null ? "" : rs.getString("column_comment");
      type = rs.getString("data_type");
      def = rs.getString("column_default") == null ? "" : rs.getString("column_default");
      String key = rs.getString("column_key");
      isKey = isKey(key);
      String nullable = rs.getString("is_nullable");
      isNullable = isNull(nullable);
      System.out.println(index + " " + name+" type: "+ type + " key: " + key+ " isKey:"+ isKey);
    }
    
    public List toList() {
      List<Object> def_ = new ArrayList<Object>();
      def_.add(index);
      def_.add(name);
      def_.add(comment);
      def_.add(type);
      def_.add(null);
      def_.add(def);
      def_.add(isKey);
      def_.add(isNullable);
      return def_;
    }

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getComment() {
      return comment;
    }

    public void setComment(String comment) {
      this.comment = comment;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getDef() {
      return def;
    }

    public void setDef(String def) {
      this.def = def;
    }

    public String getIsKey() {
      return isKey;
    }

    public void setIsKey(String isKey) {
      this.isKey = isKey;
    }

    public String getIsNullable() {
      return isNullable;
    }

    public void setIsNullable(String isNullable) {
      this.isNullable = isNullable;
    }

  }
  
  protected String isKey(String key){
    return key == null ? "no" : "PRI".equals(key) ? "yes" : "no";
  }
  
  protected String isNull(String nullable){
    return nullable == null ? "yes" : "no".equals(nullable) ? "no" : "yes";
  }

  class ColMapper implements RowMapper<Col> {
    @Override
    public Col mapRow(ResultSet rs, int arg1) throws SQLException {
      return new Col(rs);
    }

  }

  public Tab createDef(List<List<Object>> head) {
    String sql = getSql();
    List<Col> defs = jdbcTemplate.query(sql, new Object[] {}, new ColMapper());
    int size = defs == null ? 0 : defs.size();
    int pkFieldNum = 0;
    Type pkFieldType = null;
    for (int i = 0; i < size; i++) {
      Col col = defs.get(i);
      if ("yes".equals(col.getIsKey())) {
        pkFieldNum++;
        pkFieldType = Type.get(col.type);
      }

      List<Object> col_ = col.toList();
      head.add(col_);
    }
    Tab tab = new Tab(_dbName, _table, head);
    tab.setPkFieldNum(pkFieldNum);
    tab.setPkFieldType(pkFieldType);
    return tab;
  }
  protected String getSql(){
    return "SELECT DISTINCT ordinal_position, column_name,column_comment,data_type,column_default,column_key,is_nullable FROM information_schema.columns WHERE table_name='"
        + _table.trim() + "' AND table_schema='" + _dbName + "'";
  }
}
