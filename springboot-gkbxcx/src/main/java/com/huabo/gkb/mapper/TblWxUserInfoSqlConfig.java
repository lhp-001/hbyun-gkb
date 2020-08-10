package com.huabo.gkb.mapper;

import com.huabo.gkb.entity.TblWxUserInfo;

public class TblWxUserInfoSqlConfig {
	
	public String insertWxUserInfoReturnUserId(TblWxUserInfo userInfo) {
		StringBuffer columnsb = new StringBuffer("INSERT INTO TBL_WXUSER_INFO(USERID");
		StringBuffer valueSb = new StringBuffer(") VALUES(HIBERNATE_SEQUENCE.NEXTVAL");
		
		if(userInfo.getAvatarUrl() != null && !"".equals(userInfo.getAvatarUrl())) {
			columnsb.append(",AVATARURL");
			valueSb.append(",'"+userInfo.getAvatarUrl()+"'");
		}
		if(userInfo.getCity() != null && !"".equals(userInfo.getCity())) {
			columnsb.append(",CITY");
			valueSb.append(",'"+userInfo.getCity()+"'");
		}
		if(userInfo.getCountry() != null && !"".equals(userInfo.getCountry())) {
			columnsb.append(",COUNTRY");
			valueSb.append(",'"+userInfo.getCountry()+"'");
		}
		if(userInfo.getGender() != null && !"".equals(userInfo.getGender())) {
			columnsb.append(",GENDER");
			valueSb.append(",'"+userInfo.getGender()+"'");
		}
		if(userInfo.getLanguage() != null && !"".equals(userInfo.getLanguage())) {
			columnsb.append(",LANGUAGE");
			valueSb.append(",'"+userInfo.getLanguage()+"'");
		}
		if(userInfo.getNickName() != null && !"".equals(userInfo.getNickName())) {
			columnsb.append(",NICKNAME");
			valueSb.append(",'"+userInfo.getNickName()+"'");
		}
		if(userInfo.getXcxOpenId() != null && !"".equals(userInfo.getXcxOpenId())) {
			columnsb.append(",XCXOPENID");
			valueSb.append(",'"+userInfo.getXcxOpenId()+"'");
		}
		if(userInfo.getProvince() != null && !"".equals(userInfo.getProvince())) {
			columnsb.append(",PROVINCE");
			valueSb.append(",'"+userInfo.getProvince()+"'");
		}
		if(userInfo.getUnionId() != null && !"".equals(userInfo.getUnionId())) {
			columnsb.append(",UNIONID");
			valueSb.append(",'"+userInfo.getUnionId()+"'");
		}
		if(userInfo.getStaffId()!= null && !"".equals(userInfo.getStaffId())) {
			columnsb.append(",STAFFID");
			valueSb.append(",'"+userInfo.getStaffId()+"'");
		}
		valueSb.append(")");
		String sql = columnsb.toString()+valueSb.toString();
		return sql;
	}
}
