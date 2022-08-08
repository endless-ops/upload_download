const upload = function (url, method, params, progress) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open("post", url, true);
        //提交表单数据
        xhr.send(params);

        //文件传至服务器完成事件
        xhr.onload = function () {
            if (xhr.readyState !== 4) return;
            if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
                resolve(xhr.response);
            } else {
                reject(`err with ` + xhr.status);
            }
        };

        //监听上传进度事件
        xhr.upload.addEventListener('progress', progress);
    });
};

let uploadBtn = document.getElementById("upload_submit_btn");
uploadBtn.onClick = function () {
    upload("upload_with_progress_bar_servlet", "POST", function () {
        // 获取文件
        let toUploadFile = document.getElementById("upload");
        let files = toUploadFile.files;
        let formData = new FormData();
        formData.append("files", files);

        console.log(formData);

        return formData;
    }, function (event) {
        //上传进度监听处理
        console.log(event.total);
        console.log(event.loaded);
    }).then(res => console.log(res)).catch(err => console.log(err));
};