function getUserDetails(userId){
    $(document).ready(function() {

        let href = '/api/v1/users/details?id=' + userId;

        $.ajax({
            url: href,
            type: 'get',
            success: function (response){
                console.log("SUCCESS: " + response)
            },
            error: function (error){
                console.log("ERROR: " + error);
            }
        }).then(function (data){
            console.log(data);
            $('#id').val(data.id);
            $('#name').val(data.name);
            $('#lastName').val(data.lastName);
            $('#nationality').val(data.nationality);
            $('#addressId').val(data.address.id);
            $('#street').val(data.address.street);
            $('#streetNumber').val(data.address.streetNumber);
            $('#apartmentNumber').val(data.address.apartmentNumber);
            $('#postalCode').val(data.address.postalCode);
            $('#city').val(data.address.city);
            $('#drivingLicenseNumber').val(data.drivingLicenseNumber);
            $('#identityCardNumber').val(data.identityCardNumber);
            $('#peselNumber').val(data.peselNumber);
            $('#phoneNumber').val(data.phoneNumber);
            $('#birthDate').val(data.birthDate);

        })

    });
}