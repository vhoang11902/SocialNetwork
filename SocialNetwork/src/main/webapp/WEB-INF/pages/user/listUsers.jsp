<%-- 
    Document   : listUsers
    Created on : May 8, 2024, 4:46:07 PM
    Author     : hoangtrinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="container">
    <h1 class="text-center text-info mt-1">Danh sách User</h1>

    <div class="add_btn_position">
        <div class="">
            <a href="<c:url value="/admin/handleUser" />" class="btn btn-success">Add User</a>
        </div>
    </div>
    <table class="table table-hover">
        <thead>
            <tr>
                <th>Id</th>
                <th>Avatar</th>
                <th>Username<th>  
                <th>Họ</th>
                <th>Tên</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Active</th>
                <th>ROLE</th>
                <th/>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var ="u"> 
                <tr>
                    <td>${u.id}</td>
                    <td><img src="${u.avatar}" alt="avatar" width="80px"  /></td>
                    <td>${u.username}</td>
                    <td/>
                    <td>${u.firstName}</td>
                    <td>${u.lastName}</td>
                    <td>${u.phone}</td>
                    <td>${u.email}</td>
                    <td>
                        <c:url value="/admin/users/${u.id}" var="userUrl" />
                        <button class="btn ${u.active ? 'btn-success' : 'btn-danger'}" onclick="activeUser('<c:url value="/api/users/${u.id}"/>')">${u.active}</button>
                    </td>

                    <td>${u.userRole}</td>
                    <td>
                        <c:url value="/admin/users/${u.id}" var="user" />
                        <a href="<c:url value="/admin/users/${u.id}/connections"/>" class="btn btn-primary">Connection</a>
                        <button class="btn btn-danger" onclick="deleteApi('<c:url value="/api/posts/${p.id}"/>')">Xóa</button>
                    </td>
                    </td>
                </tr>
            </c:forEach>  
        </tbody>
    </table>

</section>

<script src="<c:url value="/js/main.js" />"></script>
