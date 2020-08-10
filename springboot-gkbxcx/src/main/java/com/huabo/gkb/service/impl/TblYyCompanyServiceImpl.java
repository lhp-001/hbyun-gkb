package com.huabo.gkb.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblYyOrgDeposit;
import com.huabo.gkb.entity.TblYyQueryPrice;
import com.huabo.gkb.entity.TblYyReportModel;
import com.huabo.gkb.entity.TblYyUserQuery;
import com.huabo.gkb.entity.TblyyCompany;
import com.huabo.gkb.entity.Tblyyprice;
import com.huabo.gkb.mapper.TblOrganizationMapper;
import com.huabo.gkb.mapper.TblYyCompanyMapper;
import com.huabo.gkb.mapper.TblYyOrgDepositMapper;
import com.huabo.gkb.mapper.TblYyQueryPriceMapper;
import com.huabo.gkb.mapper.TblYyReportModelMapper;
import com.huabo.gkb.mapper.TblYyUserQueryMapper;
import com.huabo.gkb.mapper.TblyypriceMapper;
import com.huabo.gkb.mysqlmapper.TblOrganizationMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyCompanyMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyOrgDepositMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyQueryPriceMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyReportModelMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyUserQueryMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblyypriceMySqlMapper;
import com.huabo.gkb.service.TblYyCompanyService;
import com.huabo.gkb.util.YHttpclicent;

import net.sf.json.JsonConfig;

@Service("tblYyCompanyServiceImpl")
public class TblYyCompanyServiceImpl extends BaseServiceImpl<TblyyCompany, Integer> implements TblYyCompanyService {

	@Resource
	public TblYyCompanyMapper tblYyCompanyMapper;
	
	@Resource
	public TblOrganizationMapper tblOrganizationMapper;
	
	@Resource
	public TblYyReportModelMapper tblYyReportModelMapper;

	@Resource
	public TblYyOrgDepositMapper tblYyOrgDepositMapper;
	
	@Resource
	public TblyypriceMapper tblyypriceMapper;
	
	@Resource
	public TblYyUserQueryMapper tblYyUserQueryMapper;
	
	@Resource
	public TblYyQueryPriceMapper tblYyQueryPriceMapper;
	
	
	@Resource
	public TblYyCompanyMySqlMapper tblYyCompanyMySqlMapper;
	
	@Resource
	public TblOrganizationMySqlMapper tblOrganizationMySqlMapper;
	
	@Resource
	public TblYyReportModelMySqlMapper tblYyReportModelMySqlMapper;

	@Resource
	public TblYyOrgDepositMySqlMapper tblYyOrgDepositMySqlMapper;
	
	@Resource
	public TblyypriceMySqlMapper tblyypriceMySqlMapper;
	
	@Resource
	public TblYyUserQueryMySqlMapper tblYyUserQueryMySqlMapper;
	
	@Resource
	public TblYyQueryPriceMySqlMapper tblYyQueryPriceMySqlMapper;
	
	
	@Override
	public void findCompanyInfoByPageInfo(PageInfo<TblyyCompany> pageInfo) throws Exception {
		pageInfo.setTlist(this.tblYyCompanyMapper.selectCompanyListByPageInfo(pageInfo));
		pageInfo.setTotalRecord(this.tblYyCompanyMapper.selectCompanyCountByPageInfo(pageInfo));
	}

	@Override
	public void findCompanyInfoByPageInfoMySql(PageInfo<com.huabo.gkb.entitymysql.TblyyCompany> pageInfo)
			throws Exception {
		pageInfo.setTlist(this.tblYyCompanyMySqlMapper.selectCompanyListByPageInfo(pageInfo));
		pageInfo.setTotalRecord(this.tblYyCompanyMySqlMapper.selectCompanyCountByPageInfo(pageInfo));
	}
	
	@Override
	public Integer addCompanyList(String chooseName, Integer staffId, String orgId) throws Exception {
		String[] names = chooseName.split(",");
		StringBuffer nameSb = new StringBuffer("(");
		for(int i = 0 ; i < names.length ; i++ ) {
			nameSb.append("'"+names[i]+"',");
		}
		nameSb.deleteCharAt(nameSb.length()-1);
		nameSb.append(")");
		String name = nameSb.toString();
		
		if(SystemStaticValue.DataType == 0) {
			Integer count = this.tblYyCompanyMySqlMapper.checkNameByList(staffId,name);
			if(count > 0) {
				return -1;//公司名称重复
			}
			
			List<com.huabo.gkb.entitymysql.TblyyCompany> comList = this.tblYyCompanyMySqlMapper.selectNoUserComByComName(staffId,name);
			
			List<String> nameList=Arrays.asList(names);
			List<String> arrayList=new ArrayList<String>(nameList);//转换为ArrayLsit调用相关的remove方法
	        
			for (com.huabo.gkb.entitymysql.TblyyCompany com : comList) {
				for(String s:nameList){
			        if(s.equals(com.getCompanyName())) {
			        	arrayList.remove(com.getCompanyName());
			        }  
			    }
				com.setCstatus(1);
				this.tblYyCompanyMySqlMapper.startCompanyByInfo(com.getCompanyName(),com.getCstatus(),com.getStaffId());
			}
			
			Integer oid = tblOrganizationMySqlMapper.findCompanyIdByDeptId(orgId);
			
			com.huabo.gkb.entitymysql.TblyyCompany com = null;
			for(int i = 0 ; i < arrayList.size() ; i++ ) {
				com = new com.huabo.gkb.entitymysql.TblyyCompany();
				com.setStaffId(staffId);
				com.setOrgId(oid);
				com.setCompanyName(arrayList.get(i));
				com.setCreateDate(new Date());
				com.setCstatus(1);
				this.tblYyCompanyMySqlMapper.insertSelective(com);
			}
		}else {
			Integer count = this.tblYyCompanyMapper.checkNameByList(staffId,name);
			if(count > 0) {
				return -1;//公司名称重复
			}
			
			List<TblyyCompany> comList = this.tblYyCompanyMapper.selectNoUserComByComName(staffId,name);
			
			List<String> nameList=Arrays.asList(names);
			List<String> arrayList=new ArrayList<String>(nameList);//转换为ArrayLsit调用相关的remove方法
	        
			for (TblyyCompany com : comList) {
				for(String s:nameList){
			        if(s.equals(com.getCompanyName())) {
			        	arrayList.remove(com.getCompanyName());
			        }  
			    }
				com.setCstatus(1);
				this.tblYyCompanyMapper.startCompanyByInfo(com.getCompanyName(),com.getCstatus(),com.getStaffId());
			}
			
			Integer oid = tblOrganizationMapper.findCompanyIdByDeptId(orgId);
			
			TblyyCompany com = null;
			for(int i = 0 ; i < arrayList.size() ; i++ ) {
				com = new TblyyCompany();
				com.setStaffId(staffId);
				com.setOrgId(oid);
				com.setCompanyName(arrayList.get(i));
				com.setCreateDate(new Date());
				com.setCstatus(1);
				this.tblYyCompanyMapper.insertSelective(com);
			}
		}
		return 0;
	}

	@Override
	public void cancelCompany(String ids) throws Exception {
		if(SystemStaticValue.DataType == 0) {
			this.tblYyCompanyMySqlMapper.cancelCompany(ids);
		}else {
			this.tblYyCompanyMapper.cancelCompany(ids);
		}
	}

	@Override
	public Integer removeTeamInfoCompany(String[] chooseids, Integer teamid) throws Exception {
		StringBuffer idSb = new StringBuffer("(");
		for (int i = 0; i < chooseids.length; i++) {
			idSb.append(chooseids[i]+",");
		}
		idSb.deleteCharAt(idSb.length()-1);
		idSb.append(")");
		String ids = idSb.toString();
		Integer count = 0;
		Integer result = 0;
		
		if(SystemStaticValue.DataType == 0) {
			List<com.huabo.gkb.entitymysql.TblyyCompany> comList = this.tblYyCompanyMySqlMapper.selectListByIds(ids); 
			com.huabo.gkb.entitymysql.TblyyCompany company = null;
			for (com.huabo.gkb.entitymysql.TblyyCompany com : comList) {
				//1.验重
				count = this.tblYyCompanyMySqlMapper.checkNameByRemoveCom(com.getCompanyName(),teamid,com.getStaffId());
				if(count == 0) {
					//2.判断添加还是修改
					company = this.tblYyCompanyMySqlMapper.checkNAddOrUpdateComPany(com.getCompanyName(),teamid,com.getStaffId());
					if(company != null) {
						this.tblYyCompanyMySqlMapper.startCompanyById(company.getCompanyid());
					}else {
						this.tblYyCompanyMySqlMapper.updateTeamInfoById(teamid,com.getCompanyid());
					}
				}else {
					result = -1;
					break;
				}
			}
		}else {
			List<TblyyCompany> comList = this.tblYyCompanyMapper.selectListByIds(ids); 
			TblyyCompany company = null;
			for (TblyyCompany com : comList) {
				//1.验重
				count = this.tblYyCompanyMapper.checkNameByRemoveCom(com.getCompanyName(),teamid,com.getStaffId());
				if(count == 0) {
					//2.判断添加还是修改
					company = this.tblYyCompanyMapper.checkNAddOrUpdateComPany(com.getCompanyName(),teamid,com.getStaffId());
					if(company != null) {
						this.tblYyCompanyMapper.startCompanyById(company.getCompanyid());
					}else {
						this.tblYyCompanyMapper.updateTeamInfoById(teamid,com.getCompanyid());
					}
				}else {
					result = -1;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void addReportModel(Integer companyid, String companyName, String orgId, Integer staffId) throws Exception {
		if(SystemStaticValue.DataType == 0) {
			com.huabo.gkb.entitymysql.TblYyReportModel model = new com.huabo.gkb.entitymysql.TblYyReportModel();
			model.setReportname(companyName+"-风险监控报告");
			model.setStaffId(new BigDecimal(staffId));
			Integer oid = tblOrganizationMySqlMapper.findCompanyIdByDeptId(orgId);
			model.setOrgId(new BigDecimal(oid));
			this.tblYyReportModelMySqlMapper.insertSelective(model);
			this.tblYyCompanyMySqlMapper.bindReportIdBycompanyid(companyid,model.getReportid());
		}else {
			TblYyReportModel model = new TblYyReportModel();
			model.setReportname(companyName+"-风险监控报告");
			model.setStaffId(new BigDecimal(staffId));
			Integer oid = tblOrganizationMapper.findCompanyIdByDeptId(orgId);
			model.setOrgId(new BigDecimal(oid));
			this.tblYyReportModelMapper.insertSelective(model);
			this.tblYyCompanyMapper.bindReportIdBycompanyid(companyid,model.getReportid());
		}
	}

	@Override
	public Map<String, String> findReportInfo(Integer companyid, String orgId, Integer staffId) throws Exception {
		Map<String,String> resultMap = new HashMap<String, String>(0);
		
		if(SystemStaticValue.DataType == 0) {
			//1.判断金额是否足够使用	 
			Integer oid = tblOrganizationMySqlMapper.findCompanyIdByDeptId(orgId);
			boolean flag = false;
			if(SystemStaticValue.WBSJSTAFFID.indexOf(staffId) == -1 ) {
				flag = true;
			}
	    	String surplusMoney = this.tblYyOrgDepositMySqlMapper.selectUrplusMoney(oid);
	    	
	    	if(surplusMoney == null && flag == false){
	    		resultMap.put("result", "-1");//账户余额不足，请充值
	    	}else {
	    		com.huabo.gkb.entitymysql.TblyyCompany company = this.tblYyCompanyMySqlMapper.selectByPrimaryKey(companyid);
	    		resultMap.put("company", JSONObject.toJSONString(company));
	    		if(company.getReportid() != null && flag == false) {
	    			Double money = this.tblYyOrgDepositMySqlMapper.selectReportTostMoney(company.getReportid()); 
	            	Double smoney = Double.valueOf(surplusMoney);	 
	            	if(smoney < money){
	            		resultMap.put("result", "-1");
	            		return resultMap;
	            	}
	    		}
	    		
	    		//2.查询所有报告信息；
	    		//2.1查询所有的接口
	    		List<com.huabo.gkb.entitymysql.Tblyyprice> priceList = this.tblyypriceMySqlMapper.selecAllList();
	    		resultMap.put("priceList", JSONObject.toJSONString(priceList));
	    		
	    		//2.2查询该公司报告用到的所有接口
	    		if(company.getReportid() != null) {
	    			com.huabo.gkb.entitymysql.TblYyReportModel model = this.tblYyReportModelMySqlMapper.selectByPrimaryKey(company.getReportid());
	    			resultMap.put("model", JSONObject.toJSONString(model));
	    			if(model.getPriceid() != null && !"".equals(model.getPriceid())) {
	    				
		    			List<com.huabo.gkb.entitymysql.Tblyyprice> ownerList = this.tblyypriceMySqlMapper.selectOwenerList(company.getReportid());
		    			//2.3保存查询消费记录
		    			if(flag == false) {
		    				this.dealMysqlUserQuery(oid,staffId,model);
		    			}
		    			
		    			//2.4 调用接口查询详细数据
		    			String result = null;
		    			String reason = null;
		    			String context = null;
		    			String items = null;
		    			net.sf.json.JSONObject jsonObject = null;
		    			net.sf.json.JSONObject resultobj = null;
		    			for (com.huabo.gkb.entitymysql.Tblyyprice price : ownerList) {
		    				items = null;
		    				context = null;
							switch (price.getInterfacename()) {
							case "基本信息":
								result = YHttpclicent.findGSXX("",company.getCompanyName());
								jsonObject = net.sf.json.JSONObject.fromObject(result);
						        reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
							        String resultall = jsonObject.getString("result");
							        resultobj = net.sf.json.JSONObject.fromObject(resultall);
							        JsonConfig jsonC = new JsonConfig();
								    jsonC.setExcludes(new String[] { "staffList" });
								    context = net.sf.json.JSONObject.fromObject(resultobj,jsonC).toString();
						         }
								resultMap.put("business", context);
								break;
							case "股东信息":
								result = YHttpclicent.findgdxx("",company.getCompanyName(),1);
								jsonObject = net.sf.json.JSONObject.fromObject(result);
						        reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	context = resultobj.getString("items");
						        	 }
						         }
								resultMap.put("shareholder", context);
								break;
							case "对外投资":
								 result = YHttpclicent.findDwtz("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items = resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("outbounds", items);
								break;
							case "变更信息":
								 result = YHttpclicent.findchange("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("chageInfo", items);
								break;
							case "分支机构":
								 result = YHttpclicent.findfzjg("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items = resultobj.getString("items");
						        	 }
						   	        
						         }
								resultMap.put("branch", items);
								break;
							case "融资历史":
								 result = YHttpclicent.finddrzls("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         items = null;
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("financing", items);
								break;
							case "投资事件":
								 result = YHttpclicent.finddeve("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("development", items);
								break;
							case "法律诉讼":
								 result = YHttpclicent.findfvss("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						 	        
						         }
						         resultMap.put("legal", items);
								break;
							case "法院公告":
								 result = YHttpclicent.findJudicialrisk("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items = resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("judicialrisk", items);
								break;
							case "失信人":
								 result = YHttpclicent.findsxr("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         items = null;
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
								resultMap.put("dishonest", items);
								break;
							case "被执行人":
								 result = YHttpclicent.findbzxr("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         items = null;
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("executor", items);
								break;
							case "开庭公告":
								result = YHttpclicent.findktgg("",company.getCompanyName(),1);
								jsonObject = net.sf.json.JSONObject.fromObject(result);
						        reason = jsonObject.getString("reason");
						        if(reason!=null && reason.equals("ok")){
							       	 String resultall = jsonObject.getString("result");
							       	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
							       	 String total = resultobj.getString("total");
							       	 if(total!=null && !total.equals("0")){
							       		items = resultobj.getString("items");
							       	}
						        }
								resultMap.put("noticecourt", items);
								break;
							case "经营异常":
								 result = YHttpclicent.findjyyc("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						        	 
						         }
						         resultMap.put("abnormal", items);
								break;
							case "行政处罚":
								 result = YHttpclicent.findRisk("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
								resultMap.put("businessRisk", items);
								break;
							case "严重违法":
								 result = YHttpclicent.findyzwf("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						 	       
						         }
						         resultMap.put("serious", items);
								break;
							case "股权出质":
								 result = YHttpclicent.findgqcz("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("stock", items);
								break;
							case "动产抵押":
								 result = YHttpclicent.finddcdy("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("chattel", items);
								break;
							case "欠税公告":
								 result = YHttpclicent.findqsgg("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
								 resultMap.put("notice", items);
								break;
							case "债券信息":
								 result = YHttpclicent.findzqxx("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						    	     
						         }
						         resultMap.put("bond", items);
								break;
							case "招聘":
								 result = YHttpclicent.findzp("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						    	     
						         }
								 resultMap.put("recruit", items);
								break;
							case "抽查检查":
								 result = YHttpclicent.findccjc("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
								 resultMap.put("spotcheck", items);
								break;
							case "产品信息":
								  result = YHttpclicent.findCpxx(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("cpxx", items);
								break;
							case "证书":
								 result = YHttpclicent.findCert(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 System.out.println(resultall);
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
							        	 items=resultobj.getString("items");
							         }
						         }
						         resultMap.put("cert", items);
								break;
							case "舆情信息":
								 result = YHttpclicent.findopein(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("result");
						         if(reason!=null && !reason.equals("null") && reason.length()>0) {
						        	 net.sf.json.JSONObject totalobj = net.sf.json.JSONObject.fromObject(reason);
						             String total=totalobj.getString("total");
						             if(total!=null && !total.equals("0")){
						            	 items = totalobj.getString("items");
						             }
						         }
								 resultMap.put("publicopinion", items);
								break;
							case "专利":
								 result = YHttpclicent.findzlxx(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("zlxx", items);
								break;
							case "著作权":
								result = YHttpclicent.findzzq(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("zzq", items);
								break;
							case "网站备案":
								 result = YHttpclicent.findwzba(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("wzba", items);
								break;
							case "高管信息":
								 result = YHttpclicent.findExecutives(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("executives", items);
								break;
							case "行政许可":
								 result = YHttpclicent.findxzxk(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("xzxk", items);
								break;
							default:
								break;
							}
		    			}
					}
	    		}
	    		resultMap.put("result","0");
	    	}
		}else {
			//1.判断金额是否足够使用	 
			Integer oid = tblOrganizationMapper.findCompanyIdByDeptId(orgId);
			boolean flag = false;
			if( SystemStaticValue.WBSJSTAFFID.indexOf(staffId) == -1 ) {
				flag = true;
			}
	    	String surplusMoney = this.tblYyOrgDepositMapper.selectUrplusMoney(oid);
	    	
	    	if(surplusMoney == null && flag == false){
	    		resultMap.put("result", "-1");//账户余额不足，请充值
	    	}else {
	    		TblyyCompany company = this.tblYyCompanyMapper.selectByPrimaryKey(companyid);
	    		resultMap.put("company", JSONObject.toJSONString(company));
	    		if(company.getReportid() != null && flag == false) {
	    			Double money = this.tblYyOrgDepositMapper.selectReportTostMoney(company.getReportid()); 
	            	Double smoney = Double.valueOf(surplusMoney);	 
	            	if(smoney < money){
	            		resultMap.put("result", "-1");
	            		return resultMap;
	            	}
	    		}
	    		
	    		//2.查询所有报告信息；
	    		//2.1查询所有的接口
	    		List<Tblyyprice> priceList = this.tblyypriceMapper.selecAllList();
	    		resultMap.put("priceList", JSONObject.toJSONString(priceList));
	    		
	    		//2.2查询该公司报告用到的所有接口
	    		if(company.getReportid() != null) {
	    			TblYyReportModel model = this.tblYyReportModelMapper.selectByPrimaryKey(company.getReportid());
	    			resultMap.put("model", JSONObject.toJSONString(model));
	    			if(model.getPriceid() != null && !"".equals(model.getPriceid())) {
	    				
		    			List<Tblyyprice> ownerList = this.tblyypriceMapper.selectOwenerList(company.getReportid());
		    			//2.3保存查询消费记录
		    			if(flag == false) {
		    				this.dealUserQuery(oid,staffId,model);
		    			}
		    			
		    			//2.4 调用接口查询详细数据
		    			String result = null;
		    			String reason = null;
		    			String context = null;
		    			String items = null;
		    			net.sf.json.JSONObject jsonObject = null;
		    			net.sf.json.JSONObject resultobj = null;
		    			for (Tblyyprice price : ownerList) {
		    				items = null;
		    				context = null;
							switch (price.getInterfacename()) {
							case "基本信息":
								result = YHttpclicent.findGSXX("",company.getCompanyName());
								jsonObject = net.sf.json.JSONObject.fromObject(result);
						        reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
							        String resultall = jsonObject.getString("result");
							        resultobj = net.sf.json.JSONObject.fromObject(resultall);
							        JsonConfig jsonC = new JsonConfig();
								    jsonC.setExcludes(new String[] { "staffList" });
								    context = net.sf.json.JSONObject.fromObject(resultobj,jsonC).toString();
						         }
								resultMap.put("business", context);
								break;
							case "股东信息":
								result = YHttpclicent.findgdxx("",company.getCompanyName(),1);
								jsonObject = net.sf.json.JSONObject.fromObject(result);
						        reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	context = resultobj.getString("items");
						        	 }
						         }
								resultMap.put("shareholder", context);
								break;
							case "对外投资":
								 result = YHttpclicent.findDwtz("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items = resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("outbounds", items);
								break;
							case "变更信息":
								 result = YHttpclicent.findchange("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("chageInfo", items);
								break;
							case "分支机构":
								 result = YHttpclicent.findfzjg("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items = resultobj.getString("items");
						        	 }
						   	        
						         }
								resultMap.put("branch", items);
								break;
							case "融资历史":
								 result = YHttpclicent.finddrzls("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         items = null;
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("financing", items);
								break;
							case "投资事件":
								 result = YHttpclicent.finddeve("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("development", items);
								break;
							case "法律诉讼":
								 result = YHttpclicent.findfvss("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						 	        
						         }
						         resultMap.put("legal", items);
								break;
							case "法院公告":
								 result = YHttpclicent.findJudicialrisk("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items = resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("judicialrisk", items);
								break;
							case "失信人":
								 result = YHttpclicent.findsxr("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         items = null;
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
								resultMap.put("dishonest", items);
								break;
							case "被执行人":
								 result = YHttpclicent.findbzxr("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         items = null;
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("executor", items);
								break;
							case "开庭公告":
								result = YHttpclicent.findktgg("",company.getCompanyName(),1);
								jsonObject = net.sf.json.JSONObject.fromObject(result);
						        reason = jsonObject.getString("reason");
						        if(reason!=null && reason.equals("ok")){
							       	 String resultall = jsonObject.getString("result");
							       	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
							       	 String total = resultobj.getString("total");
							       	 if(total!=null && !total.equals("0")){
							       		items = resultobj.getString("items");
							       	}
						        }
								resultMap.put("noticecourt", items);
								break;
							case "经营异常":
								 result = YHttpclicent.findjyyc("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						        	 
						         }
						         resultMap.put("abnormal", items);
								break;
							case "行政处罚":
								 result = YHttpclicent.findRisk("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
								resultMap.put("businessRisk", items);
								break;
							case "严重违法":
								 result = YHttpclicent.findyzwf("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						 	       
						         }
						         resultMap.put("serious", items);
								break;
							case "股权出质":
								 result = YHttpclicent.findgqcz("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("stock", items);
								break;
							case "动产抵押":
								 result = YHttpclicent.finddcdy("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
						         resultMap.put("chattel", items);
								break;
							case "欠税公告":
								 result = YHttpclicent.findqsgg("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
								 resultMap.put("notice", items);
								break;
							case "债券信息":
								 result = YHttpclicent.findzqxx("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						    	     
						         }
						         resultMap.put("bond", items);
								break;
							case "招聘":
								 result = YHttpclicent.findzp("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						    	     
						         }
								 resultMap.put("recruit", items);
								break;
							case "抽查检查":
								 result = YHttpclicent.findccjc("",company.getCompanyName(),1);
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
						        	 	items=resultobj.getString("items");
						        	 }
						         }
								 resultMap.put("spotcheck", items);
								break;
							case "产品信息":
								  result = YHttpclicent.findCpxx(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("cpxx", items);
								break;
							case "证书":
								 result = YHttpclicent.findCert(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 System.out.println(resultall);
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 String total=resultobj.getString("total");
						        	 if(total!=null && !total.equals("0")){
							        	 items=resultobj.getString("items");
							         }
						         }
						         resultMap.put("cert", items);
								break;
							case "舆情信息":
								 result = YHttpclicent.findopein(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("result");
						         if(reason!=null && !reason.equals("null") && reason.length()>0) {
						        	 net.sf.json.JSONObject totalobj = net.sf.json.JSONObject.fromObject(reason);
						             String total=totalobj.getString("total");
						             if(total!=null && !total.equals("0")){
						            	 items = totalobj.getString("items");
						             }
						         }
								 resultMap.put("publicopinion", items);
								break;
							case "专利":
								 result = YHttpclicent.findzlxx(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("zlxx", items);
								break;
							case "著作权":
								result = YHttpclicent.findzzq(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("zzq", items);
								break;
							case "网站备案":
								 result = YHttpclicent.findwzba(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("wzba", items);
								break;
							case "高管信息":
								 result = YHttpclicent.findExecutives(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("executives", items);
								break;
							case "行政许可":
								 result = YHttpclicent.findxzxk(company.getCompanyName());
								 jsonObject = net.sf.json.JSONObject.fromObject(result);
						         reason = jsonObject.getString("reason");
						         if(reason!=null && reason.equals("ok")){
						        	 String resultall = jsonObject.getString("result");
						        	 resultobj = net.sf.json.JSONObject.fromObject(resultall);
						        	 items=resultobj.getString("items");
						         }
								 resultMap.put("xzxk", items);
								break;
							default:
								break;
							}
		    			}
					}
	    		}
	    		resultMap.put("result","0");
	    	}
		}
		return resultMap;
	}
	
	private void dealMysqlUserQuery(Integer companyId, Integer staffId, com.huabo.gkb.entitymysql.TblYyReportModel model) throws Exception {
		//查询调用的所有接口
		List<com.huabo.gkb.entitymysql.Tblyyprice> priceList = this.tblyypriceMySqlMapper.selectListByPriceIds(model.getPriceid());
		
		//查询公司余额
		com.huabo.gkb.entitymysql.TblYyOrgDeposit yod = this.tblYyOrgDepositMySqlMapper.selectOrgDepositByOrgId(companyId);
		
		//计算花费金额，准备查询记录的中间表数据
		List<com.huabo.gkb.entitymysql.TblYyQueryPrice> yqpList = new ArrayList<com.huabo.gkb.entitymysql.TblYyQueryPrice>(0);
		com.huabo.gkb.entitymysql.TblYyQueryPrice yqp = null;
		Double money = 0.0;
		for (com.huabo.gkb.entitymysql.Tblyyprice yp : priceList) {
			money = money + yp.getHbprice();
			yqp = new com.huabo.gkb.entitymysql.TblYyQueryPrice();
			yqp.setPriceid(yp.getPriceId());
			yqp.setPriceMoney(yp.getHbprice());
			yqpList.add(yqp);
		}
		com.huabo.gkb.entitymysql.TblYyUserQuery yuq = new com.huabo.gkb.entitymysql.TblYyUserQuery();
		
		yuq.setStaffid(new BigDecimal(staffId));
		yuq.setPaymoney(money);
		yuq.setQuerytime(new Date());
		yuq.setReportName(model.getReportname());
		yuq.setOrgid(new BigDecimal(companyId.toString()));
		
		this.tblYyUserQueryMySqlMapper.insertSelective(yuq);
		
		for (com.huabo.gkb.entitymysql.TblYyQueryPrice tyqp : yqpList) {
			tyqp.setRecordid(yuq.getRecordid().intValue());
			this.tblYyQueryPriceMySqlMapper.insertSelective(tyqp);
		}
		if(yod.getTotalpaymoney() != null){
			yod.setTotalpaymoney(yod.getTotalpaymoney()+money);
		}else{
			yod.setTotalpaymoney(money);
		}
		this.tblYyOrgDepositMySqlMapper.updateTotalMoneyById(yod);
	}

	@Transactional(value="oracleDataSourceTransactionManager",rollbackFor = Exception.class,timeout=36000)
	public void dealUserQuery(Integer companyId, Integer staffId, TblYyReportModel model)
			throws Exception {
		//查询接口模板
		
		//查询调用的所有接口
		List<Tblyyprice> priceList = this.tblyypriceMapper.selectListByPriceIds(model.getPriceid());
		
		//查询公司余额
		TblYyOrgDeposit yod = this.tblYyOrgDepositMapper.selectOrgDepositByOrgId(companyId);
		
		//计算花费金额，准备查询记录的中间表数据
		List<TblYyQueryPrice> yqpList = new ArrayList<TblYyQueryPrice>(0);
		TblYyQueryPrice yqp = null;
		Double money = 0.0;
		for (Tblyyprice yp : priceList) {
			money = money + yp.getHbprice();
			yqp = new TblYyQueryPrice();
			yqp.setPriceid(yp.getPriceId());
			yqp.setPriceMoney(yp.getHbprice());
			yqpList.add(yqp);
		}
		TblYyUserQuery yuq = new TblYyUserQuery();
		
		yuq.setStaffid(new BigDecimal(staffId));
		yuq.setPaymoney(money);
		yuq.setQuerytime(new Date());
		yuq.setReportName(model.getReportname());
		yuq.setOrgid(new BigDecimal(companyId.toString()));
		
		this.tblYyUserQueryMapper.insertSelective(yuq);
		
		for (TblYyQueryPrice tyqp : yqpList) {
			tyqp.setRecordid(yuq.getRecordid().intValue());
			this.tblYyQueryPriceMapper.insertSelective(tyqp);
		}
		if(yod.getTotalpaymoney() != null){
			yod.setTotalpaymoney(yod.getTotalpaymoney()+money);
		}else{
			yod.setTotalpaymoney(money);
		}
		this.tblYyOrgDepositMapper.updateByPrimaryKeySelective(yod);
	}

	@Override
	public void modifyReportJkInfo(String priceIds, String reportId) throws Exception {
		if(priceIds.trim().length() == 1) {
			priceIds = "";
		}else {
			priceIds = priceIds.substring(1, priceIds.length()-1);
		}
		if(SystemStaticValue.DataType == 0) {
			this.tblYyReportModelMySqlMapper.updateJkInfoById(priceIds,reportId);
		}else {
			this.tblYyReportModelMapper.updateJkInfoById(priceIds,reportId);
		}
		
	}
}
