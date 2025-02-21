<%@ page import="org.example.vo.BoardVO" %>
<%@ page import="org.example.vo.CommentVO" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.vo.MemberVO" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // 서블릿에서 전달된 게시글 정보 및 댓글 리스트 받기
    BoardVO board = (BoardVO) request.getAttribute("board");
    // 게시글 객체를 세션에 저장
    session.setAttribute("board", board);
    MemberVO member = (MemberVO) session.getAttribute("member");
    boolean isAuthor = Objects.equals(board.getMember_id(), member.getId());
    boolean hasLiked = (boolean) request.getAttribute("hasLiked");
    System.out.println("hasLis");

    List<CommentVO> commentList = (List<CommentVO>) request.getAttribute("commentList");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 상세</title>

    <!-- jQuery CDN -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/boardDetail.js"></script>
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="mb-4 text-primary">게시글 상세</h2>

    <!-- 🔹 게시글 정보 (카드 형식) -->
    <div class="card shadow-sm mb-4">
        <div class="card-body d-flex justify-content-between align-items-center">
            <div>
               <h4 class="card-title text-dark"><%= board.getTitle() %></h4>
                <p class="card-text text-secondary"><%= board.getContent() %></p>
                <p class="text-muted">작성자: <strong><%= board.getAuthor() %></strong> | 작성일: <%= board.getCreated_at() %></p>
            </div>
            <% if (!isAuthor) { %>
            <!-- 좋아요 버튼 -->
                <div>
                    <button class="btn <%= hasLiked ? "btn-danger" : "btn-outline-danger" %> likeBtn"
                            data-board_id="<%=board.getBoard_id()%>" onclick="likeBtnHandler(this)">
                        <i class="bi <%= hasLiked ? "bi-heart-fill" : "bi-heart" %>"></i>
                        <%= hasLiked ? "좋아요 취소" : "좋아요" %>
                    </button>
                </div>
            <% } %>
            <% if (isAuthor) { %>
            <!-- 수정 및 삭제 버튼 -->
                <div class="mt-3">
                    <form action="deleteBoard?id=<%=board.getBoard_id()%>" method="post">
                        <a href="boardForm.jsp" class="btn btn-warning">수정</a>
                        <button type="submit" class="btn btn-danger">삭제</button>
                    </form>
                </div>
            <% } %>
        </div>
    </div>

    <!-- 🔹 댓글 목록 -->
    <h3 class="text-primary">댓글</h3>
    <div id="commentSection" class="mb-4">
        <% if (commentList != null && !commentList.isEmpty()) { %>
        <ul class="list-group">
            <% for (CommentVO comment : commentList) { %>
            <li class="list-group-item">
                <div>
                    <strong><%= comment.getAuthor() %></strong>: <%= comment.getContent() %>
                    <span class="text-muted float-end"><%= comment.getCreated_at() %></span>
                </div>
                <!-- 🔹 로그인한 사용자와 댓글 작성자가 동일하면 삭제 버튼 표시 -->
                <% if (comment.getUser_id().equals(member.getId())) { %>
                <button id="deleteCommentBtn" class="btn btn-danger btn-sm deleteCommentBtn"
                        data-comment_id="<%=comment.getComment_id()%>" onclick="deleteComment(this)">
                    삭제
                </button>
                <% } %>
            </li>
            <% } %>
        </ul>
        <% } else { %>
        <p class="text-muted">아직 댓글이 없습니다.</p>
        <% } %>
    </div>

    <!-- 🔹 댓글 작성 폼 -->
    <h3 class="text-primary">댓글 작성</h3>
    <input type="hidden" id="board_id" value="<%= board.getBoard_id() %>">
    <div class="mb-3">
        <label for="commentContent" class="form-label">댓글 내용</label>
        <textarea id="commentContent" name="commentContent" class="form-control" rows="3" placeholder="댓글을 입력하세요" required></textarea>
    </div>
    <button type="submit" class="btn btn-primary w-100" onclick="addComment()">댓글 등록</button>

</div>

</body>
</html>
