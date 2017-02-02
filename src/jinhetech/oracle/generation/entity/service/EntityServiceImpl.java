package jinhetech.oracle.generation.entity.service;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jinhetech.oracle.generation.common.CommonUtil;
import jinhetech.oracle.generation.common.Constants;
import jinhetech.oracle.generation.common.DateUtils;
import jinhetech.oracle.generation.entity.dao.EntityDao;
import jinhetech.oracle.generation.entity.entity.Table;
import jinhetech.oracle.generation.entity.entity.TableColumn;
import jinhetech.oracle.generation.entity.entity.TableForeignkey;
import jinhetech.oracle.generation.entity.utils.ServiceUtil;

import org.apache.log4j.Logger;

public class EntityServiceImpl implements EntityService {
	
	private EntityDao entityDao;
	
	private static Logger log = Logger.getLogger(EntityServiceImpl.class);
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	public EntityServiceImpl(){
		EntityDao entityDao = new EntityDao();
		this.setEntityDao(entityDao);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public Table getTable(Map<String,Object> entityMap,String tableName) throws Exception {
		Table table = new Table();;
		if(CommonUtil.isNotBlank(tableName)){
			// 获取表注释
			StringBuffer sbSql0 = new StringBuffer();
			List<Object> argsList0 = new ArrayList<Object>();
			Object[] args0 = null;
			sbSql0.append(" select ut.table_name,utc.table_type,utc.comments from user_tables ut,user_tab_comments utc \t\r "+
						  " where ut.table_name = utc.table_name                      				                   \t\r "+
					      " and ut.table_name=?                                                                        \t\r "
						
			);    
			argsList0.add(tableName);
			if(argsList0.size()>0){
				args0 = argsList0.toArray();
			}
			List<Map<String,Object>> resultMapList0 = entityDao.getMapList(sbSql0.toString(), args0);
			if(CommonUtil.isNotBlankList(resultMapList0)){
				for(Map<String,Object> map:resultMapList0){
					table.setTableName(map.get("table_name").toString());
					table.setTableComment(map.get("comments").toString());
				}
			}
			
			// 获取表中字段
			StringBuffer sbSql1 = new StringBuffer();
			List<Object> argsList1 = new ArrayList<Object>();
			Object[] args1 = null;
			sbSql1.append(" select utc.table_name,utc.column_name, 										\t\r "+
						  " case when utc.data_type='NUMBER' and utc.DATA_PRECISION is not null 		\t\r " +
						  "		then  utc.data_type||'('||utc.DATA_PRECISION||','||utc.DATA_SCALE||')'  \t\r " +
						  "		else utc.data_type end 													\t\r " +
						  "		as data_type, 															\t\r "+
						  " ucc.comments from user_tab_columns utc,user_col_comments ucc 				\t\r "+
						  " where utc.table_name = ucc.table_name                                       \t\r "+
						  " and utc.column_name = ucc.column_name                                       \t\r "+
						  " and utc.table_name = ?                                                      \t\r "
			);    
			argsList1.add(tableName);
			if(argsList1.size()>0){
				args1 = argsList1.toArray();
			}
			List<Map<String,Object>> resultMapList1 = entityDao.getMapList(sbSql1.toString(), args1);
			if(CommonUtil.isNotBlankList(resultMapList1)){
				List<TableColumn> columnsList = new ArrayList<TableColumn>();
				TableColumn column = null;
				for(Map<String,Object> map:resultMapList1){
					column = new TableColumn();
					column.setTableName(map.get("table_name").toString());
					column.setColumnName(map.get("column_name").toString());
					column.setDataType(map.get("data_type").toString());
					column.setComments(map.get("comments").toString());
					columnsList.add(column);
				}
				table.setColumnsList(columnsList);
			}
			
			// 获取主键
			StringBuffer sbSql2 = new StringBuffer();
			List<Object> argsList2 = new ArrayList<Object>();
			Object[] args2 = null;
			sbSql2.append(" select col.column_name as primarykey from user_constraints con, user_cons_columns col \t\r "+
						  " where con.constraint_name = col.constraint_name                      				  \t\r "+
						  " and con.constraint_type='P'                                          				  \t\r "+
						  " and col.table_name = ?                                              				  \t\r "
			);    
			argsList2.add(tableName);
			if(argsList2.size()>0){
				args2 = argsList2.toArray();
			}
			List<Map<String,Object>> resultMapList2 = entityDao.getMapList(sbSql2.toString(), args2);
			if(CommonUtil.isNotBlankList(resultMapList2)){
				for(Map<String,Object> map:resultMapList2){
					table.setPrimarykey(map.get("primarykey").toString());
				}
			}
			
			// 获取外键集合 TableForeignkey只是中间过程表,最终不要使用这张表做任何判断。而是使用TableColumn表。
			StringBuffer sbSql3 = new StringBuffer();
			List<Object> argsList3 = new ArrayList<Object>();
			Object[] args3 = null;
			sbSql3.append(" select uc_z.table_name,uc_z.constraint_name,ucc_z.column_name,ucc_y.table_name as foreign_table_name,ucc_y.column_name as foreign_column_name \t\r "+
						  " from user_constraints uc_z,USER_CONS_COLUMNS ucc_z,USER_CONS_COLUMNS ucc_y                      						 					  \t\r "+
						  " where  uc_z.r_constraint_name = ucc_y.constraint_name                                          							 					  \t\r "+
						  " and uc_z.table_name = ucc_z.table_name                                               									 					  \t\r "+
						  " and uc_z.constraint_name = ucc_z.constraint_name                                               							 					  \t\r "+
						  " and uc_z.owner = 'GJJY'                                               													 					  \t\r "+
						  " and uc_z.status = 'ENABLED'                                               												 					  \t\r "+
						  " and uc_z.constraint_type = 'R'                                              											 					  \t\r "+
						  " and uc_z.table_name = ?                                              													 					  \t\r "
			);    
			argsList3.add(tableName);
			if(argsList3.size()>0){
				args3 = argsList3.toArray();
			}
			List<Map<String,Object>> resultMapList3 = entityDao.getMapList(sbSql3.toString(), args3);
			if(CommonUtil.isNotBlankList(resultMapList3)){
				List<TableForeignkey> foreignkeysList = new ArrayList<TableForeignkey>();
				TableForeignkey foreignkey = null;
				for(Map<String,Object> map:resultMapList3){
					foreignkey = new TableForeignkey();
					foreignkey.setTableName(map.get("table_name").toString());
					foreignkey.setConstraintName(map.get("constraint_name").toString());
					foreignkey.setColumnName(map.get("column_name").toString());
					foreignkey.setForeignTableName(map.get("foreign_table_name").toString());
					foreignkey.setForeignColumnName(map.get("foreign_column_name").toString());
					
					if(CommonUtil.isNotBlank(foreignkey.getConstraintName())){
						if(foreignkey.getConstraintName().contains(Constants.FOREIGN_TYPE_ONE_TO_ONE)){
							foreignkey.setForeignAssociation(Constants.FOREIGN_TYPE_ONE_TO_ONE);
						}else if(foreignkey.getConstraintName().contains(Constants.FOREIGN_TYPE_MANY_TO_ONE)){
							foreignkey.setForeignAssociation(Constants.FOREIGN_TYPE_MANY_TO_ONE);
						}
					}
					
					// 数据库配置好了外键,不代表配置文件一定就对应的配置了外键导入包,总之使用的时候一定是取交集
					if(CommonUtil.isNotBlank(foreignkey.getForeignTableName())){
						String compareStr = foreignkey.getForeignTableName().substring(foreignkey.getForeignTableName().lastIndexOf("_")+1).toUpperCase();
						List<Map<String,Object>> foreignMapList = (List<Map<String,Object>>)entityMap.get(Constants.MODULE_ENTITYS_ENTITY_FOREIGNS);
						if(CommonUtil.isNotBlankList(foreignMapList)){
							for(Map<String,Object> foreignMap:foreignMapList){
								String foreignClass = foreignMap.get(Constants.MODULE_ENTITYS_ENTITY_FOREIGNS_FOREIGN_CLASS).toString();
								String foreignJavaType = "";
								if(foreignClass.endsWith(";")){
									foreignJavaType = foreignClass.substring(foreignClass.lastIndexOf(".")+1,foreignClass.length()-1);
								}else{
									foreignJavaType = foreignClass.substring(foreignClass.lastIndexOf(".")+1);
								}
								if(foreignJavaType.toUpperCase().contains(compareStr)){
									// 取交集的关键是看foreignPackage值是否为null
									foreignkey.setForeignJavaTypePackage(foreignClass);
									foreignkey.setForeignJavaType(foreignJavaType);
								}
							}
						}
					}
					foreignkeysList.add(foreignkey);
				}
				table.setForeignkeysList(foreignkeysList);
			}
			
			// 将外键相关关系放入TableColumn表相关字段中
			if(CommonUtil.isNotBlankList(table.getColumnsList())){
				for(TableColumn tableColumn:table.getColumnsList()){
					TableForeignkey foreignkeyObj = ServiceUtil.getForeignkeyObj(table, tableColumn.getColumnName());
					// this.isForeignKey(table, tableColumn.getColumnName())判断数据库中配置的是否是外键
					// foreignkeyObj.getForeignJavaTypePackage()!=null 判断配置文件中配置的是否是外键
					// 两者同时存在,才按照外键引用类型来处理,否则一律按照普通的简单类型来处理
					// 1.判断是否为数据库外键;2.如果条件一满足,则说明foreignkeyObj一定是存在的,因为foreignkeyObj就代表数据库外键,继续判断getForeignJavaTypePackage是否为null,如果是null说明config中没有配置,还是不能当做外键处理。
					if(ServiceUtil.isForeignKey(table, tableColumn.getColumnName()) && foreignkeyObj.getForeignJavaTypePackage()!=null){
						// 程序最终判断是否为外键的标识
						tableColumn.setSimpleJavaType(false);
						tableColumn.setForeignJavaType(foreignkeyObj.getForeignJavaType());
						tableColumn.setForeignJavaTypePackage(foreignkeyObj.getForeignJavaTypePackage());
						tableColumn.setForeignAssociation(foreignkeyObj.getForeignAssociation());
						tableColumn.setForeignField(CommonUtil.parseToSmallHumpStr(tableColumn.getColumnName(),true)+tableColumn.getForeignJavaType());
					}else{
						tableColumn.setSimpleJavaType(true);
					}
					if(tableColumn.getDataType().equals("VARCHAR2") || tableColumn.getDataType().equals("NVARCHAR2") || tableColumn.getDataType().equals("CHAR") || tableColumn.getDataType().equals("LONG") ){
						tableColumn.setSimpleJavaType("String");
					}else if(tableColumn.getDataType().equals("NUMBER")){
						tableColumn.setSimpleJavaType("Integer");
					}else if((tableColumn.getDataType().contains("NUMBER") && tableColumn.getDataType().length()>5) || tableColumn.getDataType().equals("LONG") ){
						tableColumn.setSimpleJavaType("Double");
					}else if(tableColumn.getDataType().equals("DATE")){
						tableColumn.setSimpleJavaType("Date");
					}else{
						tableColumn.setSimpleJavaType("String");
					}
					tableColumn.setSimpleField(CommonUtil.parseToSmallHumpStr(tableColumn.getColumnName(),true));
				}
			}
		}
		return table;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean generateEntity(Map<String, Object> moduleMap,Map<String, Object> entityMap, Table table) throws Exception {
		boolean result = false;
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString();
		String packageStr = moduleMap.get(Constants.MODULE_NAMESPACE).toString() + "." + moduleMap.get(Constants.MODULE_ID).toString() + "." + moduleMap.get(Constants.MODULE_ENTITYS_ID).toString();
		String packagePath = packageStr.replace(".", "/");
		String fileName = entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID).toString() + ".java";
		String classPath = rootPath + packagePath + "/" + fileName;
		
	    File file =new File(rootPath + packagePath);    
	    //如果文件夹不存在则创建    
	    if(!file.exists() && !file.isDirectory()){       
	        file.mkdirs();    
	    }
	    log.debug(EntityServiceImpl.class.getName()+"自动生成文件：" + classPath);
		Writer writer = new FileWriter(classPath, false);	
		StringBuffer sb = new StringBuffer();
		// 1.生成包信息
		sb.append(CommonUtil.linefeed("package "+ packageStr +";") + CommonUtil.linefeed(null) + CommonUtil.linefeed(null));	
		// 2.生成类javadoc信息
		sb.append(CommonUtil.linefeed("/**"));	
		sb.append(CommonUtil.linefeed(" * "+moduleMap.get(Constants.MODULE_NAME)+"_"+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_COMMENT) + moduleMap.get(Constants.MODULE_ENTITYS_NAME)));	
		sb.append(CommonUtil.linefeed(" * "));	
		sb.append(CommonUtil.linefeed(" * @author "+moduleMap.get(Constants.MODULE_AUTHOR)));	
		sb.append(CommonUtil.linefeed(" * @version V1.0 "+DateUtils.format(new Date(), "yyyy-MM-dd")+" 初版"));	
		sb.append(CommonUtil.linefeed(" */"));
		// 3.生成java类主体信息
		sb.append(CommonUtil.linefeed("@Entity"));	
		sb.append(CommonUtil.linefeed("@Table(name = \""+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_TABLENAME)+"\")"));	
		sb.append(CommonUtil.linefeed("public class "+entityMap.get(Constants.MODULE_ENTITYS_ENTITY_ID)+" implements Serializable {"));	
		sb.append(CommonUtil.linefeed("    private static final long serialVersionUID = 1L;") + CommonUtil.linefeed(null));
		// 4.生成字段信息
		if(CommonUtil.isNotBlankObj(table) && CommonUtil.isNotBlankList(table.getColumnsList())){
			// 循环每一个字段
			for(TableColumn tableColumn:table.getColumnsList()){
				sb.append(CommonUtil.linefeed("    // "+tableColumn.getComments()));
				if(tableColumn.isSimpleJavaType()){
					if(ServiceUtil.isPrimaryKey(table, tableColumn.getColumnName())){
						sb.append(CommonUtil.linefeed("    @Id"));
					}
					if(tableColumn.getSimpleJavaType().equals("Date")){
						sb.append(CommonUtil.linefeed("    @DateTimeFormat(pattern=\"yyyy-MM-dd\")"));
					}
					sb.append(CommonUtil.linefeed("    private "+tableColumn.getSimpleJavaType() + " " + tableColumn.getSimpleField() + ";"));
				}else{
					sb.append(CommonUtil.linefeed("    private "+tableColumn.getForeignJavaType() + " " + tableColumn.getForeignField() + ";"));	
					
				}
			}
		}
		sb.append(CommonUtil.linefeed(null));
		// 5.生成getter和setter方法
		if(CommonUtil.isNotBlankObj(table) && CommonUtil.isNotBlankList(table.getColumnsList())){
			// 循环每一个字段
			for(TableColumn tableColumn:table.getColumnsList()){
				if(tableColumn.isSimpleJavaType()){
					if(ServiceUtil.isPrimaryKey(table, tableColumn.getColumnName())){
						sb.append(CommonUtil.linefeed("    @Id"));
						sb.append(CommonUtil.linefeed("    @GeneratedValue(generator = \"system-uuid\")"));
						sb.append(CommonUtil.linefeed("    @GenericGenerator(name = \"system-uuid\", strategy = \"uuid\")"));
					}
					sb.append(CommonUtil.linefeed("    public "+tableColumn.getSimpleJavaType()+" get"+tableColumn.getSimpleField().substring(0,1).toUpperCase()+(tableColumn.getSimpleField().length()>1?tableColumn.getSimpleField().substring(1):"")+"() {"));
					sb.append(CommonUtil.linefeed("        return "+tableColumn.getSimpleField()+";"));
					sb.append(CommonUtil.linefeed("    }"));
					sb.append(CommonUtil.linefeed(null));
					sb.append(CommonUtil.linefeed("    public void set"+tableColumn.getSimpleField().substring(0,1).toUpperCase()+(tableColumn.getSimpleField().length()>1?tableColumn.getSimpleField().substring(1):"")+"("+tableColumn.getSimpleJavaType()+" "+tableColumn.getSimpleField()+") {"));
					sb.append(CommonUtil.linefeed("        this."+tableColumn.getSimpleField()+" = "+tableColumn.getSimpleField()+";"));
					sb.append(CommonUtil.linefeed("    }"));
					sb.append(CommonUtil.linefeed(null));
				}else{
					String association = "";
					if(tableColumn.getForeignAssociation().equals(Constants.FOREIGN_TYPE_MANY_TO_ONE)){
						association = Constants.FOREIGN_TYPE_MANY_TO_ONE_ANNOTATION;
					}else if(tableColumn.getForeignAssociation().equals(Constants.FOREIGN_TYPE_ONE_TO_ONE)){
						association = Constants.FOREIGN_TYPE_ONE_TO_ONE_ANNOTATION;
					}
					if(CommonUtil.isNotBlank(association)){
						sb.append(CommonUtil.linefeed("    "+association+"(fetch = FetchType.LAZY)"));
						sb.append(CommonUtil.linefeed("    @JoinColumn(name = \""+tableColumn.getColumnName()+"\")"));
						sb.append(CommonUtil.linefeed("    public "+tableColumn.getForeignJavaType()+" get"+tableColumn.getForeignField().substring(0,1).toUpperCase()+(tableColumn.getForeignField().length()>1?tableColumn.getForeignField().substring(1):"")+"() {"));
						sb.append(CommonUtil.linefeed("        return "+tableColumn.getForeignField()+";"));
						sb.append(CommonUtil.linefeed("    }"));
						sb.append(CommonUtil.linefeed(null));
						sb.append(CommonUtil.linefeed("    public void set"+tableColumn.getForeignField().substring(0,1).toUpperCase()+(tableColumn.getForeignField().length()>1?tableColumn.getForeignField().substring(1):"")+"("+tableColumn.getForeignJavaType()+" "+tableColumn.getForeignField()+") {"));
						sb.append(CommonUtil.linefeed("        this."+tableColumn.getForeignField()+" = "+tableColumn.getForeignField()+";"));
						sb.append(CommonUtil.linefeed("    }"));
						sb.append(CommonUtil.linefeed(null));
					}
				}
			}
		}
		sb.append(CommonUtil.linefeed("}"));
		// 6.添加导入包信息_注解导入包
		String packageSb = CommonUtil.linefeed("package "+ packageStr +";") + CommonUtil.linefeed(null);
		int packageEndIndex = sb.indexOf(packageSb) + packageSb.length();
		if(sb.indexOf("@Entity")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import javax.persistence.Entity;"));
		}
		if(sb.indexOf("@Table")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import javax.persistence.Table;"));
		}
		if(sb.indexOf("FetchType")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import javax.persistence.FetchType;"));
		}
		if(sb.indexOf("@GeneratedValue")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import javax.persistence.GeneratedValue;"));
		}
		if(sb.indexOf("@Id")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import javax.persistence.Id;"));
		}
		if(sb.indexOf("@JoinColumn")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import javax.persistence.JoinColumn;"));
		}
		if(sb.indexOf("@ManyToOne")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import javax.persistence.ManyToOne;"));
		}
		if(sb.indexOf("@OneToMany")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import javax.persistence.OneToMany;"));
		}
		if(sb.indexOf("@OneToOne")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import javax.persistence.OneToOne;"));
		}
		if(sb.indexOf("@OrderBy")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import javax.persistence.OrderBy;"));
		}
		if(sb.indexOf("@DateTimeFormat")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import org.springframework.format.annotation.DateTimeFormat;"));
		}
		if(sb.indexOf("@GenericGenerator")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import org.hibernate.annotations.GenericGenerator;"));
		}
		// 添加导入包信息_简单类型相关导入包
		if(CommonUtil.isNotBlankObj(table) && CommonUtil.isNotBlankList(table.getColumnsList())){
			for(TableColumn tableColumn:table.getColumnsList()){
				if(tableColumn.isSimpleJavaType()){
					if(tableColumn.getSimpleJavaType().equals("Date")){
						sb.insert(packageEndIndex, CommonUtil.linefeed("import java.util.Date;"));
						break;
					}
				}
			}
		}
		// 添加导入包信息_实体属性相关导入包
		List<Map<String,Object>> foreignMapList = (List<Map<String,Object>>)entityMap.get(Constants.MODULE_ENTITYS_ENTITY_FOREIGNS);
		if(CommonUtil.isNotBlankList(foreignMapList)){
			for(Map<String,Object> foreignMap:foreignMapList){
				String foreignClass = foreignMap.get(Constants.MODULE_ENTITYS_ENTITY_FOREIGNS_FOREIGN_CLASS).toString();
				if(!foreignClass.endsWith(";")){
					foreignClass += ";";
				}
				String foreignPackage = "import "+foreignClass;
				sb.insert(packageEndIndex, CommonUtil.linefeed(foreignPackage));
			}
		}
		// 添加导入包信息_序列化接口导入包
		if(sb.indexOf("Serializable")>0){
			sb.insert(packageEndIndex, CommonUtil.linefeed("import java.io.Serializable;"));
		}
		writer.append(sb);
		writer.flush();		
		writer.close();
		result = true;
		return result;
	}

}
