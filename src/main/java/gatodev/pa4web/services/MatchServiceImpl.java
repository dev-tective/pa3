package gatodev.pa4web.services;

import gatodev.pa4web.DAO.MatchDAO;
import gatodev.pa4web.DTO.MatchDTO;
import gatodev.pa4web.DTO.ParticipantDTO;
import gatodev.pa4web.models.Match;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchServiceImpl implements MatchService {
    private final MatchDAO matchDAO = MatchDAO.instance;

    @Override
    public Match addMatch(Match match) {
        try {
            return matchDAO.save(match)
                    .orElseThrow(() -> new RuntimeException("Llave no pudo ser creada."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean deleteMatch(int matchId) {
        try {
            return matchDAO.deleteById(matchId);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Match getMatch(int id) {
        try {
            return matchDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException("Llave no encontrada."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Match> getMatches() {
        try {
            return matchDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void generateRandomMatches(Integer idLeague, List<Integer> idParticipants) {
        Collections.shuffle(idParticipants);

        int total = nextPowerOfTwo(idParticipants.size());

        while (idParticipants.size() < total) {
            idParticipants.add(null);
        }

        for (int i = 0; i < idParticipants.size(); i += 2) {
            Match match = new Match();
            match.setPhase("Fase Inicial");
            match.setIdLeague(idLeague);
            match.setIdFirstFighter(idParticipants.get(i));
            match.setIdSecondFighter(idParticipants.get(i + 1));

            if (match.getIdFirstFighter() == null || match.getIdSecondFighter() == null) {
                match.setState("BYE");
            }

            addMatch(match);
        }
    }

    private int nextPowerOfTwo(int n) {
        int power = 1;
        while (power < n) {
            power *= 2;
        }
        return power;
    }


    @Override
    public MatchDTO convertMatchToDTO(Match match, List<ParticipantDTO> participants, ParticipantDTO winner) {
        return null;
    }
}
