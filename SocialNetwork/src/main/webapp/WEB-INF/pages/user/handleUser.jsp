<%-- 
    Document   : handleUser
    Created on : Jun 21, 2024, 8:22:23 PM
    Author     : hoangtrinh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm bài khảo sát</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
    <h1 class="text-center text-info mt-1">Thêm bài khảo sát</h1>
    <c:url value="/admin/users" var="user" />
    <form:form modelAttribute="user" method="post" action="${user}" enctype="multipart/form-data" id="userForm">
        <form:errors path="*" element="div" cssClass="alert alert-danger" />
        <form:hidden path="id" />

        <div class="row">
            <div class="col form-floating mb-3 mt-3">
            <form:input class="form-control" path="firstName" id="firstName" required="required" placeholder="Nhập nội dung khảo sát" name="content" />
            <label for="firstName">First Name</label>
        </div>
            
        <div class="col form-floating mb-3 mt-3">
            <form:input class="form-control" path="lastName" id="lastName" required="required" placeholder="Nhập nội dung khảo sát" name="content" />
            <label for="lastName">Last Name</label>
        </div>
        </div>
        
        <div class="form-floating mb-3 mt-3">
            <form:input class="form-control" path="username" id="username" required="required" placeholder="Nhập nội dung khảo sát" name="content" />
            <label for="username">Username</label>
        </div>
            
        <div class="form-floating mb-3 mt-3">
            <form:input class="form-control" type="password" path="password" id="password" required="required" placeholder="Nhập nội dung khảo sát" name="content" />
            <label for="password">Password</label>
        </div>
            
        <div class="form-floating mb-3 mt-3">
            <form:input class="form-control" path="email" id="email" required="required" placeholder="Nhập nội dung khảo sát" name="content" />
            <label for="email">Email:</label>
        </div>
            
        <div class="form-floating mb-3 mt-3">
            <form:input class="form-control" path="phone" id="phone" required="required" placeholder="Nhập nội dung khảo sát" name="content" />
            <label for="phone">Phone Number:</label>
        </div>
            
        <div class="">
            <label for="avatarFile">Avatar</label>
            <form:input class="form-control-file border" type="file" path="avatar" id="avatar" required="required" />
        </div>
        
        <div class="">
            <label for="coverImageFile">Cover Image</label>
            <form:input class="form-control-file border" type="file" path="coverImage" id="coverImage" required="required" />
        </div>
        
        <div class="form-floating mb-3 mt-3">
            <button class="btn btn-success" type="button" onclick="submitForm()">Thêm Khảo sát</button>
        </div>
    </form:form>

    <script>
        function submitForm() {
            var form = document.getElementById("userForm");
            var formData = new FormData(form);
            fetch("/SocialNetwork/api/adminUsers/", {
                method: "POST",
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(data => {
                window.location.href = "/SocialNetwork/admin/listUsers";
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Error occurred. Please try again.");
            });
        }
    </script>
</body>
</html>
