package jinhetech.oracle.generation.entity.entity;

public class TableForeignkey {

	// 库表名称
	private String tableName;
	
	// 库表外键名称
	private String constraintName;
	
	// 字段名称
	private String columnName;
	
	// 外键对应的库表名称
	private String foreignTableName;
	
	// 外键对应的字段名称
	private String foreignColumnName;
	
	// 字段对应的外键引用java数据类型
	private String foreignJavaType;
	
	// 字段对应的外键引用java数据类型导入包
	private String foreignJavaTypePackage;
	
	// 字段对应的外键引用java数据类型关联关系关系(Constants.FOREIGN_TYPE_ONE_TO_ONE/Constants.FOREIGN_TYPE_MANY_TO_ONE)
	private String foreignAssociation;
	

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getConstraintName() {
		return constraintName;
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getForeignTableName() {
		return foreignTableName;
	}

	public void setForeignTableName(String foreignTableName) {
		this.foreignTableName = foreignTableName;
	}

	public String getForeignColumnName() {
		return foreignColumnName;
	}

	public void setForeignColumnName(String foreignColumnName) {
		this.foreignColumnName = foreignColumnName;
	}

	public String getForeignJavaType() {
		return foreignJavaType;
	}

	public void setForeignJavaType(String foreignJavaType) {
		this.foreignJavaType = foreignJavaType;
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
