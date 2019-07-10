package cn.edu.mju.entity;

/**
 * 员工表
 */
public class Employee {

	private Integer id;
	private String name;
	private String createTime;
	private Department department;
	private Position position;
	private Integer state;

	public Employee() {
	}

	public Employee(Integer id, String name, String createTime) {
		this.id = id;
		this.name = name;
		this.createTime = createTime;
	}



	public Employee(Integer id, String name, String createTime, Department department, Position position, Integer state) {
		this.id = id;
		this.name = name;
		this.createTime = createTime;
		this.department = department;
		this.position = position;
		this.state = state;
	}

	public Employee(Integer id, String name, String createTime, Department department, Position position) {
		this.id = id;
		this.name = name;
		this.createTime = createTime;
		this.department = department;
		this.position = position;
	}


	public Employee(Integer id, String name, Department department, Position position) {
		this.id = id;
		this.name = name;
		this.department = department;
		this.position = position;
	}

	public Employee(String name, String createTime, Department department, Position position) {
		this.name = name;
		this.createTime = createTime;
		this.department = department;
		this.position = position;
	}

	public Department getDepartment() {
		return department;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
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

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", name='" + name + '\'' +
				", createTime='" + createTime + '\'' +
				", department=" + department.getName() +
				", position=" + position.getName() +
				", state=" + state +
				'}';
	}
}
