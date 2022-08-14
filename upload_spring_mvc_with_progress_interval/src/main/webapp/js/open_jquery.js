$(document).ready(function () {
    $("#find_file").on('click',function () {
        let fileBtn = $("#upload");
        fileBtn.click();
        fileBtn.change(function () {
            console.log(this.files);
        });
    });
});