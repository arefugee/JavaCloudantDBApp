<!DOCTYPE html> 
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no"/>
<title>Demo</title>
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
<!--引用百度地图API-->
<style type="text/css">
    html,body{margin:0;padding:0;}
    .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
    .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.5&ak=Qwo4QaYk0ezPv1I1RwSiELjU&services=true"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
<script type="text/javascript" src="resources/js/util.js"></script>
<script type="text/javascript" src="resources/js/main.js"></script>
<script type="text/javascript" src="resources/js/map.js"></script>

</head> 
<body> 

	<div data-role="page" id="page">
		<div data-role="header" data-fullscreen="true">
			<div data-role="fieldcontain" data-inline="true">
				    <select name="switch" id="demo" data-role="slider" data-mini="true" data-inline="true">
				      <option value="on">On</option>
				      <option value="off" selected>Off</option>
				   </select>
  			</div>
			<div data-role="navbar" data-iconpos="left">	
				<ul>
				  <li onclick="gotoPage('./')"><a href="#" data-icon="home">Case List</a></li>
		          <li onclick="gotoPage('add')"><a href="#" data-icon="plus">Add a Case</a></li>
			      <li><a href="map" class="ui-btn-active" data-icon="grid">Map</a></li>
				</ul>
			</div>
		</div>
		<div class="ui-hidden-accessible">
			<audio id="accidentaudio">
				<source src="resources/audio/accident.wma" type="audio/wma"></source>
  				<source src="resources/audio/accident.mp3" type="audio/mpeg"></source>
  				<source src="resources/audio/accident.ogg" type="audio/ogg"></source>
			</audio>
			<audio id="areaaudio">
				<source src="resources/audio/area.wma" type="audio/wma"></source>
  				<source src="resources/audio/area.mp3" type="audio/mpeg"></source>
  				<source src="resources/audio/area.ogg" type="audio/ogg"></source>
			</audio>
		</div>
		<div data-role="content" id="ditucontainer" style="height:600px"></div>
		
		<div data-role="footer" class="ui-btn">
		  		<p data-inline="true">Copyright @ 2014 IBM | <a href="http://www.ibm.com/" title="IBM">IBM</a> </p>
		</div>
		
	</div>
</body>
<script type="text/javascript">
		$(document).ready(function(){
			 initMap();
			 document.addEventListener('touchstart', function () {
				    document.getElementById('accidentaudio').play();
				    document.getElementById('accidentaudio').pause();
				    document.getElementById('areaaudio').play();
				    document.getElementById('areaaudio').pause();
				});
  		});
</script>

</html>
