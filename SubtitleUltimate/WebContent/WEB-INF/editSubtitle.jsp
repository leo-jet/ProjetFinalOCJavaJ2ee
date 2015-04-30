<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edition des sous titres</title>
</head>
<body>
	<p>Nous sommes dans la page des éditions des sous-titres</p>
	<a href="upload">aller à edit</a>
	<form action="enreg" method="post" name="form1" id="form1">
		<input type="submit" action="enreg" value="Modifier" name="MofifyEdit" id="">
	</form>
	<form action="upload" method="post" name="form2" id="form2">
		<input type="submit" action="upload" value="chargerEdit" name="charger" id="charger">
	</form>
	<form method="get" name="form" id="form" enctype="multipart/form-data" action="enreg">
		<input type="submit" value="Enregistrer" name="EnregistrerEdit" id="EnregistrerEdit">   
	    <table>
	        <c:forEach items="${ subtitlesOriginal }" var="line" varStatus="status">
	        	<tr>
	        		<td style="text-align:right;"><c:out value="${ line }" /></td>
	        		<td><input type="text" name="line${ status.index }" id="line${ status.index }" value="${ subtitlesTraduit[status.index] }" size="35" /></td>
	        	</tr>
	    	</c:forEach>
	    </table>
    </form>
    
</body>
</html>