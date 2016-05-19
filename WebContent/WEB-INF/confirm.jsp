<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">

<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/navbar.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/confirm.css">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/logo.png" type="image/png"
	sizes="64x64">
<link href='https://fonts.googleapis.com/css?family=Roboto'
	rel='stylesheet' type='text/css'>
<title>Trolléame</title>
</head>

<body>
	<jsp:include page="/WEB-INF/navbar.jsp" />
	<div class=wrap>
		<h2>
			<span>${message}</span>
		</h2>
		<a href="${pageContext.request.contextPath}/comentarios?id=${id}" >
			<input class="cancel" type="submit" value="cancelar">
		</a>
		<form action="" method="post">
			<input type="submit" value="aceptar">
		</form>
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