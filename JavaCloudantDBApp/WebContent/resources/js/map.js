
var map;//Define the global map variable
var heatmapOverlay;
var currentLocMarker;
var isFirstTime = true;
var latitude;
var longitude;
var allaccPoints;
var reported = false;
var isDemo = false;
var currentLocIconImg = new BMap.Icon(
		'resources/images/home.png',
		new BMap.Size(24,24),
		{
			imageOffset: new BMap.Size(0,0),
			infoWindowOffset:new BMap.Size(10,1),
			offset:new BMap.Size(6,32)
		});
var newAccIconImg = new BMap.Icon(
		'resources/images/newacc.png',
		new BMap.Size(24,24),
		{
			imageOffset: new BMap.Size(0,0),
			infoWindowOffset:new BMap.Size(10,1),
			offset:new BMap.Size(6,32)
		});

	function initMap(){
        createMap();
    }

    function createMap(){
    	 if(navigator.geolocation) {
             navigator.geolocation.getCurrentPosition(updateLocation);
         }
    }

    //地图控件添加函数：
    function addMapControl(){
    	var navigationControl = new BMap.NavigationControl({
    	    // 靠左上角位置
    	    anchor: BMAP_ANCHOR_TOP_LEFT,
    	    // LARGE类型
    	    type: BMAP_NAVIGATION_CONTROL_LARGE,
    	    // 启用显示定位
    	    enableGeolocation: true
    	  });
    	  map.addControl(navigationControl);
    	  map.addControl(navigationControl);
    }

    //创建InfoWindow
    function createInfoWindow(i){
        var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + i.dateTime + "'>" + i.dateTime + "</b><div class='iw_poi_content'>"+i.acc_description+"</div>");
        return iw;
    }
    //创建一个Icon
    function createIcon(json){
        var icon = new BMap.Icon("resources/images/home.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
        return icon;
    }

   function updateLocation(position) {
      var point;
	  if (isDemo){
		  //Mock current location data:
		   if (isFirstTime){
			   latitude  = 39.9058331;
			   longitude = 116.26224159999998;
		   }else{
			   latitude += 0.001;
			   longitude += 0.001;
		   }
	  }else{
		  	latitude = position.coords.latitude;
     	 	longitude = position.coords.longitude;
	  }

        	if(!latitude || !longitude) {
        	    alert("HTML5 Geolocation is supported in your browser, but location is currently not available.");
        	    return;
        	}
        	if (!map){
        		map = new BMap.Map("ditucontainer");
        		point = new BMap.Point(longitude, latitude);
        		map.centerAndZoom(point, 15);
        	}else{
        		point = new BMap.Point(longitude, latitude);
        		var pinPoint = new BMap.Point(longitude, latitude - 0.008);
        		map.panTo(pinPoint);
        	}

        	if(currentLocMarker){
        		map.removeOverlay(currentLocMarker);
        	}
        	currentLocMarker = new BMap.Marker(point,{icon:currentLocIconImg});
            map.addOverlay(currentLocMarker);
        	window.map = map;
        	if(isFirstTime){
        		addMapControl();//向地图添加控件
        		startTracking();
        		isFirstTime = false;
        	}

   }

   function startTracking(){

	   setInterval(function(){
		   //Get current map scope.
//		   var bs = map.getBounds();
//		   var bssw = bs.getSouthWest();
//		   var bsne = bs.getNorthEast();
		   var bssw = new BMap.Point(longitude - 0.005, latitude - 0.005);
		   var bsne = new BMap.Point(longitude + 0.005, latitude + 0.005);
		   
		   var demoObj = document.getElementById("demo");
		   if (demoObj.value == "on" && isDemo == false){
			   isDemo = true;
			   reported = false;
			   allaccPoints = undefined;
			   latitude  = 39.9058331;
			   longitude = 116.26224159999998;
		   }else if (demoObj.value == "off" && isDemo == true){
			   allaccPoints = undefined;
			   isDemo = false;
		   }

		   getHeatMap(longitude, latitude, bssw.lng, bssw.lat, bsne.lng, bsne.lat);
		   getRealtimeStatus(longitude, latitude, bssw.lng, bssw.lat, bsne.lng, bsne.lat, 2);
		   
		},5000);
	   setInterval(function(){
		   updateCurrentLocation();
		   }, 5000);
	   

   }
   function updateCurrentLocation(){
	   if(navigator.geolocation) {
		   navigator.geolocation.getCurrentPosition(updateLocation);
	   }
   }

   function getRealtimeStatus(currentLng, currentLat, minLongtitude, minLatitude, maxLongtitude, maxLatitue, recent_hours){
	   var url = "mapapi/realtime";
	   var paras = {};

       //Retrieve all points in recnet hours, default fetch all points
       var current_time_in_sec = Math.round( new Date().valueOf() /1000 );
       var offset_in_sec = current_time_in_sec;
       if (recent_hours > 0) {
            offset_in_sec = 3600 * recent_hours;
       }
	   var from_in_sec = current_time_in_sec - offset_in_sec;

	   paras["url"] = "https://0d63be48-d55a-4c59-acab-b6f753c2791f-bluemix.cloudant.com/sample_nosql_db/_design/seindex/_search/loctime?q=long:"
		   +encodeURIComponent("["+minLongtitude+" TO "+maxLongtitude+"]")
		   +"&lat:" + encodeURIComponent("["+minLatitude + " TO "+maxLatitue+"]")
		   +"&include_docs=true"
		   +"&sort=" + encodeURIComponent("\"-time\"")
           +"&time:" + encodeURIComponent("["+from_in_sec + " TO "+ current_time_in_sec+"]")
		   +"&limit=10"
		   + (isDemo?"&demo=true":"");

	   xhrPost(url, paras, function(data){

			   var tmpAllaccPoints = data.rows || [];
			  
			   if (!allaccPoints){//First time
				   allaccPoints = tmpAllaccPoints;
				   for(var i = 0; i < allaccPoints.length; ++i){
						var item = allaccPoints[i];
						if(item && 'id' in item){
							var point = new BMap.Point(item.doc.longitude, item.doc.latitude);
							var marker = new BMap.Marker(point);
							var content = item.doc.dateTime + '  ' +  item.doc.acc_description;
							marker.setTitle(content);
							map.addOverlay(marker);
							marker.addEventListener("click",function(e){openInfo(content,e)});
//							//create infor window
//							var _iw = createInfoWindow(item);
//							marker.addEventListener("click",function(){
//							    marker.openInfoWindow(_iw);
//						    });
						}
					}
			   }else{//from second time
				   for (var j=0; j<tmpAllaccPoints.length; j++){
					   var isSame = false;
					   var tmpItem = tmpAllaccPoints[j];
					   for (var k=0; k<allaccPoints.length; k++){
						   if (tmpItem.id == allaccPoints[k].id){
							   isSame = true;
							   break;
						   }
					   }
					   if (!isSame){
						   var accspan =  document.getElementById("acc2");
						   accspan.innerHTML = "Accident Occurred Nearby!";
						   setTimeout(function(){accspan.innerHTML = "";}, 15000);
							var music =  document.getElementById("accidentaudio");
							music.loop=false;
							music.play();
							sleep(1000);
						   var point = new BMap.Point(tmpItem.doc.longitude, tmpItem.doc.latitude);
						   var marker = new BMap.Marker(point,{icon:newAccIconImg});
						   var content = tmpItem.doc.dateTime + '  ' +  tmpItem.doc.acc_description;
						   marker.setTitle(content);
							map.addOverlay(marker);
							marker.setAnimation(BMAP_ANIMATION_BOUNCE);
							marker.addEventListener("click",function(e){openInfo(content,e);});
							//create infor window
//							var _iw = createInfoWindow(tmpItem.doc);
//							marker.addEventListener("click",function(){
//							    this.openInfoWindow(_iw);
//						    });
							allaccPoints[allaccPoints.length] = tmpItem;
					   }
				   }
			   }


		}, function(err){
			console.error(err);
		});
   }

   function getHeatMap(currentLng, currentLat, minLongtitude, minLatitude,maxLongtitude , maxLatitue){
	   var url = "mapapi/heatmap";
	   var paras = {};
	   paras["url"] = "https://0d63be48-d55a-4c59-acab-b6f753c2791f-bluemix.cloudant.com/sample_nosql_db/_design/secindex/_view/dangspot?startkey=12"
		   + (isDemo?"&demo=true":"");

	   xhrPost(url, paras, function(data){
		    var tmpAllHeatPoints = data.rows;
		    var allHeatPoints = [];
		    for(var i = 0; i < tmpAllHeatPoints.length; ++i){
				var item = tmpAllHeatPoints[i].value;
				allHeatPoints[i] = item;
			}
		    map.removeOverlay(heatmapOverlay);
			heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
			map.addOverlay(heatmapOverlay);
			heatmapOverlay.setDataSet({data:allHeatPoints,max:12});

			for(var i = 0; i < allHeatPoints.length; ++i){
				var item = allHeatPoints[i];
				if ( Math.abs(item.lng - longitude) <= 0.0001 &&
						Math.abs(item.lat - latitude) <= 0.0001 ){
					if (!reported){
						 var accspan =  document.getElementById("acc1");
						   accspan.innerHTML = " Entering Dangerous Area!";
						   setTimeout(function(){accspan.innerHTML="";}, 15000);
						var music =  document.getElementById("areaaudio");
						music.loop=false;
						music.play();
						reported = true;
					}
				}
			}
		}, function(err){
			console.error(err);
		});
   }

   function openInfo(content,e){
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(p.getTitle());  // 创建信息窗口对象
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}


