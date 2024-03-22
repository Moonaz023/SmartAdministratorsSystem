var currentPage = 1;
var itemsPerPage = 5; // Number of items per page
var totalPages = 0;
var searchQuery = '';

$(document).ready(function () {
    getAllProduct();   
});

function getAllProduct() {
    $.ajax({
        type: "GET",
        url: "/admin/fetchAllProduct",
        success: function(response) {
            console.log(response);
            $('#productListBody').empty();

            // Filter products based on search query
            var filteredProducts = response.filter(function(product) {
                return JSON.stringify(product).toLowerCase().includes(searchQuery.toLowerCase());
            });

            var startIndex = (currentPage - 1) * itemsPerPage;
            var endIndex = startIndex + itemsPerPage;
            totalPages = Math.ceil(filteredProducts.length / itemsPerPage);

            for (var i = startIndex; i < endIndex && i < filteredProducts.length; i++) {
                var editUrl = "/edit/" + filteredProducts[i].productId;
                var deleteUrl = "/delete/" + filteredProducts[i].productId;
                var valuesString = stringifyObject(filteredProducts[i]);

                // Generate QR code
                (function(index) {
                    var qrValuesString = stringifyObject(filteredProducts[index]);
                    $.post('/admin/generateQR', { text: qrValuesString }, function(data) {
                        var img = $('<img>', { src: 'data:image/png;base64,' + data });
                        // Append QR code image to the corresponding column
                        $('#qrCodeContainer_' + index).html(img);
                    });
                })(i);

                $("#productListBody").append(
                    '<tr class="tr">' +
                    '<td>Product Id:' + filteredProducts[i].productId + 
                    '<br>Type:' + filteredProducts[i].type.name +
                    '<br>Price:' + filteredProducts[i].price +
                    '<br>Allocation:' + filteredProducts[i].allocate +
                    '<br>Purpose:' + filteredProducts[i].purpose + 
                    '<br>Category:' + filteredProducts[i].category + 
                    '<br>Functionality:' + filteredProducts[i].functionality +
                    '<br>Ruin Date:' + filteredProducts[i].ruin_date +
                    '</td>' +
                    '<td class="qr-code-container">' +
				    '<h3 id="qrCodeContainer_' + i + '" class="qr"></h3>' +
				    '<i class="fa fa-download" onclick="downloadQR(' + i + ')"></i>' +
				   
					'</td>' +
                    '</tr>'
                );
            }

            renderPageNumbers();
        },
        error: function(err) {
            alert("Error: " + err);
            console.error("Error:", err);
        }
    });
}

function renderPageNumbers() {
    $('#pageNumbers').empty();
    for (var i = 1; i <= totalPages; i++) {
        var pageNumberButton = $('<button class="pageNumber">' + i + '</button>');
        if (i === currentPage) {
            pageNumberButton.addClass('active');
        }
        $('#pageNumbers').append(pageNumberButton);
    }
}

$('#prevPage').click(function() {
    if (currentPage > 1) {
        currentPage--;
        getAllProduct();
    }
});

$('#nextPage').click(function() {
    if (currentPage < totalPages) {
        currentPage++;
        getAllProduct();
    }
});

$(document).on('click', '.pageNumber', function() {
    $('.pageNumber').removeClass('active');
    $(this).addClass('active');
    currentPage = parseInt($(this).text());
    getAllProduct();
});

// Search functionality
$('#searchInput').on('input', function() {
    searchQuery = $(this).val();
    currentPage = 1;
    getAllProduct();
});

// Function to stringify nested objects
function stringifyObject(obj) {
    var result = '';
    for (var key in obj) {
        if (typeof obj[key] === 'object' && obj[key] !== null) {
            result += key + ': ' + obj[key].name + ', ';
        } else {
            result += key + ':' + obj[key] + ', ';
        }
    }
    return result.slice(0, -2); // Remove the trailing comma and space
}
function downloadQR(index) {
    var imgData = $('#qrCodeContainer_' + index + ' img').attr('src');
    var link = document.createElement('a');
    link.href = imgData;
    link.download = 'qrcode.png';
    link.click();
}
 $('#downloadProductList').click(function () {
            var text = $('#inputText').val();
            window.location = '/admin/downloadProductList';
        });