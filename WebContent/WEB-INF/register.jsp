<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navbar.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png"
	sizes="64x64">
<link href='https://fonts.googleapis.com/css?family=Roboto'
	rel='stylesheet' type='text/css'>
<title>registro</title>
</head>

<body>
	<jsp:include page="/WEB-INF/navbar.jsp" />
	<div class=wrap>
		<div class=formulario>
			<div class=register>
				<form action="register" method="post">
					<fieldset>
						<legend>datos del usuario</legend>
						<c:if test="${messages != null}">
							<div class=error>${messages.error }</div>
						</c:if>
						<label for="user">nombre de usuario:</label> <input type="text"
							name="user" id="user" value="${usuario.name}"> <label for="email">email:</label>
						<span>es importante que sea correcta, recibirás un correo
							para validar la cuenta</span> <input type="text" id="email" name="email" value="${usuario.email}">
						<label for="pass">clave:</label> <span>al menos ocho
							caracteres, incluyendo letras y números </span> <input type="password"
							name="pass" id="pass"> <label for="pass">verificación
							de clave:</label> <input type="password" name="verpass" id="verpass">
						<input type="submit" value="crear usuario">
					</fieldset>
				</form>
			</div>
		</div>
		<footer>
			<p>trolléame</p>
			<ul>
				<li><a href="#">condiciones legales</a> &nbsp;/&nbsp; <a
					href="#">de uso</a> &nbsp;/&nbsp; <a href="#">y de cookies</a>
					&nbsp;/&nbsp;</li>
				<li><a href="#">quiénes somos</a> &nbsp;/&nbsp;</li>
				<li>licencias: <a href="https://www.meneame.net/COPYING">código,</a>
					<a href="https://creativecommons.org/licenses/by-sa/3.0/">gráficos,</a>
					<a href="https://creativecommons.org/licenses/by/3.0/es/">contenido</a>
					&nbsp;/&nbsp;
				</li>
				<li><a href="">HTML5</a> &nbsp;/&nbsp;</li>
				<li><a href="https://github.com/gallir/Meneame">código
						fuente</a></li>
			</ul>
		</footer>
	</div>
</body>

</html>