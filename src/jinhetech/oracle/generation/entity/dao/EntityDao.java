package jinhetech.oracle.generation.entity.dao;

import java.util.List;
import java.util.Map;

import jinhetech.oracle.generation.common.ConnectionFactory;

import org.springframework.jdbc.core.JdbcTemplate;

public class EntityDao {

	private JdbcTemplate jt;

	public EntityDao(){
		this.jt = new JdbcTemplate(ConnectionFactory.getInstance().getDS());
	}

	public List<Map<String,Object>> getMapList(String sql,Object[] args){
		List<Map<String,Object>> resultMap = null;
		resultMap = jt.queryForList(sql,args);
		return resultMap;
	}
	
}
