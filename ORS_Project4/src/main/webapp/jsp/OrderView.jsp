<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.OrderCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Order Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.OrderBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.ORDER_CTL%>" method="post">

			<%
				List l = (List) request.getAttribute("roleList");
			%>


			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Order </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Order </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>">


			<table>
				<tr>
					<th align="left">Order Name <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="orderName"
						placeholder="Enter Order Name" size="25"
						value="<%=DataUtility.getStringData(bean.getOrderName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("orderName", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Order Type <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="orderType"
						placeholder="Enter Order Type" size="25"
						value="<%=DataUtility.getStringData(bean.getOrderType())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("orderType", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Amount<span style="color: red">*</span> :
					</th>
					<td><input type="text" style="width: 212px" name="amount"
						maxlength="10" placeholder="Enter amount" size="25"
						value="<%=(DataUtility.getStringData(bean.getAmount()).equals("0") ? ""
					: DataUtility.getStringData(bean.getAmount()))%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("amount", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Order Date <span style="color: red">*</span>
						:
					</th>
					<td><input type="date" name="orderDate"
						placeholder="Enter Order date" size="25"
						value="<%=DataUtility.getStringData(bean.getOrderDate())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("orderDate", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>



				<tr>
					<th align="left">Address <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="address"
						placeholder="Enter Address" size="25"
						value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=OrderCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=OrderCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=OrderCtl.OP_SAVE%>"> <%--  &nbsp;  &nbsp;
                    <input type="submit" name="operation" value="<%=OrderCtl.OP_RESET%>"></td> --%>

						<%
							}
						%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>