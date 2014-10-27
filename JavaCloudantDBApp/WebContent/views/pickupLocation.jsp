<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=Qwo4QaYk0ezPv1I1RwSiELjU"></script>
	<title>点击取得坐标</title>
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");  
  var point = new BMap.Point(116.30003,40.051996);
	map.centerAndZoom(point,16);    
  map.enableScrollWheelZoom();
	//单击获取点击的经纬度
	map.addEventListener("click",function(e){
		
      var str = "lng:"+e.point.lng + ",lat:" + e.point.lat;
      alert(str);
      console.log(str);
      window.clipboardData.setData("text", str);   
	});
</script>