$(document).ready(function()

{
	if ($("#alertSuccess").text().trim() == "")
	 {
	 $("#alertSuccess").hide();
 }
 	$("#alertError").hide();
});


// SAVE 

$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts
	
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide();
	 
	 
	// Form validation
	
	var status = validateBillForm();
	if (status != true)
	 {
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
	 }
	 
	 
	// If valid
	
	var type = ($("#hidbill_ID_numberSave").val() == "") ? "POST" : "PUT";
	 $.ajax(
		 {
			 url : "BillAPI",
			 type : type,
			 data : $("#formBill").serialize(),
			 dataType : "text",
			 complete : function(response, status)
			 
			 {
			 	onBillSaveComplete(response.responseText, status);
			 }
	 	});
	});

function onBillSaveComplete(response, status)
{
	if (status == "success")
	 {
	 var resultSet = JSON.parse(response);
	 if (resultSet.status.trim() == "success")
	 {
		 $("#alertSuccess").text("Successfully saved.");
		 $("#alertSuccess").show();
		 $("#divBillGrid").html(resultSet.data);
		 } 
		 	else if (resultSet.status.trim() == "error")
		 {
		 $("#alertError").text(resultSet.data);
		 $("#alertError").show();
	 }
	 
	 } 
	 else if (status == "error")
	 {
		 $("#alertError").text("Error while saving.");
		 $("#alertError").show();
	 } 
	 else
		 {
			 $("#alertError").text("Unknown error while saving..");
			 $("#alertError").show();
		 }
		 
		 14
		 $("#hidbill_ID_numberSave").val("");
		 $("#formBill")[0].reset();
}


// UPDATE

$(document).on("click", ".btnUpdate", function(event)
{
	 $("#hidbill_ID_numberSave").val($(this).data("billnumber"));
	 $("#bill_number").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#bill_type").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#bill_description").val($(this).closest("tr").find('td:eq(2)').text());
	 $("#total_amount").val($(this).closest("tr").find('td:eq(3)').text());
	 $("#bill_receipt").val($(this).closest("tr").find('td:eq(4)').text());
});


//REMOVE

$(document).on("click", ".btnRemove", function(event)
{
	 $.ajax(
	 {
		 url : "BillAPI",
		 type : "DELETE",
		 data : "bill_ID_number=" + $(this).data("billnumber"),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 	onBillDeleteComplete(response.responseText, status);
		 }
	 });
});


function onBillDeleteComplete(response, status)
{
	if (status == "success")
	 {
		 var resultSet = JSON.parse(response);
		 if (resultSet.status.trim() == "success")
		 {
			 $("#alertSuccess").text("Successfully deleted.");
			 $("#alertSuccess").show();
			 $("#divBillGrid").html(resultSet.data);
		 } 
		 else if (resultSet.status.trim() == "error")
		 {
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		 }
	 }
	  else if (status == "error")
	 {
		 $("#alertError").text("Error while deleting.");
		 $("#alertError").show();
	 } 
	 else
	 {
		 $("#alertError").text("Unknown error while deleting..");
		 $("#alertError").show();
	 }
}


// CLIENT-MODEL

function validateBillForm()
{
	// Bill Number
	if ($("#bill_number").val().trim() == "")
	 {
	 	return "Insert bill_number.";
	 }
	 
	// Bill Type
	if ($("#bill_type").val().trim() == "")
	 {
	 	return "Insert bill_type.";
	 }
	 
	 // Bill Description
	 if ($("#bill_description").val().trim() == "")
	 {
	 	return "Insert bill_description.";
	 }
	
	// Total Amount
	if ($("#total_amount").val().trim() == "")
	 {
	 	return "Insert total_amountv.";
	 }
	// is numerical value
	var tmptotal_amount = $("#total_amount").val().trim();
	if (!$.isNumeric(tmptotal_amount))
	 {
	 	return "Insert a numerical value for total_amount.";
	 }
	// convert to decimal amount 
	 $("#total_amount").val(parseFloat(tmptotal_amount).toFixed(2));
	 
	// Bill Receipt
	if ($("#bill_receipt").val().trim() == "")
	 {
	 	return "Insert bill_receipt.";
	 }
	return true;
}