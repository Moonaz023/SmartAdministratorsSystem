        $(document).ready(function () {
            getAllNoticeRecords();
            

        });
        var notice_data = "";

        function getAllNoticeRecords() {
            $.ajax({
                type: "GET",
                url: "/admin/fetchNotice",
                success: function (Notice_response) {
					 //console.log(response); // Add this line
                    notice_data = Notice_response;
                    $('#NoticeModify').DataTable().destroy();
                    $('#NoticeModifyTableresult').empty();

                    for (i = 0; i < Notice_response.length; i++) {
                        var editUrl = "/edit/" + Notice_response[i].id; // Replace with your actual edit URL
                        var deleteUrl = "/delete/" + Notice_response[i].id; // Replace with your actual delete URL
             //           console.log("Row data:", response[i]); // Add this line

                        $("#NoticeModifyTableresult").append(
                            '<tr class="tr">' +
                            
                            '<td>' + Notice_response[i].date + '</td>' +
                            '<td>' + Notice_response[i].session + '</td>' +
                            '<td>' + Notice_response[i].year + '</td>' +
                            '<td>' + Notice_response[i].semester + '</td>' +
                            '<td>' + Notice_response[i].about + '</td>' +
                            '<td>' + Notice_response[i].notice + '</td>' +
                            '<td>' + Notice_response[i].fileName + '</td>' +
                            
                            '<td><a href="#" onclick="editNotice(' + notice_data[i].id + ')">Edit</a></td>' +
                            '<td><a href="#" onclick="deleteNoticeRecord(' + Notice_response[i].id + ')">Delete</a></td>' +
                            
                            '</tr>'
                        );
                    }

                   

                    // Initialize DataTables plugin
                    $('#NoticeModify').DataTable({
                "ordering": false
            });
                },
                error: function (err) {
                    alert("Error: " + err);
                    console.error("Error:", err);
                }
            });
        }
        
//=========================================DELETE====================================================================

    function deleteNoticeRecord(id) {
        $.ajax({
            type: "DELETE",
            url: "/admin/deleteNotice?id=" + id,
            success: function (NoticeModifyResult) {
                // Refresh the table after successful deletion
                getAllNoticeRecords();
            },
            error: function (err) {
                alert("Error deleting record: " + JSON.stringify(err));
            }
        });
    }
    
    
          
  //====================================Edit==========================================================================
function editNotice(id) {
	 //getAllNoticeRecords();
    var editNoticeRecord = notice_data.find(function (Notice_item) {
        return Notice_item.id === id;
    });

    var editFormHtml = `
        <h2 class="EditNoticeHeader">Edit Notice</h2>
        <form id="editNoticeForm" enctype="multipart/form-data">
            <label>Notice File: ${editNoticeRecord.fileName}</label><br>
            <input type="hidden" id="id" name="id" value="${editNoticeRecord.id}">
            <input type="hidden" id="fileName" name="fileName" value="${editNoticeRecord.fileName}">
            <label>Change Notice File: </label><br>
            <input type="file" id="noticDoc" name="noticDoc" accept=".pdf,.csv,application/vnd.ms-excel,image/jpeg,image/png"><br>
            
            <label for="date">Date:</label><br>
            <input type="text" id="date" name="date" value="${editNoticeRecord.date}" required><br>
            
            <label for="session">Session:</label><br>
            <input type="text" id="session" name="session" value="${editNoticeRecord.session}" required><br>
            
            <label for="year">Year:</label><br>
            <input type="text" id="year" name="year" value="${editNoticeRecord.year}" required><br>
            
            <label for="semester">Semester:</label><br>
            <input type="text" id="semester" name="semester" value="${editNoticeRecord.semester}" required><br>
            
            <label for="about">About:</label><br>
            <input type="text" id="about" name="about" value="${editNoticeRecord.about}" required><br>
            
            <label for="notice">Notice:</label><br>
            <textarea id="notice" name="notice" required>${editNoticeRecord.notice}</textarea><br>
    
            <button type="button" id="updateNotice" class="btn btn-success">Save</button>
            <button type="button" id="canceleditnotice" class="btn btn-primary">Cancel</button>
        </form>
    `;
	
    // Show the edit form
    $("#editNoticeContainer").html(editFormHtml).show();

    // Hide the container
    $(".NoticeContainer").addClass("hidden");

    // Attach click event for the update button
    $("#updateNotice").click(function (event) {
        // Get the form associated with the clicked button
        var NoticeEditForm = $("#editNoticeForm");
        
        var NoticeEditFormData = new FormData(NoticeEditForm[0]);
        
        // Prevent the default form submission
        event.preventDefault();
        
        $.ajax({
            type: "POST",
            url: '/admin/updatenotice',
            data: NoticeEditFormData,
            processData: false, // Prevent jQuery from automatically processing the data
            contentType: false, // Prevent jQuery from setting the Content-Type header
            success: function (result) {
                console.log("Notice updated successfully");
              getAllNoticeRecords();  
            $("#editNoticeContainer").empty().hide();
                $(".NoticeContainer").removeClass("hidden");
               
            },
            error: function (err) {
                alert("Error: " + JSON.stringify(err));
            }
        });

    });
    $("#canceleditnotice").click(function (event) {
        $("#editNoticeContainer").empty().hide();
        $(".NoticeContainer").removeClass("hidden");
        getAllNoticeRecords();
    });
}

