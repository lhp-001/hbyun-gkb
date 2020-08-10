//打印之前隐藏不想打印出来的信息 
	function beforePrint(){
	   $('.noprint').css("display","none");
	} 
	//打印之后将隐藏掉的信息再显示出来 
	function afterPrint(){ 
	   $('.noprint').css("display","block");
	}
	function pagesetup_null(){                
	    var hkey_root,hkey_path,hkey_key;
	    hkey_root="HKEY_CURRENT_USER";
	    hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
	    try{
	        var RegWsh = new ActiveXObject("WScript.Shell");
	        hkey_key="header";
	        RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
	        hkey_key="footer";
	        RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
	    }catch(e){}
	}
	
	function getExplorer() {
	    var explorer = window.navigator.userAgent ;
	    //ie
	    if (explorer.indexOf("MSIE") >= 0) {
	        return "IE";
	    }
	    //firefox
	    else if (explorer.indexOf("Firefox") >= 0) {
	        return "Firefox";
	    }
	    //Chrome
	    else if(explorer.indexOf("Chrome") >= 0){
	        return "Chrome";
	    }
	    //Opera
	    else if(explorer.indexOf("Opera") >= 0){
	        return "Opera";
	    }
	    //Safari
	    else if(explorer.indexOf("Safari") >= 0){
	        return "Safari";
	    }
	}
	
	function windowprint() {
		 if(getExplorer() == "IE"){
	            pagesetup_null();
	        }
		bdhtml=window.document.body.innerHTML;
		sprnstr="<!--startprint-->"; //开始打印标识字符串有17个字符
		eprnstr="<!--endprint-->"; //结束打印标识字符串
		prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17); //从开始打印标识之后的内容
		prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr)); //截取开始标识和结束标识之间的内容
		window.document.body.innerHTML=prnhtml; //把需要打印的指定内容赋给body.innerHTML
		window.print(); //调用浏览器的打印功能打印指定区域
		window.document.body.innerHTML=bdhtml; // 最后还原页面
	}