<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<p>Bienvenue dans la page de modification</p>
	<form name="myform" method="get" action="edit" enctype="multipart/form-data">
		<div align="left"><br>
			<c:forEach items="${ nomTables }" var="line" varStatus="status">
	        	<input type="radio" name="open" value="${ line }"> ${ line }<br>
	    	</c:forEach>
			<input type="submit" name="modify" value="Modifier">
		</div>
	</form>
</body>
</html>