package com.huabo.gkb.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.huabo.gkb.entity.TblManageRight;
import com.huabo.gkb.entity.TblOrganization;
import com.huabo.gkb.entity.TblStaff;
import com.huabo.gkb.entity.TblWxUserInfo;
import com.huabo.gkb.mapper.TblManageRightMapper;
import com.huabo.gkb.mapper.TblOrganizationMapper;
import com.huabo.gkb.mapper.TblStaffMapper;
import com.huabo.gkb.mapper.TblWxUserInfoMapper;
import com.huabo.gkb.mysqlmapper.TblManageRightMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblOrganizationMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblStaffMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblWxUserInfoMySqlMapper;
import com.huabo.gkb.service.TblWxUserInfoService;
import com.huabo.gkb.util.JedisUtil;

import redis.clients.jedis.Jedis;

@Service("tblWxUserInfoServiceImpl")
public class TblWxUserInfoServiceImpl extends BaseServiceImpl<TblWxUserInfo, Integer> implements TblWxUserInfoService {
	@Resource
	public TblWxUserInfoMapper tblWxUserInfoMapper;
	
	@Resource
	public TblOrganizationMapper tblOrganizationMapper;
	
	@Resource
	public TblManageRightMapper tblManageRightMapper;
	
	@Resource 
	public TblStaffMapper tblStaffMapper;
	
	
	
	@Resource
	public TblWxUserInfoMySqlMapper tblWxUserInfoMySqlMapper;
	
	@Resource
	public TblOrganizationMySqlMapper tblOrganizationMySqlMapper;
	
	@Resource
	public TblManageRightMySqlMapper tblManageRightMySqlMapper;
	
	@Resource 
	public TblStaffMySqlMapper tblStaffMySqlMapper;
	
	

	@Override
	public TblWxUserInfo findWxUserInfoByOpenId(String openId) throws Exception{
		return tblWxUserInfoMapper.selectWxUserInfoByOpenId(openId);
	}

	@Override
	public Integer findCountByUserOpenId(String openId) throws Exception {
		return tblWxUserInfoMapper.selectCountUserInfoByOpenId(openId);
	}

	@Override
	public List<Integer> findCountByUserOpenIdAndCompanyName(String openId, String companyName) throws Exception {
		return tblWxUserInfoMapper.selectCountByOpenIdAndCompanyName(openId,companyName);
	}

	@Override
	@Transactional(value="oracleDataSourceTransactionManager",rollbackFor = Exception.class,timeout=36000)
	public TblWxUserInfo insertUserOrgStaffRightInfo(TblWxUserInfo userInfo, TblStaff staff, TblOrganization org,String staffId)
			throws Exception {
		TblStaff oldStaff = this.tblStaffMapper.selectByMiblePhone(staff.getMiblephone());
		List<String> rightList = tblWxUserInfoMapper.selectRegisterRightByOrgId(TblOrganization.REGISTERORGFATHERID);
		BigDecimal oid = null;
		if(oldStaff == null) {
			tblOrganizationMapper.insertOrgReturnOrgId(org);
			tblOrganizationMapper.insertOrgRight(rightList,org.getOrgid());
			TblOrganization dept = new TblOrganization();
			dept.setOrgCreate(new Date());
			dept.setFatherorgid(org.getOrgid());
			dept.setOrgnumber("ZHGLB");
			dept.setOrgtype(0);
			dept.setStatus(0);
			dept.setIsAutoNumber(0);
			dept.setOutsideId(3);
			dept.setOrgname("综合管理部");
			tblOrganizationMapper.insertOrgReturnOrgId(dept);
			staff.setOrgid(dept.getOrgid());
			tblStaffMapper.insertStaffReturnStaffId(staff);
			userInfo.setStaffId(staff.getStaffid().toString());
			tblStaffMapper.insertManagerRight(rightList,staff.getStaffid());
			Jedis jedis = JedisUtil.getJedis();
			List<TblManageRight> rightManageList = tblManageRightMapper.getRightForUser(staff.getStaffid().toString());
			jedis.set("userMangerRight"+staff.getStaffid().toString(),JSON.toJSONString(rightManageList));
			JedisUtil.returnResource(jedis);
			userInfo.setRegisterChocie(0);
			userInfo.setOrgId(org.getOrgid());
			oid = org.getOrgid();
		}else {
			staff  = oldStaff;
			userInfo.setStaffId(oldStaff.getStaffid().toString());
			userInfo.setRegisterChocie(1);
			userInfo.setOrgId(staff.getOrgid());
			
			org = this.tblOrganizationMapper.selectOrgInfoByChildren(staff.getOrgid());
			oid = org.getOrgid();
		}
		Integer sid = Integer.parseInt(staffId);
		if(sid != -1) {
			this.tblStaffMapper.insertPupularizeStaffOrgRelation(sid,oid);
		}
		 
		tblWxUserInfoMapper.insertWxUserInfoReturnUserId(userInfo);
		
		
		userInfo.setEmail(staff.getEmail());
		userInfo.setRealName(staff.getRealname());
		userInfo.setMiblePhone(staff.getMiblephone());
		return userInfo;
	}

	@Override
	public void updateAvatarUrl(TblWxUserInfo userInfo) throws Exception {
		tblWxUserInfoMapper.updateAvatarUrl(userInfo);
	}

	@Override
	@Transactional(value="oracleDataSourceTransactionManager",rollbackFor = Exception.class,timeout=36000)
	public TblWxUserInfo insertUserOrgStaffRightInfo(TblWxUserInfo userInfo, TblStaff staff, String orgId)
			throws Exception {
		TblStaff oldStaff = this.tblStaffMapper.selectByMiblePhone(staff.getMiblephone());
		List<String> rightList = tblWxUserInfoMapper.selectRegisterRightByOrgId(TblOrganization.REGISTERORGFATHERID);
		if(oldStaff == null) {
			staff.setOrgid(new BigDecimal(orgId));
			tblStaffMapper.insertStaffReturnStaffId(staff);
			userInfo.setStaffId(staff.getStaffid().toString());
			tblStaffMapper.insertManagerRight(rightList,staff.getStaffid());
			Jedis jedis = JedisUtil.getJedis();
			List<TblManageRight> rightManageList = tblManageRightMapper.getRightForUser(staff.getStaffid().toString());
			jedis.set("userMangerRight"+staff.getStaffid().toString(),JSON.toJSONString(rightManageList));
			JedisUtil.returnResource(jedis);
			userInfo.setRegisterChocie(0);
		}else {
			staff  = oldStaff;
			userInfo.setStaffId(oldStaff.getStaffid().toString());
			userInfo.setRegisterChocie(1);
			orgId = staff.getOrgid().toString();
		}
		
		 
		tblWxUserInfoMapper.insertWxUserInfoReturnUserId(userInfo);
		
		userInfo.setOrgId(new BigDecimal(orgId));
		userInfo.setEmail(staff.getEmail());
		userInfo.setRealName(staff.getRealname());
		userInfo.setMiblePhone(staff.getMiblephone());
		return userInfo;
	}

	@Override
	public com.huabo.gkb.entitymysql.TblWxUserInfo findWxUserInfoByOpenIdMySql(String openId) throws Exception {
		return tblWxUserInfoMySqlMapper.selectWxUserInfoByOpenId(openId);
	}

	@Override
	public void updateAvatarUrlMySql(com.huabo.gkb.entitymysql.TblWxUserInfo userInfo) throws Exception {
		tblWxUserInfoMySqlMapper.updateAvatarUrl(userInfo);
	}

	@Override
	public Integer findCountByUserOpenIdMySql(String openId) throws Exception {
		return tblWxUserInfoMySqlMapper.selectCountUserInfoByOpenId(openId);
	}

	@Override
	@Transactional(value="mysqlDataSourceTransactionManager",rollbackFor = Exception.class,timeout=36000)
	public com.huabo.gkb.entitymysql.TblWxUserInfo insertUserOrgStaffRightInfoMySql(
			com.huabo.gkb.entitymysql.TblWxUserInfo userInfo, com.huabo.gkb.entitymysql.TblStaff staff,
			com.huabo.gkb.entitymysql.TblOrganization org, String staffId) throws Exception {
		com.huabo.gkb.entitymysql.TblStaff oldStaff = this.tblStaffMySqlMapper.selectByMiblePhone(staff.getMiblephone());
		List<String> rightList = tblWxUserInfoMySqlMapper.selectRegisterRightByOrgId(TblOrganization.REGISTERORGFATHERID);
		BigDecimal oid = null;
		if(oldStaff == null) {
			tblOrganizationMySqlMapper.insertOrgReturnOrgId(org);
			tblOrganizationMySqlMapper.insertOrgRight(rightList,org.getOrgid());
			com.huabo.gkb.entitymysql.TblOrganization dept = new com.huabo.gkb.entitymysql.TblOrganization();
			dept.setOrgCreate(new Date());
			dept.setFatherorgid(org.getOrgid());
			dept.setOrgnumber("ZHGLB");
			dept.setOrgtype(0);
			dept.setStatus(0);
			dept.setIsAutoNumber(0);
			dept.setOutsideId(3);
			dept.setOrgname("综合管理部");
			tblOrganizationMySqlMapper.insertOrgReturnOrgId(dept);
			staff.setOrgid(dept.getOrgid());
			tblStaffMySqlMapper.insertStaffReturnStaffId(staff);
			userInfo.setStaffId(staff.getStaffid().toString());
			tblStaffMySqlMapper.insertManagerRight(rightList,staff.getStaffid());
			Jedis jedis = JedisUtil.getJedis();
			List<TblManageRight> rightManageList = tblManageRightMySqlMapper.getRightForUser(staff.getStaffid().toString());
			jedis.set("userMangerRight"+staff.getStaffid().toString(),JSON.toJSONString(rightManageList));
			JedisUtil.returnResource(jedis);
			userInfo.setRegisterChocie(0);
			userInfo.setOrgId(org.getOrgid());
			oid = org.getOrgid();
		}else {
			staff  = oldStaff;
			userInfo.setStaffId(oldStaff.getStaffid().toString());
			userInfo.setRegisterChocie(1);
			userInfo.setOrgId(staff.getOrgid());
			
			org = this.tblOrganizationMySqlMapper.selectOrgInfoByChildren(staff.getOrgid());
			oid = org.getOrgid();
		}
		Integer sid = Integer.parseInt(staffId);
		if(sid != -1) {
			this.tblStaffMySqlMapper.insertPupularizeStaffOrgRelation(sid,oid);
		}
		 
		tblWxUserInfoMySqlMapper.insertWxUserInfoReturnUserId(userInfo);
		
		
		userInfo.setEmail(staff.getEmail());
		userInfo.setRealName(staff.getRealname());
		userInfo.setMiblePhone(staff.getMiblephone());
		return userInfo;
	}

	@Override
	public com.huabo.gkb.entitymysql.TblWxUserInfo insertUserOrgStaffRightInfoMySql(
			com.huabo.gkb.entitymysql.TblWxUserInfo userInfo, com.huabo.gkb.entitymysql.TblStaff staff, String orgId)
			throws Exception {
		com.huabo.gkb.entitymysql.TblStaff oldStaff = this.tblStaffMySqlMapper.selectByMiblePhone(staff.getMiblephone());
		List<String> rightList = tblWxUserInfoMapper.selectRegisterRightByOrgId(TblOrganization.REGISTERORGFATHERID);
		if(oldStaff == null) {
			staff.setOrgid(new BigDecimal(orgId));
			tblStaffMySqlMapper.insertStaffReturnStaffId(staff);
			userInfo.setStaffId(staff.getStaffid().toString());
			tblStaffMySqlMapper.insertManagerRight(rightList,staff.getStaffid());
			Jedis jedis = JedisUtil.getJedis();
			List<TblManageRight> rightManageList = tblManageRightMySqlMapper.getRightForUser(staff.getStaffid().toString());
			jedis.set("userMangerRight"+staff.getStaffid().toString(),JSON.toJSONString(rightManageList));
			JedisUtil.returnResource(jedis);
			userInfo.setRegisterChocie(0);
		}else {
			staff  = oldStaff;
			userInfo.setStaffId(oldStaff.getStaffid().toString());
			userInfo.setRegisterChocie(1);
			orgId = staff.getOrgid().toString();
		}
		
		 
		tblWxUserInfoMySqlMapper.insertWxUserInfoReturnUserId(userInfo);
		
		userInfo.setOrgId(new BigDecimal(orgId));
		userInfo.setEmail(staff.getEmail());
		userInfo.setRealName(staff.getRealname());
		userInfo.setMiblePhone(staff.getMiblephone());
		return userInfo;
	}

}
