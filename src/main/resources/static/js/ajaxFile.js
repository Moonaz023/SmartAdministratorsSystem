//===========================================Insert=========================================================
        $(document).ready(function () {
            getAllRecords();
            $("#insert").click(function () {				
				 // Get the form associated with the clicked button
                var form = $("#formEmployee");
                // Prevent the default form submission
                event.preventDefault();
                // Make the AJAX request
                $.ajax({
                    type: "POST",
                    url: form.attr("action"),
                    data: form.serialize(),
                    success: function (result) {
                        getAllRecords();
                        $("#formEmployee")[0].reset();
                    },
                    error: function (err) {
                        alert("Error: " + JSON.stringify(err));
                    }
                });
            });
        });
        
  //====================================Edit==========================================================================
function editRecord(id) {
//	alert(id);
    var record1 = data1.find(function (item1) {
        return item1.id === id;
    });

    var editFormHtml = `
        <h2>Edit Student Record</h2>
        <form id="editForm" name="editForm" class="student-form" action="@{/update}" method="post">
            <input type="hidden" id="id" name="id" value="${record1.id}"><br>
            <label for="editRoll">Roll</label>
            <input type="text" id="editRoll" name="roll" value="${record1.roll}"><br>
            <label for="editName">Name</label>
            <input type="text" id="editName" name="name" value="${record1.name}"><br>
            <label for="editSesson">Session</label>
            <input type="text" id="editSesson" name="sesson" value="${record1.sesson}"><br>
            <label for="editYear">Passing Year</label>
            <input type="text" id="editYear" name="year" value="${record1.year}"><br>
            <label for="editCGPA">CGPA</label>
            <input type="text" id="editCGPA" name="cgpa" value="${record1.cgpa}"><br>
            <button type="button" id="update" class="btn btn-success">Save</button>
        </form>
    `;

    // Show the edit form
    $("#editFormContainer").html(editFormHtml).show();

    // Hide the container
    $(".container").addClass("hidden");
    
    

    // Attach click event for the update button
    $("#update").click(function (event) {
        // Get the form associated with the clicked button
        var editForm = $("#editForm");

        // Prevent the default form submission
        event.preventDefault();
        console.log(editForm.serialize())
        $.ajax({
            type: "POST",
            url: "/admin/update",
            data: editForm.serialize(),
            success: function (result) {
                // Handle success, e.g., update the UI
                alert("Student updated successfully!");
                $("#editFormContainer").empty().hide();
                $(".container").removeClass("hidden");
                getAllRecords();
            },
            error: function (err) {
                alert("Error: " + JSON.stringify(err));
            }
        });
    });
}


//==========================================Show Data==========================================================================

        var data = "";

        function getAllRecords() {
            $.ajax({
                type: "GET",
                url: "/admin/getAllStudents",
                success: function (response1) {
					 //console.log(response); // Add this line
                    data1 = response1;
                    $('#example').DataTable().destroy();
                    $('#tableresult').empty();

                    for (i = 0; i < response1.length; i++) {
                        var editUrl = "/edit/" + response1[i].id; // Replace with your actual edit URL
                        var deleteUrl = "/delete/" + response1[i].id; // Replace with your actual delete URL
             //           console.log("Row data:", response[i]); // Add this line

                        $("#tableresult").append(
                            '<tr class="tr">' +
                            
                            '<td><a href="#" onclick="Record(' + data1[i].id + ')">' + response1[i].roll + '</a></td>' +
                            '<td>' + response1[i].name + '</td>' +
                            '<td>' + response1[i].sesson + '</td>' +
                            '<td>' + response1[i].year + '</td>' +
                            '<td>' + response1[i].cgpa + '</td>' +
                            '<td><a href="#" onclick="editRecord(' + data1[i].id + ')">Edit</a></td>' +
                            '<td><a href="#" onclick="deleteStuRecord(' + response1[i].id + ')">Delete</a></td>' +
                            
                            '</tr>'
                        );
                    }

                    var totalAmount = 0;
                    // Display total amount
                    $("#totalAmount").text("Total Student: " + data1.length);

                    // Initialize DataTables plugin
                    $('#example').DataTable();
                },
                error: function (err) {
                    alert("Error: " + err);
                    console.error("Error:", err);
                }
            });
        }
//===========================================INSERT CSV================================================
//$(document).ready(function() {
//  getAllRecords();
//  
//  $('#uploadForm').submit(function(event) {
//    console.log('Data insert failed');
//    event.preventDefault();
//    
//    var formData = new FormData();
//    formData.append('file', $('#csvFile')[0].files[0]);
//    
//    $.ajax({
//      url: '/insert-data',
//      type: 'POST',
//      data: formData,
//      processData: false,
//      contentType: false,
//      success: function(response) {
//        console.log('Data inserted successfully');
//        getAllRecords(); // Call getAllRecords() after successful insertion
//        $("#uploadForm")[0].reset(); // Reset the form
//      },
//      error: function(xhr, status, error) {
//        console.log('Error uploading CSV file:', error);
//        console.log('Data insert failed');
//      }
//    });
//  });
//});

 function uploadFile() {
        var formData = new FormData();
        formData.append("file", document.getElementById("fileInput").files[0]);

        var csrfToken = document.getElementsByName("_csrf")[0].value;

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/admin/insert-data", true);

        xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);

        xhr.onload = function() {
            if (xhr.status === 200) {
                alert("File uploaded successfully!");
                 getAllRecords();
            } else {
                var errorMessage = xhr.responseText || "Unknown error occurred.";
                alert("Error uploading the file. Details: " + errorMessage);
            }
        };

        xhr.onerror = function() {
            alert("Network error occurred. Please try again later.");
        };

        xhr.send(formData);
    }

//=================================Download Testimonial=========================================================   
 
function Record(id) {
    var record = data1.find(function (item1) {
        return item1.id === id;
    });

    var editFormHtml = `
        <form id="editForm">
          <button type="button" onclick="downloadTestimonialPDF(${record.id})">Download PDF</button>
          
        </form>
    `;

    // Show the edit form
    $("#editFormContainer").html(editFormHtml).show();
    // Hide the container
    $(".container").addClass("hidden");
}

function downloadTestimonialPDF(id) {
    window.location.href = "/admin/download_singlePDF?id=" + id;
    //$("#editFormContainer").empty().hide();
                    // Empty the edit form and hide it
            		$("#editFormContainer").empty().hide();
            		// Show the container again
            		$(".container").removeClass("hidden");
}


//=========================================DELETE====================================================================

    function deleteStuRecord(id) {
        $.ajax({
            type: "DELETE",
            url: "/admin/delete?id=" + id,
            success: function (result) {
                // Refresh the table after successful deletion
                getAllRecords();
            },
            error: function (err) {
                alert("Error deleting record: " + JSON.stringify(err));
            }
        });
    }
//================================================DownloadCSV================================================================

  /*      function downloadCSV() {
            var csv = [];
            var rows = $("#example").find("tr");

            for (var i = 0; i < rows.length; i++) {
                var data = [];
                var row = rows[i];
                var cells = $(row).find("td, th");

                for (var j = 0; j < cells.length; j++) {
                    var cell = cells[j];
                    data.push(cell.innerText);
                }

                csv.push(data.join(","));
            }

            var csvContent = "data:text/csv;charset=utf-8," + csv.join("\n");
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "student_records.csv");
            document.body.appendChild(link);
            link.click();
        }
*/

  $(document).ready(function () {


    $("#downloadCSV").click(function () {
        downloadCSV();
    });

});

function downloadCSV() {
    window.location.href = "/admin/downloadCSV";
}       
//=============================================EXCEL DOWNLOAD============================================

  $(document).ready(function () {
    // ...

    $("#downloadExcel").click(function () {
        downloadExcel();
    });

    // ...
});

function downloadExcel() {
    window.location.href = "/admin/downloadExcel";
}

//==============================================DOC DOWNLOAD===============================================

$(document).ready(function () {
            $("#downloadDocBtn").click(function () {
                downloadDoc();
            });
            
            function downloadDoc() {
        window.location.href = "/admin/downloadDoc";
        }
 });
 //=============================================downloadPDF=================================================

   $(document).ready(function () {
    $("#downloadPDF").click(function () {
        downloadPDF();
    });

    function downloadPDF() {
        window.location.href = "/admin/downloadPDF";
    }
}); 