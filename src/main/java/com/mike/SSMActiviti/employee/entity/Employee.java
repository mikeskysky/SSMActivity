package com.mike.SSMActiviti.employee.entity;

import java.io.Serializable;

import com.mike.SSMActiviti.role.entity.Role;

/**
 * 用户表
 */
public class Employee implements Serializable
{
	private static final long serialVersionUID = 2587700276166175800L;
	private Long id;//主键ID
	private String name;//用户名
	private String password;//密码
	private String email;//电子邮箱
	private Role role;//角色
	private Employee manager;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Employee getManager() {
		return manager;
	}
	public void setManager(Employee manager) {
		this.manager = manager;
	}
	
	
}

