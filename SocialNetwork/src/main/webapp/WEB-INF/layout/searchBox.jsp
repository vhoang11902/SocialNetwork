<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form style="display:flex; width: 100%; marign: 20px; justify-content: center; align-items: center" action="${action}">
    <!-- if path is /admin/route then these elif path is /admin/trip then add 1 time more-->
    <c:choose>
        <c:when test="${users != null}">
            <div class="form-floating mb-3 mt-3" >
                <input type="text" class="form-control" id="plate"  placeholder="" name="phoneNumber">
                <label for="plate">Số Điện Thoại</label>
            </div>
            <div class="form-floating mt-3 mb-3">
                <input type="text" class="form-control" id="seats" placeholder="" name="name">
                <label for="seats">Name</label>
            </div>
            <div class="form-floating mt-3 mb-3">
                <input type="text" class="form-control" id="type" placeholder="" name="email">
                <label for="type">Mail</label>
            </div>
        </c:when>
        <c:when test="${bus != null}">
            <div class="form-floating mb-3 mt-3" >
                <input type="text" class="form-control" id="plate"  placeholder="" name="plate">
                <label for="plate">Plate</label>
            </div>
            <div class="form-floating mt-3 mb-3">
                <input type="number" class="form-control" id="seats" placeholder="" name="seats">
                <label for="seats">Number of seats</label>
            </div>
            <div class="form-floating mt-3 mb-3">
                <input type="number" class="form-control" id="type" placeholder="" name="type">
                <label for="type">Type</label>
            </div>
        </c:when>
        <c:when test="${station == null}">
            <div class="form-floating mb-3 mt-3" >
                <input type="text" class="form-control" id="origin"  placeholder="" name="origin">
                <label for="origin">Origin</label>
            </div>
            <div class="form-floating mt-3 mb-3">
                <input type="text" class="form-control" id="destination" placeholder="" name="destination">
                <label for="destination">Destination</label>
            </div>
            <c:if test="${trip != null}">
                <div class="form-floating mb-3 mt-3" >
                    <input type="date" class="form-control" id="setOffFrom" placeholder="" name="setOff">
                    <label for="setOffFrom">Set Off</label>
                </div>
            </c:if>
        </c:when>
        <c:otherwise>
            <div class="form-floating mb-3 mt-3" >
                <input type="text" class="form-control" id="province" placeholder="" name="province">
                <label for="province">Province</label>
            </div>
        </c:otherwise>
    </c:choose>
    <button class="btn btn-primary" style="height: 50px; width: 100px; padding: 8px" type="submit" >Search</button>

</form>