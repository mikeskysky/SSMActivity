package com.mike.SSMActiviti.workflow.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mike.SSMActiviti.employee.entity.Employee;
import com.mike.SSMActiviti.leavebill.entity.LeaveBill;
import com.mike.SSMActiviti.leavebill.service.LeaveBillService;
import com.mike.SSMActiviti.workflow.entity.Workflow;
import com.mike.SSMActiviti.workflow.service.WorkflowService;

/**
 * 工作流controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("workflow")
public class WorkFlowController 
{
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Resource(name="workFlowService")
	private WorkflowService workFlowService;
	@Resource(name="leaveBillService")
	private LeaveBillService leaveBillService;
	/**
	 * 跳转部署页面
	 * @return
	 */
	@RequestMapping(value="deployHome")
	public String deployHome(HttpServletRequest request)
	{
		//查询部署对象信息(act_re_deployment)
		List<Deployment> deploymentList=workFlowService.findDeploymentList();
		//查询流程定义信息(act_re_procdef);
		List<ProcessDefinition> processDefinitionList=workFlowService.findProcessDefinitionList();
		request.setAttribute("deploymentList", deploymentList);
		request.setAttribute("processDefinitionList", processDefinitionList);
		return "workflow/workflow";
	}
	/**
	 * 部署流程
	 * @param workFlow
	 * @return
	 */
	@RequestMapping(value="newdeploy")
	public String newdeploy(Workflow workFlow)
	{
		try {
			workFlowService.saveNewDeploy(workFlow);
		} catch (Exception e) {
			logger.error("部署流程报错",e);
		}
		return "redirect:/workflow/deployHome ";
	}
	/**
	 * 查看流程图
	 * @return
	 */
	@RequestMapping(value="viewImage")
	public String viewImage(Workflow workFlow,HttpServletResponse response) throws Exception
	{
		InputStream in=workFlowService.findImageInputStream(workFlow.getDeploymentId(),workFlow.getImageName());
		OutputStream out = response.getOutputStream();
		for(int b=-1;(b=in.read())!=-1;)
		{
			out.write(b);
		}
		out.close();
		in.close();
		return "";
	}
	/**
	 * 申请请假
	 * @return
	 */
	@RequestMapping(value="startProcess")
	public String startProcess(Workflow workFlow)
	{
		workFlowService.saveStartProcess(workFlow);
		return "redirect:/workflow/listTask";
	}
	/**
	 * 查询待办任务
	 * @param request
	 * @return
	 */
	@RequestMapping(value="listTask")
	public String listTask(HttpServletRequest request)
	{
		Employee employee=(Employee) request.getSession().getAttribute("userinfo");
		//使用当前用户名查询正在执行的任务表,获取当前任务的集合
		List<Task> taskList=workFlowService.findTaskListByName(employee.getName());
		request.setAttribute("taskList",taskList);
		return "/workflow/task";
	}
	/**
	 * 开发任务表单
	 * @return
	 */
	@RequestMapping(value="viewTaskForm")
	public String viewTaskForm(Workflow workFlow)
	{
		String taskId = workFlow.getTaskId();
		//获取任务表单中任务节点的url链接
		String redirect=workFlowService.findTaskFormKeyByTaskId(taskId);
		redirect+="?taskId="+taskId;
		return "redirect:/"+redirect;
	}
	/**
	 * 办理任务
	 * @param workFlow
	 * @param request
	 * @return
	 */
	@RequestMapping(value="audit")
	public String audit(Workflow workFlow,HttpServletRequest request)
	{
		LeaveBill leaveBill=workFlowService.findLeaveBillByTaskId(workFlow.getTaskId());
		//查询当前任务的连线
		List<String> outcomList=workFlowService.findOutComeListByTaskId(workFlow.getTaskId());
		//查询历史审批意见
		List<Comment> commentList=workFlowService.findCommentByTaskId(workFlow.getTaskId());
		
		request.setAttribute("leaveBill", leaveBill);
		request.setAttribute("outcomList",outcomList);
		request.setAttribute("commentList", commentList);
		request.setAttribute("taskId",workFlow.getTaskId());
		return "workflow/taskForm";
	}
	/**
	 * 处理任务
	 * @return
	 */
	@RequestMapping(value="submitTask")
	public String submitTask(Workflow workFlow)
	{
		workFlowService.saveSubmitTask(workFlow);
		return "redirect:/workflow/listTask";
	}
	
}
