package jinhetech.oracle.generation.entity.entity;

import java.util.List;


public class Table {

	// 库表名
	private String tableName;
	
	// 库表注释
	private String tableComment;
	
	// 库表字段集合
	private List<TableColumn> columnsList;
	
	// 库表主键
	private String primarykey;
	
	// 库表外键集合
	private List<TableForeignkey> foreignkeysList;
	
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public List<TableColumn> getColumnsList() {
		return columnsList;
	}

	public void setColumnsList(List<TableColumn> columnsList) {
		this.columnsList = columnsList;
	}

	public String getPrimarykey() {
		return primarykey;
	}

	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}

	public List<TableForeignkey> getForeignkeysList() {
		return foreignkeysList;
	}

	public void setForeignkeysList(List<TableForeignkey> foreignkeysList) {
		this.foreignkeysList = foreignkeysList;
	}
}
