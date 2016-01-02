<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function startIdCheck(){
	var myWindow = window.open("idcheck.jsp", "", "width=200, height=200");
}
function startSearPost(){
	var myWindow = window.open("searchPost.jsp", "", "width=200, height=200");
}
function 아이디받다(id){
	var txtId = document.getElementById("txtId");
	txtId.value=id;
}
function 주소받다(post,postAddress){
	var txtPost = document.getElementById("txtPost");
	var txtPostAddress = document.getElementById("txtPostAddress");
	txtPost.value=post;
	txtPostAddress.value=postAddress;
}
</script>
</head>
<body>
<h>회원등록</h><br>
<form action='RegistMember.do'>
성명<input type='text' name='name'/><br>
전화<input type='text' name='tel'/><br>
<input type='submit' value='regist'/>
</form>
</body>
</html>











