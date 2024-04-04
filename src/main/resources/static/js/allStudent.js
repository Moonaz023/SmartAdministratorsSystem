//===========================================Insert=========================================================
        $(document).ready(function () {
            fetchStudent();
           
             
            $("#insertStudent").click(function () {				
				 // Get the form associated with the clicked button
                var form = $("#formAllStudent");
                // Prevent the default form submission
                event.preventDefault();
                // Make the AJAX request
                $.ajax({
                    type: "POST",
                    url: form.attr("action"),
                    data: form.serialize(),
                    success: function (stu_result) {
                        fetchStudent();
                        $("#formAllStudent")[0].reset();
                    },
                    error: function (err) {
                        alert("Error: " + JSON.stringify(err));
                    }
                });
            });
        });
//==========================================Show Data===========================================================

        var stu_data = "";

        function fetchStudent() {
            $.ajax({
                type: "GET",
                url: "/admin/allfetchStudent",
                success: function (stu_response) {
					 //console.log(response); 
                    stu_data = stu_response;
                    $('#allStudent').DataTable().destroy();
                    $('#allStudent_tableresult').empty();

                    for (i = 0; i < stu_response.length; i++) {
                  $("#allStudent_tableresult").append(
                            '<tr class="tr">' +
                            
//                          '<td><a href="#" onclick="Record(' + data[i].id + ')">' + response[i].roll + '</a></td>' +
                            '<td>' + stu_response[i].roll + '</td>' +
                            '<td>' + stu_response[i].name + '</td>' +
                            '<td>' + stu_response[i].session + '</td>' +
                            '<td>' + stu_response[i].registration + '</td>' +
                            '<td><a href="#" onclick="editaStuRecord(\'' + stu_data[i].roll + '\')">Edit</a></td>' +
                            '<td><a href="#" onclick="deleteaStuRecord(\'' + stu_data[i].roll + '\')">Delete</a></td>' +
                            
                            '</tr>'
                        );
                    }

                    var totalAmount = 0;
                    // Display total amount
                    $("#totalAmount").text("Total Student: " + stu_data.length);

                    // Initialize DataTables plugin
                    $('#allStudent').DataTable();
                },
                error: function (err) {
                    alert("Error: " + err);
                    console.error("Error:", err);
                }
            });
        } 
        
  //====================================Edit==========================================================================
function editaStuRecord(roll) {
    var stu_response = stu_data.find(function (stu_item) {
        return stu_item.roll === roll;
    });

    var editFormHtml = `
        <h2>Edit Student Record</h2>
        <form id="editForm" name="editForm" class="student_edit_form" action="@{/editStudent}" method="post">
            
            <label for="editRoll">Roll</label>
            <input type="text" id="editRoll" name="roll" value="${stu_response.roll}"><br>
            <label for="editName">Name</label>
            <input type="text" id="editName" name="name" value="${stu_response.name}"><br>
            <label for="editSesson">Session</label>
            <input type="text" id="editSesson" name="session" value="${stu_response.session}"><br>
            <label for="editRegistration">Passing Year</label>
            <input type="text" id="editRegistration" name="registration" value="${stu_response.registration}"><br>
            
            <button type="button" id="updateaStuRecord" class="btn btn-success">Update</button>
            <button type="button" id="cancelupdateaStuRecord" class="btn btn-primary">Cancel</button>
        </form>
    `;
    

    // Show the edit form
    $("#editFormContainer").html(editFormHtml).show();

    // Hide the container
    $(".container").addClass("hidden");
    
    

    // Attach click event for the update button
    $("#updateaStuRecord").click(function (event) {
        // Get the form associated with the clicked button
        var editForm = $("#editForm");

        // Prevent the default form submission
        event.preventDefault();
        console.log(editForm.serialize())
        $.ajax({
            type: "POST",
            url: "/admin/editStudent",
            data: editForm.serialize(),
            success: function (result) {
                // Handle success, e.g., update the UI
                alert("Student updated successfully!");
                $("#editFormContainer").empty().hide();
                $(".container").removeClass("hidden");
                fetchStudent();
            },
            error: function (err) {
                alert("Error: " + JSON.stringify(err));
            }
        });
    });
     $("#cancelupdateaStuRecord").click(function (event) {
        $("#editFormContainer").empty().hide();
        $(".container").removeClass("hidden");
        fetchStudent();
    });
}
//=========================================DELETE====================================================================

    function deleteaStuRecord(roll) {
		 var stu_record = stu_data.find(function (stu_item) {
        return stu_item.roll === roll;
    });
        $.ajax({
            type: "DELETE",
            url: "/admin/deleteStudent?id=" + stu_record.roll,
            success: function (stu_result) {
                // Refresh the table after successful deletion
                fetchStudent();
            },
            error: function (err) {
                alert("Error deleting record: " + JSON.stringify(err));
            }
        });
    }
//===========================================INSERT CSV================================================
$(document).ready(function() {
 //fetchStudent();
  $('#csvuploadForm').submit(function(event) {
    event.preventDefault();
    
    var formData = new FormData();
    formData.append('file', $('#csvFile')[0].files[0]);
    
    $.ajax({
      url: '/admin/insert_CSV_data',
      type: 'POST',
      data: formData,
      processData: false,
      contentType: false,
      success: function(save) {
        alert('Data inserted successfully');
         
        fetchStudent();
        $("#csvuploadForm")[0].reset();
      },
      error: function(xhr, status, error) {
        console.log('Error uploading CSV file:', error);
      }
    });
  });
});





            