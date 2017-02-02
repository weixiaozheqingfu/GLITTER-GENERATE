package jinhetech.oracle.generation.entity.service;

import java.util.List;
import java.util.Map;

import jinhetech.oracle.generation.entity.entity.Table;

public interface DaoService {
	
	/**
	 * 生成Dao层信息
	 * @param moduleMap 当前模块Map
	 * @param daoMap 当前Dao配置Map
	 * @param tableList
	 * @return
	 * @throws Exception
	 */
	public boolean generateDao(Map<String, Object> moduleMap,Map<String,Object> entityMap,List<Table> tableList) throws Exception;
	
}
