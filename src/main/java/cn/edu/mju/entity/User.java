package cn.edu.mju.entity;

import java.util.Date;

public class User {
	
	private Integer id;
	private String username;
	private String loginAccount;
	private String password;
	private String email;
	private String createTime;

	public User() {

	}

	public User(Integer id, String username, String loginAccount, String password, String email, String createTime) {
		this.id = id;
		this.username = username;
		this.loginAccount = loginAccount;
		this.password = password;
		this.email = email;
		this.createTime = createTime;
	}

	public User(String username, String loginAccount, String password, String email, String createTime) {
		this.username = username;
		this.loginAccount = loginAccount;
		this.password = password;
		this.email = email;
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", loginAccount='" + loginAccount + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", createTime='" + createTime + '\'' +
				'}';
	}
}
