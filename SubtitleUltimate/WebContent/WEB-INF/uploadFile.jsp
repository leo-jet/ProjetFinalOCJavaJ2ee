<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<p>Nous sommes dans la jsp UploadFile</p>
	<a href="edit">aller à edit</a>
	<form action="edit" method="get" name="form1" id="form1">
		<input type="submit" action="edit" value="get">
	</form>
	<form action="edit" method="post" name="form2" id="form2" enctype="multipart/form-data">
		<input type="submit" value="charger">
		<input type="file" name="fichier" id="fichier" >
	</form>
</body>
</html>