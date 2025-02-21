function boardWriteBtnHandler(isloggedIn) {
    if (isloggedIn) {
        window.location.href = 'boardForm.jsp'
    } else {
        alert("로그인이 필요합니다.")
        window.location.href = 'login.html'

    }

}