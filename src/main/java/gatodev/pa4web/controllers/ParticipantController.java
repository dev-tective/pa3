package gatodev.pa4web.controllers;

import gatodev.pa4web.DTO.FighterDTO;
import gatodev.pa4web.DTO.ParticipantDTO;
import gatodev.pa4web.models.Academy;
import gatodev.pa4web.models.Fighter;
import gatodev.pa4web.models.League;
import gatodev.pa4web.models.Participant;
import gatodev.pa4web.services.FighterService;
import gatodev.pa4web.services.FighterServiceImpl;
import gatodev.pa4web.services.ParticipantService;
import gatodev.pa4web.services.ParticipantServiceImpl;
import gatodev.pa4web.services.generic.AcademyServiceImpl;
import gatodev.pa4web.services.generic.GenericService;
import gatodev.pa4web.services.generic.LeagueServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ParticipantServlet", urlPatterns = "/participant")
public class ParticipantController extends HttpServlet {
    private final GenericService<Academy> academyService = AcademyServiceImpl.instance;
    private final GenericService<League> leagueService = LeagueServiceImpl.instance;
    private final FighterService fighterService = FighterServiceImpl.instance;
    private final ParticipantService participantService = ParticipantServiceImpl.instance;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            League league = leagueService.get(Integer.parseInt(req.getParameter("leagueId")));

            if (league == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Liga no existente.");
                return;
            }

            List<ParticipantDTO> participants = participantService.getParticipants()
                    .stream()
                    .filter(p -> p.getIdLeague().equals(league.getId()))
                    .map(p -> {
                        Fighter fighter = fighterService.getFighter(p.getIdFighter());
                        FighterDTO fighterDTO = fighterService
                                .convertToFighterDTO(fighter, academyService.get(fighter.getIdAcademy()));
                        return ParticipantDTO.builder()
                                .id(p.getId())
                                .state(p.getState())
                                .place(p.getPlace())
                                .league(league)
                                .fighter(fighterDTO)
                                .build();
                    })
                    .collect(Collectors.toList());

            req.setAttribute("participants", participants);
            req.getRequestDispatcher("participant.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de Liga es invalido");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String placeStr = req.getParameter("place").trim();
        String state = req.getParameter("state").trim();
        String idFighterStr = req.getParameter("idFighter").trim();
        String idLeagueStr = req.getParameter("idLeague").trim();

        // Validar campos vacíos
        if (placeStr.isEmpty() || state.isEmpty() || idFighterStr.isEmpty() || idLeagueStr.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos los campos son obligatorios.");
            return;
        }

        int place;
        int idLeague;
        int idFighter;

        try {
            place = Integer.parseInt(placeStr);
            idLeague = Integer.parseInt(idLeagueStr);
            idFighter = Integer.parseInt(idFighterStr);

            if (leagueService.get(idLeague) == null || fighterService.getFighter(idFighter) == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Liga o Luchador no existente.");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Lugar, ID de Liga o Luchador no son válidos.");
            return;
        }

        participantService.addParticipant(Participant.builder()
                .place(place)
                .state(state)
                .idLeague(idLeague)
                .idFighter(idFighter)
                .build());
        resp.sendRedirect(req.getContextPath() + "/participant?leagueId=" + idLeague);
    }
}
