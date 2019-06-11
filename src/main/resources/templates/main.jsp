<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.cqut.mycourse.entity.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	HttpSession sessions = request.getSession();
	UserEntity user = (UserEntity) session.getAttribute("user");
	long roleId=user.getRoleId();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>松度电脑开票业务系统</title>
<link rel="stylesheet" href="plugins/layui/css/layui.css">
<script src="js/jquery1.9.1.js"></script>
<script src="plugins/layui/layui.js"></script>
<style type="text/css">
   @font-face {font-family: "iconfont";
          src: url('plugins/myfont/iconfont.eot'); /* IE9*/
          src: url('plugins/myfont/iconfont.eot#iefix') format('embedded-opentype'), /* IE6-IE8 */
          url('plugins/myfont/iconfont.woff') format('woff'), /* chrome, firefox */
          url('plugins/myfont/iconfont.ttf') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
          url('plugins/myfont/iconfont.svg#iconfont') format('svg'); /* iOS 4.1- */
        }

        .iconfont {
          font-family:"iconfont" !important;
          font-size:16px;
          font-style:normal;
          -webkit-font-smoothing: antialiased;
          -webkit-text-stroke-width: 0.2px;
          -moz-osx-font-smoothing: grayscale;
        }
         @font-face {font-family: "iconfont";
          src: url('plugins/loginOut/iconfont.eot'); /* IE9*/
          src: url('plugins/loginOut/iconfont.eot#iefix') format('embedded-opentype'), /* IE6-IE8 */
          url('plugins/loginOut/iconfont.woff') format('woff'), /* chrome, firefox */
          url('plugins/loginOut/iconfont.ttf') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
          url('plugins/loginOut/iconfont.svg#iconfont') format('svg'); /* iOS 4.1- */
        }

        .loginOut {
          font-family:"iconfont" !important;
          font-size:16px;
          font-style:normal;
          -webkit-font-smoothing: antialiased;
          -webkit-text-stroke-width: 0.2px;
          -moz-osx-font-smoothing: grayscale;
        }
  </style>
</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo">松度电脑开票业务系统</div>

			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item" style="font-size: 20px; color: white;"><i class="layui-icon" style="font-size: 20px;color: #1E9FFF;">&#xe612;</i>:<%=user.getRealName()%>
			</li>
				<li class="layui-nav-item" style="margin-left: 50px" onclick="loginOut()"><i class="loginOut" style="font-size: 30px; color: red;">&#xe618;</i></li>
			</ul>
		</div>

		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
				<ul class="layui-nav layui-nav-tree" lay-filter="test" id='menu' >
				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<iframe src="main.html" style="width: 100%" name="mainIframe" id="ifame"></iframe>
		</div>

		<div class="layui-footer" >
			<!-- 底部固定区域 -->
			<p style="margin-left:45%">© 2018毕业设计 by ybl</p>
		</div>
	</div>
	<script>
	var layer;
	function loginOut(){
		layer.confirm('真的要退出系统吗', function(index) {
				window.location.href="signOut.do"
			});
	}
	function createMenu(menus){
		var fMenus=new Array();
		var menu="";
		var count=0;
		for(var i=0;i<menus.length;i++){
			if(menus[i].parentId==0){
				fMenus[count]=menus[i];
				count++;
			}
		}
		for(var i=0;i<fMenus.length;i++){
			var menuId=fMenus[i].menuId;
			menu+='<li class="layui-nav-item layui-nav-itemed"><a class='+'"'+'"'+' href="javascript:;">';
			menu+="<i class='iconfont'style='margin-right:10px'>"+fMenus[i].icon+"</i>";
			menu+=fMenus[i].menuName+'</a>';
			menu+='<dl class="layui-nav-child">';
			for(var j=0;j<menus.length;j++){
			if(menus[j].parentId==menuId){
				menu+='<dd><a href='+'"'+'/'+menus[j].url+'"';
				
				menu+= 'target="mainIframe">'+"<i class='iconfont' style='margin-right:10px'>"+menus[j].icon+"</i>"+menus[j].menuName+'</a></dd>';
				}
				
			}
			menu+='</dl></li>';	
		}
		$('#menu').html(menu);
	}
		function getMenu() {
			var roleId=<%=roleId %>;
			$.ajax({
				url : '<%=path%>/getRoleMenu/' + roleId,
				data : {},
				type : 'post',
				async:false,
				dataType : 'json',
				success : function(r) {
					//console.log(r.data);
					createMenu(r.data);
				}
			});
		}
			//动态设置地图窗口高度
			function setWindows() {
				var winWidth = -500;
				var winHeight = -115;
				if(document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) {
					winHeight += document.documentElement.clientHeight;
					winWidth = document.documentElement.clientWidth;
				}
				document.getElementById("ifame").style.height = winHeight + 'px';
				//document.getElementById("ifame").style.width = winWidth + 'px';
			}
		//JavaScript代码区域
		layui.use(['element','layer'], function() {
			var element = layui.element;	
			 layer = layui.layer;
			setWindows();
			getMenu();
			element.init();
		});
		
	</script>
</body>
</html>