
$(document).ready(function () {
    fetchNotice();
});

function fetchNotice() {
    $.ajax({
        type: "GET",
        url: "/user/fetchNotice",
        success: function (response) {
            $('#noticeBoard').DataTable().destroy();
            $('#noticeBoard_tableresult').empty();

            for (var i = 0; i < response.length; i++) {
                var notice = response[i];
                var sessionInfo = (notice.session && notice.year && notice.semester) ? 
                    ('#For session: ' + notice.session + ', ' +
                     notice.year + ' year ' +
                     notice.semester + ' semester ') : 
                    "#For All";
                
                var noticeHtml = '<tr>' +
                    '<td>Date: ' + notice.date + '<br>' +'<hr>'+
                    '<p Class="highlight">' + sessionInfo + '<br></p>' +
                    'about: ' + notice.about + '<br>' +
                    'notice: ' + notice.notice + '</td>' +
                    '<td class="file">' +
                    '<a href="/user/open/' + notice.fileName + '" target="_blank"><i class="fa fa-file-text-o" style="font-size:100px;"></i></a>' +
                    '</td>' +
                    '</tr>';
                $('#noticeBoard_tableresult').append(noticeHtml); // Append the notice
            }

            // Initialize DataTables plugin 
           $('#noticeBoard').DataTable({
                "ordering": false
            });
        },
        error: function (err) {
            alert("Error: " + err);
            console.error("Error:", err);
        }
    });
}
