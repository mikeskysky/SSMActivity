package com.mike.SSMActiviti.employee.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mike.SSMActiviti.employee.dao.EmployeeMapper;
import com.mike.SSMActiviti.employee.entity.Employee;
import com.mike.SSMActiviti.employee.service.EmployeeService;
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Resource(name="employeeMapper")
	private EmployeeMapper employeeMapper;
	
	public Employee login(Employee employee) {
		return employeeMapper.login(employee.getName(),employee.getPassword());
	}

}
