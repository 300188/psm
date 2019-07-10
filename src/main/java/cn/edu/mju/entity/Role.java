package cn.edu.mju.entity;

public class Role {

	private Integer id;
	private String name;
	private String createTime;

	public Role() {
	}

	public Role(Integer id, String name, String createTime) {
		this.id = id;
		this.name = name;
		this.createTime = createTime;
	}

	public Role(String name, String createTime) {
		this.name = name;
		this.createTime = createTime;
	}

	public Role(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
