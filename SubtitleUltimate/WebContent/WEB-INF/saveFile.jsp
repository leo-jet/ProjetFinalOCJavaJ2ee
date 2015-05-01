<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Page d'enrégistrement</title>
<script> function submitform()
			{
				  document.form.submit();
			}
	</script>
	<script> function submitform1()
			{
				  document.form1.submit();
			}
	</script>
	<script> function submitform2()
			{
				  document.form2.submit();
			}
	</script>
	<script> function submitform3()
			{
				  document.form3.submit();
			}
	</script>
</head>
<body>
<div id="menu">
		<ul class="menu">
			<li>
				<form name="form" action="accueil" ><a href="javascript:submitform()" class="parent"><span>Accueil</span></a></form>
			</li>
			<li>
				<form action="changeSubtitle" method="get" name="form1" id="form1"><a href="javascript:submitform1()" class="parent"><span>Modifier</span></a></form>
			</li>
			<li>
				<form action="upload" method="post" name="form2" id="form2"><a href="javascript:submitform2()" class="parent"><span>Charger</span></a></form>
			</li>
			
			<li>
				<form action="export" method="get" name="form3" id="form3"><a href="javascript:submitform3()" class="parent"><span>Exporter</span></a></form>
			</li>
			<li class="last"><a href="#"><span>Contacts</span></a></li>
		</ul>
	</div>
	<form action="accueil" method="get" name="formSave" id="formSave">
		<input type="submit" value="Enregister" name="gobutton" id="gobutton">
		<input type="submit" value="Annuler" name="gobutton" id="gobutton">
	</form>
</body>
</html>