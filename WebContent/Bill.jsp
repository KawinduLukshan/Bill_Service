<%@ page import="com.Bill"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/Bill.js"></script>
</head>
<body>
<div class="container"><div class="row"><div class="col-6">
<h1>Billing Management</h1>
<form id="formBill" name="formBill">
 Bill_number:
 <input id="bill_number" name="bill_number" type="text"
 class="form-control form-control-sm">
 <br> Bill_type:
 <input id="bill_type" name="bill_type" type="text"
 class="form-control form-control-sm">
 <br> Bill_description:
 <input id="bill_description" name="bill_description" type="text"
 class="form-control form-control-sm">
 <br> Total_amount:
 <input id="total_amount" name="total_amount" type="text"
 class="form-control form-control-sm">
 <br> Bill_receipt:
 <input id="bill_receipt" name="bill_receipt" type="text"
 class="form-control form-control-sm">
 <br>
 <input id="btnSave" name="btnSave" type="button" value="Save"
 class="btn btn-primary">
 <input type="hidden" id="hidbill_ID_numberSave"
 name="hidbill_ID_numberSave" value="">
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divBillGrid">
 <%
 Bill BillObj = new Bill();
 out.print(BillObj.readBill());
 %>
</div>
</div> </div> </div>
</body>
</html>