<%@page import="com.rays.pro4.controller.ORSView"%>
<%@page import="com.rays.pro4.Bean.UserBean"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ORS PROJECT</title>
</head>
<body>
<br>
	<br>
	<%
		UserBean userBean = (UserBean) session.getAttribute("user");

		boolean userLoggedIn = userBean != null;

		if (userLoggedIn) {
			ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);

		}
	%>
	<!-- <marquee behavior="alternate" scrollamount="300" loop="1"> -->
	<div align="center">
		<img src="img/customLogo.jpg" align="middle" width="318" height="127"
			border="0">
			</div>
	<!-- </marquee> -->

	<br>
	<br>

	<h1 align="center">
		<font size="10px" color="red"> <a
			href="<%=ORSView.WELCOME_CTL%>">Online Result System</a></font>
			<!--  style="text-decoration: none" -->
	</h1>
</body>
</html>