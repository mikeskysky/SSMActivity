package com.mike.SSMActiviti.role.entity;

import java.io.Serializable;

public class Role implements Serializable
{

	private static final long serialVersionUID = -4176554539039555925L;
	private Long id;
	private String roleName;
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
