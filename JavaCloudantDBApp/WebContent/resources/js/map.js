
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
        var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + i.id + "'>" + i.id + "</b><div class='iw_poi_content'>"+i.fields.time+"</div>");
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
			   latitude  = 39.9078331;
			   longitude = 116.26424159999998;
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
        		map.panTo(point); 
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
//	   //Every 5 seconds to get heatmap
//	   setInterval(function(){
//		   getHeatMap();        
//		},5000);
//	   
//	   //Every 5 seconds to get current location 
//	   setInterval(function(){
//		   updateCurrentLocation();       
//		},5000);
//	   
//	   //Every 5 seconds to get current location
//	   setInterval(function(){
//		   getRealtimeStatus();      
//		},5000);
	   setInterval(function(){
		   //Get current map scope.
		   var bs = map.getBounds();   
		   var bssw = bs.getSouthWest();
		   var bsne = bs.getNorthEast();
		   
		   var demoObj = document.getElementById("demo");
		   if (demoObj.value == "on" && isDemo == false){
			   isDemo = true;
			   reported = false;
			   allaccPoints = undefined;
			   latitude  = 39.9078331;
			   longitude = 116.26424159999998;
		   }else if (demoObj.value == "off"){
			   allaccPoints = undefined;
			   isDemo = false;
		   }
		   
		   getHeatMap(longitude, latitude, bssw.lng, bssw.lat, bsne.lng, bsne.lat); 
		   getRealtimeStatus(longitude, latitude, bssw.lng, bssw.lat, bsne.lng, bsne.lat);      
		   updateCurrentLocation();  
		},5000);
	   
   }
   function updateCurrentLocation(){
	   if(navigator.geolocation) {
		   navigator.geolocation.getCurrentPosition(updateLocation);
	   }
   }
   
   function getRealtimeStatus(currentLng, currentLat, minLongtitude, minLatitude,maxLongtitude , maxLatitue){
//	   var url = "http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?" +
//	   		"&q=long:[" + minLongtitude + " TO " + maxLongtitude + " ]" +
//	   		"&lat:[" + minLatitude + " TO " + maxLatitue + "]" +
//	   		"&include_docs=true";
//	   var url = "http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?&q=long:[116.2906705453363 TO 116.2924293746595]&lat:[40.05284646742972 TO 40.0537483433316]&time:[1413848194 TO 1413848224]&include_docs=true";
	   var url = "mapapi/realtime";
	   var paras = {};
//	   paras["url"] = encodeURIComponent("http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?&q=long:[116.2906705453363 TO 116.2924293746595]&lat:[40.05284646742972 TO 40.0537483433316]&time:[1413848194 TO 1413848224]&include_docs=true");
	   paras["url"] = "http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?&q=long:"
		   +encodeURIComponent("[116.2906705453363 TO 116.2924293746595]")+"&lat:"
		   +encodeURIComponent("[40.05284646742972 TO 40.0537483433316]")+"&include_docs=true" + (isDemo?"&demo=true":"");
	   
	   xhrPost(url, paras, function(data){
		   var tmpAllaccPoints = data.rows;
		   
		   if (!allaccPoints){//First time
			   allaccPoints = tmpAllaccPoints;
			   for(var i = 0; i < allaccPoints.length; ++i){
					var item = allaccPoints[i];
					if(item && 'fields' in item){
						var point = new BMap.Point(item.fields.long, item.fields.lat);
						var marker = new BMap.Marker(point);
						map.addOverlay(marker);
						//create infor window
						var _iw = createInfoWindow(item);
						marker.addEventListener("click",function(){
						    this.openInfoWindow(_iw);
					    });
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
					   alert("An accident occured nearly!");
						var music =  document.getElementById("accidentaudio");
						music.play();
						sleep(1000);
					   var point = new BMap.Point(tmpItem.fields.long, tmpItem.fields.lat);
					   var marker = new BMap.Marker(point);
						map.addOverlay(marker);
						marker.setAnimation(BMAP_ANIMATION_BOUNCE);
						//create infor window
						var _iw = createInfoWindow(tmpItem);
						marker.addEventListener("click",function(){
						    this.openInfoWindow(_iw);
					    });
						allaccPoints[allaccPoints.length] = tmpItem;
						
				   }
			   }
		   }
		   
		}, function(err){
			console.error(err);
		});
   }
   
   function getHeatMap(currentLng, currentLat, minLongtitude, minLatitude,maxLongtitude , maxLatitue){
//	   var url = "http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?" +
//	   		"&q=long:[" + minLongtitude + " TO " + maxLongtitude + " ]" +
//	   		"&lat:[" + minLatitude + " TO " + maxLatitue + "]" +
//	   		"&include_docs=true";
//	   var url = "http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?&q=long:[116.2906705453363 TO 116.2924293746595]&lat:[40.05284646742972 TO 40.0537483433316]&time:[1413848194 TO 1413848224]&include_docs=true";
	   var url = "mapapi/heatmap";
	   var data = {};
	   data["url"] = "";
	   
	   xhrPost(url, data, function(data){
		    var allHeatPoints = data.rows;
		    map.removeOverlay(heatmapOverlay);
			heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
			map.addOverlay(heatmapOverlay);
			heatmapOverlay.setDataSet({data:allHeatPoints,max:100});
			
			for(var i = 0; i < allHeatPoints.length; ++i){
				var item = allHeatPoints[i];
				if ( Math.abs(item.lng - longitude) <= 0.0005 && 
						Math.abs(item.lat - latitude) <= 0.0005 ){
					if (!reported){
						alert("You are entering the dangerous area!");
						var music =  document.getElementById("areaaudio");
						music.play();
						reported = true;
					}
				}
			}
		}, function(err){
			console.error(err);
		});
   }
  
  
