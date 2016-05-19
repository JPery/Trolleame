<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<header>
		<div id=dropdown>
			<button id=dropbtn></button>
			<ul id=dropdown-content>
				<li id=searchdrop>
					<form action="search" method="get">
						<input type="search" id="q1" name="q">
					</form>
				</li>
				<li><a
					href="${pageContext.request.contextPath}/index?category=actualidad"
					<c:if test="${param.category == 'actualidad'}">class="active"</c:if>>actualidad</a></li>
				<li><a
					href="${pageContext.request.contextPath}/index?category=ocio"
					<c:if test="${param.category == 'ocio'}">class="active"</c:if>>ocio</a></li>
				<li><a
					href="${pageContext.request.contextPath}/index?category=deportes"
					<c:if test="${param.category == 'deportes'}">class="active"</c:if>>deportes</a></li>
				<li><a
					href="${pageContext.request.contextPath}/private/nuevanoticia">enviar
						historia</a></li>
				<li><a
					href="${pageContext.request.contextPath}/<c:url value="index">
   							<c:if test="${param.category!=null}">
   								<c:param name="category" value="${param.category}"/>
   							</c:if>
					  	</c:url>"
					<c:if test="${param.order == null}">class="active"</c:if>>portada</a></li>
				<li><a
					href="${pageContext.request.contextPath}/<c:url value="index">
   							<c:if test="${param.category!=null}">
   								<c:param name="category" value="${param.category}"/>
   							</c:if>
   							<c:param name="order" value="nuevas"/>
					  	</c:url>"
					<c:if test="${param.order =='nuevas'}">class="active"</c:if>>nuevas</a></li>
				<li><a
					href="${pageContext.request.contextPath}/<c:url value="index">
   							<c:if test="${param.category!=null}">
   								<c:param name="category" value="${param.category}"/>
   							</c:if>
   							<c:param name="order" value="populares"/>
					  </c:url>"
					<c:if test="${param.order=='populares'}">class="active"</c:if>>populares</a></li>
				<li><a
					href="${pageContext.request.contextPath}/<c:url value="index">
   							<c:if test="${param.category!=null}">
   								<c:param name="category" value="${param.category}"/>
   							</c:if>
   							<c:param name="order" value="destacadas"/>
					  </c:url>"
					<c:if test="${param.order=='destacadas'}">class="active"</c:if>>destacadas</a></li>
			</ul>
		</div>
		<div id="img">
			<a href="${pageContext.request.contextPath}/index"></a>
		</div>
		<div class=navlogo>
			<img src="${pageContext.request.contextPath}/img/h9_logo_ed.svg"
				alt="">
		</div>
		<ul class=formularios>
			<c:choose>
				<c:when test="${user == null}">
					<li><a href="${pageContext.request.contextPath}/user/login">login</a></li>
					<li><a href="${pageContext.request.contextPath}/user/register">registrarse</a></li>
				</c:when>
				<c:otherwise>
					<li><a
						href="${pageContext.request.contextPath}/private/profile">${user.name}
							<img width="20" height="20"
							src="<c:choose><c:when test="${user.img!=null && user.img!='null'}">${user.img}</c:when><c:otherwise>${pageContext.request.contextPath}/img/logo64x64.png</c:otherwise></c:choose>"></img>
					</a></li>
					<li><a
						href="${pageContext.request.contextPath}/private/logout"> <img
							width="20" height="20"
							src="${pageContext.request.contextPath}/img/logout.png"></img>
					</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</header>
	<nav id="header-center">
		<a href="${pageContext.request.contextPath}/index" id="logo"><img
			src="${pageContext.request.contextPath}/img/logo64x64.png"
			alt="trolléame logo" width="40" height="40"></a>
		<c:choose>
			<c:when test="${category != null}">
				<a id="nombre"
					href="${pageContext.request.contextPath}/index?category=${category}">${category}</a>
			</c:when>
			<c:otherwise>
				<a id="nombre" href="${pageContext.request.contextPath}/index">edición
					general</a>
			</c:otherwise>
		</c:choose>
		<div class=RS>
			<a
				href="https://www.facebook.com${pageContext.request.contextPath}PI/"><img
				src="${pageContext.request.contextPath}/img/fb-40.png"
				alt="facebook logo"></a> <a
				href="https://twitter.com${pageContext.request.contextPath}PI"><img
				src="${pageContext.request.contextPath}/img/tw-40.png"
				alt="twitter logo"></a>
		</div>
		<div class=search>
			<form action="search" method="get">
				<input type="search" id="q2" name="q">
			</form>
		</div>
	</nav>
	<nav id="header-bottom">
		<ul>
			<li><a
				href=${pageContext.request.contextPath}/private/nuevanoticia>enviar
					historia</a></li>
			<li><a
				href="${pageContext.request.contextPath}/<c:url value="index">
   							<c:if test="${param.category!=null}">
   								<c:param name="category" value="${param.category}"/>
   							</c:if>
					  	</c:url>"
				<c:if test="${param.order == null}">class="active"</c:if>>portada</a></li>
			<li><a
				href="${pageContext.request.contextPath}/<c:url value="index">
   							<c:if test="${param.category!=null}">
   								<c:param name="category" value="${param.category}"/>
   							</c:if>
   							<c:param name="order" value="nuevas"/>
					  	</c:url>"
				<c:if test="${param.order =='nuevas'}">class="active"</c:if>>nuevas</a></li>
			<li><a
				href="${pageContext.request.contextPath}/<c:url value="index">
   							<c:if test="${param.category!=null}">
   								<c:param name="category" value="${param.category}"/>
   							</c:if>
   							<c:param name="order" value="populares"/>
					  </c:url>"
				<c:if test="${param.order=='populares'}">class="active"</c:if>>populares</a></li>
			<li><a
				href="${pageContext.request.contextPath}/<c:url value="index">
   							<c:if test="${param.category!=null}">
   								<c:param name="category" value="${param.category}"/>
   							</c:if>
   							<c:param name="order" value="destacadas"/>
					  </c:url>"
				<c:if test="${param.order=='destacadas'}">class="active"</c:if>>destacadas</a></li>
		</ul>
		<ul class=formularios>
			<li><a
				href="${pageContext.request.contextPath}/index?category=actualidad"
				<c:if test="${param.category == 'actualidad'}">class="active"</c:if>>actualidad</a></li>
			<li><a
				href="${pageContext.request.contextPath}/index?category=ocio"
				<c:if test="${param.category == 'ocio'}">class="active"</c:if>>ocio</a></li>
			<li><a
				href="${pageContext.request.contextPath}/index?category=deportes"
				<c:if test="${param.category == 'deportes'}">class="active"</c:if>>deportes</a></li>
		</ul>
	</nav>