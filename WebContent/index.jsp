<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Revature LLC - LDS</title>
</head>
<body>

	<div class="wraper">

		<div class="top">
			<img class="logo" src="images/LDS-logo-small.jpg" />
			<div class="buffer"></div>
			<div class="nav">
			<ul>
				<li><div id="link">
						<a id="login" href="#"><br>LOG IN</a>
					</div>
				</li>
			</ul>
				
			</div>
		</div>
		<!-- <div class="left"></div> -->
		<!-- <div class="right"></div> -->
		<div class="frame">
			<!-- This gets refreshed via AJAX -->
		</div>
		<div class="footer"><div class="copyright"><br>© 2016 Revature LLC&nbsp&nbsp</div></div>
	</div>
</body>
<script src="http://code.jquery.com/jquery-1.4.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#login").click(function() {
			$('.frame').load("pages/login.jsp .login");
		});
	});
</script>
</html>