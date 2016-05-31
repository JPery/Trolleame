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
		<div class=newswrap>
			<c:forEach var="noticia" items="${newsList}">
				<article class=news-resumen onclick="location.href='comentarios?id=${noticia.id}'">
					<div class=trolleos>
						<div class=votos>
							<a href="">${noticia.likes}</a>"trolleos"
						</div>
						<form class=vota action="<c:url value='trolleos?id=${noticia.id}'/>" method="post">
							<input type="submit" value="trolléalo">
						</form>
						<div class=clics>${noticia.hits} clics</div>
					</div>

					<div class=news>

						<h2>
							<a href="<c:url value='${noticia.url}'/>"> ${noticia.title} </a>
						</h2>
						<div class=subido>
							<a href="profile?id=${noticia.owner}" class="submit_user_img"> <img
								src='<c:choose><c:when test='${usersMap[noticia.id].img!=null}'>${usersMap[noticia.id].img}</c:when><c:otherwise>${pageContext.request.contextPath}/img/logo64x64.png</c:otherwise></c:choose>' alt="">
							</a>
							<p>por</p>
							<a href="profile?id=${noticia.owner}" class="submit_user"> <c:out
									value="${usersMap[noticia.id].name}" />
							</a> <span> ${noticia.dateStamp} </span> publicado: <span><c:out
									value="${timestampMap[noticia.id]}" /></span>
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
									<a href="https://www.facebook.com/trolleamePI/"><img
										src="${pageContext.request.contextPath}/img/fb-24.png"
										alt="facebook logo"></a> <a
										href="https://twitter.com/TrolleamePI/"><img
										src="${pageContext.request.contextPath}/img/tw-24.png"
										alt="twitter logo"></a>
								</div>
							</div>
						</div>

						<div class=details-main>
							<span class=contadorcomentarios> <a
								href="<c:url value='comentarios?id=${noticia.id}'/>"> <span
									class=contador> <c:out value="${commentMap[noticia.id]}" />
								</span>comentarios
							</a>
							</span>
						</div>

					</div>

				</article>
			</c:forEach>
		</div>
		<jsp:include page="/WEB-INF/footer.jsp" />
		</div>
</body>

</html>