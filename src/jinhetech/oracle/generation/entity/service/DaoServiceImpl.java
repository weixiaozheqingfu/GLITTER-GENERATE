package jinhetech.oracle.generation.entity.service;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jinhetech.oracle.generation.common.CommonUtil;
import jinhetech.oracle.generation.common.Constants;
import jinhetech.oracle.generation.common.DateUtils;
import jinhetech.oracle.generation.entity.entity.Table;
import jinhetech.oracle.generation.entity.entity.TableColumn;
import jinhetech.oracle.generation.entity.entity.TableForeignkey;
import jinhetech.oracle.generation.entity.utils.ServiceUtil;

import org.apache.log4j.Logger;

public class DaoServiceImpl implements DaoService {
	
	
	private static Logger log = Logger.getLogger(DaoServiceImpl.class);

	/**
	 * 生成Dao层信息
	 * @param moduleMap 当前模块Map
	 * @param daoMap 当前Dao配置Map
	 * @param tableList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	@Override
	public boolean generateDao(Map<String, Object> moduleMap,Map<String, Object> daoMap, List<Table> tableList) throws Exception {
		boolean result = false;
		// 获取当前entityMap配置信息
		Map<String,Object> entityMap = ServiceUtil.getEntityMapByConfigRef(moduleMap==null?null:(List<Map<String, Object>>)moduleMap.get(Constants.MODULE_ENTYTIS),daoMap==null?null:daoMap.get(Constants.MODULE_DAOS_DAO_REF).toString());
		// 获取当前table实体
		Table table = ServiceUtil.getTableByCurrentMoudleRef(moduleMap==null?null:(List<Map<String, Object>>)moduleMap.get(Constants.MODULE_ENTYTIS),tableList, daoMap.get(Constants.MODULE_DAOS_DAO_REF).toString());
		if(CommonUtil.isNotBlankObj(moduleMap,daoMap,entityMap,table,daoMap.get(Constants.MODULE_DAOS_DAO_ID),daoMap.get(Constants.MODULE_DAOS_DAO_REF)) 
				&& CommonUtil.isNotBlank(daoMap.get(Constants.MODULE_DAOS_DAO_ID).toString(),daoMap.get(Constants.MODULE_DAOS_DAO_REF).toString())){
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString();
			String packageStr = moduleMap.get(Constants.MODULE_NAMESPACE).toString() + "." + moduleMap.get(Constants.MODULE_ID).toString() + "." + moduleMap.get(Constants.MODULE_DAOS_ID).toString();
			String packagePath = packageStr.replace(".", "/");
			String fileName = daoMap.get(Constants.MODULE_DAOS_DAO_ID).toString() + ".java";
			String classPath = rootPath + packagePath + "/" + fileName;
			
		    File file =new File(rootPath + packagePath);    
		    //如果文件夹不存在则创建
		    if(!file.exists() && !file.isDirectory()){       
		        file.mkdirs();    
		    }
		    log.debug(DaoServiceImpl.class.getName()+"自动生成文件：" + classPath);
			Writer writer = new FileWriter(classPath, false);	
			StringBuffer sb = new StringBuffer();
			// 1.生成包信息
			sb.append(CommonUtil.linefeed("package "+ packageStr +";") + CommonUtil.linefeed(null) + CommonUtil.linefeed(null));	
			// 2.生成类javadoc信息
			sb.append(CommonUtil.linefeed("/**"));
			sb.append(CommonUtil.linefeed(" * "+moduleMap.get(Constants.MODULE_NAME)+"_"+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT) + moduleMap.get(Constants.MODULE_DAOS_NAME)));	
			sb.append(CommonUtil.linefeed(" * "));	
			sb.append(CommonUtil.linefeed(" * @author "+moduleMap.get(Constants.MODULE_AUTHOR)));	
			sb.append(CommonUtil.linefeed(" * @version V1.0 "+DateUtils.format(new Date(), "yyyy-MM-dd")+" 初版"));	
			sb.append(CommonUtil.linefeed(" */"));
			// 3.生成java类主体信息
			sb.append(CommonUtil.linefeed("@Repository(\""+daoMap.get(Constants.MODULE_DAOS_DAO_ID).toString().substring(0,1).toLowerCase()+daoMap.get(Constants.MODULE_DAOS_DAO_ID).toString().substring(1)+"\")"));	
			sb.append(CommonUtil.linefeed("public interface "+daoMap.get(Constants.MODULE_DAOS_DAO_ID)+" extends PagingAndSortingRepository<"+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+","+ServiceUtil.getPrimaryKeyColumn(table).getSimpleJavaType()+">,JpaSpecificationExecutor<"+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+"> {"));	
			
			List<Map<String,Object>> methodMapList = (List<Map<String,Object>>)daoMap.get(Constants.MODULE_DAOS_DAO_METHODS);
			if(CommonUtil.isNotBlankList(methodMapList)){
				for(Map<String,Object> methodMap:methodMapList ){
					String type = methodMap.get(Constants.MODULE_DAOS_DAO_METHODS_METHOD_TYPE)==null?null:methodMap.get(Constants.MODULE_DAOS_DAO_METHODS_METHOD_TYPE).toString();
					String by = methodMap.get(Constants.MODULE_DAOS_DAO_METHODS_METHOD_BY)==null?null:methodMap.get(Constants.MODULE_DAOS_DAO_METHODS_METHOD_BY).toString().toString();
					String column = methodMap.get(Constants.MODULE_DAOS_DAO_METHODS_METHOD_COLUMN)==null?null:methodMap.get(Constants.MODULE_DAOS_DAO_METHODS_METHOD_COLUMN).toString().toString();
					if(CommonUtil.isNotBlank(type) && "query".equals(type) && CommonUtil.isNotBlank(by)){
						if("Primary".equals(by)){
							sb.append(CommonUtil.linefeed("    /**"));
							sb.append(CommonUtil.linefeed("     * 主键查询：通过"+ServiceUtil.getPrimaryKeyColumn(table).getSimpleField()+"查询\""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"实体对象"));
							sb.append(CommonUtil.linefeed("     * "));
							sb.append(CommonUtil.linefeed("     * @param "+ServiceUtil.getPrimaryKeyColumn(table).getSimpleField()+" \""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"主键"));
							sb.append(CommonUtil.linefeed("     * @return "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" \""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"实体对象"));
							sb.append(CommonUtil.linefeed("     */"));
							sb.append(CommonUtil.linefeed("    @Query(value = \"FROM "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().substring(0,1).toLowerCase()+" WHERE "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().substring(0,1).toLowerCase()+"."+ServiceUtil.getPrimaryKeyColumn(table).getSimpleField()+"=:"+ServiceUtil.getPrimaryKeyColumn(table).getSimpleField()+"\")"));
							sb.append(CommonUtil.linefeed("    public "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" findByPrimaryKey(@Param(\""+ServiceUtil.getPrimaryKeyColumn(table).getSimpleField()+"\") "+ServiceUtil.getPrimaryKeyColumn(table).getSimpleJavaType()+" "+ServiceUtil.getPrimaryKeyColumn(table).getSimpleField()+") throws Exception;"));
							sb.append(CommonUtil.linefeed(null));
						}else if("Foreign".equals(by) && CommonUtil.isNotBlank(column)){
							// 需要保证配置文件中配置的外键是存在的,否则不予生成方法
							if(ServiceUtil.isForeignKeyColumn(table, column)){
								sb = sb.append(this.generateFindByForeignColumn(entityMap, table, column));
							}
						}else if("Unique".equals(by) && CommonUtil.isNotBlank(column)){
							if(ServiceUtil.isForeignKeyColumn(table, column)){
								sb = sb.append(this.generateFindByForeignColumn(entityMap, table, column));
							}else{
								sb = sb.append(this.generateFindBySimpleColumn(entityMap, table, column));
							}
						}else if("Common".equals(by) && CommonUtil.isNotBlank(column)){
							if(ServiceUtil.isForeignKeyColumn(table, column)){
								sb = sb.append(this.generateFindByForeignColumn(entityMap, table, column));
							}else{
								sb = sb.append(this.generateFindListBySimpleColumn(entityMap, table, column));
							}
						}
					}
				}
			}
			
			// 4.添加导入包信息_注解导入包
			String packageSb = CommonUtil.linefeed("package "+ packageStr +";") + CommonUtil.linefeed(null);
			int packageEndIndex = sb.indexOf(packageSb) + packageSb.length();
			if(sb.indexOf("@Repository")>0){
				sb.insert(packageEndIndex, CommonUtil.linefeed("import org.springframework.stereotype.Repository;"));
			}
			if(sb.indexOf("@Query")>0){
				sb.insert(packageEndIndex, CommonUtil.linefeed("import org.springframework.data.jpa.repository.Query;"));
			}
			if(sb.indexOf("PagingAndSortingRepository")>0){
				sb.insert(packageEndIndex, CommonUtil.linefeed("import org.springframework.data.repository.PagingAndSortingRepository;"));
			}
			if(sb.indexOf("JpaSpecificationExecutor")>0){
				sb.insert(packageEndIndex, CommonUtil.linefeed("import org.springframework.data.jpa.repository.JpaSpecificationExecutor;"));
			}
			if(sb.indexOf("@Param")>0){
				sb.insert(packageEndIndex, CommonUtil.linefeed("import org.springframework.data.repository.query.Param;"));
			}
			
			// 添加导入包信息_实体属性相关导入包
			String importPackage = "import " + moduleMap.get(Constants.MODULE_NAMESPACE).toString() + "." + moduleMap.get(Constants.MODULE_ID).toString() + "." + moduleMap.get(Constants.MODULE_ENTITYS_ID).toString()+"."+daoMap.get(Constants.MODULE_DAOS_DAO_REF) + ";";
			sb.insert(packageEndIndex, CommonUtil.linefeed(importPackage));
			
			// 添加导入包信息_List接口导入包
			if(sb.indexOf("List")>0){
				sb.insert(packageEndIndex, CommonUtil.linefeed("import java.util.List;"));
			}
			
			
			sb.append(CommonUtil.linefeed("}"));
			writer.append(sb);
			writer.flush();		
			writer.close();
			result = true;
		}
		return result;
	}

	/**
	 * 生成外键查询方法
	 * @param entityMap
	 * @param table
	 * @param column
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private StringBuffer generateFindByForeignColumn(Map<String, Object> entityMap, Table table, String column) {
		StringBuffer sb = new StringBuffer();
		TableColumn foreignColumn = ServiceUtil.getForeignColumn(table, column);
		TableForeignkey foreignkeyObj = ServiceUtil.getForeignkeyObj(table, column);
		if(CommonUtil.isNotBlankObj(foreignColumn,foreignkeyObj)){
			if(foreignColumn.getForeignAssociation().equals(Constants.FOREIGN_TYPE_ONE_TO_ONE)){
				sb.append(CommonUtil.linefeed("    /**"));
				sb.append(CommonUtil.linefeed("     * 外键查询：通过"+foreignColumn.getSimpleField()+"查询\""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"实体对象"));
				sb.append(CommonUtil.linefeed("     * "));
				sb.append(CommonUtil.linefeed("     * @param "+foreignColumn.getSimpleField()+" "+foreignColumn.getComments()));
				sb.append(CommonUtil.linefeed("     * @return "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" \""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"实体对象"));
				sb.append(CommonUtil.linefeed("     */"));
				sb.append(CommonUtil.linefeed("     @Query(value = \"FROM "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().substring(0,1).toLowerCase()+" WHERE "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().substring(0,1).toLowerCase()+"."+foreignColumn.getForeignField()+"."+CommonUtil.parseToSmallHumpStr(foreignkeyObj.getForeignColumnName(), true)+"=:"+foreignColumn.getSimpleField()+"\")"));
				sb.append(CommonUtil.linefeed("     public "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" findBy"+foreignColumn.getSimpleField().substring(0,1).toUpperCase()+(foreignColumn.getSimpleField().length()>1?foreignColumn.getSimpleField().substring(1):"")+"(@Param(\""+foreignColumn.getSimpleField()+"\") "+foreignColumn.getSimpleJavaType()+" "+foreignColumn.getSimpleField()+") throws Exception;"));
			}else if(foreignColumn.getForeignAssociation().equals(Constants.FOREIGN_TYPE_MANY_TO_ONE)){
				sb.append(CommonUtil.linefeed("    /**"));
				sb.append(CommonUtil.linefeed("     * 外键查询：通过"+foreignColumn.getSimpleField()+"查询\""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"实体对象集合"));
				sb.append(CommonUtil.linefeed("     * "));
				sb.append(CommonUtil.linefeed("     * @param "+foreignColumn.getSimpleField()+" "+foreignColumn.getComments()));
				sb.append(CommonUtil.linefeed("     * @return List<"+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+"> \""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"实体对象集合"));
				sb.append(CommonUtil.linefeed("     */"));
				sb.append(CommonUtil.linefeed("     @Query(value = \"FROM "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().substring(0,1).toLowerCase()+" WHERE "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().substring(0,1).toLowerCase()+"."+foreignColumn.getForeignField()+"."+CommonUtil.parseToSmallHumpStr(foreignkeyObj.getForeignColumnName(), true)+"=:"+foreignColumn.getSimpleField()+"\")"));
				sb.append(CommonUtil.linefeed("     public List<"+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+"> findListBy"+foreignColumn.getSimpleField().substring(0,1).toUpperCase()+(foreignColumn.getSimpleField().length()>1?foreignColumn.getSimpleField().substring(1):"")+"(@Param(\""+foreignColumn.getSimpleField()+"\") "+foreignColumn.getSimpleJavaType()+" "+foreignColumn.getSimpleField()+") throws Exception;"));
			}
			sb.append(CommonUtil.linefeed(null));
		}
		return sb;
	}

	/**
	 * 生成普通字段查询方法 返回实体对象类型
	 * @param entityMap
	 * @param table
	 * @param column
	 * @return
	 */
	private StringBuffer generateFindBySimpleColumn(Map<String, Object> entityMap, Table table, String column) {
		StringBuffer sb = new StringBuffer();
		TableColumn tableColumn = ServiceUtil.getSimpleColumn(table, column);
		sb.append(CommonUtil.linefeed("    /**"));
		sb.append(CommonUtil.linefeed("     * 普通字段查询：通过"+tableColumn.getSimpleField()+"查询\""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"实体对象"));
		sb.append(CommonUtil.linefeed("     * "));
		sb.append(CommonUtil.linefeed("     * @param "+tableColumn.getSimpleField()+" \""+tableColumn.getComments()));
		sb.append(CommonUtil.linefeed("     * @return "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" \""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"实体对象"));
		sb.append(CommonUtil.linefeed("     */"));
		sb.append(CommonUtil.linefeed("    @Query(value = \"FROM "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().substring(0,1).toLowerCase()+" WHERE "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().substring(0,1).toLowerCase()+"."+tableColumn.getSimpleField()+"=:"+tableColumn.getSimpleField()+"\")"));
		sb.append(CommonUtil.linefeed("    public "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" findBy"+tableColumn.getSimpleField().substring(0, 1).toUpperCase()+tableColumn.getSimpleField().substring(1)+"(@Param(\""+tableColumn.getSimpleField()+"\") "+tableColumn.getSimpleJavaType()+" "+tableColumn.getSimpleField()+") throws Exception;"));
		sb.append(CommonUtil.linefeed(null));
		return sb;
	}

	/**
	 * 生成普通字段查询方法 返回实体对象集合
	 * @param entityMap
	 * @param table
	 * @param column
	 * @return
	 */
	private StringBuffer generateFindListBySimpleColumn(Map<String, Object> entityMap, Table table, String column) {
		StringBuffer sb = new StringBuffer();
		TableColumn tableColumn = ServiceUtil.getSimpleColumn(table, column);
		sb.append(CommonUtil.linefeed("    /**"));
		sb.append(CommonUtil.linefeed("     * 普通字段查询：通过"+tableColumn.getSimpleField()+"查询\""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"实体对象"));
		sb.append(CommonUtil.linefeed("     * "));
		sb.append(CommonUtil.linefeed("     * @param "+tableColumn.getSimpleField()+" "+tableColumn.getComments()));
		sb.append(CommonUtil.linefeed("     * @return "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" \""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT)+"\"实体对象"));
		sb.append(CommonUtil.linefeed("     */"));
		sb.append(CommonUtil.linefeed("    @Query(value = \"FROM "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().substring(0,1).toLowerCase()+" WHERE "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString().substring(0,1).toLowerCase()+"."+tableColumn.getSimpleField()+"=:"+tableColumn.getSimpleField()+"\")"));
		sb.append(CommonUtil.linefeed("    public List<"+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+"> findListBy"+tableColumn.getSimpleField().substring(0, 1).toUpperCase()+tableColumn.getSimpleField().substring(1)+"(@Param(\""+tableColumn.getSimpleField()+"\") "+tableColumn.getSimpleJavaType()+" "+tableColumn.getSimpleField()+") throws Exception;"));
		sb.append(CommonUtil.linefeed(null));
		return sb;
	}

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
