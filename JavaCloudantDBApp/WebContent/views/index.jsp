<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demo</title>
    <meta name="description" content="List of recent cases happened.">
    <meta name="author" content="">
    
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
	<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>

    <script type="text/javascript" src="resources/js/util.js"></script>
	<script type="text/javascript" src="resources/js/main.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			showLoadingMessage();
			loadItems();
  			});
	</script>
  </head>

  <body>

	<div data-role="page" id="page">

		<div data-role="header" data-position="fixed">
			Car Insurance Management System
			<div data-role="navbar" data-iconpos="left">	
				<ul>
				  <li><a href="#" class="ui-btn-active" data-icon="home">Case List</a></li>
		          <li onclick="gotoPage('add')"><a href="#" data-icon="plus">Add a Case</a></li>
		          <li onclick="gotoPage('map')"><a href="#" data-icon="grid">Map</a></li>
				</ul>		
			</div>
		</div>
		 
	    	<div id="loadingImage"></div>
		<div data-role="content">
	    	<!-- 
			<div class="row space30" id="caselist"></div>
			 -->
			<ul id="caselist" data-role="listview" data-inset="true" data-filter="true">
			</ul>
	     </div>  
	    
	     <div data-role="footer">
	       <p>Copyright @ 2014 IBM | <a href="http://www.ibm.com/" title="IBM">IBM</a> </p>
	     </div>
    </div> 
  </body>
</html>