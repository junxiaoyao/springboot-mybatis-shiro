//将layui的相关模块申明为全局变量，随时可调用
var form = null;
var laydate = null;
var table = null;
var laypage = null;
var $ = null;// 这个类似于jquery里的$选择符，不要再重复引用jquery.js
var upload=null;
var ticketNum='';
var vm = new Vue({
	el : '#main',
	data : {
		ticketNum : '',
		customerName : '',
		datas : [],
		length : 0,
		ticket:{
			ticketId:'',
			type:'',
			remark:''
		},
		customers:[]
	},
	methods : {
		queryData : function(pageNum) {
			vm.ticketNum = $("#sTicketNum").val();
			vm.customerName = $("#sCustomer").val();
			$.ajax({
				url : 'getRticketList.do',
				type : 'post',
				data : {
					'ticketNum' : vm.ticketNum,
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

		},
		count : function() {
			vm.ticketNum = $("#sTicketNum").val();
			vm.customerName = $("#sCustomer").val();
			$.ajax({
				url : 'getRticketCount.do',
				type : 'post',
				data : {
					'ticketNum' : vm.ticketNum,
					 'customerName' : vm.customerName,
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
			this.queryData(1);
			laypage.render({
				elem : 'pagination',
				count : vm.length,
				limit : 10,
				layout : [ 'count', 'prev', 'page', 'next', 'skip' ],
				jump : function(obj) {
					console.log(obj.curr);
					vm.queryData(obj.curr);
				}
			});
		},
		iniTable : function() {
			table.render({
				elem : '#idTest'
				,id : 'rTicketId,pic',
				cols : [ [ {
					field : 'ticketNum',
					sort : true,
					width : 200,
					align : 'center',
					title : '发票号码'
				}, 
				 {
					field : 'ticketCode',
					sort : true,
					width : 200,
					align : 'center',
					title : '发票代码'
				}, 
				{
					field : 'customerName',
					sort : true,
					align : 'center',
					width : 110,
					title : '客户'
				}, {
					field : 'type',
					sort : true,
					width : 120,
					align : 'center',
					title : '类型'
				}, {
					field : 'realName',
					sort : true,
					width : 165,
					align : 'center',
					title : '办理人'
				},  {
					field : 'remark',
					sort : true,
					minWidth : 110,
					align : 'center',
					title : '备注'
				},{
					field : 'createTime',
					sort : true,
					minWidth : 110,
					title : '创建时间'
				}, {
					fixed : 'right',
					title : '操作',
					width : 200,
					align : 'center',
					toolbar : '#barDemo'
				} ] ],
				data : vm.datas
			// ,data:[{"createTime":"2018-04-18
			// 16:35:10.0","email":"11@qq.com","password":"admin","realName":"管理员","roleId":1,"userId":1,"userName":"admin","userPhone":"12345678901"}]
			});
		},
		getTickets:function(){
			//var customerName=$('#mCustomer').val();
			$.ajax({
				url : 'getTicketNum.do',
				type : 'post',
				async : false,
				data:{},
				dataType : 'json',
				success : function(r) {
					var list = r.tickets;
					//debugger;
					$("#mTicketNum").empty();
					$("#mTicketNum").append('<option value="">直接选择或搜索选择</option>');
					for (var i = 0; i < list.length; i++) {
						$("#mTicketNum")
								.append(
										"<option value="
												+ list[i].ticketId + ">"
												+ list[i].ticketNum
												+ "</option>");
					}
					 
				}
			});
		},add:function(){
			
				//vm.ticket.ticketId='';
				this.getTickets();
				form.render('select');
				//$('#ticketID').val(vm.ticket.ticketId);
				//日期时间选择器
				laydate.render({ 
				  elem: '#ticketTime'
				  ,type: 'datetime'
					 ,theme: 'molv'
				});
				// do something
				layer.open({
					title : '新增退废票',
					type : 1,
					content : $('#addTicket'),
					area : [ '400px', '400px' ]
				});
			
		},addData:function(){
			ticketNum = $('#ticketNum').val();
			vm.ticket.moneyAccount = $('#moneyAccount').val();
			vm.ticket.paymentMethod = $('#paymentMethod').val();
			vm.ticket.customerId = $('#mCustomer').val();
			vm.ticket.reasonPayment = $('#reasonPayment').val();
			vm.ticket.ticketTime = $('#ticketTime').val();
			console.log("赋值");
			console.log(ticketNum);
			console.log(vm.ticket.moneyAccount);
			console.log(vm.ticket.paymentMethod);
			console.log(vm.ticket.customerId);
			console.log(vm.ticket.reasonPayment);
		}
	}
});

layui.use([ 'form', 'laydate', 'element', 'table', 'laypage', 'upload' ],
				function() {
					form = layui.form;
					laydate = layui.laydate;
					table = layui.table;
					laypage = layui.laypage;
					$ = layui.$;
					// 请求数据
					vm.count();
					 form.on('submit(demo)', function(data){
						 vm.ticket.ticketId=$('#mTicketNum').val();
						 vm.ticket.type=$('#mType').val();
						 vm.ticket.remark=$('#remark').val();
						 $.ajax({
								url:'addRticket.do',
								dataType : 'json',
				                 data:{'ticketId':vm.ticket.ticketId,'type': vm.ticket.type,'remark': vm.ticket.remark},
				                 async:false,
				                 type:'post',
				                 success:function(r){
				                	
				                 }
							});
						 layer.close(layer.index);
	                
	                	 vm.count();
						    return false;	    
						  });
					// vm.iniPagination();
					table.on('tool(bar)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
						// lay-filter="对应的值"
						var data = obj.data; // 获得当前行数据
						var layEvent = obj.event; // 获得 lay-event 对应的值（也可以是表头的
													// event 参数对应的值）
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
								content : $('#addTicket'),
								area : [ '450px', '500px' ]
							});

							// 同步更新缓存对应的值
							obj.update({
								userName : vm.user.userName,
							});

						}else{
							 document.getElementById("pics").src='/images/'+data.pic;
							layer.open({
								title : '票据原件',
								type : 1,
								content : $('#showPic'),
								area : [ '610px', '460px' ]
							});
						}
					});
					//自定义验证规则
					form.verify({
						title : function(value) {
							parent.layer.alert(value);
						}
					});
				});