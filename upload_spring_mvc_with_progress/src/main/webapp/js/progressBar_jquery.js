$(function () {
    function upload(url, method, params, progress) {
        $.ajax({
            url: url,
            type: method,
            data: params,
            cache: false,
            contentType: false,
            processData: false,
            xhr: function () {
                let myXhr = $.ajaxSettings.xhr()
                if (myXhr.upload) {
                    myXhr.upload.addEventListener('progress', progress, false)
                }
                return myXhr
            }
        }).done(function (response) {
            console.log(response);
        })
    }

    $("#upload_submit_btn").on('click', function () {
        console.log("开始上传");
        // 获取文件
        let toUploadFile = document.getElementById("upload");
        let files = toUploadFile.files;
        let formData = new FormData();
        formData.append("file", files[0]);

        upload("upload_with_progress_bar_servlet", "POST", formData, function (e) {
            //上传进度监听处理
            // console.log(e.total);
            // console.log(e.loaded);
            let width = $("#progress_bottom").width();
            console.log(width);
            let percentage = Math.floor((e.loaded / e.total) * 100);
            $("#progress_top").css('width',(width * (percentage / 100)) + 'px');
            $("#progress_num_show").text(percentage + '%');
        })
    })
})
