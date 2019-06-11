//将layui的相关模块申明为全局变量，随时可调用
var form = null;
var laydate = null;
var table = null;
var laypage = null;
var $ = null;// 这个类似于jquery里的$选择符，不要再重复引用jquery.js
var upload = null;
var ticketNum = '';
var layer = null;
var ticketId="";
function testNum() {
	var value = $("#ticketNum").val();
	if (value.length < 8) {
		layer.msg('发票号码格式不对');
		$('#addButton').addClass("layui-btn-disabled");
	} else {
		$('#addButton').removeClass("layui-btn-disabled");
		$.ajax({
			url : 'selectTicket.do',
			type : 'post',
			data : {
				'value' : value,
				'column' : 'ticketNum',
				'ticketId':''
			},
			async : false,
			dataType : 'json',
			success : function(r) {
				if (r.type > 0) {
					layer.msg('发票号码已存在');
					$('#addButton').addClass("layui-btn-disabled");
				} else {
					$('#addButton').removeClass("layui-btn-disabled");
				}
			}
		});
	}
}
function mtestNum() {
	var value = $("#mticketNum").val();
	if (value.length < 8) {
		layer.msg('发票号码格式不对');
		$('#upBtn').addClass("layui-btn-disabled");
	} else {
		$('#upBtn').removeClass("layui-btn-disabled");
		$.ajax({
			url : 'selectTicket.do',
			type : 'post',
			data : {
				'value' : value,
				'column' : 'ticketNum',
				'ticketId' : vm.ticket.ticketId
			},
			async : false,
			dataType : 'json',
			success : function(r) {
				if (r.type > 0) {
					layer.msg('发票号码已存在');
					$('#upBtn').addClass("layui-btn-disabled");
				} else {
					$('#upBtn').removeClass("layui-btn-disabled");
				}
			}
		});
	}
}
function mtestCode() {
	var value = $("#mticketCode").val();
	if (value.length < 8) {
		layer.msg('发票代码格式不对');
		$('#upBtn').addClass("layui-btn-disabled");
	} else {
		$('#upBtn').removeClass("layui-btn-disabled");
		$.ajax({
			url : 'selectTicket.do',
			type : 'post',
			data : {
				'value' : value,
				'column' : 'ticketCode',
				'ticketId' : vm.ticket.ticketId
			},
			async : false,
			dataType : 'json',
			success : function(r) {
				if (r.type > 0) {
					layer.msg('发票代码已存在');
					$('#upBtn').addClass("layui-btn-disabled");
				} else {
					$('#upBtn').removeClass("layui-btn-disabled");
				}
			}
		});
	}
}
function testCode() {
	var value = $("#ticketCode").val();
	if (value.length < 12) {
		layer.msg('发票代码格式不对');
		$('#addButton').addClass("layui-btn-disabled");
	} else {
		$('#addButton').removeClass("layui-btn-disabled");
		$.ajax({
			url : 'selectTicket.do',
			type : 'post',
			data : {
				'value' : value,
				'column' : 'ticketCode',
				'ticketId':''
			},
			async : false,
			dataType : 'json',
			success : function(r) {
				if (r.type > 0) {
					layer.msg('发票代码已存在');
					$('#addButton').addClass("layui-btn-disabled");
				} else {
					$('#addButton').removeClass("layui-btn-disabled");
				}
			}
		});
	}
}
var vm = new Vue({
	el : '#main',
	data : {
		ticketNum : '',
		customerName : '',
		datas : [],
		length : 0,
		ticket : {
			ticketId : '',
			ticketNum : '',
			ticketCode : '',
			moneyAccount : '',
			customerId : '',
			reasonPayment : '',
			ticketTime : '',
			pic : ''
		},
		customers : []
	},
	methods : {
		queryData : function(pageNum) {
			vm.ticketNum = $("#sTicketNum").val();
			vm.customerName = $("#sCustomer").val();
			$.ajax({
				url : 'getTicketList.do',
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
				url : 'getTicketCount.do',
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
					vm.queryData(obj.curr);
				}
			});
		},
		iniTable : function() {
			table.render({
				elem : '#idTest'
				// ,url: 'user/getUsers'
				,
				id : 'ticketId,pic,customerId',
				cols : [ [ {
					field : 'ticketNum',
					sort : true,
					width : 110,
					align : 'center',
					title : '发票号码'
				}, {
					field : 'ticketCode',
					sort : true,
					width : 130,
					align : 'center',
					title : '发票代码'
				}, {
					field : 'moneyAccount',
					sort : true,
					width : 120,
					align : 'center',
					title : '金额'
				}, {
					field : 'customerName',
					sort : true,
					width : 80,
					align : 'center',
					title : '客户姓名'
				}, {
					field : 'company',
					sort : true,
					width : 165,
					align : 'center',
					title : '公司名称'
				}, {
					field : 'reasonPayment',
					sort : true,
					minWidth : 150,
					align : 'center',
					title : '付款事由'
				}, {
					field : 'ticketTime',
					sort : true,
					minWidth : 110,
					align : 'center',
					title : '开票时间'
				}, {
					field : 'createTime',
					sort : true,
					minWidth : 110,
					align : 'center',
					title : '创建时间'
				}, {
					field : 'state',
					sort : true,
					minWidth : 80,
					align : 'center',
					title : '状态'
				}, {
					fixed : 'right',
					title : '操作',
					width : 150,
					align : 'center',
					toolbar : '#barDemo'
				} ] ],
				data : vm.datas
			});
		},
		getCustomer : function() {
			var customerName = $('#mCustomer').val();
			$.ajax({
				url : 'getCustomerByName.do',
				type : 'post',
				async : false,
				data : {
					customerName : customerName
				},
				dataType : 'json',
				success : function(r) {
					vm.customers = r.data;
					//debugger;
					$("#mCustomer").empty();
					$("#mCustomer").append(
							'<option value="">直接选择或搜索选择</option>');
					for (var i = 0; i < vm.customers.length; i++) {
						$("#mCustomer").append(
								"<option value=" + vm.customers[i].customerId
										+ ">" + vm.customers[i].customerName
										+ "</option>");
					}

				}
			});
		},
		mgetCustomer : function() {
			
			$.ajax({
				url : 'getCustomerByName.do',
				type : 'post',
				async : false,
				dataType : 'json',
				success : function(r) {
					vm.customers = r.data;
					$("#mcustomer").empty();
					$("#mcustomer").append(
							'<option value="">直接选择或搜索选择</option>');
					for (var i = 0; i < vm.customers.length; i++) {
						$("#mcustomer").append(
								"<option value=" + vm.customers[i].customerId
										+ ">" + vm.customers[i].customerName
										+ "</option>");
					}

				}
			});
		},
		add : function() {

			vm.ticket.ticketId = '';
			this.getCustomer();
			form.render('select');
			$('#ticketID').val(vm.ticket.ticketId);
			//日期时间选择器
			laydate.render({
				elem : '#ticketTime',
				type : 'datetime',
				theme : 'molv'
			});
			// do something
			layer.open({
				title : '新增代开票信息',
				type : 1,
				content : $('#addTicket'),
				area : [ '700px', '500px' ]
			});

		},
		addData : function() {
			ticketNum = $('#ticketNum').val();
			vm.ticket.moneyAccount = $('#moneyAccount').val();
			vm.ticket.paymentMethod = $('#paymentMethod').val();
			vm.ticket.customerId = $('#mCustomer').val();
			vm.ticket.reasonPayment = $('#reasonPayment').val();
			vm.ticket.ticketTime = $('#ticketTime').val();
		}
	}
});

layui.use([ 'form', 'laydate', 'element', 'table', 'laypage', 'upload','layer' ],function() {
					form = layui.form;
					laydate = layui.laydate;
					table = layui.table;
					laypage = layui.laypage;
					$ = layui.$;
					layer = layui.layer;
					upload = layui.upload;
					var uploadInst = upload.render({
								elem : '#test1',
								url : 'addTicket.do',
								auto : false,
								accept:'images',
								acceptMime: 'image/*',
								bindAction : '#addButton',
								before : function(obj) {
									uploadInst.config.data.ticketNum = $(
											'#ticketNum').val();
									uploadInst.config.data.moneyAccount = $(
											'#moneyAccount').val();
									uploadInst.config.data.ticketCode = $(
											'#ticketCode').val();
									uploadInst.config.data.customerId = $(
											'#mCustomer').val();
									uploadInst.config.data.reasonPayment = $(
											'#reasonPayment').val();
									uploadInst.config.data.ticketTime = $(
											'#ticketTime').val();
								},
								choose : function(obj) {
									//预读本地文件示例，不支持ie8
									obj.preview(function(index, file, result) {
										$('#demo1').attr('src', result); //图片链接（base64）
									});
									//  vm.addData();
								},
								done : function(res, index, upload) {
										layer.msg("操作成功");
								},
								error : function() {
									//演示失败状态，并实现重传
									layer.msg("操作失败");
								}
							});

					// 请求数据
					vm.count();
					table.on('tool(bar)', function(obj) {
						var data = obj.data; // 获得当前行数据
						var layEvent = obj.event; // 获得 lay-event 对应的值（也可以是表头的
						var tr = obj.tr; // 获得当前行 tr 的DOM对象

						if (layEvent === 'del') { // 删除
							layer.confirm('真的删除行么', function(index) {
								obj.del(); // 删除对应行（tr）的DOM结构，并更新缓存
								layer.close(index);
								// 向服务端发送删除指令
								vm.del(data.userId);
							});
						} else if (layEvent === 'edit') {
							vm.mgetCustomer();
							vm.ticket.ticketId = data.ticketId;
							ticketId=data.ticketId;
							vm.ticket.ticketNum = data.ticketNum;
							vm.ticket.ticketCode = data.ticketCode;
							vm.ticket.moneyAccount = data.moneyAccount;
							vm.ticket.reasonPayment = data.reasonPayment;
							vm.ticket.ticketTime = data.ticketTime;
							vm.ticket.customerId = data.customerId;
							$('#mticketNum').val(data.ticketNum);
							$('#mticketCode').val(data.ticketCode);
							$('#mmoneyAccount').val( data.moneyAccount);
							$('#mcustomer').val( data.customerId);
							$('#mreasonPayment').val( data.reasonPayment);
							form.render('select');
							// do something
							layer.open({
								title : '编辑开票信息',
								type : 1,
								content : $('#mTicket'),
								area : [ '400px', '450px' ]
							});
						} else {
							document.getElementById("pics").src = '/images/'
									+ data.pic;
							layer.open({
								title : '票据原件',
								type : 1,
								content : $('#showPic'),
								area : [ '610px', '460px' ]
							});
						}
					});
					form.on('submit(demo)', function(data) {
						vm.ticket.ticketNum = $('#mticketNum').val();
						vm.ticket.ticketCode = $('#mticketCode').val();
						vm.ticket.moneyAccount = $('#mmoneyAccount').val();
						vm.ticket.reasonPayment = $('#mreasonPayment').val();
						vm.ticket.customerId = $('#mcustomer').val();
						$.ajax({
							url : 'updateTicket.do',
							dataType : 'json',
							data : {
								'ticketId' : vm.ticket.ticketId,
								'ticketNum' : vm.ticket.ticketNum,
								'ticketCode' : vm.ticket.ticketCode,
								'moneyAccount' : vm.ticket.moneyAccount,
								'reasonPayment' : vm.ticket.reasonPayment,
								'customerId' : vm.ticket.customerId,
							},
							async : false,
							type : 'post',
							success : function(r) {
								if(r.msg==1){
									layer.close(layer.index);
									layer.msg("操作成功");
								}else{
									layer.close(layer.index);
									layer.msg("操作失败");
								}
								
								vm.count();
							}
						});
						return false;
					});
				});