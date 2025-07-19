<%@ page session="true" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
  <div class="container-fluid">
    <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/league">
      Karate League
    </a>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
            data-bs-target="#navbarNav" aria-controls="navbarNav"
            aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto">
        <% if (session.getAttribute("user") != null) { %>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/league">Ligas</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/academy">Academias</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/fighter">Luchadores</a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-warning" href="${pageContext.request.contextPath}/logout">
              <i class="bi bi-box-arrow-right"></i> Cerrar sesi�n
            </a>
          </li>
        <% } else { %>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/login">
              <i class="bi bi-box-arrow-in-right"></i> Iniciar sesi�n
            </a>
          </li>
        <% } %>
      </ul>
    </div>
  </div>
</nav>
