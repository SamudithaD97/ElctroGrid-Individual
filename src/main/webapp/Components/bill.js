$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// save
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
 	$("#alertSuccess").text("");
 	$("#alertSuccess").hide();
 	$("#alertError").text("");
 	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm();
	if (status != true)
 	{
 		$("#alertError").text(status);
 		$("#alertError").show();
 		return;
 	}
	
	// If valid------------------------
	var type = ($("#hidbillIDSave").val() == "") ? "POST" : "PUT";
 	$.ajax(
 	{
 		url : "billAPI",
 		type : type,
 		data : $("#formItem").serialize(),
 		dataType : "text",
 		complete : function(response, status)
 	{
 
	onItemSaveComplete(response.responseText, status);
 	}
 });
	
	
});
	
// update	
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidbillIDSave").val(
					$(this).closest("tr").find('#hidbillIDUpdate').val());
 	$("#locationCode").val($(this).closest("tr").find('td:eq(0)').text());
	$("#locationName").val($(this).closest("tr").find('td:eq(1)').text());
 	$("#date").val($(this).closest("tr").find('td:eq(2)').text());
 	$("#time").val($(this).closest("tr").find('td:eq(3)').text());
});

$(document).on("click", ".btnRemove", function(event)
{
 	$.ajax(
 	{
 		url : "billAPI",
 		type : "DELETE",
 		data : "locationId=" + $(this).data("billid"),
 		dataType : "text",
 		complete : function(response, status)
 		{
 		onItemDeleteComplete(response.responseText, status);
 		}
 	});
});


function onItemSaveComplete(response, status)
	{
	if (status == "success")
	 	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
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
	 	$("#hidbillIDSave").val("");
	 	$("#formItem")[0].reset();
	}
	
function onItemDeleteComplete(response, status)
{
	if (status == "success")
 	{
 		var resultSet = JSON.parse(response);
 		if (resultSet.status.trim() == "success")
 	{
 		$("#alertSuccess").text("Successfully deleted.");
 		$("#alertSuccess").show();
 		$("#divItemsGrid").html(resultSet.data);
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

function validateItemForm()
{
	// NAME
	if ($("#locationCode").val().trim() == "")
 	{
 		return "Insert name.";
 	}
	// DATE
	if ($("#locationName").val().trim() == "")
 	{
 		return "Insert Date.";
 	}
	// ACCOUNT NUMBER
	if ($("#date").val().trim() == "")
	{
 		return "Insert Account Number.";
 	}
	// PRE READING
	if ($("#time").val().trim() == "")
 	{
 		return "Insert Pre Reading Value.";
 	}

	return true;
}
