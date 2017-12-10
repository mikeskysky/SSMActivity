package com.mike.SSMActiviti.leavebill.service;

import java.util.List;

import com.mike.SSMActiviti.leavebill.entity.LeaveBill;

public interface LeaveBillService {

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
	List<LeaveBill> findLeaveListByUser(Long id);

	/**
	 * 新增请假单
	 * @param leaveBill
	 */
	int saveLeaveBill(LeaveBill leaveBill);

}
