<%-- 
    Document   : header
    Created on : Jul 21, 2023, 1:12:19 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="action" />
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">E-commerce Website</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav me-auto">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <li class="nav-item">
                            <a class="nav-link" href="/SocialNetwork/admin/dashboard">Dashboard</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/SocialNetwork/admin/listPosts">Post</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/SocialNetwork/admin/listUsers">User</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/SocialNetwork/admin/listSurveys">Survey</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link">${pageContext.request.userPrincipal.name}</a>
                        </li>

                        <li class="nav-item ">
                            <a class="btn btn-danger" href="<c:url value="/logout"/>">Log out</a> 
                        </li>

                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/login"/>">Đăng nhập</a>
                        </li>
                    </c:otherwise>



                </c:choose>
            </ul>


            <c:if test="${users != null}">
                <c:url value="/admin/listUsers/" var="action" />
            </c:if>
            <form class="d-flex" action="${action}">
                <c:choose>
                    <c:when test="${users != null}">
                        <div class="" >
                            <input type="text" class="form-control me-2" id="plate"  placeholder="Nhập từ khóa.." name="username">
                        </div>
                        <button class="btn btn-primary" type="submit">Tìm</button>
                    </c:when>
                </c:choose>
                
            </form>
        </div>
    </div>
</nav>