package com.mike.SSMActiviti.workflow.entity;

import java.io.File;
import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Workflow implements Serializable
{

	private static final long serialVersionUID = -243801081329268313L;
	/**
	 * 流程部署的文件
	 */
	private CommonsMultipartFile file;
	/**
	 * 流程定义名字
	 */
	private String fileName;
	/**
	 * 申请单id
	 */
	private Long id;
	/**
	 * 部署对象id
	 */
	private String deploymentId;
	/**
	 * 资源文件名称(图片)
	 */
	private String imageName;
	/**
	 * 任务id
	 */
	private String taskId;
	/**
	 * 连线名称
	 */
	private String outcome;
	/**
	 * 批注
	 */
	private String comment;
	public CommonsMultipartFile getFile() {
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
