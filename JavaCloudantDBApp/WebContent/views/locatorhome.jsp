<!DOCTYPE html>
<html>
<title>Locator </title>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
        <script type="text/javascript" src="resources/js/util.js"></script>
    </head>
<body style='margin:50px 10px;'>
        <div id="status" style="text-align: center"></div>
        <div style="width:150px;height:150px;border:1px solid gray;margin:30px auto" id="container"></div>
        <div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				<div class="account-wall">
						<input type="text" class="form-control" placeholder="location name"
							required autofocus id="locName"> 
						<button title="Submit" name="Submit" onclick="postData()">Submit</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

<script type="text/javascript">
var latitude ;
var longitude;
    window.onload = function() {
        if(navigator.geolocation) {
            document.getElementById("status").innerHTML = "HTML5 Geolocation is supported in your browser.";
            navigator.geolocation.getCurrentPosition(updateLocation);
        }
    };

    function updateLocation(position) {
         latitude = position.coords.latitude;
         longitude = position.coords.longitude;
        if(!latitude || !longitude) {
            document.getElementById("status").innerHTML = "HTML5 Geolocation is supported in your browser, but location is currently not available.";
            return;
        }

        var map = new BMap.Map("container");                     // 创建Map实例
        var point = new BMap.Point(longitude, latitude);        // 创建点坐标
        map.centerAndZoom(point, 15);                            // 初始化地图,设置中心点坐标和地图级别。
        var marker = new BMap.Marker(point);                        // 创建标注
        map.addOverlay(marker);                                    //将标注添加到地图中
    };
    
    function postData(){
    	 var latitude1 = latitude;
         var longitude1 = longitude;
         var data = {
        		 location : document.getElementById('locName').value,
        		 latitude: latitude1,
        		 longitude: longitude1
 			};	
    	xhrPost("api/location", data, function(item){
			alert(data.location + "  " + data.latitude + "  " + data.latitude);
		}, function(err){
			console.error(err);
		});
    }

</script>