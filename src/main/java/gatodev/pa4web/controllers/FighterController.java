package gatodev.pa4web.controllers;

import gatodev.pa4web.DTO.FighterDTO;
import gatodev.pa4web.models.Academy;
import gatodev.pa4web.models.Fighter;
import gatodev.pa4web.services.FighterService;
import gatodev.pa4web.services.FighterServiceImpl;
import gatodev.pa4web.services.generic.AcademyServiceImpl;
import gatodev.pa4web.services.generic.GenericService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "FighterServlet", urlPatterns = "/fighter")
public class FighterController extends HttpServlet {
    private final GenericService<Academy> academyService = AcademyServiceImpl.instance;
    private final FighterService fighterService = FighterServiceImpl.instance;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FighterDTO[] fighters = fighterService.getAllFighters()
                .stream()
                .map(f -> fighterService
                        .convertToFighterDTO(f, academyService.get(f.getIdAcademy())))
                .toArray(FighterDTO[]::new);

        req.setAttribute("academies", academyService.getAll().toArray(Academy[]::new));
        req.setAttribute("fighters", fighters);
        req.getRequestDispatcher("fighter.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("fullName");
        String ageStr = req.getParameter("age");
        String weightStr = req.getParameter("weight");
        String gender = req.getParameter("gender");
        String rank = req.getParameter("rank");
        String modality = req.getParameter("modality");
        String idAcademyStr = req.getParameter("idAcademy");

        // Validar campos nulos o vacíos
        if (name == null || name.trim().isEmpty() ||
                ageStr == null || ageStr.trim().isEmpty() ||
                weightStr == null || weightStr.trim().isEmpty() ||
                gender == null || gender.trim().isEmpty() ||
                rank == null || rank.trim().isEmpty() ||
                modality == null || modality.trim().isEmpty() ||
                idAcademyStr == null || idAcademyStr.trim().isEmpty()) {

            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos los campos son obligatorios.");
            return;
        }

        int idAcademy;
        int age;
        int weight;

        try {
            idAcademy = Integer.parseInt(idAcademyStr);
            age = Integer.parseInt(ageStr);
            weight = Integer.parseInt(weightStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Edad, peso e ID de academia no son válidos.");
            return;
        }

        // Validaciones adicionales
        if (age <= 0 || weight <= 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Edad y peso deben ser mayores que 0.");
            return;
        }

        String method = req.getParameter("_method");

        if (method != null && "update".equalsIgnoreCase(method.trim())) {
            String id = req.getParameter("id").trim();

            if (id.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"El ID es obligatorio.");
                return;
            }

            try {
                fighterService.updateFighter(Fighter.builder()
                        .id(Integer.parseInt(id))
                        .fullName(name)
                        .age(age)
                        .weight(weight + "kg")
                        .gender(gender)
                        .rank(rank)
                        .modality(modality)
                        .idAcademy(idAcademy)
                        .build());
                resp.sendRedirect(req.getContextPath() + "/fighter");
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID invalido.");
            }

            return;
        }

        fighterService.addFighter(Fighter.builder()
                .fullName(name)
                .age(age)
                .weight(weight + "kg")
                .gender(gender)
                .rank(rank)
                .modality(modality)
                .idAcademy(idAcademy)
                .build());
        resp.sendRedirect(req.getContextPath() + "/fighter");
    }
}
