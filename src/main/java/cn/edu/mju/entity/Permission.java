package cn.edu.mju.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Permission{
	
	private Integer id;
	private String name;
	private String url;
	private Integer pid;
	private String icon;
	private boolean open = true;
	private boolean checked = false;
	private List<Permission> children = new ArrayList<Permission>();

	public Permission(String name, String url, Integer pid) {
		this.name = name;
		this.url = url;
		this.pid = pid;
	}



	public Permission(Integer id, String name, String url, Integer pid) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.pid = pid;
	}

	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public List<Permission> getChildren() {
		return children;
	}
	public void setChildren(List<Permission> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", url=" + url + ", pid=" + pid + ", open=" + open
				+ ", children=" + children + "]";
	}
	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Permission(Integer id, String name, String url, Integer pid, boolean open, List<Permission> children) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.pid = pid;
		this.open = open;
		this.children = children;
	}

	public Permission(String name, String url, Integer pid, String icon, boolean open, boolean checked, List<Permission> children) {
		this.name = name;
		this.url = url;
		this.pid = pid;
		this.icon = icon;
		this.open = open;
		this.checked = checked;
		this.children = children;
	}

	public Permission(Integer id, String name, String url, Integer pid, String icon) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.pid = pid;
		this.icon = icon;
	}

	public Permission(String name, String url, Integer pid, String icon) {
		this.name = name;
		this.url = url;
		this.pid = pid;
		this.icon = icon;
	}
}
