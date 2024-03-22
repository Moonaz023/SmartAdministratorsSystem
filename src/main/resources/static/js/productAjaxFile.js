              function populateTypes() {
        $.ajax({
            url: "/admin/types",
            type: "GET",
            dataType: "json",
            success: function (p_response) {
                var dropdown = $("#type");
                dropdown.empty();
                dropdown.append('<option value="">Select Type</option>');
                $.each(p_response, function (index, type) {
                    dropdown.append('<option value="' + type.id + '">' + type.name + '</option>');
                });
            },
            error: function (xhr, status, error) {
                console.log(error);
            }
        });
    }

    // Call the function when the document is ready
    $(document).ready(function () {
        populateTypes();
    });
    
    
    
    
 //   ============================================================================
        $(document).ready(function () {
           

            $("#insertproduct").click(function () {
                $.ajax({
                    type: "POST",
                    url: "/admin/insertproduct",
                    data: {
                        productId: $("input[name='productId']").val(),
                         date: $("input[name='date']").val(),
                          price: $("input[name='price']").val(),
                           allocate: $("input[name='allocate']").val(),
                            purpose: $("select[name='purpose']").val(),
                             category: $("input[name='category']").val(),
                              //type: $("input[name='typeId']").val(),
                              type: $("select[name='typeId']").val(),
                               functionality: $("input[name='functionality']").val(),
                                ruin_date: $("input[name='ruin_date']").val(),
                    },
                    success: function (result) {
                       // Reset the form
						$("#form_manual_product")[0].reset();
						 getAllProduct();  
                    },
                    error: function (err) {
                        alert("Error: " + JSON.stringify(err));
                    }
                });
            });
        });
        
 //===========================================INSERT CSV================================================
$(document).ready(function() {

  $('#uploadProductCSVForm').submit(function(event) {
    event.preventDefault();
    
    var formData = new FormData();
    formData.append('file', $('#productcsvFile')[0].files[0]);
    
    $.ajax({
      url: '/admin/insert_product_csv',
      type: 'POST',
      data: formData,
      processData: false,
      contentType: false,
      success: function(save) {
        console.log('Data inserted successfully');
        $("#uploadProductCSVForm")[0].reset();
         getAllProduct();  
      },
      error: function(xhr, status, error) {
        console.log('Error uploading CSV file:', error);
      }
    });
  });
});      
	