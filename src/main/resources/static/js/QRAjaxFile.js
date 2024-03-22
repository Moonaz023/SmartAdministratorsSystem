
 var scannedData = ""; // variable to store the scanned data

    // Listen for keyboard events to capture the scanned data
    document.addEventListener("keydown", function(event) {
      // Check if the event key is a printable character
      if (event.key.length === 1) {
        // Append the event key to the scannedData variable
        scannedData += event.key;
      } else if (event.key === 'Enter') {
        // Update the result container with the complete scanned data
       // var resultContainer = document.getElementById("result");
       // resultContainer.textContent = "Scanned data: " + scannedData;

        // Reset the scannedData variable for the next scan
       // scannedData = "";
       
       onScanSuccess(scannedData);
       scannedData = "";
      }
    });

  var scanPurpose = "insert"; // Set the initial purpose to "insert"

  function onScanSuccess(qrCodeMessage) {
    var qrCodeParts = qrCodeMessage.split(','); // Split the QR code result by comma

    if (scanPurpose === "insert") {
      $.ajax({
        type: "POST",
        url: "/admin/insertproduct",
        data: {
          productId: qrCodeParts[0].trim(),
          date: qrCodeParts[1].trim(),
          price: qrCodeParts[2].trim(),
          allocate: qrCodeParts[3].trim(),
          purpose: qrCodeParts[4].trim(),
          category: qrCodeParts[5].trim(),
         //type: qrCodeParts[6].trim()
          type: getTypeByName(qrCodeParts[6].trim())
        },
        success: function(result) {
        //  fetchDataFromDatabase(qrCodeParts[0].trim()); // Fetch data based on the scanned QR code
        },
        error: function(err) {
          alert("Error: " + JSON.stringify(err));
        }
      });
    } else if (scanPurpose === "fetch") {
      fetchDataFromDatabase(qrCodeParts[0].trim()); // Fetch data based on the scanned QR code
    }

    //updateResultDisplay(qrCodeMessage); // Update the display of results=============================================>>>>>>>>>>>>>>>>>>>>>
  }

  function onScanError(errorMessage) {
    console.error(errorMessage); // Print error message in the console
  }

//  function updateResultDisplay(qrCodeMessage) {
//    var resultContainer = document.getElementById('result');
//    resultContainer.innerHTML = '<span class="result">' + qrCodeMessage + '</span>'; //===============================>>>>>>>>>>>>>>>>>>>>>>
//  }

  function fetchDataFromDatabase(productId) {
    // Perform AJAX request to fetch data based on the productId
    $.ajax({
      type: "GET",
      url: "/admin/fetchproduct/" + productId,
      success: function(result) {
        retrievedData = result;  // Store the retrieved data
        qr_editForm.style.display = 'block';//============================================to show edit form
        displayFormData(); // Display the retrieved data in the form
      },
      error: function(err) {
        alert("Error: " + JSON.stringify(err));
      }
    });
  }
function displayFormData() {
	 //qr_editForm.style.display = 'block';
    // Retrieve the form input elements
    var productIdInput = $("#productId");
    var dateInput = $("#date");
    var priceInput = $("#price");
    var allocateInput = $("#allocate");
    var purposeInput = $("#purpose");
    var categoryInput = $("#category");
    var typeInput = $("#edittype"); // Modified ID for the select element
    var functionalityInput = $("#functionality");

    // Set the values of the form inputs using the retrieved data
    productIdInput.val(retrievedData.productId);
    dateInput.val(retrievedData.date);
    priceInput.val(retrievedData.price);
    allocateInput.val(retrievedData.allocate);
    purposeInput.val(retrievedData.purpose);
    categoryInput.val(retrievedData.category);
    typeInput.val(retrievedData.type.id); // Set the selected type ID
    functionalityInput.val(retrievedData.functionality);
    // Display the edit form
  qr_editForm.style.display = 'block';
}
 
function qr_resetForm() {
  //var qr_dataForm = document.getElementById('qr_dataForm');//  29/11/23
  //qr_dataForm.reset(); // Reset the form inputs  //  29/11/23
  document.getElementById('qr_editForm').style.display = 'none'; // Hide the form
}
  

  function setScanPurpose(purpose) {
    scanPurpose = purpose; // Set the scan purpose (insert or fetch)
  }

  var html5QrcodeScanner = new Html5QrcodeScanner("reader", { fps: 10, qrbox: 250 });
  html5QrcodeScanner.render(onScanSuccess, onScanError);
  
  
  function updateProduct() {
  var productId = document.getElementById('productId').value;
  var date = document.getElementById('date').value;
  var price = document.getElementById('price').value;
  var allocate = document.getElementById('allocate').value;
  var purpose = document.getElementById('purpose').value;
  var category = document.getElementById('category').value;
  var type = document.getElementById('edittype').value;
   var functionality = document.getElementById('functionality').value;

  // Create an object with the updated data
  var updatedData = {
	productId: productId,
    date: date,
    price: price,
    allocate: allocate,
    purpose: purpose,
    category: category,
    type: type,
    functionality: functionality
  };

  // Perform AJAX request to update the data
  $.ajax({
    type: "POST", // Change the request type to POST
    url: "/admin/updateproduct" , // Update the URL to use POST instead of PUT
    data: updatedData, // Pass the updatedData object directly
    success: function(result) {
      alert("Data updated successfully!");
      fetchDataFromDatabase(productId); // Fetch updated data
    },
    error: function(err) {
      alert("Error: " + JSON.stringify(err));
    }
  });
}
  
function getTypeByName(typeName) {
  var typeId = ""; // Initialize the typeId variable

  $.ajax({
    url: "/admin/types",
    type: "GET",
    dataType: "json",
    async: false, // Set async to false to ensure the typeId is retrieved before sending the AJAX request
    success: function(response) {
      $.each(response, function(index, type) {
        if (type.name === typeName) {
          typeId = type.id; // Assign the typeId if the type name matches
          return false; // Exit the loop
        }
      });
    },
    error: function(xhr, status, error) {
      console.log(error);
    }
  });

  return typeId;
}  

 function fetchTypes() {
    // Perform AJAX request to fetch the types
    $.ajax({
      type: "GET",
      url: "/admin/types",
      success: function(response) {
        var dropdown = $("#edittype");
        dropdown.empty();
        dropdown.append('<option value="">Select Type</option>');
        $.each(response, function(index, type) {
          dropdown.append('<option value="' + type.id + '">' + type.name + '</option>');
        });
      },
      error: function(err) {
        alert("Error: " + JSON.stringify(err));
      }
    });
  }

  // Call the function to fetch the types when the document is ready
  $(document).ready(function() {
    fetchTypes();
  });
