package com.mike.SSMActiviti.employee.service;

import com.mike.SSMActiviti.employee.entity.Employee;

public interface EmployeeService {

	/**
	 * 查询登录用户
	 * @param employee
	 * @return
	 */
	Employee login(Employee employee);

}
