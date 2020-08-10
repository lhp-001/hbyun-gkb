/**
 * 		方法名									传入参数									作用
 * exportExcel()																		ajax访问后台导出方法
 * selectAllCheckBox(e)							tr的对象									切换页面全选、取消的状态;导出专用
 * exportSetId(e)								当前checkBox对象							将checkBox的值放入cookie
 * judgeCheckBoxStatue(e)						某个checkBox对象							判断该页面所有的checkbox的选中状态，修改全部 取消的字样
 * exportSetId(exportObj,$(e).val())			放置对象的值，放置的值					将需要导出的Id放入导出Id的隐藏域 拼接，返回结果
 * exportRemoveId(exportObj,$(e).val())			移除对象的值，移除的值					将不需要导出的值从导出Id的隐藏域移除，返回结果
 * */
function exportExcel(){
	var exports = $("[name='exportId']").val();
	if(exports.trim().length == 0){
		alert("请选择");
		return;
	}
	
	$.ajax({
		type: "POST",
		url:"/util/exportExcel",
		data:{exports:exports},
		cache: true,
		async: false,
		success: function(res) {
		}
	});
	
}


function selectAllCheckBox(e){
	//获取页面所有的checkbox
	var allCheckObj = $("input[type='checkBox']");
	var exportObj = $("[name='exportId']");
	var evalue = $(exportObj[0]).val();
	var cvalue;
	//修改 全部 取消的状态；
	if($(e).text()=="全部"){
		allCheckObj.attr("checked",true);
		$(e).text("取消");
	}else{
		allCheckObj.attr("checked",false);
		$(e).text("全部");
	}
	allCheckObj.each(function (index) {
		doExportId(allCheckObj[index]);
    })
}

function doExportId(e){
	var exportObj = $("[name='exportId']");
	var evalue = $(exportObj[0]).val();
	var cvalue = $(e).val();
	var str ;
	if($(e).is(':checked') == true){
		str = exportSetId(evalue,cvalue);
	}else{
		str = exportRemoveId(evalue,cvalue);
	}
	exportObj.val(str);
	judgeCheckBoxStatue(e);
}

function judgeCheckBoxStatue(e){
	var flag = true;
	var allCheckObj = $("input[type='checkBox']");
	for(var i =0;i<allCheckObj.length;i++){
		if($(allCheckObj[i]).is(':checked') == false){
			flag = false;
			break;
		}
	}
	if(flag){
		$($(e).parents("table").find("td")[0]).text("取消");
	}else{
		$($(e).parents("table").find("td")[0]).text("全部");
	}
}

function exportSetId(exportValue,exprotId){
	if(exportValue.trim().length == 0){
		return exprotId;
	}else{
		return exportValue+","+exprotId;
	}
}

function exportRemoveId(exportValue,exprotId){
    var strArr = exportValue.split(',');
    var endStr = "";
    var flag = true;
    for(var i = 0; i<strArr.length;i++){
    	if(strArr[i] != exprotId){
    		if(flag){
    			flag = false;
    			endStr = strArr[i]
    		}else{
    			endStr += ","+strArr[i]
    		}
    	}
    }
	return endStr;
}