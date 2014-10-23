<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Case List</title>
    <meta name="description" content="List of recent cases happened.">
    <meta name="author" content="">
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/templatemo_justified.css" rel="stylesheet">
    <link href="resources/css/bootstrap-combined.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" media="screen">
    
    <script src="resources/js/jquery-1.11.1.js"></script>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="resources/js/html5shiv.js"></script>
      <script src="resources/js/respond.min.js"></script>
    <![endif]-->
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

    <div id="container" class="container">
		<h1>Car Insurance Management System</h1>
        <ul class="nav nav-justified">
          <li class="active"><a href="#">Case List</a></li>
          <li><a href="add">Add a Case</a></li>
          <li><a href="map">Map</a></li>
        </ul>
    <div id="loadingImage"></div>
	<div class="row space30" id="caselist">
    </div>
        
      <!-- Site footer -->
      <div class="footer">
      	<div id="loadingImage"></div>	
        <p>Copyright @ 2014 IBM | <a href="http://www.ibm.com/" title="IBM">IBM</a> </p>
      </div>

    </div> 
  </body>
</html>