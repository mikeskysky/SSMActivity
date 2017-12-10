package com.mike.SSMActiviti.workflow.service;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.mike.SSMActiviti.leavebill.entity.LeaveBill;
import com.mike.SSMActiviti.workflow.entity.Workflow;

public interface WorkflowService {

	/**
	 * 部署流程
	 * @param workFlow
	 */
	void saveNewDeploy(Workflow workFlow) throws Exception;

	/**
	 * 查询部署对象信息(表act_re_deployment)
	 * @return
	 */
	List<Deployment> findDeploymentList();

	/**
	 * 查询流程定义信息(表act_re_procdef)
	 * @return
	 */
	List<ProcessDefinition> findProcessDefinitionList();

	/**
	 * 获取流程的图片
	 * @param deploymentId
	 * @param imageName
	 * @return
	 */
	InputStream findImageInputStream(String deploymentId, String imageName);

	/**
	 * 启动流程
	 * @param workFlow
	 */
	void saveStartProcess(Workflow workFlow);

	/**
	 * 根据用户名查找待办任务
	 * @param name
	 * @return
	 */
	List<Task> findTaskListByName(String name);

	/**
	 * 获取任务表单中任务节点的url链接
	 * @param taskId
	 * @return
	 */
	String findTaskFormKeyByTaskId(String taskId);

	/**
	 * 根据待办任务id查询请假单
	 * @param taskId
	 * @return
	 */
	LeaveBill findLeaveBillByTaskId(String taskId);

	/**
	 * 查询当前任务的所有连线
	 * @param taskId
	 * @return
	 */
	List<String> findOutComeListByTaskId(String taskId);

	/**
	 * 查询历史审批意见
	 * @param taskId
	 * @return
	 */
	List<Comment> findCommentByTaskId(String taskId);

	/**
	 * 处理任务
	 * @param workFlow
	 */
	void saveSubmitTask(Workflow workFlow);

}
