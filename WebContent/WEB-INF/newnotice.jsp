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
	href="${pageContext.request.contextPath}/css/newnotice.css">
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
		<div class=formulario>
			<div class=noticianueva>

				<form method="post" action="" enctype="multipart/form-data">
					<fieldset>
						<legend>nueva noticia</legend>
						<c:if test="${messages!=null}">
							<div class="error">${messages.error}</div>
						</c:if>
						<label for="url">url de la noticia:</label> <span class="explicacion">se
							linkará al enlace que tú nos mandes</span> <input type="url" id="url"
							name="url" value="${noticia.url}" required> <label
							for="title">tí­tulo de la noticia:</label> <span class="explicacion">tí­tulo
							de la noticia. máximo: 120 caracteres</span> <input type="text"
							id="title" name="title" value="${noticia.title}" required> <label>imagen
							de la noticia:</label> <span class="explicacion">sube tu imagen o danos la url de la que tú elijas</span><input <c:if test="${noticia.img!=null && noticia.img!='null'}">value="${noticia.img}"</c:if> type="text" class="file" name="file"
							id="file">
						<div class="file_upload">
							<input type="file" id="file_upload" name="file_upload">
						</div>
						<label>cuerpo de la noticia:</label>
						<textarea class=escribir name="text" id="text"
							placeholder="Da una breve descripción de la noticia" required>${noticia.text}</textarea>
						<label>categoría:</label> <select id="category" name="category"
							required>
							<option disabled
								<c:if test="${noticia.category==null}">selected</c:if>>--
								Selecciona una --</option>
							<option value="actualidad"
								<c:if test="${noticia.category=='actualidad'}">selected</c:if>>actualidad</option>
							<option value="ocio"
								<c:if test="${noticia.category=='ocio'}">selected</c:if>>ocio</option>
							<option value="deportes"
								<c:if test="${noticia.category=='deportes'}">selected</c:if>>deportes</option>
						</select>
						<input type="submit" value="enviar noticia" class="submitbutton">
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