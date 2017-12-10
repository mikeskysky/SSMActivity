package com.mike.SSMActiviti.leavebill.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mike.SSMActiviti.employee.entity.Employee;
import com.mike.SSMActiviti.leavebill.entity.LeaveBill;
import com.mike.SSMActiviti.leavebill.service.LeaveBillService;

@Controller
@RequestMapping("leavebill")
public class LeaveBillController
{
	@Resource(name="leaveBillService")
	private LeaveBillService leaveBillService;
	
	/**
	 * 查询当前用户请假列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="home")
	public String home(HttpServletRequest request)
	{
		Employee employee=(Employee) request.getSession().getAttribute("userinfo");
		List<LeaveBill> list=leaveBillService.findLeaveListByUser(employee.getId());
		request.setAttribute("list",list);
		return "leaveBill/list";
	}
	/**
	 * 跳转请假单新增页面
	 * @return
	 */
	@RequestMapping(value="input")
	public String input()
	{
		return "leaveBill/input";
	}
	/**
	 * 请假单新增
	 * @return
	 */
	@RequestMapping(value="save")
	public String save(HttpServletRequest request,LeaveBill leaveBill)
	{
		Employee employee=(Employee) request.getSession().getAttribute("userinfo");
		leaveBill.setUser_id(employee.getId());
		leaveBillService.saveLeaveBill(leaveBill);
		return "redirect:/leavebill/home";
	}
	
	
}
