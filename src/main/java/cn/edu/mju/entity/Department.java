package cn.edu.mju.entity;

import java.util.ArrayList;
import java.util.List;

public class Department{
	
	private Integer id;
	private String name="";
	private Integer state;
	private Integer pid;
	private boolean open = true;
	private boolean checked = false;
	private List<Department> children = new ArrayList<Department>();

	public Department() {
	}

	public Department(Integer id, String name, Integer state, Integer pid) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.pid = pid;
	}

	public Department(String name, Integer state, Integer pid) {
		this.name = name;
		this.state = state;
		this.pid = pid;
	}

	public Department(String name, Integer pid) {
		this.name = name;
		this.pid = pid;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<Department> getChildren() {
		return children;
	}

	public void setChildren(List<Department> children) {
		this.children = children;
	}
}
