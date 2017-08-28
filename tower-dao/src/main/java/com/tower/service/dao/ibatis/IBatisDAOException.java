package com.tower.service.dao.ibatis;

import com.tower.service.exception.basic.IExceptionBody;

/**
 * 异常代码定义枚举类
 * 
 * @author maven plugin with DB 2015-01-16 10:06:55
 *
 */
public enum IBatisDAOException implements IExceptionBody {
  /**
   * 删除-条件参数不能为空.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00001
   * </p>
   */
  MSG_1_0001("1", "删除-条件参数不能为空", 1, "0"),
  /**
   * 删除-主键参数必须>0.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00002
   * </p>
   */
  MSG_1_0002("2", "删除-主键参数必须>0", 1, "0"),
  /**
   * 删除-条件参数不能为空.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00003
   * </p>
   */
  MSG_1_0003("3", "删除-条件参数不能为空", 1, "0"),
  /**
   * 查询-条件参数不能为空.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00004
   * </p>
   */
  MSG_1_0004("4", "查询-条件参数不能为空", 1, "0"),
  /**
   * 更新-主键参数不能为空.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00005
   * </p>
   */
  MSG_1_0005("5", "更新-主键参数不能为空", 1, "0"),
  /**
   * 更新-主键参数必须>0.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00006
   * </p>
   */
  MSG_1_0006("6", "更新-主键参数必须>0", 1, "0"),
  /**
   * 更新-条件参数不能为空.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00007
   * </p>
   */
  MSG_1_0007("7", "更新-条件参数不能为空非法sql:{}", 1, "0"),
  /**
   * 系统异常－执行出错.
   * <p>
   * type:2
   * </p>
   * <p>
   * code:2{spid}00001
   * </p>
   */
  MSG_2_0001("1", "系统异常－执行出错", 2, "0"),
  /**
   * 更新-新值参数不能为空.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00008
   * </p>
   */
  MSG_1_0008("8", "更新-新值参数不能为空", 1, "0"),
  /**
   * 属性‘{}’不是外键或者未注册为外键.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00009
   * </p>
   */
  MSG_1_0009("9", "属性‘{}’不是外键或者未注册为外键", 1, "0"),
  /**
   * 外键-条件参数不能为空.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00010
   * </p>
   */
  MSG_1_0010("10", "外键-条件参数不能为空", 1, "0"),
  /**
   * 操作条件不能含有主键&外键.
   * <p>
   * type:1
   * </p>
   * <p>
   * code:1{spid}00011
   * </p>
   */
  MSG_1_0011("11", "操作条件不能含有主键&外键", 1, "0"),
  MSG_1_0012("12", "批量插入列名为空", 1, "0"),
  MSG_1_0013("13", "sql map注册失败", 1, "0"),
  MSG_1_0014("14", "非法sql:{}", 1, "0"),
  ;

  String code;
  String message;
  String spId;
  int type;

  private IBatisDAOException(String code, String message, int type, String spId) {
    this.code = code;
    this.message = message;
    this.spId = spId;
    this.type = type;
  }

  public String getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }

  public String getValue() {
    return this.getCode();
  }

  public String getSpId() {
    return this.spId;
  }

  public int getType() {
    return this.type;
  }
}
