package com.mike.SSMActiviti.leavebill.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mike.SSMActiviti.leavebill.entity.LeaveBill;

@Repository("leaveBillMapper")
public interface LeaveBillMapper 
{

	/**
	 * 查询请假单list
	 * @return
	 */
	List<LeaveBill> selectList();

	/**
	 * 查询当前用户的请假单
	 * @param id
	 * @return
	 */
	List<LeaveBill> selectLeaveListByUser(Long id);

	/**
	 * 新增请假单
	 * @param leaveBill
	 * @return
	 */
	int insert(LeaveBill leaveBill);

	/**
	 * 根据主键查询请假单
	 * @param id
	 * @return
	 */
	LeaveBill selectLeaveBillById(Long id);

	/**
	 * 根据主键更新
	 * @param leaveBill
	 * @return
	 */
	int updateById(LeaveBill leaveBill);
	
}
