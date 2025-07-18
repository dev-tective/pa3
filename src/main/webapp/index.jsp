<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="gatodev.pa4web.models.League" %>
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

    <% League[] leagues = (League[]) request.getAttribute("leagues"); %>

    <%@ include file="navbar.jsp" %>

    <div class="container py-5">
        <h1 class="text-center mb-4">Ligas Registradas</h1>

        <!-- Buscador de ligas -->
        <div class="mb-4">
            <input type="text"
                   id="leagueSearch"
                   class="form-control"
                   placeholder="Buscar liga por nombre...">
        </div>

        <!-- Despliegue de todas las ligas -->
        <div class="row justify-content-center" id="leagueContainer">

            <%
                for (League league : leagues) {
            %>

            <div class="col-md-4 mb-4 league-item">
                <div class="card league-card h-100">
                    <div class="card-body d-flex flex-column justify-content-between">
                        <!-- Nombre editable -->
                        <div class="text-center mb-2">
                            <form method="post"
                                  class="edit-form d-flex justify-content-center align-items-center"
                                  style="gap: 0.5rem;"
                                  action="league">
                                <input type="hidden" name="_method" value="update">
                                <input type="hidden" name="id" value="<%= league.getId() %>">

                                <input type="text"
                                       name="leagueName"
                                       class="form-control text-center name"
                                       style="width: 60%;"
                                       value="<%= league.getName() %>"
                                       readonly>

                                <button type="button" class="btn btn-sm btn-outline-secondary toggle-edit" title="Editar">
                                    <i class="bi bi-pencil-square"></i>
                                </button>

                                <button type="submit" class="btn btn-sm btn-success d-none confirm-edit" title="Guardar">
                                    <i class="bi bi-check-lg"></i>
                                </button>
                            </form>
                            <!-- Botón eliminar -->
                            <form method="post"
                                  action="league"
                                  onsubmit="return confirm('¿Estás seguro de que quieres eliminar esta liga?');">
                                <input type="hidden" name="_method" value="delete">
                                <input type="hidden" name="id" value="<%= league.getId() %>">
                                <button type="submit" class="btn btn-sm btn-danger w-100 mt-2" title="Eliminar">
                                    <i class="bi bi-trash"></i> Eliminar
                                </button>
                            </form>
                        </div>

                        <!-- Acciones -->
                        <div class="d-grid gap-2 mt-3">
                            <a href="${pageContext.request.contextPath}/leagueInfo?leagueId=<%= league.getId() %>"
                               class="btn btn-outline-light">Ver Liga</a>
                        </div>
                    </div>
                </div>
            </div>

            <%
                }
            %>

            <!-- Tarjeta para agregar nueva liga -->
            <div class="col-md-4 mb-4 league-item">
                <div class="card league-card h-100 border-success">
                    <div class="card-body d-flex flex-column justify-content-center">
                        <form method="post"
                              class="d-flex flex-column align-items-center"
                              action="league"
                              style="gap: 1rem;">
                            <input type="text"
                                   name="leagueName"
                                   class="form-control text-center"
                                   style="width: 80%;"
                                   placeholder="Nueva liga..."
                                   required>

                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-plus-circle me-1"></i> Agregar Liga
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Script para filtrar las ligas y editar nombres -->
    <script src="js/league.js"></script>
    <script>
        const PASSWORD = "liga123";

        // Verificar si ya está logueado en esta sesión
        if (!sessionStorage.getItem("isAuthenticated")) {
            const userInput = prompt("Ingresa la contraseña para acceder a las ligas:");

            if (userInput === PASSWORD) {
                sessionStorage.setItem("isAuthenticated", "true");
            } else {
                alert("Contraseña incorrecta. No tienes acceso.");
                document.body.innerHTML = "<h2 style='text-align: center; color: red;'>Acceso denegado.</h2>";
            }
        }
    </script>
</body>
</html>
