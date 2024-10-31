<%-- 
    Document   : listPosts
    Created on : May 7, 2024, 9:51:46 PM
    Author     : hoangtrinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="container">
    <h1 class="text-center text-info mt-1">Comments Post Id: ${p.id}</h1>


    <table class="table table-hover">
        <thead>
            <tr>
                <th>Id</th>
                <th>Username<th>  
                <th>Tac gia</th>
                <th>Nội dung</th>
                <th>Trạng thái</th>
                <th>Ngày đăng bài</th>
                <th/>
            </tr>
        </thead>
        <tbody>
                <c:forEach items="${p.comments}" var ="c"> 
                <tr>
                    <td>${c.id}</td>
                    <td>${c.user.username}</td>
                    <td/>
                    <td>${c.user.lastName} ${c.user.firstName}</td>
                    <td>${c.content}</td>
                    <td>${c.active}</td>
                    <td>${c.createdDate}</td>
                    <td>
                    <c:url value="/admin/posts/${p.id}" var="post" />
                    <button class="btn btn-danger" onclick="deleteApi('<c:url value="/api/comments/${c.id}"/>')">Xóa</button>
                    </td>
                </td>
                </tr>
                </c:forEach>
        </tbody>
    </table>
    
</section>

<script src="<c:url value="/js/main.js" />"></script>
