package com.mike.SSMActiviti.employee.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mike.SSMActiviti.employee.entity.Employee;

@Repository("employeeMapper")
public interface EmployeeMapper {

	Employee selectUserById(Integer id);

	/**
	 * 查询登录用户
	 * @param name
	 * @param password
	 * @return
	 */
	Employee login(@Param("name")String name,@Param("password")String password);
}
