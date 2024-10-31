<%-- 
    Document   : listPosts
    Created on : May 7, 2024, 9:51:46 PM
    Author     : hoangtrinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="container">
    <h1 class="text-center text-info mt-1">DANH SÁCH BÀI VIẾT</h1>
    <table class="table table-hover">
        <thead>
            <tr>
                <th>Id</th>
                <th>Người đăng bài<th>
                <th>Nội dung</th>
                <th>Số lượng bình luận</th>
                <th>Số lượng tương tác</th>
                <th>Ngày đăng bài</th>
                <th>Trạng thái</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${posts}" var="p">
                <tr>
                    <td>${p.id}</td>
                    <td>${p.userId.lastName} ${p.userId.firstName}</td>
                    <td>${p.postContent}</td>
                    <td></td>
                    <td><a href="<c:url value="/admin/listPosts/${p.id}/comments"/>" class="">${fn:length(p.comments)} </a></td>
                    <td><a href="<c:url value="/admin/listPosts/${p.id}/reactions"/>" class="">${fn:length(p.reactions)} </a></td>
                    <td>${p.createdDate}</td>
                    <td>${p.postStatus}</td>
                    <td>
                    <c:url value="/admin/posts/${p.id}" var="post" />
                    <a href="<c:url value="/admin/handleRoute/${p.id}"/>" class="btn btn-primary">Cập nhật</a>
                    <button class="btn btn-danger" onclick="deleteApi('<c:url value="/api/posts/${p.id}"/>')">Xóa</button>
                </td>
                </tr>
            </c:forEach>

        </tbody>
    </table>
    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <li class="page-item"><a class="page-link" href="<c:url value="/admin/listPosts" />">Tất cả</a></li>
                <c:forEach begin="1" end="${counter}" var="i">
                    <c:url value="/admin/listPosts/" var="pageUrl">
                        <c:param name="pageNumber" value="${i}"></c:param>
                    </c:url>
                <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                </c:forEach>
        </ul>
    </c:if>
</section>

<script src="<c:url value="/js/main.js" />"></script>
