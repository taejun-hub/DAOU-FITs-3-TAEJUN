<%@ page import="org.example.vo.BoardVO" %>
<%@ page import="org.example.vo.MemberVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // 전달된 게시글 데이터 (수정 모드일 경우)
    BoardVO board = (BoardVO) session.getAttribute("board");
    boolean isEditMode = (board != null); // id가 있으면 수정 모드
//    MemberVO member = (MemberVO) request.getAttribute("member");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title><%= isEditMode ? "게시글 수정" : "새 글 작성" %></title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4"><%= isEditMode ? "게시글 수정" : "새 글 작성" %></h2>

    <form action="<%= isEditMode ? "updateBoard" : "boardServlet" %>" method="POST">
        <% if (isEditMode) { %>
        <input type="hidden" name="id" value="<%= board.getBoard_id() %>">
        <% } %>

        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" id="title" name="title" class="form-control" value="<%= isEditMode ? board.getTitle() : "" %>" required>
        </div>

        <div class="mb-3">
            <label for="content" class="form-label">내용</label>
            <textarea id="content" name="content" class="form-control" rows="5" required><%= isEditMode ? board.getContent() : "" %></textarea>
        </div>

        <button type="submit" class="btn btn-primary"><%= isEditMode ? "수정 완료" : "작성 완료" %></button>
        <a href="<%= isEditMode ? "boardDetail?id=" + board.getBoard_id() : "boardList.jsp" %>" class="btn btn-secondary">취소</a>
    </form>
</div>

</body>
</html>