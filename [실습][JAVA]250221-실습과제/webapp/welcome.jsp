<%@ page import="org.example.vo.MemberVO" %>
<%@ page import="org.example.vo.BoardVO" %>
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

%>
<!-- 문자열을 단독으로 출력하려면 expression을 이용-->
<h1>로그인 성공</h1>
<h1><%=member.getName()%>님 환영합니다</h1>
<!-- 글작성 버튼 추가 -->
<form action="boardServlet" method="GET">
    <button type="submit"> 게시판 들어가기 </button>
</form>

<form action="logoutServlet" method="GET">
    <button type="submit"> 로그아웃 </button>
</form>



</body>
</html>
