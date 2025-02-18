<%@ page import="org.example.bookservlet.vo.MemberVO" %>
<%@ page import="org.example.bookservlet.vo.BoardVO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%-- JSP 주석 --%>
<!-- scriptlet(일반 JAVA 코드가 나올 수 있다.) -->
<%
    // request 객체를 이용해서 parameter 를 받을 수 있다
    MemberVO member = (MemberVO) request.getAttribute("member");
    List<BoardVO> boardList = (List<BoardVO>)request.getAttribute("boardList");

%>
<!-- 문자열을 단독으로 출력하려면 expression을 이용-->
<h1>로그인 성공</h1>
<h1><%=member.getName()%>님 환영합니다</h1>
<h2>게시글 목록</h2>
<!-- 글작성 버튼 추가 -->
<button onclick="location.href='board.html'">글 작성</button>
<table border="1">
    <thead>
    <tr>
        <th>제목</th>
        <th>내용</th>
        <th>작성자</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (BoardVO board : boardList) {
    %>
    <tr>
<%--        <td><%= board.getTitle() %></td>--%>
        <td><a href="boardDetailServlet?board_id=<%= board.getBoard_id() %>&title=<%= board.getTitle()%>&content=<%= board.getContent()%>&author=<%=board.getAuthor()%>"><%= board.getTitle() %></a></td>
        <td><%= board.getContent() %></td>
        <td><%= board.getAuthor() %></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
