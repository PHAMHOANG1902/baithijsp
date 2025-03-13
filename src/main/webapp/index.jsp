<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>quản lý cầu thủ</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">quản lý cầu thủ</h2>

    <!-- Form thêm mới cầu thủ -->
    <form action="players" method="post" class="mb-4">
        <div class="row">
            <div class="col-md-3">
                <input type="text" name="name" class="form-control" placeholder="tên cầu thủ" required>
            </div>
            <div class="col-md-3">
                <input type="text" name="fullName" class="form-control" placeholder="họ và tên đầy đủ" required>
            </div>
            <div class="col-md-2">
                <input type="number" name="age" class="form-control" placeholder="tuổi" required>
            </div>
            <div class="col-md-2">
                <select name="indexId" class="form-control" required>
                    <option value="1">tốc độ</option>
                    <option value="2">sức mạnh</option>
                    <option value="3">độ chính xác</option>
                </select>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-primary w-100">thêm cầu thủ</button>
            </div>
        </div>
    </form>

    <!-- Bảng danh sách cầu thủ -->
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>tên</th>
            <th>họ và tên</th>
            <th>tuổi</th>
            <th>chỉ số</th>
            <th>hành động</th>
        </tr>
        </thead>
        <tbody>
        <%@ page import="java.util.List" %>
        <%@ page import="model.Player" %>
        <%
            List<Player> players = (List<Player>) request.getAttribute("players");
            if (players != null) {
                for (Player player : players) {
        %>
        <tr>
            <td><%= player.getPlayerId() %></td>
            <td><%= player.getName() %></td>
            <td><%= player.getFullName() %></td>
            <td><%= player.getAge() %></td>
            <td><%= player.getIndexId() %></td>
            <td>
                <a href="editPlayer?id=<%= player.getPlayerId() %>" class="btn btn-warning btn-sm">Sửa</a>
                <a href="#" onclick="confirmDelete(<%= player.getPlayerId() %>)" class="btn btn-danger btn-sm">Xóa</a>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>

<script>
    function confirmDelete(playerId) {
        if (confirm("Bạn có chắc muốn xóa cầu thủ này?")) {
            window.location.href = "deletePlayer?id=" + playerId;
        }
    }
</script>
</body>
</html>
