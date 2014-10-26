
var map;//Define the global map variable
var heatmapOverlay;
var currentLocMarker;
var isFirstTime = true;
var latitude;
var longitude;
var allaccPoints;
var reported = false;
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
//        //向地图中添加缩略图控件
//	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
//	map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
//	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
//	map.addControl(ctrl_sca);
	
    }
    
    //标注点数组
    var markerArr = [{title:"昆山市司法局",content:"前进西路148号 电话：57507618",point:"120.967547|31.389313",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"高新区(玉山镇)司法所",content:"昆山市北门路757号 电话：57571148",point:"120.959274|31.415357",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"巴城镇司法所",content:"巴城镇景城南路88号 电话：57980698",point:"120.885415|31.457413",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"周市镇司法所",content:"周市镇惠安路12号 电话：57625148",point:"121.0033|31.470966",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"开发区司法办",content:"昆山市长江中路428号 电话：55216821",point:"120.984417|31.391332",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"陆家镇司法所",content:"陆家镇政府院内 电话：57671667",point:"121.054943|31.320739",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"花桥镇司法所",content:"花桥镇花溪路 电话：57691212",point:"121.096589|31.307288",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"千灯镇司法所",content:"千灯镇政府院内 电话：57466467",point:"121.01045|31.273946",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"淀山湖镇司法所",content:"淀山湖镇振淀路229号 电话：57488204",point:"121.037282|31.183918",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"张浦镇司法所",content:"张浦镇银河路2号 电话：57453612",point:"120.942727|31.279115",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"锦溪镇司法所",content:"锦溪镇普庆路116号 电话：57224860",point:"120.908043|31.186915",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ,{title:"周庄镇司法所",content:"周庄镇全旺路100号 电话：57211692",point:"120.858565|31.135356",isOpen:0,icon:{w:32,h:32,l:0,t:0,x:6,lb:5}}
		 ];
    //创建marker


    function addMarker(){
        for(var i=0;i<markerArr.length;i++){
            var json = markerArr[i];
            var p0 = json.point.split("|")[0];
            var p1 = json.point.split("|")[1];
            var point = new BMap.Point(p0,p1);
			var iconImg = createIcon(json.icon);
            var marker = new BMap.Marker(point,{icon:iconImg});
			var iw = createInfoWindow(i);
			var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
			marker.setLabel(label);
            map.addOverlay(marker);
            label.setStyle({
                        borderColor:"#808080",
                        color:"#333",
                        cursor:"pointer"
            });
			
			(function(){
				var index = i;
				var _iw = createInfoWindow(i);
				var _marker = marker;
				_marker.addEventListener("click",function(){
				    this.openInfoWindow(_iw);
			    });
			    _iw.addEventListener("open",function(){
				    _marker.getLabel().hide();
			    })
			    _iw.addEventListener("close",function(){
				    _marker.getLabel().show();
			    })
				label.addEventListener("click",function(){
				    _marker.openInfoWindow(_iw);
			    })
				if(!!json.isOpen){
					label.hide();
					_marker.openInfoWindow(_iw);
				}
			})()
        }
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
//        	var latitude = position.coords.latitude;
//        	var longitude = position.coords.longitude;
	   		
	   		//Mock current location data:
	  
	   if (isFirstTime){
		   latitude  = 39.9078331;
		   longitude = 116.26424159999998;
	   }else{
		   latitude += 0.001;
		   longitude += 0.001;
	   }
	   		
        	if(!latitude || !longitude) {
        	    alert("HTML5 Geolocation is supported in your browser, but location is currently not available.");
        	    return;
        	}
        	if (!map){
        		map = new BMap.Map("ditucontainer");                    
        	}
        	var point = new BMap.Point(longitude, latitude); 
        	map.centerAndZoom(point, 15); 
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
		   getHeatMap(); 
		   getRealtimeStatus();      
		   updateCurrentLocation();  
		},5000);
	   
   }
   function updateCurrentLocation(){
	   if(navigator.geolocation) {
		   navigator.geolocation.getCurrentPosition(updateLocation);
	   }
   }
   
   function getRealtimeStatus(curPos, minLongtitude, maxLongtitude, minLatitude, maxLatitue){
//	   var url = "http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?" +
//	   		"&q=long:[" + minLongtitude + " TO " + maxLongtitude + " ]" +
//	   		"&lat:[" + minLatitude + " TO " + maxLatitue + "]" +
//	   		"&include_docs=true";
//	   var url = "http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?&q=long:[116.2906705453363 TO 116.2924293746595]&lat:[40.05284646742972 TO 40.0537483433316]&time:[1413848194 TO 1413848224]&include_docs=true";
	   var url = "mapapi/realtime";
	   xhrGet(url, function(data){
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
					   var point = new BMap.Point(tmpItem.fields.long, tmpItem.fields.lat);
					   var marker = new BMap.Marker(point);
						map.addOverlay(marker);
						marker.setAnimation(BMAP_ANIMATION_DROP);
						//create infor window
						var _iw = createInfoWindow(tmpItem);
						marker.addEventListener("click",function(){
						    this.openInfoWindow(_iw);
					    });
						allaccPoints[allaccPoints.length] = tmpItem;
						alert("An accident occured nearly!");
						var music =  document.getElementById("accidentaudio");
						music.play();
				   }
			   }
		   }
		   
		}, function(err){
			console.error(err);
		});
   }
   
   function getHeatMap(curPos, minLongtitude, maxLongtitude, minLatitude, maxLatitue){
//	   var url = "http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?" +
//	   		"&q=long:[" + minLongtitude + " TO " + maxLongtitude + " ]" +
//	   		"&lat:[" + minLatitude + " TO " + maxLatitue + "]" +
//	   		"&include_docs=true";
//	   var url = "http://tedgenstheremeseareencee:IgqAjXieWYef1yXkSIpRjsAq@zozowell.cloudant.com/incidents/_design/seindex/_search/loctime?&q=long:[116.2906705453363 TO 116.2924293746595]&lat:[40.05284646742972 TO 40.0537483433316]&time:[1413848194 TO 1413848224]&include_docs=true";
	   var url = "mapapi/heatmap";
	   xhrGet(url, function(data){
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
  
  
