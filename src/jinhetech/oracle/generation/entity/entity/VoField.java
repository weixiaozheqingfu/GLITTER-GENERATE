package jinhetech.oracle.generation.entity.entity;

public class VoField {

	// 所属Vo的Id
	private String voId;
	
	// 属性类型
	private String type;
	
	// 属性引用
	private String ref;
	
	// 属性字段
	private String column;
	
	
	public String getVoId() {
		return voId;
	}
	
	public void setVoId(String voId) {
		this.voId = voId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
	
}
