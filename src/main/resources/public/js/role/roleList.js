//将layui的相关模块申明为全局变量，随时可调用
var form = null;
var laydate = null;
var table = null;
var laypage = null;
var $ = null;
var layer=null;
var setting = {
	check : {
		enable : true
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "menuId",
			pIdKey : "parentId",
			rootPId : 0
		},
		key : {
			name : 'menuName',
			checked: "checked"
		}
	}
};
function perms() {
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var nodes = treeObj.getCheckedNodes(true);

	var list = new Array(nodes.length);
	for (var i = 0; i < nodes.length; i++) {
		list[i] = nodes[i].menuId;
	}
	//console.log(list);
	return list;
}
var vm = new Vue({
	el : '#main',
	data : {
		roleName : "",
		datas : [],
		length : 0
	},
	methods : {
		queryData : function(pageNum) {
			vm.roleName = $("#roleName").val();
			$.ajax({
				url : 'getRoleList.do',
				type : 'post',
				data : {
					'roleName' : vm.roleName,
					'pageNum' : pageNum
				},
				async : false,
				dataType : 'json',
				success : function(r) {
					vm.datas = r.data;
				}
			});
			this.iniTable();
			//	this.count();
		},
		perms : function() {
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			//  console.log(treeObj);
			var nodes = treeObj.getCheckedNodes(true);

			var list = new Array(nodes.length);
			for (var i = 0; i < nodes.length; i++) {
				list[i] = nodes[i].menuId;
			}
			///  console.log(list);
			return list;
		},
		mperms : function() {
			var treeObj = $.fn.zTree.getZTreeObj("mztree");
			//  console.log(treeObj);
			var nodes = treeObj.getCheckedNodes(true);

			var list = new Array(nodes.length);
			for (var i = 0; i < nodes.length; i++) {
				list[i] = nodes[i].menuId;
			}
			///  console.log(list);
			return list;
		},
		count : function() {
			vm.roleName = $("#roleName").val();
			$.ajax({
				url : 'getRoleCount.do',
				type : 'post',
				data : {
					roleName : vm.roleName,
				},
				async : false,
				dataType : 'json',
				success : function(r) {
					vm.length = r.count;
					//console.log(r.data);
				}
			});
			this.iniPagination();
		},
		addSave : function() {
			var menus = this.perms();
			var roleName = $('#mRoleName').val();
			var remark = $('#mRemark').val();
			if (roleName == null || menus.length < 1) {
				layer.open({
					type : 1,
					title : '提示信息',
					area : [ '150px', '100px' ],
					content : ("必填项未填！")
				});
				;
			} else {
				$.ajax({
					url : 'addRole.do',
					type : 'post',
					data : {
						menus : menus,
						roleName:roleName,
						remark:remark
					},
					async : false,
					dataType : 'json',
					success : function(r) {
						layer.close(layer.index);
						if(r.msg==1){
							layer.msg("操作成功");
						}else{
							layer.msg("操作失败");
						}
					}
				})
				this.count();
			}
		},
		del:function(id){
			$.ajax({
				url : 'delete/'+id,
				type : 'post',
				async : false,
				dataType : 'json',
				success : function(r) {
					
				}
			})
		},
		getTree : function(roleId) {
			var data = [];
			$.ajax({
				url : 'getMenu/'+roleId,
				type : 'post',
				data : {},
				async : false,
				dataType : 'json',
				success : function(r) {
					data = r.data;
				}
			});
		//	console.log(data);
			return data;
		},
		iniTree : function(roleId) {
			var zNode = this.getTree(roleId);
			$.fn.zTree.init($("#tree"), setting, zNode);
		},
		mIniTree : function(roleId) {
			var zNode = this.getTree(roleId);
			$.fn.zTree.init($("#mztree"), setting, zNode);
		},
		add : function() {
			this.iniTree(0);
			layer.open({
				title : '新增角色',
				type : 1,
				content : $('#addRole'),
				area : [ '300px', '500px' ]
			});
		},
		edit:function(roleId,roleName,remark){
			this.mIniTree(roleId);
			 $('#mroleName').val(roleName);
			 $('#mremark').val(remark);roleId
			 $('#roleId').val(roleId);
			layer.open({
				title : '编辑角色',
				type : 1,
				content : $('#mRole'),
				area : [ '300px', '500px' ]
			});
		},
		editSave:function(){
			var menus = this.mperms();
			var roleName = $('#mroleName').val();
			var remark = $('#mremark').val();
			var  roleId=$('#roleId').val();
			if (roleName == null || menus.length < 1) {
				layer.open({
					type : 1,
					title : '提示信息',
					area : [ '150px', '100px' ],
					content : ("必填项未填！")
				});
				;
			} else {
				$.ajax({
					url : 'edit/'+roleId,
					type : 'post',
					data : {
						menus : menus,
						roleName:roleName,
						remark:remark
					},
					async : false,
					dataType : 'json',
					success : function(r) {
						layer.close(layer.index);
						if(r.msg==1){
							layer.msg("操作成功");
						}else{
							layer.msg("操作失败");
						}
					}
				})
				
			}
			this.count();
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
				elem : '#datas',
				id:'roleId',
				cols : [ [{
					field : 'roleName',
					sort : true,
					width : 200,
					align : 'center',
					title : '角色名'
				}, {
					field : 'roleName',
					sort : true,
					width : 150,
					title : '创建者'
				}, {
					field : 'remark',
					sort : true,
					width : 250,
					align : 'center',
					title : '备注'
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

	}
});

layui.use([ 'form', 'laydate', 'element', 'table', 'laypage','layer' ], function() {
	form = layui.form;
	laydate = layui.laydate;
	table = layui.table;
	laypage = layui.laypage;
	$ = layui.$;
	 layer = layui.layer;
	//请求数据
	//vm.queryData(1);
	vm.count();
	//监听工具条
	table.on('tool(bar)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data; //获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		var tr = obj.tr; //获得当前行 tr 的DOM对象

		if (layEvent === 'del') { //删除
			layer.confirm('真的删除行么', function(index) {
				obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
				layer.close(index);
				vm.del(data.roleId);
				//向服务端发送删除指令
			});
		} else if (layEvent === 'edit') { //编辑
			vm.edit(data.roleId,data.roleName,data.remark);
		}
	});
});
//ztree

