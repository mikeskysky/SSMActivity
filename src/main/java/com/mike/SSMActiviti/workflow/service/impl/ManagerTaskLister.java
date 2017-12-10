package com.mike.SSMActiviti.workflow.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mike.SSMActiviti.employee.entity.Employee;

public class ManagerTaskLister implements TaskListener
{
	private static final long serialVersionUID = -605161102736718205L;

	public void notify(DelegateTask delegateTask) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		Employee employee=(Employee) request.getSession().getAttribute("userinfo");
		delegateTask.setAssignee(employee.getManager().getName());
	}
	
}
