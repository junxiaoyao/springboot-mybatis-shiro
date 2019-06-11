
option = {
	title : {
		text : '退废票数量趋势图',
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
	 legend: {
	        data:['退票','废票']
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
		time : '',
		customer:'',
		data1:{},
		data2:{},
		year:'',
		xData:[]
		},
	methods : {
		queryData:function(){
			vm.time=$('#time').val();
			vm.customer=$('#customer').val();
			$.ajax({
				url : 'getRticketReport.do',
				type : 'post',
				data : {
					'time' : vm.time,
					'customer' : vm.customer
				},
				async : false,
				dataType : 'json',
				success : function(r) {
					vm.data1 = r.data1;
					vm.data2 = r.data2;
					if(r.type<2){
						vm.xData=r.time;
					}else{
						vm.year=r.year;
						xData();
					}
				}
			});
			this.setOption();
		},
		ini:function(){
			this.queryData();
		},
		setOption:function () {
			option.xAxis.data = vm.xData;
			option.series[0]=(vm.data1);
			option.series[1]=(vm.data2);
			//初始化echarts实例
			var myChart = echarts.init(document.getElementById('datas'));
			myChart.setOption(option);
		}
	}
});

$(function(){
	vm.ini();
});
