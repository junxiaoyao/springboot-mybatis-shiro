//将layui的相关模块申明为全局变量，随时可调用
var form = null;
var laydate = null;
var table = null;
var laypage = null;
//var $ = null;
var layer = null;
option = {
	title : {
		text : '营业额趋势图',
	},
	 toolbox: {
	        show: true,
	        feature: {
	            magicType: {type: ['line', 'bar']},
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	tooltip : {
		trigger : 'axis'
	},
	xAxis : {
		type : 'category',
	},
	yAxis : {
		type : 'value'
	},
	series : []
};
function xData(){
	var xDatas=new Array();
	var year=vm.year;
	for(var i=1;i<=12;i++){
		var d=(year-1)+'-'+i;
		xDatas.push(d)
	}
	for(var i=1;i<=12;i++){
		var d=year+'-'+i;
		xDatas.push(d)
	}
	return vm.xData=xDatas;
}
var vm = new Vue({
	el : '#main',
	data : {
		time : "",
		customer:'',
		data:[],
		year:'',
		xData:[]
	},
	methods : {
		queryData:function(){
			vm.time=$('#time').val();
			vm.customer=$('#customer').val();
			$.ajax({
				url : 'getTurnoverReport.do',
				type : 'post',
				data : {
					'time' : vm.time,
					'customer' : vm.customer
				},
				async : false,
				dataType : 'json',
				success : function(r) {
					vm.data = r.data;
					vm.year=r.year;
					if(r.type<2){
						vm.xData=r.time;
					}else{
						xData();
					}
				}
			});
			this.setOption();
		},
		setOption:function () {
			option.xAxis.data = vm.xData;
			option.series[0]=(vm.data);
			//初始化echarts实例
			var myChart = echarts.init(document.getElementById('datas'));
			myChart.setOption(option);
		},
		ini:function(){
			
			this.queryData();
		}
	}
});
$(function(){
	vm.ini();
});

