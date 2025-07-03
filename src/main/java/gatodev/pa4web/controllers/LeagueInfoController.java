package gatodev.pa4web.controllers;

import gatodev.pa4web.DTO.FighterDTO;
import gatodev.pa4web.DTO.MatchDTO;
import gatodev.pa4web.DTO.ParticipantDTO;
import gatodev.pa4web.models.*;
import gatodev.pa4web.services.*;
import gatodev.pa4web.services.generic.AcademyServiceImpl;
import gatodev.pa4web.services.generic.GenericService;
import gatodev.pa4web.services.generic.LeagueServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "LeagueInfoServlet", urlPatterns = "/leagueInfo")
public class LeagueInfoController extends HttpServlet {
    private final GenericService<Academy> academyService = AcademyServiceImpl.instance;
    private final GenericService<League> leagueService = LeagueServiceImpl.instance;
    private final FighterService fighterService = FighterServiceImpl.instance;
    private final ParticipantService participantService = ParticipantServiceImpl.instance;
    private final MatchService matchService = MatchServiceImpl.instance;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            League league = leagueService.get(Integer.parseInt(req.getParameter("leagueId")));

            if (league == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Liga no existente.");
                return;
            }

            List<ParticipantDTO> participants = getFilterParticipants(league.getId());

            List<MatchDTO> matches = matchService.getMatches(league.getId())
                    .stream()
                    .map(m -> matchService
                            .convertMatchToDTO(m,
                                    filterParticipant(participants, m.getIdFirstParticipant()),
                                    filterParticipant(participants, m.getIdSecondParticipant()),
                                    filterParticipant(participants, m.getIdWinningParticipant())))
                    .collect(Collectors.toList());

            req.setAttribute("fighters", getFighters().toArray(FighterDTO[]::new));
            req.setAttribute("participants", participants.toArray(ParticipantDTO[]::new));
            req.setAttribute("matches", matches.stream()
                    .collect(Collectors.groupingBy(MatchDTO::getPhase)));
            req.getRequestDispatcher("leagueInfo.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de Liga es invalido");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        int idLeague = Integer.parseInt(req.getParameter("leagueId"));

        if (method != null && "createMatches".equalsIgnoreCase(method.trim())) {
            List<Integer> idParticipants = getFilterParticipants(idLeague).stream()
                    .map(ParticipantDTO::getId)
                    .collect(Collectors.toList());

            matchService.generateRandomMatches(idLeague, idParticipants);
            resp.sendRedirect(req.getContextPath() + "/leagueInfo?leagueId=" + idLeague);
            return;
        }

        String idFighterStr = req.getParameter("idFighter");

        // Validar campos vacíos
        if (idFighterStr == null || idFighterStr.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID Luchador es obligatorio.");
            return;
        }

        int idFighter;

        try {
            idFighter = Integer.parseInt(idFighterStr);

            if (fighterService.getFighter(idFighter) == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Luchador no existente.");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Luchador no es válido.");
            return;
        }

        if (method != null && "updateParticipant".equalsIgnoreCase(method.trim())) {
            String idParticipant = (String) req.getAttribute("idParticipant");
            participantService.updateParticipant(Participant.builder()
                    .id(Integer.parseInt(String.valueOf(idParticipant)))
                    .idLeague(idLeague)
                    .idFighter(idFighter)
                    .build());
            resp.sendRedirect(req.getContextPath() + "/leagueInfo?leagueId=" + idLeague);
        } else {
            participantService.addParticipant(Participant.builder()
                    .idLeague(idLeague)
                    .idFighter(idFighter)
                    .build());
            resp.sendRedirect(req.getContextPath() + "/leagueInfo?leagueId=" + idLeague);
        }
    }

    private ParticipantDTO filterParticipant(List<ParticipantDTO> participants, Integer idParticipant) {
        return participants.stream()
                .filter(p -> Objects.equals(p.getId(), idParticipant))
                .findFirst()
                .orElse(null);
    }

    private List<FighterDTO> getFighters() {
        return fighterService.getAllFighters()
                .stream()
                .map(f -> fighterService.convertToFighterDTO(f, academyService
                        .get(f.getIdAcademy())))
                .collect(Collectors.toList());
    }

    private List<ParticipantDTO> getFilterParticipants(int league) {
        return participantService.getParticipants(league)
                .stream()
                .map(p -> {
                    FighterDTO fighterDTO = getFighters().stream()
                            .filter(f -> f.getId().equals(p.getIdFighter()))
                            .findFirst()
                            .orElse(null);
                    return participantService.convertToParticipantDTO(p, fighterDTO);
                })
                .collect(Collectors.toList());
    }
}
