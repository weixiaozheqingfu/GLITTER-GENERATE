package jinhetech.oracle.generation.entity.entity;

public class TableColumn {

	// 库表名
	private String tableName;

	// 字段名
	private String columnName;

	// 字段类型
	private String dataType;

	// 字段注释
	private String comments;
	
	// 字段是否为8种简单数据类型
	private boolean isSimpleJavaType;
	
	// 字段对应的简单java数据类型
	private String simpleJavaType;
	
	// 字段对应的简单java数据类型的字段名称
	private String simpleField;
	
	// 字段对应的外键引用java数据类型
	private String foreignJavaType;
	
	// 字段对应的外键引用java数据类型导入包
	private String foreignJavaTypePackage;
	
	// 字段对应的外键引用java数据类型的字段名称
	private String foreignField;
	
	// 字段对应的外键引用java数据类型关联关系关系(Constants.FOREIGN_TYPE_ONE_TO_ONE/Constants.FOREIGN_TYPE_MANY_TO_ONE)
	private String foreignAssociation;
	

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isSimpleJavaType() {
		return isSimpleJavaType;
	}

	public void setSimpleJavaType(boolean isSimpleJavaType) {
		this.isSimpleJavaType = isSimpleJavaType;
	}

	public String getSimpleJavaType() {
		return simpleJavaType;
	}

	public void setSimpleJavaType(String simpleJavaType) {
		this.simpleJavaType = simpleJavaType;
	}

	public String getSimpleField() {
		return simpleField;
	}

	public void setSimpleField(String simpleField) {
		this.simpleField = simpleField;
	}
	
	public String getForeignJavaType() {
		return foreignJavaType;
	}

	public void setForeignJavaType(String foreignJavaType) {
		this.foreignJavaType = foreignJavaType;
	}

	public String getForeignField() {
		return foreignField;
	}

	public void setForeignField(String foreignField) {
		this.foreignField = foreignField;
	}
	
	public String getForeignAssociation() {
		return foreignAssociation;
	}

	public void setForeignAssociation(String foreignAssociation) {
		this.foreignAssociation = foreignAssociation;
	}

	public String getForeignJavaTypePackage() {
		return foreignJavaTypePackage;
	}

	public void setForeignJavaTypePackage(String foreignJavaTypePackage) {
		this.foreignJavaTypePackage = foreignJavaTypePackage;
	}

}
