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
		customerName : "",
		datas : [],
		length : 0,
		customer : {
			customerId : '',
			customerName : '',
			customerAddress : '',
			customerPhone : '',
			email : '',
			company:''
		},
		roleList : []
	},
	methods : {
		queryData : function(pageNum) {
			vm.customerName = $("#customerName").val();
			$.ajax({
				url : 'getCustomerList.do',
				type : 'post',
				data : {
					'customerName' : vm.customerName,
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
			console.log("查询数量");
			vm.customerName = $("#customerName").val();
			$.ajax({
				url : 'getCustomerCount.do',
				type : 'post',
				data : {
					customerName : vm.customerName,
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
					vm.queryData(obj.curr);
				}
			});
			this.queryData(1);
		},
		iniTable : function() {
			table.render({
				elem : '#idTest'
				// ,url: 'user/getUsers'
				,
				id : 'customerId',

				cols : [ [ {
					field : 'customerName',
					sort : true,
					width : 110,
					align : 'center',
					title : '客户姓名'
				}, {
					field : 'customerAddress',
					sort : true,
					width : 200,
					align : 'center',
					title : '地址'
				}, {
					field : 'customerPhone',
					sort : true,
					width : 150,
					align : 'center',
					title : '联系电话'
				}, {
					field : 'company',
					sort : true,
					width : 200,
					align : 'center',
					title : '公司名称'
				},{
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
			// ,data:[{"createTime":"2018-04-18
			// 16:35:10.0","email":"11@qq.com","password":"admin","realName":"管理员","roleId":1,"userId":1,"userName":"admin","userPhone":"12345678901"}]
			});
		},
		del : function(id) {
			$.ajax({
				url : 'deleteCustomer/' + id,
				type : 'get',
				async : 'false',
				success : function(r) {

				}
			});
			this.count();
		},
		update : function() {
			$.ajax({
				url : 'updateUser.do',
				contentType : "application/json",
				data : JSON.stringify(vm.user),
				async : false,
				type : 'post',
				success : function(r) {

				}
			});
		},
		add : function() {
			/*
			 * 'customerId' : vm.customer.customerId, 'customerName' :
			 * vm.customer.customerName, 'customerAddress'
			 * :vm.customer.customerAddress, 'customerPhone' :
			 * vm.customer.customerPhone, 'email' : vm.customer.email,
			 */
			vm.customer.customerId = null;
			vm.customer.customerName = '';
			vm.customer.customerAddress = '';
			vm.customer.customerPhone = '';
			vm.customer.email = '';
			vm.customer.company = '';
			$('#mcustomerName').val('');
			$('#customerAddress').val('');
			$('#customerPhone').val('');
			$('#email').val('');
			$('#company').val('');
			layer.open({
				title : '新增客户',
				type : 1,
				content : $('#auCustomer'),
				area : [ '400px', '400px' ]
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
	form.on('submit(demo)', function(data) {
		console.log(data);
		vm.customer.customerName = $('#mcustomerName').val();
		vm.customer.customerAddress = $('#customerAddress').val();
		vm.customer.customerPhone = $('#customerPhone').val();
		vm.customer.email = $('#email').val();company
		vm.customer.company = $('#company').val();
		$.ajax({
			url : vm.customer.customerId!= null ? 'updateCustomer.do':'addCustomer.do',
			dataType : 'json',
			data : {
				'customerId' : vm.customer.customerId,
				'customerName' : vm.customer.customerName,
				'customerAddress' : vm.customer.customerAddress,
				'customerPhone' : vm.customer.customerPhone,
				'email' : vm.customer.email,
				'company':vm.customer.company
			},
			async : false,
			type : 'post',
			success : function(r) {
				if(r.msg==1){
					layer.msg("操作成功");
				}else{
					layer.msg("操作失败");
				}
				layer.closeAll('page');
				$('#auCustomer').hide();
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
				vm.del(data.customerId);
			});
		} else if (layEvent === 'edit') {

			vm.customer.customerName = data.customerName;
			vm.customer.customerId = data.customerId;
			vm.customer.customerAddress = data.customerAddress;
			vm.customer.customerPhone = data.customerPhone;
			vm.customer.email = data.email;
			vm.customer.company =data.company;;
			/*
			 * console.log(data.userId); console.log(vm.user.roleId);
			 */
			$('#mcustomerName').val(vm.customer.customerName);
			$('#customerAddress').val(vm.customer.customerAddress);
			$('#customerPhone').val(vm.customer.customerPhone);
			$('#email').val(vm.customer.email);
			$('#company').val(vm.customer.company);
			// do something
			layer.open({
				title : '编辑客户',
				type : 1,
				content : $('#auCustomer'),
				area : [ '400px', '400px' ]
			});
		}
	});
});