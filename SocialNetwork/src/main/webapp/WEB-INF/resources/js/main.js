function deleteApi(path) {
    if(confirm("Bạn có chắc muốn xóa?") === true) {
        fetch(path, {
            method: "delete"
        }).then(res => {
            if(res.status === 204)
                location.reload();
            else
                alert("Something wrong!!! Try again later.");
        });
    }
}

function activeUser(path) {
    if(confirm("Bạn có chắc muốn thay đổi?") === true) {
        fetch(path, {
            method: "put"
        }).then(res => {
            if(res.status === 204)
                location.reload();
            else
                alert("Something wrong!!! Try again later.");
        });
    }
}
