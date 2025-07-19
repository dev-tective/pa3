<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="gatodev.pa4web.models.User" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Usuarios | Karate System</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<%@ include file="navbar.jsp" %>

<%
    User[] users = (User[]) request.getAttribute("users");
%>

<div class="container py-5">
    <h1 class="text-center mb-4">Gestión de Usuarios</h1>

    <!-- Buscador -->
    <div class="mb-4">
        <input type="text"
               id="userSearch"
               class="form-control"
               placeholder="Buscar por nombre de usuario...">
    </div>

    <!-- Lista de usuarios -->
    <div class="row justify-content-center" id="userContainer">
        <% if (users != null) {
            for (User user : users) {
        %>
        <div class="col-md-4 mb-4 user-item">
            <div class="card h-100">
                <div class="card-body text-center">
                    <form method="post" action="user" class="edit-form d-flex flex-column align-items-center" style="gap: 0.5rem;">
                        <input type="hidden" name="_method" value="update">
                        <input type="hidden" name="id" value="<%= user.getId() %>">

                        <input type="text" name="username" class="form-control text-center" style="width: 80%;" value="<%= user.getUsername() %>" readonly>
                        <input type="password" name="password" class="form-control text-center" style="width: 80%;" value="<%= user.getPassword() %>" readonly>
                        <input type="text" name="nombres" class="form-control text-center" style="width: 80%;" value="<%= user.getNombres() %>" readonly>

                        <select name="rol" class="form-control text-center" style="width: 80%;" disabled>
                            <option selected><%= user.getRol() %></option>
                            <option value="ADMIN">ADMIN</option>
                            <option value="USER">USER</option>
                        </select>

                        <div class="d-flex gap-2 mt-2">
                            <button type="button" class="btn btn-sm btn-outline-secondary toggle-edit" title="Editar">
                                <i class="bi bi-pencil-square"></i>
                            </button>
                            <button type="submit" class="btn btn-sm btn-success d-none confirm-edit" title="Guardar">
                                <i class="bi bi-check-lg"></i>
                            </button>
                            <form method="post" action="user" onsubmit="return confirm('¿Estás seguro de eliminar este usuario?')">
                                <input type="hidden" name="_method" value="delete">
                                <input type="hidden" name="id" value="<%= user.getId() %>">
                                <button type="submit" class="btn btn-sm btn-danger" title="Eliminar">
                                    <i class="bi bi-trash-fill"></i>
                                </button>
                            </form>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <% }
        } %>

        <!-- Tarjeta para nuevo usuario -->
        <div class="col-md-4 mb-4 user-item">
            <div class="card h-100 border-success">
                <div class="card-body d-flex flex-column justify-content-center">
                    <form method="post" action="user" class="d-flex flex-column align-items-center" style="gap: 1rem;">
                        <input type="text" name="username" class="form-control text-center" style="width: 80%;" placeholder="Usuario" required>
                        <input type="password" name="password" class="form-control text-center" style="width: 80%;" placeholder="Contraseña" required>
                        <input type="text" name="nombres" class="form-control text-center" style="width: 80%;" placeholder="Nombres completos" required>

                        <select name="rol" class="form-control text-center" style="width: 80%;" required>
                            <option value="">Selecciona Rol</option>
                            <option value="ADMIN">ADMIN</option>
                            <option value="USER">USER</option>
                        </select>

                        <button type="submit" class="btn btn-success">
                            <i class="bi bi-plus-circle me-1"></i> Agregar Usuario
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Script -->
<script src="js/user.js"></script>
</body>
</html>
