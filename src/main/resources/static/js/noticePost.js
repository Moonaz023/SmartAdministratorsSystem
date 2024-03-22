$(document).ready(function () {
    $("#insertNoticeForm").submit(function (event) {
        event.preventDefault(); 
      
        
        var form = $(this);
        
        
        var formData = new FormData(form[0]);
        
        // Make the AJAX request
        $.ajax({
            type: "POST",
            url: '/admin/insertnotice',
            data: formData,
            processData: false, // Prevent jQuery from automatically processing the data
            contentType: false, // Prevent jQuery from setting the Content-Type header
            success: function (notice_result) {
               
                console.log("Notice inserted successfully");
                $("#insertNoticeForm")[0].reset();
                 getAllNoticeRecords();
               
            },
            error: function (err) {
                
                alert("Error: " + JSON.stringify(err));
            }
        });
    });
});