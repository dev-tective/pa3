<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="gatodev.pa4web.DTO.FighterDTO" %>
<%@ page import="gatodev.pa4web.models.Academy" %>
<%@ page import="gatodev.pa4web.models.League" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Karate System</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<% FighterDTO[] fighters = (FighterDTO[]) request.getAttribute("fighters"); %>
<% Academy[] academies = (Academy[]) request.getAttribute("academies"); %>
<% League[] leagues = (League[]) request.getAttribute("leagues"); %>

<%@ include file="navbar.jsp" %>

<h1 class="text-center mb-4">Luchadores Registrados</h1>

<!-- Buscador de luchadores -->
<div class="mb-4">
    <input type="text" id="fighterSearch" class="form-control" placeholder="Buscar luchador por nombre...">
</div>

<!-- Despliegue de todos los luchadores -->
<div class="row justify-content-center" id="leagueContainer">
    <% for (FighterDTO fighter : fighters) { %>
    <div class="col-md-4 mb-4 fighter-item">
        <div class="card fighter-card h-100">
            <div class="card-body d-flex flex-column justify-content-between">
                <div class="text-center mb-2">
                    <form method="post"
                          class="edit-form d-flex flex-column align-items-center"
                          action="fighter"
                          enctype="multipart/form-data"
                          style="gap: 0.5rem;">
                        <input type="hidden" name="_method" value="update">
                        <input type="hidden" name="id" value="<%= fighter.getId() %>">

                        <input type="text" name="dni" class="form-control text-center" style="width: 80%;"
                               value="<%= fighter.getDni() %>" readonly>

                        <input type="text" name="fullName" class="form-control text-center" style="width: 80%;"
                               value="<%= fighter.getFullName() %>" readonly>

                        <input type="number" name="age" class="form-control text-center" style="width: 80%;"
                               value="<%= fighter.getAge() %>" readonly>

                        <input type="number" name="weight" class="form-control text-center" style="width: 80%;"
                               value="<%= fighter.getWeight().replace("kg", "") %>" readonly>

                        <select name="gender" class="form-control text-center" style="width: 80%;" disabled>
                            <option selected><%= fighter.getGender() %></option>
                            <option value="Masculino">Masculino</option>
                            <option value="Femenino">Femenino</option>
                        </select>

                        <select name="rank" class="form-control text-center" style="width: 80%;" disabled>
                            <option selected><%= fighter.getRank() %></option>
                            <% for (int i = 10; i >= 1; i--) { %>
                            <option value="<%= i + " kyu" %>"><%= i + " kyu" %></option>
                            <% } %>
                        </select>

                        <select name="modality" class="form-control text-center" style="width: 80%;" disabled>
                            <option selected><%= fighter.getModality() %></option>
                            <option value="Kumite">Kumite</option>
                        </select>

                        <select name="idAcademy" class="form-control text-center" style="width: 80%;" disabled>
                            <option selected value="<%= fighter.getAcademy().getId() %>">
                                <%= fighter.getAcademy().getName() %>
                            </option>
                            <% for (Academy academy : academies) {
                                if (!academy.getId().equals(fighter.getAcademy().getId())) { %>
                            <option value="<%= academy.getId() %>"><%= academy.getName() %></option>
                            <% }} %>
                        </select>

                        <select name="idLeague" class="form-control text-center" style="width: 80%;" disabled>
                            <option selected value="<%= fighter.getLeague().getId() %>">
                                <%= fighter.getLeague().getName() %>
                            </option>
                            <% for (League league : leagues) {
                                if (!league.getId().equals(fighter.getLeague().getId())) { %>
                            <option value="<%= league.getId() %>"><%= league.getName() %></option>
                            <% }} %>
                        </select>

                        <input type="file" name="photo" class="form-control text-center" style="width: 80%;" disabled>

                        <div class="d-flex justify-content-center gap-2">
                            <button type="button" class="btn btn-sm btn-outline-secondary toggle-edit" title="Editar">
                                <i class="bi bi-pencil-square"></i>
                            </button>
                            <button type="submit" class="btn btn-sm btn-success d-none confirm-edit" title="Guardar">
                                <i class="bi bi-check-lg"></i>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <% } %>

    <!-- Tarjeta para agregar nuevo luchador -->
    <div class="col-md-4 mb-4 fighter-item">
        <div class="card fighter-card h-100 border-success">
            <div class="card-body d-flex flex-column justify-content-center">
                <form method="post"
                      class="d-flex flex-column align-items-center"
                      action="fighter"
                      enctype="multipart/form-data"
                      style="gap: 1rem;">
                    <input type="text" name="dni" class="form-control text-center" style="width: 80%;" placeholder="DNI" required>
                    <input type="text" name="fullName" class="form-control text-center" style="width: 80%;" placeholder="Nombre completo" required>
                    <input type="number" name="age" class="form-control text-center" style="width: 80%;" placeholder="Edad" required>
                    <input type="number" name="weight" class="form-control text-center" style="width: 80%;" placeholder="Peso (kg)" step="0.1" required>

                    <select name="gender" class="form-control text-center" style="width: 80%;" required>
                        <option value="">Sexo</option>
                        <option value="Masculino">Masculino</option>
                        <option value="Femenino">Femenino</option>
                    </select>

                    <select name="rank" class="form-control text-center" style="width: 80%;" required>
                        <option value="">Rango</option>
                        <% for (int i = 1; i <= 10; i++) { %>
                        <option value="<%= i + " kyu" %>"><%= i + " kyu" %></option>
                        <% } %>
                    </select>

                    <select name="modality" class="form-control text-center" style="width: 80%;" required>
                        <option value="">Modalidad</option>
                        <option value="Kumite">Kumite</option>
                    </select>

                    <select name="idAcademy" class="form-control text-center" style="width: 80%;" required>
                        <option value="">Academia</option>
                        <% for (Academy academy : academies) { %>
                        <option value="<%= academy.getId() %>"><%= academy.getName() %></option>
                        <% } %>
                    </select>

                    <select name="idLeague" class="form-control text-center" style="width: 80%;" required>
                        <option value="">Liga</option>
                        <% for (League league : leagues) { %>
                        <option value="<%= league.getId() %>"><%= league.getName() %></option>
                        <% } %>
                    </select>

                    <input type="file" name="photo" class="form-control text-center" style="width: 80%;" accept="image/*" required>

                    <button type="submit" class="btn btn-success">
                        <i class="bi bi-plus-circle me-1"></i> Agregar Luchador
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="js/fighter.js"></script>
</body>
</html>
