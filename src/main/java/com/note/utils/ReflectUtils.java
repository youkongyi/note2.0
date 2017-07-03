package com.note.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import ecp.runtime.collection.RequestParameterMap;

public class ReflectUtils {

	/**
	 * 获得实体类所有定义属性，包括public，private等
	 * 
	 * @param clazz
	 *            实体类Class
	 * @return
	 */
	public static Field[] getAllFields(Class clazz) {
		return clazz.getDeclaredFields();
	}

	/**
	 * 根据属性名获得Field对象
	 * 
	 * @param clazz
	 *            实体类Class
	 * @param fieldName
	 *            属性名
	 * @return
	 */
	public static Field getField(Class clazz, String fieldName)
			throws Exception {

		if (null == fieldName || "".equals(fieldName.trim())) {
			return null;
		}

		Field[] fields = getAllFields(clazz);

		for (Field field : fields) {
			if (field.getName().equalsIgnoreCase(fieldName)) {
				return field;
			}
		}

		return null;

	}

	/**
	 * 判断是否含有属性<br/>
	 * 
	 * @param clazz
	 *            实体类Class<br/>
	 * @param fieldName
	 *            属性名<br/>
	 * @return
	 */
	public static boolean existField(Class clazz, String fieldName) {
		if (null == fieldName || "".equals(fieldName.trim())) {
			return false;
		}

		Field[] fields = getAllFields(clazz);

		for (Field field : fields) {
			if (field.getName().trim().toLowerCase()
					.equals(fieldName.trim().toLowerCase())) {
				return true;
			}

		}

		return false;
	}

	/**
	 * 获得实体类所有定义方法，包括public，private等
	 * 
	 * @param clazz
	 *            实体类Class
	 * @return
	 */
	public static Method[] getAllMethod(Class clazz) {
		return clazz.getMethods();
	}

	/**
	 * 根据方法名和方法类型获得Method对象
	 * 
	 * @param clazz
	 *            实体类Class
	 * @param methodName
	 *            方法名
	 * @return
	 * @throws Exception
	 */
	public static Method getMethod(Class clazz, String methodName)
			throws Exception {

		if (null == methodName || "".equals(methodName.trim())) {
			return null;
		}

		Method[] methods = getAllMethod(clazz);

		for (Method method : methods) {
			if (method.getName().trim().toLowerCase()
					.equals(methodName.trim().toLowerCase())) {
				return method;
			}
		}

		return null;
	}
	
	public static Method getWriteMethod(Class clazz, String methodName)
			throws Exception {

		if (null == methodName || "".equals(methodName.trim())) {
			return null;
		}

		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(methodName,clazz);
		return propertyDescriptor.getWriteMethod();
	}
	
	
	

	/**
	 * 根据方法名和方法类型获得Method对象
	 * 
	 * @param clazz
	 *            实体类Class
	 * @param methodName
	 *            方法名
	 * @param paramClazzes
	 *            参数类型列表
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	private static Method getMethod(Class clazz, String methodName,
			Class[] paramClazzes) throws Exception {

		if (null == methodName || "".equals(methodName.trim())) {
			return null;
		}

		return clazz.getMethod(methodName, paramClazzes);
	}

	/**
	 * 判断是否含有某一个方法<br/>
	 * 
	 * @param clazz
	 *            属性名<br/>
	 * @param methodName
	 *            方法名
	 * @return
	 */
	public static boolean existMethod(Class clazz, String methodName) {
		if (null == methodName || "".equals(methodName.trim())) {
			return false;
		}

		Method[] methods = getAllMethod(clazz);

		for (Method method : methods) {
			if (method.getName().trim().toLowerCase()
					.equals(methodName.trim().toLowerCase())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断是否含有某一个方法<br/>
	 * 
	 * @param clazz
	 *            属性名<br/>
	 * @param methodName
	 *            方法名
	 * @param paramClazzes
	 *            参数类型列表
	 * @return
	 */
	@Deprecated
	private static boolean existMethod(Class clazz, String methodName,
			Class[] paramClazzes) throws Exception {
		try {

			Method method = getMethod(clazz, methodName, paramClazzes);

			if (method != null) {
				return true;
			}

		} catch (NoSuchMethodException e) {

			return false;

		}
		return false;
	}

	/**
	 * 获得属性值<br/>
	 * 
	 * @param clazz
	 *            实体类Class<br/>
	 * @param propertyName
	 *            属性值<br/>
	 * @return
	 */
	public static Object getPropertyValue(Object entity, String propertyName)
			throws Exception {

		if (propertyName.contains(".")) {
			String propName = propertyName.substring(0,
					propertyName.indexOf("."));

			String refPropName = propertyName.substring(propertyName
					.indexOf(".") + 1);

			Object o = getPropertyValue(entity, propName);

			if (o != null) {
				return getPropertyValue(o, refPropName);
			}
		}

		// 需要反射的对象实例
		Object obj = null;
		// 属性集合
		Field[] fields = null;
		// 属性
		Field field = null;
		// 属性名称
		String fieldName = null;
		// 方法集合
		Method[] methods = null;
		// 方法
		Method method = null;
		// 方法名
		String methodName = null;

		Class clazz = entity.getClass();
		// 反射创建对象
		obj = clazz.newInstance();
		// 获得声明的所有属性，private or public
		fields = getAllFields(clazz);
		// 获得声明的所有方法，private or public
		methods = getAllMethod(clazz);
		// 遍历属性集合
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];

			if (propertyName.toUpperCase().equals(field.getName().trim().toUpperCase())) {
				// 属性名
				fieldName = field.getName();
				// 设置方法名
				methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

				for (int j = 0; j < methods.length; j++) {
					method = methods[j];

					if (method.getName().equals(methodName)) {
						
						Object val = method.invoke(entity, null);

						return val;
					}
				}

				// 如果执行到此步，表名没有getter方法，或者getter方法设置
				boolean accessible = field.isAccessible();

				field.setAccessible(true);

				Object val = field.get(entity);

				field.setAccessible(accessible);

				return val;
			}
		}

		return null;
	}
	
	/**
	 * <pre>
	 * 映射表列名到对象属性值<br/>
	 * 
	 * @param ObjList
	 *        实体类集合对象<br/>
	 *        
	 * @param rs
	 *        ResultSet对象，获取ResultSetMetaData对象，与实体类属性进行匹配.<br/>
	 *        
	 * @param clazz
	 *        实体类Class<br/>
	 *        
	 * @return
	 *        封装属性值后的实体类集合对象<br/>
	 * 
	 * @throws Exception
	 * 
	 * </pre>
	 */
	public static List mappingColumnsToObjectProperties(ResultSet rs,Class clazz) throws Exception
	{
		List objList = new ArrayList();
		
		//获得结果集元数据
		ResultSetMetaData metaData = rs.getMetaData();
		//需要反射的对象实例
		Object obj = null;
		//属性集合
		Field[] fields = null;
		//属性
		Field field = null;
		//属性名称
		String fieldName = null;
		//方法集合
		Method[] methods = null;
		//方法
		Method method = null;
		//方法名
		String methodName = null;
		//方法参数
		String methodParamType = null;
		//方法参数全名
		String fullMethodParamType = null;
		//列名
		String colName = null;	
		//去除"_"的列名
		String specialColName = null;
		while(rs.next())
		{
			//反射创建对象
			obj = clazz.newInstance();
			//获得声明的所有属性，private or public
			fields = getAllFields(clazz);
			//获得声明的所有方法，private or public
			methods = getAllMethod(clazz);
			//遍历属性集合
			for(int i=0;i<fields.length;i++)
			{
				field = fields[i];
				for(int j=1;j<=metaData.getColumnCount();j++)
				{
					//列名
					colName = metaData.getColumnName(j);
					//初始化
					specialColName = colName;
					//去除下划线
					if(specialColName.contains("_"))
					{
						specialColName = specialColName.replace("_","");
					}
					if(specialColName.trim().toUpperCase().equals(field.getName().trim().toUpperCase()))
					{
						//属性名
						fieldName=field.getName();
						//设置方法名
						methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
						for(int k=0;k<methods.length;k++)
						{
							method = methods[k];						
							if(method.getName().equals(methodName))
							{							
								//获得属性类型
								methodParamType = method.getParameterTypes()[0].getSimpleName();
								
								fullMethodParamType = method.getParameterTypes()[0].getName();
								
								if(methodParamType.contains("String"))
								{
									method.invoke(obj,rs.getString(colName));
								}
								else if(methodParamType.contains("int") || methodParamType.contains("Integer"))
								{
									method.invoke(obj,rs.getInt(colName));
								}
								else if(methodParamType.contains("long") || methodParamType.contains("Long"))
								{
									method.invoke(obj,rs.getLong(colName));
								}
								else if(methodParamType.contains("short") || methodParamType.contains("Short"))
								{
									method.invoke(obj,rs.getLong(colName));
								}
								else if(methodParamType.contains("double") || methodParamType.contains("Double"))
								{
									method.invoke(obj,rs.getDouble(colName));
								}
								else if(methodParamType.contains("float") || methodParamType.contains("Float"))
								{
									method.invoke(obj,rs.getFloat(colName));
								}
								else if(methodParamType.contains("boolean") || methodParamType.contains("Boolean"))
								{
									method.invoke(obj,rs.getBoolean(colName));
								}
								else if(methodParamType.contains("Date"))
								{
									methodParamType = method.getParameterTypes()[0].getName();
									/**获得的日期没有时分秒
									//java.util.Date包中
									if(methodParamType.contains("java.util"))
									{
										if(rs.getDate(colName)!=null)
										{
											Date utilDate = new Date(rs.getDate(colName).getTime());
											method.invoke(obj,utilDate);
										}
										
									}
									else {
										method.invoke(obj,rs.getDate(colName));
									}
									**/
									//日期格式化类
									SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									
									//获得日期字符串
									String dateStr = rs.getString(colName);
									
									Date utilDate = null;
									
									if(dateStr!=null && !dateStr.trim().equals(""))
									{
										//日期字符串转换为日期对象
										utilDate = fmt.parse(dateStr);
									}
									
									if(methodParamType.contains("java.util"))
									{
										method.invoke(obj,utilDate);
									}
									else {
										method.invoke(obj,new java.sql.Date(utilDate.getTime()));
									}
								}
								else if(methodParamType.contains("Time"))
								{
									method.invoke(obj,rs.getTime(colName));
								}
								else if(methodParamType.contains("Timestamp"))
								{
									method.invoke(obj,rs.getTimestamp(colName));
								}									
								else if(methodParamType.contains("BigDecimal"))
								{
									method.invoke(obj,rs.getBigDecimal(colName));
								}
								else if(methodParamType.contains("byte") || methodParamType.contains("Byte"))
								{
									method.invoke(obj,rs.getByte(colName));
								}
								else if(methodParamType.toUpperCase().contains("BLOB"))
								{
									if(fullMethodParamType.contains("java.sql"))
									{
										method.invoke(obj,rs.getBlob(colName));
									}
									else
									{
//										method.invoke(obj,(BLOB)rs.getBlob(colName));
									}
								}	
								else if(methodParamType.toUpperCase().contains("CLOB"))
								{
									if(fullMethodParamType.contains("java.sql"))
									{
										method.invoke(obj,rs.getClob(colName));
									}
									else
									{
//										method.invoke(obj,(CLOB)rs.getClob(colName));
									}
								}
								else if(methodParamType.contains("Object"))
								{
									method.invoke(obj,rs.getObject(colName));
								}
								else 
								{
									break;
								}									
							}						
						}		
					}
				}
			}
			//添加对象
			objList.add(obj);
		}
		
		return objList;
	}
	
	

	public static Object packageEntityByRequestParameterMap(RequestParameterMap map, Class clazz) {
		
		Object obj = null;

		Field[] fields = clazz.getDeclaredFields();

		Method[] methods = clazz.getDeclaredMethods();

		String fieldName = null;

		String methodName = null;

		// 方法参数
		String methodParamType = null;

		Object val = null;
		
		try {
			
			obj = clazz.newInstance();
			
			if(map==null || map.size()==0) return obj;

			while(clazz!=Object.class)
			{
				for (Field field : fields) {
					
					fieldName = field.getName();
					
					val = map.get(fieldName);
					if (null == val ){
						continue;
					}
//					if (null == val || "".equals(val.toString().trim())) continue;
					methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					for (Method method : methods) {
						
						if (methodName.equalsIgnoreCase(method.getName())) {

							methodParamType = method.getParameterTypes()[0].getSimpleName();

							if (methodParamType.equals("String")) 
							{
								method.invoke(obj, val);
							} 
							else if (methodParamType.equals("int")|| methodParamType.equals("Integer")) 
							{
								try {

									method.invoke(obj,Integer.parseInt(val.toString()));

								} catch (Exception e) {
									
									System.err.println(e.getMessage());
									
								}
							} 
							else if (methodParamType.equals("long") || methodParamType.equals("Long")) 
							{
								try {
									
									method.invoke(obj,Long.parseLong(val.toString()));
									
								} catch (Exception e) {
									
									System.err.println(e.getMessage());
									
								}
							} 
							else if (methodParamType.equals("short")|| methodParamType.equals("Short")) 
							{
								try {

									method.invoke(obj,Short.parseShort(val.toString()));

								} catch (Exception e) {
									
									System.err.println(e.getMessage());
									
								}
							} 
							else if (methodParamType.equals("double")	|| methodParamType.equals("Double")) 
							{
								try {

									method.invoke(obj,Double.parseDouble(val.toString()));
									
								} catch (Exception e) {
									System.err.println(e.getMessage());
								}
							} 
							else if (methodParamType.equals("float")|| methodParamType.equals("Float")) 
							{
								try {
									
									method.invoke(obj,Float.parseFloat(val.toString()));
									
								} catch (Exception e) {
									System.err.println(e.getMessage());
								}
							} 
							else if (methodParamType.equals("boolean")|| methodParamType.equals("Boolean")) 
							{
								try {

									method.invoke(obj,Boolean.parseBoolean(val.toString()));
									
								} catch (Exception e) {
									System.err.println(e.getMessage());
								}
							} 
							else if (methodParamType.equals("Date")) 
							{
								packageDateFiled(obj,method,val);
							} 
							else if (methodParamType.equals("BigDecimal")) {
								try {

									method.invoke(obj, BigDecimal.valueOf(Double.parseDouble(val.toString())));
									
								} catch (Exception e) {
									System.err.println(e.getMessage());
								}
							}

							break;
						}
					}
				}
				
				fieldName = "clientType";
				if(existField(clazz,"clientType")){
					Method method = getWriteMethod(clazz,"clientType");
					method.invoke(obj, map.getTermParameter().getClientType()+"");
				}
				
				clazz = clazz.getSuperclass();
				
				fields = clazz.getDeclaredFields();

				methods = clazz.getDeclaredMethods();
				
			}
			
			
			
		} catch (Exception ex) {
			
			System.err.println("封装属性" + fieldName + "出错，错误消息：" + ex.getMessage());

			return null;
		}

		return obj;
	}

	private static void packageDateFiled(Object obj,Method method,Object val)
	{
		try {
			
			SimpleDateFormat fmt = new SimpleDateFormat();
			
			if (val != null && !val.equals("")) {
				
				if (val.toString().contains("-")) {
					
					fmt.applyPattern("yyyy-MM-dd");
					
					if (val.toString().contains(" ") && val.toString().contains(":")) {
						fmt.applyPattern("yyyy-MM-dd HH:mm:ss");
					}
					
				} else if (val.toString().contains("/")) {
					
					fmt.applyPattern("yyyy/MM/dd");
					
					if (val.toString().contains(" ") && val.toString().contains(":")) {
						fmt.applyPattern("yyyy/MM/dd HH:mm:ss");
					}
					
				}
				
				method.invoke(obj,fmt.parse(val.toString()));
				
			}
			
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			
		}
	}

	public static Object packageEntityByRequestParameterMap(RequestParameterMap map, Object obj) {
		
		if (obj == null) {
			return null;
		}
		
		if(map==null || map.size()==0) return obj;

		Class clazz = obj.getClass();

		Field[] fields = clazz.getDeclaredFields();

		Method[] methods = clazz.getDeclaredMethods();

		String fieldName = null;

		String methodName = null;

		Object val = null;

		try {
			
			for (Field field : fields) {
				
				fieldName = field.getName();

				val = map.get(fieldName);

				val = (null == val) ? "" : val;

				methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

				for (Method method : methods) {
					
					if (methodName.equalsIgnoreCase(method.getName())) {
						
						method.invoke(obj, val);

						break;
					}
				}
			}
		} catch (Exception ex) {
			System.err.println("封装属性" + fieldName + "出错，错误消息："
					+ ex.getMessage());

			return null;
		}

		return obj;
	}
	/**
	 * 调用set方法
	 * 
	 * @param fieldName
	 *            字段名
	 * @param obj
	 *            对象
	 * @param value
	 *            值
	 */
	public static void invokeSetMethod(final String fieldName, final Object obj, final Object value) {
		final Class className = obj.getClass();
		final Class valueClass = value.getClass();
		final String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		try {
			final Method setMethod = className.getDeclaredMethod(setMethodName, valueClass);
			setMethod.invoke(obj, new Object[] { value });
		} catch (final Exception ex) {
			System.err.println("封装属性" + fieldName + "出错，错误消息：" + ex.getMessage());
		}
	}
	
	/**
	 * 调用get方法
	 * 
	 * @param fieldName
	 *            字段名
	 * @param obj
	 *            对象
	 * @param value
	 *            值
	 */
	public static Object invokeGetMethod(final String fieldName, final Object obj) {
		final Class className = obj.getClass();
		Object result = null;
		final String setMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		try {
			final Method getMethod = className.getDeclaredMethod(setMethodName, new Class[] {});
			result = getMethod.invoke(obj, new Object[] {});
		} catch (final Exception ex) {
			System.err.println("封装属性" + fieldName + "出错，错误消息：" + ex.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 更新数据库表数据
	 * @param object 目标表映射的实体类对象
	 * @param gid 更新目标表中的主键Gid
	 * @param clazz 目标表映射的实体类字节码对象
	 * @param paramsList 
	 * @param sql 
	 * @param conditionField  where条件字段
	 * xufan
	 */
	public static void updateTableData(Object object, String gid,Class clazz,List<Object> paramsList,StringBuffer sql,String conditionField) {

		Field[] fields = ReflectUtils.getAllFields(clazz);

		for (Field field : fields) {
			String name = field.getName();
			AnnoField annotation = field.getAnnotation(AnnoField.class);
			if (annotation != null) {
				String value = annotation.value();
				String dataType = annotation.dataType();
				boolean allowNull = annotation.allowNull();
				try {
					Object propertyValue = ReflectUtils.getPropertyValue(object, name);
					if("DATE".equals(dataType)){
						Date date = new Date();
						if(propertyValue!=null){
							SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							try {
								date = df.parse(propertyValue.toString());
							} catch (Exception e) {
								df=new SimpleDateFormat("yyyy-MM-dd");
								date = df.parse(propertyValue.toString());
							}
						}
							propertyValue = date;
					}
					if (propertyValue != null) {
						sql.append("T." + value + "=?,");
							paramsList.add(propertyValue);
					}else{
						if(allowNull){
							sql.append("T." + value + "=null,");
						}
					}
				} catch (Exception e) {
				}
			}
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" WHERE T."+(StringUtils.isNotNull(conditionField)?conditionField:"GID")+"= ?");
		paramsList.add(gid);
		
	}

}
