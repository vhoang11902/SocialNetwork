<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="text-center">THỐNG KÊ</h2>

<h2 class="text-center mt-5">SỐ LƯỢNG NGƯỜI DÙNG</h2>
<div class="row">
    <div class="col-md-6">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>${type}</th>
                    <th>Số lượng User</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${userStats}" var="s">
                    <tr>
                        <td>${s[0]}</td>
                        <td>${s[1]}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <form>
            <div class="row">
                <div class="col-md-3">
                    <input type="radio" name="type" value="year" id="radio1" checked>
                    <label for="radio1">Year</label>
                </div>
                <div class="col-md-3">
                    <input type="radio" name="type" value="month" id="radio3">
                    <label for="radio3">Month</label>
                </div>
                <div class="col-md-3">
                    <select name="yearType">
                        <option value="2024">2024</option>
                        <option value="2023">2023</option>
                        <option value="2022">2022</option>
                        <!-- Thêm các option khác nếu cần -->
                    </select>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </div>
        </form>
    </div>
    <div class="col-md-6">
        <canvas id="userChart"></canvas>
    </div>
</div>


<h2 class="text-center mt-5">SỐ LƯỢNG BÀI VIẾT</h2>
<div class="row">
    <div class="col-md-6">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>${type}</th>
                    <th>Số lượng Post</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${postStats}" var="p">
                    <tr>
                        <td>${p[0]}</td>
                        <td>${p[1]}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <form>
            <div class="row">
                <div class="col-md-3">
                    <input type="radio" name="type" value="year" id="radio1" checked>
                    <label for="radio1">Year</label>
                </div>
                <div class="col-md-3">
                    <input type="radio" name="type" value="month" id="radio3">
                    <label for="radio3">Month</label>
                </div>
                <div class="col-md-3">
                    <select name="yearType">
                        <option value="2024">2024</option>
                        <option value="2023">2023</option>
                        <option value="2022">2022</option>
                        <!-- Thêm các option khác nếu cần -->
                    </select>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </div>
        </form>
    </div>
    <div class="col-md-6">
        <canvas id="postChart"></canvas>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    // Dữ liệu thống kê từ JSP sang JavaScript
    let labelsUser = [];
    let infoUser = [];

    <c:forEach items="${userStats}" var="u">
    labelsUser.push('${type} ${u[0]}');
        infoUser.push(${u[1]});
    </c:forEach>


    let labelsPost = [];
    let infoPost = [];
    <c:forEach items="${postStats}" var="p">
        labelsPost.push('${type} ${p[0]}');
            infoPost.push(${p[1]});
    </c:forEach>


            // Tạo biểu đồ bằng Chart.js
            const ctxUser = document.getElementById("userChart").getContext('2d');
            new Chart(ctxUser, {
                type: 'bar',
                data: {
                    labels: labelsUser,
                    datasets: [{
                            label: 'Số lượng User',
                            data: infoUser,
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            const ctxPost = document.getElementById("postChart").getContext('2d');
            new Chart(ctxPost, {
                type: 'bar',
                data: {
                    labels: labelsPost,
                    datasets: [{
                            label: 'Số lượng Post',
                            data: infoPost,
                            backgroundColor: 'rgba(255, 99, 132, 0.2)',
                            borderColor: 'rgba(255, 99, 132, 1)',
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
</script>
