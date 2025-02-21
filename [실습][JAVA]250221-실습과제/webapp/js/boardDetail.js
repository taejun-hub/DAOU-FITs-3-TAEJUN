function addComment() {
    // let commentContent = $('#commentContent').value();
    let board_id = $('#board_id').val();
    let commentContent = $('#commentContent').val()

    $.ajax({
        async: true,                                   // 비동기화 여부 (default : true)
        url: 'addComment',                                 // 요청할 서버 URL
        type: 'post',                                   // 타입 (get, post, put 등등)
        data: {
            board_id: board_id,
            commentContent: commentContent
        },
        dataType: 'json',                               // 서버가 보내주는 결과 데이터가 JSON(default: json)
        success: function (response) {             // 결과 성공 콜백함수
            if (response.status == 'success') {
                $('#commentContent').val("")
                location.reload();
            } else {
                alert(response.message)
            }


        },
        error: function (request, status, error) { // 결과 에러 콜백함수
            console.log(error);
            alert("호출 실패");
        }
    })
}

function deleteComment(button) {
    let comment_id = button.getAttribute("data-comment_id");
    $.ajax({
        async: true,                                   // 비동기화 여부 (default : true)
        url: 'deleteComment',                                 // 요청할 서버 URL
        type: 'post',                                   // 타입 (get, post, put 등등)
        data: {
            comment_id: comment_id,
        },
        dataType: 'json',                               // 서버가 보내주는 결과 데이터가 JSON(default: json)
        success: function (response) {             // 결과 성공 콜백함수
            if (response.status == 'success') {
                location.reload();
            } else {
                alert(response.message)
            }
        },
        error: function (request, status, error) { // 결과 에러 콜백함수
            console.log(error);
            alert("호출 실패");
        }
    })
}

function likeBtnHandler(button) {
    const board_id = button.getAttribute("data-board_id");

    // 현재 좋아요 상태 확인
    const isLiked = button.classList.contains("btn-danger");
    const url = isLiked ? "unlikeBoard" : "likeBoard";

    $.ajax({
        async: true,                                   // 비동기화 여부 (default : true)
        url: url,                                 // 요청할 서버 URL
        type: 'post',                                   // 타입 (get, post, put 등등)
        data: {
            board_id: board_id,
        },
        dataType: 'json',                               // 서버가 보내주는 결과 데이터가 JSON(default: json)
        success: function (response) {             // 결과 성공 콜백함수
            if (response.status === 'success') {
                button.classList.toggle("btn-danger");
                button.classList.toggle("btn-outline-danger");
                button.innerHTML = isLiked
                    ? '<i class="bi bi-heart"></i> 좋아요'
                    : '<i class="bi bi-heart-fill"></i> 좋아요 취소';
            } else {
                alert(response.message)
            }


        },
        error: function (request, status, error) { // 결과 에러 콜백함수
            console.log(error);
            alert("호출 실패");
        }
    })

}