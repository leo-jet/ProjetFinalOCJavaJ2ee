<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="css/styles.css" type="text/css" />
	<title>Edition des sous titres</title>
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
	
	<h1 style="text-align: center;">
	Bienvenue sur mon &eacute;diteur de sous-titre.</h1>
<h2>
	1-) Pour ex&eacute;cuter ce projet vous devez :&nbsp;</h2>
<h2>
	-<span style="color:#ff0000;">Configurer la base de donn&eacute;es </span>: j&#39;utilise une base de donn&eacute;es Mysql. Vous devez le faire dans le fichier DaoFactory.java et DaoFactoryImpl.java.&nbsp;</h2>
<h2>
	- <span style="color:#ff0000;">Renseigner le chemin du dossier de sauvegarde des fichiers</span>. Pour cela vous devez modifier le constructeur de la classe DossierUpload.java.</h2>
	
</body>
</html>