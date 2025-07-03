<%@ page import="gatodev.pa4web.DTO.ParticipantDTO" %>
<%@ page import="gatodev.pa4web.DTO.MatchDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="gatodev.pa4web.DTO.FighterDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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

    <%@ include file="navbar.jsp" %>

    <%
        FighterDTO[] fighters = (FighterDTO[]) request.getAttribute("fighters");
        ParticipantDTO[] participant = (ParticipantDTO[]) request.getAttribute("participants");
        Map<Integer, List<MatchDTO>> matchesByPhase =
                (Map<Integer, List<MatchDTO>>) request.getAttribute("matches");
    %>

    <div class="container mt-4">
        <h2 class="mb-3">Participantes</h2>

        <!-- Tabla de participantes -->
        <table class="table table-bordered table-hover">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Academia</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (participant != null && participant.length > 0) {
                    for (ParticipantDTO p : participant) {
            %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getFighter().getFullName() %></td>
                <td><%= p.getFighter().getAcademy().getName() %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="3" class="text-center">No hay participantes registrados.</td>
            </tr>
            <%
                }
            %>

            <!-- Fila para agregar nuevo participante -->
            <tr>
                <form method="post" action="leagueInfo">
                    <td>Nuevo</td>
                    <td>
                        <select name="idFighter" class="form-select" required>
                            <option value="" disabled selected>Seleccionar luchador</option>
                            <%
                                if (fighters != null) {
                                    for (FighterDTO f : fighters) {
                            %>
                            <option value="<%= f.getId() %>"><%= f.getFullName() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </td>
                    <td>
                        <input type="hidden" name="leagueId" value="<%= request.getParameter("leagueId") %>">
                        <button type="submit" class="btn btn-success btn-sm">
                            <i class="bi bi-person-plus-fill"></i> Agregar
                        </button>
                    </td>
                </form>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="container mt-5">
        <h2 class="mb-3">Enfrentamientos</h2>

        <%
            if (matchesByPhase != null && !matchesByPhase.isEmpty()) {
                for (Map.Entry<Integer, List<MatchDTO>> entry : matchesByPhase.entrySet()) {
                    int phase = entry.getKey();
                    List<MatchDTO> matchesInPhase = entry.getValue();
        %>
        <h4 class="mt-4">Fase <%= phase %></h4>
        <div class="row">
            <% for (MatchDTO match : matchesInPhase) { %>
            <div class="col-md-4 mb-3">
                <div class="card shadow-sm border-primary">
                    <div class="card-body">
                        <h5 class="card-title text-center">
                            <%= match.getFirstParticipant().getFighter().getFullName() %>
                            <strong>vs</strong>
                            <%= match.getSecondParticipant().getFighter().getFullName() %>
                        </h5>
                        <p class="card-text text-center">
                            Estado: <%= match.getState() %>
                        </p>
                        <% if (match.getWinner() != null) { %>
                        <div class="alert alert-success text-center p-1">
                            Ganador: <strong><%= match.getWinner().getFighter().getFullName() %></strong>
                        </div>
                        <% } else { %>
                        <div class="alert alert-secondary text-center p-1">
                            Sin resultado aún
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <%
            }
        } else { %>
        <div class="alert alert-info">No hay enfrentamientos registrados.</div>

        <form method="post" action="leagueInfo">
            <input type="hidden" name="leagueId" value="<%= request.getParameter("leagueId") %>">
            <input type="hidden" name="_method" value="createMatches">
            <button type="submit" class="btn btn-primary mt-2">
                <i class="bi bi-play-fill"></i> Generar Enfrentamientos
            </button>
        </form>
        <% } %>

    </div>

</body>
</html>
