<%@page import="com.Register"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/registers.js"></script>
</head>
<body>
<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Register</h1>
				<form id="formRegister" name="formRegister" method="post" action="registers.jsp">
					Name:<input id="name" name="name" type="text" class="form-control form-control-sm"> <br> 
					Address:<input id="address" name="address" type="text" class="form-control form-control-sm"> <br> 
					Phone: <input id="phone" name="phone" type="text" class="form-control form-control-sm"> <br> 
				    Email: <input id="email" name="email" type="text" class="form-control form-control-sm"> <br>
				    Username: <input id="username" name="username" type="text" class="form-control form-control-sm"> <br> 
				    Password: <input id="password" name="password" type="text" class="form-control form-control-sm"> <br>  
				    <input id="btnSave" name="btnSave" type="button" value="Save"class="btn btn-primary"> 
				    <input type="hidden" id="hidaccount_noSave" name="hidaccount_noSave" value="">
				</form>
				<br>
				<div>
					<%
					Register consumerObj = new Register();
					out.print(consumerObj.readDetails());
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>