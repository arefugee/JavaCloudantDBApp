// index.js

var REST_DATA = 'api/favorites';

var KEY_ENTER = 13;
var defaultItems = [
	
];

function loadItems(){
	xhrGet(REST_DATA, function(data){
		
		//stop showing loading message
		stopLoadingMessage();
		
		var receivedItems = data.body || [];
		var items = [];
		var i;
		// Make sure the received items have correct format
		for(i = 0; i < receivedItems.length; ++i){
			var item = receivedItems[i];
			if(item && 'id' in item){
				items.push(item);
			}
		}
		var hasItems = items.length;
		if(!hasItems){
			items = defaultItems;
		}
		var row;
		for(i = 0; i < items.length; ++i){
			if (i/4 == 0){
				row = document.createElement("div");
				row.setAttribute("class", "row space30");
				document.getElementById('caselist').appendChild(row);;
			}
			addItem(items[i], !hasItems, row);
		}
		if(!hasItems){
			var table = document.getElementById('notes');
			var nodes = [];
			for(i = 0; i < table.rows.length; ++i){
				nodes.push(table.rows[i].firstChild.firstChild);
			}
			function save(){
				if(nodes.length){
					saveChange(nodes.shift(), save);
				}
			}
			save();
		}
	}, function(err){
		console.error(err);
	});
}

function startProgressIndicator(row)
{	
	row.innerHTML="<td class='content'>Uploading file... <img height=\"50\" width=\"50\" src=\"resources/images/loading.gif\"></img></td>";	
}

function removeProgressIndicator(row)
{
	row.innerHTML="<td class='content'></td>";
}

function addNewRow(table)
{
	var newRow = document.createElement('tr');
	table.appendChild(newRow);
	return table.lastChild;
}

function uploadFile(filenode, formnode, picdiv)
{
	
	var file = filenode.files[0];
	
	//if file not selected, throw error
	if(!file)
	{
		alert("File not selected for upload... \t\t\t\t \n\n - Choose a file to upload. \n - Then click on Upload button.");
		return;
	}
	
	var row = formnode;
	var form = new FormData();
	var picStatus = document.getElementById("picStatus");
	form.append("file", file);
	
	var id = row.getAttribute('data-id');
	var queryParams = "id=" + (id==null?-1:id);
	for( i=0; i<formnode.length;i++ ){
		item = formnode[i];
		queryParams += "&" + item.name + "=" + item.value;
	}
	
	startProgressIndicator(picStatus);
	
	xhrAttach(REST_DATA+"/attach?"+queryParams, form, function(item){		
		console.log('attached: ', item);
		row.setAttribute('data-id', item.id);
		removeProgressIndicator(picStatus);
		setUploadPics(item, picdiv);
	}, function(err){
		console.error(err);
	});
}

function setUploadPics(item, picdiv){
	var attachments = item.attachements;
	var innerHTML = "";
	if(attachments && attachments.length>0)
	{
		var attachment = attachments[attachments.length-1];
		if(attachment.content_type.indexOf("image/")==0)
		{
			innerHTML += "<br><div><img height=\"250px\" width=\"250px\" src=" + attachment.url + " alt='Image' /></div>";
		}
		picdiv.innerHTML += innerHTML;
	}
}

var attachButton = "<br><input type=\"file\" name=\"file\" id=\"upload_file\"><input width=\"100\" type=\"submit\" value=\"Upload\" onClick='uploadFile(this)'>";

function setRowContent(item, row)
{
		var innerHTML = "<h3>" + item.ownerName1 +" / " + item.ownerName2 +"</h3>";
		var attachments = item.attachements;
		if(attachments && attachments.length>0)
		{
			var attachment = attachments[0];
			if(attachment.content_type.indexOf("image/")==0)
			{
				innerHTML+= "<a href='#'>" +
								"<img height=\"250px\" width=\"250px\" src=" + attachment.url + " alt='Image' />" +
							"</a>";
			}
		}
		innerHTML += "<p>" + item.carNumber1 + "</p>";
		innerHTML += "<p>" + item.carNumber2 + "</p>";
		innerHTML += "<p>" + item.dateTime + "</p>";
		innerHTML += "<p><a class='btn-sm btn-primary' href='add?id="+item.id+"'>Details &raquo;</a></p>";
		
		row.innerHTML = innerHTML;
}

function addItem(item, isNew, container){
	
	var row = document.createElement('div');
	row.setAttribute("class", "col-xs-6 col-sm-3");
	var id = item && item.id;
	if(id){
		row.setAttribute('data-id', id);
	}
	
	if(item) // if not a new row
	{
		setRowContent(item, row);
	}
	else //if new row
	{
		row.innerHTML = "<td class='content'>" +
							
							"<table border=\"0\">" +
								"<tr border=\"0\">" +
									"<textarea id='nameText' onkeydown='onKey(event)' placeholder=\"Enter the owner name\">" +
									"</textarea>" +
								"</tr>" +
								"<tr border=\"0\">" +
									"<textarea id='carPlateNumberText' onkeydown='onKey(event)' placeholder=\"Enter the car plate number\">" +
									"</textarea>" +
								"</tr>" +
								"<tr border=\"0\">" +
									"<textarea id='carTypeText' onkeydown='onKey(event)' placeholder=\"Enter the car type\">" +
									"</textarea>" +
								"</tr>" +
							"</table>" +
						"</td>" +
						"<td class='content'>" +
							"<table border=\"0\">" +
								"<tr border=\"0\">" +
									"<td class='content'>" +
										"<textarea id='valText'  onkeydown='onKey(event)' placeholder=\"Enter a description...\">" +
										"</textarea>" +
									"</td>" +
								"</tr>" +
							"</table>"+
							attachButton+
						"</td>" +
						"<td class='deleteBtn' onclick='deleteItem(this)' title='delete me'>" +
						"</td>";
	}

	container.appendChild(row);
	
}

function deleteItem(row){
	if(row.getAttribute('data-id'))
	{
		xhrDelete(REST_DATA + '?id=' + row.getAttribute('data-id'), function(){
			alert("Delete successfully!");
			window.location.href="./"; 
		}, function(err){
			console.error(err);
		});
	}
	
}

function addAnother(row){
	window.location.href="./add"; 
}





function onKey(evt){
	
	if(evt.keyCode == KEY_ENTER && !evt.shiftKey){
		
		evt.stopPropagation();
		evt.preventDefault();
		var nameV, valueV;
		var row ; 		
		
		if(evt.target.id=="nameText")
		{
			row = evt.target.parentNode.parentNode;
			nameV = evt.target.value;
			valueV = row.firstChild.nextSibling.firstChild.firstChild.firstChild.firstChild.firstChild.value ;
			
		}
		else
		{
			row = evt.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
			nameV = row.firstChild.firstChild.value;
			valueV = evt.target.value;
		}
		
		var data = {
				name: nameV,
				value: valueV
			};			
		
			if(row.isNew){
				delete row.isNew;
				xhrPost(REST_DATA, data, function(item){
					row.setAttribute('data-id', item.id);
				}, function(err){
					console.error(err);
				});
			}else{
				var requestParam = '?id=' + row.getAttribute('data-id')+"&name="+nameV+"&value="+valueV;
				xhrPut(REST_DATA+requestParam, data, function(){
					console.log('updated: ', data);
				}, function(err){
					console.error(err);
				});
			}
	
		if(row.nextSibling){
			row.nextSibling.firstChild.firstChild.focus();
		}else{
			addItem();
		}
	}
}

function saveChange(contentNode, callback){
	var data = {};
	
	for( i=0; i<contentNode.length;i++ ){
		item = contentNode[i];
		if (item.name !== ''){
			data[item.name] = item.value;
		}
	}
	var id = contentNode.getAttribute('data-id');
	if (id == null || id == ""){
		xhrPost(REST_DATA, data, function(item){
			contentNode.setAttribute('data-id', item.id);
			alert("Create record successfully!");
			callback && callback();
		}, function(err){
			console.error(err);
		});
	} else {
		data.id = id;
		data._method = 'PUT';
		xhrPut(REST_DATA, data, function(){
			console.log('updated: ', data);
			alert("Update record successfully!");
		}, function(err){
			console.error(err);
		});
	}
	
	
	// get form's each object
//	var row = contentNode.parentNode.parentNode;
//	
//	var data = {
//		name: row.firstChild.firstChild.value,
//		value:row.firstChild.nextSibling.firstChild.value		
//	};
//	
//	if(row.isNew){
//		delete row.isNew;
//		xhrPost(REST_DATA, data, function(item){
//			row.setAttribute('data-id', item.id);
//			callback && callback();
//		}, function(err){
//			console.error(err);
//		});
//	}else{
//		data.id = row.getAttribute('data-id');
//		xhrPut(REST_DATA, data, function(){
//			console.log('updated: ', data);
//		}, function(err){
//			console.error(err);
//		});
//	}
}



function toggleAppInfo(){
	var node = document.getElementById('appinfo');
	node.style.display = node.style.display == 'none' ? '' : 'none';
}


function showLoadingMessage()
{
	document.getElementById('loadingImage').innerHTML = "Loading data "+"<img height=\"100\" width=\"100\" src=\"resources/images/loading.gif\"></img>";
}
function stopLoadingMessage()
{
	document.getElementById('loadingImage').innerHTML = "";
}

function getloc() {
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(updateLocation);
    }else{
    	alert("HTML5 Geolocation is not supported in your browser.");
    }
};

function updateLocation(position) {
    var latitude = position.coords.latitude;
    var longitude = position.coords.longitude;
    if(!latitude || !longitude) {
        alert("HTML5 Geolocation is supported in your browser, but location is currently not available.");
        return;
    }

    var map = new BMap.Map("mymapcontainer");                     
    var point = new BMap.Point(longitude, latitude);        
    map.centerAndZoom(point, 15);                         
    var marker = new BMap.Marker(point);                        
    map.addOverlay(marker);
};
