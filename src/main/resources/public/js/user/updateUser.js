//将layui的相关模块申明为全局变量，随时可调用
var form = null;
var laydate = null;
var table = null;
var laypage = null;

var layer = null;

	
var vm = new Vue({
	el : '#main',
	data : {
		userName : "",
		realName : "",
		datas : [],
		length : 0,
		user:{
			userId:'',
			userName:'',
			sex:'',
			password:'',
			userPhone:'',
			email:'',
			roleId:'',	
			realName:''
		}
	},
	methods : {
		test:function(){
		
		}
	}
});
window.onload=function(){
	var name = '${id}';
	alert("666"); 	
   alert(name);	
  // getRequest();
};
function getRequest() {
	   var url = window.location.search; //获取url中"?"符后的字串
	   var theRequest = new Object();
	   if (url.indexOf("?") != -1) {
	     var str = url.substr(1);
	     strs = str.split("&");
	     for(var i = 0; i < strs.length; i ++) {
	       theRequest[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);
	     }
	   }
	   console.log(url);
	   return theRequest;
	 }