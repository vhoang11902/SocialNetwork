<%-- 
    Document   : listSurveys
    Created on : May 21, 2024, 5:56:27 PM
    Author     : hoangtrinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="container">
    <h1 class="text-center text-info mt-1">DANH SÁCH BÀI KHẢO SÁT</h1>
    <div class="add_btn_position">
    <div>
        <a href="<c:url value="/admin/handleSurvey" />" class="btn btn-success">Add Survey</a>
    </div>
</div>
    <table class="table table-hover">
        <thead>
            <tr>
                <th>Id</th>
                <th>Nội dung</th>
                <th>Ngày đăng bài</th>
                <th>Trạng thái</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
                <c:forEach items="${surveys}" var ="s"> 
                <tr>
                    <td>${s.id}</td>
                    <td>${s.content}</td>
                    <td>${s.createdDate}</td>
                    <td>
                    <c:url value="/admin/surveys/${c.id}" var="survey" />
                    <a href="<c:url value="/admin/handleSurvey/${s.id}"/>" class="btn btn-success">Xem chi tiết</a>
                    <button class="btn btn-danger" onclick="deleteApi('<c:url value="/api/surveys/${s.id}"/>')">Xóa</button>
                    </td>
                </td>
                </tr>
                </c:forEach>
        </tbody>
    </table>
    
</section>

<script src="<c:url value="/js/main.js" />"></script>
