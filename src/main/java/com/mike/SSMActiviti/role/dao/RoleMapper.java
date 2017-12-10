package com.mike.SSMActiviti.role.dao;

import org.springframework.stereotype.Repository;

import com.mike.SSMActiviti.role.entity.Role;

@Repository("roleMapper")
public interface RoleMapper 
{
	Role selectRoleById(Integer id);
}
