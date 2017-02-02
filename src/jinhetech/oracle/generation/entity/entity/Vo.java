package jinhetech.oracle.generation.entity.entity;

import java.util.List;

public class Vo {

	// Vo的id
	private String id;
	// Vo所属包
	private String packageName;
	// Vo注释
	private String name;
	// Vo的字段信息集合
	private List<VoField> voFieldList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<VoField> getVoFieldList() {
		return voFieldList;
	}
	public void setVoFieldList(List<VoField> voFieldList) {
		this.voFieldList = voFieldList;
	}
}
