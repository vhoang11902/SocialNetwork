<%-- 
    Document   : listConnections
    Created on : May 8, 2024, 5:57:25 PM
    Author     : hoangtrinh
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="container">
    <h1 class="text-center text-info mt-1">Danh sách Kết Nối</h1>


    <table class="table table-hover">
    <thead>
        <tr>
            <th>User ID</th>
            <th>Avatar</th>
            <th>Username</th>
            <th>Họ</th>
            <th>Tên</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Connect Status</th>
            <th>Created Date</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <tbody>
        <c:forEach items="${connections}" var="innerArray">
            <tr>
                <td>${innerArray[0].id}</td> <!-- User ID -->
                <td><img src="${innerArray[0].avatar}" alt="avatar" width="80px" /></td> <!-- Avatar -->
                <td>${innerArray[0].username}</td> <!-- Username -->
                <td>${innerArray[0].firstName}</td> <!-- First Name -->
                <td>${innerArray[0].lastName}</td> <!-- Last Name -->
                <td>${innerArray[0].phone}</td> <!-- Phone -->
                <td>${innerArray[0].email}</td> <!-- Email -->
                <td>${innerArray[1].connectStatus}</td> <!-- Connect Status -->
                <td>${innerArray[1].createdDate}</td> <!-- Created Date -->
                <td>
                        <c:url value="/admin/users/${innerArray[1].id}" var="user" />
                    <button class="btn btn-danger" onclick="deleteApi('<c:url value="/api/connections/${innerArray[1].id}"/>')">Xóa</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
    </tbody>
</table>

    
</section>

<script src="<c:url value="/js/main.js" />"></script>