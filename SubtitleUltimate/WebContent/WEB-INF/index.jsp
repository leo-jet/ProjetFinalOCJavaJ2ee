<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="css/styles.css" type="text/css" />
	<title>Edition des sous titres</title>
</head>
<body>
	<div align=center><p>Bienvenue dans notre site de traduction</p></div>
	<c:out value="${ message }"></c:out>
	<form action="changeSubtitle" method="get" name="form1" id="form1">
		<input type="submit" value="Modifier" name="modifierEdit" id="modifierEdit">
	</form>
	<form action="upload" method="post" name="form2" id="form2">
		<input type="submit" value="charger" name="chargerEdit" id="chargerEdit">
	</form>
	<form action="export" method="get" name="form3" id="form3">
		<input type="submit" value="exporter" name="exportEdit" id="exportEdit">
	</form>
	<table>
	        <c:forEach items="${ nomTables }" var="line" varStatus="status">
	        	<tr>
	        		<td style="text-align:right;"><a href="file:///C:/Users/Administrateur/Desktop/SubtitleUltimate/SubtitleUltimate/WebContent/WEB-INF/upload/${ line }.srt" download="${ line }.srt" target="_blank"><c:out value="${ line }" /></a></td>
	        	</tr>
	    	</c:forEach>
	</table>
</body>
</html>