package gatodev.pa4web.controllers;

import gatodev.pa4web.models.Academy;
import gatodev.pa4web.services.generic.AcademyServiceImpl;
import gatodev.pa4web.services.generic.GenericService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AcademyServlet", urlPatterns = "/academy")
public class AcademyController extends HttpServlet {
    private final GenericService<Academy> academyService = AcademyServiceImpl.instance;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("academies", academyService.getAll().toArray(Academy[]::new));
        req.getRequestDispatcher("/academy.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        // Primero manejar DELETE
        if (method != null && "delete".equalsIgnoreCase(method.trim())) {
            String id = req.getParameter("id");

            if (id == null || id.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID es obligatorio.");
                return;
            }

            try {
                academyService.delete(Integer.parseInt(id));
                resp.sendRedirect(req.getContextPath() + "/academy");
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido.");
            }

            return;
        }

        // Luego, validar campos comunes
        String name = req.getParameter("academyName");
        String ruc = req.getParameter("ruc");

        if (name == null || name.trim().isEmpty() || ruc == null || ruc.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre y ruc son obligatorios.");
            return;
        }

        if (method != null && "update".equalsIgnoreCase(method.trim())) {
            String id = req.getParameter("id").trim();

            if (id.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID es obligatorio.");
                return;
            }

            try {
                academyService.update(Academy.builder()
                        .id(Integer.parseInt(id))
                        .name(name)
                        .ruc(ruc)
                        .build());
                resp.sendRedirect(req.getContextPath() + "/academy");
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID invalido.");
            }

            return;
        }

        // Por defecto: agregar
        academyService.add(Academy.builder()
                .name(name)
                .ruc(ruc)
                .build());
        resp.sendRedirect(req.getContextPath() + "/academy");
    }
}
