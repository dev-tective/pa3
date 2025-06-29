package gatodev.pa4web.controllers;

import gatodev.pa4web.models.League;
import gatodev.pa4web.services.generic.GenericService;
import gatodev.pa4web.services.generic.LeagueServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "LeagueServlet", urlPatterns = "/league")
public class LeagueController extends HttpServlet {
    private final GenericService<League> leagueService = LeagueServiceImpl.instance;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("leagues", leagueService.getAll());
        req.getRequestDispatcher("/league.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("leagueName").trim();

        if (name.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre es obligatorio.");
            return;
        }

        String method = req.getParameter("_method").trim();

        if ("update".equalsIgnoreCase(method)) {
            String id = req.getParameter("id").trim();

            if (id.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"ID es obligatorio.");
                return;
            }

            try {
                leagueService.update(League.builder()
                        .id(Integer.parseInt(id))
                        .name(name)
                        .build());
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID invalido.");
            }

            return;
        }

        leagueService.add(League.builder()
                .name(name)
                .build());
        resp.sendRedirect(req.getContextPath() + "/league");
    }
}
