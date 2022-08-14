$(function () {
    let timer = null;

    function uploadFile(url, method, params, success, error) {
        console.log("=========================================================")
        $.ajax({
            url: url,
            type: method,
            // enctype: 'multipart/form-data',
            // contentType: 'application/json;charset=utf-8',
            data: params,
            cache: false,
            contentType: false,
            processData: false,
            success: success,
            error: error
        })
    }


    function getProgress(progress, timeout) {
        timer = setInterval(progress, timeout);
    }

    function getProgressBar(timeout, success, error) {
        console.log("//////////////////////////////////////////////")
        getProgress(function () {
            $.ajax({
                url: 'upload_progress',
                type: "post",
                async: true,
                success: success,
                error: error
            })
        }, timeout);
    }


    $("#upload_submit_btn").on('click', function () {

        let files = $("#upload")[0].files;
        console.log("files : " + files[0]);
        let fd = new FormData();
        fd.append("file", files[0]);

        uploadFile('upload_with_progress_bar', "POST", fd, function (response) {
            console.log("上传时: response ：" + response);
            clearInterval(timer);
        }, function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("上传时：XMLHttpRequest status : " + XMLHttpRequest.status);
            console.log("上传时：XMLHttpRequest readyState : " + XMLHttpRequest.readyState);
            console.log("上传时：textStatus : " + textStatus);
            console.log("上传时：errorThrown : " + errorThrown);
        });

        getProgressBar(200, function (response) {
            console.log("获取进度时: response ：" + response.pBytesRead);
        }, function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("获取进度时：XMLHttpRequest status : " + XMLHttpRequest.status);
            console.log("获取进度时：XMLHttpRequest readyState : " + XMLHttpRequest.readyState);
            console.log("获取进度时：textStatus : " + textStatus);
            console.log("获取进度时：errorThrown : " + errorThrown);
        })
    })
});