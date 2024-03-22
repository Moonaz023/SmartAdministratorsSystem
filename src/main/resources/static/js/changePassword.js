$(document).ready(function () {
    $("#changePasswordForm").submit(function (event) {
        event.preventDefault();

        
        var formData = {
            id: userId,
            currentPassword: $("#currentPassword").val(),
            newPassword: $("#newPassword").val(),
            confirmPassword: $("#confirmPassword").val()
        };

        
        if (formData.newPassword !== formData.confirmPassword) {
            alert("New password and confirm password do not match");
            return; 
        }

        console.log(formData);

        $.ajax({
            type: "POST",
            url: "/user/changePassword", 
            data: formData,
            success: function (response) {
                if (response === 'success') {

                    console.log("Password changed successfully");
                    alert("Password changed successfully");
                     $("#changePasswordForm")[0].reset();
                } else {
                    console.log("Password change failed");
                    alert("Incorrect current password. Please try again.");
                }
            },
            error: function (xhr, status, error) {
               
                console.error("Error changing password:", error);
                alert("An error occurred while changing the password. Please try again later.");
            }
        });
    });
});

function validatePasswords() {
    var newPassword = $("#newPassword").val();
    var confirmPassword = $("#confirmPassword").val();

    if (newPassword !== confirmPassword) {
        alert("New password and confirm password do not match");
    } else {
        
        $("#changePasswordForm").submit();
    }
}


