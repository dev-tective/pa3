<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="gatodev.pa4web.models.Academy" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Karate System</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

    <% Academy[] academies = (Academy[]) request.getAttribute("academies"); %>

    <%@ include file="navbar.jsp" %>

    <div class="container py-5">
        <h1 class="text-center mb-4">Academias Registradas</h1>

        <!-- Buscador de academias -->
        <div class="mb-4">
            <input type="text"
                   id="academySearch"
                   class="form-control"
                   placeholder="Buscar academia por nombre...">
        </div>

        <!-- Mostrar academias existentes -->
        <div class="row justify-content-center" id="academyContainer">
            <% for (Academy academy : academies) { %>
            <div class="col-md-4 mb-4 academy-item">
                <div class="card academy-card h-100">
                    <div class="card-body d-flex flex-column justify-content-between">
                        <!-- Formulario editable -->
                        <div class="text-center mb-2">
                            <form method="post"
                                  class="edit-form d-flex flex-column align-items-center"
                                  action="academy"
                                  style="gap: 0.5rem;">
                                <input type="hidden" name="_method" value="update">
                                <input type="hidden" name="id" value="<%= academy.getId() %>">

                                <input type="text"
                                       name="academyName"
                                       class="form-control text-center"
                                       style="width: 80%;"
                                       value="<%= academy.getName() %>"
                                       readonly>

                                <input type="text"
                                       name="ruc"
                                       class="form-control text-center"
                                       style="width: 80%;"
                                       value="<%= academy.getRuc() %>"
                                       readonly>

                                <div class="mt-2 d-flex gap-2">
                                    <button type="button" class="btn btn-sm btn-outline-secondary toggle-edit" title="Editar">
                                        <i class="bi bi-pencil-square"></i>
                                    </button>
                                    <button type="submit" class="btn btn-sm btn-success d-none confirm-edit" title="Guardar">
                                        <i class="bi bi-check-lg"></i>
                                    </button>
                                </div>
                            </form>
                            <!-- Formulario de eliminación SEPARADO -->
                            <form method="post"
                                  action="academy"
                                  onsubmit="return confirm('¿Estás seguro de que quieres eliminar esta academia?');"
                                  class="mt-2">
                                <input type="hidden" name="_method" value="delete">
                                <input type="hidden" name="id" value="<%= academy.getId() %>">
                                <button type="submit" class="btn btn-sm btn-danger" title="Eliminar">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>

            <!-- Tarjeta para agregar nueva academia -->
            <div class="col-md-4 mb-4 academy-item">
                <div class="card academy-card h-100 border-success">
                    <div class="card-body d-flex flex-column justify-content-center">
                        <form method="post"
                              class="d-flex flex-column align-items-center"
                              action="academy"
                              style="gap: 1rem;">
                            <input type="text"
                                   name="academyName"
                                   class="form-control text-center"
                                   style="width: 80%;"
                                   placeholder="Nombre"
                                   required>
                            <input type="text"
                                   name="ruc"
                                   class="form-control text-center"
                                   style="width: 80%;"
                                   placeholder="RUC"
                                   required>

                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-plus-circle me-1"></i> Agregar Academia
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="js/academy.js"></script>
</body>
</html>
