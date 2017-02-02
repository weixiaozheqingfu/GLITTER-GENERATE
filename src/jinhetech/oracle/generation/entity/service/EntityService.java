package jinhetech.oracle.generation.entity.service;

import java.util.Map;

import jinhetech.oracle.generation.entity.entity.Table;

public interface EntityService {

	/**
	 * 根据表名称获取Table实体
	 * @param entityMap
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public Table getTable(Map<String,Object> entityMap,String tableName) throws Exception;
	
	/**
	 * 生成Entity实体信息
	 * @param moduleMap 当前模块Map
	 * @param entityMap 当前实体Map
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public boolean generateEntity(Map<String, Object> moduleMap,Map<String,Object> entityMap,Table table) throws Exception;
	
}
