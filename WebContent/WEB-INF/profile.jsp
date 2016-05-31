<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">

<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navbar.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png"
	sizes="64x64">
<link href='https://fonts.googleapis.com/css?family=Roboto'
	rel='stylesheet' type='text/css'>
<title>Trolléame</title>
</head>

<body>
	<jsp:include page="/WEB-INF/navbar.jsp" />
	<div class=wrap>
		<div class="profile">
		<c:choose>
			<c:when test="${usuario.id==user.id && usuario.img!=null && usuario.img!='null'}"><a href="${pageContext.request.contextPath}/private/editProfile" class="image-holder"><img src="${usuario.img}" alt="imagen del usuario"/></a></c:when>
			<c:when test="${user.id==usuario.id && (usuario.img==null || usuario.img=='null')}"><a href="${pageContext.request.contextPath}/private/editProfile" class="image-holder"><img src="${pageContext.request.contextPath}/img/logo64x64.png" alt="imagen del usuario"/></a></c:when>
			<c:when test="${usuario.id!=user.id && usuario.img!=null && usuario.img!='null'}"><img src="${usuario.img}" alt="imagen del usuario"/></c:when>
			<c:otherwise><img src="${pageContext.request.contextPath}/img/logo64x64.png" alt="imagen del usuario"/></c:otherwise>
		</c:choose>
			<div class="text">
				<h2>${usuario.name}</h2>
				<c:if test="${user.id==usuario.id}"><h3>${usuario.email}</h3>
				<a class=editarPerfil href="${pageContext.request.contextPath}/private/eliminar?id=${user.id}">eliminar</a>
				<a class=editarPerfil href="${pageContext.request.contextPath}/private/editUser">editar</a></c:if>
			</div>
		</div>
		<jsp:include page="/WEB-INF/footer.jsp" />
	</div>
</body>

</html>