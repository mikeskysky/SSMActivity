package com.mike.SSMActiviti.employee.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mike.SSMActiviti.employee.entity.Employee;
import com.mike.SSMActiviti.employee.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController 
{
	@Resource(name="employeeService")
	private EmployeeService employeeService;
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(Employee employee,HttpServletRequest request)
	{
		Employee loginEmp=employeeService.login(employee);
		if(loginEmp!=null)
		{
			request.getSession().setAttribute("userinfo",loginEmp);
			return "main";
		}
		else
		{
			request.setAttribute("errormsg", "用户名密码错误");
			return "login";
		}
	}
	
	@RequestMapping(value="top")
	public String top()
	{
		return "top";
	}
	
	@RequestMapping(value="welcome")
	public String welcome()
	{
		return "welcome";
	}
	
	@RequestMapping(value="left")
	public String left()
	{
		return "left";
	}
	
}
