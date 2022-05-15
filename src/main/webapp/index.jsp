<%@ page import="com.bill" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill Generation</title>

	<link rel="stylesheet" href="Views/bootstrap.min.css">
	<script src="Components/jquery-3.4.1.min.js"></script>
	<script src="Components/bill.js"></script>

</head>
<body>

	<div class="container"><div class="row"><div class="col-6">
	
	<h1>Locations</h1>

	<form id="formItem" name="formItem" method="post" action="items.jsp">
 		Location Code:
			<input id="locationCode" name="locationCode" type="text" class="form-control form-control-sm">
		<br> Location Name:
			<input id="locationName" name="locationName" type="text" class="form-control form-control-sm">
		<br> Date:
			<input id="date" name="date" type="date" class="form-control form-control-sm">
		<br> Time:
			<input id="time" name="time" type="text" class="form-control form-control-sm">
		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
		<input type="hidden" id="hidbillIDSave" name="hidbillIDSave" value="">
	</form>


				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
	<div id="divItemsGrid">
 		<%
 		bill itemObj = new bill();
 		out.print(itemObj.readItems());
 		%>
	</div>
	</div>
	</div>
	</div>

</body>
</html>