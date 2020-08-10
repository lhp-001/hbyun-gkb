<%@ page language="java" import="java.util.*,org.apache.commons.lang.StringUtils" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html class="translated-ltr">
    <head><meta http-equiv="X-UA-Compatible"  content="IE=edge"/>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="divport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="generator" content="Gatsby 2.0.93">
        <title>管控宝</title>
        <link rel="stylesheet" type="text/css" href="../../../static/css/style.css" />
		<script src="../../../static/js/jquery-1.8.0.js" type="text/javascript"></script>
    </head>
   		 <style type="text/css">
				@media(max-width: 720px){
					body{
					  background-color: #F6F6F6;
					}
					.title{
						background: #e14c46;
						height: 40px;
						color:#fff;
						line-height: 40px;
						text-align: center;
						margin:0;
					}
					.head{
					  border-bottom: 1px solid #F0F0F0;
					  height: 100px;
					  background-color: #FFF;
					}
					
					.head-tip{
					  font-size: 31px;
					  height: 100px;
					  line-height: 100px;
					  color:#383838;
					  width: 100%;
					  text-indent: 1em;
					  display: inline-block;
					  font-weight: bold;
					}
					
					.head-text{
					  height: 100px;
					  line-height: 100px;
					  font-size: 26px;
					  color: #666666;
					}
					
					.list{
					  width: 100%;
					}
					
					.list-item{
					  margin-bottom: 20px;
					  width: 100%;
					  background-color: #FFFFFF;
					  padding: 30px 50px;
					}
					
					.item-content{
					  font-size: 26px;
					  line-height: 50px;
					}
					
					.con-head{
					  font-weight:400;
					}
					
					.con-text{
					  margin-left: 20px;
					  font-size:24px;
					}
					
					.con-deal{
					  font-size:22px;
					  text-align: right;
					  color: #E2514C;
					  float: right;
					  margin-right:3em; 
					}
				}
				@media(min-width: 720px){
					body{
					  background-color: #F6F6F6;
					}
					.title{
						background: #e14c46;
						height: 100px;
						color:#fff;
						line-height: 100px;
						text-align: center;
						margin:0;
						font-size:31px;
					}
					.head{
					  border-bottom: 1px solid #F0F0F0;
					  height: 100px;
					  background-color: #FFF;
					}
					
					.head-tip{
					  font-size: 31px;
					  height: 100px;
					  line-height: 100px;
					  color:#383838;
					  width: 100%;
					  text-indent: 1em;
					  display: inline-block;
					  font-weight: bold;
					}
					
					.head-text{
					  height: 100px;
					  line-height: 100px;
					  font-size: 26px;
					  color: #666666;
					}
					
					.list{
					  width: 100%;
					}
					
					.list-item{
					  margin-bottom: 20px;
					  width: 100%;
					  background-color: #FFFFFF;
					  padding: 30px 50px;
					}
					
					.item-content{
					  font-size: 26px;
					  line-height: 50px;
					}
					
					.con-head{
					  font-weight:400;
					}
					
					.con-text{
					  margin-left: 20px;
					  font-size:24px;
					}
					
					.con-deal{
					  font-size:22px;
					  text-align: right;
					  color: #E2514C;
					  float: right;
					  margin-right:3em; 
					}
				}
			</style>
    <body>
    	
    	<div>
    		<p class="title">管控宝</p>
			<div class="head">
			    <div class="head-tip">计划审批</div>
			    <div class="head-dtal">
			    </div>
			</div>
			<div class="list">
			  <div class="list-item">
			      <div class="item-content">
			        <text class="con-head">【计划编码】</text><text class="con-text">TSL-001</text>
			      </div>
			      <div class="item-content">
			        <text class="con-head">【计划名称】</text><text class="con-text">测试计划</text>
			      </div>
			      <div class="item-content">
			        <text class="con-head">【计划状态】</text><text class="con-text">未办理</text>
			        <text class="con-deal">办理</text>
			      </div>
			  </div>
			  <div class="list-item">
			      <div class="item-content">
			        <text class="con-head">【计划编码】</text><text class="con-text">TSL-002</text>
			      </div>
			      <div class="item-content">
			        <text class="con-head">【计划名称】</text><text class="con-text">测试计划</text>
			      </div>
			      <div class="item-content">
			        <text class="con-head">【计划状态】</text><text class="con-text">未办理</text>
			        <text class="con-deal">办理</text>
			      </div>
			  </div>
		</div>
    </body>
    <script type="text/javascript">
    	
	</script>
</html>
