//将layui的相关模块申明为全局变量，随时可调用
var form = null;
var laydate = null;
var table = null;
var laypage = null;
var $ = null;
var layer = null;
var vm = new Vue({
	el : '#main',
	data : {
		userName : "",
		realName : "",
		datas : [],
		length : 0,
		user : {
			userId :null,
			userName : '',
			sex : '',
			password : '',
			userPhone : '',
			email : '',
			roleId : '',
			realName : ''
		},
		roleList : []
	},
	methods : {
		queryData : function(pageNum) {
			vm.userName = $("#userName").val();
			vm.realName = $("#realName").val();
			$.ajax({
				url : 'getUsers.do',
				type : 'post',
				data : {
					'userName' : vm.userName,
					'realName' : vm.realName,
					'pageNum' : pageNum
				},
				async : false,
				dataType : 'json',
				success : function(r) {
					vm.datas = r.data;
				}
			});
			// console.log(vm.datas);
			this.iniTable();
			// this.count();
		},
		search : function() {

			vm.userName = $("#userName").val();
			vm.realName = $("#realName").val();
			$.ajax({
				url : 'getUsers.do',
				type : 'post',
				data : {
					'userName' : vm.userName,
					'realName' : vm.realName,
					'pageNum' : 1
				},
				async : false,
				dataType : 'json',
				success : function(r) {
					vm.datas = r.data;
					this.count();
				}
			});
			this.iniTable();

		},
		count : function() {
			vm.userName = $("#userName").val();
			vm.realName = $("#realName").val();
			$.ajax({
				url : 'getUsersCount.do',
				type : 'post',
				data : {
					userName : vm.userName,
					realName : vm.realName,
				},
				async : false,
				dataType : 'json',
				success : function(r) {
					vm.length = r.count;
					// console.log(r.data);
				}
			});
			this.iniPagination();
		},
		iniPagination : function() {
			laypage.render({
				elem : 'pagination',
				count : vm.length,
				limit : 10,
				layout : [ 'count', 'prev', 'page', 'next', 'skip' ],
				jump : function(obj) {
					console.log(obj);
					vm.queryData(obj.curr);
				}
			});
			this.queryData(1);
		},
		iniTable : function() {
			table.render({
				elem : '#datas',
				id:'userId,roleId',
				cols : [ [
				 {
					field : 'userName',
					sort : true,
					width : 110,
					align : 'center',
					title : '用户名'
				}, {
					field : 'realName',
					sort : true,
					width : 110,
					align : 'center',
					title : '真实姓名'
				}, {
					field : 'sex',
					sort : true,
					width : 110,
					align : 'center',
					title : '性别'
				}, 
				{
					field : 'roleName',
					sort : true,
					width : 110,
					align : 'center',
					title : '用户角色'
				},
				{
					field : 'userPhone',
					sort : true,
					width : 150,
					align : 'center',
					title : '联系电话'
				}, {
					field : 'email',
					sort : true,
					width : 200,
					align : 'center',
					title : '电子邮件'
				}, {
					field : 'createTime',
					sort : true,
					minWidth : 110,
					align : 'center',
					title : '创建时间'
				}, {
					fixed : 'right',
					title : '操作',
					width : 120,
					align : 'center',
					toolbar : '#barDemo'
				} ] ],
				data : vm.datas
			});
		},
		getRoleList : function() {
			//layer.msg("获取角色");
			$.ajax({
				url : 'getAllRole.do',
				type : 'post',
				async : false,
				dataType : 'json',
				success : function(r) {
					vm.roleList = r.data;
					//debugger;
					$("#mRole").empty();
					for (var i = 0; i < vm.roleList.length; i++) {
						$("#mRole")
								.append(
										"<option value="
												+ vm.roleList[i].roleId + ">"
												+ vm.roleList[i].roleName
												+ "</option>");
					}

				}
			});
			// console.log(vm.roleList);
		},
		del : function(id) {
			$.ajax({
				url : 'deleteUser/' + id,
				type : 'post',
				async : 'false',
				success : function(r) {
						layer.msg("操作成功");
				}
			});
			this.count();
		},
		add:function(){
			this.getRoleList();
			 vm.user.userId=null;
			 vm.user.userName='';
			 vm.user.sex='';
			 vm.user.realName='';
			 vm.user.password='';
			 vm.user.userPhone='';
			 vm.user.email='';
			 vm.user.roleId='';
			form.render('select');
			$('#mUserName').val('');
			$('#mSex').val('');
			$('#mRealName').val('');
			$('#mPassword').val('');
			$('#mUserPhone').val('');
			$('#mEmail').val('');
			$('#mRole').val('');
		
			layer.open({
				title : '新增用户',
				type : 1,
				content : $('#updateUser'),
				area : [ '450px', '500px' ]
			});
		}
	}
});

layui.use([ 'form', 'laydate', 'table', 'laypage', 'layer' ], function() {
	form = layui.form;
	laydate = layui.laydate;
	table = layui.table;
	laypage = layui.laypage;
	$ = layui.$;
	layer = layui.layer;
	// 请求数据
	// vm.queryData(1);
	vm.count();
	 form.on('submit(demo)', function(data){
		 vm.user.userName=$('#mUserName').val();
		 vm.user.sex=$('#mSex').val();
		 vm.user.realName=$('#mRealName').val();
		 vm.user.password=$('#mPassword').val();
		 vm.user.userPhone=$('#mUserPhone').val();
		 vm.user.email=$('#mEmail').val();
		 vm.user.roleId=$('#mRole').val();
		 console.log(vm.user.userId);
			$.ajax({
				url:vm.user.userId!=null?'updateUser.do':'addUser.do',
				dataType : 'json',
                 data:{'userId':vm.user.userId,'userName':vm.user.userName,'sex':vm.user.sex,'realName': vm.user.realName,'password':hex_md5(vm.user.password),'userPhone':vm.user.userPhone,'email': vm.user.email,'roleId': vm.user.roleId},
                 async:false,
                 type:'post',
                 success:function(r){
                	 debugger;
                	if(r.msg<1){
                		 layer.msg("用户名已存在");
                	}else if(r.msg==1){
    					layer.msg("操作成功");
    				}else{
    					layer.msg("操作失败");
    				}
                	 layer.closeAll('page');
                	 $('#updateUser').hide();
                	 vm.count();
                 }
			});
		    return false;	    
		  });
	// 监听工具条
	table.on('tool(bar)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
											// lay-filter="对应的值"
		var data = obj.data; // 获得当前行数据
		var layEvent = obj.event; // 获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		var tr = obj.tr; // 获得当前行 tr 的DOM对象
	
		if (layEvent === 'del') { // 删除
			layer.confirm('真的删除行么', function(index) {
				obj.del(); // 删除对应行（tr）的DOM结构，并更新缓存
				layer.close(index);
				// 向服务端发送删除指令
				vm.del(data.userId);
			});
		} else if (layEvent === 'edit') {
			// debugger;
			vm.getRoleList();
			vm.user.userId = data.userId;
			// location.href="updateUser/"+vm.user.userId;
			// form.render('select','selFilter');
			vm.user.userName = data.userName;
			vm.user.sex = data.sex;
			// console.log(vm.user.sex);
			vm.user.userId = data.userId;
			vm.user.realName = data.realName;
			vm.user.password = data.password;
			vm.user.userPhone = data.userPhone;
			vm.user.email = data.email;
			vm.user.roleId = data.roleId;
			/*console.log(data.userId);
			console.log(vm.user.roleId);*/
			$('#mUserName').val(vm.user.userName);
			$('#mSex').val(vm.user.sex);
			$('#mRealName').val(vm.user.realName);
			$('#mPassword').val(vm.user.password);
			$('#mUserPhone').val(vm.user.userPhone);
			$('#mEmail').val(vm.user.email);
			$('#mRole').val(vm.user.roleId);
			form.render('select');
			// do something
			layer.open({
				title : '编辑用户',
				type : 1,
				content : $('#updateUser'),
				area : [ '450px', '500px' ]
			});
		}
	});
});