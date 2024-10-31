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
    <c:url value="/admin/surveys" var="surveys" />
    <form:form modelAttribute="survey" method="post" action="${surveys}" enctype="multipart/form-data">
        <form:errors path="*" element="div" cssClass="alert alert-danger" />
        <form:hidden path="id" />

        <div class="form-floating mb-3 mt-3">
            <form:textarea class="form-control" path="content" id="content" required="required" placeholder="Nhập nội dung khảo sát" name="content" />
            <label for="content">Nội dung khảo sát</label>
        </div>

        <div class="form-check mb-3 mt-3">
            <form:checkbox class="form-check-input" path="active" id="active" name="active" />
            <label class="form-check-label" for="active">Kích hoạt</label>
        </div>

        <div id="surveyChoices" class="form-floating mb-3 mt-3">
            <c:forEach var="choice" items="${survey.surveyChoices}">
                <div class="input-group mb-2">
                    <input type="text" class="form-control" name="choices" value="${choice.content}" placeholder="Nhập lựa chọn khảo sát" />
                    <div class="input-group-append">
                        <button class="btn btn-danger" type="button" onclick="removeChoice(this)">Xoá</button>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="form-floating mb-3 mt-3">
            <button id="addChoice" class="btn btn-primary" type="button">Thêm lựa chọn</button>
        </div>

        <div class="form-floating mb-3 mt-3">
            <button class="btn btn-success" type="submit">Thêm Khảo sát</button>
        </div>
    </form:form>

    <script>
        document.getElementById("addChoice").addEventListener("click", function() {
            var choicesDiv = document.getElementById("surveyChoices");
            var inputGroup = document.createElement("div");
            inputGroup.className = "input-group mb-2";

            var input = document.createElement("input");
            input.type = "text";
            input.className = "form-control";
            input.name = "choices";
            input.value = "${surveychoices.content}"; // Set giá trị từ surveychoices.content vào input
            input.placeholder = "Nhập lựa chọn khảo sát";

            var inputGroupAppend = document.createElement("div");
            inputGroupAppend.className = "input-group-append";

            var deleteButton = document.createElement("button");
            deleteButton.className = "btn btn-danger";
            deleteButton.type = "button";
            deleteButton.innerText = "Xoá";
            deleteButton.addEventListener("click", function() {
                choicesDiv.removeChild(inputGroup);
            });

            inputGroupAppend.appendChild(deleteButton);
            inputGroup.appendChild(input);
            inputGroup.appendChild(inputGroupAppend);
            choicesDiv.appendChild(inputGroup);
            

        });

        function removeChoice(button) {
            var choiceDiv = button.parentNode.parentNode;
            choiceDiv.parentNode.removeChild(choiceDiv);
        }
    </script>
</body>
</html>
