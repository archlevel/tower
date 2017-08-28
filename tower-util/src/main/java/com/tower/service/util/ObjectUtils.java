package com.tower.service.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class ObjectUtils {
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	public static boolean canParseToInt(String objInt) {
		if (objInt == null) {
			return false;
		}
		try {
			Integer.parseInt(objInt);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	public static boolean canParseToShort(String objInt) {
		if (objInt == null) {
			return false;
		}
		try {
			Short.parseShort(objInt);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	public static boolean canParseToLong(String objInt) {
		if (objInt == null) {
			return false;
		}
		try {
			Long.parseLong(objInt);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	public static boolean canParseToFloat(String objInt) {
		if (objInt == null) {
			return false;
		}
		try {
			Float.parseFloat(objInt);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	public static boolean canParseToDouble(String objInt) {
		if (objInt == null) {
			return false;
		}
		try {
			Double.parseDouble(objInt);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	public static boolean canParseToNum(String objInt) {
		if (objInt == null) {
			return false;
		}
		try {
			Double.parseDouble(objInt);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	/**
	 * 禁用，太抽象了 by zxj
	 * 
	 * @param obj
	 * @return
	 */
	// public static boolean isEmpty(Object obj) {
	// if (obj == null)
	// return true;
	//
	// if (obj instanceof String)
	// return ((String) obj).isEmpty()
	// || ((String) obj).trim().length() == 0;
	// if(obj instanceof Object[])
	// return ((Object[])obj).length==0;
	//
	// if (obj instanceof Collection)
	// return ((Collection) obj).isEmpty();
	//
	// if (obj instanceof IEmpty)
	// return ((IEmpty) obj).isEmpty();
	//
	// return false;
	// }

	public static void objectFieldCopy(Object from, Object to) {

		Field[] resultFields = to.getClass().getDeclaredFields();
		for (int i = 0; i < resultFields.length; i++) {
			Field modelField = null;
			Object value;
			resultFields[i].setAccessible(true);
			try {
				modelField = from.getClass().getDeclaredField(resultFields[i].getName());
			} catch (NoSuchFieldException e) {
				// 因为 项目的框架 如果 属性是主键时，属性名是 id
				try {
					modelField = from.getClass().getSuperclass().getDeclaredField("id");
				} catch (NoSuchFieldException e1) {
					throw new RuntimeException(e1);
				}
			}
			try {
				modelField.setAccessible(true);
				value = modelField.get(from);
				// 类型转换
				if (!modelField.getType().getName().equals(resultFields[i].getType().getName())) {
					if (modelField.getType().getName().equals(java.sql.Timestamp.class.getName())) {
						if (value != null) {
							java.sql.Timestamp temp = (java.sql.Timestamp) value;
							String covValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp);
							resultFields[i].set(to, covValue);
						}
					} else if (modelField.getType().getName().equals(java.sql.Date.class.getName())) {
						if (value != null) {
							String covValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
							resultFields[i].set(to, covValue);
						}
					} else if (modelField.getType().getName().equals(java.util.Date.class.getName())) {
						if (value != null) {
							String covValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
							resultFields[i].set(to, covValue);
						}
					} else if (modelField.getType().getName().equals(java.math.BigDecimal.class.getName())) {
						if (value != null) {
							String covValue = ((java.math.BigDecimal) value).toString();
							resultFields[i].set(to, covValue);
						}
					} else if (modelField.getType().getName().equals(java.lang.Integer.class.getName())) {
						if (value != null) {
							String covValue = String.valueOf(value);
							resultFields[i].set(to, covValue);
						}
					} else if (modelField.getType().getName().equals(java.lang.Float.class.getName())) {
						if (value != null) {
							String covValue = String.valueOf(value);
							resultFields[i].set(to, covValue);
						}
					} else {
						throw new Exception("Type different Not Dealed");
					}
				} else {
					resultFields[i].set(to, value);
				}
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
			    throw new RuntimeException(e);
			} catch (Exception e) {
			    throw new RuntimeException(e);
			}

		}

	}

}
