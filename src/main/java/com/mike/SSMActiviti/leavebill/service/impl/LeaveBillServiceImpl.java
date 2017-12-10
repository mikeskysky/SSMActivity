package com.mike.SSMActiviti.leavebill.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mike.SSMActiviti.leavebill.dao.LeaveBillMapper;
import com.mike.SSMActiviti.leavebill.entity.LeaveBill;
import com.mike.SSMActiviti.leavebill.service.LeaveBillService;

@Service("leaveBillService")
public class LeaveBillServiceImpl implements LeaveBillService {

	@Resource(name="leaveBillMapper")
	private LeaveBillMapper leaveBillMapper;
	
	public List<LeaveBill> selectList() {
		return leaveBillMapper.selectList();
	}

	public List<LeaveBill> findLeaveListByUser(Long id) {
		return leaveBillMapper.selectLeaveListByUser(id);
	}

	public int saveLeaveBill(LeaveBill leaveBill) {
		return leaveBillMapper.insert(leaveBill);
	}

}
