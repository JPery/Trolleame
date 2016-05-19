<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png"
	sizes="64x64">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navbar.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link href='https://fonts.googleapis.com/css?family=Roboto'
	rel='stylesheet' type='text/css'>
<title>login</title>
</head>

<body>
	<jsp:include page="/WEB-INF/navbar.jsp" />
	<div class=wrap>
		<div class=login>
			<section class=faq>
				<h3>¿Qué es trolléame?</h3>
				<p>Es un sitio que te permite enviar una historia troll que será
					revisada por todos y será promovida, o no, a la página principal.
					Cuando un usuario enví­a una historia ésta queda en la cola de
					pendientes hasta que reúne los votos suficientes para ser promovida
					a la página principal.</p>
				<h3>¿Todaví­a no eres usuario de trolléame?</h3>
				<p>Como usuario registrado podrás, entre otras cosas:</p>
				<ul>
					<li>Enviar historias</li>
					<li class=listtext>
						<p>Una vez registrado puedes enviar las historias que
							consideres más trolls para la comunidad. Si tienes algún tipo de
							duda sobre que tipo de historias troll puedes enviar, revisa
							nuestras preguntas frecuentes sobre trolléame.</p>
					</li>
					<li>Escribir comentarios</li>
					<li class=listtext>
						<p>Puedes escribir tu opinión sobre las historias trolls
							enviadas a trolléame mediante comentarios de texto. También
							puedes votar positivamente aquellos comentarios ingeniosos,
							divertidos o interesantes y negativamente aquellos que consideres
							inoportunos.</p>
					</li>
					<li>Perfil de usuario</li>
					<li class=listtext>
						<p>Toda tu información como usuario troll está disponible
							desde la página de tu perfil. También puedes subir una imagen que
							representará a tu usuario en trolléame.</p>
					</li>
				</ul>
				<h3>
					<a href="register">Regí­strate ahora</a>
				</h3>
			</section>
			<div class=formulario>
				<form action="login" method="post">
					<fieldset>
						<legend>
							<span>usuario y contraseña</span>
						</legend>
						<c:if test="${messages != null}">
							<div class=error>${messages }</div>
						</c:if>
						<label for="user">usuario o email:</label> <input type="text"
							name="user" id="user"> <label for="pass">clave:</label> <input
							type="password" name="pass" id="pass">
						<div class=remember>
							<label for="remember">recuérdame:</label> <input type="checkbox"
								name="remember" id="remember">
						</div>
						<input type="submit" value="login">
					</fieldset>
				</form>
			</div>
		</div>
		<footer>
			<p>trolléame</p>
			<ul>
				<li><a href="">condiciones legales</a> &nbsp;/&nbsp; <a href="">de
						uso</a> &nbsp;/&nbsp; <a href="">y de cookies</a> &nbsp;/&nbsp;</li>
				<li><a href="">quiénes somos</a> &nbsp;/&nbsp;</li>
				<li>licencias: <a href="">código,</a> <a href="">gráficos,</a>
					<a href="">contenido</a> &nbsp;/&nbsp;
				</li>
				<li><a href="">HTML5</a> &nbsp;/&nbsp;</li>
				<li><a href="https://github.com/gallir/Meneame">código
						fuente</a></li>
			</ul>
		</footer>
	</div>
</body>

</html>