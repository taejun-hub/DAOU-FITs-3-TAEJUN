
<%@ page import="org.example.vo.BoardVO" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.vo.MemberVO" %>
<%@ page import="org.apache.ibatis.jdbc.Null" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  MemberVO loggedInUser = (MemberVO) session.getAttribute("member");
  boolean isLoggedIn = loggedInUser != null;
%>
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
  <meta charset="utf-8">
  <title>게시글 목록 조회</title>

  <!-- jQuery CDN -->
  <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
  <!-- Bootstrap CDN -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">


  <script src="js/boardList.js"></script>


  <style>


    .bi {
      vertical-align: -.125em;
      fill: currentColor;
    }


    .nav-scroller .nav {
      display: flex;
      flex-wrap: nowrap;
      padding-bottom: 1rem;
      margin-top: -1px;
      overflow-x: auto;
      text-align: center;
      white-space: nowrap;
      -webkit-overflow-scrolling: touch;
    }


    .bd-mode-toggle .dropdown-menu .active .bi {
      display: block !important;
    }
  </style>

  <!-- Custom styles for this template -->
  <link href="css/dashboard.css" rel="stylesheet">
</head>
<body>
<%
  // request 객체를 이용해서 parameter 를 받을 수 있다
  List<BoardVO> boardList = (List<BoardVO>)request.getAttribute("boardList");

%>


<header class="navbar sticky-top bg-dark flex-md-nowrap p-0 shadow" data-bs-theme="dark">
  <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3 fs-6 text-white" href="#">게시판</a>
</header>


<div class="container-fluid">
  <div class="row">

    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
      <h2>게시글 목록</h2>
      <!-- 🔹 글쓰기 버튼 -->
      <div class="d-flex justify-content-end mb-3">
        <button class="btn btn-success" onclick="boardWriteBtnHandler(<%=isLoggedIn%>)">글 작성</button>
      </div>
      <div class="table-responsive small">
        <table class="table table-striped table-sm">
          <thead>
          <tr>
            <th scope="col">번호</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">작성일</th>
            <th scope="col">댓글 수</th>
            <th scope="col">좋아요 수</th>
            <th scope="col">조회수</th>
          </tr>
          </thead>
          <tbody id="myTable">
          <%
            for (BoardVO board: boardList) {
          %>
            <tr>
              <!-- 제목을 클릭하면 BoardDetailServlet 실행 -->
              <td><%=board.getBoard_id()%></td>
              <td>
                <a href="boardDetailServlet?id=<%=board.getBoard_id()%>" class="text-decoration-none"><%=board.getTitle()%></a>
              </td>
              <td><%=board.getAuthor()%></td>
              <td><%=board.getCreated_at()%></td>
              <td><%=board.getComment_cnt()%></td>
              <td><%=board.getLike_cnt()%></td>
              <td><%=board.getView_cnt()%></td>
            </tr>
          <%
            }
          %>
          </tbody>
        </table>
      </div>
      <!-- 🔹 검색창 추가 -->
      <div class="d-flex mb-3">
        <input type="text" id="searchInput" class="form-control me-2" placeholder="검색어 입력">
        <button class="btn btn-primary" id="searchBtn">검색</button>
      </div>
    </main>
  </div>
</div>
</body>
</html>
