<i><%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("id:"+request.getAttribute("id"));
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'updateUser.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" href="css/global.css">
<link rel="stylesheet" href="plugins/layui/css/layui.css">
<script src="js/jquery.js"></script>
<script src="plugins/VueJS/vue.min.js"></script>
</head>

<body>
	<div id="updateUser">
<form class="layui-form" action="">
			<div class="layui-form-item">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">用户名</label>
						<div class="layui-input-inline">
							<input type="tel" name="userName" id="mUserName" v-model="user.userName" autocomplete="off" class="layui-input">
						</div>
					</div>

				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">真实姓名</label>
					<div class="layui-input-inline">
						<input type="text" name="mRealName" v-model="user.realName" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">性别</label>
						<div class="layui-input-block">
							<select name="sex" id="mSex" lay-verify="required">
							<option value="男">男</option>
							<option value="女">女</option>
						</select>
						</div>
					</div>

				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">联系电话</label>
					<div class="layui-input-inline">
						<input type="text" name="mUserPhone" id="mUserPhone" lay-verify="required|phone" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">电子邮件</label>
						<div class="layui-input-inline">
							<input type="tel" name="email" id="mEmail" lay-verify="email" autocomplete="off" class="layui-input">
						</div>
					</div>

				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">角色</label>
					<div class="layui-input-block">
						<select name="mRole" id="mRole" lay-verify="required">
						
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
</div>
<script type="text/javascript">
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
		query:function(id){
			
		}
	}
});
window.onload=function(){
	var id = '${id}';
};

</script>
</body>
</html>