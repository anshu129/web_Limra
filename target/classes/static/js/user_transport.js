$(document).ready(function(){
	
});

function submitTransportDetails(){
	debugger;
	
	if($("#date").val().trim() == ""){
		alert('Select date');
		return false;
	}else if($("#time").val().trim() == ''){
		alert('Select time');
		return false;
	}else if($("#car-model").val().trim() ==''){
		alert('Fill car model');
		return false;
	}else if($("#reg-no").val().trim() == ''){
		alert('Fill car registration');
		return false;
	}else if($("#visitor-slip").val().trim() == ''){
		alert('Fill visitor slip');
		return false;
	}else if($("#flc-number").val().trim() == ''){
		alert('Fill FLC number');
		return false;
	}else if($("#associate-name").val().trim() == ''){
		alert('Fill associate name');
		return false;
	}else if($("#associate-flc-no").val().trim() == ''){
		alert('Fill associate FLC number');
		return false;
	}else if($("#dname").val().trim() == ''){
		alert('Fill driver name');
		return false;
	}
	
	var date = $("#date").val();
	var time = $("#time").val();
	var carModel = $("#car-model").val();
	var regNo = $("#reg-no").val();
	var visitorSlip = $("#visitor-slip").val();
	var flcNumber = $("#flc-number").val();
	var associateName = $("#associate-name").val();
	var associateFlcNo = $("#associate-flc-no").val();
	var driverName = $("#dname").val();
	
	var clientsData = [];
	var newDate = new Date().toISOString().slice(0,10);
	for(var i=0; i<7;i++){
		if($("#clientname"+i).val() != '' && $("#mobileno"+i).val() != ''){
			var clients ={};
			 clients = {"name": $("#clientname"+i).val(), "mobileNumber": $("#mobileno"+i).val(), "location": $("#pickuplocation"+i).val(), "createDate": newDate}
			clientsData.push(clients);
		}
	}
	
	var data = {
		"visiteDate": date,
		"time": time,
	 	"carModel": carModel,
		"carRegistration": regNo,
		"visitorSlip": visitorSlip,
		"associateId": flcNumber,
		"associateName": associateName,
		"driverName": driverName,
		"flcNumber": flcNumber,
		"createDate": newDate,
		"associateFlcNo": associateFlcNo,
	    
	    "transportClients": clientsData
	 
	}
	
	$.ajax({
			contentType : "application/json; charset=utf-8",
			url : "/user/saveTransportDetails",
			method : 'POST',
			async:false,
			data: JSON.stringify(data),
			headers: {
    	      'X-CSRF-TOKEN': $("meta[name='_csrf']").attr("content")
    	    },
			success : function(callback) {
            debugger;
            alert(callback)
            window.location.href="/user/Transport"
            $("#date").val('');
			$("#time").val('');
			$("#car-model").val('');
			$("#reg-no").val('');
			$("#visitor-slip").val('');
			$("#flc-number").val('');
			$("#associate-name").val('');
			$("#associate-flc-no").val('');
			for(var i=0; i<7;i++){
				if($("#clientname"+i).val() != '' && $("#mobileno"+i).val() != ''){
					$("#clientname"+i).val(''); 
					$("#mobileno"+i).val('');
					$("#pickuplocation"+i).val('');
				}
			}
			},error: function(error){
		      console.log(JSON.stringify(error));
    		}
    	});
	
}