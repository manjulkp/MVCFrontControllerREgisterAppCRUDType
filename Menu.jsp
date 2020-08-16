<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" import="com.uttara.mvc.*,java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
	<h1>Menu</h1>
	<b>Welcome ${user}</b><br/>
	<a href="logout.do">Click to logout</a><br/>
	<a href="openEditAccountView.do">Click to edit account</a><br/>
	<a href="openAddContactView.do">Click to Add contact</a><br/>
	<h3>Contacts List using Scriplet</h3>
	<%
		List<ContactBean> beans = (List<ContactBean>) request.getAttribute("contacts");
	
		for(ContactBean bean : beans)
		{
			out.write("Name : <a href='openUpdateContactView.do?sl_no="+bean.getSl_no()+"'>"+bean.getName()+"</a> Email : "+bean.getEmail()+" Ph : "+bean.getPhNum()+" <a href='deleteContact.do?sl_no="+bean.getSl_no()+"'>Delete</a><br/>");
			
		}
	%>
	<h3>Contacts list using JSTL</h3>
	<c:forEach items="${contacts}" var="cb">
		<a href='openUpdateContactView.do?sl_no=${cb.sl_no}'>Name:${cb.name}</a> Email:${cb.email} Ph:${cb.phNum} <a href='deleteContact.do?sl_no=${cb.sl_no}'>Delete</a><br/>
	</c:forEach>
	
</body>

</html>



