<%@page import="com.rays.pro4.Bean.OrderBean"%>
<%@page import="com.rays.pro4.Model.RoleModel"%>
<%@page import="com.rays.pro4.Model.OrderModel"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.OrderListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Order List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- <script>
$( function() {
	  $( "#abcd" ).datepicker({
		  changeMonth :true,
		  changeYear :true,
		  yearRange :'0:+10',
		  dateFormat:'dd-mm-yy',

//Disable for Sunday
		  beforeShowDay : disableSunday,		  
//Disable for back date
		 // minDate : 0   
	  });
} );
</script> -->

</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.OrderBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.ORDER_LIST_CTL%>" method="post">

		<center>

			<div align="center">
				<h1>Order List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				// List rlist = (List) request.getAttribute("RoleList");

				List olist = (List) request.getAttribute("OrderList");

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<OrderBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<th></th>
					<td align="center"><label>OrderName</font> :
					</label> <input type="text" name="orderName" placeholder="Enter Order Name"
						value="<%=ServletUtility.getParameter("orderName", request)%>">
					<td align="center"><label>OrderType</font> :
					</label> <input type="text" name="orderType" placeholder="Enter Order type"
						value="<%=ServletUtility.getParameter("orderType", request)%>">
					<td align="center"><label>Amount</font> :
					</label> <input type="number" name="amount" placeholder="Enter amount"
						value="<%=ServletUtility.getParameter("amount", request)%>">
					<td align="center"><label>OrderDate</font> :
					</label> <input type="date" name="orderDate" placeholder="Enter date"
						value="<%=ServletUtility.getParameter("orderDate", request)%>">
						
						<td><label>Address</font> :
					</label> <%=HTMLUtility.getList1("address", String.valueOf(bean.getAddress()), olist)%></td>
						
					<%-- <td align="center"><label>address</font> :
					</label> <input type="text" name="address" placeholder="Enter address"
						value="<%=ServletUtility.getParameter("address", request)%>"> --%>


					<%-- <td><label>Order date</font> :
					</label> <%=HTMLUtility.getList("orderDate1", String.valueOf(bean.getOrderDate()), olist)%></td> --%>

						<%-- &emsp; <label>Role</font> :
					</label> <%=HTMLUtility.getList("orderList", String.valueOf(bean.getOrderType()), olist)%> --%>

						<%-- <label>Order date</font> :
					
					
					&emsp; <label>Order date</font> :
					</label> <%=HTMLUtility.getList("orderDate", String.valueOf(bean.getOrderDate()), olist)%>
					 --%> <%-- <label></font> </label> <%=HTMLUtility.getList("roleid", String.valueOf(bean.getRoleId()), rlist)%>

						<label>LoginId</font> :
					</label> <input type="text" name="loginid" placeholder="Enter Login-Id"
						value="<%=ServletUtility.getParameter("login", request)%>">
						&emsp; <label>Role</font> :
					</label> <%=HTMLUtility.getList("roleid", String.valueOf(bean.getRoleId()), rlist)%>
						&nbsp; <label>Date of Birth</font> :
					</label> <input type="text" name="dateofb" id="abcd"
						placeholder="Enter dob"
						value="<%=ServletUtility.getParameter("dateofb", request)%>"> --%>


						<%-- <%=HTMLUtility.getList("loginid", String.valueOf(bean.getRoleId()), ulist)%> --%>

						<td><input type="submit" name="operation"
						value="<%=OrderListCtl.OP_SEARCH%>"> </td>
						<td><input type="submit"
						name="operation" value="<%=OrderListCtl.OP_RESET%>"></td>
				</tr>
				<%-- <tr>
					<td><label>Order date</font> :
					</label> <%=HTMLUtility.getList("orderDate1", String.valueOf(bean.getOrderDate()), olist)%></td>
				</tr> --%>

			</table>
			<table>
				
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: yellow">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>OrderName</th>
					<th>OrderType</th>
					<th>Amount</th>
					<th>OrderDate</th>
					<th>Address</th>

					<th>Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
							/* RoleModel model = new RoleModel();
							RoleBean rolebean = new RoleBean();
							rolebean = model.findByPK(bean.getRoleId()); */
				%>


				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"<%-- <%if (OrderBean.getId() == bean.getId() || bean.getRoleId() == RoleBean.ADMIN) {%>
						<%="disabled"%> <%}%> --%>></td>
					<td><%=index++%></td>
					<td><%=bean.getOrderName()%></td>
					<td><%=bean.getOrderType()%></td>
					<td><%=bean.getAmount()%></td>
					<td><%=bean.getOrderDate()%></td>
					<td><%=bean.getAddress()%></td>
					<td><a href="OrderCtl?id=<%=bean.getId()%>"<%-- <%if (OrderBean.getId() == bean.getId() || bean.getRoleId() == RoleBean.ADMIN) {%>
						onclick="return false;" <%}%> --%>>Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">
				<tr>
					<th></th>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=OrderListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=OrderListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td>&nbsp;&nbsp;<input type="submit" name="operation"
						value="<%=OrderListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=OrderListCtl.OP_NEW%>"></td>

					<%-- <%	OrderModel model = new OrderModel();
                     %>
                     
                     <% if(list.size() < pageSize || model.nextPK()-1 == bean.getId() ){%>

                     		<td align="right"><input type="submit" name="operation" disabled="disabled" value="<%=OrderListCtl.OP_NEXT%>"></td>
                     <% }else{%>
                     		<td align="right"><input type="submit" name="operation" value="<%=OrderListCtl.OP_NEXT%>"></td>
                     <%} %> --%>

					<td align="right"><input type="submit" name="operation"
						value="<%=OrderListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=OrderListCtl.OP_BACK%>"></td>
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>

	</center>

	<%@include file="Footer.jsp"%>
</body>
</html>
