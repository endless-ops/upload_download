$(function () {


    function uploadFile(url, method, params, func) {
        $.ajax({
            url: url,
            type: method,
            datatype: 'json',
            // contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(params),
            success: function (data) {
                console.log(data);
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("jqXHR : " + XMLHttpRequest);
                console.log("textStatus : " + textStatus);
                console.log("errorThrown : " + errorThrown);
            }
        })
    }


    $("#upload_submit_btn").on('click', function () {
        uploadFile('upload_progress', "POST", {performedOn:'00:00:00'}, function () {

        });
    })
})