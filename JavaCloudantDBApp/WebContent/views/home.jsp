<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<title>Demo</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<link rel="stylesheet" href="resources/css/style.css" />
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
</head>
<body>
	<header>
		<div class='title'>auto insurance data collection
		<button class='detailBtn' onclick="toggleAppInfo()" title='Application Information'>
				<img height=\"60\" width=\"60\" src='resources/images/info.png' alt='detail'/>		
			<button class='addBtn' onclick="addItem()" title='Add a new category'><img src='resources/images/add.png' alt='add'/></button>
		</div>
	</header>	
	<section id='appinfo' style='display: none;'>
		<table>
			<tbody>
			</tbody>
		</table>
	</section>
	<section>
		<table id='notes' class='records' border='0' cellspacing='0' cellpadding='0'><tbody></tbody></table>
	</section>
	<footer>
		<div class='tips'>Click the Add button to add a new recorder.
			<!-- <button class='detailBtn' onclick="toggleServiceInfo()" title='Service information'>
				<img src='images/detail.png' alt='detail'/>
			-->
			</button> <button class='addBtn' onclick="addItem()" title='Add a new category'><img src='resources/images/add.png' alt='add'/></button>
		</div>
		<div id="loadingImage"></div>	
	</footer>
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/index.js"></script>
	<div id="myImage"><div id="innerImg"></div></div>
	<br><br><br>	
	<script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/index.js"></script>
</body>
</html>

