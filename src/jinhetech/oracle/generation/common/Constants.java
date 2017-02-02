package jinhetech.oracle.generation.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 读取配置文件applicationContext.properties，设置统一常量
 * 
 * @author LiMengjun
 * @version 1.0 2015-05-01
 */
@SuppressWarnings("unchecked")
public class Constants {
	private static Properties properties = new Properties();
	
	private static final List<Map<String,Object>> configList = new ArrayList<Map<String,Object>>();

	public static List<Map<String, Object>> getConfiglist() {
		return configList;
	}
	public static Logger log = Logger.getLogger(Constants.class);
	static {
		try {
			// 加载properties文件
			properties.load(Constants.class.getClassLoader().getResourceAsStream("jinhetech/oracle/config/dbcp.properties"));
			
			// TODO 解析到某些必须节点有值,节点之间有依赖关系的,一个有值另一个必须有值得情况,必须跑出异常,程序终止,并告诉用户哪里配置错了,错的原因是什么
			// 读取并解析config.xml文件
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString();
			//初始化读取配置文件
			SAXReader saxReader = new SAXReader();
			String StrutsPath = rootPath + "jinhetech/oracle/config/config.xml";
			Document doc = null;
			doc = saxReader.read(new File(StrutsPath));
			if(doc!=null){
				Map<String,Object> moduleMap = null;
				Element root = doc.getRootElement();
				List<Element> moduleElementList = root.elements("module");
				if(CommonUtil.isNotBlankList(moduleElementList)){
					// 解析多个
					for(Element moduleElement:moduleElementList){
						moduleMap = new HashMap<String,Object>();
						CommonUtil.putMap(moduleElement, moduleMap);
						
						Element entitysElement = moduleElement.element("entitys");
						CommonUtil.putMap(entitysElement, moduleMap);
						Map<String,Object> entityMap = null;
						List<Map<String,Object>> entityMapList = null;
						List<Map<String,Object>> foreignMapList = null;
						List<Element> entityList = entitysElement==null?null:entitysElement.elements();
						if(CommonUtil.isNotBlankList(entityList)){
							entityMapList = new ArrayList<Map<String,Object>>();
							for(Element entityElement:entityList){
								entityMap = new HashMap<String,Object>();
								CommonUtil.putMap(entityElement, entityMap);
								entityMapList.add(entityMap);
								
								Element foreignsElement = entityElement.element("foreigns");
								if(CommonUtil.isNotBlankObj(foreignsElement)){
									CommonUtil.putMap(foreignsElement, moduleMap);
									Map<String,Object> foreignMap = null;
									List<Element> foreignList = foreignsElement.elements();
									if(CommonUtil.isNotBlankList(foreignList)){
										foreignMapList = new ArrayList<Map<String,Object>>();
										for(Element foreignElement:foreignList){
											foreignMap = new HashMap<String,Object>();
											CommonUtil.putMap(foreignElement, foreignMap);
											foreignMapList.add(foreignMap);
										}
										entityMap.put(Constants.MODULE_ENTITYS_ENTITY_FOREIGNS, foreignMapList);
									}
								}
							}
							moduleMap.put(Constants.MODULE_ENTYTIS, entityMapList);
						}
						
						Element daosElement = moduleElement.element("daos");
						CommonUtil.putMap(daosElement, moduleMap);
						Map<String,Object> daoMap = null;
						List<Map<String,Object>> daoMapList = null;
						List<Map<String,Object>> methodMapList = null;
						List<Element> daoList = daosElement==null?null:daosElement.elements();
						if(CommonUtil.isNotBlankList(daoList)){
							daoMapList = new ArrayList<Map<String,Object>>();
							for(Element daoElement:daoList){
								daoMap = new HashMap<String,Object>();
								CommonUtil.putMap(daoElement, daoMap);
								daoMapList.add(daoMap);
								
								Element methodsElement = daoElement.element("methods");
								if(CommonUtil.isNotBlankObj(methodsElement)){
									CommonUtil.putMap(methodsElement, moduleMap);
									Map<String,Object> methodMap = null;
									List<Element> methodList = methodsElement.elements();
									if(CommonUtil.isNotBlankList(methodList)){
										methodMapList = new ArrayList<Map<String,Object>>();
										for(Element methodElement:methodList){
											methodMap = new HashMap<String,Object>();
											CommonUtil.putMap(methodElement, methodMap);
											methodMapList.add(methodMap);
										}
										daoMap.put(Constants.MODULE_DAOS_DAO_METHODS, methodMapList);
									}
								}
							}
							moduleMap.put(Constants.MODULE_DAOS, daoMapList);
						}
						
                        Element vosElement = moduleElement.element("vos");
						CommonUtil.putMap(vosElement, moduleMap);
						Map<String,Object> voMap = null;
						List<Map<String,Object>> voMapList = null;
						List<Map<String,Object>> fieldMapList = null;
						List<Element> voList = vosElement==null?null:vosElement.elements();
						if(CommonUtil.isNotBlankList(voList)){
							voMapList = new ArrayList<Map<String,Object>>();
							for(Element voElement:voList){
								voMap = new HashMap<String,Object>();
								CommonUtil.putMap(voElement, voMap);
								voMapList.add(voMap);
								
								Element fieldsElement = voElement.element("fields");
								if(CommonUtil.isNotBlankObj(fieldsElement)){
									CommonUtil.putMap(fieldsElement, moduleMap);
									
									Map<String,Object> fieldMap = null;
									List<Element> fieldList = fieldsElement.elements();
									if(CommonUtil.isNotBlankList(fieldList)){
										fieldMapList = new ArrayList<Map<String,Object>>();
										for(Element fieldElement:fieldList){
											fieldMap = new HashMap<String,Object>();
											CommonUtil.putMap(fieldElement, fieldMap);
											fieldMapList.add(fieldMap);
										}
										voMap.put(Constants.MODULE_VOS_VO_FIELDS, fieldMapList);
									}
								}
							}
							moduleMap.put(Constants.MODULE_VOS, voMapList);
						}
						configList.add(moduleMap);
						log.info(moduleMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 数据库相关*/
	public static final String DBCP_URL = properties.getProperty("url");
	public static final String DBCP_USERNAME = properties.getProperty("username");
	public static final String DBCP_PASSWORD = properties.getProperty("password");
	public static final String DBCP_DRIVERCLASS = properties.getProperty("driverClass");
	public static final Integer DBCP_INITIALSIZE = Integer.valueOf(properties.getProperty("initialSize"));
	public static final Integer DBCP_MAXACTIVE = Integer.valueOf(properties.getProperty("maxActive"));
	public static final Integer DBCP_MAXIDLE = Integer.valueOf(properties.getProperty("maxIdle"));
	public static final Integer DBCP_MAXWAIT = Integer.valueOf(properties.getProperty("maxWait"));
	
	/** 数据库表外键引用对应关系1:一对一;2:多对一 */
	public static final String FOREIGN_TYPE_ONE_TO_ONE = "1";
	public static final String FOREIGN_TYPE_MANY_TO_ONE = "2";
	public static final String FOREIGN_TYPE_ONE_TO_ONE_ANNOTATION = "@OneToOne";
	public static final String FOREIGN_TYPE_MANY_TO_ONE_ANNOTATION = "@ManyToOne";
	
	/** xml映射的configList中Map<String,Object>的key值 */
	public static final String MODULE_ID = "module.id";
	public static final String MODULE_NAME = "module.name";
	public static final String MODULE_NAMESPACE = "module.namespace";
	public static final String MODULE_AUTHOR = "module.author";
	
	public static final String MODULE_ENTYTIS = "module.entytis";
	public static final String MODULE_ENTITYS_ID = "module.entitys.id";
	public static final String MODULE_ENTITYS_NAME = "module.entitys.name";
	public static final String MODULE_ENTITYS_ENTITY_ID = "module.entitys.entity.id";
	public static final String MODULE_ENTITYS_ENTITY_TABLENAME = "module.entitys.entity.tablename";
	public static final String MODULE_ENTITYS_ENTITY_COMMENT = "module.entitys.entity.comment";
	public static final String MODULE_ENTITYS_ENTITY_FOREIGNS = "module.entitys.entity.foreigns";
	public static final String MODULE_ENTITYS_ENTITY_FOREIGNS_FOREIGN_CLASS = "module.entitys.entity.foreigns.foreign.class";
	
	public static final String MODULE_DAOS = "module.daos";
	public static final String MODULE_DAOS_ID = "module.daos.id";
	public static final String MODULE_DAOS_NAME = "module.daos.name";
	public static final String MODULE_DAOS_DAO_ID = "module.daos.dao.id";
	public static final String MODULE_DAOS_DAO_REF = "module.daos.dao.ref";
	public static final String MODULE_DAOS_DAO_METHODS = "module.daos.dao.methods";
	public static final String MODULE_DAOS_DAO_METHODS_METHOD_TYPE = "module.daos.dao.methods.method.type";
	public static final String MODULE_DAOS_DAO_METHODS_METHOD_BY = "module.daos.dao.methods.method.by";
	public static final String MODULE_DAOS_DAO_METHODS_METHOD_COLUMN = "module.daos.dao.methods.method.column";
	
	public static final String MODULE_VOS = "module.vos";
	public static final String MODULE_VOS_ID = "module.vos.id";
	public static final String MODULE_VOS_NAME = "module.vos.name";
	public static final String MODULE_VOS_VO_ID = "module.vos.vo.id";
	public static final String MODULE_VOS_VO_NAME = "module.vos.vo.name";
	public static final String MODULE_VOS_VO_FIELDS = "module.vos.vo.fields";
	public static final String MODULE_VOS_VO_FIELDS_FIELD_TYPE = "module.vos.vo.fields.field.type";
	public static final String MODULE_VOS_VO_FIELDS_FIELD_REF= "module.vos.vo.fields.field.ref";
	public static final String MODULE_VOS_VO_FIELDS_FIELD_COLUMN = "module.vos.vo.fields.field.column";
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
}
