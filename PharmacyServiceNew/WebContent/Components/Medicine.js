$(document).ready(function() 
		{  
	if ($("#alertSuccess").text().trim() == "")  
    {   
		$("#alertSuccess").hide();  
     }  
	     $("#alertError").hide(); 
	  
});

$(document).on("click", "#btnSave", function(event) 
		{  
			$("#alertSuccess").text("");  
			$("#alertSuccess").hide();  
			$("#alertError").text("");  
			$("#alertError").hide(); 
			
			
			var status = validateMediForm();  
			if (status != true)  
			{   
				$("#alertError").text(status);   
				$("#alertError").show();   
				return;  
			} 
			
			var type = ($("#hidmediIDSave").val() == "") ? "POST" : "PUT"; 
			
			$.ajax( 
			{  
				url : "MedicineAPI",  
				type : type,  
				data : $("#formmedi").serialize(),  
				dataType : "text",  
				complete : function(response, status)  
				{   
					onMediSaveComplete(response.responseText, status);  
					
				} 
			
		}); 
}); 
		
function onMediSaveComplete(response, status) 
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

		$("#hidmediIDSave").val("");  
		$("#formmedi")[0].reset(); 
		
}

$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "MedicineAPI",   
		type : "DELETE",   
		data : "mediID=" + $(this).data("mediid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onMediDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 


function onMediDeleteComplete(response, status) 
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

$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidmediIDSave").val($(this).closest("tr").find('#hidMediIDUpdate').val());     
	$("#mediCode").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#mediName").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#mediPrice").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#mediDesc").val($(this).closest("tr").find('td:eq(3)').text());
}); 


function validateMediForm() 
{  
	// CODE  
	if ($("#mediCode").val().trim() == "")  
	{   
		return "Insert Medicine Code.";   
	}

	 
	 // NAME  
	if ($("#mediName").val().trim() == "")  
	{   
		return "Insert Medicine Name.";  
	}
	
	if ($("#mediPrice").val().trim() == "")  
	{   
		return "Insert Medicine Price.";  
	} 
	 
	 // is numerical value  
	var tmpPrice = $("#mediPrice").val().trim();  
	if (!$.isNumeric(tmpPrice))  
	{   
		return "Insert a numerical value for Medicine Price.";  
	} 
	 

	 // convert to decimal price  
	$("#mediPrice").val(parseFloat(tmpPrice).toFixed(2)); 
	 
	 // DESCRIPTION------------------------  
	if ($("#mediDesc").val().trim() == "")  
	{   
		return "Insert Medicine Description.";  
		
	} 
	 
	 return true;
	
}
