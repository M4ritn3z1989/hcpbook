<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "javax.ejb.EJB" %>
<%@ page import = "javax.naming.InitialContext" %>
<%@ page import = "com.sappress.hcpbook.chp03.ReadingListManagerBeanLocal" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	try
	{
		ReadingListManagerBeanLocal readingListManager = (ReadingListManagerBeanLocal) request.getSession().getAttribute("readingListManager");
		java.util.List<String> readingList =null;
		if (readingListManager !=null)
			readingList = readingListManager.getReadingList();
		else
			readingList = new java.util.ArrayList<String>();
		request.setAttribute("readingList", readingList);
		
	}
	catch(Exception ex)
	{
		out.println("EJB Exception: " + ex.getMessage());
	}

%>
<jsp:useBean id="readingList" scope="session" class="java.util.ArrayList" />

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title> HCP Book :: Chapter 3 :: My HCP Reading List</title>
	<link href="css/style.css" rel="stylesheet">
</head>
<body>
	<h1> My HCP Reading List</h1>
	<table class="readingList">
		<tr>
			<th>Book Title</th>
		</tr>
		<c:forEach var="book" items="${readingList}">
			<tr> <td><c:out value="${book }"/></td></tr>
		</c:forEach>
	</table>
	<section>
	<form method="post" action="ReadingListServlet"><div>
		<label for="inpBook">
			Book:<input id="inpBook" name="book"/>
		</label>&nbsp;
		<button type="submit">Add</button></div></form>
	</section>
</body>
</html>