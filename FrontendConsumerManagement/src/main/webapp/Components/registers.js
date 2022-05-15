$(document).on("click", "#btnSave", function(event)
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateRegisterForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidaccount_noSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "RegistersAPI", 
 type : type, 
 data : $("#formRegister").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onItemSaveComplete(response.responseText, status); 
 } 
 }); 
});

function onRegisterSaveComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 }
$("#hidaccount_noSave").val(""); 
$("#formRegister")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
		{ 
		$("#hidaccount_noSave").val($(this).data("account_no")); 
		 $("#name").val($(this).closest("tr").find('td:eq(0)').text()); 
		 $("#address").val($(this).closest("tr").find('td:eq(1)').text()); 
		 $("#phone").val($(this).closest("tr").find('td:eq(2)').text()); 
		 $("#email").val($(this).closest("tr").find('td:eq(3)').text()); 
		 $("#username").val($(this).closest("tr").find('td:eq(2)').text()); 
		 $("#password").val($(this).closest("tr").find('td:eq(3)').text());
		});




$(document).on("click", ".btnRemove", function(event)
		{ 
		 $.ajax( 
		 { 
		 url : "RegistersAPI", 
		 type : "DELETE", 
		 data : "account_no=" + $(this).data("account_no"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onRegisterDeleteComplete(response.responseText, status); 
		 } 
		 }); 
		});
		
function onRegisterDeleteComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}


// CLIENT-MODEL================================================================
function validateRegisterForm()
{
	// NAME
	if ($("#name").val().trim() == "")
	{
	return "Insert Name.";
	}
	// ADDRESS
	if ($("#address").val().trim() == "")
	{
	return "Insert Address.";
	}
	// PHONE
	if ($("#phone").val().trim() == "")
	{
	return "Insert Phone.";
	}
	// EMAIL
	if ($("#email").val().trim() == "")
	{
	return "Insert Email.";
	}
	// USERNAME
	if ($("#username").val().trim() == "")
	{
	return "Insert UserName.";
	}
	// PASSWORD
	if ($("#password").val().trim() == ""){
	return "Insert Password.";

}

	return true;
}