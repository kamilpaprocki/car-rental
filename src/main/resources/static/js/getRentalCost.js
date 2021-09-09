$(document).ready(function () {

    getContent();
    $('#selection').change(getContent);

    function getContent() {

        let url = /currency/

        if ($('#selection').val() === "PLN") {
            $.ajax({
                type: 'POST',
                url: '/getrentalcost',
                async: false,
                data: {
                    cost: inputValue
                },
                success: function (response) {
                    url = url + 'currency-pln?cost=' + JSON.stringify(response);
                    console.log("SUCCES: ", response);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                }
            });
            $('#replace_div').load(url);
        }
        else if ($('#selection').val() === "USD") {
            $.ajax({
                type: 'POST',
                url: '/getrentalcost',
                async: false,
                data: {
                    cost: inputValue
                },
                success: function (response) {
                    url = url +'currency-usd?cost=' + JSON.stringify(response);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                }
            });
            $('#replace_div').load(url);
        }
        else {
            $.ajax({
                type: 'POST',
                url: '/getrentalcost',
                async: false,
                data: {
                    cost: inputValue
                },
                success: function (response) {
                    url = url + 'currency-eur?cost=' + JSON.stringify(response);
                },
                error: function (e) {
                    console.log("ERRPR: ", e);
                }
            });
            $('#replace_div').load(url);
        }
    }
})