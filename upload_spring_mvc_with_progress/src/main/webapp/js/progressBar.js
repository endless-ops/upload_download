const upload = function (url, method, params, progress) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        //文件传至服务器完成事件
        xhr.onload = function () {
            if (xhr.readyState !== 4) return;
            if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
                resolve(xhr.response);
            } else {
                reject(`err with ` + xhr.status + " " + xhr.response);
            }
        };
        //监听上传进度事件
        xhr.upload.addEventListener('progress', progress);
        xhr.open(method, url, true);
        //提交表单数据
        xhr.send(params);
    });
};

let uploadBtn = document.getElementById("upload_submit_btn");
uploadBtn.onclick = function () {
    console.log("开始上传");
    // 获取文件
    let toUploadFile = document.getElementById("upload");
    let files = toUploadFile.files;
    let formData = new FormData();
    formData.append("file", files[0]);

    upload("upload_with_progress_bar", "POST", formData, function (e) {
        //上传进度监听处理
        // console.log(e.total);
        // console.log(e.loaded);
        let progressBottom = document.getElementById("progress_bottom");
        let progressTop = document.getElementById("progress_top");
        let progressNumShow = document.getElementById("progress_num_show");
        let width = progressBottom.offsetWidth;
        let percentage = Math.floor((e.loaded / e.total) * 10000) / 100;
        progressTop.style.width = (width * (percentage / 100)) + 'px';
        progressNumShow.innerText = percentage + '%';

    }).then(res => console.log(res)).catch(err => console.log(err));
};