package com.tower.service.dao.generate.tool;

import javax.sql.DataSource;

/**
 * 从Sql server 2008获取表字段属性信息，注释信息 select b.[value] from sys.columns a left join
 * sys.extended_properties b on a.object_id=b.major_id and a.column_id=b.minor_id inner join
 * sysobjects c on a.column_id=c.id and a.[name]='列名' and c.[name]='表名' SELECT 表名=case when
 * a.colorder=1 then d.name else '' end, 表说明=case when a.colorder=1 then isnull(f.value,'') else ''
 * end, 字段序号=a.colorder, 字段名=a.name, 标识=case when COLUMNPROPERTY( a.id,a.name,'IsIdentity')=1 then
 * '√'else '' end, 主键=case when exists(SELECT 1 FROM sysobjects where xtype='PK' and name in (
 * SELECT name FROM sysindexes WHERE indid in( SELECT indid FROM sysindexkeys WHERE id = a.id AND
 * colid=a.colid ))) then '√' else '' end, 类型=b.name, 占用字节数=a.length,
 * 长度=COLUMNPROPERTY(a.id,a.name,'PRECISION'), 小数位数=isnull(COLUMNPROPERTY(a.id,a.name,'Scale'),0),
 * 允许空=case when a.isnullable=1 then '√'else '' end, 默认值=isnull(e.text,''),
 * 字段说明=isnull(g.[value],'') FROM syscolumns a left join systypes b on a.xusertype=b.xusertype inner
 * join sysobjects d on a.id=d.id and d.xtype='U' and d.name<>'dtproperties' left join syscomments e
 * on a.cdefault=e.id left join sys.extended_properties g on a.id=g.major_id and a.colid=g.minor_id
 * left join sys.extended_properties f on d.id=f.major_id and f.minor_id=0 --where d.name='orders'
 * --如果只查询指定表,加上此条件 order by a.id,a.colorder
 * 
 * @author alexzhu
 *
 */
public class TabReader_SqlSvr extends TabReader {
 
  public TabReader_SqlSvr(DataSource dataSource, String dbName, String table) throws Exception {
    super(dataSource,dbName,table);
  }

  protected String isKey(String key){
    return key == null ? "no" : "1".equals(key) ? "yes" : "no";
  }
  
  protected String isNull(String nullable){
    return nullable == null ? "yes" : "0".equals(nullable) ? "no" : "yes";
  }
  
  @Override
  protected String getSql() {//cast(isnull(g.[value],'') as varchar(100))
    return "select ordinal_position=col.column_id,col.name as column_name,column_comment=cast(ep.value as varchar(100)),"
        + "t.name as data_type,column_default=cast(null as varchar(100)),(select top 1 ind.is_primary_key from sys.index_columns ic "
        + "left join sys.indexes ind on ic.object_id=ind.object_id and ic.index_id=ind.index_id and ind.name like "
        + "'PK_%' where ic.object_id=obj.object_id and ic.column_id=col.column_id) as column_key,col.is_nullable as "
        + "is_nullable from sys.objects obj inner join sys.columns col on obj.object_id=col.object_id left join sys.types t "
        + "on t.user_type_id=col.user_type_id left join sys.extended_properties ep on ep.major_id=obj.object_id and "
        + "ep.minor_id=col.column_id and ep.name='MS_Description' where obj.name='"+_table.trim()+"'";
  }
}
