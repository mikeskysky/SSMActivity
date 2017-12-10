package com.mike.SSMActiviti.workflow.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.mike.SSMActiviti.employee.entity.Employee;
import com.mike.SSMActiviti.leavebill.dao.LeaveBillMapper;
import com.mike.SSMActiviti.leavebill.entity.LeaveBill;
import com.mike.SSMActiviti.workflow.entity.Workflow;
import com.mike.SSMActiviti.workflow.service.WorkflowService;

@Service("workFlowService")
public class WorkFlowServiceImpl implements WorkflowService {

	@Resource
	private RepositoryService repositoryService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private FormService formService;
	@Resource
	private HistoryService historyService;
	@Resource
	private LeaveBillMapper leaveBillMapper;
	@Resource
	private  HttpServletRequest request;
	
	public void saveNewDeploy(Workflow workFlow) throws Exception
	{
		ZipInputStream zipInputStream=new ZipInputStream(workFlow.getFile().getInputStream());
		repositoryService.createDeployment().name(workFlow.getFileName())
			.addZipInputStream(zipInputStream)
			.deploy();
		
	}

	public List<Deployment> findDeploymentList() {
		List<Deployment> list=repositoryService.createDeploymentQuery()//创建部署对象查询
				.orderByDeploymenTime().asc()
				.list();
		return list;
	}

	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list=repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion()
			.asc().list();
		return list;
	}

	public InputStream findImageInputStream(String deploymentId, String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}

	public void saveStartProcess(Workflow workFlow) {
		Long id = workFlow.getId();
		LeaveBill leaveBill=leaveBillMapper.selectLeaveBillById(id);
		leaveBill.setState(1);
		leaveBillMapper.updateById(leaveBill);
		String key=leaveBill.getClass().getSimpleName();
		Map<String,Object> variables=new HashMap<String,Object>();
		Employee employee=(Employee) request.getSession().getAttribute("userinfo");
		variables.put("inputUser",employee.getName());
		String objId=key+"."+id;
		variables.put("objId",objId);
		runtimeService.startProcessInstanceByKey(key,objId,variables);
		
	}

	public List<Task> findTaskListByName(String name) {
		List<Task> list=taskService.createTaskQuery().taskAssignee(name)
			.orderByTaskCreateTime().asc().list();
		return list;
	}

	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		return formData.getFormKey();
	}

	public LeaveBill findLeaveBillByTaskId(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
			.processInstanceId(processInstanceId).singleResult();
		String businessKey = processInstance.getBusinessKey();
		String id="";
		if(StringUtils.isNotBlank(businessKey))
		{
			id=businessKey.split("\\.")[1];
		}
		LeaveBill leaveBill = leaveBillMapper.selectLeaveBillById(Long.parseLong(id));
		return leaveBill;
	}

	public List<String> findOutComeListByTaskId(String taskId) {
		//存放连线
		List<String> list=new ArrayList<String>();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processDefinitionId = task.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		String processInstanceId = task.getProcessInstanceId();
		
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)//使用流程实例ID查询
				.singleResult();
		String activityId = pi.getActivityId();
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null&&pvmList.size()>0)
		{
			for (PvmTransition pvm : pvmList) {
				String name=(String) pvm.getProperty("name");
				if(StringUtils.isNotBlank(name))
				{
					list.add(name);
				}
				else
				{
					list.add("默认提交");
				}
			}
		}
		return list;
	}

	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list=new ArrayList<Comment>();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processDefinitionId = task.getProcessDefinitionId();
		list= taskService.getProcessInstanceComments(processDefinitionId);
		return list;
	}

	public void saveSubmitTask(Workflow workFlow) {
		//获取任务id
		String taskId = workFlow.getTaskId();
		//获取连线名称
		String outcome = workFlow.getOutcome();
		//获取批注信息
		String message=workFlow.getComment();
		//获取请假单id
		Long id=workFlow.getId();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//获取流程实例id
		String processInstanceId = task.getProcessInstanceId();
		Employee employee=(Employee) request.getSession().getAttribute("userinfo");
		Authentication.setAuthenticatedUserId(employee.getName());
		taskService.addComment(taskId, processInstanceId, message);
		Map<String,Object> variables=new HashMap<String,Object>();
		if(outcome!=null&&!outcome.equals("默认提交"))
		{
			variables.put("outcome",outcome);
		}
		taskService.complete(taskId, variables);
		//查询任务是否结束
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
		.singleResult();
		if(pi==null)
		{
			LeaveBill leaveBillById = leaveBillMapper.selectLeaveBillById(id);
			leaveBillById.setState(2);
			leaveBillMapper.updateById(leaveBillById);
		}
	}

}
