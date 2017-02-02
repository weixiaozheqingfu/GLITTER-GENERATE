package jinhetech.oracle.generation.entity.utils;

import java.util.List;
import java.util.Map;

import jinhetech.oracle.generation.common.CommonUtil;
import jinhetech.oracle.generation.common.Constants;
import jinhetech.oracle.generation.entity.entity.Table;
import jinhetech.oracle.generation.entity.entity.TableColumn;
import jinhetech.oracle.generation.entity.entity.TableForeignkey;
import jinhetech.oracle.generation.entity.entity.Vo;

public class ServiceUtil {
	/**
	 * 判断当前字段是否为主键字段
	 * @param table
	 * @param column
	 * @return
	 */
	public static boolean isPrimaryKey(Table table,String column){
		boolean result = false;
		if(column.equals(table.getPrimarykey())){
			result = true;
		}
		return result;
	}

	/**
	 * 判断当前字段是否为数据库配置的外键字段(慎用该方法,注意使用的场景,使用之前最好看清楚该方法的实现)
	 * @param table
	 * @param column
	 * @return
	 */
	@Deprecated
	public static boolean isForeignKey(Table table,String column){
		boolean result = false;
		if(CommonUtil.isNotBlankObj(table) && CommonUtil.isNotBlankList(table.getForeignkeysList())){
			for(TableForeignkey foreignkey:table.getForeignkeysList()){
				if(foreignkey.getColumnName().equals(column)){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据字段名称获取相关外键实体(慎用该方法,注意使用的场景,使用之前最好看清楚该方法的实现)
	 * @param table
	 * @param column
	 * @return
	 */
	@Deprecated
	public static TableForeignkey getForeignkeyObj(Table table,String column){
		TableForeignkey foreignkeyObj = null;
		if(CommonUtil.isNotBlankObj(table) && CommonUtil.isNotBlankList(table.getForeignkeysList()) && CommonUtil.isNotBlank(column)){
			for(TableForeignkey foreignkey:table.getForeignkeysList()){
				if(foreignkey.getColumnName().equals(column)){
					foreignkeyObj = foreignkey;
					break;
				}
			}
		}
		return foreignkeyObj;
	}

	/**
	 * 判断当前字段是否为外键字段
	 * @param table
	 * @param column
	 * @return
	 */
	public static boolean isForeignKeyColumn(Table table,String column){
		boolean result = false;
		if(CommonUtil.isNotBlankObj(table) && CommonUtil.isNotBlankList(table.getColumnsList())){
			for(TableColumn t:table.getColumnsList()){
				if(t.getColumnName().equals(column) && !t.isSimpleJavaType()){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据字段名称外键实体字段对象
	 * @param table
	 * @param column
	 * @return
	 */
	public static TableColumn getForeignColumn(Table table,String column){
		TableColumn tableColumn = null;
		if(CommonUtil.isNotBlankObj(table) && CommonUtil.isNotBlankList(table.getColumnsList()) && CommonUtil.isNotBlank(column)){
			for(TableColumn c:table.getColumnsList()){
				if(c.getColumnName().equals(column)){
					tableColumn = c;
					break;
				}
			}
		}
		return tableColumn;
	}
	
	/**
	 * 根据config中ref配置引用获取Table实体对象
	 * @param entityMapList
	 * @param tableList
	 * @param ref
	 * @return
	 */
	public static Table getTableByCurrentMoudleRef(List<Map<String,Object>> entityMapList,List<Table> tableList,String ref){
		Table table = null;
		if(CommonUtil.isNotBlankList(entityMapList,tableList) && CommonUtil.isNotBlank(ref)){
			for(Map<String,Object> en:entityMapList){
				if(en.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().equals(ref)){
					for(Table t:tableList){
						if(t.getTableName().equals(en.get(Constants.MODULE_ENTITYS_ENTITY_TABLENAME))){
							table = t;
							break;
						}
					}
					break;
				}
			}
		}
		return table;
	}
	
	/**
	 * 根据config中ref配置引用获取Table实体对象
	 * @param entityMapList
	 * @param tableList
	 * @param ref
	 * @return
	 */
//	public static Table getTableByConfigRef(List<Map<String,Object>> configList,List<Table> tableList,String ref){
//		Table table = null;
//		List<Map<String,Object>> entityMapList = (List<Map<String, Object>>) moduleMap.get(Constants.MODULE_ENTYTIS);
//		if(CommonUtil.isNotBlankList(entityMapList,tableList) && CommonUtil.isNotBlank(ref)){
//			for(Map<String,Object> en:entityMapList){
//				if(en.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().equals(ref)){
//					for(Table t:tableList){
//						if(t.getTableName().equals(en.get(Constants.MODULE_ENTITYS_ENTITY_TABLENAME))){
//							table = t;
//							break;
//						}
//					}
//					break;
//				}
//			}
//		}
//		return table;
//	}
	
	/**
	 * 根据config中ref配置引用获取Table实体对象
	 * @param entityMapList
	 * @param tableList
	 * @param ref
	 * @return
	 */
//	public static Vo getVoByCurrentMoudleRef(List<Map<String,Object>> entityMapList,List<Table> tableList,String ref){
//		Vo vo = null;
//		if(CommonUtil.isNotBlankList(entityMapList,tableList) && CommonUtil.isNotBlank(ref)){
//			for(Map<String,Object> en:entityMapList){
//				if(en.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().equals(ref)){
//					for(Table t:tableList){
//						if(t.getTableName().equals(en.get(Constants.MODULE_ENTITYS_ENTITY_TABLENAME))){
//							table = t;
//							break;
//						}
//					}
//					break;
//				}
//			}
//		}
//		return vo;
//	}
	
	/**
	 * 根据config中ref配置引用获取Table实体对象
	 * @param entityMapList
	 * @param tableList
	 * @param ref
	 * @return
	 */
//	public static Table getTableByConfigRef(List<Map<String,Object>> configList,List<Table> tableList,String ref){
//		Table table = null;
//		List<Map<String,Object>> entityMapList = (List<Map<String, Object>>) moduleMap.get(Constants.MODULE_ENTYTIS);
//		if(CommonUtil.isNotBlankList(entityMapList,tableList) && CommonUtil.isNotBlank(ref)){
//			for(Map<String,Object> en:entityMapList){
//				if(en.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().equals(ref)){
//					for(Table t:tableList){
//						if(t.getTableName().equals(en.get(Constants.MODULE_ENTITYS_ENTITY_TABLENAME))){
//							table = t;
//							break;
//						}
//					}
//					break;
//				}
//			}
//		}
//		return table;
//	}
	
	/**
	 * 根据config中ref配置引用获取对应的entityMap信息
	 * @param entityMapList
	 * @param ref
	 * @return
	 */
	public static Map<String,Object> getEntityMapByConfigRef(List<Map<String,Object>> entityMapList,String ref){
		Map<String,Object> entityMap = null;
		if(CommonUtil.isNotBlankList(entityMapList) && CommonUtil.isNotBlank(ref)){
			for(Map<String,Object> en:entityMapList){
				if(en.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().equals(ref)){
					entityMap = en;
					break;
				}
			}
		}
		return entityMap;
	}
	
	
	/**
	 * 获取主键字段
	 * @param table
	 * @return
	 */
	public static TableColumn getPrimaryKeyColumn(Table table){
		TableColumn tableColumn = null;
		if(CommonUtil.isNotBlankObj(table)){
			for(TableColumn t:table.getColumnsList()){
				if(ServiceUtil.isPrimaryKey(table, t.getColumnName())){
					tableColumn = t;
					break;
				}
			}
		}
		return tableColumn;
	}
	
	/**
	 * 获取简单类型字段
	 * @param table
	 * @return
	 */
	public static TableColumn getSimpleColumn(Table table,String column){
		TableColumn tableColumn = null;
		if(CommonUtil.isNotBlankObj(table) && CommonUtil.isNotBlank(column)){
			for(TableColumn t:table.getColumnsList()){
				if(t.getColumnName().equals(column)){
					tableColumn = t;
					break;
				}
			}
		}
		return tableColumn;
	}
	
}
