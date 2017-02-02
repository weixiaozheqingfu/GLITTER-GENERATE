package jinhetech.oracle.generation.entity.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jinhetech.oracle.generation.common.CommonUtil;
import jinhetech.oracle.generation.common.Constants;
import jinhetech.oracle.generation.entity.entity.Table;
import jinhetech.oracle.generation.entity.entity.VoField;
import jinhetech.oracle.generation.entity.service.DaoService;
import jinhetech.oracle.generation.entity.service.DaoServiceImpl;
import jinhetech.oracle.generation.entity.service.EntityService;
import jinhetech.oracle.generation.entity.service.EntityServiceImpl;

public class EntityController {

	private EntityService entityService;
	private DaoService daoService;
	List<Table> tableList;
	List<VoField> voFieldList;
	
	public EntityController(){
		entityService = new EntityServiceImpl();
		daoService = new DaoServiceImpl();
		tableList = new ArrayList<Table>();
		voFieldList = new ArrayList<VoField>();
	}

	@SuppressWarnings("unchecked")
	public void generate() throws Exception{
		// -----------------------------------1.自动生成entity层代码,应该将所有的实体都生成完一遍,再循环生成每一个模块的其他东西,因为实体的引用可能是跨模块的----------------------------------
		for(Map<String, Object> moduleMap:Constants.getConfiglist()){
			// 得到模块的所有表集合
			List<Map<String,Object>> entityMapList = (List<Map<String, Object>>) moduleMap.get(Constants.MODULE_ENTYTIS);
			if(CommonUtil.isNotBlankList(entityMapList)){
				Table table = null;
				for(Map<String,Object> entityMap:entityMapList ){
					String tableName = entityMap.get(Constants.MODULE_ENTITYS_ENTITY_TABLENAME).toString();
					table = entityService.getTable(entityMap,tableName);
					tableList.add(table);
					// 根据当前的moduleMap信息、entityMap信息、库中得到的table实体信息,来生成当前table实体的Entity.java文件
					entityService.generateEntity(moduleMap, entityMap, table);
				}
			}
		}
		
		// -----------------------------------2.自动生成vo层代码-------------------------------------------------------------
		for(Map<String, Object> moduleMap:Constants.getConfiglist()){
			// 得到模块的所有表集合
			List<Map<String,Object>> voMapList = (List<Map<String, Object>>) moduleMap.get(Constants.MODULE_VOS);
			if(CommonUtil.isNotBlankList(voMapList)){
				VoField voField = null;
				for(Map<String,Object> entityMap:voMapList ){
					// voField = voService.getVoField(entityMap);
					voFieldList.add(voField);
					// TODO 另外多写一个帮助方法，通过ref名称快速获取tableList中的表
					// entityService.generateVo(moduleMap, entityMap, voField); 
				}
			}
		}
		
		// -----------------------------------3.自动生成dao层代码------------------------------------------------------------------------------------------------------------------------
		for(Map<String, Object> moduleMap:Constants.getConfiglist()){
			List<Map<String,Object>> daoMapList = (List<Map<String, Object>>) moduleMap.get(Constants.MODULE_DAOS);
			if(CommonUtil.isNotBlankList(tableList,daoMapList)){
				for(Map<String,Object> daoMap:daoMapList){
					daoService.generateDao(moduleMap, daoMap, tableList);
				}
			}
		}
		
		
		
		// TODO ----------------------------------------------------------4.自动生成service层代码-------------------------------------------------------------
		
		// TODO  4.service层和jsp需要做三套,一套纯同步增删该查及常用方法 一套纯异步增删该查及常用方法,一套列表用同步其他用异步的。用户根据需要更接近自己想法那一套进行使用。这个应该在全局节点配置上
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
