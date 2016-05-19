<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navbar.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/newnotice.css">
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
			<div class=noticianueva>
				<form method="post" action="editProfile" enctype="multipart/form-data">
					<fieldset>
					<legend>usuario</legend>
						<c:if test="${messages!=null}">
							<div class="error">${messages.error}</div>
						</c:if>
						<label>subir imagen:</label> <span>sube tu imagen o danos la url de la que tú elijas</span><input <c:if test="${user.img!=null && user.img!='null'}">value="${user.img}"</c:if> type="text" class="file" name="file"
							id="file">
						<div class="file_upload">
							<input type="file" id="file_upload" name="file_upload">
						</div>
						<input type="submit" value="guardar imagen">
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