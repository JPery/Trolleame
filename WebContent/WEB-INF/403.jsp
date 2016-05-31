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
	href="${pageContext.request.contextPath}/css/error.css">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/logo.png" type="image/png"
	sizes="64x64">
<link href='https://fonts.googleapis.com/css?family=Roboto'
	rel='stylesheet' type='text/css'>
<title>Trolléame</title>
</head>

<body>
	<div class=wrap>
		<div class=errorHttp>
			<h2 class=errorh2>
				<span>Error 403:</span> Acceso denegado
			</h2>
			<img class="logoSad" alt=""
				src="${pageContext.request.contextPath}/img/notpass.png"> <a
				href="${pageContext.request.contextPath}/index" class="return">Volver
				a la página principal</a>
		</div>
		<jsp:include page="/WEB-INF/footer.jsp" />
	</div>
</body>

</html>