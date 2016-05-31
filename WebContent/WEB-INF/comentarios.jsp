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
	href="${pageContext.request.contextPath}/css/comentarios.css">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/logo.png" type="image/png"
	sizes="64x64">
<link href='https://fonts.googleapis.com/css?family=Roboto'
	rel='stylesheet' type='text/css'>
<title>Trolléame</title>
<script src="js/jquery-1.12.0.min.js"></script>
<script>function showEditForm(e) {
    var commentWrapper = $(e).closest(".comentariotexto");
    $(commentWrapper).toggle();
    $(commentWrapper).siblings(".formularioEditar").toggle();
    commentWrapper = $(commentWrapper).siblings(".formularioEditar");
    $(commentWrapper).children(".edit-comment-form").children("textarea").toggle();
    $(commentWrapper).children(".edit-comment-form").children(".formbutton").children("input").toggle();
    $(commentWrapper).children(".edit-comment-form").children(".formbutton").css("display","block");
    $(commentWrapper).children(".edit-comment-form").children("textarea").css("display","block");
};</script>
</head>

<body>
	<jsp:include page="/WEB-INF/navbar.jsp" />
	<div class=wrap>
		<section class=news-resumen>
			<div class=trolleos>

				<div class=votos>
					<a href="">${noticia.likes}</a>"trolleos"
				</div>
				<form class=vota action="<c:url value='trolleos?id=${noticia.id}'/>"
					method="post">
					<input class="trolll" type="submit" value="trolléalo">
				</form>
				<div class=clics>${noticia.hits} clics</div>
			</div>
			<div class=news>

				<h2>
					<a href="<c:url value='${noticia.url}'/>">${noticia.title}</a>
				</h2>
				<div class=subido>
					<a class="submit_user_img"> <img
						src='<c:choose><c:when test="${propietario.img!=null}">${propietario.img}</c:when><c:otherwise>${pageContext.request.contextPath}/img/logo64x64.png</c:otherwise></c:choose>'
						alt="">
					</a>
					<p>por</p>
					<a
						href="${pageContext.request.contextPath}/profile?id=${noticia.owner}"
						class="submit_user">${propietario.name}</a> <span>
						${noticia.dateStamp} </span> publicado: <span> ${timeStamp} </span>
				</div>
				<a href="<c:url value='${noticia.url}'/>" class=miniatura> <img
					src='<c:choose>
							<c:when test="${noticia.img!=null && noticia.img!='null'}">
								${noticia.img}
							</c:when>
							<c:otherwise>
								${pageContext.request.contextPath}/img/logo64x64.png
							</c:otherwise>
							</c:choose>'
					alt="">
				</a>
				<p>${noticia.text}</p>
				<div class=details>
					<strong>categoría</strong>: <span>${noticia.category}</span>
					<div class="tool">
						<strong class=wideonly><a href="">compartir</a>:</strong>
						<div class=RS>
							<a href="https://www.facebook.com/TrolleamePI/"><img
								src="${pageContext.request.contextPath}/img/fb-24.png"
								alt="facebook logo"></a> <a
								href="https://twitter.com/TrolleamePI/"><img
								src="${pageContext.request.contextPath}/img/tw-24.png"
								alt="twitter logo"></a>
						</div>
					</div>
				</div>
				<div class=details-main>
					<span class=contadorcomentarios> <a href=""> <span
							class=contador>${numComments}</span>comentarios
					</a>
					</span>
					<c:if test="${user.id == noticia.owner}">
						<div class=editar>
							<a
								href="${pageContext.request.contextPath}/private/editar?id=${noticia.id}">editar</a>
						</div>
						<div class=editar>
							<a
								href="${pageContext.request.contextPath}/private/eliminar?id=${noticia.id}">eliminar</a>
						</div>
					</c:if>
				</div>
			</div>
		</section>

		<c:if test="${(user==null && numComments>0) || user!=null}">
			<div class=comentarios>
				<c:if test="${user!=null}">
					<div class=escribircomentario>
						<c:if test="${messages != null}">
							<div class=error>${messages.error }</div>
						</c:if>
						<form method="post" action="">
							<textarea class=escribir name="text" id="text"
								placeholder="Escribe aquí tu comentario">${comentarioEditar.text}</textarea>
							<input type="submit" value="enviar comentario" class="submitbutton">
						</form>

					</div>
				</c:if>
				<c:forEach var="comentario" items="${commentList}">
					<div class=comentariodatos>
						<div class=comentario>
							<div class=comentariosvotos>
								<span>votos: </span> <span class=numero>${comentario.likes}</span>
								<span>karma: </span> <span class=numero>30</span>
							</div>


							<div class=comentariousuario>
								<a href="profile?id=${comentario.owner}" class="submit_user_img">
									<img
									src='<c:choose><c:when test='${commentMap[comentario.id].img!=null}'>${commentMap[comentario.id].img}</c:when><c:otherwise>${pageContext.request.contextPath}/img/logo64x64.png</c:otherwise></c:choose>'
									alt="">
								</a> <a
									href="${pageContext.request.contextPath}/profile?id=${comentario.owner}"
									class=submit_user><c:out
										value="${commentMap[comentario.id].name}" /></a> <span>${timeStampMap[comentario.id]}</span>
							</div>
						</div>
						<div class=comentariotexto>
							<p>${comentario.text}</p>
							<c:if test="${user.id == comentario.owner}">
								<div class=editar>
									<i onclick="showEditForm(this)">editar</i>
								</div>
								<div class=editar>
									<a
										href="${pageContext.request.contextPath}/private/eliminarComentario?id=${comentario.id}">eliminar</a>
								</div>
							</c:if>
						</div>
						<c:if test="${user.id == comentario.owner}">
						<div class="formularioEditar">
							<form class="edit-comment-form"
                                method="post" action="private/editarComentario?id=${comentario.id}">
                                <textarea class="edit-comment-textarea" name="text${comentario.id}" id="text${comentario.id}">${comentario.text}</textarea>
                                <div class="formbutton">
                                	<input type="submit" value="editar comentario" class="submitbutton">
                                </div>
                        </form>
                        </div>
                        </c:if>
					</div>
				</c:forEach>
			</div>
		</c:if>
		<jsp:include page="/WEB-INF/footer.jsp" />
	</div>
</body>

</html>