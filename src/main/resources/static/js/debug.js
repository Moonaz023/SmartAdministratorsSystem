//===========================================Insert=========================================================
        $(document).ready(function () {
            getAllRecords();


        });

//==========================================Show Data==========================================================================

        var data = "";

        function getAllRecords() {
            $.ajax({
                type: "GET",
                url: "/getAllStudents",
                success: function (response) {
					 console.log(response); // Add this line
                    data = response;
                    $('#example').DataTable().destroy();
                    $('#tableresult').empty();

                    for (i = 0; i < response.length; i++) {
                        
                        console.log("Row data:", response[i]); // Add this line

                        $("#tableresult").append(
                            '<tr class="tr">' +
                            
                            '<td><a href="#" onclick="Record(' + data[i].id + ')">' + response[i].roll + '</a></td>' +
                            '<td>' + response[i].name + '</td>' +
                            '<td>' + response[i].sesson + '</td>' +
                            '<td>' + response[i].year + '</td>' +
                            '<td>' + response[i].cgpa + '</td>' +
                            '<td><a href="#" onclick="editRecord(' + data[i].id + ')">Edit</a></td>' +
                            '<td><a href="#" onclick="deleteRecord(' + response[i].id + ')">Delete</a></td>' +
                            '</tr>'
                        );
                    }

                   

                    // Initialize DataTables plugin
                    $('#example').DataTable();
                },
                error: function (err) {
					 console.error("Error detected:", err);
                    //alert("Error detected");
                    
                }
            });
        }
//====================================Edit==========================================================================
        function editRecord(id) {
            var record = data.find(function (item) {
                return item.id === id;
            });

            var editFormHtml = `
            <form id="editForm">
                <h2>Edit Student Record</h2>
                <label for="editRoll">Roll</label>
                <input type="text" id="editRoll" value="${record.roll}"><br>
                <label for="editName">Name</label>
                <input type="text" id="editName" value="${record.name}"><br>
                <label for="editSesson">Session</label>
                <input type="text" id="editSesson" value="${record.sesson}"><br>
                <label for="editYear">Passing Year</label>
                <input type="text" id="editYear" value="${record.year}"><br>
                <label for="editCGPA">CGPA</label>
                <input type="text" id="editCGPA" value="${record.cgpa}"><br>
                <button type="button" onclick="saveRecord(${record.id})">Save</button>
                
            </form>
            `;

           
            // Show the edit form
   		    $("#editFormContainer").html(editFormHtml).show();
    		// Hide the container
    		$(".container").addClass("hidden");
        }

        function saveRecord(id) {
            var updatedRecord = {
                id: id,
                roll: $("#editRoll").val(),
                name: $("#editName").val(),
                sesson: $("#editSesson").val(),
                year: $("#editYear").val(),
                cgpa: $("#editCGPA").val()
            };

            $.ajax({
                type: "POST",
                url: "update",
                data: updatedRecord,
                success: function (result) {
                    getAllRecords();
                    //$("#editFormContainer").empty().hide();
                    // Empty the edit form and hide it
            		$("#editFormContainer").empty().hide();
            		// Show the container again
            		$(".container").removeClass("hidden");
                },
                error: function (err) {
                    alert("Error: " + JSON.stringify(err));
                }
            });
        }
//=========================================DELETE====================================================================

    function deleteRecord(id) {
        $.ajax({
            type: "DELETE",
            url: "/delete?id=" + id,
            success: function (result) {
                // Refresh the table after successful deletion
                getAllRecords();
            },
            error: function (err) {
                alert("Error deleting record: " + JSON.stringify(err));
            }
        });
    }
