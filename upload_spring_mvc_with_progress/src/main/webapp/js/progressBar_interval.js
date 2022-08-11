$(function () {

    let timer = null;

    // 定义一个 定时函数
    function getProgressInterval(url, method, func) {
        timer = setInterval(function () {
            console.log("--------------------------------------------------")
            $.ajax({
                url: url,
                type: method,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                success: func
            });
        }, 200);
    }

    function uploadFile(url, method, params, func) {
        console.log("+++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        $.ajax({
            url: url,
            type: method,
            data: params,
            cache: false,
            contentType: false,
            processData: false
        }).done(func)
    }

    $("#upload_submit_btn").on("click", function () {
        console.log("开始上传");
        // 获取文件
        // $("form").serialize();
        let files = $("#upload")[0].files;
        console.log(files);
        let form = new FormData();
        form.append("file", files[0]);

        getProgressInterval("upload_progress", "POST", function (response) {
            console.log("======================================================")
            console.log("response  :   " + response.totalSize);
        });

        uploadFile("upload_with_progress_bar", "POST", form, function (response) {
            //上传进度监听处理
            // console.log(e.total);
            // console.log(e.loaded);
            // let width = $("#progress_bottom").width();
            // console.log(width);
            // let percentage = Math.floor((e.loaded / e.total) * 10000) / 100;
            // $("#progress_top").css('width',(width * (percentage / 100)) + 'px');
            // $("#progress_num_show").text(percentage + '%');

            console.log(response);
            clearInterval(timer);
        })
    })
})
