//alert('User Name: ' + userName);

$(document).ready(function() {
	fetchStudentbyUserName();
	loadImg(userName);
});
var currentRecord;
function fetchStudentbyUserName() {
	$.ajax({
		type: "GET",
		url: "/fetchStudentbyUserName/" + userName,
		success: function(record) {
			// Log the received record to the console for debugging
			console.log("Received Record:", record);
 currentRecord = record;
			// Check if the record is not empty before processing
			if (record) {
				var studentNameContainerHTML = `
                    <h3">
                       ${record.name}
                    </h3>
                `;
				var studentInfoHTML = `
                    <div class="card-body">
                        <p class="mb-0"><strong class="pr-1">Roll:</strong>${record.roll}</p>
                        <p class="mb-0"><strong class="pr-1">Registration:</strong>${record.registration}</p>
                        <p class="mb-0"><strong class="pr-1">Session:</strong>${record.session}</p>
                    </div>
                `;
				var studentDetailsHTML = `
                	<div class="card shadow-sm">
          <div class="card-header bg-transparent border-0">
	          <div class="card-header-title">
	            <h3 class="mb-0"><i class="far fa-clone pr-1"></i>General Information</h3>
	          </div>
	          <div class="card-header-editicon">
	          	<a href="#" onclick="editRecord()"><i class="far fa-edit"></i></a>            
	          </div> 
          </div>
          <div class="card-body pt-0">
            <table class="table table-bordered">
              <tr>
                <th width="30%">Father Name</th>
                <td width="2%">:</td>
                <td>${record.details.fatherName}</td>
              </tr>
              <tr>
                <th width="30%">Mother Name</th>
                <td width="2%">:</td>
                <td>${record.details.motherName}</td>
              </tr>
              <tr>
                <th width="30%">Phone</th>
                <td width="2%">:</td>
                <td>${record.details.phone}</td>
              </tr>
              <tr>
                <th width="30%">Email</th>
                <td width="2%">:</td>
                <td>${record.details.email}</td>
              </tr>
              <tr>
                <th width="30%">Address</th>
                <td width="2%">:</td>
                <td>${record.details.address}</td>
              </tr>
            </table>
          </div>
        </div>
		 `;
				// Show studentInfo

				$("#studentNameContainer").html(studentNameContainerHTML).show();
				$("#studentInfoContainer").html(studentInfoHTML).show();
				$("#studentDetailsContainer").html(studentDetailsHTML).show();
				//studentDetailsHTML
			} else {
				// Handle the case when the record is empty
				console.error("Empty record received");
			}
		},
		error: function(err) {
			alert("Error: " + err.responseText);
			console.error("Error:", err);
		}
	});
}

//====================================Edit==========================================================================
function editRecord() {
	//alert(currentRecord);
	console.log("Received Record:", currentRecord);
	var editFormHtml = `

 <div class="card shadow-sm">
          <div class="card-header bg-transparent border-0">
	         <h3 class="mb-0"><i class="far fa-edit"></i>Edit Student Record</h3>   
          </div>
          <div class="card-body pt-0">
         <form id="editForm" name="editForm" class="student-form-profile" action="@{/editStudentProfile}" method="post">
    <div class="form-group row">
        <div class="col-sm-10">
            <input type="hidden" id="id" name="studentDetailsId" class="form-control-plaintext" value="${currentRecord.details.studentDetailsId}">
            <input type="hidden" id="id" name="student" class="form-control-plaintext" value="${currentRecord.roll}">
        </div>
    </div>
    <div class="form-group row">
        <label for="editFatherName" class="col-sm-3 col-form-label">Father Name</label>
        <div class="col-sm-8">
            <input type="text" id="editFatherName" name="fatherName" class="form-control" value="${currentRecord.details.fatherName}">
        </div>
    </div>
    <div class="form-group row">
        <label for="editMotherName" class="col-sm-3 col-form-label">Mother Name</label>
        <div class="col-sm-8">
            <input type="text" id="editMotherName" name="motherName" class="form-control" value="${currentRecord.details.motherName}">
        </div>
    </div>
    <div class="form-group row">
        <label for="editPhone" class="col-sm-3 col-form-label">Phone</label>
        <div class="col-sm-8">
            <input type="text" id="editPhone" name="phone" class="form-control" value="${currentRecord.details.phone}">
        </div>
    </div>
    <div class="form-group row">
        <label for="editEmail" class="col-sm-3 col-form-label">Email</label>
        <div class="col-sm-8">
            <input type="text" id="editEmail" name="email" class="form-control" value="${currentRecord.details.email}">
        </div>
    </div>
    <div class="form-group row">
        <label for="editAddress" class="col-sm-3 col-form-label">Address</label>
        <div class="col-sm-8">
            <input type="text" id="editAddress" name="address" class="form-control" value="${currentRecord.details.address}">
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-10 offset-sm-5">
            <button type="button" id="update" class="btn btn-success">Update</button>
            <button type="button" id="cancel" class="btn btn-primary">Cancel</button>
        </div>
    </div>
</form>

          </div>
        </div>       
    `;
	$(".studentDetailsHTML").addClass("hidden");
	// Hide the studentDetailsContainer
	//  $("#studentDetailsContainer").hide();

	// Show the edit form
	$("#studentDetailsContainer").html(editFormHtml).show();

	// Attach click event for the update button
	$("#update").click(function(event) {
		// Get the form associated with the clicked button
		var editForm = $("#editForm");

		// Prevent the default form submission
		event.preventDefault();
		console.log(editForm.serialize());
		$.ajax({
			type: "POST",
			url: "/editStudentProfile",
			data: editForm.serialize(),
			success: function(result) {
				// Handle success, e.g., update the UI
				alert("Student updated successfully!");
				$("#editFormContainer").empty().hide();
				$("#studentDetailsContainer").show();
				fetchStudentbyUserName();
			},
			error: function(err) {
				alert("Error: " + JSON.stringify(err));
			}
		});
	});

	$("#cancel").click(function(event) {
		$("#editFormContainer").empty().hide();
		$("#studentDetailsContainer").show();
		fetchStudentbyUserName();
	});
}
//===========================================INSERT Image================================================

$('#imgUploadForm').submit(function(event) {
   
    event.preventDefault();
    
    var formData = new FormData();
    formData.append('profileImage', $('#profileImage')[0].files[0]);

    
    formData.append('fileName', userName);

    $.ajax({
      url: '/uploadImage',
      type: 'POST',
      data: formData,
      processData: false,
      contentType: false,
      success: function(response) {
        console.log('Image uploaded successfully');
         $("#imgUploadForm")[0].reset();
         $("#imgUploadForm").hide();
	     $("#profileImg").show();
	     loadImg(userName);
        // Additional logic after successful image upload
        // You may want to refresh the page or update some UI elements
      },
      error: function(xhr, status, error) {
        console.log('Error uploading image:', error);
        console.log('Image upload failed');
      }
    });
  });

  
  function editPhoto() {
    $("#profileImg").hide();
    $("#imgUploadForm").show();
}

$("#cancelUploadImg").click(function(event) {
    $("#imgUploadForm").hide();
    $("#profileImg").show();
});
//---------------------------------------------Load Image--------------------------------------------------------
function loadImg(imageName) {
    // Make an Ajax request to get the image
    $.ajax({
        url: '/images/' + imageName,
        type: 'GET',
        success: function(response) {
            // Set the source of the image to the retrieved image
            $('#profileImg').attr('src', '/images/' + imageName);
        },
        error: function(xhr, status, error) {
            console.log('Error loading image:', error);

            // If there's an error, set a default image
            $('#profileImg').attr('src', '/uploads/default.jpg');
        }
    });
}