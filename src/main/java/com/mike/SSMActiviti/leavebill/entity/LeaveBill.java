package com.mike.SSMActiviti.leavebill.entity;

import java.io.Serializable;
import java.util.Date;

import com.mike.SSMActiviti.employee.entity.Employee;

/**
 * 请假单
 */
public class LeaveBill  implements Serializable
{
	private static final long serialVersionUID = -5286699105161868003L;
	private Long id;//主键ID
	private Integer days;// 请假天数
	private String content;// 请假内容
	private Date leaveDate = new Date();// 请假时间
	private String remark;// 备注
	private Long user_id;
	private Employee user;// 请假人
	
	private Integer state=0;// 请假单状态 0初始录入,1.开始审批,2为审批完成
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
