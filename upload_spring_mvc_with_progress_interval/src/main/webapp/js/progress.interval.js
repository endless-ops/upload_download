$(function () {

    $("#upload_submit_btn").on('click', function () {
        let timer = null;

        let files = $("#upload")[0].files;
        console.log("files : " + files[0]);
        let fd = new FormData();
        fd.append("file", files[0]);

        timer = setInterval(function () {
            $.ajax({
                url: 'upload_progress',
                type: "post",
                async: true,
                success: function (response) {
                    console.log("获取进度时: response ：" + response.pBytesRead);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("获取进度时：XMLHttpRequest status : " + XMLHttpRequest.status);
                    console.log("获取进度时：XMLHttpRequest readyState : " + XMLHttpRequest.readyState);
                    console.log("获取进度时：textStatus : " + textStatus);
                    console.log("获取进度时：errorThrown : " + errorThrown);
                }
            })
        }, 200);

        $.ajax({
            url: 'upload_with_progress_bar',
            type: 'POST',
            // enctype: 'multipart/form-data',
            // contentType: 'application/json;charset=utf-8',
            data: fd,
            cache: false,
            contentType: false,
            processData: false,
            success: function (response) {
                console.log("上传时: response ：" + response);
                clearInterval(timer);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("上传时：XMLHttpRequest status : " + XMLHttpRequest.status);
                console.log("上传时：XMLHttpRequest readyState : " + XMLHttpRequest.readyState);
                console.log("上传时：textStatus : " + textStatus);
                console.log("上传时：errorThrown : " + errorThrown);
            }
        })

    });
});