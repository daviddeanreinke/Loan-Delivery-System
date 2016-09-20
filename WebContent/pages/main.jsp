<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%-- <%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

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
					<div id="link">
						<li><c:choose>
								<c:when test="${sessionScope.firstname != null}">
										Welcome ${sessionScope.firstname} <br>
									<html:link action="login.do?method=logout">LOGOUT</html:link>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose></li>
						<div id="link">
							<li><a id="browse" href="#">LOAN<br>MARKET
							</a></li>
						</div>
						<div id="link">
							<li><a id="commit" href="#">ANALYZE&nbspAND<br>COMMIT
							</a></li>
						</div>
						<div id="link">
							<li><a id="history" href="#">DAILY<br>ACTIVITY
							</a></li>
						</div>
						<div id="link">
							<li><a id="about" href="#">ABOUT<br>LDS
							</a></li>
						</div>
						<div id="link">
							<li><a id="terms" href="#">TERMS OF<br>SERVICE
							</a></li>
						</div>
					</div>
				</ul>

			</div>
		</div>
		<!-- <div class="left"></div> -->
		<!-- <div class="right"></div> -->
		<div class="frame">
			<!-- This gets refreshed via AJAX -->

		<c:choose>
			<c:when test="${sessionScope.page == 'about'}">
				<%@ include file="about.jsp"%>
			</c:when>

			<c:when test="${sessionScope.page == 'commit' }">
				<%@ include file="commit.jsp"%>
			</c:when>
			
			<c:when test="${sessionScope.page == 'browse'}">
				<%@ include file="browse.jsp"%>
			</c:when>
			
			<c:when test="${sessionScope.page == 'history'}">
				<%@ include file="history.jsp"%>
			</c:when>
		</c:choose>

		</div>
		<div class="footer"><div class="copyright"><br>© 2016 Revature LLC&nbsp&nbsp</div></div>
	</div>
</body>

<script type="text/javascript">
	$(document).ready(function() {
		$("#browse").click(function() {
			$('.frame').load("pages/browse.jsp");
		});
		$("#commit").click(function() {
			$('.frame').load("pages/commit.jsp");
		});
		$("#history").click(function() {
			$('.frame').load("pages/history.jsp");
		});
		$("#about").click(function() {
			$('.frame').load("pages/login.jsp .about");
		});
		$("#terms").click(function() {
			$('.frame').load("pages/login.jsp .terms");
		});
	});
</script>
</html>