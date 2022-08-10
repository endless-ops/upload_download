$(function () {
    function upload(url, method, params, progress) {
        $.ajax({
            url: url,
            type: method,
            data: params,
            cache: false,
            contentType: false,
            processData: false
        }).done(function (response) {
            console.log(response);
        })
    }

    $("#upload_submit_btn").on('click', function () {
        console.log(new Date().getTime());
        // 获取文件
        let files = $("#upload").files;
        let data = new FormData();
        data.append("file", files[0]);

        upload("upload_with_progress_bar", "POST", data, function (e) {
            //上传进度监听处理
            // console.log(e.total);
            // console.log(e.loaded);
            let width = $("#progress_bottom").width();
            console.log(width);
            let percentage = Math.floor((e.loaded / e.total) * 10000) / 100;
            $("#progress_top").css('width',(width * (percentage / 100)) + 'px');
            $("#progress_num_show").text(percentage + '%');
        })
    })
})
