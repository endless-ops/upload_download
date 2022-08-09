window.onload = function () {

    let openFileBtn = document.getElementById("find_file");

    openFileBtn.onclick = function () {
        let fileBtn = document.getElementById("upload");
        fileBtn.click();
        fileBtn.onchange = function () {
            console.log(this.files);
        }
    }
}