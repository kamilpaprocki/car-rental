$(document).ready(function() {

    $('.table .btn').on('click',function(event){

        event.preventDefault();

        const href = $(this).attr('href');

        $.get(href, function (u, status){
            $('#email').val(u.email);
            $('#username').val(u.username);
            $('#id').val(u.id);
        });

        $('#setRole').modal('show');

    });
});